package com.qna;

import java.sql.SQLException;
import java.util.List;

public interface QnaDAO {
	public int insertQna(QnaDTO dto) throws SQLException;
	public int updateQna(QnaDTO dto) throws SQLException;
	public int deleteQna(int num, String userId) throws SQLException;
	
	public int dataCount();
	public int dataCount(String condition, String keyword);
	
	public List<QnaDTO> listQna(int offset, int rows);
	public List<QnaDTO> listQna(int offset, int rows, String condition, String keyword);
	
	public int updateHitCount(int num) throws SQLException;
	public QnaDTO readQna(int num);
	public QnaDTO preReadQna(int num, String condition, String keyword);
	public QnaDTO nextReadQna(int num, String condition, String keyword);
}
