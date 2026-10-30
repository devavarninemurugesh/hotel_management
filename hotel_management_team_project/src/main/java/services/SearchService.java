package services;
import models.*;
import repository.InMemoryDataStore;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
public class SearchService {
    private final InMemoryDataStore store;
    public SearchService(InMemoryDataStore store){this.store=store;}
    public List<Room> search(String city,LocalDate in,LocalDate out,int guests){
        validateDates(in,out); if(guests<1)throw new IllegalArgumentException("Guest count must be at least 1");
        return store.hotels.values().stream().filter(h->h.getCity().equalsIgnoreCase(city))
            .flatMap(h->h.getRooms().stream()).filter(r->r.getCapacity()>=guests)
            .filter(r->available(r,in,out)).sorted(Comparator.comparing(Room::getNightlyPrice)).collect(Collectors.toList());
    }
    public boolean available(Room room,LocalDate in,LocalDate out){
        validateDates(in,out);
        boolean booked=store.bookings.values().stream().filter(b->b.getRooms().stream().anyMatch(r->r.getId().equals(room.getId())))
            .filter(b->b.getStatus()==Booking.Status.CONFIRMED||b.getStatus()==Booking.Status.CHECKED_IN||(!b.pendingHoldExpired()&&b.getStatus()==Booking.Status.PENDING_PAYMENT))
            .anyMatch(b->b.overlaps(in,out));
        boolean maintenance=store.maintenanceBlocks.stream().filter(m->m.getRoomId().equals(room.getId())).anyMatch(m->m.overlaps(in,out));
        return !booked&&!maintenance;
    }
    public void validateDates(LocalDate in,LocalDate out){
        if(in==null||out==null||!out.isAfter(in))throw new IllegalArgumentException("Check-out must be after check-in");
        if(in.isBefore(LocalDate.now()))throw new IllegalArgumentException("Check-in cannot be in the past");
        if(Duration.between(in.atStartOfDay(),out.atStartOfDay()).toDays()>15)throw new IllegalArgumentException("Maximum stay is 15 nights");
    }
}
