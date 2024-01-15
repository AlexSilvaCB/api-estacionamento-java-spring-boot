package silvacb.alex.com.apiestacionamento.web.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserResponseDTO {
    private Long id;
    private String Username;
    private String role;

}
