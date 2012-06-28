<?php
class News_model extends CI_Model {

    var $title   = '';
    var $content = '';
    var $resume    = '';
    var $createtime = '';
    var $cont_file = '';
    var $filesize;
    var $id;
    var $idx;
    

    function __construct()
    {
        parent::__construct();
        $this->load->database();
    }
    
   
    
    function __destruct(){
    	$this->db->close();
    }
    
    function addNew(){

    	$this->createtime = date("Y-m-d H:i:s");
    	$this->load->helper('file');
    	$datetime = date("YmdHis");
    	$this->cont_file = CONTENT_FILE_DIR.'/'.$datetime."_".rand(1000,9999).".html";
    	
    	$fullfname = $this->input->server("DOCUMENT_ROOT").'/'.FETCH_APP.'/'.$this->cont_file;
    	
    	write_file($fullfname,$this->content,'w+');
    	
    	$this->db->query("insert into army_news(title,resume,cont_file,createtime,idx) 
    	                  values('$this->title', '$this->resume','$this->cont_file', '$this->createtime',0)");
    	$insert_id = $this->db->insert_id();
    	$this->db->query("update army_news set idx=$insert_id where id=$insert_id");
    	
    }
    
    function editNew(){
    	$this->load->helper('file');
    	$fullfname = $this->input->server("DOCUMENT_ROOT").'/'.FETCH_APP.'/'.$this->cont_file;    	
    	write_file($fullfname,$this->content,'w+');
    	
    	$this->db->query("update army_news set title='$this->title',resume='$this->resume' where id=$this->id");
    }
    
    function getNewList($per_page, $seg){
    	$this->db->order_by("idx", "desc");
    	return $this->db->get("army_news", $per_page, $seg);
    }
    
    function delNews($newids){
    	$newids = urldecode($newids);
    	$ids = implode(",", explode("|", $newids));
    	$this->db->query("delete from army_news where id in ($ids)");
    	
    }
    
    function getNew($nid){
    	$result = $this->db->query("select * from army_news where id=$nid");
    	$new = $result->row();
    	$this->load->helper('file');
    	$fullfname = $this->input->server("DOCUMENT_ROOT").'/'.FETCH_APP.'/'.$new->cont_file;
    	$new->content = read_file($fullfname);
    	return $new;
    }
    
    function toTop(){
    	$result = $this->db->query("select id,idx from army_news where idx=(select max(idx) from army_news)");
    	$maxidNew = $result->row();
    	$this->db->query("update army_news set idx=$maxidNew->idx where id=$this->id");
    	$this->db->query("update army_news set idx=$this->idx where id=$maxidNew->id");
    }
    

}
?>