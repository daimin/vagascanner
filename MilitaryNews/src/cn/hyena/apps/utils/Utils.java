package cn.hyena.apps.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import android.os.Environment;

/**
 * 资讯图文标签转换工具类
 *
 */
public class Utils {
    public static int valueOf(String string){
    	try{
    		if(string == null || string.equals(""))
    			return 0;
    		int i=Integer.valueOf(string);
    		return i;
    	}catch(NumberFormatException e){
    		e.printStackTrace();
    	}
    	return 0;
    }
    
    /**
     * 去掉特殊字符串（替换成空格）
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String StringFilter(String  str){       

    	 // 清除掉所有特殊字符   
    	 String regEx="";
		 String mStr=str;
    	try
    	{
    		 Pattern  p    =    Pattern.compile(regEx);      
    		 Matcher  m    =    p.matcher(str);  
    		 mStr=m.replaceAll("").trim(); 
    	}catch(PatternSyntaxException e)
    	{
    		e.printStackTrace();
    	}	 
		 return  mStr;       
   } 
    
    /**
     * 过滤掉含html标签元素
     * @param inputString
     * @return
     */
    public static String html2Tag(String inputString) {  
        String textStr = inputString;  
         
       try{
            String regEx_html = "<img[^>]+>";// 定义HTML标签的正则表达式  
            Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
            Matcher  m_html = p_html.matcher(textStr);  
            textStr = m_html.replaceAll("<p><img><p>"); // 过滤html标签  
            
           /* String regEx_html_link = "<a.*?</a>"; //  定义HTML标签的正则表达式    
            p_html = Pattern.compile(regEx_html_link, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(textStr);
            textStr = m_html.replaceAll("<p><a><p>"); // // 过滤html标签    
*/        } catch (Exception e) {
            System.err.println("Html2Tag: " + e.getMessage());  
        }  
        return textStr;
    }  
    
    /**
     *  过滤掉含img标签元素
     * @param inputString
     * @return
     */
    public static String html2Text(String inputString) {  
        String textStr = inputString;  
         
       try{
            String regEx_html = "<img[^>]+>"; // 定义HTML标签的正则表达式  
            Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
            Matcher  m_html = p_html.matcher(textStr);  
            textStr = m_html.replaceAll(""); // 过滤html标签    
        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());  
        }  
        return textStr;
    }  
    
    /**
     *  过滤掉含html标签元素
     * @param inputString
     * @return
     */
    public static String htmlText(String inputString) {  
        String textStr = inputString;  
         
       try{
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
            Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
            Matcher  m_html = p_html.matcher(textStr);  
            textStr = m_html.replaceAll(""); // 过滤html标签    
        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());  
        }  
        return textStr;
    }  

   /* 
    *//**
     * 过滤掉含html标签元素
     * @param inputString
     * @return
     *//*
    public static String  getHtml(String inputString)
    {
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    	StringBuilder sbuf = new StringBuilder();
        try{
        	Pattern p = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(inputString);
            while (m.find()) {
            	sbuf.append(m.group());
            }
        }catch (Exception e) {
			// TODO: handle exception
        	 System.err.println(e.getMessage());  
		}
        return sbuf.toString();
    }*/
    
     /**
     * 获取html标签
     * @param inputString
     * @return
     */
    public static String  getHtml(String inputString)
    {
    	StringBuilder sbuf = new StringBuilder();
    	String regEx_html = "<img[^>]+>"; // 定义HTML标签的正则表达式
    	String regEx_html_alt = "alt=\"[^>]+/>"; // 定义HTML标签的正则表达式
        try{
        	Pattern p = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        	Pattern pAlt = Pattern.compile(regEx_html_alt,Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(inputString);
            while (m.find()) {
            	String str = m.group();
            	Matcher m2 = pAlt.matcher(str);
                if (m2.find()) {
                	String oldStr = m2.group().replace("alt=\"", "").replace("\" />", "");
                	if (!Common.isNullOrBlank(oldStr)){
                	   String newStr = oldStr.replace("\"","");
                	   str = str.replace(oldStr,newStr);
                	}
                }
            	sbuf.append(str);
            }
        }catch (Exception e) {
			// TODO: handle exception
        	 System.err.println(e.getMessage());  
		}
       /* 
        regEx_html = "<a.*?</a>"; //定义HTML标签的正则表达式  
        try{
        	Pattern p2 = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
            Matcher m2 = p2.matcher(inputString);
            while (m2.find()) {
            	sbuf.append(m2.group());
            }
        }catch (Exception e) {
			// TODO: handle exception
        	 System.err.println(e.getMessage());  
		}*/
        return sbuf.toString();
    }
    
    /**
     * 获取html标签
     * @param inputString
     * @return
     */
    public static String  getHtml(String inputString,String str)
    {
        String regEx_html = "<img[^>]+>"; // 定义HTML标签的正则表达式  
    	StringBuilder sbuf = new StringBuilder();
        try{
        	Pattern p = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(inputString);
            while (m.find()) {
            	sbuf.append(m.group());
            }
        }catch (Exception e) {
			// TODO: handle exception
        	 System.err.println(e.getMessage());  
		}
        return sbuf.toString();
    }
    
    /**
     * 组装标准的xml
     * @param inputString
     * @return
     */
    public static String toXML(String inputString)
    {
    	StringBuilder sbuf = new StringBuilder();
		sbuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sbuf.append("<content>");
		sbuf.append(inputString);
		sbuf.append("</content>");
		return sbuf.toString();
    }
    
    /**
	 * 是否已经装载了sdcard
	 * 
	 */
	public static boolean isSDCARDMounted() {
		String status = Environment.getExternalStorageState();

		if (status.equals(Environment.MEDIA_MOUNTED))
			return true;
		return false;
	}

}
