package androidx.viewpager.widget;

import android.R;
import android.content.Context;
import android.content.res.Resources;
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
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
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
    public Parcelable mRestoredAdapterState;
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

    /* renamed from: androidx.viewpager.widget.ViewPager$3, reason: invalid class name */
    public class AnonymousClass3 implements Runnable {
        public AnonymousClass3() {
        }

        @Override // java.lang.Runnable
        public final void run() throws Resources.NotFoundException {
            ViewPager.this.setScrollState(0);
            ViewPager.this.populate();
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface DecorView {
    }

    public class ItemInfo {
        public Object object;
        public float offset;
        public int position;
        public boolean scrolling;
        public float widthFactor;
    }

    public class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
        public MyAccessibilityDelegate() {
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
        @Override // androidx.core.view.AccessibilityDelegateCompat
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            boolean z;
            PagerAdapter pagerAdapter;
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName("androidx.viewpager.widget.ViewPager");
            ViewPager viewPager = ViewPager.this;
            PagerAdapter pagerAdapter2 = viewPager.mAdapter;
            if (pagerAdapter2 != null) {
                z = pagerAdapter2.getCount() > 1;
            }
            accessibilityEvent.setScrollable(z);
            if (accessibilityEvent.getEventType() != 4096 || (pagerAdapter = viewPager.mAdapter) == null) {
                return;
            }
            accessibilityEvent.setItemCount(pagerAdapter.getCount());
            accessibilityEvent.setFromIndex(viewPager.mCurItem);
            accessibilityEvent.setToIndex(viewPager.mCurItem);
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

    public interface OnPageChangeListener {
        void onPageScrollStateChanged(int i);

        void onPageScrolled(float f, int i);

        void onPageSelected(int i);
    }

    public class PagerObserver extends DataSetObserver {
        public PagerObserver() {
        }

        @Override // android.database.DataSetObserver
        public final void onChanged() throws Resources.NotFoundException {
            ViewPager.this.dataSetChanged();
        }

        @Override // android.database.DataSetObserver
        public final void onInvalidated() throws Resources.NotFoundException {
            ViewPager.this.dataSetChanged();
        }
    }

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
        this.mRestoredAdapterState = null;
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
        ItemInfo itemInfoInfoForChild;
        if (arrayList == null) {
            return;
        }
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                View childAt = getChildAt(i3);
                if (childAt.getVisibility() == 0 && (itemInfoInfoForChild = infoForChild(childAt)) != null && itemInfoInfoForChild.position == this.mCurItem) {
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
        ItemInfo itemInfoInfoForChild;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0 && (itemInfoInfoForChild = infoForChild(childAt)) != null && itemInfoInfoForChild.position == this.mCurItem) {
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

    public final boolean arrowScroll(int i) {
        boolean zRequestFocus;
        View viewFindFocus = findFocus();
        if (viewFindFocus == this) {
            viewFindFocus = null;
        } else if (viewFindFocus != null) {
            for (ViewParent parent = viewFindFocus.getParent(); parent instanceof ViewGroup; parent = parent.getParent()) {
                if (parent == this) {
                    break;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(viewFindFocus.getClass().getSimpleName());
            for (ViewParent parent2 = viewFindFocus.getParent(); parent2 instanceof ViewGroup; parent2 = parent2.getParent()) {
                sb.append(" => ");
                sb.append(parent2.getClass().getSimpleName());
            }
            Log.e("ViewPager", "arrowScroll tried to find focus based on non-child current focused view " + sb.toString());
            viewFindFocus = null;
        }
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, i);
        boolean zPageRight = false;
        if (viewFindNextFocus == null || viewFindNextFocus == viewFindFocus) {
            if (i == 17 || i == 1) {
                int i2 = this.mCurItem;
                if (i2 > 0) {
                    setCurrentItem(i2 + this.mLeftIncr, true);
                    zPageRight = true;
                }
            } else if (i == 66 || i == 2) {
                zPageRight = pageRight();
            }
        } else if (i == 17) {
            int i3 = getChildRectInPagerCoordinates(this.mTempRect, viewFindNextFocus).left;
            int i4 = getChildRectInPagerCoordinates(this.mTempRect, viewFindFocus).left;
            if (viewFindFocus == null || i3 < i4) {
                zRequestFocus = viewFindNextFocus.requestFocus();
                zPageRight = zRequestFocus;
            } else {
                int i5 = this.mCurItem;
                if (i5 > 0) {
                    setCurrentItem(i5 + this.mLeftIncr, true);
                    zPageRight = true;
                }
            }
        } else if (i == 66) {
            zRequestFocus = (viewFindFocus == null || getChildRectInPagerCoordinates(this.mTempRect, viewFindNextFocus).left > getChildRectInPagerCoordinates(this.mTempRect, viewFindFocus).left) ? viewFindNextFocus.requestFocus() : pageRight();
            zPageRight = zRequestFocus;
        }
        if (zPageRight) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
        }
        return zPageRight;
    }

    public boolean canScroll(int i, int i2, int i3, View view, boolean z) {
        int i4;
        if (!(view instanceof ViewGroup)) {
            return z ? false : false;
        }
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
        if (z || !view.canScrollHorizontally(-i)) {
        }
        return true;
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

    public final void completeScroll(boolean z) throws Resources.NotFoundException {
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
    public void computeScroll() throws Resources.NotFoundException {
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

    public final void dataSetChanged() throws Resources.NotFoundException {
        int count = this.mAdapter.getCount();
        this.mExpectedAdapterCount = count;
        boolean z = this.mItems.size() < (this.mOffscreenPageLimit * 2) + 1 && this.mItems.size() < count;
        int iMax = this.mCurItem;
        int i = 0;
        boolean z2 = false;
        while (i < this.mItems.size()) {
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(i);
            int itemPosition = this.mAdapter.getItemPosition(itemInfo.object);
            if (itemPosition != -1) {
                if (itemPosition == -2) {
                    this.mItems.remove(i);
                    i--;
                    if (!z2) {
                        this.mAdapter.getClass();
                        z2 = true;
                    }
                    this.mAdapter.destroyItem(this, itemInfo.position, itemInfo.object);
                    int i2 = this.mCurItem;
                    if (i2 == itemInfo.position) {
                        iMax = Math.max(0, Math.min(i2, count - 1));
                    }
                } else {
                    int i3 = itemInfo.position;
                    if (i3 != itemPosition) {
                        if (i3 == this.mCurItem) {
                            iMax = itemPosition;
                        }
                        itemInfo.position = itemPosition;
                    }
                }
                z = true;
            }
            i++;
        }
        if (z2) {
            this.mAdapter.getClass();
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (z) {
            int childCount = getChildCount();
            for (int i4 = 0; i4 < childCount; i4++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i4).getLayoutParams();
                if (!layoutParams.isDecor) {
                    layoutParams.widthFactor = 0.0f;
                }
            }
            setCurrentItemInternal(iMax, 0, false, true);
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
            return MathUtils.clamp(i4, ((ItemInfo) this.mItems.get(0)).position, ((ItemInfo) AlertController$$ExternalSyntheticOutline0.m(1, this.mItems)).position);
        }
        return i4;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0065 A[RETURN] */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean zArrowScroll;
        if (!super.dispatchKeyEvent(keyEvent)) {
            if (keyEvent.getAction() != 0) {
                zArrowScroll = false;
                if (!zArrowScroll) {
                    return false;
                }
            } else {
                int keyCode = keyEvent.getKeyCode();
                if (keyCode != 21) {
                    if (keyCode == 22) {
                        zArrowScroll = keyEvent.hasModifiers(2) ? pageRight() : arrowScroll(66);
                    } else if (keyCode == 61) {
                        if (keyEvent.hasNoModifiers()) {
                            zArrowScroll = arrowScroll(2);
                        } else if (keyEvent.hasModifiers(1)) {
                            zArrowScroll = arrowScroll(1);
                        }
                    }
                    if (!zArrowScroll) {
                    }
                } else {
                    if (keyEvent.hasModifiers(2)) {
                        int i = this.mCurItem;
                        if (i > 0) {
                            setCurrentItem(i + this.mLeftIncr, true);
                            zArrowScroll = true;
                        }
                    } else {
                        zArrowScroll = arrowScroll(17);
                    }
                    if (!zArrowScroll) {
                    }
                }
            }
        }
        return true;
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
                    StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "IndexOutOfBoundsException: Index: ", ", Size: ");
                    sbM.append(((ArrayList) this.mOnPageChangeListeners).size());
                    Log.e("ViewPager", sbM.toString());
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
        ItemInfo itemInfoInfoForChild;
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0 && (itemInfoInfoForChild = infoForChild(childAt)) != null && itemInfoInfoForChild.position == this.mCurItem && childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
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
        boolean zDraw = false;
        if (overScrollMode == 0 || (overScrollMode == 1 && (pagerAdapter = this.mAdapter) != null && pagerAdapter.getCount() > 1)) {
            if (!this.mLeftEdge.isFinished()) {
                int iSave = canvas.save();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                int width = getWidth();
                canvas.rotate(270.0f);
                canvas.translate(getPaddingTop() + (-height), this.mFirstOffset * width);
                this.mLeftEdge.setSize(height, width);
                zDraw = this.mLeftEdge.draw(canvas);
                canvas.restoreToCount(iSave);
            }
            if (!this.mRightEdge.isFinished()) {
                int iSave2 = canvas.save();
                int width2 = getWidth();
                int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate(-getPaddingTop(), (-(this.mLastOffset + 1.0f)) * width2);
                this.mRightEdge.setSize(height2, width2);
                zDraw |= this.mRightEdge.draw(canvas);
                canvas.restoreToCount(iSave2);
            }
        } else {
            this.mLeftEdge.finish();
            this.mRightEdge.finish();
        }
        if (zDraw) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            postInvalidateOnAnimation();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
    }

    public void endFakeDrag() throws Resources.NotFoundException {
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
            ItemInfo itemInfoInfoForCurrentScrollPosition = infoForCurrentScrollPosition();
            setCurrentItemInternal(determineTargetPage(((scrollX / clientWidth) - itemInfoInfoForCurrentScrollPosition.offset) / itemInfoInfoForCurrentScrollPosition.widthFactor, itemInfoInfoForCurrentScrollPosition.position, xVelocity, (int) (this.mLastMotionX - this.mInitialMotionX)), xVelocity, true, true);
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
        ItemInfo itemInfo2 = (ItemInfo) AlertController$$ExternalSyntheticOutline0.m(1, this.mItems);
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
        MotionEvent motionEventObtain = MotionEvent.obtain(this.mFakeDragBeginTime, SystemClock.uptimeMillis(), 2, this.mLastMotionX, 0.0f, 0);
        this.mVelocityTracker.addMovement(motionEventObtain);
        motionEventObtain.recycle();
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
                WindowInsetsCompat windowInsetsCompatOnApplyWindowInsets = ViewCompat.onApplyWindowInsets(windowInsetsCompat, view);
                if (windowInsetsCompatOnApplyWindowInsets.mImpl.isConsumed()) {
                    return windowInsetsCompatOnApplyWindowInsets;
                }
                Rect rect = this.mTempRect;
                rect.left = windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetLeft();
                rect.top = windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetTop();
                rect.right = windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetRight();
                rect.bottom = windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetBottom();
                ViewPager viewPager = ViewPager.this;
                int childCount = viewPager.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    WindowInsetsCompat windowInsetsCompatDispatchApplyWindowInsets = ViewCompat.dispatchApplyWindowInsets(windowInsetsCompatOnApplyWindowInsets, viewPager.getChildAt(i));
                    rect.left = Math.min(windowInsetsCompatDispatchApplyWindowInsets.getSystemWindowInsetLeft(), rect.left);
                    rect.top = Math.min(windowInsetsCompatDispatchApplyWindowInsets.getSystemWindowInsetTop(), rect.top);
                    rect.right = Math.min(windowInsetsCompatDispatchApplyWindowInsets.getSystemWindowInsetRight(), rect.right);
                    rect.bottom = Math.min(windowInsetsCompatDispatchApplyWindowInsets.getSystemWindowInsetBottom(), rect.bottom);
                }
                WindowInsetsCompat.Builder builder = new WindowInsetsCompat.Builder(windowInsetsCompatOnApplyWindowInsets);
                Insets insetsOf = Insets.of(rect.left, rect.top, rect.right, rect.bottom);
                WindowInsetsCompat.BuilderImpl30 builderImpl30 = builder.mImpl;
                builderImpl30.setSystemWindowInsets(insetsOf);
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
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        int iFindPointerIndex;
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
            if (i != -1 && (iFindPointerIndex = motionEvent.findPointerIndex(i)) != -1) {
                float x2 = motionEvent.getX(iFindPointerIndex);
                float f = x2 - this.mLastMotionX;
                float fAbs = Math.abs(f);
                float y2 = motionEvent.getY(iFindPointerIndex);
                float fAbs2 = Math.abs(y2 - this.mInitialMotionY);
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
                if (fAbs > f3 && fAbs * this.mTouchSlopRatio > fAbs2) {
                    this.mIsBeingDragged = true;
                    ViewParent parent2 = getParent();
                    if (parent2 != null) {
                        parent2.requestDisallowInterceptTouchEvent(true);
                    }
                    setScrollState(1);
                    this.mLastMotionX = f > 0.0f ? this.mInitialMotionX + this.mTouchSlop : this.mInitialMotionX - this.mTouchSlop;
                    this.mLastMotionY = y2;
                    setScrollingCacheEnabled(true);
                } else if (fAbs2 > f3) {
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0094  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        ItemInfo itemInfoInfoForChild;
        int iMax;
        int measuredWidth;
        int iMax2;
        int measuredHeight;
        int childCount = getChildCount();
        int i5 = i3 - i;
        int i6 = i4 - i2;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollX = getScrollX();
        int i7 = 0;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isDecor) {
                    int i9 = layoutParams.gravity;
                    int i10 = i9 & 7;
                    int i11 = i9 & 112;
                    if (i10 != 1) {
                        if (i10 == 3) {
                            measuredWidth = childAt.getMeasuredWidth() + paddingLeft;
                        } else if (i10 != 5) {
                            measuredWidth = paddingLeft;
                        } else {
                            iMax = (i5 - paddingRight) - childAt.getMeasuredWidth();
                            paddingRight += childAt.getMeasuredWidth();
                        }
                        if (i11 == 16) {
                            if (i11 == 48) {
                                measuredHeight = childAt.getMeasuredHeight() + paddingTop;
                            } else if (i11 != 80) {
                                measuredHeight = paddingTop;
                            } else {
                                iMax2 = (i6 - paddingBottom) - childAt.getMeasuredHeight();
                                paddingBottom += childAt.getMeasuredHeight();
                            }
                            int i12 = paddingLeft + scrollX;
                            childAt.layout(i12, paddingTop, childAt.getMeasuredWidth() + i12, childAt.getMeasuredHeight() + paddingTop);
                            i7++;
                            paddingTop = measuredHeight;
                            paddingLeft = measuredWidth;
                        } else {
                            iMax2 = Math.max((i6 - childAt.getMeasuredHeight()) / 2, paddingTop);
                        }
                        int i13 = iMax2;
                        measuredHeight = paddingTop;
                        paddingTop = i13;
                        int i122 = paddingLeft + scrollX;
                        childAt.layout(i122, paddingTop, childAt.getMeasuredWidth() + i122, childAt.getMeasuredHeight() + paddingTop);
                        i7++;
                        paddingTop = measuredHeight;
                        paddingLeft = measuredWidth;
                    } else {
                        iMax = Math.max((i5 - childAt.getMeasuredWidth()) / 2, paddingLeft);
                    }
                    int i14 = iMax;
                    measuredWidth = paddingLeft;
                    paddingLeft = i14;
                    if (i11 == 16) {
                    }
                    int i132 = iMax2;
                    measuredHeight = paddingTop;
                    paddingTop = i132;
                    int i1222 = paddingLeft + scrollX;
                    childAt.layout(i1222, paddingTop, childAt.getMeasuredWidth() + i1222, childAt.getMeasuredHeight() + paddingTop);
                    i7++;
                    paddingTop = measuredHeight;
                    paddingLeft = measuredWidth;
                }
            }
        }
        int i15 = (i5 - paddingLeft) - paddingRight;
        for (int i16 = 0; i16 < childCount; i16++) {
            View childAt2 = getChildAt(i16);
            if (childAt2.getVisibility() != 8) {
                LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                if (!layoutParams2.isDecor && (itemInfoInfoForChild = infoForChild(childAt2)) != null) {
                    float f = i15;
                    int i17 = ((int) (itemInfoInfoForChild.offset * f)) + paddingLeft;
                    if (layoutParams2.needsMeasure) {
                        layoutParams2.needsMeasure = false;
                        childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (f * layoutParams2.widthFactor), 1073741824), View.MeasureSpec.makeMeasureSpec((i6 - paddingTop) - paddingBottom, 1073741824));
                    }
                    childAt2.layout(i17, paddingTop, childAt2.getMeasuredWidth() + i17, childAt2.getMeasuredHeight() + paddingTop);
                }
            }
        }
        this.mDecorChildCount = i7;
        if (this.mFirstLayout) {
            z2 = false;
            scrollToItem(this.mCurItem, 0, false, false);
        } else {
            z2 = false;
        }
        this.mFirstLayout = z2;
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
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824);
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        int childCount2 = getChildCount();
        for (int i12 = 0; i12 < childCount2; i12++) {
            View childAt2 = getChildAt(i12);
            if (childAt2.getVisibility() != 8 && (layoutParams = (LayoutParams) childAt2.getLayoutParams()) != null && !layoutParams.isDecor) {
                childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (paddingLeft * layoutParams.widthFactor), 1073741824), iMakeMeasureSpec);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onPageScrolled(float f, int i, int i2) {
        OnPageChangeListener onPageChangeListener;
        int iMax;
        int width;
        int left;
        if (this.mDecorChildCount > 0) {
            int scrollX = getScrollX();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int width2 = getWidth();
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isDecor) {
                    int i4 = layoutParams.gravity & 7;
                    if (i4 != 1) {
                        if (i4 == 3) {
                            width = childAt.getWidth() + paddingLeft;
                        } else if (i4 != 5) {
                            width = paddingLeft;
                        } else {
                            iMax = (width2 - paddingRight) - childAt.getMeasuredWidth();
                            paddingRight += childAt.getMeasuredWidth();
                        }
                        left = (paddingLeft + scrollX) - childAt.getLeft();
                        if (left != 0) {
                            childAt.offsetLeftAndRight(left);
                        }
                        paddingLeft = width;
                    } else {
                        iMax = Math.max((width2 - childAt.getMeasuredWidth()) / 2, paddingLeft);
                    }
                    int i5 = iMax;
                    width = paddingLeft;
                    paddingLeft = i5;
                    left = (paddingLeft + scrollX) - childAt.getLeft();
                    if (left != 0) {
                    }
                    paddingLeft = width;
                }
            }
        }
        OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListener;
        if (onPageChangeListener2 != null) {
            onPageChangeListener2.onPageScrolled(f, i);
        }
        List list = this.mOnPageChangeListeners;
        if (list != null) {
            int size = ((ArrayList) list).size();
            for (int i6 = 0; i6 < size; i6++) {
                try {
                    onPageChangeListener = (OnPageChangeListener) ((ArrayList) this.mOnPageChangeListeners).get(i6);
                } catch (IndexOutOfBoundsException unused) {
                    StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i6, "IndexOutOfBoundsException: Index: ", ", Size: ");
                    sbM.append(((ArrayList) this.mOnPageChangeListeners).size());
                    Log.e("ViewPager", sbM.toString());
                    onPageChangeListener = null;
                }
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(f, i);
                }
            }
        }
        this.mCalledSuper = true;
    }

    @Override // android.view.ViewGroup
    public final boolean onRequestFocusInDescendants(int i, Rect rect) {
        int i2;
        int i3;
        int i4;
        ItemInfo itemInfoInfoForChild;
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
            if (childAt.getVisibility() == 0 && (itemInfoInfoForChild = infoForChild(childAt)) != null && itemInfoInfoForChild.position == this.mCurItem && childAt.requestFocus(i, rect)) {
                return true;
            }
            i2 += i4;
        }
        return false;
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) throws Resources.NotFoundException {
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
            this.mRestoredAdapterState = savedState.adapterState;
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
    public final void onSizeChanged(int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            int i5 = this.mPageMargin;
            recomputeScrollPosition(i, i3, i5, i5);
            if (this.mPageMargin > 0) {
                setCurrentItemInternal(this.mCurItem, 0, false, true);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x00ec  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        PagerAdapter pagerAdapter;
        if (!this.mFakeDragging) {
            boolean zResetTouch = false;
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
                if (action != 2) {
                    if (action != 3) {
                        if (action == 5) {
                            int actionIndex = motionEvent.getActionIndex();
                            this.mLastMotionX = motionEvent.getX(actionIndex);
                            this.mActivePointerId = motionEvent.getPointerId(actionIndex);
                        } else if (action == 6) {
                            onSecondaryPointerUp(motionEvent);
                            int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                            if (iFindPointerIndex == -1) {
                                zResetTouch = resetTouch();
                            } else {
                                this.mLastMotionX = motionEvent.getX(iFindPointerIndex);
                            }
                        }
                    } else if (this.mIsBeingDragged) {
                        scrollToItem(this.mCurItem, 0, true, false);
                        zResetTouch = resetTouch();
                    }
                } else if (!this.mIsBeingDragged) {
                    int iFindPointerIndex2 = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (iFindPointerIndex2 == -1) {
                        zResetTouch = resetTouch();
                    } else {
                        float x2 = motionEvent.getX(iFindPointerIndex2);
                        float fAbs = Math.abs(x2 - this.mLastMotionX);
                        float y2 = motionEvent.getY(iFindPointerIndex2);
                        float fAbs2 = Math.abs(y2 - this.mLastMotionY);
                        if (fAbs > this.mTouchSlop && fAbs > fAbs2) {
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
                        if (this.mIsBeingDragged) {
                        }
                    }
                } else if (this.mIsBeingDragged) {
                    int iFindPointerIndex3 = motionEvent.findPointerIndex(this.mActivePointerId);
                    zResetTouch = iFindPointerIndex3 == -1 ? resetTouch() : performDrag(motionEvent.getX(iFindPointerIndex3), motionEvent.getY(iFindPointerIndex3));
                }
            } else if (this.mIsBeingDragged) {
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                int xVelocity = (int) velocityTracker.getXVelocity(this.mActivePointerId);
                this.mPopulatePending = true;
                float clientWidth = getClientWidth();
                float scrollX = getScrollX() / clientWidth;
                ItemInfo itemInfoInfoForCurrentScrollPosition = infoForCurrentScrollPosition();
                float f2 = this.mPageMargin / clientWidth;
                int i = itemInfoInfoForCurrentScrollPosition.position;
                float f3 = (scrollX - itemInfoInfoForCurrentScrollPosition.offset) / (itemInfoInfoForCurrentScrollPosition.widthFactor + f2);
                int iFindPointerIndex4 = motionEvent.findPointerIndex(this.mActivePointerId);
                if (iFindPointerIndex4 == -1) {
                    zResetTouch = resetTouch();
                } else {
                    int iDetermineTargetPage = determineTargetPage(f3, i, xVelocity, (int) (motionEvent.getX(iFindPointerIndex4) - this.mInitialMotionX));
                    setCurrentItemInternal(iDetermineTargetPage, xVelocity, true, true);
                    boolean zResetTouch2 = resetTouch();
                    if (iDetermineTargetPage == i && zResetTouch2) {
                        if (EdgeEffectCompat.getDistance(this.mRightEdge) != 0.0f) {
                            this.mRightEdge.onAbsorb(-xVelocity);
                        } else if (EdgeEffectCompat.getDistance(this.mLeftEdge) != 0.0f) {
                            this.mLeftEdge.onAbsorb(xVelocity);
                        }
                    }
                    zResetTouch = zResetTouch2;
                }
            }
            if (zResetTouch) {
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
        ItemInfo itemInfoInfoForCurrentScrollPosition = infoForCurrentScrollPosition();
        int clientWidth = getClientWidth();
        int i2 = this.mPageMargin;
        int i3 = clientWidth + i2;
        float f = clientWidth;
        int i4 = itemInfoInfoForCurrentScrollPosition.position;
        float f2 = ((i / f) - itemInfoInfoForCurrentScrollPosition.offset) / (itemInfoInfoForCurrentScrollPosition.widthFactor + (i2 / f));
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
        float fOnPullDistance = (EdgeEffectCompat.getDistance(this.mLeftEdge) != 0.0f ? -EdgeEffectCompat.onPullDistance(this.mLeftEdge, -width, 1.0f - height) : EdgeEffectCompat.getDistance(this.mRightEdge) != 0.0f ? EdgeEffectCompat.onPullDistance(this.mRightEdge, width, height) : 0.0f) * getWidth();
        float f8 = f7 - fOnPullDistance;
        boolean z = true;
        boolean z2 = fOnPullDistance != 0.0f;
        if (Math.abs(f8) < 1.0E-4f) {
            return z2;
        }
        float scrollX = getScrollX() + f8;
        int clientWidth = getClientWidth();
        ItemInfo itemInfo = (ItemInfo) this.mItems.get(0);
        ItemInfo itemInfo2 = (ItemInfo) AlertController$$ExternalSyntheticOutline0.m(1, this.mItems);
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

    public final void populate() throws Resources.NotFoundException {
        populate(this.mCurItem);
    }

    public final void recomputeScrollPosition(int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        if (i2 > 0 && !this.mItems.isEmpty()) {
            if (!this.mScroller.isFinished()) {
                this.mScroller.setFinalX(getClientWidth() * getCurrentItem());
                return;
            } else {
                scrollTo((int) ((getScrollX() / (((i2 - getPaddingLeft()) - getPaddingRight()) + i4)) * (((i - getPaddingLeft()) - getPaddingRight()) + i3)), getScrollY());
                return;
            }
        }
        ItemInfo itemInfoInfoForPosition = infoForPosition(this.mCurItem);
        int iMin = (int) ((itemInfoInfoForPosition != null ? Math.min(itemInfoInfoForPosition.offset, this.mLastOffset) : 0.0f) * ((i - getPaddingLeft()) - getPaddingRight()));
        if (iMin != getScrollX()) {
            completeScroll(false);
            scrollTo(iMin, getScrollY());
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

    public final void scrollToItem(int i, int i2, boolean z, boolean z2) throws Resources.NotFoundException {
        int scrollX;
        int iAbs;
        ItemInfo itemInfoInfoForPosition = infoForPosition(i);
        int iClamp = itemInfoInfoForPosition != null ? (int) (MathUtils.clamp(itemInfoInfoForPosition.offset, this.mFirstOffset, this.mLastOffset) * getClientWidth()) : 0;
        if (!z) {
            if (z2) {
                dispatchOnPageSelected(i);
            }
            completeScroll(false);
            scrollTo(iClamp, 0);
            pageScrolled(iClamp);
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
            int i4 = iClamp - i3;
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
                float fSin = (((float) Math.sin((Math.min(1.0f, (Math.abs(i4) * 1.0f) / f) - 0.5f) * 0.47123894f)) * f2) + f2;
                int iAbs2 = Math.abs(i2);
                if (iAbs2 > 0) {
                    iAbs = Math.round(Math.abs(fSin / iAbs2) * 1000.0f) * 4;
                } else {
                    this.mAdapter.getClass();
                    iAbs = (int) (((Math.abs(i4) / ((f * 1.0f) + this.mPageMargin)) + 1.0f) * 100.0f);
                }
                int iMin = Math.min(iAbs, VolteConstants.ErrorCode.BUSY_EVERYWHERE);
                this.mIsScrollStarted = false;
                Scroller scroller2 = this.mScroller;
                if (scroller2 != null) {
                    scroller2.startScroll(i3, scrollY, i4, i5, iMin);
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
                this.mRestoredAdapterState = null;
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

    public final void setCurrentItemInternal(int i, int i2, boolean z, boolean z2) throws Resources.NotFoundException {
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

    public final void setOffscreenPageLimit() throws Resources.NotFoundException {
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
                    StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "IndexOutOfBoundsException: Index: ", ", Size: ");
                    sbM.append(((ArrayList) this.mOnPageChangeListeners).size());
                    Log.e("ViewPager", sbM.toString());
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
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ViewPager.LAYOUT_ATTRS);
            this.gravity = typedArrayObtainStyledAttributes.getInteger(0, 48);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0063, code lost:
    
        r8 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00c8 A[PHI: r7 r10 r14
      0x00c8: PHI (r7v16 int) = (r7v15 int), (r7v5 int), (r7v19 int) binds: [B:62:0x00ec, B:59:0x00d6, B:51:0x00bd] A[DONT_GENERATE, DONT_INLINE]
      0x00c8: PHI (r10v38 int) = (r10v1 int), (r10v37 int), (r10v41 int) binds: [B:62:0x00ec, B:59:0x00d6, B:51:0x00bd] A[DONT_GENERATE, DONT_INLINE]
      0x00c8: PHI (r14v6 float) = (r14v4 float), (r14v5 float), (r14v3 float) binds: [B:62:0x00ec, B:59:0x00d6, B:51:0x00bd] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0156 A[PHI: r3 r12
      0x0156: PHI (r3v19 float) = (r3v17 float), (r3v18 float), (r3v16 float) binds: [B:96:0x0183, B:93:0x0169, B:86:0x014b] A[DONT_GENERATE, DONT_INLINE]
      0x0156: PHI (r12v24 int) = (r12v22 int), (r12v23 int), (r12v21 int) binds: [B:96:0x0183, B:93:0x0169, B:86:0x014b] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void populate(int i) throws Resources.NotFoundException {
        ItemInfo itemInfoInfoForPosition;
        String hexString;
        ItemInfo itemInfoAddNewItem;
        ItemInfo itemInfoInfoForChild;
        ItemInfo itemInfoInfoForChild2;
        int i2;
        int i3;
        ItemInfo itemInfo;
        ItemInfo itemInfo2;
        ItemInfo itemInfo3;
        int i4 = this.mCurItem;
        if (i4 != i) {
            itemInfoInfoForPosition = infoForPosition(i4);
            this.mCurItem = i;
        } else {
            itemInfoInfoForPosition = null;
        }
        if (this.mAdapter == null || this.mPopulatePending || getWindowToken() == null) {
            return;
        }
        this.mAdapter.getClass();
        int i5 = this.mOffscreenPageLimit;
        int iMax = Math.max(0, this.mCurItem - i5);
        int count = this.mAdapter.getCount();
        int iMin = Math.min(count - 1, this.mCurItem + i5);
        if (count != this.mExpectedAdapterCount) {
            try {
                hexString = getResources().getResourceName(getId());
            } catch (Resources.NotFoundException unused) {
                hexString = Integer.toHexString(getId());
            }
            StringBuilder sb = new StringBuilder("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: ");
            ViewPager$$ExternalSyntheticOutline0.m(sb, this.mExpectedAdapterCount, ", found: ", count, " Pager id: ");
            sb.append(hexString);
            sb.append(" Pager class: ");
            sb.append(getClass());
            sb.append(" Problematic adapter: ");
            sb.append(this.mAdapter.getClass());
            throw new IllegalStateException(sb.toString());
        }
        int i6 = 0;
        while (true) {
            if (i6 >= this.mItems.size()) {
                break;
            }
            itemInfoAddNewItem = (ItemInfo) this.mItems.get(i6);
            int i7 = itemInfoAddNewItem.position;
            int i8 = this.mCurItem;
            if (i7 >= i8) {
                if (i7 != i8) {
                    break;
                }
            } else {
                i6++;
            }
        }
        if (itemInfoAddNewItem == null && count > 0) {
            itemInfoAddNewItem = addNewItem(this.mCurItem, i6);
        }
        if (itemInfoAddNewItem != null) {
            int i9 = i6 - 1;
            ItemInfo itemInfo4 = i9 >= 0 ? (ItemInfo) this.mItems.get(i9) : null;
            int clientWidth = getClientWidth();
            float paddingLeft = clientWidth <= 0 ? 0.0f : (getPaddingLeft() / clientWidth) + (2.0f - itemInfoAddNewItem.widthFactor);
            float f = 0.0f;
            for (int i10 = this.mCurItem - 1; i10 >= 0; i10--) {
                if (f >= paddingLeft && i10 < iMax) {
                    if (itemInfo4 == null) {
                        break;
                    }
                    if (i10 == itemInfo4.position && !itemInfo4.scrolling) {
                        this.mItems.remove(i9);
                        this.mAdapter.destroyItem(this, i10, itemInfo4.object);
                        i9--;
                        i6--;
                        if (i9 >= 0) {
                            itemInfo3 = (ItemInfo) this.mItems.get(i9);
                        }
                        itemInfo4 = itemInfo3;
                    }
                } else if (itemInfo4 == null || i10 != itemInfo4.position) {
                    f += addNewItem(i10, i9 + 1).widthFactor;
                    i6++;
                    itemInfo3 = i9 >= 0 ? (ItemInfo) this.mItems.get(i9) : null;
                    itemInfo4 = itemInfo3;
                } else {
                    f += itemInfo4.widthFactor;
                    i9--;
                    if (i9 >= 0) {
                        itemInfo3 = (ItemInfo) this.mItems.get(i9);
                    }
                    itemInfo4 = itemInfo3;
                }
            }
            float f2 = itemInfoAddNewItem.widthFactor;
            int i11 = i6 + 1;
            if (f2 < 2.0f) {
                ItemInfo itemInfo5 = i11 < this.mItems.size() ? (ItemInfo) this.mItems.get(i11) : null;
                float paddingRight = clientWidth <= 0 ? 0.0f : (getPaddingRight() / clientWidth) + 2.0f;
                int i12 = i11;
                for (int i13 = this.mCurItem + 1; i13 < count; i13++) {
                    if (f2 >= paddingRight && i13 > iMin) {
                        if (itemInfo5 == null) {
                            break;
                        }
                        if (i13 == itemInfo5.position && !itemInfo5.scrolling) {
                            this.mItems.remove(i12);
                            this.mAdapter.destroyItem(this, i13, itemInfo5.object);
                            if (i12 < this.mItems.size()) {
                                itemInfo5 = (ItemInfo) this.mItems.get(i12);
                            }
                        }
                    } else if (itemInfo5 == null || i13 != itemInfo5.position) {
                        ItemInfo itemInfoAddNewItem2 = addNewItem(i13, i12);
                        i12++;
                        f2 += itemInfoAddNewItem2.widthFactor;
                        itemInfo5 = i12 < this.mItems.size() ? (ItemInfo) this.mItems.get(i12) : null;
                    } else {
                        f2 += itemInfo5.widthFactor;
                        i12++;
                        if (i12 < this.mItems.size()) {
                            itemInfo5 = (ItemInfo) this.mItems.get(i12);
                        }
                    }
                }
            }
            int count2 = this.mAdapter.getCount();
            int clientWidth2 = getClientWidth();
            float f3 = clientWidth2 > 0 ? this.mPageMargin / clientWidth2 : 0.0f;
            if (itemInfoInfoForPosition != null) {
                int i14 = itemInfoInfoForPosition.position;
                int i15 = itemInfoAddNewItem.position;
                if (i14 < i15) {
                    float f4 = itemInfoInfoForPosition.offset + itemInfoInfoForPosition.widthFactor + f3;
                    int i16 = i14 + 1;
                    int i17 = 0;
                    while (i16 <= itemInfoAddNewItem.position && i17 < this.mItems.size()) {
                        Object obj = this.mItems.get(i17);
                        while (true) {
                            itemInfo2 = (ItemInfo) obj;
                            if (i16 <= itemInfo2.position || i17 >= this.mItems.size() - 1) {
                                break;
                            }
                            i17++;
                            obj = this.mItems.get(i17);
                        }
                        while (i16 < itemInfo2.position) {
                            this.mAdapter.getClass();
                            f4 += 1.0f + f3;
                            i16++;
                        }
                        itemInfo2.offset = f4;
                        f4 += itemInfo2.widthFactor + f3;
                        i16++;
                    }
                } else if (i14 > i15) {
                    int size = this.mItems.size() - 1;
                    float f5 = itemInfoInfoForPosition.offset;
                    while (true) {
                        i14--;
                        if (i14 < itemInfoAddNewItem.position || size < 0) {
                            break;
                        }
                        Object obj2 = this.mItems.get(size);
                        while (true) {
                            itemInfo = (ItemInfo) obj2;
                            if (i14 >= itemInfo.position || size <= 0) {
                                break;
                            }
                            size--;
                            obj2 = this.mItems.get(size);
                        }
                        while (i14 > itemInfo.position) {
                            this.mAdapter.getClass();
                            f5 -= 1.0f + f3;
                            i14--;
                        }
                        f5 -= itemInfo.widthFactor + f3;
                        itemInfo.offset = f5;
                    }
                }
            }
            int size2 = this.mItems.size();
            float f6 = itemInfoAddNewItem.offset;
            int i18 = itemInfoAddNewItem.position;
            int i19 = i18 - 1;
            this.mFirstOffset = i18 == 0 ? f6 : -3.4028235E38f;
            int i20 = count2 - 1;
            this.mLastOffset = i18 == i20 ? (itemInfoAddNewItem.widthFactor + f6) - 1.0f : Float.MAX_VALUE;
            int i21 = i6 - 1;
            while (i21 >= 0) {
                ItemInfo itemInfo6 = (ItemInfo) this.mItems.get(i21);
                while (true) {
                    i3 = itemInfo6.position;
                    if (i19 <= i3) {
                        break;
                    }
                    i19--;
                    this.mAdapter.getClass();
                    f6 -= 1.0f + f3;
                }
                f6 -= itemInfo6.widthFactor + f3;
                itemInfo6.offset = f6;
                if (i3 == 0) {
                    this.mFirstOffset = f6;
                }
                i21--;
                i19--;
            }
            float f7 = itemInfoAddNewItem.offset + itemInfoAddNewItem.widthFactor + f3;
            int i22 = itemInfoAddNewItem.position;
            while (true) {
                i22++;
                if (i11 >= size2) {
                    break;
                }
                ItemInfo itemInfo7 = (ItemInfo) this.mItems.get(i11);
                while (true) {
                    i2 = itemInfo7.position;
                    if (i22 >= i2) {
                        break;
                    }
                    i22++;
                    this.mAdapter.getClass();
                    f7 += 1.0f + f3;
                }
                if (i2 == i20) {
                    this.mLastOffset = (itemInfo7.widthFactor + f7) - 1.0f;
                }
                itemInfo7.offset = f7;
                f7 += itemInfo7.widthFactor + f3;
                i11++;
            }
            this.mAdapter.getClass();
        }
        this.mAdapter.getClass();
        int childCount = getChildCount();
        for (int i23 = 0; i23 < childCount; i23++) {
            View childAt = getChildAt(i23);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            layoutParams.getClass();
            if (!layoutParams.isDecor && layoutParams.widthFactor == 0.0f && (itemInfoInfoForChild2 = infoForChild(childAt)) != null) {
                layoutParams.widthFactor = itemInfoInfoForChild2.widthFactor;
                layoutParams.position = itemInfoInfoForChild2.position;
            }
        }
        if (hasFocus()) {
            View viewFindFocus = findFocus();
            if (viewFindFocus != null) {
                while (true) {
                    Object parent = viewFindFocus.getParent();
                    if (parent == this) {
                        itemInfoInfoForChild = infoForChild(viewFindFocus);
                        break;
                    } else if (!(parent instanceof View)) {
                        break;
                    } else {
                        viewFindFocus = (View) parent;
                    }
                }
                itemInfoInfoForChild = null;
            } else {
                itemInfoInfoForChild = null;
            }
            if (itemInfoInfoForChild == null || itemInfoInfoForChild.position != this.mCurItem) {
                for (int i24 = 0; i24 < getChildCount(); i24++) {
                    View childAt2 = getChildAt(i24);
                    ItemInfo itemInfoInfoForChild3 = infoForChild(childAt2);
                    if (itemInfoInfoForChild3 != null && itemInfoInfoForChild3.position == this.mCurItem && childAt2.requestFocus(2)) {
                        return;
                    }
                }
            }
        }
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
        this.mRestoredAdapterState = null;
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
