package com.avalonhcs.approvalsystem.service;

import com.avalonhcs.approvalsystem.model.Approval;
import com.avalonhcs.approvalsystem.model.Request;

public interface RequestService {

    Request saveRequest(Request request);

    Request processApproval(Request request, Approval approval);

    Request getRequestById(Integer id);

}
