package com.member;

import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qna.QnaDAO;
import com.qna.QnaDAOImpl;
import com.qna.QnaDTO;

@WebServlet("/member/*")
public class MemberServlet extends HttpServlet {
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

		if (uri.indexOf("login.do") != -1) {
			loginForm(req, resp);

		} else if (uri.indexOf("login_ok.do") != -1) {
			loginSubmit(req, resp);

		} else if (uri.indexOf("logout.do") != -1) {
			logout(req, resp);

		} else if (uri.indexOf("member.do") != -1) {
			memberForm(req, resp);

		} else if (uri.indexOf("member_ok.do") != -1) {
			memberSubmit(req, resp);

		} else if (uri.indexOf("myPage.do") != -1) {
			myPageForm(req, resp);

		} else if (uri.indexOf("pwd.do") != -1) {
			pwdForm(req, resp);

		} else if (uri.indexOf("pwd_ok.do") != -1) {
			pwdSubmit(req, resp);

		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);

		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);

		} else if (uri.indexOf("delete.do") != -1) {
			deleteForm(req, resp);

		} else if (uri.indexOf("userIdCheck.do") != -1) {
			userIdCheck(req, resp);
		}
	}

	protected void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}

	protected void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao = new MemberDAOImpl();
		String cp = req.getContextPath();

		try {
			String userId = req.getParameter("userId");
			String userPwd = req.getParameter("userPwd");

			MemberDTO dto = dao.readMember(userId);

			if (dto != null) { // 일반, 운영자, 임시정지 회원이 로그인 성공한 경우
				
				if (dto.getUserPwd().equals(userPwd) && dto.getStatus()!=3) {
					HttpSession session = req.getSession();

					SessionInfo info = new SessionInfo();
					info.setUserId(dto.getUserId());
					info.setUserName(dto.getUserName());
					info.setStatus(dto.getStatus());

					session.setAttribute("member", info);
					resp.sendRedirect(cp);
					return;
					
				} else if(dto.getStatus()==3){
					req.setAttribute("message", "영구정지된 회원입니다. 자세한 사항은 관리자에게 문의바랍니다.");
					forward(req, resp, "/WEB-INF/views/member/login.jsp");
					return;					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 로그인을 실패한 경우
		req.setAttribute("message", "아이디 또는 패스워드가 일치하지 않습니다.");
		forward(req, resp, "/WEB-INF/views/member/login.jsp");
	}

	protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		session.invalidate();

		String cp = req.getContextPath();
		resp.sendRedirect(cp);
	}

	protected void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입 폼
		req.setAttribute("mode", "member");
		req.setAttribute("title", "회원 가입");
		String path = "/WEB-INF/views/member/member.jsp";
		forward(req, resp, path);
	}

	protected void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입 처리
		MemberDAO dao = new MemberDAOImpl();
		MemberDTO dto = new MemberDTO();
		String cp = req.getContextPath();

		try {
			dto.setUserId(req.getParameter("userId"));
			dto.setUserPwd(req.getParameter("userPwd"));
			dto.setUserName(req.getParameter("userName"));
			String birth = req.getParameter("birth").replaceAll("(\\.|\\-|\\/)", "");
			dto.setBirth(birth);
			dto.setEmail(req.getParameter("email1") + "@" + req.getParameter("email2"));
			dto.setTel(req.getParameter("tel1") + "-" + req.getParameter("tel2") + "-" + req.getParameter("tel3"));
			dto.setZip_code(req.getParameter("zip"));
			dto.setAddr1(req.getParameter("addr1"));
			dto.setAddr2(req.getParameter("addr2"));

			dao.insertMember(dto);

			resp.sendRedirect(cp); // index.jsp -> main.do
			return;

		} catch (SQLIntegrityConstraintViolationException e) {
			req.setAttribute("message", "아이디 중복 등의 무결성 제약 조건 위반입니다.");
		} catch (SQLDataException e) {
			req.setAttribute("message", "날짜 형식 등이 잘못 되었습니다.");
		} catch (SQLException e) {
			req.setAttribute("message", "데이터 추가에 실패했습니다.");
		}
		resp.sendRedirect(cp + "/member/member.do");
	}

	protected void myPageForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 내 정보 페이지
		String path = "/WEB-INF/views/member/myPage.jsp";
		QnaDAO qdao = new QnaDAOImpl();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		String userId = info.getUserId();
		
		List<QnaDTO> qlist = qdao.listQna(0, 3, "q.userId", userId);
		req.setAttribute("qlist", qlist);
		
		forward(req, resp, path);
	}

	protected void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정, 탈퇴 등에서 패스워드 입력 폼
		String cp = req.getContextPath();
		String mode = req.getParameter("mode");

		if (mode.equals("update")) {
			req.setAttribute("mode", "update");

		} else if (mode.equals("delete")) {
			req.setAttribute("mode", "delete");

		} else {
			resp.sendRedirect(cp);
			return;
		}

		String path = "/WEB-INF/views/member/pwd.jsp";
		forward(req, resp, path);
	}

	protected void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 패스워드 검사
		MemberDAO dao = new MemberDAOImpl();
		String cp = req.getContextPath();

		try {
			String userId = req.getParameter("userID");
			String userPwd = req.getParameter("userPwd");

			MemberDTO dto = dao.readMember(userId);

			if (dto.getUserPwd() != userPwd) { // 입력한 비밀번호가 일치하지 않는 경우
				req.setAttribute("message", "비밀번호가 일치하지 않습니다.");
				String path = "/WEB-INF/views/member/pwd.jsp";
				forward(req, resp, path);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/member/pwd.jsp");
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원 정보 수정 폼
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		MemberDAO dao = new MemberDAOImpl();
		String cp = req.getContextPath();

		try {
			String userId = info.getUserId();

			MemberDTO dto = dao.readMember(userId);
			if (userId == null) {
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/member/myPage.do");
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원 정보 수정 처리
		MemberDAO dao = new MemberDAOImpl();
		String cp = req.getContextPath();

		MemberDTO dto = new MemberDTO();

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		try {
			dto.setUserId(req.getParameter("userId"));
			dto.setUserPwd(req.getParameter("userPwd"));
			dto.setUserName(req.getParameter("userName"));
			String birth = req.getParameter("birth").replaceAll("(\\.|\\-|\\/)", "");
			dto.setBirth(birth);
			dto.setEmail(req.getParameter("email1") + "@" + req.getParameter("email2"));
			dto.setTel(req.getParameter("tel1") + "-" + req.getParameter("tel2") + "-" + req.getParameter("tel3"));
			dto.setZip_code(req.getParameter("zip"));
			dto.setAddr1(req.getParameter("addr1"));
			dto.setAddr2(req.getParameter("addr2"));

			dao.updateMember(dto);
			// req.setAttribute("dto", dto);
			// forward(req, resp, "/WEB-INF/views/member/member.jsp");

			resp.sendRedirect(cp); // index.jsp -> main.do

			return;

		} catch (SQLIntegrityConstraintViolationException e) {
			req.setAttribute("message", "아이디 중복 등의 무결성 제약 조건 위반입니다.");
		} catch (SQLDataException e) {
			req.setAttribute("message", "날짜 형식 등이 잘못 되었습니다.");
		} catch (SQLException e) {
			req.setAttribute("message", "데이터 추가에 실패했습니다.");
		}
		resp.sendRedirect(cp + "/member/member.do");
	}

	protected void deleteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원 탈퇴
		MemberDAO dao = new MemberDAOImpl();
		String cp = req.getContextPath();

		try {
			String userId = req.getParameter("userId");
			String userPwd = req.getParameter("userPwd");

			MemberDTO dto = dao.readMember(userId);

			if (dto.getUserPwd().equals(userPwd)) {
				dao.deleteMember(userId, userPwd);

				HttpSession session = req.getSession();
				session.invalidate();

				resp.sendRedirect(cp);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		req.setAttribute("message", "패스워드가 일치하지 않습니다.");

		forward(req, resp, "/WEB-INF/views/member/pwd.jsp");

	}

	protected void userIdCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원 아이디 중복 검사
		MemberDAO dao = new MemberDAOImpl();
		String cp = req.getContextPath();
		
		try {
			String idCheckMessage = "";
			String userId = req.getParameter("userId");
			if (dao.readMember(userId)!=null) {
				idCheckMessage = "사용불가. 중복 아이디";
			} else {
				idCheckMessage = "사용가능";	
			}
			req.setAttribute("idCheckMessage", idCheckMessage);
			req.setAttribute("mode", "member");
			req.setAttribute("userId", userId);
			
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/member/member.jsp");
	}
}
