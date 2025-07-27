/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import dao.MascotaDao;
import dto.MascotaDto;
import DatoInvalidoExcepcion.DatoInvalidoException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import controlador.ControladorPersona;
import persistencia.ArchivoManager;
/**
 *
 * @author Kevin
 */
public class formMascota extends JFrame {

    private JTextField txtNombre, txtEspecie, txtEdad;
    private JTable tablaMascotas;
    private DefaultTableModel modeloTabla;
    private ControladorPersona controlador = new ControladorPersona();
    private MascotaDao dao = new MascotaDao();
    private int indiceSeleccionado = -1;

    public formMascota() {
        setTitle("Gestión de Mascotas");
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2));
        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Especie:"));
        txtEspecie = new JTextField();
        panelFormulario.add(txtEspecie);

        panelFormulario.add(new JLabel("Edad:"));
        txtEdad = new JTextField();
        panelFormulario.add(txtEdad);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        panelFormulario.add(btnGuardar);
        panelFormulario.add(btnActualizar);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Especie", "Edad"}, 0);
        tablaMascotas = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaMascotas);
        add(scroll, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelBotones = new JPanel();
        JButton btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);
                //boton de mantenimiento

        JButton btnMantenimiento = new JButton("Mantenimiento");
        btnMantenimiento.addActionListener(e -> mostrarMenuMantenimiento());
        panelBotones.add(btnMantenimiento);



        // Eventos
        btnGuardar.addActionListener(this::guardarMascota);
        btnActualizar.addActionListener(this::actualizarMascota);
        btnEliminar.addActionListener(this::eliminarMascota);
        tablaMascotas.getSelectionModel().addListSelectionListener(e -> seleccionarMascota());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        listarMascotas();
    }

    private void guardarMascota(ActionEvent e) {
        try {
            MascotaDto mascota = obtenerDatosFormulario();
            controlador.guardar(mascota);
            JOptionPane.showMessageDialog(this, "✅ Mascota guardada");
            limpiarCampos();
            listarMascotas();
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void actualizarMascota(ActionEvent e) {
        try {
            if (indiceSeleccionado < 0) {
                throw new Exception("Seleccione una mascota de la tabla");
            }
            MascotaDto mascota = obtenerDatosFormulario();
            controlador.actualizar(indiceSeleccionado, mascota);
            JOptionPane.showMessageDialog(this, "✏️ Mascota actualizada");
            limpiarCampos();
            listarMascotas();
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void eliminarMascota(ActionEvent e) {
        try {
            if (indiceSeleccionado < 0) {
                throw new Exception("Seleccione una mascota para eliminar");
            }
            controlador.eliminar(indiceSeleccionado);
            JOptionPane.showMessageDialog(this, "🗑️ Mascota eliminada");
            limpiarCampos();
            listarMascotas();
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void seleccionarMascota() {
        indiceSeleccionado = tablaMascotas.getSelectedRow();
        if (indiceSeleccionado >= 0) {
            txtNombre.setText((String) modeloTabla.getValueAt(indiceSeleccionado, 0));
            txtEspecie.setText((String) modeloTabla.getValueAt(indiceSeleccionado, 1));
            txtEdad.setText(String.valueOf(modeloTabla.getValueAt(indiceSeleccionado, 2)));
        }
    }

    private void listarMascotas() {
        modeloTabla.setRowCount(0);
        List<MascotaDto> lista = controlador.listar();
        for (MascotaDto m : lista) {
            modeloTabla.addRow(new Object[]{m.getNombre(), m.getEspecie(), m.getEdad()});
        }
        indiceSeleccionado = -1;
    }

    private MascotaDto obtenerDatosFormulario() throws DatoInvalidoException {
        String nombre = txtNombre.getText().trim();
        String especie = txtEspecie.getText().trim();
        String edadStr = txtEdad.getText().trim();

        if (nombre.isEmpty() || especie.isEmpty() || edadStr.isEmpty()) {
            throw new DatoInvalidoException("Todos los campos son obligatorios");
        }

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            throw new DatoInvalidoException("La edad debe ser un número válido");
        }

        if (edad < 0) {
            throw new DatoInvalidoException("La edad no puede ser negativa");
        }

        return new MascotaDto(nombre, especie, edad);
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtEspecie.setText("");
        txtEdad.setText("");
        tablaMascotas.clearSelection();
        indiceSeleccionado = -1;
    }
    private void mostrarMenuMantenimiento() {
        ArchivoManager am = new ArchivoManager("data/mascotas.dat");

        String[] opciones = {"Ver Información", "Limpiar Archivo", "Eliminar Archivo", "Cancelar"};
        int opcion = JOptionPane.showOptionDialog(this,
                "Selecciona una opción de mantenimiento del archivo:",
                "Mantenimiento del archivo",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        switch (opcion) {
            case 0:  // Ver Información
                String info = "Nombre: " + am.getNombreArchivo() +
                        "\nRuta: " + am.getRutaAbsoluta() +
                        "\nTamaño: " + am.obtenerTamanioArchivo() + " bytes";
                JOptionPane.showMessageDialog(this, info, "Información del Archivo", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 1:  // Limpiar Archivo
                int confirmLimpiar = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas limpiar el archivo?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirmLimpiar == JOptionPane.YES_OPTION) {
                    am.limpiarArchivo();
                    JOptionPane.showMessageDialog(this, "Archivo limpiado correctamente.");
                    listarMascotas();
                }
                break;
            case 2:  // Eliminar Archivo
                int confirmEliminar = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar el archivo?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirmEliminar == JOptionPane.YES_OPTION) {
                    System.gc(); // 💡 Forzar recolección de basura para liberar posibles referencias de streams
                    if (am.eliminarArchivo()) {
                        JOptionPane.showMessageDialog(this, "Archivo eliminado.");
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo eliminar el archivo.");
                    }
                }
                listarMascotas(); // ✅ Actualiza tabla luego de eliminar
                break;

            default:
                break;
        }
    }


    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}



