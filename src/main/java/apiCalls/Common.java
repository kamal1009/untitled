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

public class Common {
    private static final Logger logger = LogManager.getLogger(Common.class);
    static Map<String, String> userKey= new HashMap<String, String>();
    static final Header userKeyHeader = new Header("user-key","37e12c494f70f04bff6b665d638afa9b");
    public  Common() {

        userKey.put("user-key","37e12c494f70f04bff6b665d638afa9b");
    }

    private static final String baseUrl ="https://developers.zomato.com/api/v2.1";

    public static Response getCategories() {
        RestAssured.baseURI = baseUrl;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header(userKeyHeader);
        Response response = httpRequest.request(Method.GET, Routes.GET_CATEGORIES.getUrl());
        logger.info(response.prettyPrint());
        Assert.assertEquals(response.getStatusCode(),200);
        return response;
    }

    public static Response getCities(Map<String,Object> qParam) {
        return getCities(qParam,200);
    }

    public static Response getCities(Map<String,Object> qParam,int statusCode){
        RestAssured.baseURI = baseUrl;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header(userKeyHeader);
        httpRequest.params(qParam);
        Response response = httpRequest.request(Method.GET, Routes.GET_CITIES.getUrl());
        logger.info(httpRequest.toString());
        logger.info(response.prettyPrint());
        Assert.assertEquals(response.getStatusCode(),statusCode);
        return response;
    }

    public static Response getCollection(Map<String,Object> qParam,int statusCode) {
        RestAssured.baseURI = baseUrl;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header(userKeyHeader);
        httpRequest.params(qParam);
        Response response = httpRequest.request(Method.GET, Routes.GET_COLLECTION.getUrl());
        logger.info(httpRequest.toString());
        logger.info(response.prettyPrint());
        Assert.assertEquals(response.getStatusCode(),statusCode);
        return response;
    }

    public static Response getCollection(Map<String,Object> qParam) {
        return getCollection(qParam,200) ;
    }
    //    public static void main(String[] args) {
//        Map<String,Object> qParam = new HashMap<String, Object>();
//        qParam.put("lat",Cities.BANGALORE.getLatitude());
//        qParam.put("lon",Cities.BANGALORE.getLongitude());
//        getCities(qParam);
//    }

}
