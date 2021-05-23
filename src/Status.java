public enum Status {

    submitted("Submitted"),
    accepted("Accepted"),
    ready("Ready");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
