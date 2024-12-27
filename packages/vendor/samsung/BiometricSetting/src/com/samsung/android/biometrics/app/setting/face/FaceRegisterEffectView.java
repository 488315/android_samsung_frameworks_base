package com.samsung.android.biometrics.app.setting.face;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.PathInterpolator;

import androidx.recyclerview.widget.RecyclerView;

import com.samsung.android.biometrics.app.setting.Utils;

import java.util.ArrayList;

public class FaceRegisterEffectView extends View {
    public final int COLOR_BACKGROUND;
    public final int COLOR_BLUR_MASK;
    public final int COLOR_CHECK_ICON;
    public final int COLOR_GREEN_MASK;
    public final int COLOR_INNER_GUIDE_LINE;
    public final int COLOR_PREVIEW_MASK;
    public final int COLOR_PROGRESS_BASE_LINE;
    public final int COLOR_PROGRESS_LINE;
    public final float WIDTH_ERROR_PREVIEW_AREA_PERCENT;
    public final float WIDTH_INNER_CIRCLE_AREA_PERCENT;
    public float mCheckIconMoveDp;
    public long mCompletionTimestamp;
    public int mErrorState;
    public long mErrorTimestamp;
    public boolean mIsBlurMask;
    public boolean mIsErrorFinished;
    public boolean mIsErrorStarted;
    public boolean mIsPreviewMask;
    public boolean mIsPreviewStarting;
    public boolean mIsScanning;
    public final Path mPath;
    public final Path mPathCheckIcon;
    public final Paint mPntBackground;
    public final Paint mPntBlurMask;
    public final Paint mPntCheckIcon;
    public final Paint mPntGreenMask;
    public final Paint mPntInnerCircle;
    public final Paint mPntPreviewMask;
    public final Paint mPntProgressBaseLine;
    public final Paint mPntProgressLine;
    public final Paint mPntScanLine;
    public final Paint mPntTraceLine;
    public float mPreviewExpandSize;
    public long mPreviewMaskTimestamp;
    public float mProgress;
    public final Matrix mRotateMatrix;
    public final ArrayList mScanPathList;
    public long mScanStartTimestamp;
    public long mStartingTimestamp;
    public int mState;
    public float mStrokeWidth;
    public float mTargetPreviewExpandSize;
    public float mTargetProgress;
    public final ArrayList mTracePathList;
    public static final PathInterpolator INTP_STARTING_ANIMATION =
            new PathInterpolator(0.33f, RecyclerView.DECELERATION_RATE, 0.2f, 1.0f);
    public static final PathInterpolator INTP_COMPLETED_LINE_SCALE =
            new PathInterpolator(0.45f, 0.2f, 0.67f, 1.0f);
    public static final PathInterpolator INTP_COMPLETED_LINE_MOVING =
            new PathInterpolator(0.5f, RecyclerView.DECELERATION_RATE, 0.5f, 1.0f);
    public static final PathInterpolator INTP_COMPLETED_ROTATION =
            new PathInterpolator(0.91f, RecyclerView.DECELERATION_RATE, 0.21f, 1.0f);
    public static final PathInterpolator INTP_CHECK_ICON_MOVING =
            new PathInterpolator(0.17f, 0.17f, 0.4f, 1.0f);
    public static final PathInterpolator INTP_ERROR_STARTING =
            new PathInterpolator(0.17f, 0.17f, 0.1f, 1.0f);
    public static final PathInterpolator INTP_ERROR_MOVING =
            new PathInterpolator(0.33f, RecyclerView.DECELERATION_RATE, 0.3f, 0.1f);

    public final class FaceScanEffectItem {
        public final float angle;
        public final int ex;
        public final int sx;

