package com.boast;

/**
 * 자랑 게시판 DTO boastNum : 자랑게시판 글 번호(기본키) userId : 아이디(외래키) userName : 이름 subject
 * : 제목 content : 내용 created : 작성일
 * 
 * @author SIST
 *
 */
public class BoastDTO {
	private int boastNum;
	private String userId;
	private String userName;
	private String subject;
	private String content;
	private String created;
	private int hitCount;
	private int replyCount;
	private int likeCount;

	private int fileNum;
	private String saveFilename;
	private String originalFilename;

	// 추가
	private int[] fileNums;
	private String[] saveFiles;
	private String[] originalFiles;

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
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

	public int[] getFileNums() {
		return fileNums;
	}

	public void setFileNums(int[] fileNums) {
		this.fileNums = fileNums;
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

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
