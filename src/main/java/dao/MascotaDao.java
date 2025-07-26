/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dto.MascotaDto;
import persistencia.GestorPersistencia;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Kevin
 */

public class MascotaDao {
    private final String ruta = "data/mascotas.dat";
    private final GestorPersistencia gestor = GestorPersistencia.getInstance();

    public MascotaDao() {
    }
    private static MascotaDao instancia;
    
    public static MascotaDao getInstancia(){
         if(instancia == null){
             instancia = new MascotaDao();
         }
         return instancia;
    }
    
    public void guardar(MascotaDto mascota) {
        List<MascotaDto> lista = listar();
        lista.add(mascota);
       gestor.guardarLista(ruta, lista);
    }

    public List<MascotaDto> listar() {
        List<MascotaDto> lista = gestor.cargarLista(ruta);
        return lista != null ? lista : new ArrayList<>();
    }

    public void eliminar(int indice) {
        List<MascotaDto> lista = listar();
        if (indice >= 0 && indice < lista.size()) {
            lista.remove(indice);
            gestor.guardarLista(ruta, lista);
        }
    }

    public void actualizar(int indice, MascotaDto mascotaActualizada) {
        List<MascotaDto> lista = listar();
        if (indice >= 0 && indice < lista.size()) {
            lista.set(indice, mascotaActualizada);
            gestor.guardarLista(ruta, lista);
        }
    }
}



