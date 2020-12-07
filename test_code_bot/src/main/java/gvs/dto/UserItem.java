package gvs.dto;

import java.util.Date;

public class UserItem {
    private String id;
    private String name;
    private String username;
    private Date date;

    public UserItem() {
    }

    public UserItem(String id, String name, String username, Date date) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.date = date;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
