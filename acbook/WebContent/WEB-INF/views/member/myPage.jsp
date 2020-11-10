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
					<h1>Mypage</h1>
				</div>
				
				<div>
					<div>
						<table>
							<tr>
								<th>MY 정보</th>
							</tr>
							<tr>
								<td>이미지 넣을 곳</td>
							</tr>
							<tr>
								<td> <a href="">개인정보 확인/수정</a> </td>
							</tr>
						</table>
					</div>
					<div>
						<table>
							<tr>
								<th>MY 소비</th>
							</tr>						
							<tr>
								<td>이미지 넣을 곳</td>
							</tr>
							<tr>
								<td> <a href="">이달의 소비</a> </td>
							</tr>
						</table>
					</div>
					<div>
						<table>
							<tr>
								<th>MY 활동</th>
							</tr>						
							<tr>
								<td>이미지 넣을 곳</td>
							</tr>
							<tr>
								<td> <a href="">더 보기</a> </td>
							</tr>
						</table>		
					</div>
					<div>
						<table>
							<tr>
								<th>MY 질문/답변</th>
							</tr>						
							<tr>
								<td>이미지 넣을 곳</td>
							</tr>
							<tr>
								<td> <a href="">이달의 소비</a> </td>
							</tr>
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