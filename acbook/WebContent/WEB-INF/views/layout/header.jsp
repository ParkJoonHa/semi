<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="header-login">
	<table class="headerTable">
		<tr>
			<c:if test="${empty sessionScope.member}">
				
				<a href="${pageContext.request.contextPath}/member/login.do">로그인</a>
					&nbsp;&nbsp;|&nbsp;&nbsp; 
				<a href="${pageContext.request.contextPath}/member/member.do">회원가입</a>
				
			</c:if>

			<c:if test="${not empty sessionScope.member}">
				<td width="" align="left"><a href="${pageContext.request.contextPath}/member/myPage.do">마이페이지</a></td>
				<td width="" align="right"><span>${sessionScope.member.userName}</span> 님</td>
				<td width="" align="left">&nbsp;|&nbsp;<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a></td>
			</c:if>
		</tr>
	</table>
</div>
<div class="aside-top">
	<div class="aside-logo">
		<a href="${pageContext.request.contextPath}/"> <span
			class="aside-logo-image">내돈이 어디로 가계부</span>
		</a>
	</div>
</div>