<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  

<html>
<head>
<title> 用户登录 </title>
</head>


<body>
<form action = "LoginServlet" method = "post">
<table>
	<tr>
		<td> 用户名： </td>
		<td> <input type = "text" name = "name"> </td>
	</tr>
	<tr>
		<td> 密码： </td>
		<td> <input type = "password" name = "password"> </td>
	</tr>
	<tr>
		<td> 验证码： </td>
		<td> <input type = "text" name = "code"> <img src = "./VerCodeServlet" onClick = 'this.src=this.src+"?"+Math.random()'> </td> 
		<td> 点击图片刷新 </td>
	</tr>
	<tr>
		<td colspan = "2">
		<input type = "submit" value = "登录"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>
