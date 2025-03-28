package com.example.Backend_PichaLAvado.Service;

import com.example.Backend_PichaLAvado.Entity.Autos;
import com.example.Backend_PichaLAvado.Entity.Users;
import com.example.Backend_PichaLAvado.Repository.AutosRepository;
import com.example.Backend_PichaLAvado.Repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutosService {
    @Autowired
    private AutosRepository autosRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Transactional
    public Autos addAutos(Long userId, Autos autos) {
        // Obtener el usuario por ID
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Vincular el vehículo al usuario
        autos.setUser(user);

        // Guardar el vehículo en la base de datos
        return autosRepository.save(autos);
    }

    public List<Autos> getAllVehicles() {
        return autosRepository.findAll();
    }

    public Autos getVehicleById(Long id) {
        return autosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
    }

    @Transactional
    public Autos updateVehicle(Long id, Autos vehicleDetails) {
        Autos autos = autosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        // Actualizar los detalles del vehículo
        autos.setMarca(vehicleDetails.getMarca());
        autos.setModelo(vehicleDetails.getModelo());
        autos.setAnio(vehicleDetails.getAnio());
        autos.setPlaca(vehicleDetails.getPlaca());
        autos.setColor(vehicleDetails.getColor());

        return autosRepository.save(autos);
    }

    @Transactional
    public void deleteVehicle(Long id) {
        Autos autos = autosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        autosRepository.delete(autos);
    }
}
