<%@ page import="java.util.List" %>
<%@ page import="kz.bitlab.academy.news.entity.NewsEntity" %>
<%@ page import="kz.bitlab.academy.categories.entity.CategoryEntity" %>
<%@ page import="kz.bitlab.academy.users.entity.UserEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="bootstrap.jsp"%>
</head>
<body>
    <%@include file="nav.jsp"%>

    <div class="container">

        <%
            if (currentUser != null && currentUser.getRoleId() == 1) {
        %>
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addNews">
            ADD NEWS
        </button>
        <%
            }
        %>

        <%
            List<NewsEntity> newsList = (List<NewsEntity>) request.getAttribute("newsList");
            for (NewsEntity news : newsList) {
        %>
        <div class="p-4 p-md-5 mb-4 rounded text-body-emphasis bg-body-secondary">
            <div class="col-lg-6 px-0">
                <h1 class="display-4 fst-italic"><%=news.getTitle()%> posted at <b><%=news.getPostDate()%></b></h1>
                <p class="lead my-3"><%=news.getContent()%></p>
                <p class="lead my-3"><%=news.getCategory().getName()%></p>
                <p class="lead mb-0"><a href="/details?id=<%=news.getId()%>" class="text-body-emphasis fw-bold">Continue reading...</a></p>
            </div>
        </div>
        <%
            }
        %>
        <form class="form-control w-30" action="/" method="post">
            <div class="modal fade" id="addNews" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="form-floating">
                                <input class="form-control" id="floatingInput" placeholder="Test title" name="title">
                                <label for="floatingInput">TITLE:</label>
                            </div>
                            <div class="form-floating">
                                <label>CONTENT:</label>
                                <textarea class="form-control" placeholder="Test content..." name="content"></textarea>
                            </div>
                            <div class="form-floating">
                                <select class="form-select" name="categoryId">
                                    <%
                                        List<CategoryEntity> categories = (List<CategoryEntity>) request.getAttribute("categories");
                                        for (CategoryEntity category : categories) {
                                    %>
                                    <option value="<%=category.getId()%>"><%=category.getName()%></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button class="btn btn-success">ADD</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>

</body>
</html>
