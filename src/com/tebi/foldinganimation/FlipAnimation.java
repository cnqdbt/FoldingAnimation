package com.tebi.foldinganimation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class FlipAnimation extends Animation{
  private final float mFromDegrees;
  private final float mToDegrees;
  private final float mCenterX;
  private final float mCenterY;
  private boolean mYAxis = true;
  
  private Camera mCamera;
  
  public FlipAnimation(float from_degrees, float to_degrees, float x_center, float y_center, boolean yaxis) {
    mFromDegrees = from_degrees;
    mToDegrees = to_degrees;
    mCenterX = x_center;
    mCenterY = y_center;
    mYAxis = yaxis;
  }
  
  public FlipAnimation(float from_degrees, float to_degrees, float x_center, float y_center) {
    this(from_degrees, to_degrees, x_center, y_center, true); 
  }
	
  @Override
  public void initialize(int width, int height, int parentWidth, int parentHeight) {
    super.initialize(width, height, parentWidth, parentHeight);
    mCamera = new Camera();
  }
  
  protected void applyTransformation(float interpolatedTime, Transformation t) {
    float degrees = mFromDegrees + ((mToDegrees - mFromDegrees) * interpolatedTime);
    final Camera camera = mCamera;
    final Matrix matrix = t.getMatrix();
    camera.save();
    
    if (mYAxis)
      camera.rotateY(degrees);
    else
      camera.rotateX(degrees);
    
    camera.getMatrix(matrix);
    camera.restore();
    matrix.preTranslate(-mCenterX, -mCenterY);
    matrix.postTranslate(mCenterX, mCenterY);
  }
}
