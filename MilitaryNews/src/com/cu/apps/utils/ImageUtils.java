package com.cu.apps.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;

public class ImageUtils {

	// 图片的最大最小像??
	private final static int MIN_SIDE_LENGTH = -1;
	private final static int MAX_NUMBER_OF_PIXLES = 1024 * 1024;
	
	// 缓存图片的路�?
    public final static String CACHE_PIC_PATH = File.separator + "sdcard"
				+ File.separator + "hyenaNews";
		
	public final static String CACHE_PIC_DIR_PATH = CACHE_PIC_PATH
				+ File.separator + "cache" + File.separator;

	/**
	 * 计算图片的采样大�?
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return 采样大小
	 */
	public static int computeSampleSize(Options options, int minSideLength,
			int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 根据字节数组返回图片像素
	 * 
	 * @param data
	 * @return
	 */
	public static Bitmap decodeByteArray(byte[] data) {

		if (data == null) {
			return null;
		}

		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		BitmapFactoryUtil.decodeByteArray(data, 0, data.length, opts);

		Options options = new Options();
		options.inSampleSize = computeSampleSize(opts, MIN_SIDE_LENGTH,
				MAX_NUMBER_OF_PIXLES);

		return BitmapFactoryUtil.decodeByteArray(data, 0, data.length, options);
	}

	/**
	 * 图片本地保存</br> 1、图片存在不再进行存??2、图片不存在进行存放
	 * 
	 * @param bitmap
	 * @throws IOException
	 */

	public static void saveImage(String imageName, Bitmap bmp,String imageType)
			throws IOException {

		if (bmp == null || bmp.isRecycled())
			return;

		String path = CACHE_PIC_DIR_PATH + imageName;
		File f = new File(path);

		// 要存放的图片不存??
		if (!f.isFile()) {
			String dir = path.substring(0, path.lastIndexOf("/"));
			File dirFile = new File(dir);
			if (!dirFile.exists()) {// 目录不存在时，兴??

				boolean isMakeDir = dirFile.mkdirs();

				if (!isMakeDir) {// 兴建目录失败
					return;
				}
			}

			f.createNewFile();

			FileOutputStream fOut = null;
			try {

				fOut = new FileOutputStream(f);
				Log.e("88888888888888",imageType);
				if("PNG".equals(imageType)){
				  bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
				}else{
				  bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);	
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (fOut != null) {
					fOut.flush();
					fOut.close();
				}
			}
		}

	}

	/**
	 * 获取对应名称的图�?
	 * @param path 缓存的路�?         
	 * @return
	 */
	public static Bitmap getImage(String imageName) throws Exception {
		Bitmap bit = null;
		try {

			String path = CACHE_PIC_DIR_PATH + imageName;
			File imageFile = new File(path);
			if (imageFile.exists()) {
				// 当前图片存在
				bit = BitmapFactoryUtil.decodeFile(path);
//				BitmapFactory.Options opts = new BitmapFactory.Options(); //当前图片不保存到内存
//				opts.inPurgeable = true;
//		        opts.inSampleSize = 2;
//				bit = BitmapFactory.decodeFile(path,opts);
				
//				BitmapFactory.Options opts = new BitmapFactory.Options();  
//				opts.inJustDecodeBounds = true;  
//				BitmapFactory.decodeFile(path, opts);  
//				  
//				opts.inSampleSize = computeSampleSize(opts, -1, 128*128);  
//				opts.inJustDecodeBounds = false;  
//				bit = BitmapFactory.decodeFile(path, opts);  
			}

		} catch(OutOfMemoryError e){
			bit.recycle();
			e.printStackTrace();
			System.gc();
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return bit;
	}
	
	/**
	 * 获取对应名称的图�?
	 * @param path 缓存的路�?         
	 * @return
	 */
	public static String getImagePath(String imageName) throws Exception {
		try {
			String path = CACHE_PIC_DIR_PATH + imageName;
			File imageFile = new File(path);
			if (imageFile.exists()) {
				// 当前图片存在
				return path;
			}else
				return "";
		}catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

/*	*//**
	 * 图片扩大、缩�?
	 * 
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 *//*
	public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
		Bitmap BitmapOrg = bitmap;
		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		int newWidth = w;
		int newHeight = h;
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
				height, matrix, true);
		return resizedBitmap;
	}*/

	/**
	 * 根据地址获取图片的名�?
	 * @param imageUrl
	 * @return
	 */
	public static String getImageName(String imageUrl) {
		String fileName = "";

		if (imageUrl != null && imageUrl.contains("/") && imageUrl.length() > 1) {
			String temp = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
			int isDot;
			isDot = temp.lastIndexOf(".");
			if(isDot != -1){
				fileName = temp.substring(0, isDot);
			}else{
				fileName = temp;
			}
		}

		// LogUnit.i("test", "image name: " + fileName);

		return fileName;
	}

	/**
	 * 删除缓存的图�?
	 */
	public static void clearCacheImage() {
		
		if (Utils.isSDCARDMounted()) {
			try {
				String path = CACHE_PIC_DIR_PATH;
				File imageDir = new File(path);
				if (imageDir != null && imageDir.isDirectory()) {
					// 当前图片存在,删除目录下所有jpg文件的缓??
					String[] children = imageDir.list();

					for (int i = 0; i < children.length; i++) {
						String name = children[i];
						// if (name.endsWith("jpg") || name.endsWith("png") ||
						// name.endsWith("gif")) {
						new File(imageDir, name).delete();
						// }
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 从网络上取数�?
	public static Drawable loadImageFromUrl(String imageUrl) {
		try {
			return Drawable.createFromStream(new URL(imageUrl).openStream(),
					"image.png");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static float sDensity = 1.0f;
	public static int sWidthPixels;
	public static int sHeightPixels;
	public static float sFontDensity;
	
	/**
	 * 图片扩大、缩�?
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap resizeImage(Bitmap bitmap, int w, int h) throws OutOfMemoryError{
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        
        Matrix matrix = new Matrix();
        Log.e("scaleHeight",scaleHeight+"");
        Log.e("scaleHeight",scaleHeight+"");
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                        height, matrix, true);
        return resizedBitmap;
	}
	
	/**
	 * Drawable 转bitmap
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
	        
	        Bitmap bitmap = Bitmap
	                        .createBitmap(
	                                        drawable.getIntrinsicWidth(),
	                                        drawable.getIntrinsicHeight(),
	                                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
	                                                        : Bitmap.Config.RGB_565);
	        Canvas canvas = new Canvas(bitmap);
	        //canvas.setBitmap(bitmap);
	        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
	        drawable.draw(canvas);
	        return bitmap;
	}
		
	/**
	 * 将bitmap 转为byte []
	 * @param icon
	 * @return
	 */
    public static  byte[] bitmap2Byte(Bitmap icon) 
    {
        if (icon == null) {  
            return null;  
        }
        
        final ByteArrayOutputStream os = new ByteArrayOutputStream();  
        // 将Bitmap压缩成PNG编码，质量为100%存储          
        icon.compress(Bitmap.CompressFormat.PNG, 100, os);   
        return os.toByteArray();   
    }  
    /**
     * 将byte[] 转为bitmap
     * @param btye
     * @return
     */
    public static Bitmap btye2Bitmap(byte[] btye) 
    {

		if (btye.length != 0) {
			return BitmapFactoryUtil.decodeByteArray(btye, 0, btye.length);
		} else {
			return null;
		}
	}
    
    /**
	 * dip/dp转像�?
	 * @param dipValue dip??dp大小
	 * @return 像素??
	 */
	public static int dip2px(float dipVlue)
	{
		return (int)(dipVlue * sDensity + 0.5f);
	}
	
	/**
	 * 像素转dip/dp
	 * @param pxValue 像素大小
	 * @return dip??
	 */
	public static int px2dip(float pxValue)
	{
		final float scale = sDensity;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * @param spValue sp大小
	 * @return 像素
	 */
	public static int sp2px(float spValue)
	{
		final float scale = sDensity;
		return (int)(scale * spValue);
	}
	
	/**
	 * px转sp
	 * @param pxValue 像素大小
	 * @return sp
	 */
	public static int px2sp(float pxValue)
	{
		final float scale = sDensity;
		return (int)(pxValue / scale);
	}
	
	 public static void resetDensity(Context context)
		{
			if (context != null)
			{
				DisplayMetrics metrics = context.getResources().getDisplayMetrics();
				sDensity = metrics.density;
				sFontDensity = metrics.scaledDensity;
				sWidthPixels = metrics.widthPixels;
				sHeightPixels = metrics.heightPixels;
//				System.out.println("Screen Density----------------" + sDensity);
			}
		}
}