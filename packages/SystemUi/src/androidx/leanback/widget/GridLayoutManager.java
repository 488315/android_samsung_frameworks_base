package androidx.leanback.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.FocusFinder;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.collection.CircularIntArray;
import androidx.collection.CollectionPlatformUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.leanback.widget.Grid;
import androidx.leanback.widget.ItemAlignment;
import androidx.leanback.widget.ItemAlignmentFacet;
import androidx.leanback.widget.WindowAlignment;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GridLayoutManager extends RecyclerView.LayoutManager {
    public static final Rect sTempRect = new Rect();
    public static final int[] sTwoInts = new int[2];
    public BaseGridView mBaseGridView;
    public ArrayList mChildViewHolderSelectedListeners;
    public final int mChildVisibility;
    public final ViewsStateBundle mChildrenStates;
    public GridLinearSmoothScroller mCurrentSmoothScroller;
    public int[] mDisappearingPositions;
    public int mExtraLayoutSpaceInPreLayout;
    public int mFixedRowSizeSecondary;
    public int mFlag;
    public int mFocusPosition;
    public int mFocusPositionOffset;
    public int mGravity;
    public Grid mGrid;
    public final C02772 mGridProvider;
    public final ItemAlignment mItemAlignment;
    public final int mMaxPendingMoves;
    public int mMaxSizeSecondary;
    public final int[] mMeasuredDimension;
    public int mNumRows;
    public int mNumRowsRequested;
    public int mOrientation;
    public OrientationHelper mOrientationHelper;
    public PendingMoveSmoothScroller mPendingMoveSmoothScroller;
    public int mPositionDeltaInPreLayout;
    public final SparseIntArray mPositionToRowInPostLayout;
    public int mPrimaryScrollExtra;
    public RecyclerView.Recycler mRecycler;
    public final RunnableC02761 mRequestLayoutRunnable;
    public int[] mRowSizeSecondary;
    public int mRowSizeSecondaryRequested;
    public int mSaveContextLevel;
    public int mScrollOffsetSecondary;
    public int mSizePrimary;
    public final float mSmoothScrollSpeedFactor;
    public int mSpacingPrimary;
    public int mSpacingSecondary;
    public RecyclerView.State mState;
    public int mSubFocusPosition;
    public int mVerticalSpacing;
    public final WindowAlignment mWindowAlignment;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.leanback.widget.GridLayoutManager$2 */
    public final class C02772 implements Grid.Provider {
        public C02772() {
        }

        public final void addItem(Object obj, int i, int i2, int i3) {
            int i4;
            int i5;
            PendingMoveSmoothScroller pendingMoveSmoothScroller;
            int i6;
            View view = (View) obj;
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            if (i3 == Integer.MIN_VALUE || i3 == Integer.MAX_VALUE) {
                boolean z = gridLayoutManager.mGrid.mReversedFlow;
                WindowAlignment windowAlignment = gridLayoutManager.mWindowAlignment;
                if (z) {
                    WindowAlignment.Axis axis = windowAlignment.mMainAxis;
                    i3 = axis.mSize - axis.mPaddingMax;
                } else {
                    i3 = windowAlignment.mMainAxis.mPaddingMin;
                }
            }
            if (!gridLayoutManager.mGrid.mReversedFlow) {
                i5 = i + i3;
                i4 = i3;
            } else {
                i4 = i3 - i;
                i5 = i3;
            }
            int rowStartSecondary = (gridLayoutManager.getRowStartSecondary(i2) + gridLayoutManager.mWindowAlignment.mSecondAxis.mPaddingMin) - gridLayoutManager.mScrollOffsetSecondary;
            gridLayoutManager.mChildrenStates.getClass();
            GridLayoutManager.this.layoutChild(view, i2, i4, i5, rowStartSecondary);
            if (!gridLayoutManager.mState.mInPreLayout) {
                gridLayoutManager.updateScrollLimits();
            }
            if ((gridLayoutManager.mFlag & 3) == 1 || (pendingMoveSmoothScroller = gridLayoutManager.mPendingMoveSmoothScroller) == null) {
                return;
            }
            boolean z2 = pendingMoveSmoothScroller.mStaggeredGrid;
            GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
            if (z2 && (i6 = pendingMoveSmoothScroller.mPendingMoves) != 0) {
                pendingMoveSmoothScroller.mPendingMoves = gridLayoutManager2.processSelectionMoves(i6, true);
            }
            int i7 = pendingMoveSmoothScroller.mPendingMoves;
            if (i7 == 0 || ((i7 > 0 && gridLayoutManager2.hasCreatedLastItem()) || (pendingMoveSmoothScroller.mPendingMoves < 0 && gridLayoutManager2.hasCreatedFirstItem()))) {
                pendingMoveSmoothScroller.mTargetPosition = gridLayoutManager2.mFocusPosition;
                pendingMoveSmoothScroller.stop();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x0065  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x0087  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x00a1  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x009d A[SYNTHETIC] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:39:0x009d -> B:19:0x009f). Please report as a decompilation issue!!! */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final int createItem(int i, boolean z, Object[] objArr, boolean z2) {
            int i2;
            int i3;
            View findViewByPosition;
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            View viewForPosition = gridLayoutManager.mRecycler.getViewForPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
            LayoutParams layoutParams = (LayoutParams) viewForPosition.getLayoutParams();
            gridLayoutManager.mBaseGridView.getChildViewHolder(viewForPosition);
            View view = null;
            layoutParams.mAlignmentFacet = null;
            if (!((LayoutParams) viewForPosition.getLayoutParams()).isItemRemoved()) {
                if (z2) {
                    if (z) {
                        gridLayoutManager.addViewInt(viewForPosition, -1, true);
                    } else {
                        gridLayoutManager.addViewInt(viewForPosition, 0, true);
                    }
                } else if (z) {
                    gridLayoutManager.addViewInt(viewForPosition, -1, false);
                } else {
                    gridLayoutManager.addViewInt(viewForPosition, 0, false);
                }
                int i4 = gridLayoutManager.mChildVisibility;
                if (i4 != -1) {
                    viewForPosition.setVisibility(i4);
                }
                PendingMoveSmoothScroller pendingMoveSmoothScroller = gridLayoutManager.mPendingMoveSmoothScroller;
                if (pendingMoveSmoothScroller != null && !pendingMoveSmoothScroller.mStaggeredGrid && (i2 = pendingMoveSmoothScroller.mPendingMoves) != 0) {
                    GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
                    if (i2 > 0) {
                        i3 = gridLayoutManager2.mFocusPosition;
                        int i5 = gridLayoutManager2.mNumRows;
                        i3 += i5;
                        while (pendingMoveSmoothScroller.mPendingMoves != 0 && (findViewByPosition = pendingMoveSmoothScroller.findViewByPosition(i3)) != null) {
                            gridLayoutManager2.getClass();
                            if (findViewByPosition.getVisibility() != 0 && (!gridLayoutManager2.hasFocus() || findViewByPosition.hasFocusable())) {
                                gridLayoutManager2.mFocusPosition = i3;
                                gridLayoutManager2.mSubFocusPosition = 0;
                                int i6 = pendingMoveSmoothScroller.mPendingMoves;
                                if (i6 > 0) {
                                    pendingMoveSmoothScroller.mPendingMoves = i6 - 1;
                                } else {
                                    pendingMoveSmoothScroller.mPendingMoves = i6 + 1;
                                }
                                view = findViewByPosition;
                            }
                            if (pendingMoveSmoothScroller.mPendingMoves <= 0) {
                                i5 = gridLayoutManager2.mNumRows;
                                i3 += i5;
                                while (pendingMoveSmoothScroller.mPendingMoves != 0) {
                                    gridLayoutManager2.getClass();
                                    if (findViewByPosition.getVisibility() != 0 && (!gridLayoutManager2.hasFocus() || findViewByPosition.hasFocusable())) {
                                    }
                                    if (pendingMoveSmoothScroller.mPendingMoves <= 0) {
                                        i3 -= gridLayoutManager2.mNumRows;
                                    }
                                }
                            }
                        }
                        if (view != null && gridLayoutManager2.hasFocus()) {
                            gridLayoutManager2.mFlag |= 32;
                            view.requestFocus();
                            gridLayoutManager2.mFlag &= -33;
                        }
                    } else {
                        i3 = gridLayoutManager2.mFocusPosition - gridLayoutManager2.mNumRows;
                        while (pendingMoveSmoothScroller.mPendingMoves != 0) {
                        }
                        if (view != null) {
                            gridLayoutManager2.mFlag |= 32;
                            view.requestFocus();
                            gridLayoutManager2.mFlag &= -33;
                        }
                    }
                }
                int subPositionByView = GridLayoutManager.getSubPositionByView(viewForPosition, viewForPosition.findFocus());
                int i7 = gridLayoutManager.mFlag;
                if ((i7 & 3) != 1) {
                    if (i == gridLayoutManager.mFocusPosition && subPositionByView == gridLayoutManager.mSubFocusPosition && gridLayoutManager.mPendingMoveSmoothScroller == null) {
                        gridLayoutManager.dispatchChildSelected();
                    }
                } else if ((i7 & 4) == 0) {
                    int i8 = i7 & 16;
                    if (i8 == 0 && i == gridLayoutManager.mFocusPosition && subPositionByView == gridLayoutManager.mSubFocusPosition) {
                        gridLayoutManager.dispatchChildSelected();
                    } else if (i8 != 0 && i >= gridLayoutManager.mFocusPosition && viewForPosition.hasFocusable()) {
                        gridLayoutManager.mFocusPosition = i;
                        gridLayoutManager.mSubFocusPosition = subPositionByView;
                        gridLayoutManager.mFlag &= -17;
                        gridLayoutManager.dispatchChildSelected();
                    }
                }
                gridLayoutManager.measureChild(viewForPosition);
            }
            objArr[0] = viewForPosition;
            return gridLayoutManager.mOrientation == 0 ? GridLayoutManager.getDecoratedMeasuredWidthWithMargin(viewForPosition) : GridLayoutManager.getDecoratedMeasuredHeightWithMargin(viewForPosition);
        }

        public final int getCount() {
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            return gridLayoutManager.mState.getItemCount() + gridLayoutManager.mPositionDeltaInPreLayout;
        }

        public final int getEdge(int i) {
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            View findViewByPosition = gridLayoutManager.findViewByPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
            return (gridLayoutManager.mFlag & 262144) != 0 ? gridLayoutManager.mOrientationHelper.getDecoratedEnd(findViewByPosition) : gridLayoutManager.mOrientationHelper.getDecoratedStart(findViewByPosition);
        }

        public final int getSize(int i) {
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            View findViewByPosition = gridLayoutManager.findViewByPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
            Rect rect = GridLayoutManager.sTempRect;
            gridLayoutManager.getDecoratedBoundsWithMargins(rect, findViewByPosition);
            return gridLayoutManager.mOrientation == 0 ? rect.width() : rect.height();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class GridLinearSmoothScroller extends LinearSmoothScroller {
        public boolean mSkipOnStopInternal;

        public GridLinearSmoothScroller() {
            super(GridLayoutManager.this.mBaseGridView.getContext());
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        public final float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return (25.0f / displayMetrics.densityDpi) * GridLayoutManager.this.mSmoothScrollSpeedFactor;
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        public final int calculateTimeForScrolling(int i) {
            int calculateTimeForScrolling = super.calculateTimeForScrolling(i);
            int i2 = GridLayoutManager.this.mWindowAlignment.mMainAxis.mSize;
            if (i2 <= 0) {
                return calculateTimeForScrolling;
            }
            float f = (30.0f / i2) * i;
            return ((float) calculateTimeForScrolling) < f ? (int) f : calculateTimeForScrolling;
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller, androidx.recyclerview.widget.RecyclerView.SmoothScroller
        public final void onStop() {
            super.onStop();
            if (!this.mSkipOnStopInternal) {
                onStopInternal();
            }
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            if (gridLayoutManager.mCurrentSmoothScroller == this) {
                gridLayoutManager.mCurrentSmoothScroller = null;
            }
            if (gridLayoutManager.mPendingMoveSmoothScroller == this) {
                gridLayoutManager.mPendingMoveSmoothScroller = null;
            }
        }

        public void onStopInternal() {
            View findViewByPosition = findViewByPosition(this.mTargetPosition);
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            if (findViewByPosition == null) {
                int i = this.mTargetPosition;
                if (i >= 0) {
                    gridLayoutManager.scrollToSelection(i, false);
                    return;
                }
                return;
            }
            int i2 = gridLayoutManager.mFocusPosition;
            int i3 = this.mTargetPosition;
            if (i2 != i3) {
                gridLayoutManager.mFocusPosition = i3;
            }
            if (gridLayoutManager.hasFocus()) {
                gridLayoutManager.mFlag |= 32;
                findViewByPosition.requestFocus();
                gridLayoutManager.mFlag &= -33;
            }
            gridLayoutManager.dispatchChildSelected();
            gridLayoutManager.dispatchChildSelectedAndPositioned();
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller, androidx.recyclerview.widget.RecyclerView.SmoothScroller
        public final void onTargetFound(View view, RecyclerView.SmoothScroller.Action action) {
            int i;
            int i2;
            int[] iArr = GridLayoutManager.sTwoInts;
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            if (gridLayoutManager.getScrollPosition(view, null, iArr)) {
                if (gridLayoutManager.mOrientation == 0) {
                    i = iArr[0];
                    i2 = iArr[1];
                } else {
                    i = iArr[1];
                    i2 = iArr[0];
                }
                action.update(i, i2, calculateTimeForDeceleration((int) Math.sqrt((i2 * i2) + (i * i))), this.mDecelerateInterpolator);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class LayoutParams extends RecyclerView.LayoutParams {
        public int[] mAlignMultiple;
        public int mAlignX;
        public int mAlignY;
        public ItemAlignmentFacet mAlignmentFacet;
        public int mBottomInset;
        public int mLeftInset;
        public int mRightInset;
        public int mTopInset;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((RecyclerView.LayoutParams) layoutParams);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PendingMoveSmoothScroller extends GridLinearSmoothScroller {
        public int mPendingMoves;
        public final boolean mStaggeredGrid;

        public PendingMoveSmoothScroller(int i, boolean z) {
            super();
            this.mPendingMoves = i;
            this.mStaggeredGrid = z;
            this.mTargetPosition = -2;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller
        public final PointF computeScrollVectorForPosition(int i) {
            int i2 = this.mPendingMoves;
            if (i2 == 0) {
                return null;
            }
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            int i3 = ((gridLayoutManager.mFlag & 262144) == 0 ? i2 >= 0 : i2 <= 0) ? 1 : -1;
            return gridLayoutManager.mOrientation == 0 ? new PointF(i3, 0.0f) : new PointF(0.0f, i3);
        }

        @Override // androidx.leanback.widget.GridLayoutManager.GridLinearSmoothScroller
        public final void onStopInternal() {
            super.onStopInternal();
            this.mPendingMoves = 0;
            View findViewByPosition = findViewByPosition(this.mTargetPosition);
            if (findViewByPosition != null) {
                GridLayoutManager.this.scrollToView(findViewByPosition, true);
            }
        }
    }

    public GridLayoutManager() {
        this(null);
    }

    public static int getAdapterPositionByView(View view) {
        LayoutParams layoutParams;
        if (view == null || (layoutParams = (LayoutParams) view.getLayoutParams()) == null || layoutParams.isItemRemoved()) {
            return -1;
        }
        return layoutParams.mViewHolder.getAbsoluteAdapterPosition();
    }

    public static int getDecoratedMeasuredHeightWithMargin(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        return RecyclerView.LayoutManager.getDecoratedMeasuredHeight(view) + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
    }

    public static int getDecoratedMeasuredWidthWithMargin(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        return RecyclerView.LayoutManager.getDecoratedMeasuredWidth(view) + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
    }

    public static int getSubPositionByView(View view, View view2) {
        ItemAlignmentFacet itemAlignmentFacet;
        if (view == null || view2 == null || (itemAlignmentFacet = ((LayoutParams) view.getLayoutParams()).mAlignmentFacet) == null) {
            return 0;
        }
        ItemAlignmentFacet.ItemAlignmentDef[] itemAlignmentDefArr = itemAlignmentFacet.mAlignmentDefs;
        if (itemAlignmentDefArr.length <= 1) {
            return 0;
        }
        while (view2 != view) {
            int id = view2.getId();
            if (id != -1) {
                for (int i = 1; i < itemAlignmentDefArr.length; i++) {
                    ItemAlignmentFacet.ItemAlignmentDef itemAlignmentDef = itemAlignmentDefArr[i];
                    int i2 = itemAlignmentDef.mFocusViewId;
                    if (i2 == -1) {
                        i2 = itemAlignmentDef.mViewId;
                    }
                    if (i2 == id) {
                        return i;
                    }
                }
            }
            view2 = (View) view2.getParent();
        }
        return 0;
    }

    public final void appendVisibleItems() {
        int i;
        Grid grid = this.mGrid;
        if ((this.mFlag & 262144) != 0) {
            i = 0 - this.mExtraLayoutSpaceInPreLayout;
        } else {
            i = this.mExtraLayoutSpaceInPreLayout + this.mSizePrimary + 0;
        }
        grid.appendVisibleItems(i, false);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean canScrollHorizontally() {
        return this.mOrientation == 0 || this.mNumRows > 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean canScrollVertically() {
        return this.mOrientation == 1 || this.mNumRows > 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void collectAdjacentPrefetchPositions(int i, int i2, RecyclerView.State state, GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl) {
        try {
            saveContext(null, state);
            if (this.mOrientation != 0) {
                i = i2;
            }
            if (getChildCount() != 0 && i != 0) {
                int i3 = 0;
                if (i >= 0) {
                    i3 = 0 + this.mSizePrimary;
                }
                this.mGrid.collectAdjacentPrefetchPositions(i3, i, layoutPrefetchRegistryImpl);
            }
        } finally {
            leaveContext();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void collectInitialPrefetchPositions(int i, GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl) {
        int i2 = this.mBaseGridView.mInitialPrefetchItemCount;
        if (i == 0 || i2 == 0) {
            return;
        }
        int max = Math.max(0, Math.min(this.mFocusPosition - ((i2 - 1) / 2), i - i2));
        for (int i3 = max; i3 < i && i3 < max + i2; i3++) {
            layoutPrefetchRegistryImpl.addPosition(i3, 0);
        }
    }

    public final void dispatchChildSelected() {
        ArrayList arrayList = this.mChildViewHolderSelectedListeners;
        if (arrayList != null && arrayList.size() > 0) {
            int i = this.mFocusPosition;
            View findViewByPosition = i == -1 ? null : findViewByPosition(i);
            if (findViewByPosition != null) {
                RecyclerView.ViewHolder childViewHolder = this.mBaseGridView.getChildViewHolder(findViewByPosition);
                BaseGridView baseGridView = this.mBaseGridView;
                int i2 = this.mFocusPosition;
                ArrayList arrayList2 = this.mChildViewHolderSelectedListeners;
                if (arrayList2 != null) {
                    int size = arrayList2.size();
                    while (true) {
                        size--;
                        if (size < 0) {
                            break;
                        } else {
                            ((OnChildViewHolderSelectedListener) this.mChildViewHolderSelectedListeners.get(size)).onChildViewHolderSelected(baseGridView, childViewHolder, i2);
                        }
                    }
                }
            } else {
                BaseGridView baseGridView2 = this.mBaseGridView;
                ArrayList arrayList3 = this.mChildViewHolderSelectedListeners;
                if (arrayList3 != null) {
                    int size2 = arrayList3.size();
                    while (true) {
                        size2--;
                        if (size2 < 0) {
                            break;
                        } else {
                            ((OnChildViewHolderSelectedListener) this.mChildViewHolderSelectedListeners.get(size2)).onChildViewHolderSelected(baseGridView2, null, -1);
                        }
                    }
                }
            }
            if ((this.mFlag & 3) == 1 || this.mBaseGridView.isLayoutRequested()) {
                return;
            }
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                if (getChildAt(i3).isLayoutRequested()) {
                    BaseGridView baseGridView3 = this.mBaseGridView;
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.postOnAnimation(baseGridView3, this.mRequestLayoutRunnable);
                    return;
                }
            }
        }
    }

    public final void dispatchChildSelectedAndPositioned() {
        ArrayList arrayList = this.mChildViewHolderSelectedListeners;
        if (!(arrayList != null && arrayList.size() > 0)) {
            return;
        }
        int i = this.mFocusPosition;
        View findViewByPosition = i == -1 ? null : findViewByPosition(i);
        if (findViewByPosition != null) {
            this.mBaseGridView.getChildViewHolder(findViewByPosition);
            ArrayList arrayList2 = this.mChildViewHolderSelectedListeners;
            if (arrayList2 == null) {
                return;
            }
            int size = arrayList2.size();
            while (true) {
                size--;
                if (size < 0) {
                    return;
                } else {
                    ((OnChildViewHolderSelectedListener) this.mChildViewHolderSelectedListeners.get(size)).getClass();
                }
            }
        } else {
            ArrayList arrayList3 = this.mChildViewHolderSelectedListeners;
            if (arrayList3 == null) {
                return;
            }
            int size2 = arrayList3.size();
            while (true) {
                size2--;
                if (size2 < 0) {
                    return;
                } else {
                    ((OnChildViewHolderSelectedListener) this.mChildViewHolderSelectedListeners.get(size2)).getClass();
                }
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Grid grid;
        return (this.mOrientation != 1 || (grid = this.mGrid) == null) ? super.getColumnCountForAccessibility(recycler, state) : grid.mNumRows;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int getDecoratedBottom(View view) {
        return (((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.bottom + view.getBottom()) - ((LayoutParams) view.getLayoutParams()).mBottomInset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void getDecoratedBoundsWithMargins(Rect rect, View view) {
        super.getDecoratedBoundsWithMargins(rect, view);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        rect.left += layoutParams.mLeftInset;
        rect.top += layoutParams.mTopInset;
        rect.right -= layoutParams.mRightInset;
        rect.bottom -= layoutParams.mBottomInset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int getDecoratedLeft(View view) {
        return (view.getLeft() - ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.left) + ((LayoutParams) view.getLayoutParams()).mLeftInset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int getDecoratedRight(View view) {
        return (((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.right + view.getRight()) - ((LayoutParams) view.getLayoutParams()).mRightInset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int getDecoratedTop(View view) {
        return (view.getTop() - ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.top) + ((LayoutParams) view.getLayoutParams()).mTopInset;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0035, code lost:
    
        if (r10 != 130) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x003d, code lost:
    
        if ((r9.mFlag & 524288) == 0) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0043, code lost:
    
        if ((r9.mFlag & 524288) == 0) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0018, code lost:
    
        if (r10 != 130) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:?, code lost:
    
        return 3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getMovement(int i) {
        int i2 = this.mOrientation;
        if (i2 != 0) {
            if (i2 == 1) {
                if (i != 17) {
                    if (i == 33) {
                        return 0;
                    }
                    if (i != 66) {
                    }
                }
            }
            return 17;
        }
        if (i != 17) {
            if (i != 33) {
                if (i == 66) {
                    if ((this.mFlag & 262144) != 0) {
                        return 0;
                    }
                }
            }
            return 2;
        }
        if ((this.mFlag & 262144) == 0) {
            return 0;
        }
        return 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Grid grid;
        return (this.mOrientation != 0 || (grid = this.mGrid) == null) ? super.getRowCountForAccessibility(recycler, state) : grid.mNumRows;
    }

    public final int getRowSizeSecondary(int i) {
        int i2 = this.mFixedRowSizeSecondary;
        if (i2 != 0) {
            return i2;
        }
        int[] iArr = this.mRowSizeSecondary;
        if (iArr == null) {
            return 0;
        }
        return iArr[i];
    }

    public final int getRowStartSecondary(int i) {
        int i2 = 0;
        if ((this.mFlag & 524288) != 0) {
            for (int i3 = this.mNumRows - 1; i3 > i; i3--) {
                i2 += getRowSizeSecondary(i3) + this.mSpacingSecondary;
            }
            return i2;
        }
        int i4 = 0;
        while (i2 < i) {
            i4 += getRowSizeSecondary(i2) + this.mSpacingSecondary;
            i2++;
        }
        return i4;
    }

    public final boolean getScrollPosition(View view, View view2, int[] iArr) {
        int top;
        int i;
        int left;
        int i2;
        int subPositionByView;
        WindowAlignment windowAlignment = this.mWindowAlignment;
        WindowAlignment.Axis axis = windowAlignment.mMainAxis;
        if (this.mOrientation == 0) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.getClass();
            top = view.getLeft() + layoutParams.mLeftInset;
            i = layoutParams.mAlignX;
        } else {
            LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
            layoutParams2.getClass();
            top = view.getTop() + layoutParams2.mTopInset;
            i = layoutParams2.mAlignY;
        }
        int scroll = axis.getScroll(top + i);
        if (view2 != null && (subPositionByView = getSubPositionByView(view, view2)) != 0) {
            int[] iArr2 = ((LayoutParams) view.getLayoutParams()).mAlignMultiple;
            scroll += iArr2[subPositionByView] - iArr2[0];
        }
        if (this.mOrientation == 0) {
            LayoutParams layoutParams3 = (LayoutParams) view.getLayoutParams();
            layoutParams3.getClass();
            left = view.getTop() + layoutParams3.mTopInset;
            i2 = layoutParams3.mAlignY;
        } else {
            LayoutParams layoutParams4 = (LayoutParams) view.getLayoutParams();
            layoutParams4.getClass();
            left = view.getLeft() + layoutParams4.mLeftInset;
            i2 = layoutParams4.mAlignX;
        }
        int scroll2 = windowAlignment.mSecondAxis.getScroll(left + i2);
        int i3 = scroll + this.mPrimaryScrollExtra;
        if (i3 == 0 && scroll2 == 0) {
            iArr[0] = 0;
            iArr[1] = 0;
            return false;
        }
        iArr[0] = i3;
        iArr[1] = scroll2;
        return true;
    }

    public final int getSizeSecondary() {
        int i = (this.mFlag & 524288) != 0 ? 0 : this.mNumRows - 1;
        return getRowSizeSecondary(i) + getRowStartSecondary(i);
    }

    public final boolean hasCreatedFirstItem() {
        return getItemCount() == 0 || this.mBaseGridView.findViewHolderForAdapterPosition(0) != null;
    }

    public final boolean hasCreatedLastItem() {
        int itemCount = getItemCount();
        return itemCount == 0 || this.mBaseGridView.findViewHolderForAdapterPosition(itemCount - 1) != null;
    }

    public final boolean isItemFullyVisible(int i) {
        RecyclerView.ViewHolder findViewHolderForAdapterPosition = this.mBaseGridView.findViewHolderForAdapterPosition(i);
        if (findViewHolderForAdapterPosition == null) {
            return false;
        }
        View view = findViewHolderForAdapterPosition.itemView;
        return view.getLeft() >= 0 && view.getRight() <= this.mBaseGridView.getWidth() && view.getTop() >= 0 && view.getBottom() <= this.mBaseGridView.getHeight();
    }

    public final void layoutChild(View view, int i, int i2, int i3, int i4) {
        int rowSizeSecondary;
        int decoratedMeasuredHeightWithMargin = this.mOrientation == 0 ? getDecoratedMeasuredHeightWithMargin(view) : getDecoratedMeasuredWidthWithMargin(view);
        int i5 = this.mFixedRowSizeSecondary;
        if (i5 > 0) {
            decoratedMeasuredHeightWithMargin = Math.min(decoratedMeasuredHeightWithMargin, i5);
        }
        int i6 = this.mGravity;
        int i7 = i6 & 112;
        int absoluteGravity = (this.mFlag & 786432) != 0 ? Gravity.getAbsoluteGravity(i6 & 8388615, 1) : i6 & 7;
        int i8 = this.mOrientation;
        if ((i8 != 0 || i7 != 48) && (i8 != 1 || absoluteGravity != 3)) {
            if ((i8 == 0 && i7 == 80) || (i8 == 1 && absoluteGravity == 5)) {
                rowSizeSecondary = getRowSizeSecondary(i) - decoratedMeasuredHeightWithMargin;
            } else if ((i8 == 0 && i7 == 16) || (i8 == 1 && absoluteGravity == 1)) {
                rowSizeSecondary = (getRowSizeSecondary(i) - decoratedMeasuredHeightWithMargin) / 2;
            }
            i4 += rowSizeSecondary;
        }
        int i9 = decoratedMeasuredHeightWithMargin + i4;
        if (this.mOrientation != 0) {
            int i10 = i4;
            i4 = i2;
            i2 = i10;
            i9 = i3;
            i3 = i9;
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        RecyclerView.LayoutManager.layoutDecoratedWithMargins(view, i2, i4, i3, i9);
        Rect rect = sTempRect;
        super.getDecoratedBoundsWithMargins(rect, view);
        int i11 = i2 - rect.left;
        int i12 = i4 - rect.top;
        int i13 = rect.right - i3;
        int i14 = rect.bottom - i9;
        layoutParams.mLeftInset = i11;
        layoutParams.mTopInset = i12;
        layoutParams.mRightInset = i13;
        layoutParams.mBottomInset = i14;
        LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
        ItemAlignmentFacet itemAlignmentFacet = layoutParams2.mAlignmentFacet;
        ItemAlignment itemAlignment = this.mItemAlignment;
        if (itemAlignmentFacet == null) {
            ItemAlignment.Axis axis = itemAlignment.horizontal;
            layoutParams2.mAlignX = ItemAlignmentFacetHelper.getAlignmentPosition(view, axis, axis.mOrientation);
            ItemAlignment.Axis axis2 = itemAlignment.vertical;
            layoutParams2.mAlignY = ItemAlignmentFacetHelper.getAlignmentPosition(view, axis2, axis2.mOrientation);
            return;
        }
        int i15 = this.mOrientation;
        int[] iArr = layoutParams2.mAlignMultiple;
        ItemAlignmentFacet.ItemAlignmentDef[] itemAlignmentDefArr = itemAlignmentFacet.mAlignmentDefs;
        if (iArr == null || iArr.length != itemAlignmentDefArr.length) {
            layoutParams2.mAlignMultiple = new int[itemAlignmentDefArr.length];
        }
        for (int i16 = 0; i16 < itemAlignmentDefArr.length; i16++) {
            layoutParams2.mAlignMultiple[i16] = ItemAlignmentFacetHelper.getAlignmentPosition(view, itemAlignmentDefArr[i16], i15);
        }
        if (i15 == 0) {
            layoutParams2.mAlignX = layoutParams2.mAlignMultiple[0];
        } else {
            layoutParams2.mAlignY = layoutParams2.mAlignMultiple[0];
        }
        if (this.mOrientation == 0) {
            ItemAlignment.Axis axis3 = itemAlignment.vertical;
            layoutParams2.mAlignY = ItemAlignmentFacetHelper.getAlignmentPosition(view, axis3, axis3.mOrientation);
        } else {
            ItemAlignment.Axis axis4 = itemAlignment.horizontal;
            layoutParams2.mAlignX = ItemAlignmentFacetHelper.getAlignmentPosition(view, axis4, axis4.mOrientation);
        }
    }

    public final void leaveContext() {
        int i = this.mSaveContextLevel - 1;
        this.mSaveContextLevel = i;
        if (i == 0) {
            this.mRecycler = null;
            this.mState = null;
            this.mPositionDeltaInPreLayout = 0;
            this.mExtraLayoutSpaceInPreLayout = 0;
        }
    }

    public final void measureChild(View view) {
        int i;
        int i2;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect = sTempRect;
        calculateItemDecorationsForChild(rect, view);
        int i3 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + rect.left + rect.right;
        int i4 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + rect.top + rect.bottom;
        int makeMeasureSpec = this.mRowSizeSecondaryRequested == -2 ? View.MeasureSpec.makeMeasureSpec(0, 0) : View.MeasureSpec.makeMeasureSpec(this.mFixedRowSizeSecondary, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        if (this.mOrientation == 0) {
            i2 = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, 0), i3, ((ViewGroup.MarginLayoutParams) layoutParams).width);
            i = ViewGroup.getChildMeasureSpec(makeMeasureSpec, i4, ((ViewGroup.MarginLayoutParams) layoutParams).height);
        } else {
            int childMeasureSpec = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, 0), i4, ((ViewGroup.MarginLayoutParams) layoutParams).height);
            int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(makeMeasureSpec, i3, ((ViewGroup.MarginLayoutParams) layoutParams).width);
            i = childMeasureSpec;
            i2 = childMeasureSpec2;
        }
        view.measure(i2, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onAdapterChanged(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            this.mGrid = null;
            this.mRowSizeSecondary = null;
            this.mFlag &= KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN;
            this.mFocusPosition = -1;
            this.mFocusPositionOffset = 0;
            this.mChildrenStates.getClass();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00ce  */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onAddFocusables(RecyclerView recyclerView, ArrayList arrayList, int i, int i2) {
        int i3;
        View childAt;
        BaseGridView baseGridView;
        View findContainingItemView;
        if ((this.mFlag & 32768) != 0) {
            return true;
        }
        if (!recyclerView.hasFocus()) {
            int size = arrayList.size();
            View findViewByPosition = findViewByPosition(this.mFocusPosition);
            if (findViewByPosition != null) {
                findViewByPosition.addFocusables(arrayList, i, i2);
            }
            if (arrayList.size() == size && recyclerView.isFocusable()) {
                arrayList.add(recyclerView);
            }
        } else {
            if (this.mPendingMoveSmoothScroller != null) {
                return true;
            }
            int movement = getMovement(i);
            View findFocus = recyclerView.findFocus();
            if (findFocus != null && (baseGridView = this.mBaseGridView) != null && findFocus != baseGridView && (findContainingItemView = findContainingItemView(findFocus)) != null) {
                int childCount = getChildCount();
                i3 = 0;
                while (i3 < childCount) {
                    if (getChildAt(i3) == findContainingItemView) {
                        break;
                    }
                    i3++;
                }
            }
            i3 = -1;
            int adapterPositionByView = getAdapterPositionByView(getChildAt(i3));
            View findViewByPosition2 = adapterPositionByView == -1 ? null : findViewByPosition(adapterPositionByView);
            if (findViewByPosition2 != null) {
                findViewByPosition2.addFocusables(arrayList, i, i2);
            }
            if (this.mGrid == null || getChildCount() == 0) {
                return true;
            }
            if ((movement == 3 || movement == 2) && this.mGrid.mNumRows <= 1) {
                return true;
            }
            Grid grid = this.mGrid;
            int i4 = (grid == null || findViewByPosition2 == null) ? -1 : grid.getLocation(adapterPositionByView).row;
            int size2 = arrayList.size();
            int i5 = (movement == 1 || movement == 3) ? 1 : -1;
            int childCount2 = i5 > 0 ? getChildCount() - 1 : 0;
            int childCount3 = i3 == -1 ? i5 > 0 ? 0 : getChildCount() - 1 : i3 + i5;
            while (true) {
                if (i5 > 0) {
                    if (childCount3 > childCount2) {
                        break;
                    }
                    childAt = getChildAt(childCount3);
                    if (childAt.getVisibility() == 0 && childAt.hasFocusable()) {
                        if (findViewByPosition2 != null) {
                            childAt.addFocusables(arrayList, i, i2);
                            if (arrayList.size() > size2) {
                                break;
                            }
                        } else {
                            int adapterPositionByView2 = getAdapterPositionByView(getChildAt(childCount3));
                            Grid.Location location = this.mGrid.getLocation(adapterPositionByView2);
                            if (location != null) {
                                int i6 = location.row;
                                if (movement == 1) {
                                    if (i6 == i4 && adapterPositionByView2 > adapterPositionByView) {
                                        childAt.addFocusables(arrayList, i, i2);
                                        if (arrayList.size() > size2) {
                                            break;
                                        }
                                    }
                                } else if (movement == 0) {
                                    if (i6 == i4 && adapterPositionByView2 < adapterPositionByView) {
                                        childAt.addFocusables(arrayList, i, i2);
                                        if (arrayList.size() > size2) {
                                            break;
                                        }
                                    }
                                } else if (movement == 3) {
                                    if (i6 != i4) {
                                        if (i6 < i4) {
                                            break;
                                        }
                                        childAt.addFocusables(arrayList, i, i2);
                                    }
                                } else if (movement == 2 && i6 != i4) {
                                    if (i6 > i4) {
                                        break;
                                    }
                                    childAt.addFocusables(arrayList, i, i2);
                                }
                            }
                        }
                    }
                    childCount3 += i5;
                } else {
                    if (childCount3 < childCount2) {
                        break;
                    }
                    childAt = getChildAt(childCount3);
                    if (childAt.getVisibility() == 0) {
                        if (findViewByPosition2 != null) {
                        }
                    }
                    childCount3 += i5;
                }
            }
        }
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        saveContext(recycler, state);
        int itemCount = state.getItemCount();
        int i = this.mFlag;
        boolean z = (262144 & i) != 0;
        if ((i & 2048) == 0 || (itemCount > 1 && !isItemFullyVisible(0))) {
            if (this.mOrientation == 0) {
                accessibilityNodeInfoCompat.addAction(z ? AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT : AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT);
            } else {
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP);
            }
            accessibilityNodeInfoCompat.setScrollable(true);
        }
        if ((this.mFlag & 4096) == 0 || (itemCount > 1 && !isItemFullyVisible(itemCount - 1))) {
            if (this.mOrientation == 0) {
                accessibilityNodeInfoCompat.addAction(z ? AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT : AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT);
            } else {
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN);
            }
            accessibilityNodeInfoCompat.setScrollable(true);
        }
        accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(getRowCountForAccessibility(recycler, state), getColumnCountForAccessibility(recycler, state), 0));
        leaveContext();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        Grid.Location location;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (this.mGrid == null || !(layoutParams instanceof LayoutParams)) {
            return;
        }
        int absoluteAdapterPosition = ((LayoutParams) layoutParams).mViewHolder.getAbsoluteAdapterPosition();
        int i = -1;
        if (absoluteAdapterPosition >= 0 && (location = this.mGrid.getLocation(absoluteAdapterPosition)) != null) {
            i = location.row;
        }
        int i2 = i;
        if (i2 < 0) {
            return;
        }
        int i3 = absoluteAdapterPosition / this.mGrid.mNumRows;
        if (this.mOrientation == 0) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, i2, 1, i3, 1, false));
        } else {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, i3, 1, i2, 1, false));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00c9 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00ca  */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View onInterceptFocusSearch(View view, int i) {
        View view2;
        View view3;
        if ((this.mFlag & 32768) != 0) {
            return view;
        }
        FocusFinder focusFinder = FocusFinder.getInstance();
        if (i == 2 || i == 1) {
            if (canScrollVertically()) {
                view2 = focusFinder.findNextFocus(this.mBaseGridView, view, i == 2 ? 130 : 33);
            } else {
                view2 = null;
            }
            if (canScrollHorizontally()) {
                view3 = focusFinder.findNextFocus(this.mBaseGridView, view, (getLayoutDirection() == 1) ^ (i == 2) ? 66 : 17);
            } else {
                view3 = view2;
            }
        } else {
            view3 = focusFinder.findNextFocus(this.mBaseGridView, view, i);
        }
        if (view3 != null) {
            return view3;
        }
        if (this.mBaseGridView.getDescendantFocusability() == 393216) {
            return this.mBaseGridView.getParent().focusSearch(view, i);
        }
        int movement = getMovement(i);
        boolean z = this.mBaseGridView.mScrollState != 0;
        if (movement == 1) {
            if (z || (this.mFlag & 4096) == 0) {
                view3 = view;
            }
            if ((this.mFlag & 131072) != 0 && !hasCreatedLastItem()) {
                processPendingMovement(true);
                view3 = view;
            }
            if (view3 == null) {
                return view3;
            }
            View focusSearch = this.mBaseGridView.getParent().focusSearch(view, i);
            return focusSearch != null ? focusSearch : view != null ? view : this.mBaseGridView;
        }
        if (movement == 0) {
            if (z || (this.mFlag & 2048) == 0) {
                view3 = view;
            }
            if ((this.mFlag & 131072) != 0 && !hasCreatedFirstItem()) {
                processPendingMovement(false);
                view3 = view;
            }
            if (view3 == null) {
            }
        } else if (movement == 3) {
            if (view3 == null) {
            }
        } else if (view3 == null) {
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsAdded(int i, int i2) {
        Grid grid;
        int i3;
        int i4 = this.mFocusPosition;
        if (i4 != -1 && (grid = this.mGrid) != null && grid.mFirstVisibleIndex >= 0 && (i3 = this.mFocusPositionOffset) != Integer.MIN_VALUE && i <= i4 + i3) {
            this.mFocusPositionOffset = i3 + i2;
        }
        this.mChildrenStates.getClass();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsChanged() {
        this.mFocusPositionOffset = 0;
        this.mChildrenStates.getClass();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsMoved(int i, int i2) {
        int i3;
        int i4 = this.mFocusPosition;
        if (i4 != -1 && (i3 = this.mFocusPositionOffset) != Integer.MIN_VALUE) {
            int i5 = i4 + i3;
            if (i <= i5 && i5 < i + 1) {
                this.mFocusPositionOffset = (i2 - i) + i3;
            } else if (i < i5 && i2 > i5 - 1) {
                this.mFocusPositionOffset = i3 - 1;
            } else if (i > i5 && i2 < i5) {
                this.mFocusPositionOffset = i3 + 1;
            }
        }
        this.mChildrenStates.getClass();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsRemoved(int i, int i2) {
        Grid grid;
        int i3;
        int i4;
        int i5 = this.mFocusPosition;
        if (i5 != -1 && (grid = this.mGrid) != null && grid.mFirstVisibleIndex >= 0 && (i3 = this.mFocusPositionOffset) != Integer.MIN_VALUE && i <= (i4 = i5 + i3)) {
            if (i + i2 > i4) {
                this.mFocusPosition = (i - i4) + i3 + i5;
                this.mFocusPositionOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
            } else {
                this.mFocusPositionOffset = i3 - i2;
            }
        }
        this.mChildrenStates.getClass();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsUpdated(int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            this.mChildrenStates.getClass();
            i++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:261:0x0653, code lost:
    
        if (r1 < 0) goto L336;
     */
    /* JADX WARN: Code restructure failed: missing block: B:262:0x0684, code lost:
    
        r0 = r0 + r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:274:0x0682, code lost:
    
        if (r1 < 0) goto L336;
     */
    /* JADX WARN: Code restructure failed: missing block: B:354:0x01f4, code lost:
    
        if (((r9 & 262144) != 0) != r10.mReversedFlow) goto L110;
     */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i;
        int i2;
        Grid grid;
        boolean z;
        int i3;
        int i4;
        int i5;
        SparseIntArray sparseIntArray;
        View view;
        boolean z2;
        int i6;
        int i7;
        int i8;
        int left;
        int right;
        int i9;
        List list;
        int size;
        SparseIntArray sparseIntArray2;
        int i10;
        int i11;
        boolean z3;
        boolean z4;
        int i12;
        boolean z5;
        int i13;
        Grid.Location location;
        int i14;
        View view2;
        Grid grid2;
        int i15;
        Grid.Location location2;
        if (this.mNumRows != 0 && state.getItemCount() >= 0) {
            if ((this.mFlag & 64) != 0 && getChildCount() > 0) {
                this.mFlag |= 128;
                return;
            }
            int i16 = this.mFlag;
            if ((i16 & 512) == 0) {
                this.mGrid = null;
                this.mRowSizeSecondary = null;
                this.mFlag = i16 & KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN;
                removeAndRecycleAllViews(recycler);
                return;
            }
            this.mFlag = (i16 & (-4)) | 1;
            saveContext(recycler, state);
            boolean z6 = state.mInPreLayout;
            int i17 = VideoPlayer.MEDIA_ERROR_SYSTEM;
            if (z6) {
                updatePositionDeltaInPreLayout();
                int childCount = getChildCount();
                if (this.mGrid != null && childCount > 0) {
                    int i18 = this.mBaseGridView.getChildViewHolder(getChildAt(0)).mOldPosition;
                    int i19 = this.mBaseGridView.getChildViewHolder(getChildAt(childCount - 1)).mOldPosition;
                    int i20 = Integer.MAX_VALUE;
                    for (int i21 = 0; i21 < childCount; i21++) {
                        View childAt = getChildAt(i21);
                        LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                        this.mBaseGridView.getClass();
                        int childAdapterPosition = RecyclerView.getChildAdapterPosition(childAt);
                        if (layoutParams.isItemChanged() || layoutParams.isItemRemoved() || childAt.isLayoutRequested() || ((!childAt.hasFocus() && this.mFocusPosition == layoutParams.mViewHolder.getAbsoluteAdapterPosition()) || ((childAt.hasFocus() && this.mFocusPosition != layoutParams.mViewHolder.getAbsoluteAdapterPosition()) || childAdapterPosition < i18 || childAdapterPosition > i19))) {
                            int min = Math.min(i20, this.mOrientationHelper.getDecoratedStart(childAt));
                            i17 = Math.max(i17, this.mOrientationHelper.getDecoratedEnd(childAt));
                            i20 = min;
                        }
                    }
                    if (i17 > i20) {
                        this.mExtraLayoutSpaceInPreLayout = i17 - i20;
                    }
                    appendVisibleItems();
                    prependVisibleItems();
                }
                this.mFlag &= -4;
                leaveContext();
                return;
            }
            boolean z7 = state.mRunPredictiveAnimations;
            SparseIntArray sparseIntArray3 = this.mPositionToRowInPostLayout;
            if (z7) {
                sparseIntArray3.clear();
                int childCount2 = getChildCount();
                for (int i22 = 0; i22 < childCount2; i22++) {
                    int i23 = this.mBaseGridView.getChildViewHolder(getChildAt(i22)).mOldPosition;
                    if (i23 >= 0 && (location2 = this.mGrid.getLocation(i23)) != null) {
                        sparseIntArray3.put(i23, location2.row);
                    }
                }
            }
            RecyclerView.SmoothScroller smoothScroller = this.mSmoothScroller;
            boolean z8 = !(smoothScroller != null && smoothScroller.mRunning);
            int i24 = this.mFocusPosition;
            if (i24 != -1 && (i15 = this.mFocusPositionOffset) != Integer.MIN_VALUE) {
                this.mFocusPosition = i24 + i15;
                this.mSubFocusPosition = 0;
            }
            this.mFocusPositionOffset = 0;
            View findViewByPosition = findViewByPosition(this.mFocusPosition);
            int i25 = this.mFocusPosition;
            int i26 = this.mSubFocusPosition;
            boolean hasFocus = this.mBaseGridView.hasFocus();
            Grid grid3 = this.mGrid;
            int i27 = grid3 != null ? grid3.mFirstVisibleIndex : -1;
            int i28 = grid3 != null ? grid3.mLastVisibleIndex : -1;
            if (this.mOrientation == 0) {
                i2 = state.mRemainingScrollHorizontal;
                i = state.mRemainingScrollVertical;
            } else {
                i = state.mRemainingScrollHorizontal;
                i2 = state.mRemainingScrollVertical;
            }
            int i29 = i;
            int i30 = i2;
            int i31 = i29;
            int itemCount = this.mState.getItemCount();
            if (itemCount == 0) {
                this.mFocusPosition = -1;
                this.mSubFocusPosition = 0;
            } else {
                int i32 = this.mFocusPosition;
                if (i32 >= itemCount) {
                    this.mFocusPosition = itemCount - 1;
                    this.mSubFocusPosition = 0;
                } else if (i32 == -1 && itemCount > 0) {
                    this.mFocusPosition = 0;
                    this.mSubFocusPosition = 0;
                }
            }
            boolean z9 = this.mState.mStructureChanged;
            WindowAlignment windowAlignment = this.mWindowAlignment;
            if (z9 || (grid2 = this.mGrid) == null || grid2.mFirstVisibleIndex < 0 || (this.mFlag & 256) != 0 || grid2.mNumRows != this.mNumRows) {
                int i33 = this.mFlag & (-257);
                this.mFlag = i33;
                Grid grid4 = this.mGrid;
                if (grid4 != null && this.mNumRows == grid4.mNumRows) {
                }
                int i34 = this.mNumRows;
                if (i34 == 1) {
                    grid = new SingleRow();
                } else {
                    StaggeredGridDefault staggeredGridDefault = new StaggeredGridDefault();
                    staggeredGridDefault.setNumRows(i34);
                    grid = staggeredGridDefault;
                }
                this.mGrid = grid;
                grid.mProvider = this.mGridProvider;
                grid.mReversedFlow = (this.mFlag & 262144) != 0;
                WindowAlignment.Axis axis = windowAlignment.mMainAxis;
                axis.mMinEdge = VideoPlayer.MEDIA_ERROR_SYSTEM;
                axis.mMaxEdge = Integer.MAX_VALUE;
                int i35 = this.mWidth;
                WindowAlignment.Axis axis2 = windowAlignment.horizontal;
                axis2.mSize = i35;
                int i36 = this.mHeight;
                WindowAlignment.Axis axis3 = windowAlignment.vertical;
                axis3.mSize = i36;
                int paddingLeft = getPaddingLeft();
                int paddingRight = getPaddingRight();
                axis2.mPaddingMin = paddingLeft;
                axis2.mPaddingMax = paddingRight;
                int paddingTop = getPaddingTop();
                int paddingBottom = getPaddingBottom();
                axis3.mPaddingMin = paddingTop;
                axis3.mPaddingMax = paddingBottom;
                this.mSizePrimary = windowAlignment.mMainAxis.mSize;
                this.mScrollOffsetSecondary = 0;
                updateSecondaryScrollLimits();
                this.mGrid.mSpacing = this.mSpacingPrimary;
                detachAndScrapAttachedViews(this.mRecycler);
                Grid grid5 = this.mGrid;
                grid5.mLastVisibleIndex = -1;
                grid5.mFirstVisibleIndex = -1;
                WindowAlignment.Axis axis4 = windowAlignment.mMainAxis;
                axis4.mMinEdge = VideoPlayer.MEDIA_ERROR_SYSTEM;
                axis4.mMinScroll = VideoPlayer.MEDIA_ERROR_SYSTEM;
                axis4.mMaxEdge = Integer.MAX_VALUE;
                axis4.mMaxScroll = Integer.MAX_VALUE;
                z = false;
            } else {
                WindowAlignment.Axis axis5 = windowAlignment.horizontal;
                axis5.mSize = this.mWidth;
                int i37 = this.mHeight;
                WindowAlignment.Axis axis6 = windowAlignment.vertical;
                axis6.mSize = i37;
                int paddingLeft2 = getPaddingLeft();
                int paddingRight2 = getPaddingRight();
                axis5.mPaddingMin = paddingLeft2;
                axis5.mPaddingMax = paddingRight2;
                int paddingTop2 = getPaddingTop();
                int paddingBottom2 = getPaddingBottom();
                axis6.mPaddingMin = paddingTop2;
                axis6.mPaddingMax = paddingBottom2;
                this.mSizePrimary = windowAlignment.mMainAxis.mSize;
                updateSecondaryScrollLimits();
                this.mGrid.mSpacing = this.mSpacingPrimary;
                z = true;
            }
            if (z) {
                this.mFlag |= 4;
                this.mGrid.mStartIndex = this.mFocusPosition;
                int childCount3 = getChildCount();
                int i38 = this.mGrid.mFirstVisibleIndex;
                this.mFlag &= -9;
                int i39 = i38;
                int i40 = 0;
                while (i40 < childCount3) {
                    View childAt2 = getChildAt(i40);
                    if (i39 == getAdapterPositionByView(childAt2) && (location = this.mGrid.getLocation(i39)) != null) {
                        int i41 = i31;
                        int rowStartSecondary = (getRowStartSecondary(location.row) + windowAlignment.mSecondAxis.mPaddingMin) - this.mScrollOffsetSecondary;
                        int decoratedStart = this.mOrientationHelper.getDecoratedStart(childAt2);
                        WindowAlignment windowAlignment2 = windowAlignment;
                        Rect rect = sTempRect;
                        getDecoratedBoundsWithMargins(rect, childAt2);
                        int i42 = i26;
                        int width = this.mOrientation == 0 ? rect.width() : rect.height();
                        if ((((LayoutParams) childAt2.getLayoutParams()).mViewHolder.mFlags & 2) != 0) {
                            this.mFlag |= 8;
                            i14 = width;
                            scrapOrRecycleView(this.mRecycler, this.mChildHelper.indexOfChild(childAt2), childAt2);
                            view2 = this.mRecycler.getViewForPosition(i39);
                            LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams();
                            this.mBaseGridView.getChildViewHolder(view2);
                            layoutParams2.mAlignmentFacet = null;
                            addViewInt(view2, i40, false);
                        } else {
                            i14 = width;
                            view2 = childAt2;
                        }
                        measureChild(view2);
                        int decoratedMeasuredWidthWithMargin = this.mOrientation == 0 ? getDecoratedMeasuredWidthWithMargin(view2) : getDecoratedMeasuredHeightWithMargin(view2);
                        int i43 = location.row;
                        view = findViewByPosition;
                        i10 = i40;
                        sparseIntArray = sparseIntArray3;
                        i11 = i39;
                        i3 = i41;
                        int i44 = i14;
                        i4 = i42;
                        i5 = i25;
                        layoutChild(view2, i43, decoratedStart, decoratedStart + decoratedMeasuredWidthWithMargin, rowStartSecondary);
                        if (i44 == decoratedMeasuredWidthWithMargin) {
                            i40 = i10 + 1;
                            i39 = i11 + 1;
                            i31 = i3;
                            windowAlignment = windowAlignment2;
                            findViewByPosition = view;
                            sparseIntArray3 = sparseIntArray;
                            i26 = i4;
                            i25 = i5;
                        }
                    } else {
                        i3 = i31;
                        i4 = i26;
                        i5 = i25;
                        sparseIntArray = sparseIntArray3;
                        view = findViewByPosition;
                        i10 = i40;
                        i11 = i39;
                    }
                    z3 = true;
                    break;
                }
                i3 = i31;
                i4 = i26;
                i5 = i25;
                sparseIntArray = sparseIntArray3;
                view = findViewByPosition;
                i10 = i40;
                i11 = i39;
                z3 = false;
                if (z3) {
                    int i45 = this.mGrid.mLastVisibleIndex;
                    for (int i46 = childCount3 - 1; i46 >= i10; i46--) {
                        View childAt3 = getChildAt(i46);
                        scrapOrRecycleView(this.mRecycler, this.mChildHelper.indexOfChild(childAt3), childAt3);
                    }
                    this.mGrid.invalidateItemsAfter(i11);
                    if ((this.mFlag & 65536) == 0) {
                        do {
                            Grid grid6 = this.mGrid;
                            if (grid6.mReversedFlow) {
                                z4 = true;
                                i12 = Integer.MAX_VALUE;
                            } else {
                                z4 = true;
                                i12 = VideoPlayer.MEDIA_ERROR_SYSTEM;
                            }
                            if (!grid6.appendVisibleItems(i12, z4)) {
                                break;
                            }
                        } while (this.mGrid.mLastVisibleIndex < i45);
                    } else {
                        appendVisibleItems();
                        int i47 = this.mFocusPosition;
                        if (i47 >= 0 && i47 <= i45) {
                            while (true) {
                                Grid grid7 = this.mGrid;
                                if (grid7.mLastVisibleIndex >= this.mFocusPosition) {
                                    break;
                                }
                                if (grid7.mReversedFlow) {
                                    z5 = true;
                                    i13 = Integer.MAX_VALUE;
                                } else {
                                    z5 = true;
                                    i13 = VideoPlayer.MEDIA_ERROR_SYSTEM;
                                }
                                grid7.appendVisibleItems(i13, z5);
                            }
                        }
                    }
                }
                updateScrollLimits();
                updateSecondaryScrollLimits();
            } else {
                i3 = i31;
                i4 = i26;
                i5 = i25;
                sparseIntArray = sparseIntArray3;
                view = findViewByPosition;
                int i48 = this.mFlag & (-5);
                this.mFlag = i48;
                this.mFlag = (i48 & (-17)) | (z8 ? 16 : 0);
                if (z8 && (i27 < 0 || (i7 = this.mFocusPosition) > i28 || i7 < i27)) {
                    i27 = this.mFocusPosition;
                    i28 = i27;
                }
                this.mGrid.mStartIndex = i27;
                if (i28 != -1) {
                    do {
                        Grid grid8 = this.mGrid;
                        if (grid8.mReversedFlow) {
                            z2 = true;
                            i6 = Integer.MAX_VALUE;
                        } else {
                            z2 = true;
                            i6 = VideoPlayer.MEDIA_ERROR_SYSTEM;
                        }
                        if (grid8.appendVisibleItems(i6, z2)) {
                        }
                    } while (findViewByPosition(i28) == null);
                }
            }
            while (true) {
                updateScrollLimits();
                Grid grid9 = this.mGrid;
                int i49 = grid9.mFirstVisibleIndex;
                int i50 = grid9.mLastVisibleIndex;
                int i51 = -i30;
                int i52 = -i3;
                View findViewByPosition2 = findViewByPosition(this.mFocusPosition);
                if (findViewByPosition2 != null && z8) {
                    scrollToView(findViewByPosition2, findViewByPosition2.findFocus(), false, i51, i52);
                }
                if (findViewByPosition2 != null && hasFocus && !findViewByPosition2.hasFocus()) {
                    findViewByPosition2.requestFocus();
                } else if (!hasFocus && !this.mBaseGridView.hasFocus()) {
                    if (findViewByPosition2 == null || !findViewByPosition2.hasFocusable()) {
                        int childCount4 = getChildCount();
                        int i53 = 0;
                        while (true) {
                            if (i53 >= childCount4) {
                                break;
                            }
                            findViewByPosition2 = getChildAt(i53);
                            if (findViewByPosition2 != null && findViewByPosition2.hasFocusable()) {
                                this.mBaseGridView.focusableViewAvailable(findViewByPosition2);
                                break;
                            }
                            i53++;
                        }
                    } else {
                        this.mBaseGridView.focusableViewAvailable(findViewByPosition2);
                    }
                    View view3 = findViewByPosition2;
                    if (z8 && view3 != null && view3.hasFocus()) {
                        scrollToView(view3, view3.findFocus(), false, i51, i52);
                    }
                }
                appendVisibleItems();
                prependVisibleItems();
                Grid grid10 = this.mGrid;
                if (grid10.mFirstVisibleIndex == i49 && grid10.mLastVisibleIndex == i50) {
                    break;
                }
                i5 = i5;
                i4 = i4;
                view = view;
                sparseIntArray = sparseIntArray;
            }
            removeInvisibleViewsAtFront();
            removeInvisibleViewsAtEnd();
            if (state.mRunPredictiveAnimations && (size = (list = this.mRecycler.mUnmodifiableAttachedScrap).size()) != 0) {
                int[] iArr = this.mDisappearingPositions;
                if (iArr == null || size > iArr.length) {
                    int length = iArr == null ? 16 : iArr.length;
                    while (length < size) {
                        length <<= 1;
                    }
                    this.mDisappearingPositions = new int[length];
                }
                int i54 = 0;
                for (int i55 = 0; i55 < size; i55++) {
                    int absoluteAdapterPosition = ((RecyclerView.ViewHolder) list.get(i55)).getAbsoluteAdapterPosition();
                    if (absoluteAdapterPosition >= 0) {
                        this.mDisappearingPositions[i54] = absoluteAdapterPosition;
                        i54++;
                    }
                }
                if (i54 > 0) {
                    Arrays.sort(this.mDisappearingPositions, 0, i54);
                    Grid grid11 = this.mGrid;
                    int[] iArr2 = this.mDisappearingPositions;
                    int i56 = grid11.mLastVisibleIndex;
                    int binarySearch = i56 >= 0 ? Arrays.binarySearch(iArr2, 0, i54, i56) : 0;
                    Object[] objArr = grid11.mTmpItem;
                    if (binarySearch < 0) {
                        int i57 = (-binarySearch) - 1;
                        int edge = grid11.mReversedFlow ? (((C02772) grid11.mProvider).getEdge(i56) - ((C02772) grid11.mProvider).getSize(i56)) - grid11.mSpacing : grid11.mSpacing + ((C02772) grid11.mProvider).getSize(i56) + ((C02772) grid11.mProvider).getEdge(i56);
                        while (i57 < i54) {
                            int i58 = iArr2[i57];
                            SparseIntArray sparseIntArray4 = sparseIntArray;
                            int i59 = sparseIntArray4.get(i58);
                            if (i59 < 0) {
                                i59 = 0;
                            }
                            int createItem = ((C02772) grid11.mProvider).createItem(i58, true, objArr, true);
                            ((C02772) grid11.mProvider).addItem(objArr[0], createItem, i59, edge);
                            edge = grid11.mReversedFlow ? (edge - createItem) - grid11.mSpacing : edge + createItem + grid11.mSpacing;
                            i57++;
                            sparseIntArray = sparseIntArray4;
                        }
                    }
                    sparseIntArray2 = sparseIntArray;
                    int i60 = grid11.mFirstVisibleIndex;
                    int binarySearch2 = i60 >= 0 ? Arrays.binarySearch(iArr2, 0, i54, i60) : 0;
                    if (binarySearch2 < 0) {
                        int edge2 = grid11.mReversedFlow ? ((C02772) grid11.mProvider).getEdge(i60) : ((C02772) grid11.mProvider).getEdge(i60);
                        for (int i61 = (-binarySearch2) - 2; i61 >= 0; i61--) {
                            int i62 = iArr2[i61];
                            int i63 = sparseIntArray2.get(i62);
                            if (i63 < 0) {
                                i63 = 0;
                            }
                            int createItem2 = ((C02772) grid11.mProvider).createItem(i62, false, objArr, true);
                            edge2 = grid11.mReversedFlow ? edge2 + grid11.mSpacing + createItem2 : (edge2 - grid11.mSpacing) - createItem2;
                            ((C02772) grid11.mProvider).addItem(objArr[0], createItem2, i63, edge2);
                        }
                    }
                } else {
                    sparseIntArray2 = sparseIntArray;
                }
                sparseIntArray2.clear();
            }
            int i64 = this.mFlag;
            if ((i64 & 1024) != 0) {
                this.mFlag = i64 & KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN;
            } else {
                int i65 = (i64 & KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN) | (processRowSizeSecondary(false) ? 1024 : 0);
                this.mFlag = i65;
                if ((i65 & 1024) != 0) {
                    BaseGridView baseGridView = this.mBaseGridView;
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.postOnAnimation(baseGridView, this.mRequestLayoutRunnable);
                }
            }
            if ((this.mFlag & 4) != 0 && ((i9 = this.mFocusPosition) != i5 || this.mSubFocusPosition != i4 || findViewByPosition(i9) != view || (this.mFlag & 8) != 0)) {
                dispatchChildSelected();
            } else if ((this.mFlag & 20) == 16) {
                dispatchChildSelected();
            }
            dispatchChildSelectedAndPositioned();
            int i66 = this.mFlag;
            if ((i66 & 64) != 0) {
                if (this.mOrientation == 1) {
                    i8 = -this.mHeight;
                    if (getChildCount() > 0) {
                        left = getChildAt(0).getTop();
                    }
                    scrollDirectionPrimary(i8);
                } else {
                    if ((i66 & 262144) != 0) {
                        i8 = this.mWidth;
                        if (getChildCount() > 0 && (right = getChildAt(0).getRight()) > i8) {
                            i8 = right;
                        }
                    } else {
                        i8 = -this.mWidth;
                        if (getChildCount() > 0) {
                            left = getChildAt(0).getLeft();
                        }
                    }
                    scrollDirectionPrimary(i8);
                }
            }
            this.mFlag &= -4;
            leaveContext();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int i, int i2) {
        int size;
        int size2;
        int mode;
        int paddingLeft;
        int paddingRight;
        int i3;
        saveContext(recycler, state);
        if (this.mOrientation == 0) {
            size2 = View.MeasureSpec.getSize(i);
            size = View.MeasureSpec.getSize(i2);
            mode = View.MeasureSpec.getMode(i2);
            paddingLeft = getPaddingTop();
            paddingRight = getPaddingBottom();
        } else {
            size = View.MeasureSpec.getSize(i);
            size2 = View.MeasureSpec.getSize(i2);
            mode = View.MeasureSpec.getMode(i);
            paddingLeft = getPaddingLeft();
            paddingRight = getPaddingRight();
        }
        int i4 = paddingRight + paddingLeft;
        this.mMaxSizeSecondary = size;
        int i5 = this.mRowSizeSecondaryRequested;
        if (i5 == -2) {
            int i6 = this.mNumRowsRequested;
            if (i6 == 0) {
                i6 = 1;
            }
            this.mNumRows = i6;
            this.mFixedRowSizeSecondary = 0;
            int[] iArr = this.mRowSizeSecondary;
            if (iArr == null || iArr.length != i6) {
                this.mRowSizeSecondary = new int[i6];
            }
            if (this.mState.mInPreLayout) {
                updatePositionDeltaInPreLayout();
            }
            processRowSizeSecondary(true);
            if (mode == Integer.MIN_VALUE) {
                size = Math.min(getSizeSecondary() + i4, this.mMaxSizeSecondary);
            } else if (mode == 0) {
                i3 = getSizeSecondary();
                size = i3 + i4;
            } else {
                if (mode != 1073741824) {
                    throw new IllegalStateException("wrong spec");
                }
                size = this.mMaxSizeSecondary;
            }
        } else {
            if (mode != Integer.MIN_VALUE) {
                if (mode == 0) {
                    if (i5 == 0) {
                        i5 = size - i4;
                    }
                    this.mFixedRowSizeSecondary = i5;
                    int i7 = this.mNumRowsRequested;
                    if (i7 == 0) {
                        i7 = 1;
                    }
                    this.mNumRows = i7;
                    i3 = ((i7 - 1) * this.mSpacingSecondary) + (i5 * i7);
                    size = i3 + i4;
                } else if (mode != 1073741824) {
                    throw new IllegalStateException("wrong spec");
                }
            }
            int i8 = this.mNumRowsRequested;
            if (i8 == 0 && i5 == 0) {
                this.mNumRows = 1;
                this.mFixedRowSizeSecondary = size - i4;
            } else if (i8 == 0) {
                this.mFixedRowSizeSecondary = i5;
                int i9 = this.mSpacingSecondary;
                this.mNumRows = (size + i9) / (i5 + i9);
            } else if (i5 == 0) {
                this.mNumRows = i8;
                this.mFixedRowSizeSecondary = ((size - i4) - ((i8 - 1) * this.mSpacingSecondary)) / i8;
            } else {
                this.mNumRows = i8;
                this.mFixedRowSizeSecondary = i5;
            }
            if (mode == Integer.MIN_VALUE) {
                int i10 = this.mFixedRowSizeSecondary;
                int i11 = this.mNumRows;
                int i12 = ((i11 - 1) * this.mSpacingSecondary) + (i10 * i11) + i4;
                if (i12 < size) {
                    size = i12;
                }
            }
        }
        if (this.mOrientation == 0) {
            this.mRecyclerView.setMeasuredDimension(size2, size);
        } else {
            this.mRecyclerView.setMeasuredDimension(size, size2);
        }
        leaveContext();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean onRequestChildFocus(RecyclerView recyclerView, View view, View view2) {
        if ((this.mFlag & 32768) == 0 && getAdapterPositionByView(view) != -1 && (this.mFlag & 35) == 0) {
            scrollToView(view, view2, true, 0, 0);
        }
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.mFocusPosition = ((SavedState) parcelable).index;
            this.mFocusPositionOffset = 0;
            this.mChildrenStates.getClass();
            this.mFlag |= 256;
            requestLayout();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.index = this.mFocusPosition;
        this.mChildrenStates.getClass();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getAdapterPositionByView(getChildAt(i));
        }
        savedState.childStates = null;
        return savedState;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002c, code lost:
    
        if (r5 != false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x004c, code lost:
    
        r7 = 4096;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0037, code lost:
    
        if (r5 != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x004a, code lost:
    
        if (r7 == androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN.getId()) goto L27;
     */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean performAccessibilityAction(RecyclerView.Recycler recycler, RecyclerView.State state, int i, Bundle bundle) {
        if (!((this.mFlag & 131072) != 0)) {
            return true;
        }
        saveContext(recycler, state);
        boolean z = (this.mFlag & 262144) != 0;
        if (this.mOrientation != 0) {
            if (i != AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP.getId()) {
            }
            i = 8192;
        } else if (i != AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT.getId()) {
            if (i == AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT.getId()) {
            }
        }
        int i2 = this.mFocusPosition;
        boolean z2 = i2 == 0 && i == 8192;
        boolean z3 = i2 == state.getItemCount() - 1 && i == 4096;
        if (z2 || z3) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain(4096);
            this.mBaseGridView.onInitializeAccessibilityEvent(obtain);
            BaseGridView baseGridView = this.mBaseGridView;
            baseGridView.requestSendAccessibilityEvent(baseGridView, obtain);
        } else if (i == 4096) {
            processPendingMovement(true);
            processSelectionMoves(1, false);
        } else if (i == 8192) {
            processPendingMovement(false);
            processSelectionMoves(-1, false);
        }
        leaveContext();
        return true;
    }

    public final void prependVisibleItems() {
        this.mGrid.prependVisibleItems((this.mFlag & 262144) != 0 ? this.mSizePrimary + 0 + this.mExtraLayoutSpaceInPreLayout : 0 - this.mExtraLayoutSpaceInPreLayout, false);
    }

    public final void processPendingMovement(boolean z) {
        if (z) {
            if (hasCreatedLastItem()) {
                return;
            }
        } else if (hasCreatedFirstItem()) {
            return;
        }
        PendingMoveSmoothScroller pendingMoveSmoothScroller = this.mPendingMoveSmoothScroller;
        if (pendingMoveSmoothScroller == null) {
            PendingMoveSmoothScroller pendingMoveSmoothScroller2 = new PendingMoveSmoothScroller(z ? 1 : -1, this.mNumRows > 1);
            this.mFocusPositionOffset = 0;
            startSmoothScroll(pendingMoveSmoothScroller2);
        } else {
            if (z) {
                int i = pendingMoveSmoothScroller.mPendingMoves;
                if (i < GridLayoutManager.this.mMaxPendingMoves) {
                    pendingMoveSmoothScroller.mPendingMoves = i + 1;
                    return;
                }
                return;
            }
            int i2 = pendingMoveSmoothScroller.mPendingMoves;
            if (i2 > (-GridLayoutManager.this.mMaxPendingMoves)) {
                pendingMoveSmoothScroller.mPendingMoves = i2 - 1;
            }
        }
    }

    public final boolean processRowSizeSecondary(boolean z) {
        if (this.mFixedRowSizeSecondary != 0 || this.mRowSizeSecondary == null) {
            return false;
        }
        Grid grid = this.mGrid;
        CircularIntArray[] itemPositionsInRows = grid == null ? null : grid.getItemPositionsInRows(grid.mFirstVisibleIndex, grid.mLastVisibleIndex);
        boolean z2 = false;
        int i = -1;
        for (int i2 = 0; i2 < this.mNumRows; i2++) {
            CircularIntArray circularIntArray = itemPositionsInRows == null ? null : itemPositionsInRows[i2];
            int i3 = circularIntArray == null ? 0 : (circularIntArray.tail + 0) & circularIntArray.capacityBitmask;
            int i4 = -1;
            for (int i5 = 0; i5 < i3; i5 += 2) {
                if (i5 >= 0) {
                    int i6 = circularIntArray.tail;
                    int i7 = circularIntArray.capacityBitmask;
                    if (i5 < ((i6 + 0) & i7)) {
                        int[] iArr = circularIntArray.elements;
                        int i8 = i5 + 1;
                        if (i8 < 0 || i8 >= ((i6 + 0) & i7)) {
                            int i9 = CollectionPlatformUtils.$r8$clinit;
                            throw new ArrayIndexOutOfBoundsException();
                        }
                        int i10 = iArr[(i8 + 0) & i7];
                        for (int i11 = iArr[(i5 + 0) & i7]; i11 <= i10; i11++) {
                            View findViewByPosition = findViewByPosition(i11 - this.mPositionDeltaInPreLayout);
                            if (findViewByPosition != null) {
                                if (z) {
                                    measureChild(findViewByPosition);
                                }
                                int decoratedMeasuredHeightWithMargin = this.mOrientation == 0 ? getDecoratedMeasuredHeightWithMargin(findViewByPosition) : getDecoratedMeasuredWidthWithMargin(findViewByPosition);
                                if (decoratedMeasuredHeightWithMargin > i4) {
                                    i4 = decoratedMeasuredHeightWithMargin;
                                }
                            }
                        }
                    }
                } else {
                    circularIntArray.getClass();
                }
                int i12 = CollectionPlatformUtils.$r8$clinit;
                throw new ArrayIndexOutOfBoundsException();
            }
            int itemCount = this.mState.getItemCount();
            if (!this.mBaseGridView.mHasFixedSize && z && i4 < 0 && itemCount > 0) {
                if (i < 0) {
                    int i13 = this.mFocusPosition;
                    if (i13 < 0) {
                        i13 = 0;
                    } else if (i13 >= itemCount) {
                        i13 = itemCount - 1;
                    }
                    if (getChildCount() > 0) {
                        int layoutPosition = this.mBaseGridView.getChildViewHolder(getChildAt(0)).getLayoutPosition();
                        int layoutPosition2 = this.mBaseGridView.getChildViewHolder(getChildAt(getChildCount() - 1)).getLayoutPosition();
                        if (i13 >= layoutPosition && i13 <= layoutPosition2) {
                            i13 = i13 - layoutPosition <= layoutPosition2 - i13 ? layoutPosition - 1 : layoutPosition2 + 1;
                            if (i13 < 0 && layoutPosition2 < itemCount - 1) {
                                i13 = layoutPosition2 + 1;
                            } else if (i13 >= itemCount && layoutPosition > 0) {
                                i13 = layoutPosition - 1;
                            }
                        }
                    }
                    if (i13 >= 0 && i13 < itemCount) {
                        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
                        View viewForPosition = this.mRecycler.getViewForPosition(i13);
                        int[] iArr2 = this.mMeasuredDimension;
                        if (viewForPosition != null) {
                            LayoutParams layoutParams = (LayoutParams) viewForPosition.getLayoutParams();
                            Rect rect = sTempRect;
                            calculateItemDecorationsForChild(rect, viewForPosition);
                            viewForPosition.measure(ViewGroup.getChildMeasureSpec(makeMeasureSpec, getPaddingRight() + getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + rect.left + rect.right, ((ViewGroup.MarginLayoutParams) layoutParams).width), ViewGroup.getChildMeasureSpec(makeMeasureSpec2, getPaddingBottom() + getPaddingTop() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + rect.top + rect.bottom, ((ViewGroup.MarginLayoutParams) layoutParams).height));
                            iArr2[0] = getDecoratedMeasuredWidthWithMargin(viewForPosition);
                            iArr2[1] = getDecoratedMeasuredHeightWithMargin(viewForPosition);
                            this.mRecycler.recycleView(viewForPosition);
                        }
                        i = this.mOrientation == 0 ? iArr2[1] : iArr2[0];
                    }
                }
                if (i >= 0) {
                    i4 = i;
                }
            }
            if (i4 < 0) {
                i4 = 0;
            }
            int[] iArr3 = this.mRowSizeSecondary;
            if (iArr3[i2] != i4) {
                iArr3[i2] = i4;
                z2 = true;
            }
        }
        return z2;
    }

    public final int processSelectionMoves(int i, boolean z) {
        Grid.Location location;
        Grid grid = this.mGrid;
        if (grid == null) {
            return i;
        }
        int i2 = this.mFocusPosition;
        int i3 = (i2 == -1 || (location = grid.getLocation(i2)) == null) ? -1 : location.row;
        int childCount = getChildCount();
        View view = null;
        int i4 = 0;
        while (true) {
            boolean z2 = true;
            if (i4 >= childCount || i == 0) {
                break;
            }
            int i5 = i > 0 ? i4 : (childCount - 1) - i4;
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 0 || (hasFocus() && !childAt.hasFocusable())) {
                z2 = false;
            }
            if (z2) {
                int adapterPositionByView = getAdapterPositionByView(getChildAt(i5));
                Grid.Location location2 = this.mGrid.getLocation(adapterPositionByView);
                int i6 = location2 == null ? -1 : location2.row;
                if (i3 == -1) {
                    i2 = adapterPositionByView;
                    i3 = i6;
                } else if (i6 == i3 && ((i > 0 && adapterPositionByView > i2) || (i < 0 && adapterPositionByView < i2))) {
                    i = i > 0 ? i - 1 : i + 1;
                    i2 = adapterPositionByView;
                }
                view = childAt;
            }
            i4++;
        }
        if (view != null) {
            if (z) {
                if (hasFocus()) {
                    this.mFlag |= 32;
                    view.requestFocus();
                    this.mFlag &= -33;
                }
                this.mFocusPosition = i2;
                this.mSubFocusPosition = 0;
            } else {
                scrollToView(view, true);
            }
        }
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void removeAndRecycleAllViews(RecyclerView.Recycler recycler) {
        int childCount = getChildCount();
        while (true) {
            childCount--;
            if (childCount < 0) {
                return;
            }
            View childAt = getChildAt(childCount);
            removeViewAt(childCount);
            recycler.recycleView(childAt);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x003b, code lost:
    
        r0 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void removeInvisibleViewsAtEnd() {
        int i = this.mFlag;
        if ((65600 & i) == 65536) {
            Grid grid = this.mGrid;
            int i2 = this.mFocusPosition;
            int i3 = (i & 262144) != 0 ? 0 : this.mSizePrimary + 0;
            while (true) {
                int i4 = grid.mLastVisibleIndex;
                if (i4 < grid.mFirstVisibleIndex || i4 <= i2) {
                    break;
                }
                boolean z = !grid.mReversedFlow ? false : false;
                if (!z) {
                    break;
                }
                Grid.Provider provider = grid.mProvider;
                int i5 = grid.mLastVisibleIndex;
                GridLayoutManager gridLayoutManager = GridLayoutManager.this;
                View findViewByPosition = gridLayoutManager.findViewByPosition(i5 - gridLayoutManager.mPositionDeltaInPreLayout);
                if ((gridLayoutManager.mFlag & 3) == 1) {
                    gridLayoutManager.scrapOrRecycleView(gridLayoutManager.mRecycler, gridLayoutManager.mChildHelper.indexOfChild(findViewByPosition), findViewByPosition);
                } else {
                    gridLayoutManager.removeAndRecycleView(findViewByPosition, gridLayoutManager.mRecycler);
                }
                grid.mLastVisibleIndex--;
            }
            if (grid.mLastVisibleIndex < grid.mFirstVisibleIndex) {
                grid.mLastVisibleIndex = -1;
                grid.mFirstVisibleIndex = -1;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0039, code lost:
    
        if ((((androidx.leanback.widget.GridLayoutManager.C02772) r1.mProvider).getEdge(r1.mFirstVisibleIndex) + r0) <= r8) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x004b, code lost:
    
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0049, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0047, code lost:
    
        if ((((androidx.leanback.widget.GridLayoutManager.C02772) r1.mProvider).getEdge(r1.mFirstVisibleIndex) - r0) >= r8) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void removeInvisibleViewsAtFront() {
        boolean z;
        int i = this.mFlag;
        if ((65600 & i) == 65536) {
            Grid grid = this.mGrid;
            int i2 = this.mFocusPosition;
            int i3 = (i & 262144) != 0 ? this.mSizePrimary + 0 : 0;
            while (true) {
                int i4 = grid.mLastVisibleIndex;
                int i5 = grid.mFirstVisibleIndex;
                if (i4 < i5 || i5 >= i2) {
                    break;
                }
                int size = ((C02772) grid.mProvider).getSize(i5);
                if (!grid.mReversedFlow) {
                }
                if (!z) {
                    break;
                }
                Grid.Provider provider = grid.mProvider;
                int i6 = grid.mFirstVisibleIndex;
                GridLayoutManager gridLayoutManager = GridLayoutManager.this;
                View findViewByPosition = gridLayoutManager.findViewByPosition(i6 - gridLayoutManager.mPositionDeltaInPreLayout);
                if ((gridLayoutManager.mFlag & 3) == 1) {
                    gridLayoutManager.scrapOrRecycleView(gridLayoutManager.mRecycler, gridLayoutManager.mChildHelper.indexOfChild(findViewByPosition), findViewByPosition);
                } else {
                    gridLayoutManager.removeAndRecycleView(findViewByPosition, gridLayoutManager.mRecycler);
                }
                grid.mFirstVisibleIndex++;
            }
            if (grid.mLastVisibleIndex < grid.mFirstVisibleIndex) {
                grid.mLastVisibleIndex = -1;
                grid.mFirstVisibleIndex = -1;
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z) {
        return false;
    }

    public final void saveContext(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i = this.mSaveContextLevel;
        if (i == 0) {
            this.mRecycler = recycler;
            this.mState = state;
            this.mPositionDeltaInPreLayout = 0;
            this.mExtraLayoutSpaceInPreLayout = 0;
        }
        this.mSaveContextLevel = i + 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0020, code lost:
    
        if (r7 > r0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0036, code lost:
    
        r7 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0034, code lost:
    
        if (r7 < r0) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int scrollDirectionPrimary(int i) {
        int i2;
        int i3 = this.mFlag;
        if ((i3 & 64) == 0 && (i3 & 3) != 1) {
            WindowAlignment windowAlignment = this.mWindowAlignment;
            if (i > 0) {
                WindowAlignment.Axis axis = windowAlignment.mMainAxis;
                if (!(axis.mMaxEdge == Integer.MAX_VALUE)) {
                    i2 = axis.mMaxScroll;
                }
            } else if (i < 0) {
                WindowAlignment.Axis axis2 = windowAlignment.mMainAxis;
                if (!(axis2.mMinEdge == Integer.MIN_VALUE)) {
                    i2 = axis2.mMinScroll;
                }
            }
        }
        if (i == 0) {
            return 0;
        }
        int i4 = -i;
        int childCount = getChildCount();
        if (this.mOrientation == 1) {
            for (int i5 = 0; i5 < childCount; i5++) {
                getChildAt(i5).offsetTopAndBottom(i4);
            }
        } else {
            for (int i6 = 0; i6 < childCount; i6++) {
                getChildAt(i6).offsetLeftAndRight(i4);
            }
        }
        if ((this.mFlag & 3) == 1) {
            updateScrollLimits();
            return i;
        }
        int childCount2 = getChildCount();
        if ((this.mFlag & 262144) == 0 ? i >= 0 : i <= 0) {
            appendVisibleItems();
        } else {
            prependVisibleItems();
        }
        boolean z = getChildCount() > childCount2;
        int childCount3 = getChildCount();
        if ((262144 & this.mFlag) == 0 ? i >= 0 : i <= 0) {
            removeInvisibleViewsAtFront();
        } else {
            removeInvisibleViewsAtEnd();
        }
        if (z | (getChildCount() < childCount3)) {
            int i7 = (this.mFlag & KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN) | (processRowSizeSecondary(false) ? 1024 : 0);
            this.mFlag = i7;
            if ((i7 & 1024) != 0) {
                BaseGridView baseGridView = this.mBaseGridView;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postOnAnimation(baseGridView, this.mRequestLayoutRunnable);
            }
        }
        this.mBaseGridView.invalidate();
        updateScrollLimits();
        return i;
    }

    public final int scrollDirectionSecondary(int i) {
        int i2 = 0;
        if (i == 0) {
            return 0;
        }
        int i3 = -i;
        int childCount = getChildCount();
        if (this.mOrientation == 0) {
            while (i2 < childCount) {
                getChildAt(i2).offsetTopAndBottom(i3);
                i2++;
            }
        } else {
            while (i2 < childCount) {
                getChildAt(i2).offsetLeftAndRight(i3);
                i2++;
            }
        }
        this.mScrollOffsetSecondary += i;
        updateSecondaryScrollLimits();
        this.mBaseGridView.invalidate();
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if ((this.mFlag & 512) != 0) {
            if (this.mGrid != null) {
                saveContext(recycler, state);
                this.mFlag = (this.mFlag & (-4)) | 2;
                int scrollDirectionPrimary = this.mOrientation == 0 ? scrollDirectionPrimary(i) : scrollDirectionSecondary(i);
                leaveContext();
                this.mFlag &= -4;
                return scrollDirectionPrimary;
            }
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void scrollToPosition(int i) {
        setSelection$1(i, false);
    }

    public final void scrollToSelection(int i, boolean z) {
        this.mPrimaryScrollExtra = 0;
        View findViewByPosition = findViewByPosition(i);
        RecyclerView.SmoothScroller smoothScroller = this.mSmoothScroller;
        boolean z2 = !(smoothScroller != null && smoothScroller.mRunning);
        if (z2 && !this.mBaseGridView.isLayoutRequested() && findViewByPosition != null && getAdapterPositionByView(findViewByPosition) == i) {
            this.mFlag |= 32;
            scrollToView(findViewByPosition, z);
            this.mFlag &= -33;
            return;
        }
        int i2 = this.mFlag;
        if ((i2 & 512) == 0 || (i2 & 64) != 0) {
            this.mFocusPosition = i;
            this.mSubFocusPosition = 0;
            this.mFocusPositionOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
            return;
        }
        if (z && !this.mBaseGridView.isLayoutRequested()) {
            this.mFocusPosition = i;
            this.mSubFocusPosition = 0;
            this.mFocusPositionOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
            if (!(this.mGrid != null)) {
                Log.w("GridLayoutManager:" + this.mBaseGridView.getId(), "setSelectionSmooth should not be called before first layout pass");
                return;
            }
            GridLinearSmoothScroller gridLinearSmoothScroller = new GridLinearSmoothScroller() { // from class: androidx.leanback.widget.GridLayoutManager.4
                @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller
                public final PointF computeScrollVectorForPosition(int i3) {
                    if (getChildCount() == 0) {
                        return null;
                    }
                    GridLayoutManager gridLayoutManager = GridLayoutManager.this;
                    boolean z3 = false;
                    int position = RecyclerView.LayoutManager.getPosition(gridLayoutManager.getChildAt(0));
                    if ((gridLayoutManager.mFlag & 262144) == 0 ? i3 < position : i3 > position) {
                        z3 = true;
                    }
                    int i4 = z3 ? -1 : 1;
                    return gridLayoutManager.mOrientation == 0 ? new PointF(i4, 0.0f) : new PointF(0.0f, i4);
                }
            };
            gridLinearSmoothScroller.mTargetPosition = i;
            startSmoothScroll(gridLinearSmoothScroller);
            int i3 = gridLinearSmoothScroller.mTargetPosition;
            if (i3 != this.mFocusPosition) {
                this.mFocusPosition = i3;
                this.mSubFocusPosition = 0;
                return;
            }
            return;
        }
        if (!z2) {
            GridLinearSmoothScroller gridLinearSmoothScroller2 = this.mCurrentSmoothScroller;
            if (gridLinearSmoothScroller2 != null) {
                gridLinearSmoothScroller2.mSkipOnStopInternal = true;
            }
            this.mBaseGridView.stopScroll();
        }
        if (!this.mBaseGridView.isLayoutRequested() && findViewByPosition != null && getAdapterPositionByView(findViewByPosition) == i) {
            this.mFlag |= 32;
            scrollToView(findViewByPosition, z);
            this.mFlag &= -33;
        } else {
            this.mFocusPosition = i;
            this.mSubFocusPosition = 0;
            this.mFocusPositionOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.mFlag |= 256;
            requestLayout();
        }
    }

    public final void scrollToView(View view, boolean z) {
        scrollToView(view, view.findFocus(), z, 0, 0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i2 = this.mFlag;
        if ((i2 & 512) != 0) {
            if (this.mGrid != null) {
                this.mFlag = (i2 & (-4)) | 2;
                saveContext(recycler, state);
                int scrollDirectionPrimary = this.mOrientation == 1 ? scrollDirectionPrimary(i) : scrollDirectionSecondary(i);
                leaveContext();
                this.mFlag &= -4;
                return scrollDirectionPrimary;
            }
        }
        return 0;
    }

    public final void setOrientation(int i) {
        if (i == 0 || i == 1) {
            this.mOrientation = i;
            this.mOrientationHelper = OrientationHelper.createOrientationHelper(this, i);
            WindowAlignment windowAlignment = this.mWindowAlignment;
            windowAlignment.getClass();
            WindowAlignment.Axis axis = windowAlignment.horizontal;
            WindowAlignment.Axis axis2 = windowAlignment.vertical;
            if (i == 0) {
                windowAlignment.mMainAxis = axis;
                windowAlignment.mSecondAxis = axis2;
            } else {
                windowAlignment.mMainAxis = axis2;
                windowAlignment.mSecondAxis = axis;
            }
            this.mItemAlignment.getClass();
            this.mFlag |= 256;
        }
    }

    public final void setRowHeight(int i) {
        if (i < 0 && i != -2) {
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Invalid row height: ", i));
        }
        this.mRowSizeSecondaryRequested = i;
    }

    public final void setSelection$1(int i, boolean z) {
        if ((this.mFocusPosition == i || i == -1) && this.mSubFocusPosition == 0 && this.mPrimaryScrollExtra == 0) {
            return;
        }
        scrollToSelection(i, z);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void smoothScrollToPosition(RecyclerView recyclerView, int i) {
        setSelection$1(i, true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void startSmoothScroll(RecyclerView.SmoothScroller smoothScroller) {
        GridLinearSmoothScroller gridLinearSmoothScroller = this.mCurrentSmoothScroller;
        if (gridLinearSmoothScroller != null) {
            gridLinearSmoothScroller.mSkipOnStopInternal = true;
        }
        super.startSmoothScroll(smoothScroller);
        if (!smoothScroller.mRunning || !(smoothScroller instanceof GridLinearSmoothScroller)) {
            this.mCurrentSmoothScroller = null;
            this.mPendingMoveSmoothScroller = null;
            return;
        }
        GridLinearSmoothScroller gridLinearSmoothScroller2 = (GridLinearSmoothScroller) smoothScroller;
        this.mCurrentSmoothScroller = gridLinearSmoothScroller2;
        if (gridLinearSmoothScroller2 instanceof PendingMoveSmoothScroller) {
            this.mPendingMoveSmoothScroller = (PendingMoveSmoothScroller) gridLinearSmoothScroller2;
        } else {
            this.mPendingMoveSmoothScroller = null;
        }
    }

    public final void updatePositionDeltaInPreLayout() {
        if (getChildCount() <= 0) {
            this.mPositionDeltaInPreLayout = 0;
        } else {
            this.mPositionDeltaInPreLayout = this.mGrid.mFirstVisibleIndex - ((LayoutParams) getChildAt(0).getLayoutParams()).getViewLayoutPosition();
        }
    }

    public final void updateScrollLimits() {
        int i;
        int i2;
        int itemCount;
        int i3;
        int i4;
        int i5;
        int top;
        int i6;
        int top2;
        int i7;
        if (this.mState.getItemCount() == 0) {
            return;
        }
        if ((this.mFlag & 262144) == 0) {
            i3 = this.mGrid.mLastVisibleIndex;
            int itemCount2 = this.mState.getItemCount() - 1;
            i = this.mGrid.mFirstVisibleIndex;
            i2 = itemCount2;
            itemCount = 0;
        } else {
            Grid grid = this.mGrid;
            int i8 = grid.mFirstVisibleIndex;
            i = grid.mLastVisibleIndex;
            i2 = 0;
            itemCount = this.mState.getItemCount() - 1;
            i3 = i8;
        }
        if (i3 < 0 || i < 0) {
            return;
        }
        boolean z = i3 == i2;
        boolean z2 = i == itemCount;
        int i9 = VideoPlayer.MEDIA_ERROR_SYSTEM;
        int i10 = Integer.MAX_VALUE;
        WindowAlignment windowAlignment = this.mWindowAlignment;
        if (!z) {
            WindowAlignment.Axis axis = windowAlignment.mMainAxis;
            if ((axis.mMaxEdge == Integer.MAX_VALUE) && !z2) {
                if (axis.mMinEdge == Integer.MIN_VALUE) {
                    return;
                }
            }
        }
        int[] iArr = sTwoInts;
        if (z) {
            i10 = this.mGrid.findRowMax(true, iArr);
            View findViewByPosition = findViewByPosition(iArr[1]);
            if (this.mOrientation == 0) {
                LayoutParams layoutParams = (LayoutParams) findViewByPosition.getLayoutParams();
                layoutParams.getClass();
                top2 = findViewByPosition.getLeft() + layoutParams.mLeftInset;
                i7 = layoutParams.mAlignX;
            } else {
                LayoutParams layoutParams2 = (LayoutParams) findViewByPosition.getLayoutParams();
                layoutParams2.getClass();
                top2 = findViewByPosition.getTop() + layoutParams2.mTopInset;
                i7 = layoutParams2.mAlignY;
            }
            int i11 = i7 + top2;
            int[] iArr2 = ((LayoutParams) findViewByPosition.getLayoutParams()).mAlignMultiple;
            i4 = (iArr2 == null || iArr2.length <= 0) ? i11 : (iArr2[iArr2.length - 1] - iArr2[0]) + i11;
        } else {
            i4 = Integer.MAX_VALUE;
        }
        if (z2) {
            i9 = this.mGrid.findRowMin(false, iArr);
            View findViewByPosition2 = findViewByPosition(iArr[1]);
            if (this.mOrientation == 0) {
                LayoutParams layoutParams3 = (LayoutParams) findViewByPosition2.getLayoutParams();
                layoutParams3.getClass();
                top = findViewByPosition2.getLeft() + layoutParams3.mLeftInset;
                i6 = layoutParams3.mAlignX;
            } else {
                LayoutParams layoutParams4 = (LayoutParams) findViewByPosition2.getLayoutParams();
                layoutParams4.getClass();
                top = findViewByPosition2.getTop() + layoutParams4.mTopInset;
                i6 = layoutParams4.mAlignY;
            }
            i5 = i6 + top;
        } else {
            i5 = Integer.MIN_VALUE;
        }
        windowAlignment.mMainAxis.updateMinMax(i9, i10, i5, i4);
    }

    public final void updateSecondaryScrollLimits() {
        WindowAlignment.Axis axis = this.mWindowAlignment.mSecondAxis;
        int i = axis.mPaddingMin - this.mScrollOffsetSecondary;
        int sizeSecondary = getSizeSecondary() + i;
        axis.updateMinMax(i, sizeSecondary, i, sizeSecondary);
    }

    /* JADX WARN: Type inference failed for: r2v7, types: [androidx.leanback.widget.GridLayoutManager$1] */
    public GridLayoutManager(BaseGridView baseGridView) {
        this.mSmoothScrollSpeedFactor = 1.0f;
        this.mMaxPendingMoves = 10;
        this.mOrientation = 0;
        this.mOrientationHelper = new OrientationHelper.C04511(this);
        this.mPositionToRowInPostLayout = new SparseIntArray();
        this.mFlag = 221696;
        this.mChildViewHolderSelectedListeners = null;
        this.mFocusPosition = -1;
        this.mSubFocusPosition = 0;
        this.mFocusPositionOffset = 0;
        this.mGravity = 8388659;
        this.mNumRowsRequested = 1;
        this.mWindowAlignment = new WindowAlignment();
        this.mItemAlignment = new ItemAlignment();
        this.mMeasuredDimension = new int[2];
        this.mChildrenStates = new ViewsStateBundle();
        this.mRequestLayoutRunnable = new Runnable() { // from class: androidx.leanback.widget.GridLayoutManager.1
            @Override // java.lang.Runnable
            public final void run() {
                GridLayoutManager.this.requestLayout();
            }
        };
        this.mGridProvider = new C02772();
        this.mBaseGridView = baseGridView;
        this.mChildVisibility = -1;
        if (this.mItemPrefetchEnabled) {
            this.mItemPrefetchEnabled = false;
            this.mPrefetchMaxCountObserved = 0;
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.mRecycler.updateViewCacheSize();
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : layoutParams instanceof RecyclerView.LayoutParams ? new LayoutParams((RecyclerView.LayoutParams) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    public final void scrollToView(View view, View view2, boolean z, int i, int i2) {
        if ((this.mFlag & 64) != 0) {
            return;
        }
        int adapterPositionByView = getAdapterPositionByView(view);
        int subPositionByView = getSubPositionByView(view, view2);
        if (adapterPositionByView != this.mFocusPosition || subPositionByView != this.mSubFocusPosition) {
            this.mFocusPosition = adapterPositionByView;
            this.mSubFocusPosition = subPositionByView;
            this.mFocusPositionOffset = 0;
            if ((this.mFlag & 3) != 1) {
                dispatchChildSelected();
            }
            if (this.mBaseGridView.isChildrenDrawingOrderEnabledInternal()) {
                this.mBaseGridView.invalidate();
            }
        }
        if (view == null) {
            return;
        }
        if (!view.hasFocus() && this.mBaseGridView.hasFocus()) {
            view.requestFocus();
        }
        if ((this.mFlag & 131072) == 0 && z) {
            return;
        }
        int[] iArr = sTwoInts;
        if (!getScrollPosition(view, view2, iArr) && i == 0 && i2 == 0) {
            return;
        }
        int i3 = iArr[0] + i;
        int i4 = iArr[1] + i2;
        if ((this.mFlag & 3) == 1) {
            scrollDirectionPrimary(i3);
            scrollDirectionSecondary(i4);
            return;
        }
        if (this.mOrientation != 0) {
            i4 = i3;
            i3 = i4;
        }
        if (z) {
            this.mBaseGridView.smoothScrollBy(i3, i4, false);
        } else {
            this.mBaseGridView.scrollBy(i3, i4);
            dispatchChildSelectedAndPositioned();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: androidx.leanback.widget.GridLayoutManager.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public Bundle childStates;
        public int index;

        public SavedState(Parcel parcel) {
            this.childStates = Bundle.EMPTY;
            this.index = parcel.readInt();
            this.childStates = parcel.readBundle(GridLayoutManager.class.getClassLoader());
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.index);
            parcel.writeBundle(this.childStates);
        }

        public SavedState() {
            this.childStates = Bundle.EMPTY;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onLayoutCompleted(RecyclerView.State state) {
    }
}
