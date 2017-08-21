<%-- 
    Document   : login
    Created on : 30 Sep, 2016, 11:02:12 PM
    Author     : Suraj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="body-full-height">
    <head><!-- META SECTION -->
        <%@include file="../common/meta.jsp"%>
        <!-- END META SECTION -->
        <!-- CSS INCLUDE -->        
        <%@include file="../common/css.jsp"%>
        <!-- EOF CSS INCLUDE -->                                    
    </head>
    <body onload="document.getElementById('j_username').focus();">
        <div class="login-container">

            <div class="login-box animated fadeInDown">
                <a href="http://www.accenture.com"><div class="login-logo"></div></a>

                <div class="login-body">

                    <div class="login-title"><strong>Performance Evalution</strong></div>

                    <!-- START LOGIN FORM HERE -->
                    <form action="../perform-login" class="form-horizontal" method="post" >

                        <div class="form-group">
                            <div class="col-md-12">
                                <input type="text" class="form-control" placeholder="Username" id="j_username" name="j_username" value="${sessionScope[SPRING_SECURITY_LAST_USERNAME]}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-12">
                                <input type="password" class="form-control" placeholder="Password" id="j_password" name="j_password"/>
                            </div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />        
                        <div class="form-group">
                            <div class="col-md-6 pull-left">
                                <a href="../home/newRegistration.htm"  class="btn btn-info btn-block">Register</a>
                            </div>
                            <div class="col-md-6">
                                <button class="btn btn-info btn-block">Log In</button>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-12">
                                <c:if test="${param.login_error=='true'}">
                                    <span class="text-danger">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</span>
                                </c:if>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="login-footer">
                    <div class="pull-left">
                        <p>Built by - <span class="text-builtBy">Suraj Roy</span></p>
                        <p><a href="http://www.accenture.com">About</a></p>
                    </div>
                    <div class="pull-right">
                        <p class="text-builtBy">ver ${minorVersion}</p>
                    </div>
                </div>
                <!-- MESSAGE SECTION -->
                <%@include file="../common/message.jsp"%>
                <!-- END MESSAGE SECTION -->

            </div>
        </div>
    </body>
</html>