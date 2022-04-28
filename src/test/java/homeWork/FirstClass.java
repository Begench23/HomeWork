package homeWork;

import homeWork.pojos.Code;
import homeWork.pojos.Places;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FirstClass {

    @BeforeAll
    public static void setupTest(){

        RestAssured.baseURI = "https://api.zippopotam.us";
    }

    @DisplayName("First Test")
    @Test
    public void test1(){

        Response resp = RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("zipCode", "22031")
                .when()
                .get("us/{zipCode}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();

        boolean equals = resp.headers().getValue("Server").equals("cloudflare");
        Assertions.assertTrue(equals);
        MatcherAssert.assertThat(resp.headers().hasHeaderWithName("Report-To"), Matchers.notNullValue());

        Code code1 = resp.body().as(Code.class);

        MatcherAssert.assertThat(code1.getPostCode(), Matchers.equalTo("22031"));
        MatcherAssert.assertThat(code1.getCountry(), Matchers.equalTo("United States"));
        MatcherAssert.assertThat(code1.getCountryAbbreviation(), Matchers.equalTo("US"));

        Assertions.assertEquals(code1.getPlacess().get(0).getPlaceName(), "Fairfax");
        Assertions.assertEquals(code1.getPlacess().get(0).getState(), "Virginia");
        Assertions.assertEquals(code1.getPlacess().get(0).getLatitude(), "38.8604");

    }

    @Test
    public void test12(){

        RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("zippo", 50000)
                .when()
                .get("/us/{zippo}")
                .then()
                .assertThat().statusCode(404)
                .and()
                .contentType("application/json");
    }

    @Test
    public void test3(){

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .pathParam("state", "va")
                .pathParam("CITY", "fairfax")
                .when()
                .get("/us/{state}/{CITY}")
                .then()
                .statusCode(200)
               .contentType("application/json")
//                .body("places.['place name']", Matchers.everyItem(
//                        Matchers.containsStringIgnoringCase("fairfax")))
                .extract().response();




        JsonPath jP = response.jsonPath();
        Code code2 = jP.getObject("", Code.class);

        MatcherAssert.assertThat(code2.getCountryAbbreviation(),
                Matchers.equalTo("US"));
    MatcherAssert.assertThat(code2.getCountry() ,
                Matchers.equalTo("United States") );
           MatcherAssert.assertThat(code2.getPlaceName(),
                Matchers.equalTo("Fairfax")  );


        for (Places each : code2.getPlacess()) {

            MatcherAssert.assertThat(each.getPlaceName(), Matchers.containsStringIgnoringCase("fairfax"));
            Assertions.assertTrue(each.getPostCode().startsWith("22"));
        }



    }


}
