package com.personal.app.controller;

import com.personal.app.model.User;
import com.personal.app.repository.UserRepository;
import com.personal.app.service.BankService;
import com.personal.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/update")
public class AddBalanceController {


    @Autowired
    private UserService userService;

    @Autowired
    private BankService bankService;

//    @PostMapping("/fetchname")
//    public ResponseEntity<?> fetchName(
//            @RequestBody String phoneNumber){
//        System.out.println(phoneNumber);
//                return userService.findName(phoneNumber);
//    }

    @Transactional
    @PatchMapping("/addbalance/{phNumber}/{money}")
    public ResponseEntity<?> add(@PathVariable String phNumber,
                                 @PathVariable Double money) {

        return bankService.add(phNumber, money);
    }


    @Autowired
    private UserRepository userRepository;

    @Transactional
    @GetMapping("/check/{phNumber}")
    public Optional<User> fetch(@PathVariable String phNumber) {
        return userRepository.findByPhNumber(phNumber);
    }
}
