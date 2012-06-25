package cn.hyena.apps.anim;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3dAnimation extends Animation
{
	private Camera mCamera;			//鎽勫儚澶?
	 private final float mCenterX;		//涓績鐐筙
	 private final float mCenterY;		//涓績鐐筜
	 private final float mDepthZ;		//涓績鐐筞
	 private final float mFromDegrees;	//寮?瑙掑害	
	 private final boolean mReverse;	//鏄惁闇?鎵洸
	 private final float mToDegrees;	//缁撴潫瑙掑害
	 //鍒濆鍖?
	 public Rotate3dAnimation(float fromDegrees, float toDegrees, float centerX, float centerY, float depthZ, boolean reverse)
	 {
		   mFromDegrees = fromDegrees;
		   mToDegrees = toDegrees;
		   mCenterX = centerX;
		   mCenterY = centerY;
		   mDepthZ = depthZ;
		   mReverse = reverse;
	 }
	 
	 @Override
	 public void initialize(int width,int height,int parentWidth,int parentHeight)
	 {
		 super.initialize(width, height, parentWidth, parentHeight);
		 mCamera=new Camera();
	 }
	 
	 //鐢熸垚Transformation
	 @Override
	 protected void applyTransformation(float interpolatedTime,Transformation t)
	 {
		 final float fromDegrees=mFromDegrees;
		 final float degrees=fromDegrees+((mToDegrees-fromDegrees)*interpolatedTime);
		 final float centerX=mCenterX;
		 final float centerY=mCenterY;
		 final Camera camera=mCamera;
		 final Matrix matrix=t.getMatrix();
		 camera.save();
		 if(mReverse) //闇?鎵洸
		 {
			 camera.translate(0.0f,0.0f, mDepthZ*interpolatedTime);
		 }
		 else
		 {
			 camera.translate(0.0f,0.0f, mDepthZ*(1.0f-interpolatedTime));
		 }
		 camera.rotateY(degrees);
		 //鍙栧緱鍙樻崲鍚庣殑鐭╅樀
		 camera.getMatrix(matrix);
		 camera.restore();
		 
		 matrix.preTranslate(-centerX,-centerY);
		 matrix.postTranslate(centerX, centerY);
	 }
}
