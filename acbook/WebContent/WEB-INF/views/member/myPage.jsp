<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/myPage.css">
<link
	href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Jua&display=swap"
	rel="stylesheet">
<title>Document</title>
</head>

<body>
	<!-- 메인페이지입니다. -->
	<div id="mainBody">

		<header class="header-font">
			<jsp:include page="/WEB-INF/views/layout/header.jsp" />
		</header>
		
		<main>
			<aside>
				<jsp:include page="/WEB-INF/views/layout/aside.jsp" />
			</aside>

			<section class="myPageSection">
				<div class="innerNav">
					<h1>My page</h1>
				</div>
				
				<div style="margin: auto;">
					<div class="myPageArticle" >
						<table class="myPageTable">
							<tr class = "myPageTrB">
								<td >MY 정보</td>
							</tr>
							<tr>
								<td style=""> <!--<img src="${pageContext.request.contextPath}/resources/css/user.png">  --> </td>
							</tr>
							<tr> 
								<td> <a href="javascript:location.href='${pageContext.request.contextPath}/member/pwd.do?mode=update';">개인정보 확인 및 수정</a> </td>
								<td width="50"></td>
								<td> <a href="javascript:location.href='${pageContext.request.contextPath}/member/pwd.do?mode=delete';">회원 탈퇴</a> </td>
							</tr>
						</table>
					</div>

					<div class="myPageArticle">
						<table class="myPageTable">
							<tr class = "myPageTrB">
								<td>MY 질문/답변</td>
							</tr>						
							<tr>
								<td></td>
							</tr>
								<c:forEach var = "qdto" items="${qlist}">
							<tr>
									<td>
										<a href = "${pageContext.request.contextPath}/qna/article.do?page=1&qnaNum=${qdto.qnaNum}&status=${qdto.status}">
											${qdto.status=='1'? '[답변완료] ' : '[답변대기] '}${qdto.q_subject}
										</a>								
									</td>
							</tr>
								</c:forEach>
						</table>		
					</div>
				</div>
			</section>
				
		</main>

		<footer>
			<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		</footer>
	</div>


</body>

</html>