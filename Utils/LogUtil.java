package cn.itcast.testmanager.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import cn.itcast.testmanager.common.entity.VisitLog;


public class LogUtil {

	private static final String file_separatorChar = ".";
	
	/**
	 * 
	 * <P>每天凌晨1点读昨天的访问日志</P>
	 * @param filePath 访问日志路径
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static synchronized List<VisitLog> readVisitLog(String filePath) 
			throws NumberFormatException, IOException, ParseException {

		/*if (filePath == null || "".equals(filePath)) {
			return null;
		}

		// 得到前一天的时间格式字符串
		String dateStr = DateHelper.getDateTime_I(DateHelper.getAddDays(-2));
		String fileAllName = filePath + file_separatorChar + dateStr;
		File file = new File(fileAllName);

		if (file.exists()) {// 文件存在
			List<VisitLogVO> visitLogList = new LinkedList<VisitLogVO>();
			BufferedReader in = new BufferedReader(new FileReader(fileAllName));
			String line = null;
			String date = null;
			VisitLogVO visitPO = null;
			String[] strArray = null;
			String origin_type = null;
			String user_id = null;
			String subject_id = null;
			String nameSpace = null;
			String requestName = null;
			String visitTimes = null;
			while ((line = in.readLine()) != null) {
				visitPO = new VisitLogVO();
				date = line.substring(1, line.indexOf("]")).trim();
				if (date != null && !"".equals(date)) {
					visitPO.setVisit_date(DateHelper.x_dateTimeFormat
							.parse(date));
				}
				strArray = line.split(",");
				if (strArray != null && strArray.length == 6) {
					// 终端来源类型
					origin_type = strArray[0].substring(strArray[0]
							.indexOf("=")+1);
					if (origin_type != null && !"".equals(origin_type)) {
						visitPO.setOrigin_terminal(Integer.valueOf(origin_type));
					}
					// 用户编号
					user_id = strArray[1].substring(strArray[1].indexOf("=")+1);
					if (user_id != null && !"".equals(user_id)) {
						visitPO.setUser_id(Long.valueOf(user_id));
					}
					
					// 命名空间
					nameSpace = strArray[3].substring(strArray[3].indexOf("=") + 2);
					if(nameSpace!=null && !"".equals(nameSpace)){
						visitPO.setVisit_topic(nameSpace);
					}else{
						nameSpace = strArray[4].substring(strArray[4].indexOf("=")+1, 
									strArray[4].indexOf("-"));
						visitPO.setVisit_topic(nameSpace);
					}
					
					// 请求名称
					requestName = strArray[4].substring(strArray[4]
							.indexOf("=")+1);
					visitPO.setVisit_name(requestName);
					// 请求时间
					visitTimes = strArray[5]
							.substring(strArray[5].indexOf("=")+1,strArray[5].length()-2);
					if (visitTimes != null && !"".equals(visitTimes)) {
						visitPO.setDuration_times(Float.valueOf(visitTimes));
					}
				}
				visitLogList.add(visitPO);
			}
			return visitLogList;
		}*/
		return null;
	}

}
