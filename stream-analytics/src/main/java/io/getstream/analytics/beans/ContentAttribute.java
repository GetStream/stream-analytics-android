package io.getstream.analytics.beans;

import java.io.Serializable;

public class ContentAttribute implements Serializable {

    private final String id;
    private String label;

    public ContentAttribute(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public ContentAttribute(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
