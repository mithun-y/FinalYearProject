package com.personal.app.dto;

import com.personal.app.model.BankAccount;
import com.personal.app.model.User;

import lombok.Data;

@Data
public class RegisterResponse {
        private UserDTO user;
        private BankAccount bankAccount;

        public RegisterResponse(User user, BankAccount bankAccount) {
            this.user = new UserDTO(user);
            this.bankAccount = bankAccount;
        }
}