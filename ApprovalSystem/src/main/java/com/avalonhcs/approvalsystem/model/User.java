package com.avalonhcs.approvalsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String department;

    @ManyToOne(fetch = FetchType.LAZY)
    private User directSupervisor;
}
