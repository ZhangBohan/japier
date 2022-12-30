package com.bohanzhang.japier;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mvel2.MVEL;

@Slf4j
class JapierApplicationTests {

	@Test
	void contextLoads() {
		Demo demo = new Demo();
		log.debug("{}", demo);
		Object eval = MVEL.eval("1 + 1");
		Assertions.assertEquals(2, eval);
	}

}
