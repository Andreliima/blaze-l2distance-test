package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import(TestContainerConfiguration.class)
class DemoApplicationTests {

	@Autowired
	private ImageQuery imageQuery;

	@Test
	void testQuery() {
		var result = imageQuery.find();
		assertEquals(0, result.size());
	}

}
