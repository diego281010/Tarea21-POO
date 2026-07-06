




@FXML
void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo){
    Alert alerta = new Alert(tipo);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    alerta.showAndWait();
}

@FXML
private void eliminar() {
    String sql = "DELETE FROM animales WHERE id = ?";

    try (Connection connection = Conexion.getConexion();
         PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, animalSeleccionado.getId());
        ps.executeUpdate();
        mostrar();
        limpiar();
    } catch (Exception e) {
        mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
        return;
    }
}

@FXML
private void editar(){
    String sql = "UPDATE animales SET nombre=?, tipo=?, edad=?, peso=?, sexo=? WHERE id=?";

    String nombre = txtNombre.getText();
    String tipo = txtTipo.getText();
    String edadStr = txtEdad.getText();
    String pesoStr = txtPeso.getText();
    String sexo = cbxSexo.getValue();

    if (nombre.isEmpty() || tipo.isEmpty() || edadStr.isEmpty() || pesoStr.isEmpty() || sexo.isEmpty()) {
        mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
        return;
    }

    int edad = (int) Double.parseDouble(edadStr);
    double peso = Double.parseDouble(pesoStr);


    try(Connection con=Conexion.getConexion();
        PreparedStatement ps = con.prepareStatement(sql)){

        ps.setInt(6, idSeleccionado);

        ps.setString(1, nombre);
        ps.setString(2,  tipo);
        ps.setInt(3, edad);
        ps.setDouble(4, peso);
        ps.setString(5, sexo);

        ps.executeUpdate();
        mostrar();

    }catch (SQLException e){
        mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
        return;
    }

}

@FXML
private void limpiar() {
    try {
        txtNombre.clear();
        txtEdad.clear();
        txtPeso.clear();
        txtTipo.clear();
        cbxSexo.setValue(null);
    } catch (Exception e) {
        return;
    }
}
}
parte 3