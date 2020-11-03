package com.member;
/**
 * userId : 아이디(member1,2 기본키)
 * userName : 이름
 * userPwd : 비밀번호
 * status : 계정 상태 판별 ex) 1이면 운영자 2면 임시정지 3이면 영구정지...
 * c_date : 계정생성일
 * m_date : 계정 정보 수정일
 * email : 이메일 1+2 통합본 ex) email1+"@"+email2 = email
 * email1 : 이메일 앞부분
 * email2 : 이메일 뒷부분
 * tel : 전화번호 통합본 ex) tel1+"-"+tel2+"-"+tel3 = tel
 * tel1 : 전화번호 앞 ex) 010
 * tel2 : 전화번호 중단 ex) 1111
 * tel3 : 전화번호 하단 ex) 1111
 * zip_code : 우편번호
 * addr1 : 주소
 * addr2 : 상세주소
 * @author SIST
 *
 */
public class MemberDTO {
	private String userId;
	private String userName;
	private String userPwd;
	private int status;
	private String c_date;
	private String m_date;
	private String birth;
	private String email;
	private String email1;
	private String email2;
	private String tel;
	private String tel1;
	private String tel2;
	private String tel3;
	private String zip_code;
	private String addr1;
	private String addr2;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getC_date() {
		return c_date;
	}
	public void setC_date(String c_date) {
		this.c_date = c_date;
	}
	public String getM_date() {
		return m_date;
	}
	public void setM_date(String m_date) {
		this.m_date = m_date;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getTel3() {
		return tel3;
	}
	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	
	
	
}
