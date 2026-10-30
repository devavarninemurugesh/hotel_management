package models;
import java.time.LocalDate;
public class MaintenanceBlock {
    private final String roomId; private final LocalDate startDate,endDate; private final String reason;
    public MaintenanceBlock(String roomId,LocalDate startDate,LocalDate endDate,String reason){
        if(!endDate.isAfter(startDate)) throw new IllegalArgumentException("Maintenance end must be after start");
        this.roomId=roomId; this.startDate=startDate; this.endDate=endDate; this.reason=reason;
    }
    public String getRoomId(){return roomId;} public LocalDate getStartDate(){return startDate;}
    public LocalDate getEndDate(){return endDate;} public String getReason(){return reason;}
    public boolean overlaps(LocalDate start,LocalDate end){return start.isBefore(endDate)&&startDate.isBefore(end);}
}
