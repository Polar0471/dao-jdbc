package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

    private static Connection conn = null;
    
    // os métodos estáticos auxiliares irão servir para obter e fechar uma conexão

    public static Connection getConnection() {
        if (conn == null) {
            try {
                // pega as propriedades e estabelecer a conexão com o banco
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            }
            catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }

        return conn;
    }

    // encerra a conexão com o banco
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    // carrega as propriedades que estão salvas do db.properties
    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);                             
            return props;
        } 
        catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    // método criado para fechar um objeto do tipo statement
    public static void closeStatement(Statement st) {
        if (st != null)
            try {
                st.close();
            } 
            catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
    }

    // método criado para fechar o ResultSet
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } 
            catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    
}
