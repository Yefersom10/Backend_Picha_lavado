package com.example.Backend_PichaLAvado.Service;

import com.example.Backend_PichaLAvado.Entity.Users;
import com.example.Backend_PichaLAvado.Repository.UsersRepository;
import com.example.Backend_PichaLAvado.Requests.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
import java.util.Optional;
@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;

    public Users addUsers(Users users) {
        users.setPassword(hashContrasenia(users.getPassword()));
        return usersRepository.save(users);
    }

    public String hashContrasenia(String password) {
        try {
            MessageDigest instancia = MessageDigest.getInstance("SHA-256");
            byte[] hash = instancia.digest(password.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña");
        }
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest, HttpSession session) {
        Optional<Users> userOptional = usersRepository.findById(loginRequest.getUserId());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Usuario no encontrado"));
        }

        Users user = userOptional.get();

        if (!user.getPassword().equals(hashContrasenia(loginRequest.getPassword()))) {
            return ResponseEntity.status(401).body(Map.of("message", "Contraseña incorrecta"));
        }

        // Guardamos el usuario en la sesión
        session.setAttribute("user", user);

        // Enviamos respuesta con datos del usuario
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login exitoso");
        response.put("name", user.getName());
        response.put("apellido", user.getApellido()); // Nuevo campo
        response.put("telefono", user.getTelefono()); // Nuevo campo

        return ResponseEntity.ok(response);
    }
}