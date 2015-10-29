package epsi.fx.com.simplecalendarproject.ws;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class ParticipantWs {

    /**
     * (Required)
     */
    @SerializedName("id")
    @Expose
    private String id;
    /**
     * (Required)
     */
    @SerializedName("status")
    @Expose
    private ParticipantWs.Status status;

    /**
     * (Required)
     *
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * (Required)
     *
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public ParticipantWs withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * (Required)
     *
     * @return The status
     */
    public ParticipantWs.Status getStatus() {
        return status;
    }

    /**
     * (Required)
     *
     * @param status The status
     */
    public void setStatus(ParticipantWs.Status status) {
        this.status = status;
    }

    public ParticipantWs withStatus(ParticipantWs.Status status) {
        this.status = status;
        return this;
    }

    public static enum Status {

        @SerializedName("present")
        PRESENT("present"),
        @SerializedName("absent")
        ABSENT("absent"),
        @SerializedName("unknown")
        UNKNOWN("unknown");
        private final String value;
        private static Map<String, ParticipantWs.Status> constants = new HashMap<String, ParticipantWs.Status>();

        static {
            for (ParticipantWs.Status c : values()) {
                constants.put(c.value, c);
            }
        }

        private Status(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public static ParticipantWs.Status fromValue(String value) {
            ParticipantWs.Status constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}