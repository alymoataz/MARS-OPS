import java.util.List;

public class EmergencyHandler {

    private List<Agent> agents;
    private List<Facility> facilities;


    public EmergencyHandler(List <Agent> agents, List <Facility> facilities){
        this.agents=agents;
        this.facilities=facilities;
    }



   public void handleEmergency(Emergency emergency){

       if(emergency==null)
           throw new IllegalArgumentException("null input"); //report error

       if(emergency.getEmergencyStatus()!= Emergency.EmergencyStatus.Reported)
           return; //continue normally , not an error

      switch (emergency.getType()){

          case Emergency.EmergencyType.OxygenLeak:
              handleOxygenLeak(emergency);
              break;

          case Emergency.EmergencyType.Fire:
              handleFire(emergency);
               break;

          case Emergency.EmergencyType.RoverBreakdown:
              handleRoverbreakdown(emergency);
              break;

          case Emergency.EmergencyType.MedicalEmergency:
              handleMedicalEmergency(emergency);
              break;

      }





   }

    private void handleOxygenLeak(Emergency emergency) {



        Habitat habitat = null;

        for (Facility f : facilities) {  //see which of your habitats is affected
            if (f instanceof Habitat && f.getLocation().equalsIgnoreCase(emergency.getLocation().getLocation())) {
                habitat = (Habitat) f;
                break;
            }
        }

        if (habitat == null) {
            emergency.setEmergencyStatus(
                    Emergency.EmergencyStatus.Unresolved);
            return;
        }

// Deducting the initial oxygen loss , Problem begins
        habitat.reduceOxygen(10);

        Agent human = null;
        Agent robot = null;

// Finding a capable human and robot
        for (Agent a : agents) {
            if (a.isAvailable() && a.canHandleEmergency(emergency)) {

                if (a instanceof CrewMember && human == null) {
                    human = a;
                } else if (a instanceof Robot && robot == null) {
                    robot = a;
                }
            }
        }

// Both responders are required
        if (human == null || robot == null) {
            habitat.reduceOxygen(10); //corrupt increases!
            emergency.setEmergencyStatus(
                    Emergency.EmergencyStatus.Unresolved);
            return;
        }

        human.assign();
        robot.assign();
        human.setLocation(habitat); //moving them, dispatching
        robot.setLocation(habitat);

        emergency.setEmergencyStatus(
                Emergency.EmergencyStatus.InProgress);

        boolean repaired = habitat.repairOxygenleak();

        if (repaired) {
            emergency.setEmergencyStatus(
                    Emergency.EmergencyStatus.Resolved);
        } else {
            habitat.reduceOxygen(10);
            emergency.setEmergencyStatus(
                    Emergency.EmergencyStatus.Unresolved);
        }

        human.release();
        robot.release();
    }



    private void handleFire(Emergency emergency){

        Laboratory laboratory=null;

        for (Facility f : facilities){
            if(f instanceof Laboratory && f.getLocation().equals(emergency.getLocation().getLocation()))
               laboratory=(Laboratory) f;
               break;
        }

        if(laboratory==null){
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved); //lab location in emergency doesn't match a location in my list of labs
        }

        Agent responder =null; // search agent in list

        for (Agent a : agents){
            if(a.canHandleEmergency(emergency) && a.isAvailable()){
                responder=a; //copy reference from agent reference a a to agent refernece called responder
                break;
            }

            if(responder==null){
                emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
                return;
            }

            responder.assign();
            responder.setLocation(laboratory);
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.InProgress);

            boolean extinguished= laboratory.repairFire();

            if(extinguished){
                emergency.setEmergencyStatus(Emergency.EmergencyStatus.Resolved);
            } else {
                emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
            }

            responder.release();

        }


    }


    private void handleMedicalEmergency(Emergency emergency){

        CrewMember patient = null;

        for (Agent a : agents) {
            if (a instanceof CrewMember && a.getCurrentLoc().getLocation().equals(emergency.getLocation().getLocation())){ //law a points to a crewmember object or any subclasses of it

                patient = (CrewMember) a; //casting to use patient. with crewmember fields
                break;                   //law mn8er casting invalid
            }
        }

        Agent medicalOfficer = null;

        for (Agent a : agents) {
            if (a.isAvailable() && a.canHandleEmergency(emergency) && a.isAvailable()) {
                medicalOfficer = a;
                break;
            }
        }

        if (patient == null || medicalOfficer == null) {
            emergency.setEmergencyStatus(
                    Emergency.EmergencyStatus.Unresolved);
            return;
        }

        Facility facility=null;

        for(Facility f : facilities){
            if(f.getLocation().equalsIgnoreCase(emergency.getLocation().getLocation())){
                facility=f;
                break;
            }
        }


        if (facility == null) {
            emergency.setEmergencyStatus(
                    Emergency.EmergencyStatus.Unresolved);
            return;
        }

        if (!facility.hasMedicalSupplies()) {
            emergency.setEmergencyStatus(
                    Emergency.EmergencyStatus.Unresolved);
            return;
        }

        medicalOfficer.assign();
        medicalOfficer.setLocation(facility); //move to patient's injured facility

        emergency.setEmergencyStatus(
                Emergency.EmergencyStatus.InProgress);

        facility.consumeMedicalSupplies();
        patient.increaseHealth();

        emergency.setEmergencyStatus(
                Emergency.EmergencyStatus.Resolved);

        medicalOfficer.release();

    }


    private void handleRoverbreakdown(Emergency emergency){



            Facility location = emergency.getLocation();


            if (!(location instanceof Rover)) {
                System.out.println("Emergency location is not a rover.");
                emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
                return;
            }


            Rover rover = (Rover) location;


            Agent responder = null;

            for (Agent a : agents) {
                if (a.isAvailable() && a.canHandleEmergency(emergency)) {
                    responder = a;
                    break;
                }
            }


            if (responder == null) {
                System.out.println("No available agent to repair the rover");
                emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
                return;
            }


            responder.assign();
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.InProgress);


            rover.repair();

        emergency.setEmergencyStatus(Emergency.EmergencyStatus.Resolved);


            responder.release();
        }







}
