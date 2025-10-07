package com.avalonhcs.approvalsystem.serviceimpl;

import com.avalonhcs.approvalsystem.enums.RequestStatus;
import com.avalonhcs.approvalsystem.enums.RequestType;
import com.avalonhcs.approvalsystem.model.Approval;
import com.avalonhcs.approvalsystem.model.Request;
import com.avalonhcs.approvalsystem.repo.RequestRepo;
import com.avalonhcs.approvalsystem.service.RequestService;
import com.avalonhcs.approvalsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {

    private RequestRepo requestRepo;

    private UserService userService;

    @Autowired
    public RequestServiceImpl(RequestRepo requestRepo, UserService userService) {
        this.requestRepo = requestRepo;
        this.userService = userService;
    }

    @Override
    public Request saveRequest(Request request) {
        // Assign the request to the next approver based on request type
        if (RequestType.PROJECT_PROPOSAL.equals(request.getRequestType())) {
            request.setAssignedTo(userService.getUserById(request.getRequester().getId()).getDirectSupervisor());
        } else{
            request.setAssignedTo(userService.getNextApprover(request.getRequestType()));
        }
        // Set initial status based on request type
        request.setRequestStatus(switch (request.getRequestType()) {
            case EXPENSE -> RequestStatus.PENDING_FINANCE_APPROVAL;
            case LEAVE -> RequestStatus.PENDING_HR_APPROVAL;
            case IT_SERVICE -> RequestStatus.PENDING_IT_APPROVAL;
            case PROJECT_PROPOSAL -> RequestStatus.PENDING_MANAGER_APPROVAL;
        });
        return requestRepo.save(request);
    }

    @Override
    public Request processApproval(Request request, Approval approval) {
        return null;
    }

    @Override
    public Request getRequestById(Integer id) {
        return requestRepo.findById(id).orElse(null);
    }
}
