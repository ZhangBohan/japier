package com.bohanzhang.japier.core.context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleNodeContext implements NodeContext {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final SampleNodeContext EMPTY = SampleNodeContext.builder().context(Collections.emptyMap()).build();

    private Map<String, Object> context;

    @Override
    public String getString(String key) {
        return get(key, String.class);
    }

    @Override
    public <T> T get(String key, TypeReference<T> typeReference) {
        return OBJECT_MAPPER.convertValue(get(key), typeReference);
    }

    @Override
    public <T> T get(String key, Class<T> tClass) {
        return OBJECT_MAPPER.convertValue(get(key), tClass);
    }

    @Override
    public void merge(Object newContextData) {
        HashMap<String, Object> convertValue = OBJECT_MAPPER.convertValue(newContextData, new TypeReference<>() {
        });
        convertValue.putAll(context);
        context = convertValue;
    }

    private Object get(String key) {
        return context.get(key);
    }
}
