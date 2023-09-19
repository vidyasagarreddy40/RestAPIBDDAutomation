package TestBase;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Properties;

public class TestBase {

    Properties properties = new Properties();
    FileInputStream fileInputStream;
    public static RequestSpecification requestSpecBuilder;
    public ResponseSpecification responseSpecBuilder;

    public TestBase() {

        try {
            fileInputStream = new FileInputStream("src/main/resources/prop.properties");
            properties.load(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RequestSpecification requestSpecification() {
        if (requestSpecBuilder == null) {
            try {
                PrintStream log = new PrintStream(new File("logging.txt"));
                requestSpecBuilder = new RequestSpecBuilder().setRelaxedHTTPSValidation().setBaseUri(properties.getProperty("baseURL")).
                        addQueryParam("key", "qaclick123").
                        addFilter(RequestLoggingFilter.logRequestTo(log)).
                        addFilter(ResponseLoggingFilter.logResponseTo(log)).
                        setContentType(ContentType.JSON).build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return requestSpecBuilder;
    }


    public ResponseSpecification responseSpecification() {
        responseSpecBuilder = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        return responseSpecBuilder;
    }

    public String getJSONPath(String resposne, String Key) {
        JsonPath jsonPath = new JsonPath(resposne);

        return jsonPath.get(Key).toString();
    }
}
