package cn.itcast.testmanager.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import cn.itcast.testmanager.common.cache.CookieUtil;
import cn.itcast.testmanager.common.cache.IMemCache;

/**
 * <p>验证码生成工具类</p>
 * @author lipp
 *
 */
public class CaptchasUtil
{
	
	/**
	 * <p>生成验证码图的方法</p>
	 * 该方法将验证码值保存进指定名称的cookie中。返回字节输入流。<br>
	 * cookieName参数为空则返回null。
	 * @param cookieUtil
	 * @param cookieName 验证码要保存的cookieName。推荐将其写在常量接口IcookieKey中，防止使用重复name。
	 * @param cookieflag 浏览器cookie状态 1正常；0禁用。
	 * 					  浏览器cookie正常，验证码存放于cookie;否则验证码存放在merrycatc中，key统一使用cookie的key
	 * @param uuidReg 用户唯一标识
	 * @return
	 * @throws Exception
	 * 2013-1-22
	 */
	public static ByteArrayInputStream verifyCode(CookieUtil cookieUtil,
			String cookieName, IMemCache icacheUtil, int cookieflag, String uuidReg)
	throws Exception
	{
		if(cookieUtil==null || StringUtil.isEmpty(cookieName))
			return null;
		int width = 70;
		int height = 30;
		BufferedImage buffImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		Font font = new Font("Times New Roman", Font.HANGING_BASELINE, 28);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width - 1, height - 1);
		g.setColor(Color.GRAY);
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		String content = RandomUtil.getRandomIntNum(4);
		FontRenderContext context = g.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(content, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = -bounds.getY();
		double baseY = y + ascent;
		g.setColor(getRandColor(1, 100));
		g.setColor(new Color(20 + random.nextInt(110),
				20 + random.nextInt(110), 20 + random.nextInt(110)));
		g.drawString(content, (int) x, (int) baseY);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageOutputStream imageOut = ImageIO
				.createImageOutputStream(output);
		ImageIO.write(buffImg, "JPEG", imageOut);
		imageOut.close();
		if(cookieflag == 1) {
			//添加到cookie中，用于回传验证码后进行比对。
			cookieUtil.addCookie(cookieName, content);
		}else {
			icacheUtil.putCache(cookieName+uuidReg,content, 300);
		}
		return new ByteArrayInputStream(output.toByteArray());
	}
	
	/**
	 * 返回一个随机颜色对象
	 * @return
	 * @author lijunmin
	 */
	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	/**
	 * <p>比对验证码。</p>
	 * 当且仅当传入的验证码和cookieName指定的验证码都不为空，且相同时返回true。否则返回false。<br>
	 * 该方法执行后将删除以cookieName为key中存放的验证码值。
	 * @param cookieUtil
	 * @param code 要比对的验证码
	 * @param cookieName 验证码保存的cookieName。推荐将其写在常量接口IcookieKey中，防止使用重复name。
	 * @param cookieflag 浏览器cookie状态 1正常；0禁用。
	 * 					  浏览器cookie正常，验证码存放于cookie;否则验证码存放在merrycatc中，key统一使用cookie的key
	 * @param uuidReg 用户唯一标识
	 * @return
	 * @author leo_soul
	 * 2013-1-22
	 */
	public static boolean equalsVerifyCode(CookieUtil cookieUtil, String code, 
			String cookieName, IMemCache icacheUtil, int cookieflag, String uuidReg)
	{
		if(cookieUtil==null || StringUtil.isEmpty(code) || StringUtil.isEmpty(cookieName))
			return false;
		String codeValue = "";
		if (cookieflag == 1) {//如果cookie正常，cookie中取值
			codeValue = cookieUtil.getCookieValue(cookieName);	
		}else {//如果cookie禁用，merrcatc中取值
			codeValue = (String) icacheUtil.get(cookieName+uuidReg);
		}
		boolean rtn = false;
		if (!StringUtil.isEmpty(codeValue) && code.trim().equals(codeValue))
			rtn = true;
//		cookieUtil.deleteCookie(cookieName);
		return rtn;
	}
	
	
	/**
	 * <p>判断是否为padcode格式</p>
	 * 4部分组成。用"-"分割的字符串。<br>
	 * @param device_code
	 * @return 是，true；不是，false。
	 * @author leo_soul
	 * 2012-7-2
	 */
	public static boolean isPadCode(String device_code)
	{
		if(null==device_code||"".equals(device_code.trim()))
			return false;
		String[] padCodeSplit = device_code.trim().split("-");
		if(padCodeSplit.length!=4)
			return false;
		for(String temp:padCodeSplit)
		{
			if(null==temp||"".equals(temp.trim()))
				return false;
		}
		String[] MACSplit = padCodeSplit[3].split(":");
		if(MACSplit.length!=6)
			return false;
		for(String temp:MACSplit)
		{
			if(null==temp||"".equals(temp.trim()))
				return false;
		}
		return true;		
	}
}
