package com.account;

import java.text.DecimalFormat;

/**
 * 가계부 게시판 DTO abNum : 가계부 번호(기본키) userId : 아이디(외래키) kind1 : 상위분류 kind2 : 하위분류
 * content : 내용 abDate : 수입/지출날짜 status : 구별 용도
 * 
 * @author INAEKIM
 */
public class AccountDTO {
	private int abNum;
	private String userId;
	private String kind1;
	private String kind2;
	private String content;
	private String abDate;
	private int amount;
	private int status;
	private int result;
	
	private String day; // 날짜
	private int expense; // 지출
	private int income; // 수입
	
	DecimalFormat formatter = new DecimalFormat("###,###");

	public String getResult() {
		return formatter.format(result);
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getAbNum() {
		return abNum;
	}

	public void setAbNum(int abNum) {
		this.abNum = abNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKind1() {
		return kind1;
	}

	public void setKind1(String kind1) {
		this.kind1 = kind1;
	}

	public String getKind2() {
		return kind2;
	}

	public void setKind2(String kind2) {
		this.kind2 = kind2;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAbDate() {
		return abDate;
	}

	public void setAbDate(String abDate) {
		this.abDate = abDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getExpense() {
		return expense;
	}

	public void setExpense(int expense) {
		this.expense = expense;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}
}
