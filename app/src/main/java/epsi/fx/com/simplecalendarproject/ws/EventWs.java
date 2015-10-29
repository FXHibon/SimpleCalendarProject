package epsi.fx.com.simplecalendarproject.ws;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventWs {

    @SerializedName("id")
    @Expose
    private UUID id;
    @SerializedName("author")
    @Expose
    private UUID author;
    /**
     * (Required)
     */
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    /**
     * (Required)
     */
    @SerializedName("begin")
    @Expose
    private DateTime begin;
    /**
     * (Required)
     */
    @SerializedName("end")
    @Expose
    private DateTime end;
    /**
     * (Required)
     */
    @SerializedName("participants")
    @Expose
    private List<ParticipantWs> participants = new ArrayList<ParticipantWs>();

    /**
     * @return The id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    public EventWs withId(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * @return The author
     */
    public UUID getAuthor() {
        return author;
    }

    /**
     * @param author The author
     */
    public void setAuthor(UUID author) {
        this.author = author;
    }

    public EventWs withAuthor(UUID author) {
        this.author = author;
        return this;
    }

    /**
     * (Required)
     *
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * (Required)
     *
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public EventWs withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public EventWs withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * (Required)
     *
     * @return The begin
     */
    public DateTime getBegin() {
        return begin;
    }

    /**
     * (Required)
     *
     * @param begin The begin
     */
    public void setBegin(DateTime begin) {
        this.begin = begin;
    }

    public EventWs withBegin(DateTime begin) {
        this.begin = begin;
        return this;
    }

    /**
     * (Required)
     *
     * @return The end
     */
    public DateTime getEnd() {
        return end;
    }

    /**
     * (Required)
     *
     * @param end The end
     */
    public void setEnd(DateTime end) {
        this.end = end;
    }

    public EventWs withEnd(DateTime end) {
        this.end = end;
        return this;
    }

    /**
     * (Required)
     *
     * @return The participants
     */
    public List<ParticipantWs> getParticipants() {
        return participants;
    }

    /**
     * (Required)
     *
     * @param participants The participants
     */
    public void setParticipants(List<ParticipantWs> participants) {
        this.participants = participants;
    }

    public EventWs withParticipants(List<ParticipantWs> participants) {
        this.participants = participants;
        return this;
    }

}