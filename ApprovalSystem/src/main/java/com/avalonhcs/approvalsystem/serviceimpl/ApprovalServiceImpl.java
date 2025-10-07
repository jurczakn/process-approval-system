package com.avalonhcs.approvalsystem.serviceimpl;

import com.avalonhcs.approvalsystem.model.Approval;
import com.avalonhcs.approvalsystem.repo.ApprovalRepo;
import com.avalonhcs.approvalsystem.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApprovalServiceImpl implements ApprovalService {

    private ApprovalRepo approvalRepo;

    @Autowired
    public ApprovalServiceImpl(ApprovalRepo approvalRepo) {
        this.approvalRepo = approvalRepo;
    }

    @Override
    public Approval saveApproval(Approval approval) {
        return null;
    }

    @Override
    public Approval getApprovalById(Integer id) {
        return null;
    }

    @Override
    public Approval updateApproval(Approval approval) {
        return null;
    }
}
