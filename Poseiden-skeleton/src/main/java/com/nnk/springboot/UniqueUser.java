package com.nnk.springboot;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUserValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface UniqueUser {

    String message() default "Username already in use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
