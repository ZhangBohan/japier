package com.bohanzhang.japier.core.node;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * node for workflow
 * @author bohan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Node {
    private String id;
    private String name;
    private String description;

    private String nodeType;
    private String code;

    private Map<String, Object> params;
}
