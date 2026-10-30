import java.util.List;
import java.util.Set;
public class Exploration extends Mission {

    public Exploration(int duration, List<Agent> assignedAgents){
        super(duration, Set.of(CrewMember.Skill.GEOLOGY), assignedAgents);//add to readme that exploration requires geology
    }

    @Override
    public boolean validateRequirements() {
        for (Agent agent : getAssignedAgents()) {
            if (agent instanceof ExplorationRover) {//rover is part of assignedAgents
                ExplorationRover rover = (ExplorationRover) agent;
                if (rover.canExplore()) {
                    return true;
                }
            }
        }

        return false;
    }
}
