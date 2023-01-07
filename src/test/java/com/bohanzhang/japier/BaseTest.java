package com.bohanzhang.japier;

import com.bohanzhang.japier.core.node.Node;
import com.bohanzhang.japier.exception.JapierException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public abstract class BaseTest {

	@Autowired
	protected ObjectMapper mapper;

	protected static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory())
			.setSerializationInclusion(JsonInclude.Include.NON_NULL)
			.findAndRegisterModules();

	@MockBean
	protected RestTemplate restTemplate;

	protected <T> T getYamlResource(String resourcePath, TypeReference<T> typeReference) {
		try {
			File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + resourcePath);
			return YAML_MAPPER.readValue(file, typeReference);
		} catch (FileNotFoundException e) {
			throw new JapierException("resource not found", e);
		} catch (IOException e) {
			throw new JapierException("getYamlResource read value error", e);
		}
	}

	protected List<Node> getNodes(String resourcePath) {
		List<Node> nodeList = getYamlResource(resourcePath, new TypeReference<>() {
		});
		return nodeList;
	}

}
