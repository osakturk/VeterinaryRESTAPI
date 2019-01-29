package java;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class LoginTest extends BaseTest {
    @Test
    public void can_login() {


        given()
                .param(username, usernameParam)
                .param(password, passwordParam)
                .when()
                .post(fillUrl(this.urlLogin))
                .then()
                .statusCode(STATUS_CODE_SUCCESS)
                .body("locale", equalTo("tr"));

    }

    @Test
    public void test_failed_login() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[]        bytesOfUser   = user.getBytes("UTF-8");
        byte[]        bytesOfPass   = pass.getBytes("UTF-8");
        MessageDigest mdpass        = MessageDigest.getInstance("MD5");
        MessageDigest mduser        = MessageDigest.getInstance("MD5");
        byte[]        thedigest     = mduser.digest(bytesOfUser);
        byte[]        thedigestpass = mdpass.digest(bytesOfPass);

        given()
                .param(username, thedigest)
                .param(password, thedigestpass)
                .when()
                .post(fillUrl(this.urlLogin))
                .then()
                .statusCode(STATUS_CODE_SUCCESS)
                .body("status", equalTo(HttpStatus.UNPROCESSABLE_ENTITY));

    }
}
