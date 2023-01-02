package com.bohanzhang.japier;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public abstract class BaseTest {

	@Autowired
	protected ObjectMapper mapper;

	@MockBean
	protected RestTemplate restTemplate;

}
