package com.jesse.ratiolayoutlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 陈雪军 on 2017/4/1.
 */

public class RatioLayout extends ViewGroup {
    private static final String TAG = "RatioLayout";
    private int width;
    private int height;
    private float ratioX;
    private float ratioY;
    private boolean canDrag;

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        ratioX = typedArray.getFloat(R.styleable.RatioLayout_x_ratio, 0);
        ratioX = Math.min(ratioX, 1f);
        ratioX = Math.max(ratioX, 0f);

        ratioY = typedArray.getFloat(R.styleable.RatioLayout_y_ratio, 0);
        ratioY = Math.min(ratioY, 1f);
        ratioY = Math.max(ratioY, 0f);
        canDrag = typedArray.getBoolean(R.styleable.RatioLayout_can_drag, false);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        if (childCount > 1) {
            throw new UnsupportedOperationException("only allowed one direct child!");
        }
        View child = getChildAt(0);
        LayoutParams params = (LayoutParams) child.getLayoutParams();
        int widthPadding = getPaddingLeft() + getPaddingRight();
        int heightPadding = getPaddingBottom() + getPaddingTop();
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        //width
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredWidth = sizeWidth;
        if (modeWidth == MeasureSpec.AT_MOST) {
            measuredWidth = Math.min(sizeWidth, child.getMeasuredWidth() + params.leftMargin + params.rightMargin + widthPadding);
        }
        //height
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measuredHeight = sizeHeight;
        if (modeHeight == MeasureSpec.AT_MOST) {
            measuredHeight = Math.min(sizeHeight, child.getMeasuredHeight() + params.bottomMargin + params.topMargin + heightPadding);
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        if (mListener != null) {
            mListener.onRatioChanged(ratioX, ratioY);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        LayoutParams params = (LayoutParams) child.getLayoutParams();

        int contentWidth = width - getPaddingLeft() - getPaddingRight();
        int contentHeight = height - getPaddingTop() - getPaddingBottom();
        int childWidth = child.getMeasuredWidth();
        int childHeight = child.getMeasuredHeight();

        int x = (int) (contentWidth * ratioX);
        int y = (int) (contentHeight * ratioY);

        //horizontal
        int left = x - childWidth / 2;
        int right = left + childWidth;
        if (left < params.leftMargin + getPaddingLeft()) {
            left = params.leftMargin + getPaddingLeft();
            right = left + childWidth;
        }
        if (right > width - params.rightMargin - getPaddingRight()) {
            right = width - params.rightMargin - getPaddingRight();
            left = right - childWidth;
        }
        //vertical
        int top = y - childHeight / 2;
        int bottom = top + childHeight;
        if (top < params.topMargin + getPaddingTop()) {
            top = params.topMargin + getPaddingTop();
            bottom = top + childHeight;
        }
        if (bottom > height - params.bottomMargin - getPaddingBottom()) {
            bottom = height - params.bottomMargin - getPaddingBottom();
            top = bottom - childHeight;
        }

        child.layout(left, top, right, bottom);
    }


    public static class LayoutParams extends MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!canDrag) {
            return false;
        }
        ratioX = event.getX() / width;
        ratioY = event.getY() / height;
        if (mListener != null) {
            mListener.onRatioChanged(ratioX, ratioY);
        }
        requestLayout();
        return true;
    }

    public interface OnRatioChangedListener {
        public void onRatioChanged(float ratioX, float ratioY);
    }

    private OnRatioChangedListener mListener;

    public void setOnRatioChangedListener(OnRatioChangedListener listener) {
        mListener = listener;
    }
}
