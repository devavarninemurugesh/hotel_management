package models;
public class HotelManager extends User {
    public HotelManager(String id,String name,String phone,String email){super(id,name,phone,email);}
    @Override public String getRole(){return "HOTEL_MANAGER";}
}
