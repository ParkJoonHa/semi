package com.repeat;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;
@WebServlet("/repeat/*")
public class RepeatQnaServlet extends MyServlet {

	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String cp = req.getContextPath();
		String uri = req.getRequestURI();
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		String rows = req.getParameter("rows");
		
		//세션 객체
		
		
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
			
			dto.setUserId(info.getUserId());
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			
			dao.insertRepeat(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/repeat/list.do");
	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RepeatQnaDAO dao = new RepeatQnaDAOImpl();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		String query = "page="+page;
		String path = "/WEB-INF/views/bbs/article.jsp";
		
		try {
			int num = Integer.parseInt(req.getParameter("repeatNum"));
			
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition == null) {
				condition = "all";
				keyword = "";
			}
			
			// GET 방식
			keyword = URLDecoder.decode(keyword, "utf-8");
			if(keyword.length()!=0) {
				query += "&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
			}
			
			RepeatQnaDTO dto = dao.readRepeat(num);
			if(dto == null) {
				resp.sendRedirect(cp+"/repeat/list.do?"+query);
				return;
			}
			
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			
			forward(req, resp, path);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/repeat/list.do?"+query);
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RepeatQnaDAO dao = new RepeatQnaDAOImpl();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		try {
			int num = Integer.parseInt(req.getParameter("repeatNum"));
			
			// 세션 객체
			
			// DB 읽어옴
			RepeatQnaDTO dto = dao.readRepeat(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RepeatQnaDAO dao = new RepeatQnaDAOImpl();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		String query = "page="+page;
		
		try {
			// 세션 객체
			
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
