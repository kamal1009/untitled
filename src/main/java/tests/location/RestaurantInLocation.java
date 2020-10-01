package tests.location;

import util.Utilities;
import apiCalls.Location;
import constants.Constants;
import enums.Locations;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantInLocation {

    private static final Logger logger = LogManager.getLogger(RestaurantInLocation.class);

    @DataProvider(name = "getCityByNameId")
    public static Object[][] getCityTestData() {
        return new Object[][]{
                {Locations.KORAMANGALA_1ST_BLOCK_BANGALORE},
                {Locations.NAGARAJA_GARDEN},
                {Locations.KORAMANGALA_BANGALORE},
                {Locations.NAGPUR}
        };
    }

    @DataProvider(name = "invalidQParam")
    public static Object[][] invalidQParam() {
        return new Object[][]{
                /* qParamKey  aParamValue  countPassed CountExpected*/
                {Utilities.randomString(4), "Ba"},
                {Utilities.randomString(3), "1"},
        };
    }

    @Test(description = "This test case is to validate get restaurant  With location positive scenarios", dataProvider = "getCityByNameId")
    public void getRestaurantWithLocation(Locations location) throws Exception {
        Map<String, Object> qParam = new HashMap();
        qParam.put(Constants.ENTITY_ID, location.getEntityId());
        qParam.put(Constants.ENTITY_TYPE, location.getEntityType());
        Response response = Location.getLocationDetails(qParam, 200);
        Utilities.validateSchema(response, Constants.GET_LOCATION_RESTAURANT_SCHEMA_PATH);
    }

    @Test(description = "This test case is to validate get restaurant  With invalid location scenarios", dataProvider = "getCityByNameId")
    public void getRestaurantWithInvalidLocation(Locations location) throws Exception {
        Map<String, Object> qParam = new HashMap();
        qParam.put(Constants.ENTITY_ID, Integer.parseInt(Utilities.randomNumericString(9)));
        qParam.put(Constants.ENTITY_TYPE, Utilities.randomString(5));
        Response response = Location.getLocationDetails(qParam, 403);
    }

    @Test(description = "This test case is to validate get restaurant  With location positive scenarios", dataProvider = "getCityByNameId")
    public void getRestaurantWithMissingParam(Locations location) throws Exception {
        Map<String, Object> qParam = new HashMap();
        qParam.put(Utilities.randomString(5), Utilities.randomString(5));
        Response response = Location.getLocationDetails(qParam, 403);
    }

    public static List<String> getListOfRestaurantInArea(String entityType, int entityId) {
        Map<String, Object> qParam = new HashMap<>();
        qParam.put(Constants.ENTITY_ID, entityId);
        qParam.put(Constants.ENTITY_TYPE, entityType);
        return Location.getLocationDetails(qParam, 200).jsonPath().get("nearby_res");
    }
}
