package com.bytetrio.financial.model;

public class SubscriptionDTO {
    private String id;
    private String title;

    public String getId() {return id;}

    public SubscriptionDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {return title;}

    public SubscriptionDTO setTitle(String title) {
        this.title = title;
        return this;
    }
}
