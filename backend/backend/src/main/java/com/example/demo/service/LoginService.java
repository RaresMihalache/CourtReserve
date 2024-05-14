package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.response.GetUserResponse;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.model.response.StatusResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Encrypting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public LoginResponse login(String email, String password) {
        User user = null;
        try {
            user = userRepository.findUserByEmail(email);
            if (user != null && Encrypting.check(password, user.getPassword())) {
                return new LoginResponse(user.getId(), user.getRole().getId(), "Logged in successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("login - Service");
        }

        return null;
    }

    public StatusResponse resetPassword(String email, String newPassword) {
        User foundUser = null;
        foundUser = userRepository.findUserByEmail(email);
        if (foundUser != null) {
            if (!Encrypting.check(newPassword, foundUser.getPassword()) && newPassword != null) {
                try {
                    foundUser.setPassword(Encrypting.encrypt(newPassword));
                    userRepository.save(foundUser);

                    return new StatusResponse("Password reset successfully!");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Reset password - Service");
                    return new StatusResponse("Failed to reset password");
                }
            }
        }

        return new StatusResponse("Email address not found or you already have this password!");
    }

    public GetUserResponse findUserByEmail(String email) {
        GetUserResponse foundUser = new GetUserResponse();
        try{
            foundUser.setUser(userRepository.findUserByEmail(email));
            foundUser.setStatus("Valid email");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("find by email - Service");
            foundUser.setUser(null);
            foundUser.setStatus("Invalid email");
            return foundUser;
        }

        return foundUser;
    }
}
