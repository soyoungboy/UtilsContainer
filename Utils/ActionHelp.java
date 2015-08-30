package cn.itcast.testmanager.common.util;


import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 
* <p>sping工具类</p>
* @ClassName: ActionHelp
*
 */
public class ActionHelp {
	
    /**
     * 
     * <P>从spring上下文得到bean信息</P>
     * @param <T>
     * @param beanid
     * @return
     */
    public static <T>T getBean(String beanid) {
        Object rootWebApplicationContext = ServletActionContext.getServletContext().
                getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        ApplicationContext appContext = (ApplicationContext) rootWebApplicationContext;
        T obj = (T)appContext.getBean(beanid);
        return obj;
    }

    public static Object getValue(String attr) {
        ValueStack vs = ActionContext.getContext().getValueStack();
        if (vs != null) {
            return vs.findValue(attr);
        } else {
            return null;
        }

    }
}
