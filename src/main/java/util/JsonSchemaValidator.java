package util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonSchemaValidator {

    private static final Logger log = LogManager.getLogger(JsonSchemaValidator.class);

    public boolean validateSchema(String SchemaFilePath, String response) throws Exception {
        log.info("Validating schema for response against file: " + SchemaFilePath);
        String schemaFileContent = new String(Files.readAllBytes(Paths.get(SchemaFilePath)), "UTF-8");
        JSONObject rawSchema = new  JSONObject(schemaFileContent);
        Schema schema = SchemaLoader.load(rawSchema);
        Object json = new JSONTokener(response).nextValue();
        if (json instanceof JSONObject) {
            try {
                schema.validate(new JSONObject(response));
                return true;
            } catch (ValidationException exception) {
                genericErrorHandler(exception, SchemaFilePath, response);
                throw new Exception("Failed validating schema: " + rawSchema.toString() + "\n against response: " + response);
            }
        } else if (json instanceof JSONArray) {
            try {
                schema.validate(new JSONArray(response));
                return true;
            } catch (ValidationException exception) {
                genericErrorHandler(exception, SchemaFilePath, response);
                throw new Exception("Failed validating schema: " + rawSchema.toString() + "\n against response: " + response);
            }
        }
        return false;
    }

    private void genericErrorHandler(ValidationException exception, String SchemaFilePath, String response) {
        log.info("Error validating schema for response against file: " + SchemaFilePath + "Details: " + exception.getMessage() + " |-----| Response:" + response);
        exception.getCausingExceptions().stream()
                .map(ValidationException::getMessage)
                .forEach(System.out::println);
        for (ValidationException causingException : exception.getCausingExceptions()) {
            log.info("Error validating schema for response against file: " + SchemaFilePath + "Details: " + causingException.getCausingExceptions());
            System.out.println(causingException.getCausingExceptions());
        }
    }
}
