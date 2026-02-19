package by.rublevskaya.hotelapi.service;

import by.rublevskaya.hotelapi.dto.CreateHotelDto;
import by.rublevskaya.hotelapi.dto.DetailedHotelDto;
import by.rublevskaya.hotelapi.dto.ShortHotelDto;

import java.util.List;
import java.util.Map;

public interface HotelService {
    List<ShortHotelDto> getAllHotels();
    DetailedHotelDto getHotelById(Long id);
    List<ShortHotelDto> searchHotels(String name, String brand, String city, String country, String amenities);
    ShortHotelDto createHotel(CreateHotelDto dto);
    void addAmenities(Long id, List<String> amenities);
    Map<String, Long> getHistogram(String param);
}