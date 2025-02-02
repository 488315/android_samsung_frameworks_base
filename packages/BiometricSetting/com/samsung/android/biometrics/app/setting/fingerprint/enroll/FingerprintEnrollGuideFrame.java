package com.samsung.android.biometrics.app.setting.fingerprint.enroll;

import android.annotation.SuppressLint;
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
import com.samsung.android.biometrics.app.setting.AnimationHelper;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;

@SuppressLint({"ViewConstructor"})
/* loaded from: classes.dex */
public class FingerprintEnrollGuideFrame extends View {
    private Bitmap mBmpFingerIcon;
    private float mBottomBlackMaskHeight;
    private float mBottomBlackMaskWidth;
    private final PointF mCurrentLoc;
    private float mCurrentProgress;
    private float mCurrentScanScale;
    private Point mDisplaySize;
    private int mFingerIconSize;
    private final PointF mFinishLoc;
    private float mFrameHeight;
    private float mFrameWidth;
    private float mGuideFrameCornerSize;
    private float mGuideFrameReadyThickness;
    private float mGuideFrameScanThickness;
    private boolean mIsTouchOnTheSensor;
    private boolean mIsWindowAttached;
    private final PathMeasure mMeasureCheckIcon;
    private float[][] mMovingDistance;
    private float mMovingHeight;
    private int mMovingIndex;
    private int mMovingTune;
    private float mMovingWidth;
    private final Path mPathCheckIcon;
    private final Path mPathCheckIconPartial;
    private Paint mPntBottomBlackMask;
    private final Paint mPntCheckIcon;
    private final Paint mPntFingerIcon;
    private final Paint mPntGuideFrameProgress;
    private final Paint mPntGuideFrameReady;
    private Rect mRectFingerIcon;
    private final PointF mSensorPos;
    private final PointF mStartLoc;
    private int mState;
    private float mTargetProgress;
    private final AnimationHelper mTimerCheckIcon;
    private final AnimationHelper mTimerMove;
    private final AnimationHelper mTimerProgress;
    private final AnimationHelper mTimerReady;
    private final AnimationHelper mTimerScanScale;
    private final AnimationHelper mTimerScanScaleRev;
    private float mTouchFrameHeight;
    private float mTouchFrameWidth;
    private int mTouchValidationSet;
    private View.OnTouchListener mTouchViewListener;
    private View mViewTouch;
    private final WindowManager mWindowManager;
    private static final PathInterpolator INTPL_PROGRESS = new PathInterpolator(0.47f, 0.41f, 0.0f, 1.0f);
    private static final PathInterpolator INTP_CHECK_ICON = new PathInterpolator(0.22f, 0.082f, 0.0f, 1.0f);
    private static final int[] POSITION_INDEX = {4, 7, 8, 5, 2, 1, 0, 3, 6, 4, 7, 5, 1, 3};
    private static final float[] MOVING_DISTANCE_CORRECTION = {0.6666667f, 1.0f, 0.6666667f, 1.0f, 1.0f, 1.0f, 0.6666667f, 1.0f, 0.6666667f};
    private static final PathInterpolator INTPL_MOVE = new PathInterpolator(0.3f, 1.0f, 0.7f, 1.0f);

    public static void $r8$lambda$xMt0u9kOokc5FZIHapm2dwzzs50(FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame, View view, MotionEvent motionEvent) {
        if (fingerprintEnrollGuideFrame.mTouchViewListener == null) {
            return;
        }
        if (fingerprintEnrollGuideFrame.mRectFingerIcon != null) {
            view.getLocationOnScreen(new int[2]);
            fingerprintEnrollGuideFrame.mIsTouchOnTheSensor = fingerprintEnrollGuideFrame.mRectFingerIcon.contains((int) ((motionEvent.getX() + r0[0]) - fingerprintEnrollGuideFrame.mCurrentLoc.x), (int) ((motionEvent.getY() + r0[1]) - fingerprintEnrollGuideFrame.mCurrentLoc.y));
            if (motionEvent.getAction() == 0) {
                fingerprintEnrollGuideFrame.addTouchValidation(1);
            } else if (motionEvent.getAction() == 1) {
                fingerprintEnrollGuideFrame.addTouchValidation(3);
            }
        }
        fingerprintEnrollGuideFrame.mTouchViewListener.onTouch(view, motionEvent);
    }

