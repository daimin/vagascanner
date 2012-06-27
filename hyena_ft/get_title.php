<?php
header("Content-type: text/plain; charset=utf-8");

include("Snoopy.class.php");
include("simple_html_dom.php");
include("file_opts.php");
include("conn.php");

date_default_timezone_set('Asia/Shanghai');

class News{
   var $id;
   var $title;
   var $createtime;
   var $resume;
}

$news = array();

$query = "select * from army_news order by idx desc";
$conn = getConn();
$conn->query("set names utf8");
if($result = $conn->query($query)){
    while($row = $result->fetch_array(MYSQL_ASSOC)){

        $n = new News();
        $n->id = $row['id'];
        $n->title = urlencode($row['title']);
        //$n->title = $row['title'];
        $resume = $row['resume'];
        $resume = trim($resume);
        $resume = str_replace("\r\n","",$resume);
        $apos = strrpos($resume, '&');
        if($apos){
            $resume = substr($resume,0,$apos);
        }
        $n->resume = urlencode($resume);
        $n->createtime = $row['createtime'];
        //$n->cont_file = $row['cont_file'];
        $news[count($news)] = $n;
    }
}


echo  urldecode(json_encode($news));
//echo  json_encode($news);

$db->close();
?>