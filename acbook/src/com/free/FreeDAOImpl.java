package com.free;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.freereply.FreeReplyDTO;
import com.util.DBConn;

public class FreeDAOImpl implements FreeDAO {
	private Connection conn = DBConn.getConnection();

	@Override
	public int insertFree(FreeDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";

		try {
			sql = "INSERT INTO free(freeNum, userId, subject, content, hitCount, created) VALUES (free_seq.NEXTVAL, ?, ?, ?, 0, SYSDATE)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}

	@Override
	public int updateFree(FreeDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";

		try {

			sql = "UPDATE free SET subject = ?, content = ? WHERE freeNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getFreeNum());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return result;
	}

	@Override
	public int deleteFree(int freeNum) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";

		try {
			
			sql = "DELETE FROM free WHERE freeNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, freeNum);

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return result;
	}

	@Override
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT COUNT(*) cnt FROM free";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}

		return result;
	}

	@Override
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT COUNT(*) FROM free f  " + " JOIN member1 m1 ON f.userId = m1.userId ";

			if (condition.equals("created")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += " WHERE TO_CHAR(created, 'YYYYMMDD') = ?";
			} else if (condition.equals("all")) {
				sql += " WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ";
			} else { // subject, content, userName
				sql += " WHERE INSTR(" + condition + ", ?) >= 1";
			}

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			if (condition.equals("all")) {
				pstmt.setString(2, keyword);
			}

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}

		return result;
	}

	@Override
	public List<FreeDTO> listFree(int offset, int rows) {
		List<FreeDTO> list = new ArrayList<FreeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(
					"SELECT freeNum, userName, subject, hitCount, TO_CHAR(created, 'YYYY-MM-DD') formatDate, (SELECT COUNT(*) FROM freereply WHERE freeNum = f.freeNum) replycnt ");
			sb.append("FROM free f ");
			sb.append("JOIN member1 m1 ON f.userId = m1.userId ");
			sb.append("ORDER BY freeNum DESC ");
			sb.append("OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				FreeDTO dto = new FreeDTO();
				dto.setFreeNum(rs.getInt("freeNum"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("formatDate"));
				dto.setReplyCount(rs.getInt("replycnt"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}
		return list;
	}

	@Override
	public List<FreeDTO> listFree(int offset, int rows, String condition, String keyword) {
		List<FreeDTO> list = new ArrayList<FreeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(
					"SELECT freeNum, userName, subject, hitCount,TO_CHAR(created, 'YYYY-MM-DD') formatDate, (SELECT COUNT(*) FROM freereply WHERE freeNum = f.freeNum) replycnt ");
			sb.append(" FROM free f ");
			sb.append(" JOIN member1 m1 ON f.userId = m1.userId ");

			if (condition.equals("created")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append("  WHERE TO_CHAR(created, 'YYYYMMDD') = ? ");
			} else if (condition.equals("all")) {
				sb.append("  WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ");
			} else {
				sb.append("  WHERE INSTR(" + condition + ", ?) >= 1 ");
			}

			sb.append(" ORDER BY freeNum DESC");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");

			pstmt = conn.prepareStatement(sb.toString());
			if (condition.equals("all")) {
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, rows);
			} else {
				pstmt.setString(1, keyword);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, rows);
			}

			rs = pstmt.executeQuery();
			while (rs.next()) {
				FreeDTO dto = new FreeDTO();
				dto.setFreeNum(rs.getInt("freeNum"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("formatDate"));
				dto.setReplyCount(rs.getInt("replycnt"));

				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}
		return list;
	}

	@Override
	public int updateHitCount(int num) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";

		try {
			sql = "UPDATE free SET hitCount = (hitCount + 1) WHERE freeNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return result;
	}

	@Override
	public FreeDTO readFree(int num) {
		FreeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			sql = "SELECT freeNum, f.userId, userName, subject, content, created, hitCount FROM free f JOIN member1 m1 ON f.userId = m1.userId WHERE freeNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new FreeDTO();

				dto.setFreeNum(rs.getInt("freeNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setCreated(rs.getString("created"));
				dto.setHitCount(rs.getInt("hitCount"));
			}

			updateHitCount(dto.getFreeNum());

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}

		return dto;
	}

	@Override
	public FreeDTO preReadFree(int num, String condition, String keyword) {
		FreeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			if (keyword.length() == 0) {
				sql = "SELECT freeNum, f.userId, userName, subject, content FROM free f JOIN member1 m1 ON f.userId = m1.userId WHERE freeNum > ? AND ROWNUM = 1 ORDER BY freeNum ASC";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
			} else {
				sql = "SELECT freeNum, f.userId, userName, subject, content FROM free f JOIN member1 m1 ON f.userId = m1.userId WHERE freeNum > ? AND INSTR(?, ?) >= 0 AND ROWNUM = 1 ORDER BY freeNum ASC";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setString(2, condition);
				pstmt.setString(3, keyword);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new FreeDTO();
				dto.setFreeNum(rs.getInt("freeNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}

		return dto;
	}

	@Override
	public FreeDTO nextReadFree(int num, String condition, String keyword) {
		FreeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			if (keyword.length() == 0) {
				sql = "SELECT freeNum, f.userId, userName, subject, content FROM free f JOIN member1 m1 ON f.userId = m1.userId WHERE freeNum < ? AND ROWNUM = 1 ORDER BY freeNum DESC";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
			} else {
				sql = "SELECT freeNum, f.userId, userName, subject, content FROM free f JOIN member1 m1 ON f.userId = m1.userId WHERE freeNum < ? AND INSTR(?, ?) >= 0 AND ROWNUM = 1 ORDER BY freeNum DESC";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setString(2, condition);
				pstmt.setString(3, keyword);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new FreeDTO();
				dto.setFreeNum(rs.getInt("freeNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}

		return dto;
	}

	@Override
	public int insertReply(FreeReplyDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";

		try {
			sql = "INSERT INTO freereply(replyNum, freeNum, userId, content, created) VALUES(freereply_seq.NEXTVAL, ?, ?, ?, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getFreeNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getContent());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return result;
	}

	@Override
	public int deleteReply(int replyNum) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";

		try {
			sql = "DELETE freereply WHERE replyNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyNum);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return result;
	}

	@Override
	public List<FreeReplyDTO> replyList(int freeNum) throws SQLException {
		List<FreeReplyDTO> replyList = new ArrayList<FreeReplyDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			sql = "SELECT freeNum, content, created, replyNum, f.userId, userName " + "FROM freereply f "
					+ "JOIN member1 m1 ON f.userId = m1.userId " + "WHERE f.freeNum = ?" + "ORDER BY replyNum DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, freeNum);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				FreeReplyDTO dto = new FreeReplyDTO();
				dto.setFreeNum(rs.getInt("freeNum"));
				dto.setContent(rs.getString("content"));
				dto.setCreated(rs.getString("created"));
				dto.setReplyNum(rs.getInt("replyNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));

				replyList.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}

		return replyList;
	}

}
