package action;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import util.UtilCommon;

import com.opensymphony.xwork2.ActionSupport;

public class UploadUtilAction extends ActionSupport implements
		ServletResponseAware {

	private File fileupload; // 和JSP中input标记name同名
	private String imageUrl;//在页面上显示图片时用于接收页面传来的图片名称
	//private String attachmentUrl; //未使用
	private String fileRealName;//下载文件时，用于接收文件的名称
	private HttpServletResponse response;
	// Struts2拦截器获得的文件名,命名规则，File的名字+FileName
	// 如此处为 'fileupload' + 'FileName' = 'fileuploadFileName'
	private String fileuploadFileName; // 上传来的文件的名字,原始名称
	private static String fileUpperLevelDirectory  = "/file/";//要上传的文件的上一级目录文件名
	private static String charSet = "utf-8";//设置response的编码方式

	
	public java.io.InputStream getDownLoad(){
		 String name = fileRealName;
		 String realPath="\\file\\"+name;
		 InputStream in = ServletActionContext.getServletContext().getResourceAsStream(realPath);
		 return in;
	}

	public String uploadFile2() {
		//上传文档类型的文件
		String extName = ""; // 保存文件拓展名
		String newFileName = ""; // 保存新的文件名
		String nowTimeStr = ""; // 保存当前时间
		PrintWriter out = null;
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");// 时间格式化的格式
		nowTimeStr = sDateFormat.format(new Date()); // 将当前时间年月日时分秒转换为字符串格式
		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
		int rannum = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
		String savePath = ServletActionContext.getServletContext().getRealPath(""); // 获取项目根路径
		savePath = savePath + fileUpperLevelDirectory;
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding(charSet); // 务必，防止返回文件名是乱码
		
		try {
			out = response.getWriter();
			if (fileuploadFileName.lastIndexOf(".") >= 0) {
				// 获取拓展名
				extName = fileuploadFileName.substring(fileuploadFileName.lastIndexOf("."));
			}
			newFileName = nowTimeStr + rannum + extName; // 文件重命名后的名字
			String filePath = savePath + newFileName;//文件的最终上传路径，绝对完整路径
			filePath = filePath.replace("\\", "/");
			//检查上传的是否是图片
			if (UtilCommon.checkIsDocument(extName)) {
				//将
				FileUtils.copyFile(fileupload, new File(filePath));
				out.print("<font color='red'>" + fileuploadFileName
						+ "上传成功!</font>#" + filePath + "#" + fileuploadFileName);
			} else {
				out.print("<font color='red'>上传的文件类型错误，请选择doc,docx,ppt,pptx,xls,xlsx,txt和pdf格式的文件!</font>");
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			out.print("<font color='red'>上传失败，出错啦!</font>");
		}finally{
			out.flush();
			out.close();
		}
		return null;
	}
	
	public String uploadFile() {
		//上传图片
		String extName = ""; // 保存文件拓展名
		String newFileName = ""; // 保存新的文件名
		String nowTimeStr = ""; // 保存当前时间
		PrintWriter out = null;
		SimpleDateFormat sDateFormat;
		Random r = new Random();

		String savePath = ServletActionContext.getServletContext().getRealPath(
				""); // 获取项目根路径
		savePath = savePath + fileUpperLevelDirectory;
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding(charSet); // 务必，防止返回文件名是乱码

		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
		int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
		sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
		nowTimeStr = sDateFormat.format(new Date()); // 当前时间
		// 获取拓展名
		
		try {
			out = response.getWriter();
			if (fileuploadFileName.lastIndexOf(".") >= 0) {
				extName = fileuploadFileName.substring(fileuploadFileName
						.lastIndexOf("."));
			}
			newFileName = nowTimeStr + rannum + extName; // 文件重命名后的名字
			String filePath = savePath + newFileName;
			filePath = filePath.replace("\\", "/");
			//检查上传的是否是图片
			if (UtilCommon.checkIsImage(extName)) {
				FileUtils.copyFile(fileupload, new File(filePath));
				out.print("<font color='red'>" + fileuploadFileName
						+ "上传成功!</font>#" + filePath + "#" + fileuploadFileName);

			} else {
				out.print("<font color='red'>上传的文件类型错误，请选择jpg,jpeg,png和gif格式的图片!</font>");
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			out.print("<font color='red'>上传失败，出错啦!</font>");
		}finally{
			out.flush();
			out.close();
		}
		return null;
	}

	public String showImage() throws Exception {
		// 根据图片地址构造file对象
		File file = new File(imageUrl);
		InputStream is = new FileInputStream(file);
		Image image = ImageIO.read(is);// 读图片
		String imageType = imageUrl.substring(imageUrl.lastIndexOf(".") + 1);
		RenderedImage img = (RenderedImage) image;
		OutputStream out = response.getOutputStream();
		ImageIO.write(img, imageType, out);
		out.flush();
		out.close();
		return null;
	}

	public File getFileupload() {
		return fileupload;
	}

	public void setFileupload(File fileupload) {
		this.fileupload = fileupload;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public String getFileRealName() {
		return fileRealName;
	}

	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getFileuploadFileName() {
		return fileuploadFileName;
	}

	public void setFileuploadFileName(String fileuploadFileName) {
		this.fileuploadFileName = fileuploadFileName;
	}
    
}
