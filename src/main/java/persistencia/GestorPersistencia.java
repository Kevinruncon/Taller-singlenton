/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Kevin
 */
public class GestorPersistencia {

    private static GestorPersistencia instancia;

    private GestorPersistencia() {}

    public static synchronized GestorPersistencia getInstance() {
        if (instancia == null) {
            instancia = new GestorPersistencia();
        }
        return instancia;
    }

    public <T extends Serializable> void guardarLista(String ruta, List<T> lista) {
        try{
        File archivo = new File(ruta);
        archivo.getParentFile().mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(lista);
}
        } catch (IOException e) {
            System.err.println("❌ Error al guardar datos: " + e.getMessage());
        }
    }
    

    public <T extends Serializable> List<T> cargarLista(String ruta) {
        File archivo = new File(ruta);
        if(!archivo.exists()){
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            return (List<T>) ois.readObject();
        }catch (EOFException e){
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Error al leer datos: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}


