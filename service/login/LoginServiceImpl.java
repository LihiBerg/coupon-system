package com.lihicouponsystem.service.login;

import com.lihicouponsystem.entities.Company;
import com.lihicouponsystem.entities.Customer;
import com.lihicouponsystem.repositories.CompanyRepository;
import com.lihicouponsystem.repositories.CustomerRepository;
import com.lihicouponsystem.service.exceptions.ExceptionMessage;
import com.lihicouponsystem.service.exceptions.InvalidLoginException;
import com.lihicouponsystem.web.ClientSession;
import com.lihicouponsystem.web.controller.LoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private static final int TOKEN_LENGTH = 8;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final Map<String, ClientSession> tokens;

    @Override
    public ClientSession createSession(String email, String password) {
        UUID clientUuid;
        LoginType type = findLoginType(email, password);
        switch (type) {
            case CUSTOMER -> clientUuid =
                    customerRepository.findByEmailAndPassword(email, password).get().getUuid();
            case COMPANY -> clientUuid =
                    companyRepository.findByEmailAndPassword(email, password).get().getUuid();
            default -> throw new InvalidLoginException(ExceptionMessage.WRONG_TYPE);
            //Just Making Sure You've Noticed That I Actually Did 'isPresent()' Check
            // On 'findLoginType()' Function Below. I'm Not Reckless Or Anything (:
        }
        Iterator<Map.Entry<String, ClientSession>> iter = tokens.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, ClientSession> entry = iter.next();
            if (entry.getValue().getUuid().equals(clientUuid)) {
                iter.remove();
            }
        }
        return ClientSession.of(clientUuid, type);
    }

    public LoginType findLoginType(String email, String password) {
        Optional<Customer> optCustomer = customerRepository.findByEmailAndPassword(email, password);
        Optional<Company> optCompany = companyRepository.findByEmailAndPassword(email, password);
        if (optCustomer.isPresent() && optCompany.isEmpty()) {
            return LoginType.CUSTOMER;
        }
        if (optCompany.isPresent() && optCustomer.isEmpty()) {
            return LoginType.COMPANY;
        } else {
            throw new InvalidLoginException(ExceptionMessage.INCORRECT_EMAIL_OR_PASSWORD);
        }
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString().replaceAll( "-", "")
                .substring(0, TOKEN_LENGTH);
    }
}
