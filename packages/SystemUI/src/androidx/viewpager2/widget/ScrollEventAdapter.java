package androidx.viewpager2.widget;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ScrollEventAdapter extends RecyclerView.OnScrollListener {
    public int mAdapterState;
    public CompositeOnPageChangeCallback mCallback;
    public boolean mDataSetChangeHappened;
    public boolean mDispatchSelected;
    public int mDragStartPosition;
    public final LinearLayoutManager mLayoutManager;
    public final ViewPager2.RecyclerViewImpl mRecyclerView;
    public boolean mScrollHappened;
    public int mScrollState;
    public final ScrollEventValues mScrollValues;
    public int mTarget;
    public final ViewPager2 mViewPager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ScrollEventValues {
        public float mOffset;
        public int mOffsetPx;
        public int mPosition;
    }

    public ScrollEventAdapter(ViewPager2 viewPager2) {
        this.mViewPager = viewPager2;
        ViewPager2.RecyclerViewImpl recyclerViewImpl = viewPager2.mRecyclerView;
        this.mRecyclerView = recyclerViewImpl;
        this.mLayoutManager = (LinearLayoutManager) recyclerViewImpl.getLayoutManager();
        this.mScrollValues = new ScrollEventValues();
        resetState();
    }

    public final void dispatchStateChanged(int i) {
        if ((this.mAdapterState == 3 && this.mScrollState == 0) || this.mScrollState == i) {
            return;
        }
        this.mScrollState = i;
        CompositeOnPageChangeCallback compositeOnPageChangeCallback = this.mCallback;
        if (compositeOnPageChangeCallback != null) {
            compositeOnPageChangeCallback.onPageScrollStateChanged(i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    public final void onScrollStateChanged(RecyclerView recyclerView, int i) {
        CompositeOnPageChangeCallback compositeOnPageChangeCallback;
        CompositeOnPageChangeCallback compositeOnPageChangeCallback2;
        int i2 = this.mAdapterState;
        boolean z = true;
        if (!(i2 == 1 && this.mScrollState == 1) && i == 1) {
            this.mAdapterState = 1;
            int i3 = this.mTarget;
            if (i3 != -1) {
                this.mDragStartPosition = i3;
                this.mTarget = -1;
            } else if (this.mDragStartPosition == -1) {
                this.mDragStartPosition = this.mLayoutManager.findFirstVisibleItemPosition();
            }
            dispatchStateChanged(1);
            return;
        }
        if ((i2 == 1 || i2 == 4) && i == 2) {
            if (this.mScrollHappened) {
                dispatchStateChanged(2);
                this.mDispatchSelected = true;
                return;
            }
            return;
        }
        if (i2 != 1 && i2 != 4) {
            z = false;
        }
        ScrollEventValues scrollEventValues = this.mScrollValues;
        if (z && i == 0) {
            updateScrollEventValues();
            if (!this.mScrollHappened) {
                int i4 = scrollEventValues.mPosition;
                if (i4 != -1 && (compositeOnPageChangeCallback2 = this.mCallback) != null) {
                    compositeOnPageChangeCallback2.onPageScrolled(0.0f, i4, 0);
                }
            } else if (scrollEventValues.mOffsetPx == 0) {
                int i5 = this.mDragStartPosition;
                int i6 = scrollEventValues.mPosition;
                if (i5 != i6 && (compositeOnPageChangeCallback = this.mCallback) != null) {
                    compositeOnPageChangeCallback.onPageSelected(i6);
                }
            }
            dispatchStateChanged(0);
            resetState();
        }
        if (this.mAdapterState == 2 && i == 0 && this.mDataSetChangeHappened) {
            updateScrollEventValues();
            if (scrollEventValues.mOffsetPx == 0) {
                int i7 = this.mTarget;
                int i8 = scrollEventValues.mPosition;
                if (i7 != i8) {
                    if (i8 == -1) {
                        i8 = 0;
                    }
                    CompositeOnPageChangeCallback compositeOnPageChangeCallback3 = this.mCallback;
                    if (compositeOnPageChangeCallback3 != null) {
                        compositeOnPageChangeCallback3.onPageSelected(i8);
                    }
                }
                dispatchStateChanged(0);
                resetState();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0026, code lost:
    
        if ((r7 < 0) == (r5.mViewPager.mLayoutManager.getLayoutDirection() == 1)) goto L15;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0038  */
    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onScrolled(androidx.recyclerview.widget.RecyclerView r6, int r7, int r8) {
        /*
            r5 = this;
            r6 = 1
            r5.mScrollHappened = r6
            r5.updateScrollEventValues()
            boolean r0 = r5.mDispatchSelected
            androidx.viewpager2.widget.ScrollEventAdapter$ScrollEventValues r1 = r5.mScrollValues
            r2 = -1
            r3 = 0
            if (r0 == 0) goto L40
            r5.mDispatchSelected = r3
            if (r8 > 0) goto L28
            if (r8 != 0) goto L30
            if (r7 >= 0) goto L18
            r7 = r6
            goto L19
        L18:
            r7 = r3
        L19:
            androidx.viewpager2.widget.ViewPager2 r8 = r5.mViewPager
            androidx.viewpager2.widget.ViewPager2$LinearLayoutManagerImpl r8 = r8.mLayoutManager
            int r8 = r8.getLayoutDirection()
            if (r8 != r6) goto L25
            r8 = r6
            goto L26
        L25:
            r8 = r3
        L26:
            if (r7 != r8) goto L30
        L28:
            int r7 = r1.mOffsetPx
            if (r7 == 0) goto L30
            int r7 = r1.mPosition
            int r7 = r7 + r6
            goto L32
        L30:
            int r7 = r1.mPosition
        L32:
            r5.mTarget = r7
            int r8 = r5.mDragStartPosition
            if (r8 == r7) goto L50
            androidx.viewpager2.widget.CompositeOnPageChangeCallback r8 = r5.mCallback
            if (r8 == 0) goto L50
            r8.onPageSelected(r7)
            goto L50
        L40:
            int r7 = r5.mAdapterState
            if (r7 != 0) goto L50
            int r7 = r1.mPosition
            if (r7 != r2) goto L49
            r7 = r3
        L49:
            androidx.viewpager2.widget.CompositeOnPageChangeCallback r8 = r5.mCallback
            if (r8 == 0) goto L50
            r8.onPageSelected(r7)
        L50:
            int r7 = r1.mPosition
            if (r7 != r2) goto L55
            r7 = r3
        L55:
            float r8 = r1.mOffset
            int r0 = r1.mOffsetPx
            androidx.viewpager2.widget.CompositeOnPageChangeCallback r4 = r5.mCallback
            if (r4 == 0) goto L60
            r4.onPageScrolled(r8, r7, r0)
        L60:
            int r7 = r1.mPosition
            int r8 = r5.mTarget
            if (r7 == r8) goto L68
            if (r8 != r2) goto L76
        L68:
            int r7 = r1.mOffsetPx
            if (r7 != 0) goto L76
            int r7 = r5.mScrollState
            if (r7 == r6) goto L76
            r5.dispatchStateChanged(r3)
            r5.resetState()
        L76:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager2.widget.ScrollEventAdapter.onScrolled(androidx.recyclerview.widget.RecyclerView, int, int):void");
    }

    public final void resetState() {
        this.mAdapterState = 0;
        this.mScrollState = 0;
        ScrollEventValues scrollEventValues = this.mScrollValues;
        scrollEventValues.mPosition = -1;
        scrollEventValues.mOffset = 0.0f;
        scrollEventValues.mOffsetPx = 0;
        this.mDragStartPosition = -1;
        this.mTarget = -1;
        this.mDispatchSelected = false;
        this.mScrollHappened = false;
        this.mDataSetChangeHappened = false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x013d, code lost:
    
        r13 = r0.getChildCount();
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0141, code lost:
    
        if (r3 >= r13) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x014b, code lost:
    
        if (androidx.viewpager2.widget.AnimateLayoutChangeDetector.hasRunningChangingLayoutTransition(r0.getChildAt(r3)) != false) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x014d, code lost:
    
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0157, code lost:
    
        throw new java.lang.IllegalStateException("Page(s) contain a ViewGroup with a LayoutTransition (or animateLayoutChanges=\"true\"), which interferes with the scrolling animation. Make sure to call getLayoutTransition().setAnimateParentHierarchy(false) on all ViewGroups with a LayoutTransition before an animation is started.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0158, code lost:
    
        r0 = java.util.Locale.US;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0167, code lost:
    
        throw new java.lang.IllegalStateException(android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r2.mOffsetPx, "Page can only be offset by a positive amount, not by "));
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0134, code lost:
    
        if (r5[r1 - 1][1] >= r4) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x013b, code lost:
    
        if (r0.getChildCount() <= 1) goto L63;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateScrollEventValues() {
        /*
            Method dump skipped, instructions count: 370
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager2.widget.ScrollEventAdapter.updateScrollEventValues():void");
    }
}
