package com.cu.apps.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import android.os.Environment;

/**
 * ��Ѷͼ�ı�ǩת��������
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
     * ȥ�������ַ������滻�ɿո�
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String StringFilter(String  str){       

    	 // ��������������ַ�   
    	 String regEx="��";
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
     * ���˵���html��ǩԪ��
     * @param inputString
     * @return
     */
    public static String html2Tag(String inputString) {  
        String textStr = inputString;  
         
       try{
            String regEx_html = "<img[^>]+>";// ����HTML��ǩ��������ʽ  
            Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
            Matcher  m_html = p_html.matcher(textStr);  
            textStr = m_html.replaceAll("<p><img><p>"); // ����html��ǩ  
            
           /* String regEx_html_link = "<a.*?</a>"; //  ����HTML��ǩ��������ʽ    
            p_html = Pattern.compile(regEx_html_link, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(textStr);
            textStr = m_html.replaceAll("<p><a><p>"); // // ����html��ǩ  ��  
*/        } catch (Exception e) {
            System.err.println("Html2Tag: " + e.getMessage());  
        }  
        return textStr;
    }  
    
    /**
     *  ���˵���img��ǩԪ��
     * @param inputString
     * @return
     */
    public static String html2Text(String inputString) {  
        String textStr = inputString;  
         
       try{
            String regEx_html = "<img[^>]+>"; // ����HTML��ǩ��������ʽ  
            Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
            Matcher  m_html = p_html.matcher(textStr);  
            textStr = m_html.replaceAll(""); // ����html��ǩ  ��  
        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());  
        }  
        return textStr;
    }  
    
    /**
     *  ���˵���html��ǩԪ��
     * @param inputString
     * @return
     */
    public static String htmlText(String inputString) {  
        String textStr = inputString;  
         
       try{
            String regEx_html = "<[^>]+>"; // ����HTML��ǩ��������ʽ  
            Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
            Matcher  m_html = p_html.matcher(textStr);  
            textStr = m_html.replaceAll(""); // ����html��ǩ  ��  
        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());  
        }  
        return textStr;
    }  

   /* 
    *//**
     * ���˵���html��ǩԪ��
     * @param inputString
     * @return
     *//*
    public static String  getHtml(String inputString)
    {
        String regEx_html = "<[^>]+>"; // ����HTML��ǩ��������ʽ
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
     * ��ȡhtml��ǩ
     * @param inputString
     * @return
     */
    public static String  getHtml(String inputString)
    {
    	StringBuilder sbuf = new StringBuilder();
    	String regEx_html = "<img[^>]+>"; // ����HTML��ǩ��������ʽ
    	String regEx_html_alt = "alt=\"[^>\"]+\""; // ����HTML��ǩ��������ʽ
        try{
        	Pattern p = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        	Pattern pAlt = Pattern.compile(regEx_html_alt,Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(inputString);
            while (m.find()) {
            	String str = m.group();
            	Matcher m2 = pAlt.matcher(str);
                if (m2.find()) {
                	String oldStr = m2.group().replace("alt=\"", "").replace("\" />", "").replace("\"", "");
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
        regEx_html = "<a.*?</a>"; //����HTML��ǩ��������ʽ  
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
     * ��ȡhtml��ǩ
     * @param inputString
     * @return
     */
    public static String  getHtml(String inputString,String str)
    {
        String regEx_html = "<img[^>]+>"; // ����HTML��ǩ��������ʽ  
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
     * ��װ��׼��xml
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
	 * �Ƿ��Ѿ�װ����sdcard
	 * 
	 */
	public static boolean isSDCARDMounted() {
		String status = Environment.getExternalStorageState();

		if (status.equals(Environment.MEDIA_MOUNTED))
			return true;
		return false;
	}

}
