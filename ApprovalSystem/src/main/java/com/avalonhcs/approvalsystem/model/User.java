package com.avalonhcs.approvalsystem.model;

public record User(String id, String username, String email, String firstName, String lastName, User directSupervisor) {
}
