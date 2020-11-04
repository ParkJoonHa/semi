package com.answer;

import java.sql.SQLException;

public interface AnswerDAO {
	// 답변 등록
	public int insertAnswer(AnswerDTO dto) throws SQLException;
	// 답변 수정
	public int updateAnswer(AnswerDTO dto) throws SQLException;
	
	// 답변 삭제
	public int deleteAnswer(int num, String userId) throws SQLException;
	
	// DB읽어오기
	public AnswerDTO readAnswer(int num);
	
}
