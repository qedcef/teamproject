package kr.co.pap.adminpage;

import lombok.Data;

@Data
public class EventVO {
	private int ev_id; 
	private String ev_startdate;//��������
	private String ev_enddate;//���������
	private String ev_pic;//������ ����
	private String ev_url;//��縵ũ
	private String ev_loc;//�����ġ,����?
	private String ev_name;//����̸�
}
