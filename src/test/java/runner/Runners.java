package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty",
                "html:target/default-cucumber-reports.html"},
        features = "src/test/resources/features",
        glue = {"stepDefinitions", "hooks"},
        tags = "@templateSurvey",
        monochrome = true,
        dryRun = false

)
public class Runners {
}
