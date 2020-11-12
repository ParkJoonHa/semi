package com.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class QnaDAOImpl implements QnaDAO {
	private Connection conn = DBConn.getConnection();
	@Override
	public int insertQna(QnaDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		
		try {
				sb.append("INSERT INTO qna (qnaNum, userId, q_subject, q_content, q_created) ");
				sb.append(" VALUES (qna_seq.NEXTVAL,?,?,?,SYSDATE) ");
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, dto.getUserId());
				pstmt.setString(2, dto.getQ_subject());
				pstmt.setString(3, dto.getQ_content());
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
	public int insertAnswer(QnaDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" INSERT INTO answer (answerNum, userId, a_subject, a_content, a_created, qnaNum) ");
			sb.append(" VALUES (answer_seq.NEXTVAL, ?, ?, ?,SYSDATE, ?) ");
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getA_subject());
			pstmt.setString(3, dto.getA_content());
			pstmt.setInt(4, dto.getQnaNum());
			result = pstmt.executeUpdate();
			
			sb.setLength(0);
			pstmt.close();
			
			sb.append("UPDATE qna SET status = 1 WHERE qnaNum = ?");
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, dto.getQnaNum());
			result += pstmt.executeUpdate();
			
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
	public int updateQna(QnaDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("UPDATE qna SET q_subject=?, q_content=? WHERE qnaNum = ? AND userId = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getQ_subject());
			pstmt.setString(2, dto.getQ_content());
			pstmt.setInt(3, dto.getQnaNum());
			pstmt.setString(4, dto.getUserId());
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
	public int deleteQna(int qnaNum) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();

		try {
			    deleteAnswer(qnaNum);

				sb.append("DELETE FROM qna WHERE qnaNum = ?");
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, qnaNum);
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
	public int deleteAnswer(int qnaNum) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("DELETE FROM answer WHERE qnaNum = ?");
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, qnaNum);
			result = pstmt.executeUpdate();
			
			sb.setLength(0);
			pstmt.close();
			
			sb.append("UPDATE qna SET status = 0 WHERE qnaNum = ?");
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, qnaNum);
			result += pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null ) {
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
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT NVL(COUNT(*), 0) cnt FROM qna ");
			pstmt = conn.prepareStatement(sb.toString());
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
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT NVL(COUNT(*), 0) FROM qna q ");
			sb.append(" JOIN member1 m1 ON q.userId = m1.userId ");
			if(condition.equals("q_created")) {
				keyword = keyword.replaceAll("(\\-|\\.|\\/)", "");
				sb.append(" WHERE TO_CHAR(q_created, 'YYYYMMDD') = ? ");
			} else if(condition.equals("all")) {
				sb.append(" WHERE INSTR(q_subject, ?) >= 1 OR INSTR(q_content, ?) >= 1");
			} else {
				sb.append(" WHERE INSTR("+condition+", ?) >= 1 ");
			}
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, keyword);
			if(condition.equals("all")) {
				pstmt.setString(2, keyword);
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
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
	public List<QnaDTO> listQna(int offset, int rows) {
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT q.qnaNum, q.userId, userName, q_subject, TO_CHAR(q_created, 'YYYY-MM-DD') q_created, q.status, ");
			sb.append(" answerNum, a.userId, a_subject, TO_CHAR(a_created, 'YYYY-MM-DD') a_created ");
			sb.append(" FROM qna q ");
			sb.append(" LEFT OUTER JOIN answer a ON q.qnaNum = a.qnaNum");
			sb.append(" JOIN member1 m1 ON q.userId = m1.userId ");
			sb.append(" ORDER BY qnaNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				QnaDTO dto = new QnaDTO();
				dto.setQnaNum(rs.getInt("qnaNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setQ_subject(rs.getString("q_subject"));
				dto.setQ_created(rs.getString("q_created"));
				dto.setA_subject(rs.getString("a_subject"));
				dto.setA_created(rs.getString("a_created"));
				dto.setStatus(rs.getInt("status"));
				list.add(dto);
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
		return list;
	}

	@Override
	public List<QnaDTO> listQna(int offset, int rows, String condition, String keyword) {
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT q.qnaNum, q.userId, userName, q_subject, TO_CHAR(q_created, 'YYYY-MM-DD') q_created, q.status, ");
			sb.append(" answerNum, a.userId, a_subject, TO_CHAR(a_created, 'YYYY-MM-DD') a_created ");
			sb.append(" FROM qna q ");
			sb.append(" LEFT OUTER JOIN answer a ON q.qnaNum = a.qnaNum");
			sb.append(" JOIN member1 m1 ON q.userId = m1.userId ");
			if(condition.equals("q_created")) {
				keyword = keyword.replaceAll("(\\/|\\.|\\-)", "");
				sb.append(" WHERE TO_CHAR(q_created, 'YYYYMMDD') = ? ");
			} else if (condition.equals("all")) {
				sb.append(" WHERE INSTR(q_subject, ?) >= 1 OR INSTR(q_content, ?) >=1 ");
			} else {
				sb.append(" WHERE INSTR("+condition+", ? ) >= 1 ");
			}
			sb.append(" ORDER BY qnaNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
			pstmt = conn.prepareStatement(sb.toString());
			if(condition.equals("all")) {
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
			while(rs.next()) {
				QnaDTO dto = new QnaDTO();
				dto.setQnaNum(rs.getInt("qnaNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setQ_subject(rs.getString("q_subject"));
				dto.setQ_created(rs.getString("q_created"));
				dto.setStatus(rs.getInt("status"));
				list.add(dto);
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
		
		return list;
	}
	

	@Override
	public QnaDTO readQna(int qnaNum) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
				sb.append("SELECT qnaNum, q.userId, userName, q_subject, q_content, q_created, q.status ");
				sb.append(" FROM qna q JOIN member1 m1 ON q.userId = m1.userId ");
				sb.append(" WHERE qnaNum = ? ");
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, qnaNum);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					dto = new QnaDTO();
					dto.setQnaNum(rs.getInt("qnaNum"));
					dto.setUserId(rs.getString("userId"));
					dto.setUserName(rs.getString("userName"));
					dto.setQ_subject(rs.getString("q_subject"));
					dto.setQ_content(rs.getString("q_content"));
					dto.setQ_created(rs.getString("q_created"));
					dto.setStatus(rs.getInt("status"));
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
		return dto;
	}

	@Override
	public QnaDTO readAnswer(int qnaNum) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT qnaNum, answerNum, a.userId, userName, a_subject, a_content, a_created ");
			sb.append(" FROM answer a JOIN member1 m1 ON a.userId = m1.userId ");
			sb.append(" WHERE qnaNum = ? ");
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, qnaNum);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new QnaDTO();
				dto.setQnaNum(rs.getInt("qnaNum"));
				dto.setAnswerNum(rs.getInt("answerNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setA_subject(rs.getString("a_subject"));
				dto.setA_content(rs.getString("a_content"));
				dto.setA_created(rs.getString("a_created"));

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
		
		return dto;
	}

	

}
