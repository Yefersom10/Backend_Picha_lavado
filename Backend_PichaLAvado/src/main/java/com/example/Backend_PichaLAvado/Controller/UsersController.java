package com.example.Backend_PichaLAvado.Controller;

import com.example.Backend_PichaLAvado.Entity.Users;
import com.example.Backend_PichaLAvado.Repository.UsersRepository;
import com.example.Backend_PichaLAvado.Requests.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody Users users) {
        if (usersRepository.findByEmail(users.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body(Map.of("message", "El usuario ya existe"));
        }

        users.setPassword(hashContrasenia(users.getPassword())); // Hashea la contraseña
        usersRepository.save(users);

        // Devolver JSON en lugar de un simple texto
        return ResponseEntity.ok(Map.of("message", "Usuario registrado con éxito"));
    }


    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpSession session) {
        System.out.println("Intentando login con: " + loginRequest.getEmail());

        Optional<Users> optionalUser = usersRepository.findByEmail(loginRequest.getEmail());

        if (optionalUser.isEmpty()) {
            System.out.println("Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Usuario no encontrado"));
        }

        Users user = optionalUser.get();

        // Verificar la contraseña encriptada
        if (!user.getPassword().equals(hashContrasenia(loginRequest.getPassword()))) {
            System.out.println("Contraseña incorrecta");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Contraseña incorrecta"));
        }

        // Guardamos el usuario en la sesión
        session.setAttribute("user", user);

        // Enviamos respuesta con datos del usuario
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login exitoso");
        response.put("name", user.getName());
        response.put("apellido", user.getApellido());
        response.put("telefono", user.getTelefono());

        return ResponseEntity.ok(response);
    }

    private String hashContrasenia(String password) {
        try {
            MessageDigest instancia = MessageDigest.getInstance("SHA-256");
            byte[] hash = instancia.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña");
        }
    }
}
