package com.turkcell.library_cqrs.library_cqrs.application.dto;

import java.util.List;

/**
 * Validation error response DTO for field-level validation errors
 */
public class ValidationErrorResponse {
    private String field;
    private List<String> messages;

    public ValidationErrorResponse() {
    }

    public ValidationErrorResponse(String field, List<String> messages) {
        this.field = field;
        this.messages = messages;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "ValidationErrorResponse{" +
                "field='" + field + '\'' +
                ", messages=" + messages +
                '}';
    }
}
