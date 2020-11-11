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
	function sendOk() {
		var f = document.boastForm;

		var str = f.subject.value;
		if (!str) {
			alert("제목을 입력하세요. ");
			f.subject.focus();
			return;
		}

		str = f.content.value;
		if (!str) {
			alert("내용을 입력하세요. ");
			f.content.focus();
			return;
		}

		f.action = "${pageContext.request.contextPath}/boast/${mode}_ok.do";

		f.submit();
	}
	
	function picture() {
		
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

			<section>
				<div class="innerNav">
					<h1>게시물 등록</h1>
				</div>

				<article class="article1">
					<!-- 여기가 게시글 올리는곳 -->
					<div>
						<form name="boastForm" method="post" enctype="multipart/form-data">
							<table
								style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
								<tr align="left" height="40"
									style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
									<td width="100" bgcolor="#eeeeee" style="text-align: center;">제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
									<td style="padding-left: 10px;"><input type="text"
										name="subject" maxlength="100" class="boxTF"
										style="width: 95%;" value="${dto.subject}"></td>
								</tr>

								<tr align="left" height="40"
									style="border-bottom: 1px solid #cccccc;">
									<td width="100" bgcolor="#eeeeee" style="text-align: center;">작성자</td>
									<td style="padding-left: 10px;">
										${mode=='update'?dto.userName:sessionScope.member.userName}</td>
								</tr>

								<tr align="left" style="border-bottom: 1px solid #cccccc;">
									<td width="100" bgcolor="#eeeeee"
										style="text-align: center; padding-top: 5px;" valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
									<td valign="top" style="padding: 5px 0px 5px 10px;"><textarea
											name="content" rows="12" class="boxTA" style="width: 95%;">${dto.content}</textarea>
									</td>
								</tr>

								<tr align="left" style="border-bottom: 1px solid #cccccc;">
									<td width="100" bgcolor="#eeeeee"
										style="text-align: center; padding-top: 5px;" valign="top">파&nbsp;&nbsp;&nbsp;&nbsp;일</td>
									<td valign="top" style="padding: 5px 0px 5px 10px;">
									<input type="file" name="selectFile" multiple="multiple" onchange="picture();"></td>
								</tr>
							</table>
							
							<div>
								<img name="test">
							</div>

							<table style="width: 100%; border-spacing: 0px;">
								<tr height="45">
									<td align="center"><c:if test="${mode=='update'}">
											<input type="hidden" name="boastNum" value="${boastNum}">
											<input type="hidden" name="page" value="${page}">
										</c:if>
										<button type="button" class="btn" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
										<button type="reset" class="btn">다시입력</button>
										<button type="button" class="btn"
											onclick="javascript:location.href='${pageContext.request.contextPath}/boast/list.do';">${mode=='update'?'수정취소':'등록취소'}</button>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</article>
			</section>
		</main>

		<footer>
			<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		</footer>
	</div>


</body>

</html>