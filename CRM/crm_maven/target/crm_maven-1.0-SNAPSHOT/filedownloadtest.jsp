<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 设置动态初始访问路径（这里本地是http://127.0.0.1:8080/crm/）
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <title>file download</title>
    <script type="text/javascript">
        $(function (){
            $("#fileDownloadBtn").click(function (){
                //all requests about downloading files are sync request
                window.location.href="workbench/activity/fileDownload.do";
            });
        });
    </script>

</head>
<body>
<input type="button" value="download" id="fileDownloadBtn">
</body>
</html>
