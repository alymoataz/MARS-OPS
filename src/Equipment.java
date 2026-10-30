public class Equipment implements Repairable {
    private String name;
    private boolean operational;

    public Equipment(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid equipment name.");
        }

        this.name = name;
        this.operational = true;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean isOperational() {
        return operational;
    }

    public void damage() {
        operational = false;
    }

    @Override
    public void restoreOperation() {
        operational = true;
    }
}
