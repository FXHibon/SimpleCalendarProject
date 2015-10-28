package epsi.fx.com.simplecalendarproject.beans;

import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

/**
 * Created by fx on 16/10/2015.
 */
public class Event {

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String AUTHOR = "author";
    public static final String DATE_BEGIN = "date_begin";
    public static final String DATE_END = "date_end";

    public static final String EVENT_TABLE_NAME = "events";
    public static final String EVENT_TABLE_CREATION = "CREATE TABLE " + EVENT_TABLE_NAME + " (" +
            "id TEXT, " +
            "title TEXT, " +
            "author TEXT, " +
            "description TEXT, " +
            "date_begin TEXT, " +
            "date_end TEXT, " +
            "FOREIGN KEY(author) REFERENCES " + User.USER_TABLE_NAME + "(id))";

    public static final String PARTICIPATION_TABLE_NAME = "participation";
    public static final String PARTICIPATION_TABLE_CREATION = "CREATE TABLE " + PARTICIPATION_TABLE_NAME + " (" +
            "id_event TEXT, " +
            "id_user TEXT, " +
            "status TEXT, " +
            "FOREIGN KEY(id_event) REFERENCES " + EVENT_TABLE_NAME + "(id), " +
            "FOREIGN KEY(id_user) REFERENCES " + User.USER_TABLE_NAME + "(id))";

    private String id;
    private String title;
    private String desc;
    private String author;
    private DateTime dateBegin;
    private DateTime dateEnd;
    private List<Map<String, Status>> participants;

    public Event() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public DateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(DateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public DateTime getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(DateTime dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Map<String, Status>> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Map<String, Status>> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "Event{" +
                "participants=" + participants +
                ", dateEnd=" + dateEnd +
                ", dateBegin=" + dateBegin +
                ", author='" + author + '\'' +
                ", desc='" + desc + '\'' +
                ", title='" + title + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
