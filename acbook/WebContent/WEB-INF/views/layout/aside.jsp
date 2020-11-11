<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
//엔터 처리
$(function(){
       $("input").not($(":button")).keypress(function (evt) {
            if (evt.keyCode == 13) {
                var fields = $(this).parents('form,body').find('button,input,textarea,select');
                var index = fields.index(this);
                if ( index > -1 && ( index + 1 ) < fields.length ) {
                    fields.eq( index + 1 ).focus();
                }
                return false;
            }
         });
});
</script>

    <div class="menu">
        <ul class = "menu-bar list">

            <li class="list-menu"><a href="${pageContext.request.contextPath}/main/main.do">HOME</a></li>
            <li class="list-menu"><a href="${pageContext.request.contextPath}/notice/list.do">공지사항</a></li>
            <li class="list-menu"><a href="${pageContext.request.contextPath}/account/listM.do">가계부</a></li>
            <li class="list-menu"><a href="${pageContext.request.contextPath}/boast/list.do">자랑게시판</a></li> <!-- 커뮤니티 묶기 -->
            <li class="list-menu"><a href="${pageContext.request.contextPath}/free/list.do">자유게시판</a></li>
            <li class="list-menu"><a href="${pageContext.request.contextPath}/news/main.do">정보게시판</a></li>
            <li class="list-menu"><a href="${pageContext.request.contextPath}/qna/list.do">Q&A</a></li>
            <li class="list-menu"><a href="${pageContext.request.contextPath}/repeat/list.do">자주하는 질문</a></li>
        </ul>
    </div>