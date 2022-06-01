<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:setBundle basename="text" var="b"/>

<fmt:message bundle="${b}" key="common.authorization" var="auth"/>
<fmt:message bundle="${b}" key="common.signIn" var="signIn"/>
<fmt:message bundle="${b}" key="reg.login" var="login"/>
<fmt:message bundle="${b}" key="reg.password" var="password"/>
<fmt:message bundle="${b}" key="wrong.nonExistingLogin" var="nonExLogin"/>
<fmt:message bundle="${b}" key="wrong.nonExistingPassword" var="nonExPassword"/>
<fmt:message bundle="${b}" key="wrong.isBlocked" var="isBlocked"/>
<fmt:message bundle="${b}" key="wrong.notAuthorized" var="notAuthorized"/>
<fmt:message bundle="${b}" key="reg.notAuthorized" var="notAuth"/>
<fmt:message bundle="${b}" key="reg.forgotPass" var="forgotPass"/>
<fmt:message bundle="${b}" key="common.main" var="main"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${signIn}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
    <link href='https://fonts.googleapis.com/css?family=Raleway' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="/common/css/index.css">
    <link rel="stylesheet" href="/common/css/responsive.css">
</head>
<body>

<header class="header">
    <nav>
        <ul>
            <li>
                <%@include file="header/locale.jsp" %>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}\mainController?command=Open_Main_Page">${main}</a>
            </li>
            <li class="logo">
                Logo
            </li>
        </ul>
    </nav>
</header>

<section class="content login-content">

    <div class="field">
        <form class="card login-card" onsubmit="validateSignIn()" action="/mainController" method="post"
              id="login-form">
            <div class="field">
                <input type="hidden" name="command" value="Sign_In">
            </div>
            <div class="field">
                <input type="text" id="login" name="login" placeholder="${login}" value="${requestScope.get("login")}"
                       required>
            </div>
            <c:if test="${requestScope.containsKey('nonExistingLogin')}">
                <div>
                    <label class="wrong-info">${nonExLogin}</label>
                </div>
            </c:if>
            <c:if test="${requestScope.containsKey('isNotAuthorized')}">
                <div>
                    <label class="wrong-info">${notAuthorized}</label>
                </div>
            </c:if>
            <c:if test="${requestScope.containsKey('isBlocked')}">
                <div>
                    <label class="wrong-info">${isBlocked}</label>
                </div>
            </c:if>
            <div class="field">
                <input type="password" id="password" name="password" placeholder="${password}"
                       value="${requestScope.get("password")}" required>
            </div>
            <c:if test="${requestScope.containsKey('wrongPassword')}">
                <div>
                    <label class="wrong-info">${nonExPassword}</label>
                </div>
            </c:if>

            <button class="button" type="submit" value="${signIn}" id="sign-in-button">${signIn}</button>
        </form>
        <a href="${pageContext.request.contextPath}/jsp/authorization.jsp" style="margin-right: 16px">${notAuth}</a>
        <a href="${pageContext.request.contextPath}/jsp/forgotPassword.jsp">${forgotPass}</a>
    </div>

</section>
<script src="${pageContext.request.contextPath}/common/js/signIn.js"></script>
<%@include file="footer/footer.jsp" %>

</body>
</html>
