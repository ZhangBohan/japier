package com.bohanzhang.japier.core.flow;

import lombok.Data;

import java.util.Map;

@Data
public class Flow {
    private String id;
    private String name;
    private String description;
    private Map<String, Object> input;
}
