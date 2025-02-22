<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <title>Dashboard - Hao-huengmin</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Manage User</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item active"><a href="/admin">Dashborad</a></li>
                                    <li class="breadcrumb-item active">Dashboard</li>
                                </ol>
                            </div>
                            <div class="mt-5">
                                <div class="row">
                                    <div class=" col-12 mx-auto">
                                        <div class="d-flex justify-content-between ">
                                            <h3>Table users</h3>
                                            <a href="/admin/user/create" class="btn btn-primary">Create an
                                                user</a>
                                        </div>
                                        <table class="table table-bordered mt-3">
                                            <thead>
                                                <tr>
                                                    <th scope="col">ID</th>
                                                    <th scope="col">Email</th>
                                                    <th scope="col">FullName</th>

                                                    <th scope="col">Action</th>
                                                </tr>
                                            </thead>
                                            <c:forEach var="user" items="${users}">
                                                <tr>
                                                    <th>${user.id}</th>
                                                    <td>${user.email}</td>
                                                    <td>${user.name}</td>
                                                    <td>${user.role}</td>
                                                    <td>
                                                        <a href="/admin/user/${user.id}"
                                                            class="btn btn-primary">View</a>
                                                        <a href="/admin/user/update/${user.id}"
                                                            class="btn btn-success">Update</a>
                                                        <a href="/admin/user/delete/${user.id}"
                                                            class="btn btn-danger">Delete</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>

                                        <nav aria-label="Page navigation example">
                                            <ul class="pagination justify-content-center">
                                                <li class="page-item ${currentPage eq 1 ? 'disabled' : ''}">
                                                    <a class="page-link" href="/admin/user?page=${currentPage - 1}"
                                                        aria-label="Previous">
                                                        <span aria-hidden="true">&laquo;</span>
                                                    </a>
                                                </li>

                                                <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                                                    <li class="page-item ${(loop.index) eq currentPage ? 'active':'' }">
                                                        <a class="page-link"
                                                            href="/admin/user?page=${loop.index}">${loop.index}</a>
                                                    </li>
                                                </c:forEach>
                                                <li>
                                                    <a class="page-link ${currentPage eq totalPages ? 'disabled' : ''}"
                                                        href="/admin/user?page=${currentPage + 1}" aria-label="Next">
                                                        <span aria-hidden="true">&raquo;</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </nav>
                                    </div>
                                </div>
                            </div>
                        </main>
                        <!-- footer.jsp  -->
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="js/scripts.js"></script>
            </body>

            </html>