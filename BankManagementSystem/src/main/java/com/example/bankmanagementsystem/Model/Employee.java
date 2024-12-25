package com.example.bankmanagementsystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    private Integer id;

    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String position;

    @Column(nullable = false)
    private Double salary = 0.0;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private MyUser myUser;

    // Add custom business logic methods if required
}
