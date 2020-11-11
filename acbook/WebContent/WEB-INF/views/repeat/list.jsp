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
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Jua&display=swap" rel="stylesheet">

<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jquery/js/jquery.min.js"></script>
	
	
<script type="text/javascript">
function search() {
	var f = document.searchForm;
	f.submit();
}

$(function(){
	$(".row-subject").click(function(){
		if($(this).next("tr").is(":hidden")) {
			$(".row-subject").next("tr").hide(300);
			$(this).next("tr").show(200);
		} else {
			$(this).next("tr").hide(200);
		}
	});
});

</script>
<title>Document</title>
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
					<h1>자주하는 질문</h1>
				</div>
				<article class="article1">
					<!-- 여기가 게시글 올리는곳 -->
					<div>
						<table class="table1" >
							<tr>
								<td>질문&nbsp;내용</td>
								<td width=100>작성일</td>
							</tr>
						</table>

						<table class="table2" style="border-collapse: collapse;">
						<c:forEach var="dto" items="${list}">
							<tr height="33" class="row-subject">
								<td style="border: 1px solid #ccc; border-right:none; ">${dto.subject}</td>
								<td style="border: 1px solid #ccc; border-left:none; " width="100">${dto.created}</td>
							</tr>
							<tr style="display: none; text-align: left;" height="30">
								<td colspan="2">
								  <div>${dto.content}</div>
								  <c:if test="${sessionScope.member.userId=='admin'}">
								  	 <div style="text-align: right; padding-right: 5px;">
								  	 <a href="${pageContext.request.contextPath}/repeat/update.do?repeatNum=${dto.repeatNum}&page=${page}">수정</a>
								  	 <a href="${pageContext.request.contextPath}/repeat/delete.do?repeatNum=${dto.repeatNum}&page=${page}">삭제</a>
								  	 </div>
								  </c:if>
								  
								</td>
							</tr>
							<tr style="height: 1px; margin: 0;"><td style="height: 1px; font-size: 0;" colspan="2">&nbsp;</td></tr>
						</c:forEach>
						</table>
						
						<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			   				<tr height="35">
								<td align="center">
			        			${dto.dataCount==0?"등록된 게시물이 없습니다.":paging}
								</td>
			  				</tr>
						</table>
					</div>
				</article>
				<article class="article2">
					<form name="searchForm" action="${pageContext.request.contextPath}/repeat/list.do" method="post">
					<table class="table3">
						<tr>
							<td>
							<button class="btn btn1" type="button" onclick="jacascript:location.href='${pageContext.request.contextPath}/repeat/list.do';">새로고침</button>
							</td>
							<td align="right">
							<select name="condition" id="selectCondi">
									<option value="subject" ${condition=="subject"?"selected='selected'":"" }>제목</option>
									<option value="content" ${condition=="content"?"selected='selected'":"" }>내용</option>
									<option value="created" ${condition=="created"?"selected='selected'":"" }>등록일</option>
							</select>
							</td>
							<td align="center" width="300">
							<input class="ipt" type="text" name="keyword">
							</td>
							<td>
								<button class="btn btn2" type="button" onclick="search()">검색</button>
							</td>
							<c:if test="${sessionScope.member.userId=='admin' || sessionScope.member.userId=='master'}">
							<td align="right">
								<button class="btn btn3" type="button" onclick="javascript:location.href='${pageContext.request.contextPath}/repeat/created.do';">글올리기</button>
							</td>
							</c:if>
						</tr>
					</table>
					</form>
				</article>
			</section>
		</main>

		<footer>
			<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		</footer>
	</div>
	
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jquery/js/jquery-ui.min.js"></script>


</body>

</html>