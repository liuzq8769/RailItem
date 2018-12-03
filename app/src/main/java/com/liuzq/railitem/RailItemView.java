package com.liuzq.railitem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RailItemView extends LinearLayout {

    //左边 包裹文字的图
    private Drawable ml_left_drawable, ml_top_drawable, ml_right_drawable, ml_bottom_drawable;
    //右边 包裹文字的图
    private Drawable mr_left_drawable, mr_top_drawable, mr_right_drawable, mr_bottom_drawable;
    //左边 文字
    private CharSequence mLeftText;
    //右边 文字
    private CharSequence mRightText;
    //文字默认大小
    private final int DEFAULT_SIZE = 16;
    //左边 文字大小
    private float mLeftSize;
    //右边 文字大小
    private float mRightSize;
    //文字默认颜色
    private final int DEFAULT_COLOR = Color.BLACK;
    //左边 文字颜色
    private int mLeftColor;
    //右边 文字颜色
    private int mRightColor;
    //图文间隔 默认
    private int DEFAULT_PADD = 0;
    //图文间隔
    private int mDrawablePadding;
    //内部间隔 默认
    private int DEFAULT_INNER_PADD = 0;
    //内部间隔
    private int mInnerPadding;

    private TextView mLeftView;
    private TextView mRightView;
    private Context mContext;

    private View lineView;
    private boolean mIsLine;

    //底部横线 默认
    private int DEFAULT_LINE_MARGIN = 0;
    private int mLineMargin;

    //底部横线宽度 默认
    private int DEFAULT_LINE_WIDTH = 0;
    private int mLineWidth;

    //文字默认颜色
    private final int DEFAULT_LINE_COLOR = Color.TRANSPARENT;
    private int mLineColor;

    public RailItemView(Context context) {
        this(context, null);
    }

    public RailItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RailItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context, attrs);
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        @SuppressLint("Recycle") TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RailItemView);
        ml_left_drawable = array.getDrawable(R.styleable.RailItemView_l_left_drawable);
        ml_top_drawable = array.getDrawable(R.styleable.RailItemView_l_top_drawable);
        ml_right_drawable = array.getDrawable(R.styleable.RailItemView_l_right_drawable);
        ml_bottom_drawable = array.getDrawable(R.styleable.RailItemView_l_bottom_drawable);

        mr_left_drawable = array.getDrawable(R.styleable.RailItemView_r_left_drawable);
        mr_top_drawable = array.getDrawable(R.styleable.RailItemView_r_top_drawable);
        mr_right_drawable = array.getDrawable(R.styleable.RailItemView_r_right_drawable);
        mr_bottom_drawable = array.getDrawable(R.styleable.RailItemView_r_bottom_drawable);
        mLeftText = array.getString(R.styleable.RailItemView_left_label);
        mRightText = array.getString(R.styleable.RailItemView_right_label);
        mLeftSize = array.getDimension(R.styleable.RailItemView_left_size, DEFAULT_SIZE);
        mRightSize = array.getDimension(R.styleable.RailItemView_right_size, DEFAULT_SIZE);
        mLeftColor = array.getColor(R.styleable.RailItemView_left_color, DEFAULT_COLOR);
        mRightColor = array.getColor(R.styleable.RailItemView_right_color, DEFAULT_COLOR);
        mDrawablePadding = array.getDimensionPixelOffset(R.styleable.RailItemView_drawablePadding, DEFAULT_PADD);
        mInnerPadding = array.getDimensionPixelOffset(R.styleable.RailItemView_inner_padding, DEFAULT_INNER_PADD);

        //是否划线
        mIsLine = array.getBoolean(R.styleable.RailItemView_is_line, false);
        mLineMargin = array.getDimensionPixelOffset(R.styleable.RailItemView_line_margin, DEFAULT_LINE_MARGIN);
        mLineWidth = array.getDimensionPixelOffset(R.styleable.RailItemView_line_width, DEFAULT_LINE_WIDTH);
        mLineColor = array.getColor(R.styleable.RailItemView_line_color, DEFAULT_LINE_COLOR);

        array.recycle();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initView() {
        mContext = getContext();
//        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(VERTICAL);

        mLeftView = new TextView(mContext);
        mRightView = new TextView(mContext);
        mLeftView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftSize);
        mLeftView.setTextColor(mLeftColor);
        mLeftView.setText(mLeftText);
        mLeftView.setCompoundDrawablesWithIntrinsicBounds(ml_left_drawable, ml_top_drawable, ml_right_drawable, ml_bottom_drawable);
        mLeftView.setCompoundDrawablePadding(mDrawablePadding);
        mLeftView.setGravity(Gravity.CENTER_VERTICAL);

        mRightView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightSize);
        mRightView.setTextColor(mRightColor);
        mRightView.setText(mRightText);
        mRightView.setCompoundDrawablesWithIntrinsicBounds(mr_left_drawable, mr_top_drawable, mr_right_drawable, mr_bottom_drawable);
        mRightView.setCompoundDrawablePadding(mDrawablePadding);
        mRightView.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout layout = new LinearLayout(mContext);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.setOrientation(HORIZONTAL);
        layout.setPadding(mInnerPadding, mInnerPadding, mInnerPadding, mInnerPadding);
        layout.setLayoutParams(layoutParams);
        LayoutParams leftParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
        leftParams.weight = 1;
        LayoutParams rightParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layout.addView(mLeftView, leftParams);
        layout.addView(mRightView, rightParams);
        addView(layout);

        //底部横线
        lineView = new View(mContext);
        setLineParams(mLineWidth, mLineMargin);
        lineView.setBackgroundColor(mLineColor);
        addView(lineView);
        if (mIsLine) {
            lineView.setVisibility(VISIBLE);
        } else {
            lineView.setVisibility(GONE);
        }
    }

    public RailItemView setLeftSize(float size) {
        mLeftSize = size;
        mLeftView.setTextSize(size);
        return this;
    }

    public RailItemView setLeftColor(@ColorInt int color) {
        mLeftColor = color;
        mLeftView.setTextColor(color);
        return this;
    }

    public RailItemView setLeftText(CharSequence text) {
        mRightText = text;
        mLeftView.setText(text);
        return this;
    }

    public RailItemView setLeftCompoundDrawables(Drawable ml_left_drawable, Drawable ml_top_drawable, Drawable ml_right_drawable, Drawable ml_bottom_drawable) {
        this.ml_left_drawable = ml_left_drawable;
        this.ml_top_drawable = ml_top_drawable;
        this.ml_right_drawable = ml_right_drawable;
        this.ml_bottom_drawable = ml_bottom_drawable;
        mLeftView.setCompoundDrawablesWithIntrinsicBounds(ml_left_drawable, ml_top_drawable, ml_right_drawable, ml_bottom_drawable);
        return this;
    }

    public RailItemView setRightSize(float size) {
        mRightSize = size;
        mRightView.setTextSize(size);
        return this;
    }

    public RailItemView setRightColor(@ColorInt int color) {
        mRightColor = color;
        mRightView.setTextColor(color);
        return this;
    }

    public RailItemView setRightText(CharSequence text) {
        mRightText = text;
        mRightView.setText(text);
        return this;
    }

    public RailItemView setRightCompoundDrawables(Drawable mr_left_drawable, Drawable mr_top_drawable, Drawable mr_right_drawable, Drawable mr_bottom_drawable) {
        this.mr_left_drawable = mr_left_drawable;
        this.mr_top_drawable = mr_top_drawable;
        this.mr_right_drawable = mr_right_drawable;
        this.mr_bottom_drawable = mr_bottom_drawable;
        mLeftView.setCompoundDrawablesWithIntrinsicBounds(mr_left_drawable, mr_top_drawable, mr_right_drawable, mr_bottom_drawable);
        return this;
    }

    //是否隐藏横线
    public RailItemView isLineView(boolean isLine) {
        if (lineView != null) {
            if (isLine) {
                lineView.setVisibility(VISIBLE);
            } else {
                lineView.setVisibility(GONE);
            }
        }
        return this;
    }

    /**
     * 横线颜色
     *
     * @param color
     * @return
     */
    public RailItemView setLineColor(int color) {
        if (lineView != null) {
            mLineColor = color;
            lineView.setBackgroundColor(color);
        }
        return this;
    }



    public RailItemView setLineParams(int lineWidth, int lineMargin) {
        mLineWidth = lineWidth;
        mLineMargin = lineMargin;

        LayoutParams lineParams = new LayoutParams(LayoutParams.MATCH_PARENT, mLineWidth);
        lineParams.setMargins(mLineMargin, 0, mLineMargin, 0);
        if (lineView != null) {
            lineView.setLayoutParams(lineParams);
        }
        return this;
    }
}
