<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="../include/head.jsp"%>
<body class="sb-nav-fixed">

	<%@ include file="../include/nav_bar.jsp"%>

	<div id="layoutSidenav">
	
		<%@ include file="../include/layoutSidenav.jsp"%>

		<div id="layoutSidenav_content">
			<main>
				<div class="box-body">
				<div class="form-group">
					<label>제목</label> <input type="text" name="mm_title"
						class="form-control" value="${memo.mm_title}" readonly="readonly" />
				</div>

				<div class="form-group">
					<label>내용</label>
					<textarea name="mm_contents" rows="5" readonly="readonly"
						class="form-control">${memo.mm_contents}</textarea>
				</div>
				
			<c:if test="${not empty memo.mm_filename}">
				<div class="form-group">
					<label>첨부파일</label>
					<img alt="" src="${contextPath }/resources/images/memo/${memo.mm_filename}" width="500px" height="500px">
					<input type="button" value="삭제" onclick="location.href='delfile?mm_mno=${memo.mm_mno}'">
				</div>
			</c:if>
			</div>
			<div class="box-footer">
			<button class="btn btn-warning" id="warning">수정</button>
			<button class="btn btn-danger" id="danger">삭제</button>
			<button class="btn btn-primary" id="primary">목록</button>
		</div>
<script>
	$(function(){
		$("#warning").click(function(){
			location.href="update?mm_mno=" + ${memo.mm_mno};
		});
		$("#danger").click(function(){
			location.href="delete?mm_mno=" + ${memo.mm_mno} + "&ui_id=${user.ui_id}";
		});
		$("#primary").click(function(){
			location.href="main?ui_id=${user.ui_id}";
		});
	})
</script>
			</main>
			<%@ include file="../include/footer.jsp"%>
		</div>
	</div>
	<%@ include file="../include/plugin.jsp"%>
</body>
</html>
