package androidx.recyclerview.widget;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.OverScroller;
import android.widget.Scroller;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class SnapHelper extends RecyclerView.OnFlingListener {
    public RecyclerView mRecyclerView;
    public final C04821 mScrollListener = new RecyclerView.OnScrollListener() { // from class: androidx.recyclerview.widget.SnapHelper.1
        public boolean mScrolled = false;

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public final void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 0 && this.mScrolled) {
                this.mScrolled = false;
                SnapHelper.this.snapToTargetExistingView();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public final void onScrolled(RecyclerView recyclerView, int i, int i2) {
            if (i == 0 && i2 == 0) {
                return;
            }
            this.mScrolled = true;
        }
    };

    public final void attachToRecyclerView(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.mRecyclerView;
        if (recyclerView2 == recyclerView) {
            return;
        }
        C04821 c04821 = this.mScrollListener;
        if (recyclerView2 != null) {
            recyclerView2.removeOnScrollListener(c04821);
            this.mRecyclerView.mOnFlingListener = null;
        }
        this.mRecyclerView = recyclerView;
        if (recyclerView != null) {
            if (recyclerView.mOnFlingListener != null) {
                throw new IllegalStateException("An instance of OnFlingListener already set.");
            }
            recyclerView.addOnScrollListener(c04821);
            this.mRecyclerView.mOnFlingListener = this;
            new Scroller(this.mRecyclerView.getContext(), new DecelerateInterpolator());
            new OverScroller(this.mRecyclerView.getContext());
            snapToTargetExistingView();
        }
    }

    public abstract int[] calculateDistanceToFinalSnap(RecyclerView.LayoutManager layoutManager, View view);

    public RecyclerView.SmoothScroller createScroller(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) {
            return new LinearSmoothScroller(this.mRecyclerView.getContext()) { // from class: androidx.recyclerview.widget.SnapHelper.2
                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                public final float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return 100.0f / displayMetrics.densityDpi;
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller, androidx.recyclerview.widget.RecyclerView.SmoothScroller
                public final void onTargetFound(View view, RecyclerView.SmoothScroller.Action action) {
                    SnapHelper snapHelper = SnapHelper.this;
                    RecyclerView recyclerView = snapHelper.mRecyclerView;
                    if (recyclerView == null) {
                        return;
                    }
                    int[] calculateDistanceToFinalSnap = snapHelper.calculateDistanceToFinalSnap(recyclerView.mLayout, view);
                    int i = calculateDistanceToFinalSnap[0];
                    int i2 = calculateDistanceToFinalSnap[1];
                    int calculateTimeForDeceleration = calculateTimeForDeceleration(Math.max(Math.abs(i), Math.abs(i2)));
                    if (calculateTimeForDeceleration > 0) {
                        action.update(i, i2, calculateTimeForDeceleration, this.mDecelerateInterpolator);
                    }
                }
            };
        }
        return null;
    }

    public abstract View findSnapView(RecyclerView.LayoutManager layoutManager);

    public abstract int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int i, int i2);

    @Override // androidx.recyclerview.widget.RecyclerView.OnFlingListener
    public final boolean onFling(int i, int i2) {
        RecyclerView.SmoothScroller createScroller;
        int findTargetSnapPosition;
        boolean z;
        RecyclerView recyclerView = this.mRecyclerView;
        RecyclerView.LayoutManager layoutManager = recyclerView.mLayout;
        if (layoutManager == null || recyclerView.mAdapter == null) {
            return false;
        }
        int i3 = recyclerView.mMinFlingVelocity;
        if (Math.abs(i2) <= i3 && Math.abs(i) <= i3) {
            return false;
        }
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) || (createScroller = createScroller(layoutManager)) == null || (findTargetSnapPosition = findTargetSnapPosition(layoutManager, i, i2)) == -1) {
            z = false;
        } else {
            createScroller.mTargetPosition = findTargetSnapPosition;
            layoutManager.startSmoothScroll(createScroller);
            z = true;
        }
        return z;
    }

    public final void snapToTargetExistingView() {
        RecyclerView.LayoutManager layoutManager;
        View findSnapView;
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView == null || (layoutManager = recyclerView.mLayout) == null || (findSnapView = findSnapView(layoutManager)) == null) {
            return;
        }
        int[] calculateDistanceToFinalSnap = calculateDistanceToFinalSnap(layoutManager, findSnapView);
        int i = calculateDistanceToFinalSnap[0];
        if (i == 0 && calculateDistanceToFinalSnap[1] == 0) {
            return;
        }
        this.mRecyclerView.smoothScrollBy(i, calculateDistanceToFinalSnap[1]);
    }
}
