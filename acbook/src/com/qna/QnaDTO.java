package com.qna;
/**QNA 답변형 게시판
 * qnaNum : qna게시글 번호(기본키)
 * userId : 아이디(외래키)
 * userName : 이름
 * subject : 제목
 * content : 내용
 * created : 작성일자
 * status : 답변상태 ex)status가 값 1을 가지고있으면 그 질문에는 답변을 달지 못하도록 하는 용도
 * 
 * @author SIST
 *
 */
public class QnaDTO {
	private int qnaNum;
	private String userId;
	private String userName;
	private String subject;
	private String content;
	private String created;
	private int status;
	
	public int getQnaNum() {
		return qnaNum;
	}
	public void setQnaNum(int qnaNum) {
		this.qnaNum = qnaNum;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
