package com.android.internal.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import android.view.ISystemGestureExclusionListener;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.RoundedCorner;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowInsets;
import android.view.WindowManagerGlobal;
import android.view.WindowManagerPolicyConstants;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.ims.settings.SemImsProfile;

/* loaded from: classes6.dex */
public class PointerLocationView extends View implements InputManager.InputDeviceListener, WindowManagerPolicyConstants.PointerEventListener {
    private static final String ALT_STRATEGY_PROPERY_KEY = "debug.velocitytracker.alt";
    private static final PointerState EMPTY_POINTER_STATE = new PointerState();
    private static final String GESTURE_EXCLUSION_PROP = "debug.pointerlocation.showexclusion";
    private static final String TAG = "Pointer";
    private int mActivePointerId;
    private final VelocityTracker mAltVelocity;
    private boolean mCurDown;
    private int mCurNumPointers;
    private final Paint mCurrentPointPaint;
    private float mDensity;
    private int mHeaderBottom;
    private int mHeaderPaddingTop;
    private final InputManager mIm;
    private int mMaxNumPointers;
    private final Paint mPaint;
    private final Paint mPathPaint;
    private final SparseArray<PointerState> mPointers;
    private boolean mPrintCoords;
    private final RectF mReusableOvalRect;
    private final Region mSystemGestureExclusion;
    private final ISystemGestureExclusionListener mSystemGestureExclusionListener;
    private final Paint mSystemGestureExclusionPaint;
    private final Path mSystemGestureExclusionPath;
    private final Region mSystemGestureExclusionRejected;
    private final Paint mSystemGestureExclusionRejectedPaint;
    private final Paint mTargetPaint;
    private final MotionEvent.PointerCoords mTempCoords;
    private final FasterStringBuilder mText;
    private final Paint mTextBackgroundPaint;
    private final Paint mTextLevelPaint;
    private final Paint.FontMetricsInt mTextMetrics;
    private final Paint mTextPaint;
    private Bitmap mTraceBitmap;
    private final Canvas mTraceCanvas;
    private final ViewConfiguration mVC;
    private final VelocityTracker mVelocity;
    private Insets mWaterfallInsets;

    public static class PointerState {
        private float mAltXVelocity;
        private float mAltYVelocity;
        private float mBoundingBottom;
        private float mBoundingLeft;
        private float mBoundingRight;
        private float mBoundingTop;
        private boolean mCurDown;
        private boolean mCurrentPointIsHistorical;
        private boolean mHasBoundingBox;
        private boolean mPreviousPointIsHistorical;
        private int mToolType;
        private float mXVelocity;
        private float mYVelocity;
        private float mCurrentX = Float.NaN;
        private float mCurrentY = Float.NaN;
        private float mPreviousX = Float.NaN;
        private float mPreviousY = Float.NaN;
        private float mFirstX = Float.NaN;
        private float mFirstY = Float.NaN;
        private final MotionEvent.PointerCoords mCoords = new MotionEvent.PointerCoords();

        void addTrace(float f, float f2, boolean z) {
            if (Float.isNaN(this.mFirstX)) {
                this.mFirstX = f;
            }
            if (Float.isNaN(this.mFirstY)) {
                this.mFirstY = f2;
            }
            this.mPreviousX = this.mCurrentX;
            this.mPreviousY = this.mCurrentY;
            this.mCurrentX = f;
            this.mCurrentY = f2;
            this.mPreviousPointIsHistorical = this.mCurrentPointIsHistorical;
            this.mCurrentPointIsHistorical = z;
        }
    }

