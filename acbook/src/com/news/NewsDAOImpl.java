package com.news;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.util.DBConn;

public class NewsDAOImpl implements NewsDAO{
	private Connection conn = DBConn.getConnection();
	@Override
	public int insertNews(NewsDTO dto) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="INSERT INTO news( newsNum, userId , subject, content, photoFileName, created, hitCount) VALUES(news_seq.NEXTVAL,?,?,?,?,SYSDATE,0)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getPhotoFileName());
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
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
	public int updateNews(NewsDTO dto) throws SQLException {
	
		return 0;
	}

	@Override
	public int deleteNews(int num, String userId) throws SQLException {
		
		return 0;
	}

	@Override
	public int dataCount() {
		
		return 0;
	}

	@Override
	public int dataCount(String condition, String keyword) {
		
		return 0;
	}

	@Override
	public List<NewsDTO> listNews(int offset, int rows) {
		List<NewsDTO> list = new ArrayList<NewsDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="  SELECT newsNum, userName, subject, hitCount,created FROM member1 m1, news n1 "
			  + "  WHERE m1.userId = n1.userId "
			  + "  ORDER BY newsNum DESC "
			  + "  OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				NewsDTO dto =new NewsDTO();
				dto.setNewsNum(rs.getInt("newsNum"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getString("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					
				}
			}
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
	public List<NewsDTO> listNews(int offset, int rows, String condition, String keyword) {
		
		return null;
	}

	@Override
	public int updateHitCount(int num) throws SQLException {
		
		return 0;
	}

	@Override
	public NewsDTO readNews(int num) {
	
		return null;
	}

	@Override
	public NewsDTO preReadNews(int num, String condition, String keyword) {
		
		return null;
	}

	@Override
	public NewsDTO nextReadNews(int num, String condition, String keyword) {
		
		return null;
	}

}
