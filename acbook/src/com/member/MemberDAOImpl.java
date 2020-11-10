package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class MemberDAOImpl implements MemberDAO {
	private Connection conn = DBConn.getConnection();

	@Override
	public int insertMember(MemberDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn.setAutoCommit(false);

			// member1
			sql = "INSERT INTO member1(userId, userName, userPwd, status, c_date, m_date) "
					+ "VALUES (?,?,?,0,SYSDATE,SYSDATE)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getUserName());
			pstmt.setString(3, dto.getUserPwd());

			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;

			// member2
			sql = " INSERT INTO member2(userId, birth, email, tel, zip_code, addr1, addr2) "
					+ "VALUES (?,TO_DATE(?,'YYYYMMDD'),?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getBirth());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getTel());
			pstmt.setString(5, dto.getZip_code());
			pstmt.setString(6, dto.getAddr1());
			pstmt.setString(7, dto.getAddr2());

			result += pstmt.executeUpdate();

			conn.commit();

		} catch (SQLIntegrityConstraintViolationException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			e.printStackTrace();
			throw e;
		} catch (SQLDataException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
		return result;
	}

	@Override
	public int updateMember(MemberDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			conn.setAutoCommit(false);
			
			//member1
			sql = "UPDATE member1 SET userName=?, userPwd=?, m_date=SYSDATE WHERE userId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserName());
			pstmt.setString(2, dto.getUserPwd());
			pstmt.setString(3, dto.getUserId());
			
			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
			
			//member2
			sql = "UPDATE member2 SET birth=?, email=?, tel=?, zip_code=?, addr1=?, addr2=? WHERE userId=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBirth());
			pstmt.setString(2, dto.getEmail());
			pstmt.setString(3, dto.getTel());
			pstmt.setString(4, dto.getZip_code());
			pstmt.setString(5, dto.getAddr1());
			pstmt.setString(6, dto.getAddr2());
			pstmt.setString(7, dto.getUserId());
			
			result += pstmt.executeUpdate();
			
			conn.commit();	
			
		} catch (SQLIntegrityConstraintViolationException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			e.printStackTrace();
			throw e;
		} catch (SQLDataException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
		return result;
	}

	@Override
	public int deleteMember(String userId, String userPwd) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM member1 WHERE userId=? AND userPwd=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}

	@Override
	public MemberDTO readMember(String userId) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT m1.userId, userName, userPwd, ");
			sb.append("  status, c_date, m_date, ");
			sb.append("  TO_CHAR(birth, 'YYYY-MM-DD') birth, ");
			sb.append("  email, tel, zip_code, addr1, addr2 ");
			sb.append("  FROM member1 m1 ");
			sb.append("  LEFT OUTER JOIN member2 m2 ");
			sb.append("  ON m1.userId=m2.userId ");
			sb.append("  WHERE m1.userId=? ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new MemberDTO();
				dto.setUserId(rs.getString("userId"));
				dto.setUserPwd(rs.getString("userPwd"));
				dto.setUserName(rs.getString("userName"));
				dto.setStatus(rs.getInt("status"));
				dto.setC_date(rs.getString("c_date"));
				dto.setM_date(rs.getString("m_date"));
				dto.setBirth(rs.getString("birth"));
				dto.setTel(rs.getString("tel"));
				if (dto.getTel() != null) {
					String[] ss = dto.getTel().split("-");
					if (ss.length == 3) {
						dto.setTel1(ss[0]);
						dto.setTel2(ss[1]);
						dto.setTel3(ss[2]);
					}
				}
				dto.setEmail(rs.getString("email"));
				if (dto.getEmail() != null) {
					String[] ss = dto.getEmail().split("@");
					if (ss.length == 2) {
						dto.setEmail1(ss[0]);
						dto.setEmail2(ss[1]);
					}
				}
				dto.setZip_code(rs.getString("zip_code"));
				dto.setAddr1(rs.getString("addr1"));
				dto.setAddr2(rs.getString("addr2"));
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
		} catch (SQLDataException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return dto;
	}

	@Override
	public List<MemberDTO> ListMember() throws SQLException {
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT m1.userId, userName, userPwd, ");
			sb.append("  status, c_date, m_date, ");
			sb.append("  TO_CHAR(birth, 'YYYY-MM-DD') birth, ");
			sb.append("  email, tel, zip_code, addr1, addr2 ");
			sb.append("  FROM member1 m1 ");
			sb.append("  LEFT OUTER JOIN member2 m2 ");
			sb.append("  ON m1.userId=m2.userId ");
			
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setUserId(rs.getString("userId"));
				dto.setUserPwd(rs.getString("userPwd"));
				dto.setUserName(rs.getString("userName"));
				dto.setStatus(rs.getInt("status"));
				dto.setC_date(rs.getString("c_date"));
				dto.setM_date(rs.getString("m_date"));
				dto.setBirth(rs.getString("birth"));
				dto.setTel(rs.getString("tel"));
				if (dto.getTel() != null) {
					String[] ss = dto.getTel().split("-");
					if (ss.length == 3) {
						dto.setTel1(ss[0]);
						dto.setTel2(ss[1]);
						dto.setTel3(ss[2]);
					}
				}
				dto.setEmail(rs.getString("email"));
				if (dto.getEmail() != null) {
					String[] ss = dto.getEmail().split("@");
					if (ss.length == 2) {
						dto.setEmail1(ss[0]);
						dto.setEmail2(ss[1]);
					}
				}
				dto.setZip_code(rs.getString("zip_code"));
				dto.setAddr1(rs.getString("addr1"));
				dto.setAddr2(rs.getString("addr2"));
				
				list.add(dto);
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
		} catch (SQLDataException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}
}
