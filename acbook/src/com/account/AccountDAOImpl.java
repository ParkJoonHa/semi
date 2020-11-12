package com.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

// status:1->지출, 2->수압
public class AccountDAOImpl implements AccountDAO {
private Connection conn=DBConn.getConnection();

	@Override
	public int insertAccount(AccountDTO dto) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="INSERT INTO accountbook ( abNum, userId, kind1, kind2, content, amount, abDate, status) VALUES (ab_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getKind1());
			pstmt.setString(3, dto.getKind2());
			pstmt.setString(4, dto.getContent());
			pstmt.setInt(5, dto.getAmount());
			pstmt.setString(6, dto.getAbDate());
			pstmt.setInt(7, dto.getStatus());
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}


	@Override
	public int updateAccount(AccountDTO dto) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="UPDATE accountbook SET kind1=?, kind2=?, content=?, amount=?, abDate=? WHERE abNum=? ";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getKind1());
			pstmt.setString(2, dto.getKind2());
			pstmt.setString(3, dto.getContent());
			pstmt.setInt(4, dto.getAmount());
			pstmt.setString(5, dto.getAbDate());
			pstmt.setInt(6, dto.getAbNum());
			
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				pstmt.close();
			}
		}
		
		return result;
	}

	@Override
	public int deleteAccount(int abNum, String userId) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			if(userId.equals("admin")) {
				sql="DELETE FROM accountbook WHERE abNum=?";
			} else { 
				sql="DELETE FROM accountbook WHERE abNum=? AND userId=?";
			}
				
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, abNum);
			if(! userId.equals("admin")) {
				pstmt.setString(2, userId);
			}
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				pstmt.close();
			}
		}
		
		return result;
	}

	@Override
	public int dataCount(String userId) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		try {
			sql="SELECT COUNT(*) FROM accountbook WHERE userId=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery(); 
			
			if(rs.next()) {
				result=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
					
			}
		}
		return result;
	}

	@Override
	public int dataCount(String userId, String condition, String keyword) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT COUNT (*) FROM accountbook WHERE userId=? ";
			
			if(condition.equals("abDate")) {
				keyword=keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql+=" AND REPLACE(abDate, '-', '') = ?";
			}else if(condition.equals("all")) {
				sql+=" AND ( INSTR(content, ?) >= 1 OR INSTR(kind1, ?)>=1 OR INSTR(kind2, ?)>=1 ) ";
			} else { 
				sql+=" AND INSTR("+condition+",?) >=1";
			}
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, keyword);
			if(condition.equals("all")) {
				pstmt.setString(3, keyword);
				pstmt.setString(4, keyword);
				pstmt.setString(5, keyword);
			}
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null ) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
					
				}
			}
		}
		return result;
	}
	
	
	@Override
	public List<AccountDTO> listAccount(int offset, int rows, String userId) {
		List<AccountDTO> list=new ArrayList<AccountDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb= new StringBuilder();
		
		try {
			sb.append("SELECT abNum, userId, status, kind1, kind2, abDate, content, ");
			sb.append("   amount  ");
			sb.append(" FROM accountbook WHERE userId= ?");
			sb.append(" ORDER BY abDate ASC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);
			
			rs=pstmt.executeQuery();
			int result = 0;
			while(rs.next()) {
				AccountDTO dto= new AccountDTO();
				dto.setAbNum(rs.getInt("abNum"));
				dto.setStatus(rs.getInt("status"));
				dto.setKind1(rs.getString("kind1"));
				dto.setKind2(rs.getString("kind2"));
				dto.setAbDate(rs.getString("abDate"));
				dto.setContent(rs.getString("content"));
				dto.setAmount(rs.getInt("amount"));
				
				if(dto.getStatus()==1) {
					result -= dto.getAmount();
				} else {
					result += dto.getAmount();
				}
				
				dto.setResult(result);
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}

	@Override
	public List<AccountDTO> listAccount(String userId, String date) {
		//월별 달력 형태의 리스트
		List<AccountDTO> list=new ArrayList<AccountDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb= new StringBuilder();
		
		try {
			sb.append(" SELECT SUBSTR(abDate,9,2) day,   ");
			sb.append("    SUM(DECODE(status, 1, amount,0)) expense,  SUM(DECODE(status,2, amount,0)) income ");
			sb.append("  FROM accountbook ");
			sb.append("  WHERE userId=? AND SUBSTR(abDate,1,7) = ? ");
			sb.append("  GROUP BY SUBSTR(abDate,9,2) ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			pstmt.setString(2, date);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				AccountDTO dto= new AccountDTO();
				dto.setDay(rs.getString("day"));
				dto.setExpense(rs.getInt("expense"));
				dto.setIncome(rs.getInt("income"));
				list.add(dto);		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
			return list;
	}

	@Override
	public List<AccountDTO> listAccount(int offset, int rows, String condition, String keyword, String userId) {
	List<AccountDTO> list=new ArrayList<AccountDTO>();
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	StringBuilder sb= new StringBuilder();
	
	try {
		sb.append(" SELECT abNum, userId, kind1, kind2,  ");
		sb.append("	 content, amount, status,  abDate  ");
		sb.append("  FROM accountbook ");
		sb.append("   WHERE userId=?  ");

		
		if(condition.equals("abDate")) {
		keyword=keyword.replaceAll("(\\-|\\/|\\.)", "");
		sb.append(" AND REPLACE(abDate,  '-', '') = ?");
		} else if(condition.equals("all")) {
			sb.append(" AND ( INSTR(content, ?) >= 1 OR INSTR(kind1, ?)>=1 OR INSTR(kind2, ?)>=1 ) ");
		} else {
			sb.append(" AND INSTR("+condition+", ?) >=1");
		}
		
		sb.append("  ORDER BY abNum ASC ");
		sb.append("  OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
		
		pstmt=conn.prepareStatement(sb.toString());
		if(condition.equals("all")) {
			pstmt.setString(1, userId);
			pstmt.setString(2, keyword);
			pstmt.setString(3, keyword);
			pstmt.setString(4, keyword);
			pstmt.setInt(5, offset);
			pstmt.setInt(6, rows);
		}else {
			pstmt.setString(1, userId);
			pstmt.setString(2, keyword);
			pstmt.setInt(3, offset);
			pstmt.setInt(4, rows);
		}
		rs=pstmt.executeQuery();
		
		while(rs.next()) {
			AccountDTO dto= new AccountDTO();
			dto.setAbNum(rs.getInt("abNum"));
			dto.setUserId(rs.getString("userId"));
			dto.setKind1(rs.getString("kind1"));
			dto.setKind2(rs.getString("kind2"));
			dto.setContent(rs.getString("content"));
			dto.setAmount(rs.getInt("amount"));
			dto.setAbDate(rs.getString("abDate"));
			list.add(dto);		
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		if(rs!=null) {
			try {
				rs.close();
			} catch (Exception e2) {
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (Exception e2) {
			}
		}
	}
		return list;
	}

	@Override
	public AccountDTO readAccount(int abNum) {
		AccountDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql=" SELECT abNum, userId, abDate, kind1, kind2, content, amount, status FROM accountbook WHERE abNum=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, abNum);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new AccountDTO();
				dto.setAbNum(rs.getInt("abNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setAbDate(rs.getString("abDate"));
				dto.setKind1(rs.getString("kind1"));
				dto.setKind2(rs.getString("kind2"));
				dto.setContent(rs.getString("content"));
				dto.setAmount(rs.getInt("amount"));
				dto.setStatus(rs.getInt("status"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
		}
		return dto;
	}

	@Override
	public AccountDTO preReadAccount(int abNum, String condition, String keyword) {	
		AccountDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb= new StringBuilder();
		
		try {
			if(keyword.length() != 0) {
				sb.append(" SELECT abNum, content FROM accountbook ");
				if(condition.equals("all")) {
					sb.append("   WHERE (INSTR(content, ?)  >= 1 OR INSTR(content, ?) >= 1 ) ");
				} else if(condition.equals("abDate")) {
					keyword= keyword.replaceAll("(\\-|\\/|\\.", "");
					sb.append("  WHERE (TO_CHAR(created, 'YYYYMMDD') = ?) ");
				} else {
					sb.append("  WHERE ( INSTR("+condition+", ?) > 0) ");
				}
				sb.append("		ORDER BY abDate DESC  ");
				sb.append("  FETCH FIRST 1 ROWS ONLY ");
				
				pstmt=conn.prepareStatement(sb.toString());
				
				if(condition.equals("all")) {
					pstmt.setString(1, keyword);
					pstmt.setString(1, keyword);
					pstmt.setInt(3, abNum);
				}else {
					pstmt.setString(1, keyword);
                   	pstmt.setInt(2, abNum);
				}
			}else {
				sb.append(" SELECT abNum, content FROM accountbook");
				sb.append("  WHERE abNum > ? ");
				sb.append("  ORDER BY abDate DESC ");
				sb.append("  FETCH FIRST 1 ROWS ONLY ");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, abNum);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new AccountDTO();
				dto.setAbNum(rs.getInt("abNum"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					
				}
			}
		}
		return dto;
	}

	@Override
	public AccountDTO nextReadAccount(int abNum, String condition, String keyword) {
		AccountDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb= new StringBuilder();
		
		try {
			if(keyword.length() != 0) {
				sb.append(" SELECT abNum, content FROM accountbook a JOIN member1 m ON a.userId = m.userId ");
				if(condition.equals("all")) {
					sb.append("   WHERE (INSTR(content, ?)  >= 1 OR INSTR(content, ?) >= 1 ) ");
				} else if(condition.equals("abDate")) {
					keyword= keyword.replaceAll("(\\-|\\/|\\.", "");
					sb.append("  WHERE (TO_CHAR(created, 'YYYYMMDD') = ?) ");
				} else {
					sb.append("  WHERE ( INSTR("+condition+", ?) > 0) ");
				}
				sb.append("		ORDER BY abDate ASC  ");
				sb.append("  FETCH FIRST 1 ROWS ONLY ");
				
				pstmt=conn.prepareStatement(sb.toString());
				
				if(condition.equals("all")) {
					pstmt.setString(1, keyword);
					pstmt.setString(1, keyword);
					pstmt.setInt(3, abNum);
				}else {
					pstmt.setString(1, keyword);
                   	pstmt.setInt(2, abNum);
				}
			}else {
				sb.append(" SELECT abNum, content FROM accountbook");
				sb.append("  WHERE abNum < ? ");
				sb.append("  ORDER BY abDate ASC ");
				sb.append("  FETCH FIRST 1 ROWS ONLY ");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, abNum);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new AccountDTO();
				dto.setAbNum(rs.getInt("abNum"));
				dto.setAbDate(rs.getString("abDate"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					
				}
			}
		}
		return dto;
	}

	
}
