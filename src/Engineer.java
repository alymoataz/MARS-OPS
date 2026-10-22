public class Engineer extends CrewMember{
    public Engineer(String name,String id){
        super(name, id);
        addSkill(Skill.STRUCTURAL_REPAIR);
        addSkill(Skill.ELECTRICAL_REPAIR);
    }
    @Override
    public boolean canPerform(Mission mission){ //WAIT FOR MISSION CLASS TO BE ABLE TO DO IT
        return isAvailable() && hasAllSkills(mission.getRequiredSkills());
    }
    @Override
    public boolean canHandleEmergency(Emergency e){ //WAIT ALSO
        return false;// just for now
    }
}
