<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 1/14/2021
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<c:forEach items="${posts}" var="post">
    <!-- Blog Post -->
    <div class="card mb-4">
        <img class="card-img-top" src="http://placehold.it/750x300" alt="Card image cap">
        <div class="card-body">
            <h2 class="card-title">${post.getTitle()}</h2>
            <p class="card-text">${post.getShortText(200)}</p>
            <a href="${pageContext.request.contextPath}/article?id=${post.getId()}" class="btn btn-primary">Read More
                &rarr;</a>
        </div>
        <div class="card-footer text-muted">
            Posted on ${post.dateToString()} by
            <a href="#">${post.getAuthor()}</a>
        </div>
    </div>
</c:forEach>
<!-- Blog Post -->
<div class="card mb-4">
    <img class="card-img-top" src="http://placehold.it/750x300" alt="Card image cap">
    <div class="card-body">
        <h2 class="card-title">Post Title</h2>
        <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reiciendis aliquid atque, nulla?
            Quos cum ex quis soluta, a laboriosam. Dicta expedita corporis animi vero voluptate voluptatibus possimus,
            veniam magni quis!</p>
        <a href="#" class="btn btn-primary">Read More &rarr;</a>
    </div>
    <div class="card-footer text-muted">
        Posted on January 1, 2020 by
        <a href="#">Start Bootstrap</a>
    </div>
</div>
