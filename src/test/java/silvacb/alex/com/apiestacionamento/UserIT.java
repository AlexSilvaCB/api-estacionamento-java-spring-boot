package silvacb.alex.com.apiestacionamento;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import silvacb.alex.com.apiestacionamento.web.dto.UserCreateDTO;
import silvacb.alex.com.apiestacionamento.web.dto.mapper.UserPasswordDTO;
import silvacb.alex.com.apiestacionamento.web.dto.mapper.UserResponseDTO;
import silvacb.alex.com.apiestacionamento.web.exception.ErrorMessage;

import java.util.List;

@AutoConfigureWebTestClient(timeout = "PT36S")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/user-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/user-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createUser_ComUsernamePasswordValidos_RetornarStatus201() {
        UserResponseDTO responseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("blue@gmail.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.getUsername()).isEqualTo("blue@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseDTO.getRole()).isEqualTo("CLIENT");
    }

    @Test
    public void createUser_ComUsernameInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.getStatus()).isEqualTo(422);

        responseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.getStatus()).isEqualTo(422);

        responseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("tody@gmail", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.getStatus()).isEqualTo(422);
    }

    @Test
    public void createUser_ComPasswordInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("blue@gmail.com", "123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.getStatus()).isEqualTo(422);

        responseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("blue@gmail.com", "123456789"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.getStatus()).isEqualTo(422);
    }

    @Test
    public void ComUsernameRepetido_RetornarErrorMessageComStatus409() {
        ErrorMessage responseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("green@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.getStatus()).isEqualTo(409);
    }

    @Test
    public void findById_ComIdExistente_RetornarUsuarioComStatus200() {
        UserResponseDTO responseDTO = testClient
                .get()
                .uri("/api/v1/users/100")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient,"green@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseDTO.getUsername()).isEqualTo("green@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseDTO.getRole()).isEqualTo("ADMIN");

        responseDTO = testClient
                .get()
                .uri("/api/v1/users/101")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient,"green@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.getId()).isEqualTo(101);
        org.assertj.core.api.Assertions.assertThat(responseDTO.getUsername()).isEqualTo("pink@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseDTO.getRole()).isEqualTo("CLIENT");

        responseDTO = testClient
                .get()
                .uri("/api/v1/users/101")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient,"pink@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.getId()).isEqualTo(101);
        org.assertj.core.api.Assertions.assertThat(responseDTO.getUsername()).isEqualTo("pink@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseDTO.getRole()).isEqualTo("CLIENT");
    }

    @Test
    public void findById_ComIdInexistente_RetornarErrorMessageComStatus404() {
        ErrorMessage responseDTO = testClient
                .get()
                .uri("/api/v1/users/105")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient,"green@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.getStatus()).isEqualTo(404);
    }

    @Test
    public void findById_ComUsuarioClienteBuscandoOutroUsuarioCliente_RetornarErrorMessageComStatus403() {
        ErrorMessage responseDTO = testClient
                .get()
                .uri("/api/v1/users/102")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient,"pink@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseDTO.getStatus()).isEqualTo(403);
    }

    @Test
    public void updatePassword_ComDadosValidos_RetornarStatus200() {
        testClient
                .patch()
                .uri("/api/v1/users/100")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient,"green@gmail.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("123456", "123456", "123456"))
                .exchange()
                .expectStatus().isOk();

        testClient
                .patch()
                .uri("/api/v1/users/101")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient,"pink@gmail.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("123456", "123456", "123456"))
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    public void updatePassword_ComUsuarioDiferentes_RetornarErrorMessageComStatus403() {
        ErrorMessage responseBody = testClient
                .patch()
                .uri("/api/v1/users/105")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient,"pink@gmail.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("123456", "123456", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void updatePassword_ComCamposInvalidos_RetornarErrorMessageComStatus422() {
        ErrorMessage responseBody = testClient
                .patch()
                .uri("/api/v1/users/100")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient,"green@gmail.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("", "", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .patch()
                .uri("/api/v1/users/100")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient,"green@gmail.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("12345678", "12345678", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .patch()
                .uri("/api/v1/users/100")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient,"green@gmail.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("12345", "12345", "12345"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void updatePassword_ComSenhaInvalidas_RetornarErrorMessageComStatus400() {
        ErrorMessage responseBody = testClient
                .patch()
                .uri("/api/v1/users/100")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient,"green@gmail.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("000000", "123456", "123456"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        testClient
                .patch()
                .uri("/api/v1/users/100")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient,"green@gmail.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("000000", "123456", "000000"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }

    @Test
    public void findByAll_RetornarListaDeUsuariosComStatus200() {
        List<UserResponseDTO> responseBody = testClient
                .get()
                .uri("/api/v1/users")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient,"green@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(3);
    }

    @Test
   public void listarUsuarios_ComUsuarioSemPermissao_RetornarErrorMessageComStatus403(){
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/users")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient,"pink@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

   }

}