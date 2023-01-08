package com.bohanzhang.japier.taskhandler;

import com.bohanzhang.japier.core.context.NodeContext;
import com.bohanzhang.japier.core.node.DefaultNodeHandler;
import com.bohanzhang.japier.core.node.Node;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component("http")
@Slf4j
public class HttpNodeHandler implements DefaultNodeHandler<JsonNode> {


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public JsonNode handle(Node node, NodeContext context) {
        String urlString = context.getString("url");
        String methodString = context.getString("method");
        HttpMethod method = HttpMethod.valueOf(methodString);

        MultiValueMap<String, String> valueMap = getMultiValueMap(context, "urlParams");
        // Query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(urlString)
                // Add query parameter
                .queryParams(valueMap);

        JsonNode body = context.get("body", JsonNode.class);
        MultiValueMap<String, String> headerParams = getMultiValueMap(context, "headerParams");
        HttpEntity<JsonNode> request = new HttpEntity<>(body, headerParams);
        ResponseEntity<JsonNode> response = restTemplate.exchange(builder.toUriString(), method, request, JsonNode.class);
        return response.getBody();
    }

    private static MultiValueMap<String, String> getMultiValueMap(NodeContext context, String paramKey) {
        Map<String, String> urlParams = context.get(paramKey, new TypeReference<>() {
        });
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        if (urlParams == null) {
            return valueMap;
        }
        urlParams.keySet().forEach(key -> valueMap.add(key, urlParams.get(key)));
        return valueMap;
    }
}
