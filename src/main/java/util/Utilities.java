package util;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;


public class Utilities {
    private static final Logger logger = LogManager.getLogger(Utilities.class);

    public RequestSpecification request = RestAssured.given();
    public Response response;
    public String apiBaseURL;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmanopqrstuvwxyz";
    private static final String NUMERIC_STRING = "123456789";
    private static JsonSchemaValidator schemaValidator =new JsonSchemaValidator();


    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String randomNumericString(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * NUMERIC_STRING.length());
            builder.append(NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String randomString(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA.length());
            builder.append(ALPHA.charAt(character));
        }
        return builder.toString();
    }

    public static void validateSchema( Response response, String filePath) throws Exception {
        Assert.assertTrue(schemaValidator.validateSchema(filePath, response.asString()));
    }

    public static boolean compareData(String actual,String expected){

        logger.info("Comparing  Data Expected :  "+expected +" Actual : "+actual);
        if(actual.equals(expected))
            return true;
            return false;
    }



}
