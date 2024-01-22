package silvacb.alex.com.apiestacionamento.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VacancyCreateDTO {

    @NotBlank
    @Size(min = 4, max = 4)
    private String code;
    @NotBlank
    @Pattern(regexp = "LIVRE|OCUPADA")
    private String status;

}
