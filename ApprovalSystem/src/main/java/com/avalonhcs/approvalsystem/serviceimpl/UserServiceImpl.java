package com.avalonhcs.approvalsystem.serviceimpl;

import com.avalonhcs.approvalsystem.enums.RequestType;
import com.avalonhcs.approvalsystem.model.User;
import com.avalonhcs.approvalsystem.repo.UserRepo;
import com.avalonhcs.approvalsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    private static int  roundRobinFinanceIndex = 0;

    private static int roundRobinHRIndex = 0;

    private static int roundRobinITIndex = 0;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User getNextApprover(RequestType requestType) {

        return switch(requestType) {
            case RequestType.EXPENSE -> userRepo.findByDepartment("Finance").get(UserServiceImpl.roundRobinFinanceIndex++ % userRepo.findByDepartment("Finance").size());
            case RequestType.LEAVE -> userRepo.findByDepartment("Human Resources").get(UserServiceImpl.roundRobinHRIndex++ % userRepo.findByDepartment("Human Resources").size());
            case RequestType.IT_SERVICE -> userRepo.findByDepartment("Information Technology").get(UserServiceImpl.roundRobinITIndex++ % userRepo.findByDepartment("Information Technology").size());
            case RequestType.PROJECT_PROPOSAL -> throw new IllegalArgumentException("Request type not supported: " + requestType);
        };

    }

    @Override
    public User getUserById(Integer id) {
        return userRepo.findById(id).orElse(null);
    }
}
