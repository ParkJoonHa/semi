package com.boastreply;

import java.sql.SQLException;

public interface BoastReplyDAO {
	public int insertPhoto(BoastReplyDTO dto) throws SQLException;
	public int updatePhoto(BoastReplyDTO dto) throws SQLException;
	public int deletePhoto(BoastReplyDTO dto) throws SQLException;
	
}
