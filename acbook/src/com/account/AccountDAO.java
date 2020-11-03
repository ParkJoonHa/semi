package com.account;

import java.sql.SQLException;
import java.util.List;

public interface AccountDAO {

	public  int insertAccount(AccountDTO dto) throws SQLException;
	public  int updateAccount(AccountDTO dto) throws SQLException;
	public  int deleteAccount(int num, String userId) throws SQLException;

	public List<AccountDTO > listAccount(int offset, int rows);
	public List<AccountDTO > listAccount(int offset, int rows, String condition, String keyword);

	public AccountDTO readAccount(int num);
	public AccountDTO preReadAccount(int num, String condition, String keyword);
	public AccountDTO nextReadAccount(int num, String condition, String keyword);
	
}
