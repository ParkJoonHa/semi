package com.notice;

/**
 * 공지게시판 DTO noticeNum : 정보게시판 글 번호(기본키) userId : 아이디(외래키) userName : 이름 subject
 * : 제목 content : 내용 photoFileName : 사진 created : 등록일
 * 
 * @author SIST
 *
 */
public class NoticeDTO {
	private int noticeNum, listNum, nStatus;
	private String userName;
	private String userId;
	private String subject;
	private String content;
	private int hitCount;
	private String created;

	private int fileNum;
	private String saveFilename;
	private String originalFilename;

	// 추가
	private String[] saveFiles;
	private String[] originalFiles;

	private long filesize;
	private long gap;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getListNum() {
		return listNum;
	}

	public void setListNum(int listNum) {
		this.listNum = listNum;
	}

	public String[] getSaveFiles() {
		return saveFiles;
	}

	public void setSaveFiles(String[] saveFiles) {
		this.saveFiles = saveFiles;
	}

	public String[] getOriginalFiles() {
		return originalFiles;
	}

	public void setOriginalFiles(String[] originalFiles) {
		this.originalFiles = originalFiles;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	public long getGap() {
		return gap;
	}

	public void setGap(long gap) {
		this.gap = gap;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public int getFileNum() {
		return fileNum;
	}

	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}

	public String getSaveFilename() {
		return saveFilename;
	}

	public void setSaveFilename(String saveFilename) {
		this.saveFilename = saveFilename;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

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

	public int getnStatus() {
		return nStatus;
	}

	public void setnStatus(int nStatus) {
		this.nStatus = nStatus;
	}

}
