package com.medilabo.exception;

import lombok.Data;

@Data
public class MedilaboException extends RuntimeException {

    public MedilaboException(String message) {
        super(message);
    }
}
