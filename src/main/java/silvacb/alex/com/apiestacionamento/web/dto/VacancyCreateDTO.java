package silvacb.alex.com.apiestacionamento.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VacancyCreateDTO {

    @NotBlank(message = "{NotBlank.vacancyCreateDTO.code}")
    @Size(min = 4, max = 4, message = "{Size.vacancyCreateDTO.code}")
    private String code;
    @NotBlank(message = "{NotBlank.vacancyCreateDTO.status}")
    @Pattern(regexp = "LIVRE|OCUPADA", message = "{Pattern.vacancyCreateDTO.status}")
    private String status;

}
