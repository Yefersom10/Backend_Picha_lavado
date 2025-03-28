package com.example.Backend_PichaLAvado.Repository;

import com.example.Backend_PichaLAvado.Entity.Autos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutosRepository extends JpaRepository<Autos, Long> {

}
