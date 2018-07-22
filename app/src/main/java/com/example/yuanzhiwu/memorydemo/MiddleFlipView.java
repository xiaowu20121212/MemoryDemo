package com.example.yuanzhiwu.memorydemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MiddleFlipView extends View {

    float mDownX, mDownY;
    Camera mCamera;
    Matrix mMatrix;
    int mWidth, mHeight;
    float mCameraRotateY = 0f;  //
    private Rect mDstRect;
    ValueAnimator mValueAnimator;
    Bitmap[] mBitmaps = new Bitmap[5];
    private int mCurrentIndex = 0;
    private int mNextIndex;
    private boolean mToRight;
    private boolean mChangeCurrent;

    public MiddleFlipView(Context context) {
        super(context);
    }

    public MiddleFlipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mMatrix = new Matrix();
        mCamera = new Camera();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mBitmaps[0] = BitmapFactory.decodeResource(getResources(), R.mipmap.timg_1);
        mBitmaps[1] = BitmapFactory.decodeResource(getResources(), R.mipmap.timg_2);
        mBitmaps[2] = BitmapFactory.decodeResource(getResources(), R.mipmap.timg_3);
        mBitmaps[3] = BitmapFactory.decodeResource(getResources(), R.mipmap.timg_4);
        mBitmaps[4] = BitmapFactory.decodeResource(getResources(), R.mipmap.timg_5);

    }

    public MiddleFlipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mDstRect = new Rect(0, 0, mWidth, mHeight);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mDownX = x;
            mDownY = y;
            if (mValueAnimator != null && mValueAnimator.isRunning()) {
                mValueAnimator.cancel();
                if (mChangeCurrent) {
                    mCurrentIndex = mNextIndex;
                }
            }
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float dx = x - mDownX;
            mToRight = dx < 0;
            if (mToRight) {
                mNextIndex = mCurrentIndex + 1;
                if (mNextIndex == mBitmaps.length) {
                    mNextIndex = 0;
                }
            } else {
                mNextIndex = mCurrentIndex - 1;
                if (mNextIndex == -1) {
                    mNextIndex = mBitmaps.length - 1;
                }
            }
            float radio = dx / mWidth;
            mChangeCurrent = true;
            if (radio >= -0.5f && radio <= 0.5f) {
                mCameraRotateY = radio * 180;
                mChangeCurrent = false;
            } else if (radio < -0.5f) {
                mCameraRotateY = (radio + 1) * 180;
            } else {
                mCameraRotateY = (radio - 1) * 180;
            }
            Log.d("xiaowu_percent", "mCameraRotateY:" + mCameraRotateY);
            invalidate();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            mValueAnimator = ValueAnimator.ofFloat(mCameraRotateY, 0);
            mValueAnimator.setDuration(300);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCameraRotateY = (float) animation.getAnimatedValue();
                    invalidate();
                    if (mCameraRotateY == 0 && mChangeCurrent) {
                        mCurrentIndex = mNextIndex;
                    }
                }
            });
            mValueAnimator.start();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCameraRotateY == 0) {
            canvas.drawBitmap(mBitmaps[mCurrentIndex], null, mDstRect, null);
        } else {
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.clipRect(0, 0, mWidth / 2, mHeight);
            if (mToRight) {
                canvas.drawBitmap(mBitmaps[mCurrentIndex], null, mDstRect, null);
            } else {
                canvas.drawBitmap(mBitmaps[mNextIndex], null, mDstRect, null);
            }
            canvas.restore();
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.clipRect(mWidth / 2, 0, mWidth, mHeight);
            if (mToRight) {
                canvas.drawBitmap(mBitmaps[mNextIndex], null, mDstRect, null);
            } else {
                canvas.drawBitmap(mBitmaps[mCurrentIndex], null, mDstRect, null);
            }
            canvas.restore();
            int count = canvas.save(Canvas.ALL_SAVE_FLAG);
            mCamera.save();
            mMatrix.reset();
            mCamera.setLocation(0, 0, -200);
            mCamera.rotateY(mCameraRotateY);
            mCamera.getMatrix(mMatrix);
            mMatrix.preTranslate(-getWidth() / 2, -getHeight());
            mMatrix.postTranslate(getWidth() / 2, getHeight());
            canvas.concat(mMatrix);
            if (mCameraRotateY > 0) {
                canvas.clipRect(0, 0, mWidth / 2, mHeight);
                if (mToRight) {
                    canvas.drawBitmap(mBitmaps[mNextIndex], null, mDstRect, null);
                } else {
                    canvas.drawBitmap(mBitmaps[mCurrentIndex], null, mDstRect, null);
                }
            } else if (mCameraRotateY < 0) {
                canvas.clipRect(mWidth / 2, 0, mWidth, mHeight);
                if (mToRight) {
                    canvas.drawBitmap(mBitmaps[mCurrentIndex], null, mDstRect, null);
                } else {
                    canvas.drawBitmap(mBitmaps[mNextIndex], null, mDstRect, null);
                }
            }
            canvas.restoreToCount(count);
            mCamera.restore();
        }

    }
}
