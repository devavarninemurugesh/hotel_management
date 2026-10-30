package services;
import models.*;
import payment.*;
import repository.InMemoryDataStore;
import java.math.BigDecimal;
import java.time.LocalDate;
public class PaymentService {
    private final InMemoryDataStore store;
    public PaymentService(InMemoryDataStore store){this.store=store;}
    public Payment process(Booking booking,PaymentMethod method){
        if(method instanceof PayAtHotelPayment && java.time.temporal.ChronoUnit.DAYS.between(booking.getCheckIn(),booking.getCheckOut())>2)
            throw new IllegalArgumentException("Pay at Hotel is allowed only up to 2 nights");
        boolean success=method.pay(booking.getTotalAmount());
        Payment p=new Payment("P"+(store.payments.size()+1),booking.getId(),booking.getTotalAmount(),method.getName(),
            success?Payment.Status.PAID:Payment.Status.FAILED);
        store.payments.add(p);
        if(success)booking.setStatus(Booking.Status.CONFIRMED);
        else booking.setStatus(Booking.Status.CANCELLED);
        return p;
    }
    public BigDecimal refund(Booking booking){
        long hours=java.time.Duration.between(java.time.LocalDateTime.now(),booking.getCheckIn().atStartOfDay()).toHours();
        BigDecimal ratio=hours>48?BigDecimal.ONE:(hours>=24?new BigDecimal("0.50"):BigDecimal.ZERO);
        BigDecimal amount=booking.getTotalAmount().multiply(ratio);
        store.payments.stream().filter(p->p.getBookingId().equals(booking.getId())&&p.getStatus()==Payment.Status.PAID)
            .findFirst().ifPresent(p->p.setStatus(amount.signum()>0?Payment.Status.REFUNDED:Payment.Status.PAID));
        return amount;
    }
}
