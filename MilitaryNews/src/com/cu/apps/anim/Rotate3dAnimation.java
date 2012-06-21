package com.cu.apps.anim;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3dAnimation extends Animation
{
	private Camera mCamera;			//摄像�?
	 private final float mCenterX;		//中心点X
	 private final float mCenterY;		//中心点Y
	 private final float mDepthZ;		//中心点Z
	 private final float mFromDegrees;	//�?��角度	
	 private final boolean mReverse;	//是否�?��扭曲
	 private final float mToDegrees;	//结束角度
	 //初始�?
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
	 
	 //生成Transformation
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
		 if(mReverse) //�?��扭曲
		 {
			 camera.translate(0.0f,0.0f, mDepthZ*interpolatedTime);
		 }
		 else
		 {
			 camera.translate(0.0f,0.0f, mDepthZ*(1.0f-interpolatedTime));
		 }
		 camera.rotateY(degrees);
		 //取得变换后的矩阵
		 camera.getMatrix(matrix);
		 camera.restore();
		 
		 matrix.preTranslate(-centerX,-centerY);
		 matrix.postTranslate(centerX, centerY);
	 }
}
