package kr.co.pap.personal;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MemoVO{
	private String ui_id; 
	private int mm_mno;
	private String mm_title; 
	private String mm_date; 
	private String mm_contents;
	private String mm_filename;
	private MultipartFile uploadFile;
}
