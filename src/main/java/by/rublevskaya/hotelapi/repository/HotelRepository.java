package by.rublevskaya.hotelapi.repository;

import by.rublevskaya.hotelapi.model.Hotel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel>, HotelHistogramRepository {
    @EntityGraph(attributePaths = {"amenities"})
    Optional<Hotel> findDetailedById(Long id);
}