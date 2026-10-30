package repository;
import models.*;
import java.util.*;
public class InMemoryDataStore {
    public final Map<String,Guest> guests=new LinkedHashMap<>();
    public final Map<String,Hotel> hotels=new LinkedHashMap<>();
    public final Map<String,Room> rooms=new LinkedHashMap<>();
    public final Map<String,Booking> bookings=new LinkedHashMap<>();
    public final List<MaintenanceBlock> maintenanceBlocks=new ArrayList<>();
    public final List<Payment> payments=new ArrayList<>();
    public final List<Review> reviews=new ArrayList<>();
    public InMemoryDataStore(){
        Guest g=new Guest("G1","Ananya Sharma","9876543210","ananya@example.com","Aadhaar","XXXX-XXXX-1234");
        guests.put(g.getId(),g);
        Hotel h=new Hotel("H1","Coimbatore Comfort Inn","Coimbatore","Avinashi Road, Coimbatore",3);
        Room r1=new SingleRoom("R1","H1","101",1,new java.math.BigDecimal("1800"));
        Room r2=new DoubleRoom("R2","H1","102",2,new java.math.BigDecimal("2800"));
        Room r3=new DeluxeRoom("R3","H1","201",3,new java.math.BigDecimal("5200"));
        h.addRoom(r1);h.addRoom(r2);h.addRoom(r3);hotels.put(h.getId(),h);
        rooms.put(r1.getId(),r1);rooms.put(r2.getId(),r2);rooms.put(r3.getId(),r3);
        Hotel h2=new Hotel("H2","Chennai City Stay","Chennai","T Nagar, Chennai",4);
        Room r4=new SuiteRoom("R4","H2","301",4,new java.math.BigDecimal("8200"));
        h2.addRoom(r4);hotels.put(h2.getId(),h2);rooms.put(r4.getId(),r4);
    }
}
