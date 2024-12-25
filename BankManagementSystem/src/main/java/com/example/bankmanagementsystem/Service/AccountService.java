package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Api.ApiException;
import com.example.bankmanagementsystem.DTO.AccountDTO;
import com.example.bankmanagementsystem.DTO.AccountOperationsDTO;
import com.example.bankmanagementsystem.Model.Account;
import com.example.bankmanagementsystem.Model.Customer;
import com.example.bankmanagementsystem.Model.MyUser;
import com.example.bankmanagementsystem.Repository.AccountRepository;
import com.example.bankmanagementsystem.Repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final MyUserRepository myUserRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public AccountDTO getAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        return convertToDTO(account);
    }

    public void activeAccount(String accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        account.setIsActive(true);
        accountRepository.save(account);
    }

    public void blockAccount(String accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        account.setIsActive(false);
        accountRepository.save(account);
    }

    public void deposit(AccountOperationsDTO accountOperations, Integer authId) {

        Account account = accountRepository.findAccountByAccountNumber(accountOperations.getAccountNumber());

        if (account == null) {
            throw new ApiException("Account not found");
        }
        if (account.getCustomer().getId() != authId) {
            throw new ApiException("Sorry you Cant deposit this account");
        }
        account.setBalance(account.getBalance() + accountOperations.getAmount());
        accountRepository.save(account);
    }

    public void withdrawal(AccountOperationsDTO accountOperations, Integer authId) {
        Account account = accountRepository.findAccountByAccountNumber(accountOperations.getAccountNumber());
        if (account == null) {
            throw new ApiException("Account not found");
        }
        if (account.getCustomer().getId() != authId) {
            throw new ApiException("Sorry you Cant withdrawal from this account");
        }
        if (account.getBalance() < accountOperations.getAmount()) {
            throw new ApiException("Not enough balance");
        }
        account.setBalance(account.getBalance() - accountOperations.getAmount());
        accountRepository.save(account);
    }

    public void createAccount(Integer authId) {
        MyUser myUser = myUserRepository.findMyUserById(authId);
        if (myUser == null) {
            throw new ApiException("User not found");
        }
        if (!myUser.getRole().equals("CUSTOMER")) {
            throw new ApiException("Only customers can create accounts");
        }
        Customer customer = myUser.getCustomer();
        if (customer == null) {
            throw new ApiException("No customer profile found for this user");
        }
        Account newAccount = new Account();
        newAccount.setCustomer(customer);
        newAccount.setAccountNumber(generateAccountNumber());
        newAccount.setIsActive(false);
        accountRepository.save(newAccount);
    }

    private String generateAccountNumber() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (i > 0) sb.append("-");
            for (int j = 0; j < 4; j++) {
                int digit = random.nextInt(10);
                sb.append(digit);
            }
        }
        return sb.toString();
    }


    public List<Account> getMyAccounts(Integer authId) {
        MyUser myUser = myUserRepository.findMyUserById(authId);
        if (myUser == null) {
            throw new ApiException("User not found");
        }
        Customer customer = myUser.getCustomer();
        if (customer == null) {
            throw new ApiException("No customer profile found");
        }
        if (customer.getAccounts().isEmpty()) {
            throw new ApiException("No accounts found for this customer");
        }

        List<Account> accounts = new ArrayList<>();
        for (Account account : customer.getAccounts()) {
            accounts.add(account);
        }
        return accounts;
    }


    public void transferFunds(AccountOperationsDTO accountOperationsDTO, String toAccountNumber, Integer authId) {
        Account toA = accountRepository.findAccountByAccountNumber(toAccountNumber);
        Account fromA = accountRepository.findAccountByAccountNumber(accountOperationsDTO.getAccountNumber());
        if (toA == null || fromA == null) {
            throw new ApiException("Account not found");
        }
        if (fromA.getCustomer().getId() != authId) {
            throw new ApiException("Sorry you Cant transfer from this account");
        }
        if (fromA.getBalance() < accountOperationsDTO.getAmount()) {
            throw new ApiException("Not enough balance");
        }
        fromA.setBalance(fromA.getBalance() + accountOperationsDTO.getAmount());
        fromA.setBalance(fromA.getBalance() - accountOperationsDTO.getAmount());
        accountRepository.save(fromA);

        accountRepository.save(toA);
    }

    public AccountDTO convertToDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setBalance(account.getBalance());
        accountDTO.getIsActive();
        return accountDTO;
    }
}
