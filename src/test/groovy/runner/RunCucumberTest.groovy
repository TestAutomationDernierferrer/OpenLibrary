package runner
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectClasspathResource
import org.junit.platform.suite.api.Suite
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME
/**
 * Único punto de entrada. Gradle ejecuta esta clase y ella lanza Cucumber.
 * - features: src/test/resources/features
 * - steps: paquete "steps"
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "steps")
class RunCucumberTest {
}