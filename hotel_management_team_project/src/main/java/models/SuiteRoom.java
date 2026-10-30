package models;
import java.math.BigDecimal;
public class SuiteRoom extends Room {
    public SuiteRoom(String id,String hotelId,String roomNumber,int capacity,BigDecimal nightlyPrice) {
        super(id,hotelId,roomNumber,capacity,nightlyPrice);
    }
    @Override public String getType(){return "Suite";}
}
