package com.example.Backend_PichaLAvado.Controller;

import com.example.Backend_PichaLAvado.Entity.Autos;
import com.example.Backend_PichaLAvado.Entity.Users;
import com.example.Backend_PichaLAvado.Repository.AutosRepository;
import com.example.Backend_PichaLAvado.Repository.UsersRepository;
import com.example.Backend_PichaLAvado.Service.AutosService;
import com.example.Backend_PichaLAvado.Service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AutosController {
    @Autowired
    private AutosService autosService;

    @PostMapping("/autos/add")
    public ResponseEntity<Autos> addVehicle(@RequestBody Autos autos, HttpSession session) {
        // Obtener el usuario de la sesión
        Users user = (Users ) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Agregar el vehículo vinculado al usuario
        Autos savedVehicle = autosService.addAutos(user.getId(), autos);
        return ResponseEntity.ok(savedVehicle);
    }

    @GetMapping("/autos")
    public ResponseEntity<List<Autos>> getAllVehicles() {
        List<Autos> vehicles = autosService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/autos/{id}")
    public ResponseEntity<Autos> getVehicleById(@PathVariable Long id) {
        Autos autos = autosService.getVehicleById(id);
        return ResponseEntity.ok(autos);
    }

    @PutMapping("/autos/put/{id}")
    public ResponseEntity<Autos> updateVehicle(@PathVariable Long id, @RequestBody Autos vehicleDetails) {
        Autos updatedVehicle = autosService.updateVehicle(id, vehicleDetails);
        return ResponseEntity.ok(updatedVehicle);
    }

    @DeleteMapping("/autos/delete/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        autosService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

}
