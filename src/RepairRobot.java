public class RepairRobot extends Robot{
    public RepairRobot(String name,String id){
        super(name,id);
    }
    @Override
    public boolean canPerform(Mission mission) {
        return mission instanceof Repair && isAvailable();
    }

    @Override
    public boolean canHandleEmergency(Emergency e){
        return(e.getEmergencyType()== Emergency.EmergencyType.OxygenLeak||e.getEmergencyType()== Emergency.EmergencyType.RoverBreakdown);

    }
}
