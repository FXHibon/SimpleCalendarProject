package epsi.fx.com.simplecalendarproject.beans;

/**
 * Created by fx on 28/10/2015.
 */
public class User {

    public static final String USER_TABLE_NAME = "users";
    public static final String USER_TABLE_CREATION = "CREATE TABLE " + USER_TABLE_NAME + " (" +
            "id TEXT, " +
            "name TEXT, " +
            "email TEXT)";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";

    private String id;
    private String name;
    private String email;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
