package silvacb.alex.com.apiestacionamento.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import silvacb.alex.com.apiestacionamento.exception.*;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class apiExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(InvalidCredencialException.class)
    public ResponseEntity<ErrorMessage> invalidCredencialException(InvalidCredencialException ex,
                                                                       HttpServletRequest request){
        log.error("Api error - ", ex);
        Object[] params = new Object[]{ex.getUsername()};
        String message = messageSource.getMessage("exception.invalidCredencialException", params, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, message));
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> passwordInvalidException(RuntimeException ex, HttpServletRequest request) {
        log.error("Api error - ", ex);
        String message = messageSource.getMessage("exception.passwordInvalidException", null, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                        request,
                        HttpStatus.BAD_REQUEST, message));
    }

    @ExceptionHandler(NewPasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> newPasswordInvalidException(RuntimeException ex, HttpServletRequest request) {
        log.error("Api error - ", ex);
        String message = messageSource.getMessage("exception.newPasswordInvalidException", null, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                        request,
                        HttpStatus.BAD_REQUEST,
                        message));
    }

    @ExceptionHandler(UserNameUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> userNameUniqueViolationException(UserNameUniqueViolationException ex,
                                                                         HttpServletRequest request){
        log.error("Api error - ", ex);
        Object[] params = new Object[]{ex.getUsername()};
        String message = messageSource.getMessage("exception.usernameUniqueViolationException", params, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, message));
    }

    @ExceptionHandler(CpfUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> cpfUniqueViolationException(CpfUniqueViolationException ex,
                                                                    HttpServletRequest request){
        log.error("Api error - ", ex);
        Object[] params = new Object[]{ex.getCpf()};
        String message = messageSource.getMessage("exception.cpfUniqueViolationException", params, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, message));
    }

    @ExceptionHandler(ReciboCheckInNotFoundException.class)
    public ResponseEntity<ErrorMessage> reciboCheckInNotFoundException(ReciboCheckInNotFoundException ex,
                                                                       HttpServletRequest request){
        log.error("Api error - ", ex);
        Object[] params = new Object[]{ex.getRecibo()};
        String message = messageSource.getMessage("exception.reciboCheckInNotFoundException", params, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, message));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex,
                                                                HttpServletRequest request){
        log.error("Api error - ", ex);
        Object[] params = new Object[]{ex.getRecurso(), ex.getCodigo()};
        String message = messageSource.getMessage("exception.entityNotFoundException", params, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, message));
    }

    @ExceptionHandler(CodigoUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> codigoUniqueViolationException(CodigoUniqueViolationException ex,
                                                                        HttpServletRequest request){
        log.error("Api error - ", ex);
        Object[] params = new Object[]{ex.getRecurso(), ex.getCodigo()};
        String message = messageSource.getMessage("exception.codigoUniqueViolationException", params, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, message));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException ex,
                                                              HttpServletRequest request){
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    @ExceptionHandler(VagaLivreException.class)
    public ResponseEntity<ErrorMessage> vagaLivreException(RuntimeException ex,
                                                                HttpServletRequest request){
        log.error("Api error - ", ex);
        String message = messageSource.getMessage("exception.vagaLivreException", null, request.getLocale());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                        HttpServletRequest request,
                                                                        BindingResult result) {
        log.error("Api error - ", ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                        request,
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        messageSource.getMessage("message.invalid.field", null, request.getLocale())
                        ,result,
                        messageSource));
    }

    public ResponseEntity<ErrorMessage> internalServerErrorException(Exception ex, HttpServletRequest request) {
        ErrorMessage error = new ErrorMessage(
                request, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        log.error("Internal Server Error {} {} ", error, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
}
