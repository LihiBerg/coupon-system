package com.lihicouponsystem.web.controller;

import com.lihicouponsystem.config.AppConfiguration;
import com.lihicouponsystem.service.company.CompanyService;
import com.lihicouponsystem.service.customer.CustomerService;
import com.lihicouponsystem.service.exceptions.ExceptionMessage;
import com.lihicouponsystem.service.exceptions.InvalidLoginException;
import com.lihicouponsystem.service.login.LoginService;
import com.lihicouponsystem.web.ClientSession;
import com.lihicouponsystem.web.dto.CompanyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000")
public class LoginController {
    private final AppConfiguration configuration;
    private final LoginService loginService;
    private final Map<String, ClientSession> tokensMap;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentials credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPassword();
        ClientSession session = loginService.createSession(email, password);
        String token = loginService.generateToken();
        tokensMap.put(token, session);
        return ResponseEntity.ok(token);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login/type")
    public ResponseEntity<String> findLoginType(@RequestBody UserCredentials credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPassword();
        String userType = loginService.findLoginType(email, password).toString().toLowerCase();
        return ResponseEntity.ok(userType);
    }

}

