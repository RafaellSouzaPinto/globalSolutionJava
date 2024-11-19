package org.example.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() throws SQLException {
        // Obtém as variáveis de ambiente
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");

        // Retorna a conexão utilizando as variáveis de ambiente
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
