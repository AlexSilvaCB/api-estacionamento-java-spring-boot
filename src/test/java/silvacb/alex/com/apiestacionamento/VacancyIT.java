package silvacb.alex.com.apiestacionamento;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import silvacb.alex.com.apiestacionamento.web.dto.VacancyCreateDTO;

@AutoConfigureWebTestClient(timeout = "PT36S")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/vacancy/vacancy-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/vacancy/vacancy-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class VacancyIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void criarVaga_ComDadosValidos_RetornarLocationStatus201() {
        testClient
                .post()
                .uri("/api/v1/vacancy")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "green@gmail.com", "123456"))
                .bodyValue(new VacancyCreateDTO("A-07", "LIVRE"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);
    }

    @Test
    public void criarVaga_ComCodigoJaExistente_RetornarErrorMessageComStatus409() {
        testClient
                .post()
                .uri("/api/v1/vacancy")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "green@gmail.com", "123456"))
                .bodyValue(new VacancyCreateDTO("A-03", "LIVRE"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("status").isEqualTo(409)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/vacancy");

    }

    @Test
    public void criarVaga_ComDadoInvalidos_RetornarErrorMessageComStatus422() {
        testClient
                .post()
                .uri("/api/v1/vacancy")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "green@gmail.com", "123456"))
                .bodyValue(new  VacancyCreateDTO("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/vacancy");

        testClient
                .post()
                .uri("/api/v1/vacancy")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "green@gmail.com", "123456"))
                .bodyValue(new  VacancyCreateDTO("A-501", "DESOCUPADA"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/vacancy");
    }


    @Test
    public void buscarVaga_ComCodigoExistente_RetornarVagaComStatus200() {
        testClient
                .get()
                .uri("/api/v1/vacancy/{code}", "A-03")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "green@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(10)
                .jsonPath("code").isEqualTo("A-03")
                .jsonPath("status").isEqualTo("LIVRE");

    }

    @Test
    public void buscarVaga_ComCodigoInexistente_RetornarErrorMessageComStatus404() {
        testClient
                .get()
                .uri("/api/v1/vacancy/{code}", "A-10")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "green@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/vacancy/A-10");
    }

    @Test
    public void buscarVaga_ComUsuarioSemPermissaoDeAcesso_RetornarErrorMessageComStatus403() {
        testClient
                .get()
                .uri("/api/v1/vacancy/{code}", "A-01")
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "pink@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/vacancy/A-01");
    }

    @Test
    public void criarVaga_ComUsuarioSemPermissaoDeAcesso_RetornarErrorMessageComStatus403() {
        testClient
                .post()
                .uri("/api/v1/vacancy")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeadersAuthorization(testClient, "pink@gmail.com", "123456"))
                .bodyValue(new VacancyCreateDTO("A-05", "OCUPADA"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/vacancy");
    }

}
