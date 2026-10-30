import java.util.ArrayList;
import java.util.List;

public class Facility {
    private String name;
    private String location;
    private int capacity;
    private boolean operational;
    private ResourceInventory inventory;
    private ArrayList<Equipment> equipment;

    public Facility(String name, String location, int capacity, ResourceInventory inventory) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.operational = true;
        this.inventory = inventory;
        this.equipment = new ArrayList<>();
    }


    public int getCapacity() {
        return capacity;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }


    public boolean setCapacity(int capacity) {
        if(capacity<0){
            System.out.println("Invalid: negative capacity.");
            return false;}
        this.capacity = capacity;
        return true;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOperational() {
        return operational;
    }

    public void shutdown() {
        this.operational = false;
    }

    public void restoreOperation() {
        this.operational = true;
    }

    public ResourceInventory getInventory() {
        return inventory;
    }

    public boolean hasMedicalSupplies (){
        if (inventory.getMedicalSupplies()<1) return false;
        return true;

    }

    public void consumeMedicalSupplies(){
        inventory.removeMedicalSupplies(1);
    }

    public void addEquipment(Equipment e) {
        equipment.add(e);
    }

    public List<Equipment> getEquipment() {
        return List.copyOf(equipment);
    }
}
