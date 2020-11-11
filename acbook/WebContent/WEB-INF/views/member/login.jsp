<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Gugi&display=swap" rel="stylesheet">
    <title>Document</title>
    
<script type="text/javascript">
function bgLabel(ob, id) {
    if(!ob.value) {
	    document.getElementById(id).style.display="";
    } else {
	    document.getElementById(id).style.display="none";
    }
}

function sendLogin() {
    var f = document.loginForm;

	var str = f.userId.value;
    if(!str) {
        alert("아이디를 입력하세요. ");
        f.userId.focus();
        return;
    }

    str = f.userPwd.value;
    if(!str) {
        alert("패스워드를 입력하세요. ");
        f.userPwd.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/member/login_ok.do";
    f.submit();
}
</script>    
    
</head>

<body>
    <form name="loginForm" method="post" action="">
    <div class="back">
    <div class="mainBody">
            <table class="logintable">
                <tr class="tr1">
                    <td colspan="2">
                        <h1><i class="far fa-clipboard"></i> 회원 로그인</h1>
                    </td>
                </tr>
                <tr class="tr1">
                   
                    <td><input name="userId" id="userId" type="text" placeholder="아이디를 입력해주세요."></td>
                </tr>
                <tr class="tr1">
                  
                    <td><input name="userPwd" id="userPwd" type="password" placeholder="비밀번호를 입력해주세요"></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <button type="button" onclick="sendLogin();" class="btn">로그인</button>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <span><a href="${pageContext.request.contextPath}/member/member.do">회원가입</a></span>
                        <span><a href="${pageContext.request.contextPath}/">| 아이디찾기 |</a></span>
                        <span><a href="${pageContext.request.contextPath}/">비밀번호찾기</a></span>
                    </td>
                </tr>
                
			    <tr align="center" height="40" >
			    	<td><span style="color: #3d7ea6;">${message}</span></td>
			    </tr>     
			               
            </table>
        </div>
        </div>
    </form>
</body>

</html>