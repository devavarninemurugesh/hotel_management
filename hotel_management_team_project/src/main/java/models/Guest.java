package models;
public class Guest extends User {
    private final String idProofType;
    private final String idProofNumber;
    public Guest(String id, String name, String phone, String email, String idProofType, String idProofNumber) {
        super(id,name,phone,email); this.idProofType=idProofType; this.idProofNumber=idProofNumber;
    }
    public String getIdProofType(){return idProofType;} public String getIdProofNumber(){return idProofNumber;}
    @Override public String getRole(){return "GUEST";}
}
