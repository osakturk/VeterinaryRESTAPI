package controller.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONArray;
import org.json.JSONObject;
import repository.LoginRepository;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

@Path("/users")
public class UserController extends BaseController {
    /**
     * Manages user login process. If success returns response OK (200) status code with header AUTHORIZATION
     * with value "Bearer + 'token'" otherwise returns UNAUTHORIZED status code with empty response.
     *
     * @param username Username
     * @param password Password
     * @return Login attempt response.
     */
    @POST
    @Path("/login")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces("application/json;charset=utf-8")
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        try {

            if (authenticateUser(username, password)) {
                String token = issueToken(username);

                JSONObject object     = new JSONObject();
                JSONArray  extensions = new JSONArray();


                object.put("token", token);
                object.put("name", "");
                object.put("locale", "");
                object.put("profile_name", "");
                object.put("extensions", extensions);
                return Response.ok().entity(object.toString()).header(AUTHORIZATION, "Bearer " + token).build();
            } else {
                System.out.println("### Authenticate user failed for user: " + username);
                JSONObject object = new JSONObject();
                object.put("status", 422);
                return Response.ok().header("Server", "IPera Web Server").header("X-XSS-Protection", "1; mode=block").header("X-Frame-Options", "DENY").entity(object.toString()).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).header("Server", "IPera Web Server").header("X-XSS-Protection", "1; mode=block").header("X-Frame-Options", "DENY").build();
        }
    }

    /**
     * Checks user credentials for user over db.
     *
     * @param username username
     * @param password password
     * @return boolean result
     */
    private boolean authenticateUser(String username, String password) {
        int             userId;
        LoginRepository repository = new LoginRepository();

        userId = repository.authenticateUser(username, password);

        if (userId >= 0) {
            return true;
        }

        return false;
    }

    /**
     * Generates token for authenticated user.
     *
     * @param username JWT token subject name.
     * @return JWT token for user session.
     */
    private String issueToken(String username) {
        byte[] decodedKey = this.getApiKeyBinary();
        Key    key        = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        Date     now      = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        Date end = calendar.getTime();

        String jwtToken = Jwts.builder()
                .setSubject(username)
                .claim("authorisation", "ACCESS_PAGE_LIST")
                .setIssuer(username)
                .setIssuedAt(now)
                .setExpiration(end)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        return jwtToken;
    }
}
