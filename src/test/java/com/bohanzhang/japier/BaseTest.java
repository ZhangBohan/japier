package com.bohanzhang.japier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public abstract class BaseTest {

	@MockBean
	protected RestTemplate restTemplate;

}
