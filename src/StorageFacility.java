public class StorageFacility extends Facility {

    private double storageCapacity;
    private double currentStorageUsed;

    public StorageFacility(String name, String location, int capacity,
                        ResourceInventory inventory, double storageCapacity) {

        super(name, location, capacity, inventory);

        this.storageCapacity = storageCapacity;
        this.currentStorageUsed = 0;
    }

    public double getStorageCapacity() {
        return storageCapacity;
    }

    public double getCurrentStorageUsed() {
        return currentStorageUsed;
    }

    public double getAvailableSpace() {
        return storageCapacity - currentStorageUsed;
    }

    public boolean hasAvailableSpace(double amount) {
        return amount > 0 && amount <= getAvailableSpace();
    }


    public boolean removeSupplies(double amount) {
        if (amount <= 0 || amount > currentStorageUsed) {
            return false;
        }

        currentStorageUsed -= amount;
        return true;
    }


    public boolean storeSupplies(double amount){
        if((amount+currentStorageUsed)>storageCapacity){
            System.out.println("Exceeds storage capacity");
            return false;
        }
        currentStorageUsed=+amount;
        return true;
    }
}
