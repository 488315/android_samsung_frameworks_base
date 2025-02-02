package com.samsung.android.biometrics.app.setting.face;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.PathInterpolator;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.PathParser;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class FaceRegisterEffectView extends View {
    private final int COLOR_BACKGROUND;
    private final int COLOR_CHECK_ICON;
    private final int COLOR_GREEN_MASK;
    private final int COLOR_INNER_GUIDE_LINE;
    private final int COLOR_PREVIEW_MASK;
    private final int COLOR_PROGRESS_BASE_LINE;
    private final int COLOR_PROGRESS_LINE;
    private final float WIDTH_ERROR_PREVIEW_AREA_PERCENT;
    private final float WIDTH_INNER_CIRCLE_AREA_PERCENT;
    private float mCheckIconMoveDp;
    private long mCompletionTimestamp;
    private int mErrorState;
    private long mErrorTimestamp;
    private boolean mIsErrorFinished;
    private boolean mIsErrorStarted;
    private boolean mIsPreviewMask;
    private boolean mIsPreviewStarting;
    private boolean mIsScanning;
    private final Path mPath;
    private final Path mPathCheckIcon;
    private final Paint mPntBackground;
    private final Paint mPntCheckIcon;
    private final Paint mPntGreenMask;
    private final Paint mPntInnerCircle;
    private final Paint mPntPreviewMask;
    private final Paint mPntProgressBaseLine;
    private final Paint mPntProgressLine;
    private final Paint mPntScanLine;
    private final Paint mPntTraceLine;
    private float mPreviewExpandSize;
    private long mPreviewMaskTimestamp;
    private float mProgress;
    private final Matrix mRotateMatrix;
    private final ArrayList<Path> mScanPathList;
    private long mScanStartTimestamp;
    private long mStartingTimestamp;
    private int mState;
    private float mStrokeWidth;
    private float mTargetPreviewExpandSize;
    private float mTargetProgress;
    private final ArrayList<Path> mTracePathList;
    private static final PathInterpolator INTP_STARTING_ANIMATION = new PathInterpolator(0.33f, 0.0f, 0.2f, 1.0f);
    private static final PathInterpolator INTP_COMPLETED_LINE_SCALE = new PathInterpolator(0.45f, 0.2f, 0.67f, 1.0f);
    private static final PathInterpolator INTP_COMPLETED_LINE_MOVING = new PathInterpolator(0.5f, 0.0f, 0.5f, 1.0f);
    private static final PathInterpolator INTP_COMPLETED_ROTATION = new PathInterpolator(0.91f, 0.0f, 0.21f, 1.0f);
    private static final PathInterpolator INTP_CHECK_ICON_MOVING = new PathInterpolator(0.17f, 0.17f, 0.4f, 1.0f);
    private static final PathInterpolator INTP_ERROR_STARTING = new PathInterpolator(0.17f, 0.17f, 0.1f, 1.0f);
    private static final PathInterpolator INTP_ERROR_MOVING = new PathInterpolator(0.33f, 0.0f, 0.3f, 0.1f);

    private static class FaceScanEffectItem {
        private float angle;

        /* renamed from: ex */
        private int f23ex;

        /* renamed from: sx */
        private int f24sx;

        public FaceScanEffectItem(PathInterpolator pathInterpolator, int i, int i2) {
            this.angle = 90.0f;
            float f = i2;
            int interpolation = (int) (pathInterpolator.getInterpolation(i / f) * f);
            this.f24sx = interpolation;
            int i3 = i2 - interpolation;
            this.f23ex = i3;
            if (interpolation > i3) {
                this.f23ex = interpolation;
                this.f24sx = i3;
                this.angle = 270.0f;
            }
        }
    }

    /* renamed from: -$$Nest$minitializeGuide, reason: not valid java name */
    static void m80$$Nest$minitializeGuide(FaceRegisterEffectView faceRegisterEffectView, int i, int i2) {
        faceRegisterEffectView.getClass();
        float min = Math.min(i, i2);
        faceRegisterEffectView.mStrokeWidth = (2.5f * min) / 100.0f;
        faceRegisterEffectView.mPntBackground.setColor(faceRegisterEffectView.COLOR_BACKGROUND);
        faceRegisterEffectView.mPntBackground.setStyle(Paint.Style.FILL);
        faceRegisterEffectView.mPntPreviewMask.setColor(faceRegisterEffectView.COLOR_PREVIEW_MASK);
        faceRegisterEffectView.mPntPreviewMask.setStyle(Paint.Style.FILL);
        faceRegisterEffectView.mPntInnerCircle.setColor(faceRegisterEffectView.COLOR_INNER_GUIDE_LINE);
        faceRegisterEffectView.mPntInnerCircle.setStyle(Paint.Style.STROKE);
        faceRegisterEffectView.mPntInnerCircle.setStrokeWidth((min * 0.5f) / 100.0f);
        faceRegisterEffectView.mPntProgressBaseLine.setColor(faceRegisterEffectView.COLOR_PROGRESS_BASE_LINE);
        faceRegisterEffectView.mPntProgressBaseLine.setMaskFilter(new BlurMaskFilter(1.0f, BlurMaskFilter.Blur.NORMAL));
        faceRegisterEffectView.mPntProgressBaseLine.setStyle(Paint.Style.STROKE);
        faceRegisterEffectView.mPntProgressBaseLine.setStrokeWidth(faceRegisterEffectView.mStrokeWidth);
        faceRegisterEffectView.mPntProgressLine.setMaskFilter(new BlurMaskFilter(1.0f, BlurMaskFilter.Blur.NORMAL));
        faceRegisterEffectView.mPntProgressLine.setColor(faceRegisterEffectView.COLOR_PROGRESS_LINE);
        faceRegisterEffectView.mPntProgressLine.setStyle(Paint.Style.STROKE);
        faceRegisterEffectView.mPntProgressLine.setStrokeWidth(faceRegisterEffectView.mStrokeWidth);
        faceRegisterEffectView.mPntProgressLine.setStrokeCap(Paint.Cap.ROUND);
        faceRegisterEffectView.mPntGreenMask.setColor(faceRegisterEffectView.COLOR_GREEN_MASK);
        faceRegisterEffectView.mPntCheckIcon.setMaskFilter(new BlurMaskFilter(1.0f, BlurMaskFilter.Blur.NORMAL));
        faceRegisterEffectView.mPntCheckIcon.setColor(faceRegisterEffectView.COLOR_CHECK_ICON);
        faceRegisterEffectView.mPntCheckIcon.setStyle(Paint.Style.STROKE);
        faceRegisterEffectView.mPntCheckIcon.setStrokeWidth(faceRegisterEffectView.mStrokeWidth);
        faceRegisterEffectView.mPntCheckIcon.setStrokeCap(Paint.Cap.ROUND);
        faceRegisterEffectView.mCheckIconMoveDp = TypedValue.applyDimension(1, 3.0f, faceRegisterEffectView.getResources().getDisplayMetrics());
        float f = i;
        float f2 = (f / 0.76f) * 0.28f;
        float f3 = f2 / 45.0f;
        float f4 = (f2 * 2.0f) / 3.0f;
        float f5 = (f / 2.0f) - f4;
        float f6 = (i2 / 2.0f) - f4;
        Matrix matrix = new Matrix();
        matrix.setScale(f3, f3);
        matrix.postTranslate(f5, f6);
        faceRegisterEffectView.mPathCheckIcon.transform(matrix);
    }

    /* renamed from: -$$Nest$minitializeScan, reason: not valid java name */
    static void m81$$Nest$minitializeScan(FaceRegisterEffectView faceRegisterEffectView, int i, int i2) {
        float f = faceRegisterEffectView.mStrokeWidth;
        float f2 = faceRegisterEffectView.WIDTH_INNER_CIRCLE_AREA_PERCENT;
        int i3 = (int) (((i * 0.9f) - f) * f2);
        int i4 = (int) (((i2 * 0.9f) - f) * f2);
        faceRegisterEffectView.mPntScanLine.setColor(-1);
        faceRegisterEffectView.mPntScanLine.setMaskFilter(new BlurMaskFilter(1.0f, BlurMaskFilter.Blur.NORMAL));
        faceRegisterEffectView.mPntTraceLine.setColor(-3088176);
        PathInterpolator pathInterpolator = new PathInterpolator(0.45f, 0.0f, 0.55f, 1.0f);
        float f3 = i3;
        int i5 = (int) ((1.5f * f3) / 100.0f);
        int i6 = (int) ((3.0f * f3) / 100.0f);
        int max = Math.max(1, i6 / 5);
        Matrix matrix = new Matrix();
        float f4 = i4;
        matrix.setRotate(90.0f, f3 / 2.0f, f4 / 2.0f);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        int i7 = 0;
        while (true) {
            int i8 = i3 + i6;
            if (i7 >= i8) {
                faceRegisterEffectView.mScanPathList.addAll(arrayList);
                faceRegisterEffectView.mScanPathList.addAll(arrayList2);
                faceRegisterEffectView.mTracePathList.addAll(arrayList3);
                faceRegisterEffectView.mTracePathList.addAll(arrayList4);
                faceRegisterEffectView.stopScanEffect();
                return;
            }
            FaceScanEffectItem faceScanEffectItem = new FaceScanEffectItem(pathInterpolator, Math.min(i3, i7), i3);
            ArrayList arrayList5 = arrayList4;
            FaceScanEffectItem faceScanEffectItem2 = new FaceScanEffectItem(pathInterpolator, Math.max(0, i7 - i5), i3);
            FaceScanEffectItem faceScanEffectItem3 = new FaceScanEffectItem(pathInterpolator, Math.max(0, i7 - i6), i3);
            float abs = Math.abs(((i7 * 50) / i8) - 25.0f);
            Path path = new Path();
            int i9 = i7;
            ArrayList arrayList6 = arrayList3;
            path.arcTo(faceScanEffectItem.f24sx, 0.0f, faceScanEffectItem.f23ex, f4, faceScanEffectItem.angle + abs, 180.0f, false);
            path.arcTo(faceScanEffectItem2.f24sx, 0.0f, faceScanEffectItem2.f23ex, f4, faceScanEffectItem2.angle + abs + 180.0f, -180.0f, false);
            path.close();
            arrayList.add(path);
            Path path2 = new Path(path);
            path2.transform(matrix);
            arrayList2.add(path2);
            Path path3 = new Path();
            path3.arcTo(faceScanEffectItem.f24sx, 0.0f, faceScanEffectItem.f23ex, f4, faceScanEffectItem.angle + abs, 180.0f, false);
            path3.arcTo(faceScanEffectItem3.f24sx, 0.0f, faceScanEffectItem3.f23ex, f4, faceScanEffectItem3.angle + abs + 180.0f, -180.0f, false);
            path3.close();
            arrayList6.add(path3);
            Path path4 = new Path(path3);
            path4.transform(matrix);
            arrayList5.add(path4);
            i7 = i9 + max;
            arrayList4 = arrayList5;
            arrayList3 = arrayList6;
            i5 = i5;
        }
    }

    public FaceRegisterEffectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mState = 0;
        this.mPath = new Path();
        this.mStrokeWidth = 0.0f;
        this.mRotateMatrix = new Matrix();
        this.mPntBackground = new Paint();
        this.mPntPreviewMask = new Paint();
        this.mPntInnerCircle = new Paint(1);
        this.mPntProgressBaseLine = new Paint();
        this.mPntProgressLine = new Paint();
        this.mPntGreenMask = new Paint();
        this.mPathCheckIcon = PathParser.createPathFromPathData("M42,11l-20.3383,21l-13.6617,-14.1065");
        this.mPntCheckIcon = new Paint();
        this.mStartingTimestamp = 0L;
        this.mPreviewMaskTimestamp = 0L;
        this.mErrorTimestamp = 0L;
        this.mCompletionTimestamp = 0L;
        this.mProgress = 0.0f;
        this.mTargetProgress = 0.0f;
        this.mPreviewExpandSize = 0.0f;
        this.mTargetPreviewExpandSize = 0.0f;
        this.mCheckIconMoveDp = 0.0f;
        this.mIsPreviewMask = false;
        this.mIsPreviewStarting = false;
        this.mIsErrorStarted = false;
        this.mErrorState = 0;
        this.mIsErrorFinished = false;
        this.mPntScanLine = new Paint(1);
        this.mPntTraceLine = new Paint();
        this.mScanPathList = new ArrayList<>();
        this.mTracePathList = new ArrayList<>();
        this.mIsScanning = false;
        this.mScanStartTimestamp = 0L;
        this.WIDTH_INNER_CIRCLE_AREA_PERCENT = getResources().getFloat(R.dimen.face_enroll_preview_guide_line_size);
        this.WIDTH_ERROR_PREVIEW_AREA_PERCENT = getResources().getFloat(R.dimen.face_enroll_preview_error_size);
        int i = ContextCompat.$r8$clinit;
        this.COLOR_BACKGROUND = context.getColor(R.color.intelligent_enroll_background_color);
        this.COLOR_PREVIEW_MASK = context.getColor(R.color.face_preview_init_color);
        this.COLOR_INNER_GUIDE_LINE = context.getColor(R.color.face_preview_guideline_color);
        this.COLOR_PROGRESS_BASE_LINE = context.getColor(R.color.face_enroll_progress_background_color);
        this.COLOR_PROGRESS_LINE = context.getColor(R.color.face_enroll_progress_color);
        this.COLOR_GREEN_MASK = context.getColor(R.color.face_enroll_progress_color);
        this.COLOR_CHECK_ICON = context.getColor(R.color.face_enroll_complete_check_color);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.samsung.android.biometrics.app.setting.face.FaceRegisterEffectView.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                FaceRegisterEffectView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                FaceRegisterEffectView faceRegisterEffectView = FaceRegisterEffectView.this;
                FaceRegisterEffectView.m80$$Nest$minitializeGuide(faceRegisterEffectView, faceRegisterEffectView.getWidth(), FaceRegisterEffectView.this.getHeight());
                FaceRegisterEffectView faceRegisterEffectView2 = FaceRegisterEffectView.this;
                FaceRegisterEffectView.m81$$Nest$minitializeScan(faceRegisterEffectView2, faceRegisterEffectView2.getWidth(), FaceRegisterEffectView.this.getHeight());
                FaceRegisterEffectView.this.invalidate();
            }
        });
    }

    private void drawBackground(Canvas canvas, float f, float f2) {
        float min = Math.min(f, f2) / 2.0f;
        if (Math.abs(this.mPreviewExpandSize - this.mTargetPreviewExpandSize) > 0.01f) {
            this.mPreviewExpandSize += this.mTargetPreviewExpandSize < 0.0f ? -0.005f : 0.005f;
        } else {
            this.mPreviewExpandSize = this.mTargetPreviewExpandSize;
        }
        if (Utils.Config.FEATURE_JDM_HAL) {
            this.mPreviewExpandSize = -0.05f;
        }
        float f3 = (this.mPreviewExpandSize * min) + min;
        canvas.save();
        this.mPath.reset();
        this.mPath.addCircle(getWidth() / 2.0f, getHeight() / 2.0f, f3, Path.Direction.CW);
        canvas.clipOutPath(this.mPath);
        canvas.drawRect(0.0f, 0.0f, getWidth(), getHeight(), this.mPntBackground);
        canvas.restore();
    }

    private void drawCompletedEffect(Canvas canvas, float f, float f2) {
        if (this.mState != 3) {
            return;
        }
        float timePos = getTimePos(1, this.mCompletionTimestamp, 600L);
        float interpolation = INTP_COMPLETED_LINE_MOVING.getInterpolation(timePos);
        float interpolation2 = INTP_COMPLETED_LINE_SCALE.getInterpolation(getTimePos(1, this.mCompletionTimestamp, 150L));
        float f3 = f * 0.1f * interpolation2;
        float f4 = 0.1f * f2 * interpolation2;
        canvas.save();
        canvas.translate((-f3) / 2.0f, (-f4) / 2.0f);
        float f5 = f3 + f;
        float f6 = f2 + f4;
        float f7 = interpolation * f5;
        float f8 = f5 - f7;
        this.mRotateMatrix.setRotate((INTP_COMPLETED_ROTATION.getInterpolation(timePos) * 120.0f) - 60.0f, f5 / 2.0f, f6 / 2.0f);
        this.mPath.reset();
        this.mPath.addOval(Math.min(f7, f8), 0.0f, Math.max(f7, f8), f6, Path.Direction.CW);
        this.mPath.transform(this.mRotateMatrix);
        canvas.drawPath(this.mPath, this.mPntProgressLine);
        canvas.restore();
        boolean z = timePos < 1.0f;
        float min = Math.min(f, f2) / 2.0f;
        float timePos2 = getTimePos(1, this.mCompletionTimestamp, 150L);
        this.mPntGreenMask.setAlpha((int) (57.0f * timePos2));
        canvas.drawCircle(f / 2.0f, f2 / 2.0f, min, this.mPntGreenMask);
        boolean z2 = z | (timePos2 < 1.0f);
        float timePos3 = getTimePos(2, this.mCompletionTimestamp, 400L);
        float interpolation3 = INTP_CHECK_ICON_MOVING.getInterpolation(timePos3 > 1.0f ? 2.0f - timePos3 : timePos3) * this.mCheckIconMoveDp;
        canvas.save();
        canvas.translate(0.0f, interpolation3);
        canvas.drawPath(this.mPathCheckIcon, this.mPntCheckIcon);
        canvas.restore();
        if (!z2 && !(timePos3 < 2.0f)) {
            this.mState = 4;
        }
    }

    private void drawPreviewMask(Canvas canvas, float f, float f2) {
        if (Utils.Config.FEATURE_FACE_HAL) {
            return;
        }
        float min = Math.min(f, f2) / 2.0f;
        boolean z = this.mIsPreviewMask;
        if (z || this.mIsPreviewStarting) {
            if (Utils.Config.FEATURE_JDM_HAL) {
                min -= 0.05f * min;
            }
            int i = 255;
            if (z) {
                canvas.drawCircle(f / 2.0f, f2 / 2.0f, min, this.mPntBackground);
                if ((SystemClock.elapsedRealtime() - this.mStartingTimestamp) - 1000 <= 400) {
                    i = (int) ((Math.max(0L, r7) * 255.0f) / 400.0f);
                }
            } else {
                if (SystemClock.elapsedRealtime() - this.mPreviewMaskTimestamp <= 250) {
                    i = 255 - ((int) ((Math.max(0L, r7) * 255.0f) / 250.0f));
                } else {
                    i = 0;
                    this.mIsPreviewStarting = false;
                }
            }
            this.mPntPreviewMask.setAlpha(i);
            canvas.drawCircle(f / 2.0f, f2 / 2.0f, min, this.mPntPreviewMask);
        }
    }

    private float getErrorSizeDiff(float f) {
        if (!this.mIsErrorStarted) {
            return 0.0f;
        }
        int i = this.mErrorState;
        if (i == 0) {
            return INTP_ERROR_STARTING.getInterpolation(Math.min(450L, Math.max(0L, SystemClock.elapsedRealtime() - this.mErrorTimestamp)) / 450.0f) * f * 0.05f;
        }
        if (i == 1) {
            return f * 0.05f;
        }
        if (i != 2) {
            return 0.0f;
        }
        long min = Math.min(2000L, Math.max(0L, SystemClock.elapsedRealtime() - this.mErrorTimestamp));
        int i2 = (int) (min / 1000);
        long j = min % 1000;
        if (i2 % 2 != 0) {
            j = 1000 - j;
        }
        return (1.0f - INTP_ERROR_MOVING.getInterpolation(j / 1000)) * f * 0.05f;
    }

    private static float getTimePos(int i, long j, long j2) {
        return Math.min(i, Math.max(0.0f, (SystemClock.elapsedRealtime() - j) / j2));
    }

    @Override // android.view.View
    protected final void onDraw(Canvas canvas) {
        int i;
        int i2;
        int i3;
        Canvas canvas2 = canvas;
        float width = getWidth();
        float height = getHeight();
        if (this.mState == 0) {
            canvas.drawRect(0.0f, 0.0f, width, height, this.mPntBackground);
            return;
        }
        float min = Math.min(width, height) * 0.1f;
        float f = this.mStrokeWidth;
        float f2 = (width - f) - min;
        float f3 = (height - f) - min;
        if (Utils.Config.FEATURE_JDM_HAL) {
            drawBackground(canvas2, width, height);
            drawPreviewMask(canvas2, width, height);
            float f4 = this.mTargetProgress;
            this.mProgress = f4;
            if (f4 == 100.0f) {
                float f5 = (this.mStrokeWidth + min) / 2.0f;
                canvas2.translate(f5, f5);
                drawCompletedEffect(canvas2, f2, f3);
            }
            i2 = 1;
        } else {
            drawBackground(canvas2, f2, f3);
            canvas.save();
            float f6 = (this.mStrokeWidth + min) / 2.0f;
            canvas2.translate(f6, f6);
            drawPreviewMask(canvas2, f2, f3);
            int i4 = this.mState;
            int i5 = 255;
            if (i4 == 1 || (i4 == 2 && this.mIsScanning && (!this.mIsErrorStarted || this.mIsErrorFinished))) {
                float min2 = Math.min(f2, f3) / 2.0f;
                if (this.mState == 1) {
                    if ((SystemClock.elapsedRealtime() - this.mStartingTimestamp) - 1000 <= 400) {
                        i = (int) ((Math.max(0L, r5) * 255.0f) / 400.0f);
                        this.mPntInnerCircle.setAlpha(i);
                        canvas2.drawCircle(f2 / 2.0f, f3 / 2.0f, min2 * this.WIDTH_INNER_CIRCLE_AREA_PERCENT, this.mPntInnerCircle);
                    }
                }
                i = 255;
                this.mPntInnerCircle.setAlpha(i);
                canvas2.drawCircle(f2 / 2.0f, f3 / 2.0f, min2 * this.WIDTH_INNER_CIRCLE_AREA_PERCENT, this.mPntInnerCircle);
            }
            int i6 = this.mState;
            if (i6 != 3 && i6 != 4) {
                float min3 = Math.min(f2, f3) / 2.0f;
                if (this.mState == 1) {
                    long elapsedRealtime = SystemClock.elapsedRealtime() - this.mStartingTimestamp;
                    if (elapsedRealtime < 600) {
                        min3 = (INTP_STARTING_ANIMATION.getInterpolation(elapsedRealtime / 600) * min3 * 0.74f) + (0.3f * min3);
                    } else if (elapsedRealtime < 1200) {
                        min3 = (1.04f * min3) - ((INTP_STARTING_ANIMATION.getInterpolation((elapsedRealtime - 600) / 600) * min3) * 0.04f);
                    }
                    i5 = (int) (Math.min(100L, Math.max(0L, elapsedRealtime)) * 2.55f);
                }
                this.mPntProgressBaseLine.setAlpha(i5);
                canvas2 = canvas;
                canvas2.drawCircle(f2 / 2.0f, f3 / 2.0f, min3 + getErrorSizeDiff(min3), this.mPntProgressBaseLine);
            }
            int i7 = this.mState;
            if (i7 == 4) {
                i2 = 1;
            } else if (i7 == 1 || i7 == 3) {
                i2 = 1;
            } else {
                float f7 = (this.mTargetProgress - this.mProgress) / 10.0f;
                float min4 = f7 < 0.0f ? Math.min(f7, -0.2f) : Math.max(f7, 0.2f);
                float f8 = Math.abs(min4) <= 0.2f ? this.mTargetProgress : min4 + this.mProgress;
                this.mProgress = f8;
                float f9 = (f8 * 360.0f) / 100.0f;
                float errorSizeDiff = getErrorSizeDiff(Math.min(f2, f3)) / 2.0f;
                float f10 = -errorSizeDiff;
                i2 = 1;
                canvas.drawArc(f10, f10, f2 + errorSizeDiff, f3 + errorSizeDiff, -90.0f, f9, false, this.mPntProgressLine);
            }
            drawCompletedEffect(canvas2, f2, f3);
            if (this.mIsScanning && (!this.mIsErrorStarted || this.mIsErrorFinished)) {
                if (this.mScanPathList.size() == 0 || this.mScanPathList.size() != this.mTracePathList.size()) {
                    Log.w("BSS_FaceRegisterEffectView", "Path is not ready (" + this.mScanPathList.size() + ", " + this.mTracePathList.size() + ")");
                } else {
                    long elapsedRealtime2 = SystemClock.elapsedRealtime();
                    float min5 = Math.min(f2, f3);
                    canvas.save();
                    float f11 = ((1.0f - this.WIDTH_INNER_CIRCLE_AREA_PERCENT) * min5) / 2.0f;
                    canvas2.translate(f11, f11);
                    int size = this.mTracePathList.size();
                    int i8 = ((int) (((elapsedRealtime2 - this.mScanStartTimestamp) * size) / 1800)) % size;
                    int i9 = 50;
                    int i10 = i8;
                    while (i10 >= i8 - 50) {
                        this.mPntTraceLine.setAlpha(Math.max(0, i9));
                        if (i10 >= 0) {
                            canvas2.drawPath(this.mTracePathList.get((i10 + size) % size), this.mPntTraceLine);
                        }
                        i10--;
                        i9--;
                    }
                    canvas2.drawPath(this.mScanPathList.get(i8), this.mPntScanLine);
                    canvas.restore();
                }
            }
            canvas.restore();
        }
        if (this.mState == i2 && getTimePos(i2, this.mStartingTimestamp, 1400L) == 1.0f) {
            i3 = 2;
            this.mState = 2;
            startScanEffect();
            if (this.mIsPreviewStarting) {
                setPreviewState();
            }
        } else {
            i3 = 2;
        }
        if (this.mState == i3 && this.mProgress == 100.0f) {
            this.mCompletionTimestamp = SystemClock.elapsedRealtime();
            this.mState = 3;
            this.mIsScanning = false;
        }
        if (this.mIsErrorStarted) {
            long elapsedRealtime3 = SystemClock.elapsedRealtime();
            int i11 = this.mErrorState;
            if (i11 == 0 && elapsedRealtime3 - this.mErrorTimestamp > 450) {
                this.mErrorTimestamp = elapsedRealtime3;
                this.mErrorState = i2;
            } else if (i11 == i2 && elapsedRealtime3 - this.mErrorTimestamp > 150) {
                this.mErrorTimestamp = elapsedRealtime3;
                this.mErrorState = 2;
            } else if (i11 == 2) {
                long j = this.mErrorTimestamp;
                long j2 = elapsedRealtime3 - j;
                if (j2 > 1000) {
                    if (this.mIsErrorFinished) {
                        this.mIsErrorStarted = false;
                        this.mScanStartTimestamp = SystemClock.elapsedRealtime();
                    } else if (j2 > 2000) {
                        this.mErrorTimestamp = j + 2000;
                    }
                }
            }
        }
        int i12 = this.mState;
        if (i12 == i2 || i12 == 3 || this.mIsScanning || this.mIsErrorStarted || this.mProgress < this.mTargetProgress) {
            invalidate();
        }
    }

    public final void setErrorState() {
        if (this.mIsErrorStarted) {
            return;
        }
        this.mErrorState = 0;
        this.mIsErrorStarted = true;
        this.mIsErrorFinished = false;
        this.mTargetPreviewExpandSize = -(1.0f - this.WIDTH_ERROR_PREVIEW_AREA_PERCENT);
        this.mErrorTimestamp = SystemClock.elapsedRealtime();
        invalidate();
    }

    public final void setPreviewState() {
        if (this.mIsPreviewMask) {
            this.mIsPreviewStarting = true;
            if (this.mState != 1) {
                this.mIsPreviewMask = false;
                this.mPreviewMaskTimestamp = SystemClock.elapsedRealtime();
            }
        }
        invalidate();
    }

    public void setProgress(int i) {
        if (this.mState != 1) {
            this.mState = 2;
            if (!this.mIsScanning) {
                startScanEffect();
            }
        }
        float f = i;
        this.mTargetProgress = f;
        if (f == 0.0f) {
            this.mProgress = 0.0f;
        }
        this.mTargetPreviewExpandSize = 0.0f;
        this.mIsErrorFinished = true;
        invalidate();
    }

    public final void showStartingAnimation() {
        this.mState = 1;
        this.mStartingTimestamp = SystemClock.elapsedRealtime();
        this.mIsPreviewMask = true;
        stopScanEffect();
        invalidate();
    }

    public final void startScanEffect() {
        this.mScanStartTimestamp = SystemClock.elapsedRealtime();
        this.mIsScanning = true;
        if (Utils.Config.FEATURE_FACE_HAL) {
            this.mPntPreviewMask.setColor(this.COLOR_PREVIEW_MASK);
            this.mPntPreviewMask.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        }
        invalidate();
    }

    public final void stopScanEffect() {
        this.mIsScanning = false;
        this.mIsErrorStarted = false;
        if (Utils.Config.FEATURE_FACE_HAL && this.mState != 1) {
            this.mPntPreviewMask.setColor(this.COLOR_BACKGROUND);
            this.mPntPreviewMask.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        }
        invalidate();
    }
}
