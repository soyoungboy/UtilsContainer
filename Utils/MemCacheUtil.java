package cn.itcast.testmanager.common.util;


import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
/**
 * 
* <p>memcached工具类</p>
*
 */
public class MemCacheUtil {
	
	/**2分钟*/
	public static final int CACHE_MINUTES = 2;
	/** 30分钟 */
	public static final int CACHE_30_MINUTES = 30;
	
	/**
	 * 用对象的属性来把要缓存的对象放到缓存中去
	 * o要缓存的对象
	 * fieldNames缓存对象唯一标示用的属性名.
	 */
	public static void putCache(Object o,String[] fieldNames){
		putCache(o,fieldNames,5);
		return;
	}
	
	/**
	 * 用对象的属性来把要缓存的对象放到缓存中去
	 * o要缓存的对象
	 * fieldNames缓存对象唯一标示用的属性名.
	 * min缓存时间(分钟)
	 */
	public static void putCache(Object o,String[] fieldNames,int min){
		//新建memCachedclient
		MemCachedClient mc = new MemCachedClient();
		mc.setCompressEnable(false);
		mc.setUseCopyMode(true);
		//建立对象标示，标示里包含类名+"|"+标示属性循环fieldName:fileValue+;
		StringBuffer key = new StringBuffer(64);
		key.append(o.getClass().getName()).append("|");
		for(int i = 0;i<fieldNames.length;i++){
			String fieldName = fieldNames[i];
			Object tmp=null;
			try {
				tmp = Reflection.getProperty(o, fieldName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(i==fieldNames.length-1){
				key.append(fieldName).append(":").append(tmp.toString());
			}else{
				key.append(fieldName).append(":").append(tmp.toString()).append(";");
			}
		}
		mc.set(key.toString(), o,min*60);
		return;
	}
	
	/**
	 * 用对象的属性来从缓存中取得缓存对象
	 * 缓存对象的类
	 * fieldNames缓存对象唯一标示用的属性名.
	 * fieldValue缓存对象唯一标示用的属性值
	 * @return
	 */
	public static Object get(Class<?> objectclass,String[] fieldNames,String[] fieldValue){
		//新建memCachedclient
		MemCachedClient mc = new MemCachedClient();
		mc.setCompressEnable(false);
		mc.setUseCopyMode(true);
		StringBuffer key = new StringBuffer(64); 
		key.append(objectclass.getName()).append("|");
		for(int i = 0;i<fieldNames.length;i++){
			String fieldName = fieldNames[i];
			if(i==fieldNames.length-1){
				key.append(fieldName).append(":").append(fieldValue[i]);
			}else{
				key.append(fieldName).append(":").append(fieldValue[i]).append(";");
			}
		}
		return mc.get(key.toString());
	}
	
	/**
	 * 把要缓存的对象放到缓存中去
	 * o要缓存的对象
	 * key缓存关键字
	 */
	public static void putCache(Object o,String key){
		//新建memCachedclient
		putCache(o,key,15);
		return;
	}
	
	/**
	 * 从缓存中取得缓存对象
	 * 
	 * key缓存关键字
	 * 
	 */
	public static Object get(String key){
		//新建memCachedclient
		MemCachedClient mc = new MemCachedClient();
		mc.setCompressEnable(false);
		mc.setUseCopyMode(true);
		return mc.get(key);
	}
	
	/**
	 * 把要缓存的对象放到缓存中去
	 * o要缓存的对象
	 * key缓存关键字
	 */
	public static void putCache(Object o,String key,int mins){
		//新建memCachedclient
		MemCachedClient mc = new MemCachedClient();
		mc.setCompressEnable(false);
		mc.setUseCopyMode(true);
		
		mc.set(key,o,mins * 60);
		 
		return;
	}
	
	/**
	 * lipp add
	 * removeCache(String key)。
	 * <p>删除缓存对象。</p>
	 * @param key
	 * @return 
	 */
	public static boolean removeCache(String key){
		if(key == null){
			return false;
		}
		MemCachedClient mc = new MemCachedClient();
		mc.setCompressEnable(false);
		mc.setUseCopyMode(true);
		return mc.delete(key);
	}
	
	public static void main(String[] args){
		String[] serverlist = {"10.2.2.127:3232"};

		// initialize the pool for memcache servers
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(serverlist);

		pool.setInitConn(1);
		pool.setMinConn(1);
		pool.setMaxConn(1);
		pool.setMaintSleep(500);
		
		pool.setNagle(false);
		pool.initialize();
		
		MemCacheUtil.putCache("a", "a");
		MemCacheUtil.removeCache("a");
	}
}
