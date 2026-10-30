package models;
public class Admin extends User {
    public Admin(String id,String name,String phone,String email){super(id,name,phone,email);}
    @Override public String getRole(){return "ADMIN";}
}
