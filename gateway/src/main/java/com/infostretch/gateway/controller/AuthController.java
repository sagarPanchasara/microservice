package com.infostretch.gateway.controller;

import com.infostretch.gateway.security.JwtHelper;
import com.infostretch.gateway.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtHelper jwtHelper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        if (username.equals(password)) {
            Map<String, String> map = new HashMap<>();
            map.put("token", jwtHelper.generateToken(
                    new UserPrincipal(1, Arrays.asList(new SimpleGrantedAuthority("test")))
            ));
            return ResponseEntity.ok(map);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
