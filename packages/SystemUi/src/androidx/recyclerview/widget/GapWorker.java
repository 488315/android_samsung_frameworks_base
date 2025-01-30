package androidx.recyclerview.widget;

import android.os.Trace;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GapWorker implements Runnable {
    public static final ThreadLocal sGapWorker = new ThreadLocal();
    public static final C04421 sTaskComparator = new Comparator() { // from class: androidx.recyclerview.widget.GapWorker.1
        /* JADX WARN: Code restructure failed: missing block: B:11:0x0037, code lost:
        
            return 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:?, code lost:
        
            return -1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0023, code lost:
        
            if (r5 != false) goto L14;
         */
        /* JADX WARN: Code restructure failed: missing block: B:9:0x0017, code lost:
        
            if (r5 == null) goto L13;
         */
        @Override // java.util.Comparator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final int compare(Object obj, Object obj2) {
            Task task = (Task) obj;
            Task task2 = (Task) obj2;
            RecyclerView recyclerView = task.view;
            if ((recyclerView == null) == (task2.view == null)) {
                boolean z = task.immediate;
                if (z == task2.immediate) {
                    int i = task2.viewVelocity - task.viewVelocity;
                    if (i == 0 && (i = task.distanceToItem - task2.distanceToItem) == 0) {
                        return 0;
                    }
                    return i;
                }
            }
        }
    };
    public long mFrameIntervalNs;
    public long mPostTimeNs;
    public final ArrayList mRecyclerViews = new ArrayList();
    public final ArrayList mTasks = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LayoutPrefetchRegistryImpl {
        public int mCount;
        public int[] mPrefetchArray;
        public int mPrefetchDx;
        public int mPrefetchDy;

        public final void addPosition(int i, int i2) {
            if (i < 0) {
                throw new IllegalArgumentException("Layout positions must be non-negative");
            }
            if (i2 < 0) {
                throw new IllegalArgumentException("Pixel distance must be non-negative");
            }
            int i3 = this.mCount * 2;
            int[] iArr = this.mPrefetchArray;
            if (iArr == null) {
                int[] iArr2 = new int[4];
                this.mPrefetchArray = iArr2;
                Arrays.fill(iArr2, -1);
            } else if (i3 >= iArr.length) {
                int[] iArr3 = new int[i3 * 2];
                this.mPrefetchArray = iArr3;
                System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
            }
            int[] iArr4 = this.mPrefetchArray;
            iArr4[i3] = i;
            iArr4[i3 + 1] = i2;
            this.mCount++;
        }

        public final void collectPrefetchPositionsFromView(RecyclerView recyclerView, boolean z) {
            this.mCount = 0;
            int[] iArr = this.mPrefetchArray;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            RecyclerView.LayoutManager layoutManager = recyclerView.mLayout;
            if (recyclerView.mAdapter == null || layoutManager == null || !layoutManager.mItemPrefetchEnabled) {
                return;
            }
            if (!z) {
                if (!(!recyclerView.mFirstLayoutComplete || recyclerView.mDataSetHasChangedAfterLayout || recyclerView.mAdapterHelper.hasPendingUpdates())) {
                    layoutManager.collectAdjacentPrefetchPositions(this.mPrefetchDx, this.mPrefetchDy, recyclerView.mState, this);
                }
            } else if (!recyclerView.mAdapterHelper.hasPendingUpdates()) {
                layoutManager.collectInitialPrefetchPositions(recyclerView.mAdapter.getItemCount(), this);
            }
            int i = this.mCount;
            if (i > layoutManager.mPrefetchMaxCountObserved) {
                layoutManager.mPrefetchMaxCountObserved = i;
                layoutManager.mPrefetchMaxObservedInInitialPrefetch = z;
                recyclerView.mRecycler.updateViewCacheSize();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Task {
        public int distanceToItem;
        public boolean immediate;
        public int position;
        public RecyclerView view;
        public int viewVelocity;
    }

    public static RecyclerView.ViewHolder prefetchPositionWithDeadline(RecyclerView recyclerView, int i, long j) {
        boolean z;
        int unfilteredChildCount = recyclerView.mChildHelper.getUnfilteredChildCount();
        int i2 = 0;
        while (true) {
            if (i2 >= unfilteredChildCount) {
                z = false;
                break;
            }
            RecyclerView.ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(recyclerView.mChildHelper.getUnfilteredChildAt(i2));
            if (childViewHolderInt.mPosition == i && !childViewHolderInt.isInvalid()) {
                z = true;
                break;
            }
            i2++;
        }
        if (z) {
            return null;
        }
        RecyclerView.Recycler recycler = recyclerView.mRecycler;
        try {
            recyclerView.onEnterLayoutOrScroll();
            RecyclerView.ViewHolder tryGetViewHolderForPositionByDeadline = recycler.tryGetViewHolderForPositionByDeadline(i, j);
            if (tryGetViewHolderForPositionByDeadline != null) {
                if (!tryGetViewHolderForPositionByDeadline.isBound() || tryGetViewHolderForPositionByDeadline.isInvalid()) {
                    recycler.addViewHolderToRecycledViewPool(tryGetViewHolderForPositionByDeadline, false);
                } else {
                    recycler.recycleView(tryGetViewHolderForPositionByDeadline.itemView);
                }
            }
            return tryGetViewHolderForPositionByDeadline;
        } finally {
            recyclerView.onExitLayoutOrScroll(false);
        }
    }

    public final void postFromTraversal(RecyclerView recyclerView, int i, int i2) {
        if (recyclerView.isAttachedToWindow() && this.mPostTimeNs == 0) {
            this.mPostTimeNs = recyclerView.getNanoTime();
            recyclerView.post(this);
        }
        LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView.mPrefetchRegistry;
        layoutPrefetchRegistryImpl.mPrefetchDx = i;
        layoutPrefetchRegistryImpl.mPrefetchDy = i2;
    }

    public final void prefetch(long j) {
        Task task;
        RecyclerView recyclerView;
        RecyclerView recyclerView2;
        Task task2;
        int size = this.mRecyclerViews.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            RecyclerView recyclerView3 = (RecyclerView) this.mRecyclerViews.get(i2);
            if (recyclerView3.getWindowVisibility() == 0) {
                recyclerView3.mPrefetchRegistry.collectPrefetchPositionsFromView(recyclerView3, false);
                i += recyclerView3.mPrefetchRegistry.mCount;
            }
        }
        this.mTasks.ensureCapacity(i);
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            RecyclerView recyclerView4 = (RecyclerView) this.mRecyclerViews.get(i4);
            if (recyclerView4.getWindowVisibility() == 0) {
                LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView4.mPrefetchRegistry;
                int abs = Math.abs(layoutPrefetchRegistryImpl.mPrefetchDy) + Math.abs(layoutPrefetchRegistryImpl.mPrefetchDx);
                for (int i5 = 0; i5 < layoutPrefetchRegistryImpl.mCount * 2; i5 += 2) {
                    if (i3 >= this.mTasks.size()) {
                        task2 = new Task();
                        this.mTasks.add(task2);
                    } else {
                        task2 = (Task) this.mTasks.get(i3);
                    }
                    int[] iArr = layoutPrefetchRegistryImpl.mPrefetchArray;
                    int i6 = iArr[i5 + 1];
                    task2.immediate = i6 <= abs;
                    task2.viewVelocity = abs;
                    task2.distanceToItem = i6;
                    task2.view = recyclerView4;
                    task2.position = iArr[i5];
                    i3++;
                }
            }
        }
        Collections.sort(this.mTasks, sTaskComparator);
        for (int i7 = 0; i7 < this.mTasks.size() && (recyclerView = (task = (Task) this.mTasks.get(i7)).view) != null; i7++) {
            RecyclerView.ViewHolder prefetchPositionWithDeadline = prefetchPositionWithDeadline(recyclerView, task.position, task.immediate ? Long.MAX_VALUE : j);
            if (prefetchPositionWithDeadline != null && prefetchPositionWithDeadline.mNestedRecyclerView != null && prefetchPositionWithDeadline.isBound() && !prefetchPositionWithDeadline.isInvalid() && (recyclerView2 = (RecyclerView) prefetchPositionWithDeadline.mNestedRecyclerView.get()) != null) {
                if (recyclerView2.mDataSetHasChangedAfterLayout && recyclerView2.mChildHelper.getUnfilteredChildCount() != 0) {
                    recyclerView2.removeAndRecycleViews();
                }
                LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl2 = recyclerView2.mPrefetchRegistry;
                layoutPrefetchRegistryImpl2.collectPrefetchPositionsFromView(recyclerView2, true);
                if (layoutPrefetchRegistryImpl2.mCount != 0) {
                    try {
                        Trace.beginSection("RV Nested Prefetch");
                        RecyclerView.State state = recyclerView2.mState;
                        RecyclerView.Adapter adapter = recyclerView2.mAdapter;
                        state.mLayoutStep = 1;
                        state.mItemCount = adapter.getItemCount();
                        state.mInPreLayout = false;
                        state.mTrackOldChangeHolders = false;
                        state.mIsMeasuring = false;
                        for (int i8 = 0; i8 < layoutPrefetchRegistryImpl2.mCount * 2; i8 += 2) {
                            prefetchPositionWithDeadline(recyclerView2, layoutPrefetchRegistryImpl2.mPrefetchArray[i8], j);
                        }
                    } finally {
                        Trace.endSection();
                    }
                } else {
                    continue;
                }
            }
            task.immediate = false;
            task.viewVelocity = 0;
            task.distanceToItem = 0;
            task.view = null;
            task.position = 0;
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            Trace.beginSection("RV Prefetch");
            if (this.mRecyclerViews.isEmpty()) {
                return;
            }
            int size = this.mRecyclerViews.size();
            long j = 0;
            for (int i = 0; i < size; i++) {
                RecyclerView recyclerView = (RecyclerView) this.mRecyclerViews.get(i);
                if (recyclerView.getWindowVisibility() == 0) {
                    j = Math.max(recyclerView.getDrawingTime(), j);
                }
            }
            if (j == 0) {
                return;
            }
            prefetch(TimeUnit.MILLISECONDS.toNanos(j) + this.mFrameIntervalNs);
        } finally {
            this.mPostTimeNs = 0L;
            Trace.endSection();
        }
    }
}
