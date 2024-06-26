package silvacb.alex.com.apiestacionamento.exception;

import lombok.Getter;

@Getter
public class UserNameUniqueViolationException extends RuntimeException {

    private String username;
    public UserNameUniqueViolationException(String username) {

        this.username = username;
    }
}
