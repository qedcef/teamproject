package kr.co.pap.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonObject;

import kr.co.pap.services.AccountService;
import kr.co.pap.services.BoardService;
import kr.co.pap.account.LoginVO;
import kr.co.pap.account.UserVO;
import kr.co.pap.board.BoardVO;
import kr.co.pap.board.HeartVO;
import kr.co.pap.board.PageMaker;
import kr.co.pap.board.ReplyVO;
import kr.co.pap.board.SearchCriteria;

@Controller
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	@Autowired
	BoardService service;
	@Autowired
	AccountService acservice;

	// ????????? ???????????? ??? ????????? ??? ??????
	@RequestMapping(value = "board/insert", method = RequestMethod.GET)
	public String insert(@RequestParam int ca, Model model, HttpSession session, HttpServletResponse response)
			throws IOException {

		int type = ca;
		model.addAttribute("type", type);

		LoginVO user = (LoginVO) session.getAttribute("user");
		if (user == null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('???????????? ????????? ??????????????????.'); history.go(-1); </script>");
			out.flush();
		}

		return "board/insert";
	}

	// ??? ??????
	@RequestMapping(value = "board/insert", method = RequestMethod.POST)
	public String insert(@RequestParam int ca, Model model, BoardVO boardVO, HttpServletRequest request,
			HttpSession session, HttpServletResponse response, String photo) throws Exception {

		logger.info("d" + boardVO.getUi_id());
		int a = (acservice.selectOne(boardVO.getUi_id())).getUi_prohibit();
		logger.info("d" + a);

		if (a == 2) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>alert('???????????? ?????????????????????.');history.go(-1);</script>");
			out.flush();
			return "redirec:/";
		}

		if (boardVO.getBo_title() == "") {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>alert('????????? ???????????????');history.go(-1);</script>");
			out.flush();
			return "redirec:/";
		}

		for (int i = 0; i < boardVO.getBo_num(); i++) {

		}

		request.setCharacterEncoding("utf-8");

		logger.info("??? ?????? ?????? : " + boardVO + "??????" + photo);
		String tempcon = boardVO.getBo_content();
		if (photo == null) {
			int r = service.insert(boardVO);
			int temp = ca;
			if (temp < 100 || temp == 901 || temp == 902) {
				return "redirect:/board/list?ca=" + temp;
			} else if (temp < 200) {
				return "redirect:/board/listpicpet?ca=" + temp;
			} else if (temp < 300) {
				return "redirect:/board/list?ca=" + temp;
			} else {
				return "redirect:/board/listpicmarket?ca=" + temp;
			}
		} else {

//			String content = tempcon.replace(photo, "");
//		boardVO.setBo_content(content);
			if (photo != null) {

				boardVO.setBo_photo(photo);

			}

			int r = service.insert(boardVO);

			int temp = ca;
			if (temp < 100) {
				return "redirect:/board/list?ca=" + temp;
			} else if (temp < 200) {
				return "redirect:/board/listpicpet?ca=" + temp;
			} else if (temp < 300) {
				return "redirect:/board/list?ca=" + temp;
			} else {
				return "redirect:/board/listpicmarket?ca=" + temp;
			}

		}

	}

	// ???????????? ??? ?????? ??????????????? ?????????
	@RequestMapping(value = "board/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
	@ResponseBody
	public Map<String, Object> uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile,
			HttpServletRequest request, Model model) {
		String photo = null;
		JsonObject jsonObject = new JsonObject();

		/*
		 * String fileRoot = "C:\\summernote_image\\"; // ??????????????? ????????? ????????????.
		 */

		// ?????? ????????? ??????
		String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
		System.out.println(contextRoot);
		String fileRoot = contextRoot + "resources/fileupload/";

		String originalFileName = multipartFile.getOriginalFilename(); // ???????????? ?????????
		String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // ?????? ?????????
		String savedFileName = UUID.randomUUID() + extension; // ????????? ?????? ???

		File targetFile = new File(fileRoot + savedFileName);
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile); // ?????? ??????
			jsonObject.addProperty("url", "/pap/resources/fileupload/" + savedFileName); // contextroot + resources +
																							// ????????? ?????? ?????? ???
			jsonObject.addProperty("responseCode", "success");

			photo = request.getContextPath() + "/resources/fileupload/" + savedFileName;
			
			
			
		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile); // ????????? ?????? ??????
			jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
		}
		String a = jsonObject.toString();

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("a", a);
		map.putIfAbsent("photo", photo);

		return map;
	}

	// ????????? ????????? ??????
	@RequestMapping(value = "board/listpicpet", method = RequestMethod.GET)
	public String listpicpet(@RequestParam("ca") int ca, Model model, @ModelAttribute("scri") SearchCriteria scri)
			throws Exception {
		int type = ca;
		model.addAttribute("type", type);

		scri.setCa_num(ca);
		List<BoardVO> list = service.list(scri);
		
		for(int i =0; i<list.size();i++) {
			if(list.get(i).getBo_photo()!=null) {
			String thumbnail = null;
			String [] st =list.get(i).getBo_photo().split(",");
			thumbnail = st[0];
			list.get(i).setBo_photo(thumbnail);
		}
		
	}
		
		
		
		logger.info("?????? : " + list);
		model.addAttribute("list", list);
		logger.info("?????? : " + service.listCount(scri));

		List<BoardVO> listnotice = service.listnotice(ca);
		model.addAttribute("listnotice", listnotice);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(service.listCount(scri));
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("scri", scri);
		logger.info(scri.toString());
		logger.info(pageMaker.toString());

		// ?????? ??? ?????? ??????
		int[] replyCnt = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			replyCnt[i] = service.replyCount(list.get(i).getBo_num());
		}
		model.addAttribute("replyCount", replyCnt);

		// ?????? ??? ?????? ??????
		List<BoardVO> listRecent = service.listRecent(ca);
		model.addAttribute("listRecent", listRecent);

		// ???????????? id??? ???????????????ncName????????????
		String[] ncName = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ncName[i] = service.getName(list.get(i).getUi_id());
		}
		model.addAttribute("ncName", ncName);

		return "board/listpicpet";
	}

	// ???????????? ?????? ??????

	@RequestMapping(value = "board/listing", method = RequestMethod.GET)
	public String listing(@RequestParam("ca") int ca, Model model, @ModelAttribute("scri") SearchCriteria scri)
			throws Exception {

		int type = ca;
		model.addAttribute("type", type);

		scri.setCa_num(ca);
		List<BoardVO> list = service.listing(scri);
		
		for(int i =0; i<list.size();i++) {
			if(list.get(i).getBo_photo()!=null) {
			String thumbnail = null;
			String [] st =list.get(i).getBo_photo().split(",");
			thumbnail = st[0];
			list.get(i).setBo_photo(thumbnail);
		}
		
	}

		logger.info("?????? : " + list);
		model.addAttribute("list", list);
		logger.info("?????? : " + service.listingCount(scri));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(service.listingCount(scri));
		model.addAttribute("pageMaker", pageMaker);

		// ?????? ??? ?????? ??????
		int[] replyCnt = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			replyCnt[i] = service.replyCount(list.get(i).getBo_num());
		}
		model.addAttribute("replyCount", replyCnt);

		// ?????? ??? ?????? ??????
		List<BoardVO> listRecent = service.listRecent(ca);
		model.addAttribute("listRecent", listRecent);

		logger.info(scri.toString());
		logger.info(pageMaker.toString());

		// ???????????? id??? ???????????????ncName????????????
		String[] ncName = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ncName[i] = service.getName(list.get(i).getUi_id());
		}
		model.addAttribute("ncName", ncName);

		return "board/listing";

	}

	// ???????????? ????????? ??????
	@RequestMapping(value = "board/listpicmarket", method = RequestMethod.GET)
	public String listpicmarket(@RequestParam("ca") int ca, Model model, @ModelAttribute("scri") SearchCriteria scri)
			throws Exception {

		int type = ca;
		model.addAttribute("type", type);

		scri.setCa_num(ca);
		List<BoardVO> list = service.list(scri);
		logger.info("?????? : " + list);
		model.addAttribute("list", list);
		logger.info("?????? : " + service.listCount(scri));

	
	

		for(int i =0; i<list.size();i++) {
			if(list.get(i).getBo_photo()!=null) {
			String thumbnail = null;
			String [] st =list.get(i).getBo_photo().split(",");
			thumbnail = st[0];
			list.get(i).setBo_photo(thumbnail);
		}
		
	}
		
		
		
		List<BoardVO> listnotice = service.listnotice(ca);
		model.addAttribute("listnotice", listnotice);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(service.listCount(scri));
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("scri", scri);
		logger.info(scri.toString());
		logger.info(pageMaker.toString());

		// ?????? ??? ?????? ??????
		int[] replyCnt = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			replyCnt[i] = service.replyCount(list.get(i).getBo_num());
		}
		model.addAttribute("replyCount", replyCnt);

		// ?????? ??? ?????? ??????
		List<BoardVO> listRecent = service.listRecent(ca);
		model.addAttribute("listRecent", listRecent);

		// ???????????? id??? ???????????????ncName????????????
		String[] ncName = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ncName[i] = service.getName(list.get(i).getUi_id());
		}
		model.addAttribute("ncName", ncName);

		return "board/listpicmarket";
	}

	// ??????????????? ????????????
	@RequestMapping(value = "board/list", method = RequestMethod.GET)
	public String list(@RequestParam("ca") int ca, Model model, @ModelAttribute("scri") SearchCriteria scri)
			throws Exception {

		int type = ca;
		model.addAttribute("type", type);

		scri.setCa_num(ca);
		List<BoardVO> list = service.list(scri);
		logger.info("?????? : " + list);
		model.addAttribute("list", list);
		logger.info("?????? : " + service.listCount(scri));

		List<BoardVO> listnotice = service.listnotice(ca);
		model.addAttribute("listnotice", listnotice);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(service.listCount(scri));
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("scri", scri);
		logger.info(scri.toString());
		logger.info(pageMaker.toString());

		// ?????? ??? ?????? ??????
		int[] replyCnt = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			replyCnt[i] = service.replyCount(list.get(i).getBo_num());
		}
		model.addAttribute("replyCount", replyCnt);

		// ???????????? id??? ???????????????ncName????????????
		String[] ncName = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ncName[i] = service.getName(list.get(i).getUi_id());
		}
		model.addAttribute("ncName", ncName);

		// ???????????? id??? ???????????????Grade????????????
		String[] grade = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			grade[i] = service.getGrade(list.get(i).getUi_id());
		}
		model.addAttribute("grade", grade);

		return "board/list";
	}

	// ????????? ?????? ??????
	@RequestMapping(value = "board/detail", method = RequestMethod.GET)
	public String detail(@RequestParam int num, HttpSession session, Model model,
			@ModelAttribute("scri") SearchCriteria scri, HttpServletResponse response) throws Exception {
		logger.info("=====detail====" + num);

		int bo_num = num;
		BoardVO board = service.detail(bo_num);

		// ????????? ??? url?????? ??????
		if (board.getBo_status() == 1 || board.getBo_status() == 3) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('????????? ????????????.'); history.go(-1); </script>");
			out.flush();
		}

		model.addAttribute("board", board);

		model.addAttribute("replyCount", service.replyCount(bo_num));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(service.listCount(scri));
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("scri", scri);

		// ??? ????????? id??? ??????????????? ????????? ??????
		String ncName = service.getName(board.getUi_id());
		model.addAttribute("ncName", ncName);

		// ?????????
		LoginVO user = (LoginVO) session.getAttribute("user");

		if (user == null) {
			// ????????? ??????
			HeartVO heart = new HeartVO();
			heart.setBo_num(bo_num);
			System.out.println(heart.getUi_id());
			System.out.println(heart.getBo_num() + "???????????????");

			Integer like = service.findLike(heart);
			if (like != null) {

				model.addAttribute("like", like);
				return "board/detail";
			} else {
				model.addAttribute("like", 0);
				return "board/detail";
			}

		} else if (board.getCa_num() == 902) {
			if (user == null) {
				return "redirect:/login";
			} else if (!(user.getUi_grade().equals("?????????") || user.getUi_grade().equals("?????????")
					|| user.getUi_id().equals(board.getUi_id()))) {
				logger.info("????????? id : " + board.getUi_id() + " / ????????? id : " + user.getUi_id());
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('????????? ????????????.'); history.go(-1); </script>");
				out.flush();
				return "board/list?ca=902";
			} else {
				logger.info("????????? id : " + board.getUi_id() + " / ????????? id : " + user.getUi_id());
				return "board/detail";
			}
		} // end of else if
		else {

			// ????????? ??????
			HeartVO heart = new HeartVO();
			heart.setBo_num(bo_num);
			heart.setUi_id(user.getUi_id());
			System.out.println(heart.getUi_id());
			System.out.println(heart.getBo_num() + "???????????????");

			Integer like = service.findLike(heart);
			if (like != null) {

				model.addAttribute("like", like);
				return "board/detail";
			} else {
				model.addAttribute("like", 0);
				return "board/detail";
			}

		} // end of else

	}

	// ????????? ??????
	@RequestMapping(value = "board/delete", method = RequestMethod.GET)
	public String delete(@RequestParam int num, HttpSession session, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		logger.info("====delete====" + num);
		request.setCharacterEncoding("utf-8");
		BoardVO board = service.detail(num);
		int temp = board.getCa_num();

		LoginVO user = (LoginVO) session.getAttribute("user");
		if (user == null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('???????????? ????????? ??????????????????.'); history.go(-1); </script>");
			out.flush();
		} else if (user.getUi_grade().equals("?????????") || user.getUi_grade().equals("?????????")
				|| user.getUi_id().equals(board.getUi_id())) {
			response.setContentType("text/html; charset=UTF-8");
			int r = service.delete(num);

			if (temp < 100 || temp == 901 || temp == 902) {
				return "redirect:/board/list?ca=" + temp;
			} else if (temp < 200) {
				return "redirect:/board/listpicpet?ca=" + temp;
			} else if (temp < 300) {
				return "redirect:/board/list?ca=" + temp;
			} else {
				return "redirect:/board/listpicmarket?ca=" + temp;
			}

		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('????????? ????????? ??????????????????.'); history.go(-1); </script>");
			out.flush();
		}

		if (temp < 100 || temp == 901 || temp == 902) {
			return "redirect:/board/list?ca=" + temp;
		} else if (temp < 200) {
			return "redirect:/board/listpicpet?ca=" + temp;
		} else if (temp < 300) {
			return "redirect:/board/list?ca=" + temp;
		} else {
			return "redirect:/board/listpicmarket?ca=" + temp;
		}

	}

	// ????????? ?????? ???
	@RequestMapping(value = "board/update", method = RequestMethod.GET)
	public String update(@RequestParam int num, Model model) throws Exception {
		BoardVO board = service.detail(num);
		model.addAttribute("board", board);

		// ??? ????????? id??? ??????????????? ????????? ??????
		String ncName = service.getName(board.getUi_id());
		model.addAttribute("ncName", ncName);

		return "board/update";
	}

	// ????????? ?????? DB ??????
	@RequestMapping(value = "board/update", method = RequestMethod.POST)
	public String update(@RequestParam int num, BoardVO boardVO, HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		logger.info("???????????? : " + boardVO);
		
		String imagecheck = boardVO.getBo_content();
		imagecheck.indexOf("<img");
		String photocheck = boardVO.getBo_photo();
		
		
		
		String [] ph = imagecheck.split("\"");
		for(int i =0; i<ph.length; i++){
			System.out.println("ph:"+ph[i]);
			}
		
		
		
		if(imagecheck.indexOf("<img")==-1) {
			boardVO.setBo_photo(null);
		}else if(imagecheck.indexOf(photocheck)==-1) {
			boardVO.setBo_photo(ph[1]);
		}
		

	
		
		int r = service.update(boardVO);
		if (r > 0) { // ????????? ???????????? ??????????????? ??????

			return "redirect:detail?num=" + num;
		}

		// ????????? ???????????? ???????????? ???????????? ??????
		return "redirect:update?num=" + num;

	}

