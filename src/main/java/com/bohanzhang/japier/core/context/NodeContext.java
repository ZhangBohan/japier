package com.bohanzhang.japier.core.context;

import com.fasterxml.jackson.core.type.TypeReference;

public interface NodeContext {
    String getString(String key);

    <T> T get(String key, TypeReference<T> typeReference);
    <T> T get(String key, Class<T> tClass);

    void merge(Object newContextData);
}
