package services;
import models.Room;
import java.math.*;
import java.time.*;
import java.util.*;
public class PricingService {
    public BigDecimal calculate(List<Room> rooms,LocalDate checkIn,LocalDate checkOut){
        BigDecimal base=BigDecimal.ZERO;
        for(Room room:rooms){
            for(LocalDate d=checkIn;d.isBefore(checkOut);d=d.plusDays(1)){
                BigDecimal night=room.getNightlyPrice();
                if(d.getDayOfWeek()==DayOfWeek.FRIDAY||d.getDayOfWeek()==DayOfWeek.SATURDAY)
                    night=night.multiply(new BigDecimal("1.20"));
                base=base.add(night);
            }
        }
        BigDecimal gst=base.compareTo(new BigDecimal("7500"))<0?new BigDecimal("0.12"):new BigDecimal("0.18");
        return base.add(base.multiply(gst)).setScale(2,RoundingMode.HALF_UP);
    }
}
