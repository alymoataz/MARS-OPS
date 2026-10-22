public class Drone extends Robot {
    public Drone(String name,String id){
        super(name,id);
    }
    @Override
    public boolean canPerform(Mission mission){ //WILL BE DONE LATER
        return false;
    }

    @Override
    public boolean canHandleEmergency(Emergency e){ //WILL BE DONE LATER
        return false;
    }
}
