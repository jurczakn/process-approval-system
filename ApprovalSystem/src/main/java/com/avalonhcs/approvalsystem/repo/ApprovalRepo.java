package com.avalonhcs.approvalsystem.repo;

import com.avalonhcs.approvalsystem.model.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalRepo extends JpaRepository<Approval, Integer> {
}