    public FingerprintEnrollGuideFrame(Context context) {
        super(context);
        Bitmap createBitmap;
        int i;
        this.mState = 1;
        this.mDisplaySize = new Point();
        PointF pointF = new PointF();
        this.mSensorPos = pointF;
        this.mFrameWidth = 0.0f;
        this.mFrameHeight = 0.0f;
        this.mPntFingerIcon = new Paint(1);
        this.mFingerIconSize = 0;
        Paint paint = new Paint(1);
        this.mPntGuideFrameReady = paint;
        this.mGuideFrameCornerSize = 0.0f;
        this.mGuideFrameReadyThickness = 0.0f;
        this.mGuideFrameScanThickness = 0.0f;
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
        Path createPathFromPathData = PathParser.createPathFromPathData("M42,11l-20.3383,21l-13.6617,-14.1065");
        this.mPathCheckIcon = createPathFromPathData;
        this.mPathCheckIconPartial = new Path();
        this.mMeasureCheckIcon = new PathMeasure();
        Paint paint3 = new Paint();
        this.mPntCheckIcon = paint3;
        this.mMovingWidth = 0.0f;
        this.mMovingHeight = 0.0f;
        this.mMovingTune = 0;
        this.mMovingIndex = 0;
        this.mBottomBlackMaskWidth = 0.0f;
        this.mBottomBlackMaskHeight = 0.0f;
        this.mViewTouch = null;
        this.mTouchFrameWidth = 0.0f;
        this.mTouchFrameHeight = 0.0f;
        AnimationHelper animationHelper4 = new AnimationHelper(false);
        this.mTimerMove = animationHelper4;
        this.mStartLoc = new PointF();
        this.mCurrentLoc = new PointF();
        this.mFinishLoc = new PointF();
        AnimationHelper animationHelper5 = new AnimationHelper(false);
        this.mTimerScanScale = animationHelper5;
        AnimationHelper animationHelper6 = new AnimationHelper(false, 0.0f);
        this.mTimerScanScaleRev = animationHelper6;
        this.mCurrentScanScale = 0.0f;
        this.mTouchValidationSet = 0;
        this.mIsTouchOnTheSensor = false;
        this.mIsWindowAttached = false;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mDisplaySize = Utils.getDisplaySize(getContext());
        FingerprintEnrollSensorHelper fingerprintEnrollSensorHelper = new FingerprintEnrollSensorHelper(getContext());
        Point point = this.mDisplaySize;
        pointF.x = (point.x / 2.0f) - fingerprintEnrollSensorHelper.offset;
        pointF.y = point.y - ((fingerprintEnrollSensorHelper.height / 2.0f) + fingerprintEnrollSensorHelper.bottomMargin);
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        this.mFrameWidth = (int) TypedValue.applyDimension(5, 16.0f, displayMetrics);
        float applyDimension = (int) TypedValue.applyDimension(5, 17.4f, displayMetrics);
        this.mFrameHeight = applyDimension;
        this.mTouchFrameWidth = this.mFrameWidth * 1.5f;
        this.mTouchFrameHeight = applyDimension * 1.5f;
        this.mGuideFrameCornerSize = TypedValue.applyDimension(1, 64.0f, displayMetrics);
        this.mGuideFrameReadyThickness = TypedValue.applyDimension(1, 5.0f, displayMetrics);
        this.mGuideFrameScanThickness = TypedValue.applyDimension(1, 6.0f, displayMetrics);
        this.mFingerIconSize = (int) TypedValue.applyDimension(1, 54.0f, displayMetrics);
        this.mBottomBlackMaskWidth = TypedValue.applyDimension(1, 180.0f, displayMetrics);
        this.mBottomBlackMaskHeight = TypedValue.applyDimension(1, 20.0f, displayMetrics);
        float applyDimension2 = TypedValue.applyDimension(5, 2.0f, displayMetrics);
        this.mMovingHeight = applyDimension2;
        this.mMovingWidth = applyDimension2;
        this.mMovingWidth = Math.min(applyDimension2, (fingerprintEnrollSensorHelper.width * 3.0f) / 4.0f);
        this.mMovingHeight = Math.min(this.mMovingHeight, (fingerprintEnrollSensorHelper.height * 3.0f) / 4.0f);
        if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL) {
            Paint paint4 = new Paint();
            this.mPntBottomBlackMask = paint4;
            float f = this.mDisplaySize.y;
            paint4.setShader(new LinearGradient(0.0f, f - this.mBottomBlackMaskHeight, 0.0f, f, 0, -16777216, Shader.TileMode.CLAMP));
        }
        float f2 = this.mMovingWidth;
        float f3 = -f2;
        float f4 = this.mMovingHeight;
        float f5 = -f4;
        this.mMovingDistance = new float[][]{new float[]{f3, f5}, new float[]{0.0f, f5}, new float[]{f2, f5}, new float[]{f3, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{f2, 0.0f}, new float[]{f3, f4}, new float[]{0.0f, f4}, new float[]{f2, f4}};
        Resources resources = getResources();
        int i2 = ResourcesCompat.$r8$clinit;
        Drawable drawable = resources.getDrawable(R.drawable.display_ux_finger, null);
        if (drawable == null) {
            createBitmap = null;
        } else {
            createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        if (createBitmap == null) {
            Log.w("BSS_EnrollGuideFrame", "Failed to load finger icon");
            i = 0;
        } else {
            Paint paint5 = new Paint();
            paint5.setColorFilter(new PorterDuffColorFilter(getContext().getColor(R.color.fingerprint_enroll_finger_icon_color), PorterDuff.Mode.SRC_ATOP));
            new Canvas(createBitmap).drawBitmap(createBitmap, 0.0f, 0.0f, paint5);
            int i3 = this.mFingerIconSize;
            i = 0;
            this.mBmpFingerIcon = Bitmap.createScaledBitmap(createBitmap, i3, i3, false);
        }
        animationHelper.addTrack(new AnimationHelper.Item(500L, pathInterpolator));
        animationHelper.addTrack(new AnimationHelper.Item(500L, pathInterpolator, i));
        animationHelper.start();
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setStrokeWidth(this.mGuideFrameScanThickness);
        paint2.setColor(-15423386);
        PathInterpolator pathInterpolator2 = INTPL_PROGRESS;
        animationHelper2.addTrack(new AnimationHelper.Item(1500L, pathInterpolator2));
        animationHelper5.addTrack(new AnimationHelper.Item(300L, pathInterpolator2));
        animationHelper6.addTrack(new AnimationHelper.Item(300L, pathInterpolator2, 0));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.mGuideFrameReadyThickness);
        paint.setColor(-12040120);
        paint3.setMaskFilter(new BlurMaskFilter(1.0f, BlurMaskFilter.Blur.NORMAL));
        paint3.setColor(-15423386);
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setStrokeCap(Paint.Cap.ROUND);
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
        addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollGuideFrame.1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                if (FingerprintEnrollGuideFrame.this.mIsWindowAttached) {
                    return;
                }
                FingerprintEnrollGuideFrame.this.hide();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
            }
        });
        setState(1);
    }

    private void addTouchValidation(int i) {
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
            if (this.mTimerScanScaleRev.getPos() > 0.0f) {
                this.mTimerReady.start();
                return (this.mTimerScanScaleRev.getPos() * this.mCurrentScanScale) + 1.0f;
            }
            pos = this.mTimerReady.getPos();
            f = 0.05f;
        }
        return (pos * f) + 1.0f;
    }

    @SuppressLint({"RtlHardcoded"})
    private WindowManager.LayoutParams getTouchWindowLayoutParam() {
        WindowManager.LayoutParams windowLayoutParam = getWindowLayoutParam((int) this.mTouchFrameWidth, (int) this.mTouchFrameHeight, true);
        windowLayoutParam.gravity = 51;
        return windowLayoutParam;
    }

    private static WindowManager.LayoutParams getWindowLayoutParam(int i, int i2, boolean z) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i, i2, 2008, (z ? 0 : 16) | 8, -3);
        layoutParams.setFitInsetsTypes(0);
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.semAddExtensionFlags(8);
        return layoutParams;
    }

    private synchronized void setFramePosition() {
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
                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
                PointF pointF3 = this.mSensorPos;
                float f3 = pointF3.x;
                PointF pointF4 = this.mFinishLoc;
                layoutParams.x = (int) ((f3 + pointF4.x) - (this.mTouchFrameWidth / 2.0f));
                layoutParams.y = (int) ((pointF3.y + pointF4.y) - (this.mTouchFrameHeight / 2.0f));
                this.mWindowManager.updateViewLayout(this.mViewTouch, layoutParams);
            }
        }
    }

    private void setState(int i) {
        if (i == 2) {
            this.mTargetProgress = -1.0f;
            this.mCurrentProgress = 0.0f;
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

    public final void completeCapture() {
        addTouchValidation(2);
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
    }

    public final boolean isFingerMoved() {
        Log.i("BSS_EnrollGuideFrame", "isFingerMoved=" + ("000" + Integer.toBinaryString(this.mTouchValidationSet)).substring(r0.length() - 3));
        int i = this.mTouchValidationSet;
        return i == 2 || i == 6;
    }

    public final void moveNext() {
        int max = Math.max(this.mMovingIndex, 0);
        int i = this.mMovingIndex + 1;
        this.mMovingIndex = i;
        int[] iArr = POSITION_INDEX;
        if (i >= iArr.length) {
            this.mMovingIndex = i - 5;
        }
        setFramePosition();
        Log.i("BSS_EnrollGuideFrame", "Moved from " + iArr[max] + " to " + iArr[this.mMovingIndex]);
    }

    @Override // android.view.View
    protected final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Display display = getContext().getDisplay();
        float f = this.mTargetProgress;
        if (f != -1.0f) {
            this.mCurrentProgress = f == 0.0f ? 0.0f : (this.mCurrentProgress + f) / 2.0f;
        } else if (this.mState == 2) {
            this.mCurrentProgress = this.mTimerProgress.getPos();
        } else {
            this.mCurrentProgress = 0.0f;
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
            if (pos >= 0.0f) {
                canvas.save();
                canvas.translate((-this.mFrameWidth) / 2.0f, (-this.mFrameHeight) / 2.0f);
                this.mMeasureCheckIcon.getSegment(length - Math.min(length, pos), length, this.mPathCheckIconPartial, true);
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
            RectF rectF2 = new RectF(rectF.left - width, rectF.top - height, rectF.right + width, rectF.bottom + height);
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
                    pathMeasure.getSegment(0.0f, f13 - length2, path2, true);
                    canvas.drawPath(path2, this.mPntGuideFrameProgress);
                }
            }
        }
        canvas.restore();
        if (this.mState != 4 && this.mPntBottomBlackMask != null) {
            canvas.drawRect((getWidth() - this.mBottomBlackMaskWidth) / 2.0f, getHeight() - this.mBottomBlackMaskHeight, (getWidth() + this.mBottomBlackMaskWidth) / 2.0f, getHeight(), this.mPntBottomBlackMask);
        }
        invalidate();
    }

    public void setProgressResult(int i) {
        if (i == 1) {
            if (this.mState == 2) {
                this.mTargetProgress = 1.0f;
            }
        } else if (i == 3) {
            this.mTargetProgress = 0.0f;
        } else if (i == 2) {
            this.mTargetProgress = this.mCurrentProgress;
        }
        invalidate();
    }

    public final void setTouchWindowListener(View.OnTouchListener onTouchListener, Rect rect) {
        this.mTouchViewListener = onTouchListener;
        this.mRectFingerIcon = rect;
    }

    public final synchronized void show() {
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
            this.mViewTouch.setOnTouchListener(new View.OnTouchListener() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollGuideFrame$$ExternalSyntheticLambda0
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view2, MotionEvent motionEvent) {
                    FingerprintEnrollGuideFrame.$r8$lambda$xMt0u9kOokc5FZIHapm2dwzzs50(FingerprintEnrollGuideFrame.this, view2, motionEvent);
                    return false;
                }
            });
        }
        setFramePosition();
        this.mIsWindowAttached = true;
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
