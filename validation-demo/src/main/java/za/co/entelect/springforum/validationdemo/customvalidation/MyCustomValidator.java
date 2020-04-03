package za.co.entelect.springforum.validationdemo.customvalidation;

import za.co.entelect.springforum.validationdemo.Cat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MyCustomValidator implements ConstraintValidator<MyCustomConstraint, Cat> {

    public void initialize(MyCustomConstraint constraint) {
        //Get state from the annotation in here
    }

    public boolean isValid(Cat obj, ConstraintValidatorContext context) {
        //Do validation in here
        return false;
    }
}
