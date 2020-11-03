package com.free;

import java.sql.SQLException;
import java.util.List;

public interface FreeDAO {
	public int insertFree(FreeDTO dto) throws SQLException;
	public int updateFree(FreeDTO dto) throws SQLException;
	public int deleteFree(int num, String userId) throws SQLException;
	
	public int dataCount();
	public int dataCount(String condition, String keyword);
	
	public List<FreeDTO> listFree(int offset, int rows);
	public List<FreeDTO> listFree(int offset, int rows, String condition, String keyword);
	
	public int updateHitCount(int num) throws SQLException;
	public FreeDTO readFree(int num);
	public FreeDTO preReadFree(int num, String condition, String keyword);
	public FreeDTO nextReadFree(int num, String condition, String keyword);
	
}
