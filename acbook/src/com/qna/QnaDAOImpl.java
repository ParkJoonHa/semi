package com.qna;

import java.sql.SQLException;
import java.util.List;

public class QnaDAOImpl implements QnaDAO {

	@Override
	public int insertQna(QnaDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateQna(QnaDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteQna(int num, String userId) throws SQLException {
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
	public List<QnaDTO> listQna(int offset, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QnaDTO> listQna(int offset, int rows, String condition, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QnaDTO readQna(int num) {
		// TODO Auto-generated method stub
		return null;
	}

}
