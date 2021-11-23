package net.xravn.examen2.model.sql;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Clase que permite la conexion a la base de datos
 * 
 */
public class DBConnection {

    private DBConnection() {
        try {
            Class.forName(DRIVER).getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            System.err.println("Error al cargar el driver");
        }
    }

    public Connection getConnection(String database, String user, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL + database + parameters, user, password);
        } catch (Exception ex) {
            System.err.println("Error al conectar a la base de datos");
        }
        return connection;
    }

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String PROTOCOL = "jdbc:mysql://";
    private static final String PORT = "3306";
    private static final String HOST = "localhost";
    private static final String parameters = "?useTimezone=true&serverTimezone=UTC";
    private static final String URL = PROTOCOL + HOST + ":" + PORT + "/";// + DATABASE + parameters;

    private static DBConnection instance;

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
}
