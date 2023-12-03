package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection conn = null;

    // Método para obter uma conexão com o banco de dados
    public static Connection getConnection() {
        if (conn == null) {
            try {
                // Carrega o driver JDBC do MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Carrega as propriedades do arquivo db.properties
                Properties props = loadProperties();

                // Obtém a URL do banco de dados e estabelece a conexão
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            } catch (ClassNotFoundException | SQLException e) {
                // Em caso de erro, lança uma exceção personalizada (DbException)
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    // Método para fechar a conexão com o banco de dados
    public static void closeConnection() {
        if (conn != null) {
            try {
                // Fecha a conexão se estiver aberta
                conn.close();
            } catch (SQLException e) {
                // Em caso de erro SQL ao fechar a conexão, lança uma exceção personalizada (DbException)
                throw new DbException(e.getMessage());
            }
        }
    }

    // Método privado para carregar as propriedades do arquivo db.properties
    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            // Cria um objeto Properties e carrega as propriedades do arquivo de entrada
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            // Em caso de erro de leitura do arquivo, lança uma exceção personalizada (DbException)
            throw new DbException(e.getMessage());
        }
    }

    // Método para fechar um objeto Statement
    public static void closeStatement(Statement st) throws SQLException {
        if (st != null) {
            // Fecha o Statement se estiver aberto
            st.close();
        }
    }

    // Método para fechar um objeto ResultSet
    public static void closeResultSet(ResultSet rs) throws SQLException {
        if (rs != null) {
            // Fecha o ResultSet se estiver aberto
            rs.close();
        }
    }
}
