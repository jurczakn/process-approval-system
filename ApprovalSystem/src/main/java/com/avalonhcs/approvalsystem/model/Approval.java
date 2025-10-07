package com.avalonhcs.approvalsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "approvals")
public class Approval {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Request request;

    @ManyToOne(fetch = FetchType.LAZY)
    private User approver;

    private String comments;

    private LocalDate approvalDate;
}
