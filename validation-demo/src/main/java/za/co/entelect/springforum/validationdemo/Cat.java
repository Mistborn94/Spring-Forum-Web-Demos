package za.co.entelect.springforum.validationdemo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class Cat {
    @NotBlank
    private final String name;
    @NotBlank
    private final String colour;
}

