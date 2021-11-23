package net.xravn.examen2.controller.sql;

import java.sql.Connection;
import java.util.Properties;

import net.xravn.examen2.model.sql.DBConnection;

/**
 * Clase que permite la conexion con la base de datos
 * 
 */
public class DBConectionController {

    private DBConectionController() {
        try {
            this.properties.load(getClass().getResourceAsStream(CONFIG_PATH));
            this.database = properties.getProperty("database");
            this.user = properties.getProperty("user");
            this.password = properties.getProperty("password");
        } catch (Exception ex) {
            System.err.println("Error al cargar el archivo de configuracion");
        }
    }

    public Connection getConnection() {
        try {
            return DBConnection.getInstance().getConnection(this.database, this.user, this.password);
        } catch (Exception ex) {
            System.err.printf("No se pudo conectar con la base de datos: %s\n", ex.getLocalizedMessage());
            return null;
        }
    }

    private static final String CONFIG_PATH = "/config/database.properties";
    private Properties properties = new Properties();
    private String database;
    private String user;
    private String password;

    private static DBConectionController instance = null;

    public static DBConectionController getInstance() {
        if (instance == null) {
            instance = new DBConectionController();
        }
        return instance;
    }
}
