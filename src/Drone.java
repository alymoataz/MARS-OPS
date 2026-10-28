public class Drone extends Robot {
    public Drone(String name,String id){
        super(name,id);
    }
    @Override
    public boolean canPerform(Mission mission){
        return isAvailable();
    }

    @Override
    public boolean canHandleEmergency(Emergency e){ //NO EMERGENCY NEEDS DRONE
        return false;
    }
}
