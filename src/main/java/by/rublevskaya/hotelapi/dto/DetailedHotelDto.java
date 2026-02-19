package by.rublevskaya.hotelapi.dto;

import by.rublevskaya.hotelapi.model.Address;
import by.rublevskaya.hotelapi.model.ArrivalTime;
import by.rublevskaya.hotelapi.model.Contacts;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class DetailedHotelDto {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private Address address;
    private Contacts contacts;
    private ArrivalTime arrivalTime;
    private Set<String> amenities;
}