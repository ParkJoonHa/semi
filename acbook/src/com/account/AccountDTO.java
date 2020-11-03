package com.account;
/**
 * abNum : 가계부 번호(기본키)
 * userId : 아이디(외래키)
 * userName : 이름
 * subject : 제목
 * content : 내용(지출및수입 내용)
 * amount : 수입/지출
 * abDate : 수입/지출날짜
 * status : 수입 지출 및 수입/지출 날짜 구별 용도
 * created : 등록일
 * 
 * @author SIST
 *
 */
public class AccountDTO {
	private int abNum;
	private String userId;
	private String userName;
	private String subject;
	private String content;
	private String amount;
	private String abDate;
	private int status;
	private String created;
	
	public int getAbNum() {
		return abNum;
	}
	public void setAbNum(int abNum) {
		this.abNum = abNum;
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAbDate() {
		return abDate;
	}
	public void setAbDate(String abDate) {
		this.abDate = abDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	
	
}
