package models;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public class Booking {
    public enum Status { PENDING_PAYMENT, CONFIRMED, CHECKED_IN, CHECKED_OUT, CANCELLED, NO_SHOW }
    private final String id; private final Guest guest; private final String hotelId;
    private List<Room> rooms; private LocalDate checkIn,checkOut; private Status status;
    private final LocalDateTime createdAt; private BigDecimal totalAmount; private boolean idVerified;
    public Booking(String id,Guest guest,String hotelId,List<Room> rooms,LocalDate checkIn,LocalDate checkOut,BigDecimal totalAmount){
        this.id=id; this.guest=guest; this.hotelId=hotelId; this.rooms=new ArrayList<>(rooms);
        this.checkIn=checkIn; this.checkOut=checkOut; this.totalAmount=totalAmount;
        this.status=Status.PENDING_PAYMENT; this.createdAt=LocalDateTime.now();
    }
    public String getId(){return id;} public Guest getGuest(){return guest;} public String getHotelId(){return hotelId;}
    public List<Room> getRooms(){return Collections.unmodifiableList(rooms);}
    public LocalDate getCheckIn(){return checkIn;} public LocalDate getCheckOut(){return checkOut;}
    public Status getStatus(){return status;} public void setStatus(Status s){status=s;}
    public LocalDateTime getCreatedAt(){return createdAt;} public BigDecimal getTotalAmount(){return totalAmount;}
    public void setDates(LocalDate in,LocalDate out){checkIn=in;checkOut=out;}
    public void setTotalAmount(BigDecimal amount){totalAmount=amount;}
    public boolean isIdVerified(){return idVerified;} public void setIdVerified(boolean v){idVerified=v;}
    public boolean pendingHoldExpired(){return status==Status.PENDING_PAYMENT && createdAt.plusMinutes(15).isBefore(LocalDateTime.now());}
    public boolean overlaps(LocalDate in,LocalDate out){return in.isBefore(checkOut)&&checkIn.isBefore(out);}
}
