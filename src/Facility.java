public class Facility {
    private String name;
    private String location;
    private int capacity;
    private boolean operational;
    private double oxygenCapacity;  //amount of stored oxygen
    private double maxOxygen;  //maximum amount of oxygen facility can store


    public Facility(double oxygenCapacity, String name, String location, int capacity, boolean operational, double maxOxygen) {
        this.oxygenCapacity = oxygenCapacity;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.operational = operational;
        this.maxOxygen = maxOxygen;
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

    public double getOxygen() {
        return maxOxygen;
    }

    public double getOxygenCapacity() {
        return oxygenCapacity;
    }

    public boolean setMaxOxygen(double maxOxygen) {
        if(maxOxygen<0){
            System.out.println("Invalid: negative oxygen.");
            return false;}
        this.maxOxygen = maxOxygen;
        return true;
    }

    public boolean setOxygenCapacity(double oxygenCapacity) {
        if(oxygenCapacity<0){
            System.out.println("Invalid: negative oxygen capacity.");
            return false; }
        this.oxygenCapacity = oxygenCapacity;
        return true;
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

    public void setOperational(boolean operational) {
        this.operational = operational;
    }
}