//	????????? ??????
	@RequestMapping(value = "board/report", method = RequestMethod.GET)
	public String report(@RequestParam int num) throws Exception {
		logger.info("====report====" + num);

		int r = service.report(num);
		BoardVO board = service.detail(num);

		int temp = board.getCa_num();
		if (temp < 100 || temp == 901 || temp == 902) {
			return "redirect:/board/list?ca=" + temp;
		} else if (temp < 200) {
			return "redirect:/board/listpicpet?ca=" + temp;
		} else if (temp < 300) {
			return "redirect:/board/list?ca=" + temp;
		} else {
			return "redirect:/board/listpicmarket?ca=" + temp;
		}

	}

//	ajax ????????? ?????? ????????? ???????????? ??????(model??? ????????? ??????)
	@ResponseBody
	@PostMapping(value = "board/list3")
	public Map<String, Object> list3(@RequestParam("bo_num") int bo_num) throws Exception {
		logger.info(Integer.toString(bo_num) + "??? ????????? ?????? ?????????");
		List<ReplyVO> list = service.list3(bo_num);

		Map<String, Object> list3 = new HashMap<>();
		list3.put("comments", list);

		// ??????????????? id??? ???????????????ncName????????????
		String[] ncName = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ncName[i] = service.getName(list.get(i).getUi_id());
		}

		list3.put("ncName", ncName);

		return list3;
	}

