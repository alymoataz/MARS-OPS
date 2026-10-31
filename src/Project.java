import java.util.ArrayList;

public class Project {
    public static void main(String[] args) {

        // Create the colony
        Colony colony = new Colony("MARS-OPS");

        // Create the habitat's resource inventory
        ResourceInventory habitatInventory = new ResourceInventory();

        // Create a habitat
        Habitat habitat = new Habitat(
            "Main Habitat",
            "Sector A",
            10,
            habitatInventory,
            0,
            true,
            10
        );

        // Add resources to the habitat
        habitatInventory.setOxygen(100);
        habitatInventory.setWater(100);
        habitatInventory.setFood(100);
        habitatInventory.setFuel(100);
        habitatInventory.setBatteryPower(100);
        habitatInventory.setMedicalSupplies(20);
        habitatInventory.setSpareParts(50);

        // Add the habitat to the colony
        colony.addFacility(habitat);

        // Create facility inventories
        ResourceInventory labInventory = new ResourceInventory();
        ResourceInventory powerInventory = new ResourceInventory();
        ResourceInventory storageInventory = new ResourceInventory();
        ResourceInventory communicationInventory = new ResourceInventory();

        // Create facilities
        Laboratory laboratory = new Laboratory(
            "Research Lab", "Sector B", 5, labInventory, 10
        );

        PowerStation powerStation = new PowerStation(
            "Power Station", "Sector C", 5, powerInventory, 100.0
        );

        StorageFacility storage = new StorageFacility(
            "Storage", "Sector D", 20, storageInventory, 1000.0
        );

        CommunicationStation communication = new CommunicationStation(
            "Comms Station", "Sector E", 5, communicationInventory
        );

        // Add facilities to colony
        colony.addFacility(laboratory);
        colony.addFacility(powerStation);
        colony.addFacility(storage);
        colony.addFacility(communication);

        // Create crew members
        Engineer engineer = new Engineer("John", "E001");
        Scientist scientist = new Scientist("Sarah", "S001");

        // Set their initial locations
        engineer.setLocation(habitat);
        scientist.setLocation(habitat);

        // Add them to the colony
        colony.addAgent(engineer);
        colony.addAgent(scientist);

        // Create robots
        RepairRobot repairRobot = new RepairRobot("Repair Bot", "R001");
        ExplorationRover rover = new ExplorationRover("Explorer", "R002", 5);

        // Set their initial locations
        repairRobot.setLocation(habitat);
        rover.setLocation(habitat);

        // Add robots to the colony
        colony.addAgent(repairRobot);
        colony.addAgent(rover);

        // Create an Exploration mission
        ArrayList<Agent> explorationAgents = new ArrayList<>();
        explorationAgents.add(rover);
        explorationAgents.add(scientist);

        Exploration exploration = new Exploration(
            3,
            explorationAgents
        );

        // Submit the mission
        ResourceInventory explorationResources = new ResourceInventory();
        explorationResources.setFuel(10);
        explorationResources.setBatteryPower(10);

        boolean accepted = colony.submitMission(
            exploration,
            explorationResources
        );

        System.out.println("Exploration mission accepted: " + accepted);

        // Advance the mission by 1 time unit
        colony.advanceMission(1);

        System.out.println("Mission status: " + exploration.getStatus());

        // Advance the remaining time
        colony.advanceMission(2);

        System.out.println("Mission status: " + exploration.getStatus());

        // Create damaged equipment
        Equipment equipment = new Equipment("Solar Panel");
        equipment.damage();

        // Create a valid repair team
        ArrayList<Agent> repairAgents = new ArrayList<>();
        repairAgents.add(engineer);
        repairAgents.add(repairRobot);

        // Create repair mission
        Repair repairMission = new Repair(
            2,
            repairAgents,
            equipment,
            5
        );

        // Resources required
        ResourceInventory repairResources = new ResourceInventory();
        repairResources.setSpareParts(5);

        // Submit mission
        boolean repairAccepted = colony.submitMission(
            repairMission,
            repairResources
        );

        System.out.println("Repair mission accepted: " + repairAccepted);

        // Complete mission
        colony.advanceMission(2);

        System.out.println("Equipment operational: " + equipment.isOperational());

        // Create Research mission
        ArrayList<Agent> researchAgents = new ArrayList<>();
        researchAgents.add(scientist);

        Research researchMission = new Research(
            2,
            researchAgents,
            CrewMember.Skill.GEOLOGY,
            laboratory,
            5
        );

        // Required resources
        ResourceInventory researchResources = new ResourceInventory();
        researchResources.setWater(5);

        // Submit mission
        boolean researchAccepted = colony.submitMission(
            researchMission,
            researchResources
        );

        System.out.println("Research mission accepted: " + researchAccepted);

        // Complete mission
        colony.advanceMission(2);

        System.out.println("Research mission status: " + researchMission.getStatus());

        communication.setSignalStrength(100);
        communication.establishConnection();

        // Create Rescue mission
        ArrayList<Agent> rescueAgents = new ArrayList<>();
        rescueAgents.add(rover);
        rescueAgents.add(engineer);

        Rescue rescueMission = new Rescue(
            2,
            rescueAgents,
            rover,
            communication
        );

        // Required resources
        ResourceInventory rescueResources = new ResourceInventory();

        // Submit mission
        boolean rescueAccepted = colony.submitMission(
            rescueMission,
            rescueResources
        );

        System.out.println("Rescue mission accepted: " + rescueAccepted);

        // Complete mission
        colony.advanceMission(2);

        System.out.println("Rescue mission status: " + rescueMission.getStatus());

        // Test insufficient resources
        ResourceInventory shortageResources = new ResourceInventory();
        shortageResources.setSpareParts(1000);

        // Record resources before submission
        double spareBefore = colony.getTotalResources().getSpareParts();

        // Create a Repair mission
        ArrayList<Agent> shortageAgents = new ArrayList<>();
        shortageAgents.add(engineer);
        shortageAgents.add(repairRobot);

        Equipment anotherEquipment = new Equipment("Damaged Generator");
        anotherEquipment.damage();

        Repair shortageMission = new Repair(
            2, shortageAgents, anotherEquipment, 1000
        );

        // Submit mission
        boolean shortageAccepted = colony.submitMission(
            shortageMission, shortageResources
        );

        System.out.println("Shortage mission accepted: " + shortageAccepted);
        System.out.println("Mission status: " + shortageMission.getStatus());
        System.out.println("Spare parts before: " + spareBefore);
        System.out.println("Spare parts after: " + colony.getTotalResources().getSpareParts());

        // Create an oxygen leak emergency
        Emergency oxygenLeak = new Emergency(
            "E001",
            habitat,
            Emergency.EmergencyType.OxygenLeak,
            4
        );

        // Handle the emergency
        colony.handleEmergency(oxygenLeak);

        // Display result
        System.out.println("Emergency status: " +
            oxygenLeak.getEmergencyStatus());

        System.out.println("Engineer available: " +
            engineer.isAvailable());

        System.out.println("Repair Robot available: " +
            repairRobot.isAvailable());

        // Change the environment to a dust storm
        colony.setEnvironment(Colony.Environment.DUST_STORM);

        // Create a new Exploration mission
        ArrayList<Agent> stormAgents = new ArrayList<>();
        stormAgents.add(rover);
        stormAgents.add(scientist);

        Exploration stormMission = new Exploration(
            3,
            stormAgents
        );

        // Submit the mission
        ResourceInventory stormResources = new ResourceInventory();

        boolean stormAccepted = colony.submitMission(
            stormMission,
            stormResources
        );

        System.out.println("Exploration during dust storm accepted: " + stormAccepted);
        System.out.println("Mission status: " + stormMission.getStatus());

        // Restore normal environment
        colony.setEnvironment(Colony.Environment.NORMAL);

        // Display colony status
        colony.displayStatusReport();
    }
}