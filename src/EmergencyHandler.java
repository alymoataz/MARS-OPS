import java.util.List;

public class EmergencyHandler {

    private List<Agent> agents;
    private List<Facility> facilities;
    private final Colony colony;
    private static final double OXYGEN_LEAK_LOSS = 10;
    private static final double LEAK_SPARE_PARTS = 2;
    private static final double LEAK_BATTERY = 5;

    public EmergencyHandler(List<Agent> agents, List<Facility> facilities, Colony colony) {
        if (agents == null || facilities == null || colony == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.agents = List.copyOf(agents);
        this.facilities = List.copyOf(facilities);
        this.colony = colony;
    }

    public void handleEmergency(Emergency emergency) {

        if (emergency == null) {
            throw new IllegalArgumentException("Null emergency.");
        }

        if (emergency.getEmergencyStatus() != Emergency.EmergencyStatus.Reported) {
            return;
        }

        switch (emergency.getEmergencyType()) {

            case OxygenLeak:
                handleOxygenLeak(emergency);
                break;

            case Fire:
                handleFire(emergency);
                break;

            case RoverBreakdown:
                handleRoverBreakdown(emergency);
                break;

            case MedicalEmergency:
                handleMedicalEmergency(emergency);
                break;
        }
    }

    // OXYGEN LEAK

    private void handleOxygenLeak(Emergency emergency) {
        if (emergency.getLocation() == null) {
            emergency.markUnresolved("Oxygen leak has no location.");
            return;
        }
        Habitat habitat = null;
        for (Facility f : facilities) {
            if (f instanceof Habitat &&
                    f.getLocation().equalsIgnoreCase(emergency.getLocation().getLocation())) {
                habitat = (Habitat) f;
                break;
            }
        }
        if (habitat == null) {
            emergency.markUnresolved("No habitat found at the leak location.");
            return;
        }

        // initial oxygen loss when the leak is detected
        habitat.reduceOxygen(OXYGEN_LEAK_LOSS);

        Engineer engineer = null;
        RepairRobot robot = null;
        for (Agent a : agents) {
            if (!a.isAvailable() || !a.isUsable() || !a.canHandleEmergency(emergency)) {
                continue;
            }
            if (a instanceof Engineer && engineer == null) engineer = (Engineer) a;
            if (a instanceof RepairRobot && robot == null) robot = (RepairRobot) a;
        }
        if (engineer == null || robot == null) {
            habitat.reduceOxygen(OXYGEN_LEAK_LOSS); // unresolved: second loss
            emergency.markUnresolved("Required responders (engineer and repair robot) are unavailable.");
            return;
        }

        if (!engineer.assign()) {
            habitat.reduceOxygen(OXYGEN_LEAK_LOSS);
            emergency.markUnresolved("Engineer could not be assigned.");
            return;
        }
        if (!robot.assign()) {
            engineer.release();
            habitat.reduceOxygen(OXYGEN_LEAK_LOSS);
            emergency.markUnresolved("Repair robot could not be assigned.");
            return;
        }

        // response resources, all-or-nothing
        ResourceInventory needed = new ResourceInventory();
        needed.setSpareParts(LEAK_SPARE_PARTS);
        needed.setBatteryPower(LEAK_BATTERY);
        if (!colony.consumeResources(needed)) {
            engineer.release();
            robot.release();
            habitat.reduceOxygen(OXYGEN_LEAK_LOSS);
            emergency.markUnresolved("Insufficient resources for the repair (spare parts / battery).");
            return;
        }

        try {
            engineer.setLocation(habitat);
            robot.setLocation(habitat);
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.InProgress);
            if (habitat.repairOxygenleak()) {
                emergency.setEmergencyStatus(Emergency.EmergencyStatus.Resolved);
            } else {
                habitat.reduceOxygen(OXYGEN_LEAK_LOSS);
                emergency.markUnresolved("Repair attempt failed.");
            }
        } finally {
            engineer.release();
            robot.release();
        }
    }

    // FIRE

    private void handleFire(Emergency emergency) {

        Laboratory laboratory = null;

        if (emergency.getLocation() == null) {
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
            return;
        }

        for (Facility f : facilities) {

            if (f instanceof Laboratory &&
                f.getLocation().equalsIgnoreCase(
                    emergency.getLocation().getLocation())) {

                laboratory = (Laboratory) f;
                break;
            }
        }

        if (laboratory == null) {
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
            return;
        }

        Agent responder = null;

        for (Agent a : agents) {

            if (a.isAvailable() && a.isUsable() &&
                a.canHandleEmergency(emergency)) {

                responder = a;
                break;
            }
        }

        if (responder == null || !responder.assign()) {
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
            return;
        }

        try {
            responder.setLocation(laboratory);

            emergency.setEmergencyStatus(Emergency.EmergencyStatus.InProgress);

            boolean extinguished = laboratory.repairFire();

            if (extinguished) {
                emergency.setEmergencyStatus(Emergency.EmergencyStatus.Resolved);
            } else {
                emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
            }

        } finally {
            responder.release();
        }
    }

    // MEDICAL EMERGENCY

    private void handleMedicalEmergency(Emergency emergency) {

        if (emergency.getLocation() == null) {
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
            return;
        }

        CrewMember patient = null;

        // Find the injured crew member
        for (Agent a : agents) {

            if (a instanceof CrewMember &&
                a.getCurrentLoc() != null &&
                a.getCurrentLoc().getLocation().equalsIgnoreCase(
                    emergency.getLocation().getLocation())) {

                patient = (CrewMember) a;
                break;
            }
        }

        Agent medicalOfficer = null;

        // Find an available medical responder
        for (Agent a : agents) {

            if (a != patient && a.isAvailable() && a.isUsable() &&
                a.canHandleEmergency(emergency)) {

                medicalOfficer = a;
                break;
            }
        }

        if (patient == null || medicalOfficer == null) {
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
            return;
        }

        Facility facility = null;

        for (Facility f : facilities) {

            if (f.getLocation().equalsIgnoreCase(
                emergency.getLocation().getLocation())) {

                facility = f;
                break;
            }
        }

        if (facility == null || !facility.hasMedicalSupplies()) {
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
            return;
        }

        if (!medicalOfficer.assign()) {
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
            return;
        }

        try {
            medicalOfficer.setLocation(facility);

            emergency.setEmergencyStatus(Emergency.EmergencyStatus.InProgress);

            facility.consumeMedicalSupplies();
            patient.increaseHealth();

            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Resolved);

        } finally {
            medicalOfficer.release();
        }
    }

    // ROVER BREAKDOWN

    private void handleRoverBreakdown(Emergency emergency) {

        ExplorationRover rover = emergency.getAffectedRover();

        if (rover == null) {
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
            return;
        }

        Agent responder = null;

        // Find a suitable repair responder
        for (Agent a : agents) {

            if (a != rover && a.isAvailable() && a.isUsable() &&
                a.canHandleEmergency(emergency)) {

                responder = a;
                break;
            }
        }

        if (responder == null || !responder.assign()) {
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
            return;
        }

        try {
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.InProgress);

            // Restore the rover's operational condition
            rover.setDamageStatus(Robot.DamageStatus.OPERATIONAL);

            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Resolved);

        } finally {
            responder.release();
        }
    }
}