import java.util.List;

public class EmergencyHandler {

    private List<Agent> agents;
    private List<Habitat> habitats;
    private List<Laboratory> laboratories;
    private List<Rover> rovers; //lessa joe hy3emel el rover class
    private double oxygenLoss;

    public EmergencyHandler(List <Agent> agents, List <Habitat> habitats, List<Laboratory> laboratories){
        this.agents=agents;
        this.habitats=habitats;
        this.laboratories=laboratories;
        oxygenLoss=10.0;
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

        for (Habitat h : habitats) {
            if (h.getLocation().equalsIgnoreCase(emergency.getLocation())) {
                habitat = h;
                break;
            }
        }

        if (habitat == null) {
            emergency.setEmergencyStatus(
                    Emergency.EmergencyStatus.Unresolved);
            return;
        }

// Deducting the initial oxygen loss , Problem begins
        habitat.reduceOxygen(oxygenLoss);

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
            habitat.reduceOxygen(oxygenLoss); //corrupt increases!
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
            habitat.reduceOxygen(oxygenLoss);
            emergency.setEmergencyStatus(
                    Emergency.EmergencyStatus.Unresolved);
        }

        human.release();
        robot.release();
    }



    private void handleFire(Emergency emergency){

        Laboratory laboratory=null;

        for (Laboratory l : laboratories){
            if(laboratory.getLocation().equalsIgnoreCase(emergency.getLocation()))
               laboratory=l;
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
            if (a instanceof CrewMember
                    && a.getCurrentLoc() != null
                    && a.getCurrentLoc().equals(emergency.getLocation())) {

                patient = (CrewMember) a; //casting to use patient. with crewmember fields
                break;
            }
        }

        Agent medicalOfficer = null;

        for (Agent a : agents) {
            if (a.isAvailable() && a.canHandleEmergency(emergency)) {
                medicalOfficer = a;
                break;
            }
        }

        if (patient == null || medicalOfficer == null) {
            emergency.setEmergencyStatus(
                    Emergency.EmergencyStatus.UNRESOLVED);
            return;
        }

        Facility facility = patient.getCurrentLoc();

        if (facility == null || !facility.isAvailable()) {
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
        medicalOfficer.setLocation(facility);

        emergency.setEmergencyStatus(
                Emergency.EmergencyStatus.InProgress);

        facility.consumeMedicalSupplies(1);
        patient.increaseHealth(20);

        emergency.setEmergencyStatus(
                Emergency.EmergencyStatus.Resolved);

        medicalOfficer.release();

    }


    private void handleRoverbreakdown(Emergency emergency){

    }






}
