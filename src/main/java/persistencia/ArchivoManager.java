/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoManager {
    private File archivo;

    // Constructor que recibe la ruta del archivo
    public ArchivoManager(String rutaArchivo) {
        this.archivo = new File(rutaArchivo);
        crearArchivoSiNoExiste();
    }

    // ✅ Verifica si el archivo existe, si no, lo crea
    private void crearArchivoSiNoExiste() {
        try {
            if (!archivo.exists()) {
                archivo.getParentFile().mkdirs(); // Crea directorio si no existe
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + e.getMessage());
        }
    }

    // ✅ Escribe una línea al final del archivo
    public void escribirLinea(String linea) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    // ✅ Lee todas las líneas del archivo
    public List<String> leerLineas() {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return lineas;
    }

    // ✅ Sobrescribe el contenido completo del archivo
    public void sobrescribirArchivo(List<String> lineas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al sobrescribir el archivo: " + e.getMessage());
        }
    }

    // ✅ Limpia el archivo sin eliminarlo
    public void limpiarArchivo() {
        sobrescribirArchivo(new ArrayList<>());
    }

    // ✅ Elimina el archivo
    public boolean eliminarArchivo() {
        return archivo.delete();
    }

    // ✅ Renombrar archivo
    public boolean renombrarArchivo(String nuevoNombre) {
        File nuevoArchivo = new File(archivo.getParent(), nuevoNombre);
        return archivo.renameTo(nuevoArchivo);
    }

    // ✅ Obtener tamaño del archivo en bytes
    public long obtenerTamanioArchivo() {
        return archivo.length();
    }

    // ✅ Obtener ruta absoluta
    public String getRutaAbsoluta() {
        return archivo.getAbsolutePath();
    }

    // ✅ Saber si el archivo es un directorio
    public boolean esDirectorio() {
        return archivo.isDirectory();
    }

    // ✅ Obtener nombre del archivo
    public String getNombreArchivo() {
        return archivo.getName();
    }

    // ✅ Listar archivos de un directorio
    public static List<String> listarArchivos(String rutaDirectorio) {
        File dir = new File(rutaDirectorio);
        List<String> archivos = new ArrayList<>();
        if (dir.exists() && dir.isDirectory()) {
            for (File archivo : dir.listFiles()) {
                archivos.add(archivo.getName());
            }
        }
        return archivos;
    }

    // ✅ Obtener la ruta del archivo gestionado
    public String getRuta() {
        return archivo.getPath();
    }
}

