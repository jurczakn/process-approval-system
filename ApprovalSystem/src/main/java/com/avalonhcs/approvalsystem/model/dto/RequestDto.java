package com.avalonhcs.approvalsystem.model.dto;

public record RequestDto(
        Integer id,
        String title, 
        String description,
        String requestType,
        Integer requesterId,
        String requestStatus,
        boolean approved) {
}