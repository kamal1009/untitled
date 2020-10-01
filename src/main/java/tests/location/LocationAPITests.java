package tests.location;

import util.Utilities;
import apiCalls.Location;
import constants.Constants;
import enums.Locations;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
public class LocationAPITests {
    private static final Logger logger = LogManager.getLogger(LocationAPITests.class);

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

    @Test(description = "This test case is to validate get location with name", dataProvider = "getCityByNameId")
    public void getLocationWithName(Locations location) throws Exception {
        Map<String, Object> qParam = new HashMap();
        qParam.put("query", location.getTitle());
        Response response = Location.getLocation(qParam);
        Utilities.validateSchema(response, Constants.GET_LOCATION_SCHEMA_PATH);
        validateLocationData(response, location);
    }

    @Test(description = "This test case is to validate get location when location coordinate is sent in request positive scenarios", dataProvider = "getCityByNameId")
    public void getLocationWithCoordinate(Locations location) throws Exception {
        Map<String, Object> qParam = new HashMap();
        qParam.put("query", location.getTitle());
        qParam.put(Constants.LATITUDE, location.getLatitude());
        qParam.put(Constants.LONGITUDE, location.getLongitude());
        Response response = Location.getLocation(qParam);
        Utilities.validateSchema(response, Constants.GET_LOCATION_SCHEMA_PATH);
        validateLocationData(response, location);
    }

    @Test(description = "This test case is to validate get location api when query params are missing")
    public void getLocationMissingParam() throws Exception {
        Map<String, Object> qParam = new HashMap();
        Response response = Location.getLocation(qParam,400);
        Utilities.validateSchema(response, Constants.GET_LOCATION_SCHEMA_PATH);
    }

    @Test(dataProvider = "invalidQParam",description = "This test case is to validate get location api when invalid param are sent in request")
    public void getLocationMissingParam(String key,String value) throws Exception {
        Map<String, Object> qParam = new HashMap();
        qParam.put(key,value);
        Response response = Location.getLocation(qParam,400);
    }

    public void validateLocationData(Response response,Locations location) {
        Assert.assertTrue(Utilities.compareData(response.jsonPath().get("status").toString(), "success"));
        Assert.assertEquals(response.jsonPath().get("location_suggestions[0].entity_type").toString(), location.getEntityType());
        Assert.assertEquals(response.jsonPath().get("location_suggestions[0].city_id").toString(), Integer.toString(location.getCityId()));
        Assert.assertEquals(response.jsonPath().get("location_suggestions[0].entity_id").toString(), Integer.toString(location.getEntityId()));
        Assert.assertEquals(response.jsonPath().get("location_suggestions[0].country_name"), location.getCountryName());
        Assert.assertEquals(response.jsonPath().get("location_suggestions[0].country_id").toString(), Integer.toString(location.getCountryId()));
    }
}
