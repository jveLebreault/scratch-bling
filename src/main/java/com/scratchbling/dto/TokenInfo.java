package com.scratchbling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Data
@AllArgsConstructor
public class TokenInfo {
    private String user;
    private String token;
    private Collection<? extends GrantedAuthority> permissions;
    private Long expiresIn;
}
