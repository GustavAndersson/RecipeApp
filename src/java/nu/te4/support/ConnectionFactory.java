/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.support;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

/**
 *
 * @author guan97005
 */
public class ConnectionFactory {
    public static Connection make(String server) throws Exception {
        if (server.equals("127.0.0.1")) {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/project4", "root", "");
            return connection;
        }

        return null;
    }
    
}
