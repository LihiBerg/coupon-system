package com.lihicouponsystem.threads;

import com.lihicouponsystem.web.ClientSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ExpiredSessionsCleanUp {
    private final Map<String, ClientSession> tokensMap;
    private static final Long HALF_HOUR_IN_MILLIS = TimeUnit.MINUTES.toMillis(30);

    @Autowired
    public ExpiredSessionsCleanUp(Map<String, ClientSession> tokensMap) {
        this.tokensMap = tokensMap;
    }

    @Async
    @Scheduled(fixedDelay = 1000)
    public void run() {
        tokensMap.entrySet().removeIf(stringClientSessionEntry -> (System.currentTimeMillis() -
                stringClientSessionEntry.getValue().getLastAccessedMillis()) > HALF_HOUR_IN_MILLIS);

    }
}
