package payment;
import java.math.BigDecimal;
public class UpiPayment implements PaymentMethod {
    @Override public String getName(){return "UPI";}
    @Override public boolean pay(BigDecimal amount){return amount!=null && amount.signum()>=0;}
}
