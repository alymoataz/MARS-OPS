import java.util.List;
import java.util.Set;

public class Research extends Mission {

    private CrewMember.Skill researchSkill;
    private Laboratory laboratory;
    private int equipmentRequired;

    public Research(int duration, List<Agent> assignedAgents, CrewMember.Skill researchSkill, Laboratory laboratory, int equipmentRequired) {

        super(duration, getRequiredSkill(researchSkill), assignedAgents);

        if (laboratory == null) {
            throw new IllegalArgumentException("Laboratory cannot be null.");
        }
        if (equipmentRequired < 0){
            throw new IllegalArgumentException("Equipment required cannot be negative.");
        }

        this.researchSkill = researchSkill;
        this.laboratory = laboratory;
        this.equipmentRequired = equipmentRequired;
    }

    private static Set<CrewMember.Skill> getRequiredSkill(CrewMember.Skill skill) {
        if (skill == null) {
            throw new IllegalArgumentException("Research skill cannot be null.");
        }
        return Set.of(skill);
    }

    public CrewMember.Skill getResearchSkill() {
        return researchSkill;
    }

    public Laboratory getLaboratory() {
        return laboratory;
    }

    public int getEquipmentRequired() {
        return equipmentRequired;
    }

    @Override
    public boolean validateRequirements() {
        if (!laboratory.isOperational()) {
            return false;
        }
        if (laboratory.isContaminationDetected()) {
            return false;
        }
        if (laboratory.isExperimentInProgress()) {
            return false;
        }
        if (!laboratory.hasEnoughEquipment(equipmentRequired)) {
            return false;
        }

        for (Agent agent : getAssignedAgents()) {
            if (agent instanceof Scientist && agent.canPerform(this) && agent.isUsable()) {
                return true;
            }
        }

        return false;
    }
}
