package com.avalonhcs.approvalsystem.enums;

public enum RequestType {
    EXPENSE("Expense"),
    LEAVE("Leave"),
    PROJECT_PROPOSAL("Project Proposal"),
    IT_SERVICE("IT Service");

    private final String displayName;

    RequestType(String displayName) {
        this.displayName = displayName;
    }
}
