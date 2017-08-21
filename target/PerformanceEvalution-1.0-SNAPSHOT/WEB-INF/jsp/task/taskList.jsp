<%-- 
    Document   : taskList
    Created on : 1 Oct, 2016, 1:19:41 PM
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
    <body>
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
                    <li><a href="#">List Task</a></li>
                </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">
                        <div class="col-md-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Task List</h3>
                                </div>
                                <div class="panel-body">
                                    <!-- START FILTER PANEL -->
                                    <div class="panel panel-warning">
                                        <div class="panel-body">
                                            <form name="form1" id="form1" method="post">
                                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                    <div class="row">
                                                        <div class="col-md-2">
                                                            <div class="form-group">
                                                                <label><spring:message code="startDt"/></label>
                                                            <input name="startDate" id="startDate" value="${startDate}" class="form-control datepicker"/>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label><spring:message code="stopDt"/></label>
                                                            <input  name="stopDate"  id="stopDate" value="${stopDate}" class="form-control datepicker"/>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label><spring:message code="username"/></label>
                                                            <select id="userId" name="userId" class="form-control select">
                                                                <option value="-1">-All-</option>
                                                                <c:forEach items="${userList}" var="user">
                                                                    <option value="${user.userId}" <c:if test="${userId==user.userId}">selected</c:if>>${user.username}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <div class="col-md-2 btn-filter">
                                                        <button type="submit"  class="btn-info btn-sm" name="btnSubmit"><spring:message code="button.Go"/></button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <!-- END FILTER PANEL -->

                                    <div class="row">
                                        <div class="col-md-12 scrollable">
                                            <table class="table datatable table-bordered" >
                                                <thead>
                                                    <tr>
                                                        <th>Client</th>
                                                        <th>Shift</th>
                                                        <th>Task Type</th>
                                                        <th>Task Date</th>
                                                        <th>Task</th>
                                                        <th>Task Details</th>
                                                        <th>Comments</th>
                                                        <th>Created By</th>
                                                        <th>Created Date</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${taskList}" var="item">
                                                        <tr id="task" data-task-id="${item.taskId}" class="clickableRow">
                                                            <td><c:out value="${item.client.clientName}"/></td>
                                                            <td><c:out value="${item.shiftTime.shiftTime}"/></td>
                                                            <td><c:out value="${item.taskType.taskTypeDesc}"/></td>
                                                            <td><fmt:formatDate value="${item.taskDate}" pattern="dd-MM-yyyy HH:mm"/></td>
                                                            <td><c:out value="${item.taskName}"/></td>
                                                            <td><c:out value="${item.taskDetails}"/></td>
                                                            <td><c:out value="${item.comments}"/></td>
                                                            <td><c:out value="${item.createdBy.username}"/></td>
                                                            <td><fmt:formatDate value="${item.createdDate}" pattern="dd-MM-yyyy HH:mm"/></td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- edit form page-->
                    <form name="form2" id="form2" action="" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" id="taskId" name="taskId"/>
                    </form>
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
        <script type="text/javascript" src="../js/plugins/datatables/jquery.dataTables.min.js"></script>    
        <!-- END THIS PAGE PLUGINS-->        

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>        
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript" defer="defer">
            //functions for edit task and reset failed attempts links
            <sec:authorize access="hasAnyRole('ROLE_BF_EDIT_TASK')">
            $('#task td').click(function () {
                $('#taskId').val($(this).parent().data("task-id"));
                $('#form2').prop('action', '../task/showEditTask.htm');
                $('#form2').submit();
            });
            </sec:authorize>

            //disble automatic table sorting
            $('.table').dataTable({
                "order": []
            });
        </script>
    </body>
</html>
