package com.avalonhcs.approvalsystem.serviceimpl;

import com.avalonhcs.approvalsystem.enums.RequestStatus;
import com.avalonhcs.approvalsystem.enums.RequestType;
import com.avalonhcs.approvalsystem.model.Approval;
import com.avalonhcs.approvalsystem.model.Request;
import com.avalonhcs.approvalsystem.model.User;
import com.avalonhcs.approvalsystem.repo.RequestRepo;
import com.avalonhcs.approvalsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RequestServiceImplTest {

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private RequestRepo requestRepo;

    @Autowired
    private RequestServiceImpl requestServiceImpl;

    private Request testRequest;

    @BeforeEach
    void setUp() {
        testRequest = new Request();
        testRequest.setId(1);
        testRequest.setDescription("Test Request");
    }

    @Test
    void whenGetRequestById_thenReturnRequest() {

        when (requestRepo.findById(1)).thenReturn(Optional.of(testRequest));

        Request foundRequest = requestServiceImpl.getRequestById(1);

        assertEquals(testRequest, foundRequest, "The found request should match the test request");

    }

    @Test
    void whenGetRequestById_thenReturnNullForNonExistentId() {
        //arrange
        when (requestRepo.findById(2)).thenReturn(Optional.empty());

        //act
        Request foundRequest = requestServiceImpl.getRequestById(2);

        //assert
        assertEquals(null, foundRequest, "The found request should be null for non-existent ID");

    }

    @Test
    void whenSaveRequest_thenReturnSavedRequest() {
        //arrange
        when(requestRepo.save(testRequest)).thenReturn(testRequest);

        //act
        Request savedRequest = requestServiceImpl.saveRequest(testRequest);

        //assert
        assertEquals(testRequest, savedRequest, "The saved request should match the test request");
    }

    @Test
    void whenSaveExpenseRequest_thenSetStatusAndAssignedTo() {
        //arrange
        Request expenseRequest = new Request();
        expenseRequest.setRequestType(RequestType.EXPENSE);
        expenseRequest.setDescription("Expense Request");

        when(requestRepo.save(any())).thenAnswer(invocation -> {
            Request req = invocation.getArgument(0);
            req.setId(2); // Simulate DB assigning an ID
            return req;
        });

        User financeApprover = new User();
        financeApprover.setId(1);
        financeApprover.setUsername("finance_approver");
        financeApprover.setDepartment("Finance");

        when(userService.getNextApprover(RequestType.EXPENSE)).thenReturn(financeApprover);

        //act
        Request savedRequest = requestServiceImpl.saveRequest(expenseRequest);

        //assert
        assertEquals(RequestStatus.PENDING_FINANCE_APPROVAL, savedRequest.getRequestStatus(), "Status should be set to PENDING_FINANCE_APPROVAL");
        assertEquals(financeApprover, savedRequest.getAssignedTo(), "AssignedTo should be set to the finance approver");
    }

    @Test
    void whenSaveLeaveRequest_thenSetStatusAndAssignedTo() {
        //arrange
        Request leaveRequest = new Request();
        leaveRequest.setRequestType(RequestType.LEAVE);
        leaveRequest.setDescription("Leave Request");

        when(requestRepo.save(any())).thenAnswer(invocation -> {
            Request req = invocation.getArgument(0);
            req.setId(2); // Simulate DB assigning an ID
            return req;
        });

        User hrApprover = new User();
        hrApprover.setId(1);
        hrApprover.setUsername("hr_approver");
        hrApprover.setDepartment("Human Resources");

        when(userService.getNextApprover(RequestType.LEAVE)).thenReturn(hrApprover);

        //act
        Request savedRequest = requestServiceImpl.saveRequest(leaveRequest);

        //assert
        assertEquals(RequestStatus.PENDING_HR_APPROVAL, savedRequest.getRequestStatus(), "Status should be set to PENDING_HR_APPROVAL");
        assertEquals(hrApprover, savedRequest.getAssignedTo(), "AssignedTo should be set to the hr approver");
    }

    @Test
    void whenSaveITRequest_thenSetStatusAndAssignedTo() {
        //arrange
        Request itRequest = new Request();
        itRequest.setRequestType(RequestType.IT_SERVICE);
        itRequest.setDescription("IT Request");

        when(requestRepo.save(any())).thenAnswer(invocation -> {
            Request req = invocation.getArgument(0);
            req.setId(2); // Simulate DB assigning an ID
            return req;
        });

        User itApprover = new User();
        itApprover.setId(1);
        itApprover.setUsername("it_approver");
        itApprover.setDepartment("Information Technology");

        when(userService.getNextApprover(RequestType.IT_SERVICE)).thenReturn(itApprover);

        //act
        Request savedRequest = requestServiceImpl.saveRequest(itRequest);

        //assert
        assertEquals(RequestStatus.PENDING_IT_APPROVAL, savedRequest.getRequestStatus(), "Status should be set to PENDING_IT_APPROVAL");
        assertEquals(itApprover, savedRequest.getAssignedTo(), "AssignedTo should be set to the it approver");
    }

    @Test
    void whenSaveProposalRequest_thenSetStatusAndAssignedTo() {
        //arrange
        Request proposalRequest = new Request();
        proposalRequest.setRequestType(RequestType.PROJECT_PROPOSAL);
        proposalRequest.setDescription("Proposal Request");

        when(requestRepo.save(any())).thenAnswer(invocation -> {
            Request req = invocation.getArgument(0);
            req.setId(2); // Simulate DB assigning an ID
            return req;
        });

        User managerApprover = new User();
        managerApprover.setId(1);
        managerApprover.setUsername("proposal_approver");
        managerApprover.setDepartment("Sales");

        User requester = new User();
        requester.setId(2);
        requester.setUsername("requester_user");
        requester.setDepartment("Sales");
        proposalRequest.setRequester(requester);
        requester.setDirectSupervisor(managerApprover);

        when(userService.getNextApprover(RequestType.PROJECT_PROPOSAL)).thenThrow(new IllegalArgumentException("Request type not supported: PROJECT_PROPOSAL"));
        when(userService.getUserById(2)).thenReturn(requester);

        //act
        Request savedRequest = requestServiceImpl.saveRequest(proposalRequest);

        //assert
        assertEquals(RequestStatus.PENDING_MANAGER_APPROVAL, savedRequest.getRequestStatus(), "Status should be set to PENDING_MANAGER_APPROVAL");
        assertEquals(managerApprover, savedRequest.getAssignedTo(), "AssignedTo should be set to the manager approver");
    }

    /*Process a new Approval:
    When a user Saves an Approval, then process the approval appropriately, save the updated request, and then save the approval.
    If it is ambiguous, or you are unsure how to arrange your test, or what to assert, go back to requirements gathering.  It does not meet our Definition of Done.
    	- Details
		○ Always check that the approver matches the assignedTo in the Request
			§ Should throw exception in this case
			§ InvalidArgument Exception
		○ If request type is expense
			§ If status is pending fianance approval
				□ Change status to manager approval
				□ Set approver to direct supervisor of requester
			§ If status is manager approval
				□ Change status to approved
				□ Set approver to null
				□ Set approved to true
			§ If status is anything else
				□ Throw exception InvalidRequestState exception
		○ If request type is leave
			§ If status is pending hr approval
				□ Change status to manager approval
				□ Set approver to direct supervisor of requester
			§ If status is manager approval
				□ Change status to approved
				□ Set approver to null
				□ Set approver to null
				□ Set approved to true
			§ If status is anything else
				□ Throw exception invalidRequestState exception
		○ If request type is project proposal
			§ If status is pending manager approval
				□ Set approver to direct supervisor of approver
					® If approver does not have a direct supervisor
						◊ Set status to approved
						◊ Set approved to null
						◊ Set approved to true
				□ Set status to pending department head approval
			§ If status is pending dept head approval
				□ Set status to approved
				□ Set approved to true
Set approver to null

     */
    @Test
    void whenExpenseRequestAndStatusFinanceApproval_ThenSetStatusAndApprover() {

        //arrange
        Request approvalRequest = new Request();
        approvalRequest.setRequestType(RequestType.EXPENSE);
        approvalRequest.setRequestStatus(RequestStatus.PENDING_FINANCE_APPROVAL);

        Approval approval = new Approval();

        User userRequester = new User();
        userRequester.setId(2);
        userRequester.setUsername("requester_user");
        userRequester.setDepartment("Sales");
        approvalRequest.setRequester(userRequester);

        User managerApprover = new User();
        managerApprover.setId(1);
        managerApprover.setUsername("manager_approver");
        managerApprover.setDepartment("Sales");
        userRequester.setDirectSupervisor(managerApprover);

        when(requestRepo.save(any())).thenAnswer(i -> i.getArgument(0));


        //act
        Request processedRequest = requestServiceImpl.processApproval(approvalRequest, approval);

        //assert
        assertEquals(RequestStatus.PENDING_MANAGER_APPROVAL, processedRequest.getRequestStatus(), "The request status should be updated appropriately");
        assertEquals(managerApprover, processedRequest.getAssignedTo(), "The assignedTo should be updated to the manager approver");
        verify(requestRepo).save(any());


    }

    @Test
    void whenExpenseRequestAndStatusManagerApproval_ThenSetStatusAndApprover() {

        //arrange
        Request approvalRequest = new Request();
        approvalRequest.setRequestType(RequestType.EXPENSE);
        approvalRequest.setRequestStatus(RequestStatus.PENDING_MANAGER_APPROVAL);

        Approval approval = new Approval();

        User userRequester = new User();
        userRequester.setId(2);
        userRequester.setUsername("requester_user");
        userRequester.setDepartment("Sales");
        approvalRequest.setRequester(userRequester);

        User managerApprover = new User();
        managerApprover.setId(1);
        managerApprover.setUsername("manager_approver");
        managerApprover.setDepartment("Sales");
        userRequester.setDirectSupervisor(managerApprover);

        when(requestRepo.save(any())).thenAnswer(i -> i.getArgument(0));


        //act
        Request processedRequest = requestServiceImpl.processApproval(approvalRequest, approval);

        //assert
        assertEquals(RequestStatus.APPROVED, processedRequest.getRequestStatus(), "The request status should be updated appropriately");
        assertNull(processedRequest.getAssignedTo(), "The assignedTo should be updated to null");
        assertTrue(processedRequest.isApproved(), "The approved flag should be set to true");
        verify(requestRepo).save(any());


    }


}
