package mfrolov;

import static org.hamcrest.Matchers.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hamcrest.MatcherAssert;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mfrolov
 * @since 2.5
 */
public class GlobeConstraintValidatorFactoryTest {

    private static final Logger LOG = LoggerFactory.getLogger(GlobeConstraintValidatorFactoryTest.class);

    @Test
    public void threeValidatorsAreExecuted() {
        Weld weld = new Weld();
        WeldContainer weldContainer = weld.initialize();

        ValidatorFactory validatorFactory = Validation.byDefaultProvider()
                .configure()
                .buildValidatorFactory();

        VesselSchedule vesselSchedule = new VesselSchedule();

        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<VesselSchedule>> constraintViolations = validator.validate(vesselSchedule);

        for (ConstraintViolation<VesselSchedule> constraintViolation : constraintViolations) {
            LOG.info(constraintViolation.toString());
        }

        MatcherAssert.assertThat(constraintViolations, hasSize(2));
        weld.shutdown();
    }
}