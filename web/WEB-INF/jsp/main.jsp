<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
<fmt:setBundle basename="text" var="b"/>

<fmt:message bundle="${b}" key="common.booking" var="mainTitle"/>
<fmt:message bundle="${b}" key="common.signIn" var="signIn"/>
<fmt:message bundle="${b}" key="common.signUp" var="signUp"/>
<fmt:message bundle="${b}" key="roomFinder.roomFinder" var="roomFinder"/>
<fmt:message bundle="${b}" key="roomFinder.p" var="p"/>
<fmt:message bundle="${b}" key="roomFinder.wifi" var="wifi"/>
<fmt:message bundle="${b}" key="roomFinder.arrive" var="arrive"/>
<fmt:message bundle="${b}" key="roomFinder.leave" var="leave"/>
<fmt:message bundle="${b}" key="roomFinder.find" var="find"/>
<fmt:message bundle="${b}" key="roomFinder.book" var="book"/>
<fmt:message bundle="${b}" key="nvgtn.left" var="left"/>
<fmt:message bundle="${b}" key="nvgtn.right" var="right"/>
<fmt:message bundle="${b}" key="empty.noRooms" var="noRooms"/>
<fmt:message bundle="${b}" key="roomFinder.yes" var="yes"/>
<fmt:message bundle="${b}" key="roomFinder.no" var="no"/>

<c:set var="role" value="${sessionScope.role}"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${mainTitle}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
    <link href='https://fonts.googleapis.com/css?family=Raleway' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/responsive.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/components/items/items.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/components/slider/input-slider.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/components/checkbox/checkbox.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/components/date/date.css">
</head>
<body>
<c:choose>
    <c:when test="${role==null}">
        <%@include file="header/questHeader.jsp" %>
    </c:when>
    <c:otherwise>
        <c:if test="${role=='CLIENT'||role == 'ADMIN'}">
            <%@include file="header/clientAdminHeader.jsp" %>
        </c:if>
    </c:otherwise>
</c:choose>

