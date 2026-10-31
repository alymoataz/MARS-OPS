public class Habitat extends Facility{
    private final int crewCapacity;
    private int currentOccupants;
    private boolean lifeSupportActive;

    public Habitat(String name, String location, int capacity,
                ResourceInventory inventory, int currentOccupants,
                boolean lifeSupportActive, int crewCapacity) {

        super(name, location, capacity, inventory);

        this.currentOccupants = currentOccupants;
        this.lifeSupportActive = lifeSupportActive;
        this.crewCapacity = crewCapacity;
    }

    public int getCrewCapacity() {
        return crewCapacity;
    }

    public int getCurrentOccupants() {
        return currentOccupants;
    }

    public void setCurrentOccupants(int currentOccupants) {
        this.currentOccupants = currentOccupants;
    }

    public void setLifeSupportActive(boolean lifeSupportActive) {
        this.lifeSupportActive = lifeSupportActive;
    }
    public boolean isLifeSupportActive() {
        return lifeSupportActive;
    }

    public void reduceOxygen(double amount) {
        if (amount > 0 ) {
            getInventory().setOxygen(amount);
        }
    }

    public boolean hasAvailableSpace(){
        return currentOccupants<crewCapacity;
    }
    public boolean addOccupant(){
        if(!isOperational() || !hasAvailableSpace()){
            System.out.println("Cannot add occupant.");
            return false;
        }
        currentOccupants++;
        return true;
    }

    public boolean removeOccupant(){
        if(currentOccupants<=0){
            System.out.println("No occupants.");
            return false;
        }
        currentOccupants--;
        return true;
    }

    public void activateLifeSupport(){
        lifeSupportActive=true;
    }


    public boolean repairOxygenleak() {
        return true;
    }
}
