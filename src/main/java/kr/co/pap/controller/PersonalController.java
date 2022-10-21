package kr.co.pap.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import kr.co.pap.account.LoginVO;
import kr.co.pap.account.UserVO;
import kr.co.pap.board.BoardVO;
import kr.co.pap.personal.CalVO;
import kr.co.pap.personal.Criteria;
import kr.co.pap.personal.MemoVO;
import kr.co.pap.personal.PageMaker;
import kr.co.pap.personal.PagingVO;
import kr.co.pap.services.PersonalService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PersonalController {
	
	@Autowired
	private PersonalService pservice;
	
	@RequestMapping(value="personal/main", method = RequestMethod.GET)
	public String perMain(UserVO vo, Model model) {
		model.addAttribute("vo", vo);
		return "/psnal/personal";
	}
	
	@RequestMapping(value="memo/main", method = RequestMethod.GET)
	public String memoMain(@RequestParam("ui_id") String ui_id,Model model,Criteria cri) {
		
		model.addAttribute("memo", pservice.memo(cri));
		
		PageMaker page = new PageMaker();
		page.setCri(cri);
		page.setTotalCount(pservice.memoCnt());
		
		model.addAttribute("page", page);
		
		return "/psnal/memo";
	}
	
	
	@RequestMapping(value="memo/insert", method = RequestMethod.GET)
	public String memoInsert() {
		return "/psnal/memoInsert";
	}
	
	@PostMapping(value="memo/insert")
	public String memoInsert(MemoVO mv,HttpServletRequest request) throws Exception {
		System.out.println(mv.getUploadFile());
		String path = request.getSession().getServletContext().getRealPath("/resources/images/memo");
		// 파일 업로드 처리
		String fileName=null;
		MultipartFile uploadFile = mv.getUploadFile();
		if (!uploadFile.isEmpty()) {
			String originalFileName = uploadFile.getOriginalFilename();
			String ext = FilenameUtils.getExtension(originalFileName);	//확장자 구하기
			UUID uuid = UUID.randomUUID();	//UUID 구하기
			fileName=uuid+"."+ext;
			uploadFile.transferTo(new File(path + "//" + fileName));
			mv.setMm_filename(fileName);
		}
			pservice.insertmemo(mv);
			return "redirect:main?ui_id=" + mv.getUi_id();
	}
	
	@RequestMapping(value="memo/detail", method = RequestMethod.GET)
	public ModelAndView memodetail(@RequestParam("mm_mno")int mm_mno) {
		MemoVO memo = pservice.detailmemo(mm_mno);
		ModelAndView mav = new ModelAndView();
		mav.addObject("memo", memo);
		mav.setViewName("psnal/detail");
		return mav;
		
	}
	
	@RequestMapping(value="memo/update", method = RequestMethod.GET)
	public String memoupdate(@RequestParam("mm_mno")int mm_mno,Model model) {
		MemoVO memo = pservice.detailmemo(mm_mno);
		model.addAttribute("memo", memo);
		return "psnal/memoupdate";
	}
	
	@RequestMapping(value="memo/update", method = RequestMethod.POST)
	public String memoupdate(MemoVO mv,HttpServletRequest request) throws Exception {
		
			String path = request.getSession().getServletContext().getRealPath("/resources/images/memo");
			MultipartFile uploadFile = mv.getUploadFile();
			System.out.println(uploadFile);
			
		   if(!uploadFile.isEmpty()) {
			    // 기존 파일을 삭제
			   	new File(path + "\\" + mv.getMm_filename()).delete();
			    // 새로 첨부한 파일을 등록
			    String fileName=null;
			    String originalFileName = uploadFile.getOriginalFilename();
				String ext = FilenameUtils.getExtension(originalFileName);	//확장자 구하기
				UUID uuid = UUID.randomUUID();	//UUID 구하기
				fileName=uuid+"."+ext;
				uploadFile.transferTo(new File(path + "//" + fileName));
			    
				mv.setMm_filename(fileName);
			   }else {
				   System.out.println(request.getParameter("mm_filename"));
				   mv.setMm_filename(request.getParameter("mm_filename"));
			   }
			   
		   int r = pservice.updatememo(mv);
		if(r>0) {
			return "redirect:detail?mm_mno=" + mv.getMm_mno();
		}else {
			return "redirect:update?mm_mno=" + mv.getMm_mno();
		}
		
	}
	
	@RequestMapping(value="memo/delete", method = RequestMethod.GET)
	public String memodelete(@RequestParam("ui_id") String ui_id,@RequestParam("mm_mno") int mm_mno,
			HttpServletRequest request) {
		
		String path = request.getSession().getServletContext().getRealPath("/resources/images/memo");
		MemoVO memo = pservice.detailmemo(mm_mno);
		File file = new File(path + "\\" + memo.getMm_filename());
		file.delete(); // 파일 삭제
		
		int r = pservice.deletememo(mm_mno);
		if(r>0) {
			return "redirect:main?ui_id=" + ui_id;
		}else {
			return "redirect:detail?mm_mno=" + mm_mno;
		}
	}
	
	@GetMapping(value="memo/delfile")
	public String delfile(@RequestParam("mm_mno") int mm_mno,HttpServletRequest request) {
		
		String path = request.getSession().getServletContext().getRealPath("/resources/images/memo");
		MemoVO memo = pservice.detailmemo(mm_mno);
		
		//현재 게시판에 존재하는 파일객체를 만듬
		File file = new File(path + "\\" + memo.getMm_filename());
				
		if(file.exists()) { // 파일이 존재하면
			file.delete(); // 파일 삭제	
		}
		
		memo.setMm_filename(null);
		pservice.updatememo(memo);
		return "redirect:detail?mm_mno=" + mm_mno;
	}
	
	@GetMapping(value="cal/main")
	public String calMain(@RequestParam("ui_id")String ui_id) {
		return "psnal/calendar";
	}

	@ResponseBody
	@GetMapping(value="cal/main2")
	public List<Map<String, Object>> calMain2(@RequestParam("ui_id")String ui_id){
		List<CalVO> cal = pservice.calendar(ui_id);

		JSONArray jsonArr = new JSONArray();

		HashMap<String, Object> hash = new HashMap<String, Object>();

		for(int i=0;i<cal.size();i++) {
			hash.put("ui_id", cal.get(i).getUi_id());
			hash.put("id", cal.get(i).getCd_id());
			hash.put("title", cal.get(i).getCd_title());
			hash.put("start", cal.get(i).getCd_start());
			hash.put("end", cal.get(i).getCd_end());
			hash.put("allDay", cal.get(i).isCd_allday());
		JSONObject jsonObj = new JSONObject(hash);
		jsonArr.add(jsonObj);
		}

		return jsonArr;
	}
	
	@ResponseBody
	@RequestMapping(value="cal/insert",method = {RequestMethod.GET,RequestMethod.POST})
	public int calinsert(CalVO cv,HttpSession session) throws Exception {
		LoginVO lv = (LoginVO) session.getAttribute("user");
		cv.setUi_id(lv.getUi_id());
		System.out.println(cv);
		return pservice.calinsert(cv);
		}
	
	@ResponseBody
	@GetMapping(value="cal/delete")
	public int caldelete(@RequestParam("cd_id")int cd_id) {
		return pservice.caldelete(cd_id);
	}
	
	@RequestMapping(value="myBoardList", method = RequestMethod.GET)
	public String myboard(HttpSession session, String nowPage, String cntPerPage, String ca, Model model) {
	    LoginVO vo = (LoginVO)session.getAttribute("user");
	    String id = vo.getUi_id();
	    
	    if(nowPage == null & cntPerPage == null) {
	        nowPage = "1";
	        cntPerPage = "10";
	    }else if(nowPage == null) {
	        nowPage = "1";
	    }else if(cntPerPage == null) {
	        cntPerPage = "10";
	    }
	    
	    if(ca == null || ca.equals("")) {
	        ca = "1";
	    }
	    
	    BoardVO bvo = new BoardVO();
	    bvo.setUi_id(id);
	    bvo.setCa_num(Integer.valueOf(ca));
	    int total = pservice.myBoardCount(bvo);
	    
	    PagingVO paging = new PagingVO(total, Integer.valueOf(nowPage), Integer.valueOf(cntPerPage));
	    paging.setCa(Integer.valueOf(ca));
	    paging.setUi_id(id);
	    List<BoardVO> blist = pservice.myBoardList(paging);
	    model.addAttribute("paging", paging);
	    model.addAttribute("list", blist);
	    
		return "psnal/myBoardList";
	}
}
