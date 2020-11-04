package com.repeat;

import java.sql.SQLException;
import java.util.List;


public interface RepeatQnaDAO {
	// 글 등록
	public int insertRepeat(RepeatQnaDTO dto) throws SQLException;
	
	// 글 수정
	public int updateRepeat(RepeatQnaDTO dto) throws SQLException;
	
	// 글 삭제
	public int deleteRepeat(int num) throws SQLException;
	
	// 등록 된 글 읽어오기
	public RepeatQnaDTO readRepeat(int num);
	
	//전체 데이터
	public int dataCount();
	// 조건검색
	public int dataCount(String condition, String keyword);
	
	
	public List<RepeatQnaDTO> listBoard(int offset, int rows);
	
	public List<RepeatQnaDTO> listBoard(int offset, int rows, String condition, String keyword);
	
}
