package by.rublevskaya.hotelapi.repository;

import java.util.Map;

public interface HotelHistogramRepository {
    Map<String, Long> getHistogramByParam(String param);
}