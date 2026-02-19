package by.rublevskaya.hotelapi.repository;

import by.rublevskaya.hotelapi.model.Hotel;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class HotelSpecification {

    public static Specification<Hotel> filterHotels(String name, String brand, String city, String country, String amenities) {
        return Specification.where(hasName(name))
                .and(hasBrand(brand))
                .and(hasCity(city))
                .and(hasCountry(country))
                .and(hasAmenities(amenities))
                .and(distinct());
    }

    private static Specification<Hotel> hasName(String name) {
        return (root, query, cb) -> (name == null || name.isBlank()) ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    private static Specification<Hotel> hasBrand(String brand) {
        return (root, query, cb) -> (brand == null || brand.isBlank()) ? null :
                cb.equal(root.get("brand"), brand);
    }

    private static Specification<Hotel> hasCity(String city) {
        return (root, query, cb) -> (city == null || city.isBlank()) ? null :
                cb.equal(root.get("address").get("city"), city);
    }

    private static Specification<Hotel> hasCountry(String country) {
        return (root, query, cb) -> (country == null || country.isBlank()) ? null :
                cb.equal(root.get("address").get("country"), country);
    }

    private static Specification<Hotel> hasAmenities(String amenities) {
        return (root, query, cb) -> {
            if (amenities == null || amenities.isBlank()) return null;
            Join<Hotel, String> amenitiesJoin = root.join("amenities");
            return cb.equal(amenitiesJoin, amenities);
        };
    }

    private static Specification<Hotel> distinct() {
        return (root, query, cb) -> {
            query.distinct(true);
            return null;
        };
    }
}