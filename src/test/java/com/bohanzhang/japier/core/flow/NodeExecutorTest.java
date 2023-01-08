package com.bohanzhang.japier.core.flow;

import com.bohanzhang.japier.BaseTest;
import com.bohanzhang.japier.core.context.SampleNodeContext;
import com.bohanzhang.japier.core.node.Node;
import com.bohanzhang.japier.taskhandler.github.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.endsWith;

@Slf4j
class NodeExecutorTest extends BaseTest {

    @MockBean
    protected RestTemplate restTemplate;

    @Autowired
    private NodeExecutor executor;

    @Test
    void execute() throws ExecutionException, InterruptedException {
        mockLookup("test");

        String resourcePath = "flows/lookup.yml";
        List<Node> nodeList = getNodes(resourcePath);
        CompletableFuture<Object> future = executor.execute(nodeList, SampleNodeContext.builder().context(Map.of("user", "bohan")).build());
        Object result = future.get();
        log.info("result: {}", result);
        Assertions.assertNotNull(result);
    }

    @ParameterizedTest
    @CsvSource({
            "flows/lookup-list.yml",
            "flows/for-each-lookup.yml",
            "flows/for-each-lookup.yml"
    })
    void testEmptyContext(String resourcePath) throws ExecutionException, InterruptedException {
        mockLookup("bohan");

        List<Node> nodeList = getNodes(resourcePath);
        CompletableFuture<Object> future = executor.execute(nodeList, SampleNodeContext.EMPTY);
        Object result = future.get();
        log.info("result: {}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    void testIfNotMatch() throws ExecutionException, InterruptedException {
        mockLookup("bohan");

        List<Node> nodeList = getNodes("flows/if-lookup-not-match.yml");
        CompletableFuture<Object> future = executor.execute(nodeList, SampleNodeContext.EMPTY);
        Object result = future.get();
        log.info("result: {}", result);
        Assertions.assertNull(result);
    }

    private void mockLookup(String test) {
        User user = new User();
        user.setName(test);

        Mockito.when(restTemplate.getForObject(endsWith("bohan"), any()))
                .thenReturn(user);
    }
}