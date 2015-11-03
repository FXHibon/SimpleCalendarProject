package epsi.fx.com.simplecalendarproject.beans;

/**
 * Created by fx on 03/11/15.
 */
public class Participant {

    private String id;

    private Participant.Status status;

    public Participant() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        PRESENT,
        ABSENT,
        UNKNOWN
    }
}
