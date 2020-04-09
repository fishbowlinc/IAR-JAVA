package com.fb.in.app.reporting;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fb.in.app.reporting.sso.auth.SisenseUtil;

@SpringBootTest
public class SisenseAPITest {

	@Test
	public void getSisenseUserIdByUsername() {
		String userId = SisenseUtil.getUserIdByUsername("jhanna");
		assertEquals(userId, "5dd3ad05685dc248f8b4a1a6");

	}
}
