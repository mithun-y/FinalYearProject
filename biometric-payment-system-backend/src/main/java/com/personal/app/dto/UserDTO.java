package com.personal.app.dto;
import com.personal.app.model.User;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private String accountNumber;

    public UserDTO(User user){
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.accountNumber=user.getAccountNumber();
    }
}
