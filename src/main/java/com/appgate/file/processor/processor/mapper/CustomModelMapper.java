package com.appgate.file.processor.processor.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Service;

@Service
public class CustomModelMapper extends ModelMapper {

    public CustomModelMapper() {
        super();
        this.getConfiguration().setMethodAccessLevel(Configuration.AccessLevel.PROTECTED);
    }

    @Override
    public <D> D map(Object source, Class<D> destinationType) {
        Object tmpSource = source;
        if(source == null){
            return null;
        }
        return super.map(tmpSource, destinationType);
    }
}
