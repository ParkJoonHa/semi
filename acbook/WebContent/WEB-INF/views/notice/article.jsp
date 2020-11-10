<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/layout.css">
<link
	href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Jua&display=swap"
	rel="stylesheet">
<title>Document</title>

<script type="text/javascript">

<c:if test="${sessionScope.member.userId=='admin'}">
function deleteNotice(noticeNum) {
	if(confirm("게시물을 삭제 하시겠습니까 ?")) {
		var url="${pageContext.request.contextPath}/notice/delete.do?noticeNum="+noticeNum+"&${query}";
		location.href=url;
	}
}
</c:if>
</script>

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
					<h1>공지사항</h1>
				</div>
				<article class="article1">
				
					<!-- 여기가 게시글 올리는곳  : 테스 -->
					
					<table
						style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
						<tr height="35"
							style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
							<td colspan="2" align="center">${dto.subject}</td>
						</tr>

						<tr height="35" style="border-bottom: 1px solid #cccccc;">
							<td width="50%" align="left" style="padding-left: 5px;">이름 :
								${dto.userName}</td>
							<td width="50%" align="right" style="padding-right: 5px;">
								${dto.created} | 조회 ${dto.hitCount}</td>
						</tr>

						<tr style="border-bottom: 1px solid #cccccc;">
							<td colspan="2" align="left" style="padding: 10px 5px;"
								valign="top" height="200">${dto.content}</td>
						</tr>

						<tr height="35" style="border-bottom: 1px solid #cccccc;">
							<td colspan="2" align="left" style="padding-left: 5px;">
								첨&nbsp;&nbsp;부 : <c:forEach var="vo" items="${fileList}">
									<a
										href="${pageContext.request.contextPath}/notice/download.do?fileNum=${vo.fileNum}">${vo.originalFilename}</a>
								</c:forEach>
							</td>
						</tr>

						<tr height="35" style="border-bottom: 1px solid #cccccc;">
							<td colspan="2" align="left" style="padding-left: 5px;">이전글
								: <c:if test="${not empty preReadDto}">
									<a
										href="${pageContext.request.contextPath}/notice/article.do?${query}&noticeNum=${preReadDto.num}">${preReadDto.subject}</a>
								</c:if>
							</td>
						</tr>

						<tr height="35" style="border-bottom: 1px solid #cccccc;">
							<td colspan="2" align="left" style="padding-left: 5px;">다음글
								: <c:if test="${not empty nextReadDto}">
									<a
										href="${pageContext.request.contextPath}/notice/article.do?${query}&noticeNum=${nextReadDto.num}">${nextReadDto.subject}</a>
								</c:if>
							</td>
						</tr>
					</table>

					<table
						style="width: 100%; margin: 0px auto 20px; border-spacing: 0px;">
						<tr height="45">
							<td width="300" align="left"><c:if
									test="${sessionScope.member.userId==dto.userId}">
									<button type="button" class="btn"
										onclick="javascript:location.href='${pageContext.request.contextPath}/notice/update.do?noticeNum=${dto.noticeNum}&page=${page}&rows=${rows}';">수정</button>
								</c:if> <c:if 
									test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
									<button type="button" class="btn"
										onclick="deleteNotice('${dto.noticeNum}');">삭제</button>
								</c:if></td>

							<td align="right">
								<button type="button" class="btn"
									onclick="javascript:location.href='${pageContext.request.contextPath}/notice/list.do?${query}';">리스트</button>
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