package com.example.proyecto;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class Conexion {
    private static final String url = "jdbc:postgresql://db.ayxwwfljwnpxtedayfgz.supabase.co:5432/postgres?sslmode=require";
    private static final String user = "postgres";
    private static final String password = "#Grupal2026";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