    public PointerLocationView(Context context) {
        super(context);
        this.mTextMetrics = new Paint.FontMetricsInt();
        this.mHeaderPaddingTop = 0;
        this.mWaterfallInsets = Insets.NONE;
        this.mPointers = new SparseArray<>();
        this.mTempCoords = new MotionEvent.PointerCoords();
        this.mSystemGestureExclusion = new Region();
        this.mSystemGestureExclusionRejected = new Region();
        this.mSystemGestureExclusionPath = new Path();
        this.mText = new FasterStringBuilder();
        this.mPrintCoords = true;
        this.mReusableOvalRect = new RectF();
        this.mSystemGestureExclusionListener = new AnonymousClass1();
        setFocusableInTouchMode(true);
        this.mIm = (InputManager) context.getSystemService(InputManager.class);
        this.mVC = ViewConfiguration.get(context);
        Paint paint = new Paint();
        this.mTextPaint = paint;
        paint.setAntiAlias(true);
        paint.setARGB(255, 0, 0, 0);
        Paint paint2 = new Paint();
        this.mTextBackgroundPaint = paint2;
        paint2.setAntiAlias(false);
        paint2.setARGB(128, 255, 255, 255);
        Paint paint3 = new Paint();
        this.mTextLevelPaint = paint3;
        paint3.setAntiAlias(false);
        paint3.setARGB(192, 255, 0, 0);
        Paint paint4 = new Paint();
        this.mPaint = paint4;
        paint4.setAntiAlias(true);
        paint4.setARGB(255, 255, 255, 255);
        paint4.setStyle(Paint.Style.STROKE);
        Paint paint5 = new Paint();
        this.mCurrentPointPaint = paint5;
        paint5.setAntiAlias(true);
        paint5.setARGB(255, 255, 0, 0);
        paint5.setStyle(Paint.Style.STROKE);
        Paint paint6 = new Paint();
        this.mTargetPaint = paint6;
        paint6.setAntiAlias(false);
        paint6.setARGB(255, 0, 0, 192);
        Paint paint7 = new Paint();
        this.mPathPaint = paint7;
        paint7.setAntiAlias(false);
        paint7.setARGB(255, 0, 96, 255);
        paint7.setStyle(Paint.Style.STROKE);
        this.mTraceCanvas = new Canvas();
        configureTraceBitmap();
        configureDensityDependentFactors();
        Paint paint8 = new Paint();
        this.mSystemGestureExclusionPaint = paint8;
        paint8.setARGB(25, 255, 0, 0);
        paint8.setStyle(Paint.Style.FILL_AND_STROKE);
        Paint paint9 = new Paint();
        this.mSystemGestureExclusionRejectedPaint = paint9;
        paint9.setARGB(25, 0, 0, 255);
        paint9.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mActivePointerId = 0;
        this.mVelocity = VelocityTracker.obtain();
        String str = SystemProperties.get(ALT_STRATEGY_PROPERY_KEY);
        if (str.length() != 0) {
            Log.d(TAG, "Comparing default velocity tracker strategy with " + str);
            this.mAltVelocity = VelocityTracker.obtain(str);
            return;
        }
        this.mAltVelocity = null;
    }

    public void setPrintCoords(boolean z) {
        this.mPrintCoords = z;
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        Insets waterfallInsets = Insets.NONE;
        RoundedCorner roundedCorner = windowInsets.getRoundedCorner(0);
        int radius = roundedCorner != null ? roundedCorner.getRadius() : 0;
        RoundedCorner roundedCorner2 = windowInsets.getRoundedCorner(1);
        if (roundedCorner2 != null) {
            radius = Math.max(radius, roundedCorner2.getRadius());
        }
        if (windowInsets.getDisplayCutout() != null) {
            radius = Math.max(radius, windowInsets.getDisplayCutout().getSafeInsetTop());
            waterfallInsets = windowInsets.getDisplayCutout().getWaterfallInsets();
        }
        this.mHeaderPaddingTop = radius;
        this.mWaterfallInsets = waterfallInsets;
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mTextPaint.getFontMetricsInt(this.mTextMetrics);
        this.mHeaderBottom = (this.mHeaderPaddingTop - this.mTextMetrics.ascent) + this.mTextMetrics.descent + 2;
    }

