/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.services;

import javax.ejb.EJB;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.te4.beans.RecipeBean;
import nu.te4.support.User;


/**
 *
 * @author guan97005
 */
@Path("")
public class RecipeService {

    @EJB
    RecipeBean recipeBean;

    @GET
    @Path("/recipes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipes() {
        JsonArray data = recipeBean.getRecipes();

        if (data == null) {
            return Response.serverError().build();
        }

        return Response.ok(data).build();

    }
    
    @GET
    @Path("/viewRecipes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getView() {
        JsonArray data = recipeBean.getView();

        if (data == null) {
            return Response.serverError().build();
        }

        return Response.ok(data).build();

    }
    
    @GET
    @Path("/ingredients/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipeIngredients(@PathParam("id")int id) {
        JsonArray data = recipeBean.getRecipeIngredients(id);

        if (data == null) {
            return Response.serverError().build();
        }

        return Response.ok(data).build();
    }
    
    @GET
    @Path("/recipe/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipe(@PathParam("id")int id) {
        JsonArray data = recipeBean.getRecipe(id);

        if (data == null) {
            return Response.serverError().build();
        }

        return Response.ok(data).build();
    }
    
    @GET
    @Path("/viewRecipe/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getViewRecipe(@PathParam("id")int id) {
        JsonArray data = recipeBean.getViewRecipe(id);

        if (data == null) {
            return Response.serverError().build();
        }

        return Response.ok(data).build();
    }
    
    
    @GET
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories() {
        JsonArray data = recipeBean.getCategories();

        if (data == null) {
            return Response.serverError().build();
        }

        return Response.ok(data).build();
    }
    
        
    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id")int id) {
        JsonArray data = recipeBean.getUser(id);

        if (data == null) {
            return Response.serverError().build();
        }

        return Response.ok(data).build();
    }
    
    @GET
    @Path("/recipes/cat/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipes_category(@PathParam("id")int id) {
        JsonArray data = recipeBean.getRecipes_category(id);

        if (data == null) {
            return Response.serverError().build();
        }

        return Response.ok(data).build();

    }
    
    @POST
    @Path("recipe")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRecipe(String body, @Context HttpHeaders httpHeaders){
        if (!User.authoricate(httpHeaders)) {
            return Response.status(401).build();
        }
        if (!recipeBean.addRecipe(body,httpHeaders)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }
    
    @POST
    @Path("add/ingredient")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addIngredient(String body, @Context HttpHeaders httpHeaders){
        if (!User.authoricate(httpHeaders)) {
            return Response.status(401).build();
        }
        if (!recipeBean.addIngredient(body)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }
    
    @POST
    @Path("add/ingredientToRecipe")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addIngredientToRecipe(String body, @Context HttpHeaders httpHeaders){
        if (!User.authoricate(httpHeaders)) {
            return Response.status(401).build();
        }
        if (!recipeBean.addIngredientToRecipe(body)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }
    
    @DELETE
    @Path("recipe/{id}")
    public Response deleteRecipe(@PathParam("id") int id, @Context HttpHeaders httpHeaders) {
        if (!User.authoricate(httpHeaders)) {
            return Response.status(401).build();
        }
        if (!recipeBean.deleteRecipe(id)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok().build();
    }
    
    @DELETE
    @Path("ingredient/{id}")
    public Response deleteIngredient(@PathParam("id") int id, @Context HttpHeaders httpHeaders) {
        if (!User.authoricate(httpHeaders)) {
            return Response.status(401).build();
        }
        if (!recipeBean.deleteIngredient(id)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok().build();
    }
    
    @DELETE
    @Path("ingredient/{recipeID}/{ingredientID}")
    public Response deleteIngFromRecipe(@PathParam("recipeID") int recipe_id, @PathParam("ingredientID") int ingredient_id, @Context HttpHeaders httpHeaders) {
        if (!User.authoricate(httpHeaders)) {
            return Response.status(401).build();
        }
        if (!recipeBean.deleteIngFromRecipe(recipe_id, ingredient_id)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
    }
    
    @PUT
    @Path("recipe")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRecipe(String body) {
        if (!recipeBean.updateRecipe(body)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok().build();
    }
}
