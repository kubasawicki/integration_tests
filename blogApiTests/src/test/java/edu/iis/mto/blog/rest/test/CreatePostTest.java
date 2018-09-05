package edu.iis.mto.blog.rest.test;

 import com.jayway.restassured.RestAssured;
 import com.jayway.restassured.http.ContentType;
 import org.apache.http.HttpStatus;
 import org.json.JSONObject;
 import org.junit.Test;

 public class CreatePostTest extends FunctionalTests {

     public static final String CONFIRMED_USER = "/blog/user/1/post";
     public static final String NEW_USER = "/blog/user/2/post";

     @Test
     public void addingPostByCONFIRMEDUserReturnsCreatedStatus() {
         JSONObject jsonObj = new JSONObject().put("entry", "Test entry");

         RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                 .body(jsonObj.toString()).expect().log().all().statusCode(HttpStatus.SC_CREATED).when().post(CONFIRMED_USER);
     }

     @Test
     public void addingPostByNewUserReturnBadRequest() {
         JSONObject jsonObj = new JSONObject().put("entry", "Test");
         RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                 .body(jsonObj.toString()).expect().log().all().statusCode(HttpStatus.SC_BAD_REQUEST).when().post(NEW_USER);

     }
 }