package com.avalonhcs.approvalsystem.model;

import java.time.LocalDate;

public record Approval(Request request, User approver, String comments, LocalDate approvalDate) {
}
