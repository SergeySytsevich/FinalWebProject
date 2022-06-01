<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:setBundle basename="text" var="b"/>

<fmt:message bundle="${b}" key="common.registration" var="reg"/>
<fmt:message bundle="${b}" key="common.signUp" var="signUp"/>

<fmt:message bundle="${b}" key="reg.name" var="name"/>
<fmt:message bundle="${b}" key="reg.surname" var="surname"/>
<fmt:message bundle="${b}" key="reg.country" var="country"/>
<fmt:message bundle="${b}" key="reg.age" var="age"/>
<fmt:message bundle="${b}" key="reg.mail" var="mail"/>
<fmt:message bundle="${b}" key="reg.login" var="login"/>
<fmt:message bundle="${b}" key="reg.password" var="password"/>
<fmt:message bundle="${b}" key="reg.repeatPassword" var="repPassword"/>

<fmt:message bundle="${b}" key="wrong.name" var="wrName"/>
<fmt:message bundle="${b}" key="wrong.surname" var="wrSurname"/>
<fmt:message bundle="${b}" key="wrong.country" var="wrCountry"/>
<fmt:message bundle="${b}" key="wrong.age" var="wrAge"/>
<fmt:message bundle="${b}" key="wrong.email" var="wrEmail"/>
<fmt:message bundle="${b}" key="wrong.existingEmail" var="wrExEmail"/>
<fmt:message bundle="${b}" key="wrong.login" var="wrLogin"/>
<fmt:message bundle="${b}" key="wrong.existingLogin" var="wrExLogin"/>
<fmt:message bundle="${b}" key="wrong.password" var="wrPassword"/>
<fmt:message bundle="${b}" key="wrong.repeatPassword" var="wrRepPassword"/>
<fmt:message bundle="${b}" key="common.main" var="main"/>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${reg}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
    <link href='https://fonts.googleapis.com/css?family=Raleway' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/css/index.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/css/responsive.css">

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
            <li class="logo">Logo</li>
        </ul>
    </nav>
</header>

<section class="content register-content">

    <form class="card register-card" onsubmit="return validateForm()"
          action="${pageContext.request.contextPath}/mainController" method="post"
          id="register-form">
        <div class="field">
            <input type="hidden" name="command" value="Sign_Up">
        </div>
        <div class="field">
            <label class="required">${name}</label>
            <input type="text" id="name" name="name" placeholder="${name}" value="${requestScope.get("name")}" required>
        </div>
        <c:if test="${requestScope.containsKey('wrongName')}">
            <div>
                <label class="wrong-info" id="wrong-name">${wrName}</label>
            </div>
        </c:if>
        <div>
            <label class="wrong-info" id="incorrect-name">${wrName}</label>
        </div>
        <div class="field">
            <label class="required">${surname}</label>
            <input type="text" id="surname" name="surname" placeholder="${surname}"
                   value="${requestScope.get("surname")}"
                   required>
        </div>
        <c:if test="${requestScope.containsKey('wrongSurname')}">
            <div>
                <label class="wrong-info" id="wrong-surname">${wrSurname}</label>
            </div>
        </c:if>
        <div>
            <label class="wrong-info" id="incorrect-surname">${wrSurname}</label>
        </div>
        <div class="field">
            <label class="required">${country}</label>
            <input type="text" id="country" name="country" placeholder="${country}"
                   value="${requestScope.get("country")}"
                   required>
        </div>
        <c:if test="${requestScope.containsKey('wrongCountry')}">
            <div>
                <label class="wrong-info" id="wrong-country">${wrCountry}</label>
            </div>
        </c:if>
        <div>
            <label class="wrong-info" id="incorrect-country">${wrCountry}</label>
        </div>
        <div class="field">
            <label class="required">${age}</label>
            <input type="text" name="age" placeholder="${age}" id="age" value="${requestScope.get("age")}" required>
        </div>
        <c:if test="${requestScope.containsKey('wrongAge')}">
            <div>
                <label class="wrong-info" id="wrong-age">${wrAge}</label>
            </div>
        </c:if>
        <div>
            <label class="wrong-info" id="incorrect-age">${wrAge}</label>
        </div>
        <div class="field">
            <label class="required">${mail}</label>
            <input type="text" id="mail" name="mail" placeholder="${mail}"
                   value="${requestScope.get("mail")}" required>
            <input type=hidden name="wrong_mail" placeholder="${wrEmail}">
        </div>
        <c:if test="${requestScope.containsKey('wrongEmail')}">
            <div>
                <label class="wrong-info" id="wrong-email">${wrEmail}</label>
            </div>
        </c:if>
        <c:if test="${requestScope.containsKey('existingEmail')}">
            <div>
                <label class="wrong-info" id="existing-email">${wrExEmail}</label>
            </div>
        </c:if>
        <div>
            <label class="wrong-info" id="incorrect-email">${wrEmail}</label>
        </div>
        <div class="field">
            <label class="required">${login}</label>
            <input type="text" id="login" name="login" placeholder="${login}" value="${requestScope.get("login")}"
                   required>
        </div>
        <c:if test="${requestScope.containsKey('wrongLogin')}">
            <div>
                <label class="wrong-info" id="wrong-login">${wrLogin}</label>
            </div>
        </c:if>
        <c:if test="${requestScope.containsKey('existingLogin')}">
            <div>
                <label class="wrong-info" id="login-exists">${wrExLogin}</label>
            </div>
        </c:if>
        <div>
            <label class="wrong-info" id="incorrect-login">${wrLogin}</label>
        </div>
        <div class="field">
            <label class="required">${password}</label>
            <input type="password" id="password" name="password" placeholder="${password}"
                   value="${requestScope.get("password")}" required>
        </div>
        <c:if test="${requestScope.containsKey('wrongPassword')}">
            <div>
                <label class="wrong-info" id="wrong-password">${wrPassword}</label>
            </div>
        </c:if>
        <div>
            <label class="wrong-info" id="incorrect-password">${wrPassword}</label>
        </div>
        <div class="field">
            <label class="required">${repPassword}</label>
            <input type="password" id="repeat-password" name="repeat-password" placeholder="${repPassword}"
                   value="${requestScope.get("repeat-password")}" required>
        </div>
        <c:if test="${requestScope.containsKey('wrongPasswordRepetition')}">
            <div>
                <label class="wrong-info" id="wrong-password-repetition">${wrRepPassword}</label>
            </div>
        </c:if>
        <div>
            <label class="wrong-info" id="incorrect-repeat-password">${wrRepPassword}</label>
        </div>
        <button class="button" type="submit">${signUp}</button>
    </form>

</section>
<script src="${pageContext.request.contextPath}/common/js/signUp.js"></script>
<%@include file="footer/footer.jsp" %>

</body>
</html>
