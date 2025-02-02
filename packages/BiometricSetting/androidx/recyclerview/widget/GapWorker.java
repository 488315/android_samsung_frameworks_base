package androidx.recyclerview.widget;

import android.annotation.SuppressLint;
import android.os.Trace;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
final class GapWorker implements Runnable {
    static final ThreadLocal<GapWorker> sGapWorker = new ThreadLocal<>();
    static Comparator<Task> sTaskComparator = new C01561();
    long mFrameIntervalNs;
    long mPostTimeNs;
    ArrayList<RecyclerView> mRecyclerViews = new ArrayList<>();
    private ArrayList<Task> mTasks = new ArrayList<>();

    /* renamed from: androidx.recyclerview.widget.GapWorker$1 */
    final class C01561 implements Comparator<Task> {
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
        public final int compare(Task task, Task task2) {
            Task task3 = task;
            Task task4 = task2;
            RecyclerView recyclerView = task3.view;
            if ((recyclerView == null) == (task4.view == null)) {
                boolean z = task3.immediate;
                if (z == task4.immediate) {
                    int i = task4.viewVelocity - task3.viewVelocity;
                    if (i == 0 && (i = task3.distanceToItem - task4.distanceToItem) == 0) {
                        return 0;
                    }
                    return i;
                }
            }
        }
    }

    @SuppressLint({"VisibleForTests"})
    static class LayoutPrefetchRegistryImpl {
        int mCount;
        int[] mPrefetchArray;
        int mPrefetchDx;
        int mPrefetchDy;
    }

    static class Task {
        public int distanceToItem;
        public boolean immediate;
        public int position;
        public RecyclerView view;
        public int viewVelocity;

        Task() {
        }
    }

    GapWorker() {
    }

    final void postFromTraversal(RecyclerView recyclerView, int i, int i2) {
        if (recyclerView.isAttachedToWindow()) {
            if (RecyclerView.sDebugAssertionsEnabled && !this.mRecyclerViews.contains(recyclerView)) {
                throw new IllegalStateException("attempting to post unregistered view!");
            }
            if (this.mPostTimeNs == 0) {
                this.mPostTimeNs = recyclerView.getNanoTime();
                recyclerView.post(this);
            }
        }
        LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView.mPrefetchRegistry;
        layoutPrefetchRegistryImpl.mPrefetchDx = i;
        layoutPrefetchRegistryImpl.mPrefetchDy = i2;
    }

    final void prefetch(long j) {
        Task task;
        RecyclerView recyclerView;
        Task task2;
        int size = this.mRecyclerViews.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            RecyclerView recyclerView2 = this.mRecyclerViews.get(i2);
            if (recyclerView2.getWindowVisibility() == 0) {
                LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView2.mPrefetchRegistry;
                layoutPrefetchRegistryImpl.mCount = 0;
                int[] iArr = layoutPrefetchRegistryImpl.mPrefetchArray;
                if (iArr != null) {
                    Arrays.fill(iArr, -1);
                }
                i += recyclerView2.mPrefetchRegistry.mCount;
            }
        }
        this.mTasks.ensureCapacity(i);
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            RecyclerView recyclerView3 = this.mRecyclerViews.get(i4);
            if (recyclerView3.getWindowVisibility() == 0) {
                LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl2 = recyclerView3.mPrefetchRegistry;
                int abs = Math.abs(layoutPrefetchRegistryImpl2.mPrefetchDy) + Math.abs(layoutPrefetchRegistryImpl2.mPrefetchDx);
                for (int i5 = 0; i5 < layoutPrefetchRegistryImpl2.mCount * 2; i5 += 2) {
                    if (i3 >= this.mTasks.size()) {
                        task2 = new Task();
                        this.mTasks.add(task2);
                    } else {
                        task2 = this.mTasks.get(i3);
                    }
                    int[] iArr2 = layoutPrefetchRegistryImpl2.mPrefetchArray;
                    int i6 = iArr2[i5 + 1];
                    task2.immediate = i6 <= abs;
                    task2.viewVelocity = abs;
                    task2.distanceToItem = i6;
                    task2.view = recyclerView3;
                    task2.position = iArr2[i5];
                    i3++;
                }
            }
        }
        Collections.sort(this.mTasks, sTaskComparator);
        if (this.mTasks.size() <= 0 || (recyclerView = (task = this.mTasks.get(0)).view) == null) {
            return;
        }
        if (task.immediate) {
            j = Long.MAX_VALUE;
        }
        int i7 = task.position;
        if (recyclerView.mChildHelper.getUnfilteredChildCount() > 0) {
            RecyclerView.getChildViewHolderInt(recyclerView.mChildHelper.getUnfilteredChildAt(0));
            throw null;
        }
        RecyclerView.Recycler recycler = recyclerView.mRecycler;
        try {
            recyclerView.onEnterLayoutOrScroll();
            recycler.tryGetViewHolderForPositionByDeadline(i7, j);
            throw null;
        } catch (Throwable th) {
            recyclerView.onExitLayoutOrScroll(false);
            throw th;
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
                RecyclerView recyclerView = this.mRecyclerViews.get(i);
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
