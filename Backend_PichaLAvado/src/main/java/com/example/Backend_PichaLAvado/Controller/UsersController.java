package com.example.Backend_PichaLAvado.Controller;

import com.example.Backend_PichaLAvado.Entity.Users;
import com.example.Backend_PichaLAvado.Requests.LoginRequest;
import com.example.Backend_PichaLAvado.Service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    @PostMapping("/addUser")
    public Users addUser(@RequestBody Users users) {
        return usersService.addUsers(users);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpSession session) {
        return usersService.loginUser(loginRequest, session);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout exitoso");
    }
}
