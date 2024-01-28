package silvacb.alex.com.apiestacionamento.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "customer_has_vacancies")
@EntityListeners(AuditingEntityListener.class)
public class ClientVacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   //gerar numero do recibo automaticamente
    @Column(name = "receipt_number", nullable = false, unique = true, length = 15)
    private String receipt;
    @Column(name = "plate", nullable = false, length = 8)
    private String plate;
    @Column(name = "brand", nullable = false, length = 45)
    private String brand;
    @Column(name = "model", nullable = false, length = 45)
    private String model;
    @Column(name = "color", nullable = false, length = 45)
    private String color;
    @Column(name = "entry_data", nullable = false)
    private LocalDateTime entryData;
    @Column(name = "departure_data")
    private LocalDateTime departureData;
    @Column(name = "value", columnDefinition = "decimal(7,2)")
    private BigDecimal value;
    @Column(name = "discount", columnDefinition = "decimal(7,2)")
    private BigDecimal discount;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;
    @ManyToOne
    @JoinColumn(name = "id_vacancy", nullable = false)
    private Vacancy vacancy;

    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;
    @CreatedBy
    @Column(name = "criado_por")
    private String criadoPor;
    @LastModifiedBy
    @Column(name = "modificado_por")
    private String modificadoPor;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientVacancy that = (ClientVacancy) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
