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
public class ArrivalTime {
    @NotBlank(message = "check-In time is required")
    private String checkIn;
    private String checkOut;
}