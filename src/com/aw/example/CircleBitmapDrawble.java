package com.aw.example;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

public class CircleBitmapDrawble extends BitmapDrawable {
	private final Matrix mShaderMatrix = new Matrix();
	private final RectF mSrcRectF = new RectF();
	private final RectF mDstRectF = new RectF();
	private final Paint mPaint = new Paint();
	private boolean mRefreshDrawState = true;

	public CircleBitmapDrawble(Resources res, String filepath) {
		super(res, filepath);
	}

	public CircleBitmapDrawble(Resources resources, Bitmap bm) {
		super(resources, bm);
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		if(!getBounds().equals(bounds))
			mRefreshDrawState = true;

		super.onBoundsChange(bounds);
	}

	@Override
	public void setAlpha(int alpha) {
		super.setAlpha(alpha);
		mPaint.setAlpha(alpha);
	}

	@Override
	public void draw(Canvas canvas) {
		if(mRefreshDrawState == true) {
			mPaint.setAntiAlias(true);
			mSrcRectF.set(0, 0,  getIntrinsicWidth(), getIntrinsicHeight());
			mDstRectF.set(getBounds());
			mShaderMatrix.setRectToRect(mSrcRectF, mDstRectF, Matrix.ScaleToFit.FILL);

			Bitmap bitmap = getBitmap();
			if( bitmap != null){
				final BitmapShader shader = new BitmapShader(getBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
				shader.setLocalMatrix(mShaderMatrix);
				mPaint.setShader(shader);				
			}

			mRefreshDrawState = false;
		}

		canvas.drawCircle(mDstRectF.centerX(), mDstRectF.centerY(), Math.min(mDstRectF.width()/2, mDstRectF.height()/2), mPaint);
	}
}