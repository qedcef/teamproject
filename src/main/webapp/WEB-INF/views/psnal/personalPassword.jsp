<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="../include/head.jsp"%>
<script>
	$('document').ready(function(){
		
		$("#pw").focus();
		
		$("#btn-yes").click(function(){
			var r_pw = '${user.ui_pw}';
			var pw = $("#pw").val();
	
			if(pw == r_pw){
				location.href='${contextPath}/persnal/update?ui_id=' + '${user.ui_id}';
			}else{
				alert('비밀번호가 틀렸습니다. 다시 입력해주세요.');
				$("#pw").focus();
			}
		});
	
	
	
	});
	
	</script>
<body class="sb-nav-fixed">

	<%@ include file="../include/nav_bar.jsp"%>

	<div id="layoutSidenav">

		<%@ include file="../include/layoutSidenav.jsp"%>

		<div id="layoutSidenav_content">
			<main>
			
				<link rel="stylesheet" href="${contextPath }/resources/assets/css/loginform.css" />


	<div class="card align-middle" style="width:20rem; border-radius:20px;">
		<div class="card-title" style="margin-top:30px;">
			<h2 class="card-title text-center" style="color:#113366;">비밀번호 확인</h2>
		</div>
		<div class="card-body">
        <h5 class="form-signin-heading">비밀번호를 입력하세요</h5>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="pw" name="pw" class="form-control" placeholder="Password" required>
        <small style="color: green; font-weight: bold;">** kakao, naver 로 가입해 처음 로그인하신 회원분들의 기본 비밀번호는 1234 입니다.</small><br>
        <small style="color: red; font-weight: bold;">** 내정보에서 비밀번호를 변경하신 회원분들은 로그아웃 후 다시 로그인 하셔서 입력하세요.</small>
        <div class="checkbox">
         
        </div>
        <button id="btn-yes" class="btn btn-lg btn-primary btn-block" >확인</button>
      
		</div>
	</div>

	<div class="modal">
	</div>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script> 
			</main>
			<%@ include file="../include/footer.jsp"%>
		</div>
	</div>
	<%@ include file="../include/plugin.jsp"%>
</body>
</html>
