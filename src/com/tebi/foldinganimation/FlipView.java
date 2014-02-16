package com.tebi.foldinganimation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class FlipView extends LinearLayout {
	public static final int INTERPOLATOR_LINEAR = 1;
	public static final int INTERPOLATOR_NONLINEAR = 2;
	public static final int DIRECTION_HORIZONTAL = 1;
	public static final int DIRECTION_VERTICAL = 2;
	public static final int PIVOT_CENTER = 1;
	public static final int PIVOT_LEFT = 2;
	public static final int PIVOT_RIGHT = 3;
	public static final int PIVOT_TOP = 2;
	public static final int PIVOT_BOTTOM = 3;

	private boolean mIsAnimating = false;

	private int mInterpolatorType = INTERPOLATOR_LINEAR;
	private int mDirection = DIRECTION_HORIZONTAL;
	private int mPivot = PIVOT_CENTER;

	private long mAnimationDuration;

	private RelativeLayout mContentContainer;
	
	private float mStartAngle, mEndAngle;

	public FlipView(Context context, RelativeLayout contentContainer) {
		super(context);
		setFrontFace(contentContainer);
	}
	
	public FlipView(Context context, AttributeSet attrs) {
	      super(context, attrs);
	   }

	public void setPivot(int pvt) {
		if (mIsAnimating)
			return;

		if (pvt == PIVOT_CENTER || pvt == PIVOT_LEFT || pvt == PIVOT_RIGHT
				|| pvt == PIVOT_TOP || pvt == PIVOT_BOTTOM) {
			mPivot = pvt;
		} else
			mPivot = PIVOT_CENTER;
	}

	public void setFrontFace(RelativeLayout frontFace) {
		if (mIsAnimating)
			return;

		if (mContentContainer != null)
			this.removeView(mContentContainer);

		mContentContainer = frontFace;

		if (mContentContainer != null) {
			this.addView(mContentContainer);

		}
	}

	public void setInterpolator(int interpolator) {
		if (interpolator == INTERPOLATOR_NONLINEAR)
			mInterpolatorType = INTERPOLATOR_NONLINEAR;
		else
			mInterpolatorType = INTERPOLATOR_LINEAR;
	}

	public void setAnimationDuration(long milliseconds) {
		mAnimationDuration = milliseconds;
	}

	public void setDirection(int dir) {
		if (mIsAnimating)
			return;

		if (dir == DIRECTION_VERTICAL)
			mDirection = DIRECTION_VERTICAL;
		else
			mDirection = DIRECTION_HORIZONTAL;
	}
	
	public void setStartEndAngle(float start, float end) {
		mStartAngle = start;
		mEndAngle = end;
	}

	public void flip() {
		if (mIsAnimating || mContentContainer == null) {
			return;
		}
		applyRotation(mStartAngle, mEndAngle);
	}

	private void applyRotation(float start, float end) {
		mIsAnimating = true;

//		final float centerX = (m_Pivot == PIVOT_LEFT) ? 0
//				: ((m_Pivot == PIVOT_CENTER) ? (m_FrontFace.getWidth() / 2.0f)
//						: m_FrontFace.getWidth());
		final float centerY = (mPivot == PIVOT_LEFT) ? 0
				: ((mPivot == PIVOT_CENTER) ? (mContentContainer.getHeight() / 2.0f)
						: mContentContainer.getHeight());

		final FlipAnimation rotation = new FlipAnimation(start, end, mContentContainer.getWidth() / 2.0f,
				centerY, ((mDirection == DIRECTION_VERTICAL) ? false : true));
		rotation.setDuration(mAnimationDuration);
		rotation.setFillAfter(true);

		if (mInterpolatorType == INTERPOLATOR_LINEAR)
			rotation.setInterpolator(new LinearInterpolator());
		else
			rotation.setInterpolator(new AccelerateInterpolator());

		mContentContainer.startAnimation(rotation);

	}

}
