package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilCommon {
	public static String getSerial(Date date, int index) {
		long msel = date.getTime();
		SimpleDateFormat fm = new SimpleDateFormat("MMddyyyyHHmmssSS");
		msel += index;
		date.setTime(msel);
		String serials = fm.format(date);
		return serials;
	}
	// 检查是否是图片格式，忽略大小写
	public static boolean checkIsImage(String imgStr) {
		boolean flag = false;
		if (imgStr != null) {
			if (imgStr.equalsIgnoreCase(".gif")
					|| imgStr.equalsIgnoreCase(".jpg")
					|| imgStr.equalsIgnoreCase(".jpeg")
					|| imgStr.equalsIgnoreCase(".png")) {
				flag = true;
			}
		}
		return flag;
	}
	
	// 检查是否是文档，忽略大小写
	public static boolean checkIsDocument(String docStr) {
		boolean flag = false;
		if (docStr != null) {
			if (docStr.equalsIgnoreCase(".doc")
					|| docStr.equalsIgnoreCase(".docx")
					|| docStr.equalsIgnoreCase(".ppt")
					|| docStr.equalsIgnoreCase(".pptx")
					|| docStr.equalsIgnoreCase(".xls")
					|| docStr.equalsIgnoreCase(".xlsx")
					|| docStr.equalsIgnoreCase(".pdf")
					|| docStr.equalsIgnoreCase(".txt")
					|| docStr.equalsIgnoreCase(".rar")) {
				flag = true;
			}
		}
		return flag;
	}
	
	
    public static Date StrToDate(String str) throws ParseException{
    	return new SimpleDateFormat("MM/dd/yyyy").parse(str);
    }
}