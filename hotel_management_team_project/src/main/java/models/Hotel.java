package models;
import java.util.*;
public class Hotel {
    private final String id; private String name, city, address; private int starRating;
    private final List<Room> rooms = new ArrayList<>();
    public Hotel(String id,String name,String city,String address,int starRating){
        if(starRating<1||starRating>5) throw new IllegalArgumentException("Star rating must be 1–5");
        this.id=id; this.name=name; this.city=city; this.address=address; this.starRating=starRating;
    }
    public String getId(){return id;} public String getName(){return name;} public String getCity(){return city;}
    public String getAddress(){return address;} public int getStarRating(){return starRating;}
    public List<Room> getRooms(){return Collections.unmodifiableList(rooms);}
    public void addRoom(Room room){
        if(rooms.stream().anyMatch(r->r.getRoomNumber().equalsIgnoreCase(room.getRoomNumber())))
            throw new IllegalArgumentException("Room number must be unique within a hotel");
        rooms.add(room);
    }
    public void removeRoom(Room room){rooms.remove(room);}
    @Override public String toString(){return id+" | "+name+" | "+city+" | "+starRating+" star";}
}
