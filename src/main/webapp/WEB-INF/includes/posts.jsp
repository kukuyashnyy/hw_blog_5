<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 1/15/2021
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="post" items="${posts}">
    <!-- Post Content Column -->
    <!-- Title -->
    <h1 class="mt-4">${post.getTitle()}</h1>

    <!-- Author -->
    <p class="lead">
        by
        <a href="#">${post.getAuthor()}</a>
    </p>

    <hr>

    <!-- Date/Time -->
    <p>Posted on ${post.dateToString()} at ${post.timeToString()}</p>

    <hr>

    <!-- Preview Image -->
    <img class="img-fluid rounded" src="http://placehold.it/900x300" alt="">

    <hr>

    <p>${post.getText()}</p>
    <form method="post">
        <input type="hidden" name="delete_post_id" value="${post.getId()}"/>
        <button type="submit" class="btn btn-primary">Delete</button>
    </form>

    <hr>
</c:forEach>
