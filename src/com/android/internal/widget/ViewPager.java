package com.android.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.view.AbsSavedState;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* loaded from: classes6.dex */
public class ViewPager extends ViewGroup {
    private static final int CLOSE_ENOUGH = 2;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    private static final int MAX_SCROLL_X = 16777216;
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    private static final int MIN_FLING_VELOCITY = 400;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "ViewPager";
    private static final boolean USE_CACHE = false;
    private int mActivePointerId;
    private PagerAdapter mAdapter;
    private OnAdapterChangeListener mAdapterChangeListener;
    private int mBottomPageBounds;
    private boolean mCalledSuper;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private final int mCloseEnough;
    private int mCurItem;
    private int mDecorChildCount;
    private final int mDefaultGutterSize;
    private int mDrawingOrder;
    private ArrayList<View> mDrawingOrderedChildren;
    private final Runnable mEndScrollRunnable;
    private int mExpectedAdapterCount;
    private boolean mFirstLayout;
    private float mFirstOffset;
    private final int mFlingDistance;
    private int mGutterSize;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private OnPageChangeListener mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private final ArrayList<ItemInfo> mItems;
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset;
    private final EdgeEffect mLeftEdge;
    private int mLeftIncr;
    private Drawable mMarginDrawable;
    private final int mMaximumVelocity;
    private final int mMinimumVelocity;
    private PagerObserver mObserver;
    private int mOffscreenPageLimit;
    private OnPageChangeListener mOnPageChangeListener;
    private int mPageMargin;
    private PageTransformer mPageTransformer;
    private boolean mPopulatePending;
    private Parcelable mRestoredAdapterState;
    private ClassLoader mRestoredClassLoader;
    private int mRestoredCurItem;
    private final EdgeEffect mRightEdge;
    private int mScrollState;
    private final Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private final ItemInfo mTempItem;
    private final Rect mTempRect;
    private int mTopPageBounds;
    private final int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private static final int[] LAYOUT_ATTRS = {16842931};
    private static final Comparator<ItemInfo> COMPARATOR = new Comparator<ItemInfo>() { // from class: com.android.internal.widget.ViewPager.1
        @Override // java.util.Comparator
        public int compare(ItemInfo itemInfo, ItemInfo itemInfo2) {
            return itemInfo.position - itemInfo2.position;
        }
    };
    private static final Interpolator sInterpolator = new Interpolator() { // from class: com.android.internal.widget.ViewPager.2
        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    private static final ViewPositionComparator sPositionComparator = new ViewPositionComparator();

    interface Decor {
    }

    interface OnAdapterChangeListener {
        void onAdapterChanged(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2);
    }

    public interface OnPageChangeListener {
        void onPageScrollStateChanged(int i);

        void onPageScrolled(int i, float f, int i2);

        void onPageSelected(int i);
    }

    public interface PageTransformer {
        void transformPage(View view, float f);
    }

    public static class SimpleOnPageChangeListener implements OnPageChangeListener {
        @Override // com.android.internal.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }

        @Override // com.android.internal.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        @Override // com.android.internal.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
        }
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        int childIndex;
        public int gravity;
        public boolean isDecor;
        boolean needsMeasure;
        int position;
        float widthFactor;

        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<LayoutParams> {
            private int mLayout_gravityId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(PropertyMapper propertyMapper) {
                this.mLayout_gravityId = propertyMapper.mapGravity("layout_gravity", 16842931);
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(LayoutParams layoutParams, PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readGravity(this.mLayout_gravityId, layoutParams.gravity);
            }
        }

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

    static class ItemInfo {
        Object object;
        float offset;
        int position;
        boolean scrolling;
        float widthFactor;

        ItemInfo() {
        }
    }

    public ViewPager(Context context) {
        this(context, null);
    }

    public ViewPager(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewPager(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ViewPager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mItems = new ArrayList<>();
        this.mTempItem = new ItemInfo();
        this.mTempRect = new Rect();
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
        this.mLeftIncr = -1;
        this.mFirstOffset = -3.4028235E38f;
        this.mLastOffset = Float.MAX_VALUE;
        this.mOffscreenPageLimit = 1;
        this.mActivePointerId = -1;
        this.mFirstLayout = true;
        this.mEndScrollRunnable = new Runnable() { // from class: com.android.internal.widget.ViewPager.3
            @Override // java.lang.Runnable
            public void run() {
                ViewPager.this.setScrollState(0);
                ViewPager.this.populate();
            }
        };
        this.mScrollState = 0;
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        float f = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        this.mMinimumVelocity = (int) (400.0f * f);
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new EdgeEffect(context, attributeSet);
        this.mRightEdge = new EdgeEffect(context, attributeSet);
        this.mFlingDistance = (int) (25.0f * f);
        this.mCloseEnough = (int) (2.0f * f);
        this.mDefaultGutterSize = (int) (f * 16.0f);
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        removeCallbacks(this.mEndScrollRunnable);
        super.onDetachedFromWindow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScrollState(int i) {
        if (this.mScrollState == i) {
            return;
        }
        this.mScrollState = i;
        if (this.mPageTransformer != null) {
            enableLayers(i != 0);
        }
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(i);
        }
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        PagerAdapter pagerAdapter2 = this.mAdapter;
        if (pagerAdapter2 != null) {
            pagerAdapter2.unregisterDataSetObserver(this.mObserver);
            this.mAdapter.startUpdate((ViewGroup) this);
            for (int i = 0; i < this.mItems.size(); i++) {
                ItemInfo itemInfo = this.mItems.get(i);
                this.mAdapter.destroyItem((ViewGroup) this, itemInfo.position, itemInfo.object);
            }
            this.mAdapter.finishUpdate((ViewGroup) this);
            this.mItems.clear();
            removeNonDecorViews();
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
            this.mAdapter.registerDataSetObserver(this.mObserver);
            this.mPopulatePending = false;
            boolean z = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if (!z) {
                populate();
            } else {
                requestLayout();
            }
        }
        OnAdapterChangeListener onAdapterChangeListener = this.mAdapterChangeListener;
        if (onAdapterChangeListener == null || pagerAdapter3 == pagerAdapter) {
            return;
        }
        onAdapterChangeListener.onAdapterChanged(pagerAdapter3, pagerAdapter);
    }

    private void removeNonDecorViews() {
        int i = 0;
        while (i < getChildCount()) {
            if (!((LayoutParams) getChildAt(i).getLayoutParams()).isDecor) {
                removeViewAt(i);
                i--;
            }
            i++;
        }
    }

    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    void setOnAdapterChangeListener(OnAdapterChangeListener onAdapterChangeListener) {
        this.mAdapterChangeListener = onAdapterChangeListener;
    }

    private int getPaddedWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public void setCurrentItem(int i) {
        this.mPopulatePending = false;
        setCurrentItemInternal(i, !this.mFirstLayout, false);
    }

    public void setCurrentItem(int i, boolean z) {
        this.mPopulatePending = false;
        setCurrentItemInternal(i, z, false);
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    boolean setCurrentItemInternal(int i, boolean z, boolean z2) {
        return setCurrentItemInternal(i, z, z2, 0);
    }

    boolean setCurrentItemInternal(int i, boolean z, boolean z2, int i2) {
        OnPageChangeListener onPageChangeListener;
        OnPageChangeListener onPageChangeListener2;
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter == null || pagerAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(false);
            return false;
        }
        int constrain = MathUtils.constrain(i, 0, this.mAdapter.getCount() - 1);
        if (!z2 && this.mCurItem == constrain && this.mItems.size() != 0) {
            setScrollingCacheEnabled(false);
            return false;
        }
        int i3 = this.mOffscreenPageLimit;
        int i4 = this.mCurItem;
        if (constrain > i4 + i3 || constrain < i4 - i3) {
            for (int i5 = 0; i5 < this.mItems.size(); i5++) {
                this.mItems.get(i5).scrolling = true;
            }
        }
        boolean z3 = this.mCurItem != constrain;
        if (this.mFirstLayout) {
            this.mCurItem = constrain;
            if (z3 && (onPageChangeListener2 = this.mOnPageChangeListener) != null) {
                onPageChangeListener2.onPageSelected(constrain);
            }
            if (z3 && (onPageChangeListener = this.mInternalPageChangeListener) != null) {
                onPageChangeListener.onPageSelected(constrain);
            }
            requestLayout();
        } else {
            populate(constrain);
            scrollToItem(constrain, z, i2, z3);
        }
        return true;
    }

    private void scrollToItem(int i, boolean z, int i2, boolean z2) {
        OnPageChangeListener onPageChangeListener;
        OnPageChangeListener onPageChangeListener2;
        OnPageChangeListener onPageChangeListener3;
        OnPageChangeListener onPageChangeListener4;
        int leftEdgeForItem = getLeftEdgeForItem(i);
        if (z) {
            smoothScrollTo(leftEdgeForItem, 0, i2);
            if (z2 && (onPageChangeListener4 = this.mOnPageChangeListener) != null) {
                onPageChangeListener4.onPageSelected(i);
            }
            if (!z2 || (onPageChangeListener3 = this.mInternalPageChangeListener) == null) {
                return;
            }
            onPageChangeListener3.onPageSelected(i);
            return;
        }
        if (z2 && (onPageChangeListener2 = this.mOnPageChangeListener) != null) {
            onPageChangeListener2.onPageSelected(i);
        }
        if (z2 && (onPageChangeListener = this.mInternalPageChangeListener) != null) {
            onPageChangeListener.onPageSelected(i);
        }
        completeScroll(false);
        scrollTo(leftEdgeForItem, 0);
        pageScrolled(leftEdgeForItem);
    }

    private int getLeftEdgeForItem(int i) {
        ItemInfo infoForPosition = infoForPosition(i);
        if (infoForPosition == null) {
            return 0;
        }
        float paddedWidth = getPaddedWidth();
        int constrain = (int) (MathUtils.constrain(infoForPosition.offset, this.mFirstOffset, this.mLastOffset) * paddedWidth);
        return isLayoutRtl() ? (16777216 - ((int) ((paddedWidth * infoForPosition.widthFactor) + 0.5f))) - constrain : constrain;
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void setPageTransformer(boolean z, PageTransformer pageTransformer) {
        boolean z2 = pageTransformer != null;
        boolean z3 = z2 != (this.mPageTransformer != null);
        this.mPageTransformer = pageTransformer;
        setChildrenDrawingOrderEnabled(z2);
        if (z2) {
            this.mDrawingOrder = z ? 2 : 1;
        } else {
            this.mDrawingOrder = 0;
        }
        if (z3) {
            populate();
        }
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int i, int i2) {
        if (this.mDrawingOrder == 2) {
            i2 = (i - 1) - i2;
        }
        return ((LayoutParams) this.mDrawingOrderedChildren.get(i2).getLayoutParams()).childIndex;
    }

    OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener onPageChangeListener) {
        OnPageChangeListener onPageChangeListener2 = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = onPageChangeListener;
        return onPageChangeListener2;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public void setOffscreenPageLimit(int i) {
        if (i < 1) {
            Log.w(TAG, "Requested offscreen page limit " + i + " too small; defaulting to 1");
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

    public int getPageMargin() {
        return this.mPageMargin;
    }

    public void setPageMarginDrawable(Drawable drawable) {
        this.mMarginDrawable = drawable;
        if (drawable != null) {
            refreshDrawableState();
        }
        setWillNotDraw(drawable == null);
        invalidate();
    }

    public void setPageMarginDrawable(int i) {
        setPageMarginDrawable(getContext().getDrawable(i));
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mMarginDrawable;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mMarginDrawable;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
    }

    float distanceInfluenceForSnapDuration(float f) {
        return (float) Math.sin((float) ((f - 0.5f) * 0.4712389167638204d));
    }

    void smoothScrollTo(int i, int i2) {
        smoothScrollTo(i, i2, 0);
    }

    void smoothScrollTo(int i, int i2, int i3) {
        int abs;
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int i4 = i - scrollX;
        int i5 = i2 - scrollY;
        if (i4 == 0 && i5 == 0) {
            completeScroll(false);
            populate();
            setScrollState(0);
            return;
        }
        setScrollingCacheEnabled(true);
        setScrollState(2);
        int paddedWidth = getPaddedWidth();
        int i6 = paddedWidth / 2;
        float f = paddedWidth;
        float f2 = i6;
        float distanceInfluenceForSnapDuration = f2 + (distanceInfluenceForSnapDuration(Math.min(1.0f, (Math.abs(i4) * 1.0f) / f)) * f2);
        int abs2 = Math.abs(i3);
        if (abs2 > 0) {
            abs = Math.round(Math.abs(distanceInfluenceForSnapDuration / abs2) * 1000.0f) * 4;
        } else {
            abs = (int) (((Math.abs(i4) / ((f * this.mAdapter.getPageWidth(this.mCurItem)) + this.mPageMargin)) + 1.0f) * 100.0f);
        }
        this.mScroller.startScroll(scrollX, scrollY, i4, i5, Math.min(abs, 600));
        postInvalidateOnAnimation();
    }

    ItemInfo addNewItem(int i, int i2) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.position = i;
        itemInfo.object = this.mAdapter.instantiateItem((ViewGroup) this, i);
        itemInfo.widthFactor = this.mAdapter.getPageWidth(i);
        if (i2 < 0 || i2 >= this.mItems.size()) {
            this.mItems.add(itemInfo);
            return itemInfo;
        }
        this.mItems.add(i2, itemInfo);
        return itemInfo;
    }

    void dataSetChanged() {
        int count = this.mAdapter.getCount();
        this.mExpectedAdapterCount = count;
        boolean z = this.mItems.size() < (this.mOffscreenPageLimit * 2) + 1 && this.mItems.size() < count;
        int i = this.mCurItem;
        int i2 = 0;
        boolean z2 = false;
        while (i2 < this.mItems.size()) {
            ItemInfo itemInfo = this.mItems.get(i2);
            int itemPosition = this.mAdapter.getItemPosition(itemInfo.object);
            if (itemPosition != -1) {
                if (itemPosition == -2) {
                    this.mItems.remove(i2);
                    i2--;
                    if (!z2) {
                        this.mAdapter.startUpdate((ViewGroup) this);
                        z2 = true;
                    }
                    this.mAdapter.destroyItem((ViewGroup) this, itemInfo.position, itemInfo.object);
                    if (this.mCurItem == itemInfo.position) {
                        i = Math.max(0, Math.min(this.mCurItem, count - 1));
                    }
                } else if (itemInfo.position != itemPosition) {
                    if (itemInfo.position == this.mCurItem) {
                        i = itemPosition;
                    }
                    itemInfo.position = itemPosition;
                }
                z = true;
            }
            i2++;
        }
        if (z2) {
            this.mAdapter.finishUpdate((ViewGroup) this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (z) {
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i3).getLayoutParams();
                if (!layoutParams.isDecor) {
                    layoutParams.widthFactor = 0.0f;
                }
            }
            setCurrentItemInternal(i, false, true);
            requestLayout();
        }
    }

    public void populate() {
        populate(this.mCurItem);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x006d, code lost:
    
        if (r9.position == r17.mCurItem) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void populate(int r18) {
        /*
            Method dump skipped, instructions count: 654
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.ViewPager.populate(int):void");
    }

    private void sortChildDrawingOrder() {
        if (this.mDrawingOrder != 0) {
            ArrayList<View> arrayList = this.mDrawingOrderedChildren;
            if (arrayList == null) {
                this.mDrawingOrderedChildren = new ArrayList<>();
            } else {
                arrayList.clear();
            }
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                this.mDrawingOrderedChildren.add(getChildAt(i));
            }
            Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
        }
    }

    private void calculatePageOffsets(ItemInfo itemInfo, int i, ItemInfo itemInfo2) {
        ItemInfo itemInfo3;
        ItemInfo itemInfo4;
        int count = this.mAdapter.getCount();
        int paddedWidth = getPaddedWidth();
        float f = paddedWidth > 0 ? this.mPageMargin / paddedWidth : 0.0f;
        if (itemInfo2 != null) {
            int i2 = itemInfo2.position;
            if (i2 < itemInfo.position) {
                float f2 = itemInfo2.offset + itemInfo2.widthFactor + f;
                int i3 = i2 + 1;
                int i4 = 0;
                while (i3 <= itemInfo.position && i4 < this.mItems.size()) {
                    ItemInfo itemInfo5 = this.mItems.get(i4);
                    while (true) {
                        itemInfo4 = itemInfo5;
                        if (i3 <= itemInfo4.position || i4 >= this.mItems.size() - 1) {
                            break;
                        }
                        i4++;
                        itemInfo5 = this.mItems.get(i4);
                    }
                    while (i3 < itemInfo4.position) {
                        f2 += this.mAdapter.getPageWidth(i3) + f;
                        i3++;
                    }
                    itemInfo4.offset = f2;
                    f2 += itemInfo4.widthFactor + f;
                    i3++;
                }
            } else if (i2 > itemInfo.position) {
                int size = this.mItems.size() - 1;
                float f3 = itemInfo2.offset;
                while (true) {
                    i2--;
                    if (i2 < itemInfo.position || size < 0) {
                        break;
                    }
                    ItemInfo itemInfo6 = this.mItems.get(size);
                    while (true) {
                        itemInfo3 = itemInfo6;
                        if (i2 >= itemInfo3.position || size <= 0) {
                            break;
                        }
                        size--;
                        itemInfo6 = this.mItems.get(size);
                    }
                    while (i2 > itemInfo3.position) {
                        f3 -= this.mAdapter.getPageWidth(i2) + f;
                        i2--;
                    }
                    f3 -= itemInfo3.widthFactor + f;
                    itemInfo3.offset = f3;
                }
            }
        }
        int size2 = this.mItems.size();
        float f4 = itemInfo.offset;
        int i5 = itemInfo.position - 1;
        this.mFirstOffset = itemInfo.position == 0 ? itemInfo.offset : -3.4028235E38f;
        int i6 = count - 1;
        this.mLastOffset = itemInfo.position == i6 ? (itemInfo.offset + itemInfo.widthFactor) - 1.0f : Float.MAX_VALUE;
        int i7 = i - 1;
        while (i7 >= 0) {
            ItemInfo itemInfo7 = this.mItems.get(i7);
            while (i5 > itemInfo7.position) {
                f4 -= this.mAdapter.getPageWidth(i5) + f;
                i5--;
            }
            f4 -= itemInfo7.widthFactor + f;
            itemInfo7.offset = f4;
            if (itemInfo7.position == 0) {
                this.mFirstOffset = f4;
            }
            i7--;
            i5--;
        }
        float f5 = itemInfo.offset + itemInfo.widthFactor + f;
        int i8 = itemInfo.position + 1;
        int i9 = i + 1;
        while (i9 < size2) {
            ItemInfo itemInfo8 = this.mItems.get(i9);
            while (i8 < itemInfo8.position) {
                f5 += this.mAdapter.getPageWidth(i8) + f;
                i8++;
            }
            if (itemInfo8.position == i6) {
                this.mLastOffset = (itemInfo8.widthFactor + f5) - 1.0f;
            }
            itemInfo8.offset = f5;
            f5 += itemInfo8.widthFactor + f;
            i9++;
            i8++;
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.android.internal.widget.ViewPager.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        Parcelable adapterState;
        ClassLoader loader;
        int position;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, i);
        }

        public String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + "}";
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            classLoader = classLoader == null ? getClass().getClassLoader() : classLoader;
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader);
            this.loader = classLoader;
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.mCurItem;
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter != null) {
            savedState.adapterState = pagerAdapter.saveState();
        }
        return savedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter != null) {
            pagerAdapter.restoreState(savedState.adapterState, savedState.loader);
            setCurrentItemInternal(savedState.position, false, true);
        } else {
            this.mRestoredCurItem = savedState.position;
            this.mRestoredAdapterState = savedState.adapterState;
            this.mRestoredClassLoader = savedState.loader;
        }
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (!checkLayoutParams(layoutParams)) {
            layoutParams = generateLayoutParams(layoutParams);
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        layoutParams2.isDecor |= view instanceof Decor;
        if (this.mInLayout) {
            if (layoutParams2 != null && layoutParams2.isDecor) {
                throw new IllegalStateException("Cannot add pager decor view during layout");
            }
            layoutParams2.needsMeasure = true;
            addViewInLayout(view, i, layoutParams);
            return;
        }
        super.addView(view, i, layoutParams);
    }

    public Object getCurrent() {
        ItemInfo infoForPosition = infoForPosition(getCurrentItem());
        if (infoForPosition == null) {
            return null;
        }
        return infoForPosition.object;
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(View view) {
        if (this.mInLayout) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    ItemInfo infoForChild(View view) {
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo itemInfo = this.mItems.get(i);
            if (this.mAdapter.isViewFromObject(view, itemInfo.object)) {
                return itemInfo;
            }
        }
        return null;
    }

    ItemInfo infoForAnyChild(View view) {
        while (true) {
            Object parent = view.getParent();
            if (parent != this) {
                if (parent == null || !(parent instanceof View)) {
                    return null;
                }
                view = (View) parent;
            } else {
                return infoForChild(view);
            }
        }
    }

    ItemInfo infoForPosition(int i) {
        for (int i2 = 0; i2 < this.mItems.size(); i2++) {
            ItemInfo itemInfo = this.mItems.get(i2);
            if (itemInfo.position == i) {
                return itemInfo;
            }
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        LayoutParams layoutParams;
        LayoutParams layoutParams2;
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z = false;
        setMeasuredDimension(getDefaultSize(0, i), getDefaultSize(0, i2));
        int measuredWidth = getMeasuredWidth();
        this.mGutterSize = Math.min(measuredWidth / 10, this.mDefaultGutterSize);
        int paddingLeft = (measuredWidth - getPaddingLeft()) - getPaddingRight();
        int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int childCount = getChildCount();
        int i7 = 0;
        while (true) {
            boolean z2 = true;
            int i8 = 1073741824;
            if (i7 >= childCount) {
                break;
            }
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8 && (layoutParams2 = (LayoutParams) childAt.getLayoutParams()) != null && layoutParams2.isDecor) {
                int i9 = layoutParams2.gravity & 7;
                int i10 = layoutParams2.gravity & 112;
                boolean z3 = (i10 == 48 || i10 == 80) ? true : z;
                if (i9 != 3 && i9 != 5) {
                    z2 = z;
                }
                int i11 = Integer.MIN_VALUE;
                if (z3) {
                    i3 = Integer.MIN_VALUE;
                    i11 = 1073741824;
                } else {
                    i3 = z2 ? 1073741824 : Integer.MIN_VALUE;
                }
                if (layoutParams2.width != -2) {
                    i5 = layoutParams2.width != -1 ? layoutParams2.width : paddingLeft;
                    i4 = 1073741824;
                } else {
                    i4 = i11;
                    i5 = paddingLeft;
                }
                if (layoutParams2.height != -2) {
                    i6 = layoutParams2.height != -1 ? layoutParams2.height : measuredHeight;
                } else {
                    i6 = measuredHeight;
                    i8 = i3;
                }
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i5, i4), View.MeasureSpec.makeMeasureSpec(i6, i8));
                if (z3) {
                    measuredHeight -= childAt.getMeasuredHeight();
                } else if (z2) {
                    paddingLeft -= childAt.getMeasuredWidth();
                }
            }
            i7++;
            z = false;
        }
        this.mChildWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(paddingLeft, 1073741824);
        this.mChildHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824);
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        int childCount2 = getChildCount();
        for (int i12 = 0; i12 < childCount2; i12++) {
            View childAt2 = getChildAt(i12);
            if (childAt2.getVisibility() != 8 && ((layoutParams = (LayoutParams) childAt2.getLayoutParams()) == null || !layoutParams.isDecor)) {
                childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (paddingLeft * layoutParams.widthFactor), 1073741824), this.mChildHeightMeasureSpec);
            }
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            int i5 = this.mPageMargin;
            recomputeScrollPosition(i, i3, i5, i5);
        }
    }

    private void recomputeScrollPosition(int i, int i2, int i3, int i4) {
        if (i2 > 0 && !this.mItems.isEmpty()) {
            int scrollX = (int) ((getScrollX() / (((i2 - getPaddingLeft()) - getPaddingRight()) + i4)) * (((i - getPaddingLeft()) - getPaddingRight()) + i3));
            scrollTo(scrollX, getScrollY());
            if (this.mScroller.isFinished()) {
                return;
            }
            this.mScroller.startScroll(scrollX, 0, (int) (infoForPosition(this.mCurItem).offset * i), 0, this.mScroller.getDuration() - this.mScroller.timePassed());
            return;
        }
        ItemInfo infoForPosition = infoForPosition(this.mCurItem);
        int min = (int) ((infoForPosition != null ? Math.min(infoForPosition.offset, this.mLastOffset) : 0.0f) * ((i - getPaddingLeft()) - getPaddingRight()));
        if (min != getScrollX()) {
            completeScroll(false);
            scrollTo(min, getScrollY());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0090  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onLayout(boolean r19, int r20, int r21, int r22, int r23) {
        /*
            Method dump skipped, instructions count: 303
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.ViewPager.onLayout(boolean, int, int, int, int):void");
    }

    @Override // android.view.View
    public void computeScroll() {
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
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
            postInvalidateOnAnimation();
            return;
        }
        completeScroll(true);
    }

    private boolean pageScrolled(int i) {
        if (this.mItems.size() == 0) {
            this.mCalledSuper = false;
            onPageScrolled(0, 0.0f, 0);
            if (this.mCalledSuper) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        if (isLayoutRtl()) {
            i = 16777216 - i;
        }
        ItemInfo infoForFirstVisiblePage = infoForFirstVisiblePage();
        int paddedWidth = getPaddedWidth();
        int i2 = this.mPageMargin;
        int i3 = paddedWidth + i2;
        float f = paddedWidth;
        int i4 = infoForFirstVisiblePage.position;
        float f2 = ((i / f) - infoForFirstVisiblePage.offset) / (infoForFirstVisiblePage.widthFactor + (i2 / f));
        this.mCalledSuper = false;
        onPageScrolled(i4, f2, (int) (i3 * f2));
        if (this.mCalledSuper) {
            return true;
        }
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0064  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onPageScrolled(int r13, float r14, int r15) {
        /*
            r12 = this;
            int r0 = r12.mDecorChildCount
            r1 = 0
            r2 = 1
            if (r0 <= 0) goto L6b
            int r0 = r12.getScrollX()
            int r3 = r12.getPaddingLeft()
            int r4 = r12.getPaddingRight()
            int r5 = r12.getWidth()
            int r6 = r12.getChildCount()
            r7 = r1
        L1b:
            if (r7 >= r6) goto L6b
            android.view.View r8 = r12.getChildAt(r7)
            android.view.ViewGroup$LayoutParams r9 = r8.getLayoutParams()
            com.android.internal.widget.ViewPager$LayoutParams r9 = (com.android.internal.widget.ViewPager.LayoutParams) r9
            boolean r10 = r9.isDecor
            if (r10 != 0) goto L2c
            goto L68
        L2c:
            int r9 = r9.gravity
            r9 = r9 & 7
            if (r9 == r2) goto L4d
            r10 = 3
            if (r9 == r10) goto L47
            r10 = 5
            if (r9 == r10) goto L3a
            r9 = r3
            goto L5c
        L3a:
            int r9 = r5 - r4
            int r10 = r8.getMeasuredWidth()
            int r9 = r9 - r10
            int r10 = r8.getMeasuredWidth()
            int r4 = r4 + r10
            goto L59
        L47:
            int r9 = r8.getWidth()
            int r9 = r9 + r3
            goto L5c
        L4d:
            int r9 = r8.getMeasuredWidth()
            int r9 = r5 - r9
            int r9 = r9 / 2
            int r9 = java.lang.Math.max(r9, r3)
        L59:
            r11 = r9
            r9 = r3
            r3 = r11
        L5c:
            int r3 = r3 + r0
            int r10 = r8.getLeft()
            int r3 = r3 - r10
            if (r3 == 0) goto L67
            r8.offsetLeftAndRight(r3)
        L67:
            r3 = r9
        L68:
            int r7 = r7 + 1
            goto L1b
        L6b:
            com.android.internal.widget.ViewPager$OnPageChangeListener r0 = r12.mOnPageChangeListener
            if (r0 == 0) goto L72
            r0.onPageScrolled(r13, r14, r15)
        L72:
            com.android.internal.widget.ViewPager$OnPageChangeListener r0 = r12.mInternalPageChangeListener
            if (r0 == 0) goto L79
            r0.onPageScrolled(r13, r14, r15)
        L79:
            com.android.internal.widget.ViewPager$PageTransformer r13 = r12.mPageTransformer
            if (r13 == 0) goto Laa
            int r13 = r12.getScrollX()
            int r14 = r12.getChildCount()
        L85:
            if (r1 >= r14) goto Laa
            android.view.View r15 = r12.getChildAt(r1)
            android.view.ViewGroup$LayoutParams r0 = r15.getLayoutParams()
            com.android.internal.widget.ViewPager$LayoutParams r0 = (com.android.internal.widget.ViewPager.LayoutParams) r0
            boolean r0 = r0.isDecor
            if (r0 == 0) goto L96
            goto La7
        L96:
            int r0 = r15.getLeft()
            int r0 = r0 - r13
            float r0 = (float) r0
            int r3 = r12.getPaddedWidth()
            float r3 = (float) r3
            float r0 = r0 / r3
            com.android.internal.widget.ViewPager$PageTransformer r3 = r12.mPageTransformer
            r3.transformPage(r15, r0)
        La7:
            int r1 = r1 + 1
            goto L85
        Laa:
            r12.mCalledSuper = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.ViewPager.onPageScrolled(int, float, int):void");
    }

    private void completeScroll(boolean z) {
        boolean z2 = this.mScrollState == 2;
        if (z2) {
            setScrollingCacheEnabled(false);
            this.mScroller.abortAnimation();
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            if (scrollX != currX || scrollY != currY) {
                scrollTo(currX, currY);
            }
        }
        this.mPopulatePending = false;
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo itemInfo = this.mItems.get(i);
            if (itemInfo.scrolling) {
                itemInfo.scrolling = false;
                z2 = true;
            }
        }
        if (z2) {
            if (z) {
                postOnAnimation(this.mEndScrollRunnable);
            } else {
                this.mEndScrollRunnable.run();
            }
        }
    }

    private boolean isGutterDrag(float f, float f2) {
        if (f >= this.mGutterSize || f2 <= 0.0f) {
            return f > ((float) (getWidth() - this.mGutterSize)) && f2 < 0.0f;
        }
        return true;
    }

    private void enableLayers(boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).setLayerType(z ? 2 : 0, null);
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        float f;
        int action = motionEvent.getAction() & 255;
        if (action == 3 || action == 1) {
            this.mIsBeingDragged = false;
            this.mIsUnableToDrag = false;
            this.mActivePointerId = -1;
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.mVelocityTracker = null;
            }
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
            this.mScroller.computeScrollOffset();
            if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough) {
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                populate();
                this.mIsBeingDragged = true;
                requestParentDisallowInterceptTouchEvent(true);
                setScrollState(1);
            } else if (this.mLeftEdge.getDistance() != 0.0f || this.mRightEdge.getDistance() != 0.0f) {
                this.mIsBeingDragged = true;
                setScrollState(1);
                if (this.mLeftEdge.getDistance() != 0.0f) {
                    this.mLeftEdge.onPullDistance(0.0f, 1.0f - (this.mLastMotionY / getHeight()));
                }
                if (this.mRightEdge.getDistance() != 0.0f) {
                    this.mRightEdge.onPullDistance(0.0f, this.mLastMotionY / getHeight());
                }
            } else {
                completeScroll(false);
                this.mIsBeingDragged = false;
            }
        } else if (action == 2) {
            int i = this.mActivePointerId;
            if (i != -1) {
                int findPointerIndex = motionEvent.findPointerIndex(i);
                float x2 = motionEvent.getX(findPointerIndex);
                float f2 = x2 - this.mLastMotionX;
                float abs = Math.abs(f2);
                float y2 = motionEvent.getY(findPointerIndex);
                float abs2 = Math.abs(y2 - this.mInitialMotionY);
                if (f2 != 0.0f && !isGutterDrag(this.mLastMotionX, f2) && canScroll(this, false, (int) f2, (int) x2, (int) y2)) {
                    this.mLastMotionX = x2;
                    this.mLastMotionY = y2;
                    this.mIsUnableToDrag = true;
                    return false;
                }
                int i2 = this.mTouchSlop;
                if (abs > i2 && abs * 0.5f > abs2) {
                    this.mIsBeingDragged = true;
                    requestParentDisallowInterceptTouchEvent(true);
                    setScrollState(1);
                    if (f2 > 0.0f) {
                        f = this.mInitialMotionX + this.mTouchSlop;
                    } else {
                        f = this.mInitialMotionX - this.mTouchSlop;
                    }
                    this.mLastMotionX = f;
                    this.mLastMotionY = y2;
                    setScrollingCacheEnabled(true);
                } else if (abs2 > i2) {
                    this.mIsUnableToDrag = true;
                }
                if (this.mIsBeingDragged && performDrag(x2, y2)) {
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

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        PagerAdapter pagerAdapter;
        float f;
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
                    float x2 = motionEvent.getX(findPointerIndex);
                    float abs = Math.abs(x2 - this.mLastMotionX);
                    float y2 = motionEvent.getY(findPointerIndex);
                    float abs2 = Math.abs(y2 - this.mLastMotionY);
                    if (abs > this.mTouchSlop && abs > abs2) {
                        this.mIsBeingDragged = true;
                        requestParentDisallowInterceptTouchEvent(true);
                        float f2 = this.mInitialMotionX;
                        this.mLastMotionX = x2 - f2 > 0.0f ? f2 + this.mTouchSlop : f2 - this.mTouchSlop;
                        this.mLastMotionY = y2;
                        setScrollState(1);
                        setScrollingCacheEnabled(true);
                        ViewParent parent = getParent();
                        if (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                if (this.mIsBeingDragged) {
                    int findPointerIndex2 = motionEvent.findPointerIndex(this.mActivePointerId);
                    z = performDrag(motionEvent.getX(findPointerIndex2), motionEvent.getY(findPointerIndex2));
                }
            } else if (action != 3) {
                if (action == 5) {
                    int actionIndex = motionEvent.getActionIndex();
                    this.mLastMotionX = motionEvent.getX(actionIndex);
                    this.mActivePointerId = motionEvent.getPointerId(actionIndex);
                } else if (action == 6) {
                    onSecondaryPointerUp(motionEvent);
                    this.mLastMotionX = motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
                }
            } else if (this.mIsBeingDragged) {
                scrollToItem(this.mCurItem, true, 0, false);
                this.mActivePointerId = -1;
                endDrag();
                this.mLeftEdge.onRelease();
                this.mRightEdge.onRelease();
                z = true;
            }
        } else if (this.mIsBeingDragged) {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
            int xVelocity = (int) velocityTracker.getXVelocity(this.mActivePointerId);
            this.mPopulatePending = true;
            float scrollStart = getScrollStart() / getPaddedWidth();
            ItemInfo infoForFirstVisiblePage = infoForFirstVisiblePage();
            int i = infoForFirstVisiblePage.position;
            if (isLayoutRtl()) {
                f = (infoForFirstVisiblePage.offset - scrollStart) / infoForFirstVisiblePage.widthFactor;
            } else {
                f = (scrollStart - infoForFirstVisiblePage.offset) / infoForFirstVisiblePage.widthFactor;
            }
            setCurrentItemInternal(determineTargetPage(i, f, xVelocity, (int) (motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId)) - this.mInitialMotionX)), true, true, xVelocity);
            this.mActivePointerId = -1;
            endDrag();
            this.mLeftEdge.onRelease();
            this.mRightEdge.onRelease();
            z = true;
        }
        if (z) {
            postInvalidateOnAnimation();
        }
        return true;
    }

    private void requestParentDisallowInterceptTouchEvent(boolean z) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z);
        }
    }

    private float releaseHorizontalGlow(float f, float f2) {
        float height = f2 / getHeight();
        float width = f / getWidth();
        float f3 = 0.0f;
        if (this.mLeftEdge.getDistance() != 0.0f) {
            f3 = -this.mLeftEdge.onPullDistance(-width, 1.0f - height);
        } else if (this.mRightEdge.getDistance() != 0.0f) {
            f3 = this.mRightEdge.onPullDistance(width, height);
        }
        return f3 * getWidth();
    }

    private boolean performDrag(float f, float f2) {
        EdgeEffect edgeEffect;
        EdgeEffect edgeEffect2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7 = this.mLastMotionX - f;
        int paddedWidth = getPaddedWidth();
        this.mLastMotionX = f;
        float releaseHorizontalGlow = releaseHorizontalGlow(f7, f2);
        float f8 = f7 - releaseHorizontalGlow;
        boolean z = true;
        boolean z2 = releaseHorizontalGlow != 0.0f;
        if (Math.abs(f8) < 1.0E-4f) {
            return z2;
        }
        if (isLayoutRtl()) {
            edgeEffect = this.mRightEdge;
            edgeEffect2 = this.mLeftEdge;
        } else {
            edgeEffect = this.mLeftEdge;
            edgeEffect2 = this.mRightEdge;
        }
        float scrollX = getScrollX() + f8;
        if (isLayoutRtl()) {
            scrollX = 1.6777216E7f - scrollX;
        }
        ItemInfo itemInfo = this.mItems.get(0);
        boolean z3 = itemInfo.position == 0;
        if (z3) {
            f3 = itemInfo.offset;
            f4 = paddedWidth;
        } else {
            f3 = paddedWidth;
            f4 = this.mFirstOffset;
        }
        float f9 = f3 * f4;
        ArrayList<ItemInfo> arrayList = this.mItems;
        ItemInfo itemInfo2 = arrayList.get(arrayList.size() - 1);
        boolean z4 = itemInfo2.position == this.mAdapter.getCount() - 1;
        if (z4) {
            f5 = itemInfo2.offset;
            f6 = paddedWidth;
        } else {
            f5 = paddedWidth;
            f6 = this.mLastOffset;
        }
        float f10 = f5 * f6;
        if (scrollX < f9) {
            if (z3) {
                edgeEffect.onPullDistance((f9 - scrollX) / paddedWidth, 1.0f - (f2 / getHeight()));
            } else {
                z = z2;
            }
            scrollX = f9;
            z2 = z;
        } else if (scrollX > f10) {
            if (z4) {
                edgeEffect2.onPullDistance((scrollX - f10) / paddedWidth, f2 / getHeight());
            } else {
                z = z2;
            }
            z2 = z;
            scrollX = f10;
        }
        if (isLayoutRtl()) {
            scrollX = 1.6777216E7f - scrollX;
        }
        int i = (int) scrollX;
        this.mLastMotionX += scrollX - i;
        scrollTo(i, getScrollY());
        pageScrolled(i);
        return z2;
    }

    private ItemInfo infoForFirstVisiblePage() {
        int i;
        int scrollStart = getScrollStart();
        int paddedWidth = getPaddedWidth();
        float f = 0.0f;
        float f2 = paddedWidth > 0 ? scrollStart / paddedWidth : 0.0f;
        float f3 = paddedWidth > 0 ? this.mPageMargin / paddedWidth : 0.0f;
        int size = this.mItems.size();
        int i2 = 0;
        boolean z = true;
        ItemInfo itemInfo = null;
        int i3 = -1;
        float f4 = 0.0f;
        while (i2 < size) {
            ItemInfo itemInfo2 = this.mItems.get(i2);
            if (!z && itemInfo2.position != (i = i3 + 1)) {
                itemInfo2 = this.mTempItem;
                itemInfo2.offset = f + f4 + f3;
                itemInfo2.position = i;
                itemInfo2.widthFactor = this.mAdapter.getPageWidth(itemInfo2.position);
                i2--;
            }
            ItemInfo itemInfo3 = itemInfo2;
            f = itemInfo3.offset;
            if (!z && f2 < f) {
                break;
            }
            if (f2 < itemInfo3.widthFactor + f + f3 || i2 == this.mItems.size() - 1) {
                return itemInfo3;
            }
            int i4 = itemInfo3.position;
            float f5 = itemInfo3.widthFactor;
            i2++;
            i3 = i4;
            f4 = f5;
            itemInfo = itemInfo3;
            z = false;
        }
        return itemInfo;
    }

    private int getScrollStart() {
        if (isLayoutRtl()) {
            return 16777216 - getScrollX();
        }
        return getScrollX();
    }

    private int determineTargetPage(int i, float f, int i2, int i3) {
        int i4;
        if (Math.abs(i3) > this.mFlingDistance && Math.abs(i2) > this.mMinimumVelocity && this.mLeftEdge.getDistance() == 0.0f && this.mRightEdge.getDistance() == 0.0f) {
            i4 = i - (i2 < 0 ? this.mLeftIncr : 0);
        } else {
            i4 = (int) (i - (this.mLeftIncr * (f + (i >= this.mCurItem ? 0.4f : 0.6f))));
        }
        if (this.mItems.size() <= 0) {
            return i4;
        }
        return MathUtils.constrain(i4, this.mItems.get(0).position, this.mItems.get(r2.size() - 1).position);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
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
                canvas.translate((-height) + getPaddingTop(), this.mFirstOffset * width);
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
            postInvalidateOnAnimation();
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        float pageWidth;
        int i;
        super.onDraw(canvas);
        if (this.mPageMargin <= 0 || this.mMarginDrawable == null || this.mItems.size() <= 0 || this.mAdapter == null) {
            return;
        }
        int scrollX = getScrollX();
        float width = getWidth();
        float f = this.mPageMargin / width;
        int i2 = 0;
        ItemInfo itemInfo = this.mItems.get(0);
        float f2 = itemInfo.offset;
        int size = this.mItems.size();
        int i3 = itemInfo.position;
        int i4 = this.mItems.get(size - 1).position;
        while (i3 < i4) {
            while (i3 > itemInfo.position && i2 < size) {
                i2++;
                itemInfo = this.mItems.get(i2);
            }
            if (i3 == itemInfo.position) {
                f2 = itemInfo.offset;
                pageWidth = itemInfo.widthFactor;
            } else {
                pageWidth = this.mAdapter.getPageWidth(i3);
            }
            float f3 = f2 * width;
            float f4 = isLayoutRtl() ? 1.6777216E7f - f3 : (pageWidth * width) + f3;
            f2 = f2 + pageWidth + f;
            int i5 = this.mPageMargin;
            if (i5 + f4 > scrollX) {
                i = scrollX;
                this.mMarginDrawable.setBounds((int) f4, this.mTopPageBounds, (int) (i5 + f4 + 0.5f), this.mBottomPageBounds);
                this.mMarginDrawable.draw(canvas);
            } else {
                i = scrollX;
            }
            if (f4 > i + r2) {
                return;
            }
            i3++;
            scrollX = i;
        }
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
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

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void setScrollingCacheEnabled(boolean z) {
        if (this.mScrollingCacheEnabled != z) {
            this.mScrollingCacheEnabled = z;
        }
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i) {
        if (this.mAdapter == null) {
            return false;
        }
        int paddedWidth = getPaddedWidth();
        int scrollX = getScrollX();
        return i < 0 ? scrollX > ((int) (((float) paddedWidth) * this.mFirstOffset)) : i > 0 && scrollX < ((int) (((float) paddedWidth) * this.mLastOffset));
    }

    protected boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        int i4;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i5 = i2 + scrollX;
                if (i5 >= childAt.getLeft() && i5 < childAt.getRight() && (i4 = i3 + scrollY) >= childAt.getTop() && i4 < childAt.getBottom() && canScroll(childAt, true, i, i5 - childAt.getLeft(), i4 - childAt.getTop())) {
                    return true;
                }
            }
        }
        return z && view.canScrollHorizontally(-i);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0) {
            return false;
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 21) {
            return arrowScroll(17);
        }
        if (keyCode == 22) {
            return arrowScroll(66);
        }
        if (keyCode != 61) {
            return false;
        }
        if (keyEvent.hasNoModifiers()) {
            return arrowScroll(2);
        }
        if (keyEvent.hasModifiers(1)) {
            return arrowScroll(1);
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00c9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean arrowScroll(int r5) {
        /*
            r4 = this;
            android.view.View r0 = r4.findFocus()
            r1 = 0
            if (r0 != r4) goto L9
        L7:
            r0 = r1
            goto L60
        L9:
            if (r0 == 0) goto L60
            android.view.ViewParent r2 = r0.getParent()
        Lf:
            boolean r3 = r2 instanceof android.view.ViewGroup
            if (r3 == 0) goto L1b
            if (r2 != r4) goto L16
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
            android.view.View r1 = r1.findNextFocus(r4, r0, r5)
            r2 = 66
            r3 = 17
            if (r1 == 0) goto Lb0
            if (r1 == r0) goto Lb0
            if (r5 != r3) goto L90
            android.graphics.Rect r2 = r4.mTempRect
            android.graphics.Rect r2 = r4.getChildRectInPagerCoordinates(r2, r1)
            int r2 = r2.left
            android.graphics.Rect r3 = r4.mTempRect
            android.graphics.Rect r3 = r4.getChildRectInPagerCoordinates(r3, r0)
            int r3 = r3.left
            if (r0 == 0) goto L8b
            if (r2 < r3) goto L8b
            boolean r0 = r4.pageLeft()
            goto Lc7
        L8b:
            boolean r0 = r1.requestFocus()
            goto Lc7
        L90:
            if (r5 != r2) goto Lbc
            android.graphics.Rect r2 = r4.mTempRect
            android.graphics.Rect r2 = r4.getChildRectInPagerCoordinates(r2, r1)
            int r2 = r2.left
            android.graphics.Rect r3 = r4.mTempRect
            android.graphics.Rect r3 = r4.getChildRectInPagerCoordinates(r3, r0)
            int r3 = r3.left
            if (r0 == 0) goto Lab
            if (r2 > r3) goto Lab
            boolean r0 = r4.pageRight()
            goto Lc7
        Lab:
            boolean r0 = r1.requestFocus()
            goto Lc7
        Lb0:
            if (r5 == r3) goto Lc3
            r0 = 1
            if (r5 != r0) goto Lb6
            goto Lc3
        Lb6:
            if (r5 == r2) goto Lbe
            r0 = 2
            if (r5 != r0) goto Lbc
            goto Lbe
        Lbc:
            r0 = 0
            goto Lc7
        Lbe:
            boolean r0 = r4.pageRight()
            goto Lc7
        Lc3:
            boolean r0 = r4.pageLeft()
        Lc7:
            if (r0 == 0) goto Ld0
            int r5 = android.view.SoundEffectConstants.getContantForFocusDirection(r5)
            r4.playSoundEffect(r5)
        Ld0:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.ViewPager.arrowScroll(int):boolean");
    }

    private Rect getChildRectInPagerCoordinates(Rect rect, View view) {
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
            rect.left += viewGroup.getLeft();
            rect.right += viewGroup.getRight();
            rect.top += viewGroup.getTop();
            rect.bottom += viewGroup.getBottom();
            parent = viewGroup.getParent();
        }
        return rect;
    }

    boolean pageLeft() {
        return setCurrentItemInternal(this.mCurItem + this.mLeftIncr, true, false);
    }

    boolean pageRight() {
        return setCurrentItemInternal(this.mCurItem - this.mLeftIncr, true, false);
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (i == 0) {
            this.mLeftIncr = -1;
        } else {
            this.mLeftIncr = 1;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        ItemInfo infoForChild;
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
            if (((i2 & 1) == 1 && isInTouchMode() && !isFocusableInTouchMode()) || arrayList == null) {
                return;
            }
            arrayList.add(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addTouchables(ArrayList<View> arrayList) {
        ItemInfo infoForChild;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0 && (infoForChild = infoForChild(childAt)) != null && infoForChild.position == this.mCurItem) {
                childAt.addTouchables(arrayList);
            }
        }
    }

    @Override // android.view.ViewGroup
    protected boolean onRequestFocusInDescendants(int i, Rect rect) {
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

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return generateDefaultLayoutParams();
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        PagerAdapter pagerAdapter;
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(ViewPager.class.getName());
        accessibilityEvent.setScrollable(canScroll());
        if (accessibilityEvent.getEventType() != 4096 || (pagerAdapter = this.mAdapter) == null) {
            return;
        }
        accessibilityEvent.setItemCount(pagerAdapter.getCount());
        accessibilityEvent.setFromIndex(this.mCurItem);
        accessibilityEvent.setToIndex(this.mCurItem);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(ViewPager.class.getName());
        accessibilityNodeInfo.setScrollable(canScroll());
        if (canScrollHorizontally(1)) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT);
        }
        if (canScrollHorizontally(-1)) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT);
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        if (super.performAccessibilityAction(i, bundle)) {
            return true;
        }
        if (i != 4096) {
            if (i == 8192 || i == 16908345) {
                if (!canScrollHorizontally(-1)) {
                    return false;
                }
                setCurrentItem(this.mCurItem - 1);
                return true;
            }
            if (i != 16908347) {
                return false;
            }
        }
        if (!canScrollHorizontally(1)) {
            return false;
        }
        setCurrentItem(this.mCurItem + 1);
        return true;
    }

    private boolean canScroll() {
        PagerAdapter pagerAdapter = this.mAdapter;
        return pagerAdapter != null && pagerAdapter.getCount() > 1;
    }

    private class PagerObserver extends DataSetObserver {
        private PagerObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            ViewPager.this.dataSetChanged();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            ViewPager.this.dataSetChanged();
        }
    }

    static class ViewPositionComparator implements Comparator<View> {
        ViewPositionComparator() {
        }

        @Override // java.util.Comparator
        public int compare(View view, View view2) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams();
            if (layoutParams.isDecor != layoutParams2.isDecor) {
                return layoutParams.isDecor ? 1 : -1;
            }
            return layoutParams.position - layoutParams2.position;
        }
    }
}
