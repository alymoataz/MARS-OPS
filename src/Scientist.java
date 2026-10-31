public class Scientist extends CrewMember {
    public Scientist(String name,String id){
        super(name,id);
        addSkill(Skill.GEOLOGY);
        addSkill(Skill.BIOLOGY);
    }
    @Override
    public boolean canHandleEmergency(Emergency e){ //WAIT ALSO
        if(e!=null&& e.getEmergencyType()!= Emergency.EmergencyType.Fire)
            return false;
        return true;
    }

}
