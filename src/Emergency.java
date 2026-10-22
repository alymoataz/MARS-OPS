public class Emergency {

    public enum EmergencyType {
        OxygenLeak,
        Fire,
        RoverBreakdown,
        MedicalEmergency
    }

    public enum EmergencyStatus {
        Reported,
        InProgress,
        Resolved,
        Unresolved
    }

    private String EmergencyID;
    private String location;
    private EmergencyStatus status;
    private EmergencyType type;
    private int severity;


    public Emergency(String EmergencyID, String location,EmergencyStatus status, EmergencyType type, int severity){

        //validations
        if (EmergencyID==null || EmergencyID.isBlank()){
            throw new IllegalArgumentException("ID invalid");
        }

        if (location==null || location.isBlank()) {
            throw new IllegalArgumentException("location invalid");
        }

        if (severity<1 || severity >1){
                throw new IllegalArgumentException("Severity invalid");
            }

        if (type==null){
           throw new IllegalArgumentException("type invalid");


        }

        this.EmergencyID=EmergencyID;
        this.location=location;
        this.status=status;
        this.type=type;
        this.severity=severity;
    }


    public String getEmergencyID() {
        return EmergencyID;
    }

    public int getSeverity() {
        return severity;
    }

    public EmergencyStatus getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public EmergencyType getType() {
        return type;
    }



    public boolean setEmergencyStatus(EmergencyStatus status) {
        if (status==null) {
            System.out.println("invalid status");
            return false;
        }
           this.status=status;
            return true; }
    }
    
}
