package com.boast;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.boastreply.BoastReplyDTO;
import com.member.SessionInfo;
import com.util.MyUtil;

@WebServlet("/boast/*")
@MultipartConfig(
			// ������ �ӽ÷� ������ ���(��������. �⺻�� ""), ������ ��ΰ� ������ ���ε尡 �ȵ�
		fileSizeThreshold = 1024*1024,		// ���ε�� ������ �ӽ÷� ������ ������� �ʰ� �޸𸮿��� ��Ʈ������ �ٷ� ���޵Ǵ� ũ��
		maxFileSize = 1024*1024*5,			// ���ε�� �ϳ��� ���� ũ��. �⺻ �뷮 ���� ����
		maxRequestSize = 1024*1024*10	// �� ��ü �뷮
	)
public class BoastServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String root;
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
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "boast";
		
		if(info==null) {
			String path = "/WEB-INF/views/member/login.jsp";
			forward(req, resp, path);
			return;
		}

		if (uri.indexOf("list.do") != -1) {
			listForm(req, resp);
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
		} else if (uri.indexOf("replyADD.do") != -1) {
			replyAdd(req, resp);
		} else if (uri.indexOf("replyDelete.do") != -1) {
			replyDelete(req, resp);
		} else if (uri.indexOf("like.do") != -1) {
			like(req, resp);
		}
	}

	protected void listForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoastDAO dao = new BoastDAOImpl();
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

		List<BoastDTO> list;
		if (keyword.length() == 0) {
			list = dao.list(offset, rows);
		} else {
			list = dao.list(offset, rows, condition, keyword);
		}

		String query = "";
		if (keyword.length() != 0) {
			query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}

		String listUrl = cp + "/boast/list.do";
		String articleUrl = cp + "/boast/article.do?page=" + current_page;
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

		String path = "/WEB-INF/views/boast/list.jsp";
		forward(req, resp, path);
	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");
		String path = "/WEB-INF/views/boast/created.jsp";
		forward(req, resp, path);
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BoastDAO dao = new BoastDAOImpl();
		BoastDTO dto = new BoastDTO();
		String cp = req.getContextPath();

		try {
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");

			dto.setUserId(info.getUserId());
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if(map != null) {
				dto.setSaveFiles(map.get("saveFilenames"));
				dto.setOriginalFiles(map.get("originalFilenames"));
			}

			dao.insertboast(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/boast/list.do");
	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoastDAO dao = new BoastDAOImpl();
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

			BoastDTO dto = new BoastDTO();
			BoastDTO dto_pre = dao.preReadBoast(num, condition, keyword);
			BoastDTO dto_next = dao.nextReadBoast(num, condition, keyword);

			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");

			dto = dao.readBoast(num);

			if (dto == null) {
				resp.sendRedirect(cp + "/boast/list.do?page=" + page);
				return;
			}

			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + keyword;
			}
//			덧글
			List<BoastReplyDTO> replyList = dao.replyList(num);
			
//			이미지
			List<BoastImgDTO> imgList = dao.readImg(dto.getBoastNum());
			
			req.setAttribute("user", info.getUserId());
			req.setAttribute("dto", dto);
			req.setAttribute("dto_pre", dto_pre);
			req.setAttribute("dto_next", dto_next);
			req.setAttribute("page", page);
			req.setAttribute("num", num);
			req.setAttribute("query", query);
			req.setAttribute("replyList", replyList);
			req.setAttribute("imgList", imgList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String path = "/WEB-INF/views/boast/article.jsp";
		forward(req, resp, path);
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoastDAO dao = new BoastDAOImpl();
		BoastDTO dto = new BoastDTO();
		int page = Integer.parseInt(req.getParameter("page"));
		int boastNum = Integer.parseInt(req.getParameter("boastNum"));
		
		dto = dao.readBoast(boastNum);
		
		req.setAttribute("mode", "update");
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("boastNum", boastNum);
		
		String path = "/WEB-INF/views/boast/created.jsp";
		forward(req, resp, path);
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoastDAO dao = new BoastDAOImpl();
		BoastDTO dto = new BoastDTO();
		String cp = req.getContextPath();
		int page = 1;
		
		try {
			page = Integer.parseInt(req.getParameter("page"));
			dto.setBoastNum(Integer.parseInt(req.getParameter("boastNum")));
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if(map != null) {
				dto.setSaveFiles(map.get("saveFilenames"));
				dto.setOriginalFiles(map.get("originalFilenames"));
			}

			dao.updateboast(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/boast/list.do?page=" + page);
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoastDAO dao = new BoastDAOImpl();
		String cp = req.getContextPath();

		try {
			int num = Integer.parseInt(req.getParameter("num"));

			dao.deleteboast(num);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/boast/list.do");
	}

	protected void replyAdd(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BoastDAO dao = new BoastDAOImpl();
		BoastReplyDTO dto = new BoastReplyDTO();
		String cp = req.getContextPath();
		int page = 0;
		int boastNum = 0;

		try {
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			page = Integer.parseInt(req.getParameter("page"));
			boastNum = Integer.parseInt(req.getParameter("boastNum"));

			dto.setUserId(info.getUserId());
			dto.setBoastNum(boastNum);
			dto.setContent(req.getParameter("content"));

			dao.insertReply(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/boast/article.do?page=" + page + "&num=" + boastNum);
	}
	
	protected void replyDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BoastDAO dao = new BoastDAOImpl();
		String cp = req.getContextPath();
		int page = 0;
		int boastNum = 0;

		try {
			page = Integer.parseInt(req.getParameter("page"));
			boastNum = Integer.parseInt(req.getParameter("boastNum"));
			int replyNum = Integer.parseInt(req.getParameter("replyNum"));
			dao.deleteReply(replyNum);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/boast/article.do?page=" + page + "&num=" + boastNum);
	}
	
	protected void like(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BoastDAO dao = new BoastDAOImpl();
		String cp = req.getContextPath();
		int page = 0;
		int boastNum = 0;

		try {
			page = Integer.parseInt(req.getParameter("page"));
			boastNum = Integer.parseInt(req.getParameter("boastNum"));
			System.out.println(page + ":" + boastNum);
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			String userId = info.getUserId();
			System.out.println(userId);
			
			dao.like(boastNum, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/boast/article.do?page=" + page + "&num=" + boastNum);
	}
	
//	파일업로드
	protected Map<String, String> doFileUpload(Part p, String pathname) throws ServletException, IOException {
		Map<String, String> map = null;
		
		try {
			File f=new File(pathname);
			if(! f.exists()) { // ������ �������� ������
				f.mkdirs();
			}
			
			String originalFilename=getOriginalFilename(p);
			if(originalFilename==null || originalFilename.length()==0) return null;
			
			String fileExt = originalFilename.substring(originalFilename.lastIndexOf("."));
			String saveFilename = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", 
				 Calendar.getInstance());
			saveFilename += System.nanoTime();
			saveFilename += fileExt;
			
			String fullpath = pathname+File.separator+saveFilename;
			p.write(fullpath);
			
			map = new HashMap<>();
			map.put("originalFilename", originalFilename);
			map.put("saveFilename", saveFilename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}

	/**
	 * ���� ���� ���ε�
	 * @param parts				Ŭ���̾�Ʈ�� ������ ������ ��� Part ��ü
	 * @param pathname		������ ������ ������ ��� 
	 * @return						������ ����� ���ϸ�, Ŭ���̾�Ʈ�� �ø� ���ϸ�
	 */
	protected Map<String, String[]> doFileUpload(Collection<Part> parts, String pathname) throws ServletException, IOException {
		Map<String, String[]> map = null;
		try {
			File f=new File(pathname);
			if(! f.exists()) { // ������ �������� ������
				f.mkdirs();
			}
			
			String original, save, ext;
			List<String> listOriginal=new ArrayList<String>();
			List<String> listSave=new ArrayList<String>();
			
			for(Part p : parts) {
				String contentType = p.getContentType();
/*				
			      if(contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
			         // multipart
			      }				
*/
				// contentType �� null �� ���� ������ �ƴ� ����̴�.(<input type="text"... ��)
				if(contentType != null) { // �����̸�
					original = getOriginalFilename(p);
					if(original == null || original.length() == 0 ) continue;
					
					ext = original.substring(original.lastIndexOf("."));
					save = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", 
						 Calendar.getInstance());
					save += System.nanoTime();
					save += ext;
					
					String fullpath = pathname+File.separator+save;
					p.write(fullpath);
					
					listOriginal.add(original);
					listSave.add(save);
					// Long size = p.getSize()); // ���� ũ��
				}
			}		
			
			if(listOriginal.size() != 0) {
				String [] originals = listOriginal.toArray(new String[listOriginal.size()]);
				String [] saves = listSave.toArray(new String[listSave.size()]);
				
				map = new HashMap<>();
				
				map.put("originalFilenames", originals);
				map.put("saveFilenames", saves);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	private String getOriginalFilename(Part p) {
		try {
			for(String s: p.getHeader("content-disposition").split(";")) {
				if(s.trim().startsWith("filename")) {
					return s.substring(s.indexOf("=")+1).trim().replace("\"","");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
