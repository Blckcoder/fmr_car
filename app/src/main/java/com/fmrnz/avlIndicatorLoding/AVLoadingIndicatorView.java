package com.fmrnz.avlIndicatorLoding;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.fmrnz.R;


public class AVLoadingIndicatorView extends View {


    //indicators
    public static final int BallPulse = 0;
    //    public static final int BallGridPulse=1;
//    public static final int BallClipRotate=2;
//    public static final int BallClipRotatePulse=3;
//    public static final int SquareSpin=4;
//    public static final int BallClipRotateMultiple=5;
//    public static final int BallPulseRise=6;
//    public static final int BallRotate=7;
//    public static final int CubeTransition=8;
//    public static final int BallZigZag=9;
//    public static final int BallZigZagDeflect=10;
//    public static final int BallTrianglePath=11;
//    public static final int BallScale=12;
    public static final int LineScale = 13;
    //    public static final int LineScaleParty=14;
    public static final int BallScaleMultiple = 15;
    public static final int BallPulseSync = 16;
    //    public static final int BallBeat=17;
//    public static final int LineScalePulseOut=18;
//    public static final int LineScalePulseOutRapid=19;
//    public static final int BallScaleRipple=20;
//    public static final int BallScaleRippleMultiple=21;
//    public static final int BallSpinFadeLoader=22;
//    public static final int LineSpinFadeLoader=23;
//    public static final int TriangleSkewSpin=24;
    public static final int Pacman = 25;
//    public static final int BallGridBeat=26;
//    public static final int SemiCircleSpin=27;


    @IntDef(flag = true,
            value = {
                    BallPulse,
                    LineScale,
                    BallScaleMultiple,
                    BallPulseSync,
                    Pacman,
            })
    public @interface Indicator {
    }

    //Sizes (with defaults in DP)
    public static final int DEFAULT_SIZE = 45;

    //attrs
    int mIndicatorId;
    int mIndicatorColor;

    Paint mPaint;
    int type = 15;
    int color = 0;
    BaseIndicatorController mIndicatorController;

    private boolean mHasAnimation;

    public void setType(int type, int color) {
        this.type = type;
        this.color = color;
        init(null, type);

    }

    public AVLoadingIndicatorView(Context context) {
        super(context);
    }

    public AVLoadingIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr);
    }

    public void init(AttributeSet attrs, int defStyle) {
        Log.d("defStyle", String.valueOf(defStyle));
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AVLoadingIndicatorView);
        mIndicatorId = a.getInt(R.styleable.AVLoadingIndicatorView_indicator, type);

        mIndicatorColor = a.getColor(R.styleable.AVLoadingIndicatorView_indicator_color, color);
        a.recycle();
        mPaint = new Paint();
        mPaint.setColor(mIndicatorColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        applyIndicator();
    }

    private void applyIndicator() {
        Log.d("**mIndicatorId", String.valueOf(mIndicatorId));

        switch (mIndicatorId) {

            case BallScaleMultiple:
                mIndicatorController = new BallScaleMultipleIndicator();
                break;
            case BallPulseSync:
                mIndicatorController = new BallPulseSyncIndicator();
                break;
            case BallPulse:
                mIndicatorController = new BallPulseIndicator();
                break;
            case Pacman:
                mIndicatorController = new PacmanIndicator();
                break;
            case LineScale:
                mIndicatorController = new LineScaleIndicator();
                break;
            default:
                mIndicatorController = new BallPulseIndicator();
                break;
        }
        Log.d("**mIndicatorController", mIndicatorController.toString());

        mIndicatorController.setTarget(AVLoadingIndicatorView.this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureDimension(dp2px(DEFAULT_SIZE), widthMeasureSpec);
        int height = measureDimension(dp2px(DEFAULT_SIZE), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureDimension(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawIndicator(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!mHasAnimation) {
            mHasAnimation = true;
            applyAnimation();
        }
    }

    void drawIndicator(Canvas canvas) {
        mIndicatorController.draw(canvas, mPaint);
    }

    void applyAnimation() {
        mIndicatorController.createAnimation();
    }

    private int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }


}
