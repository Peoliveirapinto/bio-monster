/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biomons.bio.monsters;

/**
 *
 * @author segpr
 */
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    private static final String HOST = "mysql-mauapi-mauapedro.c.aivencloud.com";
    private static final String PORT = "12142";
    private static final String DATABASE = "mydb";
    private static final String USERNAME = "avnadmin";
    private static final String PASSWORD = "AVNS_P3LgFXeD5v28MDehrnQ";
    private static final String SSL_MODE = "REQUIRED";

    public static Connection obterConexao() {
        try {
            // Construindo a URL de conexão
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
                    + "?user=" + USERNAME + "&password=" + PASSWORD + "&sslMode=" + SSL_MODE;

            // Retorna a conexão utilizando a URL formatada
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}