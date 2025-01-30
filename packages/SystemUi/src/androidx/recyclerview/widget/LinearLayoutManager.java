package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.PathInterpolator;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.android.knox.p045ex.peripheral.PeripheralConstants;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class LinearLayoutManager extends RecyclerView.LayoutManager implements ItemTouchHelper.ViewDropHandler, RecyclerView.SmoothScroller.ScrollVectorProvider {
    public final AnchorInfo mAnchorInfo;
    public final int mInitialPrefetchItemCount;
    public boolean mLastStackFromEnd;
    public final LayoutChunkResult mLayoutChunkResult;
    public LayoutState mLayoutState;
    public int mOrientation;
    public OrientationHelper mOrientationHelper;
    public final PathInterpolator mPathInterpolator;
    public SavedState mPendingSavedState;
    public int mPendingScrollPosition;
    public int mPendingScrollPositionOffset;
    public final int[] mReusableIntPair;
    public boolean mReverseLayout;
    public boolean mShouldReverseLayout;
    public final boolean mSmoothScrollbarEnabled;
    public boolean mStackFromEnd;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AnchorInfo {
        public int mCoordinate;
        public boolean mLayoutFromEnd;
        public OrientationHelper mOrientationHelper;
        public int mPosition;
        public boolean mValid;

        public AnchorInfo() {
            reset();
        }

        public final void assignCoordinateFromPadding() {
            this.mCoordinate = this.mLayoutFromEnd ? this.mOrientationHelper.getEndAfterPadding() : this.mOrientationHelper.getStartAfterPadding();
        }

        public final void assignFromView(View view, int i) {
            if (this.mLayoutFromEnd) {
                this.mCoordinate = this.mOrientationHelper.getTotalSpaceChange() + this.mOrientationHelper.getDecoratedEnd(view);
            } else {
                this.mCoordinate = this.mOrientationHelper.getDecoratedStart(view);
            }
            this.mPosition = i;
        }

        public final void assignFromViewAndKeepVisibleRect(View view, int i) {
            int totalSpaceChange = this.mOrientationHelper.getTotalSpaceChange();
            if (totalSpaceChange >= 0) {
                assignFromView(view, i);
                return;
            }
            this.mPosition = i;
            if (!this.mLayoutFromEnd) {
                int decoratedStart = this.mOrientationHelper.getDecoratedStart(view);
                int startAfterPadding = decoratedStart - this.mOrientationHelper.getStartAfterPadding();
                this.mCoordinate = decoratedStart;
                if (startAfterPadding > 0) {
                    int endAfterPadding = (this.mOrientationHelper.getEndAfterPadding() - Math.min(0, (this.mOrientationHelper.getEndAfterPadding() - totalSpaceChange) - this.mOrientationHelper.getDecoratedEnd(view))) - (this.mOrientationHelper.getDecoratedMeasurement(view) + decoratedStart);
                    if (endAfterPadding < 0) {
                        this.mCoordinate -= Math.min(startAfterPadding, -endAfterPadding);
                        return;
                    }
                    return;
                }
                return;
            }
            int endAfterPadding2 = (this.mOrientationHelper.getEndAfterPadding() - totalSpaceChange) - this.mOrientationHelper.getDecoratedEnd(view);
            this.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - endAfterPadding2;
            if (endAfterPadding2 > 0) {
                int decoratedMeasurement = this.mCoordinate - this.mOrientationHelper.getDecoratedMeasurement(view);
                int startAfterPadding2 = this.mOrientationHelper.getStartAfterPadding();
                int min = decoratedMeasurement - (Math.min(this.mOrientationHelper.getDecoratedStart(view) - startAfterPadding2, 0) + startAfterPadding2);
                if (min < 0) {
                    this.mCoordinate = Math.min(endAfterPadding2, -min) + this.mCoordinate;
                }
            }
        }

        public final void reset() {
            this.mPosition = -1;
            this.mCoordinate = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.mLayoutFromEnd = false;
            this.mValid = false;
        }

        public final String toString() {
            return "AnchorInfo{mPosition=" + this.mPosition + ", mCoordinate=" + this.mCoordinate + ", mLayoutFromEnd=" + this.mLayoutFromEnd + ", mValid=" + this.mValid + '}';
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LayoutChunkResult {
        public int mConsumed;
        public boolean mFinished;
        public boolean mFocusable;
        public boolean mIgnoreConsumed;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LayoutState {
        public int mAvailable;
        public int mCurrentPosition;
        public boolean mInfinite;
        public int mItemDirection;
        public int mLastScrollDelta;
        public int mLayoutDirection;
        public int mOffset;
        public int mScrollingOffset;
        public boolean mRecycle = true;
        public int mExtraFillSpace = 0;
        public int mNoRecycleSpace = 0;
        public List mScrapList = null;

        public final void assignPositionFromScrapList(View view) {
            int viewLayoutPosition;
            int size = this.mScrapList.size();
            View view2 = null;
            int i = Integer.MAX_VALUE;
            for (int i2 = 0; i2 < size; i2++) {
                View view3 = ((RecyclerView.ViewHolder) this.mScrapList.get(i2)).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view3.getLayoutParams();
                if (view3 != view && !layoutParams.isItemRemoved() && (viewLayoutPosition = (layoutParams.getViewLayoutPosition() - this.mCurrentPosition) * this.mItemDirection) >= 0 && viewLayoutPosition < i) {
                    view2 = view3;
                    if (viewLayoutPosition == 0) {
                        break;
                    } else {
                        i = viewLayoutPosition;
                    }
                }
            }
            if (view2 == null) {
                this.mCurrentPosition = -1;
            } else {
                this.mCurrentPosition = ((RecyclerView.LayoutParams) view2.getLayoutParams()).getViewLayoutPosition();
            }
        }

        public final View next(RecyclerView.Recycler recycler) {
            List list = this.mScrapList;
            if (list == null) {
                View viewForPosition = recycler.getViewForPosition(this.mCurrentPosition);
                this.mCurrentPosition += this.mItemDirection;
                return viewForPosition;
            }
            int size = list.size();
            for (int i = 0; i < size; i++) {
                View view = ((RecyclerView.ViewHolder) this.mScrapList.get(i)).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                if (!layoutParams.isItemRemoved() && this.mCurrentPosition == layoutParams.getViewLayoutPosition()) {
                    assignPositionFromScrapList(view);
                    return view;
                }
            }
            return null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: androidx.recyclerview.widget.LinearLayoutManager.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public boolean mAnchorLayoutFromEnd;
        public int mAnchorOffset;
        public int mAnchorPosition;

        public SavedState() {
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mAnchorPosition);
            parcel.writeInt(this.mAnchorOffset);
            parcel.writeInt(this.mAnchorLayoutFromEnd ? 1 : 0);
        }

        public SavedState(Parcel parcel) {
            this.mAnchorPosition = parcel.readInt();
            this.mAnchorOffset = parcel.readInt();
            this.mAnchorLayoutFromEnd = parcel.readInt() == 1;
        }

        public SavedState(SavedState savedState) {
            this.mAnchorPosition = savedState.mAnchorPosition;
            this.mAnchorOffset = savedState.mAnchorOffset;
            this.mAnchorLayoutFromEnd = savedState.mAnchorLayoutFromEnd;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SmoothScrollerJumpIfNeeded extends LinearSmoothScroller {
        public SmoothScrollerJumpIfNeeded(Context context) {
            super(context);
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0040  */
        /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:9:0x001e  */
        @Override // androidx.recyclerview.widget.LinearSmoothScroller, androidx.recyclerview.widget.RecyclerView.SmoothScroller
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onTargetFound(View view, RecyclerView.SmoothScroller.Action action) {
            int i;
            PointF pointF;
            int sqrt;
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
                            sqrt = (int) Math.sqrt((calculateDyToMakeVisible * calculateDyToMakeVisible) + (calculateDxToMakeVisible * calculateDxToMakeVisible));
                            if (calculateTimeForDeceleration(sqrt) > 0) {
                                int i3 = (int) (((sqrt * 2.0E-4d) + 0.44999998807907104d) * 1000.0d);
                                if (i3 > 800) {
                                    i3 = 800;
                                }
                                action.update(-calculateDxToMakeVisible, -calculateDyToMakeVisible, i3, LinearLayoutManager.this.mPathInterpolator);
                                return;
                            }
                            return;
                        }
                    }
                    i2 = 0;
                    int calculateDyToMakeVisible2 = calculateDyToMakeVisible(view, i2);
                    sqrt = (int) Math.sqrt((calculateDyToMakeVisible2 * calculateDyToMakeVisible2) + (calculateDxToMakeVisible * calculateDxToMakeVisible));
                    if (calculateTimeForDeceleration(sqrt) > 0) {
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
            sqrt = (int) Math.sqrt((calculateDyToMakeVisible22 * calculateDyToMakeVisible22) + (calculateDxToMakeVisible2 * calculateDxToMakeVisible2));
            if (calculateTimeForDeceleration(sqrt) > 0) {
            }
        }
    }

    public LinearLayoutManager(Context context) {
        this(context, 1, false);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void assertNotInLayoutOrScroll(String str) {
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll(str);
        }
    }

    public void calculateExtraLayoutSpace(RecyclerView.State state, int[] iArr) {
        int i;
        int totalSpace = state.mTargetPosition != -1 ? this.mOrientationHelper.getTotalSpace() : 0;
        if (this.mLayoutState.mLayoutDirection == -1) {
            i = 0;
        } else {
            i = totalSpace;
            totalSpace = 0;
        }
        iArr[0] = totalSpace;
        iArr[1] = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean canScrollHorizontally() {
        return this.mOrientation == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean canScrollVertically() {
        return this.mOrientation == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void collectAdjacentPrefetchPositions(int i, int i2, RecyclerView.State state, GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl) {
        if (this.mOrientation != 0) {
            i = i2;
        }
        if (getChildCount() == 0 || i == 0) {
            return;
        }
        ensureLayoutState();
        updateLayoutState(i > 0 ? 1 : -1, Math.abs(i), true, state);
        collectPrefetchPositionsForLayoutState(state, this.mLayoutState, layoutPrefetchRegistryImpl);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void collectInitialPrefetchPositions(int i, GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl) {
        boolean z;
        int i2;
        int i3;
        int i4;
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            i2 = savedState.mAnchorPosition;
            if (i2 >= 0) {
                z = savedState.mAnchorLayoutFromEnd;
                i3 = z ? -1 : 1;
                for (i4 = 0; i4 < this.mInitialPrefetchItemCount && i2 >= 0 && i2 < i; i4++) {
                    layoutPrefetchRegistryImpl.addPosition(i2, 0);
                    i2 += i3;
                }
                return;
            }
        }
        resolveShouldLayoutReverse();
        z = this.mShouldReverseLayout;
        i2 = this.mPendingScrollPosition;
        if (i2 == -1) {
            i2 = z ? i - 1 : 0;
        }
        if (z) {
        }
        while (i4 < this.mInitialPrefetchItemCount) {
            layoutPrefetchRegistryImpl.addPosition(i2, 0);
            i2 += i3;
        }
    }

    public void collectPrefetchPositionsForLayoutState(RecyclerView.State state, LayoutState layoutState, GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl) {
        int i = layoutState.mCurrentPosition;
        if (i < 0 || i >= state.getItemCount()) {
            return;
        }
        layoutPrefetchRegistryImpl.addPosition(i, Math.max(0, layoutState.mScrollingOffset));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollExtent(RecyclerView.State state) {
        return computeScrollExtent(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return computeScrollRange(state);
    }

    public final int computeScrollExtent(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        OrientationHelper orientationHelper = this.mOrientationHelper;
        boolean z = !this.mSmoothScrollbarEnabled;
        return ScrollbarHelper.computeScrollExtent(state, orientationHelper, findFirstVisibleChildClosestToStart(z), findFirstVisibleChildClosestToEnd(z), this, this.mSmoothScrollbarEnabled);
    }

    public final int computeScrollOffset(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        OrientationHelper orientationHelper = this.mOrientationHelper;
        boolean z = !this.mSmoothScrollbarEnabled;
        return ScrollbarHelper.computeScrollOffset(state, orientationHelper, findFirstVisibleChildClosestToStart(z), findFirstVisibleChildClosestToEnd(z), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    public final int computeScrollRange(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        OrientationHelper orientationHelper = this.mOrientationHelper;
        boolean z = !this.mSmoothScrollbarEnabled;
        return ScrollbarHelper.computeScrollRange(state, orientationHelper, findFirstVisibleChildClosestToStart(z), findFirstVisibleChildClosestToEnd(z), this, this.mSmoothScrollbarEnabled);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
    public final PointF computeScrollVectorForPosition(int i) {
        if (getChildCount() == 0) {
            return null;
        }
        int i2 = (i < RecyclerView.LayoutManager.getPosition(getChildAt(0))) != this.mShouldReverseLayout ? -1 : 1;
        return this.mOrientation == 0 ? new PointF(i2, 0.0f) : new PointF(0.0f, i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollExtent(RecyclerView.State state) {
        return computeScrollExtent(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return computeScrollRange(state);
    }

    public final int convertFocusDirectionToLayoutDirection(int i) {
        if (i == 1) {
            return (this.mOrientation != 1 && isLayoutRTL()) ? 1 : -1;
        }
        if (i == 2) {
            return (this.mOrientation != 1 && isLayoutRTL()) ? -1 : 1;
        }
        if (i == 17) {
            if (this.mOrientation == 0) {
                return -1;
            }
            return VideoPlayer.MEDIA_ERROR_SYSTEM;
        }
        if (i == 33) {
            if (this.mOrientation == 1) {
                return -1;
            }
            return VideoPlayer.MEDIA_ERROR_SYSTEM;
        }
        if (i == 66) {
            if (this.mOrientation == 0) {
                return 1;
            }
            return VideoPlayer.MEDIA_ERROR_SYSTEM;
        }
        if (i == 130 && this.mOrientation == 1) {
            return 1;
        }
        return VideoPlayer.MEDIA_ERROR_SYSTEM;
    }

    public final void ensureLayoutState() {
        if (this.mLayoutState == null) {
            this.mLayoutState = new LayoutState();
        }
    }

    public final int fill(RecyclerView.Recycler recycler, LayoutState layoutState, RecyclerView.State state, boolean z) {
        int i = layoutState.mAvailable;
        int i2 = layoutState.mScrollingOffset;
        if (i2 != Integer.MIN_VALUE) {
            if (i < 0) {
                layoutState.mScrollingOffset = i2 + i;
            }
            recycleByLayoutState(recycler, layoutState);
        }
        int i3 = layoutState.mAvailable + layoutState.mExtraFillSpace;
        while (true) {
            if (!layoutState.mInfinite && i3 <= 0) {
                break;
            }
            int i4 = layoutState.mCurrentPosition;
            if (!(i4 >= 0 && i4 < state.getItemCount())) {
                break;
            }
            LayoutChunkResult layoutChunkResult = this.mLayoutChunkResult;
            layoutChunkResult.mConsumed = 0;
            layoutChunkResult.mFinished = false;
            layoutChunkResult.mIgnoreConsumed = false;
            layoutChunkResult.mFocusable = false;
            layoutChunk(recycler, state, layoutState, layoutChunkResult);
            if (!layoutChunkResult.mFinished) {
                int i5 = layoutState.mOffset;
                int i6 = layoutChunkResult.mConsumed;
                layoutState.mOffset = (layoutState.mLayoutDirection * i6) + i5;
                if (!layoutChunkResult.mIgnoreConsumed || layoutState.mScrapList != null || !state.mInPreLayout) {
                    layoutState.mAvailable -= i6;
                    i3 -= i6;
                }
                int i7 = layoutState.mScrollingOffset;
                if (i7 != Integer.MIN_VALUE) {
                    int i8 = i7 + i6;
                    layoutState.mScrollingOffset = i8;
                    int i9 = layoutState.mAvailable;
                    if (i9 < 0) {
                        layoutState.mScrollingOffset = i8 + i9;
                    }
                    recycleByLayoutState(recycler, layoutState);
                }
                if (z && layoutChunkResult.mFocusable) {
                    break;
                }
            } else {
                break;
            }
        }
        return i - layoutState.mAvailable;
    }

    public final View findFirstVisibleChildClosestToEnd(boolean z) {
        return this.mShouldReverseLayout ? findOneVisibleChild(0, getChildCount(), z, true) : findOneVisibleChild(getChildCount() - 1, -1, z, true);
    }

    public final View findFirstVisibleChildClosestToStart(boolean z) {
        return this.mShouldReverseLayout ? findOneVisibleChild(getChildCount() - 1, -1, z, true) : findOneVisibleChild(0, getChildCount(), z, true);
    }

    public final int findFirstVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(0, getChildCount(), false, true);
        if (findOneVisibleChild == null) {
            return -1;
        }
        return RecyclerView.LayoutManager.getPosition(findOneVisibleChild);
    }

    public final int findLastVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(getChildCount() - 1, -1, false, true);
        if (findOneVisibleChild == null) {
            return -1;
        }
        return RecyclerView.LayoutManager.getPosition(findOneVisibleChild);
    }

    public final View findOnePartiallyOrCompletelyInvisibleChild(int i, int i2) {
        int i3;
        int i4;
        ensureLayoutState();
        if ((i2 > i ? (char) 1 : i2 < i ? (char) 65535 : (char) 0) == 0) {
            return getChildAt(i);
        }
        if (this.mOrientationHelper.getDecoratedStart(getChildAt(i)) < this.mOrientationHelper.getStartAfterPadding()) {
            i3 = 16644;
            i4 = 16388;
        } else {
            i3 = 4161;
            i4 = PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_NOT_FOUND;
        }
        return this.mOrientation == 0 ? this.mHorizontalBoundCheck.findOneViewWithinBoundFlags(i, i2, i3, i4) : this.mVerticalBoundCheck.findOneViewWithinBoundFlags(i, i2, i3, i4);
    }

    public final View findOneVisibleChild(int i, int i2, boolean z, boolean z2) {
        ensureLayoutState();
        int i3 = z ? 24579 : 320;
        int i4 = z2 ? 320 : 0;
        return this.mOrientation == 0 ? this.mHorizontalBoundCheck.findOneViewWithinBoundFlags(i, i2, i3, i4) : this.mVerticalBoundCheck.findOneViewWithinBoundFlags(i, i2, i3, i4);
    }

    public View findReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z, boolean z2) {
        int i;
        int i2;
        int i3;
        ensureLayoutState();
        int childCount = getChildCount();
        if (z2) {
            i2 = getChildCount() - 1;
            i = -1;
            i3 = -1;
        } else {
            i = childCount;
            i2 = 0;
            i3 = 1;
        }
        int itemCount = state.getItemCount();
        int startAfterPadding = this.mOrientationHelper.getStartAfterPadding();
        int endAfterPadding = this.mOrientationHelper.getEndAfterPadding();
        View view = null;
        View view2 = null;
        View view3 = null;
        while (i2 != i) {
            View childAt = getChildAt(i2);
            int position = RecyclerView.LayoutManager.getPosition(childAt);
            int decoratedStart = this.mOrientationHelper.getDecoratedStart(childAt);
            int decoratedEnd = this.mOrientationHelper.getDecoratedEnd(childAt);
            if (position >= 0 && position < itemCount) {
                if (!((RecyclerView.LayoutParams) childAt.getLayoutParams()).isItemRemoved()) {
                    boolean z3 = decoratedEnd <= startAfterPadding && decoratedStart < startAfterPadding;
                    boolean z4 = decoratedStart >= endAfterPadding && decoratedEnd > endAfterPadding;
                    if (!z3 && !z4) {
                        return childAt;
                    }
                    if (z) {
                        if (!z4) {
                            if (view != null) {
                            }
                            view = childAt;
                        }
                        view2 = childAt;
                    } else {
                        if (!z3) {
                            if (view != null) {
                            }
                            view = childAt;
                        }
                        view2 = childAt;
                    }
                } else if (view3 == null) {
                    view3 = childAt;
                }
            }
            i2 += i3;
        }
        return view != null ? view : view2 != null ? view2 : view3;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final View findViewByPosition(int i) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return null;
        }
        int position = i - RecyclerView.LayoutManager.getPosition(getChildAt(0));
        if (position >= 0 && position < childCount) {
            View childAt = getChildAt(position);
            if (RecyclerView.LayoutManager.getPosition(childAt) == i) {
                return childAt;
            }
        }
        return super.findViewByPosition(i);
    }

    public final int fixLayoutEndGap(int i, RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int endAfterPadding;
        int endAfterPadding2 = this.mOrientationHelper.getEndAfterPadding() - i;
        if (endAfterPadding2 <= 0) {
            return 0;
        }
        int i2 = -scrollBy(-endAfterPadding2, recycler, state);
        int i3 = i + i2;
        if (!z || (endAfterPadding = this.mOrientationHelper.getEndAfterPadding() - i3) <= 0) {
            return i2;
        }
        this.mOrientationHelper.offsetChildren(endAfterPadding);
        return endAfterPadding + i2;
    }

    public final int fixLayoutStartGap(int i, RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int startAfterPadding;
        int startAfterPadding2 = i - this.mOrientationHelper.getStartAfterPadding();
        if (startAfterPadding2 <= 0) {
            return 0;
        }
        int i2 = -scrollBy(startAfterPadding2, recycler, state);
        int i3 = i + i2;
        if (!z || (startAfterPadding = i3 - this.mOrientationHelper.getStartAfterPadding()) <= 0) {
            return i2;
        }
        this.mOrientationHelper.offsetChildren(-startAfterPadding);
        return i2 - startAfterPadding;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    public final View getChildClosestToEnd() {
        return getChildAt(this.mShouldReverseLayout ? 0 : getChildCount() - 1);
    }

    public final View getChildClosestToStart() {
        return getChildAt(this.mShouldReverseLayout ? getChildCount() - 1 : 0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean isAutoMeasureEnabled() {
        return true;
    }

    public final boolean isLayoutRTL() {
        return getLayoutDirection() == 1;
    }

    public void layoutChunk(RecyclerView.Recycler recycler, RecyclerView.State state, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        int paddingTop;
        int i;
        int i2;
        int i3;
        int i4;
        View next = layoutState.next(recycler);
        if (next == null) {
            layoutChunkResult.mFinished = true;
            return;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) next.getLayoutParams();
        if (layoutState.mScrapList == null) {
            if (this.mShouldReverseLayout == (layoutState.mLayoutDirection == -1)) {
                addViewInt(next, -1, false);
            } else {
                addViewInt(next, 0, false);
            }
        } else {
            if (this.mShouldReverseLayout == (layoutState.mLayoutDirection == -1)) {
                addViewInt(next, -1, true);
            } else {
                addViewInt(next, 0, true);
            }
        }
        RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) next.getLayoutParams();
        Rect itemDecorInsetsForChild = this.mRecyclerView.getItemDecorInsetsForChild(next);
        int i5 = itemDecorInsetsForChild.left + itemDecorInsetsForChild.right + 0;
        int i6 = itemDecorInsetsForChild.top + itemDecorInsetsForChild.bottom + 0;
        int childMeasureSpec = RecyclerView.LayoutManager.getChildMeasureSpec(canScrollHorizontally(), this.mWidth, this.mWidthMode, getPaddingRight() + getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin + i5, ((ViewGroup.MarginLayoutParams) layoutParams2).width);
        int childMeasureSpec2 = RecyclerView.LayoutManager.getChildMeasureSpec(canScrollVertically(), this.mHeight, this.mHeightMode, getPaddingBottom() + getPaddingTop() + ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin + i6, ((ViewGroup.MarginLayoutParams) layoutParams2).height);
        if (shouldMeasureChild(next, childMeasureSpec, childMeasureSpec2, layoutParams2)) {
            next.measure(childMeasureSpec, childMeasureSpec2);
        }
        layoutChunkResult.mConsumed = this.mOrientationHelper.getDecoratedMeasurement(next);
        if (this.mOrientation == 1) {
            if (isLayoutRTL()) {
                i2 = this.mWidth - getPaddingRight();
                i4 = i2 - this.mOrientationHelper.getDecoratedMeasurementInOther(next);
            } else {
                int paddingLeft = getPaddingLeft();
                i2 = this.mOrientationHelper.getDecoratedMeasurementInOther(next) + paddingLeft;
                i4 = paddingLeft;
            }
            if (layoutState.mLayoutDirection == -1) {
                i3 = layoutState.mOffset;
                paddingTop = i3 - layoutChunkResult.mConsumed;
            } else {
                paddingTop = layoutState.mOffset;
                i3 = layoutChunkResult.mConsumed + paddingTop;
            }
        } else {
            paddingTop = getPaddingTop();
            int decoratedMeasurementInOther = this.mOrientationHelper.getDecoratedMeasurementInOther(next) + paddingTop;
            if (layoutState.mLayoutDirection == -1) {
                i2 = layoutState.mOffset;
                i = i2 - layoutChunkResult.mConsumed;
            } else {
                i = layoutState.mOffset;
                i2 = layoutChunkResult.mConsumed + i;
            }
            int i7 = i;
            i3 = decoratedMeasurementInOther;
            i4 = i7;
        }
        RecyclerView.LayoutManager.layoutDecoratedWithMargins(next, i4, paddingTop, i2, i3);
        if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
            layoutChunkResult.mIgnoreConsumed = true;
        }
        layoutChunkResult.mFocusable = next.hasFocusable();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public View onFocusSearchFailed(View view, int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int convertFocusDirectionToLayoutDirection;
        resolveShouldLayoutReverse();
        if (getChildCount() == 0 || (convertFocusDirectionToLayoutDirection = convertFocusDirectionToLayoutDirection(i)) == Integer.MIN_VALUE) {
            return null;
        }
        ensureLayoutState();
        updateLayoutState(convertFocusDirectionToLayoutDirection, (int) (this.mOrientationHelper.getTotalSpace() * 0.33333334f), false, state);
        LayoutState layoutState = this.mLayoutState;
        layoutState.mScrollingOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
        layoutState.mRecycle = false;
        fill(recycler, layoutState, state, true);
        View findOnePartiallyOrCompletelyInvisibleChild = convertFocusDirectionToLayoutDirection == -1 ? this.mShouldReverseLayout ? findOnePartiallyOrCompletelyInvisibleChild(getChildCount() - 1, -1) : findOnePartiallyOrCompletelyInvisibleChild(0, getChildCount()) : this.mShouldReverseLayout ? findOnePartiallyOrCompletelyInvisibleChild(0, getChildCount()) : findOnePartiallyOrCompletelyInvisibleChild(getChildCount() - 1, -1);
        View childClosestToStart = convertFocusDirectionToLayoutDirection == -1 ? getChildClosestToStart() : getChildClosestToEnd();
        if (!childClosestToStart.hasFocusable()) {
            return findOnePartiallyOrCompletelyInvisibleChild;
        }
        if (findOnePartiallyOrCompletelyInvisibleChild == null) {
            return null;
        }
        return childClosestToStart;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            accessibilityEvent.setFromIndex(findFirstVisibleItemPosition());
            accessibilityEvent.setToIndex(findLastVisibleItemPosition());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:146:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x022a  */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v4 */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        View focusedChild;
        boolean z;
        boolean z2;
        View focusedChild2;
        View findReferenceChild;
        int i;
        int i2;
        int i3;
        ?? r4;
        List list;
        int i4;
        int i5;
        int fixLayoutEndGap;
        int i6;
        View findViewByPosition;
        int decoratedStart;
        int i7;
        if (!(this.mPendingSavedState == null && this.mPendingScrollPosition == -1) && state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        SavedState savedState = this.mPendingSavedState;
        boolean z3 = false;
        if (savedState != null) {
            int i8 = savedState.mAnchorPosition;
            if (i8 >= 0) {
                this.mPendingScrollPosition = i8;
            }
        }
        ensureLayoutState();
        this.mLayoutState.mRecycle = false;
        resolveShouldLayoutReverse();
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView == null || (focusedChild = recyclerView.getFocusedChild()) == null || this.mChildHelper.isHidden(focusedChild)) {
            focusedChild = null;
        }
        AnchorInfo anchorInfo = this.mAnchorInfo;
        if (!anchorInfo.mValid || this.mPendingScrollPosition != -1 || this.mPendingSavedState != null) {
            anchorInfo.reset();
            anchorInfo.mLayoutFromEnd = this.mShouldReverseLayout ^ this.mStackFromEnd;
            if (!state.mInPreLayout && (i = this.mPendingScrollPosition) != -1) {
                if (i < 0 || i >= state.getItemCount()) {
                    this.mPendingScrollPosition = -1;
                    this.mPendingScrollPositionOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
                } else {
                    int i9 = this.mPendingScrollPosition;
                    anchorInfo.mPosition = i9;
                    SavedState savedState2 = this.mPendingSavedState;
                    if (savedState2 != null) {
                        if (savedState2.mAnchorPosition >= 0) {
                            boolean z4 = savedState2.mAnchorLayoutFromEnd;
                            anchorInfo.mLayoutFromEnd = z4;
                            if (z4) {
                                anchorInfo.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - this.mPendingSavedState.mAnchorOffset;
                            } else {
                                anchorInfo.mCoordinate = this.mOrientationHelper.getStartAfterPadding() + this.mPendingSavedState.mAnchorOffset;
                            }
                            z = true;
                            if (!z) {
                                if (getChildCount() != 0) {
                                    RecyclerView recyclerView2 = this.mRecyclerView;
                                    if (recyclerView2 == null || (focusedChild2 = recyclerView2.getFocusedChild()) == null || this.mChildHelper.isHidden(focusedChild2)) {
                                        focusedChild2 = null;
                                    }
                                    if (focusedChild2 != null) {
                                        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) focusedChild2.getLayoutParams();
                                        if (!layoutParams.isItemRemoved() && layoutParams.getViewLayoutPosition() >= 0 && layoutParams.getViewLayoutPosition() < state.getItemCount()) {
                                            anchorInfo.assignFromViewAndKeepVisibleRect(focusedChild2, RecyclerView.LayoutManager.getPosition(focusedChild2));
                                            z2 = true;
                                            if (!z2) {
                                                anchorInfo.assignCoordinateFromPadding();
                                                anchorInfo.mPosition = this.mStackFromEnd ? state.getItemCount() - 1 : 0;
                                            }
                                        }
                                    }
                                    boolean z5 = this.mLastStackFromEnd;
                                    boolean z6 = this.mStackFromEnd;
                                    if (z5 == z6 && (findReferenceChild = findReferenceChild(recycler, state, anchorInfo.mLayoutFromEnd, z6)) != null) {
                                        anchorInfo.assignFromView(findReferenceChild, RecyclerView.LayoutManager.getPosition(findReferenceChild));
                                        if (!state.mInPreLayout && supportsPredictiveItemAnimations()) {
                                            int decoratedStart2 = this.mOrientationHelper.getDecoratedStart(findReferenceChild);
                                            int decoratedEnd = this.mOrientationHelper.getDecoratedEnd(findReferenceChild);
                                            int startAfterPadding = this.mOrientationHelper.getStartAfterPadding();
                                            int endAfterPadding = this.mOrientationHelper.getEndAfterPadding();
                                            boolean z7 = decoratedEnd <= startAfterPadding && decoratedStart2 < startAfterPadding;
                                            boolean z8 = decoratedStart2 >= endAfterPadding && decoratedEnd > endAfterPadding;
                                            if (z7 || z8) {
                                                if (anchorInfo.mLayoutFromEnd) {
                                                    startAfterPadding = endAfterPadding;
                                                }
                                                anchorInfo.mCoordinate = startAfterPadding;
                                            }
                                        }
                                        z2 = true;
                                        if (!z2) {
                                        }
                                    }
                                }
                                z2 = false;
                                if (!z2) {
                                }
                            }
                            anchorInfo.mValid = true;
                        }
                    }
                    if (this.mPendingScrollPositionOffset == Integer.MIN_VALUE) {
                        View findViewByPosition2 = findViewByPosition(i9);
                        if (findViewByPosition2 == null) {
                            if (getChildCount() > 0) {
                                anchorInfo.mLayoutFromEnd = (this.mPendingScrollPosition < RecyclerView.LayoutManager.getPosition(getChildAt(0))) == this.mShouldReverseLayout;
                            }
                            anchorInfo.assignCoordinateFromPadding();
                        } else if (this.mOrientationHelper.getDecoratedMeasurement(findViewByPosition2) > this.mOrientationHelper.getTotalSpace()) {
                            anchorInfo.assignCoordinateFromPadding();
                        } else if (this.mOrientationHelper.getDecoratedStart(findViewByPosition2) - this.mOrientationHelper.getStartAfterPadding() < 0) {
                            anchorInfo.mCoordinate = this.mOrientationHelper.getStartAfterPadding();
                            anchorInfo.mLayoutFromEnd = false;
                        } else if (this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(findViewByPosition2) < 0) {
                            anchorInfo.mCoordinate = this.mOrientationHelper.getEndAfterPadding();
                            anchorInfo.mLayoutFromEnd = true;
                        } else {
                            anchorInfo.mCoordinate = anchorInfo.mLayoutFromEnd ? this.mOrientationHelper.getTotalSpaceChange() + this.mOrientationHelper.getDecoratedEnd(findViewByPosition2) : this.mOrientationHelper.getDecoratedStart(findViewByPosition2);
                        }
                    } else {
                        boolean z9 = this.mShouldReverseLayout;
                        anchorInfo.mLayoutFromEnd = z9;
                        if (z9) {
                            anchorInfo.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - this.mPendingScrollPositionOffset;
                        } else {
                            anchorInfo.mCoordinate = this.mOrientationHelper.getStartAfterPadding() + this.mPendingScrollPositionOffset;
                        }
                    }
                    z = true;
                    if (!z) {
                    }
                    anchorInfo.mValid = true;
                }
            }
            z = false;
            if (!z) {
            }
            anchorInfo.mValid = true;
        } else if (focusedChild != null && (this.mOrientationHelper.getDecoratedStart(focusedChild) >= this.mOrientationHelper.getEndAfterPadding() || this.mOrientationHelper.getDecoratedEnd(focusedChild) <= this.mOrientationHelper.getStartAfterPadding())) {
            anchorInfo.assignFromViewAndKeepVisibleRect(focusedChild, RecyclerView.LayoutManager.getPosition(focusedChild));
        }
        LayoutState layoutState = this.mLayoutState;
        layoutState.mLayoutDirection = layoutState.mLastScrollDelta >= 0 ? 1 : -1;
        int[] iArr = this.mReusableIntPair;
        iArr[0] = 0;
        iArr[1] = 0;
        calculateExtraLayoutSpace(state, iArr);
        int startAfterPadding2 = this.mOrientationHelper.getStartAfterPadding() + Math.max(0, iArr[0]);
        int endPadding = this.mOrientationHelper.getEndPadding() + Math.max(0, iArr[1]);
        if (state.mInPreLayout && (i6 = this.mPendingScrollPosition) != -1 && this.mPendingScrollPositionOffset != Integer.MIN_VALUE && (findViewByPosition = findViewByPosition(i6)) != null) {
            if (this.mShouldReverseLayout) {
                i7 = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(findViewByPosition);
                decoratedStart = this.mPendingScrollPositionOffset;
            } else {
                decoratedStart = this.mOrientationHelper.getDecoratedStart(findViewByPosition) - this.mOrientationHelper.getStartAfterPadding();
                i7 = this.mPendingScrollPositionOffset;
            }
            int i10 = i7 - decoratedStart;
            if (i10 > 0) {
                startAfterPadding2 += i10;
            } else {
                endPadding -= i10;
            }
        }
        onAnchorReady(recycler, state, anchorInfo, (!anchorInfo.mLayoutFromEnd ? this.mShouldReverseLayout : !this.mShouldReverseLayout) ? 1 : -1);
        detachAndScrapAttachedViews(recycler);
        this.mLayoutState.mInfinite = this.mOrientationHelper.getMode() == 0 && this.mOrientationHelper.getEnd() == 0;
        this.mLayoutState.getClass();
        this.mLayoutState.mNoRecycleSpace = 0;
        if (anchorInfo.mLayoutFromEnd) {
            updateLayoutStateToFillStart(anchorInfo.mPosition, anchorInfo.mCoordinate);
            LayoutState layoutState2 = this.mLayoutState;
            layoutState2.mExtraFillSpace = startAfterPadding2;
            fill(recycler, layoutState2, state, false);
            LayoutState layoutState3 = this.mLayoutState;
            i3 = layoutState3.mOffset;
            int i11 = layoutState3.mCurrentPosition;
            int i12 = layoutState3.mAvailable;
            if (i12 > 0) {
                endPadding += i12;
            }
            updateLayoutStateToFillEnd(anchorInfo.mPosition, anchorInfo.mCoordinate);
            LayoutState layoutState4 = this.mLayoutState;
            layoutState4.mExtraFillSpace = endPadding;
            layoutState4.mCurrentPosition += layoutState4.mItemDirection;
            fill(recycler, layoutState4, state, false);
            LayoutState layoutState5 = this.mLayoutState;
            i2 = layoutState5.mOffset;
            int i13 = layoutState5.mAvailable;
            if (i13 > 0) {
                updateLayoutStateToFillStart(i11, i3);
                LayoutState layoutState6 = this.mLayoutState;
                layoutState6.mExtraFillSpace = i13;
                fill(recycler, layoutState6, state, false);
                i3 = this.mLayoutState.mOffset;
            }
        } else {
            updateLayoutStateToFillEnd(anchorInfo.mPosition, anchorInfo.mCoordinate);
            LayoutState layoutState7 = this.mLayoutState;
            layoutState7.mExtraFillSpace = endPadding;
            fill(recycler, layoutState7, state, false);
            LayoutState layoutState8 = this.mLayoutState;
            i2 = layoutState8.mOffset;
            int i14 = layoutState8.mCurrentPosition;
            int i15 = layoutState8.mAvailable;
            if (i15 > 0) {
                startAfterPadding2 += i15;
            }
            updateLayoutStateToFillStart(anchorInfo.mPosition, anchorInfo.mCoordinate);
            LayoutState layoutState9 = this.mLayoutState;
            layoutState9.mExtraFillSpace = startAfterPadding2;
            layoutState9.mCurrentPosition += layoutState9.mItemDirection;
            fill(recycler, layoutState9, state, false);
            LayoutState layoutState10 = this.mLayoutState;
            int i16 = layoutState10.mOffset;
            int i17 = layoutState10.mAvailable;
            if (i17 > 0) {
                updateLayoutStateToFillEnd(i14, i2);
                LayoutState layoutState11 = this.mLayoutState;
                layoutState11.mExtraFillSpace = i17;
                fill(recycler, layoutState11, state, false);
                i2 = this.mLayoutState.mOffset;
            }
            i3 = i16;
        }
        if (getChildCount() > 0) {
            if (this.mShouldReverseLayout ^ this.mStackFromEnd) {
                int fixLayoutEndGap2 = fixLayoutEndGap(i2, recycler, state, true);
                i4 = i3 + fixLayoutEndGap2;
                i5 = i2 + fixLayoutEndGap2;
                fixLayoutEndGap = fixLayoutStartGap(i4, recycler, state, false);
            } else {
                int fixLayoutStartGap = fixLayoutStartGap(i3, recycler, state, true);
                i4 = i3 + fixLayoutStartGap;
                i5 = i2 + fixLayoutStartGap;
                fixLayoutEndGap = fixLayoutEndGap(i5, recycler, state, false);
            }
            i3 = i4 + fixLayoutEndGap;
            i2 = i5 + fixLayoutEndGap;
        }
        if (state.mRunPredictiveAnimations && getChildCount() != 0 && !state.mInPreLayout && supportsPredictiveItemAnimations()) {
            List list2 = recycler.mUnmodifiableAttachedScrap;
            int size = list2.size();
            int position = RecyclerView.LayoutManager.getPosition(getChildAt(0));
            int i18 = 0;
            int i19 = 0;
            int i20 = 0;
            while (i18 < size) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) list2.get(i18);
                if (!viewHolder.isRemoved()) {
                    char c = (viewHolder.getLayoutPosition() < position ? true : z3) != this.mShouldReverseLayout ? (char) 65535 : (char) 1;
                    View view = viewHolder.itemView;
                    if (c == 65535) {
                        i19 += this.mOrientationHelper.getDecoratedMeasurement(view);
                    } else {
                        i20 += this.mOrientationHelper.getDecoratedMeasurement(view);
                    }
                }
                i18++;
                z3 = false;
            }
            this.mLayoutState.mScrapList = list2;
            if (i19 > 0) {
                updateLayoutStateToFillStart(RecyclerView.LayoutManager.getPosition(getChildClosestToStart()), i3);
                LayoutState layoutState12 = this.mLayoutState;
                layoutState12.mExtraFillSpace = i19;
                r4 = 0;
                layoutState12.mAvailable = 0;
                layoutState12.assignPositionFromScrapList(null);
                fill(recycler, this.mLayoutState, state, false);
            } else {
                r4 = 0;
            }
            if (i20 > 0) {
                updateLayoutStateToFillEnd(RecyclerView.LayoutManager.getPosition(getChildClosestToEnd()), i2);
                LayoutState layoutState13 = this.mLayoutState;
                layoutState13.mExtraFillSpace = i20;
                layoutState13.mAvailable = r4;
                list = null;
                layoutState13.assignPositionFromScrapList(null);
                fill(recycler, this.mLayoutState, state, r4);
            } else {
                list = null;
            }
            this.mLayoutState.mScrapList = list;
        }
        if (state.mInPreLayout) {
            anchorInfo.reset();
        } else {
            OrientationHelper orientationHelper = this.mOrientationHelper;
            orientationHelper.mLastTotalSpace = orientationHelper.getTotalSpace();
        }
        this.mLastStackFromEnd = this.mStackFromEnd;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(RecyclerView.State state) {
        this.mPendingSavedState = null;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
        this.mAnchorInfo.reset();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            this.mPendingSavedState = savedState;
            if (this.mPendingScrollPosition != -1) {
                savedState.mAnchorPosition = -1;
            }
            requestLayout();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final Parcelable onSaveInstanceState() {
        if (this.mPendingSavedState != null) {
            return new SavedState(this.mPendingSavedState);
        }
        SavedState savedState = new SavedState();
        if (getChildCount() > 0) {
            ensureLayoutState();
            boolean z = this.mLastStackFromEnd ^ this.mShouldReverseLayout;
            savedState.mAnchorLayoutFromEnd = z;
            if (z) {
                View childClosestToEnd = getChildClosestToEnd();
                savedState.mAnchorOffset = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(childClosestToEnd);
                savedState.mAnchorPosition = RecyclerView.LayoutManager.getPosition(childClosestToEnd);
            } else {
                View childClosestToStart = getChildClosestToStart();
                savedState.mAnchorPosition = RecyclerView.LayoutManager.getPosition(childClosestToStart);
                savedState.mAnchorOffset = this.mOrientationHelper.getDecoratedStart(childClosestToStart) - this.mOrientationHelper.getStartAfterPadding();
            }
        } else {
            savedState.mAnchorPosition = -1;
        }
        return savedState;
    }

    public final void recycleByLayoutState(RecyclerView.Recycler recycler, LayoutState layoutState) {
        if (!layoutState.mRecycle || layoutState.mInfinite) {
            return;
        }
        int i = layoutState.mScrollingOffset;
        int i2 = layoutState.mNoRecycleSpace;
        if (layoutState.mLayoutDirection == -1) {
            int childCount = getChildCount();
            if (i < 0) {
                return;
            }
            int end = (this.mOrientationHelper.getEnd() - i) + i2;
            if (this.mShouldReverseLayout) {
                for (int i3 = 0; i3 < childCount; i3++) {
                    View childAt = getChildAt(i3);
                    if (this.mOrientationHelper.getDecoratedStart(childAt) < end || this.mOrientationHelper.getTransformedStartWithDecoration(childAt) < end) {
                        recycleChildren(recycler, 0, i3);
                        return;
                    }
                }
                return;
            }
            int i4 = childCount - 1;
            for (int i5 = i4; i5 >= 0; i5--) {
                View childAt2 = getChildAt(i5);
                if (this.mOrientationHelper.getDecoratedStart(childAt2) < end || this.mOrientationHelper.getTransformedStartWithDecoration(childAt2) < end) {
                    recycleChildren(recycler, i4, i5);
                    return;
                }
            }
            return;
        }
        if (i < 0) {
            return;
        }
        int i6 = i - i2;
        int childCount2 = getChildCount();
        if (!this.mShouldReverseLayout) {
            for (int i7 = 0; i7 < childCount2; i7++) {
                View childAt3 = getChildAt(i7);
                if (this.mOrientationHelper.getDecoratedEnd(childAt3) > i6 || this.mOrientationHelper.getTransformedEndWithDecoration(childAt3) > i6) {
                    recycleChildren(recycler, 0, i7);
                    return;
                }
            }
            return;
        }
        int i8 = childCount2 - 1;
        for (int i9 = i8; i9 >= 0; i9--) {
            View childAt4 = getChildAt(i9);
            if (this.mOrientationHelper.getDecoratedEnd(childAt4) > i6 || this.mOrientationHelper.getTransformedEndWithDecoration(childAt4) > i6) {
                recycleChildren(recycler, i8, i9);
                return;
            }
        }
    }

    public final void recycleChildren(RecyclerView.Recycler recycler, int i, int i2) {
        if (i == i2) {
            return;
        }
        if (i2 <= i) {
            while (i > i2) {
                View childAt = getChildAt(i);
                removeViewAt(i);
                recycler.recycleView(childAt);
                i--;
            }
            return;
        }
        while (true) {
            i2--;
            if (i2 < i) {
                return;
            }
            View childAt2 = getChildAt(i2);
            removeViewAt(i2);
            recycler.recycleView(childAt2);
        }
    }

    public final void resolveShouldLayoutReverse() {
        if (this.mOrientation == 1 || !isLayoutRTL()) {
            this.mShouldReverseLayout = this.mReverseLayout;
        } else {
            this.mShouldReverseLayout = !this.mReverseLayout;
        }
    }

    public final int scrollBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0 || i == 0) {
            return 0;
        }
        ensureLayoutState();
        this.mLayoutState.mRecycle = true;
        int i2 = i > 0 ? 1 : -1;
        int abs = Math.abs(i);
        updateLayoutState(i2, abs, true, state);
        LayoutState layoutState = this.mLayoutState;
        int fill = fill(recycler, layoutState, state, false) + layoutState.mScrollingOffset;
        if (fill < 0) {
            return 0;
        }
        if (abs > fill) {
            i = i2 * fill;
        }
        this.mOrientationHelper.offsetChildren(-i);
        this.mLayoutState.mLastScrollDelta = i;
        if (state.mLayoutStep != 2) {
            this.mRecyclerView.showGoToTop();
        }
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 1) {
            return 0;
        }
        return scrollBy(i, recycler, state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void scrollToPosition(int i) {
        this.mPendingScrollPosition = i;
        this.mPendingScrollPositionOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            savedState.mAnchorPosition = -1;
        }
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.showGoToTop();
        }
        requestLayout();
    }

    public final void scrollToPositionWithOffset(int i, int i2) {
        this.mPendingScrollPosition = i;
        this.mPendingScrollPositionOffset = i2;
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            savedState.mAnchorPosition = -1;
        }
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.showGoToTop();
        }
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            return 0;
        }
        return scrollBy(i, recycler, state);
    }

    public final void setOrientation(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("invalid orientation:", i));
        }
        assertNotInLayoutOrScroll(null);
        if (i != this.mOrientation || this.mOrientationHelper == null) {
            OrientationHelper createOrientationHelper = OrientationHelper.createOrientationHelper(this, i);
            this.mOrientationHelper = createOrientationHelper;
            this.mAnchorInfo.mOrientationHelper = createOrientationHelper;
            this.mOrientation = i;
            requestLayout();
        }
    }

    public void setStackFromEnd(boolean z) {
        assertNotInLayoutOrScroll(null);
        if (this.mStackFromEnd == z) {
            return;
        }
        this.mStackFromEnd = z;
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean shouldMeasureTwice() {
        boolean z;
        if (this.mHeightMode == 1073741824 || this.mWidthMode == 1073741824) {
            return false;
        }
        int childCount = getChildCount();
        int i = 0;
        while (true) {
            if (i >= childCount) {
                z = false;
                break;
            }
            ViewGroup.LayoutParams layoutParams = getChildAt(i).getLayoutParams();
            if (layoutParams.width < 0 && layoutParams.height < 0) {
                z = true;
                break;
            }
            i++;
        }
        return z;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, int i) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext());
        recyclerView.showGoToTop();
        linearSmoothScroller.mTargetPosition = i;
        startSmoothScroll(linearSmoothScroller);
        Log.d("SeslLinearLayoutManager", "SS pos to : " + i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null && this.mLastStackFromEnd == this.mStackFromEnd;
    }

    public final void updateLayoutState(int i, int i2, boolean z, RecyclerView.State state) {
        int startAfterPadding;
        this.mLayoutState.mInfinite = this.mOrientationHelper.getMode() == 0 && this.mOrientationHelper.getEnd() == 0;
        this.mLayoutState.mLayoutDirection = i;
        int[] iArr = this.mReusableIntPair;
        iArr[0] = 0;
        iArr[1] = 0;
        calculateExtraLayoutSpace(state, iArr);
        int max = Math.max(0, iArr[0]);
        int max2 = Math.max(0, iArr[1]);
        boolean z2 = i == 1;
        LayoutState layoutState = this.mLayoutState;
        int i3 = z2 ? max2 : max;
        layoutState.mExtraFillSpace = i3;
        if (!z2) {
            max = max2;
        }
        layoutState.mNoRecycleSpace = max;
        if (z2) {
            layoutState.mExtraFillSpace = this.mOrientationHelper.getEndPadding() + i3;
            View childClosestToEnd = getChildClosestToEnd();
            LayoutState layoutState2 = this.mLayoutState;
            layoutState2.mItemDirection = this.mShouldReverseLayout ? -1 : 1;
            int position = RecyclerView.LayoutManager.getPosition(childClosestToEnd);
            LayoutState layoutState3 = this.mLayoutState;
            layoutState2.mCurrentPosition = position + layoutState3.mItemDirection;
            layoutState3.mOffset = this.mOrientationHelper.getDecoratedEnd(childClosestToEnd);
            startAfterPadding = this.mOrientationHelper.getDecoratedEnd(childClosestToEnd) - this.mOrientationHelper.getEndAfterPadding();
        } else {
            View childClosestToStart = getChildClosestToStart();
            LayoutState layoutState4 = this.mLayoutState;
            layoutState4.mExtraFillSpace = this.mOrientationHelper.getStartAfterPadding() + layoutState4.mExtraFillSpace;
            LayoutState layoutState5 = this.mLayoutState;
            layoutState5.mItemDirection = this.mShouldReverseLayout ? 1 : -1;
            int position2 = RecyclerView.LayoutManager.getPosition(childClosestToStart);
            LayoutState layoutState6 = this.mLayoutState;
            layoutState5.mCurrentPosition = position2 + layoutState6.mItemDirection;
            layoutState6.mOffset = this.mOrientationHelper.getDecoratedStart(childClosestToStart);
            startAfterPadding = (-this.mOrientationHelper.getDecoratedStart(childClosestToStart)) + this.mOrientationHelper.getStartAfterPadding();
        }
        LayoutState layoutState7 = this.mLayoutState;
        layoutState7.mAvailable = i2;
        if (z) {
            layoutState7.mAvailable = i2 - startAfterPadding;
        }
        layoutState7.mScrollingOffset = startAfterPadding;
    }

    public final void updateLayoutStateToFillEnd(int i, int i2) {
        this.mLayoutState.mAvailable = this.mOrientationHelper.getEndAfterPadding() - i2;
        LayoutState layoutState = this.mLayoutState;
        layoutState.mItemDirection = this.mShouldReverseLayout ? -1 : 1;
        layoutState.mCurrentPosition = i;
        layoutState.mLayoutDirection = 1;
        layoutState.mOffset = i2;
        layoutState.mScrollingOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
    }

    public final void updateLayoutStateToFillStart(int i, int i2) {
        this.mLayoutState.mAvailable = i2 - this.mOrientationHelper.getStartAfterPadding();
        LayoutState layoutState = this.mLayoutState;
        layoutState.mCurrentPosition = i;
        layoutState.mItemDirection = this.mShouldReverseLayout ? 1 : -1;
        layoutState.mLayoutDirection = -1;
        layoutState.mOffset = i2;
        layoutState.mScrollingOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
    }

    public LinearLayoutManager(Context context, int i, boolean z) {
        this.mOrientation = 1;
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mStackFromEnd = false;
        this.mSmoothScrollbarEnabled = true;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
        this.mPathInterpolator = new PathInterpolator(0.22f, 0.5f, 0.0f, 1.0f);
        this.mPendingSavedState = null;
        this.mAnchorInfo = new AnchorInfo();
        this.mLayoutChunkResult = new LayoutChunkResult();
        this.mInitialPrefetchItemCount = 2;
        this.mReusableIntPair = new int[2];
        setOrientation(i);
        assertNotInLayoutOrScroll(null);
        if (z == this.mReverseLayout) {
            return;
        }
        this.mReverseLayout = z;
        requestLayout();
    }

    public LinearLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mOrientation = 1;
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mStackFromEnd = false;
        this.mSmoothScrollbarEnabled = true;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
        this.mPathInterpolator = new PathInterpolator(0.22f, 0.5f, 0.0f, 1.0f);
        this.mPendingSavedState = null;
        this.mAnchorInfo = new AnchorInfo();
        this.mLayoutChunkResult = new LayoutChunkResult();
        this.mInitialPrefetchItemCount = 2;
        this.mReusableIntPair = new int[2];
        RecyclerView.LayoutManager.Properties properties = RecyclerView.LayoutManager.getProperties(context, attributeSet, i, i2);
        setOrientation(properties.orientation);
        boolean z = properties.reverseLayout;
        assertNotInLayoutOrScroll(null);
        if (z != this.mReverseLayout) {
            this.mReverseLayout = z;
            requestLayout();
        }
        setStackFromEnd(properties.stackFromEnd);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onDetachedFromWindow(RecyclerView recyclerView) {
    }

    public void onAnchorReady(RecyclerView.Recycler recycler, RecyclerView.State state, AnchorInfo anchorInfo, int i) {
    }
}
