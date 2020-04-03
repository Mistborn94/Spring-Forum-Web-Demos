package za.co.entelect.springforum.validationdemo.customvalidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = MyCustomValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface MyCustomConstraint {
    //These properties are required on a constraint annotation
    String message() default "This cat is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
