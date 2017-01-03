/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.beans;

import com.mysql.jdbc.Connection;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import nu.te4.support.ConnectionFactory;

/**
 *
 * @author guan97005
 */
@Stateless
public class RecipeBean {
    public JsonArray getRecipes() {
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM recept";
            ResultSet data = stmt.executeQuery(sql);
            //arraybuilder
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            while (data.next()) {
                int id = data.getInt("id");
                String name = data.getString("name");
                int categori = data.getInt("categori_id");
                String desc = data.getString("description");
                String instruction = data.getString("instruction");
                int picture = data.getInt("picture");

                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("id", id)
                        .add("name", name)
                        .add("categori_id", categori)
                        .add("description", desc)
                        .add("instruction", instruction)
                        .add("picture", picture).build());
            }
            connection.close();
            return jsonArrayBuilder.build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    
    
}
