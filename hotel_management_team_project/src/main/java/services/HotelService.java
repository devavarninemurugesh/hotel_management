package services;
import models.*;
import repository.InMemoryDataStore;
import java.util.*;
import java.time.*;
public class HotelService {
    private final InMemoryDataStore store;
    public HotelService(InMemoryDataStore store){this.store=store;}
    public void addHotel(Hotel hotel,User actor){
        requireManager(actor); if(hotel.getRooms().isEmpty()) throw new IllegalArgumentException("Hotel must have at least one room");
        store.hotels.put(hotel.getId(),hotel); for(Room r:hotel.getRooms())store.rooms.put(r.getId(),r);
    }
    public void addRoom(String hotelId,Room room,User actor){
        requireManager(actor); Hotel h=requireHotel(hotelId); if(!room.getHotelId().equals(hotelId))throw new IllegalArgumentException("Room hotel mismatch");
        h.addRoom(room);store.rooms.put(room.getId(),room);
    }
    public void blockMaintenance(String roomId,LocalDate start,LocalDate end,String reason,User actor){
        requireManager(actor); if(!store.rooms.containsKey(roomId))throw new IllegalArgumentException("Room not found");
        store.maintenanceBlocks.add(new MaintenanceBlock(roomId,start,end,reason));
    }
    public void deleteHotel(String hotelId,User actor){
        requireManager(actor);
        boolean upcoming=store.bookings.values().stream().anyMatch(b->b.getHotelId().equals(hotelId)&&b.getCheckOut().isAfter(LocalDate.now())&&
            (b.getStatus()==Booking.Status.CONFIRMED||b.getStatus()==Booking.Status.CHECKED_IN||b.getStatus()==Booking.Status.PENDING_PAYMENT));
        if(upcoming)throw new IllegalStateException("Cannot delete hotel with upcoming bookings");
        Hotel h=requireHotel(hotelId); h.getRooms().forEach(r->store.rooms.remove(r.getId()));store.hotels.remove(hotelId);
    }
    public Hotel requireHotel(String id){Hotel h=store.hotels.get(id);if(h==null)throw new IllegalArgumentException("Hotel not found");return h;}
    private void requireManager(User actor){if(!(actor instanceof Admin||actor instanceof HotelManager))throw new SecurityException("Only Admin/Hotel Manager can do this");}
}
