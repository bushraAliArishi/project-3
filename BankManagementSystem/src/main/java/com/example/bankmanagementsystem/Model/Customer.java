package com.example.bankmanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    private Integer id;

    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String phoneNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnore
    private MyUser myUser;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Account> accounts;
}
