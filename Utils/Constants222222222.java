package cn.itcast.testmanager.common.util;

/**
 * 系统使用的常量，根据需要自行添加
 * 
 */
public abstract class Constants222222222 {

	/* 登录用户对象在SESSION中保存的键名 */
	public final static String KEY_LOGIN_USER = "LU";
	
	/* 登录用户ID在SESSION中保存的键名 */
	public final static String LOGIN_USER_ID = "LOGIN_USER_ID";
	
	/* 登录用户名称在SESSION中保存的键名 */
	public final static String LOGIN_USER_NAME = "LOGIN_USER_NAME";
	
	/* 登录用户的登录名在SESSION中保存的键名 */
	public final static String LOGIN_USER_LOGINNAME = "LOGIN_USER_LOGINNAME";
	
	/* 登录用户所在部门ID在SESSION中保存的键名 */
	public final static String LOGIN_USER_DEPT_ID = "LOGIN_USER_DEPT_ID";
	
	/* 登录用户所在部门名称在SESSION中保存的键名 */
	public final static String LOGIN_USER_DEPT_NAME = "LOGIN_USER_DEPT_NAME";
	
	/* 已删除标识 */
	public final static String STATE_DELETED = "D";
	public final static String STATE_DELETED_Y = "1";// 删除状态,1 正常
	public final static String STATE_DELETED_N = "0";// 删除状态,0已删除
	
	/*公告是否已阅标识*/
	public final static String NOTICE_ISREAD_N = "0";
	public final static String NOTICE_ISREAD_Y = "1";
	
	/* 主题在SESSION中保存的键名 */
	public static final String KEY_THEME = "THEME";
	
	/* 树的根节点的 parentID 为 00000000 */
	public final static String TREE_ROOT_NODE_PARENTID = "00000000"; 
	
	/* 当前用户有效菜单连接在SESSION中保存的键名 */
	public static final String KEY_SUER_ACTIONS = "KEY_SUER_ACTIONS";
	
	/* 上一次访问的链接所对应的模块名在SESSION中保存的键名前缀 */
	public static final String LAST_MODULE_KEY = "MODEULE_CACHE_";
	
	/* License版本号 */
	public static final String LICENSE_VERSION = "1.0";
	
	/* License服务器端口号 */
	public static final Integer LICENSE_PORT = 9527;
	
	/* 系统管理模块对应的namespace */
	public static final String MODULE_SYSADMIN = "common,catalog,dict,doc,menu,notice,organ,person,role,roleuser,syslog,user";
	
	/* 合同管理模块对应的namespace */
	public static final String MODULE_HTADMIN = "ht";
	
	/* 计划管理模块对应的namespace */
	public static final String MODULE_PMADMIN = "pm";
	
	/* 综合管理模块对应的namespace */
	public static final String MODULE_ZHADMIN = "analyze";
	
	/* 工作流组件对应的namespace */
	public static final String MODULE_TOPFLOW = "topflow";
	
	/* 登录用户ID在SESSION中保存的键名 */
	public final static String LOGIN_ORGAN_ID = "LOGIN_ORGAN_ID";
	
	public final static String USER_ADMIN = "admin";// 超级管理员用户名
	
	/* 组织机构中是否公司的标识 */
	public final static String IS_COMPANY_Y = "1";// 是公司
	public final static String IS_COMPANY_N = "0";// 不是公司
	
	/*人员性别*/
	public final static String PERSON_SEX_G = "0";//性别为女
	public final static String PERSON_SEX_B = "1";//性别为男
	
	/**
	 * 通过servlePath获取模块名
	 * 合法的模块名有：
	 * SYSADMIN,HTADMIN,PMADMIN,ZHADMIN,TOPFLOW
	 * @param servletPath
	 * @return
	 */
	public static String getModelName(String servletPath){
		String namespace = servletPath.split("/")[1];
		if(MODULE_SYSADMIN.contains(namespace)){
			return "SYSADMIN";
		}
		if(MODULE_HTADMIN.contains(namespace)){
			return "HTADMIN";
		}
		if(MODULE_PMADMIN.contains(namespace)){
			return "PMADMIN";
		}
		if(MODULE_ZHADMIN.contains(namespace)){
			return "ZHADMIN";
		}
		if(MODULE_TOPFLOW.contains(namespace)){
			return "TOPFLOW";
		}
		return "SYSADMIN";// 默认进入系统管理模块
	}
	
	/* 配置在struts.properties配置文件中的上传文件路径键名 */
	public static final String STRUTS_UPLOAD_PATH = "struts.file.uploads.targetpath";
	
}
