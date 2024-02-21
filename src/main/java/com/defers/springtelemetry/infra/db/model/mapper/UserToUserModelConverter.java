package com.defers.springtelemetry.infra.db.model.mapper;

import com.defers.springtelemetry.domain.user.model.User;
import com.defers.springtelemetry.infra.db.model.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserModelConverter implements Converter<User, UserModel> {
    private final ModelMapper modelMapper;

    public UserToUserModelConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserModel convert(User source) {
        return modelMapper.map(source, UserModel.class);
    }
}
