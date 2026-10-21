public abstract class Agent {
    private String name,id;
    public enum HealthStatus{
        HEALTHY,
        INJURED,
        CRITICAL
    }
    public enum Location{
        FACILITY,
        SURFACE
    }
    private HealthStatus healthStatus;
    private Location currentLoc;
    private boolean available;

    public Agent(String name,String id){
        if(!isValidString(name)||!isValidString(id))
            throw new IllegalArgumentException("Invalid name or id");
        this.name=name;
        this.id=id;
        this.healthStatus=HealthStatus.HEALTHY;
        this.currentLoc=Location.SURFACE;
        this.available =true;


    }
    public boolean isValidString(String s){
        return s!=null && !s.isBlank();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public Location getCurrentLoc() {
        return currentLoc;
    }

    public boolean isAvailable() {
        return available &&isUsable();
    }
    public boolean setName(String name){
        if(!isValidString(name)){
            System.out.println("invalid name");
            return false;
        }
        this.name=name;
        return true;
    }
    public void setLocation(Location location){
        this.currentLoc=location;
    }
    public void assign(){
        if(!available){
            System.out.println("Agent "+name+ "is already on a mission");
            return;
        }
        available =false;
    }
    public void release(){
        if(available){
            System.out.println("Agent "+name+ "is already free");
            return;
        }
        available =true;
    }

    public void setHealthStatus(HealthStatus healthStatus){
        this.healthStatus=healthStatus;
    }
    public abstract boolean canPerform(Mission mission);
    public abstract boolean canHandleEmergency(Emergency emergency);
    public boolean isUsable(){
        return this.healthStatus!=HealthStatus.CRITICAL;
    }


}
