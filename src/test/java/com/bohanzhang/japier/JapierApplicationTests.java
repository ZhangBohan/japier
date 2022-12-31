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

import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.any;

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

		Mockito.when(restTemplate.getForObject(any(), any()))
          .thenReturn(user);

		// Kick of multiple, asynchronous lookups
		User page1 = gitHubLookupNodeHandler.handle(null, SampleNodeContext.EMPTY);

		// Print results, including elapsed time
		log.info("Elapsed time: " + (System.currentTimeMillis() - start));
		log.info("--> " + page1);

	}

}
