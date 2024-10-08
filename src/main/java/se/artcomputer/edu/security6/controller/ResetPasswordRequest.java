package se.artcomputer.edu.security6.controller;

public record ResetPasswordRequest(String email, String newPassword) {}