package com.news;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.member.SessionInfo;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/news/*")
public class NewsServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	private String pathname;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path)
			throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();
		

		// 메인페이지 만들어지면 로그인 해야 정보게시판에 접근 가능하게 만들계획

		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		String root=session.getServletContext().getRealPath("/");
		pathname=root+"uploads"+File.separator+"photo";
		
		if(info==null){
			String cp=req.getContextPath();
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}

		if (uri.indexOf("main.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("created.do") != -1) {
			createdForm(req, resp);
		} else if (uri.indexOf("created_ok.do") != -1) {
			createdSubmit(req, resp);
		} else if (uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NewsDAO dao = new NewsDAOImpl();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		int current_page = 1;
		if (page != null) {
			current_page = Integer.parseInt(page);

		}
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "all";
			keyword = "";
		}
		if (req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "utf-8");
		}
		int dataCount;
		if (keyword.length() == 0) {
			dataCount = dao.dataCount();
		} else {
			dataCount = dao.dataCount(condition, keyword);
		}

		int rows = 10;
		int total_page = util.pageCount(rows, dataCount);
		if (current_page > total_page)
			current_page = total_page;

		int offset = (current_page - 1) * rows;
		if (offset < 0)
			offset = 0;

		List<NewsDTO> list;
		if (keyword.length() == 0) {
			list = dao.listNews(offset, rows);
		} else {
			list = dao.listNews(offset, rows, condition, keyword);
		}
		// 리스트 번호 만들기

		Date curDate=new Date();
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  int listNum, n=0;
		  long gap;
		  for(NewsDTO dto:list) {
			  listNum=dataCount-(offset+n);
			  dto.setListNum(listNum);
			  
			  try {
				Date date=sdf.parse(dto.getCreated());
				gap=(curDate.getTime()- date.getTime())/(1000*60*60);
				dto.setGap(gap);
			} catch (Exception e) {
				
			}
			  dto.setCreated(dto.getCreated().substring(0,10));
			  
			  n++;
		  }

		String query = "";
		if (keyword.length() != 0) {
			query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}
		String listUrl = cp + "/news/main.do";
		String articleUrl = cp + "/news/article.do?page=" + current_page;
		if (query.length() != 0) {
			listUrl += "?" + query;
			articleUrl += "&" + query;

		}
		String paging = util.paging(current_page, total_page, listUrl);
		// /WEB-INF /view/bbs/list.jsp 에 넘겨줄 테이터
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("page", current_page);
		req.setAttribute("paging", paging);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);

		String path = "/WEB-INF/views/news/news.jsp";
		forward(req, resp, path);

	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");
		String path = "/WEB-INF/views/news/created.jsp";
		forward(req, resp, path);

	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		NewsDTO dto = new NewsDTO();
		NewsDAO dao = new NewsDAOImpl();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");

		try {
			dto.setUserId(info.getUserId());
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));

			String filename = null;
			Part p = req.getPart("selectFile");
			Map<String, String> map = doFileUpload(p, pathname);
			if (map != null) {
				filename = map.get("saveFilename");
				dto.setPhotoFileName(filename);
			}
			dao.insertNews(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String cp=req.getContextPath();
	    resp.sendRedirect(cp+"/news/main.do");
	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NewsDAO dao=new NewsDAOImpl();
		String cp=req.getContextPath();
		
		try {
			int newsNum=Integer.parseInt(req.getParameter("newsNum"));
			String page=req.getParameter("page");
			
			String condition=req.getParameter("condition");
			String keyword=req.getParameter("keyword");
			if(condition==null) {
				condition="all";
				keyword="";
			}
			keyword=URLDecoder.decode(keyword,"utf-8");
			
			String query="page="+page;
			if(keyword.length()!=0) {
				query+="&condition="+condition+"&keyword="+
			         URLEncoder.encode(keyword,"utf-8");
			}
			
	
			dao.updateHitCount(newsNum);
			
			
			NewsDTO dto=dao.readNews(newsNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/news/main.do?"+query);
				return;
			}
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			
		
			NewsDTO preReadDto=dao.preReadNews(newsNum, condition, keyword);
			NewsDTO nextReadDto=dao.nextReadNews(newsNum, condition, keyword);
			
			req.setAttribute("dto", dto);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			req.setAttribute("query", query);
			req.setAttribute("page", page);
			
			String path="/WEB-INF/views/news/article.jsp";
			forward(req, resp, path);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/news/main.do");
		
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NewsDAO dao=new NewsDAOImpl();
		String cp=req.getContextPath();
		String page=req.getParameter("page");
		String query="page="+page;
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		try {
			String condition=req.getParameter("condition");
			String keyword=req.getParameter("keyword");
			if(condition==null) {
				condition="all";
				keyword="";
			}
			keyword=URLDecoder.decode(keyword, "utf-8");
			if(keyword.length()!=0) {
				query+="&condition="+condition+"&keyword="+
			       URLEncoder.encode(keyword, "utf-8");
			}
			
			int newsNum=Integer.parseInt(req.getParameter("newsNum"));
			NewsDTO dto=dao.readNews(newsNum);
			if(dto==null || ! dto.getUserId().equals(info.getUserId())) {
				resp.sendRedirect(cp+"/news/main.do?"+query);
				return;
			}
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			
			forward(req, resp, "/WEB-INF/views/news/created.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/board/main.do?"+query);
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		String page=req.getParameter("page");
		String query="page="+page;
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/news/main.do");
			return;
		}
		
		NewsDAO dao=new NewsDAOImpl();
		try {
			String condition=req.getParameter("condition");
			String keyword=req.getParameter("keyword");
			if(condition ==null) {
				condition="all";
				keyword="";
			}
			
			keyword=URLDecoder.decode(keyword,"utf-8");
			if(keyword.length()!=0) {
				query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword,"utf-8");
			}
			NewsDTO dto=new NewsDTO();
			dto.setNewsNum(Integer.parseInt(req.getParameter("newsNum")));
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			
			dao.updateNews(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/news/main.do?"+query);
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
