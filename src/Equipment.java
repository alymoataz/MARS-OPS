public class Equipment {
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

    public boolean isOperational() {
        return operational;
    }

    public void damage() {
        operational = false;
    }

    public void repair() {
        operational = true;
    }
}
