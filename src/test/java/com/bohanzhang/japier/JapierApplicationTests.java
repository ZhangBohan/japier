package com.bohanzhang.japier;

import com.bohanzhang.japier.core.context.SampleNodeContext;
import com.bohanzhang.japier.taskhandler.github.GitHubLookupNodeHandler;
import com.bohanzhang.japier.taskhandler.github.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mvel2.MVEL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.endsWith;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class JapierApplicationTests {

	@Autowired
	private GitHubLookupNodeHandler gitHubLookupNodeHandler;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private RestTemplate restTemplate;


	@Test
	void contextLoads() {
		Demo demo = new Demo();
		log.debug("{}", demo);
		Object eval = MVEL.eval("1 + 1");
		Assertions.assertEquals(2, eval);
	}

	@Test
	void testGetUser() throws InterruptedException, ExecutionException {
		// Start the clock
		long start = System.currentTimeMillis();
		User user = new User();
		user.setName("test");

		Mockito.when(restTemplate.getForObject(endsWith("bohan"), any()))
          .thenReturn(user);

		// Kick of multiple, asynchronous lookups
		User result = gitHubLookupNodeHandler.handle(null, SampleNodeContext.
				builder().
				context(Map.of("user", "bohan")).
				build());

		log.info("--> " + result);
		Assertions.assertEquals("test", result.getName());
	}

}
