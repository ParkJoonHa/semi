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

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/newscreated.css">
<link
	href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Jua&display=swap"
	rel="stylesheet">
<title>Document</title>
<script type="text/javascript">
	function sendOk() {
		var f = document.qnaForm;

		var str = f.q_subject.value;
		if (!str) {
			alert("제목을 입력하세요. ");
			f.q_subject.focus();
			return;
		}

		str = f.q_content.value;
		if (!str) {
			alert("내용을 입력하세요. ");
			f.q_content.focus();
			return;
		}

		f.action = "${pageContext.request.contextPath}/qna/${mode}_ok.do";

		f.submit();
	}
</script>

</head>

<body>

	<div id="mainBody">
		<div id="mainBody2">
			<header class="header-font">
				<jsp:include page="/WEB-INF/views/layout/header.jsp" />
			</header>

			<main>
				<aside>
					<jsp:include page="/WEB-INF/views/layout/aside.jsp" />
				</aside>

				<form name="newsForm" method="post">
					<section class="createdSection">
						<div class="innerNav">
							<h1>QNA 등록</h1>
						</div>

						<article class="article1">
							<table class="newsTable">
								<tr class="tr1" height="20">
									<td class="td1">제목</td>
									<td><input type="text" name="q_subject"
										class="createdInput" value="${dto.q_subject}"></td>
								</tr>

								<tr height="20">
									<td class="td1">작성자</td>
									<td class="td2" align="left"><span>${sessionScope.member.userName}</span></td>
								</tr>

								<tr height="200">
									<td class="td1">내용</td>
									<td><textarea name="q_content">${dto.q_content}</textarea></td>
								</tr>
							</table>
						</article>
						<article class="article2">
							<table class="table3">
								<c:if test="${mode=='update'}">
									<input type="hidden" name="qnaNum" value="${dto.qnaNum}">
									<input type="hidden" name="page" value="${page}">
									<input type="hidden" name="userId" value="${dto.userId}">
								</c:if>
								<tr>
									<td align="center">
										<button type="button" class="btn btn3" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
										<button type="reset" class="btn btn3">다시입력</button>
										<button type="button" class="btn btn3"
											onclick="javascript:location.href='${pageContext.request.contextPath}/qna/list.do';">${mode=='update'?'수정취소':'등록취소'}</button>
									</td>
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
	</div>

</body>

</html>