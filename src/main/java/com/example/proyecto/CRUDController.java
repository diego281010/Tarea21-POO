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