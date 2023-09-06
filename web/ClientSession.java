package com.lihicouponsystem.web;

import java.util.UUID;

import com.lihicouponsystem.web.controller.LoginType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientSession {
    private final UUID uuid;
    private Long lastAccessedMillis;
    private LoginType type;

    public static ClientSession of(UUID uuid, LoginType type) {
        return new ClientSession(uuid, System.currentTimeMillis(), type);
    }

    public void updateLastAccessedMillis() {
        lastAccessedMillis = System.currentTimeMillis();
    }

}
