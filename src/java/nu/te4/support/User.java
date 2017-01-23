/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.support;

import com.mysql.jdbc.Connection;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author guan97005
 */
public class User {
    public static boolean authoricate(HttpHeaders httpHeaders){
        try {
            List<String> authHeader = httpHeaders.getRequestHeader(HttpHeaders.AUTHORIZATION);
            String header = authHeader.get(0);
            header = header.substring(header.indexOf(" ")+1);
            byte[] decoded = Base64.getDecoder().decode(header);
            String userPass = new String(decoded);
            System.out.println(userPass); //Gustav:test
            //plocka ut anv och lösenord
            String username = userPass.substring(0,userPass.indexOf(":"));
            String password = userPass.substring(userPass.indexOf(":")+1);
            
            Connection connection = ConnectionFactory.make("127.0.0.1");
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE name =?");
            stmt.setString(1, username);
            ResultSet data = stmt.executeQuery();
            data.next();
            String hashedPass = data.getString("password");
            connection.close();
             
            return BCrypt.checkpw(password, hashedPass);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public static boolean createUser(String body){
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonObject data = jsonReader.readObject();
        String name = data.getString("name");
        String password = data.getString("password");
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        try{
            Connection connection = ConnectionFactory.make("127.0.0.1");
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO users VALUES(NULL,?,?)");
            stmt.setString(1, name);
            stmt.setString(2, hashedPassword);
            stmt.executeUpdate();
            connection.close();
            return true;
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
}