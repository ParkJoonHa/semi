package com.notice;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.member.SessionInfo;
import com.util.MyUploadServlet;

@MultipartConfig
@WebServlet("/notice/*")
public class NoticeServlet extends MyUploadServlet{
	private static final long serialVersionUID = 1L;
	
	private String pathname;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String cp = req.getContextPath();
		String uri = req.getRequestURI();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		if(uri.indexOf("list.do") == -1 && info == null ) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		// 파일 저장 경로(pathname)
		String root = session.getServletContext().getRealPath("/");
		pathname = root+"uploads"+File.separator+"notice";
		
		if (uri.indexOf("list.do")!=-1) {
			list(req, resp);
		} else if (uri.indexOf("created.do")!=-1) {
			createdForm(req, resp);
		} else if (uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);
		} else if (uri.indexOf("article.do")!=-1) {
			article(req, resp);
		} else if (uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("deleteFile.do")!=-1) {
			deleteFile(req, resp);
		} else if (uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		} else if (uri.indexOf("download.do")!=-1) {
			download(req, resp);
		} else if (uri.indexOf("deleteList.do")!=-1) {			
			deleteList(req, resp);
		}		
	}
	
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	private void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		NoticeDAO dao = new NoticeDAOImpl();
		String cp = req.getContextPath();
		String rows = req.getParameter("rows");
		
		if (! info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/notice/list.do?rows="+rows);
			return;
		}
		
		try {
			NoticeDTO dto = new NoticeDTO();
			dto.setUserId(info.getUserId());
			//if(req.getParameter("notice")!=null) { // 공지 선택시 상단에 띄울지
				//dto.setNotice(Integer.parseInt(req.getParameter("notice")));
			//}
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			
			
			/*
			for(Part p : req.getParts()) { 
				String contentType = p.getContentType();

				if(contentType!=null) { // 파일이면
					Map<String, String> map = doFileUpload(p, pathname);
					if(map==null) continue;
					String saveFilename = map.get("saveFilename");
					String originalFilename = map.get("saveFilename");
				} else { 
					// 파일이 아니면(<input t ype="text"... 등)
					continue;
				}
			}
			*/
			
			//Part p = req.getPart("upload"); // 여러개 넘겨줄 파일 객체 이름 ? 아닌가 강사님 지우심
			
			Map<String, String[]>map = doFileUpload(req.getParts(), pathname);
			if (map!=null) {
				dto.setSaveFiles(map.get("saveFilenames"));
				dto.setOriginalFiles(map.get("originalFilenames"));
			}
			
			
			
			dao.insertNotice(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	private void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	private void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	private void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	private void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	private void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	private void deleteList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	
}
