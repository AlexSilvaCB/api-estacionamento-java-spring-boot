package silvacb.alex.com.apiestacionamento.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silvacb.alex.com.apiestacionamento.Util.ClientVacancyUtils;
import silvacb.alex.com.apiestacionamento.entity.ClientVacancy;
import silvacb.alex.com.apiestacionamento.exception.ReciboCheckInNotFoundException;
import silvacb.alex.com.apiestacionamento.repository.ClientVacancyRepository;
import silvacb.alex.com.apiestacionamento.repository.projection.ClientVacancyProjection;
import silvacb.alex.com.apiestacionamento.web.dto.ParkingReqReportsDTO;
import silvacb.alex.com.apiestacionamento.web.dto.ParkingResReportsDTO;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientVacancyService {

    private final ClientVacancyRepository repository;

    @Transactional
    public ClientVacancy salvar(ClientVacancy clientVacancy) {
        return repository.save(clientVacancy);
    }

    @Transactional(readOnly = true)
    public ClientVacancy buscarPorRecibo(String recibo) {
        return repository.findByReceiptAndDepartureDataIsNull(recibo).orElseThrow(
                () -> new ReciboCheckInNotFoundException(recibo)
        );
    }

    @Transactional(readOnly = true)
    public long getTotalDeVezesEstacionamentoCompleto(String cpf) {
        return repository.countByClientCpfAndDepartureDataIsNotNull(cpf);
    }

    @Transactional(readOnly = true)
    public Page<ClientVacancyProjection> buscarTodosPorClienteCpf(String cpf, Pageable pageable) {
        return repository.findAllByClientCpf(cpf, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ClientVacancyProjection> buscarTodosPorUsuarioId(Long id, Pageable pageable) {
        return repository.findAllByClientUsersId(id, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ClientVacancyProjection> buscarTodosPorUsuarioPorData(String data1, String data2, Pageable pageable) {

        return repository.findByEntryDataBetween(
                ClientVacancyUtils.converterStringData0(data1),
                ClientVacancyUtils.converterStringData23(data2), pageable);
    }

    @Transactional //funções em repository e tratamentos
    public ParkingResReportsDTO getParkingChart(ParkingReqReportsDTO dto) {

        List<ClientVacancy> listGrafic1 = repository.findByEntryDataBetween(
                ClientVacancyUtils.converterStringData0(dto.getData_inicio1()),
                ClientVacancyUtils.converterStringData23(dto.getData_inicio2()));

        List<ClientVacancy> listGrafic2 = repository.findByEntryDataBetween(
                ClientVacancyUtils.converterStringData0(dto.getData_inicio3()),
                ClientVacancyUtils.converterStringData23(dto.getData_inicio4()));

        ParkingResReportsDTO resDTO = new ParkingResReportsDTO();

        resDTO.setDateGraphSum1(listGrafic1.stream().mapToDouble(x -> x.getValue().doubleValue()).sum());
        resDTO.setDateGraphCount1(listGrafic1.stream().count());
        resDTO.setDateGraphSum2(listGrafic2.stream().mapToDouble(x -> x.getValue().doubleValue()).sum());
        resDTO.setDateGraphCount2(listGrafic2.stream().count());

        return resDTO;
    }

}
