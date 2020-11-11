package com.free;

import java.sql.SQLException;
import java.util.List;

import com.freereply.FreeReplyDTO;

public interface FreeDAO {
//	게시물 추가
	public int insertFree(FreeDTO dto) throws SQLException;

//	게시물 수정
	public int updateFree(FreeDTO dto) throws SQLException;

//	게시물 삭제
	public int deleteFree(int freeNum) throws SQLException;

//	총 게시물 수
	public int dataCount();

//	총 게시물 수 (검색시)
	public int dataCount(String condition, String keyword);

//	전체 리스트
	public List<FreeDTO> listFree(int offset, int rows);

//	전체 리스트 (검색시)
	public List<FreeDTO> listFree(int offset, int rows, String condition, String keyword);

//	조회수 증가
	public int updateHitCount(int num) throws SQLException;

//	게시물 확인 (Article)
	public FreeDTO readFree(int num);

//	이전 게시물
	public FreeDTO preReadFree(int num, String condition, String keyword);

//	다음게시물
	public FreeDTO nextReadFree(int num, String condition, String keyword);

//	덧글 추가
	public int insertReply(FreeReplyDTO dto) throws SQLException;

//	덧글 삭제
	public int deleteReply(int replyNum) throws SQLException;

//	덧글 전체 리스트
	public List<FreeReplyDTO> replyList(int freeNum) throws SQLException;
}
