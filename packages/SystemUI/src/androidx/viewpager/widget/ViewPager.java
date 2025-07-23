package androidx.viewpager.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.core.graphics.Insets;
import androidx.core.math.MathUtils;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.EdgeEffectCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.tabs.TabLayout;
import com.sec.ims.volte2.data.VolteConstants;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ViewPager extends ViewGroup {
    public int mActivePointerId;
    public PagerAdapter mAdapter;
    public List mAdapterChangeListeners;
    public boolean mCalledSuper;
    public int mCloseEnough;
    public int mCurItem;
    public int mDecorChildCount;
    public int mDefaultGutterSize;
    public final boolean mDragInGutterEnabled;
    public final AnonymousClass3 mEndScrollRunnable;
    public int mExpectedAdapterCount;
    public long mFakeDragBeginTime;
    public boolean mFakeDragging;
    public boolean mFirstLayout;
    public float mFirstOffset;
    public int mFlingDistance;
    public int mGutterSize;
    public boolean mInLayout;
    public float mInitialMotionX;
    public float mInitialMotionY;
    public boolean mIsBeingDragged;
    public boolean mIsScrollStarted;
    public boolean mIsUnableToDrag;
    public final ArrayList mItems;
    public float mLastMotionX;
    public float mLastMotionY;
    public float mLastOffset;
    public EdgeEffect mLeftEdge;
    public final int mLeftIncr;
    public int mMaximumVelocity;
    public int mMinimumVelocity;
    public PagerObserver mObserver;
    public int mOffscreenPageLimit;
    public OnPageChangeListener mOnPageChangeListener;
    public List mOnPageChangeListeners;
    public int mPageMargin;
    public boolean mPopulatePending;
    public int mRestoredCurItem;
    public EdgeEffect mRightEdge;
    public int mScrollState;
    public Scroller mScroller;
    public boolean mScrollingCacheEnabled;
    public final ItemInfo mTempItem;
    public final Rect mTempRect;
    public int mTouchSlop;
    public final float mTouchSlopRatio;
    public VelocityTracker mVelocityTracker;
    public static final int[] LAYOUT_ATTRS = {R.attr.layout_gravity};
    public static final AnonymousClass1 COMPARATOR = new Comparator() { // from class: androidx.viewpager.widget.ViewPager.1
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((ItemInfo) obj).position - ((ItemInfo) obj2).position;
        }
    };
    public static final AnonymousClass2 sInterpolator = new Interpolator() { // from class: androidx.viewpager.widget.ViewPager.2
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.viewpager.widget.ViewPager$3, reason: invalid class name */
    public class AnonymousClass3 implements Runnable {
        public AnonymousClass3() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            ViewPager.this.setScrollState(0);
            ViewPager.this.populate();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DecorView {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ItemInfo {
        public Object object;
        public float offset;
        public int position;
        public boolean scrolling;
        public float widthFactor;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
        public MyAccessibilityDelegate() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:4:0x0013, code lost:
        
            if (r2.getCount() > 1) goto L8;
         */
        @Override // androidx.core.view.AccessibilityDelegateCompat
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onInitializeAccessibilityEvent(android.view.View r2, android.view.accessibility.AccessibilityEvent r3) {
            /*
                r1 = this;
                super.onInitializeAccessibilityEvent(r2, r3)
                java.lang.String r2 = "androidx.viewpager.widget.ViewPager"
                r3.setClassName(r2)
                androidx.viewpager.widget.ViewPager r1 = androidx.viewpager.widget.ViewPager.this
                androidx.viewpager.widget.PagerAdapter r2 = r1.mAdapter
                if (r2 == 0) goto L16
                int r2 = r2.getCount()
                r0 = 1
                if (r2 <= r0) goto L16
                goto L17
            L16:
                r0 = 0
            L17:
                r3.setScrollable(r0)
                int r2 = r3.getEventType()
                r0 = 4096(0x1000, float:5.74E-42)
                if (r2 != r0) goto L37
                androidx.viewpager.widget.PagerAdapter r2 = r1.mAdapter
                if (r2 == 0) goto L37
                int r2 = r2.getCount()
                r3.setItemCount(r2)
                int r2 = r1.mCurItem
                r3.setFromIndex(r2)
                int r1 = r1.mCurItem
                r3.setToIndex(r1)
            L37:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.MyAccessibilityDelegate.onInitializeAccessibilityEvent(android.view.View, android.view.accessibility.AccessibilityEvent):void");
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
            accessibilityNodeInfoCompat.setClassName("androidx.viewpager.widget.ViewPager");
            ViewPager viewPager = ViewPager.this;
            PagerAdapter pagerAdapter = viewPager.mAdapter;
            accessibilityNodeInfoCompat.setScrollable(pagerAdapter != null && pagerAdapter.getCount() > 1);
            if (viewPager.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
            }
            if (viewPager.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
            }
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            ViewPager viewPager = ViewPager.this;
            if (i == 4096) {
                if (!viewPager.canScrollHorizontally(1)) {
                    return false;
                }
                viewPager.setCurrentItem(viewPager.mCurItem + 1);
                return true;
            }
            if (i != 8192 || !viewPager.canScrollHorizontally(-1)) {
                return false;
            }
            viewPager.setCurrentItem(viewPager.mCurItem - 1);
            return true;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnPageChangeListener {
        void onPageScrollStateChanged(int i);

        void onPageScrolled(float f, int i);

        void onPageSelected(int i);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PagerObserver extends DataSetObserver {
        public PagerObserver() {
        }

        @Override // android.database.DataSetObserver
        public final void onChanged() {
            ViewPager.this.dataSetChanged();
        }

        @Override // android.database.DataSetObserver
        public final void onInvalidated() {
            ViewPager.this.dataSetChanged();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: androidx.viewpager.widget.ViewPager.SavedState.1
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }
        };
        public Parcelable adapterState;
        public final ClassLoader loader;
        public int position;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("FragmentPager.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" position=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.position, "}", sb);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, i);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            classLoader = classLoader == null ? getClass().getClassLoader() : classLoader;
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader);
            this.loader = classLoader;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ViewPositionComparator implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            LayoutParams layoutParams = (LayoutParams) ((View) obj).getLayoutParams();
            LayoutParams layoutParams2 = (LayoutParams) ((View) obj2).getLayoutParams();
            boolean z = layoutParams.isDecor;
            return z != layoutParams2.isDecor ? z ? 1 : -1 : layoutParams.position - layoutParams2.position;
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.viewpager.widget.ViewPager$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.viewpager.widget.ViewPager$2] */
    static {
        new ViewPositionComparator();
    }

    public ViewPager(Context context) {
        super(context);
        this.mItems = new ArrayList();
        this.mTempItem = new ItemInfo();
        this.mTempRect = new Rect();
        this.mRestoredCurItem = -1;
        this.mFirstOffset = -3.4028235E38f;
        this.mLastOffset = Float.MAX_VALUE;
        this.mOffscreenPageLimit = 1;
        this.mDragInGutterEnabled = true;
        this.mActivePointerId = -1;
        this.mFirstLayout = true;
        this.mEndScrollRunnable = new AnonymousClass3();
        this.mScrollState = 0;
        this.mTouchSlopRatio = 0.5f;
        this.mLeftIncr = -1;
        initViewPager(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void addFocusables(ArrayList arrayList, int i, int i2) {
        ItemInfo infoForChild;
        if (arrayList == null) {
            return;
        }
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                View childAt = getChildAt(i3);
                if (childAt.getVisibility() == 0 && (infoForChild = infoForChild(childAt)) != null && infoForChild.position == this.mCurItem) {
                    childAt.addFocusables(arrayList, i, i2);
                }
            }
        }
        if ((descendantFocusability != 262144 || size == arrayList.size()) && isFocusable()) {
            if ((i2 & 1) == 1 && isInTouchMode() && !isFocusableInTouchMode()) {
                return;
            }
            arrayList.add(this);
        }
    }

    public final ItemInfo addNewItem(int i, int i2) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.position = i;
        itemInfo.object = this.mAdapter.instantiateItem(this, i);
        this.mAdapter.getClass();
        itemInfo.widthFactor = 1.0f;
        if (i2 < 0 || i2 >= this.mItems.size()) {
            this.mItems.add(itemInfo);
            return itemInfo;
        }
        this.mItems.add(i2, itemInfo);
        return itemInfo;
    }

    public void addOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        if (this.mOnPageChangeListeners == null) {
            this.mOnPageChangeListeners = new ArrayList();
        }
        this.mOnPageChangeListeners.add(onPageChangeListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void addTouchables(ArrayList arrayList) {
        ItemInfo infoForChild;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0 && (infoForChild = infoForChild(childAt)) != null && infoForChild.position == this.mCurItem) {
                childAt.addTouchables(arrayList);
            }
        }
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (!checkLayoutParams(layoutParams)) {
            layoutParams = new LayoutParams();
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        if (layoutParams2 != null) {
            boolean z = layoutParams2.isDecor | (view.getClass().getAnnotation(DecorView.class) != null);
            layoutParams2.isDecor = z;
            if (!this.mInLayout) {
                super.addView(view, i, layoutParams);
            } else {
                if (z) {
                    throw new IllegalStateException("Cannot add pager decor view during layout");
                }
                layoutParams2.needsMeasure = true;
                addViewInLayout(view, i, layoutParams);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00cd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean arrowScroll(int r7) {
        /*
            r6 = this;
            android.view.View r0 = r6.findFocus()
            r1 = 0
            if (r0 != r6) goto L9
        L7:
            r0 = r1
            goto L60
        L9:
            if (r0 == 0) goto L60
            android.view.ViewParent r2 = r0.getParent()
        Lf:
            boolean r3 = r2 instanceof android.view.ViewGroup
            if (r3 == 0) goto L1b
            if (r2 != r6) goto L16
            goto L60
        L16:
            android.view.ViewParent r2 = r2.getParent()
            goto Lf
        L1b:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.Class r3 = r0.getClass()
            java.lang.String r3 = r3.getSimpleName()
            r2.append(r3)
            android.view.ViewParent r0 = r0.getParent()
        L2f:
            boolean r3 = r0 instanceof android.view.ViewGroup
            if (r3 == 0) goto L48
            java.lang.String r3 = " => "
            r2.append(r3)
            java.lang.Class r3 = r0.getClass()
            java.lang.String r3 = r3.getSimpleName()
            r2.append(r3)
            android.view.ViewParent r0 = r0.getParent()
            goto L2f
        L48:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = "arrowScroll tried to find focus based on non-child current focused view "
            r0.<init>(r3)
            java.lang.String r2 = r2.toString()
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = "ViewPager"
            android.util.Log.e(r2, r0)
            goto L7
        L60:
            android.view.FocusFinder r1 = android.view.FocusFinder.getInstance()
            android.view.View r1 = r1.findNextFocus(r6, r0, r7)
            r2 = 0
            r3 = 1
            r4 = 66
            r5 = 17
            if (r1 == 0) goto Lba
            if (r1 == r0) goto Lba
            if (r7 != r5) goto L9a
            android.graphics.Rect r4 = r6.mTempRect
            android.graphics.Rect r4 = r6.getChildRectInPagerCoordinates(r4, r1)
            int r4 = r4.left
            android.graphics.Rect r5 = r6.mTempRect
            android.graphics.Rect r5 = r6.getChildRectInPagerCoordinates(r5, r0)
            int r5 = r5.left
            if (r0 == 0) goto L94
            if (r4 < r5) goto L94
            int r0 = r6.mCurItem
            if (r0 <= 0) goto Ld4
            int r1 = r6.mLeftIncr
            int r0 = r0 + r1
            r6.setCurrentItem(r0, r3)
        L92:
            r2 = r3
            goto Ld4
        L94:
            boolean r0 = r1.requestFocus()
        L98:
            r2 = r0
            goto Ld4
        L9a:
            if (r7 != r4) goto Ld4
            android.graphics.Rect r2 = r6.mTempRect
            android.graphics.Rect r2 = r6.getChildRectInPagerCoordinates(r2, r1)
            int r2 = r2.left
            android.graphics.Rect r3 = r6.mTempRect
            android.graphics.Rect r3 = r6.getChildRectInPagerCoordinates(r3, r0)
            int r3 = r3.left
            if (r0 == 0) goto Lb5
            if (r2 > r3) goto Lb5
            boolean r0 = r6.pageRight()
            goto L98
        Lb5:
            boolean r0 = r1.requestFocus()
            goto L98
        Lba:
            if (r7 == r5) goto Lc9
            if (r7 != r3) goto Lbf
            goto Lc9
        Lbf:
            if (r7 == r4) goto Lc4
            r0 = 2
            if (r7 != r0) goto Ld4
        Lc4:
            boolean r2 = r6.pageRight()
            goto Ld4
        Lc9:
            int r0 = r6.mCurItem
            if (r0 <= 0) goto Ld4
            int r1 = r6.mLeftIncr
            int r0 = r0 + r1
            r6.setCurrentItem(r0, r3)
            goto L92
        Ld4:
            if (r2 == 0) goto Ldd
            int r7 = android.view.SoundEffectConstants.getContantForFocusDirection(r7)
            r6.playSoundEffect(r7)
        Ldd:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.arrowScroll(int):boolean");
    }

    public boolean canScroll(int i, int i2, int i3, View view, boolean z) {
        int i4;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i5 = i2 + scrollX;
                if (i5 >= childAt.getLeft() && i5 < childAt.getRight() && (i4 = i3 + scrollY) >= childAt.getTop() && i4 < childAt.getBottom() && canScroll(i, i5 - childAt.getLeft(), i4 - childAt.getTop(), childAt, true)) {
                    break;
                }
            }
        }
        return z && view.canScrollHorizontally(-i);
    }

    @Override // android.view.View
    public final boolean canScrollHorizontally(int i) {
        if (this.mAdapter == null) {
            return false;
        }
        int clientWidth = getClientWidth();
        int scrollX = getScrollX();
        return i < 0 ? scrollX > ((int) (((float) clientWidth) * this.mFirstOffset)) : i > 0 && scrollX < ((int) (((float) clientWidth) * this.mLastOffset));
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public final void completeScroll(boolean z) {
        boolean z2 = this.mScrollState == 2;
        if (z2) {
            setScrollingCacheEnabled(false);
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
                int scrollX = getScrollX();
                int scrollY = getScrollY();
                int currX = this.mScroller.getCurrX();
                int currY = this.mScroller.getCurrY();
                if (scrollX != currX || scrollY != currY) {
                    scrollTo(currX, currY);
                    if (currX != scrollX) {
                        pageScrolled(currX);
                    }
                }
            }
        }
        this.mPopulatePending = false;
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(i);
            if (itemInfo.scrolling) {
                itemInfo.scrolling = false;
                z2 = true;
            }
        }
        if (z2) {
            if (!z) {
                this.mEndScrollRunnable.run();
                return;
            }
            AnonymousClass3 anonymousClass3 = this.mEndScrollRunnable;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            postOnAnimation(anonymousClass3);
        }
    }

    @Override // android.view.View
    public void computeScroll() {
        this.mIsScrollStarted = true;
        if (this.mScroller.isFinished() || !this.mScroller.computeScrollOffset()) {
            completeScroll(true);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int currX = this.mScroller.getCurrX();
        int currY = this.mScroller.getCurrY();
        if (scrollX != currX || scrollY != currY) {
            scrollTo(currX, currY);
            if (!pageScrolled(currX)) {
                this.mScroller.abortAnimation();
                scrollTo(0, currY);
            }
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        postInvalidateOnAnimation();
    }

    public final void dataSetChanged() {
        int count = this.mAdapter.getCount();
        this.mExpectedAdapterCount = count;
        boolean z = this.mItems.size() < (this.mOffscreenPageLimit * 2) + 1 && this.mItems.size() < count;
        int i = this.mCurItem;
        int i2 = 0;
        boolean z2 = false;
        while (i2 < this.mItems.size()) {
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(i2);
            int itemPosition = this.mAdapter.getItemPosition(itemInfo.object);
            if (itemPosition != -1) {
                if (itemPosition == -2) {
                    this.mItems.remove(i2);
                    i2--;
                    if (!z2) {
                        this.mAdapter.getClass();
                        z2 = true;
                    }
                    this.mAdapter.destroyItem(this, itemInfo.position, itemInfo.object);
                    int i3 = this.mCurItem;
                    if (i3 == itemInfo.position) {
                        i = Math.max(0, Math.min(i3, count - 1));
                    }
                } else {
                    int i4 = itemInfo.position;
                    if (i4 != itemPosition) {
                        if (i4 == this.mCurItem) {
                            i = itemPosition;
                        }
                        itemInfo.position = itemPosition;
                    }
                }
                z = true;
            }
            i2++;
        }
        if (z2) {
            this.mAdapter.getClass();
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (z) {
            int childCount = getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i5).getLayoutParams();
                if (!layoutParams.isDecor) {
                    layoutParams.widthFactor = 0.0f;
                }
            }
            setCurrentItemInternal(i, 0, false, true);
            requestLayout();
        }
    }

    public final int determineTargetPage(float f, int i, int i2, int i3) {
        int i4;
        if (Math.abs(i3) <= this.mFlingDistance || Math.abs(i2) <= this.mMinimumVelocity || EdgeEffectCompat.getDistance(this.mLeftEdge) != 0.0f || EdgeEffectCompat.getDistance(this.mRightEdge) != 0.0f) {
            i4 = i - (this.mLeftIncr * ((int) (f + (i >= this.mCurItem ? 0.4f : 0.6f))));
        } else {
            i4 = i - (i2 > 0 ? 0 : this.mLeftIncr);
        }
        if (this.mItems.size() > 0) {
            return MathUtils.clamp(i4, ((ItemInfo) this.mItems.get(0)).position, ((ItemInfo) AlertController$$ExternalSyntheticOutline0.m(this.mItems, 1)).position);
        }
        return i4;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0065 A[RETURN] */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean dispatchKeyEvent(android.view.KeyEvent r6) {
        /*
            r5 = this;
            boolean r0 = super.dispatchKeyEvent(r6)
            r1 = 1
            if (r0 != 0) goto L66
            int r0 = r6.getAction()
            r2 = 0
            if (r0 != 0) goto L61
            int r0 = r6.getKeyCode()
            r3 = 21
            r4 = 2
            if (r0 == r3) goto L48
            r3 = 22
            if (r0 == r3) goto L36
            r3 = 61
            if (r0 == r3) goto L20
            goto L61
        L20:
            boolean r0 = r6.hasNoModifiers()
            if (r0 == 0) goto L2b
            boolean r5 = r5.arrowScroll(r4)
            goto L62
        L2b:
            boolean r6 = r6.hasModifiers(r1)
            if (r6 == 0) goto L61
            boolean r5 = r5.arrowScroll(r1)
            goto L62
        L36:
            boolean r6 = r6.hasModifiers(r4)
            if (r6 == 0) goto L41
            boolean r5 = r5.pageRight()
            goto L62
        L41:
            r6 = 66
            boolean r5 = r5.arrowScroll(r6)
            goto L62
        L48:
            boolean r6 = r6.hasModifiers(r4)
            if (r6 == 0) goto L5a
            int r6 = r5.mCurItem
            if (r6 <= 0) goto L61
            int r0 = r5.mLeftIncr
            int r6 = r6 + r0
            r5.setCurrentItem(r6, r1)
            r5 = r1
            goto L62
        L5a:
            r6 = 17
            boolean r5 = r5.arrowScroll(r6)
            goto L62
        L61:
            r5 = r2
        L62:
            if (r5 == 0) goto L65
            goto L66
        L65:
            return r2
        L66:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.dispatchKeyEvent(android.view.KeyEvent):boolean");
    }

    public final void dispatchOnPageSelected(int i) {
        OnPageChangeListener onPageChangeListener;
        OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListener;
        if (onPageChangeListener2 != null) {
            onPageChangeListener2.onPageSelected(i);
        }
        List list = this.mOnPageChangeListeners;
        if (list != null) {
            int size = ((ArrayList) list).size();
            for (int i2 = 0; i2 < size; i2++) {
                try {
                    onPageChangeListener = (OnPageChangeListener) ((ArrayList) this.mOnPageChangeListeners).get(i2);
                } catch (IndexOutOfBoundsException unused) {
                    StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "IndexOutOfBoundsException: Index: ", ", Size: ");
                    m.append(((ArrayList) this.mOnPageChangeListeners).size());
                    Log.e("ViewPager", m.toString());
                    onPageChangeListener = null;
                }
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(i);
                }
            }
        }
    }

    @Override // android.view.View
    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        ItemInfo infoForChild;
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0 && (infoForChild = infoForChild(childAt)) != null && infoForChild.position == this.mCurItem && childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        PagerAdapter pagerAdapter;
        super.draw(canvas);
        int overScrollMode = getOverScrollMode();
        boolean z = false;
        if (overScrollMode == 0 || (overScrollMode == 1 && (pagerAdapter = this.mAdapter) != null && pagerAdapter.getCount() > 1)) {
            if (!this.mLeftEdge.isFinished()) {
                int save = canvas.save();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                int width = getWidth();
                canvas.rotate(270.0f);
                canvas.translate(getPaddingTop() + (-height), this.mFirstOffset * width);
                this.mLeftEdge.setSize(height, width);
                z = this.mLeftEdge.draw(canvas);
                canvas.restoreToCount(save);
            }
            if (!this.mRightEdge.isFinished()) {
                int save2 = canvas.save();
                int width2 = getWidth();
                int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate(-getPaddingTop(), (-(this.mLastOffset + 1.0f)) * width2);
                this.mRightEdge.setSize(height2, width2);
                z |= this.mRightEdge.draw(canvas);
                canvas.restoreToCount(save2);
            }
        } else {
            this.mLeftEdge.finish();
            this.mRightEdge.finish();
        }
        if (z) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            postInvalidateOnAnimation();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
    }

    public void endFakeDrag() {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        if (this.mAdapter != null) {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
            int xVelocity = (int) velocityTracker.getXVelocity(this.mActivePointerId);
            this.mPopulatePending = true;
            int clientWidth = getClientWidth();
            int scrollX = getScrollX();
            ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
            setCurrentItemInternal(determineTargetPage(((scrollX / clientWidth) - infoForCurrentScrollPosition.offset) / infoForCurrentScrollPosition.widthFactor, infoForCurrentScrollPosition.position, xVelocity, (int) (this.mLastMotionX - this.mInitialMotionX)), xVelocity, true, true);
        }
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        VelocityTracker velocityTracker2 = this.mVelocityTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.recycle();
            this.mVelocityTracker = null;
        }
        this.mFakeDragging = false;
    }

    public void fakeDragBy(float f) {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        if (this.mAdapter == null) {
            return;
        }
        this.mLastMotionX += f;
        float scrollX = getScrollX() - f;
        float clientWidth = getClientWidth();
        float f2 = this.mFirstOffset * clientWidth;
        float f3 = this.mLastOffset * clientWidth;
        ItemInfo itemInfo = (ItemInfo) this.mItems.get(0);
        ItemInfo itemInfo2 = (ItemInfo) AlertController$$ExternalSyntheticOutline0.m(this.mItems, 1);
        if (itemInfo.position != 0) {
            f2 = itemInfo.offset * clientWidth;
        }
        if (itemInfo2.position != this.mAdapter.getCount() - 1) {
            f3 = itemInfo2.offset * clientWidth;
        }
        if (scrollX < f2) {
            scrollX = f2;
        } else if (scrollX > f3) {
            scrollX = f3;
        }
        int i = (int) scrollX;
        this.mLastMotionX = (scrollX - i) + this.mLastMotionX;
        scrollTo(i, getScrollY());
        pageScrolled(i);
        MotionEvent obtain = MotionEvent.obtain(this.mFakeDragBeginTime, SystemClock.uptimeMillis(), 2, this.mLastMotionX, 0.0f, 0);
        this.mVelocityTracker.addMovement(obtain);
        obtain.recycle();
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams();
    }

    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    @Override // android.view.ViewGroup
    public final int getChildDrawingOrder(int i, int i2) {
        throw null;
    }

    public final Rect getChildRectInPagerCoordinates(Rect rect, View view) {
        if (rect == null) {
            rect = new Rect();
        }
        if (view == null) {
            rect.set(0, 0, 0, 0);
            return rect;
        }
        rect.left = view.getLeft();
        rect.right = view.getRight();
        rect.top = view.getTop();
        rect.bottom = view.getBottom();
        ViewParent parent = view.getParent();
        while ((parent instanceof ViewGroup) && parent != this) {
            ViewGroup viewGroup = (ViewGroup) parent;
            rect.left = viewGroup.getLeft() + rect.left;
            rect.right = viewGroup.getRight() + rect.right;
            rect.top = viewGroup.getTop() + rect.top;
            rect.bottom = viewGroup.getBottom() + rect.bottom;
            parent = viewGroup.getParent();
        }
        return rect;
    }

    public final int getClientWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    public final ItemInfo infoForChild(View view) {
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(i);
            if (this.mAdapter.isViewFromObject(view, itemInfo.object)) {
                return itemInfo;
            }
        }
        return null;
    }

    public final ItemInfo infoForCurrentScrollPosition() {
        int i;
        int scrollX = getScrollX();
        int clientWidth = getClientWidth();
        float f = 0.0f;
        float f2 = clientWidth > 0 ? scrollX / clientWidth : 0.0f;
        float f3 = clientWidth > 0 ? this.mPageMargin / clientWidth : 0.0f;
        boolean z = true;
        int i2 = 0;
        ItemInfo itemInfo = null;
        int i3 = -1;
        float f4 = 0.0f;
        while (i2 < this.mItems.size()) {
            ItemInfo itemInfo2 = (ItemInfo) this.mItems.get(i2);
            if (!z && itemInfo2.position != (i = i3 + 1)) {
                itemInfo2 = this.mTempItem;
                itemInfo2.offset = f + f4 + f3;
                itemInfo2.position = i;
                this.mAdapter.getClass();
                itemInfo2.widthFactor = 1.0f;
                i2--;
            }
            ItemInfo itemInfo3 = itemInfo2;
            f = itemInfo3.offset;
            float f5 = itemInfo3.widthFactor + f + f3;
            if (!z && f2 < f) {
                break;
            }
            if (f2 < f5 || i2 == this.mItems.size() - 1) {
                return itemInfo3;
            }
            int i4 = itemInfo3.position;
            float f6 = itemInfo3.widthFactor;
            i2++;
            i3 = i4;
            f4 = f6;
            itemInfo = itemInfo3;
            z = false;
        }
        return itemInfo;
    }

    public final ItemInfo infoForPosition(int i) {
        for (int i2 = 0; i2 < this.mItems.size(); i2++) {
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(i2);
            if (itemInfo.position == i) {
                return itemInfo;
            }
        }
        return null;
    }

    public final void initViewPager(Context context) {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        float f = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        viewConfiguration.getScaledTouchSlop();
        viewConfiguration.getScaledPagingTouchSlop();
        this.mMinimumVelocity = (int) (400.0f * f);
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new EdgeEffect(context);
        this.mRightEdge = new EdgeEffect(context);
        this.mFlingDistance = (int) (25.0f * f);
        this.mCloseEnough = (int) (2.0f * f);
        this.mDefaultGutterSize = (int) (f * 16.0f);
        ViewCompat.setAccessibilityDelegate(this, new MyAccessibilityDelegate());
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() { // from class: androidx.viewpager.widget.ViewPager.4
            public final Rect mTempRect = new Rect();

            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(WindowInsetsCompat windowInsetsCompat, View view) {
                WindowInsetsCompat onApplyWindowInsets = ViewCompat.onApplyWindowInsets(windowInsetsCompat, view);
                if (onApplyWindowInsets.mImpl.isConsumed()) {
                    return onApplyWindowInsets;
                }
                Rect rect = this.mTempRect;
                rect.left = onApplyWindowInsets.getSystemWindowInsetLeft();
                rect.top = onApplyWindowInsets.getSystemWindowInsetTop();
                rect.right = onApplyWindowInsets.getSystemWindowInsetRight();
                rect.bottom = onApplyWindowInsets.getSystemWindowInsetBottom();
                ViewPager viewPager = ViewPager.this;
                int childCount = viewPager.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    WindowInsetsCompat dispatchApplyWindowInsets = ViewCompat.dispatchApplyWindowInsets(onApplyWindowInsets, viewPager.getChildAt(i));
                    rect.left = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetLeft(), rect.left);
                    rect.top = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetTop(), rect.top);
                    rect.right = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetRight(), rect.right);
                    rect.bottom = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetBottom(), rect.bottom);
                }
                WindowInsetsCompat.Builder builder = new WindowInsetsCompat.Builder(onApplyWindowInsets);
                Insets of = Insets.of(rect.left, rect.top, rect.right, rect.bottom);
                WindowInsetsCompat.BuilderImpl30 builderImpl30 = builder.mImpl;
                builderImpl30.setSystemWindowInsets(of);
                return builderImpl30.build();
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        removeCallbacks(this.mEndScrollRunnable);
        Scroller scroller = this.mScroller;
        if (scroller != null && !scroller.isFinished()) {
            this.mScroller.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int findPointerIndex;
        int action = motionEvent.getAction() & 255;
        if (action == 3 || action == 1) {
            resetTouch();
            return false;
        }
        if (action != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return false;
            }
        }
        if (action == 0) {
            float x = motionEvent.getX();
            this.mInitialMotionX = x;
            this.mLastMotionX = x;
            float y = motionEvent.getY();
            this.mInitialMotionY = y;
            this.mLastMotionY = y;
            this.mActivePointerId = motionEvent.getPointerId(0);
            this.mIsUnableToDrag = false;
            this.mIsScrollStarted = true;
            this.mScroller.computeScrollOffset();
            if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough) {
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                populate();
                this.mIsBeingDragged = true;
                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                setScrollState(1);
            } else if (EdgeEffectCompat.getDistance(this.mLeftEdge) == 0.0f && EdgeEffectCompat.getDistance(this.mRightEdge) == 0.0f) {
                completeScroll(false);
                this.mIsBeingDragged = false;
            } else {
                this.mIsBeingDragged = true;
                setScrollState(1);
                if (EdgeEffectCompat.getDistance(this.mLeftEdge) != 0.0f) {
                    EdgeEffectCompat.onPullDistance(this.mLeftEdge, 0.0f, 1.0f - (this.mLastMotionY / getHeight()));
                }
                if (EdgeEffectCompat.getDistance(this.mRightEdge) != 0.0f) {
                    EdgeEffectCompat.onPullDistance(this.mRightEdge, 0.0f, this.mLastMotionY / getHeight());
                }
            }
        } else if (action == 2) {
            int i = this.mActivePointerId;
            if (i != -1 && (findPointerIndex = motionEvent.findPointerIndex(i)) != -1) {
                float x2 = motionEvent.getX(findPointerIndex);
                float f = x2 - this.mLastMotionX;
                float abs = Math.abs(f);
                float y2 = motionEvent.getY(findPointerIndex);
                float abs2 = Math.abs(y2 - this.mInitialMotionY);
                if (f != 0.0f) {
                    float f2 = this.mLastMotionX;
                    if ((this.mDragInGutterEnabled || ((f2 >= this.mGutterSize || f <= 0.0f) && (f2 <= getWidth() - this.mGutterSize || f >= 0.0f))) && canScroll((int) f, (int) x2, (int) y2, this, false)) {
                        this.mLastMotionX = x2;
                        this.mLastMotionY = y2;
                        this.mIsUnableToDrag = true;
                        return false;
                    }
                }
                float f3 = this.mTouchSlop;
                if (abs > f3 && abs * this.mTouchSlopRatio > abs2) {
                    this.mIsBeingDragged = true;
                    ViewParent parent2 = getParent();
                    if (parent2 != null) {
                        parent2.requestDisallowInterceptTouchEvent(true);
                    }
                    setScrollState(1);
                    this.mLastMotionX = f > 0.0f ? this.mInitialMotionX + this.mTouchSlop : this.mInitialMotionX - this.mTouchSlop;
                    this.mLastMotionY = y2;
                    setScrollingCacheEnabled(true);
                } else if (abs2 > f3) {
                    this.mIsUnableToDrag = true;
                }
                if (this.mIsBeingDragged && performDrag(x2, y2)) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    postInvalidateOnAnimation();
                }
            }
        } else if (action == 6) {
            onSecondaryPointerUp(motionEvent);
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        return this.mIsBeingDragged;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0094  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onLayout(boolean r19, int r20, int r21, int r22, int r23) {
        /*
            Method dump skipped, instructions count: 281
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.onLayout(boolean, int, int, int, int):void");
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        LayoutParams layoutParams;
        LayoutParams layoutParams2;
        int i3;
        setMeasuredDimension(ViewGroup.getDefaultSize(0, i), ViewGroup.getDefaultSize(0, i2));
        int measuredWidth = getMeasuredWidth();
        this.mGutterSize = Math.min(measuredWidth / 10, this.mDefaultGutterSize);
        int paddingLeft = (measuredWidth - getPaddingLeft()) - getPaddingRight();
        int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int childCount = getChildCount();
        int i4 = 0;
        while (true) {
            boolean z = true;
            int i5 = 1073741824;
            if (i4 >= childCount) {
                break;
            }
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8 && (layoutParams2 = (LayoutParams) childAt.getLayoutParams()) != null && layoutParams2.isDecor) {
                int i6 = layoutParams2.gravity;
                int i7 = i6 & 7;
                int i8 = i6 & 112;
                boolean z2 = i8 == 48 || i8 == 80;
                if (i7 != 3 && i7 != 5) {
                    z = false;
                }
                int i9 = Integer.MIN_VALUE;
                if (z2) {
                    i3 = Integer.MIN_VALUE;
                    i9 = 1073741824;
                } else {
                    i3 = z ? 1073741824 : Integer.MIN_VALUE;
                }
                int i10 = ((ViewGroup.LayoutParams) layoutParams2).width;
                if (i10 != -2) {
                    if (i10 == -1) {
                        i10 = paddingLeft;
                    }
                    i9 = 1073741824;
                } else {
                    i10 = paddingLeft;
                }
                int i11 = ((ViewGroup.LayoutParams) layoutParams2).height;
                if (i11 == -2) {
                    i11 = measuredHeight;
                    i5 = i3;
                } else if (i11 == -1) {
                    i11 = measuredHeight;
                }
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i10, i9), View.MeasureSpec.makeMeasureSpec(i11, i5));
                if (z2) {
                    measuredHeight -= childAt.getMeasuredHeight();
                } else if (z) {
                    paddingLeft -= childAt.getMeasuredWidth();
                }
            }
            i4++;
        }
        View.MeasureSpec.makeMeasureSpec(paddingLeft, 1073741824);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824);
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        int childCount2 = getChildCount();
        for (int i12 = 0; i12 < childCount2; i12++) {
            View childAt2 = getChildAt(i12);
            if (childAt2.getVisibility() != 8 && (layoutParams = (LayoutParams) childAt2.getLayoutParams()) != null && !layoutParams.isDecor) {
                childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (paddingLeft * layoutParams.widthFactor), 1073741824), makeMeasureSpec);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPageScrolled(float r12, int r13, int r14) {
        /*
            r11 = this;
            int r14 = r11.mDecorChildCount
            r0 = 0
            r1 = 1
            if (r14 <= 0) goto L6c
            int r14 = r11.getScrollX()
            int r2 = r11.getPaddingLeft()
            int r3 = r11.getPaddingRight()
            int r4 = r11.getWidth()
            int r5 = r11.getChildCount()
            r6 = r0
        L1b:
            if (r6 >= r5) goto L6c
            android.view.View r7 = r11.getChildAt(r6)
            android.view.ViewGroup$LayoutParams r8 = r7.getLayoutParams()
            androidx.viewpager.widget.ViewPager$LayoutParams r8 = (androidx.viewpager.widget.ViewPager.LayoutParams) r8
            boolean r9 = r8.isDecor
            if (r9 != 0) goto L2c
            goto L69
        L2c:
            int r8 = r8.gravity
            r8 = r8 & 7
            if (r8 == r1) goto L50
            r9 = 3
            if (r8 == r9) goto L4a
            r9 = 5
            if (r8 == r9) goto L3a
            r8 = r2
            goto L5d
        L3a:
            int r8 = r4 - r3
            int r9 = r7.getMeasuredWidth()
            int r8 = r8 - r9
            int r9 = r7.getMeasuredWidth()
            int r3 = r3 + r9
        L46:
            r10 = r8
            r8 = r2
            r2 = r10
            goto L5d
        L4a:
            int r8 = r7.getWidth()
            int r8 = r8 + r2
            goto L5d
        L50:
            int r8 = r7.getMeasuredWidth()
            int r8 = r4 - r8
            int r8 = r8 / 2
            int r8 = java.lang.Math.max(r8, r2)
            goto L46
        L5d:
            int r2 = r2 + r14
            int r9 = r7.getLeft()
            int r2 = r2 - r9
            if (r2 == 0) goto L68
            r7.offsetLeftAndRight(r2)
        L68:
            r2 = r8
        L69:
            int r6 = r6 + 1
            goto L1b
        L6c:
            androidx.viewpager.widget.ViewPager$OnPageChangeListener r14 = r11.mOnPageChangeListener
            if (r14 == 0) goto L73
            r14.onPageScrolled(r12, r13)
        L73:
            java.util.List r14 = r11.mOnPageChangeListeners
            if (r14 == 0) goto Laf
            java.util.ArrayList r14 = (java.util.ArrayList) r14
            int r14 = r14.size()
        L7d:
            if (r0 >= r14) goto Laf
            java.util.List r2 = r11.mOnPageChangeListeners     // Catch: java.lang.IndexOutOfBoundsException -> L8a
            java.util.ArrayList r2 = (java.util.ArrayList) r2     // Catch: java.lang.IndexOutOfBoundsException -> L8a
            java.lang.Object r2 = r2.get(r0)     // Catch: java.lang.IndexOutOfBoundsException -> L8a
            androidx.viewpager.widget.ViewPager$OnPageChangeListener r2 = (androidx.viewpager.widget.ViewPager.OnPageChangeListener) r2     // Catch: java.lang.IndexOutOfBoundsException -> L8a
            goto La7
        L8a:
            java.lang.String r2 = "IndexOutOfBoundsException: Index: "
            java.lang.String r3 = ", Size: "
            java.lang.StringBuilder r2 = android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r0, r2, r3)
            java.util.List r3 = r11.mOnPageChangeListeners
            java.util.ArrayList r3 = (java.util.ArrayList) r3
            int r3 = r3.size()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "ViewPager"
            android.util.Log.e(r3, r2)
            r2 = 0
        La7:
            if (r2 == 0) goto Lac
            r2.onPageScrolled(r12, r13)
        Lac:
            int r0 = r0 + 1
            goto L7d
        Laf:
            r11.mCalledSuper = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.onPageScrolled(float, int, int):void");
    }

    @Override // android.view.ViewGroup
    public final boolean onRequestFocusInDescendants(int i, Rect rect) {
        int i2;
        int i3;
        int i4;
        ItemInfo infoForChild;
        int childCount = getChildCount();
        if ((i & 2) != 0) {
            i3 = childCount;
            i2 = 0;
            i4 = 1;
        } else {
            i2 = childCount - 1;
            i3 = -1;
            i4 = -1;
        }
        while (i2 != i3) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (infoForChild = infoForChild(childAt)) != null && infoForChild.position == this.mCurItem && childAt.requestFocus(i, rect)) {
                return true;
            }
            i2 += i4;
        }
        return false;
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.mSuperState);
        if (this.mAdapter != null) {
            setCurrentItemInternal(savedState.position, 0, false, true);
        } else {
            this.mRestoredCurItem = savedState.position;
        }
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.mCurItem;
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter != null) {
            pagerAdapter.getClass();
            savedState.adapterState = null;
        }
        return savedState;
    }

    public final void onSecondaryPointerUp(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            this.mLastMotionX = motionEvent.getX(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            int i5 = this.mPageMargin;
            recomputeScrollPosition(i, i3, i5, i5);
            if (this.mPageMargin > 0) {
                setCurrentItemInternal(this.mCurItem, 0, false, true);
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        PagerAdapter pagerAdapter;
        if (!this.mFakeDragging) {
            boolean z = false;
            if ((motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) || (pagerAdapter = this.mAdapter) == null || pagerAdapter.getCount() == 0) {
                return false;
            }
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(motionEvent);
            int action = motionEvent.getAction() & 255;
            if (action == 0) {
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                populate();
                float x = motionEvent.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                float y = motionEvent.getY();
                this.mInitialMotionY = y;
                this.mLastMotionY = y;
                this.mActivePointerId = motionEvent.getPointerId(0);
            } else if (action != 1) {
                if (action == 2) {
                    if (!this.mIsBeingDragged) {
                        int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                        if (findPointerIndex == -1) {
                            z = resetTouch();
                        } else {
                            float x2 = motionEvent.getX(findPointerIndex);
                            float abs = Math.abs(x2 - this.mLastMotionX);
                            float y2 = motionEvent.getY(findPointerIndex);
                            float abs2 = Math.abs(y2 - this.mLastMotionY);
                            if (abs > this.mTouchSlop && abs > abs2) {
                                this.mIsBeingDragged = true;
                                ViewParent parent = getParent();
                                if (parent != null) {
                                    parent.requestDisallowInterceptTouchEvent(true);
                                }
                                float f = this.mInitialMotionX;
                                this.mLastMotionX = x2 - f > 0.0f ? f + this.mTouchSlop : f - this.mTouchSlop;
                                this.mLastMotionY = y2;
                                setScrollState(1);
                                setScrollingCacheEnabled(true);
                                ViewParent parent2 = getParent();
                                if (parent2 != null) {
                                    parent2.requestDisallowInterceptTouchEvent(true);
                                }
                            }
                        }
                    }
                    if (this.mIsBeingDragged) {
                        int findPointerIndex2 = motionEvent.findPointerIndex(this.mActivePointerId);
                        z = findPointerIndex2 == -1 ? resetTouch() : performDrag(motionEvent.getX(findPointerIndex2), motionEvent.getY(findPointerIndex2));
                    }
                } else if (action != 3) {
                    if (action == 5) {
                        int actionIndex = motionEvent.getActionIndex();
                        this.mLastMotionX = motionEvent.getX(actionIndex);
                        this.mActivePointerId = motionEvent.getPointerId(actionIndex);
                    } else if (action == 6) {
                        onSecondaryPointerUp(motionEvent);
                        int findPointerIndex3 = motionEvent.findPointerIndex(this.mActivePointerId);
                        if (findPointerIndex3 == -1) {
                            z = resetTouch();
                        } else {
                            this.mLastMotionX = motionEvent.getX(findPointerIndex3);
                        }
                    }
                } else if (this.mIsBeingDragged) {
                    scrollToItem(this.mCurItem, 0, true, false);
                    z = resetTouch();
                }
            } else if (this.mIsBeingDragged) {
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                int xVelocity = (int) velocityTracker.getXVelocity(this.mActivePointerId);
                this.mPopulatePending = true;
                float clientWidth = getClientWidth();
                float scrollX = getScrollX() / clientWidth;
                ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
                float f2 = this.mPageMargin / clientWidth;
                int i = infoForCurrentScrollPosition.position;
                float f3 = (scrollX - infoForCurrentScrollPosition.offset) / (infoForCurrentScrollPosition.widthFactor + f2);
                int findPointerIndex4 = motionEvent.findPointerIndex(this.mActivePointerId);
                if (findPointerIndex4 == -1) {
                    z = resetTouch();
                } else {
                    int determineTargetPage = determineTargetPage(f3, i, xVelocity, (int) (motionEvent.getX(findPointerIndex4) - this.mInitialMotionX));
                    setCurrentItemInternal(determineTargetPage, xVelocity, true, true);
                    boolean resetTouch = resetTouch();
                    if (determineTargetPage == i && resetTouch) {
                        if (EdgeEffectCompat.getDistance(this.mRightEdge) != 0.0f) {
                            this.mRightEdge.onAbsorb(-xVelocity);
                        } else if (EdgeEffectCompat.getDistance(this.mLeftEdge) != 0.0f) {
                            this.mLeftEdge.onAbsorb(xVelocity);
                        }
                    }
                    z = resetTouch;
                }
            }
            if (z) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                postInvalidateOnAnimation();
            }
        }
        return true;
    }

    public final boolean pageRight() {
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter == null || this.mCurItem >= pagerAdapter.getCount() - 1) {
            return false;
        }
        setCurrentItem(this.mCurItem - this.mLeftIncr, true);
        return true;
    }

    public final boolean pageScrolled(int i) {
        if (this.mItems.size() == 0) {
            if (!this.mFirstLayout) {
                this.mCalledSuper = false;
                onPageScrolled(0.0f, 0, 0);
                if (!this.mCalledSuper) {
                    throw new IllegalStateException("onPageScrolled did not call superclass implementation");
                }
            }
            return false;
        }
        ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
        int clientWidth = getClientWidth();
        int i2 = this.mPageMargin;
        int i3 = clientWidth + i2;
        float f = clientWidth;
        int i4 = infoForCurrentScrollPosition.position;
        float f2 = ((i / f) - infoForCurrentScrollPosition.offset) / (infoForCurrentScrollPosition.widthFactor + (i2 / f));
        this.mCalledSuper = false;
        onPageScrolled(f2, i4, (int) (i3 * f2));
        if (this.mCalledSuper) {
            return true;
        }
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }

    public final boolean performDrag(float f, float f2) {
        float f3;
        float f4;
        float f5;
        float f6;
        EdgeEffect edgeEffect = this.mLeftEdge;
        EdgeEffect edgeEffect2 = this.mRightEdge;
        float f7 = this.mLastMotionX - f;
        this.mLastMotionX = f;
        float height = f2 / getHeight();
        float width = f7 / getWidth();
        float onPullDistance = (EdgeEffectCompat.getDistance(this.mLeftEdge) != 0.0f ? -EdgeEffectCompat.onPullDistance(this.mLeftEdge, -width, 1.0f - height) : EdgeEffectCompat.getDistance(this.mRightEdge) != 0.0f ? EdgeEffectCompat.onPullDistance(this.mRightEdge, width, height) : 0.0f) * getWidth();
        float f8 = f7 - onPullDistance;
        boolean z = true;
        boolean z2 = onPullDistance != 0.0f;
        if (Math.abs(f8) < 1.0E-4f) {
            return z2;
        }
        float scrollX = getScrollX() + f8;
        int clientWidth = getClientWidth();
        ItemInfo itemInfo = (ItemInfo) this.mItems.get(0);
        ItemInfo itemInfo2 = (ItemInfo) AlertController$$ExternalSyntheticOutline0.m(this.mItems, 1);
        boolean z3 = itemInfo.position == 0;
        if (z3) {
            f3 = clientWidth;
            f4 = this.mFirstOffset;
        } else {
            f3 = itemInfo.offset;
            f4 = clientWidth;
        }
        float f9 = f3 * f4;
        boolean z4 = itemInfo2.position == this.mAdapter.getCount() - 1;
        if (z4) {
            f5 = clientWidth;
            f6 = this.mLastOffset;
        } else {
            f5 = itemInfo2.offset;
            f6 = clientWidth;
        }
        float f10 = f5 * f6;
        if (scrollX < f9) {
            if (z3) {
                EdgeEffectCompat.onPullDistance(edgeEffect, (f9 - scrollX) / clientWidth, 1.0f - (f2 / getHeight()));
            } else {
                z = z2;
            }
            z2 = z;
            scrollX = f9;
        } else if (scrollX > f10) {
            if (z4) {
                EdgeEffectCompat.onPullDistance(edgeEffect2, (scrollX - f10) / clientWidth, f2 / getHeight());
            } else {
                z = z2;
            }
            z2 = z;
            scrollX = f10;
        }
        int i = (int) scrollX;
        this.mLastMotionX = (scrollX - i) + this.mLastMotionX;
        scrollTo(i, getScrollY());
        pageScrolled(i);
        return z2;
    }

    public final void populate() {
        populate(this.mCurItem);
    }

    public final void recomputeScrollPosition(int i, int i2, int i3, int i4) {
        if (i2 > 0 && !this.mItems.isEmpty()) {
            if (!this.mScroller.isFinished()) {
                this.mScroller.setFinalX(getClientWidth() * getCurrentItem());
                return;
            } else {
                scrollTo((int) ((getScrollX() / (((i2 - getPaddingLeft()) - getPaddingRight()) + i4)) * (((i - getPaddingLeft()) - getPaddingRight()) + i3)), getScrollY());
                return;
            }
        }
        ItemInfo infoForPosition = infoForPosition(this.mCurItem);
        int min = (int) ((infoForPosition != null ? Math.min(infoForPosition.offset, this.mLastOffset) : 0.0f) * ((i - getPaddingLeft()) - getPaddingRight()));
        if (min != getScrollX()) {
            completeScroll(false);
            scrollTo(min, getScrollY());
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void removeView(View view) {
        if (this.mInLayout) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    public final boolean resetTouch() {
        this.mActivePointerId = -1;
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        this.mLeftEdge.onRelease();
        this.mRightEdge.onRelease();
        return (this.mLeftEdge.isFinished() && this.mRightEdge.isFinished()) ? false : true;
    }

    public final void scrollToItem(int i, int i2, boolean z, boolean z2) {
        int scrollX;
        int abs;
        ItemInfo infoForPosition = infoForPosition(i);
        int clamp = infoForPosition != null ? (int) (MathUtils.clamp(infoForPosition.offset, this.mFirstOffset, this.mLastOffset) * getClientWidth()) : 0;
        if (!z) {
            if (z2) {
                dispatchOnPageSelected(i);
            }
            completeScroll(false);
            scrollTo(clamp, 0);
            pageScrolled(clamp);
            return;
        }
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
        } else {
            Scroller scroller = this.mScroller;
            if (scroller == null || scroller.isFinished()) {
                scrollX = getScrollX();
            } else {
                scrollX = this.mIsScrollStarted ? this.mScroller.getCurrX() : this.mScroller.getStartX();
                this.mScroller.abortAnimation();
                setScrollingCacheEnabled(false);
            }
            int i3 = scrollX;
            int scrollY = getScrollY();
            int i4 = clamp - i3;
            int i5 = 0 - scrollY;
            if (i4 == 0 && i5 == 0) {
                completeScroll(false);
                populate();
                setScrollState(0);
            } else {
                setScrollingCacheEnabled(true);
                setScrollState(2);
                int clientWidth = getClientWidth();
                int i6 = clientWidth / 2;
                float f = clientWidth;
                float f2 = i6;
                float sin = (((float) Math.sin((Math.min(1.0f, (Math.abs(i4) * 1.0f) / f) - 0.5f) * 0.47123894f)) * f2) + f2;
                int abs2 = Math.abs(i2);
                if (abs2 > 0) {
                    abs = Math.round(Math.abs(sin / abs2) * 1000.0f) * 4;
                } else {
                    this.mAdapter.getClass();
                    abs = (int) (((Math.abs(i4) / ((f * 1.0f) + this.mPageMargin)) + 1.0f) * 100.0f);
                }
                int min = Math.min(abs, VolteConstants.ErrorCode.BUSY_EVERYWHERE);
                this.mIsScrollStarted = false;
                Scroller scroller2 = this.mScroller;
                if (scroller2 != null) {
                    scroller2.startScroll(i3, scrollY, i4, i5, min);
                }
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                postInvalidateOnAnimation();
            }
        }
        if (z2) {
            dispatchOnPageSelected(i);
        }
    }

    public final void setAdapter(PagerAdapter pagerAdapter) {
        PagerAdapter pagerAdapter2 = this.mAdapter;
        if (pagerAdapter2 != null) {
            synchronized (pagerAdapter2) {
                pagerAdapter2.mViewPagerObserver = null;
            }
            this.mAdapter.getClass();
            for (int i = 0; i < this.mItems.size(); i++) {
                ItemInfo itemInfo = (ItemInfo) this.mItems.get(i);
                this.mAdapter.destroyItem(this, itemInfo.position, itemInfo.object);
            }
            this.mAdapter.getClass();
            this.mItems.clear();
            int i2 = 0;
            while (i2 < getChildCount()) {
                if (!((LayoutParams) getChildAt(i2).getLayoutParams()).isDecor) {
                    removeViewAt(i2);
                    i2--;
                }
                i2++;
            }
            this.mCurItem = 0;
            scrollTo(0, 0);
        }
        this.mAdapter = pagerAdapter;
        this.mExpectedAdapterCount = 0;
        if (pagerAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            PagerAdapter pagerAdapter3 = this.mAdapter;
            PagerObserver pagerObserver = this.mObserver;
            synchronized (pagerAdapter3) {
                pagerAdapter3.mViewPagerObserver = pagerObserver;
            }
            this.mPopulatePending = false;
            boolean z = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.getClass();
                setCurrentItemInternal(this.mRestoredCurItem, 0, false, true);
                this.mRestoredCurItem = -1;
            } else if (z) {
                requestLayout();
            } else {
                populate();
            }
        }
        List list = this.mAdapterChangeListeners;
        if (list == null || ((ArrayList) list).isEmpty()) {
            return;
        }
        int size = ((ArrayList) this.mAdapterChangeListeners).size();
        for (int i3 = 0; i3 < size; i3++) {
            TabLayout.AdapterChangeListener adapterChangeListener = (TabLayout.AdapterChangeListener) ((ArrayList) this.mAdapterChangeListeners).get(i3);
            TabLayout tabLayout = TabLayout.this;
            if (tabLayout.viewPager == this) {
                tabLayout.setPagerAdapter(pagerAdapter, adapterChangeListener.autoRefresh);
            }
        }
    }

    public final void setCurrentItem(int i) {
        this.mPopulatePending = false;
        setCurrentItemInternal(i, 0, !this.mFirstLayout, false);
    }

    public final void setCurrentItemInternal(int i, int i2, boolean z, boolean z2) {
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter == null || pagerAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (!z2 && this.mCurItem == i && this.mItems.size() != 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (i < 0) {
            i = 0;
        } else if (i >= this.mAdapter.getCount()) {
            i = this.mAdapter.getCount() - 1;
        }
        int i3 = this.mOffscreenPageLimit;
        int i4 = this.mCurItem;
        if (i > i4 + i3 || i < i4 - i3) {
            for (int i5 = 0; i5 < this.mItems.size(); i5++) {
                ((ItemInfo) this.mItems.get(i5)).scrolling = true;
            }
        }
        boolean z3 = this.mCurItem != i;
        if (!this.mFirstLayout) {
            populate(i);
            scrollToItem(i, i2, z, z3);
        } else {
            this.mCurItem = i;
            if (z3) {
                dispatchOnPageSelected(i);
            }
            requestLayout();
        }
    }

    public final void setOffscreenPageLimit() {
        if (1 != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = 1;
            populate();
        }
    }

    public final void setScrollState(int i) {
        OnPageChangeListener onPageChangeListener;
        if (this.mScrollState == i) {
            return;
        }
        this.mScrollState = i;
        OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListener;
        if (onPageChangeListener2 != null) {
            onPageChangeListener2.onPageScrollStateChanged(i);
        }
        List list = this.mOnPageChangeListeners;
        if (list != null) {
            int size = ((ArrayList) list).size();
            for (int i2 = 0; i2 < size; i2++) {
                try {
                    onPageChangeListener = (OnPageChangeListener) ((ArrayList) this.mOnPageChangeListeners).get(i2);
                } catch (IndexOutOfBoundsException unused) {
                    StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "IndexOutOfBoundsException: Index: ", ", Size: ");
                    m.append(((ArrayList) this.mOnPageChangeListeners).size());
                    Log.e("ViewPager", m.toString());
                    onPageChangeListener = null;
                }
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(i);
                }
            }
        }
    }

    public final void setScrollingCacheEnabled(boolean z) {
        if (this.mScrollingCacheEnabled != z) {
            this.mScrollingCacheEnabled = z;
        }
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == null;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LayoutParams extends ViewGroup.LayoutParams {
        public final int gravity;
        public boolean isDecor;
        public boolean needsMeasure;
        public int position;
        public float widthFactor;

        public LayoutParams() {
            super(-1, -1);
            this.widthFactor = 0.0f;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.widthFactor = 0.0f;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ViewPager.LAYOUT_ATTRS);
            this.gravity = obtainStyledAttributes.getInteger(0, 48);
            obtainStyledAttributes.recycle();
        }
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x005d, code lost:
    
        if (r9 == r10) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void populate(int r18) {
        /*
            Method dump skipped, instructions count: 914
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.populate(int):void");
    }

    public void setCurrentItem(int i, boolean z) {
        this.mPopulatePending = false;
        setCurrentItemInternal(i, 0, z, false);
    }

    public ViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mItems = new ArrayList();
        this.mTempItem = new ItemInfo();
        this.mTempRect = new Rect();
        this.mRestoredCurItem = -1;
        this.mFirstOffset = -3.4028235E38f;
        this.mLastOffset = Float.MAX_VALUE;
        this.mOffscreenPageLimit = 1;
        this.mDragInGutterEnabled = true;
        this.mActivePointerId = -1;
        this.mFirstLayout = true;
        this.mEndScrollRunnable = new AnonymousClass3();
        this.mScrollState = 0;
        this.mTouchSlopRatio = 0.5f;
        this.mLeftIncr = -1;
        initViewPager(context);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SimpleOnPageChangeListener implements OnPageChangeListener {
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(float f, int i) {
        }
    }
}
