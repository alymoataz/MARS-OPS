public class Habitat extends Facility{

    public Habitat(double oxygenCapacity, String name, String location, int capacity, boolean operational, double maxOxygen) {
        super(oxygenCapacity, name, location, capacity, operational,maxOxygen);
    }

    @Override
    public boolean setOxygenCapacity(double oxygenCapacity) {
        if(oxygenCapacity>maxOxygen){
            System.out.println("Amount exceeds maximum capacity");
            return false;
        }
        return super.setOxygenCapacity(oxygenCapacity);
    }

    public void reduceOxygen(double amount){
        if(amount>0 && amount<=oxygenCapacity)
            oxygenCapacity-=amount;
    }


}
