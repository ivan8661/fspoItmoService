package com.fspoitmo.fspoitmo.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String avatar;
    @JsonProperty
    private String externalId;
    @JsonProperty
    private String cookie;
    @JsonProperty
    private String groupId;

    public User(String firstName, String lastName, String avatar, String externalId, String cookie, String groupId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.externalId = externalId;
        this.cookie = cookie;
        this.groupId = groupId;
    }
}
