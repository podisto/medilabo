package com.medilabo;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Data
@Component
//@RequestScope
public class TokenContextHolder {
    private String token;

}
