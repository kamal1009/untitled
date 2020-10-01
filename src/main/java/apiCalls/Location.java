package apiCalls;

import enums.Routes;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import java.util.HashMap;
import java.util.Map;

public class Location{
    private static final Logger logger = LogManager.getLogger(Common.class);
    static Map<String, String> userKey= new HashMap<String, String>();
    static final Header userKeyHeader = new Header("user-key","37e12c494f70f04bff6b665d638afa9b");
    public  Location() {
        userKey.put("user-key","37e12c494f70f04bff6b665d638afa9b");
    }
    private static final String baseUrl ="https://developers.zomato.com/api/v2.1";

    public static Response getLocation(Map<String,Object> qParam,int statusCode) {
        RestAssured.baseURI = baseUrl;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header(userKeyHeader);
        httpRequest.params(qParam);
        Response response = httpRequest.request(Method.GET, Routes.GET_LOCATION.getUrl());
        logger.info(httpRequest.toString());
        logger.info(response.prettyPrint());
        Assert.assertEquals(response.getStatusCode(),statusCode);
        return response;
    }

    public static Response getLocationDetails(Map<String,Object> qParam,int statusCode) {
        RestAssured.baseURI = baseUrl;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header(userKeyHeader);
        httpRequest.params(qParam);
        Response response = httpRequest.request(Method.GET, Routes.GET_LOCATION_DETAILS.getUrl());
        logger.info(httpRequest.toString());
        logger.info(response.prettyPrint());
        Assert.assertEquals(response.getStatusCode(),statusCode);
        return response;
    }

    public static Response searchRestaurant(Map<String,Object> qParam,int statusCode) {
        RestAssured.baseURI = baseUrl;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header(userKeyHeader);
        httpRequest.params(qParam);
        Response response = httpRequest.request(Method.GET, Routes.SEARCH_RESTAURANT.getUrl());
        logger.info(httpRequest.toString());
        logger.info(response.prettyPrint());
        Assert.assertEquals(response.getStatusCode(),statusCode);
        return response;
    }

    public static Response getRestaurantDetails(Map<String,Object> qParam,int statusCode) {
        RestAssured.baseURI = baseUrl;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header(userKeyHeader);
        httpRequest.params(qParam);
        Response response = httpRequest.request(Method.GET, Routes.GET_RESTAURANT.getUrl());
        logger.info(httpRequest.toString());
        logger.info(response.prettyPrint());
        Assert.assertEquals(response.getStatusCode(),statusCode);
        return response;
    }

    public static Response getRestaurantReview(Map<String,Object> qParam,int statusCode) {
        RestAssured.baseURI = baseUrl;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header(userKeyHeader);
        httpRequest.params(qParam);
        Response response = httpRequest.request(Method.GET, Routes.GET_REVIEW.getUrl());
        logger.info(httpRequest.toString());
        logger.info(response.prettyPrint());
        Assert.assertEquals(response.getStatusCode(),statusCode);
        return response;
    }

    public static Response getLocation(Map<String,Object> qParam) {
        return getLocation(qParam,200);
    }

    public static Object getCityDetails(String cityName, String path) {
        Map<String, Object> qParam = new HashMap<String, Object>();
        qParam.put("query", cityName);
        Response response =getLocation(qParam);
        return response.jsonPath().get("location_suggestions[0]."+path);
    }
}
