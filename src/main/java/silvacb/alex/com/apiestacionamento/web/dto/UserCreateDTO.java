package silvacb.alex.com.apiestacionamento.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserCreateDTO {
    @NotBlank(message = "{NotBlank.userCreateDTO.username}")
    @Email(message = "{Email.userCreateDTO.username}",
            regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String username;
    @NotBlank(message="{NotBlank.userCreateDTO.password}")
    @Size(min = 6, max = 6, message="{Size.userCreateDTO.password}")
    private String password;

}
