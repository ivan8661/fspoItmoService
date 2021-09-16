package com.fspoitmo.fspoitmo.Entities.DatabaseEntities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ScheduleUpdate {

    @Id
    @Column(name="university")
    private String name;

    @Column(name="syncTime")
    private long syncTime;

    @Column(name="week")
    private String week;


    public ScheduleUpdate(String name, long syncTime, String week) {
        this.name = name;
        this.syncTime = syncTime;
        this.week = week;
    }

    public ScheduleUpdate() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(long syncTime) {
        this.syncTime = syncTime;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}