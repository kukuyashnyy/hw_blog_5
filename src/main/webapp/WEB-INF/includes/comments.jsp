<%@ page import="java.util.HashMap" %>
<%@ page import="org.itstep.sql.Database" %>
<%@ page import="java.util.List" %>
<%@ page import="org.itstep.Classes.Comment" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Collections" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 1/12/2021
  Time: 23:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Comments Form -->
<c:if test="${sessionScope.user != null}">
    <div class="card my-4">
        <h5 class="card-header">Leave a Comment:</h5>
        <div class="card-body">
            <form method="post">
                <div class="form-group">
                    <textarea class="form-control" rows="3" name="post_text"></textarea>
                    <input type="hidden" name="comment_author" value="${user}"/>
                    <input type="hidden" name="post_id" value="${post.getId()}"/>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</c:if>

<!-- Single Comment -->
<c:forEach items="${comments}" var="comment">
    <c:if test="${comment != null}">
        <div class="media mb-4">
            <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
            <div class="media-body">
                <h5 class="mt-0">${comment.getAuthorName()}</h5>
                    ${comment.getText()}
            </div>
        </div>
    </c:if>
</c:forEach>

<div class="media mb-4">
    <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
    <div class="media-body">
        <h5 class="mt-0">Commenter Name</h5>
        Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio,
        vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec
        lacinia congue felis in faucibus.
    </div>
</div>

<!-- Comment with nested comments -->
<%--<div class="media mb-4">--%>
<%--    <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">--%>
<%--    <div class="media-body">--%>
<%--        <h5 class="mt-0">Commenter Name</h5>--%>
<%--        Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio,--%>
<%--        vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec--%>
<%--        lacinia congue felis in faucibus.--%>

<%--        <div class="media mt-4">--%>
<%--            <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">--%>
<%--            <div class="media-body">--%>
<%--                <h5 class="mt-0">Commenter Name</h5>--%>
<%--                Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus--%>
<%--                odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate--%>
<%--                fringilla. Donec lacinia congue felis in faucibus.--%>
<%--            </div>--%>
<%--        </div>--%>

<%--        <div class="media mt-4">--%>
<%--            <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">--%>
<%--            <div class="media-body">--%>
<%--                <h5 class="mt-0">Commenter Name</h5>--%>
<%--                Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus--%>
<%--                odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate--%>
<%--                fringilla. Donec lacinia congue felis in faucibus.--%>
<%--            </div>--%>
<%--        </div>--%>

<%--    </div>--%>
<%--</div>--%>

<%--</div>--%>
