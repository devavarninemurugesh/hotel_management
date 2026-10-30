package models;
import java.math.BigDecimal;
public class SingleRoom extends Room {
    public SingleRoom(String id,String hotelId,String roomNumber,int capacity,BigDecimal nightlyPrice) {
        super(id,hotelId,roomNumber,capacity,nightlyPrice);
    }
    @Override public String getType(){return "Single";}
}
