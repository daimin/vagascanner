<?php
include("Snoopy.class.php");
include("simple_html_dom.php");
include("file_opts.php");
include("conn.php");

define("DELAY", 100);

date_default_timezone_set('Asia/Shanghai');


/*
  抓取Sina mil的数据
*/
function fetch_sina_mil($url, $cache_dir ){
    global $db;
    $snoopy = new Snoopy;
    $idx = 1;
    createdir($cache_dir);
    $datetime = date("YmdHis");
    $tarfname = $datetime.'.html';
    $snoopy->fetchlinks($url); //获取所有内容   
        
    $equal_links = array();
        
    foreach($snoopy->results as $rs){
        // http://mil.news.sina.com.cn/2012-05-24/0746691312.html
        $pattern  = "#^http://mil\.news\.sina\.com\.cn/\d{4}-[0-1][0-9]-[0-3]\d/\d+\.html$#i";
        if(preg_match($pattern, $rs)){
            $equal_links[count($equal_links)] = $rs;
        }

    }
        
    $link_count = count($equal_links);
    if($link_count <= 0) {
        $db->close();
        exit();
    }
    // 如果有抓取到想要的链接，清除掉所有
        
    $conn = getConn();
    $sql = "delete from army_news";
    echo "<span style=\"color:red\">Execute: $sql</span><br/>";
    $conn->query($sql);
    $conn->query("set names utf8");
    usleep (DELAY);
    echo '<span style="color:#58d736">Parse link ready,found ['.$link_count.'] links. Start to fetch target html file....<span><br/>';
    del_file_in_dir($cache_dir);
    foreach($equal_links as $elink){

        if(!empty($elink)){
            echo "<span style=\"color:blue\">Num ".($idx)." begin fetch ...</span><br/>";
                
            $createtime = date("Y-m-d H:i:s");
            $html = file_get_html($elink);
            $title = "";
            $content = "";
            $plain_content = "";
            foreach($html->find('h1[id="artibodyTitle"]') as $ti){
                 $title = $ti->innertext;
                 if(!empty($title)) break;
            }
            $content_arr = array();
            foreach($html->find('div[id="artibody"]') as $cont){
                 //$content = $cont->innertext;
                 $img_cont = $cont->find('.img_wrapper',0);
                 if(!empty($img_cont)){
                     $img_cont = $img_cont->innertext;
                 }else{
                     $img_cont = "";
                 }
                 $content_arr[count($content_arr)] = $img_cont;
                 foreach($cont->find('p') as $p) {
                     if(empty($plain_content)){
                         $plain_content = $p->plaintext;
                     }
                     $content_arr[count($content_arr)] = $p->innertext;
                     
                 }
                 
                 if(!empty($content_arr)) break;
            }
            
            if(!empty($content_arr)){
                $content = implode('<br/>', $content_arr);
            }
            
            
                
            $datetime = date("YmdHis");
            $tarfname = $cache_dir.'/'.$datetime.($idx++).'.html';
            if(!is_utf8($content)){
                $content = iconv("GBK","UTF-8",$content); 
                $plain_content = iconv("GBK","UTF-8",$plain_content); 
            }
            
            if(!is_utf8($title)){
                $title = iconv("GBK","UTF-8",$title);  
            }
            
          
            $resume = trim(mb_substr($plain_content, 0, 50, "utf-8"));
     
            if(empty($resume)){
                $html->clear();
                continue;
            }
            writeToFile($tarfname, $content,'w+');
            
            $sql = "insert into army_news(title,cont_file,`resume`,createtime) values('$title','$tarfname','$resume','$createtime')";
            
            $conn->query($sql);
            
            $html->clear();

            usleep (DELAY);
        }
    }
      
    echo '<span style="color:#58d736">Fetch sina data over!!<span><br/>';
}


