package silvacb.alex.com.apiestacionamento;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import silvacb.alex.com.apiestacionamento.web.dto.ClientCreateDTO;
import silvacb.alex.com.apiestacionamento.web.dto.ClientResponseDTO;
import silvacb.alex.com.apiestacionamento.web.exception.ErrorMessage;

@AutoConfigureWebTestClient(timeout = "PT36S")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/clients/clients-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clients/clients-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientsIT {
    @Autowired
    WebTestClient testClient;

    @Test
    public void criarCliente_ComDadosValidos_RetornarClienteComStatus201() {
        ClientResponseDTO responseBody = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new ClientCreateDTO("Tobias Ferreira", "91191064085"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ClientResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Tobias Ferreira");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("91191064085");
    }

    @Test
    public void criarCliente_ComCpfJaCadastrado_RetornarClienteComStatus409() {
        ErrorMessage responseError = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new ClientCreateDTO("Tobias Ferreira", "79074426050"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseError).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseError.getStatus()).isEqualTo(409);
    }

    @Test
    public void criarCliente_ComDadosInvalidos_RetornarClienteComStatus409() {
        ErrorMessage responseError = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new ClientCreateDTO("Tobi", "91191064085"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseError).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseError.getStatus()).isEqualTo(422);

        responseError = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new ClientCreateDTO("", "91191064085"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseError).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseError.getStatus()).isEqualTo(422);

        responseError = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new ClientCreateDTO("Tobias Ferreira", "9119106408"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseError).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseError.getStatus()).isEqualTo(422);

        responseError = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new ClientCreateDTO("Tobias Ferreira", "91191064086"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseError).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseError.getStatus()).isEqualTo(422);
    }

    @Test
    public void criarCliente_ComPerfilAdm_RetornarClienteComStatus403() {
        ErrorMessage responseError = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "pink@gmail.com", "123456"))
                .bodyValue(new ClientCreateDTO("Bianca Silva", "79074426050"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseError).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseError.getStatus()).isEqualTo(403);
    }

}
