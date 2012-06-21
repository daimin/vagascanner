<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑新闻</title>
<!-- 设置资源的的初始化路径 -->
<base href="<?php echo base_url();?>"/>
<base src="<?php echo base_url();?>"/>
<style type="text/css">
<!--
body { background-image: url(images/frame/allbg.gif); }
.multipic {
	border: 1px dashed #FC6;
}
.albCt {
	border-bottom: 1px dashed #FC0;
	margin-bottom: 10px;
	padding-bottom:10px;
}
.albCt img{
	cursor:pointer;
}
-->
</style>
<link href="css/base.css" rel="stylesheet" type="text/css">
<link href="css/tb-box.css" rel="stylesheet" type="text/css">
<script language="javascript">
<!--
var swfu = null;
var arctype = 'article';
function checkSubmit()
{
	if(document.form1.title.value=='')
	{
		alert('文章标题不能为空！');
		return false;
	}
	if(document.form1.resume.value==0)
	{
		alert('文章摘要不能为空！');
		return false;
	}

	var newcont = document.getElementById("content");
	
	newcont.value = editor.html();
}

-->
</script>
<script src="js/config.js" type="text/javascript"></script>
<style type="text/css" id="cke_ui_color">
.cke_1 .cke_wrapper,.cke_1_dialog .cke_dialog_contents,.cke_1_dialog a.cke_dialog_tab,.cke_1_dialog .cke_dialog_footer{background-color: #F1F5F2 !important;}
</style>
<link href="css/editor.css" type="text/css" rel="stylesheet">
</head>
<body onpageshow='event.persisted &amp;&amp; (function(){var allInstances = CKEDITOR.instances, editor, doc;for ( var i in allInstances ){	editor = allInstances[ i ];	doc = editor.document;	if ( doc )	{		doc.$.designMode = "off";		doc.$.designMode = "on";	}}})();' topmargin="8">
  <table align="center" border="0" cellpadding="0" cellspacing="0" width="98%">
    <tbody><tr>
      <td height="30" width="60%">
      <img src="images/book1.gif" height="14" width="20">&nbsp;<a href="<?php echo site_url("main/new_list")?>"><u>新闻列表</u></a> &gt;&gt; 编辑新闻</td>

    </tr>
  </tbody></table>
  <?php 
  $new = $query;

  ?>

<form name="form1" action="<?php echo site_url("main/new_edit/".$new->id);?>" enctype="multipart/form-data" method="post" onsubmit="return checkSubmit()">
  <input name="id" value="<?php echo $new->id?>" type="hidden">
  <input name="action" value="edit" type="hidden">
  <input name="cont_file" value="<?php echo $new->cont_file ?>" type="hidden">
  <table id="needset" style="border: 1px solid rgb(207, 207, 207); background: none repeat scroll 0% 0% rgb(255, 255, 255);" align="center" border="0" cellpadding="2" cellspacing="2" width="98%">
    <tbody><tr>
      <td colspan="5" class="bline" height="24">
      	<table border="0" cellpadding="0" cellspacing="0" width="800">
          <tbody><tr>
            <td width="90">&nbsp;文章标题：</td>
            <td width="408" colspan="3"><input name="title" id="title" value="<?php echo $new->title?>" style="width: 388px;" type="text"></td>
          </tr>
        </tbody></table></td>
    </tr>


    <tr>
      <td colspan="5" class="bline" height="24">
      <table border="0" cellpadding="0" cellspacing="0" width="800">
        <tbody><tr>
          <td width="90">&nbsp;内容摘要：</td>
          <td width="449"><textarea name="resume" rows="5" id="resume" style="width: 100%; height: 50px;"><?php echo $new->resume?></textarea></td>
          <td width="261">&nbsp;</td>
        </tr>
      </tbody></table>
      </td>
  </tr>

    <tr>
      <td colspan="2" class="bline2" bgcolor="#F9FCEF" height="28">
      	<div style="float:left;line-height:28px;">&nbsp;<strong>文章内容：</strong></div>
      	<div style="float:right;;padding-right:8px">
        </div>
        <div style="float:right;padding-right:8px">
        </div>
      </td>
    </tr>
    <tr>
      <td id="arcBody" width="100%">
      <textarea id="content" name="content" style="width:90%;height:400px;visibility:hidden" ><?php echo $new->content?></textarea>
     
    </td>
    </tr>
  </tbody></table>

   <table style="border-right: 1px solid rgb(207, 207, 207); border-width: medium 1px 1px; border-style: none solid solid; border-color: -moz-use-text-color rgb(207, 207, 207) rgb(207, 207, 207); -moz-border-top-colors: none; -moz-border-right-colors: none; -moz-border-bottom-colors: none; -moz-border-left-colors: none; -moz-border-image: none;" align="center" bgcolor="#F9FCEF" border="0" cellpadding="0" cellspacing="0" width="98%">
   <tbody><tr height="35">
    <td width="17%">&nbsp;</td>
    <td width="83%"><input name="imageField" src="images/button_save.gif" class="np" style="cursor: pointer;" border="0" height="22" type="image" width="60">
       <img src="images/button_reset.gif" onclick="location.reload();" style="cursor: pointer;" border="0" height="22" width="60">
    </td>
   </tr>
</tbody></table>
</form>
<div id="__tmpbody" style="display:none"></div>

<script type="text/javascript" charset="utf-8" src="kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="kindeditor/lang/zh_CN.js"></script>

<script type="text/javascript">

	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			resizeType : 1,
			allowPreviewEmoticons : false,
			allowImageUpload : true,
			items : [
				 		'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
				 		'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
				 		'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
				 		'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
				 		'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
				 		'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
				 		'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
				 		'anchor', 'link', 'unlink', '|', 'about'
				 	],
			imageUploadJson:"upload/image"
			
		});
	});

</script>
</body>

</html>