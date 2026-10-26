import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public abstract  class CrewMember extends Agent {
    public enum HealthStatus{
        HEALTHY,
        INJURED,
        CRITICAL
    }
    public enum Skill {
        // Scientist
        GEOLOGY, BIOLOGY,
        // Engineer
        STRUCTURAL_REPAIR, ELECTRICAL_REPAIR,
        // Medical Officer
        FIRST_AID, SURGERY
    }
    private HealthStatus healthStatus;
    private int fatigue;
    private static final int MAX_FATIGUE =100;
    private static final int FATIGUE_LIMIT=50;
    private final Set<Skill> skills = EnumSet.noneOf(Skill.class);
    public CrewMember(String name,String id){
        super(name, id);
        healthStatus=HealthStatus.HEALTHY;
        fatigue=0;
    }
    public HealthStatus getHealthStatus(){
        return healthStatus;
    }
    public void setHealthStatus(HealthStatus healthStatus){
        if(healthStatus==null) throw new IllegalArgumentException("Health status cannot be null");
        this.healthStatus=healthStatus;
    }
    @Override
    public boolean isUsable(){
        return healthStatus!=HealthStatus.CRITICAL && fatigue<FATIGUE_LIMIT;
    }
    public int getFatigue() { return fatigue; }

    public boolean addFatigue(int amount){
        if(amount<0){
            System.out.println("invalid fatigue");
            return false;
        }
        if(fatigue+amount>MAX_FATIGUE){
            fatigue=MAX_FATIGUE;
            return true;
        }
        this.fatigue+=amount;
        return true;

    }
    public boolean rest(int amount){
        if(amount<0 ){
            System.out.println("amount cant be negative");
            return false;
        }
        if(fatigue-amount<0){
            fatigue=0;
            return true;
        }
        fatigue-=amount;
        return true;

    }

    @Override
    public boolean canPerform(Mission mission){ //WAIT FOR MISSION CLASS TO BE ABLE TO DO IT
        return isAvailable() && hasAllSkills(mission.getRequiredSkills());
    }
    public void addSkill(Skill s) {
        if (s == null) throw new IllegalArgumentException("Skill cannot be null");
        skills.add(s);
    }

    public boolean hasSkill(Skill s) { return skills.contains(s); }

    public Set<Skill> getSkills() { return Collections.unmodifiableSet(skills); }
    public boolean hasAllSkills(Set<Skill> required) { return skills.containsAll(required); }

    public void increaseHealth(){
        if(healthStatus==HealthStatus.HEALTHY) {
            System.out.println("Crewmember already healthy");
            return;
        }
        healthStatus=HealthStatus.HEALTHY;
    }
}
