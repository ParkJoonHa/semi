package com.freereply;

import java.sql.SQLException;

public interface FreeReplyDAO {
	public int insertFreeReply(FreeReplyDTO dto) throws SQLException;
	public int updateFreeReply(FreeReplyDTO dto) throws SQLException;
	public int deleteFreeReply(FreeReplyDTO dto) throws SQLException;
}
