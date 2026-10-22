public class EmergencyHandler {

   public void handleEmergency(Emergency emergency){

       if(emergency==null)
           throw new IllegalArgumentException("null input");

       if(emergency.getEmergencyStatus()!= Emergency.EmergencyStatus.Reported)
           return;

      switch (emergency.getType()){

          case Emergency.EmergencyType.OxygenLeak:
              handleOxygenLeak(emergency);

          case Emergency.EmergencyType.Fire:
              handleFire(emergency);


          case Emergency.EmergencyType.RoverBreakdown:
              handleRoverbreakdown(emergency);

          case Emergency.EmergencyType.MedicalEmergency:
              handleMedicalEmergency(emergency);

      }
   }

   private void handleOxygenLeak(Emergency emergency){


   }



    private void handleFire(Emergency emergency){

    }




    private void handleRoverbreakdown(Emergency emergency){


    }

    private void handleMedicalEmergency(Emergency emergency){


    }




}
