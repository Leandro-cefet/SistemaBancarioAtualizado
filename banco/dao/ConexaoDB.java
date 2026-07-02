package banco.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoDB {

    private static Connection conexao;

    private static String url;

    private static String usuario;

    private static String senha;

    private ConexaoDB() {

    }

    static {

        try {

            Properties propriedades = new Properties();

            propriedades.load(new FileInputStream("resources/db.properties"));

            url = propriedades.getProperty("db.url");

            usuario = propriedades.getProperty("db.usuario");

            senha = propriedades.getProperty("db.senha");

        }

        catch (IOException e) {
        }

    }

    public static Connection getConexao() {

        try {

            if (conexao == null || conexao.isClosed()) {

                conexao = DriverManager.getConnection(

                        url,

                        usuario,

                        senha);

            }

        }

        catch (SQLException e) {
        }

        return conexao;

    }

}