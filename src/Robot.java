public abstract class Robot extends Agent {
    public enum DamageStatus{
        OPERATIONAL,
        DAMAGED,
        DESTROYED
    }
    private static final int MAX_BATTERY=100;
    private DamageStatus damageStatus;
    private int batteryLevel;
    public Robot(String name,String id){
        super(name,id);
        damageStatus=DamageStatus.OPERATIONAL;
        batteryLevel=MAX_BATTERY;
    }
    public DamageStatus getDamageStatus() { return damageStatus; }

    public void setDamageStatus(DamageStatus d) {
        if (d == null) throw new IllegalArgumentException("Damage status cannot be null");
        damageStatus = d;
    }
    public int getBatteryLevel() {
        return batteryLevel;
    }
    public boolean drainBattery(int amount){
        if(amount<0){
            System.out.println("invalid amount");
            return false;
        }
        if(batteryLevel-amount<0) {
            batteryLevel = 0;
            return true;
        }
        batteryLevel-=amount;
        return true;
    }
    public boolean recharge(int amount){
        if(amount<0){
            System.out.println("amount cant be less than 0");
            return false;
        }
        if(amount+batteryLevel>MAX_BATTERY){
            batteryLevel=MAX_BATTERY;
            return true;
        }
        batteryLevel+=amount;
        return true;
    }

    public boolean hasEnoughBattery(int required){
        if(required<0){
            return false;
        }
        return batteryLevel>=required;
    }
    @Override
    public boolean isUsable(){
        return damageStatus==DamageStatus.OPERATIONAL && batteryLevel>0;
    }


}
