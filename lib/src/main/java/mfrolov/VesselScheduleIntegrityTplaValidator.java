package mfrolov;

import javax.validation.ConstraintValidatorContext;

/**
 * TPLA application validates VesselSchedule changes here.
 *
 * @author mfrolov
 * @since 2.5
 */
public class VesselScheduleIntegrityTplaValidator implements VesselScheduleIntegrityValidator {

    @Override
    public void initialize(CheckVesselScheduleIntegrity constraintAnnotation) {

    }

    @Override
    public boolean isValid(VesselSchedule value, ConstraintValidatorContext context) {
        return true;
    }
}
