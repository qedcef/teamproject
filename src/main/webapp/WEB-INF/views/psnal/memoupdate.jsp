<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<script>
	$(document).ready(){
		$("#title").click(){
			if($("#title").val()==""){
				alert("제목을 입력하세요");
				$("#title").focus();
				return false;
			}else{
				$("#title").submit();
			}
		}
	}
</script>
<%@ include file="../include/head.jsp"%>
<body class="sb-nav-fixed">

	<%@ include file="../include/nav_bar.jsp"%>

	<div id="layoutSidenav">
	
		<%@ include file="../include/layoutSidenav.jsp"%>

		<div id="layoutSidenav_content">
			<main>
				<div class="box-body">
				<div>
				<h2>게시글 수정</h2>
				</div>
				<form role="form" id="regform" enctype="multipart/form-data" method="post">
				<div class="form-group">
					<label>제목</label> <input type="text" name="mm_title"
						id="title" class="form-control" value="${memo.mm_title}"/>
				</div>

				<div class="form-group">
					<label>내용</label>
					<textarea name="mm_contents" rows="5" class="form-control">${memo.mm_contents}</textarea>
				</div>
				<div>
					<input type="file" name="uploadFile" value="파일 선택">
				</div>
				<c:if test="${not empty memo.mm_filename}">
				<div class="form-group">
					<label>첨부파일</label>
					<img alt="" src="${contextPath }/resources/images/memo/${memo.mm_filename}" width="500px" height="500px">
				</div>
				</c:if>
				<input type="hidden" name="mm_filename" value="${memo.mm_filename }">
			</div>
			<div class="box-footer">
			<button type="submit" onclick="titleSC()" class="btn btn-warning">수정완료</button>
			<button type="reset" class="btn btn-danger">다시쓰기</button>
			<button class="btn btn-primary">목록</button>
			</div>
<script>
	$(function(){
		$("#primary").click(function(){
			location.href="main?ui_id=${user.ui_id}";
		});
	})
</script>
				</form>
		</main>
			<%@ include file="../include/footer.jsp"%>
		</div>
	</div>
	<%@ include file="../include/plugin.jsp"%>
</body>
</html>
