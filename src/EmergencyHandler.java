import java.util.List;

public class EmergencyHandler {

    private List<Agent> agents;
    private List<Facility> facilities;

    public EmergencyHandler(List<Agent> agents, List<Facility> facilities) {
        if (agents == null || facilities == null) {
            throw new IllegalArgumentException("Lists cannot be null.");
        }

        this.agents = List.copyOf(agents);
        this.facilities = List.copyOf(facilities);
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

        Habitat habitat = null;

        if (emergency.getLocation() == null) {
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
            return;
        }

        for (Facility f : facilities) {
            if (f instanceof Habitat &&
                f.getLocation().equalsIgnoreCase(
                    emergency.getLocation().getLocation())) {

                habitat = (Habitat) f;
                break;
            }
        }

        if (habitat == null) {
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
            return;
        }

        Engineer engineer = null;
        RepairRobot robot = null;

        // Find suitable responders
        for (Agent a : agents) {

            if (!a.isAvailable() || !a.isUsable() ||
                !a.canHandleEmergency(emergency)) {
                continue;
            }

            if (a instanceof Engineer && engineer == null) {
                engineer = (Engineer) a;
            }

            if (a instanceof RepairRobot && robot == null) {
                robot = (RepairRobot) a;
            }
        }

        // Both responders are required
        if (engineer == null || robot == null) {
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
            return;
        }

        // Assign responders
        if (!engineer.assign()) {
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
            return;
        }

        if (!robot.assign()) {
            engineer.release();
            emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
            return;
        }

        try {
            engineer.setLocation(habitat);
            robot.setLocation(habitat);

            emergency.setEmergencyStatus(Emergency.EmergencyStatus.InProgress);

            // Oxygen lost because of the leak
            habitat.reduceOxygen(10);

            boolean repaired = habitat.repairOxygenleak();

            if (repaired) {
                emergency.setEmergencyStatus(Emergency.EmergencyStatus.Resolved);
            } else {
                emergency.setEmergencyStatus(Emergency.EmergencyStatus.Unresolved);
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