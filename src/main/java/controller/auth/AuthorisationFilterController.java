package controller.auth;

import helper.HttpResponseStatusException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.http.HttpStatus;
import org.glassfish.jersey.server.ContainerRequest;

import javax.annotation.Priority;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.security.Key;

@Provider
@Authorised //Annotation binding
@Priority(Priorities.AUTHORIZATION) //Priority
public class AuthorisationFilterController extends BaseController implements ContainerRequestFilter {

    @Context
    HttpServletRequest request;
    MultivaluedMap<String, String> form;


    /**
     * Authorisation filter for reports and definition end-points.
     *
     * @param containerRequestContext request context
     */
    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String username;
        String pageId;
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String token               = authorizationHeader.substring("Bearer".length()).trim();

        Object[] pathParams = containerRequestContext.getUriInfo().getPathParameters().values().toArray();
        pageId = containerRequestContext.getUriInfo().getPath();

        for (Object object : pathParams) {
            pageId = pageId.replaceAll("." + object.toString().substring(1, object.toString().length() - 1), "");
        }

        try {
            byte[] decodedKey = this.getApiKeyBinary();
            Key    key        = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);

            username = jws.getBody().getSubject();
            System.out.println("### Valid token for user: " + username + "\t");


        } catch (Exception e) {
            System.err.println("### Invalid token ");
            e.printStackTrace();
            throw new HttpResponseStatusException(HttpStatus.SC_UNAUTHORIZED, "Sayfaya Giriş Yetkiniz Bulunmamaktadır", Response.Status.Family.CLIENT_ERROR);
        }
        ContainerRequest containerRequest = (ContainerRequest) containerRequestContext;
        containerRequest.bufferEntity();

    }

}
