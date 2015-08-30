package cn.itcast.testmanager.common.util;


/**
 * 搜索工具类
 * 2014-01-18
 */
public class ElasticsearchUtil {
	/*
	*//**
	 * 
	 * @param theme 查询条件封装对象
	 * @param education_phase 教育阶段
	 *   1-学前教育   2-小学   3-初中   4-高中   5-大学
	 * @param theme_system 
	 * 1 系统规则试题   2用户上传规则试题   3用户上传非规则试题
	 * @param user_id 
	 * @param origin_terminal数据类型int（1web,2学习pad, 3手机，4wap）
	 * @user_id 用户编号 （当查询某用户上传试题时使用）
	 * @return
	 * @throws ParseException 
	 *//*
	public static Term getThemeQueryTerm(ThemeVO theme,
			int education_phase,int theme_system, int origin_terminal,
			long user_id) throws ParseException{
		
		 *//*********必须条件  start************//*
		List<Term> andTerms = new LinkedList<Term>();
		if(theme_system>0){
			andTerms.add(new Equal("theme_system", theme_system));
		}
		int subject_id = theme.getSubject_id();
		if(subject_id>0){
			andTerms.add(new Equal("subject_id", subject_id));
		}
		if(theme.getId()>0){
			andTerms.add(new Equal("id", theme.getId()));
		}
		if(user_id>0){
			andTerms.add(new Equal("user_id", user_id));
		}else{//非用户上传试题,呈现的是已审核试题
			andTerms.add(new Equal("auditing", 3));
		}
		if(theme.getTitletype()>0){
			andTerms.add(new Equal("titletype", theme.getTitletype()));
		}
		if(theme.getType()>0){
			andTerms.add(new Equal("type", theme.getType()));
		}
		if(theme.getYear()>0){
			andTerms.add(new Equal("year", theme.getYear()));
		}
		if(theme.getAreaid()>-1){
			andTerms.add(new Equal("areaid", theme.getAreaid()));
		}
		
		int grade = theme.getGread();//年级，默认值是-1
		if(grade > 0){
			andTerms.add(new Equal("gread", grade));
		}else{
			//小学
			if(education_phase==IConstantsBase.XIAOXUE ){
				andTerms.add(new Or(new Equal("gread", 1), new Equal("gread", 2), new Equal("gread", 3),
						new Equal("gread", 4),new Equal("gread", 5),new Equal("gread", 6)));
			//初中
			}else if(education_phase==IConstantsBase.CHUZHONG ){
				andTerms.add(new Or(new Equal("gread", 7), new Equal("gread", 8), new Equal("gread", 9)));
			//高中
			}else if(education_phase==IConstantsBase.GAOZHONG ){
				andTerms.add(new Or(new Equal("gread", 10), new Equal("gread", 11), new Equal("gread", 12)));
			}
		}
		//TODO 目前pad先不展示最新试题，后期需放开，老师端不限
//		if(origin_terminal==2){
//			String str = "2014-01-10 00:00:00";
//	        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
//	        String createdate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(date);
//	        andTerms.add(new Less("createdate", createdate));
//		}
		if(theme.getBeginDate()!=null){
	        String beginDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	        	.format(theme.getBeginDate());
	        //大于或等于
	        andTerms.add(new GreaterEqual("createdate", beginDate));

		}
		if(theme.getEndDate()!=null){
	        String endDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	        	.format(theme.getEndDate());
	        //小于或等于
	        andTerms.add(new LessEqual("createdate", endDate));

		}
		
		//知识点搜索,英文逗号分割的知识点编号
		String allken_ids = theme.getAllken_ids();
        if(!StringUtil.isEmpty(allken_ids)) {
        	String[] ids = allken_ids.split(",");
        	List<Equal> orIds = new ArrayList<Equal>();
        	for(String id : ids) {
        		orIds.add(new Equal("allken_ids", id));
        	}
        	andTerms.add(new Or(orIds.toArray(new Equal[0])));
        }

		Term must = new And(andTerms.toArray(new Term[andTerms.size()]));
		*//**************必须条件 end*****************************//*
		
		List<Term> otherTerms = new LinkedList<Term>();
		if(theme.getDifficulty()>0){
			otherTerms.add(new Equal("difficulty", theme.getDifficulty()));
		}
		Term should = null;	    
	    // 以or来组织条件
	    if(otherTerms.size() > 0)
	    	should = new Any(otherTerms.toArray(new Term[otherTerms.size()]));
	    
	    Term term = new And(should, must);
	    return term;
	}
	
	*//**
	 * 得到用户所在地区高考、中考、所考试卷类型
	 * @param areaid 地区编码
	 * @param education_phase 学段
	 * @subject_id 学科编号
	 * @return
	 *//*
	public static int getThemeAreacodeForSearch(int areaid,
			int education_phase,int subject_id){
		//初中
		if(education_phase==IConstantsBase.CHUZHONG){
			return areaid;
		//高中
		}else if(education_phase==IConstantsBase.GAOZHONG){
			switch(areaid){
			case -1:
			case 0:
				return areaid;
			//全国一卷
			case 410000://河南
			case 130000://河北
			case 140000://山西
				return 0;
			//全国二卷
			case 630000://青海
			case 540000://西藏
			case 620000://甘肃
			case 520000://贵州
			case 150000://内蒙
			case 650000://新疆
			case 640000://宁夏
			case 220000://吉林
			case 230000://黑龙江
			case 530000://云南
				return 0;
			//大纲全国卷（最后一年)
			case 450000://广西
				return 0;
			//自主命题
			case 110000: //北京
			case 310000: //上海
			case 500000: //重庆
			case 440000: //广东
			case 340000: //安徽
			case 370000: //山东
			case 350000: //福建
			case 320000: //江苏
			case 120000: //天津
			case 510000: //四川
			case 330000: //浙江
				return areaid;
			case 460000: //海南省：自主命题（政、史、地、理、化、生）+ 新课标全国Ⅱ卷（语、数、英）
				if(subject_id==IConstantsBase.LANGUAGE
				||subject_id==IConstantsBase.MATH
				||subject_id==IConstantsBase.ENGLISH){
					return 0;
				}else{
					return areaid;
				}
			case 420000: //湖北省：自主命题（语、数、英）+ 新课标全国Ⅰ卷（文综、理综）
			case 430000: //湖南省 同湖北
			case 360000: //江西  同湖北
			case 610000: //陕西省 同湖北
				if(subject_id==IConstantsBase.LANGUAGE
				||subject_id==IConstantsBase.MATH
				||subject_id==IConstantsBase.ENGLISH){
					return areaid;
				}else{
					return 0;
				}
			case 210000: //辽宁省：自主命题（语、数、英）+ 新课标全国Ⅱ卷（文综、理综）					
				if(subject_id==IConstantsBase.LANGUAGE
				||subject_id==IConstantsBase.MATH
				||subject_id==IConstantsBase.ENGLISH){
					return areaid;
				}else{
					return 0;
				}
			default :
				return 0;
			}
		}
		return 0;
	}*/
}
