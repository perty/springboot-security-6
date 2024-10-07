package se.artcomputer.edu.security6.controller;

public record ChangePasswordRequest(String currentPassword, String newPassword) {
}