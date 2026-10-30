package services;
import models.*;
import repository.InMemoryDataStore;
import java.time.temporal.ChronoUnit;
import java.util.*;
public class ReviewService {
    private final InMemoryDataStore store;
    public ReviewService(InMemoryDataStore store){this.store=store;}
    public Review add(String bookingId,int rating,String comment){
        Booking b=store.bookings.get(bookingId);if(b==null)throw new IllegalArgumentException("Booking not found");
        if(b.getStatus()!=Booking.Status.CHECKED_OUT)throw new IllegalStateException("Review allowed only after checkout");
        if(ChronoUnit.DAYS.between(b.getCheckOut(),java.time.LocalDate.now())>30)throw new IllegalStateException("Review window has expired");
        if(store.reviews.stream().anyMatch(r->r.getBookingId().equals(bookingId)))throw new IllegalStateException("One review per booking");
        Review r=new Review("RV"+(store.reviews.size()+1),bookingId,b.getHotelId(),b.getGuest().getId(),rating,comment);store.reviews.add(r);return r;
    }
    public double averageRating(String hotelId){
        return store.reviews.stream().filter(r->r.getHotelId().equals(hotelId)).mapToInt(Review::getRating).average().orElse(0.0);
    }
    public boolean shouldFlagForAdmin(String hotelId){
        List<Review> rs=store.reviews.stream().filter(r->r.getHotelId().equals(hotelId)).toList();
        return rs.size()>=10&&rs.stream().mapToInt(Review::getRating).average().orElse(0)<2.5;
    }
}
