package androidx.viewpager2.widget;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

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

    /* JADX WARN: Removed duplicated region for block: B:15:0x0028  */
    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onScrolled(RecyclerView recyclerView, int i, int i2) {
        int i3;
        CompositeOnPageChangeCallback compositeOnPageChangeCallback;
        this.mScrollHappened = true;
        updateScrollEventValues();
        boolean z = this.mDispatchSelected;
        ScrollEventValues scrollEventValues = this.mScrollValues;
        if (z) {
            this.mDispatchSelected = false;
            if (i2 > 0) {
                i3 = scrollEventValues.mOffsetPx != 0 ? scrollEventValues.mPosition + 1 : scrollEventValues.mPosition;
                this.mTarget = i3;
                if (this.mDragStartPosition != i3 && (compositeOnPageChangeCallback = this.mCallback) != null) {
                    compositeOnPageChangeCallback.onPageSelected(i3);
                }
            } else {
                if (i2 == 0) {
                    if ((i < 0) == (this.mViewPager.mLayoutManager.getLayoutDirection() == 1)) {
                    }
                }
                this.mTarget = i3;
                if (this.mDragStartPosition != i3) {
                    compositeOnPageChangeCallback.onPageSelected(i3);
                }
            }
        } else if (this.mAdapterState == 0) {
            int i4 = scrollEventValues.mPosition;
            if (i4 == -1) {
                i4 = 0;
            }
            CompositeOnPageChangeCallback compositeOnPageChangeCallback2 = this.mCallback;
            if (compositeOnPageChangeCallback2 != null) {
                compositeOnPageChangeCallback2.onPageSelected(i4);
            }
        }
        int i5 = scrollEventValues.mPosition;
        if (i5 == -1) {
            i5 = 0;
        }
        float f = scrollEventValues.mOffset;
        int i6 = scrollEventValues.mOffsetPx;
        CompositeOnPageChangeCallback compositeOnPageChangeCallback3 = this.mCallback;
        if (compositeOnPageChangeCallback3 != null) {
            compositeOnPageChangeCallback3.onPageScrolled(f, i5, i6);
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
        this.mDataSetChangeHappened = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x0143  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateScrollEventValues() {
        int top;
        int childCount;
        int top2;
        int i;
        int bottom;
        int i2;
        LinearLayoutManager linearLayoutManager = this.mLayoutManager;
        int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        ScrollEventValues scrollEventValues = this.mScrollValues;
        scrollEventValues.mPosition = iFindFirstVisibleItemPosition;
        if (iFindFirstVisibleItemPosition == -1) {
            scrollEventValues.mPosition = -1;
            scrollEventValues.mOffset = 0.0f;
            scrollEventValues.mOffsetPx = 0;
            return;
        }
        View viewFindViewByPosition = linearLayoutManager.findViewByPosition(iFindFirstVisibleItemPosition);
        if (viewFindViewByPosition == null) {
            scrollEventValues.mPosition = -1;
            scrollEventValues.mOffset = 0.0f;
            scrollEventValues.mOffsetPx = 0;
            return;
        }
        int i3 = ((RecyclerView.LayoutParams) viewFindViewByPosition.getLayoutParams()).mDecorInsets.left;
        int i4 = ((RecyclerView.LayoutParams) viewFindViewByPosition.getLayoutParams()).mDecorInsets.right;
        int i5 = ((RecyclerView.LayoutParams) viewFindViewByPosition.getLayoutParams()).mDecorInsets.top;
        int i6 = ((RecyclerView.LayoutParams) viewFindViewByPosition.getLayoutParams()).mDecorInsets.bottom;
        ViewGroup.LayoutParams layoutParams = viewFindViewByPosition.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            i3 += marginLayoutParams.leftMargin;
            i4 += marginLayoutParams.rightMargin;
            i5 += marginLayoutParams.topMargin;
            i6 += marginLayoutParams.bottomMargin;
        }
        int height = viewFindViewByPosition.getHeight() + i5 + i6;
        int width = viewFindViewByPosition.getWidth() + i3 + i4;
        int i7 = linearLayoutManager.mOrientation;
        ViewPager2.RecyclerViewImpl recyclerViewImpl = this.mRecyclerView;
        if (i7 == 0) {
            top = (viewFindViewByPosition.getLeft() - i3) - recyclerViewImpl.getPaddingLeft();
            if (this.mViewPager.mLayoutManager.getLayoutDirection() == 1) {
                top = -top;
            }
            height = width;
        } else {
            top = (viewFindViewByPosition.getTop() - i5) - recyclerViewImpl.getPaddingTop();
        }
        int i8 = -top;
        scrollEventValues.mOffsetPx = i8;
        if (i8 >= 0) {
            scrollEventValues.mOffset = height != 0 ? i8 / height : 0.0f;
            return;
        }
        AnimateLayoutChangeDetector animateLayoutChangeDetector = new AnimateLayoutChangeDetector(linearLayoutManager);
        LinearLayoutManager linearLayoutManager2 = animateLayoutChangeDetector.mLayoutManager;
        int childCount2 = linearLayoutManager2.getChildCount();
        if (childCount2 != 0) {
            boolean z = linearLayoutManager2.mOrientation == 0;
            int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, childCount2, 2);
            for (int i9 = 0; i9 < childCount2; i9++) {
                View childAt = linearLayoutManager2.getChildAt(i9);
                if (childAt == null) {
                    throw new IllegalStateException("null view contained in the view hierarchy");
                }
                ViewGroup.LayoutParams layoutParams2 = childAt.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams2 : AnimateLayoutChangeDetector.ZERO_MARGIN_LAYOUT_PARAMS;
                int[] iArr2 = iArr[i9];
                if (z) {
                    top2 = childAt.getLeft();
                    i = marginLayoutParams2.leftMargin;
                } else {
                    top2 = childAt.getTop();
                    i = marginLayoutParams2.topMargin;
                }
                iArr2[0] = top2 - i;
                int[] iArr3 = iArr[i9];
                if (z) {
                    bottom = childAt.getRight();
                    i2 = marginLayoutParams2.rightMargin;
                } else {
                    bottom = childAt.getBottom();
                    i2 = marginLayoutParams2.bottomMargin;
                }
                iArr3[1] = bottom + i2;
            }
            Arrays.sort(iArr, new Comparator(animateLayoutChangeDetector) { // from class: androidx.viewpager2.widget.AnimateLayoutChangeDetector.1
                public AnonymousClass1(AnimateLayoutChangeDetector animateLayoutChangeDetector2) {
                }

                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ((int[]) obj)[0] - ((int[]) obj2)[0];
                }
            });
            int i10 = 1;
            while (true) {
                if (i10 >= childCount2) {
                    int[] iArr4 = iArr[0];
                    int i11 = iArr4[1];
                    int i12 = iArr4[0];
                    int i13 = i11 - i12;
                    if (i12 > 0 || iArr[childCount2 - 1][1] < i13) {
                        break;
                    }
                } else if (iArr[i10 - 1][1] != iArr[i10][0]) {
                    break;
                } else {
                    i10++;
                }
            }
            childCount = linearLayoutManager2.getChildCount();
            for (int i14 = 0; i14 < childCount; i14++) {
                if (AnimateLayoutChangeDetector.hasRunningChangingLayoutTransition(linearLayoutManager2.getChildAt(i14))) {
                    throw new IllegalStateException("Page(s) contain a ViewGroup with a LayoutTransition (or animateLayoutChanges=\"true\"), which interferes with the scrolling animation. Make sure to call getLayoutTransition().setAnimateParentHierarchy(false) on all ViewGroups with a LayoutTransition before an animation is started.");
                }
            }
        } else if (linearLayoutManager2.getChildCount() <= 1) {
            childCount = linearLayoutManager2.getChildCount();
            while (i14 < childCount) {
            }
        }
        Locale locale = Locale.US;
        throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(scrollEventValues.mOffsetPx, "Page can only be offset by a positive amount, not by "));
    }
}
