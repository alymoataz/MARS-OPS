import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
public abstract class Mission {
    public enum MissionStatus {
        CREATED,//add to README (inital status for mission before it is accepted)
        ASSIGNED, IN_PROGRESS,
        COMPLETED, FAILED
    }
    private MissionStatus status;
    private int duration;
    private int elapsedTime;
    private Set<CrewMember.Skill> requiredSkills;
    private ArrayList<Agent> assignedAgents;//the team intended for this mission
    private String failureReason;
    private boolean agentsAssigned;
    public Mission(int duration, Set<CrewMember.Skill> requiredSkills, List<Agent> assignedAgents){// check in Colony that assignedAgents is not an empty list
        if (duration <= 0 || requiredSkills == null || assignedAgents == null){
            throw new IllegalArgumentException("Mission constructor argument(s) wrong.");
        }
        if (assignedAgents.contains(null)) {
            throw new IllegalArgumentException("Assigned agents cannot contain null.");
        }
        if (new HashSet<>(assignedAgents).size() != assignedAgents.size()) {
            throw new IllegalArgumentException("Duplicate agents are not allowed.");
        }
        this.status = MissionStatus.CREATED;
        this.duration = duration;
        this.elapsedTime = 0;
        this.assignedAgents = new ArrayList<>(assignedAgents);
        this.requiredSkills = new HashSet<>(requiredSkills);
        this.agentsAssigned = false;
    }
    public MissionStatus getStatus(){
        return status;
    }
    public int getDuration(){
        return duration;
    }
    public int getElapsedTime(){
        return elapsedTime;
    }
    public Set<CrewMember.Skill> getRequiredSkills(){
        return Collections.unmodifiableSet(requiredSkills);
    }
    public List<Agent> getAssignedAgents(){
        return List.copyOf(assignedAgents);
    }

    public String getFailureReason(){
        return failureReason;
    }
    public boolean advanceTime(int timeUnits) {
        if (status != MissionStatus.IN_PROGRESS) {
            return false;
        }
        if (timeUnits <= 0) {
            return false;
        }
        elapsedTime += timeUnits;
        if (elapsedTime >= duration) {
            elapsedTime = duration; //prevent elapsed time from exceeding duration
            complete();
        }
        return true;
    }
    public boolean complete(){
        if (status == MissionStatus.IN_PROGRESS){
            status = MissionStatus.COMPLETED;
            releaseAssignedAgents();
            return true;
        }
        return false;
    }
    public boolean fail(String reason){
        if (reason == null || reason.isBlank()){
            return false;
        }
        if (status != MissionStatus.FAILED && status != MissionStatus.COMPLETED){
            failureReason = reason;
            if (status == MissionStatus.IN_PROGRESS || status == MissionStatus.ASSIGNED){
                releaseAssignedAgents();
            }
            status = MissionStatus.FAILED;
            
            return true;
        }
        return false;
    }
public boolean assignAgents() {
    if (status != MissionStatus.CREATED) {
        return false;
    }
    int assignedCount = 0;
    for (int i = 0; i < assignedAgents.size(); i++) {
        if (!assignedAgents.get(i).assign()) {
            for (int j = 0; j < assignedCount; j++) {// release only previously assigned agents
                assignedAgents.get(j).release();
            }
            reject("One or more assigned agents were unavailable.");
            return false;
        }
        assignedCount++;
    }
    agentsAssigned = true;
    return true;
}
    private void releaseAssignedAgents(){
        for (int i = 0; i < assignedAgents.size(); i++){//releasing agents
            assignedAgents.get(i).release();
        }
    }
    public boolean accept(){//make sure assignAgents() is called before accept()
        if (status == MissionStatus.CREATED && agentsAssigned){
            status = MissionStatus.ASSIGNED;
            return true;
        }
        return false;
    }
    public boolean reject(String reason){
        if (status == MissionStatus.CREATED){
            if(fail(reason)){
                return true;
            }
        }
        return false;
    }
    public boolean reject() {
        return reject("Mission Rejected.");
    }
    public boolean start(){
        if(status == MissionStatus.ASSIGNED){
            status = MissionStatus.IN_PROGRESS;
            return true;
        }
        return false;
    }
    public abstract boolean validateRequirements();
    
}