package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public int calculateDxToMakeVisible(View view, int i) {
        RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
        if (layoutManager == null || !layoutManager.canScrollHorizontally()) {
            return 0;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        return calculateDtToFit(layoutManager.getDecoratedLeft(view) - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, layoutManager.getDecoratedRight(view) + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, layoutManager.getPaddingLeft(), layoutManager.mWidth - layoutManager.getPaddingRight(), i);
    }

    public int calculateDyToMakeVisible(View view, int i) {
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

    /* JADX WARN: Removed duplicated region for block: B:16:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x001e  */
    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onTargetFound(android.view.View r7, androidx.recyclerview.widget.RecyclerView.SmoothScroller.Action r8) {
        /*
            r6 = this;
            android.graphics.PointF r0 = r6.mTargetVector
            r1 = 0
            r2 = -1
            r3 = 1
            r4 = 0
            if (r0 == 0) goto L15
            float r0 = r0.x
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 != 0) goto Lf
            goto L15
        Lf:
            if (r0 <= 0) goto L13
            r0 = r3
            goto L16
        L13:
            r0 = r2
            goto L16
        L15:
            r0 = r1
        L16:
            int r0 = r6.calculateDxToMakeVisible(r7, r0)
            android.graphics.PointF r5 = r6.mTargetVector
            if (r5 == 0) goto L2a
            float r5 = r5.y
            int r4 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r4 != 0) goto L25
            goto L2a
        L25:
            if (r4 <= 0) goto L29
            r1 = r3
            goto L2a
        L29:
            r1 = r2
        L2a:
            int r7 = r6.calculateDyToMakeVisible(r7, r1)
            int r1 = r0 * r0
            int r2 = r7 * r7
            int r2 = r2 + r1
            double r1 = (double) r2
            double r1 = java.lang.Math.sqrt(r1)
            int r1 = (int) r1
            int r1 = r6.calculateTimeForDeceleration(r1)
            if (r1 <= 0) goto L46
            int r0 = -r0
            int r7 = -r7
            android.view.animation.DecelerateInterpolator r6 = r6.mDecelerateInterpolator
            r8.update(r0, r7, r1, r6)
        L46:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.LinearSmoothScroller.onTargetFound(android.view.View, androidx.recyclerview.widget.RecyclerView$SmoothScroller$Action):void");
    }
}
