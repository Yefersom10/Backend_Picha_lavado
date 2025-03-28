package com.example.Backend_PichaLAvado.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String apellido;  // Nuevo campo
    private String telefono;  // Nuevo campo
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Autos> autos;

    public Users() {}

    public Users(String email, String name, String apellido, String telefono, String password) {
        this.email = email;
        this.name = name;
        this.apellido = apellido;
        this.telefono = telefono;
        this.password = password;
    }


    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Autos> getAutos() {
        return autos;
    }

    public void setAutos(List<Autos> autos) {
        this.autos = autos;
    }
}
