package java;

import com.jayway.restassured.response.Header;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class AnimalTest extends BaseTest {
    @Test
    public void it_can_get_animals() {
        given()
                .header(new Header("Authorization", "Bearer" + " " + "{CREATED_TOKEN_FROM_USER_LOGIN}"))
                .param("{PARAMETER_KEY}", "{PARAMETER VALUE}")
                .when()
                .get(fillUrl("/animals"))
                .then()
                .statusCode(HttpStatus.CREATED);
    }
}
