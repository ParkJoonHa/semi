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
	function searchList() {
		var f = document.searchForm;
		f.submit();
	}

	<c:if test="${sessionScope.member.userId=='admin'}">
	$(function() {
		$("#chkAll").click(function() {
			if ($(this).is(":checked")) {
				$("input[name=nums]").prop("checked", true);
			} else {
				$("input[name=nums]").prop("checked", false);
			}
		});

		$("#btnDeleteList")
				.click(
						function() {
							var cnt = $("input[name=nums]:checked").length;
							if (cnt == 0) {
								alert("삭제할 게시물을 먼저 선택하세요.");
								return false;
							}

							var filename, input;
							$("input[name=nums]:checked")
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
						          	${dataCount}개(${page}/${total_page} 페이지)
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
								<c:forEach var="dto" items="${list}">
									<tr>
										<td width="50"><c:if
												test="${sessionScope.member.userId=='admin'}">
												<input type="checkbox" name="nums" value="${dto.noticeNum}"
													style="margin-top: 3px;"
													data-filename="${dto.saveFilename}">
											</c:if></td>
										<td width="50">${dto.noticeNum}</td>
										<!-- 이후에 listNum으로 확인 후 고치기 -->
										<td><a href="${articleUrl}&noticeNum=${dto.noticeNum}">${dto.subject}</a></td>
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
				<article class="article2">

					<form name="searchForm"
						action="${pageContext.request.contextPath}/notice/list.do"
						method="post">
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
									type="text" name=""></td>
								<td>
									<button class="btn btn2" type="button" onclick="searchList()">검색</button>
								</td>
								</form>
								<td align="right">
									<button class="btn btn3" type="button"
										onclick="javascript:location.href='${pageContext.request.contextPath}/notice/created.do';">글올리기</button>
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