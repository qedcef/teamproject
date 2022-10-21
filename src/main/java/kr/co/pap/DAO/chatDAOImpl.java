package kr.co.pap.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.pap.account.UserVO;
import kr.co.pap.chat.ChatVO;

@Repository
public class chatDAOImpl implements chatDAO {
	@Autowired
	private SqlSession sqlSession;
	
	private static final String namespace = "kr.co.pap.chatMapper";
	
	@Override
	public int addChat(ChatVO chatVO) throws Exception {
		// 대화내용추가
		return sqlSession.insert(namespace+".addChat", chatVO);
	}

	@Override
	public List<UserVO> listUsers() throws Exception {
		// 회원목록조회
		return sqlSession.selectList(namespace+".listUsers");
	}

	@Override
	public List<ChatVO> getChat(ChatVO chatVO) throws Exception {
		// 대화목록불러오기
		return sqlSession.selectList(namespace+".getChat", chatVO);
	}

	@Override
	public List<Map<String, Object>> autocomplete(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+".autocomplete", paramMap);
	}

	@Override
	public UserVO search(String ui_ncname) throws Exception {
		// 닉네임 검색
		return sqlSession.selectOne(namespace+".search", ui_ncname);
	}

	
	

	

}
