package com.example.bankmanagementsystem.Controller;


import com.example.bankmanagementsystem.Api.ApiResponse;
import com.example.bankmanagementsystem.DTO.AccountOperationsDTO;
import com.example.bankmanagementsystem.Model.MyUser;
import com.example.bankmanagementsystem.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    // Admin/User
    @GetMapping("/all-accounts")
    public ResponseEntity getAllAccounts() {
        return ResponseEntity.status(200).body(accountService.getAllAccounts());
    }

    // Admin/User
    @GetMapping("/{accountNumber}")
    public ResponseEntity getAccountByAccountNumber(@PathVariable String accountNumber) {
        return ResponseEntity.status(200).body(accountService.getAccountByAccountNumber(accountNumber));
    }

    // User
    @PostMapping("/create")
    public ResponseEntity createAccount(@AuthenticationPrincipal MyUser authId) {
        accountService.createAccount(authId.getId());
        return ResponseEntity.status(201).body(new ApiResponse("Account Created"));
    }

    // User
    @PostMapping("/deposit")
    public ResponseEntity deposit(@RequestBody @Valid AccountOperationsDTO operationsDTO, @AuthenticationPrincipal MyUser authId) {
        accountService.deposit(operationsDTO, authId.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Amount Deposited"));
    }

    // User
    @PostMapping("/withdraw")
    public ResponseEntity withdraw(@RequestBody @Valid AccountOperationsDTO operationsDTO, @AuthenticationPrincipal MyUser authId) {
        accountService.withdrawal(operationsDTO, authId.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Amount Withdrawn"));
    }

    // User
    @PostMapping("/transfer/{toAccountNumber}")
    public ResponseEntity transferFunds(@RequestBody @Valid  AccountOperationsDTO operationsDTO, @PathVariable String toAccountNumber, @AuthenticationPrincipal MyUser authId) {
        accountService.transferFunds(operationsDTO, toAccountNumber, authId.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Funds Transferred"));
    }

    // Admin
    @PatchMapping("/activate/{accountNumber}")
    public ResponseEntity activateAccount(@PathVariable String accountNumber) {
        accountService.activeAccount(accountNumber);
        return ResponseEntity.status(200).body(new ApiResponse("Account Activated"));
    }

    // Admin
    @PatchMapping("/block/{accountNumber}")
    public ResponseEntity blockAccount(@PathVariable String accountNumber) {
        accountService.blockAccount(accountNumber);
        return ResponseEntity.status(200).body(new ApiResponse("Account Blocked"));
    }
}
