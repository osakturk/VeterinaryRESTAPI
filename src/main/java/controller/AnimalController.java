package controller;

import controller.auth.Authorised;
import org.json.JSONObject;
import provider.AnimalProvider;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;

@Authorised
@Path("/animals")
public class AnimalController {
    /**
     * Get animal list.
     *
     * @param request HttpServletRequest
     * @param form    request form data for report filters
     * @return binary data stream
     */
    @GET
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_OCTET_STREAM)
    public Response index(@Context HttpServletRequest request, MultivaluedMap<String, String> form) {
        JSONObject result = AnimalProvider.getAnimalList(request, form);
        return Response.ok().header("Server", "Doruk Web Server").header("X-XSS-Protection", "1; mode=block").header("X-Frame-Options", "DENY").entity(result.toString()).build();
    }

    /**
     * Get a single animal.
     *
     * @param request HttpServletRequest
     * @param form    request form data for report filters
     * @return binary data stream
     */
    @GET
    @Path("{id}")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_OCTET_STREAM)
    public Response show(@Context HttpServletRequest request, MultivaluedMap<String, String> form, @PathParam("id") Integer id) {
        JSONObject result = AnimalProvider.getSingleAnimal(request, form, id);
        return Response.ok().header("Server", "Doruk Web Server").header("X-XSS-Protection", "1; mode=block").header("X-Frame-Options", "DENY").entity(result.toString()).build();
    }

    /**
     * Create new animal.
     *
     * @param request HttpServletRequest
     * @param form    request form data for report filters
     * @return binary data stream
     */
    @POST
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_OCTET_STREAM)
    public Response create(@Context HttpServletRequest request, MultivaluedMap<String, String> form) {
        JSONObject result = AnimalProvider.createNewAnimal(request, form);
        return Response.created(URI.create("/animals")).header("Server", "Doruk Web Server").header("X-XSS-Protection", "1; mode=block").header("X-Frame-Options", "DENY").entity(result.toString()).build();
    }

    /**
     * Update animal.
     *
     * @param request HttpServletRequest
     * @param form    request form data for report filters
     * @return binary data stream
     */
    @PUT
    @Path("{id}")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_OCTET_STREAM)
    public Response update(@Context HttpServletRequest request, MultivaluedMap<String, String> form, @PathParam("id") Integer id) {
        JSONObject result = AnimalProvider.updateAnimal(request, form, id);
        return Response.accepted().header("Server", "Doruk Web Server").header("X-XSS-Protection", "1; mode=block").header("X-Frame-Options", "DENY").entity(result.toString()).build();
    }

    /**
     * Delete animal.
     *
     * @param request HttpServletRequest
     * @param form    request form data for report filters
     * @return binary data stream
     */
    @DELETE
    @Path("{id}")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_OCTET_STREAM)
    public Response delete(@Context HttpServletRequest request, MultivaluedMap<String, String> form, @PathParam("id") Integer id) {
        JSONObject result = AnimalProvider.deleteAnimal(id);
        return Response.accepted().header("Server", "Doruk Web Server").header("X-XSS-Protection", "1; mode=block").header("X-Frame-Options", "DENY").entity(result.toString()).build();
    }
}
