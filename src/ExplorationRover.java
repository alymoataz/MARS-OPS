public class ExplorationRover extends Robot{
    public ExplorationRover(String name,String id){
        super(name,id);
    }
    @Override
    public boolean canPerform(Mission mission){

        return isAvailable();
    }

    @Override
    public boolean canHandleEmergency(Emergency e){ //NO EMERGENCY NEEDS EXPLORATION ROVER

        return false;
    }
}
