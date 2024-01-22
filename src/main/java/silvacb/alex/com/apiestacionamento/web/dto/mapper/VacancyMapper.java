package silvacb.alex.com.apiestacionamento.web.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import silvacb.alex.com.apiestacionamento.entity.Vacancy;
import silvacb.alex.com.apiestacionamento.web.dto.VacancyCreateDTO;
import silvacb.alex.com.apiestacionamento.web.dto.VacancyResponseDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VacancyMapper {

    public static Vacancy toVaga(VacancyCreateDTO dto) {
        return new ModelMapper().map(dto, Vacancy.class);
    }

    public static VacancyResponseDTO toDto(Vacancy vaga) {
        return new ModelMapper().map(vaga, VacancyResponseDTO.class);
    }

}
