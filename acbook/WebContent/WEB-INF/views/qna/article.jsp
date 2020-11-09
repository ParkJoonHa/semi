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
<link
	href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Jua&display=swap"
	rel="stylesheet">
<title>Document</title>
</head>
<script type="text/javascript">

</script>

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
					<h1>QNA</h1>
				</div>
				<article class="article1">
					<!-- 여기가 게시글 올리는곳 -->
					<div style="overflow: scroll; width: 970px; height: 500px;">
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			<tr height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="center">
				 제목 : ${dto.q_subject}
			    </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td width="50%" align="left" style="padding-left: 5px;">
			       작성자 : ${dto.userName}
			    </td>
			    <td width="50%" align="right" style="padding-right: 5px;">
			        ${dto.q_created} | ${dto.status==0?'답변대기':'답변완료'}
			    </td>
			</tr>
			
			<tr style="border-bottom: 1px solid #cccccc;">
			  <td colspan="2" align="left" style="padding: 10px 5px;" valign="top" height="200">
			      ${dto.q_content}
			   </td>
			</tr>
			
			<c:if test="${dto.status == 1}">
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			<tr height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="center">
				 제목 : ${dto.a_subject}
			    </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td width="50%" align="left" style="padding-left: 5px;">
			       작성자 : ${dto.userName}
			    </td>
			    <td width="50%" align="right" style="padding-right: 5px;">
			        ${dto.a_created}
			    </td>
			</tr>
			
			<tr style="border-bottom: 1px solid #cccccc;">
			  <td colspan="2" align="left" style="padding: 10px 5px;" valign="top" height="200">
			      ${dto.a_content}
			   </td>
			</tr>
			
			</table>
			</c:if>
			<tr height="45">
			    <td>
			    <c:if test="${sessionScope.member.userId == 'admin' && dto.status == 0}">
			    	<button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/qna/answer.do?page=${page}&qnaNum=${dto.qnaNum}&status=${dto.status}';">답변작성</button>
			    </c:if>
			    <c:if test="${sessionScope.member.userId == dto.userId && dto.status == 0}">
			          <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/qna/update.do?page=${page}&qnaNum=${dto.qnaNum}&status=${dto.status}';">수정</button>
			    </c:if>
			          <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/qna/delete.do?page=${page}&qnaNum=${dto.qnaNum}&status=${dto.status}';">삭제</button>
			    </td>
			
			    <td align="right">
			        <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/qna/list.do';">리스트</button>
			    </td>
			</tr>
			
			</table>
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