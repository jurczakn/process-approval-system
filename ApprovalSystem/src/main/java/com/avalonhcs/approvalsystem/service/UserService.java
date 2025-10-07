package com.avalonhcs.approvalsystem.service;

import com.avalonhcs.approvalsystem.enums.RequestType;
import com.avalonhcs.approvalsystem.model.User;

public interface UserService {
    User getNextApprover(RequestType requestType);
    User getUserById(Integer id);
}
