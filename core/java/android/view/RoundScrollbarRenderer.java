package android.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.android.internal.R;

/* loaded from: classes4.dex */
class RoundScrollbarRenderer {
    private static final int DEFAULT_THUMB_COLOR = -1;
    private static final int DEFAULT_TRACK_COLOR = 1291845631;
    private static final float MAX_SCROLLBAR_ANGLE_SWIPE = 26.3f;
    private static final float MIN_SCROLLBAR_ANGLE_SWIPE = 3.1f;
    private static final float OUTER_PADDING_DP = 2.0f;
    private static final float RESIZING_RATE = 0.8f;
    private static final int RESIZING_THRESHOLD_PX = 20;
    private static final float SCROLLBAR_ANGLE_RANGE = 28.8f;
    private static final float THUMB_WIDTH_DP = 4.0f;
    private final int mMaskThickness;
    private final View mParent;
    private final Paint mThumbPaint = new Paint();
    private final Paint mTrackPaint = new Paint();
    private final RectF mRect = new RectF();
    private float mPreviousMaxScroll = 0.0f;
    private float mMaxScrollDiff = 0.0f;
    private float mPreviousCurrentScroll = 0.0f;
    private float mCurrentScrollDiff = 0.0f;

    public RoundScrollbarRenderer(View parent) {
        this.mThumbPaint.setAntiAlias(true);
        this.mThumbPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mThumbPaint.setStyle(Paint.Style.STROKE);
        this.mTrackPaint.setAntiAlias(true);
        this.mTrackPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mTrackPaint.setStyle(Paint.Style.STROKE);
        this.mParent = parent;
        this.mMaskThickness =
                parent.getContext()
                        .getResources()
                        .getDimensionPixelSize(R.dimen.circular_display_mask_thickness);
    }

    public void drawRoundScrollbars(Canvas canvas, float alpha, Rect bounds, boolean drawToLeft) {
        if (alpha == 0.0f) {
            return;
        }
        float maxScroll = this.mParent.computeVerticalScrollRange();
        float scrollExtent = this.mParent.computeVerticalScrollExtent();
        float newScroll = this.mParent.computeVerticalScrollOffset();
        if (scrollExtent <= 0.0f) {
            if (!this.mParent.canScrollVertically(1) && !this.mParent.canScrollVertically(-1)) {
                return;
            } else {
                scrollExtent = 0.0f;
            }
        } else if (maxScroll <= scrollExtent) {
            return;
        }
        if (Math.abs(maxScroll - this.mPreviousMaxScroll) > 20.0f
                && this.mPreviousMaxScroll != 0.0f) {
            this.mMaxScrollDiff += maxScroll - this.mPreviousMaxScroll;
            this.mCurrentScrollDiff += newScroll - this.mPreviousCurrentScroll;
        }
        this.mPreviousMaxScroll = maxScroll;
        this.mPreviousCurrentScroll = newScroll;
        if (Math.abs(this.mMaxScrollDiff) > 20.0f || Math.abs(this.mCurrentScrollDiff) > 20.0f) {
            this.mMaxScrollDiff *= 0.8f;
            this.mCurrentScrollDiff *= 0.8f;
            maxScroll -= this.mMaxScrollDiff;
            newScroll -= this.mCurrentScrollDiff;
        } else {
            this.mMaxScrollDiff = 0.0f;
            this.mCurrentScrollDiff = 0.0f;
        }
        float currentScroll = Math.max(0.0f, newScroll);
        float linearThumbLength = scrollExtent;
        float thumbWidth = dpToPx(THUMB_WIDTH_DP);
        this.mThumbPaint.setStrokeWidth(thumbWidth);
        this.mTrackPaint.setStrokeWidth(thumbWidth);
        setThumbColor(applyAlpha(-1, alpha));
        setTrackColor(applyAlpha(DEFAULT_TRACK_COLOR, alpha));
        float sweepAngle =
                clamp(
                        (linearThumbLength / maxScroll) * SCROLLBAR_ANGLE_RANGE,
                        MIN_SCROLLBAR_ANGLE_SWIPE,
                        MAX_SCROLLBAR_ANGLE_SWIPE);
        float startAngle =
                clamp(
                        (((SCROLLBAR_ANGLE_RANGE - sweepAngle) * currentScroll)
                                        / (maxScroll - linearThumbLength))
                                - 14.4f,
                        -14.4f,
                        14.4f - sweepAngle);
        float inset = (thumbWidth / 2.0f) + this.mMaskThickness;
        this.mRect.set(
                bounds.left + inset,
                bounds.top + inset,
                bounds.right - inset,
                bounds.bottom - inset);
        if (drawToLeft) {
            canvas.drawArc(this.mRect, 194.4f, -28.8f, false, this.mTrackPaint);
            canvas.drawArc(this.mRect, 180.0f - startAngle, -sweepAngle, false, this.mThumbPaint);
        } else {
            canvas.drawArc(this.mRect, -14.4f, SCROLLBAR_ANGLE_RANGE, false, this.mTrackPaint);
            canvas.drawArc(this.mRect, startAngle, sweepAngle, false, this.mThumbPaint);
        }
    }

    void getRoundVerticalScrollBarBounds(Rect bounds) {
        float padding = dpToPx(2.0f);
        int width = this.mParent.mRight - this.mParent.mLeft;
        int height = this.mParent.mBottom - this.mParent.mTop;
        bounds.left = this.mParent.mScrollX + ((int) padding);
        bounds.top = this.mParent.mScrollY + ((int) padding);
        bounds.right = (this.mParent.mScrollX + width) - ((int) padding);
        bounds.bottom = (this.mParent.mScrollY + height) - ((int) padding);
    }

    private static float clamp(float val, float min, float max) {
        if (val < min) {
            return min;
        }
        if (val > max) {
            return max;
        }
        return val;
    }

    private static int applyAlpha(int color, float alpha) {
        int alphaByte = (int) (Color.alpha(color) * alpha);
        return Color.argb(alphaByte, Color.red(color), Color.green(color), Color.blue(color));
    }

    private void setThumbColor(int thumbColor) {
        if (this.mThumbPaint.getColor() != thumbColor) {
            this.mThumbPaint.setColor(thumbColor);
        }
    }

    private void setTrackColor(int trackColor) {
        if (this.mTrackPaint.getColor() != trackColor) {
            this.mTrackPaint.setColor(trackColor);
        }
    }

    private float dpToPx(float dp) {
        return (this.mParent.getContext().getResources().getDisplayMetrics().densityDpi * dp)
                / 160.0f;
    }
}
