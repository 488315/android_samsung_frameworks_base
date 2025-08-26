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
import android.view.FocusFinder;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
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
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

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
            View viewFindViewByPosition;
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
                    while (pendingMoveSmoothScroller.mPendingMoves != 0 && (viewFindViewByPosition = pendingMoveSmoothScroller.findViewByPosition(i4)) != null) {
                        gridLayoutManager2.getClass();
                        if (viewFindViewByPosition.getVisibility() == 0 && (!gridLayoutManager2.hasFocus() || viewFindViewByPosition.hasFocusable())) {
                            gridLayoutManager2.mFocusPosition = i4;
                            gridLayoutManager2.mSubFocusPosition = 0;
                            int i5 = pendingMoveSmoothScroller.mPendingMoves;
                            if (i5 > 0) {
                                pendingMoveSmoothScroller.mPendingMoves = i5 - 1;
                            } else {
                                pendingMoveSmoothScroller.mPendingMoves = i5 + 1;
                            }
                            view = viewFindViewByPosition;
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
            View viewFindViewByPosition = gridLayoutManager.findViewByPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
            return (gridLayoutManager.mFlag & 262144) != 0 ? gridLayoutManager.mOrientationHelper.getDecoratedEnd(viewFindViewByPosition) : gridLayoutManager.mOrientationHelper.getDecoratedStart(viewFindViewByPosition);
        }

        public final int getSize(int i) {
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            View viewFindViewByPosition = gridLayoutManager.findViewByPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
            Rect rect = GridLayoutManager.sTempRect;
            gridLayoutManager.getDecoratedBoundsWithMargins(rect, viewFindViewByPosition);
            return gridLayoutManager.mOrientation == 0 ? rect.width() : rect.height();
        }

        public final void removeItem(int i) {
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            View viewFindViewByPosition = gridLayoutManager.findViewByPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
            if ((gridLayoutManager.mFlag & 3) == 1) {
                gridLayoutManager.scrapOrRecycleView(gridLayoutManager.mRecycler, gridLayoutManager.mChildHelper.indexOfChild(viewFindViewByPosition), viewFindViewByPosition);
            } else {
                gridLayoutManager.removeAndRecycleView(viewFindViewByPosition, gridLayoutManager.mRecycler);
            }
        }
    }

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
            int iCalculateTimeForScrolling = super.calculateTimeForScrolling(i);
            int i2 = GridLayoutManager.this.mWindowAlignment.mMainAxis.mSize;
            if (i2 > 0) {
                float f = (30.0f / i2) * i;
                if (iCalculateTimeForScrolling < f) {
                    return (int) f;
                }
            }
            return iCalculateTimeForScrolling;
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
            View viewFindViewByPosition = findViewByPosition(this.mTargetPosition);
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            if (viewFindViewByPosition == null) {
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
                viewFindViewByPosition.requestFocus();
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
            View viewFindViewByPosition = findViewByPosition(this.mTargetPosition);
            if (viewFindViewByPosition != null) {
                GridLayoutManager.this.scrollToView(viewFindViewByPosition, true);
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
        int iMax = Math.max(0, Math.min(this.mFocusPosition - ((i2 - 1) / 2), i - i2));
        for (int i3 = iMax; i3 < i && i3 < iMax + i2; i3++) {
            layoutPrefetchRegistryImpl.addPosition(i3, 0);
        }
    }

    public final void dispatchChildSelected() {
        ArrayList arrayList = this.mChildViewHolderSelectedListeners;
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        int i = this.mFocusPosition;
        View viewFindViewByPosition = i == -1 ? null : findViewByPosition(i);
        if (viewFindViewByPosition != null) {
            RecyclerView.ViewHolder childViewHolder = this.mBaseGridView.getChildViewHolder(viewFindViewByPosition);
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
        View viewFindViewByPosition = i == -1 ? null : findViewByPosition(i);
        if (viewFindViewByPosition == null) {
            ArrayList arrayList2 = this.mChildViewHolderSelectedListeners;
            if (arrayList2 == null) {
                return;
            }
            for (int size = arrayList2.size() - 1; size >= 0; size--) {
                ((OnChildViewHolderSelectedListener) this.mChildViewHolderSelectedListeners.get(size)).getClass();
            }
            return;
        }
        this.mBaseGridView.getChildViewHolder(viewFindViewByPosition);
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
        int rowSizeSecondary = 0;
        if ((this.mFlag & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0) {
            for (int i2 = this.mNumRows - 1; i2 > i; i2--) {
                rowSizeSecondary += getRowSizeSecondary(i2) + this.mSpacingSecondary;
            }
            return rowSizeSecondary;
        }
        int rowSizeSecondary2 = 0;
        while (rowSizeSecondary < i) {
            rowSizeSecondary2 += getRowSizeSecondary(rowSizeSecondary) + this.mSpacingSecondary;
            rowSizeSecondary++;
        }
        return rowSizeSecondary2;
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
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.mBaseGridView.findViewHolderForAdapterPosition(i);
        return viewHolderFindViewHolderForAdapterPosition != null && viewHolderFindViewHolderForAdapterPosition.itemView.getLeft() >= 0 && viewHolderFindViewHolderForAdapterPosition.itemView.getRight() <= this.mBaseGridView.getWidth() && viewHolderFindViewHolderForAdapterPosition.itemView.getTop() >= 0 && viewHolderFindViewHolderForAdapterPosition.itemView.getBottom() <= this.mBaseGridView.getHeight();
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
        int childMeasureSpec;
        int childMeasureSpec2;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect = sTempRect;
        calculateItemDecorationsForChild(rect, view);
        int i = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + rect.left + rect.right;
        int i2 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + rect.top + rect.bottom;
        int iMakeMeasureSpec = this.mRowSizeSecondaryRequested == -2 ? View.MeasureSpec.makeMeasureSpec(0, 0) : View.MeasureSpec.makeMeasureSpec(this.mFixedRowSizeSecondary, 1073741824);
        if (this.mOrientation == 0) {
            childMeasureSpec2 = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, 0), i, ((ViewGroup.MarginLayoutParams) layoutParams).width);
            childMeasureSpec = ViewGroup.getChildMeasureSpec(iMakeMeasureSpec, i2, ((ViewGroup.MarginLayoutParams) layoutParams).height);
        } else {
            int childMeasureSpec3 = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, 0), i2, ((ViewGroup.MarginLayoutParams) layoutParams).height);
            int childMeasureSpec4 = ViewGroup.getChildMeasureSpec(iMakeMeasureSpec, i, ((ViewGroup.MarginLayoutParams) layoutParams).width);
            childMeasureSpec = childMeasureSpec3;
            childMeasureSpec2 = childMeasureSpec4;
        }
        view.measure(childMeasureSpec2, childMeasureSpec);
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
    /* JADX WARN: Removed duplicated region for block: B:26:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x007a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00b1  */
    /* JADX WARN: Type inference failed for: r16v11, types: [boolean] */
    /* JADX WARN: Type inference failed for: r16v2 */
    /* JADX WARN: Type inference failed for: r16v3 */
    /* JADX WARN: Type inference failed for: r16v6, types: [boolean] */
    /* JADX WARN: Type inference failed for: r16v7 */
    /* JADX WARN: Type inference failed for: r16v8 */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onAddFocusables(RecyclerView recyclerView, ArrayList arrayList, int i, int i2) {
        int i3;
        int adapterPositionByView;
        View viewFindViewByPosition;
        char c;
        char c2;
        int i4;
        int childCount;
        ?? r16;
        char c3;
        char c4;
        BaseGridView baseGridView;
        View viewFindContainingItemView;
        int i5 = 1;
        if ((this.mFlag & NetworkAnalyticsConstants.DataPoints.FLAG_UID) == 0) {
            if (!recyclerView.hasFocus()) {
                int size = arrayList.size();
                View viewFindViewByPosition2 = findViewByPosition(this.mFocusPosition);
                if (viewFindViewByPosition2 != null) {
                    viewFindViewByPosition2.addFocusables(arrayList, i, i2);
                }
                if (arrayList.size() != size || !recyclerView.isFocusable()) {
                    return true;
                }
                arrayList.add(recyclerView);
                return true;
            }
            if (this.mPendingMoveSmoothScroller == null) {
                int movement = getMovement(i);
                View viewFindFocus = recyclerView.findFocus();
                if (viewFindFocus == null || (baseGridView = this.mBaseGridView) == null || viewFindFocus == baseGridView || (viewFindContainingItemView = findContainingItemView(viewFindFocus)) == null) {
                    i3 = -1;
                    adapterPositionByView = getAdapterPositionByView(getChildAt(i3));
                    viewFindViewByPosition = adapterPositionByView != -1 ? null : findViewByPosition(adapterPositionByView);
                    if (viewFindViewByPosition != null) {
                        viewFindViewByPosition.addFocusables(arrayList, i, i2);
                    }
                    if (this.mGrid != null && getChildCount() != 0) {
                        c = 2;
                        c2 = 3;
                        if ((movement == 3 && movement != 2) || this.mGrid.mNumRows > 1) {
                            Grid grid = this.mGrid;
                            int i6 = (grid == null || viewFindViewByPosition == null) ? -1 : grid.getLocation(adapterPositionByView).mRow;
                            int size2 = arrayList.size();
                            i4 = (movement != 1 || movement == 3) ? 1 : -1;
                            int childCount2 = i4 <= 0 ? getChildCount() - 1 : 0;
                            childCount = i3 != -1 ? i4 > 0 ? 0 : getChildCount() - 1 : i3 + i4;
                            while (true) {
                                if (i4 > 0) {
                                    if (childCount < childCount2) {
                                        break;
                                    }
                                } else {
                                    if (childCount > childCount2) {
                                        break;
                                    }
                                    View childAt = getChildAt(childCount);
                                    if (childAt.getVisibility() != 0 || !childAt.hasFocusable()) {
                                        r16 = i5;
                                        c3 = c;
                                        c4 = c2;
                                    } else if (viewFindViewByPosition == null) {
                                        childAt.addFocusables(arrayList, i, i2);
                                        if (arrayList.size() > size2) {
                                            break;
                                        }
                                        r16 = i5;
                                        c3 = c;
                                        c4 = c2;
                                    } else {
                                        int adapterPositionByView2 = getAdapterPositionByView(getChildAt(childCount));
                                        Grid.Location location = this.mGrid.getLocation(adapterPositionByView2);
                                        if (location == null) {
                                            r16 = i5;
                                            c3 = 2;
                                            c4 = 3;
                                        } else {
                                            int i7 = location.mRow;
                                            if (movement == i5) {
                                                if (i7 == i6 && adapterPositionByView2 > adapterPositionByView) {
                                                    childAt.addFocusables(arrayList, i, i2);
                                                    if (arrayList.size() > size2) {
                                                        break;
                                                    }
                                                }
                                                r16 = i5;
                                                c3 = 2;
                                                c4 = 3;
                                            } else if (movement == 0) {
                                                if (i7 == i6 && adapterPositionByView2 < adapterPositionByView) {
                                                    childAt.addFocusables(arrayList, i, i2);
                                                    if (arrayList.size() > size2) {
                                                        break;
                                                    }
                                                }
                                                r16 = i5;
                                                c3 = 2;
                                                c4 = 3;
                                            } else {
                                                c4 = 3;
                                                if (movement == 3) {
                                                    if (i7 != i6) {
                                                        if (i7 < i6) {
                                                            break;
                                                        }
                                                        childAt.addFocusables(arrayList, i, i2);
                                                    }
                                                    r16 = i5;
                                                    c3 = 2;
                                                } else {
                                                    r16 = i5;
                                                    c3 = 2;
                                                    if (movement == 2 && i7 != i6) {
                                                        if (i7 > i6) {
                                                            return r16;
                                                        }
                                                        childAt.addFocusables(arrayList, i, i2);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    childCount += i4;
                                    c2 = c4;
                                    c = c3;
                                    i5 = r16;
                                }
                            }
                        }
                    }
                } else {
                    int childCount3 = getChildCount();
                    i3 = 0;
                    while (i3 < childCount3) {
                        if (getChildAt(i3) == viewFindContainingItemView) {
                            break;
                        }
                        i3++;
                    }
                    i3 = -1;
                    adapterPositionByView = getAdapterPositionByView(getChildAt(i3));
                    if (adapterPositionByView != -1) {
                    }
                    if (viewFindViewByPosition != null) {
                    }
                    if (this.mGrid != null) {
                        c = 2;
                        c2 = 3;
                        if (movement == 3) {
                            Grid grid2 = this.mGrid;
                            if (grid2 == null) {
                                int size22 = arrayList.size();
                                if (movement != 1) {
                                    if (i4 <= 0) {
                                    }
                                    if (i3 != -1) {
                                    }
                                    while (true) {
                                        if (i4 > 0) {
                                        }
                                        childCount += i4;
                                        c2 = c4;
                                        c = c3;
                                        i5 = r16;
                                    }
                                }
                            }
                        } else {
                            Grid grid22 = this.mGrid;
                            if (grid22 == null) {
                            }
                        }
                    }
                }
            }
        }
        return i5;
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

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final View onInterceptFocusSearch(View view, int i) {
        View viewFindNextFocus;
        View viewFindNextFocus2;
        if ((this.mFlag & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0) {
            return view;
        }
        FocusFinder focusFinder = FocusFinder.getInstance();
        if (i == 2 || i == 1) {
            if (canScrollVertically()) {
                viewFindNextFocus = focusFinder.findNextFocus(this.mBaseGridView, view, i == 2 ? 130 : 33);
            } else {
                viewFindNextFocus = null;
            }
            if (canScrollHorizontally()) {
                viewFindNextFocus2 = focusFinder.findNextFocus(this.mBaseGridView, view, (getLayoutDirection() == 1) ^ (i == 2) ? 66 : 17);
            } else {
                viewFindNextFocus2 = viewFindNextFocus;
            }
        } else {
            viewFindNextFocus2 = focusFinder.findNextFocus(this.mBaseGridView, view, i);
        }
        if (viewFindNextFocus2 != null) {
            return viewFindNextFocus2;
        }
        if (this.mBaseGridView.getDescendantFocusability() == 393216) {
            return this.mBaseGridView.getParent().focusSearch(view, i);
        }
        int movement = getMovement(i);
        boolean z = this.mBaseGridView.mScrollState != 0;
        if (movement == 1) {
            if (z || (this.mFlag & 4096) == 0) {
                viewFindNextFocus2 = view;
            }
            if ((this.mFlag & 131072) != 0 && !hasCreatedLastItem()) {
                processPendingMovement(true);
                viewFindNextFocus2 = view;
            }
        } else if (movement == 0) {
            if (z || (this.mFlag & 2048) == 0) {
                viewFindNextFocus2 = view;
            }
            if ((this.mFlag & 131072) != 0 && getItemCount() != 0 && this.mBaseGridView.findViewHolderForAdapterPosition(0) == null) {
                processPendingMovement(false);
                viewFindNextFocus2 = view;
            }
        } else if (movement == 3) {
        }
        if (viewFindNextFocus2 != null) {
            return viewFindNextFocus2;
        }
        View viewFocusSearch = this.mBaseGridView.getParent().focusSearch(view, i);
        return viewFocusSearch != null ? viewFocusSearch : view != null ? view : this.mBaseGridView;
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

    /* JADX WARN: Removed duplicated region for block: B:124:0x02a4 A[LOOP:3: B:123:0x02a2->B:124:0x02a4, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:127:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x02d7 A[LOOP:11: B:133:0x02d7->B:351:?, LOOP_START] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0327  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x03b5  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x03ca A[LOOP:12: B:173:0x03ca->B:352:?, LOOP_START] */
    /* JADX WARN: Removed duplicated region for block: B:297:0x05be A[PHI: r1 r2
      0x05be: PHI (r1v41 int) = (r1v37 int), (r1v44 int) binds: [B:308:0x05eb, B:296:0x05bc] A[DONT_GENERATE, DONT_INLINE]
      0x05be: PHI (r2v45 int) = (r2v41 int), (r2v48 int) binds: [B:308:0x05eb, B:296:0x05bc] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i;
        int i2;
        boolean z;
        View view;
        boolean z2;
        Grid singleRow;
        int i3;
        int i4;
        int left;
        int right;
        int i5;
        List list;
        int size;
        Grid grid;
        int i6;
        int i7;
        int i8;
        int i9;
        Grid.Location location;
        int i10;
        int i11;
        Grid.Location location2;
        if (this.mNumRows != 0 && state.getItemCount() >= 0) {
            if ((this.mFlag & 64) != 0 && getChildCount() > 0) {
                this.mFlag |= 128;
                return;
            }
            int i12 = this.mFlag;
            if ((i12 & 512) == 0) {
                this.mGrid = null;
                this.mRowSizeSecondary = null;
                this.mFlag = i12 & KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN;
                removeAndRecycleAllViews(recycler);
                return;
            }
            this.mFlag = (i12 & (-4)) | 1;
            saveContext(recycler, state);
            int iMax = Integer.MIN_VALUE;
            if (state.mInPreLayout) {
                updatePositionDeltaInPreLayout();
                int childCount = getChildCount();
                if (this.mGrid != null && childCount > 0) {
                    int i13 = this.mBaseGridView.getChildViewHolder(getChildAt(0)).mOldPosition;
                    int i14 = this.mBaseGridView.getChildViewHolder(getChildAt(childCount - 1)).mOldPosition;
                    int iMin = Integer.MAX_VALUE;
                    for (int i15 = 0; i15 < childCount; i15++) {
                        View childAt = getChildAt(i15);
                        LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                        this.mBaseGridView.getClass();
                        int childAdapterPosition = RecyclerView.getChildAdapterPosition(childAt);
                        if (layoutParams.mViewHolder.isUpdated() || layoutParams.mViewHolder.isRemoved() || childAt.isLayoutRequested() || ((!childAt.hasFocus() && this.mFocusPosition == layoutParams.mViewHolder.getAbsoluteAdapterPosition()) || ((childAt.hasFocus() && this.mFocusPosition != layoutParams.mViewHolder.getAbsoluteAdapterPosition()) || childAdapterPosition < i13 || childAdapterPosition > i14))) {
                            iMin = Math.min(iMin, this.mOrientationHelper.getDecoratedStart(childAt));
                            iMax = Math.max(iMax, this.mOrientationHelper.getDecoratedEnd(childAt));
                        }
                    }
                    if (iMax > iMin) {
                        this.mExtraLayoutSpaceInPreLayout = iMax - iMin;
                    }
                    appendVisibleItems();
                    prependVisibleItems();
                }
                this.mFlag &= -4;
                leaveContext();
                return;
            }
            if (state.mRunPredictiveAnimations) {
                this.mPositionToRowInPostLayout.clear();
                int childCount2 = getChildCount();
                for (int i16 = 0; i16 < childCount2; i16++) {
                    int i17 = this.mBaseGridView.getChildViewHolder(getChildAt(i16)).mOldPosition;
                    if (i17 >= 0 && (location2 = this.mGrid.getLocation(i17)) != null) {
                        this.mPositionToRowInPostLayout.put(i17, location2.mRow);
                    }
                }
            }
            RecyclerView.SmoothScroller smoothScroller = this.mSmoothScroller;
            boolean z3 = smoothScroller != null && smoothScroller.mRunning;
            int i18 = this.mFocusPosition;
            if (i18 != -1 && (i11 = this.mFocusPositionOffset) != Integer.MIN_VALUE) {
                this.mFocusPosition = i18 + i11;
                this.mSubFocusPosition = 0;
            }
            this.mFocusPositionOffset = 0;
            View viewFindViewByPosition = findViewByPosition(this.mFocusPosition);
            int i19 = this.mFocusPosition;
            int i20 = this.mSubFocusPosition;
            boolean zHasFocus = this.mBaseGridView.hasFocus();
            Grid grid2 = this.mGrid;
            int i21 = grid2 != null ? grid2.mFirstVisibleIndex : -1;
            int i22 = grid2 != null ? grid2.mLastVisibleIndex : -1;
            if (this.mOrientation == 0) {
                i2 = state.mRemainingScrollHorizontal;
                i = state.mRemainingScrollVertical;
            } else {
                i = state.mRemainingScrollHorizontal;
                i2 = state.mRemainingScrollVertical;
            }
            int itemCount = this.mState.getItemCount();
            if (itemCount == 0) {
                this.mFocusPosition = -1;
                this.mSubFocusPosition = 0;
            } else {
                int i23 = this.mFocusPosition;
                if (i23 >= itemCount) {
                    this.mFocusPosition = itemCount - 1;
                    this.mSubFocusPosition = 0;
                } else if (i23 == -1 && itemCount > 0) {
                    this.mFocusPosition = 0;
                    this.mSubFocusPosition = 0;
                }
            }
            boolean z4 = this.mState.mStructureChanged;
            WindowAlignment windowAlignment = this.mWindowAlignment;
            if (z4 || (grid = this.mGrid) == null || grid.mFirstVisibleIndex < 0 || (this.mFlag & 256) != 0 || grid.mNumRows != this.mNumRows) {
                z = z3;
                view = viewFindViewByPosition;
                z2 = zHasFocus;
                int i24 = this.mFlag;
                this.mFlag = i24 & (-257);
                Grid grid3 = this.mGrid;
                if (grid3 == null || this.mNumRows != grid3.mNumRows) {
                    int i25 = this.mNumRows;
                    if (i25 == 1) {
                        singleRow = new SingleRow();
                    } else {
                        StaggeredGridDefault staggeredGridDefault = new StaggeredGridDefault();
                        staggeredGridDefault.setNumRows(i25);
                        singleRow = staggeredGridDefault;
                    }
                    this.mGrid = singleRow;
                    singleRow.mProvider = this.mGridProvider;
                    singleRow.mReversedFlow = (this.mFlag & 262144) != 0;
                    WindowAlignment.Axis axis = windowAlignment.mMainAxis;
                    axis.mMinEdge = Integer.MIN_VALUE;
                    axis.mMaxEdge = Integer.MAX_VALUE;
                    int i26 = this.mWidth;
                    WindowAlignment.Axis axis2 = windowAlignment.horizontal;
                    axis2.mSize = i26;
                    int i27 = this.mHeight;
                    WindowAlignment.Axis axis3 = windowAlignment.vertical;
                    axis3.mSize = i27;
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
                    Grid grid4 = this.mGrid;
                    grid4.mLastVisibleIndex = -1;
                    grid4.mFirstVisibleIndex = -1;
                    WindowAlignment.Axis axis4 = windowAlignment.mMainAxis;
                    axis4.mMinEdge = Integer.MIN_VALUE;
                    axis4.mMinScroll = Integer.MIN_VALUE;
                    axis4.mMaxEdge = Integer.MAX_VALUE;
                    axis4.mMaxScroll = Integer.MAX_VALUE;
                    int i28 = this.mFlag;
                    this.mFlag = i28 & (-5);
                    this.mFlag = (i28 & (-21)) | (z ? 16 : 0);
                    if (!z && (i21 < 0 || (i3 = this.mFocusPosition) > i22 || i3 < i21)) {
                        i21 = this.mFocusPosition;
                        i22 = i21;
                    }
                    grid4.mStartIndex = i21;
                    if (i22 != -1) {
                        while (this.mGrid.appendOneColumnVisibleItems() && findViewByPosition(i22) == null) {
                        }
                    }
                } else {
                    if (((i24 & 262144) != 0) != grid3.mReversedFlow) {
                    }
                    WindowAlignment.Axis axis5 = windowAlignment.mMainAxis;
                    axis5.mMinEdge = Integer.MIN_VALUE;
                    axis5.mMaxEdge = Integer.MAX_VALUE;
                    int i262 = this.mWidth;
                    WindowAlignment.Axis axis22 = windowAlignment.horizontal;
                    axis22.mSize = i262;
                    int i272 = this.mHeight;
                    WindowAlignment.Axis axis32 = windowAlignment.vertical;
                    axis32.mSize = i272;
                    int paddingLeft2 = getPaddingLeft();
                    int paddingRight2 = getPaddingRight();
                    axis22.mPaddingMin = paddingLeft2;
                    axis22.mPaddingMax = paddingRight2;
                    int paddingTop2 = getPaddingTop();
                    int paddingBottom2 = getPaddingBottom();
                    axis32.mPaddingMin = paddingTop2;
                    axis32.mPaddingMax = paddingBottom2;
                    this.mSizePrimary = windowAlignment.mMainAxis.mSize;
                    this.mScrollOffsetSecondary = 0;
                    updateSecondaryScrollLimits();
                    this.mGrid.mSpacing = this.mSpacingPrimary;
                    detachAndScrapAttachedViews(this.mRecycler);
                    Grid grid42 = this.mGrid;
                    grid42.mLastVisibleIndex = -1;
                    grid42.mFirstVisibleIndex = -1;
                    WindowAlignment.Axis axis42 = windowAlignment.mMainAxis;
                    axis42.mMinEdge = Integer.MIN_VALUE;
                    axis42.mMinScroll = Integer.MIN_VALUE;
                    axis42.mMaxEdge = Integer.MAX_VALUE;
                    axis42.mMaxScroll = Integer.MAX_VALUE;
                    int i282 = this.mFlag;
                    this.mFlag = i282 & (-5);
                    this.mFlag = (i282 & (-21)) | (z ? 16 : 0);
                    if (!z) {
                        i21 = this.mFocusPosition;
                        i22 = i21;
                    }
                    grid42.mStartIndex = i21;
                    if (i22 != -1) {
                    }
                }
            } else {
                WindowAlignment.Axis axis6 = windowAlignment.horizontal;
                axis6.mSize = this.mWidth;
                int i29 = this.mHeight;
                WindowAlignment.Axis axis7 = windowAlignment.vertical;
                axis7.mSize = i29;
                int paddingLeft3 = getPaddingLeft();
                int paddingRight3 = getPaddingRight();
                axis6.mPaddingMin = paddingLeft3;
                axis6.mPaddingMax = paddingRight3;
                int paddingTop3 = getPaddingTop();
                int paddingBottom3 = getPaddingBottom();
                axis7.mPaddingMin = paddingTop3;
                axis7.mPaddingMax = paddingBottom3;
                this.mSizePrimary = windowAlignment.mMainAxis.mSize;
                updateSecondaryScrollLimits();
                Grid grid5 = this.mGrid;
                grid5.mSpacing = this.mSpacingPrimary;
                this.mFlag |= 4;
                grid5.mStartIndex = this.mFocusPosition;
                int childCount3 = getChildCount();
                int i30 = this.mGrid.mFirstVisibleIndex;
                this.mFlag &= -9;
                int i31 = 0;
                while (i31 < childCount3) {
                    View childAt2 = getChildAt(i31);
                    if (i30 == getAdapterPositionByView(childAt2) && (location = this.mGrid.getLocation(i30)) != null) {
                        i6 = childCount3;
                        int rowStartSecondary = (getRowStartSecondary(location.mRow) + windowAlignment.mSecondAxis.mPaddingMin) - this.mScrollOffsetSecondary;
                        int decoratedStart = this.mOrientationHelper.getDecoratedStart(childAt2);
                        WindowAlignment windowAlignment2 = windowAlignment;
                        Rect rect = sTempRect;
                        getDecoratedBoundsWithMargins(rect, childAt2);
                        int iWidth = this.mOrientation == 0 ? rect.width() : rect.height();
                        if ((((LayoutParams) childAt2.getLayoutParams()).mViewHolder.mFlags & 2) != 0) {
                            this.mFlag |= 8;
                            i10 = decoratedStart;
                            scrapOrRecycleView(this.mRecycler, this.mChildHelper.indexOfChild(childAt2), childAt2);
                            childAt2 = getViewForPosition(i30);
                            addViewInt(childAt2, i31, false);
                        } else {
                            i10 = decoratedStart;
                        }
                        measureChild(childAt2);
                        int decoratedMeasuredWidthWithMargin = this.mOrientation == 0 ? getDecoratedMeasuredWidthWithMargin(childAt2) : getDecoratedMeasuredHeightWithMargin(childAt2);
                        int i32 = decoratedMeasuredWidthWithMargin;
                        boolean z5 = z3;
                        i8 = i31;
                        z = z5;
                        view = viewFindViewByPosition;
                        z2 = zHasFocus;
                        i7 = i30;
                        layoutChild(childAt2, location.mRow, i10, i10 + decoratedMeasuredWidthWithMargin, rowStartSecondary);
                        if (iWidth != i32) {
                            int i33 = this.mGrid.mLastVisibleIndex;
                            for (i9 = i6 - 1; i9 >= i8; i9--) {
                                View childAt3 = getChildAt(i9);
                                scrapOrRecycleView(this.mRecycler, this.mChildHelper.indexOfChild(childAt3), childAt3);
                            }
                            this.mGrid.invalidateItemsAfter(i7);
                            if ((this.mFlag & 65536) == 0) {
                                appendVisibleItems();
                                int i34 = this.mFocusPosition;
                                if (i34 >= 0 && i34 <= i33) {
                                    while (true) {
                                        Grid grid6 = this.mGrid;
                                        if (grid6.mLastVisibleIndex >= this.mFocusPosition) {
                                            break;
                                        } else {
                                            grid6.appendOneColumnVisibleItems();
                                        }
                                    }
                                }
                            } else {
                                while (this.mGrid.appendOneColumnVisibleItems() && this.mGrid.mLastVisibleIndex < i33) {
                                }
                            }
                            updateScrollLimits();
                            updateSecondaryScrollLimits();
                        } else {
                            i31 = i8 + 1;
                            i30 = i7 + 1;
                            childCount3 = i6;
                            windowAlignment = windowAlignment2;
                            z3 = z;
                            zHasFocus = z2;
                            viewFindViewByPosition = view;
                        }
                    } else {
                        i6 = childCount3;
                        z = z3;
                        view = viewFindViewByPosition;
                        z2 = zHasFocus;
                        i7 = i30;
                        i8 = i31;
                        int i332 = this.mGrid.mLastVisibleIndex;
                        while (i9 >= i8) {
                        }
                        this.mGrid.invalidateItemsAfter(i7);
                        if ((this.mFlag & 65536) == 0) {
                        }
                        updateScrollLimits();
                        updateSecondaryScrollLimits();
                    }
                }
                z = z3;
                view = viewFindViewByPosition;
                z2 = zHasFocus;
                updateScrollLimits();
                updateSecondaryScrollLimits();
            }
            while (true) {
                updateScrollLimits();
                Grid grid7 = this.mGrid;
                int i35 = grid7.mFirstVisibleIndex;
                int i36 = grid7.mLastVisibleIndex;
                int i37 = -i2;
                int i38 = -i;
                View viewFindViewByPosition2 = findViewByPosition(this.mFocusPosition);
                if (viewFindViewByPosition2 != null && !z) {
                    scrollToView(viewFindViewByPosition2, viewFindViewByPosition2.findFocus(), false, i37, i38);
                }
                if (viewFindViewByPosition2 != null && z2 && !viewFindViewByPosition2.hasFocus()) {
                    viewFindViewByPosition2.requestFocus();
                } else if (!z2 && !this.mBaseGridView.hasFocus()) {
                    if (viewFindViewByPosition2 == null || !viewFindViewByPosition2.hasFocusable()) {
                        int childCount4 = getChildCount();
                        int i39 = 0;
                        while (true) {
                            if (i39 >= childCount4) {
                                break;
                            }
                            viewFindViewByPosition2 = getChildAt(i39);
                            if (viewFindViewByPosition2 != null && viewFindViewByPosition2.hasFocusable()) {
                                this.mBaseGridView.focusableViewAvailable(viewFindViewByPosition2);
                                break;
                            }
                            i39++;
                        }
                    } else {
                        this.mBaseGridView.focusableViewAvailable(viewFindViewByPosition2);
                    }
                    if (!z && viewFindViewByPosition2 != null && viewFindViewByPosition2.hasFocus()) {
                        scrollToView(viewFindViewByPosition2, viewFindViewByPosition2.findFocus(), false, i37, i38);
                    }
                }
                appendVisibleItems();
                prependVisibleItems();
                Grid grid8 = this.mGrid;
                if (grid8.mFirstVisibleIndex == i35 && grid8.mLastVisibleIndex == i36) {
                    break;
                }
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
                int i40 = 0;
                for (int i41 = 0; i41 < size; i41++) {
                    int absoluteAdapterPosition = ((RecyclerView.ViewHolder) list.get(i41)).getAbsoluteAdapterPosition();
                    if (absoluteAdapterPosition >= 0) {
                        this.mDisappearingPositions[i40] = absoluteAdapterPosition;
                        i40++;
                    }
                }
                if (i40 > 0) {
                    Arrays.sort(this.mDisappearingPositions, 0, i40);
                    Grid grid9 = this.mGrid;
                    int[] iArr2 = this.mDisappearingPositions;
                    SparseIntArray sparseIntArray = this.mPositionToRowInPostLayout;
                    int i42 = grid9.mLastVisibleIndex;
                    int iBinarySearch = i42 >= 0 ? Arrays.binarySearch(iArr2, 0, i40, i42) : 0;
                    Object[] objArr = grid9.mTmpItem;
                    if (iBinarySearch < 0) {
                        int edge = grid9.mReversedFlow ? (grid9.mProvider.getEdge(i42) - grid9.mProvider.getSize(i42)) - grid9.mSpacing : grid9.mSpacing + grid9.mProvider.getSize(i42) + grid9.mProvider.getEdge(i42);
                        for (int i43 = (-iBinarySearch) - 1; i43 < i40; i43++) {
                            int i44 = iArr2[i43];
                            int i45 = sparseIntArray.get(i44);
                            if (i45 < 0) {
                                i45 = 0;
                            }
                            int iCreateItem = grid9.mProvider.createItem(i44, true, objArr, true);
                            grid9.mProvider.addItem(iCreateItem, i45, edge, objArr[0]);
                            edge = grid9.mReversedFlow ? (edge - iCreateItem) - grid9.mSpacing : edge + iCreateItem + grid9.mSpacing;
                        }
                    }
                    int i46 = grid9.mFirstVisibleIndex;
                    int iBinarySearch2 = i46 >= 0 ? Arrays.binarySearch(iArr2, 0, i40, i46) : 0;
                    if (iBinarySearch2 < 0) {
                        int edge2 = grid9.mReversedFlow ? grid9.mProvider.getEdge(i46) : grid9.mProvider.getEdge(i46);
                        for (int i47 = (-iBinarySearch2) - 2; i47 >= 0; i47--) {
                            int i48 = iArr2[i47];
                            int i49 = sparseIntArray.get(i48);
                            if (i49 < 0) {
                                i49 = 0;
                            }
                            int iCreateItem2 = grid9.mProvider.createItem(i48, false, objArr, true);
                            edge2 = grid9.mReversedFlow ? edge2 + grid9.mSpacing + iCreateItem2 : (edge2 - grid9.mSpacing) - iCreateItem2;
                            grid9.mProvider.addItem(iCreateItem2, i49, edge2, objArr[0]);
                        }
                    }
                }
                this.mPositionToRowInPostLayout.clear();
            }
            int i50 = this.mFlag;
            if ((i50 & 1024) != 0) {
                this.mFlag = i50 & KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN;
            } else {
                updateRowSecondarySizeRefresh();
            }
            if (((this.mFlag & 4) != 0 && ((i5 = this.mFocusPosition) != i19 || this.mSubFocusPosition != i20 || findViewByPosition(i5) != view || (this.mFlag & 8) != 0)) || (this.mFlag & 20) == 16) {
                dispatchChildSelected();
            }
            dispatchChildSelectedAndPositioned();
            int i51 = this.mFlag;
            if ((i51 & 64) != 0) {
                if (this.mOrientation == 1) {
                    i4 = -this.mHeight;
                    if (getChildCount() > 0 && (left = getChildAt(0).getTop()) < 0) {
                        i4 += left;
                    }
                    scrollDirectionPrimary(i4);
                } else {
                    if ((i51 & 262144) != 0) {
                        i4 = this.mWidth;
                        if (getChildCount() > 0 && (right = getChildAt(0).getRight()) > i4) {
                            i4 = right;
                        }
                    } else {
                        i4 = -this.mWidth;
                        if (getChildCount() > 0 && (left = getChildAt(0).getLeft()) < 0) {
                        }
                    }
                    scrollDirectionPrimary(i4);
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
        int sizeSecondary;
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
        int i3 = paddingRight + paddingLeft;
        this.mMaxSizeSecondary = size;
        int i4 = this.mRowSizeSecondaryRequested;
        if (i4 == -2) {
            int i5 = this.mNumRowsRequested;
            if (i5 == 0) {
                i5 = 1;
            }
            this.mNumRows = i5;
            this.mFixedRowSizeSecondary = 0;
            int[] iArr = this.mRowSizeSecondary;
            if (iArr == null || iArr.length != i5) {
                this.mRowSizeSecondary = new int[i5];
            }
            if (this.mState.mInPreLayout) {
                updatePositionDeltaInPreLayout();
            }
            processRowSizeSecondary(true);
            if (mode == Integer.MIN_VALUE) {
                size = Math.min(getSizeSecondary() + i3, this.mMaxSizeSecondary);
            } else if (mode == 0) {
                sizeSecondary = getSizeSecondary();
                size = sizeSecondary + i3;
            } else {
                if (mode != 1073741824) {
                    throw new IllegalStateException("wrong spec");
                }
                size = this.mMaxSizeSecondary;
            }
        } else {
            if (mode != Integer.MIN_VALUE) {
                if (mode == 0) {
                    if (i4 == 0) {
                        i4 = size - i3;
                    }
                    this.mFixedRowSizeSecondary = i4;
                    int i6 = this.mNumRowsRequested;
                    if (i6 == 0) {
                        i6 = 1;
                    }
                    this.mNumRows = i6;
                    sizeSecondary = ((i6 - 1) * this.mSpacingSecondary) + (i4 * i6);
                    size = sizeSecondary + i3;
                } else if (mode != 1073741824) {
                    throw new IllegalStateException("wrong spec");
                }
            }
            int i7 = this.mNumRowsRequested;
            if (i7 == 0 && i4 == 0) {
                this.mNumRows = 1;
                this.mFixedRowSizeSecondary = size - i3;
            } else if (i7 == 0) {
                this.mFixedRowSizeSecondary = i4;
                int i8 = this.mSpacingSecondary;
                this.mNumRows = (size + i8) / (i4 + i8);
            } else if (i4 == 0) {
                this.mNumRows = i7;
                this.mFixedRowSizeSecondary = ((size - i3) - ((i7 - 1) * this.mSpacingSecondary)) / i7;
            } else {
                this.mNumRows = i7;
                this.mFixedRowSizeSecondary = i4;
            }
            if (mode == Integer.MIN_VALUE) {
                int i9 = this.mFixedRowSizeSecondary;
                int i10 = this.mNumRows;
                int i11 = ((i10 - 1) * this.mSpacingSecondary) + (i9 * i10) + i3;
                if (i11 < size) {
                    size = i11;
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0075  */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean performAccessibilityAction(RecyclerView.Recycler recycler, RecyclerView.State state, int i, Bundle bundle) {
        boolean z;
        if ((this.mFlag & 131072) != 0) {
            saveContext(recycler, state);
            boolean z2 = (this.mFlag & 262144) != 0;
            if (this.mOrientation == 0) {
                if (i == AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT.getId()) {
                    i = z2 ? 4096 : 8192;
                } else if (i == AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT.getId()) {
                    if (z2) {
                    }
                }
                int i2 = this.mFocusPosition;
                z = i2 != 0 && i == 8192;
                boolean z3 = i2 != state.getItemCount() - 1 && i == 4096;
                if (!z || z3) {
                    AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(4096);
                    this.mBaseGridView.onInitializeAccessibilityEvent(accessibilityEventObtain);
                    BaseGridView baseGridView = this.mBaseGridView;
                    baseGridView.requestSendAccessibilityEvent(baseGridView, accessibilityEventObtain);
                } else if (i == 4096) {
                    processPendingMovement(true);
                    processSelectionMoves(1, false);
                } else if (i == 8192) {
                    processPendingMovement(false);
                    processSelectionMoves(-1, false);
                }
                leaveContext();
            } else {
                if (i != AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP.getId()) {
                    if (i == AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN.getId()) {
                    }
                }
                int i22 = this.mFocusPosition;
                if (i22 != 0) {
                    if (i22 != state.getItemCount() - 1) {
                        if (z) {
                            AccessibilityEvent accessibilityEventObtain2 = AccessibilityEvent.obtain(4096);
                            this.mBaseGridView.onInitializeAccessibilityEvent(accessibilityEventObtain2);
                            BaseGridView baseGridView2 = this.mBaseGridView;
                            baseGridView2.requestSendAccessibilityEvent(baseGridView2, accessibilityEventObtain2);
                            leaveContext();
                        }
                    }
                }
            }
        }
        return true;
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
                            View viewFindViewByPosition = findViewByPosition(i12 - this.mPositionDeltaInPreLayout);
                            if (viewFindViewByPosition != null) {
                                if (z) {
                                    measureChild(viewFindViewByPosition);
                                }
                                int decoratedMeasuredHeightWithMargin = this.mOrientation == 0 ? getDecoratedMeasuredHeightWithMargin(viewFindViewByPosition) : getDecoratedMeasuredWidthWithMargin(viewFindViewByPosition);
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
                        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
                        View viewForPosition = this.mRecycler.getViewForPosition(i14);
                        LayoutParams layoutParams = (LayoutParams) viewForPosition.getLayoutParams();
                        Rect rect = sTempRect;
                        calculateItemDecorationsForChild(rect, viewForPosition);
                        viewForPosition.measure(ViewGroup.getChildMeasureSpec(iMakeMeasureSpec, getPaddingRight() + getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + rect.left + rect.right, ((ViewGroup.MarginLayoutParams) layoutParams).width), ViewGroup.getChildMeasureSpec(iMakeMeasureSpec2, getPaddingBottom() + getPaddingTop() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + rect.top + rect.bottom, ((ViewGroup.MarginLayoutParams) layoutParams).height));
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
                if (i4 >= grid.mFirstVisibleIndex && i4 > i2) {
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
                } else {
                    break;
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
                if (i4 >= i5 && i5 < i2) {
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
                } else {
                    break;
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x002d A[PHI: r0
      0x002d: PHI (r0v8 int) = (r0v7 int), (r0v10 int) binds: [B:19:0x002b, B:12:0x001b] A[DONT_GENERATE, DONT_INLINE]] */
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
                if (axis.mMaxEdge != Integer.MAX_VALUE && i > (i2 = axis.mMaxScroll)) {
                    i = i2;
                }
            } else if (i < 0) {
                WindowAlignment.Axis axis2 = windowAlignment.mMainAxis;
                if (axis2.mMinEdge != Integer.MIN_VALUE && i < (i2 = axis2.mMinScroll)) {
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
            updateRowSecondarySizeRefresh();
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
        if ((this.mFlag & 512) == 0 || this.mGrid == null) {
            return 0;
        }
        saveContext(recycler, state);
        this.mFlag = (this.mFlag & (-4)) | 2;
        int iScrollDirectionPrimary = this.mOrientation == 0 ? scrollDirectionPrimary(i) : scrollDirectionSecondary(i);
        leaveContext();
        this.mFlag &= -4;
        return iScrollDirectionPrimary;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void scrollToPosition(int i) {
        setSelection(i, false);
    }

    public final void scrollToSelection(int i, boolean z) {
        View viewFindViewByPosition = findViewByPosition(i);
        RecyclerView.SmoothScroller smoothScroller = this.mSmoothScroller;
        boolean z2 = smoothScroller != null && smoothScroller.mRunning;
        if (!z2 && !this.mBaseGridView.isLayoutRequested() && viewFindViewByPosition != null && getAdapterPositionByView(viewFindViewByPosition) == i) {
            this.mFlag |= 32;
            scrollToView(viewFindViewByPosition, z);
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
        if (!this.mBaseGridView.isLayoutRequested() && viewFindViewByPosition != null && getAdapterPositionByView(viewFindViewByPosition) == i) {
            this.mFlag |= 32;
            scrollToView(viewFindViewByPosition, z);
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
        int iScrollDirectionPrimary = this.mOrientation == 1 ? scrollDirectionPrimary(i) : scrollDirectionSecondary(i);
        leaveContext();
        this.mFlag &= -4;
        return iScrollDirectionPrimary;
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
        int iFindRowMin = Integer.MIN_VALUE;
        int iFindRowMax = Integer.MAX_VALUE;
        WindowAlignment windowAlignment = this.mWindowAlignment;
        if (!z) {
            WindowAlignment.Axis axis = windowAlignment.mMainAxis;
            if (axis.mMaxEdge == Integer.MAX_VALUE && !z2 && axis.mMinEdge == Integer.MIN_VALUE) {
                return;
            }
        }
        int[] iArr = sTwoInts;
        if (z) {
            iFindRowMax = this.mGrid.findRowMax(true, iArr);
            View viewFindViewByPosition = findViewByPosition(iArr[1]);
            if (this.mOrientation == 0) {
                LayoutParams layoutParams = (LayoutParams) viewFindViewByPosition.getLayoutParams();
                layoutParams.getClass();
                top2 = viewFindViewByPosition.getLeft() + layoutParams.mLeftInset;
                i7 = layoutParams.mAlignX;
            } else {
                LayoutParams layoutParams2 = (LayoutParams) viewFindViewByPosition.getLayoutParams();
                layoutParams2.getClass();
                top2 = viewFindViewByPosition.getTop() + layoutParams2.mTopInset;
                i7 = layoutParams2.mAlignY;
            }
            int i10 = top2 + i7;
            int[] iArr2 = ((LayoutParams) viewFindViewByPosition.getLayoutParams()).mAlignMultiple;
            i4 = (iArr2 == null || iArr2.length <= 0) ? i10 : (iArr2[iArr2.length - 1] - iArr2[0]) + i10;
        } else {
            i4 = Integer.MAX_VALUE;
        }
        if (z2) {
            iFindRowMin = this.mGrid.findRowMin(false, iArr);
            View viewFindViewByPosition2 = findViewByPosition(iArr[1]);
            if (this.mOrientation == 0) {
                LayoutParams layoutParams3 = (LayoutParams) viewFindViewByPosition2.getLayoutParams();
                layoutParams3.getClass();
                top = viewFindViewByPosition2.getLeft() + layoutParams3.mLeftInset;
                i6 = layoutParams3.mAlignX;
            } else {
                LayoutParams layoutParams4 = (LayoutParams) viewFindViewByPosition2.getLayoutParams();
                layoutParams4.getClass();
                top = viewFindViewByPosition2.getTop() + layoutParams4.mTopInset;
                i6 = layoutParams4.mAlignY;
            }
            i5 = top + i6;
        } else {
            i5 = Integer.MIN_VALUE;
        }
        windowAlignment.mMainAxis.updateMinMax(iFindRowMin, iFindRowMax, i5, i4);
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
