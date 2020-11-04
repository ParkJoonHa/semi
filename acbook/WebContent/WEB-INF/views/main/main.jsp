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

			<section>
				<div class="innerNav">
					<h1>자랑 게시판</h1>
				</div>
				<article class="article1">
					<!-- 여기가 게시글 올리는곳  : 테스 -->
					<div>
						<table class="table1">
							<tr>
								<td width="50">번호</td>
								<td>제 &nbsp;목</td>
								<td width="100">작성자</td>
								<td width="100">작성일</td>
								<td width="70">조회수</td>
							</tr>
						</table>

						<table class="table2">
							<tr>
								<td width="50">1</td>
								<td><a href="">난중 가계부</a></td>
								<td width="100">이순신</td>
								<td width="100">1866-04-25</td>
								<td width="70">1</td>
							</tr>
						</table>
					</div>
				</article>
				<article class="article2">
					<table class="table3">
						<tr>
							<td><button class="btn btn1">새로고침</button></td>
							<td align="right"><select name="" id="serch">
									<option value="#">검색</option>
									<option value="">작성자 검색</option>
									<option value="">제목으로 검색</option>
							</select></td>
							<td align="center" width="300"><input class="ipt"
								type="text" name=""></td>
							<td>
								<button class="btn btn2" type="button">검색</button>
							</td>
							<td align="right">
								<button class="btn btn3" type="button">글올리기</button>
							</td>
						</tr>
					</table>
				</article>
			</section>
		</main>

		<footer>
			<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		</footer>
	</div>


</body>

</html>