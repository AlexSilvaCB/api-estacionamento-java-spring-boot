package silvacb.alex.com.apiestacionamento.web.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import silvacb.alex.com.apiestacionamento.entity.Client;
import silvacb.alex.com.apiestacionamento.web.dto.ClientCreateDTO;
import silvacb.alex.com.apiestacionamento.web.dto.ClientResponseDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static Client toClient(ClientCreateDTO dto) {
        return new ModelMapper().map(dto, Client.class);
    }

    public static ClientResponseDTO toDto(Client client) {
        return new ModelMapper().map(client, ClientResponseDTO.class);
    }
}
