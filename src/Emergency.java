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
    private Facility location;
    private EmergencyStatus status;
    private EmergencyType type;
    private int severity;


    public Emergency(String EmergencyID, Facility location, EmergencyType type, int severity){

        //validations
        if (EmergencyID==null || EmergencyID.isBlank()){
            throw new IllegalArgumentException("ID invalid");
        }

        if (location==null) {
            throw new IllegalArgumentException("location invalid");
        }

        if (severity<1 || severity >5){
                throw new IllegalArgumentException("Severity invalid");
            }

        if (type==null){
           throw new IllegalArgumentException("type invalid");


        }

        this.EmergencyID=EmergencyID;
        this.location=location;
        this.status=EmergencyStatus.Reported;
        this.type=type;
        this.severity=severity;
    }


    public String getEmergencyID() {
        return EmergencyID;
    }

    public int getSeverity() {
        return severity;
    }

    public EmergencyStatus getEmergencyStatus() {
        return status;
    }

    public Facility getLocation() {
        return location;
    }

    public EmergencyType getEmergencyType() {
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



