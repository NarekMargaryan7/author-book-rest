package am.itspace.authorbookrest.mapper;

import am.itspace.authorbookrest.dto.SaveBookRequest;
import am.itspace.authorbookrest.dto.SaveUserRequest;
import am.itspace.authorbookrest.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(SaveUserRequest saveUserRequest);
}
