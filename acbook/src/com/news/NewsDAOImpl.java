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
			sql="INSERT INTO news( newsNum, userId , subject, content, photoFileName, created, hitCount, originalFilename) VALUES(news_seq.NEXTVAL,?,?,?,?,SYSDATE,0,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getPhotoFileName());
			pstmt.setString(5, dto.getOriginalFilename());
		
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
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
			sql="UPDATE news SET subject=? ,content=?,photoFileName=?, originalFilename=? WHERE newsNum=? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getPhotoFileName());
			pstmt.setString(4, dto.getOriginalFilename());
			pstmt.setInt(5, dto.getNewsNum());
		
			
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
		int result=0;
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT COUNT(*) FROM news";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				result=rs.getInt(1);
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
	public int dataCount(String condition, String keyword) {
		int result =0;
		PreparedStatement pstmt=null;
		String sql;
		ResultSet rs=null;
		
		try {
			 sql="SELECT COUNT(*) FROM news n"
		               + "  JOIN member1 m ON n.userId=m.userId";
		          if(condition.equals("created")) { //날짜는 데이터형이라 따로 
		                  keyword=keyword.replaceAll("(\\-|\\/|\\.)", "");
		                  sql+=" WHERE TO_CHAR(created, 'YYYYMMDD')=?";
		           }else if (condition.equals("all")) { //이름따로? -> 한글자만 같아도 나오게하려고?
		                  sql+=" WHERE INSTR(subject, ?)>= 1 OR INSTR(content, ?)>= 1 ";
		            }else {  //subject, content, userName
		                  sql+=" WHERE INSTR("+condition+", ? ) >= 1";
		            }
			pstmt=conn.prepareStatement(sql);
			 pstmt.setString(1, keyword);
			  if(condition.equals("all")) { //all이면 물음표가 두개니까 둘다에 키워드 넣어줘야함
		            pstmt.setString(2, keyword);
		         }
		         rs=pstmt.executeQuery();
		         if(rs.next()) {
		            result=rs.getInt(1);
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
		List<NewsDTO> list =new ArrayList<NewsDTO>();
		
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("SELECT newsNum, userName, subject, content, photoFilename,created ,hitCount");
			sb.append(" FROM news n");
			sb.append("  JOIN member1 m ON n.userId=m.userId");
			
			if(condition.equals("created")) {
				keyword=keyword.replace("(\\-|\\/|\\.)","");
				sb.append(" WHERE TO_CHAR(created,'YYYYMMDD')=? ");
			}else if(condition.equals("all")) {
				sb.append(" WHERE INSTR(subject, ?)>=1 OR INSTR(content, ?)>= 1");
				
			}else {
	            sb.append(" WHERE INSTR("+condition+" ,?)>=1 " );
	         }
			sb.append(" ORDER BY newsNum DESC");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
			
			pstmt=conn.prepareStatement(sb.toString());
			if(condition.equals("all")) {
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, rows);
			}else {
				pstmt.setString(1, keyword);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, rows);
			}
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				NewsDTO dto =new NewsDTO();
				
				dto.setNewsNum(rs.getInt("newsNum"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setCreated(rs.getString("created"));
				dto.setPhotoFileName(rs.getString("photoFileName"));
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
	public int updateHitCount(int newsNum) throws SQLException {
		int result=0;
		PreparedStatement pstmt =null;
		String sql;
		
		try {
			sql=" UPDATE news SET hitCount=hitCount+1 WHERE newsNum=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, newsNum);
			
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
	public NewsDTO readNews(int newsNum) {
		NewsDTO dto =null;
		PreparedStatement pstmt=null;
		String sql;
		ResultSet rs=null;
		
		try {
			sql="SELECT newsNum, n.userId,userName,subject,content,photoFilename,created,hitCount,originalFilename"
			  + " FROM news n"
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
				dto.setOriginalFilename(rs.getString("originalFilename"));
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
		NewsDTO dto=null;
		
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		StringBuilder sb =new StringBuilder();
		
		try {
			if(keyword!=null && keyword.length() !=0) {
				sb.append("SELECT newsNum, subject ");
				sb.append(" FROM news n");
				sb.append(" JOIN member1 m ON n.userId =m.userId ");
				if(condition.equals("created")) {
    				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
    				sb.append(" WHERE (TO_CHAR(created, 'YYYYMMDD') = ? ) AND  ");
    			} else {
    				sb.append(" WHERE (INSTR(" + condition + ", ?) >= 1 ) AND  ");
            	}
					sb.append(" (newsNum > ?)");
					sb.append(" ORDER BY newsNum ASC ");
					sb.append(" FETCH FIRST 1 ROWS ONLY");
					
					
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setNString(1, keyword);
				pstmt.setInt(2, newsNum);
			} else {
                sb.append("SELECT newsNum, subject FROM news n JOIN member1 m ON n.userId=m.userId  ");                
                sb.append(" WHERE newsNum > ?  ");
                sb.append(" ORDER BY newsNum ASC  ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, newsNum);
			}
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new NewsDTO();
				dto.setNewsNum(rs.getInt("newsNum"));
				dto.setSubject(rs.getString("subject"));
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
		return dto;
	}

	@Override
	public NewsDTO nextReadNews(int newsNum, String condition, String keyword) {
		NewsDTO dto=null;

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuilder sb=new StringBuilder();

        try {
            if(keyword.length() != 0) {
                sb.append("SELECT newsNum, subject FROM news n JOIN member1 m ON n.userId=m.userId  ");
                if(condition.equalsIgnoreCase("created")) {
                	keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
                	sb.append(" WHERE (TO_CHAR(created, 'YYYYMMDD') = ?)  ");
                } else {
                	sb.append(" WHERE (INSTR(" + condition + ", ?) >= 1)  ");
                }
                sb.append("         AND (newsNum < ? )  ");
                sb.append(" ORDER BY newsNum DESC  ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setString(1, keyword);
                pstmt.setInt(2, newsNum);
			} else {
                sb.append("SELECT newsNum, subject FROM news n JOIN member1 m ON n.userId=m.userId  ");
                sb.append(" WHERE newsNum < ?  ");
                sb.append(" ORDER BY newsNum DESC  ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, newsNum);
			}

            rs=pstmt.executeQuery();

            if(rs.next()) {
                dto=new NewsDTO();
                dto.setNewsNum(rs.getInt("newsNum"));
                dto.setSubject(rs.getString("subject"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
    
        return dto;
	}

}
