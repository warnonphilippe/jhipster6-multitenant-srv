package be.civadis.jh6.srv.service.mapper;

import be.civadis.jh6.srv.domain.*;
import be.civadis.jh6.srv.service.dto.LocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class})
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {

    @Mapping(source = "country.id", target = "countryId")
    LocationDTO toDto(Location location);

    @Mapping(source = "countryId", target = "country")
    Location toEntity(LocationDTO locationDTO);

    default Location fromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }
}
