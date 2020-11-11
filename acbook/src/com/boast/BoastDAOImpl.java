package com.boast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.boastreply.BoastReplyDTO;
import com.util.DBConn;

public class BoastDAOImpl implements BoastDAO {
	private Connection conn = DBConn.getConnection();

	@Override
	public int insertboast(BoastDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		int boastNum = 0;

		try {
			sql = "SELECT boast_seq.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				boastNum = rs.getInt(1);
			}

			pstmt.close();
			rs.close();

			sql = "INSERT INTO boast(boastNum, userId, subject, content, hitCount, created) "
					+ " VALUES (?, ?, ?, ?, 0, SYSDATE)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boastNum);
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContent());

			result = pstmt.executeUpdate();

			if (dto.getSaveFiles() != null) {
				for (int i = 0; i < dto.getSaveFiles().length; i++) {
					dto.setSaveFilename(dto.getSaveFiles()[i]);
					dto.setOriginalFilename(dto.getOriginalFiles()[i]);

					insertFile(dto, boastNum);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}

		return result;
	}

	public int insertFile(BoastDTO dto, int boastNum) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO boastFile " + "  (fileNum, boastNum, saveFilename, originalFilename) "
					+ "   VALUES (boastFile_seq.NEXTVAL, ?, ?, ? ) ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boastNum);
			pstmt.setString(2, dto.getSaveFilename());
			pstmt.setString(3, dto.getOriginalFilename());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}

	@Override
	public int updateboast(BoastDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";

		try {
//			이미지 초기화
			deleteImg(dto.getBoastNum());
			
			sql = "UPDATE boast SET subject = ?, content = ? WHERE boastNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getBoastNum());

			result = pstmt.executeUpdate();
			
			if (dto.getSaveFiles() != null) {
				for (int i = 0; i < dto.getSaveFiles().length; i++) {
					dto.setSaveFilename(dto.getSaveFiles()[i]);
					dto.setOriginalFilename(dto.getOriginalFiles()[i]);

					insertFile(dto, dto.getBoastNum());
				}
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
		}

		return result;
	}

	@Override
	public int deleteboast(int num) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";

		try {
//			이미지 제거
			deleteImg(num);
			
			sql = "DELETE FROM boast WHERE boastNum = ?";
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
	
	public int deleteImg(int num) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";

		try {
//			이미지 제거
			sql = "DELETE FROM boastFile WHERE boastNum = ?";
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
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT COUNT(*) cnt FROM boast";
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
			sql = "SELECT COUNT(*) FROM boast b  " + " JOIN member1 m ON b.userId = m.userId ";

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
	public List<BoastDTO> list(int offset, int rows) {
		List<BoastDTO> list = new ArrayList<BoastDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT boastNum, userName, subject, hitCount, TO_CHAR(created, 'YYYY-MM-DD') formatDate, (SELECT COUNT(*) FROM boastreply WHERE boastNum = b.boastNum) replycnt ");
			sb.append("FROM boast b ");
			sb.append("JOIN member1 m ON b.userId = m.userId ");
			sb.append("ORDER BY boastNum DESC ");
			sb.append("OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoastDTO dto = new BoastDTO();
				dto.setBoastNum(rs.getInt("boastNum"));
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
	public List<BoastDTO> list(int offset, int rows, String condition, String keyword) {
		List<BoastDTO> list = new ArrayList<BoastDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT boastNum, userName, subject, hitCount,TO_CHAR(created, 'YYYY-MM-DD') formatDate, (SELECT COUNT(*) FROM boastreply WHERE boastNum = b.boastNum) replycnt ");
			sb.append(" FROM boast b ");
			sb.append(" JOIN member1 m ON b.userId = m.userId ");

			if (condition.equals("created")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append("  WHERE TO_CHAR(created, 'YYYYMMDD') = ? ");
			} else if (condition.equals("all")) {
				sb.append("  WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ");
			} else {
				sb.append("  WHERE INSTR(" + condition + ", ?) >= 1 ");
			}

			sb.append(" ORDER BY boastNum DESC");
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
				BoastDTO dto = new BoastDTO();
				dto.setBoastNum(rs.getInt("boastNum"));
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
			sql = "UPDATE boast SET hitCount = (hitCount + 1) WHERE boastNum = ?";
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

//	BOASTNUM NOT NULL NUMBER         
//	USERID   NOT NULL VARCHAR2(50)   
//	SUBJECT  NOT NULL VARCHAR2(255)  
//	CONTENT  NOT NULL VARCHAR2(4000) 
//	CREATED           DATE           
//	HITCOUNT NOT NULL NUMBER(5)

	@Override
	public BoastDTO readBoast(int num) {
		BoastDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			sql = "SELECT boastNum, b.userId, userName, subject, content, created, hitCount, (SELECT COUNT(*) FROM boastlike WHERE boastNum = ?) likeCount FROM boast b JOIN member1 m1 ON b.userId = m1.userId WHERE boastNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setInt(2, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new BoastDTO();

				dto.setBoastNum(rs.getInt("boastNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setCreated(rs.getString("created"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setLikeCount(rs.getInt("likeCount"));
			}

			updateHitCount(dto.getBoastNum());

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

	public List<BoastImgDTO> readImg(int boastNum) throws SQLException {
		List<BoastImgDTO> list = new ArrayList<BoastImgDTO>();
		BoastImgDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			sql = "SELECT fileNum, boastNum, saveFileName, originalFileName FROM boastFile WHERE boastNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boastNum);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dto = new BoastImgDTO();
				dto.setFileNum(rs.getInt("fileNum"));
				dto.setBoastNum(rs.getInt("boastNum"));
				dto.setSaveFileName(rs.getString("saveFileName"));
				dto.setOriginalFileName(rs.getString("originalFileName"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public BoastDTO preReadBoast(int num, String condition, String keyword) {
		BoastDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			if (keyword.length() == 0) {
				sql = "SELECT boastNum, b.userId, userName, subject, content FROM boast b JOIN member1 m1 ON b.userId = m1.userId WHERE boastNum > ? AND ROWNUM = 1 ORDER BY boastNum ASC";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
			} else {
				sql = "SELECT boastNum, b.userId, userName, subject, content FROM boast b JOIN member1 m1 ON b.userId = m1.userId WHERE boastNum > ? AND INSTR(?, ?) >= 0 AND ROWNUM = 1 ORDER BY boastNum ASC";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setString(2, condition);
				pstmt.setString(3, keyword);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new BoastDTO();
				dto.setBoastNum(rs.getInt("boastNum"));
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
	public BoastDTO nextReadBoast(int num, String condition, String keyword) {
		BoastDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			if (keyword.length() == 0) {
				sql = "SELECT boastNum, b.userId, userName, subject, content FROM boast b JOIN member1 m1 ON b.userId = m1.userId WHERE boastNum < ? AND ROWNUM = 1 ORDER BY boastNum DESC";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
			} else {
				sql = "SELECT boastNum, b.userId, userName, subject, content FROM boast b JOIN member1 m1 ON b.userId = m1.userId WHERE boastNum < ? AND INSTR(?, ?) >= 0 AND ROWNUM = 1 ORDER BY boastNum DESC";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setString(2, condition);
				pstmt.setString(3, keyword);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new BoastDTO();
				dto.setBoastNum(rs.getInt("boastNum"));
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
	public int insertReply(BoastReplyDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";

		try {
			sql = "INSERT INTO boastreply(replyNum, boastNum, userId, content, created) VALUES(boastreply_seq.NEXTVAL, ?, ?, ?, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getBoastNum());
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
			sql = "DELETE boastreply WHERE replyNum = ?";
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
	public List<BoastReplyDTO> replyList(int boastNum) throws SQLException {
		List<BoastReplyDTO> replyList = new ArrayList<BoastReplyDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			sql = "SELECT boastNum, content, created, replyNum, b.userId, userName " + "FROM boastreply b "
					+ "JOIN member1 m1 ON b.userId = m1.userId " + "WHERE b.boastNum = ?" + "ORDER BY replyNum DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boastNum);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoastReplyDTO dto = new BoastReplyDTO();
				dto.setBoastNum(rs.getInt("boastNum"));
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
	
	public int like(int boastNum, String userId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = "";

		try {
			sql = "SELECT COUNT(*) FROM boastlike WHERE boastNum = ? AND userId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boastNum);
			pstmt.setString(2, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
			pstmt.close();
			rs.close();
			
			if(result != 0) {
				sql = "DELETE FROM boastlike WHERE boastNum = ? AND userId = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, boastNum);
				pstmt.setString(2, userId);
				pstmt.executeQuery();
			} else {
				sql = "INSERT INTO boastlike(boastNum, userId) VALUES(?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, boastNum);
				pstmt.setString(2, userId);
				pstmt.executeQuery();
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
		}

		return result;
	}

}
