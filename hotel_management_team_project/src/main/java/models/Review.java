package models;
import java.time.LocalDate;
public class Review {
    private final String id,bookingId,hotelId,guestId,comment; private final int rating; private final LocalDate createdOn=LocalDate.now();
    public Review(String id,String bookingId,String hotelId,String guestId,int rating,String comment){
        if(rating<1||rating>5) throw new IllegalArgumentException("Rating must be 1–5");
        this.id=id;this.bookingId=bookingId;this.hotelId=hotelId;this.guestId=guestId;this.rating=rating;this.comment=comment;
    }
    public String getId(){return id;} public String getBookingId(){return bookingId;} public String getHotelId(){return hotelId;}
    public String getGuestId(){return guestId;} public int getRating(){return rating;} public String getComment(){return comment;}
    public LocalDate getCreatedOn(){return createdOn;}
}