    private void drawOval(Canvas canvas, float f, float f2, float f3, float f4, float f5, Paint paint) {
        canvas.save(1);
        canvas.rotate((float) ((f5 * 180.0f) / 3.141592653589793d), f, f2);
        float f6 = f4 / 2.0f;
        this.mReusableOvalRect.left = f - f6;
        this.mReusableOvalRect.right = f + f6;
        float f7 = f3 / 2.0f;
        this.mReusableOvalRect.top = f2 - f7;
        this.mReusableOvalRect.bottom = f2 + f7;
        canvas.drawOval(this.mReusableOvalRect, paint);
        canvas.restore();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        PointerLocationView pointerLocationView;
        Canvas canvas2;
        PointerLocationView pointerLocationView2 = this;
        Canvas canvas3 = canvas;
        int size = pointerLocationView2.mPointers.size();
        canvas3.save();
        rotateCanvasToUnrotatedDisplay(canvas);
        canvas3.drawBitmap(pointerLocationView2.mTraceBitmap, 0.0f, 0.0f, (Paint) null);
        canvas3.restore();
        if (!pointerLocationView2.mSystemGestureExclusion.isEmpty()) {
            pointerLocationView2.mSystemGestureExclusionPath.reset();
            pointerLocationView2.mSystemGestureExclusion.getBoundaryPath(pointerLocationView2.mSystemGestureExclusionPath);
            canvas3.drawPath(pointerLocationView2.mSystemGestureExclusionPath, pointerLocationView2.mSystemGestureExclusionPaint);
        }
        if (!pointerLocationView2.mSystemGestureExclusionRejected.isEmpty()) {
            pointerLocationView2.mSystemGestureExclusionPath.reset();
            pointerLocationView2.mSystemGestureExclusionRejected.getBoundaryPath(pointerLocationView2.mSystemGestureExclusionPath);
            canvas3.drawPath(pointerLocationView2.mSystemGestureExclusionPath, pointerLocationView2.mSystemGestureExclusionRejectedPaint);
        }
        drawLabels(canvas);
        canvas3.save();
        rotateCanvasToUnrotatedDisplay(canvas);
        int i = 0;
        while (i < size) {
            PointerState pointerStateValueAt = pointerLocationView2.mPointers.valueAt(i);
            float f = pointerStateValueAt.mCurrentX;
            float f2 = pointerStateValueAt.mCurrentY;
            if (!Float.isNaN(f) && !Float.isNaN(f2)) {
                pointerLocationView2.mPaint.setARGB(255, 255, 64, 128);
                canvas3.drawLine(f, f2, (pointerStateValueAt.mXVelocity * 16.0f) + f, (pointerStateValueAt.mYVelocity * 16.0f) + f2, pointerLocationView2.mPaint);
                if (pointerLocationView2.mAltVelocity != null) {
                    pointerLocationView2.mPaint.setARGB(255, 64, 255, 128);
                    canvas.drawLine(f, f2, (pointerStateValueAt.mAltXVelocity * 16.0f) + f, f2 + (pointerStateValueAt.mAltYVelocity * 16.0f), pointerLocationView2.mPaint);
                }
            }
            if (pointerLocationView2.mCurDown && pointerStateValueAt.mCurDown) {
                canvas.drawLine(0.0f, pointerStateValueAt.mCoords.y, pointerLocationView2.getWidth(), pointerStateValueAt.mCoords.y, pointerLocationView2.mTargetPaint);
                canvas.drawLine(pointerStateValueAt.mCoords.x, -pointerLocationView2.getHeight(), pointerStateValueAt.mCoords.x, Math.max(pointerLocationView2.getHeight(), pointerLocationView2.getWidth()), pointerLocationView2.mTargetPaint);
                int i2 = (int) (pointerStateValueAt.mCoords.pressure * 255.0f);
                int i3 = 255 - i2;
                pointerLocationView2.mPaint.setARGB(255, i2, 255, i3);
                canvas.drawPoint(pointerStateValueAt.mCoords.x, pointerStateValueAt.mCoords.y, pointerLocationView2.mPaint);
                pointerLocationView2.mPaint.setARGB(255, i2, i3, 128);
                pointerLocationView2.drawOval(canvas, pointerStateValueAt.mCoords.x, pointerStateValueAt.mCoords.y, pointerStateValueAt.mCoords.touchMajor, pointerStateValueAt.mCoords.touchMinor, pointerStateValueAt.mCoords.orientation, pointerLocationView2.mPaint);
                pointerLocationView2.mPaint.setARGB(255, i2, 128, i3);
                pointerLocationView2.drawOval(canvas, pointerStateValueAt.mCoords.x, pointerStateValueAt.mCoords.y, pointerStateValueAt.mCoords.toolMajor, pointerStateValueAt.mCoords.toolMinor, pointerStateValueAt.mCoords.orientation, pointerLocationView2.mPaint);
                pointerLocationView = pointerLocationView2;
                float fMax = Math.max(pointerStateValueAt.mCoords.toolMajor * 0.7f, pointerLocationView.mDensity * 24.0f);
                pointerLocationView.mPaint.setARGB(255, i2, 255, 0);
                double d = fMax;
                float fSin = (float) (Math.sin(pointerStateValueAt.mCoords.orientation) * d);
                float f3 = (float) ((-Math.cos(pointerStateValueAt.mCoords.orientation)) * d);
                if (pointerStateValueAt.mToolType == 2 || pointerStateValueAt.mToolType == 4) {
                    canvas.drawLine(pointerStateValueAt.mCoords.x, pointerStateValueAt.mCoords.y, pointerStateValueAt.mCoords.x + fSin, pointerStateValueAt.mCoords.y + f3, pointerLocationView.mPaint);
                    canvas2 = canvas;
                } else {
                    canvas.drawLine(pointerStateValueAt.mCoords.x - fSin, pointerStateValueAt.mCoords.y - f3, pointerStateValueAt.mCoords.x + fSin, pointerStateValueAt.mCoords.y + f3, pointerLocationView.mPaint);
                    canvas2 = canvas;
                }
                float fSin2 = (float) Math.sin(pointerStateValueAt.mCoords.getAxisValue(25));
                canvas2.drawCircle(pointerStateValueAt.mCoords.x + (fSin * fSin2), pointerStateValueAt.mCoords.y + (f3 * fSin2), pointerLocationView.mDensity * 3.0f, pointerLocationView.mPaint);
                if (pointerStateValueAt.mHasBoundingBox) {
                    canvas.drawRect(pointerStateValueAt.mBoundingLeft, pointerStateValueAt.mBoundingTop, pointerStateValueAt.mBoundingRight, pointerStateValueAt.mBoundingBottom, pointerLocationView.mPaint);
                }
            } else {
                pointerLocationView = pointerLocationView2;
            }
            i++;
            canvas3 = canvas;
            pointerLocationView2 = pointerLocationView;
        }
        canvas.restore();
    }

