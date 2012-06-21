<?php
if (! defined ( 'BASEPATH' ))
	exit ( 'No direct script access allowed' );
/**
 * 这个后台我都只用Controller来处理页面跳转
 * @author daimin
 *
 */
class Login extends CI_Controller {

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
	}
	

	
	function __destruct(){
		if(!empty($this->db)){
			$this->db->close();
		}
	}
	
	

	public function index()
	{	
		if(isset($_POST["action"])){
			$tbUserName = $this->input->post("tbUserName");
			$tbPassword = $this->input->post("tbPassword");
			
			if(!empty($tbUserName) && !empty($tbPassword)){
				if(trim($tbUserName) == "admin" && trim($tbPassword) == "253685"){
					$this->load->helper('cookie');
					
					$cookie = array(
					    'name'   => ADMIN_USER,
					    'value'  => 'admin',
					    'expire' => '3600' // 这里设小点，如设为一天会设置失败，单位：秒
                     );

					set_cookie($cookie);
					
					redirect(site_url("main"),"refresh");
					
					return;
				}
			}
			$data['err'] = "用户名或密码错误";
			$this->load->view('login',$data);
		}else{
			$this->load->view('login');
		}
		
	}

	
	
	

}

/* End of file welcome.php */
/* Location: ./application/controllers/welcome.php */