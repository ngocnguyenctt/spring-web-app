package sale.management.app.validator;

import org.hibernate.validator.constraints.Length;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author ngoc on 24/04/2018
 * @subject sale-management-app
 */

@NotNull
@Length(max = 20)
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidId
{
    String message() default "{sale.management.app.model.ValidId}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
