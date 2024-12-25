package com.example.bankmanagementsystem.DTO;

import lombok.Data;

@Data
public class AccountDTO {
    private String accountNumber;
    private Double balance;
    private Boolean isActive;
}
