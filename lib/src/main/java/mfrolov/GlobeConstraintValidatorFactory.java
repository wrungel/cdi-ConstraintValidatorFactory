package mfrolov;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorFactory;

import org.apache.deltaspike.core.api.provider.BeanManagerProvider;
import org.hibernate.validator.internal.engine.ConstraintValidatorFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mfrolov
 * @since 2.5
 */
public class GlobeConstraintValidatorFactory implements ConstraintValidatorFactory {

    private static final Logger LOG = LoggerFactory.getLogger(GlobeConstraintValidatorFactory.class);

    private ConstraintValidatorFactory defaultConstraintValidatorFactory;

    public GlobeConstraintValidatorFactory() {
        defaultConstraintValidatorFactory = new ConstraintValidatorFactoryImpl();

    }

    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {

        if (!isGlobeIntegrityConstraint(key)) {
            return defaultConstraintValidatorFactory.getInstance(key);
        }

        BeanManager beanManager = BeanManagerProvider.getInstance().getBeanManager();

        if (beanManager == null) {
            throw new IllegalStateException("No BeanManager");
        }

        Set<Bean<?>> beans = beanManager.getBeans(key);
        LOG.info("Found {} beans for key ", beans.size());
        List<T> validators = new ArrayList<>();
        for (Bean<?> bean : beans) {
            LOG.info(bean.getBeanClass().getName());
            CreationalContext<T> creationalContext = beanManager.createCreationalContext(null);
            Bean<T> tBean = (Bean<T>) bean;
            T validator = tBean.create(creationalContext);
            validators.add(validator);
        }
        return (T) new AggregatingValidator(validators);
    }

    static class AggregatingValidator <A extends Annotation, T> implements ConstraintValidator<A, T> {
        private final List<ConstraintValidator<A, T>> validators;

        AggregatingValidator(List<ConstraintValidator<A, T>> validators) {
            this.validators = validators;
        }


        @Override
        public void initialize(A constraintAnnotation) {
            for (ConstraintValidator validator : validators) {
                validator.initialize(constraintAnnotation);
            }
        }

        @Override
        public boolean isValid(T value, ConstraintValidatorContext context) {
            boolean result = true;
            for (ConstraintValidator validator : validators) {
                result = result && validator.isValid(value, context);
            }
            return result;
        }

    }


    private ConstraintValidatorFactory getDefaultValidatorFactory() {
        return defaultConstraintValidatorFactory;
    }

    private <T extends ConstraintValidator<?, ?>> boolean isGlobeIntegrityConstraint(Class<T> key) {
        return key.isInterface();
    }

}
