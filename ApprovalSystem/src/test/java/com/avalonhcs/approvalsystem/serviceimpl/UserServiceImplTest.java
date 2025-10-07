package com.avalonhcs.approvalsystem.serviceimpl;

import com.avalonhcs.approvalsystem.enums.RequestType;
import com.avalonhcs.approvalsystem.model.User;
import com.avalonhcs.approvalsystem.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @MockitoBean
    private UserRepo userRepo;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void whenGetNextApproverExpense_thenReturnFinanceUser() {
        //arrange
        User financeUser1 = new User(1, "finUser1", "finUser1@company.com", "Fin", "Userone", "Finance", null);
        User financeUser2 = new User(2, "finUser2", "finUser2@company.com", "Finny", "Usertwo", "Finance", financeUser1);
        User financeUser3 = new User(3, "finUser3", "finUser3@company.com", "Phineous", "Userthree", "Finance", financeUser1);
        when(userRepo.findByDepartment("Finance")).thenReturn(List.of(financeUser1, financeUser2, financeUser3));
        //act
        User nextApprover = userServiceImpl.getNextApprover(RequestType.EXPENSE);
        User nextNextApprover = userServiceImpl.getNextApprover(RequestType.EXPENSE);
        //assert
        assertEquals(financeUser1, nextApprover, "The next approver should be the first finance user");
        assertEquals(financeUser2, nextNextApprover, "The next next approver should be the second finance user");
    }

    @Test
    void whenGetNextApproverLeave_thenReturnHRUser() {
        //arrange
        User hrUser1 = new User(1, "hrUser1", "hrUser1@company.com", "Human", "Userone", "Human Resources", null);
        User hrUser2 = new User(2, "hrUser2", "hrUser2@company.com", "Resource", "Usertwo", "Human Resources", hrUser1);
        User hrUser3 = new User(3, "hrUser3", "hrUser3@company.com", "Henrietta", "Userthree", "Human Resources", hrUser1);
        when(userRepo.findByDepartment("Human Resources")).thenReturn(List.of(hrUser1, hrUser2, hrUser3));
        //act
        User nextApprover = userServiceImpl.getNextApprover(RequestType.LEAVE);
        User nextNextApprover = userServiceImpl.getNextApprover(RequestType.LEAVE);
        //assert
        assertEquals(hrUser1, nextApprover, "The next approver should be the first finance user");
        assertEquals(hrUser2, nextNextApprover, "The next next approver should be the second finance user");
    }

    @Test
    void whenGetNextApproverIT_thenReturnITUser() {
        //arrange
        User itUser1 = new User(1, "itUser1", "itUser1@company.com", "Info", "Userone", "Information Technology", null);
        User itUser2 = new User(2, "itUser2", "itUser2@company.com", "Tech", "Usertwo", "Information Technology", itUser1);
        User itUser3 = new User(3, "itUser3", "itUser3@company.com", "Itra", "Userthree", "Information Technology", itUser1);
        when(userRepo.findByDepartment("Information Technology")).thenReturn(List.of(itUser1, itUser2, itUser3));
        //act
        User nextApprover = userServiceImpl.getNextApprover(RequestType.IT_SERVICE);
        User nextNextApprover = userServiceImpl.getNextApprover(RequestType.IT_SERVICE);
        //assert
        assertEquals(itUser1, nextApprover, "The next approver should be the first finance user");
        assertEquals(itUser2, nextNextApprover, "The next next approver should be the second finance user");
    }

    @Test
    void whenGetNextApproverInvalidType_thenThrowException() {
        try {
            userServiceImpl.getNextApprover(RequestType.PROJECT_PROPOSAL);
        } catch (IllegalArgumentException e) {
            assertEquals("Request type not supported: PROJECT_PROPOSAL", e.getMessage(), "The exception message should indicate an unexpected value");
        }
    }

}
