package silvacb.alex.com.apiestacionamento.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silvacb.alex.com.apiestacionamento.Util.ParkingUtils;
import silvacb.alex.com.apiestacionamento.entity.Client;
import silvacb.alex.com.apiestacionamento.entity.ClientVacancy;
import silvacb.alex.com.apiestacionamento.entity.Vacancy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ParkingService {

    private final ClientVacancyService clientVacancyService;
    private final ClientService clientService;
    private final VacancyService vacancyService;

    @Transactional
    public ClientVacancy checkIn(ClientVacancy clientVacancy) {
        Client client = clientService.buscarPorCpf(clientVacancy.getClient().getCpf());
        clientVacancy.setClient(client);

        Vacancy vaga = vacancyService.buscarPorVagaLivre();
        vaga.setStatus(Vacancy.StatusVacancy.OCUPADA);
        clientVacancy.setVacancy(vaga);

        clientVacancy.setEntryData(LocalDateTime.now());

        clientVacancy.setReceipt(ParkingUtils.gerarRecibo());

        return clientVacancyService.salvar(clientVacancy);
    }

        @Transactional
        public ClientVacancy checkOut(String recibo) {
            ClientVacancy clientVacancy = clientVacancyService.buscarPorRecibo(recibo);

            LocalDateTime dataSaida = LocalDateTime.now();

            BigDecimal valor = ParkingUtils.calcularCusto(clientVacancy.getEntryData(), dataSaida);
            clientVacancy.setValue(valor);

            long totalDeVezes = clientVacancyService.getTotalDeVezesEstacionamentoCompleto(clientVacancy.getClient().getCpf());

            BigDecimal desconto = ParkingUtils.calcularDesconto(valor, totalDeVezes);
            clientVacancy.setDiscount(desconto);

            clientVacancy.setDepartureData(dataSaida);
            clientVacancy.getVacancy().setStatus(Vacancy.StatusVacancy.LIVRE);

            return clientVacancyService.salvar(clientVacancy);
        }
}

