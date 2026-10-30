
import java.util.List;
import java.util.Set;

public class Repair extends Mission {

    private Repairable target;
    private int sparePartsRequired;

    public Repair(int duration, List<Agent> assignedAgents, Repairable target, int sparePartsRequired) {

        super(duration, Set.of(), assignedAgents);

        if (target == null) {
            throw new IllegalArgumentException("Repair target cannot be null.");
        }

        if (sparePartsRequired <= 0) {
            throw new IllegalArgumentException("Spare parts required must be positive.");
        }

        this.target = target;
        this.sparePartsRequired = sparePartsRequired;
    }

    public Repairable getTarget() {
        return target;
    }

    public int getSparePartsRequired() {
        return sparePartsRequired;
    }

    @Override
    public boolean validateRequirements() {

        //target must actually be damaged
        if (target.isOperational()) {
            return false;
        }

        boolean hasEngineer = false;
        boolean hasRepairRobot = false;

        for (Agent agent : getAssignedAgents()) {
            if (agent instanceof Engineer && agent.canPerform(this) && agent.isUsable()) {
                hasEngineer = true;
            }

            if (agent instanceof RepairRobot && agent.canPerform(this)) {
                hasRepairRobot = true;
            }
        }
        return hasEngineer && hasRepairRobot;
    }
}
