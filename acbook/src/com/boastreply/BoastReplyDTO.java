package com.boastreply;
/** 자랑게시판 댓글
 * replyNum : 자랑게시판 댓글번호(기본키)
 * boastNum : 자랑게시판 글 번호(외래키)
 * userId : 유저아이디 (외래키)
 * userName : 사용자 이름
 * content : 내용
 * created : 작성일자
 * 
 * @author SIST
 *
 */
public class BoastReplyDTO {
	private int replyNum;
	private int boastNum;
	private String userId;
	private String content;
	private String created;
	
	public int getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}
	public int getBoastNum() {
		return boastNum;
	}
	public void setBoastNum(int boastNum) {
		this.boastNum = boastNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
