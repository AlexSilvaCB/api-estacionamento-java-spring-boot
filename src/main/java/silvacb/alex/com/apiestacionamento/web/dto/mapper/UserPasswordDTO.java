package silvacb.alex.com.apiestacionamento.web.dto.mapper;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserPasswordDTO {
    @Size(min = 6, max = 6)
    @NotBlank
    private String currentPassword;
    @Size(min = 6, max = 6)
    @NotBlank
    private String newPassword;
    @Size(min = 6, max = 6)
    @NotBlank
    private String confirmPassword;
}
