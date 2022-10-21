import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.co.pap.board.BoardVO;
import kr.co.pap.DAO.BoardDAO;
import kr.co.pap.chat.DateNow;

//현재 테스트 코드를 실행할 때 스프링이 로딩 되도록 하는 부분.
@RunWith(SpringJUnit4ClassRunner.class)
//속성 경로에 xml파일을 이용해 스프링이 로딩 된다.
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/root-context.xml" })

public class BoardCreateTest {
    //위에서 스프링이 정상적으로 동작할 경우 스프링이 생성해서 주입해준다.
    //root-context.xml 파일에서 설정한 SqlSesiionFactoryBean설정에 문제가 있었다면 이 코드에서 에러가 발생한다.
	@Inject
	private BoardDAO boardDAO;
	
	@Test
	public void testInsert() throws Exception{
		BoardVO board;
		
		for(int i = 1; i < 9; i++) {
			board = new BoardVO();
			board.setBo_title("마켓 ["+ i + "]");
			board.setBo_content("테스트 - " + i);
			board.setBo_DATE(DateNow.getDateNow());
			board.setBo_heart(0);
			board.setBo_status(0);
			board.setBo_notice(0);
			board.setPl_lat(0);
			board.setPl_lon(0);
			
			
			board.setCa_num(303);
		
			if(i%2==0) {
				board.setBo_name("통키");
				board.setUi_id("tonki");
				board.setBo_pstatus("판매중");
			}else {
				board.setBo_name("삐삐");
				board.setUi_id("pipi");
				board.setBo_pstatus("예약중");
			}
			
			
			boardDAO.insert(board); // 글쓰기
			
			Thread.sleep(1000); // 1초간 잠 자기 : 해킹의심방지
		}
		
		
	}
	
	
	
	
	
	
	

}