package androidx.viewpager2.widget;

import android.R;
import android.animation.ValueAnimator;
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
import android.view.animation.PathInterpolator;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.R$styleable;
import androidx.viewpager2.widget.ScrollEventAdapter;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ViewPager2 extends ViewGroup {
    public static final PathInterpolator CONTAINER_SCALE_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
    public PageAwareAccessibilityProvider mAccessibilityProvider;
    public float mContainerScaleValue;
    public int mCurrentItem;
    public boolean mCurrentItemDirty;
    public final CompositeOnPageChangeCallback mExternalPageChangeCallbacks;
    public FakeDrag mFakeDragger;
    public OrientationHelper.AnonymousClass1 mHorizontalHelper;
    public boolean mIsSuggestionPagingEnabled;
    public LinearLayoutManagerImpl mLayoutManager;
    public final int mOffscreenPageLimit;
    public CompositeOnPageChangeCallback mPageChangeEventDispatcher;
    public PagerSnapHelperImpl mPagerSnapHelper;
    public Parcelable mPendingAdapterState;
    public int mPendingCurrentItem;
    public RecyclerViewImpl mRecyclerView;
    public ScrollEventAdapter mScrollEventAdapter;
    public int mScrollState;
    public ValueAnimator mSuggestionReleaseAnimator;
    public ValueAnimator mSuggestionStartDragAnimator;
    public final Rect mTmpChildRect;
    public final Rect mTmpContainerRect;
    public final boolean mUserInputEnabled;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class AccessibilityProvider {
        private AccessibilityProvider(ViewPager2 viewPager2) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BasicAccessibilityProvider extends AccessibilityProvider {
        public BasicAccessibilityProvider(ViewPager2 viewPager2) {
            super();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LinearLayoutManagerImpl extends LinearLayoutManager {
        public LinearLayoutManagerImpl(Context context) {
            super(context);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        public final void calculateExtraLayoutSpace(RecyclerView.State state, int[] iArr) {
            int width;
            int paddingRight;
            ViewPager2 viewPager2 = ViewPager2.this;
            int i = viewPager2.mOffscreenPageLimit;
            if (i == -1) {
                super.calculateExtraLayoutSpace(state, iArr);
                return;
            }
            RecyclerViewImpl recyclerViewImpl = viewPager2.mRecyclerView;
            if (viewPager2.mLayoutManager.mOrientation == 1) {
                width = recyclerViewImpl.getHeight() - recyclerViewImpl.getPaddingTop();
                paddingRight = recyclerViewImpl.getPaddingBottom();
            } else {
                width = recyclerViewImpl.getWidth() - recyclerViewImpl.getPaddingLeft();
                paddingRight = recyclerViewImpl.getPaddingRight();
            }
            int i2 = (width - paddingRight) * i;
            iArr[0] = i2;
            iArr[1] = i2;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public final void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(recycler, state, accessibilityNodeInfoCompat);
            ViewPager2.this.mAccessibilityProvider.getClass();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public final void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int i;
            int position;
            ViewPager2 viewPager2 = ViewPager2.this;
            LinearLayoutManagerImpl linearLayoutManagerImpl = viewPager2.mLayoutManager;
            if (linearLayoutManagerImpl.mOrientation == 1) {
                linearLayoutManagerImpl.getClass();
                i = RecyclerView.LayoutManager.getPosition(view);
            } else {
                i = 0;
            }
            LinearLayoutManagerImpl linearLayoutManagerImpl2 = viewPager2.mLayoutManager;
            if (linearLayoutManagerImpl2.mOrientation == 1) {
                position = 0;
            } else {
                linearLayoutManagerImpl2.getClass();
                position = RecyclerView.LayoutManager.getPosition(view);
            }
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, i, 1, position, 1));
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

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public final int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
            ViewPager2 viewPager2 = ViewPager2.this;
            if (viewPager2.mIsSuggestionPagingEnabled) {
                ViewPager2.access$700(viewPager2);
            }
            return super.scrollHorizontallyBy(i, recycler, state);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PageAwareAccessibilityProvider extends AccessibilityProvider {
        public final AnonymousClass2 mActionPageBackward;
        public final AnonymousClass1 mActionPageForward;

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

        public final void updatePageAccessibilityActions() {
            int itemCount;
            int i = R.id.accessibilityActionPageLeft;
            ViewPager2 viewPager2 = ViewPager2.this;
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
            boolean z = linearLayoutManagerImpl.mOrientation == 1;
            AnonymousClass2 anonymousClass2 = this.mActionPageBackward;
            AnonymousClass1 anonymousClass1 = this.mActionPageForward;
            if (z) {
                if (viewPager2.mCurrentItem < itemCount - 1) {
                    ViewCompat.replaceAccessibilityAction(viewPager2, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(R.id.accessibilityActionPageDown, null), null, anonymousClass1);
                }
                if (viewPager2.mCurrentItem > 0) {
                    ViewCompat.replaceAccessibilityAction(viewPager2, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(R.id.accessibilityActionPageUp, null), null, anonymousClass2);
                    return;
                }
                return;
            }
            boolean z2 = linearLayoutManagerImpl.getLayoutDirection() == 1;
            int i2 = z2 ? 16908360 : 16908361;
            if (z2) {
                i = 16908361;
            }
            if (viewPager2.mCurrentItem < itemCount - 1) {
                ViewCompat.replaceAccessibilityAction(viewPager2, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i2, null), null, anonymousClass1);
            }
            if (viewPager2.mCurrentItem > 0) {
                ViewCompat.replaceAccessibilityAction(viewPager2, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i, null), null, anonymousClass2);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PagerSnapHelperImpl extends PagerSnapHelper {
        public PagerSnapHelperImpl() {
        }

        @Override // androidx.recyclerview.widget.PagerSnapHelper, androidx.recyclerview.widget.SnapHelper
        public final View findSnapView(RecyclerView.LayoutManager layoutManager) {
            ViewPager2.this.mFakeDragger.mScrollEventAdapter.getClass();
            return super.findSnapView(layoutManager);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RecyclerViewImpl extends RecyclerView {
        public RecyclerViewImpl(Context context) {
            super(context);
        }

        @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        public final CharSequence getAccessibilityClassName() {
            ViewPager2.this.mAccessibilityProvider.getClass();
            return "androidx.recyclerview.widget.RecyclerView";
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
            int actionMasked;
            if ((ViewPager2.this.mIsSuggestionPagingEnabled && ValueAnimator.areAnimatorsEnabled()) && ((actionMasked = motionEvent.getActionMasked()) == 1 || actionMasked == 3)) {
                ViewPager2 viewPager2 = ViewPager2.this;
                if (viewPager2.mScrollState == 1) {
                    viewPager2.mSuggestionReleaseAnimator.setFloatValues(0.95f, 1.0f);
                    if (ViewPager2.this.mSuggestionStartDragAnimator.isRunning()) {
                        ViewPager2 viewPager22 = ViewPager2.this;
                        viewPager22.mSuggestionReleaseAnimator.setFloatValues(viewPager22.mContainerScaleValue, 1.0f);
                        ViewPager2.this.mSuggestionStartDragAnimator.cancel();
                    }
                    if (ViewPager2.this.mSuggestionReleaseAnimator.isRunning()) {
                        ViewPager2.this.mSuggestionReleaseAnimator.cancel();
                    }
                    ViewPager2.this.mSuggestionReleaseAnimator.start();
                }
            }
            return ViewPager2.this.mUserInputEnabled && super.onTouchEvent(motionEvent);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SmoothScrollToPosition implements Runnable {
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

    public ViewPager2(Context context) {
        super(context);
        this.mTmpContainerRect = new Rect();
        this.mTmpChildRect = new Rect();
        this.mExternalPageChangeCallbacks = new CompositeOnPageChangeCallback(3);
        this.mCurrentItemDirty = false;
        new DataSetChangeObserver() { // from class: androidx.viewpager2.widget.ViewPager2.1
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
        this.mContainerScaleValue = 1.0f;
        this.mIsSuggestionPagingEnabled = false;
        this.mScrollState = 0;
        initialize(context, null);
    }

    public static void access$700(ViewPager2 viewPager2) {
        View findSnapView;
        PagerSnapHelperImpl pagerSnapHelperImpl = viewPager2.mPagerSnapHelper;
        if (pagerSnapHelperImpl == null || (findSnapView = pagerSnapHelperImpl.findSnapView(viewPager2.mLayoutManager)) == null) {
            return;
        }
        int indexOfChild = viewPager2.mRecyclerView.indexOfChild(findSnapView);
        LinearLayoutManagerImpl linearLayoutManagerImpl = viewPager2.mLayoutManager;
        OrientationHelper.AnonymousClass1 anonymousClass1 = viewPager2.mHorizontalHelper;
        if (anonymousClass1 == null || anonymousClass1.mLayoutManager != linearLayoutManagerImpl) {
            viewPager2.mHorizontalHelper = new OrientationHelper.AnonymousClass1(linearLayoutManagerImpl);
        }
        OrientationHelper.AnonymousClass1 anonymousClass12 = viewPager2.mHorizontalHelper;
        viewPager2.mHorizontalHelper = anonymousClass12;
        int decoratedStart = anonymousClass12.getDecoratedStart(findSnapView);
        View childAt = viewPager2.mRecyclerView.getChildAt(decoratedStart < 0 ? indexOfChild + 1 : indexOfChild - 1);
        int i = decoratedStart < 0 ? decoratedStart * (-1) : decoratedStart;
        float width = ((((findSnapView.getWidth() - i) / findSnapView.getWidth()) * 0.1f) + 0.9f) * viewPager2.mContainerScaleValue;
        float f = i;
        float width2 = (((f / findSnapView.getWidth()) * 0.1f) + 0.9f) * viewPager2.mContainerScaleValue;
        float f2 = decoratedStart > 0 ? -4 : 4;
        float width3 = ((findSnapView.getWidth() - i) / findSnapView.getWidth()) * f2;
        findSnapView.setScaleX(width);
        findSnapView.setScaleY(width);
        findSnapView.setRotationY((f / findSnapView.getWidth()) * f2);
        if (childAt != null) {
            childAt.setScaleX(width2);
            childAt.setScaleY(width2);
            childAt.setRotationY(-width3);
        }
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
        RecyclerView.Adapter adapter;
        Parcelable parcelable = (Parcelable) sparseArray.get(getId());
        if (parcelable instanceof SavedState) {
            int i = ((SavedState) parcelable).mRecyclerViewId;
            sparseArray.put(this.mRecyclerView.getId(), (Parcelable) sparseArray.get(i));
            sparseArray.remove(i);
        }
        super.dispatchRestoreInstanceState(sparseArray);
        int i2 = this.mPendingCurrentItem;
        if (i2 == -1 || (adapter = this.mRecyclerView.mAdapter) == null) {
            return;
        }
        if (this.mPendingAdapterState != null) {
            this.mPendingAdapterState = null;
        }
        int max = Math.max(0, Math.min(i2, adapter.getItemCount() - 1));
        this.mCurrentItem = max;
        this.mPendingCurrentItem = -1;
        this.mRecyclerView.scrollToPosition(max);
        this.mAccessibilityProvider.updatePageAccessibilityActions();
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
        recyclerViewImpl.setId(View.generateViewId());
        this.mRecyclerView.setDescendantFocusability(131072);
        LinearLayoutManagerImpl linearLayoutManagerImpl = new LinearLayoutManagerImpl(context);
        this.mLayoutManager = linearLayoutManagerImpl;
        this.mRecyclerView.setLayoutManager(linearLayoutManagerImpl);
        RecyclerViewImpl recyclerViewImpl2 = this.mRecyclerView;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(recyclerViewImpl2.getContext());
        Log.d("SeslRecyclerView", "setScrollingTouchSlop(): slopConstant[1]");
        recyclerViewImpl2.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        int[] iArr = R$styleable.ViewPager2;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, 0, 0);
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
            this.mRecyclerView.setOverScrollMode(getOverScrollMode());
            CompositeOnPageChangeCallback compositeOnPageChangeCallback = new CompositeOnPageChangeCallback(3);
            this.mPageChangeEventDispatcher = compositeOnPageChangeCallback;
            this.mScrollEventAdapter.mCallback = compositeOnPageChangeCallback;
            OnPageChangeCallback onPageChangeCallback = new OnPageChangeCallback() { // from class: androidx.viewpager2.widget.ViewPager2.2
                @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                public final void onPageScrollStateChanged(int i) {
                    ViewPager2 viewPager2 = ViewPager2.this;
                    if (i == 0) {
                        viewPager2.updateCurrentItem();
                    }
                    if (viewPager2.mScrollState != i) {
                        viewPager2.mScrollState = i;
                    }
                    if (viewPager2.mIsSuggestionPagingEnabled && ValueAnimator.areAnimatorsEnabled() && i == 1) {
                        viewPager2.getParent().requestDisallowInterceptTouchEvent(true);
                        if (viewPager2.mSuggestionStartDragAnimator.isRunning()) {
                            viewPager2.mSuggestionStartDragAnimator.cancel();
                        }
                        viewPager2.mSuggestionStartDragAnimator.setFloatValues(1.0f, 0.95f);
                        if (viewPager2.mSuggestionReleaseAnimator.isRunning()) {
                            viewPager2.mSuggestionStartDragAnimator.setFloatValues(viewPager2.mContainerScaleValue, 0.95f);
                            viewPager2.mSuggestionReleaseAnimator.cancel();
                        }
                        viewPager2.mSuggestionStartDragAnimator.start();
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
            final PageAwareAccessibilityProvider pageAwareAccessibilityProvider = this.mAccessibilityProvider;
            RecyclerViewImpl recyclerViewImpl4 = this.mRecyclerView;
            pageAwareAccessibilityProvider.getClass();
            recyclerViewImpl4.setImportantForAccessibility(2);
            new DataSetChangeObserver() { // from class: androidx.viewpager2.widget.ViewPager2.PageAwareAccessibilityProvider.3
                @Override // androidx.viewpager2.widget.ViewPager2.DataSetChangeObserver, androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public final void onChanged() {
                    PageAwareAccessibilityProvider.this.updatePageAccessibilityActions();
                }
            };
            ViewPager2 viewPager2 = ViewPager2.this;
            if (viewPager2.getImportantForAccessibility() == 0) {
                viewPager2.setImportantForAccessibility(1);
            }
            CompositeOnPageChangeCallback compositeOnPageChangeCallback2 = this.mPageChangeEventDispatcher;
            ((ArrayList) compositeOnPageChangeCallback2.mCallbacks).add(this.mExternalPageChangeCallbacks);
            ((ArrayList) this.mPageChangeEventDispatcher.mCallbacks).add(new PageTransformerAdapter(this.mLayoutManager));
            RecyclerViewImpl recyclerViewImpl5 = this.mRecyclerView;
            attachViewToParent(recyclerViewImpl5, 0, recyclerViewImpl5.getLayoutParams());
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
        PageAwareAccessibilityProvider pageAwareAccessibilityProvider = this.mAccessibilityProvider;
        pageAwareAccessibilityProvider.getClass();
        AccessibilityNodeInfoCompat wrap = AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo);
        ViewPager2 viewPager2 = ViewPager2.this;
        RecyclerView.Adapter adapter = viewPager2.mRecyclerView.mAdapter;
        if (adapter != null) {
            if (viewPager2.mLayoutManager.mOrientation == 1) {
                i = adapter.getItemCount();
                i2 = 1;
            } else {
                i2 = adapter.getItemCount();
                i = 1;
            }
        } else {
            i = 0;
            i2 = 0;
        }
        wrap.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(i, i2, 0));
        RecyclerView.Adapter adapter2 = viewPager2.mRecyclerView.mAdapter;
        if (adapter2 == null || (itemCount = adapter2.getItemCount()) == 0 || !viewPager2.mUserInputEnabled) {
            return;
        }
        if (viewPager2.mCurrentItem > 0) {
            wrap.addAction(8192);
        }
        if (viewPager2.mCurrentItem < itemCount - 1) {
            wrap.addAction(4096);
        }
        wrap.setScrollable(true);
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
            return savedState;
        }
        RecyclerView.Adapter adapter = this.mRecyclerView.mAdapter;
        return savedState;
    }

    @Override // android.view.ViewGroup
    public final void onViewAdded(View view) {
        throw new IllegalStateException("ViewPager2 does not support direct child views");
    }

    @Override // android.view.View
    public final boolean performAccessibilityAction(int i, Bundle bundle) {
        this.mAccessibilityProvider.getClass();
        if (i != 8192 && i != 4096) {
            return super.performAccessibilityAction(i, bundle);
        }
        PageAwareAccessibilityProvider pageAwareAccessibilityProvider = this.mAccessibilityProvider;
        pageAwareAccessibilityProvider.getClass();
        if (i != 8192 && i != 4096) {
            throw new IllegalStateException();
        }
        ViewPager2 viewPager2 = ViewPager2.this;
        int i2 = i == 8192 ? viewPager2.mCurrentItem - 1 : viewPager2.mCurrentItem + 1;
        if (viewPager2.mUserInputEnabled) {
            viewPager2.setCurrentItemInternal(i2);
        }
        return true;
    }

    public final void setCurrentItemInternal(int i) {
        CompositeOnPageChangeCallback compositeOnPageChangeCallback;
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
        if ((min == i2 && this.mScrollEventAdapter.mScrollState == 0) || min == i2) {
            return;
        }
        double d = i2;
        this.mCurrentItem = min;
        this.mAccessibilityProvider.updatePageAccessibilityActions();
        ScrollEventAdapter scrollEventAdapter = this.mScrollEventAdapter;
        if (scrollEventAdapter.mScrollState != 0) {
            scrollEventAdapter.updateScrollEventValues();
            ScrollEventAdapter.ScrollEventValues scrollEventValues = scrollEventAdapter.mScrollValues;
            d = scrollEventValues.mPosition + scrollEventValues.mOffset;
        }
        ScrollEventAdapter scrollEventAdapter2 = this.mScrollEventAdapter;
        scrollEventAdapter2.getClass();
        scrollEventAdapter2.mAdapterState = 2;
        boolean z = scrollEventAdapter2.mTarget != min;
        scrollEventAdapter2.mTarget = min;
        scrollEventAdapter2.dispatchStateChanged(2);
        if (z && (compositeOnPageChangeCallback = scrollEventAdapter2.mCallback) != null) {
            compositeOnPageChangeCallback.onPageSelected(min);
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

    @Override // android.view.View
    public final void setOverScrollMode(int i) {
        RecyclerViewImpl recyclerViewImpl = this.mRecyclerView;
        if (recyclerViewImpl != null) {
            recyclerViewImpl.setOverScrollMode(i);
        }
        super.setOverScrollMode(i);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedState extends View.BaseSavedState {
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

    public ViewPager2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTmpContainerRect = new Rect();
        this.mTmpChildRect = new Rect();
        this.mExternalPageChangeCallbacks = new CompositeOnPageChangeCallback(3);
        this.mCurrentItemDirty = false;
        new DataSetChangeObserver() { // from class: androidx.viewpager2.widget.ViewPager2.1
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
        this.mContainerScaleValue = 1.0f;
        this.mIsSuggestionPagingEnabled = false;
        this.mScrollState = 0;
        initialize(context, attributeSet);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class OnPageChangeCallback {
        public void onPageScrollStateChanged(int i) {
        }

        public void onPageSelected(int i) {
        }

        public void onPageScrolled(float f, int i, int i2) {
        }
    }

    public ViewPager2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTmpContainerRect = new Rect();
        this.mTmpChildRect = new Rect();
        this.mExternalPageChangeCallbacks = new CompositeOnPageChangeCallback(3);
        this.mCurrentItemDirty = false;
        new DataSetChangeObserver() { // from class: androidx.viewpager2.widget.ViewPager2.1
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
        this.mContainerScaleValue = 1.0f;
        this.mIsSuggestionPagingEnabled = false;
        this.mScrollState = 0;
        initialize(context, attributeSet);
    }

    public ViewPager2(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTmpContainerRect = new Rect();
        this.mTmpChildRect = new Rect();
        this.mExternalPageChangeCallbacks = new CompositeOnPageChangeCallback(3);
        this.mCurrentItemDirty = false;
        new DataSetChangeObserver() { // from class: androidx.viewpager2.widget.ViewPager2.1
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
        this.mContainerScaleValue = 1.0f;
        this.mIsSuggestionPagingEnabled = false;
        this.mScrollState = 0;
        initialize(context, attributeSet);
    }
}
