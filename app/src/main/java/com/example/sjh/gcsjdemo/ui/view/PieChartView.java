package com.example.sjh.gcsjdemo.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
 
import java.util.List;
 
/**
 * 自定义饼状图 第一：可能需要绘制多个颜色的图
 * <p>
 * Created by 54966 on 2018/2/27.
 */
 
public class PieChartView extends View {
 
	private Paint			mChartPaint;
 
	private Paint			mCirclePaint;					// 中心圆
 
	private RectF			mRectF;
 
	private int				padding;
 
	private List<PieModel>	mPieModelList;
 
	private float			mAnimaAngle;
 
	private RectF			mSelectedRectF	= new RectF();
 
	public PieChartView(Context context) {
        this(context, null);
	}
 
	public PieChartView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}
 
	public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
 
	private void init() {
		mChartPaint = new Paint();
		mChartPaint.setAntiAlias(true);
		mChartPaint.setDither(true);
		mChartPaint.setStrokeWidth(100);
		mChartPaint.setStyle(Paint.Style.FILL);
 
		mCirclePaint = new Paint();
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setStyle(Paint.Style.FILL);
		mCirclePaint.setColor(Color.WHITE);
	}
 
	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
 
	@Override protected void onDraw(Canvas canvas) {
		if (mPieModelList == null || mPieModelList.isEmpty()) {
			return;
		}
		for (int i = 0; i < mPieModelList.size(); i++) {
			if (mPieModelList.get(i).percent > 0) {
				if (mAnimaAngle >= mPieModelList.get(i).startAngle &&
						mAnimaAngle <= (mPieModelList.get(i).startAngle + mPieModelList.get(i).sweepAngle)) {
 
					drawColor(canvas, mPieModelList.get(i).color, mPieModelList.get(i).startAngle, mAnimaAngle - mPieModelList.get(i).startAngle);
 
				} else if (mAnimaAngle >= (mPieModelList.get(i).startAngle + mPieModelList.get(i).sweepAngle)) {
					drawColor(canvas, mPieModelList.get(i).color, mPieModelList.get(i).startAngle, mPieModelList.get(i).sweepAngle);
				}
				if (mPieModelList.get(i).selected) {
					drawSelectedView(canvas, mPieModelList.get(i).color, mPieModelList.get(i).startAngle, mPieModelList.get(i).sweepAngle);
				}
			}
		}
		canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredWidth() / 2, padding, mCirclePaint);
	}
 
	private void drawColor(Canvas canvas, int color, float startAngle, float sweepAngle) {
		mChartPaint.setColor(color);
		mChartPaint.setAlpha(255);
		canvas.drawArc(mRectF, startAngle, sweepAngle, true, mChartPaint);
	}
 
	private void drawSelectedView(Canvas canvas, int color, float startAngle, float sweepAngle) {
		mChartPaint.setColor(color);
		mChartPaint.setAlpha(150);
		canvas.drawArc(mSelectedRectF, startAngle, sweepAngle, true, mChartPaint);
	}
 
	public void startAnima() {
		final ValueAnimator mValueAnimator = ValueAnimator.ofFloat(0f, 360f);
		mValueAnimator.setDuration(3 * 1000);
 
		mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
 
			@Override public void onAnimationUpdate(ValueAnimator animation) {
				mAnimaAngle = (float) animation.getAnimatedValue();
				invalidate();
			}
		});
		mValueAnimator.start();
	}
 
	public void setData(List<PieModel> pieModelList) {
		this.mPieModelList = pieModelList;
		for (int i = 0; i < mPieModelList.size(); i++) {
			PieModel model = mPieModelList.get(i);
			if (i == 0) {
				model.startAngle = 0;
			} else {
				model.startAngle = mPieModelList.get(i - 1).startAngle + mPieModelList.get(i - 1).sweepAngle;
			}
			model.sweepAngle = (model.percent * 360);
		}
	}
 
	@Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		padding = w / 6;
		mRectF = new RectF(padding, padding, w - padding, w - padding);
		mSelectedRectF.set(mRectF);
		mSelectedRectF.inset(-30, -30);
	}
 
}
