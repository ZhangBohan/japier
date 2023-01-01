package com.bohanzhang.japier.core.node;

import lombok.Data;

/**
 * node for workflow
 * @author bohan
 */
@Data
public class Node {
    private String id;
    private String name;
    private String description;

    private String nodeType;
}
