package com.example.bankmanagementsystem.Repository;

import com.example.bankmanagementsystem.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
//
//    Employee findByEmployeeId(Integer employeeId);
//    Employee findEmployeeByEmail(String email);

}
