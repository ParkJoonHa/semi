package com.answer;

import java.sql.SQLException;

public interface AnswerDAO {
	public int insertAnswer(AnswerDTO dto) throws SQLException;
	public int updateAnswer(AnswerDTO dto) throws SQLException;
	public int deleteAnswer(AnswerDTO dto) throws SQLException;
	
}
