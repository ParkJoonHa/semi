package com.repeat;
/** 자주하는 질문 게시판
 * repreatNum : 자주하는 질문 게시판 글번호(기본키)
 * listNum : 리스트 번호 
 * userId : 아이디(외래키)
 * userName : 이름
 * subject : 제목
 * content : 내용
 * created : 글 등록일
 * @author SIST
 *
 */
public class RepeatQnaDTO {
	private int repeatNum;
	private int listNum;
	private String userId;
	private String userName;
	private String subject;
	private String content;
	private String created;
	
	public int getRepeatNum() {
		return repeatNum;
	}
	public void setRepeatNum(int repeatNum) {
		this.repeatNum = repeatNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	
	
}
