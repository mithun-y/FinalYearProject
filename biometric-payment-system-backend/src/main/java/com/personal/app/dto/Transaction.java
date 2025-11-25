package com.personal.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String id;
    private Double amount;
    private String account;
    private String status;
    private String deviceId;
    private String location;

}
