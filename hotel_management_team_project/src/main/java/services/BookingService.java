package services;
import models.*;
import repository.InMemoryDataStore;
import java.time.*;
import java.util.*;
public class BookingService {
    private final InMemoryDataStore store; private final SearchService search; private final PricingService pricing;
    public BookingService(InMemoryDataStore store,SearchService search,PricingService pricing){this.store=store;this.search=search;this.pricing=pricing;}
    public Booking create(String guestId,List<String> roomIds,LocalDate in,LocalDate out){
        search.validateDates(in,out);
        Guest guest=store.guests.get(guestId);if(guest==null)throw new IllegalArgumentException("Guest not found");
        if(roomIds==null||roomIds.isEmpty()||roomIds.size()>3)throw new IllegalArgumentException("Choose 1–3 rooms");
        List<Room> rooms=new ArrayList<>();
        for(String id:roomIds){Room r=store.rooms.get(id);if(r==null)throw new IllegalArgumentException("Room not found: "+id);
            if(!search.available(r,in,out))throw new IllegalStateException("Room unavailable: "+id);rooms.add(r);}
        String hotel=rooms.get(0).getHotelId();
        if(rooms.stream().anyMatch(r->!r.getHotelId().equals(hotel)))throw new IllegalArgumentException("All rooms must be in the same hotel");
        Booking b=new Booking("B"+(store.bookings.size()+1),guest,hotel,rooms,in,out,pricing.calculate(rooms,in,out));
        store.bookings.put(b.getId(),b);return b;
    }
    public void checkIn(String bookingId,boolean idVerified){
        Booking b=requireBooking(bookingId);if(b.getStatus()!=Booking.Status.CONFIRMED)throw new IllegalStateException("Booking must be confirmed");
        if(!LocalDate.now().equals(b.getCheckIn()))throw new IllegalStateException("Check-in is allowed only on check-in date");
        if(!idVerified)throw new IllegalArgumentException("Guest ID must be verified");
        b.setIdVerified(true);b.setStatus(Booking.Status.CHECKED_IN);
    }
    public void checkOut(String bookingId){
        Booking b=requireBooking(bookingId);if(b.getStatus()!=Booking.Status.CHECKED_IN)throw new IllegalStateException("Guest is not checked in");
        b.setStatus(Booking.Status.CHECKED_OUT);
    }
    public void changeDates(String bookingId,LocalDate in,LocalDate out){
        Booking b=requireBooking(bookingId);
        if(b.getStatus()!=Booking.Status.CONFIRMED)throw new IllegalStateException("Only confirmed bookings can change dates");
        if(Duration.between(LocalDateTime.now(),b.getCheckIn().atStartOfDay()).toHours()<=48)throw new IllegalStateException("Date changes require more than 48 hours' notice");
        search.validateDates(in,out);
        for(Room r:b.getRooms())if(!search.available(r,in,out))throw new IllegalStateException("Room unavailable for new dates");
        b.setDates(in,out);b.setTotalAmount(pricing.calculate(b.getRooms(),in,out));
    }
    public void cancel(String bookingId,PaymentService paymentService){
        Booking b=requireBooking(bookingId);if(b.getStatus()==Booking.Status.CHECKED_IN||b.getStatus()==Booking.Status.CHECKED_OUT)throw new IllegalStateException("Cannot cancel after check-in");
        paymentService.refund(b);b.setStatus(Booking.Status.CANCELLED);
    }
    public void markNoShows(){
        LocalDateTime cutoff=LocalDate.now().atTime(12,0);
        for(Booking b:store.bookings.values())if(b.getStatus()==Booking.Status.CONFIRMED&&LocalDateTime.now().isAfter(b.getCheckIn().plusDays(1).atTime(12,0)))b.setStatus(Booking.Status.NO_SHOW);
    }
    private Booking requireBooking(String id){Booking b=store.bookings.get(id);if(b==null)throw new IllegalArgumentException("Booking not found");return b;}
}
