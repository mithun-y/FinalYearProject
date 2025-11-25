package com.personal.app.service;

import com.personal.app.model.User;
import com.personal.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> findName(String phoneNumber) {
        System.out.println("service class phNumber "+phoneNumber);
        User user = userRepository.findByPhNumber(phoneNumber).orElse(null);
        System.out.println(user);
        if(user==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(user.getFullName());
    }
}
