package net.xravn.examen2.controller.configuration;

import java.io.File;

import com.moandjiezana.toml.Toml;

import ch.qos.logback.classic.Logger;
import net.xravn.examen2.model.util.ResourceManager;

public class ConfigurationManager {

    private ConfigurationManager() {
        if (!configExists()) {
            System.out.println("No se encontro el archivo de configuracion, creando...");
            initDefaultConfig();
        }
        try {
            toml = new Toml().read(new File(getConfigFilePath()));
            System.out.println("cargadas configuraciones en : " + getConfigFilePath());
        } catch (Exception ex) {
            logger.error("Error al leer el archivo de configuracion", ex);
            toml = null;
        }
    }

    /**
     * check if server config file exists
     * 
     * @return true if exists
     */
    public static boolean configExists() {
        File file = new File(getConfigFilePath());
        return file.isFile();
    }

    public static String getConfigFilePath() {
        // try to load the enviroment variable
        String path = System.getenv("APP_CONFIG_FILE_PATH");
        return path == null ? configFile : path;
    }

    /**
     * create the default server configuration file
     */
    private void initDefaultConfig() {
        try {
            ResourceManager.ExportResource(configResourceFile, getConfigFilePath());
        } catch (Exception e) {
            logger.error("Error al inicializar el archivo de configuracion por defecto");
        }
    }

    public Integer getWebServerPort() {
        return toml.getLong("web_server.port").intValue();
    }

    public String getDatabaseHost() {
        return toml.getString("database.host");
    }

    public Integer getDatabasePort() {
        return toml.getLong("database.port").intValue();
    }

    public String getDatabaseSchema() {
        return toml.getString("database.schema");
    }

    public String getDatabaseUser() {
        return toml.getString("database.credentials.user");
    }

    public String getDatabasePassword() {
        return toml.getString("database.credentials.password");
    }

    public String getTestDatabaseHost() {
        return toml.getString("database.host");
    }

    public Integer getTestDatabasePort() {
        return toml.getLong("database.port").intValue();
    }

    public String getTestDatabaseSchema() {
        return toml.getString("database.schema");
    }

    public String getTestDatabaseUser() {
        return toml.getString("unit-tests.database.credentials.user");
    }

    public String getTestDatabasePassword() {
        return toml.getString("unit-tests.database.credentials.password");
    }

    public Boolean ommitDBTests() {
        return toml.getBoolean("unit-tests.ommit_db_tests");
    }

    public boolean printStackTrace() {
        return toml.getBoolean("logging.print_stack_trace");
    }

    private Toml toml;
    private static String configFile = "server.toml";
    private static String configResourceFile = "/configuration/server.toml";
    private static Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(ConfigurationManager.class);

    private static ConfigurationManager instance = null;

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

}
