package com.masprogtech.config;

import com.masprogtech.dtos.OrderDTO;
import com.masprogtech.entities.Order;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Order.class, OrderDTO.class)
                .addMapping(src -> src.getClient().getUserId(), OrderDTO::setClientId);
        return modelMapper;
    }
}
