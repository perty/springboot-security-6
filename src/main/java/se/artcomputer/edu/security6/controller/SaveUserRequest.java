package se.artcomputer.edu.security6.controller;

public record SaveUserRequest(String email, String roles, String password) {
}
