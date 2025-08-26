package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class StaggeredGridLayoutManager extends RecyclerView.LayoutManager implements RecyclerView.SmoothScroller.ScrollVectorProvider {
    public final AnchorInfo mAnchorInfo;
    public final AnonymousClass1 mCheckForGapsRunnable;
    public final int mGapStrategy;
    public boolean mLastLayoutFromEnd;
    public boolean mLastLayoutRTL;
    public final LayoutState mLayoutState;
    public final LazySpanLookup mLazySpanLookup;
    public final int mOrientation;
    public SavedState mPendingSavedState;
    public int mPendingScrollPosition;
    public int mPendingScrollPositionOffset;
    public int[] mPrefetchDistances;
    public final OrientationHelper mPrimaryOrientation;
    public BitSet mRemainingSpans;
    public boolean mReverseLayout;
    public final OrientationHelper mSecondaryOrientation;
    public boolean mShouldReverseLayout;
    public int mSizePerSpan;
    public final boolean mSmoothScrollbarEnabled;
    public int mSpanCount;
    public Span[] mSpans;
    public final Rect mTmpRect;

    public class AnchorInfo {
        public boolean mInvalidateOffsets;
        public boolean mLayoutFromEnd;
        public int mOffset;
        public int mPosition;
        public int[] mSpanReferenceLines;
        public boolean mValid;

        public AnchorInfo() {
            reset();
        }

        public final void reset() {
            this.mPosition = -1;
            this.mOffset = Integer.MIN_VALUE;
            this.mLayoutFromEnd = false;
            this.mInvalidateOffsets = false;
            this.mValid = false;
            int[] iArr = this.mSpanReferenceLines;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
        }
    }

    public class LayoutParams extends RecyclerView.LayoutParams {
        public Span mSpan;

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
    }

    public class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: androidx.recyclerview.widget.StaggeredGridLayoutManager.SavedState.1
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
        public int mAnchorPosition;
        public List mFullSpanItems;
        public boolean mLastLayoutRTL;
        public boolean mReverseLayout;
        public int[] mSpanLookup;
        public int mSpanLookupSize;
        public int[] mSpanOffsets;
        public int mSpanOffsetsSize;
        public int mVisibleAnchorPosition;

        public SavedState() {
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mAnchorPosition);
            parcel.writeInt(this.mVisibleAnchorPosition);
            parcel.writeInt(this.mSpanOffsetsSize);
            if (this.mSpanOffsetsSize > 0) {
                parcel.writeIntArray(this.mSpanOffsets);
            }
            parcel.writeInt(this.mSpanLookupSize);
            if (this.mSpanLookupSize > 0) {
                parcel.writeIntArray(this.mSpanLookup);
            }
            parcel.writeInt(this.mReverseLayout ? 1 : 0);
            parcel.writeInt(this.mAnchorLayoutFromEnd ? 1 : 0);
            parcel.writeInt(this.mLastLayoutRTL ? 1 : 0);
            parcel.writeList(this.mFullSpanItems);
        }

        public SavedState(Parcel parcel) {
            this.mAnchorPosition = parcel.readInt();
            this.mVisibleAnchorPosition = parcel.readInt();
            int i = parcel.readInt();
            this.mSpanOffsetsSize = i;
            if (i > 0) {
                int[] iArr = new int[i];
                this.mSpanOffsets = iArr;
                parcel.readIntArray(iArr);
            }
            int i2 = parcel.readInt();
            this.mSpanLookupSize = i2;
            if (i2 > 0) {
                int[] iArr2 = new int[i2];
                this.mSpanLookup = iArr2;
                parcel.readIntArray(iArr2);
            }
            this.mReverseLayout = parcel.readInt() == 1;
            this.mAnchorLayoutFromEnd = parcel.readInt() == 1;
            this.mLastLayoutRTL = parcel.readInt() == 1;
            this.mFullSpanItems = parcel.readArrayList(LazySpanLookup.FullSpanItem.class.getClassLoader());
        }

        public SavedState(SavedState savedState) {
            this.mSpanOffsetsSize = savedState.mSpanOffsetsSize;
            this.mAnchorPosition = savedState.mAnchorPosition;
            this.mVisibleAnchorPosition = savedState.mVisibleAnchorPosition;
            this.mSpanOffsets = savedState.mSpanOffsets;
            this.mSpanLookupSize = savedState.mSpanLookupSize;
            this.mSpanLookup = savedState.mSpanLookup;
            this.mReverseLayout = savedState.mReverseLayout;
            this.mAnchorLayoutFromEnd = savedState.mAnchorLayoutFromEnd;
            this.mLastLayoutRTL = savedState.mLastLayoutRTL;
            this.mFullSpanItems = savedState.mFullSpanItems;
        }
    }

    public class Span {
        public final int mIndex;
        public final ArrayList mViews = new ArrayList();
        public int mCachedStart = Integer.MIN_VALUE;
        public int mCachedEnd = Integer.MIN_VALUE;
        public int mDeletedSize = 0;

        public Span(int i) {
            this.mIndex = i;
        }

        public final void calculateCachedEnd() {
            View view = (View) AlertController$$ExternalSyntheticOutline0.m(1, this.mViews);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            this.mCachedEnd = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(view);
            layoutParams.getClass();
        }

        public final void clear() {
            this.mViews.clear();
            this.mCachedStart = Integer.MIN_VALUE;
            this.mCachedEnd = Integer.MIN_VALUE;
            this.mDeletedSize = 0;
        }

        public final int findFirstPartiallyVisibleItemPosition() {
            return StaggeredGridLayoutManager.this.mReverseLayout ? findOnePartiallyOrCompletelyVisibleChild(this.mViews.size() - 1, -1, false, true) : findOnePartiallyOrCompletelyVisibleChild(0, this.mViews.size(), false, true);
        }

        public final int findLastPartiallyVisibleItemPosition() {
            return StaggeredGridLayoutManager.this.mReverseLayout ? findOnePartiallyOrCompletelyVisibleChild(0, this.mViews.size(), false, true) : findOnePartiallyOrCompletelyVisibleChild(this.mViews.size() - 1, -1, false, true);
        }

        public final int findOnePartiallyOrCompletelyVisibleChild(int i, int i2, boolean z, boolean z2) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = StaggeredGridLayoutManager.this;
            int startAfterPadding = staggeredGridLayoutManager.mPrimaryOrientation.getStartAfterPadding();
            int endAfterPadding = staggeredGridLayoutManager.mPrimaryOrientation.getEndAfterPadding();
            int i3 = i2 > i ? 1 : -1;
            while (i != i2) {
                View view = (View) this.mViews.get(i);
                int decoratedStart = staggeredGridLayoutManager.mPrimaryOrientation.getDecoratedStart(view);
                int decoratedEnd = staggeredGridLayoutManager.mPrimaryOrientation.getDecoratedEnd(view);
                boolean z3 = false;
                boolean z4 = !z2 ? decoratedStart >= endAfterPadding : decoratedStart > endAfterPadding;
                if (!z2 ? decoratedEnd > startAfterPadding : decoratedEnd >= startAfterPadding) {
                    z3 = true;
                }
                if (z4 && z3) {
                    if (z) {
                        return RecyclerView.LayoutManager.getPosition(view);
                    }
                    if (decoratedStart < startAfterPadding || decoratedEnd > endAfterPadding) {
                        return RecyclerView.LayoutManager.getPosition(view);
                    }
                }
                i += i3;
            }
            return -1;
        }

        public final int getEndLine(int i) {
            int i2 = this.mCachedEnd;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            if (this.mViews.size() == 0) {
                return i;
            }
            calculateCachedEnd();
            return this.mCachedEnd;
        }

        public final View getFocusableViewAfter(int i, int i2) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = StaggeredGridLayoutManager.this;
            View view = null;
            if (i2 != -1) {
                int size = this.mViews.size() - 1;
                while (size >= 0) {
                    View view2 = (View) this.mViews.get(size);
                    if ((staggeredGridLayoutManager.mReverseLayout && RecyclerView.LayoutManager.getPosition(view2) >= i) || ((!staggeredGridLayoutManager.mReverseLayout && RecyclerView.LayoutManager.getPosition(view2) <= i) || !view2.hasFocusable())) {
                        break;
                    }
                    size--;
                    view = view2;
                }
                return view;
            }
            int size2 = this.mViews.size();
            int i3 = 0;
            while (i3 < size2) {
                View view3 = (View) this.mViews.get(i3);
                if ((staggeredGridLayoutManager.mReverseLayout && RecyclerView.LayoutManager.getPosition(view3) <= i) || ((!staggeredGridLayoutManager.mReverseLayout && RecyclerView.LayoutManager.getPosition(view3) >= i) || !view3.hasFocusable())) {
                    break;
                }
                i3++;
                view = view3;
            }
            return view;
        }

        public final int getStartLine(int i) {
            int i2 = this.mCachedStart;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            if (this.mViews.size() == 0) {
                return i;
            }
            View view = (View) this.mViews.get(0);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            this.mCachedStart = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(view);
            layoutParams.getClass();
            return this.mCachedStart;
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [androidx.recyclerview.widget.StaggeredGridLayoutManager$1] */
    public StaggeredGridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mSpanCount = -1;
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mLazySpanLookup = new LazySpanLookup();
        this.mGapStrategy = 2;
        this.mTmpRect = new Rect();
        this.mAnchorInfo = new AnchorInfo();
        this.mSmoothScrollbarEnabled = true;
        this.mCheckForGapsRunnable = new Runnable() { // from class: androidx.recyclerview.widget.StaggeredGridLayoutManager.1
            @Override // java.lang.Runnable
            public final void run() {
                StaggeredGridLayoutManager.this.checkForGaps();
            }
        };
        RecyclerView.LayoutManager.Properties properties = RecyclerView.LayoutManager.getProperties(context, attributeSet, i, i2);
        int i3 = properties.orientation;
        if (i3 != 0 && i3 != 1) {
            throw new IllegalArgumentException("invalid orientation.");
        }
        assertNotInLayoutOrScroll(null);
        if (i3 != this.mOrientation) {
            this.mOrientation = i3;
            OrientationHelper orientationHelper = this.mPrimaryOrientation;
            this.mPrimaryOrientation = this.mSecondaryOrientation;
            this.mSecondaryOrientation = orientationHelper;
            requestLayout();
        }
        setSpanCount(properties.spanCount);
        boolean z = properties.reverseLayout;
        assertNotInLayoutOrScroll(null);
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null && savedState.mReverseLayout != z) {
            savedState.mReverseLayout = z;
        }
        this.mReverseLayout = z;
        requestLayout();
        this.mLayoutState = new LayoutState();
        this.mPrimaryOrientation = OrientationHelper.createOrientationHelper(this, this.mOrientation);
        this.mSecondaryOrientation = OrientationHelper.createOrientationHelper(this, 1 - this.mOrientation);
    }

    public static int updateSpecWithExtra(int i, int i2, int i3) {
        int mode;
        return (!(i2 == 0 && i3 == 0) && ((mode = View.MeasureSpec.getMode(i)) == Integer.MIN_VALUE || mode == 1073741824)) ? View.MeasureSpec.makeMeasureSpec(Math.max(0, (View.MeasureSpec.getSize(i) - i2) - i3), mode) : i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void assertNotInLayoutOrScroll(String str) {
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll(str);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean canScrollHorizontally() {
        return this.mOrientation == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean canScrollVertically() {
        return this.mOrientation == 1;
    }

    public final boolean checkForGaps() {
        int firstChildPosition;
        if (getChildCount() != 0 && this.mGapStrategy != 0 && this.mIsAttachedToWindow) {
            if (this.mShouldReverseLayout) {
                firstChildPosition = getLastChildPosition();
                getFirstChildPosition();
            } else {
                firstChildPosition = getFirstChildPosition();
                getLastChildPosition();
            }
            LazySpanLookup lazySpanLookup = this.mLazySpanLookup;
            if (firstChildPosition == 0 && hasGapsToFix() != null) {
                lazySpanLookup.clear();
                this.mRequestedSimpleAnimations = true;
                requestLayout();
                return true;
            }
        }
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void collectAdjacentPrefetchPositions(int i, int i2, RecyclerView.State state, GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl) {
        LayoutState layoutState;
        int endLine;
        int startLine;
        if (this.mOrientation != 0) {
            i = i2;
        }
        if (getChildCount() == 0 || i == 0) {
            return;
        }
        prepareLayoutStateForDelta(i, state);
        int[] iArr = this.mPrefetchDistances;
        if (iArr == null || iArr.length < this.mSpanCount) {
            this.mPrefetchDistances = new int[this.mSpanCount];
        }
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int i5 = this.mSpanCount;
            layoutState = this.mLayoutState;
            if (i3 >= i5) {
                break;
            }
            if (layoutState.mItemDirection == -1) {
                endLine = layoutState.mStartLine;
                startLine = this.mSpans[i3].getStartLine(endLine);
            } else {
                endLine = this.mSpans[i3].getEndLine(layoutState.mEndLine);
                startLine = layoutState.mEndLine;
            }
            int i6 = endLine - startLine;
            if (i6 >= 0) {
                this.mPrefetchDistances[i4] = i6;
                i4++;
            }
            i3++;
        }
        Arrays.sort(this.mPrefetchDistances, 0, i4);
        for (int i7 = 0; i7 < i4; i7++) {
            int i8 = layoutState.mCurrentPosition;
            if (i8 < 0 || i8 >= state.getItemCount()) {
                return;
            }
            layoutPrefetchRegistryImpl.addPosition(layoutState.mCurrentPosition, this.mPrefetchDistances[i7]);
            layoutState.mCurrentPosition += layoutState.mItemDirection;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollExtent(RecyclerView.State state) {
        return computeScrollExtent$1(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset$1(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollRange(RecyclerView.State state) {
        return computeScrollRange$1(state);
    }

    public final int computeScrollExtent$1(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        OrientationHelper orientationHelper = this.mPrimaryOrientation;
        boolean z = !this.mSmoothScrollbarEnabled;
        return ScrollbarHelper.computeScrollExtent(state, orientationHelper, findFirstVisibleItemClosestToStart(z), findFirstVisibleItemClosestToEnd(z), this, this.mSmoothScrollbarEnabled);
    }

    public final int computeScrollOffset$1(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        OrientationHelper orientationHelper = this.mPrimaryOrientation;
        boolean z = !this.mSmoothScrollbarEnabled;
        return ScrollbarHelper.computeScrollOffset(state, orientationHelper, findFirstVisibleItemClosestToStart(z), findFirstVisibleItemClosestToEnd(z), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    public final int computeScrollRange$1(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        OrientationHelper orientationHelper = this.mPrimaryOrientation;
        boolean z = !this.mSmoothScrollbarEnabled;
        return ScrollbarHelper.computeScrollRange(state, orientationHelper, findFirstVisibleItemClosestToStart(z), findFirstVisibleItemClosestToEnd(z), this, this.mSmoothScrollbarEnabled);
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x000c  */
    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final PointF computeScrollVectorForPosition(int i) {
        int i2 = -1;
        if (getChildCount() != 0) {
            if ((i < getFirstChildPosition()) == this.mShouldReverseLayout) {
            }
        } else if (this.mShouldReverseLayout) {
            i2 = 1;
        }
        PointF pointF = new PointF();
        if (i2 == 0) {
            return null;
        }
        if (this.mOrientation == 0) {
            pointF.x = i2;
            pointF.y = 0.0f;
            return pointF;
        }
        pointF.x = 0.0f;
        pointF.y = i2;
        return pointF;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollExtent(RecyclerView.State state) {
        return computeScrollExtent$1(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset$1(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollRange(RecyclerView.State state) {
        return computeScrollRange$1(state);
    }

    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4, types: [boolean, int] */
    public final int fill(RecyclerView.Recycler recycler, LayoutState layoutState, RecyclerView.State state) {
        Span span;
        ?? r6;
        int i;
        int startLine;
        int decoratedMeasurement;
        int startAfterPadding;
        int decoratedMeasurement2;
        int i2;
        int i3;
        int i4;
        int i5 = 0;
        int i6 = 1;
        this.mRemainingSpans.set(0, this.mSpanCount, true);
        LayoutState layoutState2 = this.mLayoutState;
        int i7 = layoutState2.mInfinite ? layoutState.mLayoutDirection == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE : layoutState.mLayoutDirection == 1 ? layoutState.mEndLine + layoutState.mAvailable : layoutState.mStartLine - layoutState.mAvailable;
        int i8 = layoutState.mLayoutDirection;
        for (int i9 = 0; i9 < this.mSpanCount; i9++) {
            if (!this.mSpans[i9].mViews.isEmpty()) {
                updateRemainingSpans(this.mSpans[i9], i8, i7);
            }
        }
        int endAfterPadding = this.mShouldReverseLayout ? this.mPrimaryOrientation.getEndAfterPadding() : this.mPrimaryOrientation.getStartAfterPadding();
        boolean z = false;
        while (true) {
            int i10 = layoutState.mCurrentPosition;
            if (((i10 < 0 || i10 >= state.getItemCount()) ? i5 : i6) == 0 || (!layoutState2.mInfinite && this.mRemainingSpans.isEmpty())) {
                break;
            }
            View viewForPosition = recycler.getViewForPosition(layoutState.mCurrentPosition);
            layoutState.mCurrentPosition += layoutState.mItemDirection;
            LayoutParams layoutParams = (LayoutParams) viewForPosition.getLayoutParams();
            int layoutPosition = layoutParams.mViewHolder.getLayoutPosition();
            LazySpanLookup lazySpanLookup = this.mLazySpanLookup;
            int[] iArr = lazySpanLookup.mData;
            int i11 = (iArr == null || layoutPosition >= iArr.length) ? -1 : iArr[layoutPosition];
            if (i11 == -1) {
                if (preferLastSpan(layoutState.mLayoutDirection)) {
                    i4 = this.mSpanCount - i6;
                    i3 = -1;
                    i2 = -1;
                } else {
                    i2 = i6;
                    i3 = this.mSpanCount;
                    i4 = i5;
                }
                Span span2 = null;
                if (layoutState.mLayoutDirection == i6) {
                    int startAfterPadding2 = this.mPrimaryOrientation.getStartAfterPadding();
                    int i12 = Integer.MAX_VALUE;
                    while (i4 != i3) {
                        Span span3 = this.mSpans[i4];
                        int endLine = span3.getEndLine(startAfterPadding2);
                        if (endLine < i12) {
                            i12 = endLine;
                            span2 = span3;
                        }
                        i4 += i2;
                    }
                } else {
                    int endAfterPadding2 = this.mPrimaryOrientation.getEndAfterPadding();
                    int i13 = Integer.MIN_VALUE;
                    while (i4 != i3) {
                        Span span4 = this.mSpans[i4];
                        int startLine2 = span4.getStartLine(endAfterPadding2);
                        if (startLine2 > i13) {
                            span2 = span4;
                            i13 = startLine2;
                        }
                        i4 += i2;
                    }
                }
                span = span2;
                lazySpanLookup.ensureSize(layoutPosition);
                lazySpanLookup.mData[layoutPosition] = span.mIndex;
            } else {
                span = this.mSpans[i11];
            }
            layoutParams.mSpan = span;
            if (layoutState.mLayoutDirection == 1) {
                r6 = 0;
                addViewInt(viewForPosition, -1, false);
            } else {
                r6 = 0;
                addViewInt(viewForPosition, 0, false);
            }
            if (this.mOrientation == 1) {
                i = 1;
                measureChildWithDecorationsAndMargin$1(viewForPosition, RecyclerView.LayoutManager.getChildMeasureSpec(r6, this.mSizePerSpan, this.mWidthMode, r6, ((ViewGroup.MarginLayoutParams) layoutParams).width), RecyclerView.LayoutManager.getChildMeasureSpec(true, this.mHeight, this.mHeightMode, getPaddingBottom() + getPaddingTop(), ((ViewGroup.MarginLayoutParams) layoutParams).height));
            } else {
                i = 1;
                measureChildWithDecorationsAndMargin$1(viewForPosition, RecyclerView.LayoutManager.getChildMeasureSpec(true, this.mWidth, this.mWidthMode, getPaddingRight() + getPaddingLeft(), ((ViewGroup.MarginLayoutParams) layoutParams).width), RecyclerView.LayoutManager.getChildMeasureSpec(false, this.mSizePerSpan, this.mHeightMode, 0, ((ViewGroup.MarginLayoutParams) layoutParams).height));
            }
            if (layoutState.mLayoutDirection == i) {
                decoratedMeasurement = span.getEndLine(endAfterPadding);
                startLine = this.mPrimaryOrientation.getDecoratedMeasurement(viewForPosition) + decoratedMeasurement;
            } else {
                startLine = span.getStartLine(endAfterPadding);
                decoratedMeasurement = startLine - this.mPrimaryOrientation.getDecoratedMeasurement(viewForPosition);
            }
            if (layoutState.mLayoutDirection == 1) {
                Span span5 = layoutParams.mSpan;
                span5.getClass();
                LayoutParams layoutParams2 = (LayoutParams) viewForPosition.getLayoutParams();
                layoutParams2.mSpan = span5;
                span5.mViews.add(viewForPosition);
                span5.mCachedEnd = Integer.MIN_VALUE;
                if (span5.mViews.size() == 1) {
                    span5.mCachedStart = Integer.MIN_VALUE;
                }
                if (layoutParams2.mViewHolder.isRemoved() || layoutParams2.mViewHolder.isUpdated()) {
                    span5.mDeletedSize = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(viewForPosition) + span5.mDeletedSize;
                }
            } else {
                Span span6 = layoutParams.mSpan;
                span6.getClass();
                LayoutParams layoutParams3 = (LayoutParams) viewForPosition.getLayoutParams();
                layoutParams3.mSpan = span6;
                span6.mViews.add(0, viewForPosition);
                span6.mCachedStart = Integer.MIN_VALUE;
                if (span6.mViews.size() == 1) {
                    span6.mCachedEnd = Integer.MIN_VALUE;
                }
                if (layoutParams3.mViewHolder.isRemoved() || layoutParams3.mViewHolder.isUpdated()) {
                    span6.mDeletedSize = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(viewForPosition) + span6.mDeletedSize;
                }
            }
            if (isLayoutRTL() && this.mOrientation == 1) {
                decoratedMeasurement2 = this.mSecondaryOrientation.getEndAfterPadding() - (((this.mSpanCount - 1) - span.mIndex) * this.mSizePerSpan);
                startAfterPadding = decoratedMeasurement2 - this.mSecondaryOrientation.getDecoratedMeasurement(viewForPosition);
            } else {
                startAfterPadding = this.mSecondaryOrientation.getStartAfterPadding() + (span.mIndex * this.mSizePerSpan);
                decoratedMeasurement2 = this.mSecondaryOrientation.getDecoratedMeasurement(viewForPosition) + startAfterPadding;
            }
            if (this.mOrientation == 1) {
                RecyclerView.LayoutManager.layoutDecoratedWithMargins(viewForPosition, startAfterPadding, decoratedMeasurement, decoratedMeasurement2, startLine);
            } else {
                RecyclerView.LayoutManager.layoutDecoratedWithMargins(viewForPosition, decoratedMeasurement, startAfterPadding, startLine, decoratedMeasurement2);
            }
            updateRemainingSpans(span, layoutState2.mLayoutDirection, i7);
            recycle(recycler, layoutState2);
            if (layoutState2.mStopInFocusable && viewForPosition.hasFocusable()) {
                this.mRemainingSpans.set(span.mIndex, false);
            }
            i6 = 1;
            z = true;
            i5 = 0;
        }
        if (!z) {
            recycle(recycler, layoutState2);
        }
        int startAfterPadding3 = layoutState2.mLayoutDirection == -1 ? this.mPrimaryOrientation.getStartAfterPadding() - getMinStart(this.mPrimaryOrientation.getStartAfterPadding()) : getMaxEnd(this.mPrimaryOrientation.getEndAfterPadding()) - this.mPrimaryOrientation.getEndAfterPadding();
        if (startAfterPadding3 > 0) {
            return Math.min(layoutState.mAvailable, startAfterPadding3);
        }
        return 0;
    }

    public final View findFirstVisibleItemClosestToEnd(boolean z) {
        int startAfterPadding = this.mPrimaryOrientation.getStartAfterPadding();
        int endAfterPadding = this.mPrimaryOrientation.getEndAfterPadding();
        View view = null;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            int decoratedStart = this.mPrimaryOrientation.getDecoratedStart(childAt);
            int decoratedEnd = this.mPrimaryOrientation.getDecoratedEnd(childAt);
            if (decoratedEnd > startAfterPadding && decoratedStart < endAfterPadding) {
                if (decoratedEnd <= endAfterPadding || !z) {
                    return childAt;
                }
                if (view == null) {
                    view = childAt;
                }
            }
        }
        return view;
    }

    public final View findFirstVisibleItemClosestToStart(boolean z) {
        int startAfterPadding = this.mPrimaryOrientation.getStartAfterPadding();
        int endAfterPadding = this.mPrimaryOrientation.getEndAfterPadding();
        int childCount = getChildCount();
        View view = null;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int decoratedStart = this.mPrimaryOrientation.getDecoratedStart(childAt);
            if (this.mPrimaryOrientation.getDecoratedEnd(childAt) > startAfterPadding && decoratedStart < endAfterPadding) {
                if (decoratedStart >= startAfterPadding || !z) {
                    return childAt;
                }
                if (view == null) {
                    view = childAt;
                }
            }
        }
        return view;
    }

    public final int[] findFirstVisibleItemPositions() {
        int[] iArr = new int[this.mSpanCount];
        for (int i = 0; i < this.mSpanCount; i++) {
            Span span = this.mSpans[i];
            iArr[i] = StaggeredGridLayoutManager.this.mReverseLayout ? span.findOnePartiallyOrCompletelyVisibleChild(span.mViews.size() - 1, -1, true, false) : span.findOnePartiallyOrCompletelyVisibleChild(0, span.mViews.size(), true, false);
        }
        return iArr;
    }

    public final void fixEndGap(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int endAfterPadding;
        int maxEnd = getMaxEnd(Integer.MIN_VALUE);
        if (maxEnd != Integer.MIN_VALUE && (endAfterPadding = this.mPrimaryOrientation.getEndAfterPadding() - maxEnd) > 0) {
            int i = endAfterPadding - (-scrollBy$1(-endAfterPadding, recycler, state));
            if (!z || i <= 0) {
                return;
            }
            this.mPrimaryOrientation.offsetChildren(i);
        }
    }

    public final void fixStartGap(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int startAfterPadding;
        int minStart = getMinStart(Integer.MAX_VALUE);
        if (minStart != Integer.MAX_VALUE && (startAfterPadding = minStart - this.mPrimaryOrientation.getStartAfterPadding()) > 0) {
            int iScrollBy$1 = startAfterPadding - scrollBy$1(startAfterPadding, recycler, state);
            if (!z || iScrollBy$1 <= 0) {
                return;
            }
            this.mPrimaryOrientation.offsetChildren(-iScrollBy$1);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return this.mOrientation == 0 ? new LayoutParams(-2, -1) : new LayoutParams(-1, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.mOrientation == 1 ? this.mSpanCount : super.getColumnCountForAccessibility(recycler, state);
    }

    public final int getFirstChildPosition() {
        if (getChildCount() == 0) {
            return 0;
        }
        return RecyclerView.LayoutManager.getPosition(getChildAt(0));
    }

    public final int getLastChildPosition() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return 0;
        }
        return RecyclerView.LayoutManager.getPosition(getChildAt(childCount - 1));
    }

    public final int getMaxEnd(int i) {
        int endLine = this.mSpans[0].getEndLine(i);
        for (int i2 = 1; i2 < this.mSpanCount; i2++) {
            int endLine2 = this.mSpans[i2].getEndLine(i);
            if (endLine2 > endLine) {
                endLine = endLine2;
            }
        }
        return endLine;
    }

    public final int getMinStart(int i) {
        int startLine = this.mSpans[0].getStartLine(i);
        for (int i2 = 1; i2 < this.mSpanCount; i2++) {
            int startLine2 = this.mSpans[i2].getStartLine(i);
            if (startLine2 < startLine) {
                startLine = startLine2;
            }
        }
        return startLine;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.mOrientation == 0 ? this.mSpanCount : super.getRowCountForAccessibility(recycler, state);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:68:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleUpdate(int i, int i2, int i3) {
        int i4;
        int i5;
        LazySpanLookup lazySpanLookup;
        int[] iArr;
        List list;
        int i6;
        int lastChildPosition = this.mShouldReverseLayout ? getLastChildPosition() : getFirstChildPosition();
        if (i3 != 8) {
            i4 = i + i2;
        } else {
            if (i >= i2) {
                i4 = i + 1;
                i5 = i2;
                lazySpanLookup = this.mLazySpanLookup;
                iArr = lazySpanLookup.mData;
                if (iArr != null && i5 < iArr.length) {
                    list = lazySpanLookup.mFullSpanItems;
                    if (list != null) {
                        i6 = -1;
                        if (i6 != -1) {
                            int[] iArr2 = lazySpanLookup.mData;
                            Arrays.fill(iArr2, i5, iArr2.length, -1);
                            int length = lazySpanLookup.mData.length;
                        } else {
                            Arrays.fill(lazySpanLookup.mData, i5, Math.min(i6 + 1, lazySpanLookup.mData.length), -1);
                        }
                    } else {
                        LazySpanLookup.FullSpanItem fullSpanItem = null;
                        if (list != null) {
                            int size = list.size() - 1;
                            while (true) {
                                if (size < 0) {
                                    break;
                                }
                                LazySpanLookup.FullSpanItem fullSpanItem2 = (LazySpanLookup.FullSpanItem) lazySpanLookup.mFullSpanItems.get(size);
                                if (fullSpanItem2.mPosition == i5) {
                                    fullSpanItem = fullSpanItem2;
                                    break;
                                }
                                size--;
                            }
                        }
                        if (fullSpanItem != null) {
                            lazySpanLookup.mFullSpanItems.remove(fullSpanItem);
                        }
                        int size2 = lazySpanLookup.mFullSpanItems.size();
                        int i7 = 0;
                        while (true) {
                            if (i7 >= size2) {
                                i7 = -1;
                                break;
                            } else if (((LazySpanLookup.FullSpanItem) lazySpanLookup.mFullSpanItems.get(i7)).mPosition >= i5) {
                                break;
                            } else {
                                i7++;
                            }
                        }
                        if (i7 != -1) {
                            LazySpanLookup.FullSpanItem fullSpanItem3 = (LazySpanLookup.FullSpanItem) lazySpanLookup.mFullSpanItems.get(i7);
                            lazySpanLookup.mFullSpanItems.remove(i7);
                            i6 = fullSpanItem3.mPosition;
                        }
                        if (i6 != -1) {
                        }
                    }
                }
                if (i3 != 1) {
                    lazySpanLookup.offsetForAddition(i, i2);
                } else if (i3 == 2) {
                    lazySpanLookup.offsetForRemoval(i, i2);
                } else if (i3 == 8) {
                    lazySpanLookup.offsetForRemoval(i, 1);
                    lazySpanLookup.offsetForAddition(i2, 1);
                }
                if (i4 > lastChildPosition) {
                    return;
                }
                if (i5 <= (this.mShouldReverseLayout ? getFirstChildPosition() : getLastChildPosition())) {
                    requestLayout();
                    return;
                }
                return;
            }
            i4 = i2 + 1;
        }
        i5 = i;
        lazySpanLookup = this.mLazySpanLookup;
        iArr = lazySpanLookup.mData;
        if (iArr != null) {
            list = lazySpanLookup.mFullSpanItems;
            if (list != null) {
            }
        }
        if (i3 != 1) {
        }
        if (i4 > lastChildPosition) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00fa A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x002c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View hasGapsToFix() {
        int childCount = getChildCount();
        int i = childCount - 1;
        BitSet bitSet = new BitSet(this.mSpanCount);
        bitSet.set(0, this.mSpanCount, true);
        char c = (this.mOrientation == 1 && isLayoutRTL()) ? (char) 1 : (char) 65535;
        if (this.mShouldReverseLayout) {
            childCount = -1;
        } else {
            i = 0;
        }
        int i2 = i < childCount ? 1 : -1;
        while (i != childCount) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (bitSet.get(layoutParams.mSpan.mIndex)) {
                Span span = layoutParams.mSpan;
                if (this.mShouldReverseLayout) {
                    int i3 = span.mCachedEnd;
                    if (i3 == Integer.MIN_VALUE) {
                        span.calculateCachedEnd();
                        i3 = span.mCachedEnd;
                    }
                    if (i3 < this.mPrimaryOrientation.getEndAfterPadding()) {
                        ((LayoutParams) ((View) AlertController$$ExternalSyntheticOutline0.m(1, span.mViews)).getLayoutParams()).getClass();
                        return childAt;
                    }
                } else {
                    int i4 = span.mCachedStart;
                    if (i4 == Integer.MIN_VALUE) {
                        View view = (View) span.mViews.get(0);
                        LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
                        span.mCachedStart = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(view);
                        layoutParams2.getClass();
                        i4 = span.mCachedStart;
                    }
                    if (i4 > this.mPrimaryOrientation.getStartAfterPadding()) {
                        ((LayoutParams) ((View) span.mViews.get(0)).getLayoutParams()).getClass();
                        return childAt;
                    }
                }
                bitSet.clear(layoutParams.mSpan.mIndex);
            }
            i += i2;
            if (i != childCount) {
                View childAt2 = getChildAt(i);
                if (this.mShouldReverseLayout) {
                    int decoratedEnd = this.mPrimaryOrientation.getDecoratedEnd(childAt);
                    int decoratedEnd2 = this.mPrimaryOrientation.getDecoratedEnd(childAt2);
                    if (decoratedEnd < decoratedEnd2) {
                        return childAt;
                    }
                    if (decoratedEnd == decoratedEnd2) {
                        if ((layoutParams.mSpan.mIndex - ((LayoutParams) childAt2.getLayoutParams()).mSpan.mIndex >= 0) == (c >= 0)) {
                            return childAt;
                        }
                    } else {
                        continue;
                    }
                } else {
                    int decoratedStart = this.mPrimaryOrientation.getDecoratedStart(childAt);
                    int decoratedStart2 = this.mPrimaryOrientation.getDecoratedStart(childAt2);
                    if (decoratedStart > decoratedStart2) {
                        return childAt;
                    }
                    if (decoratedStart == decoratedStart2) {
                        if ((layoutParams.mSpan.mIndex - ((LayoutParams) childAt2.getLayoutParams()).mSpan.mIndex >= 0) == (c >= 0)) {
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean isAutoMeasureEnabled() {
        return this.mGapStrategy != 0;
    }

    public final boolean isLayoutRTL() {
        return getLayoutDirection() == 1;
    }

    public final void measureChildWithDecorationsAndMargin$1(View view, int i, int i2) {
        calculateItemDecorationsForChild(this.mTmpRect, view);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i3 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
        Rect rect = this.mTmpRect;
        int iUpdateSpecWithExtra = updateSpecWithExtra(i, i3 + rect.left, ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + rect.right);
        int i4 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
        Rect rect2 = this.mTmpRect;
        int iUpdateSpecWithExtra2 = updateSpecWithExtra(i2, i4 + rect2.top, ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + rect2.bottom);
        if (shouldMeasureChild(view, iUpdateSpecWithExtra, iUpdateSpecWithExtra2, layoutParams)) {
            view.measure(iUpdateSpecWithExtra, iUpdateSpecWithExtra2);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void offsetChildrenHorizontal(int i) {
        super.offsetChildrenHorizontal(i);
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            Span span = this.mSpans[i2];
            int i3 = span.mCachedStart;
            if (i3 != Integer.MIN_VALUE) {
                span.mCachedStart = i3 + i;
            }
            int i4 = span.mCachedEnd;
            if (i4 != Integer.MIN_VALUE) {
                span.mCachedEnd = i4 + i;
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void offsetChildrenVertical(int i) {
        super.offsetChildrenVertical(i);
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            Span span = this.mSpans[i2];
            int i3 = span.mCachedStart;
            if (i3 != Integer.MIN_VALUE) {
                span.mCachedStart = i3 + i;
            }
            int i4 = span.mCachedEnd;
            if (i4 != Integer.MIN_VALUE) {
                span.mCachedEnd = i4 + i;
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onAdapterChanged(RecyclerView.Adapter adapter) {
        this.mLazySpanLookup.clear();
        for (int i = 0; i < this.mSpanCount; i++) {
            this.mSpans[i].clear();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onDetachedFromWindow(RecyclerView recyclerView) {
        AnonymousClass1 anonymousClass1 = this.mCheckForGapsRunnable;
        RecyclerView recyclerView2 = this.mRecyclerView;
        if (recyclerView2 != null) {
            recyclerView2.removeCallbacks(anonymousClass1);
        }
        for (int i = 0; i < this.mSpanCount; i++) {
            this.mSpans[i].clear();
        }
        recyclerView.requestLayout();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x003d  */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View onFocusSearchFailed(View view, int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        View viewFindContainingItemView;
        int i2;
        if (getChildCount() == 0 || (viewFindContainingItemView = findContainingItemView(view)) == null) {
            return null;
        }
        resolveShouldLayoutReverse$1();
        if (i != 1) {
            if (i != 2) {
                if (i != 17) {
                    if (i != 33) {
                        if (i == 66 ? this.mOrientation == 0 : !(i != 130 || this.mOrientation != 1)) {
                            i2 = 1;
                        }
                    } else if (this.mOrientation == 1) {
                        i2 = -1;
                    }
                    i2 = Integer.MIN_VALUE;
                } else if (this.mOrientation != 0) {
                    i2 = Integer.MIN_VALUE;
                }
            } else if (this.mOrientation != 1 && isLayoutRTL()) {
            }
        } else if (this.mOrientation != 1 && isLayoutRTL()) {
        }
        if (i2 == Integer.MIN_VALUE) {
            return null;
        }
        LayoutParams layoutParams = (LayoutParams) viewFindContainingItemView.getLayoutParams();
        layoutParams.getClass();
        Span span = layoutParams.mSpan;
        int lastChildPosition = i2 == 1 ? getLastChildPosition() : getFirstChildPosition();
        updateLayoutState(lastChildPosition, state);
        setLayoutStateDirection(i2);
        LayoutState layoutState = this.mLayoutState;
        layoutState.mCurrentPosition = layoutState.mItemDirection + lastChildPosition;
        layoutState.mAvailable = (int) (this.mPrimaryOrientation.getTotalSpace() * 0.33333334f);
        layoutState.mStopInFocusable = true;
        layoutState.mRecycle = false;
        fill(recycler, layoutState, state);
        this.mLastLayoutFromEnd = this.mShouldReverseLayout;
        View focusableViewAfter = span.getFocusableViewAfter(lastChildPosition, i2);
        if (focusableViewAfter != null && focusableViewAfter != viewFindContainingItemView) {
            return focusableViewAfter;
        }
        if (preferLastSpan(i2)) {
            for (int i3 = this.mSpanCount - 1; i3 >= 0; i3--) {
                View focusableViewAfter2 = this.mSpans[i3].getFocusableViewAfter(lastChildPosition, i2);
                if (focusableViewAfter2 != null && focusableViewAfter2 != viewFindContainingItemView) {
                    return focusableViewAfter2;
                }
            }
        } else {
            for (int i4 = 0; i4 < this.mSpanCount; i4++) {
                View focusableViewAfter3 = this.mSpans[i4].getFocusableViewAfter(lastChildPosition, i2);
                if (focusableViewAfter3 != null && focusableViewAfter3 != viewFindContainingItemView) {
                    return focusableViewAfter3;
                }
            }
        }
        boolean z = (this.mReverseLayout ^ true) == (i2 == -1);
        View viewFindViewByPosition = findViewByPosition(z ? span.findFirstPartiallyVisibleItemPosition() : span.findLastPartiallyVisibleItemPosition());
        if (viewFindViewByPosition != null && viewFindViewByPosition != viewFindContainingItemView) {
            return viewFindViewByPosition;
        }
        if (!preferLastSpan(i2)) {
            for (int i5 = 0; i5 < this.mSpanCount; i5++) {
                View viewFindViewByPosition2 = findViewByPosition(z ? this.mSpans[i5].findFirstPartiallyVisibleItemPosition() : this.mSpans[i5].findLastPartiallyVisibleItemPosition());
                if (viewFindViewByPosition2 != null && viewFindViewByPosition2 != viewFindContainingItemView) {
                    return viewFindViewByPosition2;
                }
            }
            return null;
        }
        for (int i6 = this.mSpanCount - 1; i6 >= 0; i6--) {
            if (i6 != span.mIndex) {
                View viewFindViewByPosition3 = findViewByPosition(z ? this.mSpans[i6].findFirstPartiallyVisibleItemPosition() : this.mSpans[i6].findLastPartiallyVisibleItemPosition());
                if (viewFindViewByPosition3 != null && viewFindViewByPosition3 != viewFindContainingItemView) {
                    return viewFindViewByPosition3;
                }
            }
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            View viewFindFirstVisibleItemClosestToStart = findFirstVisibleItemClosestToStart(false);
            View viewFindFirstVisibleItemClosestToEnd = findFirstVisibleItemClosestToEnd(false);
            if (viewFindFirstVisibleItemClosestToStart == null || viewFindFirstVisibleItemClosestToEnd == null) {
                return;
            }
            int position = RecyclerView.LayoutManager.getPosition(viewFindFirstVisibleItemClosestToStart);
            int position2 = RecyclerView.LayoutManager.getPosition(viewFindFirstVisibleItemClosestToEnd);
            if (position < position2) {
                accessibilityEvent.setFromIndex(position);
                accessibilityEvent.setToIndex(position2);
            } else {
                accessibilityEvent.setFromIndex(position2);
                accessibilityEvent.setToIndex(position);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            onInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfoCompat);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        if (this.mOrientation == 0) {
            Span span = layoutParams2.mSpan;
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, span == null ? -1 : span.mIndex, 1, -1, -1));
        } else {
            Span span2 = layoutParams2.mSpan;
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, -1, -1, span2 == null ? -1 : span2.mIndex, 1));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsAdded(int i, int i2) {
        handleUpdate(i, i2, 1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsChanged() {
        this.mLazySpanLookup.clear();
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsMoved(int i, int i2) {
        handleUpdate(i, i2, 8);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsRemoved(int i, int i2) {
        handleUpdate(i, i2, 2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
        handleUpdate(i, i2, 4);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        onLayoutChildren(recycler, state, true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onLayoutCompleted(RecyclerView.State state) {
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo.reset();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            this.mPendingSavedState = savedState;
            if (this.mPendingScrollPosition != -1) {
                savedState.mSpanOffsets = null;
                savedState.mSpanOffsetsSize = 0;
                savedState.mAnchorPosition = -1;
                savedState.mVisibleAnchorPosition = -1;
                savedState.mSpanOffsets = null;
                savedState.mSpanOffsetsSize = 0;
                savedState.mSpanLookupSize = 0;
                savedState.mSpanLookup = null;
                savedState.mFullSpanItems = null;
            }
            requestLayout();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final Parcelable onSaveInstanceState() {
        int startLine;
        int startAfterPadding;
        int[] iArr;
        if (this.mPendingSavedState != null) {
            return new SavedState(this.mPendingSavedState);
        }
        SavedState savedState = new SavedState();
        savedState.mReverseLayout = this.mReverseLayout;
        savedState.mAnchorLayoutFromEnd = this.mLastLayoutFromEnd;
        savedState.mLastLayoutRTL = this.mLastLayoutRTL;
        LazySpanLookup lazySpanLookup = this.mLazySpanLookup;
        if (lazySpanLookup == null || (iArr = lazySpanLookup.mData) == null) {
            savedState.mSpanLookupSize = 0;
        } else {
            savedState.mSpanLookup = iArr;
            savedState.mSpanLookupSize = iArr.length;
            savedState.mFullSpanItems = lazySpanLookup.mFullSpanItems;
        }
        if (getChildCount() <= 0) {
            savedState.mAnchorPosition = -1;
            savedState.mVisibleAnchorPosition = -1;
            savedState.mSpanOffsetsSize = 0;
            return savedState;
        }
        savedState.mAnchorPosition = this.mLastLayoutFromEnd ? getLastChildPosition() : getFirstChildPosition();
        View viewFindFirstVisibleItemClosestToEnd = this.mShouldReverseLayout ? findFirstVisibleItemClosestToEnd(true) : findFirstVisibleItemClosestToStart(true);
        savedState.mVisibleAnchorPosition = viewFindFirstVisibleItemClosestToEnd != null ? RecyclerView.LayoutManager.getPosition(viewFindFirstVisibleItemClosestToEnd) : -1;
        int i = this.mSpanCount;
        savedState.mSpanOffsetsSize = i;
        savedState.mSpanOffsets = new int[i];
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            if (this.mLastLayoutFromEnd) {
                startLine = this.mSpans[i2].getEndLine(Integer.MIN_VALUE);
                if (startLine != Integer.MIN_VALUE) {
                    startAfterPadding = this.mPrimaryOrientation.getEndAfterPadding();
                    startLine -= startAfterPadding;
                }
            } else {
                startLine = this.mSpans[i2].getStartLine(Integer.MIN_VALUE);
                if (startLine != Integer.MIN_VALUE) {
                    startAfterPadding = this.mPrimaryOrientation.getStartAfterPadding();
                    startLine -= startAfterPadding;
                }
            }
            savedState.mSpanOffsets[i2] = startLine;
        }
        return savedState;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onScrollStateChanged(int i) {
        if (i == 0) {
            checkForGaps();
        }
    }

    public final boolean preferLastSpan(int i) {
        if (this.mOrientation == 0) {
            return (i == -1) != this.mShouldReverseLayout;
        }
        return ((i == -1) == this.mShouldReverseLayout) == isLayoutRTL();
    }

    public final void prepareLayoutStateForDelta(int i, RecyclerView.State state) {
        int firstChildPosition;
        int i2;
        if (i > 0) {
            firstChildPosition = getLastChildPosition();
            i2 = 1;
        } else {
            firstChildPosition = getFirstChildPosition();
            i2 = -1;
        }
        LayoutState layoutState = this.mLayoutState;
        layoutState.mRecycle = true;
        updateLayoutState(firstChildPosition, state);
        setLayoutStateDirection(i2);
        layoutState.mCurrentPosition = firstChildPosition + layoutState.mItemDirection;
        layoutState.mAvailable = Math.abs(i);
    }

    public final void recycle(RecyclerView.Recycler recycler, LayoutState layoutState) {
        if (!layoutState.mRecycle || layoutState.mInfinite) {
            return;
        }
        if (layoutState.mAvailable == 0) {
            if (layoutState.mLayoutDirection == -1) {
                recycleFromEnd(recycler, layoutState.mEndLine);
                return;
            } else {
                recycleFromStart(recycler, layoutState.mStartLine);
                return;
            }
        }
        int i = 1;
        if (layoutState.mLayoutDirection == -1) {
            int i2 = layoutState.mStartLine;
            int startLine = this.mSpans[0].getStartLine(i2);
            while (i < this.mSpanCount) {
                int startLine2 = this.mSpans[i].getStartLine(i2);
                if (startLine2 > startLine) {
                    startLine = startLine2;
                }
                i++;
            }
            int i3 = i2 - startLine;
            recycleFromEnd(recycler, i3 < 0 ? layoutState.mEndLine : layoutState.mEndLine - Math.min(i3, layoutState.mAvailable));
            return;
        }
        int i4 = layoutState.mEndLine;
        int endLine = this.mSpans[0].getEndLine(i4);
        while (i < this.mSpanCount) {
            int endLine2 = this.mSpans[i].getEndLine(i4);
            if (endLine2 < endLine) {
                endLine = endLine2;
            }
            i++;
        }
        int i5 = endLine - layoutState.mEndLine;
        recycleFromStart(recycler, i5 < 0 ? layoutState.mStartLine : Math.min(i5, layoutState.mAvailable) + layoutState.mStartLine);
    }

    public final void recycleFromEnd(RecyclerView.Recycler recycler, int i) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (this.mPrimaryOrientation.getDecoratedStart(childAt) < i || this.mPrimaryOrientation.getTransformedStartWithDecoration(childAt) < i) {
                return;
            }
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            layoutParams.getClass();
            if (layoutParams.mSpan.mViews.size() == 1) {
                return;
            }
            Span span = layoutParams.mSpan;
            int size = span.mViews.size();
            View view = (View) span.mViews.remove(size - 1);
            LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
            layoutParams2.mSpan = null;
            if (layoutParams2.mViewHolder.isRemoved() || layoutParams2.mViewHolder.isUpdated()) {
                span.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
            }
            if (size == 1) {
                span.mCachedStart = Integer.MIN_VALUE;
            }
            span.mCachedEnd = Integer.MIN_VALUE;
            removeAndRecycleView(childAt, recycler);
        }
    }

    public final void recycleFromStart(RecyclerView.Recycler recycler, int i) {
        while (getChildCount() > 0) {
            View childAt = getChildAt(0);
            if (this.mPrimaryOrientation.getDecoratedEnd(childAt) > i || this.mPrimaryOrientation.getTransformedEndWithDecoration(childAt) > i) {
                return;
            }
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            layoutParams.getClass();
            if (layoutParams.mSpan.mViews.size() == 1) {
                return;
            }
            Span span = layoutParams.mSpan;
            View view = (View) span.mViews.remove(0);
            LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
            layoutParams2.mSpan = null;
            if (span.mViews.size() == 0) {
                span.mCachedEnd = Integer.MIN_VALUE;
            }
            if (layoutParams2.mViewHolder.isRemoved() || layoutParams2.mViewHolder.isUpdated()) {
                span.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
            }
            span.mCachedStart = Integer.MIN_VALUE;
            removeAndRecycleView(childAt, recycler);
        }
    }

    public final void resolveShouldLayoutReverse$1() {
        if (this.mOrientation == 1 || !isLayoutRTL()) {
            this.mShouldReverseLayout = this.mReverseLayout;
        } else {
            this.mShouldReverseLayout = !this.mReverseLayout;
        }
    }

    public final int scrollBy$1(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0 || i == 0) {
            return 0;
        }
        prepareLayoutStateForDelta(i, state);
        LayoutState layoutState = this.mLayoutState;
        int iFill = fill(recycler, layoutState, state);
        if (layoutState.mAvailable >= iFill) {
            i = i < 0 ? -iFill : iFill;
        }
        this.mPrimaryOrientation.offsetChildren(-i);
        this.mLastLayoutFromEnd = this.mShouldReverseLayout;
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.showGoToTop$1();
        }
        layoutState.mAvailable = 0;
        recycle(recycler, layoutState);
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return scrollBy$1(i, recycler, state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void scrollToPosition(int i) {
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null && savedState.mAnchorPosition != i) {
            savedState.mSpanOffsets = null;
            savedState.mSpanOffsetsSize = 0;
            savedState.mAnchorPosition = -1;
            savedState.mVisibleAnchorPosition = -1;
        }
        this.mPendingScrollPosition = i;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.showGoToTop$1();
        }
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return scrollBy$1(i, recycler, state);
    }

    public final void setLayoutStateDirection(int i) {
        LayoutState layoutState = this.mLayoutState;
        layoutState.mLayoutDirection = i;
        layoutState.mItemDirection = this.mShouldReverseLayout != (i == -1) ? -1 : 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void setMeasuredDimension(int i, int i2, Rect rect) {
        int iChooseSize;
        int iChooseSize2;
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        if (this.mOrientation == 1) {
            int iHeight = rect.height() + paddingBottom;
            RecyclerView recyclerView = this.mRecyclerView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            iChooseSize2 = RecyclerView.LayoutManager.chooseSize(i2, iHeight, recyclerView.getMinimumHeight());
            iChooseSize = RecyclerView.LayoutManager.chooseSize(i, (this.mSizePerSpan * this.mSpanCount) + paddingRight, this.mRecyclerView.getMinimumWidth());
        } else {
            int iWidth = rect.width() + paddingRight;
            RecyclerView recyclerView2 = this.mRecyclerView;
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            iChooseSize = RecyclerView.LayoutManager.chooseSize(i, iWidth, recyclerView2.getMinimumWidth());
            iChooseSize2 = RecyclerView.LayoutManager.chooseSize(i2, (this.mSizePerSpan * this.mSpanCount) + paddingBottom, this.mRecyclerView.getMinimumHeight());
        }
        this.mRecyclerView.setMeasuredDimension(iChooseSize, iChooseSize2);
    }

    public final void setSpanCount(int i) {
        assertNotInLayoutOrScroll(null);
        if (i != this.mSpanCount) {
            this.mLazySpanLookup.clear();
            requestLayout();
            this.mSpanCount = i;
            this.mRemainingSpans = new BitSet(this.mSpanCount);
            this.mSpans = new Span[this.mSpanCount];
            for (int i2 = 0; i2 < this.mSpanCount; i2++) {
                this.mSpans[i2] = new Span(i2);
            }
            requestLayout();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void smoothScrollToPosition(RecyclerView recyclerView, int i) {
        if (recyclerView != null) {
            LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext());
            recyclerView.showGoToTop$1();
            linearSmoothScroller.mTargetPosition = i;
            startSmoothScroll(linearSmoothScroller);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null;
    }

    public final void updateLayoutState(int i, RecyclerView.State state) {
        int totalSpace;
        int totalSpace2;
        int i2;
        LayoutState layoutState = this.mLayoutState;
        boolean z = false;
        layoutState.mAvailable = 0;
        layoutState.mCurrentPosition = i;
        RecyclerView.SmoothScroller smoothScroller = this.mSmoothScroller;
        if (!(smoothScroller != null && smoothScroller.mRunning) || (i2 = state.mTargetPosition) == -1) {
            totalSpace = 0;
            totalSpace2 = 0;
        } else {
            if (this.mShouldReverseLayout == (i2 < i)) {
                totalSpace = this.mPrimaryOrientation.getTotalSpace();
                totalSpace2 = 0;
            } else {
                totalSpace2 = this.mPrimaryOrientation.getTotalSpace();
                totalSpace = 0;
            }
        }
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView == null || !recyclerView.mClipToPadding) {
            layoutState.mEndLine = this.mPrimaryOrientation.getEnd() + totalSpace;
            layoutState.mStartLine = -totalSpace2;
        } else {
            layoutState.mStartLine = this.mPrimaryOrientation.getStartAfterPadding() - totalSpace2;
            layoutState.mEndLine = this.mPrimaryOrientation.getEndAfterPadding() + totalSpace;
        }
        layoutState.mStopInFocusable = false;
        layoutState.mRecycle = true;
        if (this.mPrimaryOrientation.getMode() == 0 && this.mPrimaryOrientation.getEnd() == 0) {
            z = true;
        }
        layoutState.mInfinite = z;
    }

    public final void updateRemainingSpans(Span span, int i, int i2) {
        int i3 = span.mDeletedSize;
        int i4 = span.mIndex;
        if (i != -1) {
            int i5 = span.mCachedEnd;
            if (i5 == Integer.MIN_VALUE) {
                span.calculateCachedEnd();
                i5 = span.mCachedEnd;
            }
            if (i5 - i3 >= i2) {
                this.mRemainingSpans.set(i4, false);
                return;
            }
            return;
        }
        int i6 = span.mCachedStart;
        if (i6 == Integer.MIN_VALUE) {
            View view = (View) span.mViews.get(0);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            span.mCachedStart = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(view);
            layoutParams.getClass();
            i6 = span.mCachedStart;
        }
        if (i6 + i3 <= i2) {
            this.mRemainingSpans.set(i4, false);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:263:0x044c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        SavedState savedState;
        int[] iArr;
        int[] iArr2;
        int position;
        int i;
        boolean z2;
        SavedState savedState2 = this.mPendingSavedState;
        AnchorInfo anchorInfo = this.mAnchorInfo;
        if (!(savedState2 == null && this.mPendingScrollPosition == -1) && state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            anchorInfo.reset();
            return;
        }
        boolean z3 = true;
        boolean z4 = (anchorInfo.mValid && this.mPendingScrollPosition == -1 && this.mPendingSavedState == null) ? false : true;
        LazySpanLookup lazySpanLookup = this.mLazySpanLookup;
        StaggeredGridLayoutManager staggeredGridLayoutManager = StaggeredGridLayoutManager.this;
        if (z4) {
            anchorInfo.reset();
            SavedState savedState3 = this.mPendingSavedState;
            if (savedState3 != null) {
                int i2 = savedState3.mSpanOffsetsSize;
                if (i2 > 0) {
                    if (i2 == this.mSpanCount) {
                        for (int i3 = 0; i3 < this.mSpanCount; i3++) {
                            this.mSpans[i3].clear();
                            SavedState savedState4 = this.mPendingSavedState;
                            int endAfterPadding = savedState4.mSpanOffsets[i3];
                            if (endAfterPadding != Integer.MIN_VALUE) {
                                endAfterPadding += savedState4.mAnchorLayoutFromEnd ? this.mPrimaryOrientation.getEndAfterPadding() : this.mPrimaryOrientation.getStartAfterPadding();
                            }
                            Span span = this.mSpans[i3];
                            span.mCachedStart = endAfterPadding;
                            span.mCachedEnd = endAfterPadding;
                        }
                    } else {
                        savedState3.mSpanOffsets = null;
                        savedState3.mSpanOffsetsSize = 0;
                        savedState3.mSpanLookupSize = 0;
                        savedState3.mSpanLookup = null;
                        savedState3.mFullSpanItems = null;
                        savedState3.mAnchorPosition = savedState3.mVisibleAnchorPosition;
                    }
                }
                SavedState savedState5 = this.mPendingSavedState;
                this.mLastLayoutRTL = savedState5.mLastLayoutRTL;
                boolean z5 = savedState5.mReverseLayout;
                assertNotInLayoutOrScroll(null);
                SavedState savedState6 = this.mPendingSavedState;
                if (savedState6 != null && savedState6.mReverseLayout != z5) {
                    savedState6.mReverseLayout = z5;
                }
                this.mReverseLayout = z5;
                requestLayout();
                resolveShouldLayoutReverse$1();
                SavedState savedState7 = this.mPendingSavedState;
                int i4 = savedState7.mAnchorPosition;
                if (i4 != -1) {
                    this.mPendingScrollPosition = i4;
                    anchorInfo.mLayoutFromEnd = savedState7.mAnchorLayoutFromEnd;
                } else {
                    anchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
                }
                if (savedState7.mSpanLookupSize > 1) {
                    lazySpanLookup.mData = savedState7.mSpanLookup;
                    lazySpanLookup.mFullSpanItems = savedState7.mFullSpanItems;
                }
            } else {
                resolveShouldLayoutReverse$1();
                anchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
            }
            if (state.mInPreLayout || (i = this.mPendingScrollPosition) == -1) {
                if (this.mLastLayoutFromEnd) {
                    int itemCount = state.getItemCount();
                    int childCount = getChildCount();
                    for (int i5 = 0; i5 < childCount; i5++) {
                        int position2 = RecyclerView.LayoutManager.getPosition(getChildAt(i5));
                        if (position2 >= 0 && position2 < itemCount) {
                            position = position2;
                            break;
                        }
                    }
                    position = 0;
                    anchorInfo.mPosition = position;
                    anchorInfo.mOffset = Integer.MIN_VALUE;
                    anchorInfo.mValid = true;
                } else {
                    int itemCount2 = state.getItemCount();
                    for (int childCount2 = getChildCount() - 1; childCount2 >= 0; childCount2--) {
                        position = RecyclerView.LayoutManager.getPosition(getChildAt(childCount2));
                        if (position >= 0 && position < itemCount2) {
                            break;
                        }
                    }
                    position = 0;
                    anchorInfo.mPosition = position;
                    anchorInfo.mOffset = Integer.MIN_VALUE;
                    anchorInfo.mValid = true;
                }
            } else if (i < 0 || i >= state.getItemCount()) {
                this.mPendingScrollPosition = -1;
                this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
                if (this.mLastLayoutFromEnd) {
                }
            } else {
                SavedState savedState8 = this.mPendingSavedState;
                if (savedState8 == null || savedState8.mAnchorPosition == -1 || savedState8.mSpanOffsetsSize < 1) {
                    View viewFindViewByPosition = findViewByPosition(this.mPendingScrollPosition);
                    if (viewFindViewByPosition != null) {
                        anchorInfo.mPosition = this.mShouldReverseLayout ? getLastChildPosition() : getFirstChildPosition();
                        if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE) {
                            if (anchorInfo.mLayoutFromEnd) {
                                anchorInfo.mOffset = (this.mPrimaryOrientation.getEndAfterPadding() - this.mPendingScrollPositionOffset) - this.mPrimaryOrientation.getDecoratedEnd(viewFindViewByPosition);
                            } else {
                                anchorInfo.mOffset = (this.mPrimaryOrientation.getStartAfterPadding() + this.mPendingScrollPositionOffset) - this.mPrimaryOrientation.getDecoratedStart(viewFindViewByPosition);
                            }
                        } else if (this.mPrimaryOrientation.getDecoratedMeasurement(viewFindViewByPosition) > this.mPrimaryOrientation.getTotalSpace()) {
                            anchorInfo.mOffset = anchorInfo.mLayoutFromEnd ? this.mPrimaryOrientation.getEndAfterPadding() : this.mPrimaryOrientation.getStartAfterPadding();
                        } else {
                            int decoratedStart = this.mPrimaryOrientation.getDecoratedStart(viewFindViewByPosition) - this.mPrimaryOrientation.getStartAfterPadding();
                            if (decoratedStart < 0) {
                                anchorInfo.mOffset = -decoratedStart;
                            } else {
                                int endAfterPadding2 = this.mPrimaryOrientation.getEndAfterPadding() - this.mPrimaryOrientation.getDecoratedEnd(viewFindViewByPosition);
                                if (endAfterPadding2 < 0) {
                                    anchorInfo.mOffset = endAfterPadding2;
                                } else {
                                    anchorInfo.mOffset = Integer.MIN_VALUE;
                                }
                            }
                        }
                    } else {
                        int i6 = this.mPendingScrollPosition;
                        anchorInfo.mPosition = i6;
                        int i7 = this.mPendingScrollPositionOffset;
                        if (i7 == Integer.MIN_VALUE) {
                            if (getChildCount() == 0) {
                                z2 = this.mShouldReverseLayout;
                            } else if ((i6 < getFirstChildPosition()) != this.mShouldReverseLayout) {
                            }
                            anchorInfo.mLayoutFromEnd = z2;
                            anchorInfo.mOffset = z2 ? staggeredGridLayoutManager.mPrimaryOrientation.getEndAfterPadding() : staggeredGridLayoutManager.mPrimaryOrientation.getStartAfterPadding();
                        } else if (anchorInfo.mLayoutFromEnd) {
                            anchorInfo.mOffset = staggeredGridLayoutManager.mPrimaryOrientation.getEndAfterPadding() - i7;
                        } else {
                            anchorInfo.mOffset = staggeredGridLayoutManager.mPrimaryOrientation.getStartAfterPadding() + i7;
                        }
                        anchorInfo.mInvalidateOffsets = true;
                    }
                } else {
                    anchorInfo.mOffset = Integer.MIN_VALUE;
                    anchorInfo.mPosition = this.mPendingScrollPosition;
                }
                anchorInfo.mValid = true;
            }
        }
        if (this.mPendingSavedState == null && this.mPendingScrollPosition == -1 && (anchorInfo.mLayoutFromEnd != this.mLastLayoutFromEnd || isLayoutRTL() != this.mLastLayoutRTL)) {
            lazySpanLookup.clear();
            anchorInfo.mInvalidateOffsets = true;
        }
        if (getChildCount() > 0 && ((savedState = this.mPendingSavedState) == null || savedState.mSpanOffsetsSize < 1)) {
            if (anchorInfo.mInvalidateOffsets) {
                for (int i8 = 0; i8 < this.mSpanCount; i8++) {
                    this.mSpans[i8].clear();
                    int i9 = anchorInfo.mOffset;
                    if (i9 != Integer.MIN_VALUE) {
                        Span span2 = this.mSpans[i8];
                        span2.mCachedStart = i9;
                        span2.mCachedEnd = i9;
                    }
                }
            } else if (z4 || (iArr2 = anchorInfo.mSpanReferenceLines) == null || iArr2.length < this.mSpanCount) {
                if (!z4 && (iArr = anchorInfo.mSpanReferenceLines) != null && iArr.length < this.mSpanCount) {
                    Log.w("StaggeredGridLManager", "mSpanReferenceLines length(" + anchorInfo.mSpanReferenceLines.length + ") smaller than SpanCount(" + this.mSpanCount + ")");
                }
                for (int i10 = 0; i10 < this.mSpanCount; i10++) {
                    Span span3 = this.mSpans[i10];
                    boolean z6 = this.mShouldReverseLayout;
                    int i11 = anchorInfo.mOffset;
                    int endLine = z6 ? span3.getEndLine(Integer.MIN_VALUE) : span3.getStartLine(Integer.MIN_VALUE);
                    span3.clear();
                    if (endLine != Integer.MIN_VALUE) {
                        StaggeredGridLayoutManager staggeredGridLayoutManager2 = StaggeredGridLayoutManager.this;
                        if ((!z6 || endLine >= staggeredGridLayoutManager2.mPrimaryOrientation.getEndAfterPadding()) && (z6 || endLine <= staggeredGridLayoutManager2.mPrimaryOrientation.getStartAfterPadding())) {
                            if (i11 != Integer.MIN_VALUE) {
                                endLine += i11;
                            }
                            span3.mCachedEnd = endLine;
                            span3.mCachedStart = endLine;
                        }
                    }
                }
                Span[] spanArr = this.mSpans;
                int length = spanArr.length;
                int[] iArr3 = anchorInfo.mSpanReferenceLines;
                if (iArr3 == null || iArr3.length < length) {
                    anchorInfo.mSpanReferenceLines = new int[staggeredGridLayoutManager.mSpans.length];
                }
                for (int i12 = 0; i12 < length; i12++) {
                    anchorInfo.mSpanReferenceLines[i12] = spanArr[i12].getStartLine(Integer.MIN_VALUE);
                }
            } else {
                for (int i13 = 0; i13 < this.mSpanCount; i13++) {
                    Span span4 = this.mSpans[i13];
                    span4.clear();
                    int i14 = anchorInfo.mSpanReferenceLines[i13];
                    span4.mCachedStart = i14;
                    span4.mCachedEnd = i14;
                }
            }
        }
        detachAndScrapAttachedViews(recycler);
        LayoutState layoutState = this.mLayoutState;
        layoutState.mRecycle = false;
        int totalSpace = this.mSecondaryOrientation.getTotalSpace();
        this.mSizePerSpan = totalSpace / this.mSpanCount;
        View.MeasureSpec.makeMeasureSpec(totalSpace, this.mSecondaryOrientation.getMode());
        updateLayoutState(anchorInfo.mPosition, state);
        if (anchorInfo.mLayoutFromEnd) {
            setLayoutStateDirection(-1);
            fill(recycler, layoutState, state);
            setLayoutStateDirection(1);
            layoutState.mCurrentPosition = anchorInfo.mPosition + layoutState.mItemDirection;
            fill(recycler, layoutState, state);
        } else {
            setLayoutStateDirection(1);
            fill(recycler, layoutState, state);
            setLayoutStateDirection(-1);
            layoutState.mCurrentPosition = anchorInfo.mPosition + layoutState.mItemDirection;
            fill(recycler, layoutState, state);
        }
        if (this.mSecondaryOrientation.getMode() != 1073741824) {
            int childCount3 = getChildCount();
            float fMax = 0.0f;
            for (int i15 = 0; i15 < childCount3; i15++) {
                View childAt = getChildAt(i15);
                float decoratedMeasurement = this.mSecondaryOrientation.getDecoratedMeasurement(childAt);
                if (decoratedMeasurement >= fMax) {
                    ((LayoutParams) childAt.getLayoutParams()).getClass();
                    fMax = Math.max(fMax, decoratedMeasurement);
                }
            }
            int i16 = this.mSizePerSpan;
            int iRound = Math.round(fMax * this.mSpanCount);
            if (this.mSecondaryOrientation.getMode() == Integer.MIN_VALUE) {
                iRound = Math.min(iRound, this.mSecondaryOrientation.getTotalSpace());
            }
            this.mSizePerSpan = iRound / this.mSpanCount;
            View.MeasureSpec.makeMeasureSpec(iRound, this.mSecondaryOrientation.getMode());
            if (this.mSizePerSpan != i16) {
                for (int i17 = 0; i17 < childCount3; i17++) {
                    View childAt2 = getChildAt(i17);
                    LayoutParams layoutParams = (LayoutParams) childAt2.getLayoutParams();
                    layoutParams.getClass();
                    if (isLayoutRTL() && this.mOrientation == 1) {
                        int i18 = -((this.mSpanCount - 1) - layoutParams.mSpan.mIndex);
                        childAt2.offsetLeftAndRight((this.mSizePerSpan * i18) - (i18 * i16));
                    } else {
                        int i19 = layoutParams.mSpan.mIndex;
                        int i20 = this.mSizePerSpan * i19;
                        int i21 = i19 * i16;
                        if (this.mOrientation == 1) {
                            childAt2.offsetLeftAndRight(i20 - i21);
                        } else {
                            childAt2.offsetTopAndBottom(i20 - i21);
                        }
                    }
                }
            }
        }
        if (getChildCount() > 0) {
            if (this.mShouldReverseLayout) {
                fixEndGap(recycler, state, true);
                fixStartGap(recycler, state, false);
            } else {
                fixStartGap(recycler, state, true);
                fixEndGap(recycler, state, false);
            }
        }
        if (!z || state.mInPreLayout || this.mGapStrategy == 0 || getChildCount() <= 0 || hasGapsToFix() == null) {
            z3 = false;
        } else {
            AnonymousClass1 anonymousClass1 = this.mCheckForGapsRunnable;
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.removeCallbacks(anonymousClass1);
            }
            if (!checkForGaps()) {
            }
        }
        if (state.mInPreLayout) {
            anchorInfo.reset();
        }
        this.mLastLayoutFromEnd = anchorInfo.mLayoutFromEnd;
        this.mLastLayoutRTL = isLayoutRTL();
        if (z3) {
            anchorInfo.reset();
            onLayoutChildren(recycler, state, false);
        }
    }

    public class LazySpanLookup {
        public int[] mData;
        public List mFullSpanItems;

        public final void clear() {
            int[] iArr = this.mData;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            this.mFullSpanItems = null;
        }

        public final void ensureSize(int i) {
            int[] iArr = this.mData;
            if (iArr == null) {
                int[] iArr2 = new int[Math.max(i, 10) + 1];
                this.mData = iArr2;
                Arrays.fill(iArr2, -1);
            } else if (i >= iArr.length) {
                int length = iArr.length;
                while (length <= i) {
                    length *= 2;
                }
                int[] iArr3 = new int[length];
                this.mData = iArr3;
                System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
                int[] iArr4 = this.mData;
                Arrays.fill(iArr4, iArr.length, iArr4.length, -1);
            }
        }

        public final void offsetForAddition(int i, int i2) {
            int[] iArr = this.mData;
            if (iArr == null || i >= iArr.length) {
                return;
            }
            int i3 = i + i2;
            ensureSize(i3);
            int[] iArr2 = this.mData;
            System.arraycopy(iArr2, i, iArr2, i3, (iArr2.length - i) - i2);
            Arrays.fill(this.mData, i, i3, -1);
            List list = this.mFullSpanItems;
            if (list == null) {
                return;
            }
            for (int size = list.size() - 1; size >= 0; size--) {
                FullSpanItem fullSpanItem = (FullSpanItem) this.mFullSpanItems.get(size);
                int i4 = fullSpanItem.mPosition;
                if (i4 >= i) {
                    fullSpanItem.mPosition = i4 + i2;
                }
            }
        }

        public final void offsetForRemoval(int i, int i2) {
            int[] iArr = this.mData;
            if (iArr == null || i >= iArr.length) {
                return;
            }
            int i3 = i + i2;
            ensureSize(i3);
            int[] iArr2 = this.mData;
            System.arraycopy(iArr2, i3, iArr2, i, (iArr2.length - i) - i2);
            int[] iArr3 = this.mData;
            Arrays.fill(iArr3, iArr3.length - i2, iArr3.length, -1);
            List list = this.mFullSpanItems;
            if (list == null) {
                return;
            }
            for (int size = list.size() - 1; size >= 0; size--) {
                FullSpanItem fullSpanItem = (FullSpanItem) this.mFullSpanItems.get(size);
                int i4 = fullSpanItem.mPosition;
                if (i4 >= i) {
                    if (i4 < i3) {
                        this.mFullSpanItems.remove(size);
                    } else {
                        fullSpanItem.mPosition = i4 - i2;
                    }
                }
            }
        }

        public class FullSpanItem implements Parcelable {
            public static final Parcelable.Creator<FullSpanItem> CREATOR = new Parcelable.Creator() { // from class: androidx.recyclerview.widget.StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem.1
                @Override // android.os.Parcelable.Creator
                public final Object createFromParcel(Parcel parcel) {
                    return new FullSpanItem(parcel);
                }

                @Override // android.os.Parcelable.Creator
                public final Object[] newArray(int i) {
                    return new FullSpanItem[i];
                }
            };
            public final int mGapDir;
            public final int[] mGapPerSpan;
            public final boolean mHasUnwantedGapAfter;
            public int mPosition;

            public FullSpanItem(Parcel parcel) {
                this.mPosition = parcel.readInt();
                this.mGapDir = parcel.readInt();
                this.mHasUnwantedGapAfter = parcel.readInt() == 1;
                int i = parcel.readInt();
                if (i > 0) {
                    int[] iArr = new int[i];
                    this.mGapPerSpan = iArr;
                    parcel.readIntArray(iArr);
                }
            }

            @Override // android.os.Parcelable
            public final int describeContents() {
                return 0;
            }

            public final String toString() {
                return "FullSpanItem{mPosition=" + this.mPosition + ", mGapDir=" + this.mGapDir + ", mHasUnwantedGapAfter=" + this.mHasUnwantedGapAfter + ", mGapPerSpan=" + Arrays.toString(this.mGapPerSpan) + '}';
            }

            @Override // android.os.Parcelable
            public final void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.mPosition);
                parcel.writeInt(this.mGapDir);
                parcel.writeInt(this.mHasUnwantedGapAfter ? 1 : 0);
                int[] iArr = this.mGapPerSpan;
                if (iArr == null || iArr.length <= 0) {
                    parcel.writeInt(0);
                } else {
                    parcel.writeInt(iArr.length);
                    parcel.writeIntArray(this.mGapPerSpan);
                }
            }

            public FullSpanItem() {
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [androidx.recyclerview.widget.StaggeredGridLayoutManager$1] */
    public StaggeredGridLayoutManager(int i, int i2) {
        this.mSpanCount = -1;
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mLazySpanLookup = new LazySpanLookup();
        this.mGapStrategy = 2;
        this.mTmpRect = new Rect();
        this.mAnchorInfo = new AnchorInfo();
        this.mSmoothScrollbarEnabled = true;
        this.mCheckForGapsRunnable = new Runnable() { // from class: androidx.recyclerview.widget.StaggeredGridLayoutManager.1
            @Override // java.lang.Runnable
            public final void run() {
                StaggeredGridLayoutManager.this.checkForGaps();
            }
        };
        this.mOrientation = i2;
        setSpanCount(i);
        this.mLayoutState = new LayoutState();
        this.mPrimaryOrientation = OrientationHelper.createOrientationHelper(this, this.mOrientation);
        this.mSecondaryOrientation = OrientationHelper.createOrientationHelper(this, 1 - this.mOrientation);
    }
}
