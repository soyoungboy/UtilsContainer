package cn.itcast.testmanager.common.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import javax.servlet.ServletRequest;

import cn.itcast.testmanager.common.exception.BusinessException;

public class Common {
	public Common() {
	}

	public static final String HQL_SEPARATORS = " \n\r\f\t,()=<>&|+-=/*'^![]#~\\";

	public static final String HQL_VARIABLE_PREFIX = ":";

	/**
	 * 将文本格式转化为html格式 Create By: xie jianhua Fliter the input content from
	 * client. example:result=htmlFilter("   test test.") the value of [result]
	 * is "&nbsp; &nbsp;test test."
	 * 
	 */
	public static String htmlFilter(String strContent) {
		String strHtmlFilter = strContent;
		String VBCRLF = "\r\n";
		String VBCRLF1 = "\r\n ";
		String ASPACE = " ";
		String ASPACE1 = "  ";
		String BR = "<br>";
		String BR1 = "<br>&nbsp;";
		String SPACETAG = "&nbsp;";
		int P = -1;
		if (strHtmlFilter.length() != 0) {
			strHtmlFilter = replace(strHtmlFilter, VBCRLF1, BR1);
			strHtmlFilter = replace(strHtmlFilter, VBCRLF, BR);
			if (strHtmlFilter.substring(0, 1).equals(ASPACE)) {
				strHtmlFilter = SPACETAG + strHtmlFilter.substring(1);
			}
			P = strHtmlFilter.indexOf(ASPACE1);
			while (P > 0) {
				strHtmlFilter = strHtmlFilter.substring(0, P) + SPACETAG
						+ ASPACE + strHtmlFilter.substring(P + 2);
				P = strHtmlFilter.indexOf(ASPACE + ASPACE);
			}
		}
		return strHtmlFilter;
	}

	public static String htmlFilter1(String strContent) {
		String strHtmlFilter = strContent;
		String VBCRLF = "\r\n";
		String SPACETAG = "&nbsp;";
		String ASPACE = " ";
		String BR = "<br>";

		if (strHtmlFilter.length() != 0) {
			strHtmlFilter = replace(strHtmlFilter, VBCRLF, BR);
			strHtmlFilter = replace(strHtmlFilter, ASPACE, SPACETAG);
		}
		return strHtmlFilter;
	}

	/**
     * filtertoHTML
     * 
     * @author chenjiang
     * @param input String
     * @return input String
     */
    public static String filterToHtml(String input) {
        if (input == null || input.equals("")) {
            return input;
        }
        StringBuffer filtered = new StringBuffer(input.length());
        char c;
        for (int i = 0; i <= input.length() - 1; i++) {
            c = input.charAt(i);
            switch (c) {
            case '"':
                filtered.append("&quot;");
                break;
            case '&':
                filtered.append("&amp;");
                break;
            case '<':
                filtered.append("&lt;");
                break;
            case '>':
                filtered.append("&gt;");
                break;
            case ' ':
                filtered.append("&#160;");
                break;
//            case '|':
//                filtered.append("&brvbar;");
//                break;
            case 13:
                filtered.append("<br/>");
                break;
            default:
                filtered.append(c);
            }
        }
        return (filtered.toString());
    }
	
	/**
	 * 转换所有用户提交的中文字体。 由于在WebSphere中服务器已对中文进行了处理，所以不用转换代码 public static String
	 * transform(String newString)只处理checkInput
	 * 如果使用其他的服务器没有对中文处理，transform方法用transform_bak代替
	 */
	public static String transform_bak(String newString) {
		String submit = newString;
		if (submit == null) {
			submit = "";
		}
		return submit;
	}

