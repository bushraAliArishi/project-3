package com.example.bankmanagementsystem.Service;


import com.example.bankmanagementsystem.Api.ApiException;
import com.example.bankmanagementsystem.DTO.CustomerDTO;
import com.example.bankmanagementsystem.DTO.CustomerOutput;
import com.example.bankmanagementsystem.Model.Customer;
import com.example.bankmanagementsystem.Model.MyUser;
import com.example.bankmanagementsystem.Repository.CustomerRepository;
import com.example.bankmanagementsystem.Repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final MyUserRepository myUserRepository;
    private final MyUserService myUserService;

    public void registerCustomer(CustomerDTO dto) {
        MyUser user = myUserRepository.findMyUserByUsername(dto.getUsername());
        if (user != null) {
            throw new ApiException("username exist ");
        }
        MyUser newUser = new MyUser();

        newUser.setUsername(dto.getUsername());
        newUser.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        newUser.setEmail(dto.getEmail());
        newUser.setRole("CUSTOMER");
        myUserRepository.save(newUser);

        Customer customer = new Customer();
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setMyUser(newUser);
        customerRepository.save(customer);
    }

    public List<CustomerOutput> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerOutput> outputs = new ArrayList<>();
        for (Customer c : customers) {
            outputs.add(convertToOutput(c));
        }
        return outputs;
    }

    private CustomerOutput convertToOutput(Customer c) {
        if (c == null) {
            throw new ApiException("Customer not found");
        }
        CustomerOutput out = new CustomerOutput();
        out.setUsername(c.getMyUser().getUsername());
        out.setEmail(c.getMyUser().getEmail());
        out.setPhoneNumber(c.getPhoneNumber());
        return out;
    }
}
