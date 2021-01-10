<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.function.Predicate" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 1/10/2021
  Time: 2:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" href="#">Start Bootstrap</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive"
                aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/home">Home
                        <span class="sr-only">(current)</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/post">Post</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">About</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Services</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Contact</a>
                </li>
                <%
                    Cookie[] cookies = request.getCookies();
                    Boolean isLogin = false;
                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
//                            System.out.println("Name: " + cookie.getName());
//                            System.out.println("Value: " + cookie.getValue());
                            if (cookie.getName().equals("admin") && cookie.getValue().equals("true")) { %>
                                <li class="nav-item">
                                    <a class="nav-link" href="/logout">Logout</a>
                                </li>
                            <% }
                        }
                    }
                %>
            </ul>
        </div>
    </div>
</nav>
