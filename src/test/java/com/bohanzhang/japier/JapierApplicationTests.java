package com.bohanzhang.japier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mvel2.MVEL;

class JapierApplicationTests {

	@Test
	void contextLoads() {
		Object eval = MVEL.eval("1 + 1");
		Assertions.assertEquals(2, eval);
	}

}
