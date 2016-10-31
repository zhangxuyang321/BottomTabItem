package com.xyz.tabitem;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by paike on 2016/10/11.
 * xyz@163.com
 */

public class BottmTabItem extends View {

    private boolean selectState;      //是否选中状态
    private Bitmap icon;              //默认图片
    private Bitmap selectIcon;        //选中图片
    private String title = "";        //底部标题
    private int iconWidth;            //图片宽
    private int iconHeight;           //图片高
    private int titleColor;           //默认文字颜色
    private int titleTop;           //默认文字颜色
    private int titleSelectColor;     //选中文字颜色
    private Paint mPaint;
    private Rect mBound;              //绘制时控制文本绘制的范围
    private int width;
    private int height;
    private float titleSize;


    public BottmTabItem(Context context) {
        this(context, null);
    }

    public BottmTabItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottmTabItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottmTabItem);
        selectState = ta.getBoolean(R.styleable.BottmTabItem_xyzSelectState, false);
        icon = BitmapFactory.decodeResource(getResources(), ta.getResourceId(R.styleable.BottmTabItem_xyzIcon, 0));
        selectIcon = BitmapFactory.decodeResource(getResources(), ta.getResourceId(R.styleable.BottmTabItem_xyzSelectIcon, 0));
        iconWidth = (int) ta.getDimension(R.styleable.BottmTabItem_xyzIconWidth, 0);
        iconHeight = (int) ta.getDimension(R.styleable.BottmTabItem_xyzIconHeight, 0);
        title = ta.getString(R.styleable.BottmTabItem_xyzTitle);
        titleTop = (int) ta.getDimension(R.styleable.BottmTabItem_xyzTitleTop, 0);
        titleColor = ta.getColor(R.styleable.BottmTabItem_xyzTitleColor, Color.GRAY);
        titleSelectColor = ta.getColor(R.styleable.BottmTabItem_xyzTitleSelectColor, Color.BLACK);
        titleSize = ta.getDimension(R.styleable.BottmTabItem_xyzTitleSize, dp2px(15));
        ta.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(titleSize);
        mBound = new Rect();
        mPaint.getTextBounds(title, 0, title.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int iconWidth = getPaddingLeft() + getPaddingRight() + icon.getWidth();
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mBound.width();
            width = Math.max(iconWidth, desireByTitle);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int desire = getPaddingTop() + getPaddingBottom() + icon.getHeight() + mBound.height();
            height = Math.min(desire, heightSize);

        }
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == icon || null == selectIcon) {
            throw new RuntimeException("必须设置图片");
        }
        int left = width / 2 - iconWidth / 2;
        int top = (int) (height - iconHeight - titleTop - titleSize) / 2;
        int right = width / 2 + iconWidth / 2;
        int bottom = (int) (height + iconHeight - titleTop - titleSize) / 2;
        canvas.save();
        if (selectState) {
            canvas.drawBitmap(selectIcon, null, new Rect(left, top, right, bottom), mPaint);
            mPaint.setColor(titleSelectColor);
        } else {
            canvas.drawBitmap(icon, null, new Rect(left, top, right, bottom), mPaint);
            mPaint.setColor(titleColor);
        }
        canvas.restore();
        drawTitle(canvas, top);
    }

    private void drawTitle(Canvas canvas, int top) {
        canvas.drawText(title, width / 2 - mBound.width() * 1.0f / 2, top + iconHeight + titleTop + titleSize-getPaddingBottom(), mPaint);
    }

    private int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + .5f);
    }

    /**
     * 设置选中状态
     *
     * @param flag 是否选中
     */
    public void setSelectState(boolean flag) {
        this.selectState = flag;
        postInvalidate();
    }
}
