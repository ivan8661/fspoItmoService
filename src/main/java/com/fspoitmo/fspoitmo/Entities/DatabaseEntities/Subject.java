package com.fspoitmo.fspoitmo.Entities.DatabaseEntities;

import GetGraphQL.SearchableField;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Subject {


    @Id
    @Column(name="id")
    @JsonProperty("_id")
    private String id;

    @Column(name="name")
    @SearchableField
    private String name;

    @Column(name="subject_university_id")
    private String subjectUniversityId;

    @OneToMany
    @JoinColumn(name = "subject_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Lesson> lessons;




    public Subject() {

    }

    public Subject(String id, String name, String subjectUniversityId) {
        this.id = id;
        this.name = name;
        this.subjectUniversityId = subjectUniversityId;
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

    public String getSubjectUniversityId() {
        return subjectUniversityId;
    }

    public void setSubjectUniversityId(String subjectUniversityId) {
        this.subjectUniversityId = subjectUniversityId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }
}
