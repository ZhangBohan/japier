package com.bohanzhang.japier.core.flow;

import com.bohanzhang.japier.BaseTest;
import com.bohanzhang.japier.core.node.Node;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class FlowRepositoryTest extends BaseTest {

    @Autowired
    private FlowRepository flowRepository;

    @Test
    void testSave() {
        Flow flow = new Flow();
        flow.setName("test flow");
        flow.setDescription("this is a test flow");
        flow.setInput(mapper.createObjectNode());
        flow.setNodes(List.of(Node.builder().build()));

        flowRepository.save(flow);
        log.info("flow: {}", flow);
        Assertions.assertNotNull(flow.getId());
    }

}