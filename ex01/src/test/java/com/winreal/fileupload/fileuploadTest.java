package com.winreal.fileupload;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.winreal.board.boardTest;
import com.winreal.mapper.FileuploadMapper;
import com.winreal.vo.FileuploadVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class fileuploadTest {
	
	@Autowired
	FileuploadMapper mapper;

	@Test
	public void test() {
		log.info("insert()");
		FileuploadVO vo = new FileuploadVO();
		vo.setBno(50);
		vo.setFileName("fileName");
		vo.setFiletype("I");
		vo.setUploadPath("uploadPath");
		UUID uuid =UUID.randomUUID();
		vo.setUuid(uuid.toString());
		
		int res = mapper.insert(vo);
		
		assertEquals(0, res);
		
	}

	@Test
	public void testGetList() {
		log.info("getList");
		System.out.println(mapper.getList(50));
	}
	
	@Test
	public void delete() {
		log.info("delete()");
		int res = mapper.delete(4, "23d709c8-89bf-4cb1-8336-2beba56b5477");
		assertEquals(1, res);
		
	}
}
