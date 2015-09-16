package com.dihaiboyun.common.util.image;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageUtil {
	
	/**
	 * 获取远程图片尺寸 {宽, 高}
	 * @param imageUrl
	 * @return	获取失败时反回{0, 0}
	 */
	public static int[] getRemoteImageWidthHeight(String imageUrl){
		int[] wh = new int[]{0,0};
		Image img = null;
		try {
			URL u = new URL(imageUrl);
		
			ImageIO.setUseCache(false); //使用系统缓存
		
			img = ImageIO.read(u);
			wh[0] = img.getWidth(null);
			wh[1] = img.getHeight(null);
			
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			if(img !=null)
				img.flush();
			
			img = null;
		}

		return wh;
	}
	public static void main(String[] args) throws Exception {
		String imageUrl = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superplus/img/logo_white_ee663702.png";
		int[] wh = getRemoteImageWidthHeight(imageUrl);		
		System.out.println(wh[0]);
		System.out.println(wh[1]);
	}
}
