package com.bohanzhang.japier.core.flow;

import com.bohanzhang.japier.BaseTest;
import com.bohanzhang.japier.core.context.SampleNodeContext;
import com.bohanzhang.japier.core.node.Node;
import com.bohanzhang.japier.taskhandler.github.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.endsWith;

@Slf4j
class NodeExecutorTest extends BaseTest {

    @Autowired
    private NodeExecutor executor;

    @Test
    void execute() {
        User user = new User();
        user.setName("test");

        Mockito.when(restTemplate.getForObject(endsWith("bohan"), any()))
                .thenReturn(user);

        Node node = new Node();
        node.setNodeType("github-lookup");
        Object result = executor.execute(List.of(node), SampleNodeContext.builder().context(Map.of("user", "bohan")).build());
        log.info("result: {}", result);
        Assertions.assertNotNull(result);
    }
}