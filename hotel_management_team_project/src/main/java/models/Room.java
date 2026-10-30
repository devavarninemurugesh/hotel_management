package models;
import java.math.BigDecimal;
public abstract class Room {
    private final String id, hotelId, roomNumber;
    private final int capacity;
    private final BigDecimal nightlyPrice;
    protected Room(String id,String hotelId,String roomNumber,int capacity,BigDecimal nightlyPrice){
        if(capacity<1||nightlyPrice.signum()<0) throw new IllegalArgumentException("Invalid capacity/price");
        this.id=id; this.hotelId=hotelId; this.roomNumber=roomNumber; this.capacity=capacity; this.nightlyPrice=nightlyPrice;
    }
    public String getId(){return id;} public String getHotelId(){return hotelId;}
    public String getRoomNumber(){return roomNumber;} public int getCapacity(){return capacity;}
    public BigDecimal getNightlyPrice(){return nightlyPrice;}
    public abstract String getType();
    @Override public String toString(){return getType()+" room "+roomNumber+" (capacity "+capacity+", ₹"+nightlyPrice+"/night)";}
}
