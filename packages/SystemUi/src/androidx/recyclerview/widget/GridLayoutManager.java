package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.Arrays;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

        /* JADX WARN: Code restructure failed: missing block: B:32:0x004e, code lost:
        
            if (r2 > r10) goto L20;
         */
        /* JADX WARN: Removed duplicated region for block: B:29:0x0045  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x0054  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x004c -> B:21:0x0051). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x004e -> B:20:0x003f). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x004e -> B:21:0x0051). Please report as a decompilation issue!!! */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public int getSpanIndex(int i, int i2) {
            int i3;
            int i4;
            int spanSize = getSpanSize(i);
            if (spanSize == i2) {
                return 0;
            }
            if (this.mCacheSpanIndices) {
                SparseIntArray sparseIntArray = this.mSpanIndexCache;
                i4 = -1;
                int size = sparseIntArray.size() - 1;
                int i5 = 0;
                while (i5 <= size) {
                    int i6 = (i5 + size) >>> 1;
                    if (sparseIntArray.keyAt(i6) < i) {
                        i5 = i6 + 1;
                    } else {
                        size = i6 - 1;
                    }
                }
                int i7 = i5 - 1;
                if (i7 >= 0 && i7 < sparseIntArray.size()) {
                    i4 = sparseIntArray.keyAt(i7);
                }
                if (i4 >= 0) {
                    int spanSize2 = getSpanSize(i4) + sparseIntArray.get(i4);
                    i3 = spanSize2;
                    i4++;
                    if (i4 >= i) {
                        spanSize2 = getSpanSize(i4);
                        i3 += spanSize2;
                        if (i3 == i2) {
                            i3 = 0;
                        }
                        i4++;
                        if (i4 >= i) {
                            if (spanSize + i3 <= i2) {
                                return i3;
                            }
                            return 0;
                        }
                    }
                }
            }
            i3 = 0;
            i4 = 0;
            if (i4 >= i) {
            }
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
        int i = this.mSpanCount;
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            int i3 = layoutState.mCurrentPosition;
            if (!(i3 >= 0 && i3 < state.getItemCount()) || i <= 0) {
                return;
            }
            int i4 = layoutState.mCurrentPosition;
            layoutPrefetchRegistryImpl.addPosition(i4, Math.max(0, layoutState.mScrollingOffset));
            i -= this.mSpanSizeLookup.getSpanSize(i4);
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

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final View findReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z, boolean z2) {
        int i;
        int i2;
        int childCount = getChildCount();
        int i3 = 1;
        if (z2) {
            i2 = getChildCount() - 1;
            i = -1;
            i3 = -1;
        } else {
            i = childCount;
            i2 = 0;
        }
        int itemCount = state.getItemCount();
        ensureLayoutState();
        int startAfterPadding = this.mOrientationHelper.getStartAfterPadding();
        int endAfterPadding = this.mOrientationHelper.getEndAfterPadding();
        View view = null;
        View view2 = null;
        while (i2 != i) {
            View childAt = getChildAt(i2);
            int position = RecyclerView.LayoutManager.getPosition(childAt);
            if (position >= 0 && position < itemCount && getSpanIndex(position, recycler, state) == 0) {
                if (((RecyclerView.LayoutParams) childAt.getLayoutParams()).isItemRemoved()) {
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
            i2 += i3;
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
        IconCompat$$ExternalSyntheticOutline0.m30m("Cannot find span size for pre layout position. ", i, "GridLayoutManager");
        return 0;
    }

    public final int getSpanIndex(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!state.mInPreLayout) {
            SpanSizeLookup spanSizeLookup = this.mSpanSizeLookup;
            int i2 = this.mSpanCount;
            if (!spanSizeLookup.mCacheSpanIndices) {
                return spanSizeLookup.getSpanIndex(i, i2);
            }
            SparseIntArray sparseIntArray = spanSizeLookup.mSpanIndexCache;
            int i3 = sparseIntArray.get(i, -1);
            if (i3 != -1) {
                return i3;
            }
            int spanIndex = spanSizeLookup.getSpanIndex(i, i2);
            sparseIntArray.put(i, spanIndex);
            return spanIndex;
        }
        int i4 = this.mPreLayoutSpanIndexCache.get(i, -1);
        if (i4 != -1) {
            return i4;
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout == -1) {
            IconCompat$$ExternalSyntheticOutline0.m30m("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:", i, "GridLayoutManager");
            return 0;
        }
        SpanSizeLookup spanSizeLookup2 = this.mSpanSizeLookup;
        int i5 = this.mSpanCount;
        if (!spanSizeLookup2.mCacheSpanIndices) {
            return spanSizeLookup2.getSpanIndex(convertPreLayoutPositionToPostLayout, i5);
        }
        SparseIntArray sparseIntArray2 = spanSizeLookup2.mSpanIndexCache;
        int i6 = sparseIntArray2.get(convertPreLayoutPositionToPostLayout, -1);
        if (i6 != -1) {
            return i6;
        }
        int spanIndex2 = spanSizeLookup2.getSpanIndex(convertPreLayoutPositionToPostLayout, i5);
        sparseIntArray2.put(convertPreLayoutPositionToPostLayout, spanIndex2);
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
        IconCompat$$ExternalSyntheticOutline0.m30m("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:", i, "GridLayoutManager");
        return 1;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final void layoutChunk(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.LayoutState layoutState, LinearLayoutManager.LayoutChunkResult layoutChunkResult) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int childMeasureSpec;
        int i10;
        boolean z;
        View next;
        int modeInOther = this.mOrientationHelper.getModeInOther();
        int i11 = 1;
        boolean z2 = modeInOther != 1073741824;
        int i12 = getChildCount() > 0 ? this.mCachedBorders[this.mSpanCount] : 0;
        if (z2) {
            updateMeasurements();
        }
        boolean z3 = layoutState.mItemDirection == 1;
        int i13 = this.mSpanCount;
        if (!z3) {
            i13 = getSpanIndex(layoutState.mCurrentPosition, recycler, state) + getSpanSize(layoutState.mCurrentPosition, recycler, state);
        }
        int i14 = 0;
        while (i14 < this.mSpanCount) {
            int i15 = layoutState.mCurrentPosition;
            if (!(i15 >= 0 && i15 < state.getItemCount()) || i13 <= 0) {
                break;
            }
            int i16 = layoutState.mCurrentPosition;
            int spanSize = getSpanSize(i16, recycler, state);
            if (spanSize > this.mSpanCount) {
                throw new IllegalArgumentException(ConstraintWidget$$ExternalSyntheticOutline0.m19m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("Item at position ", i16, " requires ", spanSize, " spans but GridLayoutManager has only "), this.mSpanCount, " spans."));
            }
            i13 -= spanSize;
            if (i13 < 0 || (next = layoutState.next(recycler)) == null) {
                break;
            }
            this.mSet[i14] = next;
            i14++;
        }
        if (i14 == 0) {
            layoutChunkResult.mFinished = true;
            return;
        }
        if (z3) {
            i = 0;
            i2 = i14;
        } else {
            i = i14 - 1;
            i11 = -1;
            i2 = -1;
        }
        int i17 = 0;
        while (i != i2) {
            View view = this.mSet[i];
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            int spanSize2 = getSpanSize(RecyclerView.LayoutManager.getPosition(view), recycler, state);
            layoutParams.mSpanSize = spanSize2;
            layoutParams.mSpanIndex = i17;
            i17 += spanSize2;
            i += i11;
        }
        float f = 0.0f;
        int i18 = 0;
        for (int i19 = 0; i19 < i14; i19++) {
            View view2 = this.mSet[i19];
            if (layoutState.mScrapList != null) {
                z = false;
                if (z3) {
                    addViewInt(view2, -1, true);
                } else {
                    addViewInt(view2, 0, true);
                }
            } else if (z3) {
                z = false;
                addViewInt(view2, -1, false);
            } else {
                z = false;
                addViewInt(view2, 0, false);
            }
            calculateItemDecorationsForChild(this.mDecorInsets, view2);
            measureChild(view2, modeInOther, z);
            int decoratedMeasurement = this.mOrientationHelper.getDecoratedMeasurement(view2);
            if (decoratedMeasurement > i18) {
                i18 = decoratedMeasurement;
            }
            float decoratedMeasurementInOther = (this.mOrientationHelper.getDecoratedMeasurementInOther(view2) * 1.0f) / ((LayoutParams) view2.getLayoutParams()).mSpanSize;
            if (decoratedMeasurementInOther > f) {
                f = decoratedMeasurementInOther;
            }
        }
        if (z2) {
            calculateItemBorders(Math.max(Math.round(f * this.mSpanCount), i12));
            i18 = 0;
            for (int i20 = 0; i20 < i14; i20++) {
                View view3 = this.mSet[i20];
                measureChild(view3, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS, true);
                int decoratedMeasurement2 = this.mOrientationHelper.getDecoratedMeasurement(view3);
                if (decoratedMeasurement2 > i18) {
                    i18 = decoratedMeasurement2;
                }
            }
        }
        for (int i21 = 0; i21 < i14; i21++) {
            View view4 = this.mSet[i21];
            if (this.mOrientationHelper.getDecoratedMeasurement(view4) != i18) {
                LayoutParams layoutParams2 = (LayoutParams) view4.getLayoutParams();
                Rect rect = layoutParams2.mDecorInsets;
                int i22 = rect.top + rect.bottom + ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin;
                int i23 = rect.left + rect.right + ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin;
                int spaceForSpanRange = getSpaceForSpanRange(layoutParams2.mSpanIndex, layoutParams2.mSpanSize);
                if (this.mOrientation == 1) {
                    i10 = RecyclerView.LayoutManager.getChildMeasureSpec(false, spaceForSpanRange, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS, i23, ((ViewGroup.MarginLayoutParams) layoutParams2).width);
                    childMeasureSpec = View.MeasureSpec.makeMeasureSpec(i18 - i22, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                } else {
                    int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i18 - i23, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                    childMeasureSpec = RecyclerView.LayoutManager.getChildMeasureSpec(false, spaceForSpanRange, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS, i22, ((ViewGroup.MarginLayoutParams) layoutParams2).height);
                    i10 = makeMeasureSpec;
                }
                if (shouldReMeasureChild(view4, i10, childMeasureSpec, (RecyclerView.LayoutParams) view4.getLayoutParams())) {
                    view4.measure(i10, childMeasureSpec);
                }
            }
        }
        layoutChunkResult.mConsumed = i18;
        if (this.mOrientation == 1) {
            if (layoutState.mLayoutDirection == -1) {
                i9 = layoutState.mOffset;
                i8 = i9 - i18;
            } else {
                i8 = layoutState.mOffset;
                i9 = i18 + i8;
            }
            i6 = 0;
            i5 = i8;
            i7 = i9;
            i4 = 0;
        } else {
            if (layoutState.mLayoutDirection == -1) {
                i4 = layoutState.mOffset;
                i3 = i4 - i18;
            } else {
                i3 = layoutState.mOffset;
                i4 = i18 + i3;
            }
            i5 = 0;
            i6 = i3;
            i7 = 0;
        }
        for (int i24 = 0; i24 < i14; i24++) {
            View view5 = this.mSet[i24];
            LayoutParams layoutParams3 = (LayoutParams) view5.getLayoutParams();
            if (this.mOrientation != 1) {
                int paddingTop = getPaddingTop() + this.mCachedBorders[layoutParams3.mSpanIndex];
                i5 = paddingTop;
                i7 = this.mOrientationHelper.getDecoratedMeasurementInOther(view5) + paddingTop;
            } else if (isLayoutRTL()) {
                i4 = getPaddingLeft() + this.mCachedBorders[this.mSpanCount - layoutParams3.mSpanIndex];
                i6 = i4 - this.mOrientationHelper.getDecoratedMeasurementInOther(view5);
            } else {
                i6 = this.mCachedBorders[layoutParams3.mSpanIndex] + getPaddingLeft();
                i4 = this.mOrientationHelper.getDecoratedMeasurementInOther(view5) + i6;
            }
            RecyclerView.LayoutManager.layoutDecoratedWithMargins(view5, i6, i5, i4, i7);
            if (layoutParams3.isItemRemoved() || layoutParams3.isItemChanged()) {
                layoutChunkResult.mIgnoreConsumed = true;
            }
            layoutChunkResult.mFocusable = view5.hasFocusable() | layoutChunkResult.mFocusable;
        }
        Arrays.fill(this.mSet, (Object) null);
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
        View[] viewArr = this.mSet;
        if (viewArr == null || viewArr.length != this.mSpanCount) {
            this.mSet = new View[this.mSpanCount];
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:67:0x00cb, code lost:
    
        if (r13 == (r2 > r15)) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00fb, code lost:
    
        if (r13 == (r2 > r9)) goto L75;
     */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0107  */
    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View onFocusSearchFailed(View view, int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int childCount;
        int i2;
        int i3;
        View view2;
        View view3;
        int i4;
        boolean z;
        RecyclerView.Recycler recycler2 = recycler;
        RecyclerView.State state2 = state;
        View findContainingItemView = findContainingItemView(view);
        View view4 = null;
        if (findContainingItemView == null) {
            return null;
        }
        LayoutParams layoutParams = (LayoutParams) findContainingItemView.getLayoutParams();
        int i5 = layoutParams.mSpanIndex;
        int i6 = layoutParams.mSpanSize + i5;
        if (super.onFocusSearchFailed(view, i, recycler, state) == null) {
            return null;
        }
        if ((convertFocusDirectionToLayoutDirection(i) == 1) != this.mShouldReverseLayout) {
            i3 = getChildCount() - 1;
            childCount = -1;
            i2 = -1;
        } else {
            childCount = getChildCount();
            i2 = 1;
            i3 = 0;
        }
        boolean z2 = this.mOrientation == 1 && isLayoutRTL();
        int spanGroupIndex = getSpanGroupIndex(i3, recycler2, state2);
        int i7 = -1;
        int i8 = -1;
        int i9 = i2;
        int i10 = 0;
        int i11 = 0;
        int i12 = i3;
        View view5 = null;
        while (i12 != childCount) {
            int i13 = childCount;
            int spanGroupIndex2 = getSpanGroupIndex(i12, recycler2, state2);
            View childAt = getChildAt(i12);
            if (childAt == findContainingItemView) {
                break;
            }
            if (!childAt.hasFocusable() || spanGroupIndex2 == spanGroupIndex) {
                LayoutParams layoutParams2 = (LayoutParams) childAt.getLayoutParams();
                int i14 = layoutParams2.mSpanIndex;
                view2 = findContainingItemView;
                int i15 = layoutParams2.mSpanSize + i14;
                if (childAt.hasFocusable() && i14 == i5 && i15 == i6) {
                    return childAt;
                }
                if (!(childAt.hasFocusable() && view4 == null) && (childAt.hasFocusable() || view5 != null)) {
                    view3 = view5;
                    int min = Math.min(i15, i6) - Math.max(i14, i5);
                    if (childAt.hasFocusable()) {
                        if (min <= i10) {
                            if (min == i10) {
                            }
                        }
                    } else if (view4 == null) {
                        i4 = i10;
                        if (!(this.mHorizontalBoundCheck.isViewWithinBoundFlags(childAt) && this.mVerticalBoundCheck.isViewWithinBoundFlags(childAt))) {
                            if (min <= i11) {
                                if (min == i11) {
                                }
                            }
                            z = true;
                            if (z) {
                                if (childAt.hasFocusable()) {
                                    int i16 = layoutParams2.mSpanIndex;
                                    i10 = Math.min(i15, i6) - Math.max(i14, i5);
                                    i8 = i16;
                                    view5 = view3;
                                    view4 = childAt;
                                } else {
                                    int i17 = layoutParams2.mSpanIndex;
                                    i11 = Math.min(i15, i6) - Math.max(i14, i5);
                                    i7 = i17;
                                    i10 = i4;
                                    view5 = childAt;
                                }
                                i12 += i9;
                                recycler2 = recycler;
                                state2 = state;
                                childCount = i13;
                                findContainingItemView = view2;
                            }
                        }
                        z = false;
                        if (z) {
                        }
                    }
                    i4 = i10;
                    z = false;
                    if (z) {
                    }
                } else {
                    view3 = view5;
                }
                i4 = i10;
                z = true;
                if (z) {
                }
            } else {
                if (view4 != null) {
                    break;
                }
                view2 = findContainingItemView;
                view3 = view5;
                i4 = i10;
            }
            i10 = i4;
            view5 = view3;
            i12 += i9;
            recycler2 = recycler;
            state2 = state;
            childCount = i13;
            findContainingItemView = view2;
        }
        return view4 != null ? view4 : view5;
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
        int spanGroupIndex = getSpanGroupIndex(layoutParams2.getViewLayoutPosition(), recycler, state);
        if (this.mOrientation == 0) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, layoutParams2.mSpanIndex, layoutParams2.mSpanSize, spanGroupIndex, 1, false));
        } else {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, spanGroupIndex, 1, layoutParams2.mSpanIndex, layoutParams2.mSpanSize, false));
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
        boolean z = state.mInPreLayout;
        SparseIntArray sparseIntArray = this.mPreLayoutSpanIndexCache;
        SparseIntArray sparseIntArray2 = this.mPreLayoutSpanSizeCache;
        if (z) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
                int viewLayoutPosition = layoutParams.getViewLayoutPosition();
                sparseIntArray2.put(viewLayoutPosition, layoutParams.mSpanSize);
                sparseIntArray.put(viewLayoutPosition, layoutParams.mSpanIndex);
            }
        }
        super.onLayoutChildren(recycler, state);
        sparseIntArray2.clear();
        sparseIntArray.clear();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.mPendingSpanCountChange = false;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        updateMeasurements();
        View[] viewArr = this.mSet;
        if (viewArr == null || viewArr.length != this.mSpanCount) {
            this.mSet = new View[this.mSpanCount];
        }
        return super.scrollHorizontallyBy(i, recycler, state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        updateMeasurements();
        View[] viewArr = this.mSet;
        if (viewArr == null || viewArr.length != this.mSpanCount) {
            this.mSet = new View[this.mSpanCount];
        }
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
            chooseSize2 = RecyclerView.LayoutManager.chooseSize(i2, height, ViewCompat.Api16Impl.getMinimumHeight(recyclerView));
            int[] iArr = this.mCachedBorders;
            chooseSize = RecyclerView.LayoutManager.chooseSize(i, iArr[iArr.length - 1] + paddingRight, ViewCompat.Api16Impl.getMinimumWidth(this.mRecyclerView));
        } else {
            int width = rect.width() + paddingRight;
            RecyclerView recyclerView2 = this.mRecyclerView;
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            chooseSize = RecyclerView.LayoutManager.chooseSize(i, width, ViewCompat.Api16Impl.getMinimumWidth(recyclerView2));
            int[] iArr2 = this.mCachedBorders;
            chooseSize2 = RecyclerView.LayoutManager.chooseSize(i2, iArr2[iArr2.length - 1] + paddingBottom, ViewCompat.Api16Impl.getMinimumHeight(this.mRecyclerView));
        }
        this.mRecyclerView.setMeasuredDimension(chooseSize, chooseSize2);
    }

    public final void setSpanCount(int i) {
        if (i == this.mSpanCount) {
            return;
        }
        this.mPendingSpanCountChange = true;
        if (i < 1) {
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Span count should be at least 1. Provided ", i));
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class LayoutParams extends RecyclerView.LayoutParams {
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
