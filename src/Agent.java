public abstract class Agent {
    private String name,id;
    private Facility location;
    private boolean available;

    public Agent(String name,String id){
        if(!isValidString(name)||!isValidString(id))
            throw new IllegalArgumentException("Invalid name or id");
        this.name=name;
        this.id=id;
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

    public Facility getCurrentLoc() {
        return location ;
    }

    public boolean isAvailable() {
        return available && isUsable();
    }

    public boolean setName(String name){
        if(!isValidString(name)){
            System.out.println("invalid name");
            return false;
        }
        this.name=name;
        return true;
    }

    public void setLocation(Facility location){
        this.location=location;
    }

    public boolean assign(){
        if(!isAvailable()){
            System.out.println("Agent "+name+ " is already on a mission or not in usable condition");
            return false;
        }
        available =false;
        return true;
    }

    public boolean release(){
        if(available){
            System.out.println("Agent "+name+ " is already free");
            return false;
        }
        available =true;
        return true;
    } //release doesnt use isavailable() bec he might get critical state while on mission so he wont get released

    public abstract boolean canPerform(Mission mission);
    public abstract boolean canHandleEmergency(Emergency e);
    public abstract boolean isUsable();


}
