package mfrolov;

import javax.inject.Inject;
import javax.validation.ConstraintValidatorContext;

/**
 * Booking application validates VesselSchedule changes here.
 *
 * @author mfrolov
 * @since 2.5
 */
public class VesselScheduleIntegrityBookingValidator implements VesselScheduleIntegrityValidator {

    public static final String VALIDATION_MESSAGE = "Booking sets veto on VesselSchedule change.";

    @Inject
    private InjectableService injectableService;

    @Override
    public void initialize(CheckVesselScheduleIntegrity constraintAnnotation) {

    }

    @Override
    public boolean isValid(VesselSchedule value, ConstraintValidatorContext context) {
        injectableService.foo();
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(VALIDATION_MESSAGE)
                .addConstraintViolation();

        return false;
    }
}
