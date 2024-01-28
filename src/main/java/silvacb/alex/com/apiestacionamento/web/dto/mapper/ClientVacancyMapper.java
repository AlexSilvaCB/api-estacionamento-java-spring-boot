package silvacb.alex.com.apiestacionamento.web.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import silvacb.alex.com.apiestacionamento.entity.ClientVacancy;
import silvacb.alex.com.apiestacionamento.web.dto.ParkingCreateDTO;
import silvacb.alex.com.apiestacionamento.web.dto.ParkingResponseDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientVacancyMapper {

    public static ClientVacancy toClienteVaga(ParkingCreateDTO dto) {
        return new ModelMapper().map(dto, ClientVacancy.class);
    }

    public static ParkingResponseDTO toDto(ClientVacancy clienteVaga) {
        return new ModelMapper().map(clienteVaga, ParkingResponseDTO.class);
    }


}
