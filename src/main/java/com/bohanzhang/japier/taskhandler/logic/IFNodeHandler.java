package com.bohanzhang.japier.taskhandler.logic;

import com.bohanzhang.japier.core.context.NodeContext;
import com.bohanzhang.japier.core.node.LogicNodeHandler;
import com.bohanzhang.japier.core.node.Node;
import org.springframework.stereotype.Component;

@Component("if")
public class IFNodeHandler implements LogicNodeHandler<Boolean> {
    @Override
    public Boolean handle(Node node, NodeContext context) {
        return context.getBoolean("condition");
    }
}
