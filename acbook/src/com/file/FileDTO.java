package com.file;
/**다중파일처리 테이블
 * fileNum : 파일번호(기본키)
 * noticeNum : 공지게시글 번호(외래키)
 * saveFilename : 서버저장이름
 * originalFilename : 클라이언트저장 이름
 * @author SIST
 *
 */
public class FileDTO {
	private int fileNum;
	private int noticeNum;
	private String saveFilename;
	private String originalFilename;
	
	public int getFileNum() {
		return fileNum;
	}
	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}
	public int getNoticeNum() {
		return noticeNum;
	}
	public void setNoticeNum(int noticeNum) {
		this.noticeNum = noticeNum;
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
	
	
}
