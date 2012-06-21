<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻发布系统</title>
<!-- 设置资源的的初始化路径 -->
<base href="<?php echo base_url();?>"/>
<base src="<?php echo base_url();?>"/>
<style>
body
{
  scrollbar-base-color:#C0D586;
  scrollbar-arrow-color:#FFFFFF;
  scrollbar-shadow-color:DEEFC6;
}
</style>
</head>
<frameset rows="60,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="<?php echo site_url("main/top")?>" name="topFrame" scrolling="no">
  <frameset cols="180,*"  id="btFrame" frameborder="NO" border="0" framespacing="0">
    <frame src="<?php echo site_url("main/menu")?>" noresize name="menu" scrolling="yes">
    <frame src="<?php echo site_url("main/new_list")?>" noresize name="main" scrolling="yes">
  </frameset>
</frameset>
<noframes>
	<body>您的浏览器不支持框架！</body>
</noframes>
</html>