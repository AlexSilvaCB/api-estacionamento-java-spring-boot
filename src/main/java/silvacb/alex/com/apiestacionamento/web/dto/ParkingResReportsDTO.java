package silvacb.alex.com.apiestacionamento.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingResReportsDTO {

    private double dateGraphSum1;
    private long dateGraphCount1;
    private double dateGraphSum2;
    private long dateGraphCount2;

}
