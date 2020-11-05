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
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Jua&display=swap" rel="stylesheet">
<link  rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/newscreated.css">
<title>Document</title>

<script type="text/javascript">
function sendOk() {
    var f = document.newsForm;

	var str = f.subject.value;
    if(!str) {
        alert("제목을 입력하세요. ");
        f.subject.focus();
        return;
    }

	str = f.content.value;
    if(!str) {
        alert("내용을 입력하세요. ");
        f.content.focus();
        return;
    }

		f.action="${pageContext.request.contextPath}/news/${mode}_ok.do";

    f.submit();
}
</script>
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
					<table class="newsTable">
						<tr class="tr1" height="20">
							<td class="td1">제목</td>
							<td ><input type="text" name="subject" value="${dto.subject}"></td>
						</tr>
						<tr height="20">
							<td class="td1">작성자</td>
							<td class="td2" align="left"> <span>${sessionScope.member.userName}</span></td>
							
						</tr>
						<tr height="200">
							<td class="td1">내 용</td>
							<td><textarea name="content">${dto.content}</textarea> </td>
						</tr>
						<tr>
						<td class="td1" height="20">이미지파일</td>
						<td  class="td2" align="left"> <input name="selectFile" type="file" accept="image/*"> </td>
						</tr>
					</table>
				</article>
				<article class="article2">
						<table class="table3">
							<tr>
								<td align="center">
									<button class="btn btn3" type="button" onclick="sendOk();">등록하기</button>
									<button class="btn btn3" type="reset">다시입력</button>
									<button class="btn btn3" type="button" onclick="javascript:location.href='${pageContext.request.contextPath}/news/main.do';">등록취소</button>
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


</body>

</html>