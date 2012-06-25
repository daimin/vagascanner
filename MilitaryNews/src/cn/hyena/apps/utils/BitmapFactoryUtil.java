package cn.hyena.apps.utils;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;

public class BitmapFactoryUtil {
	public static SoftReference<Bitmap>  softReference  = null;
	//private final static ConcurrentHashMap<String, SoftReference<Bitmap>> mSoftBitmapCache =  new ConcurrentHashMap<String, SoftReference<Bitmap>>();  
	//private static ImageMemoryCache memoryCache = new ImageMemoryCache();;// 内存缓存 
	
	public static Bitmap decodeFile(String path) {
		Bitmap bitmap = null;
		Bitmap temp = null;
		try {
			bitmap = BitmapFactory.decodeFile(path);
			int width = (int) (ImageUtils.dip2px(bitmap.getWidth()) - 0.5f);
			int height = (int) (ImageUtils.dip2px(bitmap.getHeight()) - 0.5f);
			temp = ImageUtils.resizeImage(bitmap, width, height);
			if (temp != null) {
				softReference = new SoftReference<Bitmap>(temp);
			}
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
			//System.gc();
			return temp;
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
			System.gc();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return temp;
		
		
//		Bitmap result = null;
//		Bitmap temp = null;
//		try {
//		    result = memoryCache.getBitmapFromCache(path);
//		    if (result == null) {
//			    // 文件缓存中获取
//				result = BitmapFactory.decodeFile(path);
//			}
//			int width = (int) (ImageUtil.dip2px(result.getWidth()) - 0.5f);
//			int height = (int) (ImageUtil.dip2px(result.getHeight()) - 0.5f);
//			temp = ImageUtil.resizeImage(result, width, height);
//			if (temp != null) {
//				memoryCache.addBitmapToCache(path, temp);
//			}
//			if (result != null && !result.isRecycled()) {
//				result.recycle();
//				result = null;
//			}
//			return temp;
//		} catch (OutOfMemoryError e) {
//			// TODO: handle exception
//			System.gc();
//		}
//		return null;
	}
	
	public static Bitmap decodeStream(InputStream is, Rect outPadding, Options opts){
		Bitmap bitmap = null;
		Bitmap temp = null;
		try {
			bitmap = BitmapFactory.decodeStream(is, outPadding, opts);
			int width = (int) (ImageUtils.dip2px(bitmap.getWidth()) - 0.5f);
			int height = (int) (ImageUtils.dip2px(bitmap.getHeight()) - 0.5f);
			temp = ImageUtils.resizeImage(bitmap, width, height);
			if (temp != null) {
				softReference = new SoftReference<Bitmap>(temp);
			}
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
			//System.gc();
			return temp;
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
			System.gc();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return temp;
	}
	
	public static Bitmap decodeStream(InputStream is){
		Bitmap bitmap = null;
		Bitmap temp = null;
		try {
			bitmap = BitmapFactory.decodeStream(is);
			int width = (int) (ImageUtils.dip2px(bitmap.getWidth()) - 0.5f);
			int height = (int) (ImageUtils.dip2px(bitmap.getHeight()) - 0.5f);
			temp = ImageUtils.resizeImage(bitmap, width, height);
			if (temp != null) {
				softReference = new SoftReference<Bitmap>(temp);
			}
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
			//System.gc();
			return temp;
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
			System.gc();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return temp;
	}
	
	public static Bitmap decodeByteArray(byte[] data, int offset, int length,String url){
		Bitmap bitmap = null;
		Bitmap temp = null;
		try {
			 bitmap =  BitmapFactory.decodeByteArray(data,offset,length);
			int width = (int) (ImageUtils.dip2px(bitmap.getWidth()) - 0.5f);
			int height = (int) (ImageUtils.dip2px(bitmap.getHeight()) - 0.5f);
			temp = ImageUtils.resizeImage(bitmap, width, height);
			if (temp != null) {
				softReference = new SoftReference<Bitmap>(temp);
			}
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
			//System.gc();
			return temp;
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
			System.gc();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return temp;
		
//		Bitmap result = null;
//		Bitmap temp = null;
//		try {
//		    result = memoryCache.getBitmapFromCache(url);
//		    if (result == null) {
//			    // 文件缓存中获取
//				result = BitmapFactory.decodeByteArray(data,offset,length);
//			}
//			int width = (int) (ImageUtil.dip2px(result.getWidth()) - 0.5f);
//			int height = (int) (ImageUtil.dip2px(result.getHeight()) - 0.5f);
//			temp = ImageUtil.resizeImage(result, width, height);
//			if (temp != null) {
//				memoryCache.addBitmapToCache(url, temp);
//			}
//			if (result != null && !result.isRecycled()) {
//				result.recycle();
//				result = null;
//			}
//			return temp;
//		} catch (OutOfMemoryError e) {
//			// TODO: handle exception
//			System.gc();
//		}
//		return null;
	}
	
	public static Bitmap decodeByteArray(byte[] data, int offset,int length, Options opts){
		Bitmap bitmap = null;
		Bitmap temp = null;
		try {
			 bitmap =  BitmapFactory.decodeByteArray(data, offset, length, opts);
			int width = (int) (ImageUtils.dip2px(bitmap.getWidth()) - 0.5f);
			int height = (int) (ImageUtils.dip2px(bitmap.getHeight()) - 0.5f);
			temp = ImageUtils.resizeImage(bitmap, width, height);
			if (temp != null) {
				softReference = new SoftReference<Bitmap>(temp);
			}
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
			//System.gc();
			return temp;
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
			System.gc();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return temp;
	}
	
	public static Bitmap decodeByteArray(byte[] data, int offset, int length){
		Bitmap bitmap = null;
		Bitmap temp = null;
		try {
			 bitmap =  BitmapFactory.decodeByteArray(data,offset,length);
			int width = (int) (ImageUtils.dip2px(bitmap.getWidth()) - 0.5f);
			int height = (int) (ImageUtils.dip2px(bitmap.getHeight()) - 0.5f);
			temp = ImageUtils.resizeImage(bitmap, width, height);
			if (temp != null) {
				softReference = new SoftReference<Bitmap>(temp);
			}
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
			//System.gc();
			return temp;
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
			System.gc();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return temp;
	}
	
	public static Bitmap decodeResource(Resources res, int id){
		Bitmap bitmap = null;
		Bitmap temp = null;
		try {
			 bitmap =   BitmapFactory.decodeResource(res, id);
			int width = (int) (ImageUtils.dip2px(bitmap.getWidth()) - 0.5f);
			int height = (int) (ImageUtils.dip2px(bitmap.getHeight()) - 0.5f);
			temp = ImageUtils.resizeImage(bitmap, width, height);
			if (temp != null) {
				softReference = new SoftReference<Bitmap>(temp);
			}
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
			//System.gc();
			return temp;
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
			System.gc();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return temp;
		
		/*Bitmap result = null;
		Bitmap temp = null;
		try {
		    result = memoryCache.getBitmapFromCache(id+"");
		    if (result == null) {
			    // 文件缓存中获取
				result = BitmapFactory.decodeResource(res, id);
			}
			int width = (int) (ImageUtil.dip2px(result.getWidth()) - 0.5f);
			int height = (int) (ImageUtil.dip2px(result.getHeight()) - 0.5f);
			temp = ImageUtil.resizeImage(result, width, height);
			if (temp != null) {
				memoryCache.addBitmapToCache(id+"", temp);
			}
			if (result != null && !result.isRecycled()) {
				result.recycle();
				result = null;
			}
			return temp;
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
			System.gc();
		}
		return null;*/
	}
	 
//	 public static Bitmap decodeResource(Resources res, int id){
//		 Bitmap bitmap = null;
//		 try{
//			    bitmap =  BitmapFactory.decodeResource(res, id);
//       	}catch(OutOfMemoryError e){
//	            //bitmap.recycle();
//				e.printStackTrace();
//				if(bitmap != null && !bitmap.isRecycled()){
//					//bitmap.recycle();
//					bitmap = null;
//	            }
//				System.gc();
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//			}
//		 return bitmap;
//	 }
//	 
//	 public static Bitmap decodeByteArray(byte[] data, int offset, int length){
//		 Bitmap bitmap = null;
//		 try{
//			    bitmap =  BitmapFactory.decodeByteArray(data,offset,length);
//       	 }catch(OutOfMemoryError e){
//	            //bitmap.recycle();
//				e.printStackTrace();
//				if(bitmap != null && !bitmap.isRecycled()){
//					//bitmap.recycle();
//					bitmap = null;
//	            }
//				System.gc();
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//			}
//		 return bitmap;
//	 }
//	 
//	 public static Bitmap decodeStream(InputStream is, Rect outPadding, Options opts){
//		 Bitmap bitmap = null;
//		    try{
//			   bitmap = BitmapFactory.decodeStream(is, outPadding, opts);
//       	    }catch(OutOfMemoryError e){
//	            //bitmap.recycle();
//				e.printStackTrace();
//				if(bitmap != null && !bitmap.isRecycled()){
//					//bitmap.recycle();
//					bitmap = null;
//	            }
//				System.gc();
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//			}
//		 return bitmap;
//	 }
}
