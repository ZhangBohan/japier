package com.bohanzhang.japier.core.flow;

import com.bohanzhang.japier.core.context.NodeContext;
import com.bohanzhang.japier.core.node.DefaultNodeHandler;
import com.bohanzhang.japier.core.node.Node;
import com.bohanzhang.japier.core.node.NodeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NodeExecutor {

    @Autowired
    private Map<String, NodeHandler<?>> nodeHandlerMap;

    /**
     * Execute a flow node asynchronously.
     * @param nodes
     * @return
     */
    public Object execute(@NonNull List<Node> nodes, NodeContext context) {
        Object result = null;
        for (Node node : nodes) {
            String nodeType = node.getNodeType();
            NodeHandler<?> nodeHandler = nodeHandlerMap.get(nodeType);
            if (nodeHandler instanceof DefaultNodeHandler<?> handler) {
                // node params add to node execute context
                NodeContext executeNodeContext = context.merge(node.getParams());
                result = handler.handle(node, executeNodeContext);
                context = context.merge(Map.of(node.getCode(), result));
            }
        }
        return result;
    }
}
