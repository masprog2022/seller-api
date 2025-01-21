package com.masprogtech.payload;

public class APIResponse {
    public String message;
    private boolean status;

    public APIResponse() {
    }

    public APIResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}
