package net.xravn.examen2.model.sql;

import java.sql.Connection;
import java.sql.DriverManager;

import net.xravn.examen2.controller.configuration.ConfigurationManager;

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
            ConfigurationManager config = ConfigurationManager.getInstance();
            String url;
            if (!testMode) {
                url = generateURL(config.getDatabaseHost(), config.getDatabasePort());
            } else {
                url = generateURL(config.getTestDatabaseHost(), config.getTestDatabasePort());
            }
            connection = DriverManager.getConnection(url + database + parameters, user, password);
        } catch (Exception ex) {
            System.err.println("Error al conectar a la base de datos");
        }
        return connection;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    private static String generateURL(String host, Integer port) {
        return PROTOCOL + host + ":" + port + "/";
    }

    private boolean testMode = false;

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String PROTOCOL = "jdbc:mysql://";
    private static final String parameters = "?useTimezone=true&serverTimezone=UTC";

    private static DBConnection instance;

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

}
