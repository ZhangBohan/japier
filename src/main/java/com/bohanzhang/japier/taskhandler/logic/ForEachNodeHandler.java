package com.bohanzhang.japier.taskhandler.logic;

import com.bohanzhang.japier.core.context.NodeContext;
import com.bohanzhang.japier.core.node.LogicNodeHandler;
import com.bohanzhang.japier.core.node.Node;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("for-each")
public class ForEachNodeHandler implements LogicNodeHandler<List<Object>> {
    @Override
    public List<Object> handle(Node node, NodeContext context) {
        return context.get("inputList", new TypeReference<>() {
        });
    }
}
