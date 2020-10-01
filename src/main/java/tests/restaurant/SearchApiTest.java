package tests.restaurant;

import util.Utilities;
import apiCalls.Location;
import constants.Constants;
import enums.Cities;
import enums.Locations;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

public class SearchApiTest {

    @DataProvider(name = "getCityByNameId")
    public static Object[][] getCityTestData() {
        return new Object[][]{
                {Locations.KORAMANGALA_1ST_BLOCK_BANGALORE},
                {Locations.NAGARAJA_GARDEN},
                {Locations.KORAMANGALA_BANGALORE},
                {Locations.NAGPUR}
        };
    }

    @Test(dataProvider = "getCityByNameId")
    public void searchByLocation(Locations location) throws Exception {
        Map<String, Object> qParam = new HashMap();
        qParam.put("entity_id", location.getEntityId());
        qParam.put("entity_type", location.getEntityType());
        Response response = Location.searchRestaurant(qParam, 200);
        Utilities.validateSchema(response, Constants.SEARCH_SCHEMA_PATH);
    }

    @Test(description = "This test case is to validate search api where invalid entity id is passed in request")
    public void searchByInvalidLocation() throws Exception {
        Map<String, Object> qParam = new HashMap();
        qParam.put("entity_id", Integer.parseInt(Utilities.randomNumericString(7)));
        qParam.put("entity_type", Utilities.randomString(6));
        Response response = Location.searchRestaurant(qParam, 400);
        Utilities.validateSchema(response, Constants.SEARCH_SCHEMA_PATH);
    }

    @Test(description = "This test case is to validate Positive cases for Review API")
    public void searchByCoordinate() throws Exception {
        Map<String, Object> qParam = new HashMap<>();
        qParam.put("lat", Cities.DELHI.getLatitude());
        qParam.put("lon", Cities.DELHI.getLongitude());
        Response response = Location.searchRestaurant(qParam, 200);
        Utilities.validateSchema(response, Constants.SEARCH_SCHEMA_PATH);
        Assert.assertEquals(response.jsonPath().get("restaurants[0].restaurant.location.city_id").toString(),Integer.toString(Cities.DELHI.getCityCode()));
    }
}
