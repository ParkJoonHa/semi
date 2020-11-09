package com.repeat;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;
@WebServlet("/repeat/*")
public class RepeatQnaServlet extends MyServlet {

	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String cp = req.getContextPath();
		String uri = req.getRequestURI();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		if(uri.indexOf("list.do") != -1 && info == null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		if(uri.indexOf("list.do")!=-1) {
			list(req, resp);
		} else if(uri.indexOf("created.do")!=-1) {
			createdForm(req, resp);
		} else if(uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);
		} else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		} 
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RepeatQnaDAO dao = new RepeatQnaDAOImpl();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		int current_page = 1;
		if(page != null) {
			current_page = Integer.parseInt(page);
		}
		
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition == null) {
			condition = "subject";
			keyword = "";
		}
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "utf-8");
		}
		
		String numPerPage=req.getParameter("rows");
        int rows = numPerPage == null ? 10 : Integer.parseInt(numPerPage);
        
		int dataCount, total_page;
		
		if(keyword.length()!=0) {
			dataCount= dao.dataCount(condition, keyword);
		} else {
			dataCount= dao.dataCount();
		}
		total_page=util.pageCount(rows, dataCount);
		
		if(current_page>total_page)
			current_page=total_page;
		
		int offset=(current_page-1)*rows;
		if(offset < 0)
			offset = 0;
		
		List<RepeatQnaDTO> list;
		if(keyword.length()!=0) {
			list = dao.listBoard(offset, rows, condition, keyword);
		} else {
			list = dao.listBoard(offset, rows);
		}
		
		int listNum, n=0;
		for(RepeatQnaDTO dto : list) {
			listNum = dataCount-(offset+n);
			dto.setListNum(listNum);
			
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			
			n++;
		}
		
		String query = "";
		if(keyword.length()!=0) {
			query = "condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		
		String listUrl = cp+"/repeat/list.do";
		String articleUrl = cp+"/repeat/article.do?page="+current_page;
		if(query.length()!=0) {
			listUrl += "?"+query;
			articleUrl += "&"+query;
		}
		String paging = util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("page", current_page);
		req.setAttribute("paging", paging);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("keyword", keyword);
		
		String path = "/WEB-INF/views/repeat/list.jsp";
		forward(req, resp, path);
	}
	
	

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		String rows = req.getParameter("rows");
		
		//세션 객체
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		// admin(관리자)가 아니면 글을 작성할 수 없음
		if(! info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/repeat/list.do?rows="+rows);
			return;
		}
		
		String path = "/WEB-INF/views/repeat/created.jsp";
		req.setAttribute("mode", "created");
		forward(req, resp, path);
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RepeatQnaDAO dao = new RepeatQnaDAOImpl();
		RepeatQnaDTO dto = new RepeatQnaDTO();
		String cp = req.getContextPath();
		
		try {
			// 세션 객체
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			
			dto.setUserId(info.getUserId());
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			
			dao.insertRepeat(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/repeat/list.do");
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RepeatQnaDAO dao = new RepeatQnaDAOImpl();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		try {
			int num = Integer.parseInt(req.getParameter("repeatNum"));
			
			// 세션 객체
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			
			// DB 읽어옴
			RepeatQnaDTO dto = dao.readRepeat(num);
			
			if(dto == null || ! dto.getUserId().equals(info.getUserId())) {
				resp.sendRedirect(cp+"/repeat/list.do?page="+page);
				return;
			}
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");
			
			String path = "/WEB-INF/views/repeat/created.jsp";
			forward(req, resp, path);
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/repeat/list.do?page="+page);
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RepeatQnaDAO dao = new RepeatQnaDAOImpl();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		try {
			int num = Integer.parseInt(req.getParameter("repeatNum"));
			RepeatQnaDTO dto = new RepeatQnaDTO();
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			dto.setRepeatNum(num);
			dto.setUserId(info.getUserId());
			
			dao.updateRepeat(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/repeat/list.do?page="+page);
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RepeatQnaDAO dao = new RepeatQnaDAOImpl();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		String query = "page="+page;
		
		try {
			// 세션 객체
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			
			int num = Integer.parseInt(req.getParameter("repeatNum"));
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition == null) {
				condition = "subject";
				keyword = "";
			}
			if(keyword.length()!=0) {
				query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
			}
			
			RepeatQnaDTO dto = dao.readRepeat(num);
			if(dto==null) {
				resp.sendRedirect(cp+"/repeat/list.do?"+query);
				return;
			}
			
			if(! info.getUserId().equals(dto.getUserId()) && ! info.getUserId().equals("admin")) {
				resp.sendRedirect(cp+"/repeat/list.do?"+query);
				return;
			}
			
			dao.deleteRepeat(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/repeat/list.do?"+query);
	}
}
