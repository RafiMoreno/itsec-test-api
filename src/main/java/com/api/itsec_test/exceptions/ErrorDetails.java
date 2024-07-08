package com.api.itsec_test.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
}
