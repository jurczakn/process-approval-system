package com.avalonhcs.approvalsystem.model;

import com.avalonhcs.approvalsystem.enums.RequestStatus;
import com.avalonhcs.approvalsystem.enums.RequestType;

public record Request(Integer id, String title, String description, RequestType requestType, User requester, RequestStatus requestStatus, boolean approved, User assignedTo, String approvalComments) {
}
