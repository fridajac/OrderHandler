import java.io.Serializable;

public enum Status implements Serializable {

    SUBMITTED("Submitted"),
    ACCEPTED("Accepted"),
    READY("Ready");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
