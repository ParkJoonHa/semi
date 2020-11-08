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

<script type="text/javascript">
    function sendNotice() {
        var f = document.noticeForm;

    	var str = f.subject.value;
        if(!str) {
            alert("제목을 입력하세요. ");
            f.subject.focus();
            return;
        }

    	str = f.content.value;
        if(!str) {
            alert("내용을 입력하세요. ");
            f.content.focus();
            return;
        }

   		f.action="${pageContext.request.contextPath}/notice/${mode}_ok.do";

        f.submit();
    }
    
<c:if test="${mode=='update'}">
    function deleteFile(noticeNum) {
  	  var url="${pageContext.request.contextPath}/notice/deleteFile.do?noticeNum="+noticeNum+"&page=${page}&rows=${rows}";
  	  location.href=url;
    }
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
				<article class="article1">
					<!-- 여기가 게시글 올리는곳  : 테스 -->
					<div>
					
						<form name="noticeForm" method="post" enctype="multipart/form-data">
						  <table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
						  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
						      <td width="100" bgcolor="#dde6e6" style="text-align: center;">제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
						      <td style="padding-left:10px;"> 
						        <input type="text" name="subject" maxlength="100" class="boxTF" style="width: 95%;" value="${dto.subject}">
						      </td>
						  </tr>
			
						  <tr align="left" height="40" style="border-bottom: 1px solid #cccccc;"> 
						      <td width="100" bgcolor="#dde6e6" style="text-align: center;">공지여부</td>
						      <td style="padding-left:10px;"> 
						        <input type="checkbox" name="notice" value="1" ${dto.notice==1 ? "checked='checked' ":"" } > 공지
						      </td>
						  </tr>
						
						  <tr align="left" height="40" style="border-bottom: 1px solid #cccccc;"> 
						      <td width="100" bgcolor="#dde6e6" style="text-align: center;">작성자</td>
						      <td style="padding-left:10px;"> 
						            ${sessionScope.member.userName}
						      </td>
						  </tr>
						
						  <tr align="left" style="border-bottom: 1px solid #cccccc;"> 
						      <td width="100" bgcolor="#dde6e6" style="text-align: center; padding-top:5px;" valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
						      <td valign="top" style="padding:5px 0px 5px 10px;"> 
						        <textarea name="content" rows="12" class="boxTA" style="width: 95%;">${dto.content}</textarea>
						      </td>
						  </tr>
						  
						  <tr align="left" height="40" style="border-bottom: 1px solid #cccccc;">
						      <td width="100" bgcolor="#dde6e6" style="text-align: center;">첨&nbsp;&nbsp;&nbsp;&nbsp;부</td>
						      <td style="padding-left:10px;"> 
						           <input type="file" name="upload" class="boxTF" size="53" style="height: 25px;" multiple="multiple">
						       </td>
						  </tr> 
			
						  <c:if test="${mode=='update'}">
							  <tr align="left" height="40" style="border-bottom: 1px solid #cccccc;">
							      <td width="100" bgcolor="#dde6e6" style="text-align: center;">첨부된파일</td>
							      <td style="padding-left:10px;"> 
							         <c:if test="${not empty dto.saveFilename}">
							             ${dto.originalFilename}
							             | <a href="javascript:deleteFile('${dto.noticeNum}');">삭제</a>
							         </c:if>     
							       </td>
							  </tr> 
						  </c:if>
						  </table>
						
						  <table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
						     <tr height="45"> 
						      <td align="center" >
						        <button type="button" class="btn" onclick="sendNotice();">${mode=='update'?'수정완료':'등록하기'}</button>
						        <button type="reset" class="btn">다시입력</button>
						        <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/notice/list.do?rows=${rows}';">${mode=='update'?'수정취소':'등록취소'}</button>
						         <c:if test="${mode=='update'}">
						         	 <input type="hidden" name="noticeNum" value="${dto.noticeNum}">
						        	 <input type="hidden" name="page" value="${page}">
						        	 <input type="hidden" name="fileSize" value="${dto.fileSize}">
						        	 <input type="hidden" name="saveFilename" value="${dto.saveFilename}">
						        	 <input type="hidden" name="originalFilename" value="${dto.originalFilename}">
						        </c:if>
						        <input type="hidden" name="rows" value="${rows}">
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