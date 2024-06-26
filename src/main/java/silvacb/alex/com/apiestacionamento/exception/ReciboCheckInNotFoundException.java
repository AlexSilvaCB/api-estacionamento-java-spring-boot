package silvacb.alex.com.apiestacionamento.exception;

import lombok.Getter;

@Getter
public class ReciboCheckInNotFoundException extends RuntimeException {

    private String recibo;
    public ReciboCheckInNotFoundException(String recibo) {
        this.recibo = recibo;
    }
}
