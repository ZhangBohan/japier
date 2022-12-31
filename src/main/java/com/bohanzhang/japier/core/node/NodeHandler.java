package com.bohanzhang.japier.core.node;

import com.bohanzhang.japier.core.context.NodeContext;

public interface NodeHandler<T> {

    T handle(Node node, NodeContext context);
}
