package com.free;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.freereply.FreeReplyDTO;
import com.member.SessionInfo;
import com.util.MyUtil;

@WebServlet("/free/*")
public class FreeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			String path = "/WEB-INF/main.do";
			forward(req, resp, path);
			return;
		}

		if (uri.indexOf("list.do") != -1) {
			listForm(req, resp);
		}

		else if (uri.indexOf("created.do") != -1) {
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
		} else if (uri.indexOf("replyADD.do") != -1) {
			replyAdd(req, resp);
		} else if (uri.indexOf("replyDelete.do") != -1) {
			replyDelete(req, resp);
		}

	}

	protected void listForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FreeDAO dao = new FreeDAOImpl();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");

		int current_page = 1;
		int dataCount;

		if (page != null) {
			current_page = Integer.parseInt(page);
		}

		if (condition == null) {
			condition = "all";
			keyword = "";
		}

		if (req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "utf-8");
		}

		if (keyword.length() == 0) {
			dataCount = dao.dataCount();
		} else {
			dataCount = dao.dataCount(condition, keyword);
		}

		int rows = 10;
		int total_page = util.pageCount(rows, dataCount);
		if (current_page > total_page) {
			current_page = total_page;
		}

		int offset = (current_page - 1) * rows;
		if (offset < 0) {
			offset = 0;
		}

		List<FreeDTO> list;
		if (keyword.length() == 0) {
			list = dao.listFree(offset, rows);
		} else {
			list = dao.listFree(offset, rows, condition, keyword);
		}

		String query = "";
		if (keyword.length() != 0) {
			query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}

		String listUrl = cp + "/free/list.do";
		String articleUrl = cp + "/free/article.do?page=" + current_page;
		if (query.length() != 0) {
			listUrl += "?" + query;
			articleUrl += "&" + query;
		}
		String paging = util.paging(current_page, total_page, listUrl);

		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("page", current_page);
		req.setAttribute("paging", paging);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);

		String path = "/WEB-INF/views/free/list.jsp";
		forward(req, resp, path);
	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");
		String path = "/WEB-INF/views/free/created.jsp";
		forward(req, resp, path);
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		FreeDAO dao = new FreeDAOImpl();
		FreeDTO dto = new FreeDTO();
		String cp = req.getContextPath();

		try {
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");

			dto.setUserId(info.getUserId());
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));

			dao.insertFree(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/free/list.do");
	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FreeDAO dao = new FreeDAOImpl();
		String cp = req.getContextPath();

		try {
			int page = Integer.parseInt(req.getParameter("page"));
			int num = Integer.parseInt(req.getParameter("num"));
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			String query = "page=" + page;

			if (condition == null) {
				condition = "All";
				keyword = "";
			}

			FreeDTO dto = new FreeDTO();
			FreeDTO dto_pre = dao.preReadFree(num, condition, keyword);
			FreeDTO dto_next = dao.nextReadFree(num, condition, keyword);

			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");

			dto = dao.readFree(num);

			if (dto == null) {
				resp.sendRedirect(cp + "/free/list.do?page=" + page);
				return;
			}

			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + keyword;
			}
//			덧글
			List<FreeReplyDTO> replyList = dao.replyList(num);

			req.setAttribute("user", info.getUserId());
			req.setAttribute("dto", dto);
			req.setAttribute("dto_pre", dto_pre);
			req.setAttribute("dto_next", dto_next);
			req.setAttribute("page", page);
			req.setAttribute("num", num);
			req.setAttribute("query", query);
			req.setAttribute("replyList", replyList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String path = "/WEB-INF/views/free/article.jsp";
		forward(req, resp, path);
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FreeDAO dao = new FreeDAOImpl();
		FreeDTO dto = new FreeDTO();
		int page = Integer.parseInt(req.getParameter("page"));
		int boastNum = Integer.parseInt(req.getParameter("freeNum"));

		dto = dao.readFree(boastNum);

		req.setAttribute("mode", "update");
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("boastNum", boastNum);

		String path = "/WEB-INF/views/free/created.jsp";
		forward(req, resp, path);
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FreeDAO dao = new FreeDAOImpl();
		FreeDTO dto = new FreeDTO();
		String cp = req.getContextPath();
		int page = 1;

		try {
			page = Integer.parseInt(req.getParameter("page"));
			dto.setFreeNum(Integer.parseInt(req.getParameter("boastNum")));
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));

			dao.updateFree(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/free/list.do?page=" + page);
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FreeDAO dao = new FreeDAOImpl();
		String cp = req.getContextPath();

		try {
			int freeNum = Integer.parseInt(req.getParameter("freeNum"));

			dao.deleteFree(freeNum);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/free/list.do");
	}

	protected void replyAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FreeDAO dao = new FreeDAOImpl();
		FreeReplyDTO dto = new FreeReplyDTO();
		String cp = req.getContextPath();
		int page = 0;
		int freeNum = 0;

		try {
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			page = Integer.parseInt(req.getParameter("page"));
			freeNum = Integer.parseInt(req.getParameter("freeNum"));

			dto.setUserId(info.getUserId());
			dto.setFreeNum(freeNum);
			dto.setContent(req.getParameter("content"));

			dao.insertReply(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/free/article.do?page=" + page + "&num=" + freeNum);
	}

	protected void replyDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FreeDAO dao = new FreeDAOImpl();
		String cp = req.getContextPath();
		int page = 0;
		int freeNum = 0;

		try {
			page = Integer.parseInt(req.getParameter("page"));
			freeNum = Integer.parseInt(req.getParameter("freeNum"));
			int replyNum = Integer.parseInt(req.getParameter("replyNum"));
			dao.deleteReply(replyNum);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/free/article.do?page=" + page + "&num=" + freeNum);
	}

}
