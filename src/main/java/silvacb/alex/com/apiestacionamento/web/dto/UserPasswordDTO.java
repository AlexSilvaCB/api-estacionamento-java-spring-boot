package silvacb.alex.com.apiestacionamento.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserPasswordDTO {
    @Size(min = 6, max = 6, message = "{Size.userPasswordDTO.currentPassword}")
    @NotBlank(message = "{NotBlank.userPasswordDTO.currentPassword}")
    private String currentPassword;
    @Size(min = 6, max = 6, message = "{Size.userPasswordDTO.newPassword}")
    @NotBlank(message = "{NotBlank.userPasswordDTO.newPassword}")
    private String newPassword;
    @Size(min = 6, max = 6, message = "{Size.userPasswordDTO.confirmPassword}")
    @NotBlank(message = "{NotBlank.userPasswordDTO.confirmPassword}")
    private String confirmPassword;
}
