package silvacb.alex.com.apiestacionamento.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import silvacb.alex.com.apiestacionamento.entity.User;
import silvacb.alex.com.apiestacionamento.exception.EntityNotFoundException;
import silvacb.alex.com.apiestacionamento.exception.PasswordInvalidException;
import silvacb.alex.com.apiestacionamento.exception.UserNameUniqueViolationException;
import silvacb.alex.com.apiestacionamento.repository.UserRepository;


@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;


	
	@Transactional(readOnly = true)
	public List<User> searchAll() {
		return userRepository.findAll();
	}

	@Transactional(readOnly = true)
	public User searchById(Long id) {
		return userRepository.findById(id).orElseThrow(
				()-> new EntityNotFoundException(String.format("Usuário id= %s não encontrado.", id)));
	}

	@Transactional
	public User save(User user) {
		try {
			return userRepository.save(user);
		} catch (DataIntegrityViolationException ex){
			throw new UserNameUniqueViolationException(String.format("Usuario '%s' já cadastrado",
					user.getUsername()));
		}
	}

	@Transactional
	public void editPassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
		if (!newPassword.equals(confirmPassword))
		{
			throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
		}

		User updatePassword = searchById(id);
		if (!updatePassword.getPassword().equals(currentPassword))
		{
			throw new PasswordInvalidException("Senha não confere.");
		}

		updatePassword.setPassword(newPassword);
	}
}
