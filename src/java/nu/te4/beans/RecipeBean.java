/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.beans;

import com.mysql.jdbc.Connection;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
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
                String picture = data.getString("picture");
                int byID = data.getInt("byID");

                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("id", id)
                        .add("name", name)
                        .add("categori_id", categori)
                        .add("description", desc)
                        .add("instruction", instruction)
                        .add("picture", picture)
                        .add("byID", byID).build());
            }
            connection.close();
            return jsonArrayBuilder.build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public JsonArray getRecipeIngredients(int id) {
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            Statement stmt = connection.createStatement();
            String sql = "SELECT r_i.amount,(SELECT ingredienser.name FROM ingredienser where ingredienser.id = r_i.ingredient_id) AS ing FROM r_i WHERE r_i.recipe_id=" + id;
            ResultSet data = stmt.executeQuery(sql);
            //arraybuilder
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            while (data.next()) {
                String amount = data.getString("amount");
                String name = data.getString("ing");

                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("amount", amount)
                        .add("ing", name).build());
            }
            connection.close();
            return jsonArrayBuilder.build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public JsonArray getRecipe(int id) {
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM recept where id=" +id;
            ResultSet data = stmt.executeQuery(sql);
            //arraybuilder
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            while (data.next()) {
                String name = data.getString("name");
                String description = data.getString("description");
                String instruction = data.getString("instruction");
                String picture = data.getString("picture");
                int byID = data.getInt("byID");

                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("name", name)
                        .add("description", description)
                        .add("instruction", instruction)
                        .add("picture", picture)
                        .add("byID", byID).build());
            }
            connection.close();
            return jsonArrayBuilder.build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public JsonArray getCategories() {
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM categori";
            ResultSet data = stmt.executeQuery(sql);
            //arraybuilder
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            while (data.next()) {
                int id = data.getInt("id");
                String name = data.getString("name");

                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("id", id)
                        .add("name", name).build());
            }
            connection.close();
            return jsonArrayBuilder.build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public JsonArray getRecipes_category(int id) {
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            String sql = "select recept.name from recept where recept.categori_id=(select categori.id from categori where id = ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet data = stmt.executeQuery();
            //arraybuilder
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            while (data.next()) {
                String name = data.getString("name");

                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("name", name).build());
            }
            connection.close();
            return jsonArrayBuilder.build();
        } catch (Exception ex) {
            System.out.println("ERROR: "+ex.getMessage());
        }
        return null;
    }
    
    public JsonArray getUsers() {
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM users";
            ResultSet data = stmt.executeQuery(sql);
            //arraybuilder
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            while (data.next()) {
                String name = data.getString("name");

                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("name", name).build());
            }
            connection.close();
            return jsonArrayBuilder.build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public boolean addRecipe(String body) {
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonObject data = jsonReader.readObject();
        jsonReader.close();
        String name = data.getString("name");
        int categori = data.getInt("categori_id");
        String desc = data.getString("description");
        String instruction = data.getString("instruction");
        String picture = data.getString("picture");
        int byID = data.getInt("byID");
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO recept VALUES (NULL,?,?,?,?,?,?)");
            stmt.setString(1, name);
            stmt.setInt(2, categori);
            stmt.setString(3, desc);
            stmt.setString(4, instruction);
            stmt.setString(5, picture);
            stmt.setInt(6, byID);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean addIngredientToRecipe(String body) {
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonObject data = jsonReader.readObject();
        jsonReader.close();
        int recipe_id = data.getInt("recipe_id");
        String amount = data.getString("amount");
        int ingredient_id = data.getInt("ingredient_id");
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO r_i VALUES (?,?,?)");
            stmt.setInt(1, recipe_id);
            stmt.setString(2, amount);
            stmt.setInt(3, ingredient_id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteRecipe(int id) {
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM recept WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            connection.close();
            return true;
        } catch (Exception ex) {
            System.out.println("Error:" + ex.getMessage());
        }
        return false;
    }
    
    public boolean deleteIngredient(int id) {
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM ingredienser WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            connection.close();
            return true;
        } catch (Exception ex) {
            System.out.println("Error:" + ex.getMessage());
        }
        return false;
    }
    
    public boolean deleteIngFromRecipe(int recipe_id, int ingredient_id) {
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM r_i WHERE r_i.recipe_id = ? AND r_i.ingredient_id = ?");
            stmt.setInt(1, recipe_id);
            stmt.setInt(2, ingredient_id);
            stmt.executeUpdate();
            connection.close();
            return true;
        } catch (Exception ex) {
            System.out.println("Error:" + ex.getMessage());
        }
        return false;
    }

    public boolean updateRecipe(String body) {
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonObject data = jsonReader.readObject();
        jsonReader.close();
        int id = data.getInt("id");
        String name = data.getString("name");
        int categori = data.getInt("categori_id");
        String desc = data.getString("description");
        String instruction = data.getString("instruction");
        String picture = data.getString("picture");
        int byID = data.getInt("byID");
        
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            PreparedStatement stmt = connection.prepareStatement("UPDATE recept SET name=?, categori_id=?, description=?, instruction=?, picture=?, byID=? WHERE id =?");
            stmt.setString(1, name);
            stmt.setInt(2, categori);
            stmt.setString(3, desc);
            stmt.setString(4, instruction);
            stmt.setString(5, picture);
            stmt.setInt(6, byID);
            stmt.setInt(7, id);
            stmt.executeUpdate();
            connection.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addIngredient(String body) {
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonObject data = jsonReader.readObject();
        jsonReader.close();
        String name = data.getString("name");
        try {
            Connection connection = ConnectionFactory.make("127.0.0.1");
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO ingredienser VALUES (NULL,?)");
            stmt.setString(1, name);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
