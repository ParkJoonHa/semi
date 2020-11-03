package com.notice;
/** 공지게시판 DTO
 * noticeNum : 정보게시판 글 번호(기본키)
 * userId : 아이디(외래키)
 * userName : 이름
 * subject : 제목
 * content : 내용
 * photoFileName : 사진
 * created : 등록일
 * @author SIST
 *
 */
public class NoticeDTO {
	private int noticeNum;
	private String userId;
	private String userName;
	private String subject;
	private String content;
	private String photoFileName;
	private String created;
	
	
	public int getNoticeNum() {
		return noticeNum;
	}
	public void setNoticeNum(int noticeNum) {
		this.noticeNum = noticeNum;
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
	public String getPhotoFileName() {
		return photoFileName;
	}
	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	
	
}
