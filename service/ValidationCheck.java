package com.lihicouponsystem.service;

import com.lihicouponsystem.service.exceptions.ExceptionMessage;
import com.lihicouponsystem.service.exceptions.InvalidAmountException;
import com.lihicouponsystem.service.exceptions.UnauthorizedOperationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

//todo added
@Service
@RequiredArgsConstructor
public class ValidationCheck {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Integer PASSWORD_MIN_LENGTH = 8;


    public void emailValidation(String email) {
        if (email == null) {
            throw new InvalidAmountException(ExceptionMessage.INVALID_EMAIL_OR_PASSWORD_UPDATE);
        }
        if (!VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches()) {
            throw new UnauthorizedOperationException(ExceptionMessage.INVALID_EMAIL);
        }
    }

    public void passwordValidation(String password) {
        if (password == null) {
            throw new InvalidAmountException(ExceptionMessage.INVALID_EMAIL_OR_PASSWORD_UPDATE);
        }

        if (!passwordRegexCheck(password)) {
            throw new UnauthorizedOperationException(ExceptionMessage.INVALID_PASSWORD);
        }
    }

    static boolean passwordRegexCheck(String password) {
        boolean validLength = password.length() >= PASSWORD_MIN_LENGTH;
        boolean containsOneNumber = password.matches(".*[0-9].*");
        boolean containsOneLetter = password.matches(".*[a-z].*");
        boolean containsOneCapitalLetter = password.matches(".*[A-Z].*");

        return validLength && containsOneNumber && containsOneLetter && containsOneCapitalLetter;

    }
}
