<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 1/9/2021
  Time: 12:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Blog Post - Start Bootstrap Template</title>

    <!-- Bootstrap core CSS -->
    <link href="resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="resources/css/blog-post.css" rel="stylesheet">

</head>

<body>

<!-- Navigation -->
<%@include file="/WEB-INF/includes/navbar.jsp" %>

<!-- Page Content -->
<div class="container">
    <!-- Comments Form -->
    <div class="card my-4">
        <h5 class="card-header">Sign in:</h5>
        <div class="card-body">
            <form method="post">
                <div class="form-group">
                    <label for="login">Login: </label>
                    <input class="form-control" rows="3" id="login" name="login"/>
                </div>
                <div class="form-group">
                    <label for="password">Password: </label>
                    <input class="form-control" rows="3" id="password" name="password"/>
                </div>
                <button type="submit" class="btn btn-primary">Login</button>
            </form>
        </div>
    </div>
</div>
<!-- /.container -->

<!-- Footer -->
<footer class="py-5 bg-dark">
    <div class="container">
        <p class="m-0 text-center text-white">Copyright &copy; Your Website 2020</p>
    </div>
    <!-- /.container -->
</footer>

<!-- Bootstrap core JavaScript -->
<script src="resources/vendor/jquery/jquery.min.js"></script>
<script src="resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>
