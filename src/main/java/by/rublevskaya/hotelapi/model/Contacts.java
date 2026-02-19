package by.rublevskaya.hotelapi.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contacts {
    @NotBlank(message = "phone is required")
    private String phone;

    @NotBlank(message = "email is required")
    private String email;
}