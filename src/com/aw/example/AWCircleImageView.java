package com.aw.example;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.example.awcircleframedimageview.R;

public class AWCircleImageView extends ImageView {
	
	private Paint mPaint;
	private float mFrameSize;
	private int mFrameColor;
	private int mBorderColor;
	private float mBorderSize;
	
	private RectF mDstRectf;

	public AWCircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AWCircleImageView);
				
		if(a != null) {
			setFrameSize(a.getInteger(R.styleable.AWCircleImageView_frameSize, 5));
			setFrameColor(a.getInteger(R.styleable.AWCircleImageView_frameColor, Color.WHITE));
			setBorderColor(a.getColor(R.styleable.AWCircleImageView_borderColor, Color.BLACK));
			setBorderSize(a.getInteger(R.styleable.AWCircleImageView_borderSize, 1));
			a.recycle();
		}

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mDstRectf = new RectF(0,0, getWidth(), getHeight());
	}

	@Override
	public void setAlpha(float alpha) {
		super.setAlpha(alpha);
		mPaint.setAlpha((int) alpha);
	}
	
	@Override
	public void setImageDrawable(Drawable drawable) {
		if(! (drawable instanceof CircleBitmapDrawble)){
			BitmapDrawable bmdrawable = (BitmapDrawable) drawable;
			setImageBitmap(bmdrawable.getBitmap());
		}else{
			super.setImageDrawable(drawable);
		}
		return;
	}
	
	@Override
	public void setImageResource(int resId) {
		Bitmap bm = BitmapFactory.decodeResource(getContext().getResources(),
				resId);
		setImageBitmap(bm);
	}
	
	@Override
	public void setImageBitmap(Bitmap bm) {
		setImageDrawable(new CircleBitmapDrawble(getResources(), bm));
	}
	
	@Override
	public void setPadding(int left, int top, int right, int bottom) {
		//ignore setPadding
	}
		
	@Override
	protected void onDraw(Canvas canvas) {
		//drawFrameArea
		if(mFrameSize > 0){ 
			drawFrameArea(canvas);
		}
		
		//fill Circle
		if(getDrawable() != null){
			super.onDraw(canvas);
		}
	}

	private void drawFrameArea(Canvas canvas) {
		mPaint.setColor(getFrameColor());
		mPaint.setStyle(Style.FILL);
		
		mDstRectf.set(0,0, getWidth(), getHeight());
		canvas.drawCircle(mDstRectf.centerX(), mDstRectf.centerY(), Math.min(mDstRectf.width()/2, mDstRectf.height()/2), mPaint);
		
		if(getBorderSize() > 0){
			mPaint.setStyle(Style.STROKE);
			mPaint.setStrokeWidth(mBorderSize);
			mPaint.setColor(getBorderColor());
			mDstRectf.inset( mBorderSize / 2, mBorderSize / 2);	

			canvas.drawCircle(mDstRectf.centerX(), mDstRectf.centerY(), Math.min(mDstRectf.width()/2, mDstRectf.height()/2), mPaint);
		}
	}

	public float getFrameWith() {
		return mFrameSize;
	}

	public void setFrameSize(int frameSize) {
		mFrameSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
				, frameSize
				, getResources().getDisplayMetrics());
		
		int padding = (int) mFrameSize;
		super.setPadding(padding,padding,padding,padding);
	}

	public int getFrameColor() {
		return mFrameColor;
	}

	public void setFrameColor(int color) {
		mFrameColor = color;
	}

	public int getBorderColor() {
		return mBorderColor;
	}

	public void setBorderColor(int borderColor) {
		mBorderColor = borderColor;
	}
	
	public float getBorderSize(){
		return mBorderSize;
	}

	private void setBorderSize(int borderSize) {
		mBorderSize = (float) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
						, borderSize
						, getResources().getDisplayMetrics());
	}
}
