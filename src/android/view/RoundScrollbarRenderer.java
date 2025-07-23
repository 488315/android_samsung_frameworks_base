package android.view;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.SystemProperties;
import android.util.MathUtils;
import android.view.flags.Flags;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class RoundScrollbarRenderer {
    public static final String BLUECHIP_ENABLED_SYSPROP = "persist.cw_build.bluechip.enabled";
    private static final int DEFAULT_THUMB_COLOR = -3750201;
    private static final int DEFAULT_TRACK_COLOR = -13684431;
    private static final float GAP_BETWEEN_TRACK_AND_THUMB_DP = 3.0f;
    private static final float MAX_SCROLLBAR_ANGLE_SWIPE = 20.16f;
    private static final float MIN_SCROLLBAR_ANGLE_SWIPE = 8.64f;
    private static final float OUTER_PADDING_DP = 2.0f;
    private static final float RESIZING_RATE = 0.8f;
    private static final int RESIZING_THRESHOLD_PX = 20;
    private static final float SCROLLBAR_ANGLE_RANGE = 28.8f;
    private float mCurrentScrollDiff;
    private boolean mDrawToLeft;
    private final float mGapBetweenThumbAndTrackPx;
    private float mGapBetweenTrackAndThumbAsDegrees;
    private final float mInset;
    private float mMaxScrollDiff;
    private final View mParent;
    private float mPreviousCurrentScroll;
    private float mPreviousMaxScroll;
    private final RectF mRect;
    private final Paint mThumbPaint;
    private float mThumbStrokeWidthAsDegrees;
    private final Paint mTrackPaint;
    private final boolean mUseRefactoredRoundScrollbar;

    public RoundScrollbarRenderer(View view) {
        Paint paint = new Paint();
        this.mThumbPaint = paint;
        Paint paint2 = new Paint();
        this.mTrackPaint = paint2;
        this.mRect = new RectF();
        this.mPreviousMaxScroll = 0.0f;
        this.mMaxScrollDiff = 0.0f;
        this.mPreviousCurrentScroll = 0.0f;
        this.mCurrentScrollDiff = 0.0f;
        this.mThumbStrokeWidthAsDegrees = 0.0f;
        this.mGapBetweenTrackAndThumbAsDegrees = 0.0f;
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint2.setAntiAlias(true);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setStyle(Paint.Style.STROKE);
        this.mParent = view;
        Resources resources = view.getContext().getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.circular_display_mask_thickness);
        float dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.round_scrollbar_width);
        this.mGapBetweenThumbAndTrackPx = dpToPx(3.0f);
        paint.setStrokeWidth(dimensionPixelSize2);
        paint2.setStrokeWidth(dimensionPixelSize2);
        this.mInset = (dimensionPixelSize2 / 2.0f) + dimensionPixelSize;
        this.mUseRefactoredRoundScrollbar = Flags.useRefactoredRoundScrollbar() && SystemProperties.getBoolean(BLUECHIP_ENABLED_SYSPROP, false);
    }

    private float computeScrollExtent(float f, float f2) {
        if (f <= 0.0f) {
            return (this.mParent.canScrollVertically(1) || this.mParent.canScrollVertically(-1)) ? 0.0f : -1.0f;
        }
        if (f2 <= f) {
            return -1.0f;
        }
        return f;
    }

    private void resizeGradually(float f, float f2) {
        if (Math.abs(f - this.mPreviousMaxScroll) > 20.0f) {
            float f3 = this.mPreviousMaxScroll;
            if (f3 != 0.0f) {
                this.mMaxScrollDiff += f - f3;
                this.mCurrentScrollDiff += f2 - this.mPreviousCurrentScroll;
            }
        }
        this.mPreviousMaxScroll = f;
        this.mPreviousCurrentScroll = f2;
        if (Math.abs(this.mMaxScrollDiff) > 20.0f || Math.abs(this.mCurrentScrollDiff) > 20.0f) {
            this.mMaxScrollDiff *= 0.8f;
            this.mCurrentScrollDiff *= 0.8f;
        } else {
            this.mMaxScrollDiff = 0.0f;
            this.mCurrentScrollDiff = 0.0f;
        }
    }

    public void drawRoundScrollbars(Canvas canvas, float f, Rect rect, boolean z) {
        if (f == 0.0f) {
            return;
        }
        float computeVerticalScrollRange = this.mParent.computeVerticalScrollRange();
        float computeVerticalScrollExtent = this.mParent.computeVerticalScrollExtent();
        float computeVerticalScrollOffset = this.mParent.computeVerticalScrollOffset();
        float computeScrollExtent = computeScrollExtent(computeVerticalScrollExtent, computeVerticalScrollRange);
        if (computeScrollExtent < 0.0f) {
            return;
        }
        resizeGradually(computeVerticalScrollRange, computeVerticalScrollOffset);
        float f2 = computeVerticalScrollRange - this.mMaxScrollDiff;
        float f3 = computeVerticalScrollOffset - this.mCurrentScrollDiff;
        applyThumbColor(f);
        float computeSweepAngle = computeSweepAngle(computeScrollExtent, f2);
        float computeStartAngle = computeStartAngle(Math.max(0.0f, f3), computeSweepAngle, f2, computeScrollExtent);
        updateBounds(rect);
        this.mDrawToLeft = z;
        drawRoundScrollbars(canvas, computeStartAngle, computeSweepAngle, f);
    }

    private void drawRoundScrollbars(Canvas canvas, float f, float f2, float f3) {
        if (this.mUseRefactoredRoundScrollbar) {
            draw(canvas, f, f2, f3);
            return;
        }
        applyTrackColor(f3);
        drawArc(canvas, -14.4f, SCROLLBAR_ANGLE_RANGE, this.mTrackPaint);
        drawArc(canvas, f, f2, this.mThumbPaint);
    }

    private void updateBounds(Rect rect) {
        this.mRect.set(rect.left + this.mInset, rect.top + this.mInset, rect.right - this.mInset, rect.bottom - this.mInset);
        this.mThumbStrokeWidthAsDegrees = getVertexAngle((this.mRect.right - this.mRect.left) / 2.0f, this.mThumbPaint.getStrokeWidth() / 2.0f);
        this.mGapBetweenTrackAndThumbAsDegrees = getVertexAngle((this.mRect.right - this.mRect.left) / 2.0f, this.mGapBetweenThumbAndTrackPx);
    }

    private float computeSweepAngle(float f, float f2) {
        return clamp((f / f2) * SCROLLBAR_ANGLE_RANGE, MIN_SCROLLBAR_ANGLE_SWIPE, MAX_SCROLLBAR_ANGLE_SWIPE);
    }

    private float computeStartAngle(float f, float f2, float f3, float f4) {
        return clamp(((f * (SCROLLBAR_ANGLE_RANGE - f2)) / (f3 - f4)) - 14.4f, -14.4f, 14.4f - f2);
    }

    void getRoundVerticalScrollBarBounds(Rect rect) {
        float dpToPx = dpToPx(2.0f);
        int i = this.mParent.mRight - this.mParent.mLeft;
        int i2 = this.mParent.mBottom - this.mParent.mTop;
        int i3 = (int) dpToPx;
        rect.left = this.mParent.mScrollX + i3;
        rect.top = this.mParent.mScrollY + i3;
        rect.right = (this.mParent.mScrollX + i) - i3;
        rect.bottom = (this.mParent.mScrollY + i2) - i3;
    }

    private static float clamp(float f, float f2, float f3) {
        return f < f2 ? f2 : Math.min(f, f3);
    }

    private static int applyAlpha(int i, float f) {
        return Color.argb((int) (Color.alpha(i) * f), Color.red(i), Color.green(i), Color.blue(i));
    }

    private void applyThumbColor(float f) {
        int applyAlpha = applyAlpha(DEFAULT_THUMB_COLOR, f);
        if (this.mThumbPaint.getColor() != applyAlpha) {
            this.mThumbPaint.setColor(applyAlpha);
        }
    }

    private void applyTrackColor(float f) {
        int applyAlpha = applyAlpha(DEFAULT_TRACK_COLOR, f);
        if (this.mTrackPaint.getColor() != applyAlpha) {
            this.mTrackPaint.setColor(applyAlpha);
        }
    }

    private float dpToPx(float f) {
        return (f * this.mParent.getContext().getResources().getDisplayMetrics().densityDpi) / 160.0f;
    }

    private static float getVertexAngle(float f, float f2) {
        float f3 = f * f * 2.0f;
        return (float) Math.toDegrees(MathUtils.acos((f3 - (f2 * f2)) / f3));
    }

    private static float getKiteEdge(float f, float f2) {
        return (float) (f * 2.0f * Math.sin(Math.toRadians(f2 / 2.0f)));
    }

    private void draw(Canvas canvas, float f, float f2, float f3) {
        float f4 = this.mThumbStrokeWidthAsDegrees;
        drawTrack(canvas, (-14.4f) - f4, (f - f4) - this.mGapBetweenTrackAndThumbAsDegrees, f3);
        drawArc(canvas, f, f2, this.mThumbPaint);
        float f5 = f + f2;
        float f6 = this.mThumbStrokeWidthAsDegrees;
        drawTrack(canvas, f5 + f6 + this.mGapBetweenTrackAndThumbAsDegrees, f6 + 14.4f, f3);
    }

    private void drawTrack(Canvas canvas, float f, float f2, float f3) {
        float f4;
        float kiteEdge;
        float f5 = f2 - f;
        float f6 = this.mThumbStrokeWidthAsDegrees;
        float f7 = f5 - (f6 * 2.0f);
        if (f7 > 0.0f) {
            f4 = f + f6;
            kiteEdge = this.mThumbPaint.getStrokeWidth();
        } else {
            if (Math.abs(f7) >= this.mThumbStrokeWidthAsDegrees * 2.0f) {
                return;
            }
            f4 = f + (f5 / 2.0f);
            kiteEdge = getKiteEdge((this.mRect.right - this.mRect.left) / 2.0f, f5);
            f3 *= Math.min(1.0f, (2.0f * kiteEdge) / this.mThumbPaint.getStrokeWidth());
            f7 = Float.MIN_NORMAL;
        }
        applyTrackColor(f3);
        this.mTrackPaint.setStrokeWidth(kiteEdge);
        drawArc(canvas, f4, f7, this.mTrackPaint);
    }

    private void drawArc(Canvas canvas, float f, float f2, Paint paint) {
        if (this.mDrawToLeft) {
            canvas.drawArc(this.mRect, 180.0f - f, -f2, false, paint);
        } else {
            canvas.drawArc(this.mRect, f, f2, false, paint);
        }
    }
}
