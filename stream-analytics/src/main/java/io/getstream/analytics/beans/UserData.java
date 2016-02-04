package io.getstream.analytics.beans;

public class UserData {

    private String id;
    private String alias;

    public UserData(String id, String alias) {
        this.id = id;
        this.alias = alias;
    }

    public UserData(String id) {
        this.id = id;
    }

    public UserData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
