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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.2/css/all.min.css"/>	
<style type="text/css">
.titleDate {
	display: inline-block;
	font-weight: 600; 
	font-size: 19px;
	font-family: 나눔고딕, "맑은 고딕", 돋움, sans-serif;
	padding:2px 4px 4px;
	text-align:center;
	position: relative;
	top: 4px;
}

.btnDate {
	display: inline-block;
	font-size: 10px;
	font-family: 나눔고딕, "맑은 고딕", 돋움, sans-serif;
	color:#333333;
	padding:3px 5px 5px;
	border:1px solid #cccccc;
    background-color:#fff;
    text-align:center;
    cursor: pointer;
    border-radius:2px;
}

.textDate {
      font-weight: 500; cursor: pointer;  display: block; color:#333333;
}
.preMonthDate, .nextMonthDate {
      color:#aaaaaa;
}
.nowDate {
      color:#111111;
}
.saturdayDate{
      color:#0000ff;
}
.sundayDate{
      color:#ff0000;
}

.accountExpense {
   display:block;
   width:110px;
   margin:3px 0;
   font-size:13px;
   color:red;
   font-weight: 600;
}
.accountIncome {
   display:block;
   width:110px;
   margin:3px 0;
   font-size:13px;
   color:blue;
   font-weight: 600;
}

</style>	
<title>Document</title>

<script type="text/javascript">
function changeDate(year, month) {
	var url="${pageContext.request.contextPath}/account/listM.do?year="+year+"&month="+month;
	location.href=url;
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

				   		<table style="width: 840px; margin:0px auto; border-spacing: 0;" >
				   			<tr height="60">
				   			     <td width="200">&nbsp;</td>
				   			     <td align="center">
				   			         <span class="btnDate" onclick="changeDate(${todayYear}, ${todayMonth});">오늘</span>
				   			         <span class="btnDate" onclick="changeDate(${year}, ${month-1});">＜</span>
				   			         <span class="titleDate">${year}年 ${month}月</span>
				   			         <span class="btnDate" onclick="changeDate(${year}, ${month+1});">＞</span>
				   			     </td>
				   			     <td width="200">&nbsp;</td>
				   			</tr>
				   		</table>
				   		
					    <table id="largeCalendar" style="width: 840px; margin:0px auto; border-spacing: 1px; background: #cccccc;" >
							<tr align="center" height="30" bgcolor="#ffffff">
								<td width="120" style="color:#ff0000;">일</td>
								<td width="120">월</td>
								<td width="120">화</td>
								<td width="120">수</td>
								<td width="120">목</td>
								<td width="120">금</td>
								<td width="120" style="color:#0000ff;">토</td>
							</tr>
		
						<c:forEach var="row" items="${days}" >
								<tr align="left" height="100" valign="top" bgcolor="#ffffff">
									<c:forEach var="d" items="${row}">
										<td style="padding: 5px; box-sizing:border-box;">
											${d}
										</td>
									</c:forEach>
								</tr>
						</c:forEach>
					    </table>		   

					</div>
				</article>
				<article class="article2">
					<table class="table3">
						<tr>
							<td><button class="btn btn1" onclick="javascript:location.href='${pageContext.request.contextPath}/account/listM.do'"></button></td>
							
							<td align="right">
								<button class="btn btn3" type="button" onclick="javascript:location.href='${pageContext.request.contextPath}/account/addlog.do'">글등록</button>
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