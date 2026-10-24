public class MedicalOfficer extends CrewMember{
    public MedicalOfficer(String name,String id){
        super(name, id);
        addSkill(Skill.FIRST_AID);
        addSkill(Skill.SURGERY);
    }

    @Override
    public boolean canHandleEmergency(Emergency e){ //WAIT ALSO
        return (e.getEmergencyType()== Emergency.EmergencyType.MedicalEmergency||e.getEmergencyType()== Emergency.EmergencyType.Fire);

    }
}
