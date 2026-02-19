package by.rublevskaya.hotelapi.dto;

import by.rublevskaya.hotelapi.model.Address;
import by.rublevskaya.hotelapi.model.ArrivalTime;
import by.rublevskaya.hotelapi.model.Contacts;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateHotelDto {
    @NotBlank(message = "name is required")
    private String name;

    private String description;

    @NotBlank(message = "brand is required")
    private String brand;

    @Valid
    @NotNull(message = "address is required")
    private Address address;

    @Valid
    @NotNull(message = "contacts are required")
    private Contacts contacts;

    @Valid
    @NotNull(message = "arrival time is required")
    private ArrivalTime arrivalTime;
}