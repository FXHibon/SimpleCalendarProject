package epsi.fx.com.simplecalendarproject.ws.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class UserWs {

    @SerializedName("id")
    @Expose
    private UUID id;
    /**
     * (Required)
     */
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    /**
     * (Required)
     */
    @SerializedName("email")
    @Expose
    private String email;
    /**
     * (Required)
     */
    @SerializedName("password")
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

    public UserWs withId(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * (Required)
     *
     * @return The name
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

    public UserWs withName(String name) {
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

    public UserWs withDescription(String description) {
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

    public UserWs withEmail(String email) {
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

    public UserWs withPassword(String password) {
        this.password = password;
        return this;
    }

}