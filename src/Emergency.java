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
    private String failureReason;
    private String emergencyID;
    private Facility location;
    private ExplorationRover affectedRover;
    private EmergencyStatus status;
    private EmergencyType type;
    private int severity;

    // Constructor for emergencies occurring at a facility
    public Emergency(String emergencyID, Facility location,
                     EmergencyType type, int severity) {

        if (emergencyID == null || emergencyID.isBlank()) {
            throw new IllegalArgumentException("ID invalid.");
        }

        if (location == null) {
            throw new IllegalArgumentException("Location invalid.");
        }

        if (severity < 1 || severity > 5) {
            throw new IllegalArgumentException("Severity invalid.");
        }

        if (type == null) {
            throw new IllegalArgumentException("Type invalid.");
        }

        this.emergencyID = emergencyID;
        this.location = location;
        this.affectedRover = null;
        this.status = EmergencyStatus.Reported;
        this.type = type;
        this.severity = severity;
    }

    // Constructor for rover breakdowns on the Martian surface
    public Emergency(String emergencyID, ExplorationRover rover,
                     int severity) {

        if (emergencyID == null || emergencyID.isBlank()) {
            throw new IllegalArgumentException("ID invalid.");
        }

        if (rover == null) {
            throw new IllegalArgumentException("Rover invalid.");
        }

        if (severity < 1 || severity > 5) {
            throw new IllegalArgumentException("Severity invalid.");
        }

        this.emergencyID = emergencyID;
        this.location = null;
        this.affectedRover = rover;
        this.status = EmergencyStatus.Reported;
        this.type = EmergencyType.RoverBreakdown;
        this.severity = severity;
    }

    public String getEmergencyID() {
        return emergencyID;
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

    public ExplorationRover getAffectedRover() {
        return affectedRover;
    }

    public EmergencyType getEmergencyType() {
        return type;
    }

    public boolean setEmergencyStatus(EmergencyStatus status) {
        if (status == null) {
            return false;
        }

        this.status = status;
        return true;
    }
    public String getFailureReason() {
        return failureReason;
    }

    public boolean markUnresolved(String reason) {
        if (reason == null || reason.isBlank()) {
            return false;
        }
        this.failureReason = reason;
        this.status = EmergencyStatus.Unresolved;
        return true;
    }
}