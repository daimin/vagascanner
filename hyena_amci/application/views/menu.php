
<html>
<head>
<title>menu</title>
<!-- 设置资源的的初始化路径 -->
<base href="<?php echo base_url();?>"/>
<base src="<?php echo base_url();?>"/>
<link rel="stylesheet" href="css/base.css" type="text/css" />
<link rel="stylesheet" href="css/menu.css" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language='javascript'>var curopenItem = '1';</script>
<script language="javascript" type="text/javascript" src="js/frame/menu.js"></script>
<base target="main" />
</head>
<body target="main">
<table width='99%' height="100%" border='0' cellspacing='0' cellpadding='0'>
  <tr>
    <td style='padding-left:3px;padding-top:8px' valign="top">
	<!-- Item 1 Strat -->
      <dl class='bitem'>
        <dt onClick='showHide("items1_1")'><b>常用操作</b></dt>
        <dd style='display:block' class='sitem' id='items1_1'>
          <ul class='sitemu'>
            <li>
              <div class='items'>
                <div class='fllct'><a href='<?php echo site_url("main/new_add")?>' target='main'>添加新闻</a></div>
<!--                <div class='flrct'> <a href='archives.html' target='main'><img src='<view>images/frame/addnews.gif</view>' alt='添加新闻' title='添加新闻'/></a> </div>-->
              </div>
            </li>
            <li>
              <div class='items'>
                <div class='fllct'><a href='<?php echo site_url("main/new_list")?>' target='main'>所有新闻</a></div>
<!--                <div class='flrct'> <a href='<url>New_list</url>' target='main'><img src='<view>images/frame/arrfc.gif</view>' alt='所有新闻' title='所有新闻'/></a> </div>-->
              </div>

          </ul>
        </dd>
      </dl>
      <!-- Item 1 End -->
      <!-- Item 2 Strat -->

      <!-- Item 2 End -->
	  </td>
  </tr>
</table>
</body>
</html>