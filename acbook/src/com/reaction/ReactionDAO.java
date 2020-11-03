package com.reaction;

import java.sql.SQLException;

public interface ReactionDAO {
	public int insertReaction(ReactionDTO dto) throws SQLException;
	public int updateReaction(ReactionDTO dto) throws SQLException;
	public int deleteReaction(ReactionDTO dto) throws SQLException;
}
