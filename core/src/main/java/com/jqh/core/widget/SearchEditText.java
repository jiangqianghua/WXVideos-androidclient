package com.jqh.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.jqh.core.R;

public class SearchEditText extends AppCompatEditText {

    private float searchSize = 0 ;
    private float textSize = 0 ;
    private int textColor = 0x000000;
    private Drawable mDrawable;
    private Paint mPaint;

    public SearchEditText(Context context) {
        this(context,null,0);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initResource(context,attrs);
        initPaint();
    }

    private void initResource(Context context, AttributeSet attrs){
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.searchedittext);
        float density = context.getResources().getDisplayMetrics().density;
        searchSize = typeArray.getDimension(R.styleable.searchedittext_imageWidth,18*density+0.5f);
        textColor = typeArray.getColor(R.styleable.searchedittext_textColor,0xff848484);
        textSize = typeArray.getDimension(R.styleable.searchedittext_textSize,14*density+0.5f);
        typeArray.recycle();
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mPaint == null)
            return ;
        drawSearchIcon(canvas);
    }

    private void drawSearchIcon(Canvas canvas){
        if(this.getText().toString().length() == 0){
            float textWidth = mPaint.measureText("搜索");
            float textHeight = getFontLeading(mPaint);

            float dx = (getWidth() - searchSize - textWidth - 8) / 2;
            float dy =(getHeight() - searchSize) / 2;
            canvas.save();
            canvas.translate(getScrollX() + dx , getScrollY() + dy);
            if(mDrawable != null){
                mDrawable.draw(canvas);
            }
            canvas.drawText("搜索",getScrollX() + searchSize + 8 ,
                    getScrollY() + (getHeight() - (getHeight() - textHeight)/2) - mPaint.getFontMetrics().bottom - dy,
                    mPaint);
            canvas.restore();

        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(mDrawable == null) {
            mDrawable = getContext().getResources().getDrawable(R.drawable.searchicon);
            mDrawable.setBounds(0, 0, (int) searchSize, (int) searchSize);
        }
    }

    private float getFontLeading(Paint paint){
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.bottom - fm.top ;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mDrawable != null){
            mDrawable.setCallback(this);
            mDrawable = null ;
        }
    }
}
