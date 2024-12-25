package com.example.bankmanagementsystem.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class EmployeeDTO {

    @NotEmpty(message = "username cant be empty")
    private String username;

    @NotEmpty(message = "password cant be empty")
    @NotEmpty(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, one special character, and must be at least 8 characters long"
    )
    private String password;
    @Email(message = "invalid email")
    @NotEmpty(message = "email cant be empty")
    private String email;

    @NotEmpty(message = "position cant be empty")
    private String position;

    @PositiveOrZero(message = "salary must be ether positive or zero ")
    private Double salary = 0.0;


}
