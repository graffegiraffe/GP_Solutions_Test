package by.rublevskaya.hotelapi.controller;

import by.rublevskaya.hotelapi.dto.CreateHotelDto;
import by.rublevskaya.hotelapi.dto.DetailedHotelDto;
import by.rublevskaya.hotelapi.dto.ShortHotelDto;
import by.rublevskaya.hotelapi.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Hotel API", description = "API for hotel management")
public class HotelController {

    private final HotelService hotelService;

    @Operation(summary = "get a list of all hotels")
    @GetMapping("/hotels")
    public List<ShortHotelDto> getAllHotels() {
        log.info("Received request: GET /hotels");
        return hotelService.getAllHotels();
    }

    @Operation(summary = "get information on a specific hotel")
    @GetMapping("/hotels/{id}")
    public DetailedHotelDto getHotelById(@PathVariable Long id) {
        log.info("Received request: GET /hotels/{}", id);
        return hotelService.getHotelById(id);
    }

    @Operation(summary = "search by parameters")
    @GetMapping("/search")
    public List<ShortHotelDto> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenities) {
        log.info("Received request: GET /search");
        return hotelService.searchHotels(name, brand, city, country, amenities);
    }

    @Operation(summary = "create a new hotel")
    @PostMapping("/hotels")
    @ResponseStatus(HttpStatus.CREATED)
    public ShortHotelDto createHotel(@Valid @RequestBody CreateHotelDto dto) {
        log.info("Received request: POST /hotels");
        return hotelService.createHotel(dto);
    }

    @Operation(summary = "add a list of amenities to the hotel")
    @PostMapping("/hotels/{id}/amenities")
    @ResponseStatus(HttpStatus.OK)
    public void addAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
        log.info("Received request: POST /hotels/{}/amenities", id);
        hotelService.addAmenities(id, amenities);
    }

    @Operation(summary = "Get a histogram")
    @GetMapping("/histogram/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        log.info("Received request: GET /histogram/{}", param);
        return hotelService.getHistogram(param);
    }
}