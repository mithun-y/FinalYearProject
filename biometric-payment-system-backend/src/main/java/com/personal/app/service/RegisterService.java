package com.personal.app.service;

import com.personal.app.model.BankAccount;
import com.personal.app.model.User;
import com.personal.app.repository.UserRepository;
import com.personal.app.utils.EncryptionUtil;
import com.personal.app.dto.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private EncryptionUtil aesUtil;

    @Autowired
    private WelcomeMailService welcomeMailService;

    @Autowired
    private BankService bankService;

    @Autowired
    private KeyProvider keyProvider;

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder("ACC");
        for (int i = 0; i < 9; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public RegisterResponse registerUser(String fullName, String email, byte[] fingerprintImage, String pin, String phNumber, Double initialBalance) throws Exception {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered!");
        }
        if (userRepository.findByPhNumber(phNumber).orElse(null)!=null) {
            throw new RuntimeException("phone number already registered!");
        }


        try {
            byte[] encryptedFingerprint = aesUtil.encrypt(fingerprintImage, keyProvider.getMasterKey());

            String accountNumber=generateAccountNumber();

            User user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setFingerprint(encryptedFingerprint); // store encrypted JPEG
            user.setPinHash(passwordEncoder.encode(pin));
            user.setPhNumber(phNumber);
            user.setAccountNumber(accountNumber);

            User value1=userRepository.save(user);
            // delegate bank account creation to BankService
            BankAccount value2=bankService.createBankAccount(accountNumber,initialBalance);

            welcomeMailService.sendWelcomeEmail(email,fullName,accountNumber,initialBalance);

            return new RegisterResponse(value1,value2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // Optional: decrypt fingerprint
    public byte[] decryptFingerprint(User user) throws Exception {
        return EncryptionUtil.decrypt(user.getFingerprint(), keyProvider.getMasterKey());
    }
}