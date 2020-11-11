package com.boast;

import java.sql.SQLException;
import java.util.List;

import com.boastreply.BoastReplyDTO;

public interface BoastDAO {
//	게시물 추가
	public int insertboast(BoastDTO dto) throws SQLException;

//	게시물 수정
	public int updateboast(BoastDTO dto) throws SQLException;

//	게시물 삭제
	public int deleteboast(int num) throws SQLException;

//	총 게시물 수
	public int dataCount();

//	총 게시물 수 (검색했을 때)
	public int dataCount(String condition, String keyword);

//	전체 리스트
	public List<BoastDTO> list(int offset, int rows);

//	검색 리스트
	public List<BoastDTO> list(int offset, int rows, String condition, String keyword);

//	조회수 증가
	public int updateHitCount(int num) throws SQLException;

//	게시물 보기
	public BoastDTO readBoast(int num);

//	이전 게시물
	public BoastDTO preReadBoast(int num, String condition, String keyword);

//	다음 게시물
	public BoastDTO nextReadBoast(int num, String condition, String keyword);

//	덧글 추가
	public int insertReply(BoastReplyDTO dto) throws SQLException;

//	덧글 삭제
	public int deleteReply(int replyNum) throws SQLException;

//	덧글 전체 리스트
	public List<BoastReplyDTO> replyList(int boastNum) throws SQLException;
	
//	이미지
	public List<BoastImgDTO> readImg(int boastNum) throws SQLException;
	
//	좋아요
	public int like(int boastNum, String userId) throws SQLException;
}
