<%-- 
    Document   : addTask
    Created on : 5 Aug, 2017, 1:08:34 PM
    Author     : Suraj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    <body onLoad="document.getElementById('client.clientId').focus();">
        <!-- START PAGE CONTAINER -->
        <div class="page-container page-navigation-toggled page-container-wide">
            <%@include file="../common/sidebar.jsp" %>

            <!-- PAGE CONTENT -->
            <div class="page-content">
                <%@include file="../common/topbar.jsp" %>

                <!-- START BREADCRUMB -->
                <ul class="breadcrumb">
                    <li><a href="../home/home.htm">Home</a></li>
                    <li><a href="../home/home.htm">Task</a></li>
                    <li><a href="#">Add Task</a></li>
                </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">
                        <div class="col-md-12">
                            <form:form cssClass="form-horizontal" commandName="task" method="POST" name="form1" id="form1">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Add Task</h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label">Client</label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:select path="client.clientId" cssClass="form-control select">
                                                    <form:option value="" label="Nothing selected"/>
                                                    <form:options items="${clientList}" itemLabel="clientName" itemValue="clientId"/>
                                                </form:select>
                                                <span class="help-block">Please enter client</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label">Shift</label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:select path="shiftTime.shiftTimeId" cssClass="form-control select">
                                                    <form:option value="" label="Nothing selected"/>
                                                    <form:options items="${shiftTimeList}" itemLabel="shiftTime" itemValue="shiftTimeId"/>
                                                </form:select>
                                                <span class="help-block">Please select Shift</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label">Task Type</label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:select path="taskType.taskTypeId" cssClass="form-control select">
                                                    <form:option value="" label="Nothing selected"/>
                                                    <form:options items="${taskTypeList}" itemLabel="taskTypeDesc" itemValue="taskTypeId"/>
                                                </form:select>
                                                <span class="help-block">Please select task type</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label">Task Name</label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:input path="taskName" cssClass="form-control " />
                                                <span class="help-block">Please enter task name</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label">Task Date & Time</label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:input path="taskDate" cssClass="form-control " />
                                                <span class="help-block">Please select task date and time</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label">Task Details</label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:textarea path="taskDetails" cssClass="form-control " />
                                                <span class="help-block">Please enter task details</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label">Comments</label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:textarea path="comments" cssClass="form-control " />
                                                <span class="help-block">Please enter comments</span>
                                            </div>
                                        </div>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    </div> 

                                    <div class="panel-footer">
                                        <div class="pull-right">
                                            <button type="submit" id="_submit" name="btnSubmit"  class="btn btn-success">Submit</button>
                                            <button type="button" id="_cancel" name="_cancel" class="btn btn-primary" formnovalidate="formnovalidate"><spring:message code="button.Cancel"/></button>
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

        <%@include file="../common/messagebox.jsp" %>

        <%@include file="../common/script.jsp" %>
        <!-- START THIS PAGE PLUGINS-->
        <script type='text/javascript' src='../js/plugins/icheck/icheck.min.js'></script>
        <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-select.js"></script>
        <script type='text/javascript' src='../js/plugins/jquery-validation/jquery.validate.js'></script>           
        <script type="text/javascript" src="../js/plugins/bootstrap/moment.js"></script>
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-datetimepicker.js"></script>    
        <!-- END THIS PAGE PLUGINS-->

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript">
        $('#taskDate').datetimepicker({format: 'DD-MM-YYYY HH:mm'});
        var jvalidate = $("#form1").validate({
            ignore: [],
            rules: {
                'client.clientId': {
                    required: true
                },
                'shiftTime.shiftTimeId': {
                    required: true
                },
                'taskType.taskTypeId': {
                    required: true
                },
                'taskName': {
                    required: true
                },
                'taskDate': {
                    required: true
                },
                'taskDetails': {
                    required: true
                },
                comments: {
                    required: true
                }
            },
            errorPlacement: function (error, element) {
                if (element.hasClass('select')) {
                    error.insertAfter(element.next(".bootstrap-select"));
                    element.next("div").addClass("error");
                } else {
                    error.insertAfter(element);
                }
            }
        });
        //for dropdown 
        $('.select').on('change', function () {
            if ($(this).val() != "") {
                $(this).valid();
                $(this).next('div').addClass('valid');
            } else {
                $(this).next('div').removeClass('valid');
            }
        });
        $('#_cancel').click(function () {
            window.location = "../task/taskList.htm?msg=msg.actionCancelled" ;
        });
        </script>
    </body>
</html>