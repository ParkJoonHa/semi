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
					<article class="article1">
						<table border="1">
							<tr>
								<td><span>제목</span></td>
								<td><span>${dto.subject}</span></td>
								<td><span>작성자</span></td>
								<td><span>${dto.userName}</span></td>
								<td><span>${dto.created}</span></td>
								<td><span>${dto.hitCount}</span></td>
							</tr>
							<tr>
								<td>${dto.content}</td>
							</tr>
						</table>
					</article>
					<article class="article2">
						<table border="1">
							<tr>
								<td>
									<button type="button" onclick="script:location.href='${pageContext.request.contextPath}/news/update.do?page=${page}&newsNum=${dto.newsNum}';">수정</button>
								</td>
								<td>
									<button>삭제</button>
								</td>
							</tr>
						</table>
					</article>
					<article class="article2">
						<table border="1">
							<tr>
								<td><span>공유글 목록</span></td>
								<td><a href="#">전체글 보기</a></td>
							</tr>
							<tr>
								<td><a href="#"> 이전게시</a></td>
							</tr>
							<tr>
								<td><a href="#"> 이전게시</a></td>
							</tr>
						</table>
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