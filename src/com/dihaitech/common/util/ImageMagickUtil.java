package com.dihaitech.common.util;

import java.awt.Dimension;
import java.awt.Rectangle;

import magick.CompositeOperator;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

/**
 * 使用ImageMagic压缩图片
 * 
 * @author nathan
 * 
 */
public class ImageMagickUtil {
	static {
		// 不能漏掉这个，不然jmagick.jar的路径找不到
		System.setProperty("jmagick.systemclassloader", "no");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 System.out.println(System.getProperty("java.library.path"));

		// 测试代码
		try {
//			reSize("D:/workgbk/acomp/WebRoot/image/bg.jpg", "D:/13440.jpg",
//					"D:/w.jpg", 300, 300);
			
			reSize("/Users/qiusen/Desktop/0046032.png", "/Users/qiusen/Desktop/9a8d222.jpg",
					"/myfile/workspaceutf8/cms-manage/webapp/image/shuiyin.png", 300, 300);

			// cutImg("D:/13440.jpg", "D:/13440_new.jpg", 480, 480, 80, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 等比压缩图片，填充白色背景
	 * 
	 * @param bgPic
	 * @param srcPic
	 * @param targetPic
	 * @param targetWidth
	 * @param targetHeight
	 * @return
	 * @throws MagickException
	 */
	public static boolean reSize(String bgPic, String srcPic, String targetPic,
			int targetWidth, int targetHeight) throws MagickException {

		System.out.println(System.getProperty("java.library.path"));

		boolean success = false;

		if (targetWidth <= 0 && targetHeight <= 0) {
			System.out.println("图片不存在");
			return false;
		}

		ImageInfo info = new ImageInfo();
		MagickImage bgImage = null;
		MagickImage srcImage = null;
		MagickImage targetBgImage = null;
		MagickImage targetImage = null;

		int offx = 0;
		int offy = 0;

		try {
			// 背景图
			bgImage = new MagickImage(new ImageInfo(bgPic));

			// 原图
			srcImage = new MagickImage(new ImageInfo(srcPic));
			// 原图尺寸
			Dimension dimension = srcImage.getDimension();
			int srcWidth = (int) dimension.getWidth();
			int srcHeight = (int) dimension.getHeight();

			// 图片等比压缩
			// 如果设定的长度之比大于图片本身的长度比。说明图片太长了
			int toWidth = targetWidth;
			int toHeight = targetHeight;

			if (srcWidth < targetWidth && srcHeight < targetHeight) { // 如果图片宽高都小于目标宽高，直接铺背景
				toWidth = srcWidth;
				toHeight = srcHeight;

			} else if (((float) targetWidth / (float) targetHeight) > ((float) srcWidth / (float) srcHeight)) {
				toWidth = (int) (((float) targetHeight / (float) srcHeight) * srcWidth);
				toHeight = targetHeight;
			} else {
				toWidth = targetWidth;
				toHeight = (int) (((float) targetWidth / (float) srcWidth) * srcHeight);
			}

			targetBgImage = bgImage.scaleImage(targetWidth, targetHeight);
			srcImage.profileImage("*", null);// 移除图片的其他信息
			targetImage = srcImage.scaleImage(toWidth, toHeight);

			offx = (targetWidth - toWidth) / 2;
			offy = (targetHeight - toHeight) / 2;

			targetBgImage.compositeImage(CompositeOperator.AtopCompositeOp,
					targetImage, offx, offy);
			targetBgImage.setFileName(targetPic);
			targetBgImage.writeImage(info);

			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (bgImage != null) {
				bgImage.destroyImages();
			}
			if (srcImage != null) {
				srcImage.destroyImages();
			}
			if (targetBgImage != null) {
				targetBgImage.destroyImages();
			}
			if (targetImage != null) {
				targetImage.destroyImages();
			}
		}

		return success;

	}

	/**
	 * @param imgPath
	 *            源图路径
	 * @param toPath
	 *            修改图路径
	 * @param w
	 *            宽度
	 * @param h
	 *            高度
	 * @param x
	 *            左上角的 X 坐标
	 * @param y
	 *            左上角的 Y 坐标
	 * @throws MagickException
	 */
	public static void cutImg(String imgPath, String toPath, int w, int h,
			int x, int y) throws MagickException {
		ImageInfo infoS = null;
		MagickImage image = null;
		MagickImage cropped = null;
		Rectangle rect = null;

		try {
			infoS = new ImageInfo(imgPath);
			image = new MagickImage(infoS);
			rect = new Rectangle(x, y, w, h);
			cropped = image.cropImage(rect);
			cropped.setFileName(toPath);
			cropped.writeImage(infoS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cropped != null) {
				cropped.destroyImages();
			}

		}
	}
}
