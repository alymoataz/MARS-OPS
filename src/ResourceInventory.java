public class ResourceInventory {

    private double oxygen;
    private double water;
    private double food;
    private double fuel;
    private double batteryPower;
    private double medicalSupplies;
    private double spareParts;

    public ResourceInventory() {
        oxygen = 0;
        water = 0;
        food = 0;
        fuel = 0;
        batteryPower = 0;
        medicalSupplies = 0;
        spareParts = 0;
    }

    public double getOxygen() {
        return oxygen;
    }

    public void setOxygen(double amount) {
        if (amount >= 0) {
            oxygen = amount;
        }
    }

    public boolean removeOxygen(double amount) {
        if (amount <= 0 || amount > oxygen) {
            return false;
        }
        oxygen -= amount;
        return true;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double amount) {
        if (amount >= 0) {
            water = amount;
        }
    }

    public boolean removeWater(double amount) {
        if (amount <= 0 || amount > water) {
            return false;
        }
        water -= amount;
        return true;
    }

    public double getFood() {
        return food;
    }

    public void setFood(double amount) {
        if (amount >= 0) {
            food = amount;
        }
    }

    public boolean removeFood(double amount) {
        if (amount <= 0 || amount > food) {
            return false;
        }
        food -= amount;
        return true;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double amount) {
        if (amount >= 0) {
            fuel = amount;
        }
    }

    public boolean removeFuel(double amount) {
        if (amount <= 0 || amount > fuel) {
            return false;
        }
        fuel -= amount;
        return true;
    }

    public double getBatteryPower() {
        return batteryPower;
    }

    public void setBatteryPower(double amount) {
        if (amount >= 0) {
            batteryPower = amount;
        }
    }

    public boolean removeBatteryPower(double amount) {
        if (amount <= 0 || amount > batteryPower) {
            return false;
        }
        batteryPower -= amount;
        return true;
    }

    public double getMedicalSupplies() {
        return medicalSupplies;
    }

    public void setMedicalSupplies(double amount) {
        if (amount >= 0) {
            medicalSupplies = amount;
        }
    }

    public boolean removeMedicalSupplies(double amount) {
        if (amount <= 0 || amount > medicalSupplies) {
            return false;
        }
        medicalSupplies -= amount;
        return true;
    }

    public double getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(double amount) {
        if (amount >= 0) {
            spareParts = amount;
        }
    }

    public boolean removeSpareParts(double amount) {
        if (amount <= 0 || amount > spareParts) {
            return false;
        }
        spareParts -= amount;
        return true;
    }
}