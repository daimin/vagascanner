<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- saved from url=(0114)http://www.blogjava.net/login.aspx?ReturnUrl=%2fvagasnail%2fadmin%2fEditPosts.aspx&/vagasnail/admin/EditPosts.aspx -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统登录</title>
<base href="<?php echo base_url();?>"/>
<base src="<?php echo base_url();?>"/>
<link href="css/base.css" type="text/css" rel="stylesheet"> 
<link href="css/login.css" type="text/css" rel="stylesheet">
<script language="javascript">
	function setfocus()
	{
		if(document.forms[0]['tbUserName'].value!='')
		{
			document.forms[0]['tbPassword'].focus()
		}
		else
		{
			document.forms[0]['tbUserName'].focus()
		}

		document.onkeypress = function(){
			 var code = 0;
			 if (NS4)
			     code = event.which;
			 else
			     code = event.keyCode;
		     if(code == 13){
		    	 doSubmit();
			 }
		};
	}
    </script></head>

<body onload="setfocus()">
    <form name="frmLogin" id="frmLogin" method="post" action="<?php echo site_url("login")?>" id="frmLogin">
<div>
</div>



<script language="JavaScript">
NS4 = (document.layers) ? true : false;
function checkEnter(event,element)
{     
//    var code = 0;
//    if (NS4)
//        code = event.which;
//    else
//        code = event.keyCode;
//    if (code==13)
//	{
//       	if(element.name=='tbUserName')//tbUserName-�û����ı����Name
//		{
//			document.frmLogin.tbPassword.focus();//frmLogin-�������,tbPassword-�����ı��ܵ�Name
//		}
//		
//	}
}

</script>


<script type="text/javascript">
//<![CDATA[
function WebForm_OnSubmit() {
    if (typeof(ValidatorOnSubmit) == "function" && ValidatorOnSubmit() == false) return false;
    return true;
}
//]]>
</script>

        <table id="tbLogin" align="center">
            <tbody><tr>
                <td>
                    <div id="Main">
                        <div id="Heading">请登录</div>
                        <label class="MyLabel">用户名</label>
                        <input name="tbUserName" type="text" id="tbUserName" class="Textbox" onkeypress="checkEnter(event,this)"><br>
                        <span id="Required_UserName" style="color:Red;visibility:hidden;">用户名不能为空</span>
                        <label class="MyLabel">密码</label>
                        <input name="tbPassword" type="password" id="tbPassword" class="Textbox" onkeypress="checkEnter(event,this)"><br>
                        <span id="Required＿Password" style="color:Red;visibility:hidden;">密码不能为空</span><br/>
                       
                        <a id="lblLogin" class="Button" href="javascript:doSubmit()" style="margin-top: 8px">登录</a>
                        
                        <p class="Small">
                        &nbsp;&nbsp;
                        <input type="hidden" name="action" value="login" >
                        <a href="mailto:daiming253685@126.com">联系管理员</a>
                         <br>
                         <br>
                         &nbsp;&nbsp;
                        </p>
                        <br style="clear: both">
                        <span id="Message" class="ErrorMessage" fontbold="true" style="color:Red;">
                        <?php 
                        if(isset($err)){
                            echo $err;   	
                        }
                        ?>
                        </span></div>
                </td>
            </tr>
        </tbody></table>
    
<script type="text/javascript">
//<![CDATA[
var Page_Validators =  new Array(document.getElementById("Required_UserName"), document.getElementById("Required＿Password"));
//]]>
</script>




<script type="text/javascript">
//<![CDATA[

function ValidatorCommonOnSubmit(){
    var frmLogin = document.getElementById("frmLogin");
    var ok = true;
    if(frmLogin.tbUserName.value == ""){
        Page_Validators[0].style.visibility = "";
        ok = false;
    }
    
    if(frmLogin.tbPassword.value == ""){
        Page_Validators[1].style.visibility = "";
        ok = false;
    }

    return ok;
    
}

function doSubmit(){

	var frmLogin = document.getElementById("frmLogin");
	var re = WebForm_OnSubmit();
	if(re){
		frmLogin&frmLogin.submit();
	}
}

function ValidatorOnSubmit() {
    return ValidatorCommonOnSubmit();
}
        //]]>
</script>
</form>


</body></html>