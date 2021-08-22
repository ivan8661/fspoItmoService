package com.fspoitmo.fspoitmo.Entities.DatabaseEntities;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Building {

    @Id
    @Column(name="build_id")
    @JsonProperty("_id")
    private String id;


    @Column(name="build_name")
    private String buildName;


    public Building() {
    }

    public Building(String id, String buildName) {
        this.id = id;
        this.buildName = buildName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }
}
