<?php
require 'config.php';

// Returns true if $string is valid UTF-8 and false otherwise.
function is_utf8($word)
{
    if (preg_match("/^([".chr(228)."-".chr(233)."]{1}[".chr(128)."-".chr(191)."]{1}[".chr(128)."-".chr(191)."]{1}){1}/",$word) == true || preg_match("/([".chr(228)."-".chr(233)."]{1}[".chr(128)."-".chr(191)."]{1}[".chr(128)."-".chr(191)."]{1}){1}$/",$word) == true || preg_match("/([".chr(228)."-".chr(233)."]{1}[".chr(128)."-".chr(191)."]{1}[".chr(128)."-".chr(191)."]{1}){2,}/",$word) == true)
    {
        return true;
    }
    else
    {
        return false;
    }
} // function is_utf8



class Db {
	var $conn = null;
	/*
 获取mysqli连接，一般来讲只需要在一个页面中调用一次该函数接口
 通常是在页面中最初使用mysql时
*/
	function getConn() {
		global $host;
		global $dbusername;
		global $dbpassword;
		global $database;
		
		if ($this->conn == null) {
			$this->conn = new mysqli ( $host, $dbusername, $dbpassword, $database );
			$this->conn->query("set names gbk");
			/* check connection */
			if (mysqli_connect_errno ()) {
				printf ( "Connect failed: %s\n", mysqli_connect_error () );
				return null;
			}
		}
		
		return $this->conn;
	}
	
	/*
 获取mysqli连接，一般来讲只需要在一个页面中调用一次该函数接口
 通常是在页面中不需要使用mysql时
*/
	function close($con = null) {
		if ($con != null) {
			$con->close ();
			$con = null;
		}
		
		if ($this->conn != null) {
			$this->conn->close ();
			$this->conn = null;
		}
	}
}


$db = new Db();

function getConn(){
	global $db;
	return $db->getConn();
}


function remove_htmlchar($htmlstr){
  // $htmlchar = array('<div>','</div>','<p>','</p>','<p/>');
  // return str_ireplace($htmlchar, '', $htmlstr);
  return $htmlstr;
}

 function zh_2_en_time($zhdatetime){
    $zhs = explode(' ', $zhdatetime);
    if(empty($zhs) || count($zhs) < 2){
       return '';
    }
    $zh_date = $zhs[0];
    $zh_time = $zhs[1];
    $marchs = null;
    $zh_date = preg_match("#^(\d{4})年(\d{2})月(\d{2})日$#", $zh_date,$marchs);
    if(empty($marchs) || count($marchs) < 4){
        return '';
    }
    $entime = $marchs[1].'-'.$marchs[2].'-'.$marchs[3].' '.$zh_time;
    
    return $entime;
    
 }

?>