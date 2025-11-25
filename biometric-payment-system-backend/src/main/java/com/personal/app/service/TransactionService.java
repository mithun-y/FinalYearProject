package com.personal.app.service;

import com.personal.app.dto.Transaction;
import com.personal.app.model.User;
import com.personal.app.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class TransactionService {


    @Autowired
    private AuthService authService;

    @Autowired
    private TransactionMailService transactionMailService;

    @Autowired
    private BankService bankService;

    @Autowired
    private UserRepository userRepository;

    public String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
    public String generateDeviceId() {
        return "DEV-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    @Transactional
    public Transaction makePayment(String pin, MultipartFile fingerprintFile, Double amount,String location) throws Exception {

        //authenticating user
        String accountNum=authService.authenticate(pin,fingerprintFile);

        System.out.println(accountNum);

        User user=userRepository.findByAccountNumber(accountNum);
        System.out.println(user.getEmail());
        String email=user.getEmail();
        String deviceId=generateDeviceId();
        String trancationId=generateTransactionId();

        if (accountNum == null) {
            Transaction txn=new Transaction(trancationId,amount,accountNum,"FAILED",deviceId,location);
            transactionMailService.sendFingerprintDebitEmail(email,txn);
            return txn;
        }

        Boolean bankStatus=bankService.makePayment(accountNum,amount);



        System.out.println(bankStatus);


        try {
            if(bankStatus){
                Transaction txn=new Transaction(trancationId,amount,accountNum,"SUCCESS",deviceId,location);
                transactionMailService.sendFingerprintDebitEmail(email,txn);
                return txn;
            }
            Transaction txn=new Transaction(trancationId,amount,accountNum,"FAILED",deviceId,location);
            transactionMailService.sendFingerprintDebitEmail(email,txn);
            return txn;
        } catch (MessagingException e) {
            Transaction txn=new Transaction(trancationId,amount,accountNum,"FAILED",deviceId,location);
            transactionMailService.sendFingerprintDebitEmail(email,txn);
            return txn;
        }
    }
}