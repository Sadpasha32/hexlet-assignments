package exercise.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class CarUpdateDTO {
    @NotNull
    private JsonNullable<String> model;
    @NotNull
    private JsonNullable<String> manufacturer;
    @NotNull
    private JsonNullable<Integer> enginePower;
}
