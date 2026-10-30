public class Laboratory extends Facility {
    private int equipmentCount;
    private boolean experimentInProgress;
    private boolean contaminationDetected;

    public Laboratory(String name, String location, int capacity, ResourceInventory inventory, int equipmentCount) {
        super(name, location, capacity, inventory);
        this.equipmentCount = equipmentCount;
        this.experimentInProgress = false;
        this.contaminationDetected = false;
    }

    public int getEquipmentCount() {
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
    public boolean hasEnoughEquipment(int required) {
        return equipmentCount >= required;
    }
}
