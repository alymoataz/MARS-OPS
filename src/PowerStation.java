public class PowerStation extends Facility {

    private double generationRate;
    private boolean generatorActive;

    public PowerStation(String name, String location, int capacity,
                        double generationRate) {
        super(name, location, capacity);
        this.generationRate = generationRate;
        this.generatorActive = false;
    }

    public double getGenerationRate() {
        return generationRate;
    }

    public boolean isGeneratorActive() {
        return generatorActive;
    }

    public void startGenerator() {
        if (isOperational()) {
            generatorActive = true;
        }
    }

    public void shutDownGenerator() {
        generatorActive = false;
    }

    public boolean generatePower() {
        if (!isOperational() || !generatorActive || generationRate <= 0) {
            System.out.println("Cannot generate power.");
            return false;
        }

        double currentPower = getInventory().getBatteryPower();
        getInventory().setBatteryPower(currentPower + generationRate);
        return true;
    }
}