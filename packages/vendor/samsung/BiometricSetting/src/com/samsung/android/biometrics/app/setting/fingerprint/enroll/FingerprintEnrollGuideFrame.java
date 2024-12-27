package com.samsung.android.biometrics.app.setting.fingerprint.enroll;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.PathParser;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.android.biometrics.app.setting.AnimationHelper;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;

public class FingerprintEnrollGuideFrame extends View {
    public final Bitmap mBmpFingerIcon;
    public final float mBottomBlackMaskHeight;
    public final float mBottomBlackMaskWidth;
    public final PointF mCurrentLoc;
    public float mCurrentProgress;
    public float mCurrentScanScale;
    public final Point mDisplaySize;
    public final int mFingerIconSize;
    public final PointF mFinishLoc;
    public final float mFrameHeight;
    public final float mFrameWidth;
    public final float mGuideFrameCornerSize;
    public final float mGuideFrameReadyThickness;
    public final float mGuideFrameScanThickness;
    public boolean mIsTouchOnTheSensor;
    public boolean mIsWindowAttached;
    public final PathMeasure mMeasureCheckIcon;
    public final float[][] mMovingDistance;
    public final float mMovingHeight;
    public int mMovingIndex;
    public int mMovingTune;
    public final float mMovingWidth;
    public final Path mPathCheckIcon;
    public final Path mPathCheckIconPartial;
    public final Paint mPntBottomBlackMask;
    public final Paint mPntCheckIcon;
    public final Paint mPntFingerIcon;
    public final Paint mPntGuideFrameProgress;
    public final Paint mPntGuideFrameReady;
    public Rect mRectFingerIcon;
    public final PointF mSensorPos;
    public final PointF mStartLoc;
    public int mState;
    public float mTargetProgress;
    public final AnimationHelper mTimerCheckIcon;
    public final AnimationHelper mTimerMove;
    public final AnimationHelper mTimerProgress;
    public final AnimationHelper mTimerReady;
    public final AnimationHelper mTimerScanScale;
    public final AnimationHelper mTimerScanScaleRev;
    public final float mTouchFrameHeight;
    public final float mTouchFrameWidth;
    public int mTouchValidationSet;
    public View.OnTouchListener mTouchViewListener;
    public View mViewTouch;
    public final WindowManager mWindowManager;
    public static final PathInterpolator INTPL_PROGRESS =
            new PathInterpolator(0.47f, 0.41f, RecyclerView.DECELERATION_RATE, 1.0f);
    public static final PathInterpolator INTP_CHECK_ICON =
            new PathInterpolator(0.22f, 0.082f, RecyclerView.DECELERATION_RATE, 1.0f);
    public static final int[] POSITION_INDEX = {4, 7, 8, 5, 2, 1, 0, 3, 6, 4, 7, 5, 1, 3};
    public static final float[] MOVING_DISTANCE_CORRECTION = {
        0.6666667f, 1.0f, 0.6666667f, 1.0f, 1.0f, 1.0f, 0.6666667f, 1.0f, 0.6666667f
    };
    public static final PathInterpolator INTPL_MOVE = new PathInterpolator(0.3f, 1.0f, 0.7f, 1.0f);