    private void drawLabels(Canvas canvas) {
        float f;
        int width = (getWidth() - this.mWaterfallInsets.left) - this.mWaterfallInsets.right;
        int i = width / 7;
        int i2 = (this.mHeaderPaddingTop - this.mTextMetrics.ascent) + 1;
        int i3 = this.mHeaderBottom;
        canvas.save();
        canvas.translate(this.mWaterfallInsets.left, 0.0f);
        PointerState pointerState = this.mPointers.get(this.mActivePointerId, EMPTY_POINTER_STATE);
        float f2 = i3;
        canvas.drawRect(0.0f, this.mHeaderPaddingTop, i - 1, f2, this.mTextBackgroundPaint);
        float f3 = i2;
        canvas.drawText(this.mText.clear().append("P: ").append(this.mCurNumPointers).append(" / ").append(this.mMaxNumPointers).toString(), 1.0f, f3, this.mTextPaint);
        if ((this.mCurDown && pointerState.mCurDown) || Float.isNaN(pointerState.mCurrentX)) {
            canvas.drawRect(i, this.mHeaderPaddingTop, r13 - 1, f2, this.mTextBackgroundPaint);
            canvas.drawText(this.mText.clear().append("X: ").append(pointerState.mCoords.x, 1).toString(), i + 1, f3, this.mTextPaint);
            canvas.drawRect(i * 2, this.mHeaderPaddingTop, (i * 3) - 1, f2, this.mTextBackgroundPaint);
            canvas.drawText(this.mText.clear().append("Y: ").append(pointerState.mCoords.y, 1).toString(), r13 + 1, f3, this.mTextPaint);
            f = 1.0f;
        } else {
            float f4 = pointerState.mCurrentX - pointerState.mFirstX;
            float f5 = pointerState.mCurrentY - pointerState.mFirstY;
            int i4 = i * 2;
            f = 1.0f;
            canvas.drawRect(i, this.mHeaderPaddingTop, i4 - 1, f2, Math.abs(f4) < ((float) this.mVC.getScaledTouchSlop()) ? this.mTextBackgroundPaint : this.mTextLevelPaint);
            canvas.drawText(this.mText.clear().append("dX: ").append(f4, 1).toString(), i + 1, f3, this.mTextPaint);
            canvas.drawRect(i4, this.mHeaderPaddingTop, (i * 3) - 1, f2, Math.abs(f5) < ((float) this.mVC.getScaledTouchSlop()) ? this.mTextBackgroundPaint : this.mTextLevelPaint);
            canvas.drawText(this.mText.clear().append("dY: ").append(f5, 1).toString(), i4 + 1, f3, this.mTextPaint);
        }
        canvas.drawRect(i * 3, this.mHeaderPaddingTop, r13 - 1, f2, this.mTextBackgroundPaint);
        canvas.drawText(this.mText.clear().append("Xv: ").append(pointerState.mXVelocity, 3).toString(), r12 + 1, f3, this.mTextPaint);
        canvas.drawRect(i * 4, this.mHeaderPaddingTop, r12 - 1, f2, this.mTextBackgroundPaint);
        canvas.drawText(this.mText.clear().append("Yv: ").append(pointerState.mYVelocity, 3).toString(), r13 + 1, f3, this.mTextPaint);
        float f6 = i * 5;
        int i5 = i * 6;
        canvas.drawRect(f6, this.mHeaderPaddingTop, i5 - 1, f2, this.mTextBackgroundPaint);
        float f7 = i;
        canvas.drawRect(f6, this.mHeaderPaddingTop, ((pointerState.mCoords.pressure * f7) + f6) - f, f2, this.mTextLevelPaint);
        canvas.drawText(this.mText.clear().append("Prs: ").append(pointerState.mCoords.pressure, 2).toString(), r12 + 1, f3, this.mTextPaint);
        float f8 = i5;
        canvas.drawRect(f8, this.mHeaderPaddingTop, width, f2, this.mTextBackgroundPaint);
        canvas.drawRect(f8, this.mHeaderPaddingTop, ((pointerState.mCoords.size * f7) + f8) - f, f2, this.mTextLevelPaint);
        canvas.drawText(this.mText.clear().append("Size: ").append(pointerState.mCoords.size, 2).toString(), i5 + 1, f3, this.mTextPaint);
        canvas.restore();
    }

