<%@include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@include file="/WEB-INF/jspf/directive/page.jspf" %>
<html>
<c:set var="title" value="Account"/>
<%@include file="/WEB-INF/jspf/head.jspf" %>
<body>
<table id="main-container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <tr>
        <td class="content center account">
            <fieldset>
                <legend>
                    <fmt:message key="account.label.username"/>
                </legend>
                <p>${user.username}</p>
            </fieldset>
            <br/>

            <fieldset>
                <legend>
                    <fmt:message key="account.label.login"/>
                </legend>
                <p>${user.login}</p>
            </fieldset>
            <br/>

            <fieldset>
                <legend>
                    <fmt:message key="account.label.password"/>
                </legend>
                <p>${user.password}</p>
            </fieldset>
            <br/>

            <fieldset>
                <legend>
                    <fmt:message key="account.label.phone_number"/>
                </legend>
                <p>${user.phone_number}</p>
            </fieldset>
            <br/>

            <c:if test="${user.status == false}">
                <fieldset>
                    <legend>
                        <fmt:message key="account.label.status"/>
                    </legend>
                    <p>
                        <fmt:message key="account.label.block"/>
                    </p>
                </fieldset>
                <br/>
            </c:if>
            <fieldset>
                <legend>
                    <fmt:message key="account.label.role"/>
                </legend>
                <p>${userRole}</p>
            </fieldset>
        </td>
        <td class="content right account">
            <fieldset>
                <legend>
                    <fmt:message key="account.legend.myListTours"/>
                </legend>
                <table>
                    <c:forEach var="order" items="${orders}">
                        <tr class="order_view">
                            <td>
                                Name tour: ${order.name}<br>
                                Price: ${order.price}<br>
                                Status: ${order.status}<br>
                                <details>
                                    <summary>
                                        More>>
                                    </summary>
                                    <fieldset>
                                        Count people: ${order.count_people}<br>
                                        Mark hotel: ${order.start_date}<br>
                                        Days: ${order.days}<br>
                                        Discount: ${order.discount}<br>
                                    </fieldset>
                                </details>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </fieldset>
        </td>
    </tr>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</table>
</body>
</html>
