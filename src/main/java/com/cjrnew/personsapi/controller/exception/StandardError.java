package com.cjrnew.personsapi.controller.exception;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime timeStamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError() {
    }

    public StandardError(LocalDateTime timeStamp, Integer status, String error, String message, String path) {
        super();
        this.timeStamp = timeStamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
