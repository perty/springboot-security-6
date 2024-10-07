package se.artcomputer.edu.security6.controller;

import se.artcomputer.edu.security6.model.OurUser;

import java.util.List;

public record AllUsersResponse(List<OurUser> users) {
    public static AllUsersResponse from(List<OurUser> users) {
        return new AllUsersResponse(users);
    }
}
