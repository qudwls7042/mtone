package com.torun.mtone;

import com.torun.mtone.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MtoneApplicationTests {

	@Autowired
	private BoardMapper boardMapper;


	@Test
	public void testGetTime() {

	}
	//XML 방식
	@Test
	public void testGetTime2() {
	}


}
