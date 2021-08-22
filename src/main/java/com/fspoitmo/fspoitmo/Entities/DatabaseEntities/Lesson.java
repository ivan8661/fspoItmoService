package com.fspoitmo.fspoitmo.Entities.DatabaseEntities;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Lesson {

    @Id
    @Column(name="lesson_id")
    @JsonProperty("_id")
    private String id;

    @Column(name="start_name")
    private String startTime;

    @Column(name="end_time")
    private String endTime;

    @Column(name="number_lesson")
    @JsonProperty("lessonNum")
    private int lessonNum;

    @Column(name="day")
    private String day;

    @Column(name="room")
    @JsonProperty("rooms")
    private String rooms;

    @Column(name="type")
    private String type;

    @Column(name = "week")
    private String week;


    @ManyToMany
    private Set<Professor> professors;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToMany
    private Set<PupilGroup> groups;


    public Lesson() {
    }


    public Lesson(String id, String startTime, String endTime, int numLesson, String day, String rooms, String type, Subject subject, Set<Professor> professors, Set<PupilGroup> pupilGroups, String week) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lessonNum = numLesson;
        this.day = day;
        this.rooms = rooms;
        this.type = type;
        this.professors = professors;
        this.subject = subject;
        this.week = week;
        this.groups = pupilGroups;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getLessonNum() {
        return lessonNum;
    }

    public void setLessonNum(int numLesson) {
        this.lessonNum = numLesson;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String room) {
        this.rooms = room;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Set<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(Set<Professor> professors) {
        this.professors = professors;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Set<PupilGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<PupilGroup> groups) {
        this.groups = groups;
    }
}
