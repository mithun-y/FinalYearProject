package com.personal.app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Lob
    private byte[] fingerprint; // store encrypted JPEG

    private String pinHash;

    @Column(name = "ph_number", unique = true, nullable = false)
    private String phNumber;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    // mapping to bank account
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private BankAccount bankAccount;

    private LocalDateTime createdTime = LocalDateTime.now();

}