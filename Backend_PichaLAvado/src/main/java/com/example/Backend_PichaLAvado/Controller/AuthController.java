package com.example.Backend_PichaLAvado.Controller;

import com.example.Backend_PichaLAvado.Entity.Users;
import com.example.Backend_PichaLAvado.Repository.UsersRepository;
import com.example.Backend_PichaLAvado.Requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private UsersRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Users> user = userRepository.findById(request.getUserId());

        if (user.isPresent() && user.get().getPassword().equals(request.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("email", user.get().getEmail());
            response.put("name", user.get().getName());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }
}
