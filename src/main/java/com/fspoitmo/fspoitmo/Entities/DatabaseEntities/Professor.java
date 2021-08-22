package com.fspoitmo.fspoitmo.Entities.DatabaseEntities;

import GetGraphQL.SearchableField;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Professor {


    @Id
    @Column(name="id", nullable = false, unique = true)
    @JsonProperty("_id")
    private String id;

    @Column(name="full_name")
    @JsonProperty("name")
    @SearchableField
    private String name;

    @Column(name="professor_university_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String professorUniversityId;


    public Professor() {
    }

    public Professor(String id, String fullName, String professorUniversityId) {
        this.id = id;
        this.name = fullName;
        this.professorUniversityId = professorUniversityId;
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

    public void setName(String fullName) {
        this.name = fullName;
    }

    public String getProfessorUniversityId() {
        return professorUniversityId;
    }

    public void setProfessorUniversityId(String professorUniversityId) {
        this.professorUniversityId = professorUniversityId;
    }
}
