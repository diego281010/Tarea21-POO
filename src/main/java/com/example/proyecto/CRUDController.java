package com.example.proyecto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;

public class CRUDController {
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtTipo;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextField txtPeso;
    @FXML
    private ComboBox<String> cbxSexo;

    @FXML
    private TableView<Animales> tblAnimales;
    @FXML
    private TableColumn<Animales, String> colNombre, colSexo, colTipo;
    @FXML
    private TableColumn<Animales, Integer> colEdad, colId;
    @FXML
    private TableColumn<Animales, Double> colPeso;

    private Animales animalSeleccionado;
    private int idSeleccionado = -1;

    private ObservableList<Animales> animales;

    @FXML
    private void initialize() {
        cbxSexo.getItems().addAll("Macho", "Hembra");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));

        animales = FXCollections.observableArrayList();
        tblAnimales.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                animalSeleccionado = newValue;
                idSeleccionado = newValue.getId();
                txtNombre.setText(newValue.getNombre());
                txtTipo.setText(newValue.getTipo());
                txtEdad.setText(String.valueOf(newValue.getPeso()));
                txtPeso.setText(String.valueOf(newValue.getPeso()));
                cbxSexo.getSelectionModel().select(newValue.getSexo());
            }
        });
        mostrar();
    }

    private void mostrar() {
        animales = FXCollections.observableArrayList();
        String sql = "SELECT * FROM animales";

        try(Connection con = Conexion.getConexion();){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                Animales animal = new Animales();

                animal.setId(rs.getInt("id"));
                animal.setNombre(rs.getString("nombre"));
                animal.setSexo(rs.getString("sexo"));
                animal.setTipo(rs.getString("tipo"));
                animal.setEdad(rs.getInt("edad"));
                animal.setPeso(rs.getDouble("peso"));
                animales.add(animal);
            }

            tblAnimales.setItems(animales);
        } catch (SQLException e){
            mostrarAlerta("Error", "No se pudo cargar los datos." + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void guardar() {
        String sql = "INSERT INTO animales (nombre, tipo, edad, peso, sexo) Values (?, ?, ?, ?, ?)";

        String nombre = txtNombre.getText().trim();
        String tipo = txtTipo.getText().trim();
        String edadStr = txtEdad.getText().trim();
        String pesoStr = txtPeso.getText().trim();
        String sexo = cbxSexo.getValue();

        if (nombre.isEmpty() || tipo.isEmpty() || edadStr.isEmpty() || pesoStr.isEmpty() || sexo.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return;
        }

        int edad = (int) Double.parseDouble(edadStr);
        double peso = Double.parseDouble(pesoStr);

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, tipo);
            ps.setInt(3, edad);
            ps.setDouble(4, peso);
            ps.setString(5, sexo);

            ps.executeUpdate();
            mostrar();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            e.printStackTrace();
            alert.showAndWait();
            return;
        }
    }
    

