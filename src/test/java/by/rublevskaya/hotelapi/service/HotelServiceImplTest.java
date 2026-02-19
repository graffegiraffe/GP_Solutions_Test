package by.rublevskaya.hotelapi.service;

import by.rublevskaya.hotelapi.dto.DetailedHotelDto;
import by.rublevskaya.hotelapi.dto.ShortHotelDto;
import by.rublevskaya.hotelapi.exception.EntityNotFoundException;
import by.rublevskaya.hotelapi.mapper.HotelMapper;
import by.rublevskaya.hotelapi.model.Hotel;
import by.rublevskaya.hotelapi.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Test
    void getAllHotels_ShouldReturnListOfShortHotelDto() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");

        ShortHotelDto shortDto = ShortHotelDto.builder().id(1L).name("Test Hotel").build();
        when(hotelRepository.findAll()).thenReturn(List.of(hotel));
        when(hotelMapper.toShortDto(hotel)).thenReturn(shortDto);
        List<ShortHotelDto> result = hotelService.getAllHotels();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Hotel", result.get(0).getName());
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    void getHotelById_ShouldReturnDetailedHotelDto() {
        Long hotelId = 1L;
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);

        DetailedHotelDto detailedDto = DetailedHotelDto.builder().id(hotelId).name("Detailed Hotel").build();
        when(hotelRepository.findDetailedById(hotelId)).thenReturn(Optional.of(hotel));
        when(hotelMapper.toDetailedDto(hotel)).thenReturn(detailedDto);
        DetailedHotelDto result = hotelService.getHotelById(hotelId);

        assertNotNull(result);
        assertEquals(hotelId, result.getId());
        assertEquals("Detailed Hotel", result.getName());
    }

    @Test
    void getHotelById_ShouldThrowException() {
        Long hotelId = 99L;
        when(hotelRepository.findDetailedById(hotelId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> hotelService.getHotelById(hotelId));

        assertEquals("Hotel not found with id 99", exception.getMessage());
        verify(hotelMapper, never()).toDetailedDto(any());
    }
}