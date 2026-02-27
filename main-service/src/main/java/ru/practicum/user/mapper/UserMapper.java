//package ru.practicum.user.mapper;
//
//import org.mapstruct.Mapper;
//import ru.practicum.user.dto.NewUserRequest;
//import ru.practicum.user.dto.UserDto;
//import ru.practicum.user.dto.UserShortDto;
//import ru.practicum.user.model.User;
//
//import java.util.List;
//
///**
// * Маппер для преобразования между DTO пользователей и сущностью User.
// */
//@Mapper(componentModel = "spring")
//public interface UserMapper {
//
//    User toUser(NewUserRequest dto);
//
//    UserDto toUserDto(User user);
//
//    List<UserDto> toUserDtoList(List<User> users);
//
//    UserShortDto toUserShortDto(User user);
//
//    List<UserShortDto> toUserShortDtoList(List<User> users);
//}
