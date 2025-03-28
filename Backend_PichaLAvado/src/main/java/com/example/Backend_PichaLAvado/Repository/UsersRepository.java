package com.example.Backend_PichaLAvado.Repository;

import com.example.Backend_PichaLAvado.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findById(Long userId);
}
