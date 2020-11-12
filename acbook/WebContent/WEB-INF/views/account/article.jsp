<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Document</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/account.css">
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Jua&display=swap"
		rel="stylesheet">
	
<script type="text/javascript">
<c:if test="${sessionScope.member.userId=='admin'}">
function deleteAccount(abNum) {
	if(confirm("게시물을 삭제 하시겠습니까 ?")) {
		var url="${pageContext.request.contextPath}/account/delete.do?abNum="+abNum+"&${query}";
		location.href=url;
	}
}
</c:if>
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

			<section>
				<div class="innerNav">
					<h1>소비로그</h1>
				</div>
				<article class="article1">
					<!-- 여기가 게시글 올리는곳 -->
					<div>
					<table style="width: 80%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
					  <tr align="left" height="70px" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
					      <td width="100" bgcolor="#eeeeee" style="text-align: center;">구&nbsp;&nbsp;&nbsp;&nbsp;분</td>
					      <td style="padding-left:10px;"> 
					        ${dto.status==1?"지출":"수입"}
					      </td>
					  </tr>
		
					  <tr align="left" height="70px" style="border-bottom: 1px solid #cccccc;"> 
					      <td width="100" bgcolor="#eeeeee" style="text-align: center;">날&nbsp;&nbsp;&nbsp;&nbsp;짜</td>
					      <td style="padding-left:10px;"> 
					           ${dto.kind1}   &nbsp;&nbsp;  ${dto.kind2}
					      </td>
					  </tr>
		
					<tr align="left" height="70px" style="border-bottom: 1px solid #cccccc;"> 
					      <td width="100" bgcolor="#eeeeee" style="text-align: center;">아&nbsp;&nbsp;이&nbsp;&nbsp;템</td>
					      <td style="padding-left:10px;"> 
					            ${dto.content}
					      </td>
					  </tr>
		
					<tr align="left" height="70px" style="border-bottom: 1px solid #cccccc;"> 
					      <td width="100" bgcolor="#eeeeee" style="text-align: center;">금&nbsp;&nbsp;&nbsp;&nbsp;액</td>
					      <td style="padding-left:10px;"> 
					            ${dto.amount}
					      </td>
					  </tr>
											
						</table>						
					</div>
				</article>
				
				<article class="article2">
					<table class="table3">
						<tr height="45">
						    <td>
					          <c:if test="${sessionScope.member.userId==dto.userId}">
					              <button type="button" class="btn"  onclick="javascript:location.href='${pageContext.request.contextPath}/account/update.do?page=${page}&abNum=${dto.abNum}';">수정</button>
					       	  </c:if>
					          <button type="button" class="btn" onclick="deleteAccount('${dto.abNum}');">삭제</button>
						    </td>
						
						    <td align="right">
						        <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/account/listD.do?${query}';">리스트</button>
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
</div>

</body>

</html>