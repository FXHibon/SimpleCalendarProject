package epsi.fx.com.simplecalendarproject.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Created by fx on 28/10/2015.
 */
public class User {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    @SerializedName(ID)
    @Expose
    private UUID id;
    /**
     * (Required)
     */
    @SerializedName(NAME)
    @Expose
    private String name;
    @SerializedName(DESCRIPTION)
    @Expose
    private String description;
    /**
     * (Required)
     */
    @SerializedName(EMAIL)
    @Expose
    private String email;
    /**
     * (Required)
     */
    @SerializedName(PASSWORD)
    @Expose
    private String password;

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

    public User withId(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * (Required)
     *
     * @retur n The name
     */
    public String getName() {
        return name;
    }

    /**
     * (Required)
     *
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public User withName(String name) {
        this.name = name;
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

    public User withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * (Required)
     *
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * (Required)
     *
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public User withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * (Required)
     *
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * (Required)
     *
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public User withPassword(String password) {
        this.password = password;
        return this;
    }
}
