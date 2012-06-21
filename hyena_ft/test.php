<?php
include "simple_html_dom.php";

//$str = '		   <div class="img_wrapper">  	<img alt="资料图：中国编号2002的第二架歼-20首飞成功" src="http://i2.sinaimg.cn/jc/2012-05-28/U6917P27T1D691625F3DT20120528091915.jpg" />  	<span class="img_descr">资料图：中国编号2002的第二架歼-20首飞成功</span>  </div>   				<!-- publish_helper name=\'原始正文\' p_id=\'27\' t_id=\'1\' d_id=\'691625\' f_id=\'2\' --> <p>　　据俄罗斯军工新闻网5月28日报道，从2011年初开始飞行试验以来，中国航空制造企业成都飞机工业集团公司已经组装了4架第5代歼击机歼-20的原型机，其外形和配置各不相同。</p>  <p>　　俄媒称，英国《简氏防务周刊》披露称，在中国成飞集团已经制造出的4架歼-20原型机中，目前只有两架编号分别为2001和2002的飞机正在执行试飞任务，其余两架飞机参与地面测试。在两架试飞型歼-20中，一架飞行验证机安装了两种不同的发动机。据推测，其中一台是俄罗斯生产的，可能是AL-31F或AL-41F发动机，而中国2011年从俄罗斯得到了150台AL-31F和AL-31FN发动机。另一台是中国国产WS-10A发动机。至于另外一架歼-20飞行验证机，安装的全是中国国产WS-10A发动机，由黎明航空发动机制造公司生产。</p>  <p>　　俄媒称，乌克兰和俄罗斯专家认为，中国歼-20研制项目有困难，比如，中国专家暂时未能完善WS系列发动机，使其能在新型歼击机上顺利使用。这些动力装置不具备高度可靠性的特点，推力也不够大。另外，中国暂时仍旧无法筛选出相当坚硬的材料，以增加WS-10A发动机的修理间隔寿命。</p>  <p>　　俄媒称，中国已经制造出来的四架歼-20原型机的机首部分形状、飞行员座舱风挡、水平前翼和前缘缝翼也各不相同。这可能意味着，成飞工程师暂时未能选定飞机的最佳结构方案，未能找到黄金分割点，以达到气动布局和隐身性能之间的最佳平衡。歼-20歼击机采用鸭式气动布局，高位三角翼，机翼与机身共轭，具体技术性能暂时不详。(编译：林海)</p> <div style="clear:both;height:0;visibility:hiddden;overflow:hidden;"></div><style type="text/css"> .otherContent_01 p {line-height:23px; margin:0px;} </style>  <div class="otherContent_01"><p>　　<strong>相关报道：</strong></p><p>　　<a onclick="recordPv(this,\'军事相关阅读\')" href=http://mil.news.sina.com.cn/2012-05-24/0924691326.html target=_blank>俄称中国第二架歼-20试飞可匹敌F-22战机</a></p> <p>　　<a onclick="recordPv(this,\'军事相关阅读\')" href=http://mil.news.sina.com.cn/2012-05-18/0955690883.html target=_blank>美评估中国第二架歼-20首飞称外形有改进</a></p> <p>　　<a onclick="recordPv(this,\'军事相关阅读\')" href=http://mil.news.sina.com.cn/2012-05-16/0914690658.html target=_blank>美媒称中国航空发动机技术薄弱制约歼-20战斗力</a></p> <p>　　<a onclick="recordPv(this,\'军事相关阅读\')" href=http://mil.news.sina.com.cn/2012-04-10/0809687153.html target=_blank>美媒渲染中国威胁论称歼20研发速度让美国吃惊</a></p></div> <!-- publish_helper_end -->   <!-- 正文内容清除浮动 开始 -->  <div class="clearit"></div>   <!-- 正文内容清除浮动 结束 -->   <!-- 分项按钮 开始 -->  <style>  .icon_sina, .icon_msn, .icon_fx{ background-position: 2px -1px}  .icon_msn {background-position: -25px -1px;}  .icon_fx {background-position: -240px -50px;}  </style>  <div style="line-height:35px; margin-bottom:5px; padding:10px 0 0; text-align:right;" id="sinashareto">  <span>分享到:</span>  <a class="icon_sina" href="javascript:;" id="fxwb" title="分享到新浪微博"></a><a class="icon_msn" id="fxMSN" href="javascript:;" target="_blank" title="分享到MSN"></a><a class="icon_fx" href="javascript:;" target="_blank" id="fxfx" title="分享到飞信"></a>  </div>  <!-- 分项按钮 结束 -->                                   <div style="margin-top:6px;"><iframe id="ipylcf01" width="570" height="260" frameborder="0" scrolling="no" allowtransparency="true" style="display:none;"></iframe></div>  <script> ~(function(){ 		var ids = {"data":[{"id":"1975410995,1676160070,2073915493,1471413527,1925349851"}]}; 		var src="http://widget.weibo.com/relationship/bulkfollow.php?refer="; 		var rest="&color=cccccc,f5f8fd,000099,666666&showtitle=1&showinfo=1&count=5&sense=1&wide=2&verified=0&ch=sina-mil-zw-1-0&dpc=1"; 		var page_href=location.href; 		page_href=page_href.replace(/#/g,"%23"); 		src +=  page_href; 		src += "&uids="; 		x=Math.round(Math.random()*ids[\'data\'].length); 		if(x ==ids[\'data\'].length ||x<0){x=0;} 		var recids = ids[\'data\'][x][\'id\']; 		src +=recids+rest; 		var iframe = document.getElementById("ipylcf01"); 		iframe.src = src;         iframe.style.display="block"; })(); </script>  				';
/*
$html = str_get_html($str);

$effHtml = "";
echo $html->find('.img_wrapper', 0)->innertext;
*/
/*
$html->find('div', 1)->class = 'bar';

$html->find('div[id=hello]', 0)->innertext = 'foo';
*/
//echo $html->find('div', 1)->class = 'bar';
 //http://mil.sina.cn/?sa=t134d287723v76&pos=24&vt=1
 /*
 $pattern  = "#^http://mil\.sina\.cn/\?sa=\w+&pos=\d+&vt=\d+$#i";
 $rs = 'http://mil.sina.cn/?sa=t134d287700v76&pos=24&vt=1';
 if(preg_match($pattern, $rs)){
     echo "ok....";
 }
 */
 $title = "媒体:中国导弹吓美军一身冷汗<br />     |     来源:中国新闻周刊<br />2012年05月31日 08:39";
 $ts = null;
 if(!empty($title)){
     $ts = explode("<br/>", $title);
 }
 if(empty($ts) || count($ts) < 3){
     $ts = explode("<br />", $title);
 }
 if(empty($ts) || count($ts) < 3){
     $ts = explode("<br />", $title);
 }
 if(empty($ts) || count($ts) < 3){
     $ts = explode("<br>", $title);
 }

 function zh_2_en_time($zhdatetime){
    $zhs = explode(' ', $zhdatetime);
    $zh_date = $zhs[0];
    $zh_time = $zhs[1];
    $marchs = null;
    $zh_date = preg_match("#^(\d{4})年(\d{2})月(\d{2})日$#", $zh_date,$marchs);
    
    $entime = $marchs[1].'-'.$marchs[2].'-'.$marchs[3].' '.$zh_time;
    
    return $entime;
    
 }
      
//print_r($ts);      
  echo zh_2_en_time("2012年05月31日 08:39");     
?>