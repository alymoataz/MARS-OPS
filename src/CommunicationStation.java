public class CommunicationStation extends Facility {

    private boolean signalAvailable;
    private double signalStrength;
    private boolean connected;

    public CommunicationStation(String name, String location, int capacity, ResourceInventory inventory) {
        super(name, location, capacity, inventory);

        this.signalAvailable = false;
        this.signalStrength = 0;
        this.connected = false;
    }

    public boolean isSignalAvailable() {
        return signalAvailable;
    }

    public double getSignalStrength() {
        return signalStrength;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setSignalStrength(double signalStrength) {
        if (signalStrength >= 0 && signalStrength <= 100) {
            this.signalStrength = signalStrength;
            this.signalAvailable = signalStrength > 0;
        }
    }

    public boolean establishConnection() {
        if (!isOperational() || !signalAvailable) {
            System.out.println("No signal reached.");
            connected = false;
            return false;
        }

        connected = true;
        return true;
    }

    public void disconnect() {
        connected = false;
    }

}