// ajax ?????? id??? ??????????????? ????????? ????????????
	@ResponseBody
	@GetMapping(value = "board/getName3")
	public Map<String, Object> getName3(@RequestParam("re_id") String re_id) throws Exception {

		System.out.println("?????? ?????????" + re_id);
//		String ncName = service.getName(re_id);
//		logger.info(ncName);
		Map<String, Object> ncName = new HashMap<>();
		ncName.put("name", service.getName(re_id));
		ncName.put("grade", service.getGrade(re_id));

		return ncName;
	}

	// ajax ?????? id??? ??????????????? ?????? ????????????
	@ResponseBody
	@GetMapping(value = "board/getGrade3")
	public Map<String, Object> getGrade3(@RequestParam("re_id") String re_id) throws Exception {

		System.out.println("?????? ?????????" + re_id);

		Map<String, Object> ncGrade = new HashMap<>();
		ncGrade.put("grade", service.getGrade(re_id));

		return ncGrade;
	}

//	ajax ?????? ?????? 
	@ResponseBody
	@PostMapping(value = "board/insert3")
	public int insert3(ReplyVO reply) throws Exception {
		return service.insert3(reply);
	}

	// ajax ?????? ??????
	@ResponseBody
	@PostMapping(value = "board/update3")
	public int update3(ReplyVO replyvo) throws Exception {

		return service.update3(replyvo);
	}

