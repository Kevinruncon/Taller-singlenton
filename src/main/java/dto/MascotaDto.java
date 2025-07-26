/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;
import java.io.Serializable;

/**
 *
 * @author Kevin
 */

public class MascotaDto implements Serializable {
    private String nombre;
    private String especie;
    private int edad;

    public MascotaDto(String nombre, String especie, int edad) {
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
    }

    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }
    public int getEdad() { return edad; }

    @Override
    public String toString() {
        return nombre + " - " + especie + " - " + edad + " años";
    }
}



