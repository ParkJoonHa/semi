package com.qna;
/**
 *  qnaNum : 질문 글번호
	answerNum : 답변 글번호
	listNum : 리스트 번호
	status : 상태
	userId : 아이디
	userName : 이름
	q_subject : 질문 제목
	q_content : 질문 내용
	q_created : 질문 등록일
	a_subject : 답변 제목
	a_content : 답변 내용
	a_created : 답변 등록일
 * @author SIST
 *
 */
public class QnaDTO {
	private int qnaNum;
	private int answerNum;
	private int listNum;
	private int status;
	private String userId;
	private String userName;
	private String q_subject;
	private String q_content;
	private String q_created;
	private String a_subject;
	private String a_content;
	private String a_created;
	
	public int getQnaNum() {
		return qnaNum;
	}
	public void setQnaNum(int qnaNum) {
		this.qnaNum = qnaNum;
	}
	public int getAnswerNum() {
		return answerNum;
	}
	public void setAnswerNum(int answerNum) {
		this.answerNum = answerNum;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public String getQ_subject() {
		return q_subject;
	}
	public void setQ_subject(String q_subject) {
		this.q_subject = q_subject;
	}
	public String getQ_content() {
		return q_content;
	}
	public void setQ_content(String q_content) {
		this.q_content = q_content;
	}
	public String getQ_created() {
		return q_created;
	}
	public void setQ_created(String q_created) {
		this.q_created = q_created;
	}
	public String getA_subject() {
		return a_subject;
	}
	public void setA_subject(String a_subject) {
		this.a_subject = a_subject;
	}
	public String getA_content() {
		return a_content;
	}
	public void setA_content(String a_content) {
		this.a_content = a_content;
	}
	public String getA_created() {
		return a_created;
	}
	public void setA_created(String a_created) {
		this.a_created = a_created;
	}
	
}
