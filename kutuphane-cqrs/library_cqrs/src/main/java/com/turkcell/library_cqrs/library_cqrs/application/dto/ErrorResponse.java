package com.turkcell.library_cqrs.library_cqrs.application.dto;

/**
 * Standard error response DTO for API error responses
 */
public class ErrorResponse {
    private String errorType;
    private String errorCode;
    private String errorMessage;

    public ErrorResponse() {
    }

    public ErrorResponse(String errorType, String errorCode, String errorMessage) {
        this.errorType = errorType;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "errorType='" + errorType + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
