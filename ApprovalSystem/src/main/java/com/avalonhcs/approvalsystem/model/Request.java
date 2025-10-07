package com.avalonhcs.approvalsystem.model;

import com.avalonhcs.approvalsystem.enums.RequestStatus;
import com.avalonhcs.approvalsystem.enums.RequestType;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    private RequestType requestType;

    @ManyToOne(fetch = FetchType.LAZY)
    private User requester;

    private RequestStatus requestStatus;

    private boolean approved;

    @ManyToOne(fetch = FetchType.LAZY)
    private User assignedTo;
}
