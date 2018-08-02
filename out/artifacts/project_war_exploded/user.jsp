<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="bootstrap/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" href="css/user.css" type="text/css">
</head>
<body>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="nav.jsp"></c:import>
<div class="form">
    <ul class="tab-group">
        <li class="tab active"><a href="#signup">Sign Up</a></li>
        <li class="tab"><a href="#login">Log In</a></li>
    </ul>
    <div class="tab-content">
        <div id="signup">
            <h1>Sign Up for Free</h1>
            <form action="login.user" method="post">
                <div class="field-wrap">
                    <label>
                        Name<span class="req">*</span>
                    </label>
                    <input type="text" autocomplete="off" id="registerName"/>
                </div>
                <div class="field-wrap">
                    <label>
                        Set A Password<span class="req">*</span>
                    </label>
                    <input type="password" autocomplete="off" name="password"/>
                </div>
                <div class="field-wrap">
                    <label>
                        Repeat Password<span class="req">*</span>
                    </label>
                    <input type="password" autocomplete="off" id="registerPassword" name="rpassword"/>
                </div>
                <input type="submit" class="button button-block" id="registerButton">Get Started</input>
            </form>
        </div>

        <div id="login">
            <h1>Welcome Back!</h1>
            <form action="register.user" method="post">
                <div class="field-wrap">
                    <label>
                        Name<span class="req">*</span>
                    </label>
                    <input type="text" autocomplete="off" id="loginName"/>
                </div>
                <div class="field-wrap">
                    <label>
                        Password<span class="req">*</span>
                    </label>
                    <input type="password" autocomplete="off" id="loginPassword"/>
                </div>
                <p class="forgot"><a href="#">Forgot Password?</a></p>
                <button type="button" class="button button-block" id="loginButton">Log In</button>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/user.js"></script>
<script src="js/nav.js"></script>
</body>
</html>