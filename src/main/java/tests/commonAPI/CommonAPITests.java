package tests.commonAPI;

import constants.TestGroups;
import util.Utilities;
import apiCalls.Common;
import constants.Constants;
import enums.Cities;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonAPITests {
    Common commonApiImpl = new Common();
    SoftAssert softAssert = new SoftAssert();
    private static final Logger logger = LogManager.getLogger(CommonAPITests.class);

    @DataProvider(name = "getCityByNameId")
    public static Object[][] getCityTestData() {
        return new Object[][]{
                {Cities.BANGALORE},
                {Cities.DELHI},
                {Cities.MUMBAI},
                {Cities.NEW_YORK},
        };
    }

    @DataProvider(name = "getCityWithParam")
    public static Object[][] getCityWithParam() {
        return new Object[][]{
                /* qParamKey  aParamValue  expectedCount*/
                {"q", "a", 0},
                {"q", "1,1,1,1,1", 1},
                {"q", "haryana", 0},
                {"q", "null", 0},
                {"q", "null", 0},
                {"q", " H  I  S  A  R", 1},
                {"city_ids", "a", 0},
                {"city_ids", -1, 0},
                {"city_ids", 1.0, 1},
                {"city_ids", 0, 0},
                {"city_ids", 1234543212, 0},
                {"q", Utilities.randomString(9), 0},
        };
    }

    @DataProvider(name = "getCityCountPassed")
    public static Object[][] getCityCountPassed() {
        return new Object[][]{
                /* qParamKey  aParamValue  countPassed CountExpected*/
                {"q", "Ba", 0, 0},
                {"q", "Ba", 11, 11},
                {"q", "Ba", 2, 2},
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

    @Test(dataProvider = "getCityWithParam",groups = {TestGroups.COMMON_API})
    public void getCityWithDiffParam(String key, Object value, int expectedCount) throws Exception {
        Map<String, Object> qParam = new HashMap();
        qParam.put(key, value);
        Response response = Common.getCities(qParam);
        Assert.assertEquals(((List<Object>) response.jsonPath().get("location_suggestions")).size(), expectedCount);
    }

    @Test(description = "This test case is to validate get City when count param is passed in request", dataProvider = "getCityCountPassed",groups = {TestGroups.COMMON_API})
    public void getCityWithDiffCount(String key, Object value, int countParam, int expectedCount) throws Exception {
        Map<String, Object> qParam = new HashMap();
        qParam.put(key, value);
        qParam.put("count", countParam);
        Response response = Common.getCities(qParam);
        Assert.assertEquals(((List<Object>) response.jsonPath().get("location_suggestions")).size(), expectedCount);
    }

    @Test(description = "This test case is to validate get Cities api when wrong Qparam is passed in request ideally API should through Bad request", dataProvider = "invalidQParam",groups = {TestGroups.COMMON_API})
    public void getCityWithInvalidQParam(String key, Object value) throws Exception {
        Map<String, Object> qParam = new HashMap();
        qParam.put(key, value);
        Response response = Common.getCities(qParam, 400);
    }

    @Test(description = "This test case is to validate get city With name positive scenarios", dataProvider = "getCityByNameId",groups = {TestGroups.COMMON_API})
    public void getCityWithName(Cities city) throws Exception {
        Map<String, Object> qParam = new HashMap();
        qParam.put(Constants.Q, city.getName());
        Response response = Common.getCities(qParam);
        Utilities.validateSchema(response, Constants.GET_CITIES_SCHEMA_PATH);
        validateCityData(response, city);
    }

    @Test(description = "This test case is to validate get city With ID positive scenarios", dataProvider = "getCityByNameId",groups = {TestGroups.COMMON_API})
    public void getCityWithIdOnly(Cities city) {
        Map<String, Object> qParam = new HashMap();
        qParam.put(Constants.CITY_IDS, city.getCityCode());
        validateCityData(Common.getCities(qParam), city);
    }

    @Test(description = "This test case is validate get city name when invalid qParam is passed in request", dataProvider = "getCityByNameId",groups = {TestGroups.COMMON_API})
    public void getCityWithIdAndName(Cities city) {
        Map<String, Object> qParam = new HashMap();
        qParam.put(Constants.CITY_IDS, city.getCityCode());
        qParam.put(Constants.Q, city.getName());
        validateCityData(Common.getCities(qParam), city);
    }

    @Test(description = "This Test case is to validate get City API when Location is passed in request", dataProvider = "getCityByNameId",groups = {TestGroups.COMMON_API})
    public void getCityWithLocation(Cities city) {
        Map<String, Object> qParam = new HashMap();
        qParam.put(Constants.LATITUDE, city.getLatitude());
        qParam.put(Constants.LONGITUDE, city.getLongitude());
        validateCityData(Common.getCities(qParam), city);
    }

    @Test(description = "This test case is to validate when invalid combination of qParam is passed in request for get cities API",groups = {TestGroups.COMMON_API})
    public void getCityWithInvalidIdNameCombination() {
        Map<String, Object> qParam = new HashMap();
        qParam.put(Constants.CITY_IDS, Cities.MUMBAI.getCityCode());
        qParam.put(Constants.Q, Cities.BANGALORE.getName());
        Response response = Common.getCities(qParam);
        Assert.assertEquals(((List<Object>) response.jsonPath().get("location_suggestions")).size(), 0);
    }

    @Test(description = "This test case is to validate get Categories API to positive flow. We will validate the schema as well",groups = {TestGroups.COMMON_API})
    public void getCategoriesTest() throws Exception {
        Response response = Common.getCategories();
        Utilities.validateSchema(response, Constants.GET_CATEGORIES_SCHEMA_PATH);
    }


    @Test(dataProvider = "getCityByNameId",description = "This test case is to validate get collection API to positive flow. We will validate the schema as well",groups = {TestGroups.COMMON_API})
    public void getCollectionWithCityId(Cities city) throws Exception {
        Map<String,Object> qParam =new HashMap<>();
        qParam.put(Constants.CITY_ID,city.getCityCode());
        Response response = Common.getCollection(qParam);
        Utilities.validateSchema(response, Constants.GET_COLLECTION_SCHEMA_PATH);
    }

    @Test(dataProvider = "getCityByNameId",description = "This test case is to validate get collection API to positive flow with location in request. We will validate the schema as well",groups = {TestGroups.COMMON_API})
    public void getCollectionWithLocation(Cities city) throws Exception {
        Map<String,Object> qParam =new HashMap<>();
        qParam.put(Constants.LATITUDE,city.getLatitude());
        qParam.put(Constants.LONGITUDE,city.getLongitude());
        Response response = Common.getCollection(qParam);
        Utilities.validateSchema(response, Constants.GET_COLLECTION_SCHEMA_PATH);
    }

    @Test(description = "This test case is to validate get collection API to positive flow with location in request. We will validate the schema as well",groups = {TestGroups.COMMON_API})
    public void getCollectionWithInvalidLocation() throws Exception {
        Map<String,Object> qParam =new HashMap<>();
        qParam.put(Constants.LATITUDE,Utilities.randomNumericString(13));
        qParam.put(Constants.LONGITUDE,Utilities.randomNumericString(13));
        Response response = Common.getCollection(qParam,400);
    }

    @Test(description = "This test case is to validate get collection API to positive flow with location in request. We will validate the schema as well",groups = {TestGroups.COMMON_API})
    public void getCitiesWithInvalidLocation() throws Exception {
        Map<String,Object> qParam =new HashMap<>();
        qParam.put(Constants.LATITUDE,Utilities.randomNumericString(13));
        qParam.put(Constants.LONGITUDE,Utilities.randomNumericString(13));
        Response response = Common.getCities(qParam,400);
    }

    @Test(description = "This test case is to validate get collection API to positive flow with location in request. We will validate the schema as well",groups = {TestGroups.COMMON_API})
    public void getCollectionWithInvalidCityId() throws Exception {
        Map<String,Object> qParam =new HashMap<>();
        qParam.put(Constants.CITY_ID,Utilities.randomNumericString(5));
        Response response = Common.getCollection(qParam,400);
    }

    @Test(dataProvider = "invalidQParam",description = "This test case is to validate get collection API to positive flow with location in request. We will validate the schema as well",groups = {TestGroups.COMMON_API})
    public void getCollectionWithInvalidQParam(String key ,String value) throws Exception {
        Map<String,Object> qParam =new HashMap<>();
        qParam.put(key,value);
        Response response = Common.getCollection(qParam,400);
    }

    public void validateCityData(Response response, Cities city) {
        Assert.assertTrue(Utilities.compareData(response.jsonPath().get("status").toString(), "success"));
        Assert.assertEquals(response.jsonPath().get("location_suggestions[0].id").toString(), Integer.toString(city.getCityCode()));
        Assert.assertEquals(response.jsonPath().get("location_suggestions[0].name"), city.getName());
        Assert.assertEquals(response.jsonPath().get("location_suggestions[0].country_name"), city.getCountryName());
        Assert.assertEquals(response.jsonPath().get("location_suggestions[0].country_id").toString(), Integer.toString(city.getCountryId()));
    }
}
