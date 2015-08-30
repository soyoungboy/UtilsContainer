package cn.itcast.testmanager.common.util;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import com.flycloud.Client;
import com.flycloud.data.Limit;
import com.flycloud.data.Operate;
import com.flycloud.data.Response;
import com.flycloud.data.SObject;
import com.flycloud.data.Sort;
import com.flycloud.data.response.Data;
import com.flycloud.data.response.ListData;
import com.flycloud.mathnew.Term;

/**
 * 搜索引擎客户端工具类
 * 2014-01-20
 */
public class ElasticsearchClient {

	private static Client client = new Client();
	static Logger log = Logger.getLogger("ElasticsearchLog");
	
	/**
	 * 新增或修改 同步数据到搜索引擎
	 * @param o1
	 */
	public static void updateOrSaveTheme(SObject o1){
		Client client = new Client();
		Collection<SObject> objs = new ArrayList<SObject>();
		if(o1!=null){
			objs.add(o1);
			client.index(Operate.CreateOrSave, objs.toArray(new SObject[objs.size()]));
			StringBuilder sb = new StringBuilder();
			sb.append("updateOrSaveTheme: ");
			sb.append("theme_id="+(o1.get("id")==null?0:(String)o1.get("id")));
			sb.append("\t ,subject_id="+(o1.get("subject_id")==null?0:(o1.get("subject_id") + "")));
			log.info(sb.toString());
			
		}
	}
	
	/**
	 * 删除试题数据 同步搜索引擎
	 * @param o1
	 */
	public static void deleteTheme(SObject o1){
		Client client = new Client();
		Collection<SObject> objs = new ArrayList<SObject>();
		if(o1!=null){
			objs.add(o1);
			client.index(Operate.Delete, objs.toArray(new SObject[objs.size()]));
			StringBuilder sb = new StringBuilder();
			sb.append("deleteTheme: ");
			sb.append("theme_id="+(o1.get("id")==null?0:(String)o1.get("id")));
			sb.append("\t ,subject_id="+(o1.get("subject_id")==null?0:(o1.get("subject_id") + "")));
			log.info(sb.toString());  
		}
	}
	
	/**
	 * 得到查询返回试题结果集
	 * @param term 组装查询条件
	 * @param pageSize 每页显示条数
	 * @param currentpageNum 当前页数
	 * @param queryKeywords 查询关键词
	 * @param order 排序，试题里排序字段
	 * @return
	 */
	public static ListData getQueryThemesResult(Term term,int pageSize,
			int currentpageNum,String queryKeywords,String order){
		
		if(pageSize<=0){
			return null;
		}
		if(currentpageNum <= 0){
			currentpageNum = 1;
		}
		Response res = null;
		Data data = null;
		try{
			//分页,搜索引擎偏移量从0开始
			int offset = pageSize*(currentpageNum > 0 ? currentpageNum -1 : 0);

			 Limit limit = new Limit(offset, pageSize);
			 //排序
			 Sort sort = new Sort(); 
			 sort.put("year", Sort.Order.desc);
			 sort.put("createdate", Sort.Order.desc);
			 
			 res = client.query(queryKeywords, term, sort, limit, "");
			//log.info("------------res="+res);
			data = res.getData();  
			if(data==null){
				return null;
			}  
		}catch(Exception es){
			log.info(es);	
		}   
		return (ListData)data;
	}

}
