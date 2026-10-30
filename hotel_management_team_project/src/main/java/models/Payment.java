package models;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public class Payment {
    public enum Status { PAID, FAILED, REFUNDED, PENDING }
    private final String id,bookingId,method; private BigDecimal amount; private Status status;
    private final LocalDateTime createdAt=LocalDateTime.now();
    public Payment(String id,String bookingId,BigDecimal amount,String method,Status status){
        this.id=id;this.bookingId=bookingId;this.amount=amount;this.method=method;this.status=status;
    }
    public String getId(){return id;} public String getBookingId(){return bookingId;} public BigDecimal getAmount(){return amount;}
    public String getMethod(){return method;} public Status getStatus(){return status;} public void setStatus(Status s){status=s;}
    public LocalDateTime getCreatedAt(){return createdAt;}
}
