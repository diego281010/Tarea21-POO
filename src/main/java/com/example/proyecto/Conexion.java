package com.example.proyecto;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class Conexion {
    private static final String url = "jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:6543/postgres";
    private static final String user = "postgres.ayxwwfljwnpxtedayfgz";
    private static final String password = "#Grupal2026";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
