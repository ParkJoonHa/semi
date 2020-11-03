package com.boast;

import java.sql.SQLException;
import java.util.List;

public interface BoastDAO {
	public int insertboast(BoastDTO dto) throws SQLException;
	public int updateboast(BoastDTO dto) throws SQLException;
	public int deleteboast(int num, String userId) throws SQLException;
	
	public int dataCount();
	public int dataCount(String condition, String keyword);
	
	public List<BoastDTO> listBoast(int offset, int rows);
	public List<BoastDTO> listBoast(int offset, int rows, String condition, String keyword);
	
	public int updateHitCount(int num) throws SQLException;
	public BoastDTO readBoast(int num);
	public BoastDTO preReadBoast(int num, String condition, String keyword);
	public BoastDTO nextReadBoast(int num, String condition, String keyword);
}
