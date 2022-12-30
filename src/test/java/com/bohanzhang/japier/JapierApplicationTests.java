package com.bohanzhang.japier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mvel2.MVEL;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JapierApplicationTests {

	@Test
	void contextLoads() {
		Object eval = MVEL.eval("1 + 1");
		Assertions.assertEquals(2, eval);
	}

}
