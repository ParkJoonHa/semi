package com.qna;

import java.sql.SQLException;
import java.util.List;

public interface QnaDAO {
	// 질문등록
	public int insertQna(QnaDTO dto) throws SQLException;
	// 답변등록
	public int insertAnswer(QnaDTO dto) throws SQLException;
	// 질문수정
	public int updateQna(QnaDTO dto) throws SQLException;
	// 질문삭제
	public int deleteQna(int qnaNum) throws SQLException;
	// 답변삭제
	public int deleteAnswer(int qnaNum) throws SQLException;
	
	
	//전체 데이터
	public int dataCount();
	
	// 조건검색
	public int dataCount(String condition, String keyword);
	
	public List<QnaDTO> listQna(int offset, int rows);
	public List<QnaDTO> listQna(int offset, int rows, String condition, String keyword);
	
	// 데이터 읽어오는 용도
	public QnaDTO readQna(int qnaNum);
	
	// 답변글 읽어옴
	public QnaDTO readAnswer(int qnaNum);
}
