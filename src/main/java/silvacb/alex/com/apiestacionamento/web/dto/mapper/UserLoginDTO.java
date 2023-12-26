package silvacb.alex.com.apiestacionamento.web.dto.mapper;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserLoginDTO {

    @NotBlank
    @Email(regexp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$",
            message = "Formato de e-mail inválido.")
    private String username;
    @NotBlank
    @Size(min = 6, max = 6)
    private String password;
}
