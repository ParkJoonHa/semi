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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/newscreated.css">
<title>Document</title>

<script type="text/javascript">
	function sendNotice() {
		var f = document.noticeForm;

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

		f.action = "${pageContext.request.contextPath}/notice/${mode}_ok.do";

		f.submit();
	}

	<c:if test="${mode=='update'}">
	function deleteFile(fileNum) {
		var url = "${pageContext.request.contextPath}/notice/deleteFile.do?fileNum="
				+ fileNum + "&noticeNum=${dto.noticeNum}" + "&page=${page}";
		location.href = url;
	}
	</c:if>
</script>
</head>

<body>
	<!-- 메인페이지입니다. -->
	<div id="mainBody">
		<div id="mainBody2">

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
							<h1>공지사항</h1>
						</div>
						<article class="article1">
							<!-- 여기가 게시글 올리는곳  : 테스 -->
							<div>
								<table class="newsTable">
									<tr class="tr1" height="30">
										<td class="td1">제목</td>
										<td><input type="text" name="subject"
											value="${dto.subject}"></td>
									</tr>

									<tr class="td1"  height="30">
										<td>공지여부</td>
										<td class="td2" align="left"><input type="checkbox" name="nStatus" value="1">
											공지</td>
									</tr>

									<tr height="20">
										<td class="td1">작성자</td>
										<td class="td2" align="left"> <span> ${sessionScope.member.userName} </span></td>
									</tr>

									<tr>
										<td>내용</td>
										<td><textarea name="content">${dto.content}</textarea></td>
									</tr>

									<tr>
										<td>첨부</td>
										<td><input type="file" name="upload" class="boxTF"
											size="53" style="height: 25px;" multiple="multiple"></td>
									</tr>

									<c:if test="${mode=='update'}">
										<tr align="left" height="40"
											style="border-bottom: 1px solid #cccccc;">
											<td width="100" bgcolor="#dde6e6" style="text-align: center;">첨부된
												파일</td>

											<td style="padding-left: 10px;"><c:forEach var="vo"
													items="${fileList}">
													<span> ${vo.originalFilename} <a
														href="javascript:deleteFile('${vo.fileNum}');"> 삭제 </a>
													</span>
												</c:forEach></td>
										</tr>
									</c:if>
								</table>

								<table
									style="width: 100%; margin: 0px auto; border-spacing: 0px;">
									<tr height="45">
										<td align="center">
											<button type="button" class="btn btn3"
												onclick="sendNotice();">${mode=='update'?'수정완료':'등록하기'}</button>
											<button type="reset" class="btn btn3">다시입력</button>
											<button type="button" class="btn btn3"
												onclick="javascript:location.href='${pageContext.request.contextPath}/notice/list.do?rows=${rows}';">${mode=='update'?'수정취소':'등록취소'}</button>
											<c:if test="${mode=='update'}">
												<input type="hidden" name="noticeNum"
													value="${dto.noticeNum}">
												<input type="hidden" name="page" value="${page}">
												<input type="hidden" name="saveFilename"
													value="${dto.saveFilename}">
												<input type="hidden" name="originalFilename"
													value="${dto.originalFilename}">
											</c:if> <input type="hidden" name="rows" value="${rows}">
										</td>
									</tr>
								</table>
							</div>
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