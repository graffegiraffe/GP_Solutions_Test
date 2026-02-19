package by.rublevskaya.hotelapi.mapper;

import by.rublevskaya.hotelapi.dto.CreateHotelDto;
import by.rublevskaya.hotelapi.dto.DetailedHotelDto;
import by.rublevskaya.hotelapi.dto.ShortHotelDto;
import by.rublevskaya.hotelapi.model.Address;
import by.rublevskaya.hotelapi.model.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    @Mapping(target = "address", source = "address", qualifiedByName = "mapAddressToString")
    @Mapping(target = "phone", source = "contacts.phone")
    ShortHotelDto toShortDto(Hotel hotel);

    DetailedHotelDto toDetailedDto(Hotel hotel);

    Hotel toEntity(CreateHotelDto dto);

    @Named("mapAddressToString")
    default String mapAddressToString(Address address) {
        if (address == null) {
            return null;
        }
        return String.format("%s %s, %s, %s, %s",
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getPostCode(),
                address.getCountry());
    }
}