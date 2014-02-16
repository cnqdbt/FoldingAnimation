package com.tebi.foldinganimation;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class FoldingActivity extends Activity {

	private FlipView mFlipView1, mFlipView2, mFlipView3, mFlipView4;
	private TranslateAnimation mTransAnimation2, mTransAnimation3, mTransAnimation4;
	private long mDuration = 500;
	private float mDeltaY;
	private RelativeLayout mContainer1, mContainer2, mContainer3, mContainer4;
	private Handler mAnimationHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 2:
				mFlipView2.startAnimation(mTransAnimation2);
				break;
			case 3:
				mFlipView3.startAnimation(mTransAnimation3);
				break;
			case 4:
				mFlipView4.startAnimation(mTransAnimation4);
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		LinearLayout llTotal = (LinearLayout) this.findViewById(R.id.ll_total);

		mContainer1 = (RelativeLayout) View.inflate(this, R.layout.container_1,
				null);
		mContainer2 = (RelativeLayout) View.inflate(this, R.layout.container_2,
				null);
		mContainer3 = (RelativeLayout) View.inflate(this, R.layout.container_3,
				null);
		mContainer4 = (RelativeLayout) View.inflate(this, R.layout.container_4,
				null);

		mFlipView1 = new FlipView(this, mContainer1);
		mFlipView1.setStartEndAngle(-90, 0);
		mFlipView1.setAnimationDuration(mDuration);
		mFlipView1.setDirection(FlipView.DIRECTION_VERTICAL);
		mFlipView1.setPivot(FlipView.PIVOT_TOP);
		llTotal.addView(mFlipView1);

		mFlipView2 = new FlipView(this, mContainer2);
		mFlipView2.setStartEndAngle(90, 0);
		mFlipView2.setAnimationDuration(mDuration);
		mFlipView2.setDirection(FlipView.DIRECTION_VERTICAL);
		mFlipView2.setPivot(FlipView.PIVOT_BOTTOM);
		llTotal.addView(mFlipView2);

		mFlipView3 = new FlipView(this, mContainer3);
		mFlipView3.setStartEndAngle(-90, 0);
		mFlipView3.setAnimationDuration(mDuration);
		mFlipView3.setDirection(FlipView.DIRECTION_VERTICAL);
		mFlipView3.setPivot(FlipView.PIVOT_TOP);
		llTotal.addView(mFlipView3);

		mFlipView4 = new FlipView(this, mContainer4);
		mFlipView4.setStartEndAngle(90, 0);
		mFlipView4.setAnimationDuration(mDuration);
		mFlipView4.setDirection(FlipView.DIRECTION_VERTICAL);
		mFlipView4.setPivot(FlipView.PIVOT_BOTTOM);
		llTotal.addView(mFlipView4);

		mContainer1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				doAllAnimation();
			}
		});

	}

	private void doAllAnimation() {

		mDeltaY = mContainer1.getHeight();

		// distance from bottom of FlipView2 to the top of FlipView1
		mTransAnimation2 = new TranslateAnimation(0, 0, 0, mDeltaY * 2);
		mTransAnimation2.setInterpolator(new CircleInterpolator());
		mTransAnimation2.setDuration(mDuration);
		mTransAnimation2.setFillAfter(true);
		mFlipView2.setTranslationY(-mDeltaY * 2);

		// distance from top of FlipView3 to the top of FlipView1
		mTransAnimation3 = new TranslateAnimation(0, 0, 0, mDeltaY * 2);
		mTransAnimation3.setInterpolator(new CircleInterpolator());
		mTransAnimation3.setDuration(mDuration);
		mTransAnimation3.setFillAfter(true);
		mFlipView3.setTranslationY(-mDeltaY * 2);

		// distance from bottom of FlipView4 to the top of FlipView1
		mTransAnimation4 = new TranslateAnimation(0, 0, 0, mDeltaY * 4);
		mTransAnimation4.setInterpolator(new CircleInterpolator());
		mTransAnimation4.setDuration(mDuration);
		mTransAnimation4.setFillAfter(true);
		mFlipView4.setTranslationY(-mDeltaY * 4);

		mFlipView1.flip();
		mFlipView2.flip();
		mFlipView3.flip();
		mFlipView4.flip();
		mAnimationHandler.sendEmptyMessage(2);
		mAnimationHandler.sendEmptyMessage(3);
		mAnimationHandler.sendEmptyMessage(4);
	}
	
	public class CircleInterpolator implements Interpolator {
	    @Override
	    public float getInterpolation(float input) {
	    	// because the translation should not be linear, use this formula to simulate
	    	// it's not accurate
	        return (float)Math.pow(input, 0.76);
	    }
	}
}