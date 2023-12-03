package silvacb.alex.com.apiestacionamento.web.dto.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import silvacb.alex.com.apiestacionamento.entity.User;
import silvacb.alex.com.apiestacionamento.web.dto.UserCreateDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toUser(UserCreateDTO createDto){
        return new ModelMapper().map(createDto, User.class);
    }

    public static UserResponseDTO toDto(User user) {
        String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<User, UserResponseDTO> props = new PropertyMap<User, UserResponseDTO>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(user, UserResponseDTO.class);
    }

    public static List<UserResponseDTO> toListDto(List<User> user){
        return user.stream().map(x -> toDto(x)).collect(Collectors.toList());
    }
}