// 	ajax ?????? ??????
	@ResponseBody
	@PostMapping(value = "board/delete3")
	public int delete3(@RequestParam("co_num") int co_num) throws Exception {

		return service.delete3(co_num);
	}

//	ajax ?????? ?????? 
	@ResponseBody
	@PostMapping(value = "board/report3")
	public int report3(@RequestParam("co_num") int co_num) throws Exception {

		return service.report3(co_num);
	}

	// ajax ????????? ?????????
	@ResponseBody
	@PostMapping(value = "board/like")
	public int like(@RequestBody HeartVO vo) throws Exception {
		System.out.println(vo.getUi_id());
		System.out.println(vo.getBo_num());

		service.like(vo.getBo_num());
		return service.like2(vo);
	}

	// ajax ????????? ????????? ??????
	@ResponseBody
	@PostMapping(value = "board/dislike")
	public int dislike(@RequestBody HeartVO vo) throws Exception {

		System.out.println(vo.getBo_num());
		System.out.println(vo.getHe_num() + "??????????????? ?????????");

		service.dislike(vo.getBo_num());
		return service.dislike2(vo.getHe_num());

	}

	// ajax ?????? ????????? & ??????
	@ResponseBody
	@PostMapping(value = "board/like3")
	public int like3(@RequestBody HeartVO vo) throws Exception {
		System.out.println(vo.getUi_id());
		System.out.println(vo.getCo_num());

		// ????????? ??????
		Integer likeCheck = service.findLike3(vo);
		if (likeCheck == null) {
			// ????????? ????????????
			service.like3(vo.getCo_num());
			service.like2(vo);
			likeCheck = 0;
			return likeCheck;
		} else { // ????????? ???????????? ??????
			service.dislike3(vo.getCo_num());
			service.dislike4(vo);
			likeCheck = 1;
			return likeCheck;
		}

	}

	// ?????? detail????????? ??? ?????? ????????? ??????
	@ResponseBody
	@PostMapping(value = "board/likeReply")
	public int likeReply(@RequestBody HeartVO vo) throws Exception {
		Integer likeCheck = service.findLike3(vo);
		if (likeCheck == null) {
			likeCheck = 0;
			return likeCheck;
		} else {
			likeCheck = 1;
			return likeCheck;
		}

	}

	// ?????? ??? ??????
	@RequestMapping(value = "map/relatedPost", method = RequestMethod.GET)
	public String relatedPost(@RequestParam("pl_name") String pl_name, Model model) throws Exception {

		List<BoardVO> list = service.relatedPost(pl_name);
		logger.info("?????? : " + list);
		model.addAttribute("list", list);

		return "map/relatedPost";
	}

}
