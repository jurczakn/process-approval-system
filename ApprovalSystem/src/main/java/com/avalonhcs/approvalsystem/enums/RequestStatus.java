package com.avalonhcs.approvalsystem.enums;

public enum RequestStatus {
    PENDING_MANAGER_APPROVAL("Pending Manager Approval"),
    PENDING_DEPT_HEAD_APPROVAL("Pending Department Head Approval"),
    PENDING_FINANCE_APPROVAL("Pending Finance Approval"),
    PENDING_IT_APPROVAL("Pending IT Approval"),
    PENDING_HR_APPROVAL("Pending HR Approval"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    CANCELLED("Cancelled"),
    IN_REVIEW("In Review");

    private final String displayName;

    RequestStatus(String displayName) {
        this.displayName = displayName;
    }

}