        public FaceScanEffectItem(PathInterpolator pathInterpolator, int i, int i2) {
            this.angle = 90.0f;
            float f = i2;
            int interpolation = (int) (pathInterpolator.getInterpolation(i / f) * f);
            this.sx = interpolation;
            int i3 = i2 - interpolation;
            this.ex = i3;
            if (interpolation > i3) {
                this.ex = interpolation;
                this.sx = i3;
                this.angle = 270.0f;
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public FaceRegisterEffectView(android.content.Context r22, android.util.AttributeSet r23) {
        /*
            Method dump skipped, instructions count: 592
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.biometrics.app.setting.face.FaceRegisterEffectView.<init>(android.content.Context,"
                    + " android.util.AttributeSet):void");
    }

    public static float getTimePos(long j, long j2, int i) {
        return Math.min(
                i,
                Math.max(RecyclerView.DECELERATION_RATE, (SystemClock.elapsedRealtime() - j) / j2));
    }

    public final void drawBackground(Canvas canvas, float f, float f2) {
        float min = Math.min(f, f2) / 2.0f;
        if (Math.abs(this.mPreviewExpandSize - this.mTargetPreviewExpandSize) > 0.01f) {
            this.mPreviewExpandSize +=
                    this.mTargetPreviewExpandSize < RecyclerView.DECELERATION_RATE
                            ? -0.005f
                            : 0.005f;
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
        canvas.drawRect(
                RecyclerView.DECELERATION_RATE,
                RecyclerView.DECELERATION_RATE,
                getWidth(),
                getHeight(),
                this.mPntBackground);
        canvas.restore();
    }

    public final void drawCompletedEffect(Canvas canvas, float f, float f2) {
        if (this.mState != 3) {
            return;
        }
        float timePos = getTimePos(this.mCompletionTimestamp, 600L, 1);
        float interpolation = INTP_COMPLETED_LINE_MOVING.getInterpolation(timePos);
        float interpolation2 =
                INTP_COMPLETED_LINE_SCALE.getInterpolation(
                        getTimePos(this.mCompletionTimestamp, 150L, 1));
        float f3 = f * 0.1f * interpolation2;
        float f4 = 0.1f * f2 * interpolation2;
        canvas.save();
        canvas.translate((-f3) / 2.0f, (-f4) / 2.0f);
        float f5 = f + f3;
        float f6 = f2 + f4;
        float f7 = interpolation * f5;
        float f8 = f5 - f7;
        this.mRotateMatrix.setRotate(
                (INTP_COMPLETED_ROTATION.getInterpolation(timePos) * 120.0f) - 60.0f,
                f5 / 2.0f,
                f6 / 2.0f);
        this.mPath.reset();
        this.mPath.addOval(
                Math.min(f7, f8),
                RecyclerView.DECELERATION_RATE,
                Math.max(f7, f8),
                f6,
                Path.Direction.CW);
        this.mPath.transform(this.mRotateMatrix);
        canvas.drawPath(this.mPath, this.mPntProgressLine);
        canvas.restore();
        boolean z = timePos < 1.0f;
        float min = Math.min(f, f2) / 2.0f;
        float timePos2 = getTimePos(this.mCompletionTimestamp, 150L, 1);
        this.mPntGreenMask.setAlpha((int) (57.0f * timePos2));
        canvas.drawCircle(f / 2.0f, f2 / 2.0f, min, this.mPntGreenMask);
        boolean z2 = z | (timePos2 < 1.0f);
        float timePos3 = getTimePos(this.mCompletionTimestamp, 400L, 2);
        float interpolation3 =
                INTP_CHECK_ICON_MOVING.getInterpolation(
                                timePos3 > 1.0f ? 2.0f - timePos3 : timePos3)
                        * this.mCheckIconMoveDp;
        canvas.save();
        canvas.translate(RecyclerView.DECELERATION_RATE, interpolation3);
        canvas.drawPath(this.mPathCheckIcon, this.mPntCheckIcon);
        canvas.restore();
        if (!z2 && !(timePos3 < 2.0f)) {
            this.mState = 4;
        }
    }

    public final void drawPreviewMask(Canvas canvas, float f, float f2) {
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

    public final float getErrorSizeDiff(float f) {
        if (!this.mIsErrorStarted) {
            return RecyclerView.DECELERATION_RATE;
        }
        int i = this.mErrorState;
        if (i == 0) {
            return INTP_ERROR_STARTING.getInterpolation(
                            Math.min(
                                            450L,
                                            Math.max(
                                                    0L,
                                                    SystemClock.elapsedRealtime()
                                                            - this.mErrorTimestamp))
                                    / 450.0f)
                    * f
                    * 0.05f;
        }
        if (i == 1) {
            return f * 0.05f;
        }
        if (i != 2) {
            return RecyclerView.DECELERATION_RATE;
        }
        long min =
                Math.min(2000L, Math.max(0L, SystemClock.elapsedRealtime() - this.mErrorTimestamp));
        int i2 = (int) (min / 1000);
        long j = min % 1000;
        if (i2 % 2 != 0) {
            j = 1000 - j;
        }
        return (1.0f - INTP_ERROR_MOVING.getInterpolation(j / 1000)) * f * 0.05f;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int i;
        int i2;
        int i3;
        float width = getWidth();
        float height = getHeight();
        if (this.mState == 0) {
            canvas.drawRect(
                    RecyclerView.DECELERATION_RATE,
                    RecyclerView.DECELERATION_RATE,
                    width,
                    height,
                    this.mPntBackground);
            return;
        }
        float min = Math.min(width, height) * 0.1f;
        float f = this.mStrokeWidth;
        float f2 = (width - f) - min;
        float f3 = (height - f) - min;
        if (Utils.Config.FEATURE_JDM_HAL) {
            drawBackground(canvas, width, height);
            drawPreviewMask(canvas, width, height);
            float f4 = this.mTargetProgress;
            this.mProgress = f4;
            if (f4 == 100.0f) {
                float f5 = (this.mStrokeWidth + min) / 2.0f;
                canvas.translate(f5, f5);
                drawCompletedEffect(canvas, f2, f3);
            }
            i2 = 3;
            i = 2;
        } else {
            drawBackground(canvas, f2, f3);
            canvas.save();
            float f6 = (this.mStrokeWidth + min) / 2.0f;
            canvas.translate(f6, f6);
            drawPreviewMask(canvas, f2, f3);
            int i4 = this.mState;
            int i5 = 255;
            if ((i4 == 1 && !this.mIsBlurMask)
                    || (i4 == 2
                            && this.mIsScanning
                            && (!this.mIsErrorStarted || this.mIsErrorFinished))) {
                float min2 = Math.min(f2, f3) / 2.0f;
                if (this.mState == 1) {
                    if ((SystemClock.elapsedRealtime() - this.mStartingTimestamp) - 1000 <= 400) {
                        i3 = (int) ((Math.max(0L, r5) * 255.0f) / 400.0f);
                        this.mPntInnerCircle.setAlpha(i3);
                        canvas.drawCircle(
                                f2 / 2.0f,
                                f3 / 2.0f,
                                min2 * this.WIDTH_INNER_CIRCLE_AREA_PERCENT,
                                this.mPntInnerCircle);
                    }
                }
                i3 = 255;
                this.mPntInnerCircle.setAlpha(i3);
                canvas.drawCircle(
                        f2 / 2.0f,
                        f3 / 2.0f,
                        min2 * this.WIDTH_INNER_CIRCLE_AREA_PERCENT,
                        this.mPntInnerCircle);
            }
            int i6 = this.mState;
            if (i6 != 3 && i6 != 4) {
                float min3 = Math.min(f2, f3) / 2.0f;
                if (this.mState == 1) {
                    long elapsedRealtime = SystemClock.elapsedRealtime() - this.mStartingTimestamp;
                    if (elapsedRealtime < 600) {
                        min3 =
                                (INTP_STARTING_ANIMATION.getInterpolation(elapsedRealtime / 600)
                                                * min3
                                                * 0.74f)
                                        + (0.3f * min3);
                    } else if (elapsedRealtime < 1200) {
                        min3 =
                                (1.04f * min3)
                                        - ((INTP_STARTING_ANIMATION.getInterpolation(
                                                                (elapsedRealtime - 600) / 600)
                                                        * min3)
                                                * 0.04f);
                    }
                    i5 = (int) (Math.min(100L, Math.max(0L, elapsedRealtime)) * 2.55f);
                }
                this.mPntProgressBaseLine.setAlpha(i5);
                canvas.drawCircle(
                        f2 / 2.0f,
                        f3 / 2.0f,
                        getErrorSizeDiff(min3) + min3,
                        this.mPntProgressBaseLine);
            }
            int i7 = this.mState;
            if (i7 == 4 || i7 == 1) {
                i = 2;
                i2 = 3;
            } else if (i7 == 3) {
                i2 = 3;
                i = 2;
            } else {
                float f7 = (this.mTargetProgress - this.mProgress) / 10.0f;
                float min4 =
                        f7 < RecyclerView.DECELERATION_RATE
                                ? Math.min(f7, -0.2f)
                                : Math.max(f7, 0.2f);
                float f8 = Math.abs(min4) <= 0.2f ? this.mTargetProgress : min4 + this.mProgress;
                this.mProgress = f8;
                float f9 = (f8 * 360.0f) / 100.0f;
                float errorSizeDiff = getErrorSizeDiff(Math.min(f2, f3)) / 2.0f;
                float f10 = -errorSizeDiff;
                i2 = 3;
                i = 2;
                canvas.drawArc(
                        f10,
                        f10,
                        f2 + errorSizeDiff,
                        f3 + errorSizeDiff,
                        -90.0f,
                        f9,
                        false,
                        this.mPntProgressLine);
            }
            drawCompletedEffect(canvas, f2, f3);
            if (this.mIsScanning && (!this.mIsErrorStarted || this.mIsErrorFinished)) {
                if (this.mScanPathList.size() == 0
                        || this.mScanPathList.size() != this.mTracePathList.size()) {
                    Log.w(
                            "BSS_FaceRegisterEffectView",
                            "Path is not ready ("
                                    + this.mScanPathList.size()
                                    + ", "
                                    + this.mTracePathList.size()
                                    + ")");
                } else {
                    long elapsedRealtime2 = SystemClock.elapsedRealtime();
                    float min5 = Math.min(f2, f3);
                    canvas.save();
                    float f11 = ((1.0f - this.WIDTH_INNER_CIRCLE_AREA_PERCENT) * min5) / 2.0f;
                    canvas.translate(f11, f11);
                    int size = this.mTracePathList.size();
                    int i8 =
                            ((int) (((elapsedRealtime2 - this.mScanStartTimestamp) * size) / 1800))
                                    % size;
                    int i9 = 50;
                    int i10 = i8;
                    while (i10 >= i8 - 50) {
                        this.mPntTraceLine.setAlpha(Math.max(0, i9));
                        if (i10 >= 0) {
                            canvas.drawPath(
                                    (Path) this.mTracePathList.get((i10 + size) % size),
                                    this.mPntTraceLine);
                        }
                        i10--;
                        i9--;
                    }
                    canvas.drawPath((Path) this.mScanPathList.get(i8), this.mPntScanLine);
                    canvas.restore();
                }
            }
            if (this.mIsBlurMask) {
                canvas.drawCircle(f2 / 2.0f, f3 / 2.0f, Math.min(f2, f3) / 2.0f, this.mPntBlurMask);
            }
            canvas.restore();
        }
        if (this.mState == 1 && getTimePos(this.mStartingTimestamp, 1400L, 1) == 1.0f) {
            this.mState = i;
            startScanEffect();
            if (this.mIsPreviewStarting) {
                if (this.mIsPreviewMask) {
                    this.mIsPreviewStarting = true;
                    if (this.mState != 1) {
                        this.mIsPreviewMask = false;
                        this.mPreviewMaskTimestamp = SystemClock.elapsedRealtime();
                    }
                }
                invalidate();
            }
        }
        if (this.mState == i && this.mProgress == 100.0f) {
            this.mCompletionTimestamp = SystemClock.elapsedRealtime();
            this.mState = i2;
            this.mIsScanning = false;
        }
        if (this.mIsErrorStarted) {
            long elapsedRealtime3 = SystemClock.elapsedRealtime();
            int i11 = this.mErrorState;
            if (i11 == 0 && elapsedRealtime3 - this.mErrorTimestamp > 450) {
                this.mErrorTimestamp = elapsedRealtime3;
                this.mErrorState = 1;
            } else if (i11 == 1 && elapsedRealtime3 - this.mErrorTimestamp > 150) {
                this.mErrorTimestamp = elapsedRealtime3;
                this.mErrorState = i;
            } else if (i11 == i) {
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
        if (i12 == 1
                || i12 == i2
                || this.mIsScanning
                || this.mIsErrorStarted
                || this.mProgress < this.mTargetProgress) {
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

    public void setProgress(int i) {
        if (this.mState != 1) {
            this.mState = 2;
            if (!this.mIsScanning) {
                startScanEffect();
            }
        }
        float f = i;
        this.mTargetProgress = f;
        if (f == RecyclerView.DECELERATION_RATE) {
            this.mProgress = RecyclerView.DECELERATION_RATE;
        }
        this.mTargetPreviewExpandSize = RecyclerView.DECELERATION_RATE;
        this.mIsErrorFinished = true;
        invalidate();
    }

    public final void startScanEffect() {
        if (this.mIsBlurMask) {
            return;
        }
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
