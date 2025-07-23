package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class GridLayoutManager extends LinearLayoutManager {
    public int[] mCachedBorders;
    public final Rect mDecorInsets;
    public boolean mPendingSpanCountChange;
    public final SparseIntArray mPreLayoutSpanIndexCache;
    public final SparseIntArray mPreLayoutSpanSizeCache;
    public View[] mSet;
    public int mSpanCount;
    public SpanSizeLookup mSpanSizeLookup;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DefaultSpanSizeLookup extends SpanSizeLookup {
        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public final int getSpanIndex(int i, int i2) {
            return i % i2;
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public final int getSpanSize(int i) {
            return 1;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class SpanSizeLookup {
        public final SparseIntArray mSpanIndexCache = new SparseIntArray();
        public final SparseIntArray mSpanGroupIndexCache = new SparseIntArray();
        public boolean mCacheSpanIndices = false;

        public final int getSpanGroupIndex(int i, int i2) {
            int spanSize = getSpanSize(i);
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                int spanSize2 = getSpanSize(i5);
                i3 += spanSize2;
                if (i3 == i2) {
                    i4++;
                    i3 = 0;
                } else if (i3 > i2) {
                    i4++;
                    i3 = spanSize2;
                }
            }
            return i3 + spanSize > i2 ? i4 + 1 : i4;
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x004a  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x0059  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0051 -> B:18:0x0056). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0053 -> B:18:0x0056). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x0055 -> B:18:0x0056). Please report as a decompilation issue!!! */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int getSpanIndex(int r8, int r9) {
            /*
                r7 = this;
                int r0 = r7.getSpanSize(r8)
                r1 = 0
                if (r0 != r9) goto L8
                goto L5d
            L8:
                boolean r2 = r7.mCacheSpanIndices
                if (r2 == 0) goto L46
                android.util.SparseIntArray r2 = r7.mSpanIndexCache
                int r3 = r2.size()
                int r3 = r3 + (-1)
                r4 = r1
            L15:
                if (r4 > r3) goto L28
                int r5 = r4 + r3
                int r5 = r5 >>> 1
                int r6 = r2.keyAt(r5)
                if (r6 >= r8) goto L24
                int r4 = r5 + 1
                goto L15
            L24:
                int r5 = r5 + (-1)
                r3 = r5
                goto L15
            L28:
                int r4 = r4 + (-1)
                if (r4 < 0) goto L37
                int r3 = r2.size()
                if (r4 >= r3) goto L37
                int r2 = r2.keyAt(r4)
                goto L38
            L37:
                r2 = -1
            L38:
                if (r2 < 0) goto L46
                android.util.SparseIntArray r3 = r7.mSpanIndexCache
                int r3 = r3.get(r2)
                int r4 = r7.getSpanSize(r2)
                int r4 = r4 + r3
                goto L56
            L46:
                r2 = r1
                r4 = r2
            L48:
                if (r2 >= r8) goto L59
                int r3 = r7.getSpanSize(r2)
                int r4 = r4 + r3
                if (r4 != r9) goto L53
                r4 = r1
                goto L56
            L53:
                if (r4 <= r9) goto L56
                r4 = r3
            L56:
                int r2 = r2 + 1
                goto L48
            L59:
                int r0 = r0 + r4
                if (r0 > r9) goto L5d
                return r4
            L5d:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup.getSpanIndex(int, int):int");
        }

        public abstract int getSpanSize(int i);

        public final void invalidateSpanIndexCache() {
            this.mSpanIndexCache.clear();
        }
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPendingSpanCountChange = false;
        this.mSpanCount = -1;
        this.mPreLayoutSpanSizeCache = new SparseIntArray();
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        setSpanCount(RecyclerView.LayoutManager.getProperties(context, attributeSet, i, i2).spanCount);
    }

    public final void calculateItemBorders(int i) {
        int i2;
        int[] iArr = this.mCachedBorders;
        int i3 = this.mSpanCount;
        if (iArr == null || iArr.length != i3 + 1 || iArr[iArr.length - 1] != i) {
            iArr = new int[i3 + 1];
        }
        int i4 = 0;
        iArr[0] = 0;
        int i5 = i / i3;
        int i6 = i % i3;
        int i7 = 0;
        for (int i8 = 1; i8 <= i3; i8++) {
            i4 += i6;
            if (i4 <= 0 || i3 - i4 >= i6) {
                i2 = i5;
            } else {
                i2 = i5 + 1;
                i4 -= i3;
            }
            i7 += i2;
            iArr[i8] = i7;
        }
        this.mCachedBorders = iArr;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final void collectPrefetchPositionsForLayoutState(RecyclerView.State state, LinearLayoutManager.LayoutState layoutState, GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl) {
        int i;
        int i2 = this.mSpanCount;
        for (int i3 = 0; i3 < this.mSpanCount && (i = layoutState.mCurrentPosition) >= 0 && i < state.getItemCount() && i2 > 0; i3++) {
            int i4 = layoutState.mCurrentPosition;
            layoutPrefetchRegistryImpl.addPosition(i4, Math.max(0, layoutState.mScrollingOffset));
            i2 -= this.mSpanSizeLookup.getSpanSize(i4);
            layoutState.mCurrentPosition += layoutState.mItemDirection;
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollRange(RecyclerView.State state) {
        return computeScrollRange(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollRange(RecyclerView.State state) {
        return computeScrollRange(state);
    }

    public final void ensureViewSet() {
        View[] viewArr = this.mSet;
        if (viewArr == null || viewArr.length != this.mSpanCount) {
            this.mSet = new View[this.mSpanCount];
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final View findReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z, boolean z2) {
        int i;
        int i2 = 0;
        int max = Math.max(getChildCount(), 0);
        if (z2) {
            i2 = max - 1;
            max = -1;
            i = -1;
        } else {
            i = 1;
        }
        int itemCount = state.getItemCount();
        ensureLayoutState();
        int startAfterPadding = this.mOrientationHelper.getStartAfterPadding();
        int endAfterPadding = this.mOrientationHelper.getEndAfterPadding();
        View view = null;
        View view2 = null;
        while (i2 != max) {
            View childAt = getChildAt(i2);
            int position = RecyclerView.LayoutManager.getPosition(childAt);
            if (position >= 0 && position < itemCount && getSpanIndex(position, recycler, state) == 0) {
                if (((RecyclerView.LayoutParams) childAt.getLayoutParams()).mViewHolder.isRemoved()) {
                    if (view2 == null) {
                        view2 = childAt;
                    }
                } else {
                    if (this.mOrientationHelper.getDecoratedStart(childAt) < endAfterPadding && this.mOrientationHelper.getDecoratedEnd(childAt) > startAfterPadding) {
                        return childAt;
                    }
                    if (view == null) {
                        view = childAt;
                    }
                }
            }
            i2 += i;
        }
        return view != null ? view : view2;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return this.mOrientation == 0 ? new LayoutParams(-2, -1) : new LayoutParams(-1, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 1) {
            return state.getItemCount() < 1 ? this.mSpanCount : (getSpanGroupIndex(state.getItemCount() - 1, recycler, state) + 1 != 1 || state.getItemCount() >= this.mSpanCount) ? this.mSpanCount : state.getItemCount();
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return getSpanGroupIndex(state.getItemCount() - 1, recycler, state) + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return getSpanGroupIndex(state.getItemCount() - 1, recycler, state) + 1;
    }

    public final int getSpaceForSpanRange(int i, int i2) {
        if (this.mOrientation != 1 || !isLayoutRTL()) {
            int[] iArr = this.mCachedBorders;
            return iArr[i2 + i] - iArr[i];
        }
        int[] iArr2 = this.mCachedBorders;
        int i3 = this.mSpanCount;
        return iArr2[i3 - i] - iArr2[(i3 - i) - i2];
    }

    public final int getSpanGroupIndex(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!state.mInPreLayout) {
            return this.mSpanSizeLookup.getSpanGroupIndex(i, this.mSpanCount);
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout != -1) {
            return this.mSpanSizeLookup.getSpanGroupIndex(convertPreLayoutPositionToPostLayout, this.mSpanCount);
        }
        RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "Cannot find span size for pre layout position. ", "GridLayoutManager");
        return 0;
    }

    public final int getSpanIndex(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!state.mInPreLayout) {
            SpanSizeLookup spanSizeLookup = this.mSpanSizeLookup;
            int i2 = this.mSpanCount;
            if (!spanSizeLookup.mCacheSpanIndices) {
                return spanSizeLookup.getSpanIndex(i, i2);
            }
            int i3 = spanSizeLookup.mSpanIndexCache.get(i, -1);
            if (i3 != -1) {
                return i3;
            }
            int spanIndex = spanSizeLookup.getSpanIndex(i, i2);
            spanSizeLookup.mSpanIndexCache.put(i, spanIndex);
            return spanIndex;
        }
        int i4 = this.mPreLayoutSpanIndexCache.get(i, -1);
        if (i4 != -1) {
            return i4;
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout == -1) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:", "GridLayoutManager");
            return 0;
        }
        SpanSizeLookup spanSizeLookup2 = this.mSpanSizeLookup;
        int i5 = this.mSpanCount;
        if (!spanSizeLookup2.mCacheSpanIndices) {
            return spanSizeLookup2.getSpanIndex(convertPreLayoutPositionToPostLayout, i5);
        }
        int i6 = spanSizeLookup2.mSpanIndexCache.get(convertPreLayoutPositionToPostLayout, -1);
        if (i6 != -1) {
            return i6;
        }
        int spanIndex2 = spanSizeLookup2.getSpanIndex(convertPreLayoutPositionToPostLayout, i5);
        spanSizeLookup2.mSpanIndexCache.put(convertPreLayoutPositionToPostLayout, spanIndex2);
        return spanIndex2;
    }

    public final int getSpanSize(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!state.mInPreLayout) {
            return this.mSpanSizeLookup.getSpanSize(i);
        }
        int i2 = this.mPreLayoutSpanSizeCache.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout != -1) {
            return this.mSpanSizeLookup.getSpanSize(convertPreLayoutPositionToPostLayout);
        }
        RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:", "GridLayoutManager");
        return 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x008c, code lost:
    
        r22.mFinished = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x008e, code lost:
    
        return;
     */
    @Override // androidx.recyclerview.widget.LinearLayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void layoutChunk(androidx.recyclerview.widget.RecyclerView.Recycler r19, androidx.recyclerview.widget.RecyclerView.State r20, androidx.recyclerview.widget.LinearLayoutManager.LayoutState r21, androidx.recyclerview.widget.LinearLayoutManager.LayoutChunkResult r22) {
        /*
            Method dump skipped, instructions count: 606
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.GridLayoutManager.layoutChunk(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State, androidx.recyclerview.widget.LinearLayoutManager$LayoutState, androidx.recyclerview.widget.LinearLayoutManager$LayoutChunkResult):void");
    }

    public final void measureChild(View view, int i, boolean z) {
        int i2;
        int i3;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect = layoutParams.mDecorInsets;
        int i4 = rect.top + rect.bottom + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        int i5 = rect.left + rect.right + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        int spaceForSpanRange = getSpaceForSpanRange(layoutParams.mSpanIndex, layoutParams.mSpanSize);
        if (this.mOrientation == 1) {
            i3 = RecyclerView.LayoutManager.getChildMeasureSpec(false, spaceForSpanRange, i, i5, ((ViewGroup.MarginLayoutParams) layoutParams).width);
            i2 = RecyclerView.LayoutManager.getChildMeasureSpec(true, this.mOrientationHelper.getTotalSpace(), this.mHeightMode, i4, ((ViewGroup.MarginLayoutParams) layoutParams).height);
        } else {
            int childMeasureSpec = RecyclerView.LayoutManager.getChildMeasureSpec(false, spaceForSpanRange, i, i4, ((ViewGroup.MarginLayoutParams) layoutParams).height);
            int childMeasureSpec2 = RecyclerView.LayoutManager.getChildMeasureSpec(true, this.mOrientationHelper.getTotalSpace(), this.mWidthMode, i5, ((ViewGroup.MarginLayoutParams) layoutParams).width);
            i2 = childMeasureSpec;
            i3 = childMeasureSpec2;
        }
        RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) view.getLayoutParams();
        if (z ? shouldReMeasureChild(view, i3, i2, layoutParams2) : shouldMeasureChild(view, i3, i2, layoutParams2)) {
            view.measure(i3, i2);
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final void onAnchorReady(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.AnchorInfo anchorInfo, int i) {
        updateMeasurements();
        if (state.getItemCount() > 0 && !state.mInPreLayout) {
            boolean z = i == 1;
            int spanIndex = getSpanIndex(anchorInfo.mPosition, recycler, state);
            if (z) {
                while (spanIndex > 0) {
                    int i2 = anchorInfo.mPosition;
                    if (i2 <= 0) {
                        break;
                    }
                    int i3 = i2 - 1;
                    anchorInfo.mPosition = i3;
                    spanIndex = getSpanIndex(i3, recycler, state);
                }
            } else {
                int itemCount = state.getItemCount() - 1;
                int i4 = anchorInfo.mPosition;
                while (i4 < itemCount) {
                    int i5 = i4 + 1;
                    int spanIndex2 = getSpanIndex(i5, recycler, state);
                    if (spanIndex2 <= spanIndex) {
                        break;
                    }
                    i4 = i5;
                    spanIndex = spanIndex2;
                }
                anchorInfo.mPosition = i4;
            }
        }
        ensureViewSet();
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x00c9, code lost:
    
        if (r13 == (r2 > r15)) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00ee, code lost:
    
        if (r13 == (r2 > r8)) goto L70;
     */
    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View onFocusSearchFailed(android.view.View r23, int r24, androidx.recyclerview.widget.RecyclerView.Recycler r25, androidx.recyclerview.widget.RecyclerView.State r26) {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.GridLayoutManager.onFocusSearchFailed(android.view.View, int, androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State):android.view.View");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(recycler, state, accessibilityNodeInfoCompat);
        accessibilityNodeInfoCompat.setClassName(GridView.class.getName());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            onInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfoCompat);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        int spanGroupIndex = getSpanGroupIndex(layoutParams2.mViewHolder.getLayoutPosition(), recycler, state);
        if (this.mOrientation == 0) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, layoutParams2.mSpanIndex, layoutParams2.mSpanSize, spanGroupIndex, 1));
        } else {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, spanGroupIndex, 1, layoutParams2.mSpanIndex, layoutParams2.mSpanSize));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsAdded(int i, int i2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.mSpanGroupIndexCache.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsChanged() {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.mSpanGroupIndexCache.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsMoved(int i, int i2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.mSpanGroupIndexCache.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsRemoved(int i, int i2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.mSpanGroupIndexCache.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.mSpanGroupIndexCache.clear();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.mInPreLayout) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
                int layoutPosition = layoutParams.mViewHolder.getLayoutPosition();
                this.mPreLayoutSpanSizeCache.put(layoutPosition, layoutParams.mSpanSize);
                this.mPreLayoutSpanIndexCache.put(layoutPosition, layoutParams.mSpanIndex);
            }
        }
        super.onLayoutChildren(recycler, state);
        this.mPreLayoutSpanSizeCache.clear();
        this.mPreLayoutSpanIndexCache.clear();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.mPendingSpanCountChange = false;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        updateMeasurements();
        ensureViewSet();
        return super.scrollHorizontallyBy(i, recycler, state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        updateMeasurements();
        ensureViewSet();
        return super.scrollVerticallyBy(i, recycler, state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void setMeasuredDimension(int i, int i2, Rect rect) {
        int chooseSize;
        int chooseSize2;
        if (this.mCachedBorders == null) {
            super.setMeasuredDimension(i, i2, rect);
        }
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        if (this.mOrientation == 1) {
            int height = rect.height() + paddingBottom;
            RecyclerView recyclerView = this.mRecyclerView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            chooseSize2 = RecyclerView.LayoutManager.chooseSize(i2, height, recyclerView.getMinimumHeight());
            int[] iArr = this.mCachedBorders;
            chooseSize = RecyclerView.LayoutManager.chooseSize(i, iArr[iArr.length - 1] + paddingRight, this.mRecyclerView.getMinimumWidth());
        } else {
            int width = rect.width() + paddingRight;
            RecyclerView recyclerView2 = this.mRecyclerView;
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            chooseSize = RecyclerView.LayoutManager.chooseSize(i, width, recyclerView2.getMinimumWidth());
            int[] iArr2 = this.mCachedBorders;
            chooseSize2 = RecyclerView.LayoutManager.chooseSize(i2, iArr2[iArr2.length - 1] + paddingBottom, this.mRecyclerView.getMinimumHeight());
        }
        this.mRecyclerView.setMeasuredDimension(chooseSize, chooseSize2);
    }

    public final void setSpanCount(int i) {
        if (i == this.mSpanCount) {
            return;
        }
        this.mPendingSpanCountChange = true;
        if (i < 1) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Span count should be at least 1. Provided "));
        }
        this.mSpanCount = i;
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final void setStackFromEnd(boolean z) {
        if (z) {
            throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.setStackFromEnd(false);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null && !this.mPendingSpanCountChange;
    }

    public final void updateMeasurements() {
        int paddingBottom;
        int paddingTop;
        if (this.mOrientation == 1) {
            paddingBottom = this.mWidth - getPaddingRight();
            paddingTop = getPaddingLeft();
        } else {
            paddingBottom = this.mHeight - getPaddingBottom();
            paddingTop = getPaddingTop();
        }
        calculateItemBorders(paddingBottom - paddingTop);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LayoutParams extends RecyclerView.LayoutParams {
        public int mSpanIndex;
        public int mSpanSize;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }
    }

    public GridLayoutManager(Context context, int i) {
        super(context);
        this.mPendingSpanCountChange = false;
        this.mSpanCount = -1;
        this.mPreLayoutSpanSizeCache = new SparseIntArray();
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        setSpanCount(i);
    }

    public GridLayoutManager(Context context, int i, int i2, boolean z) {
        super(context, i2, z);
        this.mPendingSpanCountChange = false;
        this.mSpanCount = -1;
        this.mPreLayoutSpanSizeCache = new SparseIntArray();
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        setSpanCount(i);
    }
}
