package com.avalonhcs.approvalsystem.service;

import com.avalonhcs.approvalsystem.model.Approval;

public interface ApprovalService {

    Approval saveApproval(Approval approval);

    Approval getApprovalById(Integer id);

    Approval updateApproval(Approval approval);

}
