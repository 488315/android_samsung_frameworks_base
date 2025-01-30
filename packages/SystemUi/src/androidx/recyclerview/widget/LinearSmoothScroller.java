package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class LinearSmoothScroller extends RecyclerView.SmoothScroller {
    public final DisplayMetrics mDisplayMetrics;
    public float mMillisPerPixel;
    public PointF mTargetVector;
    public final LinearInterpolator mLinearInterpolator = new LinearInterpolator();
    public final DecelerateInterpolator mDecelerateInterpolator = new DecelerateInterpolator();
    public boolean mHasCalculatedMillisPerPixel = false;
    public int mInterimTargetDx = 0;
    public int mInterimTargetDy = 0;

    public LinearSmoothScroller(Context context) {
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
    }

    public static int calculateDtToFit(int i, int i2, int i3, int i4, int i5) {
        if (i5 == -1) {
            return i3 - i;
        }
        if (i5 != 0) {
            if (i5 == 1) {
                return i4 - i2;
            }
            throw new IllegalArgumentException("snap preference should be one of the constants defined in SmoothScroller, starting with SNAP_");
        }
        int i6 = i3 - i;
        if (i6 > 0) {
            return i6;
        }
        int i7 = i4 - i2;
        if (i7 < 0) {
            return i7;
        }
        return 0;
    }

    public final int calculateDxToMakeVisible(View view, int i) {
        RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
        if (layoutManager == null || !layoutManager.canScrollHorizontally()) {
            return 0;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        return calculateDtToFit(layoutManager.getDecoratedLeft(view) - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, layoutManager.getDecoratedRight(view) + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, layoutManager.getPaddingLeft(), layoutManager.mWidth - layoutManager.getPaddingRight(), i);
    }

    public final int calculateDyToMakeVisible(View view, int i) {
        RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
        if (layoutManager == null || !layoutManager.canScrollVertically()) {
            return 0;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        return calculateDtToFit(layoutManager.getDecoratedTop(view) - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, layoutManager.getDecoratedBottom(view) + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, layoutManager.getPaddingTop(), layoutManager.mHeight - layoutManager.getPaddingBottom(), i);
    }

    public float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return 25.0f / displayMetrics.densityDpi;
    }

    public final int calculateTimeForDeceleration(int i) {
        return (int) Math.ceil(calculateTimeForScrolling(i) / 0.3356d);
    }

    public int calculateTimeForScrolling(int i) {
        float abs = Math.abs(i);
        if (!this.mHasCalculatedMillisPerPixel) {
            this.mMillisPerPixel = calculateSpeedPerPixel(this.mDisplayMetrics);
            this.mHasCalculatedMillisPerPixel = true;
        }
        return (int) Math.ceil(abs * this.mMillisPerPixel);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller
    public final void onSeekTargetStep(int i, int i2, RecyclerView.SmoothScroller.Action action) {
        if (getChildCount() == 0) {
            stop();
            return;
        }
        int i3 = this.mInterimTargetDx;
        int i4 = i3 - i;
        if (i3 * i4 <= 0) {
            i4 = 0;
        }
        this.mInterimTargetDx = i4;
        int i5 = this.mInterimTargetDy;
        int i6 = i5 - i2;
        int i7 = i5 * i6 > 0 ? i6 : 0;
        this.mInterimTargetDy = i7;
        if (i4 == 0 && i7 == 0) {
            PointF computeScrollVectorForPosition = computeScrollVectorForPosition(this.mTargetPosition);
            if (computeScrollVectorForPosition != null) {
                if (computeScrollVectorForPosition.x != 0.0f || computeScrollVectorForPosition.y != 0.0f) {
                    float f = computeScrollVectorForPosition.y;
                    float sqrt = (float) Math.sqrt((f * f) + (r4 * r4));
                    float f2 = computeScrollVectorForPosition.x / sqrt;
                    computeScrollVectorForPosition.x = f2;
                    float f3 = computeScrollVectorForPosition.y / sqrt;
                    computeScrollVectorForPosition.y = f3;
                    this.mTargetVector = computeScrollVectorForPosition;
                    this.mInterimTargetDx = (int) (f2 * 10000.0f);
                    this.mInterimTargetDy = (int) (f3 * 10000.0f);
                    action.update((int) (this.mInterimTargetDx * 1.2f), (int) (this.mInterimTargetDy * 1.2f), (int) (calculateTimeForScrolling(10000) * 1.2f), this.mLinearInterpolator);
                    return;
                }
            }
            action.mJumpToPosition = this.mTargetPosition;
            stop();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller
    public void onStop() {
        this.mInterimTargetDy = 0;
        this.mInterimTargetDx = 0;
        this.mTargetVector = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x001e  */
    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onTargetFound(View view, RecyclerView.SmoothScroller.Action action) {
        int i;
        PointF pointF;
        int calculateTimeForDeceleration;
        PointF pointF2 = this.mTargetVector;
        int i2 = 1;
        if (pointF2 != null) {
            float f = pointF2.x;
            if (f != 0.0f) {
                i = f > 0.0f ? 1 : -1;
                int calculateDxToMakeVisible = calculateDxToMakeVisible(view, i);
                pointF = this.mTargetVector;
                if (pointF != null) {
                    float f2 = pointF.y;
                    if (f2 != 0.0f) {
                        if (f2 <= 0.0f) {
                            i2 = -1;
                        }
                        int calculateDyToMakeVisible = calculateDyToMakeVisible(view, i2);
                        calculateTimeForDeceleration = calculateTimeForDeceleration((int) Math.sqrt((calculateDyToMakeVisible * calculateDyToMakeVisible) + (calculateDxToMakeVisible * calculateDxToMakeVisible)));
                        if (calculateTimeForDeceleration > 0) {
                            action.update(-calculateDxToMakeVisible, -calculateDyToMakeVisible, calculateTimeForDeceleration, this.mDecelerateInterpolator);
                            return;
                        }
                        return;
                    }
                }
                i2 = 0;
                int calculateDyToMakeVisible2 = calculateDyToMakeVisible(view, i2);
                calculateTimeForDeceleration = calculateTimeForDeceleration((int) Math.sqrt((calculateDyToMakeVisible2 * calculateDyToMakeVisible2) + (calculateDxToMakeVisible * calculateDxToMakeVisible)));
                if (calculateTimeForDeceleration > 0) {
                }
            }
        }
        i = 0;
        int calculateDxToMakeVisible2 = calculateDxToMakeVisible(view, i);
        pointF = this.mTargetVector;
        if (pointF != null) {
        }
        i2 = 0;
        int calculateDyToMakeVisible22 = calculateDyToMakeVisible(view, i2);
        calculateTimeForDeceleration = calculateTimeForDeceleration((int) Math.sqrt((calculateDyToMakeVisible22 * calculateDyToMakeVisible22) + (calculateDxToMakeVisible2 * calculateDxToMakeVisible2)));
        if (calculateTimeForDeceleration > 0) {
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller
    public final void onStart() {
    }
}
