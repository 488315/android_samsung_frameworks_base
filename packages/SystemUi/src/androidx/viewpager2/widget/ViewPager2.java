package androidx.viewpager2.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.collection.LongSparseArray;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.Fragment$5$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.R$styleable;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.StatefulAdapter;
import androidx.viewpager2.widget.ScrollEventAdapter;
import com.android.systemui.controls.management.StructureAdapter;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ViewPager2 extends ViewGroup {
    public PageAwareAccessibilityProvider mAccessibilityProvider;
    public int mCurrentItem;
    public final C05781 mCurrentItemDataSetChangeObserver;
    public boolean mCurrentItemDirty;
    public final CompositeOnPageChangeCallback mExternalPageChangeCallbacks;
    public FakeDrag mFakeDragger;
    public LinearLayoutManagerImpl mLayoutManager;
    public final int mOffscreenPageLimit;
    public CompositeOnPageChangeCallback mPageChangeEventDispatcher;
    public PagerSnapHelperImpl mPagerSnapHelper;
    public Parcelable mPendingAdapterState;
    public int mPendingCurrentItem;
    public RecyclerViewImpl mRecyclerView;
    public ScrollEventAdapter mScrollEventAdapter;
    public final Rect mTmpChildRect;
    public final Rect mTmpContainerRect;
    public final boolean mUserInputEnabled;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class AccessibilityProvider {
        private AccessibilityProvider(ViewPager2 viewPager2) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BasicAccessibilityProvider extends AccessibilityProvider {
        public BasicAccessibilityProvider(ViewPager2 viewPager2) {
            super();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class DataSetChangeObserver extends RecyclerView.AdapterDataObserver {
        private DataSetChangeObserver() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public abstract void onChanged();

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeChanged(int i, int i2) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeInserted(int i, int i2) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeMoved(int i, int i2) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeRemoved(int i, int i2) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeChanged(int i, int i2, Object obj) {
            onChanged();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LinearLayoutManagerImpl extends LinearLayoutManager {
        public LinearLayoutManagerImpl(Context context) {
            super(context);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        public final void calculateExtraLayoutSpace(RecyclerView.State state, int[] iArr) {
            int height;
            int paddingBottom;
            ViewPager2 viewPager2 = ViewPager2.this;
            int i = viewPager2.mOffscreenPageLimit;
            if (i == -1) {
                super.calculateExtraLayoutSpace(state, iArr);
                return;
            }
            RecyclerViewImpl recyclerViewImpl = viewPager2.mRecyclerView;
            if (viewPager2.mLayoutManager.mOrientation == 0) {
                height = recyclerViewImpl.getWidth() - recyclerViewImpl.getPaddingLeft();
                paddingBottom = recyclerViewImpl.getPaddingRight();
            } else {
                height = recyclerViewImpl.getHeight() - recyclerViewImpl.getPaddingTop();
                paddingBottom = recyclerViewImpl.getPaddingBottom();
            }
            int i2 = (height - paddingBottom) * i;
            iArr[0] = i2;
            iArr[1] = i2;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public final void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(recycler, state, accessibilityNodeInfoCompat);
            ViewPager2.this.mAccessibilityProvider.getClass();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public final boolean performAccessibilityAction(RecyclerView.Recycler recycler, RecyclerView.State state, int i, Bundle bundle) {
            ViewPager2.this.mAccessibilityProvider.getClass();
            return super.performAccessibilityAction(recycler, state, i, bundle);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public final boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
            return false;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PageAwareAccessibilityProvider extends AccessibilityProvider {
        public final C05832 mActionPageBackward;
        public final C05821 mActionPageForward;
        public C05843 mAdapterDataObserver;

        /* JADX WARN: Type inference failed for: r2v1, types: [androidx.viewpager2.widget.ViewPager2$PageAwareAccessibilityProvider$1] */
        /* JADX WARN: Type inference failed for: r2v2, types: [androidx.viewpager2.widget.ViewPager2$PageAwareAccessibilityProvider$2] */
        public PageAwareAccessibilityProvider() {
            super();
            this.mActionPageForward = new AccessibilityViewCommand() { // from class: androidx.viewpager2.widget.ViewPager2.PageAwareAccessibilityProvider.1
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public final boolean perform(View view) {
                    int i = ((ViewPager2) view).mCurrentItem + 1;
                    ViewPager2 viewPager2 = ViewPager2.this;
                    if (viewPager2.mUserInputEnabled) {
                        viewPager2.setCurrentItemInternal(i);
                    }
                    return true;
                }
            };
            this.mActionPageBackward = new AccessibilityViewCommand() { // from class: androidx.viewpager2.widget.ViewPager2.PageAwareAccessibilityProvider.2
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public final boolean perform(View view) {
                    int i = ((ViewPager2) view).mCurrentItem - 1;
                    ViewPager2 viewPager2 = ViewPager2.this;
                    if (viewPager2.mUserInputEnabled) {
                        viewPager2.setCurrentItemInternal(i);
                    }
                    return true;
                }
            };
        }

        /* JADX WARN: Type inference failed for: r2v1, types: [androidx.viewpager2.widget.ViewPager2$PageAwareAccessibilityProvider$3] */
        public final void onInitialize(RecyclerView recyclerView) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setImportantForAccessibility(recyclerView, 2);
            this.mAdapterDataObserver = new DataSetChangeObserver() { // from class: androidx.viewpager2.widget.ViewPager2.PageAwareAccessibilityProvider.3
                @Override // androidx.viewpager2.widget.ViewPager2.DataSetChangeObserver, androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public final void onChanged() {
                    PageAwareAccessibilityProvider.this.updatePageAccessibilityActions();
                }
            };
            ViewPager2 viewPager2 = ViewPager2.this;
            if (ViewCompat.Api16Impl.getImportantForAccessibility(viewPager2) == 0) {
                ViewCompat.Api16Impl.setImportantForAccessibility(viewPager2, 1);
            }
        }

        public final void updatePageAccessibilityActions() {
            int itemCount;
            ViewPager2 viewPager2 = ViewPager2.this;
            int i = R.id.accessibilityActionPageLeft;
            ViewCompat.removeActionWithId(viewPager2, R.id.accessibilityActionPageLeft);
            ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(viewPager2, 0);
            ViewCompat.removeActionWithId(viewPager2, R.id.accessibilityActionPageRight);
            ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(viewPager2, 0);
            ViewCompat.removeActionWithId(viewPager2, R.id.accessibilityActionPageUp);
            ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(viewPager2, 0);
            ViewCompat.removeActionWithId(viewPager2, R.id.accessibilityActionPageDown);
            ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(viewPager2, 0);
            RecyclerView.Adapter adapter = viewPager2.mRecyclerView.mAdapter;
            if (adapter == null || (itemCount = adapter.getItemCount()) == 0 || !viewPager2.mUserInputEnabled) {
                return;
            }
            LinearLayoutManagerImpl linearLayoutManagerImpl = viewPager2.mLayoutManager;
            int i2 = linearLayoutManagerImpl.mOrientation;
            C05832 c05832 = this.mActionPageBackward;
            C05821 c05821 = this.mActionPageForward;
            if (i2 != 0) {
                if (viewPager2.mCurrentItem < itemCount - 1) {
                    ViewCompat.replaceAccessibilityAction(viewPager2, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(R.id.accessibilityActionPageDown, null), null, c05821);
                }
                if (viewPager2.mCurrentItem > 0) {
                    ViewCompat.replaceAccessibilityAction(viewPager2, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(R.id.accessibilityActionPageUp, null), null, c05832);
                    return;
                }
                return;
            }
            boolean z = linearLayoutManagerImpl.getLayoutDirection() == 1;
            int i3 = z ? 16908360 : 16908361;
            if (z) {
                i = 16908361;
            }
            if (viewPager2.mCurrentItem < itemCount - 1) {
                ViewCompat.replaceAccessibilityAction(viewPager2, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i3, null), null, c05821);
            }
            if (viewPager2.mCurrentItem > 0) {
                ViewCompat.replaceAccessibilityAction(viewPager2, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i, null), null, c05832);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PagerSnapHelperImpl extends PagerSnapHelper {
        public PagerSnapHelperImpl() {
        }

        @Override // androidx.recyclerview.widget.PagerSnapHelper, androidx.recyclerview.widget.SnapHelper
        public final View findSnapView(RecyclerView.LayoutManager layoutManager) {
            if (ViewPager2.this.mFakeDragger.mScrollEventAdapter.mFakeDragging) {
                return null;
            }
            return super.findSnapView(layoutManager);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RecyclerViewImpl extends RecyclerView {
        public RecyclerViewImpl(Context context) {
            super(context);
        }

        @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        public final CharSequence getAccessibilityClassName() {
            PageAwareAccessibilityProvider pageAwareAccessibilityProvider = ViewPager2.this.mAccessibilityProvider;
            pageAwareAccessibilityProvider.getClass();
            if (!(pageAwareAccessibilityProvider instanceof BasicAccessibilityProvider)) {
                return "androidx.recyclerview.widget.RecyclerView";
            }
            ViewPager2.this.mAccessibilityProvider.getClass();
            throw new IllegalStateException("Not implemented.");
        }

        @Override // android.view.View
        public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setFromIndex(ViewPager2.this.mCurrentItem);
            accessibilityEvent.setToIndex(ViewPager2.this.mCurrentItem);
            accessibilityEvent.setSource(ViewPager2.this);
            accessibilityEvent.setClassName("androidx.viewpager.widget.ViewPager");
        }

        @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return ViewPager2.this.mUserInputEnabled && super.onInterceptTouchEvent(motionEvent);
        }

        @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            return ViewPager2.this.mUserInputEnabled && super.onTouchEvent(motionEvent);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SmoothScrollToPosition implements Runnable {
        public final int mPosition;
        public final RecyclerView mRecyclerView;

        public SmoothScrollToPosition(int i, RecyclerView recyclerView) {
            this.mPosition = i;
            this.mRecyclerView = recyclerView;
        }

        @Override // java.lang.Runnable
        public final void run() {
            this.mRecyclerView.smoothScrollToPosition(this.mPosition);
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.viewpager2.widget.ViewPager2$1] */
    public ViewPager2(Context context) {
        super(context);
        this.mTmpContainerRect = new Rect();
        this.mTmpChildRect = new Rect();
        this.mExternalPageChangeCallbacks = new CompositeOnPageChangeCallback(3);
        this.mCurrentItemDirty = false;
        this.mCurrentItemDataSetChangeObserver = new DataSetChangeObserver() { // from class: androidx.viewpager2.widget.ViewPager2.1
            @Override // androidx.viewpager2.widget.ViewPager2.DataSetChangeObserver, androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public final void onChanged() {
                ViewPager2 viewPager2 = ViewPager2.this;
                viewPager2.mCurrentItemDirty = true;
                viewPager2.mScrollEventAdapter.mDataSetChangeHappened = true;
            }
        };
        this.mPendingCurrentItem = -1;
        this.mUserInputEnabled = true;
        this.mOffscreenPageLimit = -1;
        initialize(context, null);
    }

    @Override // android.view.View
    public final boolean canScrollHorizontally(int i) {
        return this.mRecyclerView.canScrollHorizontally(i);
    }

    @Override // android.view.View
    public final boolean canScrollVertically(int i) {
        return this.mRecyclerView.canScrollVertically(i);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchRestoreInstanceState(SparseArray sparseArray) {
        Parcelable parcelable = (Parcelable) sparseArray.get(getId());
        if (parcelable instanceof SavedState) {
            int i = ((SavedState) parcelable).mRecyclerViewId;
            sparseArray.put(this.mRecyclerView.getId(), (Parcelable) sparseArray.get(i));
            sparseArray.remove(i);
        }
        super.dispatchRestoreInstanceState(sparseArray);
        restorePendingState();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final CharSequence getAccessibilityClassName() {
        this.mAccessibilityProvider.getClass();
        this.mAccessibilityProvider.getClass();
        return "androidx.viewpager.widget.ViewPager";
    }

    public final void initialize(Context context, AttributeSet attributeSet) {
        this.mAccessibilityProvider = new PageAwareAccessibilityProvider();
        RecyclerViewImpl recyclerViewImpl = new RecyclerViewImpl(context);
        this.mRecyclerView = recyclerViewImpl;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        recyclerViewImpl.setId(ViewCompat.Api17Impl.generateViewId());
        this.mRecyclerView.setDescendantFocusability(131072);
        LinearLayoutManagerImpl linearLayoutManagerImpl = new LinearLayoutManagerImpl(context);
        this.mLayoutManager = linearLayoutManagerImpl;
        this.mRecyclerView.setLayoutManager(linearLayoutManagerImpl);
        RecyclerViewImpl recyclerViewImpl2 = this.mRecyclerView;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(recyclerViewImpl2.getContext());
        Log.d("SeslRecyclerView", "setScrollingTouchSlop(): slopConstant[1]");
        recyclerViewImpl2.mUsePagingTouchSlopForStylus = false;
        recyclerViewImpl2.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        int[] iArr = R$styleable.ViewPager2;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        saveAttributeDataForStyleable(context, iArr, attributeSet, obtainStyledAttributes, 0, 0);
        try {
            this.mLayoutManager.setOrientation(obtainStyledAttributes.getInt(0, 0));
            this.mAccessibilityProvider.updatePageAccessibilityActions();
            obtainStyledAttributes.recycle();
            this.mRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            RecyclerViewImpl recyclerViewImpl3 = this.mRecyclerView;
            RecyclerView.OnChildAttachStateChangeListener onChildAttachStateChangeListener = new RecyclerView.OnChildAttachStateChangeListener(this) { // from class: androidx.viewpager2.widget.ViewPager2.4
                @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
                public final void onChildViewAttachedToWindow(View view) {
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                    if (((ViewGroup.MarginLayoutParams) layoutParams).width != -1 || ((ViewGroup.MarginLayoutParams) layoutParams).height != -1) {
                        throw new IllegalStateException("Pages must fill the whole ViewPager2 (use match_parent)");
                    }
                }

                @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
                public final void onChildViewDetachedFromWindow(View view) {
                }
            };
            if (recyclerViewImpl3.mOnChildAttachStateListeners == null) {
                recyclerViewImpl3.mOnChildAttachStateListeners = new ArrayList();
            }
            ((ArrayList) recyclerViewImpl3.mOnChildAttachStateListeners).add(onChildAttachStateChangeListener);
            ScrollEventAdapter scrollEventAdapter = new ScrollEventAdapter(this);
            this.mScrollEventAdapter = scrollEventAdapter;
            this.mFakeDragger = new FakeDrag(this, scrollEventAdapter, this.mRecyclerView);
            PagerSnapHelperImpl pagerSnapHelperImpl = new PagerSnapHelperImpl();
            this.mPagerSnapHelper = pagerSnapHelperImpl;
            pagerSnapHelperImpl.attachToRecyclerView(this.mRecyclerView);
            this.mRecyclerView.addOnScrollListener(this.mScrollEventAdapter);
            CompositeOnPageChangeCallback compositeOnPageChangeCallback = new CompositeOnPageChangeCallback(3);
            this.mPageChangeEventDispatcher = compositeOnPageChangeCallback;
            this.mScrollEventAdapter.mCallback = compositeOnPageChangeCallback;
            OnPageChangeCallback onPageChangeCallback = new OnPageChangeCallback() { // from class: androidx.viewpager2.widget.ViewPager2.2
                @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                public final void onPageScrollStateChanged(int i) {
                    if (i == 0) {
                        ViewPager2.this.updateCurrentItem();
                    }
                }

                @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                public final void onPageSelected(int i) {
                    ViewPager2 viewPager2 = ViewPager2.this;
                    if (viewPager2.mCurrentItem != i) {
                        viewPager2.mCurrentItem = i;
                        viewPager2.mAccessibilityProvider.updatePageAccessibilityActions();
                    }
                }
            };
            OnPageChangeCallback onPageChangeCallback2 = new OnPageChangeCallback() { // from class: androidx.viewpager2.widget.ViewPager2.3
                @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                public final void onPageSelected(int i) {
                    ViewPager2 viewPager2 = ViewPager2.this;
                    viewPager2.clearFocus();
                    if (viewPager2.hasFocus()) {
                        viewPager2.mRecyclerView.requestFocus(2);
                    }
                }
            };
            ((ArrayList) this.mPageChangeEventDispatcher.mCallbacks).add(onPageChangeCallback);
            ((ArrayList) this.mPageChangeEventDispatcher.mCallbacks).add(onPageChangeCallback2);
            this.mAccessibilityProvider.onInitialize(this.mRecyclerView);
            CompositeOnPageChangeCallback compositeOnPageChangeCallback2 = this.mPageChangeEventDispatcher;
            ((ArrayList) compositeOnPageChangeCallback2.mCallbacks).add(this.mExternalPageChangeCallbacks);
            ((ArrayList) this.mPageChangeEventDispatcher.mCallbacks).add(new PageTransformerAdapter(this.mLayoutManager));
            RecyclerViewImpl recyclerViewImpl4 = this.mRecyclerView;
            attachViewToParent(recyclerViewImpl4, 0, recyclerViewImpl4.getLayoutParams());
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        int i;
        int i2;
        int itemCount;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        ViewPager2 viewPager2 = ViewPager2.this;
        RecyclerView.Adapter adapter = viewPager2.mRecyclerView.mAdapter;
        if (adapter == null) {
            i = 0;
            i2 = 0;
        } else if (viewPager2.mLayoutManager.mOrientation == 1) {
            i = adapter.getItemCount();
            i2 = 0;
        } else {
            i2 = adapter.getItemCount();
            i = 0;
        }
        AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(i, i2, 0));
        RecyclerView.Adapter adapter2 = viewPager2.mRecyclerView.mAdapter;
        if (adapter2 == null || (itemCount = adapter2.getItemCount()) == 0 || !viewPager2.mUserInputEnabled) {
            return;
        }
        if (viewPager2.mCurrentItem > 0) {
            accessibilityNodeInfo.addAction(8192);
        }
        if (viewPager2.mCurrentItem < itemCount - 1) {
            accessibilityNodeInfo.addAction(4096);
        }
        accessibilityNodeInfo.setScrollable(true);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth = this.mRecyclerView.getMeasuredWidth();
        int measuredHeight = this.mRecyclerView.getMeasuredHeight();
        this.mTmpContainerRect.left = getPaddingLeft();
        this.mTmpContainerRect.right = (i3 - i) - getPaddingRight();
        this.mTmpContainerRect.top = getPaddingTop();
        this.mTmpContainerRect.bottom = (i4 - i2) - getPaddingBottom();
        Gravity.apply(8388659, measuredWidth, measuredHeight, this.mTmpContainerRect, this.mTmpChildRect);
        RecyclerViewImpl recyclerViewImpl = this.mRecyclerView;
        Rect rect = this.mTmpChildRect;
        recyclerViewImpl.layout(rect.left, rect.top, rect.right, rect.bottom);
        if (this.mCurrentItemDirty) {
            updateCurrentItem();
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        measureChild(this.mRecyclerView, i, i2);
        int measuredWidth = this.mRecyclerView.getMeasuredWidth();
        int measuredHeight = this.mRecyclerView.getMeasuredHeight();
        int measuredState = this.mRecyclerView.getMeasuredState();
        int paddingRight = getPaddingRight() + getPaddingLeft() + measuredWidth;
        int paddingBottom = getPaddingBottom() + getPaddingTop() + measuredHeight;
        setMeasuredDimension(ViewGroup.resolveSizeAndState(Math.max(paddingRight, getSuggestedMinimumWidth()), i, measuredState), ViewGroup.resolveSizeAndState(Math.max(paddingBottom, getSuggestedMinimumHeight()), i2, measuredState << 16));
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mPendingCurrentItem = savedState.mCurrentItem;
        this.mPendingAdapterState = savedState.mAdapterState;
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mRecyclerViewId = this.mRecyclerView.getId();
        int i = this.mPendingCurrentItem;
        if (i == -1) {
            i = this.mCurrentItem;
        }
        savedState.mCurrentItem = i;
        Parcelable parcelable = this.mPendingAdapterState;
        if (parcelable != null) {
            savedState.mAdapterState = parcelable;
        } else {
            Object obj = this.mRecyclerView.mAdapter;
            if (obj instanceof StatefulAdapter) {
                FragmentStateAdapter fragmentStateAdapter = (FragmentStateAdapter) ((StatefulAdapter) obj);
                fragmentStateAdapter.getClass();
                LongSparseArray longSparseArray = fragmentStateAdapter.mFragments;
                int size = longSparseArray.size();
                LongSparseArray longSparseArray2 = fragmentStateAdapter.mSavedStates;
                Bundle bundle = new Bundle(longSparseArray2.size() + size);
                for (int i2 = 0; i2 < longSparseArray.size(); i2++) {
                    long keyAt = longSparseArray.keyAt(i2);
                    Fragment fragment = (Fragment) longSparseArray.get(keyAt);
                    if (fragment != null && fragment.isAdded()) {
                        String m25m = ValueAnimator$$ExternalSyntheticOutline0.m25m("f#", keyAt);
                        FragmentManager fragmentManager = fragmentStateAdapter.mFragmentManager;
                        fragmentManager.getClass();
                        if (fragment.mFragmentManager != fragmentManager) {
                            fragmentManager.throwException(new IllegalStateException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", fragment, " is not currently in the FragmentManager")));
                            throw null;
                        }
                        bundle.putString(m25m, fragment.mWho);
                    }
                }
                for (int i3 = 0; i3 < longSparseArray2.size(); i3++) {
                    long keyAt2 = longSparseArray2.keyAt(i3);
                    if (fragmentStateAdapter.containsItem(keyAt2)) {
                        bundle.putParcelable(ValueAnimator$$ExternalSyntheticOutline0.m25m("s#", keyAt2), (Parcelable) longSparseArray2.get(keyAt2));
                    }
                }
                savedState.mAdapterState = bundle;
            }
        }
        return savedState;
    }

    @Override // android.view.ViewGroup
    public final void onViewAdded(View view) {
        throw new IllegalStateException("ViewPager2 does not support direct child views");
    }

    @Override // android.view.View
    public final boolean performAccessibilityAction(int i, Bundle bundle) {
        this.mAccessibilityProvider.getClass();
        if (!(i == 8192 || i == 4096)) {
            return super.performAccessibilityAction(i, bundle);
        }
        PageAwareAccessibilityProvider pageAwareAccessibilityProvider = this.mAccessibilityProvider;
        pageAwareAccessibilityProvider.getClass();
        if (!(i == 8192 || i == 4096)) {
            throw new IllegalStateException();
        }
        ViewPager2 viewPager2 = ViewPager2.this;
        int i2 = i == 8192 ? viewPager2.mCurrentItem - 1 : viewPager2.mCurrentItem + 1;
        if (viewPager2.mUserInputEnabled) {
            viewPager2.setCurrentItemInternal(i2);
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void restorePendingState() {
        RecyclerView.Adapter adapter;
        if (this.mPendingCurrentItem == -1 || (adapter = this.mRecyclerView.mAdapter) == 0) {
            return;
        }
        Parcelable parcelable = this.mPendingAdapterState;
        if (parcelable != null) {
            if (adapter instanceof StatefulAdapter) {
                ((FragmentStateAdapter) ((StatefulAdapter) adapter)).restoreState(parcelable);
            }
            this.mPendingAdapterState = null;
        }
        int max = Math.max(0, Math.min(this.mPendingCurrentItem, adapter.getItemCount() - 1));
        this.mCurrentItem = max;
        this.mPendingCurrentItem = -1;
        this.mRecyclerView.scrollToPosition(max);
        this.mAccessibilityProvider.updatePageAccessibilityActions();
    }

    public final void setAdapter(StructureAdapter structureAdapter) {
        RecyclerView.Adapter adapter = this.mRecyclerView.mAdapter;
        PageAwareAccessibilityProvider pageAwareAccessibilityProvider = this.mAccessibilityProvider;
        if (adapter != null) {
            adapter.mObservable.unregisterObserver(pageAwareAccessibilityProvider.mAdapterDataObserver);
        } else {
            pageAwareAccessibilityProvider.getClass();
        }
        if (adapter != null) {
            adapter.mObservable.unregisterObserver(this.mCurrentItemDataSetChangeObserver);
        }
        this.mRecyclerView.setAdapter(structureAdapter);
        this.mCurrentItem = 0;
        restorePendingState();
        PageAwareAccessibilityProvider pageAwareAccessibilityProvider2 = this.mAccessibilityProvider;
        pageAwareAccessibilityProvider2.updatePageAccessibilityActions();
        structureAdapter.registerAdapterDataObserver(pageAwareAccessibilityProvider2.mAdapterDataObserver);
        structureAdapter.registerAdapterDataObserver(this.mCurrentItemDataSetChangeObserver);
    }

    public final void setCurrentItemInternal(int i) {
        OnPageChangeCallback onPageChangeCallback;
        RecyclerView.Adapter adapter = this.mRecyclerView.mAdapter;
        if (adapter == null) {
            if (this.mPendingCurrentItem != -1) {
                this.mPendingCurrentItem = Math.max(i, 0);
                return;
            }
            return;
        }
        if (adapter.getItemCount() <= 0) {
            return;
        }
        int min = Math.min(Math.max(i, 0), adapter.getItemCount() - 1);
        int i2 = this.mCurrentItem;
        if (min == i2) {
            if (this.mScrollEventAdapter.mScrollState == 0) {
                return;
            }
        }
        if (min == i2) {
            return;
        }
        double d = i2;
        this.mCurrentItem = min;
        this.mAccessibilityProvider.updatePageAccessibilityActions();
        ScrollEventAdapter scrollEventAdapter = this.mScrollEventAdapter;
        if (!(scrollEventAdapter.mScrollState == 0)) {
            scrollEventAdapter.updateScrollEventValues();
            ScrollEventAdapter.ScrollEventValues scrollEventValues = scrollEventAdapter.mScrollValues;
            d = scrollEventValues.mPosition + scrollEventValues.mOffset;
        }
        ScrollEventAdapter scrollEventAdapter2 = this.mScrollEventAdapter;
        scrollEventAdapter2.getClass();
        scrollEventAdapter2.mAdapterState = 2;
        scrollEventAdapter2.mFakeDragging = false;
        boolean z = scrollEventAdapter2.mTarget != min;
        scrollEventAdapter2.mTarget = min;
        scrollEventAdapter2.dispatchStateChanged(2);
        if (z && (onPageChangeCallback = scrollEventAdapter2.mCallback) != null) {
            onPageChangeCallback.onPageSelected(min);
        }
        double d2 = min;
        if (Math.abs(d2 - d) <= 3.0d) {
            this.mRecyclerView.smoothScrollToPosition(min);
            return;
        }
        this.mRecyclerView.scrollToPosition(d2 > d ? min - 3 : min + 3);
        RecyclerViewImpl recyclerViewImpl = this.mRecyclerView;
        recyclerViewImpl.post(new SmoothScrollToPosition(min, recyclerViewImpl));
    }

    @Override // android.view.View
    public final void setLayoutDirection(int i) {
        super.setLayoutDirection(i);
        this.mAccessibilityProvider.updatePageAccessibilityActions();
    }

    public final void updateCurrentItem() {
        PagerSnapHelperImpl pagerSnapHelperImpl = this.mPagerSnapHelper;
        if (pagerSnapHelperImpl == null) {
            throw new IllegalStateException("Design assumption violated.");
        }
        View findSnapView = pagerSnapHelperImpl.findSnapView(this.mLayoutManager);
        if (findSnapView == null) {
            return;
        }
        this.mLayoutManager.getClass();
        int position = RecyclerView.LayoutManager.getPosition(findSnapView);
        if (position != this.mCurrentItem && this.mScrollEventAdapter.mScrollState == 0) {
            this.mPageChangeEventDispatcher.onPageSelected(position);
        }
        this.mCurrentItemDirty = false;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: androidx.viewpager2.widget.ViewPager2.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.ClassLoaderCreator
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }
        };
        public Parcelable mAdapterState;
        public int mCurrentItem;
        public int mRecyclerViewId;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.mRecyclerViewId = parcel.readInt();
            this.mCurrentItem = parcel.readInt();
            this.mAdapterState = parcel.readParcelable(classLoader);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mRecyclerViewId);
            parcel.writeInt(this.mCurrentItem);
            parcel.writeParcelable(this.mAdapterState, i);
        }

        public SavedState(Parcel parcel) {
            super(parcel);
            this.mRecyclerViewId = parcel.readInt();
            this.mCurrentItem = parcel.readInt();
            this.mAdapterState = parcel.readParcelable(null);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.viewpager2.widget.ViewPager2$1] */
    public ViewPager2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTmpContainerRect = new Rect();
        this.mTmpChildRect = new Rect();
        this.mExternalPageChangeCallbacks = new CompositeOnPageChangeCallback(3);
        this.mCurrentItemDirty = false;
        this.mCurrentItemDataSetChangeObserver = new DataSetChangeObserver() { // from class: androidx.viewpager2.widget.ViewPager2.1
            @Override // androidx.viewpager2.widget.ViewPager2.DataSetChangeObserver, androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public final void onChanged() {
                ViewPager2 viewPager2 = ViewPager2.this;
                viewPager2.mCurrentItemDirty = true;
                viewPager2.mScrollEventAdapter.mDataSetChangeHappened = true;
            }
        };
        this.mPendingCurrentItem = -1;
        this.mUserInputEnabled = true;
        this.mOffscreenPageLimit = -1;
        initialize(context, attributeSet);
    }

    /* JADX WARN: Type inference failed for: r4v5, types: [androidx.viewpager2.widget.ViewPager2$1] */
    public ViewPager2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTmpContainerRect = new Rect();
        this.mTmpChildRect = new Rect();
        this.mExternalPageChangeCallbacks = new CompositeOnPageChangeCallback(3);
        this.mCurrentItemDirty = false;
        this.mCurrentItemDataSetChangeObserver = new DataSetChangeObserver() { // from class: androidx.viewpager2.widget.ViewPager2.1
            @Override // androidx.viewpager2.widget.ViewPager2.DataSetChangeObserver, androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public final void onChanged() {
                ViewPager2 viewPager2 = ViewPager2.this;
                viewPager2.mCurrentItemDirty = true;
                viewPager2.mScrollEventAdapter.mDataSetChangeHappened = true;
            }
        };
        this.mPendingCurrentItem = -1;
        this.mUserInputEnabled = true;
        this.mOffscreenPageLimit = -1;
        initialize(context, attributeSet);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class OnPageChangeCallback {
        public void onPageScrollStateChanged(int i) {
        }

        public void onPageSelected(int i) {
        }

        public void onPageScrolled(float f, int i, int i2) {
        }
    }

    /* JADX WARN: Type inference failed for: r3v5, types: [androidx.viewpager2.widget.ViewPager2$1] */
    public ViewPager2(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTmpContainerRect = new Rect();
        this.mTmpChildRect = new Rect();
        this.mExternalPageChangeCallbacks = new CompositeOnPageChangeCallback(3);
        this.mCurrentItemDirty = false;
        this.mCurrentItemDataSetChangeObserver = new DataSetChangeObserver() { // from class: androidx.viewpager2.widget.ViewPager2.1
            @Override // androidx.viewpager2.widget.ViewPager2.DataSetChangeObserver, androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public final void onChanged() {
                ViewPager2 viewPager2 = ViewPager2.this;
                viewPager2.mCurrentItemDirty = true;
                viewPager2.mScrollEventAdapter.mDataSetChangeHappened = true;
            }
        };
        this.mPendingCurrentItem = -1;
        this.mUserInputEnabled = true;
        this.mOffscreenPageLimit = -1;
        initialize(context, attributeSet);
    }
}
