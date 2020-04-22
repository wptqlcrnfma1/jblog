<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-3.4.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>

<script>

///////////////////////////////////////////////////////////
var listItemTemplate = new EJS({
	url: "${pageContext.request.contextPath }/assets/js/ejs/list-item-template.ejs"
});
var listTemplate = new EJS({
	url: "${pageContext.request.contextPath }/assets/js/ejs/list-template.ejs"
});
//////////////////////////////////////////////////////////////

var fetchList = function(response){
	var id = ${id};
	$.ajax({
		url: '${pageContext.request.contextPath }/api/blog/list/'+id,
		type: 'get',
		dataType: 'json',
		success: function(response){
			
			//rendering
			var html = listTemplate.render(response);
			$("#menu-title").after(html);
			
		},
		
		error: function(XHR, status, e){
			console.error(status + ":" + e); // 통신에러
		}
	});
}

$(function(){
		//////////////////////////////////////////////추가
		$('#add-form').submit(function(event){
			
		event.preventDefault();
 		var vo ={};
 		vo.id =${id};
 		vo.name  = $('#name').val();
 		vo.description = $('#description').val();
 		
 		$.ajax({
			url: '${pageContext.request.contextPath }/api/blog/add',
			async: true,
 			type: 'post',
 			dataType: 'json',
 			contentType: 'application/json',
 			data: JSON.stringify(vo), //vo를 string으로 바꿔준다.
 			success: function(response){
 				
 				if(response.result !="success"){
 					console.error(response.message);
 					return;
 				}
				
				var indexNumber = $('.admin-cat tr').last().index() + 1;
				response.data.indexNumber = indexNumber;
				response.data.contextPath = "${pageContext.request.contextPath }";
		
				var html = listItemTemplate.render(response.data);
				
				$("#menu-title").after(html);

 			},
 			error: function(xhr, status, e){
 				console.error(status + ":" + e);
 			}
 		});
 	});
	
		$(document).on('click','.admin-cat tr td a ', function(event){
			event.preventDefault();
			
			var vo = {};
			var no = $(this).data('no');
			
			console.log("asd" + no);
			
			$.ajax({
				url: '${pageContext.request.contextPath }/api/blog/delete/'+ no,
				async: true,
				type: 'delete',
				dataType: 'json',
				
				data: '',
				success: function(response){
					
					if(response.result != "success"){
						console.error(response.message);
						return;
					}
					
					var indexNumber = $('.admin-cat tr').last().index() + 1;
					response.data.indexNumber = indexNumber;
					
					$(".admin-cat tr[data-no=" + no + "]").remove();
					
		            return;
					
				},
				error: function(xhr, status, e){
					console.error(status + ":" + e);
				}
			});
		});
		
		fetchList();
	});
	
</script>


</head>
<body>
	<div id="container">
		<div id="header">
			<h1>${blogVo.title }</h1>
			<ul>
				<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
				<li><a href="${pageContext.request.contextPath}/blog/${authUser.id}/admin">블로그 관리</a></li>
			</ul>
		</div>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.request.contextPath}/blog/${authUser.id}/admin">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a href="${pageContext.request.contextPath}/blog/${authUser.id}/write">글작성</a></li>
				</ul>
				
				
				<table class="admin-cat">
					<tr id="menu-title">
                  		<th>번호</th>
                		  <th>카테고리명</th>
                 		 <th>포스트 수</th>
                		  <th>설명</th>
               			   <th>삭제</th>
              		</tr>
              		
              		
              		
				</table>
			
      	
      		<form id="add-form" method="post" enctype="multipart/form-data">
      			<h4 class="n-c">새로운 카테고리 추가</h4>
		      	<table id="admin-cat-add">
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td><input type="text" name="name" id = "name"></td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input type="text" name="description" id = "description"></td>
		      		</tr>
		      		<tr>
		      			<td class="s">&nbsp;</td>
		      			<td><input id = 'addForm' type="submit" value="카테고리 추가"></td>
		      		</tr>      		      		
		      	</table> 
		      	</form>
			</div>
		</div>
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>