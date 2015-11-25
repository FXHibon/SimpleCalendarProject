package epsi.fx.com.simplecalendarproject.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by fx on 16/10/2015.
 */
public class Event {

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String AUTHOR = "author";
    public static final String BEGIN = "begin";
    public static final String END = "end";
    public static final String PARTICIPANTS = "participants";

    @SerializedName(ID)
    @Expose
    private UUID id;
    @SerializedName(AUTHOR)
    @Expose
    private UUID author;
    /**
     * (Required)
     */
    @SerializedName(TITLE)
    @Expose
    private String title;
    @SerializedName(DESCRIPTION)
    @Expose
    private String description;
    /**
     * (Required)
     */
    @SerializedName(BEGIN)
    @Expose
    private DateTime begin;
    /**
     * (Required)
     */
    @SerializedName(END)
    @Expose
    private DateTime end;
    /**
     * (Required)
     */
    @SerializedName(PARTICIPANTS)
    @Expose
    private List<Participant> participants = new ArrayList<Participant>();

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

    public Event withId(UUID id) {
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

    public Event withAuthor(UUID author) {
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

    public Event withTitle(String title) {
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

    public Event withDescription(String description) {
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

    public Event withBegin(DateTime begin) {
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

    public Event withEnd(DateTime end) {
        this.end = end;
        return this;
    }

    /**
     * (Required)
     *
     * @return The participants
     */
    public List<Participant> getParticipants() {
        return participants;
    }

    /**
     * (Required)
     *
     * @param participants The participants
     */
    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public Event withParticipants(List<Participant> participants) {
        this.participants = participants;
        return this;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", author=" + author +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", begin=" + begin +
                ", end=" + end +
                ", participants=" + participants.toString() +
                '}';
    }
}
