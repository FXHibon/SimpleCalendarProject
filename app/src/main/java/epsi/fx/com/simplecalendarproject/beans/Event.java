package epsi.fx.com.simplecalendarproject.beans;

/**
 * Created by fx on 16/10/2015.
 */
public class Event {

    private String title;
    private String desc;
    private String author;
    private String dateBegin;
    private String dateEnd;

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

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", author='" + author + '\'' +
                ", dateBegin='" + dateBegin + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                '}';
    }
}