function fetch_ifeng_mil_3g($url, $cache_dir){
    global $db;
    $snoopy = new Snoopy();
    $idx = 1;
    createdir($cache_dir);
    $datetime = date("YmdHis");
    $tarfname = $datetime.'.html';
    $snoopy->fetchlinks($url); //获取所有内容   
        
    $equal_links = array();
    

    foreach($snoopy->results as $rs){
        //http://i.ifeng.com/mil/defence/news?aid=37136334&mid=ahEYxf

        $pattern  = "#^http://i\.ifeng\.com/mil/.+/news\?aid=\d+&amp;.*mid=\w+$#i"; // 注意转码
        if(preg_match($pattern, $rs)){
//echo $rs.'<br/>';
            $equal_links[count($equal_links)] = $rs.'&m=1';
        }
        
    }
    
    $link_count = count($equal_links);
    if($link_count <= 0) {
        $db->close();
        exit();
    }
    
    // 只删除非手工添加的新闻(手工添加的包括手工修改过的)
    $conn = getConn();
    $query = "select cont_file from army_news where manual=0";
    if($result = $conn->query($query)){
        while($row = $result->fetch_array(MYSQL_ASSOC)){
            $fpath = $_SERVER['DOCUMENT_ROOT'].'/hyena_ft/'.$row['cont_file'];
           // echo $fpath."<br/>";
            delFile($fpath);
        }
    }
    
    $sql = "delete from army_news where manual=0";
    echo "<span style=\"color:red\">Execute: $sql</span><br/>";
    $conn->query($sql);
    $conn->query("set names utf8");
    usleep (DELAY);
    echo '<span style="color:#58d736">Parse link ready,found ['.$link_count.'] links. Start to fetch target html file....<span><br/>';
    //del_file_in_dir($cache_dir);
    foreach($equal_links as $elink){

        if(!empty($elink)){
            echo "<span style=\"color:blue\">Num ".($idx)." begin fetch ...</span><br/>";
                
            $createtime = date("Y-m-d H:i:s");
            $html = file_get_html($elink);
            $title = "";
            $content = "";
            $plain_content = "";
            
            $hasplit = false;
            foreach($html->find('.newstitle') as $ti){
                 $title = $ti->innertext;
                 $ts = null;
                 if(!empty($title) || count($ts) < 3){
                     
                     $ts = explode('<br/>', $title);
                 }
                 if(empty($ts) || count($ts) < 3){
                     
                     $ts = explode('<br />', $title);
                 }
                 if(empty($ts) || count($ts) < 3){
                     
                     $ts = explode('<br />', $title);
                 }
                 if(empty($ts) || count($ts) < 3){
                     
                     $ts = explode('<br>', $title);
                 }
                 
                 if(!empty($ts)){
                     if(isset($ts[0])){
                         $title = $ts[0];
                         $hasplit = true;
                     }
                     
                     if(isset($ts[2])){
                        $createtime = zh_2_en_time($ts[2]);
                     }
                 }
                
                 if(!empty($title)) break;

            }
           if(empty($title)) {
               foreach($html->find('.newsTitle') as $ti){
                     $title = $ti->innertext;
                     $ts = null;
            
                     $ts = explode('<br />', $title);
    
                     if(!empty($ts)){
                         if(isset($ts[0])){
                             $title = $ts[0];
                         }
                         
                     }
                    
                     if(!empty($title)) break;

                }
            }
            
            
            
            
            $cont_str = "";
            foreach($html->find('.mainls') as $cont){
                
                $content = $cont->innertext;
                $contents = explode("分享",$content);
                if(count($contents) > 1){
                    $content = $contents[0];
                }
                //str_replace('')$content
                $contents = explode("<br/>",$content);
                $ccount = count($contents);
                array_shift($contents);
                array_shift($contents);
                array_shift($contents);
                $cont_str = implode($contents, '<br/>');
                
                if(empty($plain_content)){
                    $cont_html = str_get_html($cont_str);
                    $plain_content = $cont_html->plaintext;
                }
                break;
            }
            
            $content = $cont_str;
            $datetime = date("YmdHis");
            $tarfname = $cache_dir.'/'.$datetime.($idx++).'.html';
            if(!is_utf8($content)){
                $content = iconv("GBK","UTF-8",$content); 
                $plain_content = iconv("GBK","UTF-8",$plain_content); 
            }
            
            if(!is_utf8($title)){
                $title = iconv("GBK","UTF-8",$title);  
            }
            
            if(empty($createtime)){
                $createtime = date("Y-m-d H:i:s");
            }
          
            $resume = trim(mb_substr($plain_content, 0, 50, "utf-8"));
     echo $content;
            writeToFile($tarfname, $content,'w+');
            $title = trim($title);
            $resume = trim($resume);
            //echo $title.'-------------'.$elink.'-------------<br/>';
            $sql = "insert into army_news(title,cont_file,`resume`,createtime,idx) values('$title','$tarfname','$resume','$createtime',0)";
           // echo $sql;
            $conn->query($sql);
            // 更新排序ID
            $insert_id = $conn->insert_id;
            $sql = "update army_news set idx=$insert_id where id=$insert_id";
            $conn->query($sql);
            $html->clear();

            usleep (DELAY);
            
            
            
        }
    }

}


//fetch_sina_mil("http://mil.news.sina.com.cn/", "sina_cache");
//fetch_sina_mil_3g("http://mil.sina.cn/", "sina_cache");

//fetch_163_mil_3g("http://3g.163.com/news/special/00010360/junshiindex.html","163_cache");
fetch_ifeng_mil_3g("http://i.ifeng.com/mil/mili","ifeng_cache");


echo '<span style="color:#58d736">Parse link already!!<span><br/>';

$db->close();
?>