package som.winreal.reply;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.winreal.mapper.ReplyMapper;
import com.winreal.vo.Criteria;
import com.winreal.vo.ReplyVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTest {
	
	@Autowired
	ReplyMapper mapper;
	
	@Test
	public void test() {
		assertNotNull(mapper);
		Criteria cri = new Criteria();
		cri.setAmount(5);
		cri.setPageNo(1);
		List<ReplyVO> list = mapper.getList(1, cri);
		log.info("======================");
		log.info("list : " + list);
	}
	
	@Test
	public void insertTest() {
		ReplyVO vo = new ReplyVO();
		
		vo.setBno(83);
		vo.setReply("댓글을 달아 보아요");
		vo.setReplyer("작성자");
		
		int res = mapper.insert(vo);
		
		assertEquals(res, 1);
	}
	
	@Test
	public void deleteTest() {	
		int rno = 15;
		int res = mapper.delete(rno);
		
		assertEquals(1, res);
	}
	
	@Test
	public void updateTest() {
		ReplyVO vo = new ReplyVO();
		
		vo.setRno(10);
		vo.setReply("댓글을 수정하기");
		
		int res = mapper.update(vo);
		
		assertEquals(res, 1);
	}
	
	
	@Test
	public void totalCnt() {
		int res = mapper.totalCnt(30);
		System.out.println(res);
	}
}











