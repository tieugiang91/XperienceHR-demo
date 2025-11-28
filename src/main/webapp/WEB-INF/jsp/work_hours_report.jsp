<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Work Hours Report - XperienceHR</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .report-container {
            max-width: 1200px;
            margin: 40px auto;
        }
        .card {
            box-shadow: 0 0.125rem 0.25rem rgba(0,0,0,0.075);
            border: none;
        }
        .card-header {
            background-color: #0d6efd;
            color: white;
            font-weight: 600;
        }
        .table thead th {
            background-color: #f8f9fa;
            border-bottom: 2px solid #dee2e6;
        }
        .badge-role {
            font-size: 0.875rem;
            padding: 0.35em 0.65em;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-dark bg-primary">
        <div class="container-fluid">
            <span class="navbar-brand mb-0 h1">
                <i class="bi bi-clock-history"></i> XperienceHR Time Tracking
            </span>
            <div class="d-flex align-items-center">
                <span class="text-white me-3">
                    <i class="bi bi-person-circle"></i> ${username}
                    <c:if test="${isAdmin}">
                        <span class="badge bg-warning text-dark badge-role">ADMIN</span>
                    </c:if>
                    <c:if test="${!isAdmin}">
                        <span class="badge bg-info badge-role">EMPLOYEE</span>
                    </c:if>
                </span>
                <form action="/logout" method="post" class="d-inline">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-outline-light btn-sm">
                        <i class="bi bi-box-arrow-right"></i> Logout
                    </button>
                </form>
            </div>
        </div>
    </nav>

    <div class="container report-container">
        <div class="card mb-4">
            <div class="card-header">
                <i class="bi bi-funnel"></i> Filter Report
            </div>
            <div class="card-body">
                <form action="/report" method="get" id="reportForm">
                    <input type="hidden" name="page" id="pageField" value="0"/>
                    <div class="row g-3">
                        <div class="col-md-5">
                            <label for="startDate" class="form-label">Start Date</label>
                            <input type="datetime-local" class="form-control" id="startDate" name="startDate" 
                                   value="${startDateInput}" required>
                        </div>
                        <div class="col-md-5">
                            <label for="endDate" class="form-label">End Date</label>
                            <input type="datetime-local" class="form-control" id="endDate" name="endDate" 
                                   value="${endDateInput}" required>
                        </div>
                        <div class="col-md-2">
                            <label for="size" class="form-label">Page Size</label>
                            <select class="form-select" id="size" name="size">
                                <c:forEach var="option" items="${pageSizeOptions}">
                                    <option value="${option}" ${option == size ? 'selected' : ''}>${option}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-2 d-flex align-items-end">
                            <button type="submit" class="btn btn-primary w-100">
                                <i class="bi bi-search"></i> Generate
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="card">
            <div class="card-header">
                <i class="bi bi-table"></i> Work Hours Report
                <c:if test="${!isAdmin}">
                    <span class="badge bg-info float-end">Showing your records only</span>
                </c:if>
                <c:if test="${isAdmin}">
                    <span class="badge bg-warning text-dark float-end">Showing all employees</span>
                </c:if>
            </div>
            <div class="card-body">
                <c:if test="${empty reportData}">
                    <div class="alert alert-info" role="alert">
                        <i class="bi bi-info-circle"></i> No records found for the selected period.
                    </div>
                </c:if>
                
                <c:if test="${not empty reportData}">
                    <div class="table-responsive">
                        <table class="table table-hover table-striped">
                            <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col"><i class="bi bi-person"></i> Employee Name</th>
                                    <th scope="col"><i class="bi bi-folder"></i> Project Name</th>
                                    <th scope="col" class="text-end"><i class="bi bi-clock"></i> Total Hours</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="record" items="${reportData}" varStatus="status">
                                    <tr>
                                        <td>${page * size + status.index + 1}</td>
                                        <td><strong>${record.employeeName}</strong></td>
                                        <td>${record.projectName}</td>
                                        <td class="text-end">
                                            <span class="badge bg-primary">
                                                <fmt:formatNumber value="${record.totalHours}" maxFractionDigits="2"/> hrs
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot>
                                <tr class="table-secondary">
                                    <td colspan="3" class="text-end"><strong>Total Records:</strong></td>
                                    <td class="text-end"><strong>${totalRecords}</strong></td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </c:if>
                <c:if test="${reportPage.totalPages > 1}">
                    <div class="d-flex justify-content-between align-items-center mt-3">
                        <div>
                            <c:set var="startRecord" value="${reportData.isEmpty() ? 0 : page * size + 1}" />
                            <c:set var="endRecord" value="${page * size + reportData.size()}" />
                            <span class="text-muted">
                                Showing ${startRecord} - ${endRecord} of ${totalRecords} records
                            </span>
                        </div>
                        <nav>
                            <ul class="pagination mb-0">
                                <c:url var="prevUrl" value="/report">
                                    <c:param name="startDate" value="${startDateInput}"/>
                                    <c:param name="endDate" value="${endDateInput}"/>
                                    <c:param name="size" value="${size}"/>
                                    <c:param name="page" value="${page - 1}"/>
                                </c:url>
                                <c:url var="nextUrl" value="/report">
                                    <c:param name="startDate" value="${startDateInput}"/>
                                    <c:param name="endDate" value="${endDateInput}"/>
                                    <c:param name="size" value="${size}"/>
                                    <c:param name="page" value="${page + 1}"/>
                                </c:url>
                                <li class="page-item ${!reportPage.hasPrevious ? 'disabled' : ''}">
                                    <a class="page-link" href="${reportPage.hasPrevious ? prevUrl : '#'}">Previous</a>
                                </li>
                                <li class="page-item disabled">
                                    <span class="page-link">Page ${page + 1} of ${totalPages}</span>
                                </li>
                                <li class="page-item ${!reportPage.hasNext ? 'disabled' : ''}">
                                    <a class="page-link" href="${reportPage.hasNext ? nextUrl : '#'}">Next</a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('reportForm').addEventListener('submit', function () {
            document.getElementById('pageField').value = 0;
        });
    </script>
</body>
</html>
