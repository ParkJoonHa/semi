package com.answer;
/** QNA 답변 DTO
 * qnaAnswerNum : 답변 글 번호 (기본키)
 * userId : 유저아이디 (외래키)
 * userName : 사용자 이름
 * content : 내용
 * created : 작성일자
 * @author SIST
 *
 */
public class AnswerDTO {
	private int qnaAnswerNum;
	private String userId;
	private String userName;
	private String content;
	private String created;
	public int getQnaAnswerNum() {
		return qnaAnswerNum;
	}
	public void setQnaAnswerNum(int qnaAnswerNum) {
		this.qnaAnswerNum = qnaAnswerNum;
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
