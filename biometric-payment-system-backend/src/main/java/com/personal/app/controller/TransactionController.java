package com.personal.app.controller;

import com.personal.app.dto.Transaction;
import com.personal.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/pay")
    public ResponseEntity<?> makePayment(
            @RequestParam("pin") String pin,
            @RequestParam("fingerprint") MultipartFile fingerprint,
            @RequestParam("amount") Double amount,
            @RequestParam("location") String location) {

        try {
            Transaction result = transactionService.makePayment(pin, fingerprint, amount,location);
            System.out.println(result);
            if(result.getStatus()=="SUCCESS"){
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}

