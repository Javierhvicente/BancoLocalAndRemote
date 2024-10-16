package org.example.creditcard.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.config.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que gestiona la conexion a una base de datos utilizando HikariCP.
 * Esta clase proporciona metodos para conectar y desconectar de la base de datos,
 * asi como para obtener una conexion a la base de datos.
 * @author Raul Fernandez, Alvaro Herrero, Javier Ruiz, Javier Hernandez, Samuel Cortes, Yahya El Hadri.
 * @since 1.0
 */

public class DataBaseManager implements AutoCloseable {
    private static DataBaseManager instance = null;
    private HikariDataSource dataSource;
    private final Logger logger = LoggerFactory.getLogger(DataBaseManager.class);
    private String DB_URL = "jdbc:postgresql://localhost:5432/credit-card";
    private String DB_USER = "admin"; // reemplaza con tu usuario
    private String DB_PASSWORD = "adminPassword123"; // reemplaza con tu contrasena
    private String DB_Timeout = "10000";
    private Connection connection = null;

    protected DataBaseManager() {
    }

    /**
     * Constructor que recibe la configuracion de la base de datos.
     * @author Raul Fernandez, Alvaro Herrero, Javier Ruiz, Javier Hernandez, Samuel Cortes, Yahya El Hadri.
     * @since 1.0
     * @param config configuracion de la base de datos
     */

    private DataBaseManager(ConfigProperties config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getProperty("database.url", DB_URL));
        hikariConfig.setUsername(config.getProperty("database.user", DB_USER));
        hikariConfig.setPassword(config.getProperty("database.password", DB_PASSWORD));
        hikariConfig.setConnectionTimeout(Long.parseLong(config.getProperty("database.timeout", DB_Timeout)));
        this.dataSource = new HikariDataSource(hikariConfig);
        logger.info("Hikari configurado correctamente (con valores predeterminados)...");
    }

    /**
     * Obtiene la instancia unica de la clase.
     * @author Raul Fernandez, Alvaro Herrero, Javier Ruiz, Javier Hernandez, Samuel Cortes, Yahya El Hadri.
     * @since 1.0
     * @return instancia unica de la clase
     */

    public static DataBaseManager getInstance() {
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }

    /**
     * Obtiene la instancia unica de la clase utilizando la configuracion proporcionada.
     * @author Raul Fernandez, Alvaro Herrero, Javier Ruiz, Javier Hernandez, Samuel Cortes, Yahya El Hadri.
     * @since 1.0
     * @param config configuracion de la base de datos
     * @return instancia unica de la clase
     */

    public static DataBaseManager getInstance(ConfigProperties config) {
        if (instance == null) {
            instance = new DataBaseManager(config);
        }
        return instance;
    }

    /**
     * Establece una conexion a la base de datos.
     * @author Raul Fernandez, Alvaro Herrero, Javier Ruiz, Javier Hernandez, Samuel Cortes, Yahya El Hadri.
     * @since 1.0
     * @return conexion a la base de datos
     * @throws SQLException si ocurre un error al conectar a la base de datos
     */

    public Connection connect() throws SQLException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Error al conectar a la base de datos", e);
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }

    /**
     * Cierra la conexion a la base de datos.
     * @author Raul Fernandez, Alvaro Herrero, Javier Ruiz, Javier Hernandez, Samuel Cortes, Yahya El Hadri.
     * @since 1.0
     */

    public void disconnect() {
        if (connection != null) {
            dataSource.close();
            connection = null;
            logger.info("Desconectado de la base de datos...");
        }
    }

    /**
     * Cierra la conexion a la base de datos y libera los recursos.
     * @author Raul Fernandez, Alvaro Herrero, Javier Ruiz, Javier Hernandez, Samuel Cortes, Yahya El Hadri.
     * @since 1.0
     * @throws Exception si ocurre un error al cerrar la conexion
     */

    @Override
    public void close() throws Exception {
        disconnect();
    }
}
