package com.bohanzhang.japier.core.context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mvel2.templates.TemplateRuntime;

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

    public static NodeContext withMap(Map<String, Object> executeNodeContextMap) {
        return SampleNodeContext.builder().context(executeNodeContextMap).build();
    }

    @Override
    public String getString(String key) {
        return get(key, String.class);
    }

    @Override
    public <T> T get(String key, TypeReference<T> typeReference) {
        return OBJECT_MAPPER.convertValue(get(key), typeReference);
    }

    public void put(String key, Object value) {
        context.put(key, value);
    }

    @Override
    public <T> T get(String key, Class<T> tClass) {
        return OBJECT_MAPPER.convertValue(get(key), tClass);
    }

    @Override
    public NodeContext merge(Object newContextData) {

        Map<String, Object> convertValue = OBJECT_MAPPER.convertValue(newContextData, new TypeReference<>() {
        });
        HashMap<String, Object> newContext = new HashMap<>(context);
        convertValue.forEach((key, value) -> {
            if (value instanceof String stringValue) {
                value = TemplateRuntime.eval(stringValue, context);
            }
            newContext.put(key, value);
        });
        return SampleNodeContext.withMap(newContext);
    }

    @Override
    public boolean getBoolean(String condition) {
        return get(condition, Boolean.class);
    }

    private Object get(String key) {
        return context.get(key);
    }
}
