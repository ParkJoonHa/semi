package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.util.DBConn;

public class NoticeDAOImpl implements NoticeDAO{
	private Connection conn = DBConn.getConnection();

	@Override
	public int insertNotice(NoticeDTO dto) throws SQLException {
		int result=0;
		StringBuilder sb=new StringBuilder();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		try {
			// 먼저 시퀀스 값 가져와서 num에 대입 (시퀀스 currval로 가져올 시 문제생길 수 있어서 먼저하기)
			
			// 내용 테이블 추가
			
			if (dto.getSaveFiles()!=null) {
				for (int i = 0; i < dto.getSaveFiles().length; i++) {
					dto.setSaveFilename(dto.getSaveFiles()[i]);
					dto.setOriginalFilename(dto.getOriginalFiles()[i]);
					// 아래에 작성한 파일테이블에 추가하는 메소드 호출
					// insertFile(dto); 
				}
			}
			
			
			
			
			
			
			
			
			
			
			sb.append("INSERT INTO notice ");
			sb.append(" (noticeNum, userId, subject, content, hitCount, created) ");
			sb.append(" VALUES (notice_seq.NEXTVAL, ?, ?, ?, 0, SYSDATE) ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			
			result = pstmt.executeUpdate();

			pstmt.close();
			
			sb.append("SELECT notice_number.CURRVAL FROM dual ");
				// 이렇게 currval 써두 되는지..?
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			int noticeNum = rs.getInt(1);
			
			
			if (dto.getOriginalFilename()!=null) { 
				// 파일이 있는지?? 확인하기 위한 조건문으론 무엇을 넣어야 할까?
				
				sb.append("INSERT INTO multifile ");
				sb.append(" (fileNum, noticeNum, saveFilename, originalFilename) ");
				sb.append(" VALUES (notice_file_seq.NEXTVAL, ?, ?, ? ) ");
				
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, noticeNum);
				pstmt.setString(2, dto.getSaveFilename());
				pstmt.setString(3, dto.getOriginalFilename());
				
				result =pstmt.executeUpdate();					
			}			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int dataCount(String condition, String keyword) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<NoticeDTO> listNotice(int offset, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NoticeDTO> listNotice(int offset, int rows, String condition, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateHitCount(int num) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public NoticeDTO readNotice(int num) {
		// TODO Auto-generated method stub
		return null;
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
