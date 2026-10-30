public class ExplorationRover extends Robot{
    private int equipmentCount;
    public ExplorationRover(String name,String id, int equipmentCount){
        if (equipmentCount < 0) {
            throw new IllegalArgumentException("Equipment count cannot be negative.");
        }
        super(name,id);
        this.equipmentCount = equipmentCount;
    }
    public int getEquipmentCount(){
        return equipmentCount;
    }
    @Override
    public boolean canPerform(Mission mission) {
        return mission instanceof Exploration && canExplore();
    }

    @Override
    public boolean canHandleEmergency(Emergency e){ //NO EMERGENCY NEEDS EXPLORATION ROVER

        return false;
    }
    public boolean hasEnoughEquipment(int required){
        return equipmentCount >= required;
    }
    public boolean canExplore() {
        return isUsable() && hasEnoughBattery(70) && hasEnoughEquipment(5);//exploration mission needs rover with atleast 70% battery and 5 equipment, add to readme
    }
}
