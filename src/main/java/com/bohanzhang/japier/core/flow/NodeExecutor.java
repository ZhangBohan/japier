package com.bohanzhang.japier.core.flow;

import com.bohanzhang.japier.core.context.NodeContext;
import com.bohanzhang.japier.core.node.DefaultNodeHandler;
import com.bohanzhang.japier.core.node.Node;
import com.bohanzhang.japier.core.node.NodeHandler;
import com.bohanzhang.japier.taskhandler.logic.IFNodeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class NodeExecutor {

    @Autowired
    private Map<String, NodeHandler<?>> nodeHandlerMap;

    @Autowired
    private ExecutorService executorService;

    /**
     * Execute a flow node asynchronously.
     * @param nodes
     * @return
     */
    public CompletableFuture<Object> execute(@NonNull List<Node> nodes, NodeContext context) {
        CompletableFuture<Object> completableFuture = new CompletableFuture<>();
        executorService.submit(() -> {
            Object result = asyncExecute(nodes, context);
            completableFuture.complete(result);
        });

        return completableFuture;
    }

    public Object asyncExecute(@NonNull List<Node> nodes, NodeContext context) {
        if (nodes.isEmpty()) {
            return null;
        }

        Object result = null;
        for (Node node : nodes) {
            String nodeType = node.getNodeType();
            NodeHandler<?> nodeHandler = nodeHandlerMap.get(nodeType);
            if (nodeHandler instanceof DefaultNodeHandler<?> handler) {
                // node params add to node execute context
                NodeContext executeNodeContext = context.merge(node.getParams());
                result = handler.handle(node, executeNodeContext);
                context = context.merge(Map.of(node.getCode(), result));
            } else if (nodeHandler instanceof IFNodeHandler handler) {
                NodeContext executeNodeContext = context.merge(node.getParams());
                if (Boolean.TRUE.equals(handler.handle(node, executeNodeContext))) {
                    result = asyncExecute(node.getSubNodes(), executeNodeContext);
                    context = context.merge(Map.of(node.getCode(), result));
                }
            }
        }
        return result;
    }
}
