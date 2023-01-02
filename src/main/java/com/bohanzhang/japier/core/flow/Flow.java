package com.bohanzhang.japier.core.flow;

import com.bohanzhang.japier.config.jpa.JpaConverterJson;
import com.bohanzhang.japier.core.node.Node;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Flow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @Convert(converter = JpaConverterJson.class)
    @Column(columnDefinition = "json")
    private JsonNode input;

    @Convert(converter = JpaConverterJson.class)
    @Column(columnDefinition = "json")
    private List<Node> nodes;
}
