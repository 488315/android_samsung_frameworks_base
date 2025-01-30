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
import android.support.v4.media.AbstractC0000x2c234b15;
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
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.core.graphics.Insets;
import androidx.core.math.MathUtils;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.EdgeEffectCompat;
import androidx.customview.view.AbsSavedState;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import com.sec.ims.volte2.data.VolteConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    public final RunnableC05663 mEndScrollRunnable;
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
    public OnPageChangeListener mInternalPageChangeListener;
    public boolean mIsBeingDragged;
    public boolean mIsScrollStarted;
    public boolean mIsUnableToDrag;
    public final ArrayList mItems;
    public float mLastMotionX;
    public float mLastMotionY;
    public float mLastOffset;
    public EdgeEffect mLeftEdge;
    public int mLeftIncr;
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
    public static final C05641 COMPARATOR = new Comparator() { // from class: androidx.viewpager.widget.ViewPager.1
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((ItemInfo) obj).position - ((ItemInfo) obj2).position;
        }
    };
    public static final InterpolatorC05652 sInterpolator = new Interpolator() { // from class: androidx.viewpager.widget.ViewPager.2
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.viewpager.widget.ViewPager$3 */
    public final class RunnableC05663 implements Runnable {
        public RunnableC05663() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            ViewPager.this.setScrollState(0);
            ViewPager.this.populate();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DecorView {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ItemInfo {
        public Object object;
        public float offset;
        public int position;
        public boolean scrolling;
        public float widthFactor;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
        public MyAccessibilityDelegate() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:4:0x0013, code lost:
        
            if (r2.getCount() > 1) goto L8;
         */
        @Override // androidx.core.view.AccessibilityDelegateCompat
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            PagerAdapter pagerAdapter;
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName("androidx.viewpager.widget.ViewPager");
            ViewPager viewPager = ViewPager.this;
            PagerAdapter pagerAdapter2 = viewPager.mAdapter;
            boolean z = pagerAdapter2 != null;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnAdapterChangeListener {
        void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnPageChangeListener {
        void onPageScrollStateChanged(int i);

        void onPageScrolled(float f, int i, int i2);

        void onPageSelected(int i);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PagerObserver extends DataSetObserver {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends AbsSavedState {
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
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.position, "}");
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mSuperState, i);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, i);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            classLoader = classLoader == null ? SavedState.class.getClassLoader() : classLoader;
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader);
            this.loader = classLoader;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ViewPositionComparator implements Comparator {
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
        this.mEndScrollRunnable = new RunnableC05663();
        this.mScrollState = 0;
        this.mTouchSlopRatio = 0.5f;
        this.mLeftIncr = -1;
        initViewPager(context);
    }

    public static boolean canScroll(int i, int i2, int i3, View view, boolean z) {
        int i4;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i5 = i2 + scrollX;
                if (i5 >= childAt.getLeft() && i5 < childAt.getRight() && (i4 = i3 + scrollY) >= childAt.getTop() && i4 < childAt.getBottom() && canScroll(i, i5 - childAt.getLeft(), i4 - childAt.getTop(), childAt, true)) {
                    return true;
                }
            }
        }
        return z && view.canScrollHorizontally(-i);
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
        } else {
            this.mItems.add(i2, itemInfo);
        }
        return itemInfo;
    }

    public final void addOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
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
            layoutParams = generateLayoutParams(layoutParams);
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

    /* JADX WARN: Removed duplicated region for block: B:32:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ce  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean arrowScroll(int i) {
        View findNextFocus;
        int i2;
        boolean requestFocus;
        boolean z;
        View findFocus = findFocus();
        boolean z2 = true;
        boolean z3 = false;
        if (findFocus != this) {
            if (findFocus != null) {
                ViewParent parent = findFocus.getParent();
                while (true) {
                    if (!(parent instanceof ViewGroup)) {
                        z = false;
                        break;
                    }
                    if (parent == this) {
                        z = true;
                        break;
                    }
                    parent = parent.getParent();
                }
                if (!z) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(findFocus.getClass().getSimpleName());
                    for (ViewParent parent2 = findFocus.getParent(); parent2 instanceof ViewGroup; parent2 = parent2.getParent()) {
                        sb.append(" => ");
                        sb.append(parent2.getClass().getSimpleName());
                    }
                    Log.e("ViewPager", "arrowScroll tried to find focus based on non-child current focused view " + sb.toString());
                }
            }
            findNextFocus = FocusFinder.getInstance().findNextFocus(this, findFocus, i);
            if (findNextFocus == null && findNextFocus != findFocus) {
                if (i == 17) {
                    int i3 = getChildRectInPagerCoordinates(this.mTempRect, findNextFocus).left;
                    int i4 = getChildRectInPagerCoordinates(this.mTempRect, findFocus).left;
                    if (findFocus == null || i3 < i4) {
                        requestFocus = findNextFocus.requestFocus();
                    } else {
                        int i5 = this.mCurItem;
                        if (i5 > 0) {
                            setCurrentItem(i5 + this.mLeftIncr, true);
                            z3 = z2;
                        }
                        z2 = false;
                        z3 = z2;
                    }
                } else if (i == 66) {
                    requestFocus = (findFocus == null || getChildRectInPagerCoordinates(this.mTempRect, findNextFocus).left > getChildRectInPagerCoordinates(this.mTempRect, findFocus).left) ? findNextFocus.requestFocus() : pageRight();
                }
                z3 = requestFocus;
            } else if (i != 17 || i == 1) {
                i2 = this.mCurItem;
                if (i2 > 0) {
                    setCurrentItem(i2 + this.mLeftIncr, true);
                    z3 = z2;
                }
                z2 = false;
                z3 = z2;
            } else if (i == 66 || i == 2) {
                z3 = pageRight();
            }
            if (z3) {
                playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
            }
            return z3;
        }
        findFocus = null;
        findNextFocus = FocusFinder.getInstance().findNextFocus(this, findFocus, i);
        if (findNextFocus == null) {
        }
        if (i != 17) {
        }
        i2 = this.mCurItem;
        if (i2 > 0) {
        }
        z2 = false;
        z3 = z2;
        if (z3) {
        }
        return z3;
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
            RunnableC05663 runnableC05663 = this.mEndScrollRunnable;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postOnAnimation(this, runnableC05663);
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
        ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
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
                        i = Math.max(0, Math.min(i3, (-1) + count));
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
        if (this.mItems.size() <= 0) {
            return i4;
        }
        return MathUtils.clamp(i4, ((ItemInfo) this.mItems.get(0)).position, ((ItemInfo) this.mItems.get(r2.size() - 1)).position);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean z;
        if (super.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 21) {
                if (keyCode == 22) {
                    z = keyEvent.hasModifiers(2) ? pageRight() : arrowScroll(66);
                } else if (keyCode == 61) {
                    if (keyEvent.hasNoModifiers()) {
                        z = arrowScroll(2);
                    } else if (keyEvent.hasModifiers(1)) {
                        z = arrowScroll(1);
                    }
                }
            } else if (keyEvent.hasModifiers(2)) {
                int i = this.mCurItem;
                if (i > 0) {
                    setCurrentItem(i + this.mLeftIncr, true);
                    z = true;
                }
            } else {
                z = arrowScroll(17);
            }
            return !z;
        }
        z = false;
        if (!z) {
        }
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
                    StringBuilder m1m = AbstractC0000x2c234b15.m1m("IndexOutOfBoundsException: Index: ", i2, ", Size: ");
                    m1m.append(((ArrayList) this.mOnPageChangeListeners).size());
                    Log.e("ViewPager", m1m.toString());
                    onPageChangeListener = null;
                }
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(i);
                }
            }
        }
        OnPageChangeListener onPageChangeListener3 = this.mInternalPageChangeListener;
        if (onPageChangeListener3 != null) {
            onPageChangeListener3.onPageSelected(i);
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
                if (seslIsDatePickerLayoutRtl()) {
                    canvas.translate(getPaddingTop() + (-height), ((-(this.mLastOffset + 1.0f)) * width) + 1.6777216E7f);
                } else {
                    canvas.translate(getPaddingTop() + (-height), this.mFirstOffset * width);
                }
                this.mLeftEdge.setSize(height, width);
                z = false | this.mLeftEdge.draw(canvas);
                canvas.restoreToCount(save);
            }
            if (!this.mRightEdge.isFinished()) {
                int save2 = canvas.save();
                int width2 = getWidth();
                int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                canvas.rotate(90.0f);
                if (seslIsDatePickerLayoutRtl()) {
                    canvas.translate(-getPaddingTop(), (this.mFirstOffset * width2) - 1.6777216E7f);
                } else {
                    canvas.translate(-getPaddingTop(), (-(this.mLastOffset + 1.0f)) * width2);
                }
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
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
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
        ItemInfo itemInfo2 = (ItemInfo) this.mItems.get(r4.size() - 1);
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
        return generateDefaultLayoutParams();
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

    public final int getScrollStart() {
        return seslIsDatePickerLayoutRtl() ? 16777216 - getScrollX() : getScrollX();
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
        int scrollStart = getScrollStart();
        int clientWidth = getClientWidth();
        float f = 0.0f;
        float f2 = clientWidth > 0 ? scrollStart / clientWidth : 0.0f;
        float f3 = clientWidth > 0 ? this.mPageMargin / clientWidth : 0.0f;
        int i2 = 0;
        boolean z = true;
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
                return itemInfo;
            }
            if (f2 < f5 || i2 == this.mItems.size() - 1) {
                return itemInfo3;
            }
            int i4 = itemInfo3.position;
            float f6 = itemInfo3.widthFactor;
            i2++;
            z = false;
            i3 = i4;
            f4 = f6;
            itemInfo = itemInfo3;
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
        if (ViewCompat.Api16Impl.getImportantForAccessibility(this) == 0) {
            ViewCompat.Api16Impl.setImportantForAccessibility(this, 1);
        }
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() { // from class: androidx.viewpager.widget.ViewPager.4
            public final Rect mTempRect = new Rect();

            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                WindowInsetsCompat onApplyWindowInsets = ViewCompat.onApplyWindowInsets(view, windowInsetsCompat);
                if (onApplyWindowInsets.mImpl.isConsumed()) {
                    return onApplyWindowInsets;
                }
                int systemWindowInsetLeft = onApplyWindowInsets.getSystemWindowInsetLeft();
                Rect rect = this.mTempRect;
                rect.left = systemWindowInsetLeft;
                rect.top = onApplyWindowInsets.getSystemWindowInsetTop();
                rect.right = onApplyWindowInsets.getSystemWindowInsetRight();
                rect.bottom = onApplyWindowInsets.getSystemWindowInsetBottom();
                ViewPager viewPager = ViewPager.this;
                int childCount = viewPager.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    WindowInsetsCompat dispatchApplyWindowInsets = ViewCompat.dispatchApplyWindowInsets(viewPager.getChildAt(i), onApplyWindowInsets);
                    rect.left = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetLeft(), rect.left);
                    rect.top = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetTop(), rect.top);
                    rect.right = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetRight(), rect.right);
                    rect.bottom = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetBottom(), rect.bottom);
                }
                WindowInsetsCompat.Builder builder = new WindowInsetsCompat.Builder(onApplyWindowInsets);
                builder.mImpl.setSystemWindowInsets(Insets.m28of(rect.left, rect.top, rect.right, rect.bottom));
                return builder.build();
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

    @Override // android.view.View
    public final boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return super.onGenericMotionEvent(motionEvent);
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
                    if (!(!this.mDragInGutterEnabled && ((f2 < ((float) this.mGutterSize) && f > 0.0f) || (f2 > ((float) (getWidth() - this.mGutterSize)) && f < 0.0f))) && canScroll((int) f, (int) x2, (int) y2, this, false)) {
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
                    ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
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

    /* JADX WARN: Removed duplicated region for block: B:17:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x008e  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        ItemInfo infoForChild;
        int max;
        int i5;
        int max2;
        int i6;
        int childCount = getChildCount();
        int i7 = i3 - i;
        int i8 = i4 - i2;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollX = getScrollX();
        int i9 = 0;
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt = getChildAt(i10);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isDecor) {
                    int i11 = layoutParams.gravity;
                    int i12 = i11 & 7;
                    int i13 = i11 & 112;
                    if (i12 != 1) {
                        if (i12 == 3) {
                            i5 = childAt.getMeasuredWidth() + paddingLeft;
                        } else if (i12 != 5) {
                            i5 = paddingLeft;
                        } else {
                            max = (i7 - paddingRight) - childAt.getMeasuredWidth();
                            paddingRight += childAt.getMeasuredWidth();
                        }
                        if (i13 == 16) {
                            if (i13 == 48) {
                                i6 = childAt.getMeasuredHeight() + paddingTop;
                            } else if (i13 != 80) {
                                i6 = paddingTop;
                            } else {
                                max2 = (i8 - paddingBottom) - childAt.getMeasuredHeight();
                                paddingBottom += childAt.getMeasuredHeight();
                            }
                            int i14 = paddingLeft + scrollX;
                            childAt.layout(i14, paddingTop, childAt.getMeasuredWidth() + i14, childAt.getMeasuredHeight() + paddingTop);
                            i9++;
                            paddingTop = i6;
                            paddingLeft = i5;
                        } else {
                            max2 = Math.max((i8 - childAt.getMeasuredHeight()) / 2, paddingTop);
                        }
                        int i15 = max2;
                        i6 = paddingTop;
                        paddingTop = i15;
                        int i142 = paddingLeft + scrollX;
                        childAt.layout(i142, paddingTop, childAt.getMeasuredWidth() + i142, childAt.getMeasuredHeight() + paddingTop);
                        i9++;
                        paddingTop = i6;
                        paddingLeft = i5;
                    } else {
                        max = Math.max((i7 - childAt.getMeasuredWidth()) / 2, paddingLeft);
                    }
                    int i16 = max;
                    i5 = paddingLeft;
                    paddingLeft = i16;
                    if (i13 == 16) {
                    }
                    int i152 = max2;
                    i6 = paddingTop;
                    paddingTop = i152;
                    int i1422 = paddingLeft + scrollX;
                    childAt.layout(i1422, paddingTop, childAt.getMeasuredWidth() + i1422, childAt.getMeasuredHeight() + paddingTop);
                    i9++;
                    paddingTop = i6;
                    paddingLeft = i5;
                }
            }
        }
        int i17 = (i7 - paddingLeft) - paddingRight;
        for (int i18 = 0; i18 < childCount; i18++) {
            View childAt2 = getChildAt(i18);
            if (childAt2.getVisibility() != 8) {
                LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                if (!layoutParams2.isDecor && (infoForChild = infoForChild(childAt2)) != null) {
                    float f = i17;
                    int i19 = ((int) (infoForChild.offset * f)) + paddingLeft;
                    if (layoutParams2.needsMeasure) {
                        layoutParams2.needsMeasure = false;
                        childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (f * layoutParams2.widthFactor), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec((i8 - paddingTop) - paddingBottom, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                    }
                    childAt2.layout(i19, paddingTop, childAt2.getMeasuredWidth() + i19, childAt2.getMeasuredHeight() + paddingTop);
                }
            }
        }
        this.mDecorChildCount = i9;
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
            int i5 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
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
                int i9 = VideoPlayer.MEDIA_ERROR_SYSTEM;
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
        View.MeasureSpec.makeMeasureSpec(paddingLeft, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredHeight, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        int childCount2 = getChildCount();
        for (int i12 = 0; i12 < childCount2; i12++) {
            View childAt2 = getChildAt(i12);
            if (childAt2.getVisibility() != 8 && (layoutParams = (LayoutParams) childAt2.getLayoutParams()) != null && !layoutParams.isDecor) {
                childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (paddingLeft * layoutParams.widthFactor), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), makeMeasureSpec);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0064  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onPageScrolled(float f, int i, int i2) {
        OnPageChangeListener onPageChangeListener;
        int max;
        int i3;
        int left;
        if (this.mDecorChildCount > 0) {
            int scrollX = getScrollX();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int width = getWidth();
            int childCount = getChildCount();
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt = getChildAt(i4);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isDecor) {
                    int i5 = layoutParams.gravity & 7;
                    if (i5 != 1) {
                        if (i5 == 3) {
                            i3 = childAt.getWidth() + paddingLeft;
                        } else if (i5 != 5) {
                            i3 = paddingLeft;
                        } else {
                            max = (width - paddingRight) - childAt.getMeasuredWidth();
                            paddingRight += childAt.getMeasuredWidth();
                        }
                        left = (paddingLeft + scrollX) - childAt.getLeft();
                        if (left != 0) {
                            childAt.offsetLeftAndRight(left);
                        }
                        paddingLeft = i3;
                    } else {
                        max = Math.max((width - childAt.getMeasuredWidth()) / 2, paddingLeft);
                    }
                    int i6 = max;
                    i3 = paddingLeft;
                    paddingLeft = i6;
                    left = (paddingLeft + scrollX) - childAt.getLeft();
                    if (left != 0) {
                    }
                    paddingLeft = i3;
                }
            }
        }
        OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListener;
        if (onPageChangeListener2 != null) {
            onPageChangeListener2.onPageScrolled(f, i, i2);
        }
        List list = this.mOnPageChangeListeners;
        if (list != null) {
            int size = ((ArrayList) list).size();
            for (int i7 = 0; i7 < size; i7++) {
                try {
                    onPageChangeListener = (OnPageChangeListener) ((ArrayList) this.mOnPageChangeListeners).get(i7);
                } catch (IndexOutOfBoundsException unused) {
                    StringBuilder m1m = AbstractC0000x2c234b15.m1m("IndexOutOfBoundsException: Index: ", i7, ", Size: ");
                    m1m.append(((ArrayList) this.mOnPageChangeListeners).size());
                    Log.e("ViewPager", m1m.toString());
                    onPageChangeListener = null;
                }
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(f, i, i2);
                }
            }
        }
        OnPageChangeListener onPageChangeListener3 = this.mInternalPageChangeListener;
        if (onPageChangeListener3 != null) {
            onPageChangeListener3.onPageScrolled(f, i, i2);
        }
        this.mCalledSuper = true;
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
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
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
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        PagerAdapter pagerAdapter;
        if (this.mFakeDragging) {
            return true;
        }
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
                    z = findPointerIndex2 == -1 ? resetTouch() : false | performDrag(motionEvent.getX(findPointerIndex2), motionEvent.getY(findPointerIndex2));
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
            float scrollStart = getScrollStart() / clientWidth;
            ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
            float f2 = this.mPageMargin / clientWidth;
            int i = infoForCurrentScrollPosition.position;
            float f3 = (scrollStart - infoForCurrentScrollPosition.offset) / (infoForCurrentScrollPosition.widthFactor + f2);
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
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
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
            if (this.mFirstLayout) {
                return false;
            }
            this.mCalledSuper = false;
            onPageScrolled(0.0f, 0, 0);
            if (this.mCalledSuper) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        if (seslIsDatePickerLayoutRtl()) {
            i = 16777216 - i;
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
        ArrayList arrayList = this.mItems;
        ItemInfo itemInfo2 = (ItemInfo) arrayList.get(arrayList.size() - 1);
        boolean z3 = itemInfo.position == 0;
        if (z3) {
            f3 = clientWidth;
            f4 = this.mFirstOffset;
        } else {
            f3 = itemInfo.offset;
            f4 = clientWidth;
        }
        float f9 = f4 * f3;
        boolean z4 = itemInfo2.position == this.mAdapter.getCount() - 1;
        if (z4) {
            f5 = clientWidth;
            f6 = this.mLastOffset;
        } else {
            f5 = itemInfo2.offset;
            f6 = clientWidth;
        }
        float f10 = f6 * f5;
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
        if (i2 <= 0 || this.mItems.isEmpty()) {
            ItemInfo infoForPosition = infoForPosition(this.mCurItem);
            int min = (int) ((infoForPosition != null ? Math.min(infoForPosition.offset, this.mLastOffset) : 0.0f) * ((i - getPaddingLeft()) - getPaddingRight()));
            if (min != getScrollX()) {
                completeScroll(false);
                scrollTo(min, getScrollY());
                return;
            }
            return;
        }
        if (!this.mScroller.isFinished()) {
            this.mScroller.setFinalX(getClientWidth() * getCurrentItem());
            return;
        }
        int paddingLeft = ((i - getPaddingLeft()) - getPaddingRight()) + i3;
        float scrollStart = getScrollStart() / (((i2 - getPaddingLeft()) - getPaddingRight()) + i4);
        scrollTo(seslIsDatePickerLayoutRtl() ? (int) (1.6777216E7f - (scrollStart * paddingLeft)) : (int) (scrollStart * paddingLeft), getScrollY());
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
        int i3;
        int scrollX;
        int abs;
        ItemInfo infoForPosition = infoForPosition(i);
        if (infoForPosition != null) {
            float clientWidth = getClientWidth();
            i3 = (int) (MathUtils.clamp(infoForPosition.offset, this.mFirstOffset, this.mLastOffset) * clientWidth);
            if (seslIsDatePickerLayoutRtl()) {
                i3 = (16777216 - ((int) ((clientWidth * infoForPosition.widthFactor) + 0.5f))) - i3;
            }
        } else {
            i3 = 0;
        }
        if (!z) {
            if (z2) {
                dispatchOnPageSelected(i);
            }
            completeScroll(false);
            scrollTo(i3, 0);
            pageScrolled(i3);
            return;
        }
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
        } else {
            Scroller scroller = this.mScroller;
            if ((scroller == null || scroller.isFinished()) ? false : true) {
                scrollX = this.mIsScrollStarted ? this.mScroller.getCurrX() : this.mScroller.getStartX();
                this.mScroller.abortAnimation();
                setScrollingCacheEnabled(false);
            } else {
                scrollX = getScrollX();
            }
            int i4 = scrollX;
            int scrollY = getScrollY();
            int i5 = i3 - i4;
            int i6 = 0 - scrollY;
            if (i5 == 0 && i6 == 0) {
                completeScroll(false);
                populate();
                setScrollState(0);
            } else {
                setScrollingCacheEnabled(true);
                setScrollState(2);
                int clientWidth2 = getClientWidth();
                int i7 = clientWidth2 / 2;
                float f = clientWidth2;
                float f2 = i7;
                float sin = (((float) Math.sin((Math.min(1.0f, (Math.abs(i5) * 1.0f) / f) - 0.5f) * 0.47123894f)) * f2) + f2;
                int abs2 = Math.abs(i2);
                if (abs2 > 0) {
                    abs = Math.round(Math.abs(sin / abs2) * 1000.0f) * 4;
                } else {
                    this.mAdapter.getClass();
                    abs = (int) (((Math.abs(i5) / ((f * 1.0f) + this.mPageMargin)) + 1.0f) * 100.0f);
                }
                int min = Math.min(abs, VolteConstants.ErrorCode.BUSY_EVERYWHERE);
                this.mIsScrollStarted = false;
                Scroller scroller2 = this.mScroller;
                if (scroller2 != null) {
                    scroller2.startScroll(i4, scrollY, i5, i6, min);
                }
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
            }
        }
        if (z2) {
            dispatchOnPageSelected(i);
        }
    }

    public final boolean seslIsDatePickerLayoutRtl() {
        return false;
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
        PagerAdapter pagerAdapter3 = this.mAdapter;
        this.mAdapter = pagerAdapter;
        this.mExpectedAdapterCount = 0;
        if (pagerAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            PagerAdapter pagerAdapter4 = this.mAdapter;
            PagerObserver pagerObserver = this.mObserver;
            synchronized (pagerAdapter4) {
                pagerAdapter4.mViewPagerObserver = pagerObserver;
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
            ((OnAdapterChangeListener) ((ArrayList) this.mAdapterChangeListeners).get(i3)).onAdapterChanged(this, pagerAdapter3, pagerAdapter);
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

    public final void setOffscreenPageLimit(int i) {
        if (i < 1) {
            Log.w("ViewPager", "Requested offscreen page limit " + i + " too small; defaulting to 1");
            i = 1;
        }
        if (i != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = i;
            populate();
        }
    }

    public void setPageMargin(int i) {
        int i2 = this.mPageMargin;
        this.mPageMargin = i;
        int width = getWidth();
        recomputeScrollPosition(width, width, i, i2);
        requestLayout();
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
                    StringBuilder m1m = AbstractC0000x2c234b15.m1m("IndexOutOfBoundsException: Index: ", i2, ", Size: ");
                    m1m.append(((ArrayList) this.mOnPageChangeListeners).size());
                    Log.e("ViewPager", m1m.toString());
                    onPageChangeListener = null;
                }
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(i);
                }
            }
        }
        OnPageChangeListener onPageChangeListener3 = this.mInternalPageChangeListener;
        if (onPageChangeListener3 != null) {
            onPageChangeListener3.onPageScrollStateChanged(i);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class LayoutParams extends ViewGroup.LayoutParams {
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

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0055, code lost:
    
        if (r5 == r6) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:230:0x031d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void populate(int i) {
        ItemInfo itemInfo;
        String hexString;
        ItemInfo itemInfo2;
        ItemInfo itemInfo3;
        int i2;
        ItemInfo infoForChild;
        int i3;
        int i4;
        ItemInfo itemInfo4;
        ItemInfo itemInfo5;
        int i5 = this.mCurItem;
        if (i5 != i) {
            itemInfo = infoForPosition(i5);
            this.mCurItem = i;
        } else {
            itemInfo = null;
        }
        if (this.mAdapter == null || this.mPopulatePending || getWindowToken() == null) {
            return;
        }
        this.mAdapter.getClass();
        int i6 = this.mOffscreenPageLimit;
        int i7 = 0;
        int max = Math.max(0, this.mCurItem - i6);
        int count = this.mAdapter.getCount();
        int min = Math.min(count - 1, this.mCurItem + i6);
        if (count != this.mExpectedAdapterCount) {
            try {
                hexString = getResources().getResourceName(getId());
            } catch (Resources.NotFoundException unused) {
                hexString = Integer.toHexString(getId());
            }
            StringBuilder sb = new StringBuilder("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: ");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, this.mExpectedAdapterCount, ", found: ", count, " Pager id: ");
            sb.append(hexString);
            sb.append(" Pager class: ");
            sb.append(getClass());
            sb.append(" Problematic adapter: ");
            sb.append(this.mAdapter.getClass());
            throw new IllegalStateException(sb.toString());
        }
        while (true) {
            if (i7 >= this.mItems.size()) {
                break;
            }
            itemInfo2 = (ItemInfo) this.mItems.get(i7);
            int i8 = itemInfo2.position;
            int i9 = this.mCurItem;
            if (i8 < i9) {
                i7++;
            }
        }
        itemInfo2 = null;
        if (itemInfo2 == null && count > 0) {
            itemInfo2 = addNewItem(this.mCurItem, i7);
        }
        if (itemInfo2 != null) {
            int i10 = i7 - 1;
            ItemInfo itemInfo6 = i10 >= 0 ? (ItemInfo) this.mItems.get(i10) : null;
            int clientWidth = getClientWidth();
            float paddingLeft = clientWidth <= 0 ? 0.0f : (getPaddingLeft() / clientWidth) + (2.0f - itemInfo2.widthFactor);
            float f = 0.0f;
            for (int i11 = this.mCurItem - 1; i11 >= 0; i11--) {
                if (f >= paddingLeft && i11 < max) {
                    if (itemInfo6 == null) {
                        break;
                    }
                    if (i11 == itemInfo6.position && !itemInfo6.scrolling) {
                        this.mItems.remove(i10);
                        this.mAdapter.destroyItem(this, i11, itemInfo6.object);
                        i10--;
                        i7--;
                        if (i10 >= 0) {
                            itemInfo6 = (ItemInfo) this.mItems.get(i10);
                        }
                        itemInfo6 = null;
                    }
                } else if (itemInfo6 == null || i11 != itemInfo6.position) {
                    f += addNewItem(i11, i10 + 1).widthFactor;
                    i7++;
                    if (i10 >= 0) {
                        itemInfo6 = (ItemInfo) this.mItems.get(i10);
                    }
                    itemInfo6 = null;
                } else {
                    f += itemInfo6.widthFactor;
                    i10--;
                    if (i10 >= 0) {
                        itemInfo6 = (ItemInfo) this.mItems.get(i10);
                    }
                    itemInfo6 = null;
                }
            }
            float f2 = itemInfo2.widthFactor;
            int i12 = i7 + 1;
            if (f2 < 2.0f) {
                ItemInfo itemInfo7 = i12 < this.mItems.size() ? (ItemInfo) this.mItems.get(i12) : null;
                float paddingRight = clientWidth <= 0 ? 0.0f : (getPaddingRight() / clientWidth) + 2.0f;
                int i13 = i12;
                for (int i14 = this.mCurItem + 1; i14 < count; i14++) {
                    if (f2 >= paddingRight && i14 > min) {
                        if (itemInfo7 == null) {
                            break;
                        }
                        if (i14 == itemInfo7.position && !itemInfo7.scrolling) {
                            this.mItems.remove(i13);
                            this.mAdapter.destroyItem(this, i14, itemInfo7.object);
                            if (i13 < this.mItems.size()) {
                                itemInfo7 = (ItemInfo) this.mItems.get(i13);
                            }
                            itemInfo7 = null;
                        }
                    } else if (itemInfo7 == null || i14 != itemInfo7.position) {
                        ItemInfo addNewItem = addNewItem(i14, i13);
                        i13++;
                        f2 += addNewItem.widthFactor;
                        if (i13 < this.mItems.size()) {
                            itemInfo7 = (ItemInfo) this.mItems.get(i13);
                        }
                        itemInfo7 = null;
                    } else {
                        f2 += itemInfo7.widthFactor;
                        i13++;
                        if (i13 < this.mItems.size()) {
                            itemInfo7 = (ItemInfo) this.mItems.get(i13);
                        }
                        itemInfo7 = null;
                    }
                }
            }
            int count2 = this.mAdapter.getCount();
            int clientWidth2 = getClientWidth();
            float f3 = clientWidth2 > 0 ? this.mPageMargin / clientWidth2 : 0.0f;
            if (itemInfo != null) {
                int i15 = itemInfo.position;
                int i16 = itemInfo2.position;
                if (i15 < i16) {
                    float f4 = itemInfo.offset + itemInfo.widthFactor + f3;
                    int i17 = i15 + 1;
                    int i18 = 0;
                    while (i17 <= itemInfo2.position && i18 < this.mItems.size()) {
                        Object obj = this.mItems.get(i18);
                        while (true) {
                            itemInfo5 = (ItemInfo) obj;
                            if (i17 <= itemInfo5.position || i18 >= this.mItems.size() - 1) {
                                break;
                            }
                            i18++;
                            obj = this.mItems.get(i18);
                        }
                        while (i17 < itemInfo5.position) {
                            this.mAdapter.getClass();
                            f4 += 1.0f + f3;
                            i17++;
                        }
                        itemInfo5.offset = f4;
                        f4 += itemInfo5.widthFactor + f3;
                        i17++;
                    }
                } else if (i15 > i16) {
                    int size = this.mItems.size() - 1;
                    float f5 = itemInfo.offset;
                    while (true) {
                        i15--;
                        if (i15 < itemInfo2.position || size < 0) {
                            break;
                        }
                        Object obj2 = this.mItems.get(size);
                        while (true) {
                            itemInfo4 = (ItemInfo) obj2;
                            if (i15 >= itemInfo4.position || size <= 0) {
                                break;
                            }
                            size--;
                            obj2 = this.mItems.get(size);
                        }
                        while (i15 > itemInfo4.position) {
                            this.mAdapter.getClass();
                            f5 -= 1.0f + f3;
                            i15--;
                        }
                        f5 -= itemInfo4.widthFactor + f3;
                        itemInfo4.offset = f5;
                    }
                }
            }
            int size2 = this.mItems.size();
            float f6 = itemInfo2.offset;
            int i19 = itemInfo2.position;
            int i20 = i19 - 1;
            this.mFirstOffset = i19 == 0 ? f6 : -3.4028235E38f;
            int i21 = count2 - 1;
            this.mLastOffset = i19 == i21 ? (itemInfo2.widthFactor + f6) - 1.0f : Float.MAX_VALUE;
            int i22 = i7 - 1;
            while (i22 >= 0) {
                ItemInfo itemInfo8 = (ItemInfo) this.mItems.get(i22);
                while (true) {
                    i4 = itemInfo8.position;
                    if (i20 <= i4) {
                        break;
                    }
                    i20--;
                    this.mAdapter.getClass();
                    f6 -= 1.0f + f3;
                }
                f6 -= itemInfo8.widthFactor + f3;
                itemInfo8.offset = f6;
                if (i4 == 0) {
                    this.mFirstOffset = f6;
                }
                i22--;
                i20--;
            }
            float f7 = itemInfo2.offset + itemInfo2.widthFactor + f3;
            int i23 = itemInfo2.position;
            while (true) {
                i23++;
                if (i12 >= size2) {
                    break;
                }
                ItemInfo itemInfo9 = (ItemInfo) this.mItems.get(i12);
                while (true) {
                    i3 = itemInfo9.position;
                    if (i23 >= i3) {
                        break;
                    }
                    i23++;
                    this.mAdapter.getClass();
                    f7 += 1.0f + f3;
                }
                if (i3 == i21) {
                    this.mLastOffset = (itemInfo9.widthFactor + f7) - 1.0f;
                }
                itemInfo9.offset = f7;
                f7 += itemInfo9.widthFactor + f3;
                i12++;
            }
            this.mAdapter.getClass();
        }
        this.mAdapter.getClass();
        int childCount = getChildCount();
        for (int i24 = 0; i24 < childCount; i24++) {
            View childAt = getChildAt(i24);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            layoutParams.getClass();
            if (!layoutParams.isDecor && layoutParams.widthFactor == 0.0f && (infoForChild = infoForChild(childAt)) != null) {
                layoutParams.widthFactor = infoForChild.widthFactor;
                layoutParams.position = infoForChild.position;
            }
        }
        if (hasFocus()) {
            View findFocus = findFocus();
            if (findFocus != null) {
                while (true) {
                    Object parent = findFocus.getParent();
                    if (parent == this) {
                        itemInfo3 = infoForChild(findFocus);
                        break;
                    } else if (!(parent instanceof View)) {
                        break;
                    } else {
                        findFocus = (View) parent;
                    }
                }
                if (itemInfo3 == null && itemInfo3.position == this.mCurItem) {
                    return;
                }
                for (i2 = 0; i2 < getChildCount(); i2++) {
                    View childAt2 = getChildAt(i2);
                    ItemInfo infoForChild2 = infoForChild(childAt2);
                    if (infoForChild2 != null && infoForChild2.position == this.mCurItem && childAt2.requestFocus(2)) {
                        return;
                    }
                }
            }
            itemInfo3 = null;
            if (itemInfo3 == null) {
            }
            while (i2 < getChildCount()) {
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
        this.mFirstOffset = -3.4028235E38f;
        this.mLastOffset = Float.MAX_VALUE;
        this.mOffscreenPageLimit = 1;
        this.mDragInGutterEnabled = true;
        this.mActivePointerId = -1;
        this.mFirstLayout = true;
        this.mEndScrollRunnable = new RunnableC05663();
        this.mScrollState = 0;
        this.mTouchSlopRatio = 0.5f;
        this.mLeftIncr = -1;
        initViewPager(context);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class SimpleOnPageChangeListener implements OnPageChangeListener {
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(float f, int i, int i2) {
        }
    }
}
