package by.rublevskaya.hotelapi.controller;

import by.rublevskaya.hotelapi.dto.CreateHotelDto;
import by.rublevskaya.hotelapi.dto.ShortHotelDto;
import by.rublevskaya.hotelapi.exception.EntityNotFoundException;
import by.rublevskaya.hotelapi.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HotelController.class)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HotelService hotelService;

    @MockBean(name = "log-interceptor")
    private HandlerInterceptor logInterceptor;

    @BeforeEach
    void setUp() throws Exception {
        when(logInterceptor.preHandle(any(), any(), any())).thenReturn(true);
    }

    @Test
    void getAllHotels_ShouldReturn200() throws Exception {
        ShortHotelDto dto = ShortHotelDto.builder().id(1L).name("Hilton").build();
        when(hotelService.getAllHotels()).thenReturn(List.of(dto));
        mockMvc.perform(get("/hotels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Hilton"));
    }

    @Test
    void createHotel_ShouldReturn201() throws Exception {
        ShortHotelDto responseDto = ShortHotelDto.builder().id(1L).name("Valid Hotel").build();
        when(hotelService.createHotel(any())).thenReturn(responseDto);

        String validJson = """
                {
                    "name": "Valid Hotel",
                    "brand": "Hilton",
                    "address": { "houseNumber": "1", "street": "A", "city": "B", "country": "C", "postCode": "123" },
                    "contacts": { "phone": "123", "email": "a@b.com" },
                    "arrivalTime": { "checkIn": "14:00" }
                }
                """;

        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Valid Hotel"));
    }

    @Test
    void getHotelById_ShouldReturn404() throws Exception {
        when(hotelService.getHotelById(99L)).thenThrow(new EntityNotFoundException("Hotel not found with id: 99"));
        mockMvc.perform(get("/hotels/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Hotel not found with id: 99"));
    }

    @Test
    void createHotel_ShouldReturn400() throws Exception {
        CreateHotelDto invalidDto = new CreateHotelDto();
        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void getHotelById_ShouldReturn400TypeMismatch() throws Exception {
        mockMvc.perform(get("/hotels/abc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void getHotelById_ShouldReturn405MethodNotAllowed() throws Exception {
        mockMvc.perform(post("/hotels/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.status").value(405))
                .andExpect(jsonPath("$.error").value("Method Not Allowed"));
    }

    @Test
    void createHotel_ShouldReturn415UnsupportedMediaType() throws Exception {
        String xmlBody = "<hotel><name>Hilton</name></hotel>";
        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_XML)
                        .content(xmlBody))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.status").value(415))
                .andExpect(jsonPath("$.error").value("Unsupported Media Type"));
    }

    @Test
    void createHotel_WhenMalformedJson_ShouldReturn400() throws Exception {
        String brokenJson = "{ \"name\": \"Broken Hotel\", \"brand\": \"Hilton\" ";

        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brokenJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Malformed JSON request or invalid data format"));
    }
}