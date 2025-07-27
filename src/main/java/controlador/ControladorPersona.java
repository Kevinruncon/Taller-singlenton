/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import dao.MascotaDao;
import dto.MascotaDto;
import java.util.List;
/**
 *
 * @author Kevin
 */
public class ControladorPersona {

    private final MascotaDao dao;
    private List<MascotaDto> listaMascotas;

    public ControladorPersona() {
        this.dao = MascotaDao.getInstancia();
        this.listaMascotas = dao.listar(); // Cargar la lista inicial desde archivo
    }

    public List<MascotaDto> listar() {
        // Siempre se actualiza la lista más reciente
        listaMascotas = dao.listar();
        return listaMascotas;
    }

    public void guardar(MascotaDto mascota) {
        dao.guardar(mascota);
        listaMascotas = dao.listar(); // Refrescar lista
    }

    public void eliminar(int indice) {
        dao.eliminar(indice);
        listaMascotas = dao.listar(); // Refrescar lista
    }

    public void actualizar(int indice, MascotaDto mascotaActualizada) {
        dao.actualizar(indice, mascotaActualizada);
        listaMascotas = dao.listar(); // Refrescar lista
    }
}


