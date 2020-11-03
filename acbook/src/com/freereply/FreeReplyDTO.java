package com.freereply;
/** 자유게시판 댓글 DTO
 * replyNum : 자유게시판 댓글번호(기본키)
 * freeNum : 자유게시판 글 번호 (외래키)
 * userId : 유저아이디 (외래키)
 * userName : 사용자 이름
 * content : 내용
 * created : 작성일자 
 * @author SIST
 *
 */
public class FreeReplyDTO {
	private int replyNum;
	private int freeNum;
	private String userId;
	private String userName;
	private String content;
	private String created;
	
	public int getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}
	public int getFreeNum() {
		return freeNum;
	}
	public void setFreeNum(int freeNum) {
		this.freeNum = freeNum;
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
	
	
}
