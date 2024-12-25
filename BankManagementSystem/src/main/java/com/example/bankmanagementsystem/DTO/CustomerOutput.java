package com.example.bankmanagementsystem.DTO;

import com.example.bankmanagementsystem.Model.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class CustomerOutput {

    private String username;
    private String email;
    private String phoneNumber;
    private List<AccountDTO> accounts;


}
