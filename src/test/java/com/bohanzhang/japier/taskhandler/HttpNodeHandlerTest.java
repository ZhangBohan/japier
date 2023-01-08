package com.bohanzhang.japier.taskhandler;

import com.bohanzhang.japier.BaseTest;
import com.bohanzhang.japier.core.context.SampleNodeContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@Slf4j
class HttpNodeHandlerTest extends BaseTest {

    @Autowired
    private HttpNodeHandler nodeHandler;

    @MockBean
    private RestTemplate restTemplate;

    /**
     * response data demo
     * {args={user=bohan}, data={"foo":"bar"}, files={}, form={}, headers={Accept=application/json, application/*+json, Content-Length=13, Content-Type=application/json, Host=httpbin.org, User-Agent=Java/18.0.1.1, X-Amzn-Trace-Id=Root=1-63baa01a-077505cc457a056209acb218, X-Requestid=123456}, json={foo=bar}, origin=103.152.220.44, url=https://httpbin.org/post?user=bohan}
     */
    @Test
    void handle() {

        JsonNode response = getJsonResource("node/http-demo.json", new TypeReference<>() {
        });

        // mock rest template
        Mockito.when(restTemplate.exchange(anyString(),
                        any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity(response, HttpStatus.OK));

        JsonNode result = nodeHandler.handle(null, SampleNodeContext.withMap(Map.of(
                "url", "https://httpbin.org/post",
                "method", "POST",
                "urlParams", Map.of("user", "bohan"),
                "body", Map.of("foo", "bar"),
                "headerParams", Map.of("X-RequestId", "123456")
        )));
        log.info("result: {}", result);

        // assert result
        Assertions.assertNotNull(result);
        Assertions.assertEquals("bar", result.get("json").get("foo").asText());
        Assertions.assertEquals("123456", result.get("headers").get("X-Requestid").asText());
        Assertions.assertEquals("bohan", result.get("args").get("user").asText());
    }
}