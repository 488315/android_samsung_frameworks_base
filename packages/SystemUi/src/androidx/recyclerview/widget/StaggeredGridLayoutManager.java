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
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class StaggeredGridLayoutManager extends RecyclerView.LayoutManager implements RecyclerView.SmoothScroller.ScrollVectorProvider {
    public final AnchorInfo mAnchorInfo;
    public final RunnableC04841 mCheckForGapsRunnable;
    public final int mGapStrategy;
    public boolean mLastLayoutFromEnd;
    public boolean mLastLayoutRTL;
    public final LayoutState mLayoutState;
    public final LazySpanLookup mLazySpanLookup;
    public int mOrientation;
    public SavedState mPendingSavedState;
    public int mPendingScrollPosition;
    public int mPendingScrollPositionOffset;
    public int[] mPrefetchDistances;
    public OrientationHelper mPrimaryOrientation;
    public BitSet mRemainingSpans;
    public boolean mReverseLayout;
    public OrientationHelper mSecondaryOrientation;
    public boolean mShouldReverseLayout;
    public int mSizePerSpan;
    public final boolean mSmoothScrollbarEnabled;
    public int mSpanCount;
    public Span[] mSpans;
    public final Rect mTmpRect;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AnchorInfo {
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
            this.mOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.mLayoutFromEnd = false;
            this.mInvalidateOffsets = false;
            this.mValid = false;
            int[] iArr = this.mSpanReferenceLines;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class LayoutParams extends RecyclerView.LayoutParams {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState implements Parcelable {
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
            int readInt = parcel.readInt();
            this.mSpanOffsetsSize = readInt;
            if (readInt > 0) {
                int[] iArr = new int[readInt];
                this.mSpanOffsets = iArr;
                parcel.readIntArray(iArr);
            }
            int readInt2 = parcel.readInt();
            this.mSpanLookupSize = readInt2;
            if (readInt2 > 0) {
                int[] iArr2 = new int[readInt2];
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Span {
        public final int mIndex;
        public final ArrayList mViews = new ArrayList();
        public int mCachedStart = VideoPlayer.MEDIA_ERROR_SYSTEM;
        public int mCachedEnd = VideoPlayer.MEDIA_ERROR_SYSTEM;
        public int mDeletedSize = 0;

        public Span(int i) {
            this.mIndex = i;
        }

        public static LayoutParams getLayoutParams(View view) {
            return (LayoutParams) view.getLayoutParams();
        }

        public final void calculateCachedEnd() {
            View view = (View) this.mViews.get(r0.size() - 1);
            LayoutParams layoutParams = getLayoutParams(view);
            this.mCachedEnd = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(view);
            layoutParams.getClass();
        }

        public final void clear() {
            this.mViews.clear();
            this.mCachedStart = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.mCachedEnd = VideoPlayer.MEDIA_ERROR_SYSTEM;
            this.mDeletedSize = 0;
        }

        public final int findFirstPartiallyVisibleItemPosition() {
            boolean z = StaggeredGridLayoutManager.this.mReverseLayout;
            ArrayList arrayList = this.mViews;
            return z ? findOnePartiallyOrCompletelyVisibleChild(arrayList.size() - 1, -1, false, true) : findOnePartiallyOrCompletelyVisibleChild(0, arrayList.size(), false, true);
        }

        public final int findLastPartiallyVisibleItemPosition() {
            boolean z = StaggeredGridLayoutManager.this.mReverseLayout;
            ArrayList arrayList = this.mViews;
            return z ? findOnePartiallyOrCompletelyVisibleChild(0, arrayList.size(), false, true) : findOnePartiallyOrCompletelyVisibleChild(arrayList.size() - 1, -1, false, true);
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
            ArrayList arrayList = this.mViews;
            StaggeredGridLayoutManager staggeredGridLayoutManager = StaggeredGridLayoutManager.this;
            View view = null;
            if (i2 != -1) {
                int size = arrayList.size() - 1;
                while (size >= 0) {
                    View view2 = (View) arrayList.get(size);
                    if ((staggeredGridLayoutManager.mReverseLayout && RecyclerView.LayoutManager.getPosition(view2) >= i) || ((!staggeredGridLayoutManager.mReverseLayout && RecyclerView.LayoutManager.getPosition(view2) <= i) || !view2.hasFocusable())) {
                        break;
                    }
                    size--;
                    view = view2;
                }
            } else {
                int size2 = arrayList.size();
                int i3 = 0;
                while (i3 < size2) {
                    View view3 = (View) arrayList.get(i3);
                    if ((staggeredGridLayoutManager.mReverseLayout && RecyclerView.LayoutManager.getPosition(view3) <= i) || ((!staggeredGridLayoutManager.mReverseLayout && RecyclerView.LayoutManager.getPosition(view3) >= i) || !view3.hasFocusable())) {
                        break;
                    }
                    i3++;
                    view = view3;
                }
            }
            return view;
        }

        public final int getStartLine(int i) {
            int i2 = this.mCachedStart;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            ArrayList arrayList = this.mViews;
            if (arrayList.size() == 0) {
                return i;
            }
            View view = (View) arrayList.get(0);
            LayoutParams layoutParams = getLayoutParams(view);
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
        this.mPendingScrollPositionOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
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
        if (i2 == 0 && i3 == 0) {
            return i;
        }
        int mode = View.MeasureSpec.getMode(i);
        return (mode == Integer.MIN_VALUE || mode == 1073741824) ? View.MeasureSpec.makeMeasureSpec(Math.max(0, (View.MeasureSpec.getSize(i) - i2) - i3), mode) : i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void assertNotInLayoutOrScroll(String str) {
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll(str);
        }
    }

    public final int calculateScrollDirectionForPosition(int i) {
        if (getChildCount() == 0) {
            return this.mShouldReverseLayout ? 1 : -1;
        }
        return (i < getFirstChildPosition()) != this.mShouldReverseLayout ? -1 : 1;
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
            if (firstChildPosition == 0 && hasGapsToFix() != null) {
                this.mLazySpanLookup.clear();
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
        int i3;
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
        int i4 = 0;
        int i5 = 0;
        while (true) {
            int i6 = this.mSpanCount;
            layoutState = this.mLayoutState;
            if (i4 >= i6) {
                break;
            }
            if (layoutState.mItemDirection == -1) {
                endLine = layoutState.mStartLine;
                i3 = this.mSpans[i4].getStartLine(endLine);
            } else {
                endLine = this.mSpans[i4].getEndLine(layoutState.mEndLine);
                i3 = layoutState.mEndLine;
            }
            int i7 = endLine - i3;
            if (i7 >= 0) {
                this.mPrefetchDistances[i5] = i7;
                i5++;
            }
            i4++;
        }
        Arrays.sort(this.mPrefetchDistances, 0, i5);
        for (int i8 = 0; i8 < i5; i8++) {
            int i9 = layoutState.mCurrentPosition;
            if (!(i9 >= 0 && i9 < state.getItemCount())) {
                return;
            }
            layoutPrefetchRegistryImpl.addPosition(layoutState.mCurrentPosition, this.mPrefetchDistances[i8]);
            layoutState.mCurrentPosition += layoutState.mItemDirection;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollExtent(RecyclerView.State state) {
        return computeScrollExtent(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollRange(RecyclerView.State state) {
        return computeScrollRange(state);
    }

    public final int computeScrollExtent(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        OrientationHelper orientationHelper = this.mPrimaryOrientation;
        boolean z = this.mSmoothScrollbarEnabled;
        return ScrollbarHelper.computeScrollExtent(state, orientationHelper, findFirstVisibleItemClosestToStart(!z), findFirstVisibleItemClosestToEnd(!z), this, this.mSmoothScrollbarEnabled);
    }

    public final int computeScrollOffset(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        OrientationHelper orientationHelper = this.mPrimaryOrientation;
        boolean z = this.mSmoothScrollbarEnabled;
        return ScrollbarHelper.computeScrollOffset(state, orientationHelper, findFirstVisibleItemClosestToStart(!z), findFirstVisibleItemClosestToEnd(!z), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    public final int computeScrollRange(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        OrientationHelper orientationHelper = this.mPrimaryOrientation;
        boolean z = this.mSmoothScrollbarEnabled;
        return ScrollbarHelper.computeScrollRange(state, orientationHelper, findFirstVisibleItemClosestToStart(!z), findFirstVisibleItemClosestToEnd(!z), this, this.mSmoothScrollbarEnabled);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
    public final PointF computeScrollVectorForPosition(int i) {
        int calculateScrollDirectionForPosition = calculateScrollDirectionForPosition(i);
        PointF pointF = new PointF();
        if (calculateScrollDirectionForPosition == 0) {
            return null;
        }
        if (this.mOrientation == 0) {
            pointF.x = calculateScrollDirectionForPosition;
            pointF.y = 0.0f;
        } else {
            pointF.x = 0.0f;
            pointF.y = calculateScrollDirectionForPosition;
        }
        return pointF;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollExtent(RecyclerView.State state) {
        return computeScrollExtent(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollRange(RecyclerView.State state) {
        return computeScrollRange(state);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v19 */
    public final int fill(RecyclerView.Recycler recycler, LayoutState layoutState, RecyclerView.State state) {
        Span span;
        ?? r8;
        int startLine;
        int decoratedMeasurement;
        int startAfterPadding;
        int decoratedMeasurement2;
        int i;
        int i2;
        int i3;
        int i4;
        int i5 = 0;
        int i6 = 1;
        this.mRemainingSpans.set(0, this.mSpanCount, true);
        LayoutState layoutState2 = this.mLayoutState;
        int i7 = layoutState2.mInfinite ? layoutState.mLayoutDirection == 1 ? Integer.MAX_VALUE : VideoPlayer.MEDIA_ERROR_SYSTEM : layoutState.mLayoutDirection == 1 ? layoutState.mEndLine + layoutState.mAvailable : layoutState.mStartLine - layoutState.mAvailable;
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
            int viewLayoutPosition = layoutParams.getViewLayoutPosition();
            LazySpanLookup lazySpanLookup = this.mLazySpanLookup;
            int[] iArr = lazySpanLookup.mData;
            int i11 = (iArr == null || viewLayoutPosition >= iArr.length) ? -1 : iArr[viewLayoutPosition];
            if ((i11 == -1 ? i6 : i5) != 0) {
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
                    int i13 = VideoPlayer.MEDIA_ERROR_SYSTEM;
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
                lazySpanLookup.ensureSize(viewLayoutPosition);
                lazySpanLookup.mData[viewLayoutPosition] = span.mIndex;
            } else {
                span = this.mSpans[i11];
            }
            layoutParams.mSpan = span;
            if (layoutState.mLayoutDirection == 1) {
                r8 = 0;
                addViewInt(viewForPosition, -1, false);
            } else {
                r8 = 0;
                addViewInt(viewForPosition, 0, false);
            }
            if (this.mOrientation == 1) {
                measureChildWithDecorationsAndMargin(RecyclerView.LayoutManager.getChildMeasureSpec(r8, this.mSizePerSpan, this.mWidthMode, r8, ((ViewGroup.MarginLayoutParams) layoutParams).width), RecyclerView.LayoutManager.getChildMeasureSpec(true, this.mHeight, this.mHeightMode, getPaddingBottom() + getPaddingTop(), ((ViewGroup.MarginLayoutParams) layoutParams).height), viewForPosition, r8);
            } else {
                measureChildWithDecorationsAndMargin(RecyclerView.LayoutManager.getChildMeasureSpec(true, this.mWidth, this.mWidthMode, getPaddingRight() + getPaddingLeft(), ((ViewGroup.MarginLayoutParams) layoutParams).width), RecyclerView.LayoutManager.getChildMeasureSpec(false, this.mSizePerSpan, this.mHeightMode, 0, ((ViewGroup.MarginLayoutParams) layoutParams).height), viewForPosition, false);
            }
            if (layoutState.mLayoutDirection == 1) {
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
                ArrayList arrayList = span5.mViews;
                arrayList.add(viewForPosition);
                span5.mCachedEnd = VideoPlayer.MEDIA_ERROR_SYSTEM;
                if (arrayList.size() == 1) {
                    span5.mCachedStart = VideoPlayer.MEDIA_ERROR_SYSTEM;
                }
                if (layoutParams2.isItemRemoved() || layoutParams2.isItemChanged()) {
                    span5.mDeletedSize = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(viewForPosition) + span5.mDeletedSize;
                }
            } else {
                Span span6 = layoutParams.mSpan;
                span6.getClass();
                LayoutParams layoutParams3 = (LayoutParams) viewForPosition.getLayoutParams();
                layoutParams3.mSpan = span6;
                ArrayList arrayList2 = span6.mViews;
                arrayList2.add(0, viewForPosition);
                span6.mCachedStart = VideoPlayer.MEDIA_ERROR_SYSTEM;
                if (arrayList2.size() == 1) {
                    span6.mCachedEnd = VideoPlayer.MEDIA_ERROR_SYSTEM;
                }
                if (layoutParams3.isItemRemoved() || layoutParams3.isItemChanged()) {
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
                i = 0;
                this.mRemainingSpans.set(span.mIndex, false);
            } else {
                i = 0;
            }
            i5 = i;
            i6 = 1;
            z = true;
        }
        int i14 = i5;
        if (!z) {
            recycle(recycler, layoutState2);
        }
        int startAfterPadding3 = layoutState2.mLayoutDirection == -1 ? this.mPrimaryOrientation.getStartAfterPadding() - getMinStart(this.mPrimaryOrientation.getStartAfterPadding()) : getMaxEnd(this.mPrimaryOrientation.getEndAfterPadding()) - this.mPrimaryOrientation.getEndAfterPadding();
        return startAfterPadding3 > 0 ? Math.min(layoutState.mAvailable, startAfterPadding3) : i14;
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
            iArr[i] = StaggeredGridLayoutManager.this.mReverseLayout ? span.findOnePartiallyOrCompletelyVisibleChild(r5.size() - 1, -1, true, false) : span.findOnePartiallyOrCompletelyVisibleChild(0, span.mViews.size(), true, false);
        }
        return iArr;
    }

    public final void fixEndGap(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int endAfterPadding;
        int maxEnd = getMaxEnd(VideoPlayer.MEDIA_ERROR_SYSTEM);
        if (maxEnd != Integer.MIN_VALUE && (endAfterPadding = this.mPrimaryOrientation.getEndAfterPadding() - maxEnd) > 0) {
            int i = endAfterPadding - (-scrollBy(-endAfterPadding, recycler, state));
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
            int scrollBy = startAfterPadding - scrollBy(startAfterPadding, recycler, state);
            if (!z || scrollBy <= 0) {
                return;
            }
            this.mPrimaryOrientation.offsetChildren(-scrollBy);
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleUpdate(int i, int i2, int i3) {
        int i4;
        int i5;
        int lastChildPosition = this.mShouldReverseLayout ? getLastChildPosition() : getFirstChildPosition();
        if (i3 != 8) {
            i4 = i + i2;
        } else {
            if (i >= i2) {
                i4 = i + 1;
                i5 = i2;
                LazySpanLookup lazySpanLookup = this.mLazySpanLookup;
                lazySpanLookup.invalidateAfter(i5);
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
        LazySpanLookup lazySpanLookup2 = this.mLazySpanLookup;
        lazySpanLookup2.invalidateAfter(i5);
        if (i3 != 1) {
        }
        if (i4 > lastChildPosition) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d1, code lost:
    
        if (r10 == r11) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00e7, code lost:
    
        r10 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00e5, code lost:
    
        r10 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00e3, code lost:
    
        if (r10 == r11) goto L51;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View hasGapsToFix() {
        int i;
        boolean z;
        boolean z2;
        int childCount = getChildCount() - 1;
        BitSet bitSet = new BitSet(this.mSpanCount);
        bitSet.set(0, this.mSpanCount, true);
        char c = (this.mOrientation == 1 && isLayoutRTL()) ? (char) 1 : (char) 65535;
        if (this.mShouldReverseLayout) {
            i = -1;
        } else {
            i = childCount + 1;
            childCount = 0;
        }
        int i2 = childCount < i ? 1 : -1;
        while (childCount != i) {
            View childAt = getChildAt(childCount);
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
                        ArrayList arrayList = span.mViews;
                        Span.getLayoutParams((View) arrayList.get(arrayList.size() - 1)).getClass();
                        z2 = true;
                    }
                    z2 = false;
                } else {
                    int i4 = span.mCachedStart;
                    if (i4 == Integer.MIN_VALUE) {
                        View view = (View) span.mViews.get(0);
                        LayoutParams layoutParams2 = Span.getLayoutParams(view);
                        span.mCachedStart = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(view);
                        layoutParams2.getClass();
                        i4 = span.mCachedStart;
                    }
                    if (i4 > this.mPrimaryOrientation.getStartAfterPadding()) {
                        Span.getLayoutParams((View) span.mViews.get(0)).getClass();
                        z2 = true;
                    }
                    z2 = false;
                }
                if (z2) {
                    return childAt;
                }
                bitSet.clear(layoutParams.mSpan.mIndex);
            }
            childCount += i2;
            if (childCount != i) {
                View childAt2 = getChildAt(childCount);
                if (this.mShouldReverseLayout) {
                    int decoratedEnd = this.mPrimaryOrientation.getDecoratedEnd(childAt);
                    int decoratedEnd2 = this.mPrimaryOrientation.getDecoratedEnd(childAt2);
                    if (decoratedEnd < decoratedEnd2) {
                        return childAt;
                    }
                } else {
                    int decoratedStart = this.mPrimaryOrientation.getDecoratedStart(childAt);
                    int decoratedStart2 = this.mPrimaryOrientation.getDecoratedStart(childAt2);
                    if (decoratedStart > decoratedStart2) {
                        return childAt;
                    }
                }
                if (z) {
                    if ((layoutParams.mSpan.mIndex - ((LayoutParams) childAt2.getLayoutParams()).mSpan.mIndex < 0) != (c < 0)) {
                        return childAt;
                    }
                } else {
                    continue;
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

    public final void measureChildWithDecorationsAndMargin(int i, int i2, View view, boolean z) {
        Rect rect = this.mTmpRect;
        calculateItemDecorationsForChild(rect, view);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int updateSpecWithExtra = updateSpecWithExtra(i, ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + rect.left, ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + rect.right);
        int updateSpecWithExtra2 = updateSpecWithExtra(i2, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + rect.top, ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + rect.bottom);
        if (shouldMeasureChild(view, updateSpecWithExtra, updateSpecWithExtra2, layoutParams)) {
            view.measure(updateSpecWithExtra, updateSpecWithExtra2);
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
        RecyclerView recyclerView2 = this.mRecyclerView;
        if (recyclerView2 != null) {
            recyclerView2.removeCallbacks(this.mCheckForGapsRunnable);
        }
        for (int i = 0; i < this.mSpanCount; i++) {
            this.mSpans[i].clear();
        }
        recyclerView.requestLayout();
    }

    /* JADX WARN: Code restructure failed: missing block: B:110:0x0038, code lost:
    
        if (r8.mOrientation == 1) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x003d, code lost:
    
        if (r8.mOrientation == 0) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x004b, code lost:
    
        if (isLayoutRTL() == false) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0057, code lost:
    
        if (isLayoutRTL() == false) goto L46;
     */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View onFocusSearchFailed(View view, int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        View findContainingItemView;
        int i2;
        if (getChildCount() == 0 || (findContainingItemView = findContainingItemView(view)) == null) {
            return null;
        }
        resolveShouldLayoutReverse();
        if (i == 1) {
            if (this.mOrientation != 1) {
            }
            i2 = -1;
        } else if (i == 2) {
            if (this.mOrientation != 1) {
            }
            i2 = 1;
        } else if (i != 17) {
            if (i != 33) {
                if (i == 66) {
                }
            }
            i2 = Integer.MIN_VALUE;
        }
        if (i2 == Integer.MIN_VALUE) {
            return null;
        }
        LayoutParams layoutParams = (LayoutParams) findContainingItemView.getLayoutParams();
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
        if (focusableViewAfter != null && focusableViewAfter != findContainingItemView) {
            return focusableViewAfter;
        }
        if (preferLastSpan(i2)) {
            for (int i3 = this.mSpanCount - 1; i3 >= 0; i3--) {
                View focusableViewAfter2 = this.mSpans[i3].getFocusableViewAfter(lastChildPosition, i2);
                if (focusableViewAfter2 != null && focusableViewAfter2 != findContainingItemView) {
                    return focusableViewAfter2;
                }
            }
        } else {
            for (int i4 = 0; i4 < this.mSpanCount; i4++) {
                View focusableViewAfter3 = this.mSpans[i4].getFocusableViewAfter(lastChildPosition, i2);
                if (focusableViewAfter3 != null && focusableViewAfter3 != findContainingItemView) {
                    return focusableViewAfter3;
                }
            }
        }
        boolean z = (this.mReverseLayout ^ true) == (i2 == -1);
        View findViewByPosition = findViewByPosition(z ? span.findFirstPartiallyVisibleItemPosition() : span.findLastPartiallyVisibleItemPosition());
        if (findViewByPosition != null && findViewByPosition != findContainingItemView) {
            return findViewByPosition;
        }
        if (preferLastSpan(i2)) {
            for (int i5 = this.mSpanCount - 1; i5 >= 0; i5--) {
                if (i5 != span.mIndex) {
                    View findViewByPosition2 = findViewByPosition(z ? this.mSpans[i5].findFirstPartiallyVisibleItemPosition() : this.mSpans[i5].findLastPartiallyVisibleItemPosition());
                    if (findViewByPosition2 != null && findViewByPosition2 != findContainingItemView) {
                        return findViewByPosition2;
                    }
                }
            }
        } else {
            for (int i6 = 0; i6 < this.mSpanCount; i6++) {
                View findViewByPosition3 = findViewByPosition(z ? this.mSpans[i6].findFirstPartiallyVisibleItemPosition() : this.mSpans[i6].findLastPartiallyVisibleItemPosition());
                if (findViewByPosition3 != null && findViewByPosition3 != findContainingItemView) {
                    return findViewByPosition3;
                }
            }
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            View findFirstVisibleItemClosestToStart = findFirstVisibleItemClosestToStart(false);
            View findFirstVisibleItemClosestToEnd = findFirstVisibleItemClosestToEnd(false);
            if (findFirstVisibleItemClosestToStart == null || findFirstVisibleItemClosestToEnd == null) {
                return;
            }
            int position = RecyclerView.LayoutManager.getPosition(findFirstVisibleItemClosestToStart);
            int position2 = RecyclerView.LayoutManager.getPosition(findFirstVisibleItemClosestToEnd);
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
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, span != null ? span.mIndex : -1, 1, -1, -1, false));
        } else {
            Span span2 = layoutParams2.mSpan;
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, -1, -1, span2 != null ? span2.mIndex : -1, 1, false));
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
        this.mPendingScrollPositionOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
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
        if (getChildCount() > 0) {
            savedState.mAnchorPosition = this.mLastLayoutFromEnd ? getLastChildPosition() : getFirstChildPosition();
            View findFirstVisibleItemClosestToEnd = this.mShouldReverseLayout ? findFirstVisibleItemClosestToEnd(true) : findFirstVisibleItemClosestToStart(true);
            savedState.mVisibleAnchorPosition = findFirstVisibleItemClosestToEnd != null ? RecyclerView.LayoutManager.getPosition(findFirstVisibleItemClosestToEnd) : -1;
            int i = this.mSpanCount;
            savedState.mSpanOffsetsSize = i;
            savedState.mSpanOffsets = new int[i];
            for (int i2 = 0; i2 < this.mSpanCount; i2++) {
                if (this.mLastLayoutFromEnd) {
                    startLine = this.mSpans[i2].getEndLine(VideoPlayer.MEDIA_ERROR_SYSTEM);
                    if (startLine != Integer.MIN_VALUE) {
                        startAfterPadding = this.mPrimaryOrientation.getEndAfterPadding();
                        startLine -= startAfterPadding;
                        savedState.mSpanOffsets[i2] = startLine;
                    } else {
                        savedState.mSpanOffsets[i2] = startLine;
                    }
                } else {
                    startLine = this.mSpans[i2].getStartLine(VideoPlayer.MEDIA_ERROR_SYSTEM);
                    if (startLine != Integer.MIN_VALUE) {
                        startAfterPadding = this.mPrimaryOrientation.getStartAfterPadding();
                        startLine -= startAfterPadding;
                        savedState.mSpanOffsets[i2] = startLine;
                    } else {
                        savedState.mSpanOffsets[i2] = startLine;
                    }
                }
            }
        } else {
            savedState.mAnchorPosition = -1;
            savedState.mVisibleAnchorPosition = -1;
            savedState.mSpanOffsetsSize = 0;
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
                recycleFromEnd(layoutState.mEndLine, recycler);
                return;
            } else {
                recycleFromStart(layoutState.mStartLine, recycler);
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
            recycleFromEnd(i3 < 0 ? layoutState.mEndLine : layoutState.mEndLine - Math.min(i3, layoutState.mAvailable), recycler);
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
        recycleFromStart(i5 < 0 ? layoutState.mStartLine : Math.min(i5, layoutState.mAvailable) + layoutState.mStartLine, recycler);
    }

    public final void recycleFromEnd(int i, RecyclerView.Recycler recycler) {
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
            ArrayList arrayList = span.mViews;
            int size = arrayList.size();
            View view = (View) arrayList.remove(size - 1);
            LayoutParams layoutParams2 = Span.getLayoutParams(view);
            layoutParams2.mSpan = null;
            if (layoutParams2.isItemRemoved() || layoutParams2.isItemChanged()) {
                span.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
            }
            if (size == 1) {
                span.mCachedStart = VideoPlayer.MEDIA_ERROR_SYSTEM;
            }
            span.mCachedEnd = VideoPlayer.MEDIA_ERROR_SYSTEM;
            removeAndRecycleView(childAt, recycler);
        }
    }

    public final void recycleFromStart(int i, RecyclerView.Recycler recycler) {
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
            ArrayList arrayList = span.mViews;
            View view = (View) arrayList.remove(0);
            LayoutParams layoutParams2 = Span.getLayoutParams(view);
            layoutParams2.mSpan = null;
            if (arrayList.size() == 0) {
                span.mCachedEnd = VideoPlayer.MEDIA_ERROR_SYSTEM;
            }
            if (layoutParams2.isItemRemoved() || layoutParams2.isItemChanged()) {
                span.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
            }
            span.mCachedStart = VideoPlayer.MEDIA_ERROR_SYSTEM;
            removeAndRecycleView(childAt, recycler);
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
        prepareLayoutStateForDelta(i, state);
        LayoutState layoutState = this.mLayoutState;
        int fill = fill(recycler, layoutState, state);
        if (layoutState.mAvailable >= fill) {
            i = i < 0 ? -fill : fill;
        }
        this.mPrimaryOrientation.offsetChildren(-i);
        this.mLastLayoutFromEnd = this.mShouldReverseLayout;
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.showGoToTop();
        }
        layoutState.mAvailable = 0;
        recycle(recycler, layoutState);
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return scrollBy(i, recycler, state);
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
        this.mPendingScrollPositionOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.showGoToTop();
        }
        requestLayout();
    }

    public final void scrollToPositionWithOffset(int i, boolean z) {
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            savedState.mSpanOffsets = null;
            savedState.mSpanOffsetsSize = 0;
            savedState.mAnchorPosition = -1;
            savedState.mVisibleAnchorPosition = -1;
        }
        this.mPendingScrollPosition = i;
        this.mPendingScrollPositionOffset = 0;
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.showGoToTop();
        }
        if (z) {
            this.mLazySpanLookup.clear();
        }
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return scrollBy(i, recycler, state);
    }

    public final void setLayoutStateDirection(int i) {
        LayoutState layoutState = this.mLayoutState;
        layoutState.mLayoutDirection = i;
        layoutState.mItemDirection = this.mShouldReverseLayout != (i == -1) ? -1 : 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void setMeasuredDimension(int i, int i2, Rect rect) {
        int chooseSize;
        int chooseSize2;
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        if (this.mOrientation == 1) {
            int height = rect.height() + paddingBottom;
            RecyclerView recyclerView = this.mRecyclerView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            chooseSize2 = RecyclerView.LayoutManager.chooseSize(i2, height, ViewCompat.Api16Impl.getMinimumHeight(recyclerView));
            chooseSize = RecyclerView.LayoutManager.chooseSize(i, (this.mSizePerSpan * this.mSpanCount) + paddingRight, ViewCompat.Api16Impl.getMinimumWidth(this.mRecyclerView));
        } else {
            int width = rect.width() + paddingRight;
            RecyclerView recyclerView2 = this.mRecyclerView;
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            chooseSize = RecyclerView.LayoutManager.chooseSize(i, width, ViewCompat.Api16Impl.getMinimumWidth(recyclerView2));
            chooseSize2 = RecyclerView.LayoutManager.chooseSize(i2, (this.mSizePerSpan * this.mSpanCount) + paddingBottom, ViewCompat.Api16Impl.getMinimumHeight(this.mRecyclerView));
        }
        this.mRecyclerView.setMeasuredDimension(chooseSize, chooseSize2);
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
            recyclerView.showGoToTop();
            linearSmoothScroller.mTargetPosition = i;
            startSmoothScroll(linearSmoothScroller);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null;
    }

    public final void updateLayoutState(int i, RecyclerView.State state) {
        int i2;
        int i3;
        int i4;
        LayoutState layoutState = this.mLayoutState;
        boolean z = false;
        layoutState.mAvailable = 0;
        layoutState.mCurrentPosition = i;
        RecyclerView.SmoothScroller smoothScroller = this.mSmoothScroller;
        if (!(smoothScroller != null && smoothScroller.mRunning) || (i4 = state.mTargetPosition) == -1) {
            i2 = 0;
            i3 = 0;
        } else {
            if (this.mShouldReverseLayout == (i4 < i)) {
                i2 = this.mPrimaryOrientation.getTotalSpace();
                i3 = 0;
            } else {
                i3 = this.mPrimaryOrientation.getTotalSpace();
                i2 = 0;
            }
        }
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null && recyclerView.mClipToPadding) {
            layoutState.mStartLine = this.mPrimaryOrientation.getStartAfterPadding() - i3;
            layoutState.mEndLine = this.mPrimaryOrientation.getEndAfterPadding() + i2;
        } else {
            layoutState.mEndLine = this.mPrimaryOrientation.getEnd() + i2;
            layoutState.mStartLine = -i3;
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
            LayoutParams layoutParams = Span.getLayoutParams(view);
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

    /* JADX WARN: Code restructure failed: missing block: B:269:0x043d, code lost:
    
        if (checkForGaps() != false) goto L262;
     */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        SavedState savedState;
        int[] iArr;
        int[] iArr2;
        boolean z2;
        int i;
        int i2;
        SavedState savedState2 = this.mPendingSavedState;
        AnchorInfo anchorInfo = this.mAnchorInfo;
        if (!(savedState2 == null && this.mPendingScrollPosition == -1) && state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            anchorInfo.reset();
            return;
        }
        boolean z3 = true;
        boolean z4 = (anchorInfo.mValid && this.mPendingScrollPosition == -1 && this.mPendingSavedState == null) ? false : true;
        StaggeredGridLayoutManager staggeredGridLayoutManager = StaggeredGridLayoutManager.this;
        LazySpanLookup lazySpanLookup = this.mLazySpanLookup;
        if (z4) {
            anchorInfo.reset();
            SavedState savedState3 = this.mPendingSavedState;
            if (savedState3 != null) {
                int i3 = savedState3.mSpanOffsetsSize;
                if (i3 > 0) {
                    if (i3 == this.mSpanCount) {
                        for (int i4 = 0; i4 < this.mSpanCount; i4++) {
                            this.mSpans[i4].clear();
                            SavedState savedState4 = this.mPendingSavedState;
                            int i5 = savedState4.mSpanOffsets[i4];
                            if (i5 != Integer.MIN_VALUE) {
                                i5 += savedState4.mAnchorLayoutFromEnd ? this.mPrimaryOrientation.getEndAfterPadding() : this.mPrimaryOrientation.getStartAfterPadding();
                            }
                            Span span = this.mSpans[i4];
                            span.mCachedStart = i5;
                            span.mCachedEnd = i5;
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
                resolveShouldLayoutReverse();
                SavedState savedState7 = this.mPendingSavedState;
                int i6 = savedState7.mAnchorPosition;
                if (i6 != -1) {
                    this.mPendingScrollPosition = i6;
                    anchorInfo.mLayoutFromEnd = savedState7.mAnchorLayoutFromEnd;
                } else {
                    anchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
                }
                if (savedState7.mSpanLookupSize > 1) {
                    lazySpanLookup.mData = savedState7.mSpanLookup;
                    lazySpanLookup.mFullSpanItems = savedState7.mFullSpanItems;
                }
            } else {
                resolveShouldLayoutReverse();
                anchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
            }
            if (!state.mInPreLayout && (i2 = this.mPendingScrollPosition) != -1) {
                if (i2 < 0 || i2 >= state.getItemCount()) {
                    this.mPendingScrollPosition = -1;
                    this.mPendingScrollPositionOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
                } else {
                    SavedState savedState8 = this.mPendingSavedState;
                    if (savedState8 == null || savedState8.mAnchorPosition == -1 || savedState8.mSpanOffsetsSize < 1) {
                        View findViewByPosition = findViewByPosition(this.mPendingScrollPosition);
                        if (findViewByPosition != null) {
                            anchorInfo.mPosition = this.mShouldReverseLayout ? getLastChildPosition() : getFirstChildPosition();
                            if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE) {
                                if (anchorInfo.mLayoutFromEnd) {
                                    anchorInfo.mOffset = (this.mPrimaryOrientation.getEndAfterPadding() - this.mPendingScrollPositionOffset) - this.mPrimaryOrientation.getDecoratedEnd(findViewByPosition);
                                } else {
                                    anchorInfo.mOffset = (this.mPrimaryOrientation.getStartAfterPadding() + this.mPendingScrollPositionOffset) - this.mPrimaryOrientation.getDecoratedStart(findViewByPosition);
                                }
                            } else if (this.mPrimaryOrientation.getDecoratedMeasurement(findViewByPosition) > this.mPrimaryOrientation.getTotalSpace()) {
                                anchorInfo.mOffset = anchorInfo.mLayoutFromEnd ? this.mPrimaryOrientation.getEndAfterPadding() : this.mPrimaryOrientation.getStartAfterPadding();
                            } else {
                                int decoratedStart = this.mPrimaryOrientation.getDecoratedStart(findViewByPosition) - this.mPrimaryOrientation.getStartAfterPadding();
                                if (decoratedStart < 0) {
                                    anchorInfo.mOffset = -decoratedStart;
                                } else {
                                    int endAfterPadding = this.mPrimaryOrientation.getEndAfterPadding() - this.mPrimaryOrientation.getDecoratedEnd(findViewByPosition);
                                    if (endAfterPadding < 0) {
                                        anchorInfo.mOffset = endAfterPadding;
                                    } else {
                                        anchorInfo.mOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
                                    }
                                }
                            }
                        } else {
                            int i7 = this.mPendingScrollPosition;
                            anchorInfo.mPosition = i7;
                            int i8 = this.mPendingScrollPositionOffset;
                            if (i8 == Integer.MIN_VALUE) {
                                boolean z6 = calculateScrollDirectionForPosition(i7) == 1;
                                anchorInfo.mLayoutFromEnd = z6;
                                anchorInfo.mOffset = z6 ? staggeredGridLayoutManager.mPrimaryOrientation.getEndAfterPadding() : staggeredGridLayoutManager.mPrimaryOrientation.getStartAfterPadding();
                            } else if (anchorInfo.mLayoutFromEnd) {
                                anchorInfo.mOffset = staggeredGridLayoutManager.mPrimaryOrientation.getEndAfterPadding() - i8;
                            } else {
                                anchorInfo.mOffset = staggeredGridLayoutManager.mPrimaryOrientation.getStartAfterPadding() + i8;
                            }
                            anchorInfo.mInvalidateOffsets = true;
                        }
                    } else {
                        anchorInfo.mOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
                        anchorInfo.mPosition = this.mPendingScrollPosition;
                    }
                    z2 = true;
                    if (!z2) {
                        if (this.mLastLayoutFromEnd) {
                            int itemCount = state.getItemCount();
                            int childCount = getChildCount();
                            while (true) {
                                childCount--;
                                if (childCount < 0) {
                                    break;
                                }
                                i = RecyclerView.LayoutManager.getPosition(getChildAt(childCount));
                                if (i >= 0 && i < itemCount) {
                                    break;
                                }
                            }
                            i = 0;
                            anchorInfo.mPosition = i;
                            anchorInfo.mOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
                        } else {
                            int itemCount2 = state.getItemCount();
                            int childCount2 = getChildCount();
                            for (int i9 = 0; i9 < childCount2; i9++) {
                                int position = RecyclerView.LayoutManager.getPosition(getChildAt(i9));
                                if (position >= 0 && position < itemCount2) {
                                    i = position;
                                    break;
                                }
                            }
                            i = 0;
                            anchorInfo.mPosition = i;
                            anchorInfo.mOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
                        }
                    }
                    anchorInfo.mValid = true;
                }
            }
            z2 = false;
            if (!z2) {
            }
            anchorInfo.mValid = true;
        }
        if (this.mPendingSavedState == null && this.mPendingScrollPosition == -1 && (anchorInfo.mLayoutFromEnd != this.mLastLayoutFromEnd || isLayoutRTL() != this.mLastLayoutRTL)) {
            lazySpanLookup.clear();
            anchorInfo.mInvalidateOffsets = true;
        }
        if (getChildCount() > 0 && ((savedState = this.mPendingSavedState) == null || savedState.mSpanOffsetsSize < 1)) {
            if (anchorInfo.mInvalidateOffsets) {
                for (int i10 = 0; i10 < this.mSpanCount; i10++) {
                    this.mSpans[i10].clear();
                    int i11 = anchorInfo.mOffset;
                    if (i11 != Integer.MIN_VALUE) {
                        Span span2 = this.mSpans[i10];
                        span2.mCachedStart = i11;
                        span2.mCachedEnd = i11;
                    }
                }
            } else if (z4 || (iArr2 = anchorInfo.mSpanReferenceLines) == null || iArr2.length < this.mSpanCount) {
                if (!z4 && (iArr = anchorInfo.mSpanReferenceLines) != null && iArr.length < this.mSpanCount) {
                    Log.w("StaggeredGridLManager", "mSpanReferenceLines length(" + anchorInfo.mSpanReferenceLines.length + ") smaller than SpanCount(" + this.mSpanCount + ")");
                }
                for (int i12 = 0; i12 < this.mSpanCount; i12++) {
                    Span span3 = this.mSpans[i12];
                    boolean z7 = this.mShouldReverseLayout;
                    int i13 = anchorInfo.mOffset;
                    int endLine = z7 ? span3.getEndLine(VideoPlayer.MEDIA_ERROR_SYSTEM) : span3.getStartLine(VideoPlayer.MEDIA_ERROR_SYSTEM);
                    span3.clear();
                    if (endLine != Integer.MIN_VALUE) {
                        StaggeredGridLayoutManager staggeredGridLayoutManager2 = StaggeredGridLayoutManager.this;
                        if ((!z7 || endLine >= staggeredGridLayoutManager2.mPrimaryOrientation.getEndAfterPadding()) && (z7 || endLine <= staggeredGridLayoutManager2.mPrimaryOrientation.getStartAfterPadding())) {
                            if (i13 != Integer.MIN_VALUE) {
                                endLine += i13;
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
                for (int i14 = 0; i14 < length; i14++) {
                    anchorInfo.mSpanReferenceLines[i14] = spanArr[i14].getStartLine(VideoPlayer.MEDIA_ERROR_SYSTEM);
                }
            } else {
                for (int i15 = 0; i15 < this.mSpanCount; i15++) {
                    Span span4 = this.mSpans[i15];
                    span4.clear();
                    int i16 = anchorInfo.mSpanReferenceLines[i15];
                    span4.mCachedStart = i16;
                    span4.mCachedEnd = i16;
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
            float f = 0.0f;
            for (int i17 = 0; i17 < childCount3; i17++) {
                View childAt = getChildAt(i17);
                float decoratedMeasurement = this.mSecondaryOrientation.getDecoratedMeasurement(childAt);
                if (decoratedMeasurement >= f) {
                    ((LayoutParams) childAt.getLayoutParams()).getClass();
                    f = Math.max(f, decoratedMeasurement);
                }
            }
            int i18 = this.mSizePerSpan;
            int round = Math.round(f * this.mSpanCount);
            if (this.mSecondaryOrientation.getMode() == Integer.MIN_VALUE) {
                round = Math.min(round, this.mSecondaryOrientation.getTotalSpace());
            }
            this.mSizePerSpan = round / this.mSpanCount;
            View.MeasureSpec.makeMeasureSpec(round, this.mSecondaryOrientation.getMode());
            if (this.mSizePerSpan != i18) {
                for (int i19 = 0; i19 < childCount3; i19++) {
                    View childAt2 = getChildAt(i19);
                    LayoutParams layoutParams = (LayoutParams) childAt2.getLayoutParams();
                    layoutParams.getClass();
                    if (isLayoutRTL() && this.mOrientation == 1) {
                        int i20 = this.mSpanCount;
                        int i21 = layoutParams.mSpan.mIndex;
                        childAt2.offsetLeftAndRight(((-((i20 - 1) - i21)) * this.mSizePerSpan) - ((-((i20 - 1) - i21)) * i18));
                    } else {
                        int i22 = layoutParams.mSpan.mIndex;
                        int i23 = this.mSizePerSpan * i22;
                        int i24 = i22 * i18;
                        if (this.mOrientation == 1) {
                            childAt2.offsetLeftAndRight(i23 - i24);
                        } else {
                            childAt2.offsetTopAndBottom(i23 - i24);
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
        if (z && !state.mInPreLayout) {
            if ((this.mGapStrategy == 0 || getChildCount() <= 0 || hasGapsToFix() == null) ? false : true) {
                RecyclerView recyclerView = this.mRecyclerView;
                if (recyclerView != null) {
                    recyclerView.removeCallbacks(this.mCheckForGapsRunnable);
                }
            }
        }
        z3 = false;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LazySpanLookup {
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

        /* JADX WARN: Removed duplicated region for block: B:31:0x0061  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x006b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final int invalidateAfter(int i) {
            int i2;
            int[] iArr = this.mData;
            if (iArr == null || i >= iArr.length) {
                return -1;
            }
            List list = this.mFullSpanItems;
            if (list != null) {
                FullSpanItem fullSpanItem = null;
                if (list != null) {
                    int size = list.size() - 1;
                    while (true) {
                        if (size < 0) {
                            break;
                        }
                        FullSpanItem fullSpanItem2 = (FullSpanItem) this.mFullSpanItems.get(size);
                        if (fullSpanItem2.mPosition == i) {
                            fullSpanItem = fullSpanItem2;
                            break;
                        }
                        size--;
                    }
                }
                if (fullSpanItem != null) {
                    this.mFullSpanItems.remove(fullSpanItem);
                }
                int size2 = this.mFullSpanItems.size();
                int i3 = 0;
                while (true) {
                    if (i3 >= size2) {
                        i3 = -1;
                        break;
                    }
                    if (((FullSpanItem) this.mFullSpanItems.get(i3)).mPosition >= i) {
                        break;
                    }
                    i3++;
                }
                if (i3 != -1) {
                    FullSpanItem fullSpanItem3 = (FullSpanItem) this.mFullSpanItems.get(i3);
                    this.mFullSpanItems.remove(i3);
                    i2 = fullSpanItem3.mPosition;
                    if (i2 != -1) {
                        int[] iArr2 = this.mData;
                        Arrays.fill(iArr2, i, iArr2.length, -1);
                        return this.mData.length;
                    }
                    int min = Math.min(i2 + 1, this.mData.length);
                    Arrays.fill(this.mData, i, min, -1);
                    return min;
                }
            }
            i2 = -1;
            if (i2 != -1) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class FullSpanItem implements Parcelable {
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
            public int mGapDir;
            public int[] mGapPerSpan;
            public boolean mHasUnwantedGapAfter;
            public int mPosition;

            public FullSpanItem(Parcel parcel) {
                this.mPosition = parcel.readInt();
                this.mGapDir = parcel.readInt();
                this.mHasUnwantedGapAfter = parcel.readInt() == 1;
                int readInt = parcel.readInt();
                if (readInt > 0) {
                    int[] iArr = new int[readInt];
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
        this.mPendingScrollPositionOffset = VideoPlayer.MEDIA_ERROR_SYSTEM;
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
