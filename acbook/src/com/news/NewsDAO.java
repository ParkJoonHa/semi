package com.news;

import java.sql.SQLException;
import java.util.List;

public interface NewsDAO {
	public int insertNews(NewsDTO dto) throws SQLException;
	public int updateNews(NewsDTO dto) throws SQLException;
	public int deleteNews(int num, String userId) throws SQLException;
	
	public int dataCount();
	public int dataCount(String condition, String keyword);
	
	public List<NewsDTO> listNews(int offset, int rows);
	public List<NewsDTO> listNews(int offset, int rows, String condition, String keyword);
	
	public int updateHitCount(int newsNum) throws SQLException;
	public NewsDTO readNews(int newsNum);
	public NewsDTO preReadNews(int newsNum, String condition, String keyword);
	public NewsDTO nextReadNews(int newsNum, String condition, String keyword);
}
