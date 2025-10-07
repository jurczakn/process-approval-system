package com.avalonhcs.approvalsystem.repo;

import com.avalonhcs.approvalsystem.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepo extends JpaRepository<Request, Integer> {
}
