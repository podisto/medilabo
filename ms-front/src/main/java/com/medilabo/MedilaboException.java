package com.medilabo;

import lombok.Data;

@Data
public class MedilaboException extends RuntimeException {

    public MedilaboException(String message) {
        super(message);
    }
}
