package silvacb.alex.com.apiestacionamento.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingReqReportsDTO {

    @NotBlank(message = "{NotBlank.parkingReqReportsDTO.data_inicio1}")
    private String data_inicio1;
    @NotBlank(message = "{NotBlank.parkingReqReportsDTO.data_inicio2}")
    private String data_inicio2;
    @NotBlank(message = "{NotBlank.parkingReqReportsDTO.data_inicio3}")
    private String data_inicio3;
    @NotBlank(message = "{NotBlank.parkingReqReportsDTO.data_inicio4}")
    private String  data_inicio4;
}
