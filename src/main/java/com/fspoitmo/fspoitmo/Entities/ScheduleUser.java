package com.fspoitmo.fspoitmo.Entities;

import GetGraphQL.SearchableField;

public class ScheduleUser {

    private String id;

    @SearchableField
    private String name;

    public ScheduleUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ScheduleUser() {
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
}
