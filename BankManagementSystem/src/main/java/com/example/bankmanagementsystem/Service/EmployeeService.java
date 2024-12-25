package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Api.ApiException;
import com.example.bankmanagementsystem.DTO.AccountDTO;
import com.example.bankmanagementsystem.DTO.CustomerOutput;
import com.example.bankmanagementsystem.DTO.EmployeeDTO;
import com.example.bankmanagementsystem.DTO.EmployeeOutput;
import com.example.bankmanagementsystem.Model.Account;
import com.example.bankmanagementsystem.Model.Customer;
import com.example.bankmanagementsystem.Model.Employee;
import com.example.bankmanagementsystem.Model.MyUser;
import com.example.bankmanagementsystem.Repository.EmployeeRepository;
import com.example.bankmanagementsystem.Repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final MyUserRepository myUserRepository;
    private final MyUserService myUserService;

    public void registerEmployee(EmployeeDTO dto) {
        MyUser user = new MyUser();
        user.setUsername(dto.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setRole("EMPLOYEE");
        myUserService.registerNewUser(user);
        Employee employee = new Employee();
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());
        employee.setMyUser(user);
        employeeRepository.save(employee);
    }

    public List<EmployeeOutput> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeOutput> outputs = new ArrayList<>();
        for (Employee e : employees) {
            outputs.add(convertToOutput(e));
        }
        return outputs;
    }

    private EmployeeOutput convertToOutput(Employee e) {
        if (e == null) {
            throw new ApiException("Employee not found");
        }
        EmployeeOutput out = new EmployeeOutput();
        out.setUsername(e.getMyUser().getUsername());
        out.setEmail(e.getMyUser().getEmail());
        out.setPosition(e.getPosition());
        return out;
    }
}