    private void logMotionEvent(String str, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int historySize = motionEvent.getHistorySize();
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < historySize; i++) {
            for (int i2 = 0; i2 < pointerCount; i2++) {
                int pointerId = motionEvent.getPointerId(i2);
                motionEvent.getHistoricalPointerCoords(i2, i, this.mTempCoords);
                logCoords(str, action, i2, this.mTempCoords, pointerId, motionEvent);
            }
        }
        for (int i3 = 0; i3 < pointerCount; i3++) {
            int pointerId2 = motionEvent.getPointerId(i3);
            motionEvent.getPointerCoords(i3, this.mTempCoords);
            logCoords(str, action, i3, this.mTempCoords, pointerId2, motionEvent);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void logCoords(String str, int i, int i2, MotionEvent.PointerCoords pointerCoords, int i3, MotionEvent motionEvent) {
        int toolType = motionEvent.getToolType(i2);
        int buttonState = motionEvent.getButtonState();
        int i4 = i & 255;
        String string = SemImsProfile.RcsProfileType.RCS_PROFILE_TYPE_UP;
        switch (i4) {
            case 0:
                string = "DOWN";
                break;
            case 1:
                break;
            case 2:
                string = "MOVE";
                break;
            case 3:
                string = "CANCEL";
                break;
            case 4:
                string = "OUTSIDE";
                break;
            case 5:
                if (i2 == ((i & 65280) >> 8)) {
                }
                break;
            case 6:
                if (i2 != ((i & 65280) >> 8)) {
                }
                break;
            case 7:
                string = "HOVER MOVE";
                break;
            case 8:
                string = "SCROLL";
                break;
            case 9:
                string = "HOVER ENTER";
                break;
            case 10:
                string = "HOVER EXIT";
                break;
            default:
                string = Integer.toString(i);
                break;
        }
        Log.i(TAG, this.mText.clear().append(str).append(" id ").append(i3 + 1).append(": ").append(string).append(" (").append(pointerCoords.x, 3).append(", ").append(pointerCoords.y, 3).append(") Pressure=").append(pointerCoords.pressure, 3).append(" Size=").append(pointerCoords.size, 3).append(" TouchMajor=").append(pointerCoords.touchMajor, 3).append(" TouchMinor=").append(pointerCoords.touchMinor, 3).append(" ToolMajor=").append(pointerCoords.toolMajor, 3).append(" ToolMinor=").append(pointerCoords.toolMinor, 3).append(" Orientation=").append((float) ((pointerCoords.orientation * 180.0f) / 3.141592653589793d), 1).append("deg").append(" Tilt=").append((float) ((pointerCoords.getAxisValue(25) * 180.0f) / 3.141592653589793d), 1).append("deg").append(" Distance=").append(pointerCoords.getAxisValue(24), 1).append(" VScroll=").append(pointerCoords.getAxisValue(9), 1).append(" HScroll=").append(pointerCoords.getAxisValue(10), 1).append(" BoundingBox=[(").append(motionEvent.getAxisValue(32), 3).append(", ").append(motionEvent.getAxisValue(33), 3).append(NavigationBarInflaterView.KEY_CODE_END).append(", (").append(motionEvent.getAxisValue(34), 3).append(", ").append(motionEvent.getAxisValue(35), 3).append(")]").append(" ToolType=").append(MotionEvent.toolTypeToString(toolType)).append(" ButtonState=").append(MotionEvent.buttonStateToString(buttonState)).toString());
    }

    @Override // android.view.WindowManagerPolicyConstants.PointerEventListener
    public void onPointerEvent(MotionEvent motionEvent) {
        motionEvent.transform(MotionEvent.createRotateMatrix(inverseRotation(motionEvent.getSurfaceRotation()), this.mTraceBitmap.getWidth(), this.mTraceBitmap.getHeight()));
        int action = motionEvent.getAction();
        if (action == 0 || (action & 255) == 5) {
            int i = (action & 65280) >> 8;
            if (action == 0) {
                this.mPointers.clear();
                this.mCurDown = true;
                this.mCurNumPointers = 0;
                this.mMaxNumPointers = 0;
                this.mVelocity.clear();
                this.mTraceCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
                VelocityTracker velocityTracker = this.mAltVelocity;
                if (velocityTracker != null) {
                    velocityTracker.clear();
                }
            }
            int i2 = this.mCurNumPointers + 1;
            this.mCurNumPointers = i2;
            if (this.mMaxNumPointers < i2) {
                this.mMaxNumPointers = i2;
            }
            int pointerId = motionEvent.getPointerId(i);
            PointerState pointerState = this.mPointers.get(pointerId);
            if (pointerState == null) {
                pointerState = new PointerState();
                this.mPointers.put(pointerId, pointerState);
            }
            if (!this.mPointers.contains(this.mActivePointerId) || !this.mPointers.get(this.mActivePointerId).mCurDown) {
                this.mActivePointerId = pointerId;
            }
            pointerState.mCurDown = true;
            InputDevice device = InputDevice.getDevice(motionEvent.getDeviceId());
            pointerState.mHasBoundingBox = (device == null || device.getMotionRange(32) == null) ? false : true;
        }
        int pointerCount = motionEvent.getPointerCount();
        this.mVelocity.addMovement(motionEvent);
        this.mVelocity.computeCurrentVelocity(1);
        VelocityTracker velocityTracker2 = this.mAltVelocity;
        if (velocityTracker2 != null) {
            velocityTracker2.addMovement(motionEvent);
            this.mAltVelocity.computeCurrentVelocity(1);
        }
        int historySize = motionEvent.getHistorySize();
        for (int i3 = 0; i3 < historySize; i3++) {
            for (int i4 = 0; i4 < pointerCount; i4++) {
                int pointerId2 = motionEvent.getPointerId(i4);
                PointerState pointerState2 = this.mCurDown ? this.mPointers.get(pointerId2) : null;
                MotionEvent.PointerCoords pointerCoords = pointerState2 != null ? pointerState2.mCoords : this.mTempCoords;
                motionEvent.getHistoricalPointerCoords(i4, i3, pointerCoords);
                if (this.mPrintCoords) {
                    logCoords(TAG, action, i4, pointerCoords, pointerId2, motionEvent);
                }
                if (pointerState2 != null) {
                    pointerState2.addTrace(pointerCoords.x, pointerCoords.y, true);
                    updateDrawTrace(pointerState2);
                }
            }
        }
        for (int i5 = 0; i5 < pointerCount; i5++) {
            int pointerId3 = motionEvent.getPointerId(i5);
            PointerState pointerState3 = this.mCurDown ? this.mPointers.get(pointerId3) : null;
            MotionEvent.PointerCoords pointerCoords2 = pointerState3 != null ? pointerState3.mCoords : this.mTempCoords;
            motionEvent.getPointerCoords(i5, pointerCoords2);
            if (this.mPrintCoords) {
                logCoords(TAG, action, i5, pointerCoords2, pointerId3, motionEvent);
            }
            if (pointerState3 != null) {
                pointerState3.addTrace(pointerCoords2.x, pointerCoords2.y, false);
                updateDrawTrace(pointerState3);
                pointerState3.mXVelocity = this.mVelocity.getXVelocity(pointerId3);
                pointerState3.mYVelocity = this.mVelocity.getYVelocity(pointerId3);
                VelocityTracker velocityTracker3 = this.mAltVelocity;
                if (velocityTracker3 != null) {
                    pointerState3.mAltXVelocity = velocityTracker3.getXVelocity(pointerId3);
                    pointerState3.mAltYVelocity = this.mAltVelocity.getYVelocity(pointerId3);
                }
                pointerState3.mToolType = motionEvent.getToolType(i5);
                if (pointerState3.mHasBoundingBox) {
                    pointerState3.mBoundingLeft = motionEvent.getAxisValue(32, i5);
                    pointerState3.mBoundingTop = motionEvent.getAxisValue(33, i5);
                    pointerState3.mBoundingRight = motionEvent.getAxisValue(34, i5);
                    pointerState3.mBoundingBottom = motionEvent.getAxisValue(35, i5);
                }
            }
        }
        if (action == 1 || action == 3 || (action & 255) == 6) {
            int i6 = (action & 65280) >> 8;
            int pointerId4 = motionEvent.getPointerId(i6);
            PointerState pointerState4 = this.mPointers.get(pointerId4);
            if (pointerState4 == null) {
                Slog.wtf(TAG, "Could not find pointer id=" + pointerId4 + " in mPointers map, size=" + this.mPointers.size() + " pointerindex=" + i6 + " action=0x" + Integer.toHexString(action));
                return;
            }
            pointerState4.mCurDown = false;
            if (action == 1 || action == 3) {
                this.mCurDown = false;
                this.mCurNumPointers = 0;
            } else {
                this.mCurNumPointers--;
                if (this.mActivePointerId == pointerId4) {
                    this.mActivePointerId = motionEvent.getPointerId(i6 == 0 ? 1 : 0);
                }
                pointerState4.addTrace(Float.NaN, Float.NaN, true);
            }
        }
        invalidate();
    }

    private void updateDrawTrace(PointerState pointerState) {
        this.mPaint.setARGB(255, 128, 255, 255);
        float f = pointerState.mCurrentX;
        float f2 = pointerState.mCurrentY;
        float f3 = pointerState.mPreviousX;
        float f4 = pointerState.mPreviousY;
        if (Float.isNaN(f) || Float.isNaN(f2) || Float.isNaN(f3) || Float.isNaN(f4)) {
            return;
        }
        this.mTraceCanvas.drawLine(f3, f4, f, f2, this.mPathPaint);
        this.mTraceCanvas.drawPoint(f3, f4, pointerState.mPreviousPointIsHistorical ? this.mPaint : this.mCurrentPointPaint);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        onPointerEvent(motionEvent);
        if (motionEvent.getAction() != 0 || isFocused()) {
            return true;
        }
        requestFocus();
        return true;
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        int source = motionEvent.getSource();
        if ((source & 2) != 0) {
            onPointerEvent(motionEvent);
            return true;
        }
        if ((source & 16) != 0) {
            logMotionEvent("Joystick", motionEvent);
            return true;
        }
        if ((source & 8) != 0) {
            logMotionEvent("Position", motionEvent);
            return true;
        }
        logMotionEvent("Generic", motionEvent);
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (shouldLogKey(i)) {
            int repeatCount = keyEvent.getRepeatCount();
            if (repeatCount == 0) {
                Log.i(TAG, "Key Down: " + keyEvent);
                return true;
            }
            Log.i(TAG, "Key Repeat #" + repeatCount + ": " + keyEvent);
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (shouldLogKey(i)) {
            Log.i(TAG, "Key Up: " + keyEvent);
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    private static boolean shouldLogKey(int i) {
        switch (i) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                break;
            default:
                if (KeyEvent.isGamepadButton(i) || KeyEvent.isModifierKey(i)) {
                }
                break;
        }
        return true;
    }

    @Override // android.view.View
    public boolean onTrackballEvent(MotionEvent motionEvent) {
        logMotionEvent("Trackball", motionEvent);
        return true;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIm.registerInputDeviceListener(this, getHandler());
        if (shouldShowSystemGestureExclusion()) {
            try {
                WindowManagerGlobal.getWindowManagerService().registerSystemGestureExclusionListener(this.mSystemGestureExclusionListener, this.mContext.getDisplayId());
                int iSystemGestureExclusionOpacity = systemGestureExclusionOpacity();
                this.mSystemGestureExclusionPaint.setAlpha(iSystemGestureExclusionOpacity);
                this.mSystemGestureExclusionRejectedPaint.setAlpha(iSystemGestureExclusionOpacity);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            this.mSystemGestureExclusion.setEmpty();
        }
        logInputDevices();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIm.unregisterInputDeviceListener(this);
        try {
            WindowManagerGlobal.getWindowManagerService().unregisterSystemGestureExclusionListener(this.mSystemGestureExclusionListener, this.mContext.getDisplayId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (IllegalArgumentException e2) {
            Log.e(TAG, "Failed to unregister window manager callbacks", e2);
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceAdded(int i) {
        logInputDeviceState(i, "Device Added");
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceChanged(int i) {
        logInputDeviceState(i, "Device Changed");
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceRemoved(int i) {
        logInputDeviceState(i, "Device Removed");
    }

    private void logInputDevices() {
        for (int i : InputDevice.getDeviceIds()) {
            logInputDeviceState(i, "Device Enumerated");
        }
    }

    private void logInputDeviceState(int i, String str) {
        InputDevice inputDevice = this.mIm.getInputDevice(i);
        if (inputDevice != null) {
            Log.i(TAG, str + ": " + inputDevice);
            return;
        }
        Log.i(TAG, str + ": " + i);
    }

    private static boolean shouldShowSystemGestureExclusion() {
        return systemGestureExclusionOpacity() > 0;
    }

    private static int systemGestureExclusionOpacity() {
        int i = SystemProperties.getInt(GESTURE_EXCLUSION_PROP, 0);
        if (i < 0 || i > 255) {
            return 0;
        }
        return i;
    }

    private static final class FasterStringBuilder {
        private char[] mChars = new char[64];
        private int mLength;

        public FasterStringBuilder clear() {
            this.mLength = 0;
            return this;
        }

        public FasterStringBuilder append(String str) {
            int length = str.length();
            str.getChars(0, length, this.mChars, reserve(length));
            this.mLength += length;
            return this;
        }

        public FasterStringBuilder append(int i) {
            return append(i, 0);
        }

        public FasterStringBuilder append(int i, int i2) {
            boolean z = i < 0;
            if (z && (i = -i) < 0) {
                append("-2147483648");
                return this;
            }
            int iReserve = reserve(11);
            char[] cArr = this.mChars;
            if (i == 0) {
                cArr[iReserve] = '0';
                this.mLength++;
                return this;
            }
            if (z) {
                cArr[iReserve] = '-';
                iReserve++;
            }
            int i3 = 1000000000;
            int i4 = 10;
            while (i < i3) {
                i3 /= 10;
                i4--;
                if (i4 < i2) {
                    cArr[iReserve] = '0';
                    iReserve++;
                }
            }
            while (true) {
                int i5 = i / i3;
                i -= i5 * i3;
                i3 /= 10;
                int i6 = iReserve + 1;
                cArr[iReserve] = (char) (i5 + 48);
                if (i3 == 0) {
                    this.mLength = i6;
                    return this;
                }
                iReserve = i6;
            }
        }

        public FasterStringBuilder append(float f, int i) {
            int i2 = 1;
            for (int i3 = 0; i3 < i; i3++) {
                i2 *= 10;
            }
            float f2 = i2;
            float fRint = (float) (Math.rint(f * f2) / i2);
            int i4 = (int) fRint;
            if (i4 == 0 && fRint < 0.0f) {
                append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            }
            append(i4);
            if (i != 0) {
                append(MediaMetrics.SEPARATOR);
                double dAbs = Math.abs(fRint);
                append((int) (((float) (dAbs - Math.floor(dAbs))) * f2), i);
            }
            return this;
        }

        public String toString() {
            return new String(this.mChars, 0, this.mLength);
        }

        private int reserve(int i) {
            int i2 = this.mLength;
            int i3 = i + i2;
            char[] cArr = this.mChars;
            int length = cArr.length;
            if (i3 > length) {
                char[] cArr2 = new char[length * 2];
                System.arraycopy(cArr, 0, cArr2, 0, i2);
                this.mChars = cArr2;
            }
            return i2;
        }
    }

    /* renamed from: com.android.internal.widget.PointerLocationView$1, reason: invalid class name */
    class AnonymousClass1 extends ISystemGestureExclusionListener.Stub {
        AnonymousClass1() {
        }

        @Override // android.view.ISystemGestureExclusionListener
        public void onSystemGestureExclusionChanged(int i, Region region, Region region2) {
            final Region regionObtain = Region.obtain(region);
            final Region regionObtain2 = Region.obtain();
            if (region2 != null) {
                regionObtain2.set(region2);
                regionObtain2.op(regionObtain, Region.Op.DIFFERENCE);
            }
            Handler handler = PointerLocationView.this.getHandler();
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.android.internal.widget.PointerLocationView$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSystemGestureExclusionChanged$0(regionObtain, regionObtain2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSystemGestureExclusionChanged$0(Region region, Region region2) {
            PointerLocationView.this.mSystemGestureExclusion.set(region);
            PointerLocationView.this.mSystemGestureExclusionRejected.set(region2);
            region.recycle();
            PointerLocationView.this.invalidate();
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        configureTraceBitmap();
        configureDensityDependentFactors();
    }

    private void configureDensityDependentFactors() {
        float f = getResources().getDisplayMetrics().density;
        this.mDensity = f;
        this.mTextPaint.setTextSize(f * 10.0f);
        this.mPaint.setStrokeWidth(this.mDensity * 1.0f);
        this.mCurrentPointPaint.setStrokeWidth(this.mDensity * 1.0f);
        this.mPathPaint.setStrokeWidth(this.mDensity * 1.0f);
    }

    private void configureTraceBitmap() {
        Display display = this.mContext.getDisplay();
        boolean z = true;
        if (display.getRotation() != 1 && display.getRotation() != 3) {
            z = false;
        }
        int height = z ? display.getHeight() : display.getWidth();
        int width = z ? display.getWidth() : display.getHeight();
        Bitmap bitmap = this.mTraceBitmap;
        if (bitmap != null && bitmap.getWidth() == height && this.mTraceBitmap.getHeight() == width) {
            return;
        }
        if (height <= 0 || width <= 0) {
            Slog.w(TAG, "Ignoring configuration: invalid display size: " + height + "x" + width);
            height = 100;
            width = 100;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(height, width, Bitmap.Config.ARGB_8888);
        this.mTraceBitmap = bitmapCreateBitmap;
        this.mTraceCanvas.setBitmap(bitmapCreateBitmap);
    }

    private static int inverseRotation(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 3;
        }
        if (i == 2) {
            return 2;
        }
        if (i == 3) {
            return 1;
        }
        Slog.e(TAG, "Received unexpected surface rotation: " + i);
        return 0;
    }

    private void rotateCanvasToUnrotatedDisplay(Canvas canvas) {
        int iInverseRotation = inverseRotation(this.mContext.getDisplay().getRotation());
        if (iInverseRotation == 1) {
            canvas.rotate(90.0f);
            canvas.translate(0.0f, -this.mTraceBitmap.getHeight());
        } else if (iInverseRotation == 2) {
            canvas.rotate(180.0f);
            canvas.translate(-this.mTraceBitmap.getWidth(), -this.mTraceBitmap.getHeight());
        } else {
            if (iInverseRotation != 3) {
                return;
            }
            canvas.rotate(270.0f);
            canvas.translate(-this.mTraceBitmap.getWidth(), 0.0f);
        }
    }
}
