package models;
import java.math.BigDecimal;
public class DoubleRoom extends Room {
    public DoubleRoom(String id,String hotelId,String roomNumber,int capacity,BigDecimal nightlyPrice) {
        super(id,hotelId,roomNumber,capacity,nightlyPrice);
    }
    @Override public String getType(){return "Double";}
}
