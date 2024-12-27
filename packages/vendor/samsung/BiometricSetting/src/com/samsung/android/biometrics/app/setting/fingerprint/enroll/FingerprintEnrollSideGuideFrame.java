package com.samsung.android.biometrics.app.setting.fingerprint.enroll;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;

import androidx.recyclerview.widget.RecyclerView;

import com.samsung.android.biometrics.app.setting.AnimationHelper;
import com.samsung.android.biometrics.app.setting.Utils;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public class FingerprintEnrollSideGuideFrame extends View {
    public static final PathInterpolator INTPL_READY = new PathInterpolator(0.1f, 0.5f, 0.5f, 0.9f);
    public static final PathInterpolator INTPL_SCAN = new PathInterpolator(0.1f, 0.5f, 0.5f, 0.9f);
    public final Point mDisplaySize;
    public final Handler mHandler;
    public final float mInnerBarWidth;
    public float mInnerScanHRatio;
    public float mInnerScanVRatio;
    public boolean mIsWindowAlreadyAdded;
    public final float mOuterBarMargin;
    public final float mOuterBarWidth;
    public float mOuterScanHRatio;
    public float mOuterScanVRatio;
    public final Paint mPntInnerBar;
    public final Paint mPntOuterBar;
    public final PointF mSensorPos;
    public final PointF mSensorSize;
    public int mState;
    public final AnimationHelper mTimerInnerReady;
    public final AnimationHelper mTimerOuterReady;
    public final AnimationHelper mTimerScan;
    public final WindowManager mWindowManager;

    public FingerprintEnrollSideGuideFrame(
            Context context, FingerprintEnrollSensorHelper fingerprintEnrollSensorHelper) {
        super(context);
        this.mDisplaySize = new Point();
        PointF pointF = new PointF();
        this.mSensorPos = pointF;
        PointF pointF2 = new PointF();
        this.mSensorSize = pointF2;
        AnimationHelper animationHelper = new AnimationHelper(true);
        this.mTimerInnerReady = animationHelper;
        AnimationHelper animationHelper2 = new AnimationHelper(true);
        this.mTimerOuterReady = animationHelper2;
        AnimationHelper animationHelper3 = new AnimationHelper(false);
        this.mTimerScan = animationHelper3;
        Paint paint = new Paint(1);
        this.mPntInnerBar = paint;
        this.mInnerBarWidth = RecyclerView.DECELERATION_RATE;
        this.mInnerScanHRatio = RecyclerView.DECELERATION_RATE;
        this.mInnerScanVRatio = RecyclerView.DECELERATION_RATE;
        this.mOuterScanHRatio = RecyclerView.DECELERATION_RATE;
        this.mOuterScanVRatio = RecyclerView.DECELERATION_RATE;
        Paint paint2 = new Paint(1);
        this.mPntOuterBar = paint2;
        this.mOuterBarWidth = RecyclerView.DECELERATION_RATE;
        this.mOuterBarMargin = RecyclerView.DECELERATION_RATE;
        this.mState = 1;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mHandler = new Handler(getContext().getMainLooper());
        this.mDisplaySize = Utils.getDisplaySize(getContext());
        pointF.x = r10.x;
        pointF.y =
                getResources().getConfiguration().semDisplayDeviceType == 5
                        ? fingerprintEnrollSensorHelper.foldTopMargin
                        : fingerprintEnrollSensorHelper.topMargin;
        pointF2.y = fingerprintEnrollSensorHelper.height;
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        this.mInnerBarWidth = TypedValue.applyDimension(5, 1.0f, displayMetrics) * 2.0f;
        this.mOuterBarWidth = TypedValue.applyDimension(5, 1.9f, displayMetrics) * 2.0f;
        this.mOuterBarMargin = TypedValue.applyDimension(5, 1.5f, displayMetrics);
        paint.setColor(-15423386);
        Paint.Style style = Paint.Style.FILL_AND_STROKE;
        paint.setStyle(style);
        paint2.setColor(-2146129818);
        paint2.setStyle(style);
        addReadyTrack(animationHelper);
        addReadyTrack(animationHelper2);
        animationHelper3.addTrack(new AnimationHelper.Item(500L, INTPL_SCAN));
    }

    public static void addReadyTrack(AnimationHelper animationHelper) {
        PathInterpolator pathInterpolator = INTPL_READY;
        animationHelper.addTrack(new AnimationHelper.Item(800L, pathInterpolator));
        animationHelper.addTrack(new AnimationHelper.Item(300L, 1.0f));
        animationHelper.addTrack(new AnimationHelper.Item(550L, pathInterpolator, 0));
        animationHelper.addTrack(new AnimationHelper.Item(300L, RecyclerView.DECELERATION_RATE));
    }

    public static RectF getExpandRectF(RectF rectF, float f, float f2, float f3) {
        float width = ((f - 1.0f) * rectF.width()) / 2.0f;
        float height = (((f2 - 1.0f) * rectF.height()) / 2.0f) + f3;
        return new RectF(
                rectF.left - width, rectF.top - height, rectF.right + width, rectF.bottom + height);
    }

    public final synchronized void addView() {
        try {
            if (this.mIsWindowAlreadyAdded) {
                Log.d("BSS_SideSensorGuideFrame", "addView: SideGuideFrame Already added!");
            } else {
                if (getWindowToken() == null && getParent() == null) {
                    WindowManager windowManager = this.mWindowManager;
                    Point point = this.mDisplaySize;
                    WindowManager.LayoutParams layoutParams =
                            new WindowManager.LayoutParams(point.x, point.y, 2008, 24, -3);
                    layoutParams.setFitInsetsTypes(0);
                    layoutParams.layoutInDisplayCutoutMode = 3;
                    layoutParams.semAddExtensionFlags(8);
                    windowManager.addView(this, layoutParams);
                }
                this.mIsWindowAlreadyAdded = true;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void hide() {
        Log.i("BSS_SideSensorGuideFrame", "hide");
        this.mHandler.postDelayed(
                new Runnable() { // from class:
                                 // com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollSideGuideFrame$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        FingerprintEnrollSideGuideFrame fingerprintEnrollSideGuideFrame =
                                FingerprintEnrollSideGuideFrame.this;
                        PathInterpolator pathInterpolator =
                                FingerprintEnrollSideGuideFrame.INTPL_READY;
                        fingerprintEnrollSideGuideFrame.removeView();
                    }
                },
                500L);
        animate().alpha(RecyclerView.DECELERATION_RATE).setDuration(500L).start();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        PointF pointF = this.mSensorPos;
        canvas.translate(pointF.x, pointF.y);
        float pos = (this.mTimerOuterReady.getPos() * 0.1f) + 1.0f;
        float pos2 = (this.mTimerOuterReady.getPos() * 0.1f) + 1.0f;
        if (this.mState == 2) {
            pos = (this.mTimerScan.getPos() * 0.1f) + 1.0f;
            this.mOuterScanHRatio = pos;
            pos2 = (this.mTimerScan.getPos() * 0.1f) + 1.0f;
            this.mOuterScanVRatio = pos2;
        } else {
            float f = this.mOuterScanHRatio;
            if (f != RecyclerView.DECELERATION_RATE) {
                if (Math.abs(pos - f) < 0.01f) {
                    this.mOuterScanHRatio = RecyclerView.DECELERATION_RATE;
                } else {
                    pos = (pos + this.mOuterScanHRatio) / 2.0f;
                    this.mOuterScanHRatio = pos;
                    pos2 = (pos2 + this.mOuterScanVRatio) / 2.0f;
                    this.mOuterScanVRatio = pos2;
                }
            }
        }
        float f2 = this.mOuterBarWidth;
        RectF expandRectF =
                getExpandRectF(
                        new RectF(
                                (-f2) / 2.0f,
                                RecyclerView.DECELERATION_RATE,
                                f2 / 2.0f,
                                this.mSensorSize.y),
                        pos,
                        pos2,
                        this.mOuterBarMargin);
        float width = expandRectF.width() / 2.0f;
        canvas.drawRoundRect(expandRectF, width, width, this.mPntOuterBar);
        float pos3 = (this.mTimerInnerReady.getPos() * 0.1f) + 1.0f;
        float pos4 = (this.mTimerInnerReady.getPos() * 0.1f) + 1.0f;
        if (this.mState == 2) {
            pos3 = (this.mTimerScan.getPos() * 1.2f) + 1.0f;
            this.mInnerScanHRatio = pos3;
            pos4 = (this.mTimerScan.getPos() * 0.3f) + 1.0f;
            this.mInnerScanVRatio = pos4;
        } else {
            float f3 = this.mInnerScanHRatio;
            if (f3 != RecyclerView.DECELERATION_RATE) {
                if (Math.abs(pos3 - f3) < 0.01f) {
                    this.mInnerScanHRatio = RecyclerView.DECELERATION_RATE;
                } else {
                    pos3 = (pos3 + this.mInnerScanHRatio) / 2.0f;
                    this.mInnerScanHRatio = pos3;
                    pos4 = (pos4 + this.mInnerScanVRatio) / 2.0f;
                    this.mInnerScanVRatio = pos4;
                }
            }
        }
        float f4 = this.mInnerBarWidth;
        RectF expandRectF2 =
                getExpandRectF(
                        new RectF(
                                (-f4) / 2.0f,
                                RecyclerView.DECELERATION_RATE,
                                f4 / 2.0f,
                                this.mSensorSize.y),
                        pos3,
                        pos4,
                        RecyclerView.DECELERATION_RATE);
        float width2 = expandRectF2.width() / 2.0f;
        canvas.drawRoundRect(expandRectF2, width2, width2, this.mPntInnerBar);
        canvas.restore();
        invalidate();
    }

    public final synchronized void removeView() {
        if (this.mIsWindowAlreadyAdded && getWindowToken() != null) {
            this.mIsWindowAlreadyAdded = false;
            this.mWindowManager.removeViewImmediate(this);
        }
    }

    public final synchronized void show() {
        Log.i("BSS_SideSensorGuideFrame", "show");
        addView();
        this.mTimerInnerReady.start();
        this.mTimerOuterReady.start();
        setAlpha(RecyclerView.DECELERATION_RATE);
        animate().alpha(1.0f).setDuration(500L).start();
    }
}
