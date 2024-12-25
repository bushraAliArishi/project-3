package com.example.bankmanagementsystem.Service;


import com.example.bankmanagementsystem.Api.ApiException;
import com.example.bankmanagementsystem.Model.MyUser;
import com.example.bankmanagementsystem.Repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserService {

    private final MyUserRepository myUserRepository;

    public List<MyUser> getAllUsers() {
        return myUserRepository.findAll();
    }

    public MyUser getUser(Integer id) {
        MyUser myUser = myUserRepository.findById(id).orElse(null);
        if (myUser == null) {
            throw new ApiException("User not found");
        }
        return myUser;
    }

    public void registerNewUser(MyUser newUser) {
        String hashedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());
        newUser.setPassword(hashedPassword);
        myUserRepository.save(newUser);
    }

    public void updateUser(MyUser newUser, Integer id) {
        MyUser oldUser = getUser(id);
        newUser.setId(id);
        newUser.setRole(oldUser.getRole());
        newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
        myUserRepository.save(newUser);
    }

    public void deleteUser(Integer id) {
        MyUser myUser = getUser(id);
        myUserRepository.delete(myUser);
    }
}
