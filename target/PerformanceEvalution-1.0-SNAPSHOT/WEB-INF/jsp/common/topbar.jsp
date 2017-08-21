<%-- 
    Document   : topbar
    Created on : 1 Oct, 2016, 11:08:59 AM
    Author     : Suraj
--%>

<!-- START X-NAVIGATION VERTICAL -->
<ul class="x-navigation x-navigation-horizontal x-navigation-panel">
    <!-- TOGGLE NAVIGATION -->
    <li class="xn-icon-button">
        <a href="#" class="x-navigation-minimize"><span class="fa fa-dedent"></span></a>
    </li>
    <li class="xn-titleText">
        Performance Evalution
    </li>
    <!-- SIGN OUT -->
    <li class="xn-icon-button pull-right">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <a href="#" class="mb-control" data-box="#mb-signout" title="Logout"><span class="fa fa-sign-out"></span></a>                        
    </li> 
    <li class="xn-icon-button pull-right">
        <a href="../home/changePassword.htm" title="Change Password"><span class="fa fa-key"></span> <span class="xn-text"></span></a>
    </li>
    <!-- END SIGN OUT -->
    <!-- END TOGGLE NAVIGATION -->
</ul>
<!-- END X-NAVIGATION VERTICAL --> 