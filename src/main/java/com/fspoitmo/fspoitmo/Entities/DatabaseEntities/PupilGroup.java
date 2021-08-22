package com.fspoitmo.fspoitmo.Entities.DatabaseEntities;


import GetGraphQL.SearchableField;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class PupilGroup {


    @Id
    @JsonProperty("_id")
    @Column(name="id")
    private String id;

    @Column(name="name")
    @SearchableField
    private String name;

    @Column(name="group_university_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String universityGroupId;

    @ManyToMany
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Lesson> lessons;

    public PupilGroup() {
    }


    public PupilGroup(String id, String name, String universityGroupId) {
        this.id = id;
        this.name = name;
        this.universityGroupId = universityGroupId;
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

    public String getUniversityGroupId() {
        return universityGroupId;
    }

    public void setUniversityGroupId(String universityGroupId) {
        this.universityGroupId = universityGroupId;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }


}
