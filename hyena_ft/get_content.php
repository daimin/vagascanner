<?php
header("Content-type: text/plain; charset=utf-8");

include("Snoopy.class.php");
include("simple_html_dom.php");
include("file_opts.php");
include("conn.php");

define("__ERROR_1__", "新闻不存在");
define("__ERROR_2__", "新闻内容为空");

$nid = 0;

if(isset($_GET['id'])){
    $nid = $_GET['id'];
}

if(empty($nid)){
    echo '{"err":"'.__ERROR_1__.'"}';
    exit();
}

date_default_timezone_set('Asia/Shanghai');

class Content{
   var $title;
   var $content;
}

$title = "";
$cont_file = "";

$query = "select title,cont_file from army_news where id=$nid";
$conn = getConn();
$conn->query("set names utf8");
if($result = $conn->query($query)){
    if($row = $result->fetch_array(MYSQL_ASSOC)){
       
        $title = $row['title'];
        $cont_file = $row['cont_file'];
    }
}


if(empty( $title)){
    echo '{"err":"'.__ERROR_1__.'"}';
    exit();
}

$title .= " ";
$cont = new Content();
$title = str_replace("\r\n","",$title);
$title = str_replace("\n","",$title);
$cont->title = urlencode(remove_htmlchar($title));

$handle = fopen($cont_file, "r");
$content = '';
while(!feof($handle)){
    $content .= fread($handle, 8080);
}
fclose($handle);

if(empty($content)){
    echo '{"err":"'.__ERROR_2__.'"}';
    exit();
}

if(!is_utf8($content)){
    $content = iconv("GBK","UTF-8",$content);  
}


	$search = array ("'<script[^>]*?>.*?</script>'si",  // 去掉 javascript

	                 "'([\r\n])[\s]+'",                 // 去掉空白字符
	                 "'&(quot|#34);'i",                 // 替换 HTML 实体
	                 "'&(amp|#38);'i",
	                 "'&(lt|#60);'i",
	                 "'&(gt|#62);'i",
	                 "'&(nbsp|#160);'i",
	                 "'&(iexcl|#161);'i",
	                 "'&(cent|#162);'i",
	                 "'&(pound|#163);'i",
                 "'&(copy|#169);'i",
	                 "'&#(\d+);'e");                    // 作为 PHP 代码运行
	 
	$replace = array ("",
	                  "\\1",
	                  "\"",
	                  "&",
	                  "<",
	                  ">",
	                  " ",
	                  chr(161),
	                  chr(162),
	                  chr(163),
	                  chr(169),
	                  "chr(\\1)");

$content = preg_replace ($search, $replace, $content);                      

$content = urlencode(htmlspecialchars(remove_htmlchar($content)));
$content = str_replace("\r\n","",$content);
$content = str_replace("\n","",$content);

$cont->content = $content;


echo  urldecode(json_encode($cont));
//echo  json_encode($cont);

$db->close();
?>