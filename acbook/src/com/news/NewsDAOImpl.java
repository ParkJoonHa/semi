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
		PreparedStatement pstmt=null;
		int result=0;
		String sql;
		
		try {
			sql="UPDATE news SET subject=? ,content=? WHERE newsNum=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getNewsNum());
			
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
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
	public int deleteNews(int newsNum, String userId) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		
		try {
			if(userId.equals("admin")) {
				sql="DELETE FROM news WHERE newsNum=?";
			}else {
				sql="DELETE FORM news WHERE newsNum=? AND useId=?";	
			}
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, newsNum);
			if(!userId.equals("admin")) {
				pstmt.setString(2, "userId");
			}
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}

	@Override
	public int dataCount() {
		
		return 0;
	}

	@Override
	public int dataCount(String condition, String keyword) {
		int result =0;
		PreparedStatement pstmt=null;
		String sql;
		ResultSet rs=null;
		
		try {
			
		} catch (Exception e) {
		}
		return result;
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
	public int updateHitCount(int newsNum) throws SQLException {
		
		return 0;
	}

	@Override
	public NewsDTO readNews(int newsNum) {
		NewsDTO dto =null;
		PreparedStatement pstmt=null;
		String sql;
		ResultSet rs=null;
		
		try {
			sql="SELECT newsNum, n.userId,userName,subject,content,photoFilename,created,hitCount"
			  + " FROM news n "
			  + " JOIN member1 m ON n.userId =m.userId WHERE newsNum=? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, newsNum);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new NewsDTO();
				dto.setNewsNum(rs.getInt("newsNum"));
				dto.setUserId(rs.getNString("userId"));
				dto.setUserName(rs.getNString("userName"));
				dto.setSubject(rs.getNString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setPhotoFileName(rs.getNString("photoFileName"));
				dto.setCreated(rs.getString("created"));
				dto.setHitCount(rs.getString("hitCount"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		return dto;
	}

	@Override
	public NewsDTO preReadNews(int newsNum, String condition, String keyword) {
		
		return null;
	}

	@Override
	public NewsDTO nextReadNews(int newsNum, String condition, String keyword) {
		
		return null;
	}

}
