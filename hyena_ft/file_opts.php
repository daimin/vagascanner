<?php

/**
 * 全局数组，用于存储并返回数据
 */
$pics_buf = array();
//-----------------------------------

/**
 * 删除图片
 * @param unknown_type $path
 */
function delPicFromDisk($path) {
	$imgRelPath = $path;
	$httpPos = strpos ( $imgRelPath, "//", 0 );
	if ($httpPos != false) {
		$imgRelPath = substr ( $imgRelPath, $httpPos + 2 );
		$imgRelPath = substr ( $imgRelPath, strpos ( $imgRelPath, "/", 0 ) + 1 );
	}
	
	$imgRelPath = $_SERVER ["DOCUMENT_ROOT"] . "/" . $imgRelPath;
	
	$res = delFile ( $imgRelPath );
}

/**
 * 删除文件
 * @param $filePath
 */
function delFile($filePath) {
	if (! is_dir ( $filePath )) {
		unlink ( $filePath );
		return 1;
	} else {
		$str = scandir ( $filePath );
		foreach ( $str as $file ) {
			if ($file != "." && $file != "..") {
				$path = $filePath . "/" . $file;
				if (! is_dir ( $path )) {
					unlink ( $path );
				} else {
					$filePath ( $path );
				}
			}
		}
		if (rmdir ( $filePath )) {
			return 1;
		} else {
			return 0;
		}
	}
}

//// 删除文件夹中的所有文件
function del_file_in_dir($tardir){
    if(!is_dir($tardir)){
        return 0;
    } else {
        $files = scandir($tardir);
        foreach($files as $file){
            if($file != '.' && $file != '..'){
                $path = $tardir . '/' . $file;
                if(is_dir($path)) continue;
                else {
                    unlink($path);
                }
            }
        }
    }
    
    return 1;
    
    
}

///创建文件
function creat_file($PATH, $sFile) {
	if (file_exists ( $PATH . $sFile )) {
		creat_file ();
	} else {
		$fp = fopen ( $PATH . $sFile, "w" );
		fclose ( $fp );
	}
	return $sFile;
}

///创建文件夹
function createdir($dir) {
	if (file_exists ( $dir ) && is_dir ( $dir )) {
	} else {
		mkdir ( $dir, 0777 );
	}
}

function writeToFile($fname, $str, $mode = "a+") {
	$file_pointer = fopen ( $fname, $mode );
	// "w"模式
	fwrite ( $file_pointer, $str );
	// 不把文件剪切成0字节， 把数据追加到文件最后
	
	fclose ( $file_pointer );
}


function listDir($dpath,$root) {
	global $pics_buf;
	$fso = opendir ( $dpath );
//	echo "<div style='background:#00ffaa;border-bottom:1px solid #fff;'>[D]:&nbsp;$dpath</div>";
	while ( $flist = readdir ($fso) ) {
	
		if ($flist != "." && $flist != "..") {
			$fullfpath = $dpath . '/' . $flist;
			if (is_dir ( $fullfpath )) {
				listDir($fullfpath,$root);
			} else {
                $fullfpath = substr($fullfpath,stripos($fullfpath,$root) + strlen($root) + 1);
                $pics_buf[count($pics_buf)] = $fullfpath;
//				echo "<div style='background:#00aaff;border-bottom:1px solid #fff;'>[F]:&nbsp;$fullfpath<br/></div>";
			}
		}
	}
	closedir ( $fso );
	
	
	return $pics_buf;
}

?>