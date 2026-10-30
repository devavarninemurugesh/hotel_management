package payment;
import java.math.BigDecimal;
public class PayAtHotelPayment implements PaymentMethod {
    @Override public String getName(){return "PAY_AT_HOTEL";}
    @Override public boolean pay(BigDecimal amount){return amount!=null && amount.signum()>=0;}
}
