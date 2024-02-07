package silvacb.alex.com.apiestacionamento.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ParkingCreateDTO {

    @NotBlank(message = "{NotBlank.parkingCreateDTO.plate}")
    @Size(min = 8, max = 8, message = "{Size.parkingCreateDTO.plate}")
    @Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message = "{Pattern.parkingCreateDTO.plate}")//A placa do veículo deve seguir o padrão 'XXX-0000'
    private String plate;
    @NotBlank(message = "{NotBlank.parkingCreateDTO.brand}")
    private String brand;
    @NotBlank(message = "{NotBlank.parkingCreateDTO.model}")
    private String model;
    @NotBlank(message = "{NotBlank.parkingCreateDTO.color}")
    private String color;
    @NotBlank(message = "{NotBlank.parkingCreateDTO.clientCpf}")
    @Size(min = 11, max = 11, message = "{Size.parkingCreateDTO.clientCpf}")
    @CPF(message = "CPF.parkingCreateDTO.clientCpf")
    private String clientCpf;


}
