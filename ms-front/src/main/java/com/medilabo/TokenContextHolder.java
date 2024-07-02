package com.medilabo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TokenContextHolder {
    private String token;

}
