<%@ page import="kz.bitlab.academy.users.entity.UserEntity" %>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">BITLAB NEWS</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/register">Register</a>
                </li>
                <%
                    if (request.getSession().getAttribute("currentUser") == null) {
                %>

                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/login">Login</a>
                </li>

                <%
                    }
                %>

                <%
                    UserEntity currentUser = (UserEntity) request.getSession().getAttribute("currentUser");
                    if (currentUser != null && currentUser.getRoleId() == 1) {
                %>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/categories">Categories</a>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</nav>