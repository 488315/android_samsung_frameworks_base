package androidx.viewpager2.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ScrollEventAdapter extends RecyclerView.OnScrollListener {
    public int mAdapterState;
    public ViewPager2.OnPageChangeCallback mCallback;
    public boolean mDataSetChangeHappened;
    public boolean mDispatchSelected;
    public int mDragStartPosition;
    public boolean mFakeDragging;
    public final LinearLayoutManager mLayoutManager;
    public final RecyclerView mRecyclerView;
    public boolean mScrollHappened;
    public int mScrollState;
    public final ScrollEventValues mScrollValues;
    public int mTarget;
    public final ViewPager2 mViewPager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ScrollEventValues {
        public float mOffset;
        public int mOffsetPx;
        public int mPosition;
    }

    public ScrollEventAdapter(ViewPager2 viewPager2) {
        this.mViewPager = viewPager2;
        ViewPager2.RecyclerViewImpl recyclerViewImpl = viewPager2.mRecyclerView;
        this.mRecyclerView = recyclerViewImpl;
        this.mLayoutManager = (LinearLayoutManager) recyclerViewImpl.getLayoutManager$1();
        this.mScrollValues = new ScrollEventValues();
        resetState();
    }

    public final void dispatchStateChanged(int i) {
        if ((this.mAdapterState == 3 && this.mScrollState == 0) || this.mScrollState == i) {
            return;
        }
        this.mScrollState = i;
        ViewPager2.OnPageChangeCallback onPageChangeCallback = this.mCallback;
        if (onPageChangeCallback != null) {
            onPageChangeCallback.onPageScrollStateChanged(i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    public final void onScrollStateChanged(RecyclerView recyclerView, int i) {
        ViewPager2.OnPageChangeCallback onPageChangeCallback;
        ViewPager2.OnPageChangeCallback onPageChangeCallback2;
        int i2 = this.mAdapterState;
        boolean z = true;
        if (!(i2 == 1 && this.mScrollState == 1) && i == 1) {
            this.mFakeDragging = false;
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
        boolean z2 = i2 == 1 || i2 == 4;
        ScrollEventValues scrollEventValues = this.mScrollValues;
        if (z2 && i == 0) {
            updateScrollEventValues();
            if (!this.mScrollHappened) {
                int i4 = scrollEventValues.mPosition;
                if (i4 != -1 && (onPageChangeCallback2 = this.mCallback) != null) {
                    onPageChangeCallback2.onPageScrolled(0.0f, i4, 0);
                }
            } else if (scrollEventValues.mOffsetPx == 0) {
                int i5 = this.mDragStartPosition;
                int i6 = scrollEventValues.mPosition;
                if (i5 != i6 && (onPageChangeCallback = this.mCallback) != null) {
                    onPageChangeCallback.onPageSelected(i6);
                }
            } else {
                z = false;
            }
            if (z) {
                dispatchStateChanged(0);
                resetState();
            }
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
                    ViewPager2.OnPageChangeCallback onPageChangeCallback3 = this.mCallback;
                    if (onPageChangeCallback3 != null) {
                        onPageChangeCallback3.onPageSelected(i8);
                    }
                }
                dispatchStateChanged(0);
                resetState();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0026, code lost:
    
        if ((r7 < 0) == (r5.mViewPager.mLayoutManager.getLayoutDirection() == 1)) goto L17;
     */
    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onScrolled(RecyclerView recyclerView, int i, int i2) {
        boolean z;
        int i3;
        ViewPager2.OnPageChangeCallback onPageChangeCallback;
        this.mScrollHappened = true;
        updateScrollEventValues();
        boolean z2 = this.mDispatchSelected;
        ScrollEventValues scrollEventValues = this.mScrollValues;
        if (z2) {
            this.mDispatchSelected = false;
            if (i2 <= 0) {
                if (i2 == 0) {
                }
                z = false;
                i3 = (z || scrollEventValues.mOffsetPx == 0) ? scrollEventValues.mPosition : scrollEventValues.mPosition + 1;
                this.mTarget = i3;
                if (this.mDragStartPosition != i3 && (onPageChangeCallback = this.mCallback) != null) {
                    onPageChangeCallback.onPageSelected(i3);
                }
            }
            z = true;
            if (z) {
            }
            this.mTarget = i3;
            if (this.mDragStartPosition != i3) {
                onPageChangeCallback.onPageSelected(i3);
            }
        } else if (this.mAdapterState == 0) {
            int i4 = scrollEventValues.mPosition;
            if (i4 == -1) {
                i4 = 0;
            }
            ViewPager2.OnPageChangeCallback onPageChangeCallback2 = this.mCallback;
            if (onPageChangeCallback2 != null) {
                onPageChangeCallback2.onPageSelected(i4);
            }
        }
        int i5 = scrollEventValues.mPosition;
        if (i5 == -1) {
            i5 = 0;
        }
        float f = scrollEventValues.mOffset;
        int i6 = scrollEventValues.mOffsetPx;
        ViewPager2.OnPageChangeCallback onPageChangeCallback3 = this.mCallback;
        if (onPageChangeCallback3 != null) {
            onPageChangeCallback3.onPageScrolled(f, i5, i6);
        }
        int i7 = scrollEventValues.mPosition;
        int i8 = this.mTarget;
        if ((i7 == i8 || i8 == -1) && scrollEventValues.mOffsetPx == 0 && this.mScrollState != 1) {
            dispatchStateChanged(0);
            resetState();
        }
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
        this.mFakeDragging = false;
        this.mDataSetChangeHappened = false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x0140, code lost:
    
        r13 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x013b, code lost:
    
        if (r5[r1 - 1][1] >= r3) goto L66;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateScrollEventValues() {
        int top;
        boolean z;
        int top2;
        int i;
        int bottom;
        int i2;
        LinearLayoutManager linearLayoutManager = this.mLayoutManager;
        int findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        ScrollEventValues scrollEventValues = this.mScrollValues;
        scrollEventValues.mPosition = findFirstVisibleItemPosition;
        boolean z2 = false;
        if (findFirstVisibleItemPosition == -1) {
            scrollEventValues.mPosition = -1;
            scrollEventValues.mOffset = 0.0f;
            scrollEventValues.mOffsetPx = 0;
            return;
        }
        View findViewByPosition = linearLayoutManager.findViewByPosition(findFirstVisibleItemPosition);
        if (findViewByPosition == null) {
            scrollEventValues.mPosition = -1;
            scrollEventValues.mOffset = 0.0f;
            scrollEventValues.mOffsetPx = 0;
            return;
        }
        int i3 = ((RecyclerView.LayoutParams) findViewByPosition.getLayoutParams()).mDecorInsets.left;
        int i4 = ((RecyclerView.LayoutParams) findViewByPosition.getLayoutParams()).mDecorInsets.right;
        int i5 = ((RecyclerView.LayoutParams) findViewByPosition.getLayoutParams()).mDecorInsets.top;
        int i6 = ((RecyclerView.LayoutParams) findViewByPosition.getLayoutParams()).mDecorInsets.bottom;
        ViewGroup.LayoutParams layoutParams = findViewByPosition.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            i3 += marginLayoutParams.leftMargin;
            i4 += marginLayoutParams.rightMargin;
            i5 += marginLayoutParams.topMargin;
            i6 += marginLayoutParams.bottomMargin;
        }
        int height = findViewByPosition.getHeight() + i5 + i6;
        int width = findViewByPosition.getWidth() + i3 + i4;
        boolean z3 = linearLayoutManager.mOrientation == 0;
        RecyclerView recyclerView = this.mRecyclerView;
        if (z3) {
            top = (findViewByPosition.getLeft() - i3) - recyclerView.getPaddingLeft();
            if (this.mViewPager.mLayoutManager.getLayoutDirection() == 1) {
                top = -top;
            }
            height = width;
        } else {
            top = (findViewByPosition.getTop() - i5) - recyclerView.getPaddingTop();
        }
        int i7 = -top;
        scrollEventValues.mOffsetPx = i7;
        if (i7 >= 0) {
            scrollEventValues.mOffset = height != 0 ? i7 / height : 0.0f;
            return;
        }
        AnimateLayoutChangeDetector animateLayoutChangeDetector = new AnimateLayoutChangeDetector(linearLayoutManager);
        LinearLayoutManager linearLayoutManager2 = animateLayoutChangeDetector.mLayoutManager;
        int childCount = linearLayoutManager2.getChildCount();
        if (childCount != 0) {
            boolean z4 = linearLayoutManager2.mOrientation == 0;
            int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, childCount, 2);
            for (int i8 = 0; i8 < childCount; i8++) {
                View childAt = linearLayoutManager2.getChildAt(i8);
                if (childAt == null) {
                    throw new IllegalStateException("null view contained in the view hierarchy");
                }
                ViewGroup.LayoutParams layoutParams2 = childAt.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams2 : AnimateLayoutChangeDetector.ZERO_MARGIN_LAYOUT_PARAMS;
                int[] iArr2 = iArr[i8];
                if (z4) {
                    top2 = childAt.getLeft();
                    i = marginLayoutParams2.leftMargin;
                } else {
                    top2 = childAt.getTop();
                    i = marginLayoutParams2.topMargin;
                }
                iArr2[0] = top2 - i;
                int[] iArr3 = iArr[i8];
                if (z4) {
                    bottom = childAt.getRight();
                    i2 = marginLayoutParams2.rightMargin;
                } else {
                    bottom = childAt.getBottom();
                    i2 = marginLayoutParams2.bottomMargin;
                }
                iArr3[1] = bottom + i2;
            }
            Arrays.sort(iArr, new Comparator(animateLayoutChangeDetector) { // from class: androidx.viewpager2.widget.AnimateLayoutChangeDetector.1
                public C05771(AnimateLayoutChangeDetector animateLayoutChangeDetector2) {
                }

                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ((int[]) obj)[0] - ((int[]) obj2)[0];
                }
            });
            int i9 = 1;
            while (true) {
                if (i9 >= childCount) {
                    int[] iArr4 = iArr[0];
                    int i10 = iArr4[1];
                    int i11 = iArr4[0];
                    int i12 = i10 - i11;
                    if (i11 <= 0) {
                    }
                } else if (iArr[i9 - 1][1] != iArr[i9][0]) {
                    break;
                } else {
                    i9++;
                }
            }
        }
        boolean z5 = true;
        if (!z5 || linearLayoutManager2.getChildCount() <= 1) {
            int childCount2 = linearLayoutManager2.getChildCount();
            int i13 = 0;
            while (true) {
                if (i13 >= childCount2) {
                    z = false;
                    break;
                } else {
                    if (AnimateLayoutChangeDetector.hasRunningChangingLayoutTransition(linearLayoutManager2.getChildAt(i13))) {
                        z = true;
                        break;
                    }
                    i13++;
                }
            }
            if (z) {
                z2 = true;
            }
        }
        if (!z2) {
            throw new IllegalStateException(String.format(Locale.US, "Page can only be offset by a positive amount, not by %d", Integer.valueOf(scrollEventValues.mOffsetPx)));
        }
        throw new IllegalStateException("Page(s) contain a ViewGroup with a LayoutTransition (or animateLayoutChanges=\"true\"), which interferes with the scrolling animation. Make sure to call getLayoutTransition().setAnimateParentHierarchy(false) on all ViewGroups with a LayoutTransition before an animation is started.");
    }
}
