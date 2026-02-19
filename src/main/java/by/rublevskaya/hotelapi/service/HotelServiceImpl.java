package by.rublevskaya.hotelapi.service;

import by.rublevskaya.hotelapi.dto.CreateHotelDto;
import by.rublevskaya.hotelapi.dto.DetailedHotelDto;
import by.rublevskaya.hotelapi.dto.ShortHotelDto;
import by.rublevskaya.hotelapi.exception.EntityNotFoundException;
import by.rublevskaya.hotelapi.mapper.HotelMapper;
import by.rublevskaya.hotelapi.model.Hotel;
import by.rublevskaya.hotelapi.repository.HotelRepository;
import by.rublevskaya.hotelapi.repository.HotelSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ShortHotelDto> getAllHotels() {
        log.info("Fetching all hotels");
        return hotelRepository.findAll().stream()
                .map(hotelMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DetailedHotelDto getHotelById(Long id) {
        log.info("Fetching detailed info for hotel id: {}", id);
        Hotel hotel = hotelRepository.findDetailedById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with id: " + id));
        return hotelMapper.toDetailedDto(hotel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShortHotelDto> searchHotels(String name, String brand, String city, String country, String amenities) {
        log.info("Searching hotels by criteria - name: {}, brand: {}, city: {}, country: {}, amenities: {}",
                name, brand, city, country, amenities);
        Specification<Hotel> spec = HotelSpecification.filterHotels(name, brand, city, country, amenities);
        return hotelRepository.findAll(spec).stream()
                .map(hotelMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ShortHotelDto createHotel(CreateHotelDto dto) {
        log.info("Creating new hotel with name: {}", dto.getName());
        Hotel hotel = hotelMapper.toEntity(dto);
        Hotel saved = hotelRepository.save(hotel);
        log.info("Hotel successfully created with id: {}", saved.getId());
        return hotelMapper.toShortDto(saved);
    }

    @Override
    @Transactional
    public void addAmenities(Long id, List<String> amenities) {
        log.info("Adding amenities {} to hotel id: {}", amenities, id);
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with id: " + id));

        if (hotel.getAmenities() == null) {
            hotel.setAmenities(new HashSet<>());
        }
        hotel.getAmenities().addAll(amenities);
        hotelRepository.save(hotel);
        log.info("Amenities added successfully for hotel id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> getHistogram(String param) {
        log.info("Fetching histogram for parameter: {}", param);
        return hotelRepository.getHistogramByParam(param);
    }
}