<section class="content">

    <aside>
        <div class="card">
            <h2>${roomFinder}</h2>
            <p>${p}</p>
            <hr>

            <form id="find-room-form" name="find-room-form" action="${pageContext.request.contextPath}/mainController" method="post">
                <div class="field">
                    <input type="hidden" name="command" value="Find_Rooms">
                </div>
                <div class="slider-group-container">
                    <div class="slider-item slider-rooms-group" data-id="0">
                        <div class="slider-item-title"><i class="fa fa-bed" aria-hidden="true"></i></div>
                        <input class="slider-in" name="rooms-number" id="slider-rooms" type="range" min="1" max="5"
                               value="${requestScope.get("beds")}" step="1">
                        <output class="slider-out" for="slider-rooms">${requestScope.get("beds")}</output>
                    </div>

                    <div class="slider-item slider-class-group" data-id="1">
                        <div class="slider-item-title"><i class="fa fa-star" aria-hidden="true"></i></div>
                        <input class="slider-in" name="stars-number" id="slider-class" type="range" min="1" max="5"
                               value="${requestScope.get("stars")}" step="1">
                        <output class="slider-out" for="slider-class">${requestScope.get("stars")}</output>
                    </div>

                    <div class="slider-item slider-price-group" data-id="2">
                        <div class="slider-item-title"><i class="fa fa-money" aria-hidden="true"></i></div>
                        <input class="slider-in" name="price-number" id="slider-price" type="range" min="100"
                               max="1000" value="${requestScope.get("price")}" step="100">
                        <output class="slider-out" for="slider-price">${requestScope.get("price")}</output>
                    </div>
                </div>

                <div class="wifi-group-container">
                    <div class="wifi-title">${wifi}</div>
                    <div class="checkbox-wifi">
                        <c:choose>
                            <c:when test="${requestScope.get('wifi')==null}">
                                <input type="checkbox" name="wifi-number" id="checkbox-wifi-input">
                            </c:when>
                            <c:when test="${requestScope.get('wifi')==false}">
                                <input type="checkbox" name="wifi-number" id="checkbox-wifi-input">
                            </c:when>
                            <c:when test="${requestScope.get('wifi')==true}">
                                <input type="checkbox" name="wifi-number" id="checkbox-wifi-input" checked>
                            </c:when>
                        </c:choose>
                        <label for="checkbox-wifi-input"></label>
                    </div>
                </div>

                <div class="date-group-container">
                    <div class="date-item date-arriving" data-id="0">
                        <label for="date-arriving-input">${arrive}</label>
                        <input type="date" name="arriving-date" id="date-arriving-input"
                               value="${requestScope.get("arriveDate")}" required>
                    </div>

                    <div class="date-item date-leaving" data-id="1">
                        <label for="date-living-input">${leave}</label>
                        <input type="date" name="leaving-date" id="date-living-input"
                               value="${requestScope.get("leaveDate")}" required>
                    </div>
                </div>

                <hr>

                <button class="button find-room-button" id="find-room-button" type="submit">
                    ${find}
                </button>
            </form>

        </div>
    </aside>
    <!--<div class="field">
    <div>
    <button class="button left-btn" id="left-button">${left}</button>
    </div>
    <div>
    <button class="button right-btn" id="right-button">${right}</button>
    </div>
    </div>-->

    <c:if test="${allRooms.size() == 0}">
        <main class="items items-no-results">
            <h1 class="items-no-results-title">${noRooms}</h1>
            <div class="img-container">
                <img class="server-img"
                     src="${pageContext.request.contextPath}/common/images/sadness.jpg">
            </div>
        </main>
    </c:if>
    <c:if test="${allRooms.size() != 0}">
        <main class="items">
            <c:forEach var="rooms" items="${allRooms}">
                <form action="/mainController" method="post">
                    <input type="hidden" name="id" value="${rooms.getId()}">
                    <article class="item card" data-id=${rooms.getId()}>
                        <header class="item-header">
                            <img src="https://placeimg.com/640/480/nature)">
                            <h2 class="item-title">${rooms.type.getName()}</h2>
                            <p class="item-description">${rooms.getDescription()}</p>
                        </header>
                        <main class="item-content">
                            <ul class="item-table">
                                <li class="item-cell beds">
                                    <c:forEach var="bed" begin="1" end="${rooms.getCapacity()}">
                                        <i class="class icon fa fa-bed" aria-hidden="true"></i>
                                    </c:forEach>
                                </li>
                                <li class="item-cell class">
                                    <c:forEach var="star" begin="1" end="${rooms.getStarCount()}">
                                        <i class="class icon fa fa-star" aria-hidden="true"></i>
                                    </c:forEach>
                                </li>
                                <c:if test="${rooms.isWifi() == true}">
                                    <li class="item-cell wifi yes">
                                        <i class="icon fa fa-wifi" aria-hidden="true"></i>
                                        <span class="value">${yes}</span>
                                    </li>
                                </c:if>
                                <c:if test="${rooms.isWifi() == false}">
                                    <li class="item-cell wifi no">
                                        <i class="icon fa fa-wifi" aria-hidden="true"></i>
                                        <span class="value">${no}</span>
                                    </li>
                                </c:if>
                                <li class="item-cell price">
                                    <i class="icon fa fa-money" aria-hidden="true"></i>
                                    <span class="value" name="cost">${rooms.getPrice()}</span>
                                </li>
                            </ul>
                        </main>
                        <input type="hidden" name="cost" value="${rooms.getPrice()}">
                        <input type="hidden" name="arrivalTime" value="${arrivalTime}">
                        <input type="hidden" name="leavingTime" value="${leavingTime}">
                        <c:if test="${sessionScope.role!=null && arrivalTime!=null}">
                            <h3>${arrivalTime} - ${leavingTime}</h3>
                            <footer class="item-footer">
                                <input type="hidden" name="command" value="Book_Room">
                                <button class="button book-button" name="book-button" type="submit"><span>${book}</span>
                                </button>
                            </footer>
                        </c:if>
                    </article>
                </form>
            </c:forEach>
        </main>
    </c:if>
</section>

<%@include file="footer/footer.jsp" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/components/slider/slider.controller.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/components/checkbox/checkbox.controller.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/components/date/date.controller.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/components/date/minDate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/main.js"></script>

</body>
</html>
