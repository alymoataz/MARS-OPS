import java.util.List;
import java.util.Set;
public class Exploration extends Mission {

    public Exploration(int duration, List<Agent> assignedAgents){
        super(duration, Set.of(CrewMember.Skill.GEOLOGY), assignedAgents);//add to readme that exploration requires geology
    }

    @Override
    public boolean validateRequirements() {
        boolean hasRover = false;
        boolean hasGeologist = false;

        for (Agent agent : getAssignedAgents()) {
            if (agent instanceof ExplorationRover && agent.canPerform(this)) {
                hasRover = true;
            }
            if (agent instanceof Scientist && agent.canPerform(this) && agent.isUsable()) {
                hasGeologist = true;
            }
        }

        return hasRover && hasGeologist;
    }
}
