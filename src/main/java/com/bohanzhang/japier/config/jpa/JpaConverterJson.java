package com.bohanzhang.japier.config.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Converter(autoApply = true)
public class JpaConverterJson implements AttributeConverter<Object, String> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Object meta) {
        try {
            return OBJECT_MAPPER.writeValueAsString(meta);
        } catch (JsonProcessingException ex) {
            return null;
            // or throw an error
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        try {
            return OBJECT_MAPPER.readValue(dbData, Object.class);
        } catch (IOException ex) {
            log.error("Unexpected IOEx decoding json from database: " + dbData);
            return null;
        }
    }

}
