package com.baconbao.portfolio.config;

import com.baconbao.portfolio.dto.ProfileDTO;
import com.baconbao.portfolio.model.Profile;
import com.baconbao.portfolio.model.TypeProfile;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Converter from String to TypeProfile
        Converter<String, TypeProfile> toTypeProfile = new Converter<>() {
            @Override
            public TypeProfile convert(MappingContext<String, TypeProfile> context) {
                String source = context.getSource();
                if (source == null || source.trim().isEmpty()) {
                    return null; // or handle this case as per your requirement
                }
                try {
                    return TypeProfile.valueOf(source.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid value for TypeProfile enum: " + source);
                }
            }
        };

        // Define mapping from ProfileDTO to Profile
        modelMapper.addMappings(new PropertyMap<ProfileDTO, Profile>() {
            @Override
            protected void configure() {
                using(toTypeProfile).map(source.getTypeProfile()).setTypeProfile(null);
            }
        });

        // Converter from TypeProfile to String
        Converter<TypeProfile, String> toString = new Converter<>() {
            @Override
            public String convert(MappingContext<TypeProfile, String> context) {
                TypeProfile source = context.getSource();
                return source == null ? null : source.name();
            }
        };

        // Custom converter from Profile to ProfileDTO for image URL
        Converter<Profile, String> imageUrlConverter = new Converter<>() {
            @Override
            public String convert(MappingContext<Profile, String> context) {
                Profile source = context.getSource();
                return source != null && source.getImage() != null ? source.getImage().getUrl() : null;
            }
        };

        // Define mapping from Profile to ProfileDTO
        modelMapper.addMappings(new PropertyMap<Profile, ProfileDTO>() {
            @Override
            protected void configure() {
                using(toString).map(source.getTypeProfile()).setTypeProfile(null);
                using(imageUrlConverter).map(source).setUrl(null);
            }
        });

        return modelMapper;
    }
}