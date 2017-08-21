<%-- 
    Document   : sidebar
    Created on : 1 Oct, 2016, 11:06:11 AM
    Author     : Suraj
--%>

<!-- START PAGE SIDEBAR -->
<div class="page-sidebar toggled">
    <!-- START X-NAVIGATION -->
    <ul class="x-navigation x-navigation-minimized">
        <li class="xn-logo">
            <a href="../home/home.htm"><div class="xn-logoImage"></div></a>
            <a href="#" class="x-navigation-control"></a>
        </li>
        <li class="xn-profile">
            <a href="#" class="profile-mini">
                <img src="../assets/images/users/no-image.jpg" alt='<c:out value="${curUser.username}"/>'/>
            </a>
            <div class="profile">
                <div class="profile-image">
                    <img src="../assets/images/users/no-image.jpg" alt='<c:out value="${curUser.username}"/>'/>
                </div>
                <div class="profile-data">
                    <div class="profile-data-name"><c:out value="${curUser.username}"/></div>
                </div>
            </div>
        </li>

        <li class="active">
            <a href="../home/home.htm"><span class="fa fa-home"></span> <span class="xn-text">Home</span></a>
        </li>
        <sec:authorize access="hasAnyRole('ROLE_BF_RELOAD_APP_LAYER,ROLE_BF_CREATE_USER,ROLE_BF_LIST_USER')">
            <li class="xn-openable">
                <a href="#" title="Admin"><span class="fa fa-user"></span><span class="xn-text">Admin</span></a>
                <ul>
                    <sec:authorize access="hasAnyRole('ROLE_BF_RELOAD_APP_LAYER')">
                        <li><a href="../admin/reloadApplicationLayer.htm"><span class="fa fa-refresh fa-spin"></span>Reload application</a></li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_BF_CREATE_USER,ROLE_BF_LIST_USER')">
                        <li class="xn-openable">
                            <a href="#"><span class="fa fa-user"></span>User</a>
                            <ul>
                                <sec:authorize access="hasAnyRole('ROLE_BF_CREATE_USER')"><li><a href="../admin/addUser.htm"><span class="fa fa-plus"></span>Add User</a></li></sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_BF_LIST_USER')"><li><a href="../admin/userList.htm"><span class="fa fa-list-alt"></span>List User</a></li></sec:authorize>
                            </ul>
                        </li>
                    </sec:authorize>
                </ul>
            </li>
        </sec:authorize>

        <sec:authorize access="hasAnyRole('ROLE_BF_ADD_TASK,ROLE_BF_LIST_TASK')">
            <li class="xn-openable">
                <a href="#" title="Task"><span class="fa fa-tasks"></span><span class="xn-text">Task</span></a>
                <ul>
                    <sec:authorize access="hasAnyRole('ROLE_BF_ADD_TASK')"><li><a href="../task/addTask.htm"><span class="fa fa-plus"></span>Add Task</a></li></sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_BF_LIST_TASK')"><li><a href="../task/taskList.htm"><span class="fa fa-list-alt"></span>List Task</a></li></sec:authorize>
                </ul>
            </li>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_BF_REPORT_ACCESS_LOG')">
            <li class="xn-openable">
                <a href="#" title="Reports"><span class="fa fa-file-text-o"></span><span class="xn-text">Reports</span></a>
                <ul>
                    <sec:authorize access="hasAnyRole('ROLE_BF_REPORT_ACCESS_LOG')">
                        <li>
                            <a href="../report/reportAccessLog.htm"><span class="fa fa-th-list"></span>Access Log</a>
                        </li>
                    </sec:authorize>
                </ul>
            </li>
        </sec:authorize>
    </ul>
    <!-- END X-NAVIGATION -->
</div>
<!-- END PAGE SIDEBAR -->