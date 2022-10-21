package kr.co.pap.services;


import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.pap.DAO.AccountDAO;
import kr.co.pap.account.LoginVO;
import kr.co.pap.account.UserVO;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountDAO dao;
	
	@Override
	public int registerUser(UserVO vo) throws Exception {
		// TODO Auto-generated method stub
		if(vo.getUi_birth().isEmpty()) {
			vo.setUi_birth(null);
		}
		
		return dao.registerUser(vo);
	}
	

	@Override
	public LoginVO login(LoginVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.login(vo);
	}
@Override
	public UserVO selecOneUser(String id) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectOneUser(id);
	}

	@Override
	public int dueplicate(String id) throws Exception {
		
		return dao.UserIdCount(id);
	}
	@Override
	public int ncdueplicate(String ncName) throws Exception {
		// TODO Auto-generated method stub
		return dao.UserNcCount(ncName);
	}
	@Override
	public String confirmPW(LoginVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.confirmPW(vo);
	}
		
	@Override
	public UserVO selectOne(String ui_id) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectOneUser(ui_id);
	}

	@Override
	public int userupdate(UserVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.userupdate(vo);
	}

	@Override
	public int userdel(String ui_id) {
		// TODO Auto-generated method stub
		return dao.userdel(ui_id);
	}


	@Override
	public LoginVO kakaocheck(String ui_id) throws Exception {
		// TODO Auto-generated method stub
		return dao.kakaocheck(ui_id);
	}


	@Override
	public int kakaoregister(UserVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.registerUser(vo);
	}

    @Override
    public int countEmail(String email) throws Exception {
        
        return dao.countEmail(email);
    }


    @Override
    public UserVO idSearch(String email) throws Exception {
        // TODO Auto-generated method stub
        return dao.idSearch(email);
    }


    @Override
    public String insertProfile(MultipartFile file, HttpServletRequest request) {
        
        String fileRealName = file.getOriginalFilename(); //파일명을 얻어낼 수 있는 메서드!
        long size = file.getSize(); //파일 사이즈
        String saveDir = request.getSession().getServletContext().getRealPath("/resources/images/profile/");
        System.out.println("파일명 : "  + fileRealName);
        System.out.println("파일용량(byte) : " + size);
        //서버에 저장할 파일이름 fileextension으로 .jsp이런식의  확장자 명을 구함
        String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."),fileRealName.length());
        String uploadFolder = saveDir;
        System.out.println(uploadFolder);
        /*
          파일 업로드시 파일명이 동일한 파일이 이미 존재할 수도 있고 사용자가 
          업로드 하는 파일명이 언어 이외의 언어로 되어있을 수 있습니다. 
          타인어를 지원하지 않는 환경에서는 정산 동작이 되지 않습니다.(리눅스가 대표적인 예시)
          고유한 랜던 문자를 통해 db와 서버에 저장할 파일명을 새롭게 만들어 준다.
         */
        
        
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
        String[] uuids = uuid.toString().split("-");
        
        String uniqueName = uuids[0];
        System.out.println("생성된 고유문자열" + uniqueName);
        System.out.println("확장자명" + fileExtension);
        String img = uniqueName + fileExtension;
        
        // File saveFile = new File(uploadFolder+"\\"+fileRealName); uuid적용 전
        
        File saveFile = new File(uploadFolder+"\\"+uniqueName + fileExtension);  // 적용 후
        try {
            file.transferTo(saveFile); // 실제 파일 저장메서드(filewriter 작업을 손쉽게 한방에 처리해준다.)
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return img;
    }


}
