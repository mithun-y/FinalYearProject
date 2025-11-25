package com.personal.app.service;

import com.personal.app.model.Token;
import com.personal.app.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public String generateToken(String accountNumber) {
        String tokenValue = UUID.randomUUID().toString();

        Token token = new Token();
        token.setToken(tokenValue);
        token.setAccountNumber(accountNumber);
        token.setExpiryTime(LocalDateTime.now().plusMinutes(2));
        token.setUsed(false);

        tokenRepository.save(token);

        return tokenValue;
    }

    public Boolean validateAndUseToken(String accountNumber) {
        try {
            Optional<Token> optionalToken = tokenRepository.findByAccountNumber(accountNumber);

            if (optionalToken.isEmpty()) return false;

            Token token = optionalToken.orElseThrow(() -> new RuntimeException("Token not found"));

            if (token.getUsed()) return false;
            if (token.getExpiryTime().isBefore(LocalDateTime.now())) return false;

            // mark as used
            token.setUsed(true);
            tokenRepository.save(token);

            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
}