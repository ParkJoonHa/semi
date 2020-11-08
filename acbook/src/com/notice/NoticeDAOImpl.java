package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.util.DBConn;

public class NoticeDAOImpl implements NoticeDAO {
	private Connection conn = DBConn.getConnection();

	@Override
	public int insertNotice(NoticeDTO dto) throws SQLException {
		int result = 0;
		StringBuilder sb = new StringBuilder();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 먼저 시퀀스 값 가져와서 num에 대입 (시퀀스 currval로 가져올 시 문제생길 수 있어서 먼저하기)
			sb.append("SELECT notice_seq.NEXTVAL FROM dual ");
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			int noticeNum=0;
			
			if (rs.next()) {
				noticeNum = rs.getInt(1);								
			}
			
			rs.close();
			pstmt.close();
			sb.setLength(0);

			sb.append("INSERT INTO notice ");
			sb.append(" (noticeNum, userId, subject, content, hitCount, created) ");
			sb.append(" VALUES (?, ?, ?, ?, 0, SYSDATE) ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, noticeNum);
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContent());
			
			result = pstmt.executeUpdate();

			// 내용 테이블 추가
			if (dto.getSaveFiles() != null) { // 첨부 파일이 있으면
				for (int i = 0; i < dto.getSaveFiles().length; i++) {
					dto.setSaveFilename(dto.getSaveFiles()[i]);
					dto.setOriginalFilename(dto.getOriginalFiles()[i]);
					// 아래에 작성한 파일테이블에 추가하는 메소드 호출
					insertFile(dto, noticeNum);
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
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
	public int insertFile(NoticeDTO dto, int noticeNum) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO multiFile "
					+ "  (fileNum, noticeNum, saveFilename, originalFilename) "
					+ "   VALUES (notice_file_seq.NEXTVAL, ?, ?, ? ) ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeNum);
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
	public int updateNotice(NoticeDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteNotice(int num, String userId) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int dataCount() {
		int result =0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*),0) FROM notice";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result=rs.getInt(1);
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
		
		return result;
	}

	@Override
	public int dataCount(String condition, String keyword) {
        int result=0;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String sql;

        try {
        	if(condition.equalsIgnoreCase("created")) {
        		keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
        		sql="SELECT NVL(COUNT(*), 0) "
        				+ " FROM notice n "
        				+ " JOIN member1 m ON n.userId=m.userId "
        				+ " WHERE TO_CHAR(created, 'YYYYMMDD') = ?  ";
        	} else {
        		sql="SELECT NVL(COUNT(*), 0) "
        				+ " FROM notice n "
        				+ " JOIN member1 m ON n.userId=m.userId "
        				+ " WHERE  INSTR(" + condition + ", ?) >= 1 ";
        	}
        	
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, keyword);

            rs=pstmt.executeQuery();

            if(rs.next()) {
                result=rs.getInt(1);
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
        
        return result;
	}

	@Override
	public List<NoticeDTO> listNotice(int offset, int rows) {
		List<NoticeDTO> list=new ArrayList<NoticeDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			// saveFilename 을 제외하고 테이블의 정보 받아서 dto에 담기? (현재 첨부파일이 여러개 있으면 다 리스트에 담김.)
			sb.append("SELECT n.noticeNum, n.userId, userName, subject, ");
			sb.append("       hitCount, created  ");
			sb.append(" FROM notice n "
					+ " JOIN member1 m ON n.userId=m.userId ");
			sb.append(" LEFT OUTER JOIN multiFile f ON n.noticeNum=f.noticeNum");
			sb.append(" ORDER BY noticeNum DESC  ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			
			// mutiFile 테이블의 내용 전부 리스트로 가져오는 메소드
			List<NoticeDTO> fileList = listNoticeFile();
			
			while(rs.next()) {
				NoticeDTO dto=new NoticeDTO();
				
				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				// dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				int noticeNum = dto.getNoticeNum();
				List<String> addSaveFilename = new ArrayList<String>();
				List<String> addOriginalFilename = new ArrayList<String>();
				
				// noticeNum이 동일한 저장파일명, 원본파일명 리스트로 받기
				for(NoticeDTO fileDto : fileList) {
					if (noticeNum == fileDto.getNoticeNum()) {
						addSaveFilename.add(fileDto.getSaveFilename());
						addOriginalFilename.add(fileDto.getOriginalFilename());
					}
				}
				
				dto.setSaveFiles(addSaveFilename.toArray(new String[addSaveFilename.size()])); 	
				dto.setOriginalFiles(addOriginalFilename.toArray(new String[addOriginalFilename.size()]));
				
				list.add(dto);
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
		return list;
	}

	@Override
	public List<NoticeDTO> listNotice(int offset, int rows, String condition, String keyword) {
        List<NoticeDTO> list=new ArrayList<NoticeDTO>();

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuilder sb=new StringBuilder();

        try {
			sb.append("SELECT n.noticeNum, n.userId, userName, subject, saveFilename, ");
			sb.append("       hitCount, created  ");
			sb.append(" FROM notice n "
					+ " JOIN member1 m ON n.userId=m.userId  ");
			sb.append(" LEFT OUTER JOIN multiFile f ON n.noticeNum = f.noticeNum  ");
			
			if(condition.equalsIgnoreCase("created")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" WHERE TO_CHAR(created, 'YYYYMMDD') = ?  ");
			} else {
				sb.append(" WHERE INSTR(" + condition + ", ?) >= 1  ");
			}
			sb.append(" ORDER BY noticeNum DESC  ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
            
			pstmt=conn.prepareStatement(sb.toString());
            
			pstmt.setString(1, keyword);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);
            
            rs=pstmt.executeQuery();
            
            while(rs.next()) {
				NoticeDTO dto=new NoticeDTO();
				
				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				
				dto.setSaveFilename(rs.getString("saveFilename"));
				
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created")); // yyyy-MM-dd HH:mm:ss
                
                list.add(dto);
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
        
        return list;
	}

	public List<NoticeDTO> listNoticeFile(){ // 파일리스트 
		NoticeDTO dto = new NoticeDTO();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		List<NoticeDTO> fileList = new ArrayList<NoticeDTO>();
		
		try {
			sql = "SELECT noticeNum, fileNum, saveFilename, originalFilename FROM multiFile";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setFileNum(rs.getInt("fileNum"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setOriginalFilename(rs.getString("originalFilename"));
				fileList.add(dto);
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
		
		return fileList;
	}
	
	
	@Override
	public int updateHitCount(int num) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public NoticeDTO readNotice(int noticeNum) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		
		try {
			sql = "SELECT NVL(COUNT(*),0) FROM multiFile WHERE noticeNum = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeNum);
			rs = pstmt.executeQuery();
			int f_length=0;
			
			if (rs.next()) {
				f_length = rs.getInt(1);
			}
			
			rs.close();
			pstmt.close();
			
			sql = "SELECT f.noticeNum, fileNum, saveFilename, originalFilename FROM multiFile "
					+ " FROM multiFile f "
					+ "	JOIN notice n ON n.noticeNum = f.noticeNum "
					+ " WHERE noticeNum = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeNum);
			rs = pstmt.executeQuery();		
			
			while(rs.next()) {
				String saveFilename = rs.getString(2);
				String saveFiles[] = new String[f_length];
				
				String originalFilename = rs.getString(3);
				String originalFiles[] = new String[f_length];
				
				dto.setSaveFiles(saveFiles);
				dto.setOriginalFiles(originalFiles);
			}			
			
			rs.close();
			pstmt.close();
			
			
			sql = "SELECT n.noticeNum, n.userId, userName, subject, content, "
					+ " fileNum, saveFilename, originalFilename, hitCount, created "
					+ " FROM notice n "
					+ " JOIN member1 m ON n.userId=m.userId "
					+ " LEFT OUTER JOIN mulfiFile f ON n.noticeNum=f.noticeNum "
					+ " WHERE noticeNum = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeNum);
			
			rs = pstmt.executeQuery();

			if( rs.next()) {
				dto = new NoticeDTO();
				
				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));	
				dto.setFileNum(rs.getInt("fileNum"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setOriginalFilename(rs.getString("originalFilename"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
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

	@Override
	public NoticeDTO preReadNotice(int num, String condition, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoticeDTO nextReadNotice(int num, String condition, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
