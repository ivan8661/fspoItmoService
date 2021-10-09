package com.fspoitmo.fspoitmo.Entities;

import GetGraphQL.SearchableField;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ScheduleUser {

    @JsonProperty("_id")
    private String id;

    @SearchableField
    @JsonProperty("name")
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
