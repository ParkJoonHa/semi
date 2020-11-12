package com.account;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyUtil;

@WebServlet("/account/*")
public class AccountServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		// 포워딩을 위한 메소드
		RequestDispatcher rd=req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
	
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { 
			String cp= req.getContextPath();
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		String uri=req.getRequestURI();
		
		if(uri.indexOf("listD.do")!=-1) {
			listD(req, resp);
		} else if(uri.indexOf("listW.do")!=-1) {
			listW(req, resp);
		} else if(uri.indexOf("listM.do")!=-1) {
			listM(req, resp);
		} else if(uri.indexOf("article.do")!=-1) {
			article(req, resp);
		} else if(uri.indexOf("addlog.do")!=-1) {
			addlogForm(req, resp);
		} else if(uri.indexOf("addlog_ok.do")!=-1) {
			addlogSubmit(req, resp);
		} else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		}	
	}

	protected void addlogForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "addlog");
		forward(req, resp, "/WEB-INF/views/account/addlog.jsp");	
	}
	
	protected void addlogSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AccountDAO dao=new AccountDAOImpl();
		AccountDTO dto=new AccountDTO();
		String cp=req.getContextPath();
		
		try {
			HttpSession session=req.getSession();
			SessionInfo info=(SessionInfo) session.getAttribute("member");
			
			dto.setUserId(info.getUserId());
			dto.setStatus(Integer.parseInt(req.getParameter("status")));
			dto.setAbDate(req.getParameter("abDate"));
			dto.setKind1(req.getParameter("kind1"));
			dto.setKind2(req.getParameter("kind2"));
			dto.setContent(req.getParameter("content"));
			dto.setAmount(Integer.parseInt(req.getParameter("amount")));
			
			dao.insertAccount(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/account/listD.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//글내용
		String cp=req.getContextPath();
		AccountDAO dao= new AccountDAOImpl();
		
		try {
			int abNum=Integer.parseInt(req.getParameter("abNum"));
			String page=req.getParameter("page");
			
			String condition=req.getParameter("condition");
			String keyword=req.getParameter("keyword");
			
			if(condition==null ) {
				condition="all";
				keyword="";
			}
			keyword=URLDecoder.decode(keyword,"utf-8");
			
			String query=URLDecoder.decode(keyword,"utf-8");
			if(keyword.length()!=0) {
				query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword,"utf-8");
			}
			
			AccountDTO dto=dao.readAccount(abNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/account/list.do?page="+page);
				return;
			}
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			
			AccountDTO preAccountDTO=dao.preReadAccount(abNum, condition, keyword);
			AccountDTO nextAccountDTO=dao.nextReadAccount(abNum, condition, keyword);
			
			req.setAttribute("dto", dto);
			req.setAttribute("preAccountDTO", preAccountDTO);
			req.setAttribute("nextAccountDTO", nextAccountDTO );
			req.setAttribute("query", query);
			req.setAttribute("page", page);
			
			forward(req, resp, "/WEB-INF/views/account/article.jsp");			
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/account/listD.do");
	}
	
	protected void listM(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//월별 리스트
		AccountDAO dao= new AccountDAOImpl();
		String cp = req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		Calendar cal=Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;  // 0 ~ 11
		int todayYear=year;
		int todayMonth=month;
		int todayDate=cal.get(Calendar.DATE);

		String date=String.format("%04d-%02d", year, month);
		
		try {
			String y=req.getParameter("year");
			String m=req.getParameter("month");
			
			if(y!=null) {
				year=Integer.parseInt(y);
			}
			if(m!=null) {
				month=Integer.parseInt(m);
			}
			
			date=String.format("%04d-%02d", year, month);
			
			// year년 month월 1일의 요일
			cal.set(year, month-1, 1);
			year=cal.get(Calendar.YEAR);
			month=cal.get(Calendar.MONTH)+1;
			int week=cal.get(Calendar.DAY_OF_WEEK); // 1~7
			
			// 첫주의 year년도 month월 1일 이전 날짜
			Calendar scal=(Calendar)cal.clone();
			scal.add(Calendar.DATE, -(week-1));
			int syear=scal.get(Calendar.YEAR);
			int smonth=scal.get(Calendar.MONTH)+1;
			int sdate=scal.get(Calendar.DATE);
			
			// 마지막주의 year년도 month월 말일주의 토요일 날짜
			Calendar ecal=(Calendar)cal.clone();
			// year년도 month월 말일
			ecal.add(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			// year년도 month월 말일주의 토요일
			ecal.add(Calendar.DATE, 7-ecal.get(Calendar.DAY_OF_WEEK));
			int eyear=ecal.get(Calendar.YEAR);
			int emonth=ecal.get(Calendar.MONTH)+1;
			int edate=ecal.get(Calendar.DATE);
			
			// 가계부 정보 가져오기
			List<AccountDTO> list=dao.listAccount(info.getUserId(), date);
			
			String s;
			String [][]days=new String[cal.getActualMaximum(Calendar.WEEK_OF_MONTH)][7];
			
			// 1일 앞의 전달 날짜 출력
			// startDay ~ endDay 까지 처리
			for(int i=1; i<week; i++) {
				s=String.format("%04d-%02d-%02d", syear, smonth, sdate);
				days[0][i-1]="<span class='textDate preMonthDate' data-date='"+s+"' >"+sdate+"</span>";
				
				sdate++;
			}
			
			// year년도 month월 날짜 및 금액 출력
			int row, n=0;
			
			jump:
			for(row=0; row<days.length; row++) {
				for(int i=week-1; i<7; i++) {
					n++;
					s=String.format("%04d-%02d-%02d", year, month, n);
					
					if(i==0) {
						days[row][i]="<span class='textDate sundayDate' data-date='"+s+"' >"+n+"</span>";
					} else if(i==6) {
						days[row][i]="<span class='textDate saturdayDate' data-date='"+s+"' >"+n+"</span>";
					} else {
						days[row][i]="<span class='textDate nowDate' data-date='"+s+"' >"+n+"</span>";
					}
					
					for(AccountDTO dto:list) {
						int d=Integer.parseInt(dto.getDay());
						if(n==d) {					
							days[row][i]+=" <span class='accountIncome'> <i class='fas fa-angle-up'></i> "+dto.getIncome()+" </span>";
							days[row][i]+=" <span class='accountExpense'> <i class='fas fa-angle-down'></i> "+dto.getExpense()+" </span>";
							break;
						}
					}
					
					if(n==cal.getActualMaximum(Calendar.DATE)) {
						week=i+1;
						break jump;
					}
				}
				week=1;
			}
			
			// year년도 month월 마지막 날짜 이후 날짜 출력
			if(week!=7) {
				n=0;
				for(int i=week; i<7; i++) {
					n++;
					s=String.format("%04d-%02d-%02d", eyear, emonth, n);
					days[row][i]="<span class='textDate nextMonthDate' data-date='"+s+"' >"+n+"</span>";
				}
			}
			
			String today=String.format("%04d-%02d-%02d", todayYear, todayMonth, todayDate);
			
			req.setAttribute("year", year);
			req.setAttribute("month", month);
			req.setAttribute("todayYear", todayYear);
			req.setAttribute("todayMonth", todayMonth);
			req.setAttribute("todayDate", todayDate);
			req.setAttribute("today", today);
			req.setAttribute("days", days);
			
			forward(req, resp, "/WEB-INF/views/account/listM.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/account/listD.do");
		
	}
	protected void listW(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//주별 리스트
		forward(req, resp, "/WEB-INF/views/account/listW.jsp");
	}
	protected void listD(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//일별 리스트
		AccountDAO dao= new AccountDAOImpl();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");

		String page = req.getParameter("page");
		int current_page = 1;
		if (page != null) {
			current_page = Integer.parseInt(page);
		}
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		String userId = info.getUserId();
		
		if (condition == null) {
			condition = "all";
			keyword = "";
		}
		if (req.getMethod().equalsIgnoreCase("GET"));
		{
			keyword = URLDecoder.decode(keyword, "utf-8");
		}

		int dataCount;
		if (keyword.length() == 0) {
			dataCount = dao.dataCount(userId);
		} else {
			dataCount = dao.dataCount(userId, condition, keyword);
		}
		int rows = 10;
		int total_page = util.pageCount(rows, dataCount);
		if (current_page > total_page)
			current_page = total_page;

		int offset = (current_page - 1) * rows;
		if (offset < 0)
			offset = 0;

		List<AccountDTO> list;
		if (keyword.length() == 0) {
			list = dao.listAccount(offset, rows, userId);
		} else {
			list = dao.listAccount(offset, rows, condition, keyword, userId);
		}

		String query = "";
		if (keyword.length() != 0) {
			query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}

		String listDUrl = cp + "/account/listD.do";
		String articleUrl = cp + "/account/article.do?page=" + current_page;
		if (query.length() != 0) {
			listDUrl += "?" + query;
			articleUrl += "&" + query;
		}
		String paging = util.paging(current_page, total_page, listDUrl);

		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("page", current_page);
		req.setAttribute("paging", paging);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);

		forward(req, resp,"/WEB-INF/views/account/listD.jsp");
	}	
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AccountDAO dao=new AccountDAOImpl();
		String cp=req.getContextPath();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		String page=req.getParameter("page");
		int abNum=Integer.parseInt(req.getParameter("abNum"));
	
		try {
			
			AccountDTO dto=dao.readAccount(abNum);
//			 || ! dto.getUserId().equals(info.getUserId())
			if(dto==null) {
				resp.sendRedirect(cp+"/account/listM.do?page="+page);
				return;
			}		
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/account/addlog.jsp");						
			return;			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/account/listD.do?page="+page);
	}
		
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AccountDAO dao=new AccountDAOImpl();
		String cp=req.getContextPath();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		int page = Integer.parseInt(req.getParameter("page"));
		int abNum = Integer.parseInt(req.getParameter("abNum"));

		try {
			AccountDTO dto=new AccountDTO();
			
			dto.setUserId(info.getUserId()); 
			dto.setAbNum(abNum);
			dto.setStatus(Integer.parseInt(req.getParameter("status")));
			dto.setAbDate(req.getParameter("abDate"));
			dto.setKind1(req.getParameter("kind1"));
			dto.setKind2(req.getParameter("kind2"));
			dto.setContent(req.getParameter("content"));
			dto.setAmount(Integer.parseInt(req.getParameter("amount")));
			
			dao.updateAccount(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/account/listD.do?page="+page);
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AccountDAO dao=new AccountDAOImpl();
		String cp=req.getContextPath();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		String page=req.getParameter("page");
		String query="page="+page;
		
		try {
			int abNum=Integer.parseInt(req.getParameter("abNum"));
			dao.deleteAccount(abNum, info.getUserId());
			String condition=req.getParameter("condition");
			String keyword=req.getParameter("keyword");
			if(condition==null) {
				condition="all";
				keyword="";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/account/listD.do?+query");
	}
	
}
