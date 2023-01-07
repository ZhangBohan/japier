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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.endsWith;

@Slf4j
class NodeExecutorTest extends BaseTest {

    @Autowired
    private NodeExecutor executor;

    @Test
    void execute() throws ExecutionException, InterruptedException {
        User user = new User();
        user.setName("test");

        Mockito.when(restTemplate.getForObject(endsWith("bohan"), any()))
                .thenReturn(user);

        Node node = Node.builder().nodeType("github-lookup").code("g1").params(Map.of("user", "bohan")).build();
        CompletableFuture<Object> future = executor.execute(List.of(node), SampleNodeContext.builder().context(Map.of("user", "bohan")).build());
        Object result = future.get();
        log.info("result: {}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    void executeList() throws ExecutionException, InterruptedException {
        User user = new User();
        user.setName("bohan");

        Mockito.when(restTemplate.getForObject(endsWith("bohan"), any()))
                .thenReturn(user);

        Node node = Node.builder().nodeType("github-lookup").code("g1").params(Map.of("user", "bohan")).build();
        Node node2 = Node.builder().nodeType("github-lookup").code("g2").params(Map.of("user", "${g1.name}")).build();
        CompletableFuture<Object> future = executor.execute(List.of(node, node2), SampleNodeContext.EMPTY);
        Object result = future.get();
        log.info("result: {}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    void testIf() throws ExecutionException, InterruptedException {
        User user = new User();
        user.setName("bohan");

        Mockito.when(restTemplate.getForObject(endsWith("bohan"), any()))
                .thenReturn(user);

        Node node2 = Node.builder().nodeType("github-lookup").code("g2").params(Map.of("user", "bohan")).build();
        Node node = Node.builder()
                .nodeType("if").code("g1")
                .params(Map.of("condition", "${1 > 0}"))
                .subNodes(List.of(node2))
                .build();
        CompletableFuture<Object> future = executor.execute(List.of(node), SampleNodeContext.EMPTY);
        Object result = future.get();
        log.info("result: {}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    void testIfNotMatch() throws ExecutionException, InterruptedException {
        User user = new User();
        user.setName("bohan");

        Mockito.when(restTemplate.getForObject(endsWith("bohan"), any()))
                .thenReturn(user);

        Node node2 = Node.builder().nodeType("github-lookup").code("g2").params(Map.of("user", "bohan")).build();
        Node node = Node.builder()
                .nodeType("if").code("g1")
                .params(Map.of("condition", "${1 > 2}"))
                .subNodes(List.of(node2))
                .build();
        CompletableFuture<Object> future = executor.execute(List.of(node), SampleNodeContext.EMPTY);
        Object result = future.get();
        log.info("result: {}", result);
        Assertions.assertNull(result);
    }

    @Test
    void testForEach() throws ExecutionException, InterruptedException {
        User user = new User();
        user.setName("bohan");

        Mockito.when(restTemplate.getForObject(endsWith("bohan"), any()))
                .thenReturn(user);

        Node node2 = Node.builder().nodeType("github-lookup").code("g2").params(Map.of("user", "${$item.user}")).build();
        Node node = Node.builder()
                .nodeType("for-each").code("g1")
                .params(Map.of("inputList", List.of(Map.of("user", "bohan"))))
                .subNodes(List.of(node2))
                .build();
        CompletableFuture<Object> future = executor.execute(List.of(node), SampleNodeContext.EMPTY);
        Object result = future.get();
        log.info("result: {}", result);
        Assertions.assertNotNull(result);
    }
}