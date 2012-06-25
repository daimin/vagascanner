package cn.hyena.apps.utils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
/**
 * 异步获取图片
 * @author 
 *
 */
public class ImageAsync {

	private static ImageAsync imgAsc;
	private String markStr;
	
	public static ImageAsync getImageAscnc()
	{
		if(imgAsc==null)
			imgAsc=new ImageAsync();
		return imgAsc;
	}
	
	public void execute(ImageView img,String markStr)
	{
		new DownThread().execute(img);
		this.markStr = markStr;
	}	
	 SoftReference<Bitmap>  softReference  = null;
	class DownThread extends AsyncTask<ImageView, Integer, Bitmap> {

		private ImageView mImageView;
		
		@Override
		protected Bitmap doInBackground(ImageView... imgView) {
			Bitmap bitmap=null;
			mImageView=imgView[0];
			String url=imgView[0].getTag().toString();
			String imageType = "";
			/*if (imgView[0].getTag(R.id.tag_first) != null){
				imageType = imgView[0].getTag(R.id.tag_first).toString();
			}*/
//			System.out.println("image url------------" + url);
			if(url!=null)
			{
				try {
					bitmap = Common.getResource(url,imageType);
					
					if("newscontent".equals(markStr) && bitmap != null && Common.screenwidth>=480){
					   ByteArrayOutputStream baos = new ByteArrayOutputStream();
					   bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos);
				       InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
				       BitmapFactory.Options opts = new BitmapFactory.Options(); //当前图片不保存到内存
                       opts.inPurgeable = true;
    				   opts.inSampleSize = 1;
    				   bitmap = BitmapFactory.decodeStream(isBm, null, opts);
					}

				} catch (OutOfMemoryError e) {
					System.gc();
				}
				
			}			
			return bitmap;
		}

		protected void onPostExecute(Bitmap bm) 
		{
			if (bm != null) {
				 try {
						int width = (int)(ImageUtils.dip2px(bm.getWidth())  - 0.5f);
						int height = (int)(ImageUtils.dip2px(bm.getHeight()) - 0.5f);
						Bitmap temp;
						if("newscontent".equals(markStr) && Common.screenwidth>=480){
							temp = ImageUtils.resizeImage(bm, width*3/2, height*3/2);
						}else
							temp = ImageUtils.resizeImage(bm, width, height);

		                 this.mImageView.setImageBitmap(temp);
//		                 Animation anim = AnimationUtils.loadAnimation(mImageView.getContext(),R.anim.fade);
//		                 mImageView.startAnimation(anim);
		                 this.mImageView=null;
		                 if(temp != null)
		                 {
		                	 softReference = new SoftReference<Bitmap>(temp);
		                 }
		                 if(bm != null && !bm.isRecycled()){
		                    bm.recycle();
		                    bm = null;
		                 }
		                 System.gc();
		                 
				} catch (OutOfMemoryError e) {
					// TODO: handle exception
					System.gc();
				}
            }
		}		
	}
}
