public class Laboratory extends Facility {
    private String equipmentCount;
    private boolean experimentInProgress;
    private boolean contaminationDetected;

    public Laboratory(String name, String location, int capacity, boolean operational, String equipmentCount, boolean experimentInProgress,boolean contaminationDetected) {
        super(name, location, capacity, operational);
        this.equipmentCount = equipmentCount;
        this.experimentInProgress = false;
        this.contaminationDetected = false;
    }

    public String getEquipmentCount() {
        return equipmentCount;
    }

    public boolean isExperimentInProgress() {
        return experimentInProgress;
    }

    public boolean isContaminationDetected() {
        return contaminationDetected;
    }

    public void setExperimentInProgress(boolean experimentInProgress) {
        this.experimentInProgress = experimentInProgress;
    }

    public void startExperiment(){
        experimentInProgress=true;
    }

    public void finishExperiment(){
        experimentInProgress=false;
    }

    public void detectedContamination(){
        contaminationDetected=true;
    }

    public boolean repairFire() {
        return true;
    }
}
