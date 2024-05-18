package silvacb.alex.com.apiestacionamento.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silvacb.alex.com.apiestacionamento.entity.Vacancy;
import silvacb.alex.com.apiestacionamento.exception.CodigoUniqueViolationException;
import silvacb.alex.com.apiestacionamento.exception.EntityNotFoundException;
import silvacb.alex.com.apiestacionamento.exception.VagaLivreException;
import silvacb.alex.com.apiestacionamento.repository.VacancyRepository;
import silvacb.alex.com.apiestacionamento.web.dto.ControlVacancyDTO;

import static silvacb.alex.com.apiestacionamento.entity.Vacancy.StatusVacancy.LIVRE;
import static silvacb.alex.com.apiestacionamento.entity.Vacancy.StatusVacancy.OCUPADA;

@RequiredArgsConstructor
@Service
public class VacancyService {

    private final VacancyRepository vacancyRepository;

    @Transactional
    public Vacancy save(Vacancy vaga) {
        try {
            return vacancyRepository.save(vaga);
        } catch (DataIntegrityViolationException ex) {
            throw new CodigoUniqueViolationException("Vaga", vaga.getCode());
        }
    }

    @Transactional(readOnly = true)
    public Vacancy searchCode(String code) {
        return vacancyRepository.findByCode(code).orElseThrow(
                () -> new EntityNotFoundException ("Vaga", code)
        );
    }

    @Transactional(readOnly = true)
    public Vacancy buscarPorVagaLivre() {
        return vacancyRepository.findFirstByStatus(LIVRE).orElseThrow(
                () -> new VagaLivreException());
    }

    @Transactional(readOnly = true)
    public ControlVacancyDTO contatorDeVagas() {
        long free = vacancyRepository.countByStatus(LIVRE);
        long occupied = vacancyRepository.countByStatus(OCUPADA);

        ControlVacancyDTO freeDto = new ControlVacancyDTO();
        freeDto.setFreeVancacy(free);
        freeDto.setVacancyOccupied(occupied);

        return freeDto;
    }


}
