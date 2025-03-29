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
import java.util.Map;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

     // Método para registrar un usuario con contraseña encriptada

    public Users addUsers(Users users) {
        users.setPassword(hashContrasenia(users.getPassword())); // Encripta la contraseña
        return usersRepository.save(users);
    }

    /**
     * Método para autenticar un usuario
     */
    public ResponseEntity<?> loginUser(LoginRequest loginRequest, HttpSession session) {
        System.out.println("Intentando login con: " + loginRequest.getEmail());

        // Buscar usuario por email usando Optional
        Optional<Users> optionalUser = usersRepository.findByEmail(loginRequest.getEmail());

        if (optionalUser.isEmpty()) {
            System.out.println("Usuario no encontrado");
            return ResponseEntity.status(401).body(Map.of("message", "Usuario no encontrado"));
        }

        Users user = optionalUser.get(); // Extraer usuario del Optional

        // Verificar contraseña encriptada
        if (!user.getPassword().equals(hashContrasenia(loginRequest.getPassword()))) {
            System.out.println("Contraseña incorrecta");
            return ResponseEntity.status(401).body(Map.of("message", "Contraseña incorrecta"));
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


    /**
     * Método para cerrar sesión
     */
    public ResponseEntity<?> logoutUser(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Logout exitoso"));
    }

    /**
     * Método para encriptar la contraseña con SHA-256
     */
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
