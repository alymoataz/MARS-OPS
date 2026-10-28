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
    private ArrayList<Agent> assignedAgents;
    private String failureReason;
    public Mission(int duration, Set<CrewMember.Skill> requiredSkills, List<Agent> assignedAgents){// check in Colony that assignedAgents is not an empty list
        if (duration <= 0 || requiredSkills == null || assignedAgents == null){
            throw new IllegalArgumentException("Mission constructor argument(s) wrong.");
        }
        this.status = MissionStatus.CREATED;
        this.duration = duration;
        this.elapsedTime = 0;
        this.assignedAgents = new ArrayList<>(assignedAgents);
        this.requiredSkills = new HashSet<>(requiredSkills);
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
    //make assignAgent method
    //
    public String getFailureReason(){
        return failureReason;
    }
    public boolean advanceTime(){
        if (status == MissionStatus.IN_PROGRESS){
            elapsedTime++;
            if (elapsedTime ==  duration){
                this.complete();
            }
            return true;
        }
        return false;
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
            if (status == MissionStatus.IN_PROGRESS){
                releaseAssignedAgents();
            }
            status = MissionStatus.FAILED;
            
            return true;
        }
        return false;
    }
    private void releaseAssignedAgents(){
        for (int i = 0; i < assignedAgents.size(); i++){//releasing agents
            assignedAgents.get(i).release();
        }
    }
    public boolean accept(){
        if (status == MissionStatus.CREATED){
            status = MissionStatus.ASSIGNED;
            start();
            return true;
        }
        return false;
    }
    public boolean reject(){
        if (status == MissionStatus.CREATED){
            if(fail("Mission Rejected.")){
                return true;
            }
        }
        return false;
    }
    private void start(){
        if(status == MissionStatus.ASSIGNED){
            status = MissionStatus.IN_PROGRESS;
        }
    }
    
}