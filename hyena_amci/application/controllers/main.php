<?php
if (! defined ( 'BASEPATH' ))
	exit ( 'No direct script access allowed' );
/**
 * 这个后台我都只用Controller来处理页面跳转
 * @author daimin
 *
 */
class Main extends CI_Controller {

	/**
	 * main Page for this controller.
	 *
	 * Maps to the following URL
	 * 		http://example.com/main.php/welcome
	 *	- or -  
	 * 		http://example.com/main.php/welcome/main
	 *	- or -
	 * Since this controller is set as the default controller in 
	 * config/routes.php, it's displayed at http://example.com/
	 *
	 * So any other public methods not prefixed with an underscore will
	 * map to /main.php/welcome/<method_name>
	 * @see http://codeigniter.com/user_guide/general/urls.html
	 */
	
	function __construct(){
		parent::__construct();
		$this->load->helper('url');
	    $this->valid_admin();
	}
	
	private function valid_admin(){
		$this->load->helper('cookie');
		$admin_user = get_cookie(ADMIN_USER);

		if($admin_user != "admin"){
		   // redirect("login","refresh");
		    echo '<script type="text/javascript">window.top.location="'.site_url("login").'";</script>';
		    exit();
		}
		
	}
	
	function __destruct(){
		if(!empty($this->db)){
			$this->db->close();
		}
	}
	
	
	public function index()
	{
		
		$this->load->view('index');
	}
	
	public function top() { 
	    $this->load->helper('cookie');
		$admin_user = get_cookie(ADMIN_USER);
		$data["user"] = $admin_user;
    	$this->load->view('top',$data);    
	}
	 
	
	public function logout(){
		$this->load->helper('cookie');
		delete_cookie(ADMIN_USER);
		echo '<script type="text/javascript">window.top.location="'.site_url("login").'";</script>';
	}
	
	function menu() {
		$this->load->view('menu');    
	}    

	function new_list() {
		
		$this->load->database();
		
		$this->load->library('pagination');
		
		$this->load->model('news_model');
		
		$this->load->helper('file');
		
		
		$query = $this->db->count_all_results("army_news");
		
		//配置分页类参数
        $config['base_url'] = site_url('main/new_list'); //注意"index"必须要.
        $config['use_page_numbers'] = FALSE;
 
        $config['total_rows'] = $query;//数据总行数 
 
        $config['per_page'] = '20'; //每页显示数
        $config['prev_link']="上一页";
        $config['next_link']="下一页";
        $config['first_link']="首页";
        $config['last_link']="尾页";
    
        $config['uri_segment'] ='3'; //页数在URL的参数的第几个: 
                                     // 如 [url]http://yoursite.com/index.php/article/index/2[/url], 
                                     // 那么"/index/"后面的就是页数即'3'. 这个的作用是让页面的连接显示当前页数对应起来
    
        $news_stmt = $this->news_model->getNewList($config['per_page'], $this->uri->segment(3));
        $news = $news_stmt->result();
        foreach($news as $n){
        	$finfo = get_file_info($this->input->server("DOCUMENT_ROOT").'/'.FETCH_APP.'/'.$n->cont_file);
        	$n->filesize = ($finfo['size']/1024);
        	
        }
        $data["query"] = $news;
        $this->pagination->initialize($config); //初始化分页

		$this->load->view ( 'new_list', $data );
	}
	
	
	function new_add(){
		if(isset($_POST['action'])){
			$this->load->model('news_model');

			$this->news_model->title = html_escape($this->input->post('title'));
			$this->news_model->resume = html_escape($this->input->post('resume'));
			$this->news_model->content = ($this->input->post('content'));
			$this->news_model->addNew();
			
			redirect("main/new_list","refresh");
			
		}else{
			$this->load->view ( 'new_add' );
		}
		
	}
	
	function new_del(){
		
		$ids = $this->input->post('nid');
		
		if(!empty($ids)){
			$this->load->model('news_model');
			$this->news_model->delNews($ids);
			
		}
		
		echo $ids;
	}
	
	
	function new_edit($nid){
	    if(isset($_POST['action'])){
			$this->load->model('news_model');
			$this->news_model->title = html_escape($this->input->post('title'));
			$this->news_model->resume = html_escape($this->input->post('resume'));
			$this->news_model->content = ($this->input->post('content'));
			$this->news_model->id = ($this->input->post('id'));
			$this->news_model->cont_file = ($this->input->post('cont_file'));
			$this->news_model->editNew();
			redirect("main/new_list","refresh");
		}else{
			$this->load->model('news_model');
	        $data["query"] = $this->news_model->getNew($nid);
	        $this->load->view ( 'new_edit',$data );
		}
	    
	}
	
	
	function to_top($nid,$idx){
		$this->load->model('news_model');
		$this->news_model->id = $nid;
		$this->news_model->idx = $idx;
		$this->news_model->toTop();
		redirect("main/new_list","refresh");
		
	}
	

}

/* End of file welcome.php */
/* Location: ./application/controllers/welcome.php */