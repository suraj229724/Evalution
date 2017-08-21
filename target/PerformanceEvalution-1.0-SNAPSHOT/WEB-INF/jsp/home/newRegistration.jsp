<%-- 
    Document   : newRegistration
    Created on : 20 May, 2016, 9:44:11 AM
    Author     : Suraj
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- META SECTION -->
        <%@include file="../common/meta.jsp"%>
        <!-- END META SECTION -->

        <!-- CSS INCLUDE -->
        <%@include file="../common/css.jsp"%>
        <!-- EOF CSS INCLUDE -->
    </head>
    <body onload="document.getElementById('name').focus();">
        <!-- START PAGE CONTAINER -->
        <div class="page-container page-navigation-toggled page-container-wide">
            <!-- PAGE CONTENT -->
            <div class="page-content">
                <!-- START TOPBAR -->
                <ul class="x-navigation x-navigation-horizontal x-navigation-panel">
                    <li class="xn-titleText">
                        Performance Evaluation
                    </li>
                </ul>
                <!-- END TOPBAR -->

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">
                        <div class="col-md-12">
                            <form:form cssClass="form-horizontal" commandName="user" method="POST" name="form1" id="form1">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><spring:message code="title.register"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="name"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:input path="name" cssClass="form-control"/>
                                                <span class="help-block">Please enter your name</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="designation"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:input path="designation" cssClass="form-control"/>
                                                <span class="help-block">Please enter your designation</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="level"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:input path="level" cssClass="form-control"/>
                                                <span class="help-block">Please enter your level</span>
                                            </div>
                                        </div>        
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="location"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:input path="location" cssClass="form-control"/>
                                                <span class="help-block">Please enter your location</span>
                                            </div>
                                        </div>        
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="mobileNo"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:input path="phoneNo" maxlength="10" cssClass="form-control"/>
                                                <span class="help-block">Please enter 10 digit mobile number</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="emailId"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:input path="username" cssClass="form-control"/>
                                                <span class="help-block">Please enter email-id</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="password"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:password path="password" cssClass="form-control"/>
                                                <span class="help-block">Please enter new password</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="confirmPassword"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <input type="password" id="repassword" name="repassword" Class="form-control"/>
                                                <span class="help-block">Please enter new password again</span>
                                            </div>
                                        </div>        
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label">Captcha text</label>
                                            <div class="col-md-6 col-xs-12">
                                                <div>${capcha}<br><span style="color: #da4453;">${message}</span></div>
                                                <span class="help-block">Please enter capcha text</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-2 col-xs-12 control-label">
                                                <spring:hasBindErrors name="user">
                                                    <div class="text-danger">
                                                        <form:errors path="*"/>
                                                    </div>
                                                </spring:hasBindErrors>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel-footer">
                                        <div class="pull-right">
                                            <button type="submit" id="_submit" class="btn btn-success" name="btnSubmit"><spring:message code="button.Submit"/></button>
                                            <button type="submit" id="_cancel" class="btn btn-primary" name="_cancel" formnovalidate="formnovalidate"><spring:message code="button.Cancel"/></button>
                                        </div>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
                <!-- END PAGE CONTENT WRAPPER -->
            </div>
            <!-- END PAGE CONTENT -->
        </div>
        <!-- END PAGE CONTAINER -->

        <%@include file="../common/script.jsp" %>
        <!-- START THIS PAGE PLUGINS-->
        <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
        <script type='text/javascript' src='../js/plugins/jquery-validation/jquery.validate.js'></script>
        <!-- END THIS PAGE PLUGINS-->

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->
        <script type="text/javascript">

        var jvalidate = $("#form1").validate({
            ignore: [],
            rules: {
                name: {
                    required: true
                },
                designation: {
                    required: true
                },
                level: {
                    required: true
                },
                location: {
                    required: true
                },
                phoneNo: {
                    required: true,
                    minlength: 10,
                    number: true
                },
                username: {
                    required: true,
                    email: true
                },
                password: {
                    required: true,
                    minlength: 4,
                    maxlength: 16
                },
                repassword: {
                    required: true,
                    minlength: 4,
                    maxlength: 16,
                    equalTo: "#password"
                },
            },
            errorPlacement: function (error, element) {
                if (element.hasClass('select')) {
                    error.insertAfter(".bootstrap-select");
                } else {
                    error.insertAfter(element);
                }
            }
        });

        </script>
    </body>
</html>
