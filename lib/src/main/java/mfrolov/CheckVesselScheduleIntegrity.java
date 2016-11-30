package mfrolov;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Payload;

/**
 * @author mfrolov
 * @since 2.5
 */
@java.lang.annotation.Target({ElementType.TYPE})
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
@javax.validation.Constraint(validatedBy = VesselScheduleIntegrityValidator.class)
@java.lang.annotation.Documented
public @interface CheckVesselScheduleIntegrity {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
