package androidx.leanback.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.collection.CircularIntArray;
import androidx.collection.CollectionPlatformUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.leanback.widget.Grid;
import androidx.leanback.widget.ItemAlignment;
import androidx.leanback.widget.WindowAlignment;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class GridLayoutManager extends RecyclerView.LayoutManager {
    public static final Rect sTempRect = new Rect();
    public static final int[] sTwoInts = new int[2];
    public AudioManager mAudioManager;
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
    public final AnonymousClass2 mGridProvider;
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
    public RecyclerView.Recycler mRecycler;
    public final AnonymousClass1 mRequestLayoutRunnable;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.leanback.widget.GridLayoutManager$2, reason: invalid class name */
    public class AnonymousClass2 {
        public AnonymousClass2() {
        }

        public final void addItem(int i, int i2, int i3, Object obj) {
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
            if (gridLayoutManager.mGrid.mReversedFlow) {
                i4 = i3 - i;
                i5 = i3;
            } else {
                i5 = i + i3;
                i4 = i3;
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
            if (i7 != 0 && (i7 <= 0 || !gridLayoutManager2.hasCreatedLastItem())) {
                if (pendingMoveSmoothScroller.mPendingMoves >= 0) {
                    return;
                }
                if (gridLayoutManager2.getItemCount() != 0 && gridLayoutManager2.mBaseGridView.findViewHolderForAdapterPosition(0) == null) {
                    return;
                }
            }
            pendingMoveSmoothScroller.mTargetPosition = gridLayoutManager2.mFocusPosition;
            pendingMoveSmoothScroller.stop();
        }

        public final int createItem(int i, boolean z, Object[] objArr, boolean z2) {
            int i2;
            View findViewByPosition;
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            View viewForPosition = gridLayoutManager.getViewForPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
            if (!((LayoutParams) viewForPosition.getLayoutParams()).mViewHolder.isRemoved()) {
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
                int i3 = gridLayoutManager.mChildVisibility;
                if (i3 != -1) {
                    viewForPosition.setVisibility(i3);
                }
                PendingMoveSmoothScroller pendingMoveSmoothScroller = gridLayoutManager.mPendingMoveSmoothScroller;
                if (pendingMoveSmoothScroller != null && !pendingMoveSmoothScroller.mStaggeredGrid && (i2 = pendingMoveSmoothScroller.mPendingMoves) != 0) {
                    GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
                    int i4 = i2 > 0 ? gridLayoutManager2.mFocusPosition + gridLayoutManager2.mNumRows : gridLayoutManager2.mFocusPosition - gridLayoutManager2.mNumRows;
                    View view = null;
                    while (pendingMoveSmoothScroller.mPendingMoves != 0 && (findViewByPosition = pendingMoveSmoothScroller.findViewByPosition(i4)) != null) {
                        gridLayoutManager2.getClass();
                        if (findViewByPosition.getVisibility() == 0 && (!gridLayoutManager2.hasFocus() || findViewByPosition.hasFocusable())) {
                            gridLayoutManager2.mFocusPosition = i4;
                            gridLayoutManager2.mSubFocusPosition = 0;
                            int i5 = pendingMoveSmoothScroller.mPendingMoves;
                            if (i5 > 0) {
                                pendingMoveSmoothScroller.mPendingMoves = i5 - 1;
                            } else {
                                pendingMoveSmoothScroller.mPendingMoves = i5 + 1;
                            }
                            view = findViewByPosition;
                        }
                        i4 = pendingMoveSmoothScroller.mPendingMoves > 0 ? i4 + gridLayoutManager2.mNumRows : i4 - gridLayoutManager2.mNumRows;
                    }
                    if (view != null && gridLayoutManager2.hasFocus()) {
                        gridLayoutManager2.mFlag |= 32;
                        view.requestFocus();
                        gridLayoutManager2.mFlag &= -33;
                    }
                }
                GridLayoutManager.getSubPositionByView(viewForPosition, viewForPosition.findFocus());
                int i6 = gridLayoutManager.mFlag;
                if ((i6 & 3) != 1) {
                    if (i == gridLayoutManager.mFocusPosition && gridLayoutManager.mSubFocusPosition == 0 && gridLayoutManager.mPendingMoveSmoothScroller == null) {
                        gridLayoutManager.dispatchChildSelected();
                    }
                } else if ((i6 & 4) == 0) {
                    int i7 = i6 & 16;
                    if (i7 == 0 && i == gridLayoutManager.mFocusPosition && gridLayoutManager.mSubFocusPosition == 0) {
                        gridLayoutManager.dispatchChildSelected();
                    } else if (i7 != 0 && i >= gridLayoutManager.mFocusPosition && viewForPosition.hasFocusable()) {
                        gridLayoutManager.mFocusPosition = i;
                        gridLayoutManager.mSubFocusPosition = 0;
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

        public final void removeItem(int i) {
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            View findViewByPosition = gridLayoutManager.findViewByPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
            if ((gridLayoutManager.mFlag & 3) == 1) {
                gridLayoutManager.scrapOrRecycleView(gridLayoutManager.mRecycler, gridLayoutManager.mChildHelper.indexOfChild(findViewByPosition), findViewByPosition);
            } else {
                gridLayoutManager.removeAndRecycleView(findViewByPosition, gridLayoutManager.mRecycler);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class GridLinearSmoothScroller extends LinearSmoothScroller {
        public boolean mSkipOnStopInternal;

        public GridLinearSmoothScroller() {
            super(GridLayoutManager.this.mBaseGridView.getContext());
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        public final float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return super.calculateSpeedPerPixel(displayMetrics) * GridLayoutManager.this.mSmoothScrollSpeedFactor;
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        public final int calculateTimeForScrolling(int i) {
            int calculateTimeForScrolling = super.calculateTimeForScrolling(i);
            int i2 = GridLayoutManager.this.mWindowAlignment.mMainAxis.mSize;
            if (i2 > 0) {
                float f = (30.0f / i2) * i;
                if (calculateTimeForScrolling < f) {
                    return (int) f;
                }
            }
            return calculateTimeForScrolling;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LayoutParams extends RecyclerView.LayoutParams {
        public final int[] mAlignMultiple;
        public int mAlignX;
        public int mAlignY;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        if (view == null || (layoutParams = (LayoutParams) view.getLayoutParams()) == null || layoutParams.mViewHolder.isRemoved()) {
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
        if (view != null && view2 != null) {
            ((LayoutParams) view.getLayoutParams()).getClass();
        }
        return 0;
    }

    public final void appendVisibleItems() {
        int i;
        Grid grid = this.mGrid;
        if ((this.mFlag & 262144) != 0) {
            i = 0 - this.mExtraLayoutSpaceInPreLayout;
        } else {
            i = this.mExtraLayoutSpaceInPreLayout + this.mSizePrimary;
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
                this.mGrid.collectAdjacentPrefetchPositions(i < 0 ? 0 : this.mSizePrimary, i, layoutPrefetchRegistryImpl);
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
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        int i = this.mFocusPosition;
        View findViewByPosition = i == -1 ? null : findViewByPosition(i);
        if (findViewByPosition != null) {
            RecyclerView.ViewHolder childViewHolder = this.mBaseGridView.getChildViewHolder(findViewByPosition);
            BaseGridView baseGridView = this.mBaseGridView;
            int i2 = this.mFocusPosition;
            ArrayList arrayList2 = this.mChildViewHolderSelectedListeners;
            if (arrayList2 != null) {
                for (int size = arrayList2.size() - 1; size >= 0; size--) {
                    ((OnChildViewHolderSelectedListener) this.mChildViewHolderSelectedListeners.get(size)).onChildViewHolderSelected(baseGridView, childViewHolder, i2);
                }
            }
        } else {
            BaseGridView baseGridView2 = this.mBaseGridView;
            ArrayList arrayList3 = this.mChildViewHolderSelectedListeners;
            if (arrayList3 != null) {
                for (int size2 = arrayList3.size() - 1; size2 >= 0; size2--) {
                    ((OnChildViewHolderSelectedListener) this.mChildViewHolderSelectedListeners.get(size2)).onChildViewHolderSelected(baseGridView2, null, -1);
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
                AnonymousClass1 anonymousClass1 = this.mRequestLayoutRunnable;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                baseGridView3.postOnAnimation(anonymousClass1);
                return;
            }
        }
    }

    public final void dispatchChildSelectedAndPositioned() {
        ArrayList arrayList = this.mChildViewHolderSelectedListeners;
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        int i = this.mFocusPosition;
        View findViewByPosition = i == -1 ? null : findViewByPosition(i);
        if (findViewByPosition == null) {
            ArrayList arrayList2 = this.mChildViewHolderSelectedListeners;
            if (arrayList2 == null) {
                return;
            }
            for (int size = arrayList2.size() - 1; size >= 0; size--) {
                ((OnChildViewHolderSelectedListener) this.mChildViewHolderSelectedListeners.get(size)).getClass();
            }
            return;
        }
        this.mBaseGridView.getChildViewHolder(findViewByPosition);
        ArrayList arrayList3 = this.mChildViewHolderSelectedListeners;
        if (arrayList3 == null) {
            return;
        }
        for (int size2 = arrayList3.size() - 1; size2 >= 0; size2--) {
            ((OnChildViewHolderSelectedListener) this.mChildViewHolderSelectedListeners.get(size2)).getClass();
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
        return super.getDecoratedBottom(view) - ((LayoutParams) view.getLayoutParams()).mBottomInset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void getDecoratedBoundsWithMargins(Rect rect, View view) {
        RecyclerView.getDecoratedBoundsWithMarginsInt(rect, view);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        rect.left += layoutParams.mLeftInset;
        rect.top += layoutParams.mTopInset;
        rect.right -= layoutParams.mRightInset;
        rect.bottom -= layoutParams.mBottomInset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int getDecoratedLeft(View view) {
        return super.getDecoratedLeft(view) + ((LayoutParams) view.getLayoutParams()).mLeftInset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int getDecoratedRight(View view) {
        return super.getDecoratedRight(view) - ((LayoutParams) view.getLayoutParams()).mRightInset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int getDecoratedTop(View view) {
        return super.getDecoratedTop(view) + ((LayoutParams) view.getLayoutParams()).mTopInset;
    }

    public final int getMovement(int i) {
        int i2 = this.mOrientation;
        if (i2 != 0) {
            if (i2 == 1) {
                if (i == 17) {
                    return (this.mFlag & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) == 0 ? 2 : 3;
                }
                if (i == 33) {
                    return 0;
                }
                if (i == 66) {
                    return (this.mFlag & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) == 0 ? 3 : 2;
                }
                if (i == 130) {
                    return 1;
                }
            }
        }
        if (i != 17) {
            if (i == 33) {
                return 2;
            }
            if (i != 66) {
                return i != 130 ? 17 : 3;
            }
            if ((this.mFlag & 262144) != 0) {
                return 0;
            }
        } else if ((this.mFlag & 262144) == 0) {
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
        if ((this.mFlag & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0) {
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
        if (view2 != null) {
            getSubPositionByView(view, view2);
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
        if (scroll == 0 && scroll2 == 0) {
            iArr[0] = 0;
            iArr[1] = 0;
            return false;
        }
        iArr[0] = scroll;
        iArr[1] = scroll2;
        return true;
    }

    public final int getSizeSecondary() {
        int i = (this.mFlag & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 ? 0 : this.mNumRows - 1;
        return getRowSizeSecondary(i) + getRowStartSecondary(i);
    }

    public final View getViewForPosition(int i) {
        View viewForPosition = this.mRecycler.getViewForPosition(i);
        LayoutParams layoutParams = (LayoutParams) viewForPosition.getLayoutParams();
        this.mBaseGridView.getChildViewHolder(viewForPosition);
        layoutParams.getClass();
        return viewForPosition;
    }

    public final boolean hasCreatedLastItem() {
        int itemCount = getItemCount();
        return itemCount == 0 || this.mBaseGridView.findViewHolderForAdapterPosition(itemCount - 1) != null;
    }

    public final boolean isItemFullyVisible(int i) {
        RecyclerView.ViewHolder findViewHolderForAdapterPosition = this.mBaseGridView.findViewHolderForAdapterPosition(i);
        return findViewHolderForAdapterPosition != null && findViewHolderForAdapterPosition.itemView.getLeft() >= 0 && findViewHolderForAdapterPosition.itemView.getRight() <= this.mBaseGridView.getWidth() && findViewHolderForAdapterPosition.itemView.getTop() >= 0 && findViewHolderForAdapterPosition.itemView.getBottom() <= this.mBaseGridView.getHeight();
    }

    public final void layoutChild(View view, int i, int i2, int i3, int i4) {
        int rowSizeSecondary;
        int i5;
        int decoratedMeasuredHeightWithMargin = this.mOrientation == 0 ? getDecoratedMeasuredHeightWithMargin(view) : getDecoratedMeasuredWidthWithMargin(view);
        int i6 = this.mFixedRowSizeSecondary;
        if (i6 > 0) {
            decoratedMeasuredHeightWithMargin = Math.min(decoratedMeasuredHeightWithMargin, i6);
        }
        int i7 = this.mGravity;
        int i8 = i7 & 112;
        int absoluteGravity = (this.mFlag & 786432) != 0 ? Gravity.getAbsoluteGravity(i7 & 8388615, 1) : i7 & 7;
        int i9 = this.mOrientation;
        if ((i9 != 0 || i8 != 48) && (i9 != 1 || absoluteGravity != 3)) {
            if ((i9 == 0 && i8 == 80) || (i9 == 1 && absoluteGravity == 5)) {
                rowSizeSecondary = getRowSizeSecondary(i) - decoratedMeasuredHeightWithMargin;
            } else if ((i9 == 0 && i8 == 16) || (i9 == 1 && absoluteGravity == 1)) {
                rowSizeSecondary = (getRowSizeSecondary(i) - decoratedMeasuredHeightWithMargin) / 2;
            }
            i4 += rowSizeSecondary;
        }
        if (this.mOrientation == 0) {
            i5 = decoratedMeasuredHeightWithMargin + i4;
        } else {
            int i10 = decoratedMeasuredHeightWithMargin + i4;
            int i11 = i4;
            i4 = i2;
            i2 = i11;
            i5 = i3;
            i3 = i10;
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        RecyclerView.LayoutManager.layoutDecoratedWithMargins(view, i2, i4, i3, i5);
        Rect rect = sTempRect;
        RecyclerView.getDecoratedBoundsWithMarginsInt(rect, view);
        int i12 = i2 - rect.left;
        int i13 = i4 - rect.top;
        int i14 = rect.right - i3;
        int i15 = rect.bottom - i5;
        layoutParams.mLeftInset = i12;
        layoutParams.mTopInset = i13;
        layoutParams.mRightInset = i14;
        layoutParams.mBottomInset = i15;
        LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
        layoutParams2.getClass();
        ItemAlignment itemAlignment = this.mItemAlignment;
        ItemAlignment.Axis axis = itemAlignment.horizontal;
        layoutParams2.mAlignX = ItemAlignmentFacetHelper.getAlignmentPosition(view, axis, axis.mOrientation);
        ItemAlignment.Axis axis2 = itemAlignment.vertical;
        layoutParams2.mAlignY = ItemAlignmentFacetHelper.getAlignmentPosition(view, axis2, axis2.mOrientation);
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
        int makeMeasureSpec = this.mRowSizeSecondaryRequested == -2 ? View.MeasureSpec.makeMeasureSpec(0, 0) : View.MeasureSpec.makeMeasureSpec(this.mFixedRowSizeSecondary, 1073741824);
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00d4  */
    /* JADX WARN: Type inference failed for: r16v11, types: [boolean] */
    /* JADX WARN: Type inference failed for: r16v2 */
    /* JADX WARN: Type inference failed for: r16v3 */
    /* JADX WARN: Type inference failed for: r16v6, types: [boolean] */
    /* JADX WARN: Type inference failed for: r16v7 */
    /* JADX WARN: Type inference failed for: r16v8 */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onAddFocusables(androidx.recyclerview.widget.RecyclerView r18, java.util.ArrayList r19, int r20, int r21) {
        /*
            Method dump skipped, instructions count: 343
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.onAddFocusables(androidx.recyclerview.widget.RecyclerView, java.util.ArrayList, int, int):boolean");
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
        accessibilityNodeInfoCompat.setClassName(GridView.class.getName());
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
            i = location.mRow;
        }
        if (i < 0) {
            return;
        }
        int i2 = absoluteAdapterPosition / this.mGrid.mNumRows;
        if (this.mOrientation == 0) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, i, 1, i2, 1));
        } else {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, i2, 1, i, 1));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00d2 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00d3  */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View onInterceptFocusSearch(android.view.View r8, int r9) {
        /*
            Method dump skipped, instructions count: 230
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.onInterceptFocusSearch(android.view.View, int):android.view.View");
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
                this.mFocusPositionOffset = Integer.MIN_VALUE;
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

    /* JADX WARN: Code restructure failed: missing block: B:238:0x05bc, code lost:
    
        if (r2 < 0) goto L297;
     */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x05be, code lost:
    
        r1 = r1 + r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:251:0x05eb, code lost:
    
        if (r2 < 0) goto L297;
     */
    /* JADX WARN: Code restructure failed: missing block: B:308:0x0325, code lost:
    
        if (((r2 & 262144) != 0) != r3.mReversedFlow) goto L151;
     */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onLayoutChildren(androidx.recyclerview.widget.RecyclerView.Recycler r25, androidx.recyclerview.widget.RecyclerView.State r26) {
        /*
            Method dump skipped, instructions count: 1531
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.onLayoutChildren(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State):void");
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
        if ((this.mFlag & NetworkAnalyticsConstants.DataPoints.FLAG_UID) == 0 && getAdapterPositionByView(view) != -1 && (this.mFlag & 35) == 0) {
            scrollToView(view, view2, true, 0, 0);
        }
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.mFocusPosition = ((SavedState) parcelable).mIndex;
            this.mFocusPositionOffset = 0;
            this.mChildrenStates.getClass();
            this.mFlag |= 256;
            requestLayout();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.mIndex = this.mFocusPosition;
        this.mChildrenStates.getClass();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getAdapterPositionByView(getChildAt(i));
        }
        savedState.mChildStates = null;
        return savedState;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0026, code lost:
    
        if (r5 != false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0046, code lost:
    
        r7 = 4096;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0031, code lost:
    
        if (r5 != false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0044, code lost:
    
        if (r7 == androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN.getId()) goto L23;
     */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean performAccessibilityAction(androidx.recyclerview.widget.RecyclerView.Recycler r5, androidx.recyclerview.widget.RecyclerView.State r6, int r7, android.os.Bundle r8) {
        /*
            r4 = this;
            int r8 = r4.mFlag
            r0 = 131072(0x20000, float:1.83671E-40)
            r8 = r8 & r0
            r0 = 1
            if (r8 == 0) goto L86
            r4.saveContext(r5, r6)
            int r5 = r4.mFlag
            r8 = 262144(0x40000, float:3.67342E-40)
            r5 = r5 & r8
            r8 = 0
            if (r5 == 0) goto L15
            r5 = r0
            goto L16
        L15:
            r5 = r8
        L16:
            int r1 = r4.mOrientation
            r2 = 8192(0x2000, float:1.148E-41)
            r3 = 4096(0x1000, float:5.74E-42)
            if (r1 != 0) goto L34
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r1 = androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT
            int r1 = r1.getId()
            if (r7 != r1) goto L29
            if (r5 == 0) goto L3c
            goto L46
        L29:
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r1 = androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT
            int r1 = r1.getId()
            if (r7 != r1) goto L47
            if (r5 == 0) goto L46
            goto L3c
        L34:
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r5 = androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP
            int r5 = r5.getId()
            if (r7 != r5) goto L3e
        L3c:
            r7 = r2
            goto L47
        L3e:
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r5 = androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN
            int r5 = r5.getId()
            if (r7 != r5) goto L47
        L46:
            r7 = r3
        L47:
            int r5 = r4.mFocusPosition
            if (r5 != 0) goto L4f
            if (r7 != r2) goto L4f
            r1 = r0
            goto L50
        L4f:
            r1 = r8
        L50:
            int r6 = r6.getItemCount()
            int r6 = r6 - r0
            if (r5 != r6) goto L5b
            if (r7 != r3) goto L5b
            r5 = r0
            goto L5c
        L5b:
            r5 = r8
        L5c:
            if (r1 != 0) goto L75
            if (r5 == 0) goto L61
            goto L75
        L61:
            if (r7 == r3) goto L6e
            if (r7 == r2) goto L66
            goto L83
        L66:
            r4.processPendingMovement(r8)
            r5 = -1
            r4.processSelectionMoves(r5, r8)
            goto L83
        L6e:
            r4.processPendingMovement(r0)
            r4.processSelectionMoves(r0, r8)
            goto L83
        L75:
            android.view.accessibility.AccessibilityEvent r5 = android.view.accessibility.AccessibilityEvent.obtain(r3)
            androidx.leanback.widget.BaseGridView r6 = r4.mBaseGridView
            r6.onInitializeAccessibilityEvent(r5)
            androidx.leanback.widget.BaseGridView r6 = r4.mBaseGridView
            r6.requestSendAccessibilityEvent(r6, r5)
        L83:
            r4.leaveContext()
        L86:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.performAccessibilityAction(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State, int, android.os.Bundle):boolean");
    }

    public final void prependVisibleItems() {
        this.mGrid.prependVisibleItems((this.mFlag & 262144) != 0 ? this.mSizePrimary + this.mExtraLayoutSpaceInPreLayout : 0 - this.mExtraLayoutSpaceInPreLayout, false);
    }

    public final void processPendingMovement(boolean z) {
        int i;
        if (z) {
            if (hasCreatedLastItem()) {
                return;
            }
        } else if (getItemCount() == 0 || this.mBaseGridView.findViewHolderForAdapterPosition(0) != null) {
            return;
        }
        PendingMoveSmoothScroller pendingMoveSmoothScroller = this.mPendingMoveSmoothScroller;
        if (pendingMoveSmoothScroller == null) {
            PendingMoveSmoothScroller pendingMoveSmoothScroller2 = new PendingMoveSmoothScroller(z ? 1 : -1, this.mNumRows > 1);
            this.mFocusPositionOffset = 0;
            startSmoothScroll(pendingMoveSmoothScroller2);
        } else {
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            if (z) {
                int i2 = pendingMoveSmoothScroller.mPendingMoves;
                if (i2 < gridLayoutManager.mMaxPendingMoves) {
                    pendingMoveSmoothScroller.mPendingMoves = i2 + 1;
                }
            } else {
                int i3 = pendingMoveSmoothScroller.mPendingMoves;
                if (i3 > (-gridLayoutManager.mMaxPendingMoves)) {
                    pendingMoveSmoothScroller.mPendingMoves = i3 - 1;
                }
            }
        }
        if (this.mOrientation == 0) {
            i = 4;
            if (getLayoutDirection() != 1 ? !z : z) {
                i = 3;
            }
        } else {
            i = z ? 2 : 1;
        }
        if (this.mAudioManager == null) {
            this.mAudioManager = (AudioManager) this.mBaseGridView.getContext().getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        }
        this.mAudioManager.playSoundEffect(i);
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
            int i3 = circularIntArray == null ? 0 : circularIntArray.tail & circularIntArray.capacityBitmask;
            int i4 = -1;
            for (int i5 = 0; i5 < i3; i5 += 2) {
                if (i5 >= 0) {
                    int i6 = circularIntArray.tail;
                    int i7 = circularIntArray.capacityBitmask;
                    if (i5 < (i6 & i7)) {
                        int[] iArr = circularIntArray.elements;
                        int i8 = iArr[i5 & i7];
                        int i9 = i5 + 1;
                        if (i9 < 0 || i9 >= (i6 & i7)) {
                            int i10 = CollectionPlatformUtils.$r8$clinit;
                            throw new ArrayIndexOutOfBoundsException();
                        }
                        int i11 = iArr[i9 & i7];
                        for (int i12 = i8; i12 <= i11; i12++) {
                            View findViewByPosition = findViewByPosition(i12 - this.mPositionDeltaInPreLayout);
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
                int i13 = CollectionPlatformUtils.$r8$clinit;
                throw new ArrayIndexOutOfBoundsException();
            }
            int itemCount = this.mState.getItemCount();
            if (!this.mBaseGridView.mHasFixedSize && z && i4 < 0 && itemCount > 0) {
                if (i < 0) {
                    int i14 = this.mFocusPosition;
                    if (i14 < 0) {
                        i14 = 0;
                    } else if (i14 >= itemCount) {
                        i14 = itemCount - 1;
                    }
                    if (getChildCount() > 0) {
                        int layoutPosition = this.mBaseGridView.getChildViewHolder(getChildAt(0)).getLayoutPosition();
                        int layoutPosition2 = this.mBaseGridView.getChildViewHolder(getChildAt(getChildCount() - 1)).getLayoutPosition();
                        if (i14 >= layoutPosition && i14 <= layoutPosition2) {
                            i14 = i14 - layoutPosition <= layoutPosition2 - i14 ? layoutPosition - 1 : layoutPosition2 + 1;
                            if (i14 < 0 && layoutPosition2 < itemCount - 1) {
                                i14 = layoutPosition2 + 1;
                            } else if (i14 >= itemCount && layoutPosition > 0) {
                                i14 = layoutPosition - 1;
                            }
                        }
                    }
                    if (i14 >= 0 && i14 < itemCount) {
                        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
                        View viewForPosition = this.mRecycler.getViewForPosition(i14);
                        LayoutParams layoutParams = (LayoutParams) viewForPosition.getLayoutParams();
                        Rect rect = sTempRect;
                        calculateItemDecorationsForChild(rect, viewForPosition);
                        viewForPosition.measure(ViewGroup.getChildMeasureSpec(makeMeasureSpec, getPaddingRight() + getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + rect.left + rect.right, ((ViewGroup.MarginLayoutParams) layoutParams).width), ViewGroup.getChildMeasureSpec(makeMeasureSpec2, getPaddingBottom() + getPaddingTop() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + rect.top + rect.bottom, ((ViewGroup.MarginLayoutParams) layoutParams).height));
                        int decoratedMeasuredWidthWithMargin = getDecoratedMeasuredWidthWithMargin(viewForPosition);
                        int[] iArr2 = this.mMeasuredDimension;
                        iArr2[0] = decoratedMeasuredWidthWithMargin;
                        iArr2[1] = getDecoratedMeasuredHeightWithMargin(viewForPosition);
                        this.mRecycler.recycleView(viewForPosition);
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
        int i3 = (i2 == -1 || (location = grid.getLocation(i2)) == null) ? -1 : location.mRow;
        int childCount = getChildCount();
        View view = null;
        for (int i4 = 0; i4 < childCount && i != 0; i4++) {
            int i5 = i > 0 ? i4 : (childCount - 1) - i4;
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() == 0 && (!hasFocus() || childAt.hasFocusable())) {
                int adapterPositionByView = getAdapterPositionByView(getChildAt(i5));
                Grid.Location location2 = this.mGrid.getLocation(adapterPositionByView);
                int i6 = location2 == null ? -1 : location2.mRow;
                if (i3 == -1) {
                    i2 = adapterPositionByView;
                    view = childAt;
                    i3 = i6;
                } else if (i6 == i3 && ((i > 0 && adapterPositionByView > i2) || (i < 0 && adapterPositionByView < i2))) {
                    i = i > 0 ? i - 1 : i + 1;
                    i2 = adapterPositionByView;
                    view = childAt;
                }
            }
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
                return i;
            }
            scrollToView(view, true);
        }
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void removeAndRecycleAllViews(RecyclerView.Recycler recycler) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            removeViewAt(childCount);
            recycler.recycleView(childAt);
        }
    }

    public final void removeInvisibleViewsAtEnd() {
        int i = this.mFlag;
        if ((65600 & i) == 65536) {
            Grid grid = this.mGrid;
            int i2 = this.mFocusPosition;
            int i3 = (i & 262144) != 0 ? 0 : this.mSizePrimary;
            while (true) {
                int i4 = grid.mLastVisibleIndex;
                if (i4 < grid.mFirstVisibleIndex || i4 <= i2) {
                    break;
                }
                if (!grid.mReversedFlow) {
                    if (grid.mProvider.getEdge(i4) < i3) {
                        break;
                    }
                    grid.mProvider.removeItem(grid.mLastVisibleIndex);
                    grid.mLastVisibleIndex--;
                } else {
                    if (grid.mProvider.getEdge(i4) > i3) {
                        break;
                    }
                    grid.mProvider.removeItem(grid.mLastVisibleIndex);
                    grid.mLastVisibleIndex--;
                }
            }
            if (grid.mLastVisibleIndex < grid.mFirstVisibleIndex) {
                grid.mLastVisibleIndex = -1;
                grid.mFirstVisibleIndex = -1;
            }
        }
    }

    public final void removeInvisibleViewsAtFront() {
        int i = this.mFlag;
        if ((65600 & i) == 65536) {
            Grid grid = this.mGrid;
            int i2 = this.mFocusPosition;
            int i3 = (i & 262144) != 0 ? this.mSizePrimary : 0;
            while (true) {
                int i4 = grid.mLastVisibleIndex;
                int i5 = grid.mFirstVisibleIndex;
                if (i4 < i5 || i5 >= i2) {
                    break;
                }
                int size = grid.mProvider.getSize(i5);
                if (!grid.mReversedFlow) {
                    if (grid.mProvider.getEdge(grid.mFirstVisibleIndex) + size > i3) {
                        break;
                    }
                    grid.mProvider.removeItem(grid.mFirstVisibleIndex);
                    grid.mFirstVisibleIndex++;
                } else {
                    if (grid.mProvider.getEdge(grid.mFirstVisibleIndex) - size < i3) {
                        break;
                    }
                    grid.mProvider.removeItem(grid.mFirstVisibleIndex);
                    grid.mFirstVisibleIndex++;
                }
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

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001b, code lost:
    
        if (r7 <= r0) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x002d, code lost:
    
        r7 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002b, code lost:
    
        if (r7 >= r0) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int scrollDirectionPrimary(int r7) {
        /*
            r6 = this;
            int r0 = r6.mFlag
            r1 = r0 & 64
            r2 = 1
            if (r1 != 0) goto L2e
            r0 = r0 & 3
            if (r0 == r2) goto L2e
            androidx.leanback.widget.WindowAlignment r0 = r6.mWindowAlignment
            if (r7 <= 0) goto L1e
            androidx.leanback.widget.WindowAlignment$Axis r0 = r0.mMainAxis
            int r1 = r0.mMaxEdge
            r3 = 2147483647(0x7fffffff, float:NaN)
            if (r1 != r3) goto L19
            goto L2e
        L19:
            int r0 = r0.mMaxScroll
            if (r7 <= r0) goto L2e
            goto L2d
        L1e:
            if (r7 >= 0) goto L2e
            androidx.leanback.widget.WindowAlignment$Axis r0 = r0.mMainAxis
            int r1 = r0.mMinEdge
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r1 != r3) goto L29
            goto L2e
        L29:
            int r0 = r0.mMinScroll
            if (r7 >= r0) goto L2e
        L2d:
            r7 = r0
        L2e:
            r0 = 0
            if (r7 != 0) goto L32
            return r0
        L32:
            int r1 = -r7
            int r3 = r6.getChildCount()
            int r4 = r6.mOrientation
            if (r4 != r2) goto L48
            r4 = r0
        L3c:
            if (r4 >= r3) goto L55
            android.view.View r5 = r6.getChildAt(r4)
            r5.offsetTopAndBottom(r1)
            int r4 = r4 + 1
            goto L3c
        L48:
            r4 = r0
        L49:
            if (r4 >= r3) goto L55
            android.view.View r5 = r6.getChildAt(r4)
            r5.offsetLeftAndRight(r1)
            int r4 = r4 + 1
            goto L49
        L55:
            int r1 = r6.mFlag
            r1 = r1 & 3
            if (r1 != r2) goto L5f
            r6.updateScrollLimits()
            return r7
        L5f:
            int r1 = r6.getChildCount()
            int r3 = r6.mFlag
            r4 = 262144(0x40000, float:3.67342E-40)
            r3 = r3 & r4
            if (r3 == 0) goto L6d
            if (r7 <= 0) goto L73
            goto L6f
        L6d:
            if (r7 >= 0) goto L73
        L6f:
            r6.prependVisibleItems()
            goto L76
        L73:
            r6.appendVisibleItems()
        L76:
            int r3 = r6.getChildCount()
            if (r3 <= r1) goto L7e
            r1 = r2
            goto L7f
        L7e:
            r1 = r0
        L7f:
            int r3 = r6.getChildCount()
            int r5 = r6.mFlag
            r4 = r4 & r5
            if (r4 == 0) goto L8b
            if (r7 <= 0) goto L91
            goto L8d
        L8b:
            if (r7 >= 0) goto L91
        L8d:
            r6.removeInvisibleViewsAtEnd()
            goto L94
        L91:
            r6.removeInvisibleViewsAtFront()
        L94:
            int r4 = r6.getChildCount()
            if (r4 >= r3) goto L9b
            goto L9c
        L9b:
            r2 = r0
        L9c:
            r0 = r1 | r2
            if (r0 == 0) goto La3
            r6.updateRowSecondarySizeRefresh()
        La3:
            androidx.leanback.widget.BaseGridView r0 = r6.mBaseGridView
            r0.invalidate()
            r6.updateScrollLimits()
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.scrollDirectionPrimary(int):int");
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
        if ((this.mFlag & 512) == 0 || this.mGrid == null) {
            return 0;
        }
        saveContext(recycler, state);
        this.mFlag = (this.mFlag & (-4)) | 2;
        int scrollDirectionPrimary = this.mOrientation == 0 ? scrollDirectionPrimary(i) : scrollDirectionSecondary(i);
        leaveContext();
        this.mFlag &= -4;
        return scrollDirectionPrimary;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void scrollToPosition(int i) {
        setSelection(i, false);
    }

    public final void scrollToSelection(int i, boolean z) {
        View findViewByPosition = findViewByPosition(i);
        RecyclerView.SmoothScroller smoothScroller = this.mSmoothScroller;
        boolean z2 = smoothScroller != null && smoothScroller.mRunning;
        if (!z2 && !this.mBaseGridView.isLayoutRequested() && findViewByPosition != null && getAdapterPositionByView(findViewByPosition) == i) {
            this.mFlag |= 32;
            scrollToView(findViewByPosition, z);
            this.mFlag &= -33;
            return;
        }
        int i2 = this.mFlag;
        if ((i2 & 512) == 0 || (i2 & 64) != 0) {
            this.mFocusPosition = i;
            this.mSubFocusPosition = 0;
            this.mFocusPositionOffset = Integer.MIN_VALUE;
            return;
        }
        if (z && !this.mBaseGridView.isLayoutRequested()) {
            this.mFocusPosition = i;
            this.mSubFocusPosition = 0;
            this.mFocusPositionOffset = Integer.MIN_VALUE;
            if (this.mGrid == null) {
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
                    int position = RecyclerView.LayoutManager.getPosition(gridLayoutManager.getChildAt(0));
                    int i4 = ((gridLayoutManager.mFlag & 262144) == 0 ? i3 >= position : i3 <= position) ? 1 : -1;
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
        if (z2) {
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
            this.mFocusPositionOffset = Integer.MIN_VALUE;
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
        if ((i2 & 512) == 0 || this.mGrid == null) {
            return 0;
        }
        this.mFlag = (i2 & (-4)) | 2;
        saveContext(recycler, state);
        int scrollDirectionPrimary = this.mOrientation == 1 ? scrollDirectionPrimary(i) : scrollDirectionSecondary(i);
        leaveContext();
        this.mFlag &= -4;
        return scrollDirectionPrimary;
    }

    public final void setOrientation(int i) {
        if (i == 0 || i == 1) {
            this.mOrientation = i;
            this.mOrientationHelper = OrientationHelper.createOrientationHelper(this, i);
            WindowAlignment windowAlignment = this.mWindowAlignment;
            windowAlignment.getClass();
            WindowAlignment.Axis axis = windowAlignment.vertical;
            WindowAlignment.Axis axis2 = windowAlignment.horizontal;
            if (i == 0) {
                windowAlignment.mMainAxis = axis2;
                windowAlignment.mSecondAxis = axis;
            } else {
                windowAlignment.mMainAxis = axis;
                windowAlignment.mSecondAxis = axis2;
            }
            this.mItemAlignment.getClass();
            this.mFlag |= 256;
        }
    }

    public final void setRowHeight(int i) {
        if (i < 0 && i != -2) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid row height: "));
        }
        this.mRowSizeSecondaryRequested = i;
    }

    public final void setSelection(int i, boolean z) {
        if ((this.mFocusPosition == i || i == -1) && this.mSubFocusPosition == 0) {
            return;
        }
        scrollToSelection(i, z);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void smoothScrollToPosition(RecyclerView recyclerView, int i) {
        setSelection(i, true);
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
            this.mPositionDeltaInPreLayout = this.mGrid.mFirstVisibleIndex - ((LayoutParams) getChildAt(0).getLayoutParams()).mViewHolder.getLayoutPosition();
        }
    }

    public final void updateRowSecondarySizeRefresh() {
        int i = (this.mFlag & KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN) | (processRowSizeSecondary(false) ? 1024 : 0);
        this.mFlag = i;
        if ((i & 1024) != 0) {
            BaseGridView baseGridView = this.mBaseGridView;
            AnonymousClass1 anonymousClass1 = this.mRequestLayoutRunnable;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            baseGridView.postOnAnimation(anonymousClass1);
        }
    }

    public final void updateScrollLimits() {
        int itemCount;
        int i;
        int i2;
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
            i = this.mGrid.mLastVisibleIndex;
            int itemCount2 = this.mState.getItemCount() - 1;
            i2 = this.mGrid.mFirstVisibleIndex;
            i3 = itemCount2;
            itemCount = 0;
        } else {
            Grid grid = this.mGrid;
            int i8 = grid.mFirstVisibleIndex;
            int i9 = grid.mLastVisibleIndex;
            itemCount = this.mState.getItemCount() - 1;
            i = i8;
            i2 = i9;
            i3 = 0;
        }
        if (i < 0 || i2 < 0) {
            return;
        }
        boolean z = i == i3;
        boolean z2 = i2 == itemCount;
        int i10 = Integer.MIN_VALUE;
        int i11 = Integer.MAX_VALUE;
        WindowAlignment windowAlignment = this.mWindowAlignment;
        if (!z) {
            WindowAlignment.Axis axis = windowAlignment.mMainAxis;
            if (axis.mMaxEdge == Integer.MAX_VALUE && !z2 && axis.mMinEdge == Integer.MIN_VALUE) {
                return;
            }
        }
        int[] iArr = sTwoInts;
        if (z) {
            i11 = this.mGrid.findRowMax(true, iArr);
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
            int i12 = top2 + i7;
            int[] iArr2 = ((LayoutParams) findViewByPosition.getLayoutParams()).mAlignMultiple;
            i4 = (iArr2 == null || iArr2.length <= 0) ? i12 : (iArr2[iArr2.length - 1] - iArr2[0]) + i12;
        } else {
            i4 = Integer.MAX_VALUE;
        }
        if (z2) {
            i10 = this.mGrid.findRowMin(false, iArr);
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
            i5 = top + i6;
        } else {
            i5 = Integer.MIN_VALUE;
        }
        windowAlignment.mMainAxis.updateMinMax(i10, i11, i5, i4);
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
        this.mOrientationHelper = new OrientationHelper.AnonymousClass1(this);
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
        this.mGridProvider = new AnonymousClass2();
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
        getSubPositionByView(view, view2);
        if (adapterPositionByView != this.mFocusPosition || this.mSubFocusPosition != 0) {
            this.mFocusPosition = adapterPositionByView;
            this.mSubFocusPosition = 0;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        public Bundle mChildStates;
        public int mIndex;

        public SavedState(Parcel parcel) {
            this.mChildStates = Bundle.EMPTY;
            this.mIndex = parcel.readInt();
            this.mChildStates = parcel.readBundle(GridLayoutManager.class.getClassLoader());
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mIndex);
            parcel.writeBundle(this.mChildStates);
        }

        public SavedState() {
            this.mChildStates = Bundle.EMPTY;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onLayoutCompleted(RecyclerView.State state) {
    }
}
