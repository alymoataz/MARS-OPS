public class StorageFacility extends Facility {
    private double water;
    private double food;
    private double fuel;


    public StorageFacility(double oxygenCapacity, String name, String location, int capacity, boolean operational, double maxOxygen, double water, double food, double fuel) {
        super(oxygenCapacity, name, location, capacity, operational, maxOxygen);
        this.water = water;
        this.food = food;
        this.fuel = fuel;
    }



    public double getFood() {
        return food;
    }

    public double getFuel() {
        return fuel;
    }

    public double getWater() {
        return water;
    }

    public void setFood(double food) {
        this.food = food;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public void setWater(double water) {
        this.water = water;
    }
}
