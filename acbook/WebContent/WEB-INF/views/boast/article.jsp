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
	function deleteBoard(num) {
		var user = "${user}";
		
		if(user != "admin") {
			if(user != "${dto.userId}") {
				alert("삭제할 권한이 없습니다.");
				
				return;
			}
		}
		
		location.href = "${pageContext.request.contextPath}/boast/delete.do?num=" + num;
	}
	
	function replyDelete(replyNum, userId) {
		var user = "${sessionScope.member.userId}";
		
		if(user != "admin") {
			if(user != userId) {
				alert("삭제할 권한이 없습니다.");
				
				return;
			}
		}
		
		location.href = "${pageContext.request.contextPath}/boast/replyDelete.do?${query}&boastNum=${dto.boastNum}&replyNum=" + replyNum;
	}
	
	function replyADD(boastNum) {
		var f = document.replyForm;
		
		var str = f.content.value;
		if(!str) {
			alert("내용을 입력하세요. ");
	        f.content.focus();
	        return;
		}
		
		f.action = "${pageContext.request.contextPath}/boast/replyADD.do?boastNum=" + boastNum + "&page=" + "${page}";
		f.submit();
	}
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
					<h1>자랑 게시판</h1>
				</div>
				<article class="article1">
					<!-- 여기가 게시글 올리는곳 -->
					<div style="overflow: scroll; width:990px; height:540px;">
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			<tr height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="center">
				 제목 : ${dto.subject}
			    </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td width="50%" align="left" style="padding-left: 5px;">
			       작성자 : ${dto.userName}
			    </td>
			    <td width="50%" align="right" style="padding-right: 5px;">
			        ${dto.created} | 조회 ${dto.hitCount}
			    </td>
			</tr>
			
			<tr style="border-bottom: 1px solid #cccccc;">
			  <td colspan="2" align="left" style="padding: 10px 5px;" valign="top" height="200">
			  	  <c:forEach var="img" items="${imgList}">
			  	  	<img src="${pageContext.request.contextPath}/uploads/boast/${img.saveFileName}"/><br>
			  	  </c:forEach>
			      ${dto.content}
			   </td>
			</tr>
			<tr>
			  <td colspan="2" align="center">
			  	  <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/boast/like.do?${query}&boastNum=${dto.boastNum}';">추천 | ${dto.likeCount}개</button>
			   </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="left" style="padding-left: 5px;">
			       이전글 :
				<c:if test="${not empty dto_pre}">
					<a href="${pageContext.request.contextPath}/boast/article.do?${query}&num=${dto_pre.boastNum}">${dto_pre.subject}</a>
				</c:if>
				<c:if test="${empty dto_pre}">
       				이전글이 존재하지 않습니다.
    			</c:if>
			    </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="left" style="padding-left: 5px;">
			       다음글 :
				<c:if test="${not empty dto_next}">
       				<a href="${pageContext.request.contextPath}/boast/article.do?${query}&num=${dto_next.boastNum}">${dto_next.subject}</a>
    			</c:if>
    			<c:if test="${empty dto_next}">
       				다음글이 존재하지 않습니다.
    			</c:if>
			    </td>
			</tr>
			<tr height="45">
			    <td>
			    <c:if test="${sessionScope.member.userId == dto.userId}">
			          <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/boast/update.do?page=${page}&boastNum=${dto.boastNum}';">수정</button>
			    </c:if>
			          <button type="button" class="btn" onclick="deleteBoard('${dto.boastNum}');">삭제</button>
			    </td>
			
			    <td align="right">
			        <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/boast/list.do?${query}';">리스트</button>
			    </td>
			</tr>
			</table>
			
			<hr>
			
			
			<form name="replyForm" method="post">
     <table style="margin-top: 10px;">
     
     <tr style="border-bottom: 1px solid #ccc;">
         <td valign="top" style="padding:5px 0px 5px 10px;"> 
           <textarea name="content" cols="72" class="boxTA" style="width:97%; height: 70px;"></textarea>
         </td>
         <td>
         	<button type="button" class="btn" onclick="replyADD('${dto.boastNum}');">등록</button>
         </td>
     </tr>
     </table>
   		</form>
			
			<c:forEach var="dto" items="${replyList}">
			<table style="margin-top: 15px; table-layout:fixed; word-break:break-all;">
            <tr height='35' bgcolor='#eeeeee'>
                <td width='50%' style='padding-left: 5px; border:1px solid #ccc; border-right:none;'>
                      <span style='font-weight: 600;'>${dto.userName}</span>
                </td>
                <td width='50%' align='right' style='padding-right: 5px; border:1px solid #ccc; border-left:none;'>
                      ${dto.created} | <a href="javascript:replyDelete('${dto.replyNum}', '${dto.userId}')">삭제</a>
                </td>
             </tr>

             <tr height='50'>
                 <td colspan='2' style='padding: 5px;' valign='top'>${dto.content}</td>
             </tr>

    		</table>
			</c:forEach>
    		
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