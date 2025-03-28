package com.example.Backend_PichaLAvado.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "autos")
public class Autos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marca;

    private String modelo;

    private Integer anio;
    @Column(unique = true)
    private String placa;

    private String color;

    private String tipoDeVehiculo; // Carro, moto, camión, etc.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipoDeVehiculo() {
        return tipoDeVehiculo;
    }

    public void setTipoDeVehiculo(String tipoDeVehiculo) {
        this.tipoDeVehiculo = tipoDeVehiculo;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}

