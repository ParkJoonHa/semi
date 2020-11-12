package com.account;

import java.sql.SQLException;
import java.util.List;

public interface AccountDAO {

	public  int insertAccount(AccountDTO dto) throws SQLException;
	public  int updateAccount(AccountDTO dto) throws SQLException;
	public  int deleteAccount(int abNum, String userId) throws SQLException;

	public int dataCount(String userId);
	public int dataCount(String userId, String condition, String keyword);
	
	public List<AccountDTO > listAccount(int offset, int rows, String userId);
	public List<AccountDTO > listAccount(int offset, int rows, String condition, String keyword, String userId);
	public List<AccountDTO > listAccount(String userId, String date);

	public AccountDTO readAccount(int abNum);
	public AccountDTO preReadAccount(int abNum, String condition, String keyword);
	public AccountDTO nextReadAccount(int abNum, String condition, String keyword);
	
}