    public FingerprintEnrollGuideFrame(Context context) {
        super(context);
        Bitmap createBitmap;
        int i;
        this.mState = 1;
        this.mDisplaySize = new Point();
        PointF pointF = new PointF();
        this.mSensorPos = pointF;
        this.mFrameWidth = RecyclerView.DECELERATION_RATE;
        this.mFrameHeight = RecyclerView.DECELERATION_RATE;
        this.mPntFingerIcon = new Paint(1);
        this.mFingerIconSize = 0;
        Paint paint = new Paint(1);
        this.mPntGuideFrameReady = paint;
        this.mGuideFrameCornerSize = RecyclerView.DECELERATION_RATE;
        this.mGuideFrameReadyThickness = RecyclerView.DECELERATION_RATE;
        this.mGuideFrameScanThickness = RecyclerView.DECELERATION_RATE;
        PathInterpolator pathInterpolator = new PathInterpolator(0.1f, 0.5f, 0.5f, 0.9f);
        AnimationHelper animationHelper = new AnimationHelper(true);
        this.mTimerReady = animationHelper;
        AnimationHelper animationHelper2 = new AnimationHelper(false);
        this.mTimerProgress = animationHelper2;
        this.mTargetProgress = -1.0f;
        this.mCurrentProgress = -1.0f;
        Paint paint2 = new Paint(1);
        this.mPntGuideFrameProgress = paint2;
        AnimationHelper animationHelper3 = new AnimationHelper(false, -1.0f);
        this.mTimerCheckIcon = animationHelper3;
        Path createPathFromPathData =
                PathParser.createPathFromPathData("M42,11l-20.3383,21l-13.6617,-14.1065");
        this.mPathCheckIcon = createPathFromPathData;
        this.mPathCheckIconPartial = new Path();
        this.mMeasureCheckIcon = new PathMeasure();
        Paint paint3 = new Paint();
        this.mPntCheckIcon = paint3;
        this.mMovingWidth = RecyclerView.DECELERATION_RATE;
        this.mMovingHeight = RecyclerView.DECELERATION_RATE;
        this.mMovingTune = 0;
        this.mMovingIndex = 0;
        this.mBottomBlackMaskWidth = RecyclerView.DECELERATION_RATE;
        this.mBottomBlackMaskHeight = RecyclerView.DECELERATION_RATE;
        this.mViewTouch = null;
        this.mTouchFrameWidth = RecyclerView.DECELERATION_RATE;
        this.mTouchFrameHeight = RecyclerView.DECELERATION_RATE;
        AnimationHelper animationHelper4 = new AnimationHelper(false);
        this.mTimerMove = animationHelper4;
        this.mStartLoc = new PointF();
        this.mCurrentLoc = new PointF();
        this.mFinishLoc = new PointF();
        AnimationHelper animationHelper5 = new AnimationHelper(false);
        this.mTimerScanScale = animationHelper5;
        AnimationHelper animationHelper6 =
                new AnimationHelper(false, RecyclerView.DECELERATION_RATE);
        this.mTimerScanScaleRev = animationHelper6;
        this.mCurrentScanScale = RecyclerView.DECELERATION_RATE;
        this.mTouchValidationSet = 0;
        this.mIsTouchOnTheSensor = false;
        this.mIsWindowAttached = false;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mDisplaySize = Utils.getDisplaySize(getContext());
        int i2 = new FingerprintEnrollSensorHelper(getContext()).height;
        Point point = this.mDisplaySize;
        pointF.x = (point.x / 2.0f) - r4.offset;
        pointF.y = point.y - ((i2 / 2.0f) + r4.bottomMargin);
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        this.mFrameWidth = (int) TypedValue.applyDimension(5, 16.0f, displayMetrics);
        float applyDimension = (int) TypedValue.applyDimension(5, 18.0f, displayMetrics);
        this.mFrameHeight = applyDimension;
        this.mTouchFrameWidth = this.mFrameWidth * 1.3f;
        this.mTouchFrameHeight = applyDimension * 1.3f;
        this.mGuideFrameCornerSize = TypedValue.applyDimension(1, 64.0f, displayMetrics);
        this.mGuideFrameReadyThickness = TypedValue.applyDimension(1, 5.0f, displayMetrics);
        this.mGuideFrameScanThickness = TypedValue.applyDimension(1, 6.0f, displayMetrics);
        this.mFingerIconSize = (int) TypedValue.applyDimension(1, 72.0f, displayMetrics);
        this.mBottomBlackMaskWidth = TypedValue.applyDimension(1, 180.0f, displayMetrics);
        this.mBottomBlackMaskHeight = TypedValue.applyDimension(1, 20.0f, displayMetrics);
        float applyDimension2 = TypedValue.applyDimension(5, 2.0f, displayMetrics);
        this.mMovingHeight = applyDimension2;
        this.mMovingWidth = applyDimension2;
        this.mMovingWidth = Math.min(applyDimension2, (r4.width * 3.0f) / 4.0f);
        this.mMovingHeight = Math.min(this.mMovingHeight, (i2 * 3.0f) / 4.0f);
        if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL) {
            Paint paint4 = new Paint();
            this.mPntBottomBlackMask = paint4;
            float f = this.mDisplaySize.y;
            paint4.setShader(
                    new LinearGradient(
                            RecyclerView.DECELERATION_RATE,
                            f - this.mBottomBlackMaskHeight,
                            RecyclerView.DECELERATION_RATE,
                            f,
                            0,
                            -16777216,
                            Shader.TileMode.CLAMP));
        }
        float f2 = this.mMovingWidth;
        float f3 = -f2;
        float f4 = this.mMovingHeight;
        float f5 = -f4;
        this.mMovingDistance =
                new float[][] {
                    new float[] {f3, f5},
                    new float[] {RecyclerView.DECELERATION_RATE, f5},
                    new float[] {f2, f5},
                    new float[] {f3, RecyclerView.DECELERATION_RATE},
                    new float[] {RecyclerView.DECELERATION_RATE, RecyclerView.DECELERATION_RATE},
                    new float[] {f2, RecyclerView.DECELERATION_RATE},
                    new float[] {f3, f4},
                    new float[] {RecyclerView.DECELERATION_RATE, f4},
                    new float[] {f2, f4}
                };
        Resources resources = getResources();
        ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
        Drawable drawable = resources.getDrawable(R.drawable.display_ux_finger, null);
        if (drawable == null) {
            createBitmap = null;
        } else {
            createBitmap =
                    Bitmap.createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        if (createBitmap == null) {
            Log.w("BSS_EnrollGuideFrame", "Failed to load finger icon");
            i = 0;
        } else {
            Paint paint5 = new Paint();
            paint5.setColorFilter(
                    new PorterDuffColorFilter(
                            getContext().getColor(R.color.fingerprint_enroll_finger_icon_color),
                            PorterDuff.Mode.SRC_ATOP));
            new Canvas(createBitmap)
                    .drawBitmap(
                            createBitmap,
                            RecyclerView.DECELERATION_RATE,
                            RecyclerView.DECELERATION_RATE,
                            paint5);
            int i3 = this.mFingerIconSize;
            i = 0;
            this.mBmpFingerIcon = Bitmap.createScaledBitmap(createBitmap, i3, i3, false);
        }
        animationHelper.addTrack(new AnimationHelper.Item(500L, pathInterpolator));
        animationHelper.addTrack(new AnimationHelper.Item(500L, pathInterpolator, i));
        animationHelper.start();
        Paint.Style style = Paint.Style.STROKE;
        paint2.setStyle(style);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        Paint.Cap cap = Paint.Cap.ROUND;
        paint2.setStrokeCap(cap);
        paint2.setStrokeWidth(this.mGuideFrameScanThickness);
        paint2.setColor(-15620001);
        PathInterpolator pathInterpolator2 = INTPL_PROGRESS;
        animationHelper2.addTrack(new AnimationHelper.Item(1500L, pathInterpolator2));
        animationHelper5.addTrack(new AnimationHelper.Item(300L, pathInterpolator2));
        animationHelper6.addTrack(new AnimationHelper.Item(300L, pathInterpolator2, 0));
        paint.setStyle(style);
        paint.setStrokeWidth(this.mGuideFrameReadyThickness);
        paint.setColor(-12040120);
        paint3.setMaskFilter(new BlurMaskFilter(1.0f, BlurMaskFilter.Blur.NORMAL));
        paint3.setColor(-15620001);
        paint3.setStyle(style);
        paint3.setStrokeCap(cap);
        paint3.setStrokeWidth(20.0f);
        PathInterpolator pathInterpolator3 = INTP_CHECK_ICON;
        animationHelper3.addTrack(new AnimationHelper.Item(630L, pathInterpolator3));
        animationHelper3.addTrack(new AnimationHelper.Item(1000L, 1.0f));
        animationHelper3.addTrack(new AnimationHelper.Item(330L, pathInterpolator3, 0));
        Matrix matrix = new Matrix();
        float f6 = this.mFrameWidth / 45.0f;
        matrix.setScale(f6, f6);
        createPathFromPathData.transform(matrix);
        animationHelper4.addTrack(new AnimationHelper.Item(300L, INTPL_MOVE));
        addOnAttachStateChangeListener(
                new View
                        .OnAttachStateChangeListener() { // from class:
                                                         // com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollGuideFrame.1
                    @Override // android.view.View.OnAttachStateChangeListener
                    public final void onViewAttachedToWindow(View view) {
                        FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame =
                                FingerprintEnrollGuideFrame.this;
                        if (fingerprintEnrollGuideFrame.mIsWindowAttached) {
                            return;
                        }
                        fingerprintEnrollGuideFrame.hide();
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public final void onViewDetachedFromWindow(View view) {}
                });
        setState(1);
    }

    private int getMoveIndex() {
        int i = this.mMovingIndex;
        if (i < 0) {
            return -1;
        }
        int[] iArr = POSITION_INDEX;
        if (i >= iArr.length) {
            return -1;
        }
        return iArr[i];
    }

    private float getScale() {
        float pos;
        float f;
        if (this.mState == 2) {
            pos = this.mTimerScanScale.getPos();
            f = 0.35f;
        } else {
            if (this.mTimerScanScaleRev.getPos() > RecyclerView.DECELERATION_RATE) {
                this.mTimerReady.start();
                return (this.mTimerScanScaleRev.getPos() * this.mCurrentScanScale) + 1.0f;
            }
            pos = this.mTimerReady.getPos();
            f = 0.05f;
        }
        return (pos * f) + 1.0f;
    }

    private WindowManager.LayoutParams getTouchWindowLayoutParam() {
        WindowManager.LayoutParams windowLayoutParam =
                getWindowLayoutParam(
                        (int) this.mTouchFrameWidth, (int) this.mTouchFrameHeight, true);
        windowLayoutParam.gravity = 51;
        return windowLayoutParam;
    }

    public static WindowManager.LayoutParams getWindowLayoutParam(int i, int i2, boolean z) {
        WindowManager.LayoutParams layoutParams =
                new WindowManager.LayoutParams(i, i2, 2008, (z ? 0 : 16) | 8, -3);
        layoutParams.setFitInsetsTypes(0);
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.semAddExtensionFlags(8);
        return layoutParams;
    }

    private void setState(int i) {
        if (i == 2) {
            this.mTargetProgress = -1.0f;
            this.mCurrentProgress = RecyclerView.DECELERATION_RATE;
            this.mTimerProgress.start();
            this.mTimerScanScale.start();
        } else if (i == 3) {
            if (this.mState == 2) {
                this.mCurrentScanScale = this.mTimerScanScale.getPos() * 0.35f;
                this.mTimerScanScaleRev.start();
            }
        } else if (i == 4) {
            this.mTimerCheckIcon.start();
        }
        this.mState = i;
        invalidate();
    }

    public final void addTouchValidation(int i) {
        if (i < 1 || i > 3) {
            return;
        }
        if (i == 1) {
            this.mTouchValidationSet = 0;
        }
        if (this.mIsTouchOnTheSensor) {
            this.mTouchValidationSet = (1 << (3 - i)) | this.mTouchValidationSet;
        }
    }

    public final void completeScan() {
        setState(4);
    }

    public final void finishScan() {
        setState(3);
    }

    public int getTalkBackPositionIndex() {
        return getMoveIndex();
    }

    public final synchronized void hide() {
        try {
            Log.i("BSS_EnrollGuideFrame", "hide");
            if (getWindowToken() != null) {
                this.mWindowManager.removeViewImmediate(this);
            }
            View view = this.mViewTouch;
            if (view != null && view.getWindowToken() != null) {
                this.mWindowManager.removeViewImmediate(this.mViewTouch);
                this.mViewTouch = null;
            }
            this.mIsWindowAttached = false;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void moveNext() {
        int max = Math.max(this.mMovingIndex, 0);
        int i = this.mMovingIndex;
        int i2 = i + 1;
        this.mMovingIndex = i2;
        int[] iArr = POSITION_INDEX;
        if (i2 >= iArr.length) {
            this.mMovingIndex = i - 4;
        }
        setFramePosition();
        Log.i("BSS_EnrollGuideFrame", "Moved from " + iArr[max] + " to " + iArr[this.mMovingIndex]);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Display display = getContext().getDisplay();
        float f = this.mTargetProgress;
        if (f != -1.0f) {
            this.mCurrentProgress =
                    f == RecyclerView.DECELERATION_RATE ? 0.0f : (this.mCurrentProgress + f) / 2.0f;
        } else if (this.mState == 2) {
            this.mCurrentProgress = this.mTimerProgress.getPos();
        } else {
            this.mCurrentProgress = RecyclerView.DECELERATION_RATE;
        }
        canvas.save();
        PointF pointF = this.mSensorPos;
        canvas.translate(pointF.x, pointF.y);
        canvas.rotate(display != null ? Utils.getRotation(((View) this).mContext) * 90 : 0.0f);
        if (this.mState == 4) {
            this.mPathCheckIconPartial.reset();
            this.mMeasureCheckIcon.setPath(this.mPathCheckIcon, false);
            float length = this.mMeasureCheckIcon.getLength();
            float pos = this.mTimerCheckIcon.getPos() * length;
            if (pos >= RecyclerView.DECELERATION_RATE) {
                canvas.save();
                canvas.translate((-this.mFrameWidth) / 2.0f, (-this.mFrameHeight) / 2.0f);
                this.mMeasureCheckIcon.getSegment(
                        length - Math.min(length, pos), length, this.mPathCheckIconPartial, true);
                canvas.drawPath(this.mPathCheckIconPartial, this.mPntCheckIcon);
                canvas.restore();
                invalidate();
            }
        } else {
            float pos2 = this.mTimerMove.getPos();
            PointF pointF2 = this.mStartLoc;
            float f2 = 1.0f - pos2;
            float f3 = pointF2.x * f2;
            PointF pointF3 = this.mFinishLoc;
            float f4 = (pointF3.x * pos2) + f3;
            float f5 = (pointF3.y * pos2) + (pointF2.y * f2);
            this.mCurrentLoc.set(f4, f5);
            canvas.translate(f4, f5);
            Bitmap bitmap = this.mBmpFingerIcon;
            if (bitmap != null) {
                float f6 = (-this.mFingerIconSize) / 2.0f;
                canvas.drawBitmap(bitmap, f6, f6, this.mPntFingerIcon);
            }
            float scale = getScale();
            float f7 = this.mFrameWidth;
            float f8 = this.mFrameHeight;
            RectF rectF = new RectF((-f7) / 2.0f, (-f8) / 2.0f, f7 / 2.0f, f8 / 2.0f);
            float f9 = scale - 1.0f;
            float f10 = this.mGuideFrameReadyThickness / 2.0f;
            float width = ((rectF.width() * f9) / 2.0f) + f10;
            float height = ((rectF.height() * f9) / 2.0f) + f10;
            RectF rectF2 =
                    new RectF(
                            rectF.left - width,
                            rectF.top - height,
                            rectF.right + width,
                            rectF.bottom + height);
            float f11 = (this.mGuideFrameReadyThickness / 2.0f) + this.mGuideFrameCornerSize;
            canvas.drawRoundRect(rectF2, f11, f11, this.mPntGuideFrameReady);
            if (this.mState == 2) {
                Path path = new Path();
                path.addRoundRect(rectF2, f11, f11, Path.Direction.CW);
                Path path2 = new Path();
                PathMeasure pathMeasure = new PathMeasure(path, false);
                float length2 = pathMeasure.getLength();
                float f12 = 0.26f * length2;
                float f13 = (this.mCurrentProgress * length2) + f12;
                pathMeasure.getSegment(f12, Math.min(length2, f13), path2, true);
                canvas.drawPath(path2, this.mPntGuideFrameProgress);
                if (f13 > length2) {
                    pathMeasure.getSegment(
                            RecyclerView.DECELERATION_RATE, f13 - length2, path2, true);
                    canvas.drawPath(path2, this.mPntGuideFrameProgress);
                }
            }
        }
        canvas.restore();
        if (this.mState != 4 && this.mPntBottomBlackMask != null) {
            canvas.drawRect(
                    (getWidth() - this.mBottomBlackMaskWidth) / 2.0f,
                    getHeight() - this.mBottomBlackMaskHeight,
                    (getWidth() + this.mBottomBlackMaskWidth) / 2.0f,
                    getHeight(),
                    this.mPntBottomBlackMask);
        }
        invalidate();
    }

    public final synchronized void setFramePosition() {
        int moveIndex = getMoveIndex();
        float[][] fArr = this.mMovingDistance;
        if (fArr != null && moveIndex < fArr.length) {
            float[] fArr2 = fArr[moveIndex];
            float f = MOVING_DISTANCE_CORRECTION[moveIndex];
            float f2 = (3 - this.mMovingTune) / 3.0f;
            PointF pointF = this.mStartLoc;
            PointF pointF2 = this.mCurrentLoc;
            pointF.set(pointF2.x, pointF2.y);
            this.mFinishLoc.set(fArr2[0] * f * f2, fArr2[1] * f * f2);
            this.mTimerMove.start();
            View view = this.mViewTouch;
            if (view != null) {
                WindowManager.LayoutParams layoutParams =
                        (WindowManager.LayoutParams) view.getLayoutParams();
                PointF pointF3 = this.mSensorPos;
                float f3 = pointF3.x;
                PointF pointF4 = this.mFinishLoc;
                layoutParams.x = (int) ((f3 + pointF4.x) - (this.mTouchFrameWidth / 2.0f));
                layoutParams.y = (int) ((pointF3.y + pointF4.y) - (this.mTouchFrameHeight / 2.0f));
                this.mWindowManager.updateViewLayout(this.mViewTouch, layoutParams);
            }
        }
    }

    public void setProgressResult(int i) {
        if (i == 1) {
            if (this.mState == 2) {
                this.mTargetProgress = 1.0f;
            }
        } else if (i == 3) {
            this.mTargetProgress = RecyclerView.DECELERATION_RATE;
        } else if (i == 2) {
            this.mTargetProgress = this.mCurrentProgress;
        }
        invalidate();
    }

    public final synchronized void show() {
        try {
            Log.i("BSS_EnrollGuideFrame", "show");
            if (getWindowToken() == null && getParent() == null) {
                WindowManager windowManager = this.mWindowManager;
                Point point = this.mDisplaySize;
                windowManager.addView(this, getWindowLayoutParam(point.x, point.y, false));
            }
            if (this.mViewTouch == null) {
                View view = new View(getContext());
                this.mViewTouch = view;
                this.mWindowManager.addView(view, getTouchWindowLayoutParam());
                this.mViewTouch.setOnTouchListener(
                        new View
                                .OnTouchListener() { // from class:
                                                     // com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollGuideFrame$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnTouchListener
                            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                                FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame =
                                        FingerprintEnrollGuideFrame.this;
                                if (fingerprintEnrollGuideFrame.mTouchViewListener != null) {
                                    if (fingerprintEnrollGuideFrame.mRectFingerIcon != null) {
                                        view2.getLocationOnScreen(new int[2]);
                                        fingerprintEnrollGuideFrame.mIsTouchOnTheSensor =
                                                fingerprintEnrollGuideFrame.mRectFingerIcon
                                                        .contains(
                                                                (int)
                                                                        ((motionEvent.getX()
                                                                                        + r0[0])
                                                                                - fingerprintEnrollGuideFrame
                                                                                        .mCurrentLoc
                                                                                        .x),
                                                                (int)
                                                                        ((motionEvent.getY()
                                                                                        + r0[1])
                                                                                - fingerprintEnrollGuideFrame
                                                                                        .mCurrentLoc
                                                                                        .y));
                                        if (motionEvent.getAction() == 0) {
                                            fingerprintEnrollGuideFrame.addTouchValidation(1);
                                        } else if (motionEvent.getAction() == 1) {
                                            fingerprintEnrollGuideFrame.addTouchValidation(3);
                                        }
                                    }
                                    fingerprintEnrollGuideFrame.mTouchViewListener.onTouch(
                                            view2, motionEvent);
                                }
                                return false;
                            }
                        });
            }
            setFramePosition();
            this.mIsWindowAttached = true;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void startScan() {
        Log.i("BSS_EnrollGuideFrame", "startScan");
        if (this.mState != 2) {
            setState(2);
        }
    }

    public final void tuneMovingArea(boolean z) {
        int i = this.mMovingTune;
        if (z) {
            this.mMovingTune = Math.max(0, i - 1);
        } else {
            this.mMovingTune = Math.min(2, i + 1);
        }
        setFramePosition();
        Log.i("BSS_EnrollGuideFrame", "Tuned from " + i + " to " + this.mMovingTune);
    }
}
