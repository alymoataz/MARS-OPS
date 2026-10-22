import java.util.List;

public class EmergencyHandler {

    private List<Agent> agents;
    private List<Habitat> habitats;
    private double oxygenLoss;

    public EmergencyHandler(List <Agent> agents, List <Habitat> habitats){
        this.agents=agents;
        this.habitats=habitats;
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
            if (h.getLocation().equals(emergency.getLocation())) {
                habitat = h;
                break;
            }
        }

        if (habitat == null) {
            emergency.setEmergencyStatus(
                    Emergency.EmergencyStatus.Unresolved);
            return;
        }

// Deduct the initial oxygen loss
        habitat.reduceOxygen(oxygenLoss);

        Agent human = null;
        Agent robot = null;

// Find a capable human and robot
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
            habitat.reduceOxygen(oxygenLoss);
            emergency.setEmergencyStatus(
                    Emergency.EmergencyStatus.Unresolved);
            return;
        }

        human.assign();
        robot.assign();

        human.setLocation(emergency.getLocation());
        robot.setLocation(emergency.getLocation());

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

    }




    private void handleRoverbreakdown(Emergency emergency){


    }

    private void handleMedicalEmergency(Emergency emergency){


    }




}
