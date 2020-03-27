package pl.training.bank.user;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserTransferObject toUserTransferObject(User user);

    User toUser(UserTransferObject userTransferObject);

}
