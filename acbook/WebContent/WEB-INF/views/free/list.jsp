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
	href="${pageContext.request.contextPath }/resource/css/layout.css">
<link
	href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Jua&display=swap"
	rel="stylesheet">
<title>Document</title>
<script type="text/javascript">
	function searchList() {
		var f = document.newsForm;

		f.action = "${pageContext.request.contextPath}/free/list.do";
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
					<section class="newsSection">
						<div class="innerNav">
							<h1>자유 게시판</h1>
						</div>
						<article class="article1">
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
								<c:forEach var="dto" items="${list}">
									<tr>
										<td width="50">${dto.freeNum}</td>
										<td style="text-align: left;"><a
											href="${articleUrl}&num=${dto.freeNum}">${dto.subject}
												[${dto.replyCount}]</a></td>
										<td width="100">${dto.userName}</td>
										<td width="100">${dto.created}</td>
										<td width="70">${dto.hitCount}</td>
									</tr>
								</c:forEach>
							</table>

							
						</article>
						<article class="article2">
							<table class="table3">
								<tr>
									<td><button class="btn btn1" type="button"
											onclick="javascript:location.href='${pageContext.request.contextPath}/free/list.do';"></button></td>
									<td align="right"><select name="condition" id="serch">
											<option value="all"
												${condition=="all"?"selected='selected'":""}>제목+내용</option>
											<option value="subject"
												${condition=="subject"?"selected='selected'":""}>제목</option>
											<option value="userName"
												${condition=="userName"?"selected='selected'":""}>작성자</option>
											<option value="content"
												${condition=="content"?"selected='selected'":""}>내용</option>
											<option value="created"
												${condition=="created"?"selected='selected'":""}>등록일</option>
									</select></td>
									<td align="center" width="300"><input class="ipt"
										type="text" name="keyword" value="${keyword}"></td>

									<td>
										<button class="btn btn2" type="button" onclick="searchList();">검색</button>
									</td>

									<td align="right">
										<button class="btn btn3" type="button"
											onclick="javascript:location.href='${pageContext.request.contextPath}/free/created.do';">글올리기</button>
									</td>
								</tr>

							</table>
							<table
								style="width: 100%; margin: 0px auto; border-spacing: 0px;">
								<tr height="35">
									<td align="center">${dataCount==0?"등록된 게시물이 없습니다.":paging}
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