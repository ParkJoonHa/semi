package com.notice;

import java.sql.SQLException;
import java.util.List;

public interface NoticeDAO {
	public int insertNotice(NoticeDTO dto) throws SQLException;
	public int updateNotice(NoticeDTO dto) throws SQLException;
	public int deleteNotice(int num, String userId) throws SQLException;
	
	public int dataCount();
	public int dataCount(String condition, String keyword);
	
	public List<NoticeDTO> listNotice(int offset, int rows);
	public List<NoticeDTO> listNotice(int offset, int rows, String condition, String keyword);
	
	public int updateHitCount(int num) throws SQLException;
	public NoticeDTO readNotice(int num);
	public NoticeDTO preReadNotice(int num, String condition, String keyword);
	public NoticeDTO nextReadNotice(int num, String condition, String keyword);
}


