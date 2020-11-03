package com.member;

import java.sql.SQLException;

public interface MemberDAO {
	
	public int insertMember(MemberDTO dto) throws SQLException;
	public int updateMember(MemberDTO dto) throws SQLException;
	public int deleteMember(String userId, String userPwd) throws SQLException;
	public MemberDTO readMember(String userId);
		
}
