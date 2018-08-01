<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
 <title>首页</title>
</head>
<body>
<%--<a href="/initProduceBasicGet">RabbitMQ生产者_BasicGet</a><br>
<a href="/initProduceConsume">RabbitMQ生产者_BasicConsume</a><br>--%>
<h1>Java内部测试</h1>
<a href="<%=contextPath%>/initSingleQueue">RabbitMQ_SingleQueue</a><br>
<a href="<%=contextPath%>/initProducePublish">RabbitMQ_Publish</a><br>
<a href="<%=contextPath%>/initRouting">RabbitMQ_Routing(Exchange不为空)</a><br>
<a href="<%=contextPath%>/initRouting1">RabbitMQ_Routing(Exchange为空)</a><br>
<a href="<%=contextPath%>/initProduceTopic">RabbitMQ_Topic(Exchange不为空)</a><br>
<a href="<%=contextPath%>/initProduceTopic1">RabbitMQ_Topic(Exchange为空)</a><br>
</body>
</html>