	/**
	 * 
	 * 替换public static String transform(String newString)之用
	 * 
	 * @return
	 */
	public static String transform(String newString) {
		String submit = newString;
		try {
			if (submit != null) {
				// submit=submit.replace('\'','`').replace('"','`');
				submit = new String(submit.getBytes("ISO8859_1"), "GBK");
			} else {
				submit = "";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return submit;
	}

	/**
     * 从request获取参数
     *
     * @param request
     * @param name    属性名
     * @param defval  如果获取的参数为nul时默认值
     * @return int
     */
    public static final int getParam(ServletRequest request, String name, int defval) {
        String param = request.getParameter(name);
        int value = defval;
        if (param != null) {
            try {
                value = Integer.parseInt(param);
            } catch (NumberFormatException ignore) {
            }
        }
        return value;
    }

	/**
	 * <p></p>
	 * <br>
	 * @param strInput
	 * @return
	 */
	public static String checkInput(String strInput) {
		String temp = strInput;
		if (strInput == null) {
			temp = "";
		} else {
			temp = replace(temp, "'", "&#039;");
			temp = replace(temp, "\"", "&#034;");
			temp = replace(temp, "%", "&#037;");
			temp = replace(temp, "<", "&#060;");
			temp = replace(temp, ">", "&#062;");
		}
		return transform(temp);
	}

	public static String checkInput1(String strInput) {
		String temp = strInput;
		if (strInput == null) {
			temp = "";
		} else {
			temp = replace(temp, "'", "&#039;");
			temp = replace(temp, "\"", "&#034;");
			temp = replace(temp, "%", "&#037;");
			temp = replace(temp, "<", "&#060;");
			temp = replace(temp, ">", "&#062;");
		}
		return temp;
	}

	/**
	 * 将字符串格式化成 HTML 代码输出
	 * 
	 * @param str
	 *            要格式化的字符串
	 * @return 格式化后的字符串
	 */
	public String toHtml(String str) {
		String html = str;

		html = replace(html, "&", "&amp;");
		html = replace(html, "<", "&lt;");
		html = replace(html, ">", "&gt;");
		html = replace(html, "\r\n", "\n");
		html = replace(html, "\n", "<br>\n");
		html = replace(html, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		html = replace(html, "\\", "\\\\");
		html = replace(html, "\'", "\\\'");
		html = replace(html, "\"", "\\\"");
		html = replace(html, "  ", "&nbsp;&nbsp;"); // ����Ӣ�Ŀո�

		return html;
	}

	public String toHtml1(String str) {
		String html = str;
		html = Replace(html, "<br>", "\r\n");
		html = replace(html, "&nbsp;", " ");
		return html;
	}
	
	public static String Replace(String Content, String oldString,
			String newString) {
		if (Content == null || oldString == null || newString == null) {
			return "";
		}
		String makeContent = new String();
		StringTokenizer strToken = new StringTokenizer(Content, oldString);
		while (strToken.hasMoreTokens()) {
			makeContent = makeContent + newString + strToken.nextToken();
		}
		return makeContent;
	}

	/**
	 * 替换\n为<br>
	 * 
	 * @param str
	 *            要格式化的字符串
	 * @return 格式化后的字符串
	 */
	public String toBr(String str) {
		String html = str;
		html = replace(html, "\r\n", "\n");
		html = replace(html, "\n", "<br>\n");
		html = replace(html, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		html = replace(html, "\\", "\\\\");
		html = replace(html, "\'", "\\\'");
		html = replace(html, "\"", "\\\"");
		html = replace(html, "  ", "&nbsp;&nbsp;"); // ����Ӣ�Ŀո�

		return html;
	}

	/**
	 * 
	 * <P> 当s的长度为一位时，返回值在s前加0</P>
	 * @param s
	 * @return
	 */
	public String get2byte(String s) {
		String temp = s.trim();
		if (temp.length() < 2) {
			temp = "0" + temp;
		}
		return temp;
	}

	public int getIncludeNum(String temp, String cmp) {
		int a = 0;
		int b = 0;
		int n = 0;
		if (cmp.equals("")) {
			n = 1;
		} else {
			while (b >= 0) {
				b = temp.indexOf(cmp, a);
				a = b + 1;
				n++;
			}
		}
		return n - 1;
	}

	public String changeNull(String str1) {
		String temp = str1;
		if (str1 == null || str1.equals("")) {
			temp = "0";
		}
		return temp;
	}

	public String replaceNull(String str) {
		String str2 = "";
		if (str == null) {
			str2 = "";
		} else {
			str2 = str;
		}
		return str2;
	}

	public String replaceNullTemplate(String str) {
		String str2 = "&nbsp;";
		if (str == null) {
			str2 = "&nbsp;";
		} else {
			str2 = str;
		}
		return str2;
	}

	public String change0(String str) {
		String str2 = "";
		if (str.equals("0")) {
			str2 = "";
		} else {
			str2 = str;
		}
		return str2;
	}
	
	/**
	 * <p>由传入的字符串得到utf-8格式的InputStream。</p>
	 * 本方法已经捕获UnsupportedEncodingException<br>
	 * @param str
	 * @return
	 * @author leo_soul
	 * 2012-6-14
	 */
	public static InputStream getUTF8InStream(String str)
	{
    	try
		{
			return new ByteArrayInputStream(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e)
		{
			//Do nothing.
			return null;
		}
	}
	
	/**
	 * 将传入的数据流解析成字符串
	 * @param inputStream
	 * @param encoding
	 * @return
	 */
	public static String getStringFormInPutStream(InputStream inputStream,String encoding){
	    ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
	    byte[] buffer=new byte[1024];
	    int len;
	    String str = null;
        try {
            while((len=inputStream.read(buffer))!=-1){
                outputStream.write(buffer, 0, len);
            }
            str = outputStream.toString(encoding);
        } catch (UnsupportedEncodingException e) {
        	new BusinessException(e,Common.class.getName(),"getStringFormInPutStream");
        } catch (IOException e) {
        	new BusinessException(e,Common.class.getName(),"getStringFormInPutStream");
        }finally{
            try {
                if(inputStream!=null)
                    inputStream.close();
                if(outputStream!=null){
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
            	new BusinessException(e,Common.class.getName(),"getStringFormInPutStream");
            }
        }
	    return str;
	}
	
	/**
	 * 
	 * <P> 将Str中的字符串str1替换为str2</P>
	 * @param Str
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String replace(String Str, String str1, String str2) {
		String temp = Str;
		int flag = 0;
		if (Str.indexOf(str1) != -1) {
			int str1Length = str1.length();
			boolean bflag = false;
			String str3 = "";
			if (str2.indexOf(str1) != -1) {
				bflag = true;
				str3 = str2;
				str2 = "!#@$%";
			}
			while (temp.indexOf(str1) != -1) {
				flag = temp.indexOf(str1);
				temp = temp.substring(0, flag) + str2
						+ temp.substring(flag + str1Length);
			}
			if (bflag) {
				str1Length = str2.length();
				while (temp.indexOf(str2) != -1) {
					flag = temp.indexOf(str2);
					temp = temp.substring(0, flag) + str3
							+ temp.substring(flag + str1Length);
				}
			}
		}
		return temp;
	}
}
