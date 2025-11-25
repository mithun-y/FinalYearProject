package com.personal.app.service;

import com.personal.app.model.BankAccount;
import com.personal.app.model.User;
import com.personal.app.repository.BankAccountRepository;
import com.personal.app.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class BankService {

    @Autowired
    private BankAccountRepository bankAccountRepository;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private TransactionMailService transactionMailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditMailService emailService;




    public BankAccount createBankAccount(String accountNumber, Double initialBalance) {
        BankAccount account = new BankAccount();
        account.setAccountNumber(accountNumber);
        account.setBalance(initialBalance != null ? initialBalance : 0.0);
        return bankAccountRepository.save(account);
    }

    public Optional<BankAccount> getAccount(String accountNumber) {
        return bankAccountRepository.findById(accountNumber);
    }



    public ResponseEntity<?> add(String phNumber, Double money) {
        User user= userRepository.findByPhNumber(phNumber).orElse(null);
        if(user==null) ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Account not found");



        return bankAccountRepository.findByAccountNumber(user.getAccountNumber())
                .map(account -> {
                    // Add balance
                    account.setBalance(account.getBalance() + money);
                    bankAccountRepository.save(account);
                    try {
                        emailService.sendBalanceUpdateMail(
                                user.getEmail(),
                                money,
                                account.getBalance()
                        );
                    } catch (MessagingException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    return ResponseEntity.ok().build(); // 200 OK
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Account not found")); // 404 if account not found
    }

    public Boolean makePayment(String accountNumber,Double amount){
        try {
            if(accountNumber==null)  new RuntimeException("Account not found");

            Boolean tokenStatus=tokenService.validateAndUseToken(accountNumber);

            if (!tokenStatus) {
                throw new RuntimeException("Invalid or already used token");
            }

            BankAccount matchedUser = bankAccountRepository.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new RuntimeException("Account not found"));

            // Check balance
            if (matchedUser.getBalance() < amount) {
                throw new RuntimeException("Insufficient balance");
            }

            // Deduct money
            matchedUser.setBalance(matchedUser.getBalance() - amount);
            bankAccountRepository.save(matchedUser);


            //mail service


            return true;
        } catch (RuntimeException e) {
//            throw new RuntimeException(e);
            return false;
        }

    }
}