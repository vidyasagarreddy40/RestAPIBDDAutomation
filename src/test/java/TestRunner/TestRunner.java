package TestRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features", glue = {"stepDefinations"},
        plugin = {"json:target/cucumber.json", "html:target/report/cucumber.html",
                "json:target/jsonReports/cucumber.json"})
public class TestRunner {

}
