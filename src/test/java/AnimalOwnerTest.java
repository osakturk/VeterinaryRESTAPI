package java;

import com.jayway.restassured.response.Header;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class AnimalOwnerTest extends BaseTest {
    @Test
    public void it_can_get_animals() {
        given()
                .header(new Header("Authorization", "Bearer" + " " + "{CREATED_TOKEN_FROM_USER_LOGIN}"))
            .when()
                .get(fillUrl("/owners"))
            .then()
                .statusCode(HttpStatus.CREATED);
    }
}
