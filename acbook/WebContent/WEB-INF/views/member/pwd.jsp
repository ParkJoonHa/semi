<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spring</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/member.css"
	type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/layout.css"
	type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/jquery/css/smoothness/jquery-ui.min.css"
	type="text/css">

<style type="text/css">
.lbl {
	position: absolute;
	margin-left: 15px;
	margin-top: 17px;
	color: #999999;
	font-size: 11pt;
}

.loginTF {
	width: 340px;
	height: 35px;
	padding: 5px;
	padding-left: 15px;
	border: 1px solid #999999;
	color: #333333;
	margin-top: 5px;
	margin-bottom: 5px;
	font-size: 14px;
	border-radius: 4px;
}
</style>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/jquery/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/util.js"></script>
<script type="text/javascript">
	function bgLabel(ob, id) {
		if (!ob.value) {
			document.getElementById(id).style.display = "";
		} else {
			document.getElementById(id).style.display = "none";
		}
	}

	function sendOk() {
		var f = document.pwdForm;

		var str = f.userPwd.value;
		if (!str) {
			alert("패스워드를 입력하세요. ");
			f.userPwd.focus();
			return;
		}

		f.action = "${pageContext.request.contextPath}/member/${mode}.do";
		f.submit();
	}
</script>

</head>
<body>

	<div class="memberContainer">
		<div class="memberBack">

			<div class="header">
				<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
			</div>

			<div class="container">
					<div style="margin: 0 auto;">

						<div style="text-align: center;">
							<span style="font-weight: bold; font-size: 27px; color: white;">패스워드
								재확인</span>
						</div>



						<div >
						<form name="pwdForm" method="post" action="" style="margin: 0 auto;">
							<table class="pwdMainBody"
								style="width: 50%; margin: 20px auto; padding: 30px; border-collapse: collapse; border: 1px solid #DAD9FF; border-radius: 5px;">
								<tr style="height: 50px;">
									<td style="text-align: center; padding-top: 10px;">정보보호를 위해
										패스워드를 다시 한 번 입력해주세요.</td>
								</tr>

								<tr style="height: 60px;" align="center">
									<td>&nbsp; <input type="text" name="userId"
										class="loginTF" maxlength="15" tabindex="1"
										value="${sessionScope.member.userId}" readonly="readonly">
										&nbsp;
									</td>
								</tr>
								<tr align="center" height="60">
									<td>&nbsp; <label for="userPwd" id="lblUserPwd"
										class="lbl">패스워드</label> <input type="password" name="userPwd"
										id="userPwd" class="loginTF" maxlength="20" tabindex="2"
										onfocus="document.getElementById('lblUserPwd').style.display='none';"
										onblur="bgLabel(this, 'lblUserPwd');"> &nbsp;
									</td>
								</tr>
								<tr align="center" height="80">
									<td>&nbsp;
										<button type="button" onclick="sendOk();"
											class="btn btnConfirm" style="width:120px; height: 40px; line-height: 10px;">확인</button> <input type="hidden"
										name="mode" value="${mode}"> &nbsp;
									</td>
								</tr>
								<tr align="center" height="10">
									<td>&nbsp;</td>
								</tr>
							</table>
						</form>

						<table
							style="width: 50%; margin: 10px auto 0; border-collapse: collapse;">
							<tr align="center" height="30">
								<td><span style="color: blue;">${message}</span></td>
							</tr>
						</table>
						</div>
					</div>


			</div>
			<div style="height: 350px;"></div>
			<div class="footer">
				<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
			</div>

			<script type="text/javascript"
				src="${pageContext.request.contextPath}/resource/jquery/js/jquery-ui.min.js"></script>
			<script type="text/javascript"
				src="${pageContext.request.contextPath}/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
		</div>
	</div>
</body>
</html>