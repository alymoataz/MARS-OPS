public class Engineer extends CrewMember{
    public Engineer(String name,String id){
        super(name, id);
        addSkill(Skill.STRUCTURAL_REPAIR);
        addSkill(Skill.ELECTRICAL_REPAIR);
    }

    @Override
    public boolean canHandleEmergency(Emergency e){ //WAIT ALSO
        return false;// just for now
    }
}
