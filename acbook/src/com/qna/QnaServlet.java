package com.qna;

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
@WebServlet("/qna/*")
public class QnaServlet extends MyServlet {

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
		} else if(uri.indexOf("answer.do")!=-1) {
			answerForm(req, resp);
		} else if(uri.indexOf("answer_ok.do")!=-1) {
			answerSubmit(req, resp);
		} else if(uri.indexOf("article.do")!=-1) {
			article(req, resp);
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MyUtil util=new MyUtil();
		QnaDAO dao = new QnaDAOImpl();
		String cp=req.getContextPath();
		
		String page=req.getParameter("page");
		int current_page=1;
		if(page!=null) {
			current_page=Integer.parseInt(page);
		}
		
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="all";
			keyword="";
		}
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword=URLDecoder.decode(keyword, "utf-8");
		}
		
		int dataCount;
		if(keyword.length()==0) {
			dataCount=dao.dataCount();
		} else {
			dataCount=dao.dataCount(condition, keyword);
		}
		
		int rows=10;
		int total_page=util.pageCount(rows, dataCount);
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		int offset=(current_page-1)*rows;
		if(offset<0) {
			offset=0;
		}
		
		List<QnaDTO> list;
		if(keyword.length()==0) {
			list=dao.listQna(offset, rows);
		} else {
			list=dao.listQna(offset, rows, condition, keyword);
		}
		
		int listNum, n=0;
		for(QnaDTO dto : list){
			listNum=dataCount-(offset+n);
			dto.setListNum(listNum);
			n++;
		}
		
		String query="";
		if(keyword.length()!=0) {
			query="condition="+condition+"&keyword="
		         +URLEncoder.encode(keyword,"utf-8");
		}
		
		String listUrl=cp+"/qna/list.do";
		String articleUrl=cp+"/qna/article.do?page="+current_page;
		if(query.length()!=0) {
			listUrl+="?"+query;
			articleUrl+="&"+query;
		}
		String paging=util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("page", current_page);
		req.setAttribute("paging", paging);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		
		forward(req, resp, "/WEB-INF/views/qna/list.jsp");
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");
		String path = "/WEB-INF/views/qna/created.jsp";
		forward(req, resp, path);
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		String cp = req.getContextPath();
		QnaDAO dao = new QnaDAOImpl();
		
		try {
			QnaDTO dto = new QnaDTO();
			dto.setUserId(info.getUserId());
			dto.setQ_subject(req.getParameter("q_subject"));
			dto.setQ_content(req.getParameter("q_content"));
			
			dao.insertQna(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/qna/list.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAOImpl();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		String query = "page="+page;
		String path = "/WEB-INF/views/qna/article.jsp";
		
		try {
			int qnaNum = Integer.parseInt(req.getParameter("qnaNum"));
			int status = Integer.parseInt(req.getParameter("status"));

			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition == null) {
				condition = "all";
				keyword = "";
			}
			
			keyword = URLDecoder.decode(keyword, "utf-8");
			if(keyword.length()!=0) {
				query += "condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
			}
			
			QnaDTO dto = dao.readQna(qnaNum, status);

			if(dto == null) {
				resp.sendRedirect(cp+"/qna/list.do?"+query);
				return;
			}
			dto.setQ_content(dto.getQ_content().replaceAll("\n", "<br>"));
			
			if(dto.getStatus() == 1) {
				dto.setA_content(dto.getA_content().replaceAll("\n", "<br>"));
			}
			
			
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			
			forward(req, resp, path);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/qna/list.do?"+query);
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String path = "/WEB-INF/views/qna/created.jsp";
	    QnaDAO dao = new QnaDAOImpl();
	    String cp = req.getContextPath();
	    String page = req.getParameter("page");
	    
	    HttpSession session = req.getSession();
	    SessionInfo info = (SessionInfo)session.getAttribute("member");
	    
	    try {
	    	int qnaNum = Integer.parseInt(req.getParameter("qnaNum"));
	    	int status = Integer.parseInt(req.getParameter("status")); 
			
	    	QnaDTO dto = dao.readQna(qnaNum, status);
	    	
	    	if(dto == null || ! dto.getUserId().equals(info.getUserId())) {
	    		resp.sendRedirect(cp+"/qna/list.do?page="+page);
	    		return;
	    	}
	    	
	    	req.setAttribute("dto", dto);
	    	req.setAttribute("page", page);
	    	req.setAttribute("mode", "update");
	    	
	    	forward(req, resp, path);
	    	return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	    resp.sendRedirect(cp+"/qna/list.do?=page"+page);
	 }

	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAOImpl();
		QnaDTO dto = new QnaDTO();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		try {
			dto.setQnaNum(Integer.parseInt(req.getParameter("qnaNum")));
			dto.setQ_subject(req.getParameter("q_subject"));
			dto.setQ_content(req.getParameter("q_content"));
			dto.setUserId(req.getParameter("userId"));
			
			dao.updateQna(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/qna/list.do?page="+page);
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		String query = "page="+page;
		QnaDAO dao = new QnaDAOImpl();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		try {
			int status = Integer.parseInt(req.getParameter("status"));
			String condition = req.getParameter("contition");
			String keyword = req.getParameter("keyword");
			if(condition == null) {
				condition = "all";
				keyword ="";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");
			if(keyword.length()!=0) {
				query += "&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
			}
			
			int qnaNum = Integer.parseInt(req.getParameter("qnaNum"));
			QnaDTO dto = dao.readQna(qnaNum, status);
			
			if(dto == null) {
				resp.sendRedirect(cp+"/qna/list.do?"+query);
				return;
			}
			if(!dto.getUserId().equals(info.getUserId()) && !info.getUserId().equals("admin")) {
				resp.sendRedirect(cp+"/qna/list.do?"+query);
				return;
			}
			dao.deleteQna(qnaNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/qna/list.do?"+query);
	}
	
	protected void answerForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int qnaNum = Integer.parseInt(req.getParameter("qnaNum"));
		int status = Integer.parseInt(req.getParameter("status"));
		String page = req.getParameter("page");
		QnaDAO dao = new QnaDAOImpl();
		QnaDTO dto = new QnaDTO();
		dto = dao.readQna(qnaNum, status);
		
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("qnaNum", qnaNum);
		req.setAttribute("mode", "created");
		String path = "/WEB-INF/views/qna/answer.jsp";
		forward(req, resp, path);
	}
	protected void answerSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		String cp = req.getContextPath();
		QnaDAO dao = new QnaDAOImpl();
		
		try {
			QnaDTO dto = new QnaDTO();
			dto.setQnaNum(Integer.parseInt(req.getParameter("qnaNum")));
			dto.setUserId(info.getUserId());
			dto.setA_subject(req.getParameter("a_subject"));
			dto.setA_content(req.getParameter("a_content"));
			
			dao.insertAnswer(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/qna/list.do");
	}
}
