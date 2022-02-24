package com.abstractkamen.mappers.impl;

import com.abstractkamen.entities.User;
import com.abstractkamen.mappers.api.GenericMapper;
import com.abstractkamen.xsddomain.UserCommand;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper extends GenericMapper<User, UserCommand> {

    @Override
    User fromDto(UserCommand userCommand);

    @Override
    UserCommand toDto(User target);
}
