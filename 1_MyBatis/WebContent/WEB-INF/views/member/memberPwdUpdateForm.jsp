<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#pwdTable{margin: auto;}
</style>
</head>
<body>
	<jsp:include page="../common/menubar.jsp"/>
	<h1 align="center">비밀번호 수정</h1>
	<form action="${ contextPath }/mPwdUpdate.me" method="post" onsubmit="return send();">
		<table id="pwdTable">
			<tr>
				<td>* 현재 비밀번호</td>
				<td><input type="password" name="userPwd" required></td>
			</tr>
			<tr>
				<td>* 새 비밀번호</td>
				<td><input type="password" name="newPwd" required></td>
			</tr>
			<tr>
				<td>* 새 비밀번호 확인</td>
				<td><input type="password" name="newPwd2" required></td>
			</tr>
			<tr>
				<td colspan="2">
					<div align="center">
						<br>
						<input type="submit" value="수정하기">
						<button type="reset">취소</button>
					</div>
				</td>
			</tr>
		</table>
	</form>
	
	<script>
		function send(){
			var userPwd = $('input[name="userPwd"]');
			var newPwd = $('input[name="newPwd"]');
			var newPwd2 = $('input[name="newPwd2"]');
			
			if(newPwd.val().trim() != newPwd2.val().trim()){
				alert('비밀번호가 다릅니다.');
				newPwd2.val('');
				newPwd2.focus();
				return false;
				
			} else if(newPwd.val().trim() == '' || newPwd2.val().trim() == ''){
				alert('비밀번호를 입력해주세요');
				return false;
				
			} 
			return true;
			
		}
	</script>
	
</body>
</html>