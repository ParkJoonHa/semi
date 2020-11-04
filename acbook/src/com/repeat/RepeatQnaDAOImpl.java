package com.repeat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class RepeatQnaDAOImpl implements RepeatQnaDAO{
	private Connection conn = DBConn.getConnection();
	@Override
	public int insertRepeat(RepeatQnaDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO repeat (repeatNum, userId, subject, content, created) VALUES (repeat_seq, ?, ?, ?, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			
			result = pstmt.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw e;
		} catch (SQLDataException e) {
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return result;
	}

	@Override
	public int updateRepeat(RepeatQnaDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE repeat SET subject=?, content=? WHERE repeatNum=? AND userId=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getRepeatNum());
			pstmt.setString(4, dto.getUserId());
			
			result = pstmt.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw e;
		} catch (SQLDataException e) {
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}

	@Override
	public int deleteRepeat(int num) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM repeat WHERE repeatNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}

	@Override
	public RepeatQnaDTO readRepeat(int num) {
		RepeatQnaDTO dto = new RepeatQnaDTO();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT repeatNum, rp.userId, userName, subject, content, created "
					+ " FROM repeat rp "
					+ " JOIN member1 m1 ON rp.userId = m1.userId "
					+ " WHERE num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto.setRepeatNum(rs.getInt("repeatNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setCreated(rs.getString("created"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return dto;
	}

	@Override
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(repeatNum), 0) cnt FROM repeat";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
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
			if(condition.equalsIgnoreCase("created")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
        		sql="SELECT NVL(COUNT(*), 0) FROM repeat rp JOIN member1 m1 ON rp.userId = m1.userId WHERE TO_CHAR(created, 'YYYYMMDD') = ?  ";
			} else {
				sql="SELECT NVL(COUNT(*), 0) FROM repeat rp JOIN member1 m1 ON rp.userId = m1.userId WHERE  INSTR(" + condition + ", ?) >= 1 ";
			}
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		} 
		return result;
	}

	@Override
	public List<RepeatQnaDTO> listBoard(int offset, int rows) {
		List<RepeatQnaDTO> list = new ArrayList<RepeatQnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT repeatNum, rp.userId, userName, subject, created "
					+ " FROM repeat rp JOIN member1 m1 ON rp.userId = m1.userId "
					+ " ORDER BY repeatNum DESC "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RepeatQnaDTO dto = new RepeatQnaDTO();
				
				dto.setRepeatNum(rs.getInt("repeatNum"));
				dto.setUserId(rs.getString("uesrId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}

	@Override
	public List<RepeatQnaDTO> listBoard(int offset, int rows, String condition, String keyword) {
		List<RepeatQnaDTO> list = new ArrayList<RepeatQnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT repeatNum, rp.userId, userName, subject, created ");
			sb.append(" FROM repeat rp JOIN member1 m1 ON rp.userId = m1.userId ");
			if(condition.equalsIgnoreCase("created")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" WHERE TO_CHAR(created, 'YYYYMMDD') = ?  ");
			} else {
				sb.append(" WHERE INSTR(" + condition + ", ?) >= 1  ");
			}
			
			sb.append(" ORDER BY repeatNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, keyword);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				RepeatQnaDTO dto = new RepeatQnaDTO();
				
				dto.setRepeatNum(rs.getInt("repeatNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}

}
