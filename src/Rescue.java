import java.util.List;
import java.util.Set;

public class Rescue extends Mission {

    private ExplorationRover rover;
    private CommunicationStation communicationStation;

    public Rescue(int duration, List<Agent> assignedAgents, ExplorationRover rover, CommunicationStation communicationStation) {

        super(duration, Set.of(), assignedAgents);

        if (rover == null) {
            throw new IllegalArgumentException("Rover cannot be null.");
        }
        if (communicationStation == null) {
            throw new IllegalArgumentException("Communication station cannot be null.");
        }
        if (!assignedAgents.contains(rover)) {
            throw new IllegalArgumentException("Rover must be included in assigned agents.");
        }
        this.rover = rover;
        this.communicationStation = communicationStation;
    }

    public ExplorationRover getRover() {
        return rover;
    }

    public CommunicationStation getCommunicationStation() {
        return communicationStation;
    }
    @Override
    public boolean validateRequirements() {
        if (!rover.isAvailable() || !rover.canRescue()) {//check the rover
            return false;
        }
        if (!communicationStation.isOperational() || !communicationStation.isConnected()) {//check the communication station
            return false;
        }
        
        for (Agent agent : getAssignedAgents()) {//check for at least one suitable crew member
            if (agent instanceof CrewMember && agent.isAvailable() && agent.isUsable()) {
                return true;
            }
        }

        return false;
    }
}