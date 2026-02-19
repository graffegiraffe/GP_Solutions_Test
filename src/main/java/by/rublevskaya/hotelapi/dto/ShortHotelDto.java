package by.rublevskaya.hotelapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortHotelDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}