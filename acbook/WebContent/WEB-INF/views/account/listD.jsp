<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">
<title>Document</title>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/account.css">
<link
	href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Jua&display=swap"
	rel="stylesheet">
	
<script type="text/javascript">
function search() {
	var f=document.searchForm;
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
		
		<form name="searchForm" action="${pageContext.request.contextPath}/account/listD.do" method="post">
			<section>
				<div class="acctheader">					
					<ul class="acctmenu">
					<li>
						<a href="${pageContext.request.contextPath}/account"> </a>
					</li>			
					<li>
						<a href="${pageContext.request.contextPath}/account/addlog.do">기록</a>
					</li>		
					<li>
						<a href="${pageContext.request.contextPath}/account/listM.do">월별 </a>
					</li>							
					<li>
						<a href="${pageContext.request.contextPath}/account/listD.do">일일 </a>
					</li>					
					</ul>

				</div>

				<article class="article1">
					<!-- 여기가 게시글 올리는곳 -->
					<div>
						<table class="table1">
							<tr>																
								<th width="10%">구분</th>
								<th width="20%">날짜</th>
								<th width="15%">품</th>
								<th width="15%">목</th>
								<th width="20%">금액</th>
								<th width="20%">총합계</th>
							</tr>
						</table>
						<table class="table2">
						
						 <c:forEach var="dto" items="${list}">
							<tr onclick="javascript:location.href='${articleUrl}&abNum=${dto.abNum}'" style="cursor: pointer;">					
								<td width="10%">${dto.status == 1?'지출':'수입'}</td>
								<td width="20%">${dto.abDate}</td>
								<td width="15%">${dto.kind1}</td>
								<td width="15%">${dto.kind2}</td>
								<td width="20%">${dto.amount} 원 </td>
								<td width="20%">${dto.result} </td>
							</tr>
								
						</c:forEach>
						</table>
					</div>
				</article>
				<article class="article2">
					<table class="table3">
					
						<tr>
							<td><button class="btn btn1"  onclick="javascript:location.href='${pageContext.request.contextPath}/account/listM.do'"></button></td>
							<td align="right">
							<select name="condition" id="serch">
									<option value="all" ${condition=="all"?"selected='selected'":""}>통합검색</option>
									<option value="abDate" ${condition=="abDate"?"selected='selected'":""}>날 짜</option>
									<option value="content" ${condition=="content"?"selected='selected'":""}>내 용</option>
									
							</select></td>
							<td align="center" width="300">
								<input class="ipt" type="text" name="keyword"></td>
							<td>
								<button class="btn btn2" type="button" onclick="search()"></button>
							</td>
							<td align="right">
								<button class="btn btn3" type="button" onclick="javascript:location.href='${pageContext.request.contextPath}/account/addlog.do'">글등록</button>
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