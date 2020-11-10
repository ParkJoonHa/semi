<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/layout.css">
<link
	href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Jua&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/newscreated.css">
<title>Document</title>
</head>
<body>
	<div id="mainBody">

		<header class="header-font">
			<jsp:include page="/WEB-INF/views/layout/header.jsp" />
		</header>

		<main>
			<aside>
				<jsp:include page="/WEB-INF/views/layout/aside.jsp" />
			</aside>

			<form name="newsForm" method="post" enctype="multipart/form-data">
				<section>
					<div class="innerNav">
						<h3>공유도 공유하는 정보 공유!</h3>
					</div>
					<article class="article1-1">
						<table class="articleTable" border="1">
							<tr>
								<td><span>제목 :</span></td>
								<td><span>${dto.subject}</span></td>
								<td><span>작성자 :</span></td>
								<td><span>${dto.userName}</span></td>
								<td><span>${dto.created}</span></td>
								<td>조회수:<span>${dto.hitCount}</span></td>
							</tr>
							<tr class="articleTableTR">
								<td colspan="6">${dto.content}</td>
							</tr>
							<tr height="35" style="border-bottom: 1px solid #cccccc;">
								<td colspan="2" align="left" style="padding-left: 5px;">
									첨&nbsp;&nbsp;부 : 
									<c:if test="${not empty dto.photoFileName}">
										<a href="${pageContext.request.contextPath}/news/download.do?newsNum=${dto.newsNum}">${dto.originalFilename}</a>
		                            </c:if>
								</td>
							</tr>
						</table>
					</article>
					<article class="articleBody">
						<article class="article3">
							<table border="1">
								<tr>
									<td>
										<button type="button"
											onclick="script:location.href='${pageContext.request.contextPath}/news/update.do?page=${page}&newsNum=${dto.newsNum}';">수정</button>
									</td>
									<td>
										<button type="button"
											onclick="script:location.href='${pageContext.request.contextPath}/news/delete.do?page=${page}&newsNum=${dto.newsNum}';">삭제</button>
									</td>
								</tr>
							</table>
						</article>
						<article class="article4">
							<table class="articleTable2" border="1">
								<tr>
									<td><span>공유글 목록</span></td>
									<td><a
										href="javascript:location.href='${pageContext.request.contextPath}/news/main.do';">전체글
											보기</a></td>
								</tr>
								<tr>
									<td>이전글: <a
										href="${pageContext.request.contextPath}/news/article.do?${query}&newsNum=${preReadDto.newsNum}">${preReadDto.subject}</a></td>
								</tr>
								<tr>
									<td>다음글:<a
										href="${pageContext.request.contextPath}/news/article.do?${qurey}&newsNum=${nextReadDto.newsNum}">
											${nextReadDto.subject}</a></td>
								</tr>
							</table>
						</article>
					</article>
				</section>
			</form>
		</main>

		<footer>
			<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		</footer>
	</div>


</body>


</body>
</html>