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

        // Display colony status
        colony.displayStatusReport();
    }
}