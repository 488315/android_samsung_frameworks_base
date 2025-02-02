package androidx.recyclerview.widget;

import android.R;
import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import androidx.collection.LongSparseArray;
import androidx.collection.LongSparseArrayKt;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.EdgeEffectCompat;
import androidx.customview.poolingcontainer.PoolingContainer;
import androidx.customview.view.AbsSavedState;
import androidx.recyclerview.R$styleable;
import androidx.recyclerview.widget.AdapterHelper;
import androidx.recyclerview.widget.ChildHelper;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import androidx.recyclerview.widget.ViewBoundsCheck;
import androidx.recyclerview.widget.ViewInfoStore;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public class RecyclerView extends ViewGroup {
    private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE;
    static boolean sDebugAssertionsEnabled = false;
    static final StretchEdgeEffectFactory sDefaultEdgeEffectFactory;
    static final Interpolator sQuinticInterpolator;
    static boolean sVerboseLoggingEnabled = false;
    RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
    private final AccessibilityManager mAccessibilityManager;
    AdapterHelper mAdapterHelper;
    private EdgeEffect mBottomGlow;
    ChildHelper mChildHelper;
    boolean mClipToPadding;
    boolean mDataSetHasChangedAfterLayout;
    boolean mDispatchItemsChangedEvent;
    private int mDispatchScrollCounter;
    private int mEatenAccessibilityChangeFlags;
    private EdgeEffectFactory mEdgeEffectFactory;
    boolean mFirstLayoutComplete;
    GapWorker mGapWorker;
    boolean mHasFixedSize;
    private boolean mIgnoreMotionEventTillDown;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mInterceptRequestLayoutDepth;
    private OnItemTouchListener mInterceptingOnItemTouchListener;
    boolean mIsAttached;
    ItemAnimator mItemAnimator;
    private ItemAnimatorRestoreListener mItemAnimatorListener;
    private Runnable mItemAnimatorRunner;
    final ArrayList<ItemDecoration> mItemDecorations;
    boolean mItemsAddedOrRemoved;
    boolean mItemsChanged;
    private int mLastTouchX;
    private int mLastTouchY;
    LayoutManager mLayout;
    private int mLayoutOrScrollCounter;
    boolean mLayoutSuppressed;
    boolean mLayoutWasDefered;
    private EdgeEffect mLeftGlow;
    private final int mMaxFlingVelocity;
    private final int mMinFlingVelocity;
    private final int[] mNestedOffsets;
    private final ArrayList<OnItemTouchListener> mOnItemTouchListeners;
    final List<ViewHolder> mPendingAccessibilityImportanceChange;
    SavedState mPendingSavedState;
    private final float mPhysicalCoef;
    GapWorker.LayoutPrefetchRegistryImpl mPrefetchRegistry;
    private boolean mPreserveFocusAfterLayout;
    final Recycler mRecycler;
    RecyclerListener mRecyclerListener;
    final List<RecyclerListener> mRecyclerListeners;
    final int[] mReusableIntPair;
    private EdgeEffect mRightGlow;
    private float mScaledHorizontalScrollFactor;
    private float mScaledVerticalScrollFactor;
    private OnScrollListener mScrollListener;
    private List<OnScrollListener> mScrollListeners;
    private final int[] mScrollOffset;
    private int mScrollPointerId;
    private int mScrollState;
    private NestedScrollingChildHelper mScrollingChildHelper;
    final State mState;
    final Rect mTempRect;
    private final Rect mTempRect2;
    final RectF mTempRectF;
    private EdgeEffect mTopGlow;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    final ViewFlinger mViewFlinger;
    private final C01624 mViewInfoProcessCallback;
    final ViewInfoStore mViewInfoStore;
    private static final int[] NESTED_SCROLLING_ATTRS = {R.attr.nestedScrollingEnabled};
    private static final float DECELERATION_RATE = (float) (Math.log(0.78d) / Math.log(0.9d));
    static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC = true;
    static final boolean ALLOW_THREAD_GAP_WORK = true;

    /* renamed from: androidx.recyclerview.widget.RecyclerView$3 */
    final class InterpolatorC01613 implements Interpolator {
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    }

    /* renamed from: androidx.recyclerview.widget.RecyclerView$5 */
    final class C01635 implements ChildHelper.Callback {
        C01635() {
        }

        public final int getChildCount() {
            return RecyclerView.this.getChildCount();
        }

        public final void removeViewAt(int i) {
            RecyclerView recyclerView = RecyclerView.this;
            View childAt = recyclerView.getChildAt(i);
            if (childAt != null) {
                recyclerView.getClass();
                RecyclerView.getChildViewHolderInt(childAt);
                childAt.clearAnimation();
            }
            recyclerView.removeViewAt(i);
        }
    }

    public static abstract class Adapter<VH extends ViewHolder> {
    }

    public interface ChildDrawingOrderCallback {
    }

    public static class EdgeEffectFactory {
    }

    public static abstract class ItemAnimator {
        private ItemAnimatorListener mListener = null;
        private ArrayList<ItemAnimatorFinishedListener> mFinishedListeners = new ArrayList<>();
        private long mRemoveDuration = 120;
        private long mMoveDuration = 250;
        private long mChangeDuration = 250;

        public interface ItemAnimatorFinishedListener {
            void onAnimationsFinished();
        }

        interface ItemAnimatorListener {
        }

        public final void dispatchAnimationFinished(ViewHolder viewHolder) {
            ItemAnimatorListener itemAnimatorListener = this.mListener;
            if (itemAnimatorListener != null) {
                ItemAnimatorRestoreListener itemAnimatorRestoreListener = (ItemAnimatorRestoreListener) itemAnimatorListener;
                viewHolder.setIsRecyclable(true);
                if (viewHolder.mShadowedHolder != null) {
                    viewHolder.mShadowedHolder = null;
                }
                if ((viewHolder.mFlags & 16) != 0) {
                    return;
                }
                RecyclerView recyclerView = RecyclerView.this;
                recyclerView.startInterceptRequestLayout();
                boolean removeViewIfHidden = recyclerView.mChildHelper.removeViewIfHidden();
                if (removeViewIfHidden) {
                    RecyclerView.getChildViewHolderInt(null);
                    recyclerView.mRecycler.unscrapView(null);
                    throw null;
                }
                recyclerView.stopInterceptRequestLayout(!removeViewIfHidden);
                if (removeViewIfHidden || !viewHolder.isTmpDetached()) {
                    return;
                }
                recyclerView.removeDetachedView(null, false);
            }
        }

        public final void dispatchAnimationsFinished() {
            int size = this.mFinishedListeners.size();
            for (int i = 0; i < size; i++) {
                this.mFinishedListeners.get(i).onAnimationsFinished();
            }
            this.mFinishedListeners.clear();
        }

        public abstract void endAnimations();

        public final long getChangeDuration() {
            return this.mChangeDuration;
        }

        public final long getMoveDuration() {
            return this.mMoveDuration;
        }

        public final long getRemoveDuration() {
            return this.mRemoveDuration;
        }

        public abstract boolean isRunning();

        public abstract void runPendingAnimations();

        final void setListener(ItemAnimatorListener itemAnimatorListener) {
            this.mListener = itemAnimatorListener;
        }
    }

    private class ItemAnimatorRestoreListener implements ItemAnimator.ItemAnimatorListener {
        ItemAnimatorRestoreListener() {
        }
    }

    public static abstract class OnFlingListener {
    }

    public interface OnItemTouchListener {
        boolean onInterceptTouchEvent(MotionEvent motionEvent);

        void onRequestDisallowInterceptTouchEvent();

        void onTouchEvent(MotionEvent motionEvent);
    }

    public static abstract class OnScrollListener {
    }

    public static class RecycledViewPool {
        SparseArray<ScrapData> mScrap = new SparseArray<>();
        int mAttachCountForClearing = 0;
        Set<Adapter<?>> mAttachedAdaptersForPoolingContainer = Collections.newSetFromMap(new IdentityHashMap());

        static class ScrapData {
            final ArrayList<ViewHolder> mScrapHeap = new ArrayList<>();
            int mMaxScrap = 5;
            long mBindRunningAverageNs = 0;

            ScrapData() {
            }
        }

        private ScrapData getScrapDataForType() {
            ScrapData scrapData = this.mScrap.get(0);
            if (scrapData != null) {
                return scrapData;
            }
            ScrapData scrapData2 = new ScrapData();
            this.mScrap.put(0, scrapData2);
            return scrapData2;
        }

        public final void putRecycledView(ViewHolder viewHolder) {
            viewHolder.getClass();
            ArrayList<ViewHolder> arrayList = getScrapDataForType().mScrapHeap;
            if (this.mScrap.get(0).mMaxScrap <= arrayList.size()) {
                PoolingContainer.callPoolingContainerOnRelease();
                throw null;
            }
            if (RecyclerView.sDebugAssertionsEnabled && arrayList.contains(viewHolder)) {
                throw new IllegalArgumentException("this scrap item already exists");
            }
            viewHolder.resetInternal();
            arrayList.add(viewHolder);
        }

        final boolean willBindInTime(long j, long j2) {
            long j3 = getScrapDataForType().mBindRunningAverageNs;
            return j3 == 0 || j + j3 < j2;
        }
    }

    public final class Recycler {
        final ArrayList<ViewHolder> mAttachedScrap;
        final ArrayList<ViewHolder> mCachedViews;
        ArrayList<ViewHolder> mChangedScrap;
        RecycledViewPool mRecyclerPool;
        private int mRequestedCacheMax;
        private final List<ViewHolder> mUnmodifiableAttachedScrap;
        int mViewCacheMax;

        public Recycler() {
            ArrayList<ViewHolder> arrayList = new ArrayList<>();
            this.mAttachedScrap = arrayList;
            this.mChangedScrap = null;
            this.mCachedViews = new ArrayList<>();
            this.mUnmodifiableAttachedScrap = Collections.unmodifiableList(arrayList);
            this.mRequestedCacheMax = 2;
            this.mViewCacheMax = 2;
        }

        private void poolingContainerDetach(boolean z) {
            RecycledViewPool recycledViewPool = this.mRecyclerPool;
            if (recycledViewPool != null) {
                recycledViewPool.mAttachedAdaptersForPoolingContainer.remove(null);
                if (recycledViewPool.mAttachedAdaptersForPoolingContainer.size() != 0 || z) {
                    return;
                }
                for (int i = 0; i < recycledViewPool.mScrap.size(); i++) {
                    SparseArray<RecycledViewPool.ScrapData> sparseArray = recycledViewPool.mScrap;
                    ArrayList<ViewHolder> arrayList = sparseArray.get(sparseArray.keyAt(i)).mScrapHeap;
                    if (arrayList.size() > 0) {
                        arrayList.get(0).getClass();
                        PoolingContainer.callPoolingContainerOnRelease();
                        throw null;
                    }
                }
            }
        }

        static void recycleViewHolderInternal(ViewHolder viewHolder) {
            if (!viewHolder.isScrap()) {
                throw null;
            }
            throw null;
        }

        public final int convertPreLayoutPositionToPostLayout(int i) {
            RecyclerView recyclerView = RecyclerView.this;
            if (i >= 0 && i < recyclerView.mState.getItemCount()) {
                return !recyclerView.mState.mInPreLayout ? i : recyclerView.mAdapterHelper.findPositionOffset(i, 0);
            }
            throw new IndexOutOfBoundsException("invalid position " + i + ". State item count is " + recyclerView.mState.getItemCount() + recyclerView.exceptionLabel());
        }

        final RecycledViewPool getRecycledViewPool() {
            if (this.mRecyclerPool == null) {
                this.mRecyclerPool = new RecycledViewPool();
                RecyclerView.this.getClass();
            }
            return this.mRecyclerPool;
        }

        final void onAdapterChanged() {
            this.mAttachedScrap.clear();
            recycleAndClearCachedViews();
            poolingContainerDetach(true);
            RecycledViewPool recycledViewPool = getRecycledViewPool();
            if (recycledViewPool.mAttachCountForClearing == 0) {
                for (int i = 0; i < recycledViewPool.mScrap.size(); i++) {
                    RecycledViewPool.ScrapData valueAt = recycledViewPool.mScrap.valueAt(i);
                    Iterator<ViewHolder> it = valueAt.mScrapHeap.iterator();
                    if (it.hasNext()) {
                        it.next().getClass();
                        PoolingContainer.callPoolingContainerOnRelease();
                        throw null;
                    }
                    valueAt.mScrapHeap.clear();
                }
            }
            if (this.mRecyclerPool != null) {
                RecyclerView.this.getClass();
            }
        }

        final void onAttachedToWindow() {
            if (this.mRecyclerPool != null) {
                RecyclerView.this.getClass();
            }
        }

        final void onDetachedFromWindow() {
            ArrayList<ViewHolder> arrayList = this.mCachedViews;
            if (arrayList.size() <= 0) {
                RecyclerView.this.getClass();
                poolingContainerDetach(false);
            } else {
                arrayList.get(0).getClass();
                PoolingContainer.callPoolingContainerOnRelease();
                throw null;
            }
        }

        final void recycleAndClearCachedViews() {
            ArrayList<ViewHolder> arrayList = this.mCachedViews;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                recycleCachedViewAt(size);
            }
            arrayList.clear();
            if (RecyclerView.ALLOW_THREAD_GAP_WORK) {
                GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = RecyclerView.this.mPrefetchRegistry;
                int[] iArr = layoutPrefetchRegistryImpl.mPrefetchArray;
                if (iArr != null) {
                    Arrays.fill(iArr, -1);
                }
                layoutPrefetchRegistryImpl.mCount = 0;
            }
        }

        final void recycleCachedViewAt(int i) {
            Object obj;
            Object obj2;
            if (RecyclerView.sVerboseLoggingEnabled) {
                Log.d("RecyclerView", "Recycling cached view at index " + i);
            }
            ArrayList<ViewHolder> arrayList = this.mCachedViews;
            ViewHolder viewHolder = arrayList.get(i);
            if (RecyclerView.sVerboseLoggingEnabled) {
                Log.d("RecyclerView", "CachedViewHolder to be recycled: " + viewHolder);
            }
            viewHolder.getClass();
            RecyclerView recyclerView = RecyclerView.this;
            RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate = recyclerView.mAccessibilityDelegate;
            if (recyclerViewAccessibilityDelegate != null) {
                RecyclerViewAccessibilityDelegate.ItemDelegate itemDelegate = recyclerViewAccessibilityDelegate.getItemDelegate();
                ViewCompat.setAccessibilityDelegate(null, itemDelegate instanceof RecyclerViewAccessibilityDelegate.ItemDelegate ? itemDelegate.getAndRemoveOriginalDelegateForItem() : null);
            }
            RecyclerListener recyclerListener = recyclerView.mRecyclerListener;
            if (recyclerListener != null) {
                recyclerListener.onViewRecycled();
            }
            int size = ((ArrayList) recyclerView.mRecyclerListeners).size();
            for (int i2 = 0; i2 < size; i2++) {
                ((RecyclerListener) ((ArrayList) recyclerView.mRecyclerListeners).get(i2)).onViewRecycled();
            }
            if (recyclerView.mState != null) {
                ViewInfoStore viewInfoStore = recyclerView.mViewInfoStore;
                LongSparseArray<ViewHolder> longSparseArray = viewInfoStore.mOldChangedHolders;
                int size2 = longSparseArray.size() - 1;
                while (true) {
                    if (size2 < 0) {
                        break;
                    }
                    if (viewHolder == longSparseArray.valueAt(size2)) {
                        Object obj3 = longSparseArray.values[size2];
                        obj = LongSparseArrayKt.DELETED;
                        if (obj3 != obj) {
                            Object[] objArr = longSparseArray.values;
                            obj2 = LongSparseArrayKt.DELETED;
                            objArr[size2] = obj2;
                            longSparseArray.garbage = true;
                        }
                    } else {
                        size2--;
                    }
                }
                ViewInfoStore.InfoRecord remove = viewInfoStore.mLayoutHolderMap.remove(viewHolder);
                if (remove != null) {
                    ViewInfoStore.InfoRecord.sPool.release(remove);
                }
            }
            if (RecyclerView.sVerboseLoggingEnabled) {
                Log.d("RecyclerView", "dispatchViewRecycled: " + viewHolder);
            }
            viewHolder.mOwnerRecyclerView = null;
            getRecycledViewPool().putRecycledView(viewHolder);
            arrayList.remove(i);
        }

        final void setRecycledViewPool(RecycledViewPool recycledViewPool) {
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.getClass();
            poolingContainerDetach(false);
            if (this.mRecyclerPool != null) {
                r1.mAttachCountForClearing--;
            }
            this.mRecyclerPool = recycledViewPool;
            if (recycledViewPool != null) {
                recyclerView.getAdapter();
            }
            if (this.mRecyclerPool != null) {
                recyclerView.getClass();
            }
        }

        public final void setViewCacheSize(int i) {
            this.mRequestedCacheMax = i;
            updateViewCacheSize();
        }

        /* JADX WARN: Removed duplicated region for block: B:100:0x0178  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x004f  */
        /* JADX WARN: Removed duplicated region for block: B:95:0x013e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        final void tryGetViewHolderForPositionByDeadline(int i, long j) {
            ViewHolder viewHolder;
            boolean z;
            RecyclerView recyclerView;
            int size;
            RecyclerView recyclerView2 = RecyclerView.this;
            if (i < 0 || i >= recyclerView2.mState.getItemCount()) {
                throw new IndexOutOfBoundsException("Invalid item position " + i + "(" + i + "). Item count:" + recyclerView2.mState.getItemCount() + recyclerView2.exceptionLabel());
            }
            if (recyclerView2.mState.mInPreLayout) {
                ArrayList<ViewHolder> arrayList = this.mChangedScrap;
                if (arrayList != null && (size = arrayList.size()) != 0) {
                    for (int i2 = 0; i2 < size; i2++) {
                        viewHolder = this.mChangedScrap.get(i2);
                        if (!((viewHolder.mFlags & 32) != 0) && viewHolder.getLayoutPosition() == i) {
                            viewHolder.addFlags(32);
                        }
                    }
                    throw null;
                }
                viewHolder = null;
                if (viewHolder != null) {
                    z = true;
                    if (viewHolder == null) {
                        ArrayList<ViewHolder> arrayList2 = this.mAttachedScrap;
                        int size2 = arrayList2.size();
                        for (int i3 = 0; i3 < size2; i3++) {
                            ViewHolder viewHolder2 = arrayList2.get(i3);
                            if (!((viewHolder2.mFlags & 32) != 0) && viewHolder2.getLayoutPosition() == i && !viewHolder2.isInvalid() && (recyclerView2.mState.mInPreLayout || !viewHolder2.isRemoved())) {
                                viewHolder2.addFlags(32);
                                viewHolder = viewHolder2;
                                break;
                            }
                        }
                        ChildHelper childHelper = recyclerView2.mChildHelper;
                        ArrayList arrayList3 = (ArrayList) childHelper.mHiddenViews;
                        if (arrayList3.size() > 0) {
                            View view = (View) arrayList3.get(0);
                            ((C01635) childHelper.mCallback).getClass();
                            RecyclerView.getChildViewHolderInt(view);
                            throw null;
                        }
                        ArrayList<ViewHolder> arrayList4 = this.mCachedViews;
                        int size3 = arrayList4.size();
                        for (int i4 = 0; i4 < size3; i4++) {
                            ViewHolder viewHolder3 = arrayList4.get(i4);
                            if (!viewHolder3.isInvalid() && viewHolder3.getLayoutPosition() == i) {
                                throw null;
                            }
                        }
                        viewHolder = null;
                        if (viewHolder != null) {
                            if (!viewHolder.isRemoved()) {
                                if (viewHolder.mPosition >= 0) {
                                    throw null;
                                }
                                throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + viewHolder + recyclerView2.exceptionLabel());
                            }
                            if (RecyclerView.sDebugAssertionsEnabled && !recyclerView2.mState.mInPreLayout) {
                                throw new IllegalStateException("should not receive a removed view unless it is pre layout" + recyclerView2.exceptionLabel());
                            }
                            if (!recyclerView2.mState.mInPreLayout) {
                                viewHolder.addFlags(4);
                                if (viewHolder.isScrap()) {
                                    recyclerView2.removeDetachedView(null, false);
                                    viewHolder.mScrapContainer.unscrapView(viewHolder);
                                } else {
                                    int i5 = viewHolder.mFlags;
                                    if ((i5 & 32) != 0) {
                                        viewHolder.mFlags = i5 & (-33);
                                    }
                                }
                                recycleViewHolderInternal(viewHolder);
                                throw null;
                            }
                            z = true;
                        }
                    }
                    if (viewHolder != null) {
                        int findPositionOffset = recyclerView2.mAdapterHelper.findPositionOffset(i, 0);
                        if (findPositionOffset >= 0) {
                            throw null;
                        }
                        throw new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + i + "(offset:" + findPositionOffset + ").state:" + recyclerView2.mState.getItemCount() + recyclerView2.exceptionLabel());
                    }
                    if (z) {
                        State state = recyclerView2.mState;
                        if (!state.mInPreLayout) {
                            int i6 = viewHolder.mFlags;
                            if ((i6 & 8192) != 0) {
                                int i7 = (i6 & (-8193)) | 0;
                                viewHolder.mFlags = i7;
                                if (state.mRunSimpleAnimations) {
                                    int i8 = i7 & 14;
                                    if (!viewHolder.isInvalid() && (i8 & 4) == 0 && (recyclerView = viewHolder.mOwnerRecyclerView) != null) {
                                        recyclerView.getAdapterPositionInRecyclerView(viewHolder);
                                    }
                                    ItemAnimator itemAnimator = recyclerView2.mItemAnimator;
                                    viewHolder.getUnmodifiedPayloads();
                                    itemAnimator.getClass();
                                    throw null;
                                }
                            }
                        }
                    }
                    if (recyclerView2.mState.mInPreLayout) {
                        if ((viewHolder.mFlags & 1) != 0) {
                            viewHolder.mPreLayoutPosition = i;
                            throw null;
                        }
                    }
                    int i9 = viewHolder.mFlags;
                    if ((i9 & 1) != 0) {
                        if (!((i9 & 2) != 0) && !viewHolder.isInvalid()) {
                            throw null;
                        }
                    }
                    if (RecyclerView.sDebugAssertionsEnabled && viewHolder.isRemoved()) {
                        throw new IllegalStateException("Removed holder should be bound and it should come here only in pre-layout. Holder: " + viewHolder + recyclerView2.exceptionLabel());
                    }
                    recyclerView2.mAdapterHelper.findPositionOffset(i, 0);
                    viewHolder.mOwnerRecyclerView = recyclerView2;
                    long nanoTime = recyclerView2.getNanoTime();
                    if (j != Long.MAX_VALUE && !this.mRecyclerPool.willBindInTime(nanoTime, j)) {
                        throw null;
                    }
                    if (!viewHolder.isTmpDetached()) {
                        throw null;
                    }
                    recyclerView2.getChildCount();
                    throw null;
                }
            } else {
                viewHolder = null;
            }
            z = false;
            if (viewHolder == null) {
            }
            if (viewHolder != null) {
            }
        }

        final void unscrapView(ViewHolder viewHolder) {
            if (viewHolder.mInChangeScrap) {
                this.mChangedScrap.remove(viewHolder);
            } else {
                this.mAttachedScrap.remove(viewHolder);
            }
            viewHolder.mScrapContainer = null;
            viewHolder.mInChangeScrap = false;
            viewHolder.mFlags &= -33;
        }

        final void updateViewCacheSize() {
            LayoutManager layoutManager = RecyclerView.this.mLayout;
            this.mViewCacheMax = this.mRequestedCacheMax + 0;
            ArrayList<ViewHolder> arrayList = this.mCachedViews;
            int size = arrayList.size();
            while (true) {
                size--;
                if (size < 0 || arrayList.size() <= this.mViewCacheMax) {
                    return;
                } else {
                    recycleCachedViewAt(size);
                }
            }
        }
    }

    public interface RecyclerListener {
        void onViewRecycled();
    }

    public static class State {
        int mPreviousLayoutItemCount = 0;
        int mDeletedInvisibleItemCountSincePreviousLayout = 0;
        int mLayoutStep = 1;
        boolean mStructureChanged = false;
        boolean mInPreLayout = false;
        boolean mRunSimpleAnimations = false;
        boolean mRunPredictiveAnimations = false;

        public final int getItemCount() {
            if (this.mInPreLayout) {
                return this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout;
            }
            return 0;
        }

        public final String toString() {
            return "State{mTargetPosition=-1, mData=null, mItemCount=0, mIsMeasuring=false, mPreviousLayoutItemCount=" + this.mPreviousLayoutItemCount + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.mDeletedInvisibleItemCountSincePreviousLayout + ", mStructureChanged=" + this.mStructureChanged + ", mInPreLayout=" + this.mInPreLayout + ", mRunSimpleAnimations=" + this.mRunSimpleAnimations + ", mRunPredictiveAnimations=" + this.mRunPredictiveAnimations + '}';
        }
    }

    static class StretchEdgeEffectFactory extends EdgeEffectFactory {
    }

    public static abstract class ViewCacheExtension {
    }

    class ViewFlinger implements Runnable {
        private boolean mEatRunOnAnimationRequest;
        Interpolator mInterpolator;
        private int mLastFlingX;
        private int mLastFlingY;
        OverScroller mOverScroller;
        private boolean mReSchedulePostAnimationCallback;

        ViewFlinger() {
            Interpolator interpolator = RecyclerView.sQuinticInterpolator;
            this.mInterpolator = interpolator;
            this.mEatRunOnAnimationRequest = false;
            this.mReSchedulePostAnimationCallback = false;
            this.mOverScroller = new OverScroller(RecyclerView.this.getContext(), interpolator);
        }

        public final void fling(int i, int i2) {
            RecyclerView.this.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            Interpolator interpolator = this.mInterpolator;
            Interpolator interpolator2 = RecyclerView.sQuinticInterpolator;
            if (interpolator != interpolator2) {
                this.mInterpolator = interpolator2;
                this.mOverScroller = new OverScroller(RecyclerView.this.getContext(), interpolator2);
            }
            this.mOverScroller.fling(0, 0, i, i2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (this.mEatRunOnAnimationRequest) {
                this.mReSchedulePostAnimationCallback = true;
            } else {
                RecyclerView.this.removeCallbacks(this);
                ViewCompat.postOnAnimation(RecyclerView.this, this);
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            RecyclerView recyclerView = RecyclerView.this;
            if (recyclerView.mLayout == null) {
                recyclerView.removeCallbacks(this);
                this.mOverScroller.abortAnimation();
                return;
            }
            this.mReSchedulePostAnimationCallback = false;
            this.mEatRunOnAnimationRequest = true;
            recyclerView.consumePendingUpdateOperations();
            OverScroller overScroller = this.mOverScroller;
            if (overScroller.computeScrollOffset()) {
                int currX = overScroller.getCurrX();
                int currY = overScroller.getCurrY();
                int i = currX - this.mLastFlingX;
                int i2 = currY - this.mLastFlingY;
                this.mLastFlingX = currX;
                this.mLastFlingY = currY;
                int consumeFlingInHorizontalStretch = RecyclerView.this.consumeFlingInHorizontalStretch(i);
                int consumeFlingInVerticalStretch = RecyclerView.this.consumeFlingInVerticalStretch(i2);
                RecyclerView recyclerView2 = RecyclerView.this;
                int[] iArr = recyclerView2.mReusableIntPair;
                iArr[0] = 0;
                iArr[1] = 0;
                if (recyclerView2.dispatchNestedPreScroll(consumeFlingInHorizontalStretch, consumeFlingInVerticalStretch, 1, iArr, null)) {
                    int[] iArr2 = RecyclerView.this.mReusableIntPair;
                    consumeFlingInHorizontalStretch -= iArr2[0];
                    consumeFlingInVerticalStretch -= iArr2[1];
                }
                if (RecyclerView.this.getOverScrollMode() != 2) {
                    RecyclerView.this.considerReleasingGlowsOnScroll(consumeFlingInHorizontalStretch, consumeFlingInVerticalStretch);
                }
                RecyclerView.this.getClass();
                if (!RecyclerView.this.mItemDecorations.isEmpty()) {
                    RecyclerView.this.invalidate();
                }
                RecyclerView recyclerView3 = RecyclerView.this;
                int[] iArr3 = recyclerView3.mReusableIntPair;
                iArr3[0] = 0;
                iArr3[1] = 0;
                recyclerView3.dispatchNestedScroll(0, consumeFlingInHorizontalStretch, consumeFlingInVerticalStretch, null, 1, iArr3);
                RecyclerView recyclerView4 = RecyclerView.this;
                int[] iArr4 = recyclerView4.mReusableIntPair;
                int i3 = consumeFlingInHorizontalStretch - iArr4[0];
                int i4 = consumeFlingInVerticalStretch - iArr4[1];
                if (!recyclerView4.awakenScrollBars()) {
                    RecyclerView.this.invalidate();
                }
                boolean z = overScroller.isFinished() || (((overScroller.getCurrX() == overScroller.getFinalX()) || i3 != 0) && ((overScroller.getCurrY() == overScroller.getFinalY()) || i4 != 0));
                RecyclerView.this.mLayout.getClass();
                if (z) {
                    if (RecyclerView.this.getOverScrollMode() != 2) {
                        int currVelocity = (int) overScroller.getCurrVelocity();
                        int i5 = i3 < 0 ? -currVelocity : i3 > 0 ? currVelocity : 0;
                        if (i4 < 0) {
                            currVelocity = -currVelocity;
                        } else if (i4 <= 0) {
                            currVelocity = 0;
                        }
                        RecyclerView.this.absorbGlows(i5, currVelocity);
                    }
                    if (RecyclerView.ALLOW_THREAD_GAP_WORK) {
                        GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = RecyclerView.this.mPrefetchRegistry;
                        int[] iArr5 = layoutPrefetchRegistryImpl.mPrefetchArray;
                        if (iArr5 != null) {
                            Arrays.fill(iArr5, -1);
                        }
                        layoutPrefetchRegistryImpl.mCount = 0;
                    }
                } else {
                    if (this.mEatRunOnAnimationRequest) {
                        this.mReSchedulePostAnimationCallback = true;
                    } else {
                        RecyclerView.this.removeCallbacks(this);
                        ViewCompat.postOnAnimation(RecyclerView.this, this);
                    }
                    RecyclerView recyclerView5 = RecyclerView.this;
                    GapWorker gapWorker = recyclerView5.mGapWorker;
                    if (gapWorker != null) {
                        gapWorker.postFromTraversal(recyclerView5, 0, 0);
                    }
                }
            }
            RecyclerView.this.mLayout.getClass();
            this.mEatRunOnAnimationRequest = false;
            if (this.mReSchedulePostAnimationCallback) {
                RecyclerView.this.removeCallbacks(this);
                ViewCompat.postOnAnimation(RecyclerView.this, this);
            } else {
                RecyclerView.this.setScrollState(0);
                RecyclerView.this.stopNestedScroll(1);
            }
        }

        public final void smoothScrollBy(int i, int i2) {
            int abs = Math.abs(i);
            int abs2 = Math.abs(i2);
            boolean z = abs > abs2;
            RecyclerView recyclerView = RecyclerView.this;
            int width = z ? recyclerView.getWidth() : recyclerView.getHeight();
            if (!z) {
                abs = abs2;
            }
            int min = Math.min((int) (((abs / width) + 1.0f) * 300.0f), 2000);
            Interpolator interpolator = RecyclerView.sQuinticInterpolator;
            if (this.mInterpolator != interpolator) {
                this.mInterpolator = interpolator;
                this.mOverScroller = new OverScroller(RecyclerView.this.getContext(), interpolator);
            }
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            RecyclerView.this.setScrollState(2);
            this.mOverScroller.startScroll(0, 0, i, i2, min);
            if (this.mEatRunOnAnimationRequest) {
                this.mReSchedulePostAnimationCallback = true;
            } else {
                RecyclerView.this.removeCallbacks(this);
                ViewCompat.postOnAnimation(RecyclerView.this, this);
            }
        }
    }

    public static abstract class ViewHolder {
        private static final List<Object> FULLUPDATE_PAYLOADS = Collections.emptyList();
        int mFlags;
        boolean mInChangeScrap;
        private int mIsRecyclableCount;
        long mItemId;
        int mOldPosition;
        RecyclerView mOwnerRecyclerView;
        List<Object> mPayloads;
        int mPendingAccessibilityState;
        int mPosition;
        int mPreLayoutPosition;
        Recycler mScrapContainer;
        ViewHolder mShadowedHolder;
        List<Object> mUnmodifiedPayloads;
        private int mWasImportantForAccessibilityBeforeHidden;

        final void addChangePayload(Object obj) {
            if (obj == null) {
                addFlags(1024);
                return;
            }
            if ((1024 & this.mFlags) == 0) {
                if (this.mPayloads == null) {
                    ArrayList arrayList = new ArrayList();
                    this.mPayloads = arrayList;
                    this.mUnmodifiedPayloads = Collections.unmodifiableList(arrayList);
                }
                ((ArrayList) this.mPayloads).add(obj);
            }
        }

        final void addFlags(int i) {
            this.mFlags = i | this.mFlags;
        }

        public final int getLayoutPosition() {
            int i = this.mPreLayoutPosition;
            return i == -1 ? this.mPosition : i;
        }

        final List<Object> getUnmodifiedPayloads() {
            List<Object> list;
            return ((this.mFlags & 1024) != 0 || (list = this.mPayloads) == null || ((ArrayList) list).size() == 0) ? FULLUPDATE_PAYLOADS : this.mUnmodifiedPayloads;
        }

        final boolean isInvalid() {
            return (this.mFlags & 4) != 0;
        }

        final boolean isRemoved() {
            return (this.mFlags & 8) != 0;
        }

        final boolean isScrap() {
            return this.mScrapContainer != null;
        }

        final boolean isTmpDetached() {
            return (this.mFlags & 256) != 0;
        }

        final void onLeftHiddenState(RecyclerView recyclerView) {
            int i = this.mWasImportantForAccessibilityBeforeHidden;
            if (recyclerView.isComputingLayout()) {
                this.mPendingAccessibilityState = i;
                ((ArrayList) recyclerView.mPendingAccessibilityImportanceChange).add(this);
            } else {
                ViewCompat.setImportantForAccessibility(null, i);
            }
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        final void resetInternal() {
            if (RecyclerView.sDebugAssertionsEnabled && isTmpDetached()) {
                throw new IllegalStateException("Attempting to reset temp-detached ViewHolder: " + this + ". ViewHolders should be fully detached before resetting.");
            }
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1L;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            List<Object> list = this.mPayloads;
            if (list != null) {
                ((ArrayList) list).clear();
            }
            this.mFlags &= -1025;
            this.mWasImportantForAccessibilityBeforeHidden = 0;
            this.mPendingAccessibilityState = -1;
        }

        public final void setIsRecyclable(boolean z) {
            int i = this.mIsRecyclableCount;
            int i2 = z ? i - 1 : i + 1;
            this.mIsRecyclableCount = i2;
            if (i2 < 0) {
                this.mIsRecyclableCount = 0;
                if (RecyclerView.sDebugAssertionsEnabled) {
                    throw new RuntimeException("isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
                }
                Log.e("View", "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
            } else if (!z && i2 == 1) {
                this.mFlags |= 16;
            } else if (z && i2 == 0) {
                this.mFlags &= -17;
            }
            if (RecyclerView.sVerboseLoggingEnabled) {
                Log.d("RecyclerView", "setIsRecyclable val:" + z + ":" + this);
            }
        }

        public final String toString() {
            new StringBuilder((ViewHolder.class.isAnonymousClass() ? "ViewHolder" : ViewHolder.class.getSimpleName()) + "{" + Integer.toHexString(hashCode()) + " position=" + this.mPosition + " id=" + this.mItemId + ", oldPos=" + this.mOldPosition + ", pLpos:" + this.mPreLayoutPosition);
            isScrap();
            if (!((this.mFlags & 16) == 0 && !ViewCompat.hasTransientState())) {
                StringBuilder sb = new StringBuilder(" not recyclable(");
                sb.append(this.mIsRecyclableCount);
                sb.append(")");
            }
            if ((this.mFlags & 512) == 0) {
                isInvalid();
            }
            throw null;
        }
    }

    static {
        Class cls = Integer.TYPE;
        LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class, cls, cls};
        sQuinticInterpolator = new InterpolatorC01613();
        sDefaultEdgeEffectFactory = new StretchEdgeEffectFactory();
    }

    public RecyclerView(Context context) {
        this(context, null);
    }

    private static int consumeFlingInStretch(int i, EdgeEffect edgeEffect, EdgeEffect edgeEffect2, int i2) {
        if (i > 0 && edgeEffect != null && EdgeEffectCompat.getDistance(edgeEffect) != DECELERATION_RATE) {
            int round = Math.round(EdgeEffectCompat.onPullDistance(edgeEffect, ((-i) * 4.0f) / i2, 0.5f) * ((-i2) / 4.0f));
            if (round != i) {
                edgeEffect.finish();
            }
            return i - round;
        }
        if (i >= 0 || edgeEffect2 == null || EdgeEffectCompat.getDistance(edgeEffect2) == DECELERATION_RATE) {
            return i;
        }
        float f = i2;
        int round2 = Math.round(EdgeEffectCompat.onPullDistance(edgeEffect2, (i * 4.0f) / f, 0.5f) * (f / 4.0f));
        if (round2 != i) {
            edgeEffect2.finish();
        }
        return i - round2;
    }

    private boolean findInterceptingOnItemTouchListener(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int size = this.mOnItemTouchListeners.size();
        for (int i = 0; i < size; i++) {
            OnItemTouchListener onItemTouchListener = this.mOnItemTouchListeners.get(i);
            if (onItemTouchListener.onInterceptTouchEvent(motionEvent) && action != 3) {
                this.mInterceptingOnItemTouchListener = onItemTouchListener;
                return true;
            }
        }
        return false;
    }

    static ViewHolder getChildViewHolderInt(View view) {
        if (view == null) {
            return null;
        }
        ((LayoutParams) view.getLayoutParams()).getClass();
        return null;
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (this.mScrollingChildHelper == null) {
            this.mScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return this.mScrollingChildHelper;
    }

    private void onPointerUp(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mScrollPointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            this.mScrollPointerId = motionEvent.getPointerId(i);
            int x = (int) (motionEvent.getX(i) + 0.5f);
            this.mLastTouchX = x;
            this.mInitialTouchX = x;
            int y = (int) (motionEvent.getY(i) + 0.5f);
            this.mLastTouchY = y;
            this.mInitialTouchY = y;
        }
    }

    private void releaseGlows() {
        boolean z;
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            z = this.mLeftGlow.isFinished();
        } else {
            z = false;
        }
        EdgeEffect edgeEffect2 = this.mTopGlow;
        if (edgeEffect2 != null) {
            edgeEffect2.onRelease();
            z |= this.mTopGlow.isFinished();
        }
        EdgeEffect edgeEffect3 = this.mRightGlow;
        if (edgeEffect3 != null) {
            edgeEffect3.onRelease();
            z |= this.mRightGlow.isFinished();
        }
        EdgeEffect edgeEffect4 = this.mBottomGlow;
        if (edgeEffect4 != null) {
            edgeEffect4.onRelease();
            z |= this.mBottomGlow.isFinished();
        }
        if (z) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private int releaseHorizontalGlow(int i, float f) {
        float height = f / getHeight();
        float width = i / getWidth();
        EdgeEffect edgeEffect = this.mLeftGlow;
        float f2 = DECELERATION_RATE;
        if (edgeEffect == null || EdgeEffectCompat.getDistance(edgeEffect) == DECELERATION_RATE) {
            EdgeEffect edgeEffect2 = this.mRightGlow;
            if (edgeEffect2 != null && EdgeEffectCompat.getDistance(edgeEffect2) != DECELERATION_RATE) {
                if (canScrollHorizontally(1)) {
                    this.mRightGlow.onRelease();
                } else {
                    float onPullDistance = EdgeEffectCompat.onPullDistance(this.mRightGlow, width, height);
                    if (EdgeEffectCompat.getDistance(this.mRightGlow) == DECELERATION_RATE) {
                        this.mRightGlow.onRelease();
                    }
                    f2 = onPullDistance;
                }
                invalidate();
            }
        } else {
            if (canScrollHorizontally(-1)) {
                this.mLeftGlow.onRelease();
            } else {
                float f3 = -EdgeEffectCompat.onPullDistance(this.mLeftGlow, -width, 1.0f - height);
                if (EdgeEffectCompat.getDistance(this.mLeftGlow) == DECELERATION_RATE) {
                    this.mLeftGlow.onRelease();
                }
                f2 = f3;
            }
            invalidate();
        }
        return Math.round(f2 * getWidth());
    }

    private int releaseVerticalGlow(int i, float f) {
        float width = f / getWidth();
        float height = i / getHeight();
        EdgeEffect edgeEffect = this.mTopGlow;
        float f2 = DECELERATION_RATE;
        if (edgeEffect == null || EdgeEffectCompat.getDistance(edgeEffect) == DECELERATION_RATE) {
            EdgeEffect edgeEffect2 = this.mBottomGlow;
            if (edgeEffect2 != null && EdgeEffectCompat.getDistance(edgeEffect2) != DECELERATION_RATE) {
                if (canScrollVertically(1)) {
                    this.mBottomGlow.onRelease();
                } else {
                    float onPullDistance = EdgeEffectCompat.onPullDistance(this.mBottomGlow, height, 1.0f - width);
                    if (EdgeEffectCompat.getDistance(this.mBottomGlow) == DECELERATION_RATE) {
                        this.mBottomGlow.onRelease();
                    }
                    f2 = onPullDistance;
                }
                invalidate();
            }
        } else {
            if (canScrollVertically(-1)) {
                this.mTopGlow.onRelease();
            } else {
                float f3 = -EdgeEffectCompat.onPullDistance(this.mTopGlow, -height, width);
                if (EdgeEffectCompat.getDistance(this.mTopGlow) == DECELERATION_RATE) {
                    this.mTopGlow.onRelease();
                }
                f2 = f3;
            }
            invalidate();
        }
        return Math.round(f2 * getHeight());
    }

    private void requestChildOnScreen(View view, View view2) {
        View view3 = view2 != null ? view2 : view;
        this.mTempRect.set(0, 0, view3.getWidth(), view3.getHeight());
        ViewGroup.LayoutParams layoutParams = view3.getLayoutParams();
        if (layoutParams instanceof LayoutParams) {
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            if (!layoutParams2.mInsetsDirty) {
                Rect rect = layoutParams2.mDecorInsets;
                Rect rect2 = this.mTempRect;
                rect2.left -= rect.left;
                rect2.right += rect.right;
                rect2.top -= rect.top;
                rect2.bottom += rect.bottom;
            }
        }
        if (view2 != null) {
            offsetDescendantRectToMyCoords(view2, this.mTempRect);
            offsetRectIntoDescendantCoords(view, this.mTempRect);
        }
        this.mLayout.requestChildRectangleOnScreen(this, view, this.mTempRect, !this.mFirstLayoutComplete, view2 == null);
    }

    public static void setDebugAssertionsEnabled(boolean z) {
        sDebugAssertionsEnabled = z;
    }

    public static void setVerboseLoggingEnabled(boolean z) {
        sVerboseLoggingEnabled = z;
    }

    private boolean shouldAbsorb(EdgeEffect edgeEffect, int i, int i2) {
        if (i > 0) {
            return true;
        }
        float distance = EdgeEffectCompat.getDistance(edgeEffect) * i2;
        double log = Math.log((Math.abs(-i) * 0.35f) / (this.mPhysicalCoef * 0.015f));
        double d = DECELERATION_RATE;
        return ((float) (Math.exp((d / (d - 1.0d)) * log) * ((double) (this.mPhysicalCoef * 0.015f)))) < distance;
    }

    final void absorbGlows(int i, int i2) {
        if (i < 0) {
            ensureLeftGlow();
            if (this.mLeftGlow.isFinished()) {
                this.mLeftGlow.onAbsorb(-i);
            }
        } else if (i > 0) {
            ensureRightGlow();
            if (this.mRightGlow.isFinished()) {
                this.mRightGlow.onAbsorb(i);
            }
        }
        if (i2 < 0) {
            ensureTopGlow();
            if (this.mTopGlow.isFinished()) {
                this.mTopGlow.onAbsorb(-i2);
            }
        } else if (i2 > 0) {
            ensureBottomGlow();
            if (this.mBottomGlow.isFinished()) {
                this.mBottomGlow.onAbsorb(i2);
            }
        }
        if (i == 0 && i2 == 0) {
            return;
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.getClass();
        }
        super.addFocusables(arrayList, i, i2);
    }

    public final void addOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.add(onItemTouchListener);
    }

    public final void addOnScrollListener(OnScrollListener onScrollListener) {
        if (this.mScrollListeners == null) {
            this.mScrollListeners = new ArrayList();
        }
        this.mScrollListeners.add(onScrollListener);
    }

    final void assertNotInLayoutOrScroll(String str) {
        if (isComputingLayout()) {
            if (str != null) {
                throw new IllegalStateException(str);
            }
            throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling" + exceptionLabel());
        }
        if (this.mDispatchScrollCounter > 0) {
            Log.w("RecyclerView", "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new IllegalStateException("" + exceptionLabel()));
        }
    }

    @Override // android.view.ViewGroup
    protected final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && this.mLayout.checkLayoutParams((LayoutParams) layoutParams);
    }

    @Override // android.view.View
    public final int computeHorizontalScrollExtent() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollHorizontally()) {
            return this.mLayout.computeHorizontalScrollExtent(this.mState);
        }
        return 0;
    }

    @Override // android.view.View
    public final int computeHorizontalScrollOffset() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollHorizontally()) {
            this.mLayout.computeHorizontalScrollOffset(this.mState);
        }
        return 0;
    }

    @Override // android.view.View
    public final int computeHorizontalScrollRange() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollHorizontally()) {
            return this.mLayout.computeHorizontalScrollRange(this.mState);
        }
        return 0;
    }

    @Override // android.view.View
    public final int computeVerticalScrollExtent() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollVertically()) {
            return this.mLayout.computeVerticalScrollExtent(this.mState);
        }
        return 0;
    }

    @Override // android.view.View
    public final int computeVerticalScrollOffset() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollVertically()) {
            this.mLayout.computeVerticalScrollOffset(this.mState);
        }
        return 0;
    }

    @Override // android.view.View
    public final int computeVerticalScrollRange() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollVertically()) {
            return this.mLayout.computeVerticalScrollRange(this.mState);
        }
        return 0;
    }

    final void considerReleasingGlowsOnScroll(int i, int i2) {
        boolean z;
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect == null || edgeEffect.isFinished() || i <= 0) {
            z = false;
        } else {
            this.mLeftGlow.onRelease();
            z = this.mLeftGlow.isFinished();
        }
        EdgeEffect edgeEffect2 = this.mRightGlow;
        if (edgeEffect2 != null && !edgeEffect2.isFinished() && i < 0) {
            this.mRightGlow.onRelease();
            z |= this.mRightGlow.isFinished();
        }
        EdgeEffect edgeEffect3 = this.mTopGlow;
        if (edgeEffect3 != null && !edgeEffect3.isFinished() && i2 > 0) {
            this.mTopGlow.onRelease();
            z |= this.mTopGlow.isFinished();
        }
        EdgeEffect edgeEffect4 = this.mBottomGlow;
        if (edgeEffect4 != null && !edgeEffect4.isFinished() && i2 < 0) {
            this.mBottomGlow.onRelease();
            z |= this.mBottomGlow.isFinished();
        }
        if (z) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    final int consumeFlingInHorizontalStretch(int i) {
        return consumeFlingInStretch(i, this.mLeftGlow, this.mRightGlow, getWidth());
    }

    final int consumeFlingInVerticalStretch(int i) {
        return consumeFlingInStretch(i, this.mTopGlow, this.mBottomGlow, getHeight());
    }

    final void consumePendingUpdateOperations() {
        if (!this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout) {
            Trace.beginSection("RV FullInvalidate");
            Log.w("RecyclerView", "No adapter attached; skipping layout");
            Trace.endSection();
            return;
        }
        if (this.mAdapterHelper.mPendingUpdates.size() > 0) {
            this.mAdapterHelper.getClass();
            if (this.mAdapterHelper.mPendingUpdates.size() > 0) {
                Trace.beginSection("RV FullInvalidate");
                Log.w("RecyclerView", "No adapter attached; skipping layout");
                Trace.endSection();
            }
        }
    }

    final void defaultOnMeasure(int i, int i2) {
        setMeasuredDimension(LayoutManager.chooseSize(i, getPaddingRight() + getPaddingLeft(), ViewCompat.getMinimumWidth(this)), LayoutManager.chooseSize(i2, getPaddingBottom() + getPaddingTop(), ViewCompat.getMinimumHeight(this)));
    }

    @Override // android.view.View
    public final boolean dispatchNestedFling(float f, float f2, boolean z) {
        return getScrollingChildHelper().dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.View
    public final boolean dispatchNestedPreFling(float f, float f2) {
        return getScrollingChildHelper().dispatchNestedPreFling(f, f2);
    }

    @Override // android.view.View
    public final boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return getScrollingChildHelper().dispatchNestedPreScroll(i, i2, 0, iArr, iArr2);
    }

    @Override // android.view.View
    public final boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return getScrollingChildHelper().dispatchNestedScroll(i, i2, i3, i4, iArr);
    }

    @Override // android.view.View
    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected final void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected final void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        boolean z;
        super.draw(canvas);
        int size = this.mItemDecorations.size();
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            this.mItemDecorations.get(i).onDrawOver(canvas);
        }
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect == null || edgeEffect.isFinished()) {
            z = false;
        } else {
            int save = canvas.save();
            int paddingBottom = this.mClipToPadding ? getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((-getHeight()) + paddingBottom, DECELERATION_RATE);
            EdgeEffect edgeEffect2 = this.mLeftGlow;
            z = edgeEffect2 != null && edgeEffect2.draw(canvas);
            canvas.restoreToCount(save);
        }
        EdgeEffect edgeEffect3 = this.mTopGlow;
        if (edgeEffect3 != null && !edgeEffect3.isFinished()) {
            int save2 = canvas.save();
            if (this.mClipToPadding) {
                canvas.translate(getPaddingLeft(), getPaddingTop());
            }
            EdgeEffect edgeEffect4 = this.mTopGlow;
            z |= edgeEffect4 != null && edgeEffect4.draw(canvas);
            canvas.restoreToCount(save2);
        }
        EdgeEffect edgeEffect5 = this.mRightGlow;
        if (edgeEffect5 != null && !edgeEffect5.isFinished()) {
            int save3 = canvas.save();
            int width = getWidth();
            int paddingTop = this.mClipToPadding ? getPaddingTop() : 0;
            canvas.rotate(90.0f);
            canvas.translate(paddingTop, -width);
            EdgeEffect edgeEffect6 = this.mRightGlow;
            z |= edgeEffect6 != null && edgeEffect6.draw(canvas);
            canvas.restoreToCount(save3);
        }
        EdgeEffect edgeEffect7 = this.mBottomGlow;
        if (edgeEffect7 != null && !edgeEffect7.isFinished()) {
            int save4 = canvas.save();
            canvas.rotate(180.0f);
            if (this.mClipToPadding) {
                canvas.translate(getPaddingRight() + (-getWidth()), getPaddingBottom() + (-getHeight()));
            } else {
                canvas.translate(-getWidth(), -getHeight());
            }
            EdgeEffect edgeEffect8 = this.mBottomGlow;
            if (edgeEffect8 != null && edgeEffect8.draw(canvas)) {
                z2 = true;
            }
            z |= z2;
            canvas.restoreToCount(save4);
        }
        if ((z || this.mItemAnimator == null || this.mItemDecorations.size() <= 0 || !this.mItemAnimator.isRunning()) ? z : true) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        return super.drawChild(canvas, view, j);
    }

    final void ensureBottomGlow() {
        if (this.mBottomGlow != null) {
            return;
        }
        ((StretchEdgeEffectFactory) this.mEdgeEffectFactory).getClass();
        EdgeEffect edgeEffect = new EdgeEffect(getContext());
        this.mBottomGlow = edgeEffect;
        if (this.mClipToPadding) {
            edgeEffect.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
        } else {
            edgeEffect.setSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    final void ensureLeftGlow() {
        if (this.mLeftGlow != null) {
            return;
        }
        ((StretchEdgeEffectFactory) this.mEdgeEffectFactory).getClass();
        EdgeEffect edgeEffect = new EdgeEffect(getContext());
        this.mLeftGlow = edgeEffect;
        if (this.mClipToPadding) {
            edgeEffect.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
        } else {
            edgeEffect.setSize(getMeasuredHeight(), getMeasuredWidth());
        }
    }

    final void ensureRightGlow() {
        if (this.mRightGlow != null) {
            return;
        }
        ((StretchEdgeEffectFactory) this.mEdgeEffectFactory).getClass();
        EdgeEffect edgeEffect = new EdgeEffect(getContext());
        this.mRightGlow = edgeEffect;
        if (this.mClipToPadding) {
            edgeEffect.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
        } else {
            edgeEffect.setSize(getMeasuredHeight(), getMeasuredWidth());
        }
    }

    final void ensureTopGlow() {
        if (this.mTopGlow != null) {
            return;
        }
        ((StretchEdgeEffectFactory) this.mEdgeEffectFactory).getClass();
        EdgeEffect edgeEffect = new EdgeEffect(getContext());
        this.mTopGlow = edgeEffect;
        if (this.mClipToPadding) {
            edgeEffect.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
        } else {
            edgeEffect.setSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    final String exceptionLabel() {
        return " " + super.toString() + ", adapter:null, layout:" + this.mLayout + ", context:" + getContext();
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:?, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View findContainingItemView(View view) {
        ViewParent parent = view.getParent();
        while (parent != null && parent != this && (parent instanceof View)) {
            view = parent;
            parent = view.getParent();
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x00cb, code lost:
    
        if (r4 > 0) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00e9, code lost:
    
        if (r6 > 0) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00ec, code lost:
    
        if (r4 < 0) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00ef, code lost:
    
        if (r6 < 0) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00f7, code lost:
    
        if ((r6 * r3) <= 0) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00ff, code lost:
    
        if ((r6 * r3) >= 0) goto L87;
     */
    @Override // android.view.ViewGroup, android.view.ViewParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View focusSearch(View view, int i) {
        int i2;
        this.mLayout.getClass();
        View findNextFocus = FocusFinder.getInstance().findNextFocus(this, view, i);
        if (findNextFocus != null && !findNextFocus.hasFocusable()) {
            if (getFocusedChild() == null) {
                return super.focusSearch(view, i);
            }
            requestChildOnScreen(findNextFocus, null);
            return view;
        }
        boolean z = false;
        if (findNextFocus != null && findNextFocus != this && findNextFocus != view && findContainingItemView(findNextFocus) != null) {
            if (view != null && findContainingItemView(view) != null) {
                this.mTempRect.set(0, 0, view.getWidth(), view.getHeight());
                this.mTempRect2.set(0, 0, findNextFocus.getWidth(), findNextFocus.getHeight());
                offsetDescendantRectToMyCoords(view, this.mTempRect);
                offsetDescendantRectToMyCoords(findNextFocus, this.mTempRect2);
                char c = 65535;
                int i3 = ViewCompat.getLayoutDirection(this.mLayout.mRecyclerView) == 1 ? -1 : 1;
                Rect rect = this.mTempRect;
                int i4 = rect.left;
                Rect rect2 = this.mTempRect2;
                int i5 = rect2.left;
                if ((i4 < i5 || rect.right <= i5) && rect.right < rect2.right) {
                    i2 = 1;
                } else {
                    int i6 = rect.right;
                    int i7 = rect2.right;
                    i2 = ((i6 > i7 || i4 >= i7) && i4 > i5) ? -1 : 0;
                }
                int i8 = rect.top;
                int i9 = rect2.top;
                if ((i8 < i9 || rect.bottom <= i9) && rect.bottom < rect2.bottom) {
                    c = 1;
                } else {
                    int i10 = rect.bottom;
                    int i11 = rect2.bottom;
                    if ((i10 <= i11 && i8 < i11) || i8 <= i9) {
                        c = 0;
                    }
                }
                if (i != 1) {
                    if (i != 2) {
                        if (i != 17) {
                            if (i != 33) {
                                if (i != 66) {
                                    if (i != 130) {
                                        throw new IllegalArgumentException("Invalid direction: " + i + exceptionLabel());
                                    }
                                }
                            }
                        }
                    } else if (c <= 0) {
                        if (c == 0) {
                        }
                    }
                } else if (c >= 0) {
                    if (c == 0) {
                    }
                }
            }
            z = true;
        }
        return z ? findNextFocus : super.focusSearch(view, i);
    }

    @Override // android.view.ViewGroup
    protected final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.generateDefaultLayoutParams();
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + exceptionLabel());
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.generateLayoutParams(getContext(), attributeSet);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + exceptionLabel());
    }

    @Override // android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return "androidx.recyclerview.widget.RecyclerView";
    }

    public Adapter getAdapter() {
        return null;
    }

    final int getAdapterPositionInRecyclerView(ViewHolder viewHolder) {
        int i = viewHolder.mFlags;
        if (!((i & 524) != 0)) {
            if ((i & 1) != 0) {
                AdapterHelper adapterHelper = this.mAdapterHelper;
                int i2 = viewHolder.mPosition;
                ArrayList<AdapterHelper.UpdateOp> arrayList = adapterHelper.mPendingUpdates;
                int size = arrayList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    AdapterHelper.UpdateOp updateOp = arrayList.get(i3);
                    int i4 = updateOp.cmd;
                    if (i4 != 1) {
                        if (i4 == 2) {
                            int i5 = updateOp.positionStart;
                            if (i5 <= i2) {
                                int i6 = updateOp.itemCount;
                                if (i5 + i6 <= i2) {
                                    i2 -= i6;
                                }
                            } else {
                                continue;
                            }
                        } else if (i4 == 8) {
                            int i7 = updateOp.positionStart;
                            if (i7 == i2) {
                                i2 = updateOp.itemCount;
                            } else {
                                if (i7 < i2) {
                                    i2--;
                                }
                                if (updateOp.itemCount <= i2) {
                                    i2++;
                                }
                            }
                        }
                    } else if (updateOp.positionStart <= i2) {
                        i2 += updateOp.itemCount;
                    }
                }
                return i2;
            }
        }
        return -1;
    }

    @Override // android.view.View
    public int getBaseline() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            return super.getBaseline();
        }
        layoutManager.getClass();
        return -1;
    }

    @Override // android.view.ViewGroup
    protected final int getChildDrawingOrder(int i, int i2) {
        return super.getChildDrawingOrder(i, i2);
    }

    @Override // android.view.ViewGroup
    public boolean getClipToPadding() {
        return this.mClipToPadding;
    }

    public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate() {
        return this.mAccessibilityDelegate;
    }

    public EdgeEffectFactory getEdgeEffectFactory() {
        return this.mEdgeEffectFactory;
    }

    public ItemAnimator getItemAnimator() {
        return this.mItemAnimator;
    }

    public int getItemDecorationCount() {
        return this.mItemDecorations.size();
    }

    public LayoutManager getLayoutManager() {
        return this.mLayout;
    }

    public int getMaxFlingVelocity() {
        return this.mMaxFlingVelocity;
    }

    public int getMinFlingVelocity() {
        return this.mMinFlingVelocity;
    }

    long getNanoTime() {
        if (ALLOW_THREAD_GAP_WORK) {
            return System.nanoTime();
        }
        return 0L;
    }

    public OnFlingListener getOnFlingListener() {
        return null;
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.mPreserveFocusAfterLayout;
    }

    public RecycledViewPool getRecycledViewPool() {
        return this.mRecycler.getRecycledViewPool();
    }

    public int getScrollState() {
        return this.mScrollState;
    }

    @Override // android.view.View
    public final boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent(0);
    }

    final boolean isAccessibilityEnabled() {
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        return accessibilityManager != null && accessibilityManager.isEnabled();
    }

    @Override // android.view.View
    public final boolean isAttachedToWindow() {
        return this.mIsAttached;
    }

    public final boolean isComputingLayout() {
        return this.mLayoutOrScrollCounter > 0;
    }

    @Override // android.view.ViewGroup
    public final boolean isLayoutSuppressed() {
        return this.mLayoutSuppressed;
    }

    @Override // android.view.View
    public final boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    final void markItemDecorInsetsDirty() {
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < unfilteredChildCount; i++) {
            ((LayoutParams) this.mChildHelper.getUnfilteredChildAt(i).getLayoutParams()).mInsetsDirty = true;
        }
        ArrayList<ViewHolder> arrayList = this.mRecycler.mCachedViews;
        if (arrayList.size() <= 0) {
            return;
        }
        arrayList.get(0).getClass();
        throw null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x004c, code lost:
    
        if (r1 >= 30.0f) goto L21;
     */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected final void onAttachedToWindow() {
        float f;
        super.onAttachedToWindow();
        boolean z = false;
        this.mLayoutOrScrollCounter = 0;
        this.mIsAttached = true;
        if (this.mFirstLayoutComplete && !isLayoutRequested()) {
            z = true;
        }
        this.mFirstLayoutComplete = z;
        this.mRecycler.onAttachedToWindow();
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.mIsAttachedToWindow = true;
        }
        if (ALLOW_THREAD_GAP_WORK) {
            ThreadLocal<GapWorker> threadLocal = GapWorker.sGapWorker;
            GapWorker gapWorker = threadLocal.get();
            this.mGapWorker = gapWorker;
            if (gapWorker == null) {
                this.mGapWorker = new GapWorker();
                Display display = ViewCompat.getDisplay(this);
                if (!isInEditMode() && display != null) {
                    f = display.getRefreshRate();
                }
                f = 60.0f;
                GapWorker gapWorker2 = this.mGapWorker;
                gapWorker2.mFrameIntervalNs = (long) (1.0E9f / f);
                threadLocal.set(gapWorker2);
            }
            GapWorker gapWorker3 = this.mGapWorker;
            gapWorker3.getClass();
            if (sDebugAssertionsEnabled && gapWorker3.mRecyclerViews.contains(this)) {
                throw new IllegalStateException("RecyclerView already present in worker list!");
            }
            gapWorker3.mRecyclerViews.add(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected final void onDetachedFromWindow() {
        GapWorker gapWorker;
        super.onDetachedFromWindow();
        ItemAnimator itemAnimator = this.mItemAnimator;
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        setScrollState(0);
        ViewFlinger viewFlinger = this.mViewFlinger;
        RecyclerView.this.removeCallbacks(viewFlinger);
        viewFlinger.mOverScroller.abortAnimation();
        this.mIsAttached = false;
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.mIsAttachedToWindow = false;
            layoutManager.onDetachedFromWindow(this);
        }
        ((ArrayList) this.mPendingAccessibilityImportanceChange).clear();
        removeCallbacks(this.mItemAnimatorRunner);
        this.mViewInfoStore.getClass();
        while (ViewInfoStore.InfoRecord.sPool.acquire() != null) {
        }
        this.mRecycler.onDetachedFromWindow();
        PoolingContainer.callPoolingContainerOnReleaseForChildren(this);
        if (!ALLOW_THREAD_GAP_WORK || (gapWorker = this.mGapWorker) == null) {
            return;
        }
        boolean remove = gapWorker.mRecyclerViews.remove(this);
        if (sDebugAssertionsEnabled && !remove) {
            throw new IllegalStateException("RecyclerView removal failed!");
        }
        this.mGapWorker = null;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = this.mItemDecorations.size();
        for (int i = 0; i < size; i++) {
            this.mItemDecorations.get(i).getClass();
        }
    }

    final void onEnterLayoutOrScroll() {
        this.mLayoutOrScrollCounter++;
    }

    final void onExitLayoutOrScroll(boolean z) {
        int i = this.mLayoutOrScrollCounter - 1;
        this.mLayoutOrScrollCounter = i;
        if (i < 1) {
            if (sDebugAssertionsEnabled && i < 0) {
                throw new IllegalStateException("layout or scroll counter cannot go below zero.Some calls are not matching" + exceptionLabel());
            }
            this.mLayoutOrScrollCounter = 0;
            if (z) {
                int i2 = this.mEatenAccessibilityChangeFlags;
                this.mEatenAccessibilityChangeFlags = 0;
                if (i2 != 0 && isAccessibilityEnabled()) {
                    AccessibilityEvent obtain = AccessibilityEvent.obtain();
                    obtain.setEventType(2048);
                    obtain.setContentChangeTypes(i2);
                    sendAccessibilityEventUnchecked(obtain);
                }
                int size = this.mPendingAccessibilityImportanceChange.size() - 1;
                if (size < 0) {
                    this.mPendingAccessibilityImportanceChange.clear();
                } else {
                    this.mPendingAccessibilityImportanceChange.get(size).getClass();
                    throw null;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007f  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onGenericMotionEvent(MotionEvent motionEvent) {
        float f;
        float f2;
        LayoutManager layoutManager;
        if (this.mLayout != null && !this.mLayoutSuppressed && motionEvent.getAction() == 8) {
            if ((motionEvent.getSource() & 2) != 0) {
                f = this.mLayout.canScrollVertically() ? -motionEvent.getAxisValue(9) : 0.0f;
                if (this.mLayout.canScrollHorizontally()) {
                    f2 = motionEvent.getAxisValue(10);
                    if (f == DECELERATION_RATE || f2 != DECELERATION_RATE) {
                        int i = (int) (f2 * this.mScaledHorizontalScrollFactor);
                        int i2 = (int) (f * this.mScaledVerticalScrollFactor);
                        layoutManager = this.mLayout;
                        if (layoutManager == null) {
                            Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
                        } else if (!this.mLayoutSuppressed) {
                            int[] iArr = this.mReusableIntPair;
                            iArr[0] = 0;
                            iArr[1] = 0;
                            boolean canScrollHorizontally = layoutManager.canScrollHorizontally();
                            boolean canScrollVertically = this.mLayout.canScrollVertically();
                            int i3 = canScrollVertically ? (canScrollHorizontally ? 1 : 0) | 2 : canScrollHorizontally ? 1 : 0;
                            float y = motionEvent.getY();
                            float x = motionEvent.getX();
                            int releaseHorizontalGlow = i - releaseHorizontalGlow(i, y);
                            int releaseVerticalGlow = i2 - releaseVerticalGlow(i2, x);
                            getScrollingChildHelper().startNestedScroll(i3, 1);
                            if (dispatchNestedPreScroll(canScrollHorizontally ? releaseHorizontalGlow : 0, canScrollVertically ? releaseVerticalGlow : 0, 1, this.mReusableIntPair, this.mScrollOffset)) {
                                int[] iArr2 = this.mReusableIntPair;
                                releaseHorizontalGlow -= iArr2[0];
                                releaseVerticalGlow -= iArr2[1];
                            }
                            scrollByInternal(canScrollHorizontally ? releaseHorizontalGlow : 0, canScrollVertically ? releaseVerticalGlow : 0, motionEvent, 1);
                            GapWorker gapWorker = this.mGapWorker;
                            if (gapWorker != null && (releaseHorizontalGlow != 0 || releaseVerticalGlow != 0)) {
                                gapWorker.postFromTraversal(this, releaseHorizontalGlow, releaseVerticalGlow);
                            }
                            stopNestedScroll(1);
                        }
                    }
                }
                f2 = 0.0f;
                if (f == DECELERATION_RATE) {
                }
                int i4 = (int) (f2 * this.mScaledHorizontalScrollFactor);
                int i22 = (int) (f * this.mScaledVerticalScrollFactor);
                layoutManager = this.mLayout;
                if (layoutManager == null) {
                }
            } else {
                if ((motionEvent.getSource() & 4194304) != 0) {
                    f2 = motionEvent.getAxisValue(26);
                    if (this.mLayout.canScrollVertically()) {
                        f = -f2;
                        f2 = 0.0f;
                        if (f == DECELERATION_RATE) {
                        }
                        int i42 = (int) (f2 * this.mScaledHorizontalScrollFactor);
                        int i222 = (int) (f * this.mScaledVerticalScrollFactor);
                        layoutManager = this.mLayout;
                        if (layoutManager == null) {
                        }
                    } else if (this.mLayout.canScrollHorizontally()) {
                        f = 0.0f;
                        if (f == DECELERATION_RATE) {
                        }
                        int i422 = (int) (f2 * this.mScaledHorizontalScrollFactor);
                        int i2222 = (int) (f * this.mScaledVerticalScrollFactor);
                        layoutManager = this.mLayout;
                        if (layoutManager == null) {
                        }
                    }
                }
                f = 0.0f;
                f2 = 0.0f;
                if (f == DECELERATION_RATE) {
                }
                int i4222 = (int) (f2 * this.mScaledHorizontalScrollFactor);
                int i22222 = (int) (f * this.mScaledVerticalScrollFactor);
                layoutManager = this.mLayout;
                if (layoutManager == null) {
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        if (this.mLayoutSuppressed) {
            return false;
        }
        this.mInterceptingOnItemTouchListener = null;
        if (findInterceptingOnItemTouchListener(motionEvent)) {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
            stopNestedScroll(0);
            releaseGlows();
            setScrollState(0);
            return true;
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            return false;
        }
        boolean canScrollHorizontally = layoutManager.canScrollHorizontally();
        boolean canScrollVertically = this.mLayout.canScrollVertically();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            if (this.mIgnoreMotionEventTillDown) {
                this.mIgnoreMotionEventTillDown = false;
            }
            this.mScrollPointerId = motionEvent.getPointerId(0);
            int x = (int) (motionEvent.getX() + 0.5f);
            this.mLastTouchX = x;
            this.mInitialTouchX = x;
            int y = (int) (motionEvent.getY() + 0.5f);
            this.mLastTouchY = y;
            this.mInitialTouchY = y;
            EdgeEffect edgeEffect = this.mLeftGlow;
            if (edgeEffect == null || EdgeEffectCompat.getDistance(edgeEffect) == DECELERATION_RATE || canScrollHorizontally(-1)) {
                z = false;
            } else {
                EdgeEffectCompat.onPullDistance(this.mLeftGlow, DECELERATION_RATE, 1.0f - (motionEvent.getY() / getHeight()));
                z = true;
            }
            EdgeEffect edgeEffect2 = this.mRightGlow;
            boolean z3 = z;
            if (edgeEffect2 != null) {
                z3 = z;
                if (EdgeEffectCompat.getDistance(edgeEffect2) != DECELERATION_RATE) {
                    z3 = z;
                    if (!canScrollHorizontally(1)) {
                        EdgeEffectCompat.onPullDistance(this.mRightGlow, DECELERATION_RATE, motionEvent.getY() / getHeight());
                        z3 = true;
                    }
                }
            }
            EdgeEffect edgeEffect3 = this.mTopGlow;
            boolean z4 = z3;
            if (edgeEffect3 != null) {
                z4 = z3;
                if (EdgeEffectCompat.getDistance(edgeEffect3) != DECELERATION_RATE) {
                    z4 = z3;
                    if (!canScrollVertically(-1)) {
                        EdgeEffectCompat.onPullDistance(this.mTopGlow, DECELERATION_RATE, motionEvent.getX() / getWidth());
                        z4 = true;
                    }
                }
            }
            EdgeEffect edgeEffect4 = this.mBottomGlow;
            boolean z5 = z4;
            if (edgeEffect4 != null) {
                z5 = z4;
                if (EdgeEffectCompat.getDistance(edgeEffect4) != DECELERATION_RATE) {
                    z5 = z4;
                    if (!canScrollVertically(1)) {
                        EdgeEffectCompat.onPullDistance(this.mBottomGlow, DECELERATION_RATE, 1.0f - (motionEvent.getX() / getWidth()));
                        z5 = true;
                    }
                }
            }
            if (z5 || this.mScrollState == 2) {
                getParent().requestDisallowInterceptTouchEvent(true);
                setScrollState(1);
                stopNestedScroll(1);
            }
            int[] iArr = this.mNestedOffsets;
            iArr[1] = 0;
            iArr[0] = 0;
            int i = canScrollHorizontally;
            if (canScrollVertically) {
                i = (canScrollHorizontally ? 1 : 0) | 2;
            }
            getScrollingChildHelper().startNestedScroll(i, 0);
        } else if (actionMasked == 1) {
            this.mVelocityTracker.clear();
            stopNestedScroll(0);
        } else if (actionMasked == 2) {
            int findPointerIndex = motionEvent.findPointerIndex(this.mScrollPointerId);
            if (findPointerIndex < 0) {
                Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                return false;
            }
            int x2 = (int) (motionEvent.getX(findPointerIndex) + 0.5f);
            int y2 = (int) (motionEvent.getY(findPointerIndex) + 0.5f);
            if (this.mScrollState != 1) {
                int i2 = x2 - this.mInitialTouchX;
                int i3 = y2 - this.mInitialTouchY;
                if (canScrollHorizontally == 0 || Math.abs(i2) <= this.mTouchSlop) {
                    z2 = false;
                } else {
                    this.mLastTouchX = x2;
                    z2 = true;
                }
                if (canScrollVertically && Math.abs(i3) > this.mTouchSlop) {
                    this.mLastTouchY = y2;
                    z2 = true;
                }
                if (z2) {
                    setScrollState(1);
                }
            }
        } else if (actionMasked == 3) {
            VelocityTracker velocityTracker2 = this.mVelocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.clear();
            }
            stopNestedScroll(0);
            releaseGlows();
            setScrollState(0);
        } else if (actionMasked == 5) {
            this.mScrollPointerId = motionEvent.getPointerId(actionIndex);
            int x3 = (int) (motionEvent.getX(actionIndex) + 0.5f);
            this.mLastTouchX = x3;
            this.mInitialTouchX = x3;
            int y3 = (int) (motionEvent.getY(actionIndex) + 0.5f);
            this.mLastTouchY = y3;
            this.mInitialTouchY = y3;
        } else if (actionMasked == 6) {
            onPointerUp(motionEvent);
        }
        return this.mScrollState == 1;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Trace.beginSection("RV OnLayout");
        Log.w("RecyclerView", "No adapter attached; skipping layout");
        Trace.endSection();
        this.mFirstLayoutComplete = true;
    }

    @Override // android.view.View
    protected final void onMeasure(int i, int i2) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            defaultOnMeasure(i, i2);
            return;
        }
        if (layoutManager.isAutoMeasureEnabled()) {
            View.MeasureSpec.getMode(i);
            View.MeasureSpec.getMode(i2);
            this.mLayout.mRecyclerView.defaultOnMeasure(i, i2);
        } else {
            if (this.mHasFixedSize) {
                this.mLayout.mRecyclerView.defaultOnMeasure(i, i2);
                return;
            }
            State state = this.mState;
            if (state.mRunPredictiveAnimations) {
                setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
                return;
            }
            state.getClass();
            startInterceptRequestLayout();
            this.mLayout.mRecyclerView.defaultOnMeasure(i, i2);
            stopInterceptRequestLayout(false);
            this.mState.mInPreLayout = false;
        }
    }

    @Override // android.view.ViewGroup
    protected final boolean onRequestFocusInDescendants(int i, Rect rect) {
        if (isComputingLayout()) {
            return false;
        }
        return super.onRequestFocusInDescendants(i, rect);
    }

    @Override // android.view.View
    protected final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        this.mPendingSavedState = savedState;
        super.onRestoreInstanceState(savedState.getSuperState());
        requestLayout();
    }

    @Override // android.view.View
    protected final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState savedState2 = this.mPendingSavedState;
        if (savedState2 != null) {
            savedState.mLayoutState = savedState2.mLayoutState;
        } else {
            LayoutManager layoutManager = this.mLayout;
            if (layoutManager != null) {
                savedState.mLayoutState = layoutManager.onSaveInstanceState();
            } else {
                savedState.mLayoutState = null;
            }
        }
        return savedState;
    }

    @Override // android.view.View
    protected final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i == i3 && i2 == i4) {
            return;
        }
        this.mBottomGlow = null;
        this.mTopGlow = null;
        this.mRightGlow = null;
        this.mLeftGlow = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:113:0x030f, code lost:
    
        if (r0 != false) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x02ce, code lost:
    
        if (r2 == 0) goto L185;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0268  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x02ab A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x02ca A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x02d9  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x012b  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        int i;
        int i2;
        float f;
        float f2;
        boolean z2;
        boolean z3;
        boolean z4 = false;
        if (this.mLayoutSuppressed || this.mIgnoreMotionEventTillDown) {
            return false;
        }
        OnItemTouchListener onItemTouchListener = this.mInterceptingOnItemTouchListener;
        if (onItemTouchListener == null) {
            z = motionEvent.getAction() == 0 ? false : findInterceptingOnItemTouchListener(motionEvent);
        } else {
            onItemTouchListener.onTouchEvent(motionEvent);
            int action = motionEvent.getAction();
            if (action == 3 || action == 1) {
                this.mInterceptingOnItemTouchListener = null;
            }
            z = true;
        }
        if (z) {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
            stopNestedScroll(0);
            releaseGlows();
            setScrollState(0);
            return true;
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            return false;
        }
        boolean canScrollHorizontally = layoutManager.canScrollHorizontally();
        boolean canScrollVertically = this.mLayout.canScrollVertically();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            int[] iArr = this.mNestedOffsets;
            iArr[1] = 0;
            iArr[0] = 0;
        }
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        int[] iArr2 = this.mNestedOffsets;
        obtain.offsetLocation(iArr2[0], iArr2[1]);
        if (actionMasked == 0) {
            this.mScrollPointerId = motionEvent.getPointerId(0);
            int x = (int) (motionEvent.getX() + 0.5f);
            this.mLastTouchX = x;
            this.mInitialTouchX = x;
            int y = (int) (motionEvent.getY() + 0.5f);
            this.mLastTouchY = y;
            this.mInitialTouchY = y;
            int i3 = canScrollHorizontally;
            if (canScrollVertically) {
                i3 = (canScrollHorizontally ? 1 : 0) | 2;
            }
            getScrollingChildHelper().startNestedScroll(i3, 0);
        } else if (actionMasked == 1) {
            this.mVelocityTracker.addMovement(obtain);
            this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxFlingVelocity);
            float f3 = canScrollHorizontally != 0 ? -this.mVelocityTracker.getXVelocity(this.mScrollPointerId) : 0.0f;
            float f4 = canScrollVertically ? -this.mVelocityTracker.getYVelocity(this.mScrollPointerId) : 0.0f;
            if (f3 != DECELERATION_RATE || f4 != DECELERATION_RATE) {
                int i4 = (int) f3;
                int i5 = (int) f4;
                LayoutManager layoutManager2 = this.mLayout;
                if (layoutManager2 == null) {
                    Log.e("RecyclerView", "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
                } else if (!this.mLayoutSuppressed) {
                    boolean canScrollHorizontally2 = layoutManager2.canScrollHorizontally();
                    boolean canScrollVertically2 = this.mLayout.canScrollVertically();
                    if (canScrollHorizontally2 == 0 || Math.abs(i4) < this.mMinFlingVelocity) {
                        i4 = 0;
                    }
                    if (!canScrollVertically2 || Math.abs(i5) < this.mMinFlingVelocity) {
                        i5 = 0;
                    }
                    if (i4 != 0 || i5 != 0) {
                        if (i4 != 0) {
                            EdgeEffect edgeEffect = this.mLeftGlow;
                            if (edgeEffect == null || EdgeEffectCompat.getDistance(edgeEffect) == DECELERATION_RATE) {
                                EdgeEffect edgeEffect2 = this.mRightGlow;
                                if (edgeEffect2 != null && EdgeEffectCompat.getDistance(edgeEffect2) != DECELERATION_RATE) {
                                    if (shouldAbsorb(this.mRightGlow, i4, getWidth())) {
                                        this.mRightGlow.onAbsorb(i4);
                                        i4 = 0;
                                    }
                                    i = 0;
                                }
                            } else {
                                int i6 = -i4;
                                if (shouldAbsorb(this.mLeftGlow, i6, getWidth())) {
                                    this.mLeftGlow.onAbsorb(i6);
                                    i4 = 0;
                                }
                                i = 0;
                            }
                            if (i5 != 0) {
                                EdgeEffect edgeEffect3 = this.mTopGlow;
                                if (edgeEffect3 == null || EdgeEffectCompat.getDistance(edgeEffect3) == DECELERATION_RATE) {
                                    EdgeEffect edgeEffect4 = this.mBottomGlow;
                                    if (edgeEffect4 != null && EdgeEffectCompat.getDistance(edgeEffect4) != DECELERATION_RATE) {
                                        if (shouldAbsorb(this.mBottomGlow, i5, getHeight())) {
                                            this.mBottomGlow.onAbsorb(i5);
                                            i5 = 0;
                                        }
                                        i2 = 0;
                                    }
                                } else {
                                    int i7 = -i5;
                                    if (shouldAbsorb(this.mTopGlow, i7, getHeight())) {
                                        this.mTopGlow.onAbsorb(i7);
                                        i5 = 0;
                                    }
                                    i2 = 0;
                                }
                                if (i4 == 0 || i5 != 0) {
                                    int i8 = this.mMaxFlingVelocity;
                                    i4 = Math.max(-i8, Math.min(i4, i8));
                                    int i9 = this.mMaxFlingVelocity;
                                    i5 = Math.max(-i9, Math.min(i5, i9));
                                    this.mViewFlinger.fling(i4, i5);
                                }
                                if (i == 0 || i2 != 0) {
                                    f = i;
                                    f2 = i2;
                                    if (!dispatchNestedPreFling(f, f2)) {
                                        boolean z5 = canScrollHorizontally2 != 0 || canScrollVertically2;
                                        dispatchNestedFling(f, f2, z5);
                                        int i10 = canScrollHorizontally2;
                                        if (z5) {
                                            if (canScrollVertically2) {
                                                i10 = (canScrollHorizontally2 ? 1 : 0) | 2;
                                            }
                                            getScrollingChildHelper().startNestedScroll(i10, 1);
                                            int i11 = this.mMaxFlingVelocity;
                                            int max = Math.max(-i11, Math.min(i, i11));
                                            int i12 = this.mMaxFlingVelocity;
                                            this.mViewFlinger.fling(max, Math.max(-i12, Math.min(i2, i12)));
                                            z2 = true;
                                        }
                                    }
                                } else {
                                    if (i4 == 0) {
                                    }
                                    z2 = true;
                                }
                            }
                            i2 = i5;
                            i5 = 0;
                            if (i4 == 0) {
                            }
                            int i82 = this.mMaxFlingVelocity;
                            i4 = Math.max(-i82, Math.min(i4, i82));
                            int i92 = this.mMaxFlingVelocity;
                            i5 = Math.max(-i92, Math.min(i5, i92));
                            this.mViewFlinger.fling(i4, i5);
                            if (i == 0) {
                            }
                            f = i;
                            f2 = i2;
                            if (!dispatchNestedPreFling(f, f2)) {
                            }
                        }
                        i = i4;
                        i4 = 0;
                        if (i5 != 0) {
                        }
                        i2 = i5;
                        i5 = 0;
                        if (i4 == 0) {
                        }
                        int i822 = this.mMaxFlingVelocity;
                        i4 = Math.max(-i822, Math.min(i4, i822));
                        int i922 = this.mMaxFlingVelocity;
                        i5 = Math.max(-i922, Math.min(i5, i922));
                        this.mViewFlinger.fling(i4, i5);
                        if (i == 0) {
                        }
                        f = i;
                        f2 = i2;
                        if (!dispatchNestedPreFling(f, f2)) {
                        }
                    }
                }
                z2 = false;
            }
            setScrollState(0);
            VelocityTracker velocityTracker2 = this.mVelocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.clear();
            }
            stopNestedScroll(0);
            releaseGlows();
            z4 = true;
        } else if (actionMasked == 2) {
            int findPointerIndex = motionEvent.findPointerIndex(this.mScrollPointerId);
            if (findPointerIndex < 0) {
                Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                return false;
            }
            int x2 = (int) (motionEvent.getX(findPointerIndex) + 0.5f);
            int y2 = (int) (motionEvent.getY(findPointerIndex) + 0.5f);
            int i13 = this.mLastTouchX - x2;
            int i14 = this.mLastTouchY - y2;
            if (this.mScrollState != 1) {
                if (canScrollHorizontally != 0) {
                    i13 = i13 > 0 ? Math.max(0, i13 - this.mTouchSlop) : Math.min(0, i13 + this.mTouchSlop);
                    if (i13 != 0) {
                        z3 = true;
                        if (canScrollVertically) {
                            i14 = i14 > 0 ? Math.max(0, i14 - this.mTouchSlop) : Math.min(0, i14 + this.mTouchSlop);
                            if (i14 != 0) {
                                z3 = true;
                            }
                        }
                        if (z3) {
                            setScrollState(1);
                        }
                    }
                }
                z3 = false;
                if (canScrollVertically) {
                }
                if (z3) {
                }
            }
            if (this.mScrollState == 1) {
                int[] iArr3 = this.mReusableIntPair;
                iArr3[0] = 0;
                iArr3[1] = 0;
                int releaseHorizontalGlow = i13 - releaseHorizontalGlow(i13, motionEvent.getY());
                int releaseVerticalGlow = i14 - releaseVerticalGlow(i14, motionEvent.getX());
                if (dispatchNestedPreScroll(canScrollHorizontally != 0 ? releaseHorizontalGlow : 0, canScrollVertically ? releaseVerticalGlow : 0, 0, this.mReusableIntPair, this.mScrollOffset)) {
                    int[] iArr4 = this.mReusableIntPair;
                    releaseHorizontalGlow -= iArr4[0];
                    releaseVerticalGlow -= iArr4[1];
                    int[] iArr5 = this.mNestedOffsets;
                    int i15 = iArr5[0];
                    int[] iArr6 = this.mScrollOffset;
                    iArr5[0] = i15 + iArr6[0];
                    iArr5[1] = iArr5[1] + iArr6[1];
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                int i16 = releaseVerticalGlow;
                int[] iArr7 = this.mScrollOffset;
                this.mLastTouchX = x2 - iArr7[0];
                this.mLastTouchY = y2 - iArr7[1];
                if (scrollByInternal(canScrollHorizontally != 0 ? releaseHorizontalGlow : 0, canScrollVertically ? i16 : 0, motionEvent, 0)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                GapWorker gapWorker = this.mGapWorker;
                if (gapWorker != null && (releaseHorizontalGlow != 0 || i16 != 0)) {
                    gapWorker.postFromTraversal(this, releaseHorizontalGlow, i16);
                }
            }
        } else if (actionMasked == 3) {
            VelocityTracker velocityTracker3 = this.mVelocityTracker;
            if (velocityTracker3 != null) {
                velocityTracker3.clear();
            }
            stopNestedScroll(0);
            releaseGlows();
            setScrollState(0);
        } else if (actionMasked == 5) {
            this.mScrollPointerId = motionEvent.getPointerId(actionIndex);
            int x3 = (int) (motionEvent.getX(actionIndex) + 0.5f);
            this.mLastTouchX = x3;
            this.mInitialTouchX = x3;
            int y3 = (int) (motionEvent.getY(actionIndex) + 0.5f);
            this.mLastTouchY = y3;
            this.mInitialTouchY = y3;
        } else if (actionMasked == 6) {
            onPointerUp(motionEvent);
        }
        if (!z4) {
            this.mVelocityTracker.addMovement(obtain);
        }
        obtain.recycle();
        return true;
    }

    @Override // android.view.ViewGroup
    protected final void removeDetachedView(View view, boolean z) {
        getChildViewHolderInt(view);
        if (sDebugAssertionsEnabled) {
            throw new IllegalArgumentException("No ViewHolder found for child: " + view + exceptionLabel());
        }
        view.clearAnimation();
        getChildViewHolderInt(view);
        super.removeDetachedView(view, z);
    }

    public final void removeOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.remove(onItemTouchListener);
        if (this.mInterceptingOnItemTouchListener == onItemTouchListener) {
            this.mInterceptingOnItemTouchListener = null;
        }
    }

    public final void removeOnScrollListener(OnScrollListener onScrollListener) {
        List<OnScrollListener> list = this.mScrollListeners;
        if (list != null) {
            ((ArrayList) list).remove(onScrollListener);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestChildFocus(View view, View view2) {
        this.mLayout.getClass();
        if (!isComputingLayout() && view2 != null) {
            requestChildOnScreen(view, view2);
        }
        super.requestChildFocus(view, view2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        return this.mLayout.requestChildRectangleOnScreen(this, view, rect, z, false);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z) {
        int size = this.mOnItemTouchListeners.size();
        for (int i = 0; i < size; i++) {
            this.mOnItemTouchListeners.get(i).onRequestDisallowInterceptTouchEvent();
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    @Override // android.view.View, android.view.ViewParent
    public final void requestLayout() {
        if (this.mInterceptRequestLayoutDepth != 0 || this.mLayoutSuppressed) {
            this.mLayoutWasDefered = true;
        } else {
            super.requestLayout();
        }
    }

    @Override // android.view.View
    public final void scrollBy(int i, int i2) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.mLayoutSuppressed) {
            return;
        }
        boolean canScrollHorizontally = layoutManager.canScrollHorizontally();
        boolean canScrollVertically = this.mLayout.canScrollVertically();
        if (canScrollHorizontally || canScrollVertically) {
            if (!canScrollHorizontally) {
                i = 0;
            }
            if (!canScrollVertically) {
                i2 = 0;
            }
            scrollByInternal(i, i2, null, 0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00bc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final boolean scrollByInternal(int i, int i2, MotionEvent motionEvent, int i3) {
        consumePendingUpdateOperations();
        if (!this.mItemDecorations.isEmpty()) {
            invalidate();
        }
        int[] iArr = this.mReusableIntPair;
        boolean z = false;
        iArr[0] = 0;
        boolean z2 = true;
        iArr[1] = 0;
        dispatchNestedScroll(0, 0, 0, this.mScrollOffset, i3, iArr);
        int[] iArr2 = this.mReusableIntPair;
        int i4 = iArr2[0];
        int i5 = 0 - i4;
        int i6 = iArr2[1];
        int i7 = 0 - i6;
        boolean z3 = (i4 == 0 && i6 == 0) ? false : true;
        int i8 = this.mLastTouchX;
        int[] iArr3 = this.mScrollOffset;
        int i9 = iArr3[0];
        this.mLastTouchX = i8 - i9;
        int i10 = this.mLastTouchY;
        int i11 = iArr3[1];
        this.mLastTouchY = i10 - i11;
        int[] iArr4 = this.mNestedOffsets;
        iArr4[0] = iArr4[0] + i9;
        iArr4[1] = iArr4[1] + i11;
        if (getOverScrollMode() != 2) {
            if (motionEvent != null && !MotionEventCompat.isFromSource(motionEvent, 8194)) {
                float x = motionEvent.getX();
                float f = i5;
                float y = motionEvent.getY();
                float f2 = i7;
                if (f < DECELERATION_RATE) {
                    ensureLeftGlow();
                    EdgeEffectCompat.onPullDistance(this.mLeftGlow, (-f) / getWidth(), 1.0f - (y / getHeight()));
                } else {
                    if (f > DECELERATION_RATE) {
                        ensureRightGlow();
                        EdgeEffectCompat.onPullDistance(this.mRightGlow, f / getWidth(), y / getHeight());
                    }
                    if (f2 >= DECELERATION_RATE) {
                        ensureTopGlow();
                        EdgeEffectCompat.onPullDistance(this.mTopGlow, (-f2) / getHeight(), x / getWidth());
                    } else if (f2 > DECELERATION_RATE) {
                        ensureBottomGlow();
                        EdgeEffectCompat.onPullDistance(this.mBottomGlow, f2 / getHeight(), 1.0f - (x / getWidth()));
                    } else {
                        z2 = z;
                    }
                    if (!z2 || f != DECELERATION_RATE || f2 != DECELERATION_RATE) {
                        ViewCompat.postInvalidateOnAnimation(this);
                    }
                    if (MotionEventCompat.isFromSource(motionEvent, 4194304)) {
                        releaseGlows();
                    }
                }
                z = true;
                if (f2 >= DECELERATION_RATE) {
                }
                if (!z2) {
                }
                ViewCompat.postInvalidateOnAnimation(this);
                if (MotionEventCompat.isFromSource(motionEvent, 4194304)) {
                }
            }
            considerReleasingGlowsOnScroll(i, i2);
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        return z3;
    }

    @Override // android.view.View
    public final void scrollTo(int i, int i2) {
        Log.w("RecyclerView", "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    @Override // android.view.View, android.view.accessibility.AccessibilityEventSource
    public final void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (isComputingLayout()) {
            int contentChangeTypes = accessibilityEvent != null ? accessibilityEvent.getContentChangeTypes() : 0;
            this.mEatenAccessibilityChangeFlags |= contentChangeTypes != 0 ? contentChangeTypes : 0;
            r1 = 1;
        }
        if (r1 != 0) {
            return;
        }
        super.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate) {
        this.mAccessibilityDelegate = recyclerViewAccessibilityDelegate;
        ViewCompat.setAccessibilityDelegate(this, recyclerViewAccessibilityDelegate);
    }

    public void setAdapter(Adapter adapter) {
        setLayoutFrozen(false);
        ItemAnimator itemAnimator = this.mItemAnimator;
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.removeAndRecycleAllViews(this.mRecycler);
            this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        }
        Recycler recycler = this.mRecycler;
        recycler.mAttachedScrap.clear();
        recycler.recycleAndClearCachedViews();
        this.mAdapterHelper.reset();
        LayoutManager layoutManager2 = this.mLayout;
        if (layoutManager2 != null) {
            layoutManager2.onAdapterChanged();
        }
        this.mRecycler.onAdapterChanged();
        this.mState.mStructureChanged = true;
        this.mDispatchItemsChangedEvent |= false;
        this.mDataSetHasChangedAfterLayout = true;
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < unfilteredChildCount; i++) {
            getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
        }
        markItemDecorInsetsDirty();
        Recycler recycler2 = this.mRecycler;
        ArrayList<ViewHolder> arrayList = recycler2.mCachedViews;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ViewHolder viewHolder = arrayList.get(i2);
            if (viewHolder != null) {
                viewHolder.addFlags(6);
                viewHolder.addChangePayload(null);
            }
        }
        RecyclerView.this.getClass();
        recycler2.recycleAndClearCachedViews();
        requestLayout();
    }

    public void setChildDrawingOrderCallback(ChildDrawingOrderCallback childDrawingOrderCallback) {
        if (childDrawingOrderCallback == null) {
            return;
        }
        setChildrenDrawingOrderEnabled(false);
    }

    @Override // android.view.ViewGroup
    public void setClipToPadding(boolean z) {
        if (z != this.mClipToPadding) {
            this.mBottomGlow = null;
            this.mTopGlow = null;
            this.mRightGlow = null;
            this.mLeftGlow = null;
        }
        this.mClipToPadding = z;
        super.setClipToPadding(z);
        if (this.mFirstLayoutComplete) {
            requestLayout();
        }
    }

    public void setEdgeEffectFactory(EdgeEffectFactory edgeEffectFactory) {
        edgeEffectFactory.getClass();
        this.mEdgeEffectFactory = edgeEffectFactory;
        this.mBottomGlow = null;
        this.mTopGlow = null;
        this.mRightGlow = null;
        this.mLeftGlow = null;
    }

    public void setHasFixedSize(boolean z) {
        this.mHasFixedSize = z;
    }

    public void setItemAnimator(ItemAnimator itemAnimator) {
        ItemAnimator itemAnimator2 = this.mItemAnimator;
        if (itemAnimator2 != null) {
            itemAnimator2.endAnimations();
            this.mItemAnimator.setListener(null);
        }
        this.mItemAnimator = itemAnimator;
        if (itemAnimator != null) {
            itemAnimator.setListener(this.mItemAnimatorListener);
        }
    }

    public void setItemViewCacheSize(int i) {
        this.mRecycler.setViewCacheSize(i);
    }

    @Deprecated
    public void setLayoutFrozen(boolean z) {
        suppressLayout(z);
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        ChildHelper.Callback callback;
        RecyclerView recyclerView;
        if (layoutManager == this.mLayout) {
            return;
        }
        int i = 0;
        setScrollState(0);
        ViewFlinger viewFlinger = this.mViewFlinger;
        RecyclerView.this.removeCallbacks(viewFlinger);
        viewFlinger.mOverScroller.abortAnimation();
        if (this.mLayout != null) {
            ItemAnimator itemAnimator = this.mItemAnimator;
            if (itemAnimator != null) {
                itemAnimator.endAnimations();
            }
            this.mLayout.removeAndRecycleAllViews(this.mRecycler);
            this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
            Recycler recycler = this.mRecycler;
            recycler.mAttachedScrap.clear();
            recycler.recycleAndClearCachedViews();
            if (this.mIsAttached) {
                LayoutManager layoutManager2 = this.mLayout;
                layoutManager2.mIsAttachedToWindow = false;
                layoutManager2.onDetachedFromWindow(this);
            }
            this.mLayout.setRecyclerView(null);
            this.mLayout = null;
        } else {
            Recycler recycler2 = this.mRecycler;
            recycler2.mAttachedScrap.clear();
            recycler2.recycleAndClearCachedViews();
        }
        ChildHelper childHelper = this.mChildHelper;
        childHelper.mBucket.reset();
        ArrayList arrayList = (ArrayList) childHelper.mHiddenViews;
        int size = arrayList.size();
        while (true) {
            size--;
            callback = childHelper.mCallback;
            if (size < 0) {
                break;
            }
            View view = (View) arrayList.get(size);
            ((C01635) callback).getClass();
            getChildViewHolderInt(view);
            arrayList.remove(size);
        }
        C01635 c01635 = (C01635) callback;
        int childCount = c01635.getChildCount();
        while (true) {
            recyclerView = RecyclerView.this;
            if (i >= childCount) {
                break;
            }
            View childAt = recyclerView.getChildAt(i);
            recyclerView.getClass();
            getChildViewHolderInt(childAt);
            childAt.clearAnimation();
            i++;
        }
        recyclerView.removeAllViews();
        this.mLayout = layoutManager;
        if (layoutManager != null) {
            if (layoutManager.mRecyclerView != null) {
                throw new IllegalArgumentException("LayoutManager " + layoutManager + " is already attached to a RecyclerView:" + layoutManager.mRecyclerView.exceptionLabel());
            }
            layoutManager.setRecyclerView(this);
            if (this.mIsAttached) {
                this.mLayout.mIsAttachedToWindow = true;
            }
        }
        this.mRecycler.updateViewCacheSize();
        requestLayout();
    }

    @Override // android.view.ViewGroup
    @Deprecated
    public void setLayoutTransition(LayoutTransition layoutTransition) {
        if (layoutTransition != null) {
            throw new IllegalArgumentException("Providing a LayoutTransition into RecyclerView is not supported. Please use setItemAnimator() instead for animating changes to the items in this RecyclerView");
        }
        super.setLayoutTransition(null);
    }

    @Override // android.view.View
    public void setNestedScrollingEnabled(boolean z) {
        getScrollingChildHelper().setNestedScrollingEnabled(z);
    }

    @Deprecated
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mScrollListener = onScrollListener;
    }

    public void setPreserveFocusAfterLayout(boolean z) {
        this.mPreserveFocusAfterLayout = z;
    }

    public void setRecycledViewPool(RecycledViewPool recycledViewPool) {
        this.mRecycler.setRecycledViewPool(recycledViewPool);
    }

    @Deprecated
    public void setRecyclerListener(RecyclerListener recyclerListener) {
        this.mRecyclerListener = recyclerListener;
    }

    void setScrollState(int i) {
        if (i == this.mScrollState) {
            return;
        }
        if (sVerboseLoggingEnabled) {
            Log.d("RecyclerView", "setting scroll state to " + i + " from " + this.mScrollState, new Exception());
        }
        this.mScrollState = i;
        if (i != 2) {
            ViewFlinger viewFlinger = this.mViewFlinger;
            RecyclerView.this.removeCallbacks(viewFlinger);
            viewFlinger.mOverScroller.abortAnimation();
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.onScrollStateChanged(i);
        }
        List<OnScrollListener> list = this.mScrollListeners;
        if (list == null) {
            return;
        }
        int size = ((ArrayList) list).size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            } else {
                ((OnScrollListener) ((ArrayList) this.mScrollListeners).get(size)).getClass();
            }
        }
    }

    public void setScrollingTouchSlop(int i) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        if (i != 0) {
            if (i == 1) {
                this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
                return;
            }
            Log.w("RecyclerView", "setScrollingTouchSlop(): bad argument constant " + i + "; using default value");
        }
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
    }

    public void setViewCacheExtension(ViewCacheExtension viewCacheExtension) {
        this.mRecycler.getClass();
    }

    final void smoothScrollBy$1(int i, int i2, boolean z) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.mLayoutSuppressed) {
            return;
        }
        if (!layoutManager.canScrollHorizontally()) {
            i = 0;
        }
        if (!this.mLayout.canScrollVertically()) {
            i2 = 0;
        }
        if (i == 0 && i2 == 0) {
            return;
        }
        if (z) {
            int i3 = i != 0 ? 1 : 0;
            if (i2 != 0) {
                i3 |= 2;
            }
            getScrollingChildHelper().startNestedScroll(i3, 1);
        }
        this.mViewFlinger.smoothScrollBy(i, i2);
    }

    final void startInterceptRequestLayout() {
        int i = this.mInterceptRequestLayoutDepth + 1;
        this.mInterceptRequestLayoutDepth = i;
        if (i != 1 || this.mLayoutSuppressed) {
            return;
        }
        this.mLayoutWasDefered = false;
    }

    @Override // android.view.View
    public final boolean startNestedScroll(int i) {
        return getScrollingChildHelper().startNestedScroll(i, 0);
    }

    final void stopInterceptRequestLayout(boolean z) {
        if (this.mInterceptRequestLayoutDepth < 1) {
            if (sDebugAssertionsEnabled) {
                throw new IllegalStateException("stopInterceptRequestLayout was called more times than startInterceptRequestLayout." + exceptionLabel());
            }
            this.mInterceptRequestLayoutDepth = 1;
        }
        if (!z && !this.mLayoutSuppressed) {
            this.mLayoutWasDefered = false;
        }
        int i = this.mInterceptRequestLayoutDepth;
        if (i == 1) {
            if (z && this.mLayoutWasDefered && !this.mLayoutSuppressed) {
                LayoutManager layoutManager = this.mLayout;
            }
            if (!this.mLayoutSuppressed) {
                this.mLayoutWasDefered = false;
            }
        }
        this.mInterceptRequestLayoutDepth = i - 1;
    }

    @Override // android.view.View
    public final void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll(0);
    }

    @Override // android.view.ViewGroup
    public final void suppressLayout(boolean z) {
        if (z != this.mLayoutSuppressed) {
            assertNotInLayoutOrScroll("Do not suppressLayout in layout or scroll");
            if (!z) {
                this.mLayoutSuppressed = false;
                this.mLayoutWasDefered = false;
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            onTouchEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, DECELERATION_RATE, DECELERATION_RATE, 0));
            this.mLayoutSuppressed = true;
            this.mIgnoreMotionEventTillDown = true;
            setScrollState(0);
            ViewFlinger viewFlinger = this.mViewFlinger;
            RecyclerView.this.removeCallbacks(viewFlinger);
            viewFlinger.mOverScroller.abortAnimation();
        }
    }

    public RecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.samsung.android.biometrics.app.setting.R.attr.recyclerViewStyle);
    }

    public final void dispatchNestedScroll(int i, int i2, int i3, int[] iArr, int i4, int[] iArr2) {
        getScrollingChildHelper().dispatchNestedScroll(0, i2, i3, iArr, i4, iArr2);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        final Rect mDecorInsets;
        boolean mInsetsDirty;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mDecorInsets = new Rect();
            this.mInsetsDirty = true;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.mDecorInsets = new Rect();
            this.mInsetsDirty = true;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.mDecorInsets = new Rect();
            this.mInsetsDirty = true;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mDecorInsets = new Rect();
            this.mInsetsDirty = true;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams) layoutParams);
            this.mDecorInsets = new Rect();
            this.mInsetsDirty = true;
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new C01671();
        Parcelable mLayoutState;

        /* renamed from: androidx.recyclerview.widget.RecyclerView$SavedState$1 */
        final class C01671 implements Parcelable.ClassLoaderCreator<SavedState> {
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
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
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.mLayoutState = parcel.readParcelable(classLoader == null ? LayoutManager.class.getClassLoader() : classLoader);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.mLayoutState, 0);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    /* JADX WARN: Type inference failed for: r1v12, types: [androidx.recyclerview.widget.RecyclerView$4] */
    /* JADX WARN: Type inference failed for: r2v8, types: [androidx.recyclerview.widget.RecyclerView$6] */
    public RecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int i2;
        TypedArray typedArray;
        char c;
        ClassLoader classLoader;
        Constructor constructor;
        Object[] objArr;
        this.mRecycler = new Recycler();
        this.mViewInfoStore = new ViewInfoStore();
        this.mTempRect = new Rect();
        this.mTempRect2 = new Rect();
        this.mTempRectF = new RectF();
        this.mRecyclerListeners = new ArrayList();
        this.mItemDecorations = new ArrayList<>();
        this.mOnItemTouchListeners = new ArrayList<>();
        this.mInterceptRequestLayoutDepth = 0;
        this.mDataSetHasChangedAfterLayout = false;
        this.mDispatchItemsChangedEvent = false;
        this.mLayoutOrScrollCounter = 0;
        this.mDispatchScrollCounter = 0;
        this.mEdgeEffectFactory = sDefaultEdgeEffectFactory;
        this.mItemAnimator = new DefaultItemAnimator();
        this.mScrollState = 0;
        this.mScrollPointerId = -1;
        this.mScaledHorizontalScrollFactor = Float.MIN_VALUE;
        this.mScaledVerticalScrollFactor = Float.MIN_VALUE;
        this.mPreserveFocusAfterLayout = true;
        this.mViewFlinger = new ViewFlinger();
        this.mPrefetchRegistry = ALLOW_THREAD_GAP_WORK ? new GapWorker.LayoutPrefetchRegistryImpl() : null;
        this.mState = new State();
        this.mItemsAddedOrRemoved = false;
        this.mItemsChanged = false;
        this.mItemAnimatorListener = new ItemAnimatorRestoreListener();
        this.mScrollOffset = new int[2];
        this.mNestedOffsets = new int[2];
        this.mReusableIntPair = new int[2];
        this.mPendingAccessibilityImportanceChange = new ArrayList();
        this.mItemAnimatorRunner = new Runnable() { // from class: androidx.recyclerview.widget.RecyclerView.2
            @Override // java.lang.Runnable
            public final void run() {
                ItemAnimator itemAnimator = RecyclerView.this.mItemAnimator;
                if (itemAnimator != null) {
                    itemAnimator.runPendingAnimations();
                }
                RecyclerView.this.getClass();
            }
        };
        this.mViewInfoProcessCallback = new Object() { // from class: androidx.recyclerview.widget.RecyclerView.4
        };
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mScaledHorizontalScrollFactor = viewConfiguration.getScaledHorizontalScrollFactor();
        this.mScaledVerticalScrollFactor = viewConfiguration.getScaledVerticalScrollFactor();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mPhysicalCoef = context.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
        setWillNotDraw(getOverScrollMode() == 2);
        this.mItemAnimator.setListener(this.mItemAnimatorListener);
        this.mAdapterHelper = new AdapterHelper(new AdapterHelper.Callback() { // from class: androidx.recyclerview.widget.RecyclerView.6
        });
        this.mChildHelper = new ChildHelper(new C01635());
        if (ViewCompat.getImportantForAutofill(this) == 0) {
            ViewCompat.setImportantForAutofill(this);
        }
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        this.mAccessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this));
        int[] iArr = R$styleable.RecyclerView;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, i, 0);
        String string = obtainStyledAttributes.getString(8);
        if (obtainStyledAttributes.getInt(2, -1) == -1) {
            setDescendantFocusability(262144);
        }
        this.mClipToPadding = obtainStyledAttributes.getBoolean(1, true);
        if (obtainStyledAttributes.getBoolean(3, false)) {
            StateListDrawable stateListDrawable = (StateListDrawable) obtainStyledAttributes.getDrawable(6);
            Drawable drawable = obtainStyledAttributes.getDrawable(7);
            StateListDrawable stateListDrawable2 = (StateListDrawable) obtainStyledAttributes.getDrawable(4);
            Drawable drawable2 = obtainStyledAttributes.getDrawable(5);
            if (stateListDrawable != null && drawable != null && stateListDrawable2 != null && drawable2 != null) {
                Resources resources = getContext().getResources();
                i2 = 4;
                typedArray = obtainStyledAttributes;
                c = 2;
                new FastScroller(this, stateListDrawable, drawable, stateListDrawable2, drawable2, resources.getDimensionPixelSize(com.samsung.android.biometrics.app.setting.R.dimen.fastscroll_default_thickness), resources.getDimensionPixelSize(com.samsung.android.biometrics.app.setting.R.dimen.fastscroll_minimum_range), resources.getDimensionPixelOffset(com.samsung.android.biometrics.app.setting.R.dimen.fastscroll_margin));
            } else {
                throw new IllegalArgumentException("Trying to set fast scroller without both required drawables." + exceptionLabel());
            }
        } else {
            i2 = 4;
            typedArray = obtainStyledAttributes;
            c = 2;
        }
        typedArray.recycle();
        if (string != null) {
            String trim = string.trim();
            if (!trim.isEmpty()) {
                if (trim.charAt(0) == '.') {
                    trim = context.getPackageName() + trim;
                } else if (!trim.contains(".")) {
                    trim = RecyclerView.class.getPackage().getName() + '.' + trim;
                }
                String str = trim;
                try {
                    if (isInEditMode()) {
                        classLoader = getClass().getClassLoader();
                    } else {
                        classLoader = context.getClassLoader();
                    }
                    Class<? extends U> asSubclass = Class.forName(str, false, classLoader).asSubclass(LayoutManager.class);
                    try {
                        constructor = asSubclass.getConstructor(LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
                        objArr = new Object[i2];
                        objArr[0] = context;
                        objArr[1] = attributeSet;
                        objArr[c] = Integer.valueOf(i);
                        objArr[3] = 0;
                    } catch (NoSuchMethodException e) {
                        try {
                            constructor = asSubclass.getConstructor(new Class[0]);
                            objArr = null;
                        } catch (NoSuchMethodException e2) {
                            e2.initCause(e);
                            throw new IllegalStateException(attributeSet.getPositionDescription() + ": Error creating LayoutManager " + str, e2);
                        }
                    }
                    constructor.setAccessible(true);
                    setLayoutManager((LayoutManager) constructor.newInstance(objArr));
                } catch (ClassCastException e3) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Class is not a LayoutManager " + str, e3);
                } catch (ClassNotFoundException e4) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Unable to find LayoutManager " + str, e4);
                } catch (IllegalAccessException e5) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Cannot access non-public constructor " + str, e5);
                } catch (InstantiationException e6) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + str, e6);
                } catch (InvocationTargetException e7) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + str, e7);
                }
            }
        }
        int[] iArr2 = NESTED_SCROLLING_ATTRS;
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, iArr2, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, iArr2, attributeSet, obtainStyledAttributes2, i, 0);
        boolean z = obtainStyledAttributes2.getBoolean(0, true);
        obtainStyledAttributes2.recycle();
        setNestedScrollingEnabled(z);
        setTag(com.samsung.android.biometrics.app.setting.R.id.is_pooling_container_tag, Boolean.TRUE);
    }

    public final boolean dispatchNestedPreScroll(int i, int i2, int i3, int[] iArr, int[] iArr2) {
        return getScrollingChildHelper().dispatchNestedPreScroll(i, i2, i3, iArr, iArr2);
    }

    public final void stopNestedScroll(int i) {
        getScrollingChildHelper().stopNestedScroll(i);
    }

    @Override // android.view.ViewGroup
    protected final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.generateLayoutParams(layoutParams);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + exceptionLabel());
    }

    public static abstract class LayoutManager {
        ChildHelper mChildHelper;
        private int mHeight;
        private int mHeightMode;
        ViewBoundsCheck mHorizontalBoundCheck;
        private final C01651 mHorizontalBoundCheckCallback;
        boolean mIsAttachedToWindow;
        RecyclerView mRecyclerView;
        boolean mRequestedSimpleAnimations;
        ViewBoundsCheck mVerticalBoundCheck;
        private final C01662 mVerticalBoundCheckCallback;
        private int mWidth;
        private int mWidthMode;

        public static class Properties {
            public int orientation;
            public boolean reverseLayout;
            public int spanCount;
            public boolean stackFromEnd;
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [androidx.recyclerview.widget.RecyclerView$LayoutManager$1, androidx.recyclerview.widget.ViewBoundsCheck$Callback] */
        /* JADX WARN: Type inference failed for: r1v0, types: [androidx.recyclerview.widget.RecyclerView$LayoutManager$2, androidx.recyclerview.widget.ViewBoundsCheck$Callback] */
        public LayoutManager() {
            ?? r0 = new ViewBoundsCheck.Callback() { // from class: androidx.recyclerview.widget.RecyclerView.LayoutManager.1
                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public final View getChildAt(int i) {
                    return LayoutManager.this.getChildAt(i);
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public final int getChildEnd(View view) {
                    LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                    LayoutManager.this.getClass();
                    return view.getRight() + ((LayoutParams) view.getLayoutParams()).mDecorInsets.right + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public final int getChildStart(View view) {
                    LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                    LayoutManager.this.getClass();
                    return (view.getLeft() - ((LayoutParams) view.getLayoutParams()).mDecorInsets.left) - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public final int getParentEnd() {
                    LayoutManager layoutManager = LayoutManager.this;
                    return layoutManager.getWidth() - layoutManager.getPaddingRight();
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public final int getParentStart() {
                    return LayoutManager.this.getPaddingLeft();
                }
            };
            this.mHorizontalBoundCheckCallback = r0;
            ?? r1 = new ViewBoundsCheck.Callback() { // from class: androidx.recyclerview.widget.RecyclerView.LayoutManager.2
                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public final View getChildAt(int i) {
                    return LayoutManager.this.getChildAt(i);
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public final int getChildEnd(View view) {
                    LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                    LayoutManager.this.getClass();
                    return view.getBottom() + ((LayoutParams) view.getLayoutParams()).mDecorInsets.bottom + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public final int getChildStart(View view) {
                    LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                    LayoutManager.this.getClass();
                    return (view.getTop() - ((LayoutParams) view.getLayoutParams()).mDecorInsets.top) - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public final int getParentEnd() {
                    LayoutManager layoutManager = LayoutManager.this;
                    return layoutManager.getHeight() - layoutManager.getPaddingBottom();
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public final int getParentStart() {
                    return LayoutManager.this.getPaddingTop();
                }
            };
            this.mVerticalBoundCheckCallback = r1;
            this.mHorizontalBoundCheck = new ViewBoundsCheck(r0);
            this.mVerticalBoundCheck = new ViewBoundsCheck(r1);
            this.mRequestedSimpleAnimations = false;
            this.mIsAttachedToWindow = false;
        }

        public static int chooseSize(int i, int i2, int i3) {
            int mode = View.MeasureSpec.getMode(i);
            int size = View.MeasureSpec.getSize(i);
            return mode != Integer.MIN_VALUE ? mode != 1073741824 ? Math.max(i2, i3) : size : Math.min(size, Math.max(i2, i3));
        }

        public static void getDecoratedBoundsWithMargins(Rect rect, View view) {
            boolean z = RecyclerView.sDebugAssertionsEnabled;
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect rect2 = layoutParams.mDecorInsets;
            rect.set((view.getLeft() - rect2.left) - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, (view.getTop() - rect2.top) - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, view.getRight() + rect2.right + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, view.getBottom() + rect2.bottom + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
        }

        public static void getPosition(View view) {
            ((LayoutParams) view.getLayoutParams()).getClass();
            throw null;
        }

        public static Properties getProperties(Context context, AttributeSet attributeSet, int i, int i2) {
            Properties properties = new Properties();
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RecyclerView, i, i2);
            properties.orientation = obtainStyledAttributes.getInt(0, 1);
            properties.spanCount = obtainStyledAttributes.getInt(10, 1);
            properties.reverseLayout = obtainStyledAttributes.getBoolean(9, false);
            properties.stackFromEnd = obtainStyledAttributes.getBoolean(11, false);
            obtainStyledAttributes.recycle();
            return properties;
        }

        @SuppressLint({"UnknownNullness"})
        public void assertNotInLayoutOrScroll(String str) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.assertNotInLayoutOrScroll(str);
            }
        }

        public boolean canScrollHorizontally() {
            return false;
        }

        public boolean canScrollVertically() {
            return false;
        }

        public boolean checkLayoutParams(LayoutParams layoutParams) {
            return layoutParams != null;
        }

        public int computeHorizontalScrollExtent(State state) {
            return 0;
        }

        public int computeHorizontalScrollRange(State state) {
            return 0;
        }

        public int computeVerticalScrollExtent(State state) {
            return 0;
        }

        public int computeVerticalScrollRange(State state) {
            return 0;
        }

        @SuppressLint({"UnknownNullness"})
        public abstract LayoutParams generateDefaultLayoutParams();

        @SuppressLint({"UnknownNullness"})
        public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
            return layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
        }

        public final View getChildAt(int i) {
            ChildHelper childHelper = this.mChildHelper;
            if (childHelper != null) {
                return childHelper.getChildAt(i);
            }
            return null;
        }

        public final int getChildCount() {
            ChildHelper childHelper = this.mChildHelper;
            if (childHelper != null) {
                return childHelper.getChildCount();
            }
            return 0;
        }

        public int getColumnCountForAccessibility(Recycler recycler, State state) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) {
                return 1;
            }
            recyclerView.getClass();
            return 1;
        }

        public final int getHeight() {
            return this.mHeight;
        }

        public final int getPaddingBottom() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.getPaddingBottom();
            }
            return 0;
        }

        public final int getPaddingLeft() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.getPaddingLeft();
            }
            return 0;
        }

        public final int getPaddingRight() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.getPaddingRight();
            }
            return 0;
        }

        public final int getPaddingTop() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.getPaddingTop();
            }
            return 0;
        }

        public int getRowCountForAccessibility(Recycler recycler, State state) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) {
                return 1;
            }
            recyclerView.getClass();
            return 1;
        }

        public final int getWidth() {
            return this.mWidth;
        }

        public boolean isAutoMeasureEnabled() {
            return false;
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            RecyclerView recyclerView = this.mRecyclerView;
            Recycler recycler = recyclerView.mRecycler;
            State state = recyclerView.mState;
            if (recyclerView == null || accessibilityEvent == null) {
                return;
            }
            boolean z = true;
            if (!recyclerView.canScrollVertically(1) && !this.mRecyclerView.canScrollVertically(-1) && !this.mRecyclerView.canScrollHorizontally(-1) && !this.mRecyclerView.canScrollHorizontally(1)) {
                z = false;
            }
            accessibilityEvent.setScrollable(z);
            this.mRecyclerView.getClass();
        }

        public void onInitializeAccessibilityNodeInfo(Recycler recycler, State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (this.mRecyclerView.canScrollVertically(-1) || this.mRecyclerView.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            if (this.mRecyclerView.canScrollVertically(1) || this.mRecyclerView.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(getRowCountForAccessibility(recycler, state), getColumnCountForAccessibility(recycler, state)));
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        boolean performAccessibilityAction(int i, Bundle bundle) {
            int paddingTop;
            int paddingLeft;
            Recycler recycler = this.mRecyclerView.mRecycler;
            int i2 = this.mHeight;
            int i3 = this.mWidth;
            Rect rect = new Rect();
            if (this.mRecyclerView.getMatrix().isIdentity() && this.mRecyclerView.getGlobalVisibleRect(rect)) {
                i2 = rect.height();
                i3 = rect.width();
            }
            if (i == 4096) {
                paddingTop = this.mRecyclerView.canScrollVertically(1) ? (i2 - getPaddingTop()) - getPaddingBottom() : 0;
                if (this.mRecyclerView.canScrollHorizontally(1)) {
                    paddingLeft = (i3 - getPaddingLeft()) - getPaddingRight();
                }
                paddingLeft = 0;
            } else if (i != 8192) {
                paddingTop = 0;
                paddingLeft = 0;
            } else {
                paddingTop = this.mRecyclerView.canScrollVertically(-1) ? -((i2 - getPaddingTop()) - getPaddingBottom()) : 0;
                if (this.mRecyclerView.canScrollHorizontally(-1)) {
                    paddingLeft = -((i3 - getPaddingLeft()) - getPaddingRight());
                }
                paddingLeft = 0;
            }
            if (paddingTop == 0 && paddingLeft == 0) {
                return false;
            }
            this.mRecyclerView.smoothScrollBy$1(paddingLeft, paddingTop, true);
            return true;
        }

        public final void removeAndRecycleAllViews(Recycler recycler) {
            int childCount = getChildCount() - 1;
            if (childCount < 0) {
                return;
            }
            RecyclerView.getChildViewHolderInt(getChildAt(childCount));
            throw null;
        }

        final void removeAndRecycleScrapInt(Recycler recycler) {
            int size = recycler.mAttachedScrap.size();
            int i = size - 1;
            ArrayList<ViewHolder> arrayList = recycler.mAttachedScrap;
            if (i >= 0) {
                arrayList.get(i).getClass();
                RecyclerView.getChildViewHolderInt(null);
                throw null;
            }
            arrayList.clear();
            ArrayList<ViewHolder> arrayList2 = recycler.mChangedScrap;
            if (arrayList2 != null) {
                arrayList2.clear();
            }
            if (size > 0) {
                this.mRecyclerView.invalidate();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x00ab, code lost:
        
            if (r8 == false) goto L33;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
            boolean z3;
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = this.mWidth - getPaddingRight();
            int paddingBottom = this.mHeight - getPaddingBottom();
            int left = (view.getLeft() + rect.left) - view.getScrollX();
            int top = (view.getTop() + rect.top) - view.getScrollY();
            int width = rect.width() + left;
            int height = rect.height() + top;
            int i = left - paddingLeft;
            int min = Math.min(0, i);
            int i2 = top - paddingTop;
            int min2 = Math.min(0, i2);
            int i3 = width - paddingRight;
            int max = Math.max(0, i3);
            int max2 = Math.max(0, height - paddingBottom);
            if (ViewCompat.getLayoutDirection(this.mRecyclerView) != 1) {
                if (min == 0) {
                    min = Math.min(i, max);
                }
                max = min;
            } else if (max == 0) {
                max = Math.max(min, i3);
            }
            if (min2 == 0) {
                min2 = Math.min(i2, max2);
            }
            if (z2) {
                View focusedChild = recyclerView.getFocusedChild();
                if (focusedChild != null) {
                    int paddingLeft2 = getPaddingLeft();
                    int paddingTop2 = getPaddingTop();
                    int paddingRight2 = this.mWidth - getPaddingRight();
                    int paddingBottom2 = this.mHeight - getPaddingBottom();
                    Rect rect2 = this.mRecyclerView.mTempRect;
                    getDecoratedBoundsWithMargins(rect2, focusedChild);
                    if (rect2.left - max < paddingRight2 && rect2.right - max > paddingLeft2 && rect2.top - min2 < paddingBottom2 && rect2.bottom - min2 > paddingTop2) {
                        z3 = true;
                    }
                }
                z3 = false;
            }
            if (max != 0 || min2 != 0) {
                if (z) {
                    recyclerView.scrollBy(max, min2);
                } else {
                    recyclerView.smoothScrollBy$1(max, min2, false);
                }
                return true;
            }
            return false;
        }

        public final void requestLayout() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.requestLayout();
            }
        }

        final void setRecyclerView(RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.mRecyclerView = null;
                this.mChildHelper = null;
                this.mWidth = 0;
                this.mHeight = 0;
            } else {
                this.mRecyclerView = recyclerView;
                this.mChildHelper = recyclerView.mChildHelper;
                this.mWidth = recyclerView.getWidth();
                this.mHeight = recyclerView.getHeight();
            }
            this.mWidthMode = 1073741824;
            this.mHeightMode = 1073741824;
        }

        @SuppressLint({"UnknownNullness"})
        public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
            return new LayoutParams(context, attributeSet);
        }

        public void onAdapterChanged() {
        }

        public void computeHorizontalScrollOffset(State state) {
        }

        public void computeVerticalScrollOffset(State state) {
        }

        @SuppressLint({"UnknownNullness"})
        public void onDetachedFromWindow(RecyclerView recyclerView) {
        }

        public void onScrollStateChanged(int i) {
        }
    }

    public static abstract class ItemDecoration {
        public void onDrawOver(Canvas canvas) {
        }
    }

    public void setOnFlingListener(OnFlingListener onFlingListener) {
    }
}
