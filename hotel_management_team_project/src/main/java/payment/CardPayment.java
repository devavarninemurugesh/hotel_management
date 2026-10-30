package payment;
import java.math.BigDecimal;
public class CardPayment implements PaymentMethod {
    @Override public String getName(){return "CARD";}
    @Override public boolean pay(BigDecimal amount){return amount!=null && amount.signum()>=0;}
}
