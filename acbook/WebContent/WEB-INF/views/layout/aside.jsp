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

   <%--  <div class="aside-top">
        <div class="aside-logo">
            <p>
                <a href="${pageContext.request.contextPath}/">
                    <span class="aside-logo-image">로고 들어갈 자리</span>
                </a>
            </p>
        </div>
    </div> --%>

    <div class="menu">
        <ul class = "menu-bar list">
            <li class="list-menu"><a href="">HOME</a></li>
            <li class="list-menu"><a href="">공지사항</a></li>
            <li class="list-menu"><a href="">가계부</a></li>
            <li class="list-menu"><a href="">자랑게시판</a></li> <!-- 커뮤니티 묶기 -->
            <li class="list-menu"><a href="">자유게시판</a></li>
            <li class="list-menu"><a href="">정보게시판</a></li>
            <li class="list-menu"><a href="">Q&A</a></li>
            <li class="list-menu"><a href="">자주하는 질문</a></li>
        </ul>
    </div>