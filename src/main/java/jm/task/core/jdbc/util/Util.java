package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Connection connection = null;
    private static Util instance = null;


        private Util() throws SQLException {
        try{
            if (connection == null || connection.isClosed()){
                Properties properties = getProps();
                connection = DriverManager
                        .getConnection(properties.getProperty("db.url"), properties.getProperty("db.username"),
                                properties.getProperty("db.password"));
            }
        } catch (SQLException e) {
            System.out.println("Problem with Util in Util");
            throw new RuntimeException(e);
        }
    }
    private Properties getProps() {
        Properties properties = new Properties();
        try(InputStream inputStream =
                    Files.newInputStream(Paths.get(Util.class.getResource("/database.properties").toURI()))){
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            System.out.println("Problem with new InputStream load(inputStream) in Util, getProps()");
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            System.out.println("Problem with URL in Util, getProps()");
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Util getInstance() {
        if (instance == null){
            try {
                instance = new Util();
            } catch (SQLException e) {
                System.out.println("Problem with getInstance in Util");
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
}
