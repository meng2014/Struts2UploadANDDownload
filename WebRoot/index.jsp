<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<script type="text/javascript" src="<%=path%>/js/jquery-1.4.2.min.js">
</script>
		<script type="text/javascript" src="<%=path%>/js/jquery.form.js">
</script>
		
		<script type="text/javascript" >
function uploadImage() {
	$(document)
			.ready(
					function() {
						var options = {
						//这里的file_uploadFile.action使用的是struts2的通配符形式，下同
							url : "<%=path%>/file_uploadFile.action",
							type : "POST",
							dataType : "script",
							success : function(msg) {
								if (msg.indexOf("#") > 0) {
									var data = msg.split("#");
									$("#tipDiv").html(data[0]);
									$("#showImage").html("<img src='<%=path%>/file_showImage.action?imageUrl="+data[1]+"'/>");
								} else {
									$("#tipDiv").html(msg);
								}
							},
						};
						$("#form2").ajaxSubmit(options);
						return false;
					});
}


function uploadFile() {
	$(document)
			.ready(
					function() {
						var options = {
							url : "<%=path%>/file_uploadFile2.action",
							type : "POST",
							dataType : "script",
							success : function(msg) {
								if (msg.indexOf("#") > 0) {
									var data = msg.split("#");
									$("#tipDiv2").html(data[0]);
								} else {
									$("#tipDiv2").html(msg);
								}
							},
						};
						$("#form3").ajaxSubmit(options);
						return false;
					});
}
</script>
	</head>

	<body>
	<a href="downLoad.action?fileRealName=2014-2015xiaoli.jpg">下载"2014-2015xiaoli.jpg"需要有该文件</a>
	  <br>  <br>
	
	<!-- 提交的表单中包括文件时，必须指定enctype的值，且必须是multipart/form-data -->
		<form id="form3" method="post" enctype="multipart/form-data">
			<table width="500" border="1" cellspacing="1" cellpadding="10">
				<tr>
					<td height="25">
						<h1>上传文件</h1>
					</td>
					<td>
						<input id="fileupload" name="fileupload" type="file" />
						<div id="tipDiv2"></div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="button" class="right-button02"
							onclick="uploadFile()" value="上传" />
					</td>
				</tr>
			</table>
		</form>
        <br>
       
	
	
	 
	<!-- 提交的表单中包括文件时，必须指定enctype的值，且必须是multipart/form-data -->
		<form id="form2" method="post" enctype="multipart/form-data">
			<table width="500" border="1" cellspacing="1" cellpadding="10">
				<tr>
					<td height="25">
						<h1>上传图片</h1>
					</td>
					<td>
						<input id="fileupload" name="fileupload" type="file" />
						<div id="tipDiv"></div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="button" class="right-button02"
							onclick="uploadImage()" value="上传" />
					</td>
				</tr>
			</table>
		</form>
        <br>
                     图片预览
        <div id="showImage" style="width: 475px;height: 600px;"></div>
      
         <br>
        
        
	</body>
</html>
