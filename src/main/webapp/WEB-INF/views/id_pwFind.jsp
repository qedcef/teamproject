<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<%@ include file="include/head.jsp"%>

<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
<script>
	$('document').ready(function(){
		$("#findId").click(function(){
			var name = $("#name").val();
			var email = $("#id_email").val();

			fn_findId(name, email);
		})

		$("#findPw").click(function(){
			var id = $("#id").val();
			var email = $("#pw_email").val();

			fn_findPw(id, email);
		})
	})

	function fn_findId(name, email){
		var param = {
			"name": name,
			"email": email
		};

		$.ajax({
			url:"${contextPath}/login/findIdProc",
			data: param,
			type: 'POST',
			headers:{
				"Context-Type":"application/json"
			},
			success: function(result){
				console.log(result);
				if(result == "1"){
					alert('이름이 일치하지 않습니다.');
					$("#name").focus();
				}else if(result == "0"){
					alert('가입되어 있지 않은 이메일 입니다.');
					$("#id_email").focus();
				}else{
					var id = result;
					$("#h_id").val(id);
					$("#h_span").removeAttr("hidden");
					$("#name").attr("readonly", "readonly");
					$("#id_email").attr("readonly", "readonly");
				}
			},
			error: function(result){
				console.log('이름, 이메일 전송 실패');
			}
		});
	} // end of findId

	function fn_findPw(id, email){
		var param = {
			"id": id,
			"email": email
		};

		$.ajax({
			url:"${contextPath}/login/findPwProc",
			data: param,
			type: 'POST',
			headers:{
				"Context-Type":"application/json"
			},
			success: function(result){
				console.log(result);
				if(result == "1"){
					alert('아이디가 일치하지 않습니다.');
					$("#id").focus();
				}else if(result == "0"){
					alert('가입되어 있지 않은 이메일 입니다.');
					$("#pw_email").focus();
				}else{
					var id = result;
					alert('입력하신 이메일로 임시 비밀번호가 발급되었습니다. 로그인 해주세요.');
					location.href='${contextPath}/login';
				}
			},
			error: function(result){
				console.log('아이디, 이메일 전송 실패');
			}
		});
	}

</script>
<style>
	.rrow{
		width: 25rem;
		border-radius: 20px;
		margin-top: 6rem;
		margin-left: 40rem;
	}
	.card-body{
		margin-left: 90px;
	}
</style>
<body class="sb-nav-fixed">

	<%@ include file="include/nav_bar.jsp"%>

	<div id="layoutSidenav">

		<%@ include file="include/layoutSidenav.jsp"%>

		<div id="layoutSidenav_content">
			<main>
				<link rel="stylesheet" href="${contextPath}/resources/assets/css/loginform.css" />

				<div class="rrow">
					<div class="card">
						<div class="card-title">
							<h2 style="text-align: center;">아이디 찾기(Find ID)</h2>
						</div>
						<div class="card-body">
							<label>이름</label><br>
							<input type="text" id="name" name="name" >
							<p></p>
							<label>이메일</label><br>
							<input type="text" id="id_email" name="id_email">
							<p></p>
							<button class="btn btn-primary" style="margin-left: 30px;" id="findId">아이디 찾기</button>
						</div>
						<span id="h_span" style="margin-left: 30px;" hidden="true">찾으신 ID 는 
							<input id="h_id" type="text" readonly="readonly"> 입니다.</span>
					</div>
					<div class="card">
						<div class="card-title">
							<h2 style="text-align: center;">비밀번호 찾기(Find PW)</h2>
						</div>
						<div class="card-body">
							<label>아이디</label><br>
							<input type="text" id="id" name="id" >
							<p></p>
							<label>이메일</label><br>
							<input type="text" id="pw_email" name="pw_email">
							<p></p>
							<button class="btn btn-primary" style="margin-left: 30px;" id="findPw">비밀번호 찾기</button>
						</div>
					</div>
				</div>
			<div class="modal">
			</div>
    		<!-- Optional JavaScript -->
   			<!-- jQuery first, then Popper.js, then Bootstrap JS -->
		    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
		    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script> 
			</main>
			<%@ include file="include/footer.jsp"%>
		</div>
	</div>
	<%@ include file="include/plugin.jsp"%>
</body>
</html>
