package silvacb.alex.com.apiestacionamento.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 100)
	private String username;
	
	@Column(nullable = false, length = 200)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 25)
	private Role role;
	
	private LocalDateTime creationDate;
	private LocalDateTime motificationDate;
	private String createdBy;
	private String modifiedBy;
	
	public enum Role {
		ROLE_ADMIN, ROLE_CLIENT
	}

	@Override
	public String toString() {
		return "User [id=" + id + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}
}
