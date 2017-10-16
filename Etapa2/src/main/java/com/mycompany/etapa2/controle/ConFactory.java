/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.etapa2.controle;

/**
 *
 * @author Flavio
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Flavio
 */
public class ConFactory {

    private final String host;
    private final String user;
    private final String password;

    public ConFactory() {

        host = "jdbc:postgresql://127.0.0.1:5432/projetoPOO";
        user = "postgres";
        password = "flavio22";
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(host, user, password);
    }
}
