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
	href="${pageContext.request.contextPath}/resource/jquery/css/smoothness/jquery-ui.min.css"
	type="text/css">

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/util.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/jquery/js/jquery.min.js"></script>

<script type="text/javascript">
	function searchList() {
		var f = document.searchForm;
		f.submit();
	}

	function listBoard() {
		var f = document.noticeListForm;
		f.page.value = "1";
		f.action = "${pageContext.request.contextPath}/notice/list.do";
		f.submit();
	}

	<c:if test="${sessionScope.member.userId=='admin'}">
	$(function() {
		$("#chkAll").click(function() {
			if ($(this).is(":checked")) {
				$("input[name=noticeNums]").prop("checked", true);
			} else {
				$("input[name=noticeNums]").prop("checked", false);
			}
		});

		$("#btnDeleteList")
				.click(
						function() {
							var cnt = $("input[name=noticeNums]:checked").length;
							if (cnt == 0) {
								alert("삭제할 게시물을 먼저 선택하세요.");
								return false;
							}

							var filename, input;
							$("input[name=noticeNums]:checked")
									.each(
											function(index) {
												filename = $(this).attr(
														"data-filename");
												if (filename != "") {
													input = "<input type='hidden' name='filenames' value='"+filename+"'>";
													$(
															"form[name=noticeListForm]")
															.append(input);
												}
											});

							if (confirm("선택한 게시물을 삭제 하시겠습니까 ?")) {
								var f = document.noticeListForm;
								f.action = "${pageContext.request.contextPath}/notice/deleteList.do";
								f.submit();
							}

						});
	});
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
				<form name="noticeListForm" method="post">
					<article class="article1">
						<!-- 여기가 게시글 올리는곳  : 테스 -->
						<div>
							<table
								style="width: 100%; margin: 20px auto 0px; border-spacing: 0px;">
								<tr height="35">
									<td align="left" width="50%"><c:if
											test="${sessionScope.member.userId=='admin'}">
											<button type="button" class="btn" id="btnDeleteList">삭제</button>
										</c:if> <c:if test="${sessionScope.member.userId!='admin'}">
						          	전체게시글 ${dataCount}개(${page}/${total_page} 페이지)
						          </c:if></td>
									<td align="right"><c:if test="${dataCount!=0 }">
											<select name="rows" class="selectField"
												onchange="listBoard();">
												<option value="5" ${rows==5 ? "selected='selected' ":""}>5개씩
													출력</option>
												<option value="10" ${rows==10 ? "selected='selected' ":""}>10개씩
													출력</option>
												<option value="20" ${rows==20 ? "selected='selected' ":""}>20개씩
													출력</option>
												<option value="30" ${rows==30 ? "selected='selected' ":""}>30개씩
													출력</option>
												<option value="50" ${rows==50 ? "selected='selected' ":""}>50개씩
													출력</option>
											</select>
										</c:if> <input type="hidden" name="page" value="${page}"> <input
										type="hidden" name="condition" value="${condition}"> <input
										type="hidden" name="keyword" value="${keyword}"></td>
								</tr>
							</table>

							<table class="table1">
								<tr>
									<c:if test="${sessionScope.member.userId=='admin'}">
										<td width="50" style="color: #787878;"><input
											type="checkbox" name="chkAll" id="chkAll"
											style="margin-top: 3px;"></td>
									</c:if>
									<c:if test="${sessionScope.member.userId!='admin'}">
										<td width="50"></td>
									</c:if>
									<td width="50">번호</td>
									<td>제 &nbsp;목</td>
									<td width="100">작성자</td>
									<td width="100">작성일</td>
									<td width="70">조회수</td>
								</tr>
							</table>


							<table class="table2">
								<c:forEach var="dto" items="${listNotice}">
									<tr align="center" bgcolor="#ffffff" height="35"
										style="border-bottom: 1px solid #cccccc;">
										<c:if test="${sessionScope.member.userId=='admin'}">
											<td><input type="checkbox" name="noticeNums"
												value="${dto.noticeNum}" style="margin-top: 3px;"></td>
										</c:if>
										<td><span
											style="display: inline-block; padding: 1px 3px; background: #ED4C00; color: #FFFFFF;">공지</span>
										</td>
										<td align="left" style="padding-left: 10px;"><a
											href="${articleUrl}&noticeNum=${dto.noticeNum}">${dto.subject}</a></td>
										<td>${dto.userName}</td>
										<td>${dto.created}</td>
										<td>${dto.hitCount}</td>
									</tr>
								</c:forEach>
								<c:forEach var="dto" items="${list}">
									<tr>
										<td width="50"><c:if
												test="${sessionScope.member.userId=='admin'}">
												<input type="checkbox" name="noticeNums"
													value="${dto.noticeNum}" style="margin-top: 3px;"
													>
											</c:if></td>
										<td width="50">${dto.listNum}</td>
										<td><a href="${articleUrl}&noticeNum=${dto.noticeNum}">${dto.subject}</a>
										<c:if test="${dto.gap<1}"><img src="${pageContext.request.contextPath}/resource/images/new.gif"></c:if>
										</td>
										<td width="100">${dto.userName}</td>
										<td width="100">${dto.created}</td>
										<td width="70">${dto.hitCount}</td>
									</tr>
								</c:forEach>

							</table>
							<table
								style="width: 100%; margin: 0px auto; border-spacing: 0px;">
								<tr height="35">
									<td align="center">${dataCount!=0?paging:"등록된 게시물이 없습니다."}
									</td>
								</tr>
							</table>
						</div>
					</article>
				</form>
				<form name="searchForm"
					action="${pageContext.request.contextPath}/notice/list.do"
					method="post">
					<article class="article2">

						<table class="table3">
							<tr>
								<td><button class="btn btn1">새로고침</button></td>
								<td align="right"><select name="condition" id="serch">
										<option value="subject"
											${condition=="subject"?"selected='selected'":"" }>제목</option>
										<option value="userName"
											${condition=="userName"?"selected='selected'":"" }>작성자</option>
										<option value="content"
											${condition=="content"?"selected='selected'":"" }>내용</option>
										<option value="created"
											${condition=="created"?"selected='selected'":"" }>등록일</option>
								</select></td>
								<td align="center" width="300"><input class="ipt"
									type="text" name="keyword"></td>
								<td><input type="hidden" name="rows" value="${rows}">
									<button class="btn btn2" type="button" onclick="searchList()">검색</button>
								</td>
								<td align="right">
									<button class="btn btn3" type="button"
										onclick="javascript:location.href='${pageContext.request.contextPath}/notice/created.do?rows=${rows}';">글올리기</button>
								</td>
							</tr>
						</table>
					</article>
				</form>
			</section>
		</main>

		<footer>
			<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		</footer>
	</div>


</body>

</html>