/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.services;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.te4.support.User;

/**
 *
 * @author guan97005
 */
@Path("")
public class LoginService {
    
    @GET
    @Path("login")
    public Response checkLogin(@Context HttpHeaders httpHeaders) {
        if (!User.authoricate(httpHeaders)) {
            return Response.status(401).build();
        }
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder().add("admin", true);
        
        return Response.ok(jsonObjectBuilder.build()).build();
    }
    
    @POST
    @Path("createUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String body){
        if (!User.createUser(body)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

}
