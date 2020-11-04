package com.qna;

import java.sql.SQLException;
import java.util.List;

public interface QnaDAO {
	// 질문등록
	public int insertQna(QnaDTO dto) throws SQLException;
	// 질문수정
	public int updateQna(QnaDTO dto) throws SQLException;
	// 질문삭제
	public int deleteQna(int num, String userId) throws SQLException;
	
	//전체 데이터
	public int dataCount();
	
	// 조건검색
	public int dataCount(String condition, String keyword);
	
	public List<QnaDTO> listQna(int offset, int rows);
	public List<QnaDTO> listQna(int offset, int rows, String condition, String keyword);
	
	// 수정
	public QnaDTO readQna(int num);
}
