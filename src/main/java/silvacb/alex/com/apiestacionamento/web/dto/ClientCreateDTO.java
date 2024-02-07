package silvacb.alex.com.apiestacionamento.web.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClientCreateDTO {
    @NotBlank(message = "{NotBlank.clientCreateDTO.name}")
    @Size(min = 5, max = 100, message = "{Size.clientCreateDTO.name}")
    private String name;
    @Size(min = 11, max = 11, message = "{Size.clientCreateDTO.cpf}")
    @CPF(message = "CPF.clientCreateDTO.cpf")
    private String cpf;
}
