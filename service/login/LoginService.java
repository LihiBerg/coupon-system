package com.lihicouponsystem.service.login;

import com.lihicouponsystem.web.ClientSession;
import com.lihicouponsystem.web.controller.LoginType;

public interface LoginService {
    ClientSession createSession(String email, String password);

    String generateToken();

    LoginType findLoginType(String email, String password);
}
