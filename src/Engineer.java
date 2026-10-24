public class Engineer extends CrewMember{
    public Engineer(String name,String id){
        super(name, id);
        addSkill(Skill.STRUCTURAL_REPAIR);
        addSkill(Skill.ELECTRICAL_REPAIR);
    }

    @Override
    public boolean canHandleEmergency(Emergency e){ //WAIT ALSO
        if(e.getEmergencyType()== Emergency.EmergencyType.OxygenLeak||e.getEmergencyType()== Emergency.EmergencyType.RoverBreakdown)
            return true;
        return false;
    }
}
