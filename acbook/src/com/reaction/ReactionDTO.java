package com.reaction;
/** 자랑게시판 좋아요/싫어요
 * likeNum : 좋아요 번호(기본키)
 * boastNum : 자랑게시판 글번호(외래키)
 * userId : 아이디(기본키, 외래키)
 * userName : 이름
 * 
 * @author SIST
 *
 */
public class ReactionDTO {
	private int likeNum;
	private int boastNum;
	private String userId;
	private String userName;
	
	public int getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String usserName) {
		this.userName = usserName;
	}
	
	
}
