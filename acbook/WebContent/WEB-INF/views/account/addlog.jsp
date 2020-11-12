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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/account.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Jua&display=swap" rel="stylesheet">
<title>Document</title>
</head>

<script type="text/javascript">
function selectOpt(chk) {
    if(chk.value==1){
        document.getElementById("expenditureLayout").style.display="block";
        document.getElementById("incomeLayout").style.display="none";
        document.getElementById("op1").disabled=false;
        document.getElementById("op2").disabled=false;
        document.getElementById("op3").disabled=true;
        document.getElementById("op4").disabled=true;
    } else {
        document.getElementById("expenditureLayout").style.display="none";
        document.getElementById("incomeLayout").style.display="block";     
        document.getElementById("op1").disabled=true;
        document.getElementById("op2").disabled=true;
        document.getElementById("op3").disabled=false;
        document.getElementById("op4").disabled=false;       
    }
}

function sendOk() {
    var f = document.spendForm;

	str = f.amount.value;
    if(!str) {
        alert("금액을 입력하세요. ");
        f.amount.focus();
        return;
    }

	f.action="${pageContext.request.contextPath}/account/${mode}_ok.do";

    f.submit();
}
	
</script>

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

			<form name="spendForm" method="post">
				<section>
					<div class="innerNav">
					<h1>소비 기록</h1>
				</div>
				<article class="article1">
					<!-- 여기가 게시글 올리는곳 -->
					<div>
						<table style="width: 100%; height: 50px; margin: 5px auto; text-align: center;">
							<tr align="left" height="100" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
							      <td width="200" bgcolor="#eeeeee" style="text-align: center;">구&nbsp;&nbsp;분</td>
							      <td style="padding-left:10px;"> &nbsp;&nbsp;&nbsp;&nbsp;  
								<input type="radio" name="status" value="1"  onclick="selectOpt(this)" checked="checked">&nbsp;&nbsp;&nbsp;&nbsp;지출&nbsp;&nbsp;&nbsp;&nbsp;  
							    <input type="radio" name="status" value="2" onclick="selectOpt(this)">&nbsp;&nbsp;&nbsp;&nbsp;수입&nbsp;&nbsp;
						
							<tr align="left" height="100" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
							      <td width="200" bgcolor="#eeeeee" style="text-align: center;">날&nbsp;&nbsp;짜</td>
							      <td style="padding-left:10px;">&nbsp;&nbsp;&nbsp;&nbsp;  
									<input type="date" name="abDate">
							      </td>
							 </tr>								
								
							<tr align="left" height="100" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
							      <td width="200" bgcolor="#eeeeee" style="text-align: center;">자&nbsp;&nbsp;산</td>
							      <td style="padding-left:10px;">  
								    <div id="expenditureLayout"> <!-- 지출 -->;&nbsp;&nbsp;&nbsp;
										<select id="op1" name="kind1">
											<option value="신용카드">신용카드</option> 
											<option value="체크카드">체크카드</option> 
											<option value="계좌이체">계좌이체</option>
											<option value="현금">현금</option>
											<option value="대출이자">대출이자</option>	
										</select>
										;&nbsp;&nbsp;  
										<select id="op2" name="kind2">
											<option value="카카오">카카오</option>
											<option value="국민">국민</option> 
											<option value="기업">기업</option> 
											<option value="신한">신한</option>
											<option value="농협">농협</option>
											<option value="우리">우리</option>		
											<option value="하나">하나</option>
											<option value="시티">시티</option>		
											<option value="현금">현금</option>
										</select>
									</div>
								    <div id="incomeLayout" style="display: none;"> <!-- 수입 -->
										<select id="op3" name="kind1" disabled="disabled">
											<option value="급여">급	여</option> 
											<option value="상여금">상 여 금</option>
											<option value="급여">계좌 이체</option>
											<option value="대출">대	출</option>
											<option value="용돈">용  돈</option>
											<option vlaue="금융소득">금융 소득</option>	
											<option value="부수압">기타 수입</option>
										</select>
										;&nbsp;&nbsp;  
										<select id="op4" name="kind2" disabled="disabled">
											<option value="선택안함">선택안함</option>	
											<option value="예금">예 금</option>
											<option value="적금">적 금</option>
											<option value="보험">보 험</option>
											<option value="펀드">펀 드</option> 
											<option value="기업">기 업</option> 
											<option value="현금">현 금</option>	
											<option value="투자">투 자</option>			
										</select>
									</div>									
							 </tr>
							 <c:if test="${mode=='update'}">
							 	<input type="hidden" name="page" value="${page}">
							 	<input type="hidden" name="abNum" value="${dto.abNum}">
							 </c:if>
							<tr align="left" height="100" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
							      <td width="200" bgcolor="#eeeeee" style="text-align: center;">분&nbsp;&nbsp;류</td>
							      <td style="padding-left:10px;">
								    <input type="text" name="content">
							      </td>						   
							 </tr>
							 <tr align="left" height="100" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
							      <td width="200" bgcolor="#eeeeee" style="text-align: center;">금&nbsp;&nbsp;액</td>
							      <td style="padding-left:10px;"> 
							        <input type="text" name="amount" value="${dto.amount}" > 
							      </td>
							 </tr>
						</table>
					</div>
				</article>
				<article class="article2">
					<table class="table3">
						<tr>
							<td>
								<button class="btn btn3" type="button" onclick="javascript:loacaion.href='${pageContext.request.contextPath}/account/listD.do';">${mode=='update'?'수정취소':'등록취소'}</button>
							</td>
							<td>
								<button class="btn btn3" type="button" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
							</td>
						</tr>
					</table>
				</article>
			</section>
		</main>
	</form>

		<footer>
			<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
		</footer>
	</div>
</div>

</body>

</html>