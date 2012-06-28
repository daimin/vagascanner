<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文档管理</title>
<!-- 设置资源的的初始化路径 -->
<base href="<?php echo base_url();?>"/>
<base src="<?php echo base_url();?>"/>
<link rel="stylesheet" type="text/css" href="css/base.css" />
<script type="text/javascript" src="js/jquery-1.4.2.min.js" ></script>
<script language="javascript">
function viewArc(aid){
	if(aid==0) aid = getOneItem();
	window.open("archives.asp?aid="+aid+"&action=viewArchives");
}
function editArc(aid){
	if(aid==0) aid = getOneItem();
	location="archives.asp?aid="+aid+"&action=editArchives";
}
function updateArc(aid){
	var qstr=getCheckboxItem();
	if(aid==0) aid = getOneItem();
	location="archives.asp?aid="+aid+"&action=makeArchives&qstr="+qstr+"";
}
function checkArc(aid){
	var qstr=getCheckboxItem();
	if(aid==0) aid = getOneItem();
	location="archives.asp?aid="+aid+"&action=checkArchives&qstr="+qstr+"";
}
function moveArc(aid){
	var qstr=getCheckboxItem();
	if(aid==0) aid = getOneItem();
	location="archives.asp?aid="+aid+"&action=moveArchives&qstr="+qstr+"";
}
function adArc(aid){
	var qstr=getCheckboxItem();
	if(aid==0) aid = getOneItem();
	location="archives.asp?aid="+aid+"&action=commendArchives&qstr="+qstr+"";
}
function delArc(aid){
	var qstr = getCheckboxItem();
	if(qstr == ""){
		 alert("选择要删除的新闻");
		 return;
	}

	var args = {
        "nid":encodeURIComponent(qstr)
    };
    
    $.post("<?=site_url("main/new_del")?>",args, function (data, textStatus){
    	window.location.reload();
	});
}


//获得选中文件的文件名
function getCheckboxItem()
{
	var allSel="";
	if(document.form2.id.value) return document.form2.id.value;
	for(i=0;i<document.form2.id.length;i++)
	{
		if(document.form2.id[i].checked)
		{
			if(allSel=="")
				allSel=document.form2.id[i].value;
			else
				allSel=allSel+"|"+document.form2.id[i].value;
		}
	}
	
	return allSel;
}


function selAll()
{
	for(i=0;i<document.form2.id.length;i++)
	{
		if(!document.form2.id[i].checked)
		{
			document.form2.id[i].checked=true;
		}
	}
}
function noSelAll()
{
	for(i=0;i<document.form2.id.length;i++)
	{
		if(document.form2.id[i].checked)
		{
			document.form2.id[i].checked=false;
		}
	}
}

function add_new(){
	window.location = "<?php echo site_url("main/new_add");?>";
}
</script>
</head>
<body leftmargin="8" topmargin="8" >

<!--  快速转换位置按钮  -->
<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D1DDAA" align="center">
<tr>
 <td height="26">
  <table width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr>
  <td align="left">
    &nbsp;&nbsp;<input type='button' class="coolbg np" onClick="add_new()" value='添加新闻' />
 </td>
 </tr>
</table>
</td>
</tr>
</table>
  
<!--  内容列表   -->
<form name="form2">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="24" colspan="10" >&nbsp;新闻列表&nbsp;</td>
</tr>
<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="6%">ID</td>
	<td width="4%">选择</td>
	<td width="28%">新闻标题</td>
	<td width="10%">录入时间</td>
	<td width="8%">发布人</td>
	<td width="8%">文件大小(K)</td>
	<td width="10%">操作</td>
</tr>
<?php foreach($query as $n){ 
?>
<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22" >
	<td><?php echo $n->id;?></td>
	<td><input name="id" type="checkbox" id="id" value="<?php echo $n->id?>" class="np"></td>
	<td align="left"><u><?php echo $n->title;?></u></td>
	<td><?php echo $n->createtime;?></td>
	<td>admin</td>
	<td><?php echo $n->filesize?></td>
	<td><a href="<?php echo site_url("main/new_edit/".$n->id); ?>">编辑</a>&nbsp;|&nbsp;<a href="<?php echo site_url("main/to_top/".$n->id."/".$n->idx); ?>">置顶</a></td>
</tr>
<?php } ?>


<tr bgcolor="#FAFAF1">
<td height="28" colspan="10">
	&nbsp;
	<a href="javascript:selAll()" class="coolbg">全选</a>
	<a href="javascript:noSelAll()" class="coolbg">取消</a>
	<a href="javascript:delArc(0)" class="coolbg">&nbsp;删除&nbsp;</a>
</td>
</tr>
<tr align="right" bgcolor="#EEF4EA">
	<td height="36" colspan="10" align="center">
		<?php echo $this->pagination->create_links();//生成页面连接 ?>

	</td>
</tr>
</table>

</form>

</body>
</html>