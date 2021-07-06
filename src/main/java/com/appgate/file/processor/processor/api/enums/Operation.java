package com.appgate.file.processor.processor.api.enums;

public enum Operation {

    SUCCESSFUL("Successful"),
    WRONG_FILE_TYPE("Wrong file type"),
    LOADED_WITH_ERROR("Loaded with errors"),
    INVALID_IP("Invalid ip address"),
    NOT_FOUND("Not found"),
    FAILED("Failed: ");

    private String description;

    public String getDescription() {
        return description;
    }

    Operation(String description) {
        this.description = description;
    }
}
