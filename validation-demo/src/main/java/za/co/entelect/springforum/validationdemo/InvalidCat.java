package za.co.entelect.springforum.validationdemo;

import za.co.entelect.springforum.validationdemo.customvalidation.MyCustomConstraint;

import javax.validation.constraints.NotBlank;

@MyCustomConstraint
public class InvalidCat extends Cat {
    public InvalidCat(@NotBlank String name, @NotBlank String colour) {
        super(name, colour);
    }
}
