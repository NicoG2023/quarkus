package io.quarkus.hibernate.orm.multiplepersistenceunits;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.runtime.configuration.ConfigurationException;
import io.quarkus.test.QuarkusUnitTest;

public class MultiplePersistenceUnitsBlockingModeReactiveOnlyDatasourceTest {

    @RegisterExtension
    static QuarkusUnitTest runner = new QuarkusUnitTest()
            .assertException(t -> {
                Assertions.assertInstanceOf(ConfigurationException.class, t);
                String msg = t.getMessage();
                Assertions.assertTrue(
                        msg.contains("mode=BLOCKING")
                                && msg.contains("no JDBC datasource")
                                && msg.contains("set mode="),
                        "Unexpected exception message: " + msg);
            })
            .withApplicationRoot((jar) -> jar
                    .addAsResource("application-multiple-persistence-units-mode-blocking-reactive-only.properties",
                            "application.properties"));

    @Test
    public void shouldFailAtBuildTime() {
        // The deployment exception should happen first.
        Assertions.fail();
    }
}
