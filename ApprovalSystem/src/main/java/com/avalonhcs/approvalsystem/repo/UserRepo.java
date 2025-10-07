package com.avalonhcs.approvalsystem.repo;

import com.avalonhcs.approvalsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    List<User> findByDepartment(String role);

}
