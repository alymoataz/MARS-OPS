import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Colony {

    public enum Environment {
        NORMAL,
        DUST_STORM,
        COMMUNICATION_DISRUPTION
    }

    private String name;

    private List<Agent> agents;
    private List<Facility> facilities;
    private List<Mission> missions;

    private Mission activeMission;

    private Environment environment;

    public Colony(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid colony name.");
        }

        this.name = name;
        this.agents = new ArrayList<>();
        this.facilities = new ArrayList<>();
        this.missions = new ArrayList<>();

        this.activeMission = null;
        this.environment = Environment.NORMAL;
    }

    public String getName() {
        return name;
    }

    public List<Agent> getAgents() {
        return Collections.unmodifiableList(agents);
    }

    public List<Facility> getFacilities() {
        return Collections.unmodifiableList(facilities);
    }

    public List<Mission> getMissions() {
        return Collections.unmodifiableList(missions);
    }

    public Mission getActiveMission() {
        return activeMission;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {

        if (environment == null) {
            throw new IllegalArgumentException("Environment cannot be null.");
        }

        this.environment = environment;

        if (activeMission == null) {
            return;
        }

        if (environment == Environment.DUST_STORM &&
            activeMission instanceof Exploration) {

            activeMission.fail("Mission interrupted by a dust storm.");
            activeMission = null;
        }

        else if (environment == Environment.COMMUNICATION_DISRUPTION &&
                activeMission instanceof Rescue) {

            activeMission.fail("Mission interrupted by communication disruption.");
            activeMission = null;
        }
    }
    public boolean addAgent(Agent agent) {
    if (agent == null || agents.contains(agent)) {
        return false;
    }

    agents.add(agent);
    return true;
    }

    public boolean removeAgent(Agent agent) {
        if (agent == null || !agents.contains(agent)) {
            return false;
        }

        if (!agent.isAvailable()) {
            return false;
        }

        agents.remove(agent);
        return true;
    }

    public boolean addFacility(Facility facility) {
        if (facility == null || facilities.contains(facility)) {
            return false;
        }

        facilities.add(facility);
        return true;
    }

    public boolean removeFacility(Facility facility) {
        if (facility == null || !facilities.contains(facility)) {
            return false;
        }

        facilities.remove(facility);
        return true;
    }
    public ResourceInventory getTotalResources() {

        ResourceInventory total = new ResourceInventory();

        for (Facility facility : facilities) {

            if (!facility.isOperational()) {
                continue;
            }

            ResourceInventory inventory = facility.getInventory();

            total.setOxygen(total.getOxygen() + inventory.getOxygen());
            total.setWater(total.getWater() + inventory.getWater());
            total.setFood(total.getFood() + inventory.getFood());
            total.setFuel(total.getFuel() + inventory.getFuel());
            total.setBatteryPower(total.getBatteryPower() + inventory.getBatteryPower());
            total.setMedicalSupplies(total.getMedicalSupplies() + inventory.getMedicalSupplies());
            total.setSpareParts(total.getSpareParts() + inventory.getSpareParts());
        }

        return total;
    }
    public boolean consumeResources(ResourceInventory required) {

        if (required == null) {
            return false;
        }

        ResourceInventory total = getTotalResources();

        // Check availability first — don't consume anything yet.
        if (total.getOxygen() < required.getOxygen() ||
            total.getWater() < required.getWater() ||
            total.getFood() < required.getFood() ||
            total.getFuel() < required.getFuel() ||
            total.getBatteryPower() < required.getBatteryPower() ||
            total.getMedicalSupplies() < required.getMedicalSupplies() ||
            total.getSpareParts() < required.getSpareParts()) {

            return false;
        }

        // Consume resources from operational facilities.
        return consumeResourceType(required.getOxygen(), 0) &&
            consumeResourceType(required.getWater(), 1) &&
            consumeResourceType(required.getFood(), 2) &&
            consumeResourceType(required.getFuel(), 3) &&
            consumeResourceType(required.getBatteryPower(), 4) &&
            consumeResourceType(required.getMedicalSupplies(), 5) &&
            consumeResourceType(required.getSpareParts(), 6);
    }
    private boolean consumeResourceType(double amount, int type) {

        if (amount <= 0) {
            return true;
        }

        for (Facility facility : facilities) {

            if (!facility.isOperational()) {
                continue;
            }

            ResourceInventory inventory = facility.getInventory();

            double available = switch (type) {
                case 0 -> inventory.getOxygen();
                case 1 -> inventory.getWater();
                case 2 -> inventory.getFood();
                case 3 -> inventory.getFuel();
                case 4 -> inventory.getBatteryPower();
                case 5 -> inventory.getMedicalSupplies();
                case 6 -> inventory.getSpareParts();
                default -> 0;
            };

            double toRemove = Math.min(amount, available);

            if (toRemove > 0) {
                switch (type) {
                    case 0 -> inventory.removeOxygen(toRemove);
                    case 1 -> inventory.removeWater(toRemove);
                    case 2 -> inventory.removeFood(toRemove);
                    case 3 -> inventory.removeFuel(toRemove);
                    case 4 -> inventory.removeBatteryPower(toRemove);
                    case 5 -> inventory.removeMedicalSupplies(toRemove);
                    case 6 -> inventory.removeSpareParts(toRemove);
                }

                amount -= toRemove;
            }

            if (amount <= 0) {
                return true;
            }
        }

        return amount <= 0;
    }
 public boolean submitMission(Mission mission, ResourceInventory requiredResources) {

    if (mission == null || requiredResources == null ||
        mission.getStatus() != Mission.MissionStatus.CREATED ||
        missions.contains(mission)) {
        return false;
    }

    if (!isEnvironmentSuitable(mission)) {
        mission.reject("Mission cannot start under current environmental conditions.");
        missions.add(mission);
        return false;
    }

    if (activeMission != null) {
        mission.reject("Another mission is already active.");
        missions.add(mission);
        return false;
    }

    if (mission.getAssignedAgents().isEmpty() ||
        !agents.containsAll(mission.getAssignedAgents())) {
        mission.reject("Invalid or unavailable colony agents.");
        missions.add(mission);
        return false;
    }

    if (!mission.validateRequirements()) {
        mission.reject("Mission requirements are not satisfied.");
        missions.add(mission);
        return false;
    }

    ResourceInventory total = getTotalResources();

    if (total.getOxygen() < requiredResources.getOxygen() ||
        total.getWater() < requiredResources.getWater() ||
        total.getFood() < requiredResources.getFood() ||
        total.getFuel() < requiredResources.getFuel() ||
        total.getBatteryPower() < requiredResources.getBatteryPower() ||
        total.getMedicalSupplies() < requiredResources.getMedicalSupplies() ||
        total.getSpareParts() < requiredResources.getSpareParts()) {

        mission.reject("Insufficient colony resources.");
        missions.add(mission);
        return false;
    }

    // Assign agents first.
    if (!mission.assignAgents()) {
        missions.add(mission);
        return false;
    }

    // Accept the mission.
    if (!mission.accept()) {
        missions.add(mission);
        return false;
    }

    // Consume resources after successful assignment and acceptance.
    if (!consumeResources(requiredResources)) {
        mission.fail("Failed to consume required resources.");
        missions.add(mission);
        return false;
    }

    activeMission = mission;
    missions.add(mission);

    return true;
}
    public void advanceMission(int timeUnits) {

        if (activeMission == null) {
            System.out.println("There is no active mission.");
            return;
        }

        if (timeUnits <= 0) {
            System.out.println("Time units must be positive.");
            return;
        }

        // Start the mission if it hasn't started yet.
        if (activeMission.getStatus() == Mission.MissionStatus.ASSIGNED) {
            activeMission.start();
        }

        // Advance mission time.
        activeMission.advanceTime(timeUnits);

        // Check whether the mission has ended.
        if (activeMission.getStatus() == Mission.MissionStatus.COMPLETED ||
            activeMission.getStatus() == Mission.MissionStatus.FAILED) {

            System.out.println("Mission ended: " + activeMission.getStatus());
            activeMission = null;
        }
    }
    private boolean isEnvironmentSuitable(Mission mission) {

        if (environment == Environment.DUST_STORM &&
            mission instanceof Exploration) {
            return false;
        }

        if (environment == Environment.COMMUNICATION_DISRUPTION &&
            mission instanceof Rescue) {
            return false;
        }

        return true;
    }
    public void handleEmergency(Emergency emergency) {

        if (emergency == null) {
            throw new IllegalArgumentException("Emergency cannot be null.");
        }

        EmergencyHandler handler = new EmergencyHandler(agents, facilities);

        handler.handleEmergency(emergency);
    }
    public void displayStatusReport() {

        System.out.println("\n========== COLONY STATUS ==========");

        System.out.println("Colony: " + name);
        System.out.println("Environment: " + environment);

        System.out.println("\nAgents: " + agents.size());
        System.out.println("Facilities: " + facilities.size());
        for (Facility facility : facilities) {
            System.out.println("- " + facility.getName());
        }

        System.out.println("\nActive Mission:");

        if (activeMission == null) {
            System.out.println("No active mission.");
        } else {
            System.out.println("Status: " + activeMission.getStatus());
        }

        ResourceInventory total = getTotalResources();

        System.out.println("\nTotal Colony Resources:");
        System.out.println("Oxygen: " + total.getOxygen());
        System.out.println("Water: " + total.getWater());
        System.out.println("Food: " + total.getFood());
        System.out.println("Fuel: " + total.getFuel());
        System.out.println("Battery Power: " + total.getBatteryPower());
        System.out.println("Medical Supplies: " + total.getMedicalSupplies());
        System.out.println("Spare Parts: " + total.getSpareParts());

        System.out.println("===================================");
    }
}
