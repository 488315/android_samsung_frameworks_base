package androidx.recyclerview.widget;

import android.R;
import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Display;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EdgeEffect;
import android.widget.ImageView;
import android.widget.OverScroller;
import androidx.appcompat.animation.SeslAnimationUtils;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.util.SeslSubheaderRoundedCorner;
import androidx.collection.SimpleArrayMap;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.EdgeEffectCompat;
import androidx.customview.poolingcontainer.PoolingContainer;
import androidx.customview.view.AbsSavedState;
import androidx.preference.PreferenceGroupAdapter;
import androidx.recyclerview.R$styleable;
import androidx.recyclerview.widget.AdapterHelper;
import androidx.recyclerview.widget.ChildHelper;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager.SmoothScrollerJumpIfNeeded;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.recyclerview.widget.ViewBoundsCheck;
import androidx.recyclerview.widget.ViewInfoStore;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.provider.SeslSettingsReflector$SeslSystemReflector;
import androidx.reflect.view.SeslInputDeviceReflector;
import androidx.reflect.view.SeslPointerIconReflector;
import androidx.reflect.widget.SeslOverScrollerReflector;
import androidx.reflect.widget.SeslTextViewReflector;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class RecyclerView extends ViewGroup implements NestedScrollingChild2 {
    public static final Class[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE;
    public static final Interpolator LINEAR_INTERPOLATOR;
    public static final StretchEdgeEffectFactory sDefaultEdgeEffectFactory;
    public static final InterpolatorC04718 sQuinticInterpolator;
    public RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
    public final AccessibilityManager mAccessibilityManager;
    public Adapter mAdapter;
    public AdapterHelper mAdapterHelper;
    public boolean mAdapterUpdateDuringMeasure;
    public final C04642 mAnimListener;
    public int mAnimatedBlackTop;
    public final RunnableC04685 mAutoHide;
    public int mBlackTop;
    public EdgeEffect mBottomGlow;
    public final Rect mChildBound;
    public ChildHelper mChildHelper;
    public boolean mClipToPadding;
    public final int mCloseChildPositionByBottom;
    public final int mCloseChildPositionByTop;
    public final Context mContext;
    public boolean mDataSetHasChangedAfterLayout;
    public boolean mDispatchItemsChangedEvent;
    public int mDispatchScrollCounter;
    public boolean mDrawLastRoundedCorner;
    public boolean mDrawRect;
    public boolean mDrawReverse;
    public int mEatenAccessibilityChangeFlags;
    public boolean mEdgeEffectByDragging;
    public final StretchEdgeEffectFactory mEdgeEffectFactory;
    public boolean mEnableGoToTop;
    public SeslRecyclerViewFastScroller mFastScroller;
    boolean mFirstLayoutComplete;
    public float mFrameLatency;
    public GapWorker mGapWorker;
    public final RunnableC04674 mGoToToFadeInRunnable;
    public final RunnableC04663 mGoToToFadeOutRunnable;
    public int mGoToTopBottomPadding;
    public int mGoToTopElevation;
    public ValueAnimator mGoToTopFadeInAnimator;
    public ValueAnimator mGoToTopFadeOutAnimator;
    public Drawable mGoToTopImage;
    public int mGoToTopImmersiveBottomPadding;
    public int mGoToTopLastState;
    public final Rect mGoToTopRect;
    public int mGoToTopSize;
    public int mGoToTopState;
    public ImageView mGoToTopView;
    public boolean mHasFixedSize;
    public boolean mHasNestedScrollRange;
    public boolean mHoverAreaEnter;
    public int mHoverBottomAreaHeight;
    public final HandlerC04696 mHoverHandler;
    public long mHoverRecognitionCurrentTime;
    public long mHoverRecognitionDurationTime;
    public long mHoverRecognitionStartTime;
    public final int[] mHoverScrollArrows;
    public int mHoverScrollDirection;
    public final boolean mHoverScrollEnable;
    public int mHoverScrollSpeed;
    public long mHoverScrollStartTime;
    public int mHoverScrollStateForListener;
    public final long mHoverScrollTimeInterval;
    public int mHoverTopAreaHeight;
    public boolean mIgnoreMotionEventTillDown;
    public int mInitialTopOffsetOfScreen;
    public int mInterceptRequestLayoutDepth;
    public OnItemTouchListener mInterceptingOnItemTouchListener;
    public boolean mIsArrowKeyPressed;
    public boolean mIsAttached;
    public boolean mIsCloseChildSetted;
    public boolean mIsCtrlKeyPressed;
    public boolean mIsCtrlMultiSelection;
    public boolean mIsFirstMultiSelectionMove;
    public boolean mIsFirstPenMoveEvent;
    public boolean mIsHoverOverscrolled;
    public boolean mIsNeedCheckLatency;
    public boolean mIsNeedPenSelectIconSet;
    public boolean mIsNeedPenSelection;
    public final boolean mIsPenDragBlockEnabled;
    public boolean mIsPenHovered;
    public boolean mIsPenPressed;
    public boolean mIsPenSelectPointerSetted;
    public final boolean mIsPenSelectionEnabled;
    public boolean mIsSendHoverScrollState;
    public boolean mIsSetOnlyAddAnim;
    public boolean mIsSetOnlyRemoveAnim;
    public boolean mIsSkipMoveEvent;
    public ItemAnimator mItemAnimator;
    public final ItemAnimatorRestoreListener mItemAnimatorListener;
    public final RunnableC04707 mItemAnimatorRunner;
    public final ArrayList mItemDecorations;
    public boolean mItemsAddedOrRemoved;
    public boolean mItemsChanged;
    public int mLastAutoMeasureNonExactMeasuredHeight;
    public int mLastAutoMeasureNonExactMeasuredWidth;
    public boolean mLastAutoMeasureSkippedDueToExact;
    public int mLastBlackTop;
    public ValueAnimator mLastItemAddRemoveAnim;
    public int mLastItemAnimTop;
    public int mLastTouchX;
    public int mLastTouchY;
    LayoutManager mLayout;
    public int mLayoutOrScrollCounter;
    public boolean mLayoutSuppressed;
    public boolean mLayoutWasDefered;
    public EdgeEffect mLeftGlow;
    public final Rect mListPadding;
    public int mMaxFlingVelocity;
    public int mMinFlingVelocity;
    public final int[] mMinMaxLayoutPositions;
    public boolean mNeedsHoverScroll;
    public final int[] mNestedOffsets;
    public int mNestedScrollRange;
    public boolean mNewTextViewHoverState;
    public final RecyclerViewDataObserver mObserver;
    public int mOldHoverScrollDirection;
    public boolean mOldTextViewHoverState;
    public List mOnChildAttachStateListeners;
    public OnFlingListener mOnFlingListener;
    public final ArrayList mOnItemTouchListeners;
    public int mPagingTouchSlop;
    public int mPenDistanceFromTrackedChildTop;
    public final Drawable mPenDragBlockImage;
    public int mPenDragBlockLeft;
    public final Rect mPenDragBlockRect;
    public int mPenDragBlockRight;
    public int mPenDragBlockTop;
    public int mPenDragEndX;
    public int mPenDragEndY;
    public final long mPenDragScrollTimeInterval;
    public ArrayList mPenDragSelectedItemArray;
    public int mPenDragSelectedViewPosition;
    public int mPenDragStartX;
    public int mPenDragStartY;
    public View mPenTrackedChild;
    public int mPenTrackedChildPosition;
    final List<ViewHolder> mPendingAccessibilityImportanceChange;
    public SavedState mPendingSavedState;
    public final float mPhysicalCoef;
    public boolean mPostedAnimatorRunner;
    public final GapWorker.LayoutPrefetchRegistryImpl mPrefetchRegistry;
    public boolean mPreserveFocusAfterLayout;
    public boolean mPreventFirstGlow;
    public final int mRectColor;
    public final Paint mRectPaint;
    public final Recycler mRecycler;
    public RecyclerListener mRecyclerListener;
    public final List mRecyclerListeners;
    public int mRemainNestedScrollRange;
    public final int[] mReusableIntPair;
    public EdgeEffect mRightGlow;
    public final SeslSubheaderRoundedCorner mRoundedCorner;
    public float mScaledHorizontalScrollFactor;
    public float mScaledVerticalScrollFactor;
    public List mScrollListeners;
    public final int[] mScrollOffset;
    public int mScrollPointerId;
    public int mScrollState;
    public NestedScrollingChildHelper mScrollingChildHelper;
    public int mSeslOverlayFeatureHeight;
    public int mShowFadeOutGTP;
    public boolean mSizeChnage;
    public final State mState;
    public final Rect mTempRect;
    public final Rect mTempRect2;
    public final RectF mTempRectF;
    public EdgeEffect mTopGlow;
    public int mTouchSlop;
    public int mTouchSlop2;
    public final RunnableC04541 mUpdateChildViewsRunnable;
    public boolean mUsePagingTouchSlopForStylus;
    public VelocityTracker mVelocityTracker;
    public final ViewFlinger mViewFlinger;
    public final C04729 mViewInfoProcessCallback;
    public final ViewInfoStore mViewInfoStore;
    public final int[] mWindowOffsets;
    public static final int[] NESTED_SCROLLING_ATTRS = {R.attr.nestedScrollingEnabled};
    public static final float DECELERATION_RATE = (float) (Math.log(0.78d) / Math.log(0.9d));
    public static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC = true;
    public static final boolean POST_UPDATES_ON_ANIMATION = true;
    public static final boolean ALLOW_THREAD_GAP_WORK = true;
    public static final float HOVERSCROLL_SPEED = 10.0f;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.recyclerview.widget.RecyclerView$10 */
    public final class C045510 implements ChildHelper.Callback {
        public C045510() {
        }

        public final int getChildCount() {
            return RecyclerView.this.getChildCount();
        }

        public final void removeViewAt(int i) {
            RecyclerView recyclerView = RecyclerView.this;
            View childAt = recyclerView.getChildAt(i);
            if (childAt != null) {
                recyclerView.dispatchChildDetached(childAt);
                childAt.clearAnimation();
            }
            recyclerView.removeViewAt(i);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.recyclerview.widget.RecyclerView$11 */
    public final class C045611 implements AdapterHelper.Callback {
        public C045611() {
        }

        public final void dispatchUpdate(AdapterHelper.UpdateOp updateOp) {
            int i = updateOp.cmd;
            RecyclerView recyclerView = RecyclerView.this;
            if (i == 1) {
                recyclerView.mLayout.onItemsAdded(updateOp.positionStart, updateOp.itemCount);
                return;
            }
            if (i == 2) {
                recyclerView.mLayout.onItemsRemoved(updateOp.positionStart, updateOp.itemCount);
            } else if (i == 4) {
                recyclerView.mLayout.onItemsUpdated(recyclerView, updateOp.positionStart, updateOp.itemCount);
            } else {
                if (i != 8) {
                    return;
                }
                recyclerView.mLayout.onItemsMoved(updateOp.positionStart, updateOp.itemCount);
            }
        }

        public final void markViewHoldersUpdated(int i, int i2, Object obj) {
            int i3;
            int i4;
            RecyclerView recyclerView = RecyclerView.this;
            int unfilteredChildCount = recyclerView.mChildHelper.getUnfilteredChildCount();
            int i5 = i2 + i;
            for (int i6 = 0; i6 < unfilteredChildCount; i6++) {
                View unfilteredChildAt = recyclerView.mChildHelper.getUnfilteredChildAt(i6);
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(unfilteredChildAt);
                if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore() && (i4 = childViewHolderInt.mPosition) >= i && i4 < i5) {
                    childViewHolderInt.addFlags(2);
                    childViewHolderInt.addChangePayload(obj);
                    ((LayoutParams) unfilteredChildAt.getLayoutParams()).mInsetsDirty = true;
                }
            }
            Recycler recycler = recyclerView.mRecycler;
            ArrayList arrayList = recycler.mCachedViews;
            int size = arrayList.size();
            while (true) {
                size--;
                if (size < 0) {
                    recyclerView.mItemsChanged = true;
                    return;
                }
                ViewHolder viewHolder = (ViewHolder) arrayList.get(size);
                if (viewHolder != null && (i3 = viewHolder.mPosition) >= i && i3 < i5) {
                    viewHolder.addFlags(2);
                    recycler.recycleCachedViewAt(size);
                }
            }
        }

        public final void offsetPositionsForAdd(int i, int i2) {
            RecyclerView recyclerView = RecyclerView.this;
            int unfilteredChildCount = recyclerView.mChildHelper.getUnfilteredChildCount();
            for (int i3 = 0; i3 < unfilteredChildCount; i3++) {
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(recyclerView.mChildHelper.getUnfilteredChildAt(i3));
                if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore() && childViewHolderInt.mPosition >= i) {
                    childViewHolderInt.offsetPosition(i2, false);
                    recyclerView.mState.mStructureChanged = true;
                }
            }
            ArrayList arrayList = recyclerView.mRecycler.mCachedViews;
            int size = arrayList.size();
            for (int i4 = 0; i4 < size; i4++) {
                ViewHolder viewHolder = (ViewHolder) arrayList.get(i4);
                if (viewHolder != null && viewHolder.mPosition >= i) {
                    viewHolder.offsetPosition(i2, true);
                }
            }
            recyclerView.requestLayout();
            recyclerView.mItemsAddedOrRemoved = true;
        }

        public final void offsetPositionsForMove(int i, int i2) {
            int i3;
            int i4;
            int i5;
            int i6;
            int i7;
            int i8;
            int i9;
            RecyclerView recyclerView = RecyclerView.this;
            int unfilteredChildCount = recyclerView.mChildHelper.getUnfilteredChildCount();
            int i10 = -1;
            if (i < i2) {
                i4 = i;
                i3 = i2;
                i5 = -1;
            } else {
                i3 = i;
                i4 = i2;
                i5 = 1;
            }
            for (int i11 = 0; i11 < unfilteredChildCount; i11++) {
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(recyclerView.mChildHelper.getUnfilteredChildAt(i11));
                if (childViewHolderInt != null && (i9 = childViewHolderInt.mPosition) >= i4 && i9 <= i3) {
                    if (i9 == i) {
                        childViewHolderInt.offsetPosition(i2 - i, false);
                    } else {
                        childViewHolderInt.offsetPosition(i5, false);
                    }
                    recyclerView.mState.mStructureChanged = true;
                }
            }
            Recycler recycler = recyclerView.mRecycler;
            recycler.getClass();
            if (i < i2) {
                i7 = i;
                i6 = i2;
            } else {
                i6 = i;
                i7 = i2;
                i10 = 1;
            }
            ArrayList arrayList = recycler.mCachedViews;
            int size = arrayList.size();
            for (int i12 = 0; i12 < size; i12++) {
                ViewHolder viewHolder = (ViewHolder) arrayList.get(i12);
                if (viewHolder != null && (i8 = viewHolder.mPosition) >= i7 && i8 <= i6) {
                    if (i8 == i) {
                        viewHolder.offsetPosition(i2 - i, false);
                    } else {
                        viewHolder.offsetPosition(i10, false);
                    }
                }
            }
            recyclerView.requestLayout();
            recyclerView.mItemsAddedOrRemoved = true;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.recyclerview.widget.RecyclerView$20 */
    public abstract /* synthetic */ class AbstractC046520 {

        /* renamed from: $SwitchMap$androidx$recyclerview$widget$RecyclerView$Adapter$StateRestorationPolicy */
        public static final /* synthetic */ int[] f179x3d106619;

        static {
            int[] iArr = new int[Adapter.StateRestorationPolicy.values().length];
            f179x3d106619 = iArr;
            try {
                iArr[Adapter.StateRestorationPolicy.PREVENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f179x3d106619[Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.recyclerview.widget.RecyclerView$9 */
    public final class C04729 {
        public C04729() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Adapter {
        public final AdapterDataObservable mObservable = new AdapterDataObservable();
        public boolean mHasStableIds = false;
        public final StateRestorationPolicy mStateRestorationPolicy = StateRestorationPolicy.ALLOW;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public enum StateRestorationPolicy {
            ALLOW,
            PREVENT_WHEN_EMPTY,
            PREVENT
        }

        public abstract int getItemCount();

        public long getItemId(int i) {
            return -1L;
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public final void notifyDataSetChanged() {
            this.mObservable.notifyChanged();
        }

        public final void notifyItemChanged(int i, Object obj) {
            this.mObservable.notifyItemRangeChanged(i, 1, obj);
        }

        public final void notifyItemInserted(int i) {
            this.mObservable.notifyItemRangeInserted(i, 1);
        }

        public final void notifyItemMoved(int i, int i2) {
            this.mObservable.notifyItemMoved(i, i2);
        }

        public final void notifyItemRangeInserted(int i, int i2) {
            this.mObservable.notifyItemRangeInserted(i, i2);
        }

        public final void notifyItemRangeRemoved(int i, int i2) {
            this.mObservable.notifyItemRangeRemoved(i, i2);
        }

        public final void notifyItemRemoved(int i) {
            this.mObservable.notifyItemRangeRemoved(i, 1);
        }

        public abstract void onBindViewHolder(ViewHolder viewHolder, int i);

        public void onBindViewHolder(ViewHolder viewHolder, int i, List list) {
            onBindViewHolder(viewHolder, i);
        }

        public abstract ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i);

        public boolean onFailedToRecycleView(ViewHolder viewHolder) {
            return false;
        }

        public final void registerAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            this.mObservable.registerObserver(adapterDataObserver);
        }

        public int seslGetAccessibilityItemCount() {
            return getItemCount();
        }

        public final void setHasStableIds(boolean z) {
            if (this.mObservable.hasObservers()) {
                throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
            }
            this.mHasStableIds = z;
        }

        public final void notifyItemChanged(int i) {
            this.mObservable.notifyItemRangeChanged(i, 1, null);
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        }

        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        }

        public void onViewAttachedToWindow(ViewHolder viewHolder) {
        }

        public void onViewRecycled(ViewHolder viewHolder) {
        }

        public int seslGetAccessibilityItemPosition(int i) {
            return i;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AdapterDataObservable extends Observable {
        public final boolean hasObservers() {
            return !((Observable) this).mObservers.isEmpty();
        }

        public final void notifyChanged() {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) ((Observable) this).mObservers.get(size)).onChanged();
            }
        }

        public final void notifyItemMoved(int i, int i2) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) ((Observable) this).mObservers.get(size)).onItemRangeMoved(i, i2);
            }
        }

        public final void notifyItemRangeChanged(int i, int i2, Object obj) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) ((Observable) this).mObservers.get(size)).onItemRangeChanged(i, i2, obj);
            }
        }

        public final void notifyItemRangeInserted(int i, int i2) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) ((Observable) this).mObservers.get(size)).onItemRangeInserted(i, i2);
            }
        }

        public final void notifyItemRangeRemoved(int i, int i2) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) ((Observable) this).mObservers.get(size)).onItemRangeRemoved(i, i2);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class AdapterDataObserver {
        public void onItemRangeChanged(int i, int i2) {
        }

        public void onItemRangeChanged(int i, int i2, Object obj) {
            onItemRangeChanged(i, i2);
        }

        public void onChanged() {
        }

        public void onItemRangeInserted(int i, int i2) {
        }

        public void onItemRangeMoved(int i, int i2) {
        }

        public void onItemRangeRemoved(int i, int i2) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class EdgeEffectFactory {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class ItemAnimator {
        public ItemAnimatorRestoreListener mListener = null;
        public final ArrayList mFinishedListeners = new ArrayList();
        public View mHostView = null;
        public final long mRemoveDuration = 120;
        public long mMoveDuration = 250;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ItemHolderInfo {
            public int left;
            public int top;
        }

        public static int buildAdapterChangeFlagsForAnimations(ViewHolder viewHolder) {
            int i = viewHolder.mFlags & 14;
            if (viewHolder.isInvalid()) {
                return 4;
            }
            if ((i & 4) != 0) {
                return i;
            }
            int i2 = viewHolder.mOldPosition;
            int absoluteAdapterPosition = viewHolder.getAbsoluteAdapterPosition();
            return (i2 == -1 || absoluteAdapterPosition == -1 || i2 == absoluteAdapterPosition) ? i : i | 2048;
        }

        public abstract boolean animateAppearance(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2);

        public abstract boolean animateChange(ViewHolder viewHolder, ViewHolder viewHolder2, ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2);

        public abstract boolean animateDisappearance(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2);

        public abstract boolean animatePersistence(ViewHolder viewHolder, ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2);

        public boolean canReuseUpdatedViewHolder(ViewHolder viewHolder) {
            return true;
        }

        public final void dispatchAnimationFinished(ViewHolder viewHolder) {
            ItemAnimatorRestoreListener itemAnimatorRestoreListener = this.mListener;
            if (itemAnimatorRestoreListener != null) {
                boolean z = true;
                viewHolder.setIsRecyclable(true);
                if (viewHolder.mShadowedHolder != null && viewHolder.mShadowingHolder == null) {
                    viewHolder.mShadowedHolder = null;
                }
                viewHolder.mShadowingHolder = null;
                RecyclerView recyclerView = RecyclerView.this;
                Iterator it = recyclerView.mItemDecorations.iterator();
                while (it.hasNext()) {
                    ItemDecoration itemDecoration = (ItemDecoration) it.next();
                    if (itemDecoration instanceof ItemTouchHelper) {
                        ((ItemTouchHelper) itemDecoration).endRecoverAnimation(viewHolder, false);
                    }
                }
                if ((viewHolder.mFlags & 16) != 0) {
                    return;
                }
                recyclerView.startInterceptRequestLayout();
                ChildHelper childHelper = recyclerView.mChildHelper;
                C045510 c045510 = (C045510) childHelper.mCallback;
                RecyclerView recyclerView2 = RecyclerView.this;
                View view = viewHolder.itemView;
                int indexOfChild = recyclerView2.indexOfChild(view);
                if (indexOfChild == -1) {
                    childHelper.unhideViewInternal(view);
                } else {
                    ChildHelper.Bucket bucket = childHelper.mBucket;
                    if (bucket.get(indexOfChild)) {
                        bucket.remove(indexOfChild);
                        childHelper.unhideViewInternal(view);
                        c045510.removeViewAt(indexOfChild);
                    } else {
                        z = false;
                    }
                }
                if (z) {
                    ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
                    recyclerView.mRecycler.unscrapView(childViewHolderInt);
                    recyclerView.mRecycler.recycleViewHolderInternal(childViewHolderInt);
                }
                recyclerView.stopInterceptRequestLayout(!z);
                if (z || !viewHolder.isTmpDetached()) {
                    return;
                }
                recyclerView.removeDetachedView(view, false);
            }
        }

        public abstract void endAnimation(ViewHolder viewHolder);

        public abstract void endAnimations();

        public long getMoveDuration() {
            return this.mMoveDuration;
        }

        public long getRemoveDuration() {
            return this.mRemoveDuration;
        }

        public abstract boolean isRunning();

        public abstract void runPendingAnimations();

        public boolean canReuseUpdatedViewHolder(ViewHolder viewHolder, List list) {
            return canReuseUpdatedViewHolder(viewHolder);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ItemAnimatorRestoreListener {
        public ItemAnimatorRestoreListener() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class LayoutManager {
        public boolean mAutoMeasure;
        public ChildHelper mChildHelper;
        public int mHeight;
        public int mHeightMode;
        public final ViewBoundsCheck mHorizontalBoundCheck;
        public final C04731 mHorizontalBoundCheckCallback;
        public boolean mIsAttachedToWindow;
        public boolean mItemPrefetchEnabled;
        public final boolean mMeasurementCacheEnabled;
        public int mPrefetchMaxCountObserved;
        public boolean mPrefetchMaxObservedInInitialPrefetch;
        public RecyclerView mRecyclerView;
        public boolean mRequestedSimpleAnimations;
        public SmoothScroller mSmoothScroller;
        public final ViewBoundsCheck mVerticalBoundCheck;
        public final C04742 mVerticalBoundCheckCallback;
        public int mWidth;
        public int mWidthMode;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Properties {
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
                    return LayoutManager.this.getDecoratedRight(view) + ((ViewGroup.MarginLayoutParams) ((LayoutParams) view.getLayoutParams())).rightMargin;
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public final int getChildStart(View view) {
                    return LayoutManager.this.getDecoratedLeft(view) - ((ViewGroup.MarginLayoutParams) ((LayoutParams) view.getLayoutParams())).leftMargin;
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public final int getParentEnd() {
                    LayoutManager layoutManager = LayoutManager.this;
                    return layoutManager.mWidth - layoutManager.getPaddingRight();
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
                    return LayoutManager.this.getDecoratedBottom(view) + ((ViewGroup.MarginLayoutParams) ((LayoutParams) view.getLayoutParams())).bottomMargin;
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public final int getChildStart(View view) {
                    return LayoutManager.this.getDecoratedTop(view) - ((ViewGroup.MarginLayoutParams) ((LayoutParams) view.getLayoutParams())).topMargin;
                }

                @Override // androidx.recyclerview.widget.ViewBoundsCheck.Callback
                public final int getParentEnd() {
                    LayoutManager layoutManager = LayoutManager.this;
                    return layoutManager.mHeight - layoutManager.getPaddingBottom();
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
            this.mAutoMeasure = false;
            this.mMeasurementCacheEnabled = true;
            this.mItemPrefetchEnabled = true;
        }

        public static int chooseSize(int i, int i2, int i3) {
            int mode = View.MeasureSpec.getMode(i);
            int size = View.MeasureSpec.getSize(i);
            return mode != Integer.MIN_VALUE ? mode != 1073741824 ? Math.max(i2, i3) : size : Math.min(size, Math.max(i2, i3));
        }

        /* JADX WARN: Code restructure failed: missing block: B:7:0x0017, code lost:
        
            if (r6 == 1073741824) goto L14;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static int getChildMeasureSpec(boolean z, int i, int i2, int i3, int i4) {
            int max = Math.max(0, i - i3);
            if (z) {
                if (i4 < 0) {
                    if (i4 == -1) {
                        if (i2 != Integer.MIN_VALUE) {
                            if (i2 != 0) {
                            }
                        }
                        i4 = max;
                    }
                    i2 = 0;
                    i4 = 0;
                }
                i2 = 1073741824;
            } else {
                if (i4 < 0) {
                    if (i4 != -1) {
                        if (i4 == -2) {
                            if (i2 == Integer.MIN_VALUE || i2 == 1073741824) {
                                i4 = max;
                                i2 = Integer.MIN_VALUE;
                            } else {
                                i4 = max;
                                i2 = 0;
                            }
                        }
                        i2 = 0;
                        i4 = 0;
                    }
                    i4 = max;
                }
                i2 = 1073741824;
            }
            return View.MeasureSpec.makeMeasureSpec(i4, i2);
        }

        public static int getDecoratedMeasuredHeight(View view) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).mDecorInsets;
            return view.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public static int getDecoratedMeasuredWidth(View view) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).mDecorInsets;
            return view.getMeasuredWidth() + rect.left + rect.right;
        }

        public static int getPosition(View view) {
            return ((LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
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

        public static boolean isMeasurementUpToDate(int i, int i2, int i3) {
            int mode = View.MeasureSpec.getMode(i2);
            int size = View.MeasureSpec.getSize(i2);
            if (i3 > 0 && i != i3) {
                return false;
            }
            if (mode == Integer.MIN_VALUE) {
                return size >= i;
            }
            if (mode != 0) {
                return mode == 1073741824 && size == i;
            }
            return true;
        }

        public static void layoutDecoratedWithMargins(View view, int i, int i2, int i3, int i4) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect rect = layoutParams.mDecorInsets;
            view.layout(i + rect.left + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, i2 + rect.top + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, (i3 - rect.right) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, (i4 - rect.bottom) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
        }

        public final void addViewInt(View view, int i, boolean z) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (z || childViewHolderInt.isRemoved()) {
                SimpleArrayMap simpleArrayMap = this.mRecyclerView.mViewInfoStore.mLayoutHolderMap;
                ViewInfoStore.InfoRecord infoRecord = (ViewInfoStore.InfoRecord) simpleArrayMap.get(childViewHolderInt);
                if (infoRecord == null) {
                    infoRecord = ViewInfoStore.InfoRecord.obtain();
                    simpleArrayMap.put(childViewHolderInt, infoRecord);
                }
                infoRecord.flags |= 1;
            } else {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(childViewHolderInt);
            }
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (childViewHolderInt.wasReturnedFromScrap() || childViewHolderInt.isScrap()) {
                if (childViewHolderInt.isScrap()) {
                    childViewHolderInt.mScrapContainer.unscrapView(childViewHolderInt);
                } else {
                    childViewHolderInt.mFlags &= -33;
                }
                this.mChildHelper.attachViewToParent(view, i, view.getLayoutParams(), false);
            } else if (view.getParent() == this.mRecyclerView) {
                int indexOfChild = this.mChildHelper.indexOfChild(view);
                if (i == -1) {
                    i = this.mChildHelper.getChildCount();
                }
                if (indexOfChild == -1) {
                    throw new IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.mRecyclerView.indexOfChild(view) + this.mRecyclerView.exceptionLabel());
                }
                if (indexOfChild != i) {
                    LayoutManager layoutManager = this.mRecyclerView.mLayout;
                    View childAt = layoutManager.getChildAt(indexOfChild);
                    if (childAt == null) {
                        throw new IllegalArgumentException("Cannot move a child from non-existing index:" + indexOfChild + layoutManager.mRecyclerView.toString());
                    }
                    layoutManager.getChildAt(indexOfChild);
                    layoutManager.mChildHelper.detachViewFromParent(indexOfChild);
                    LayoutParams layoutParams2 = (LayoutParams) childAt.getLayoutParams();
                    ViewHolder childViewHolderInt2 = RecyclerView.getChildViewHolderInt(childAt);
                    if (childViewHolderInt2.isRemoved()) {
                        SimpleArrayMap simpleArrayMap2 = layoutManager.mRecyclerView.mViewInfoStore.mLayoutHolderMap;
                        ViewInfoStore.InfoRecord infoRecord2 = (ViewInfoStore.InfoRecord) simpleArrayMap2.get(childViewHolderInt2);
                        if (infoRecord2 == null) {
                            infoRecord2 = ViewInfoStore.InfoRecord.obtain();
                            simpleArrayMap2.put(childViewHolderInt2, infoRecord2);
                        }
                        infoRecord2.flags = 1 | infoRecord2.flags;
                    } else {
                        layoutManager.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(childViewHolderInt2);
                    }
                    layoutManager.mChildHelper.attachViewToParent(childAt, i, layoutParams2, childViewHolderInt2.isRemoved());
                }
            } else {
                this.mChildHelper.addView(view, i, false);
                layoutParams.mInsetsDirty = true;
                SmoothScroller smoothScroller = this.mSmoothScroller;
                if (smoothScroller != null && smoothScroller.mRunning) {
                    smoothScroller.mRecyclerView.getClass();
                    if (RecyclerView.getChildLayoutPosition(view) == smoothScroller.mTargetPosition) {
                        smoothScroller.mTargetView = view;
                    }
                }
            }
            if (layoutParams.mPendingInvalidate) {
                childViewHolderInt.itemView.invalidate();
                layoutParams.mPendingInvalidate = false;
            }
        }

        public void assertNotInLayoutOrScroll(String str) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.assertNotInLayoutOrScroll(str);
            }
        }

        public void calculateItemDecorationsForChild(Rect rect, View view) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) {
                rect.set(0, 0, 0, 0);
            } else {
                rect.set(recyclerView.getItemDecorInsetsForChild(view));
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

        public int computeHorizontalScrollOffset(State state) {
            return 0;
        }

        public int computeHorizontalScrollRange(State state) {
            return 0;
        }

        public int computeVerticalScrollExtent(State state) {
            return 0;
        }

        public int computeVerticalScrollOffset(State state) {
            return 0;
        }

        public int computeVerticalScrollRange(State state) {
            return 0;
        }

        public final void detachAndScrapAttachedViews(Recycler recycler) {
            int childCount = getChildCount();
            while (true) {
                childCount--;
                if (childCount < 0) {
                    return;
                } else {
                    scrapOrRecycleView(recycler, childCount, getChildAt(childCount));
                }
            }
        }

        public final View findContainingItemView(View view) {
            View findContainingItemView;
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null || (findContainingItemView = recyclerView.findContainingItemView(view)) == null || this.mChildHelper.isHidden(findContainingItemView)) {
                return null;
            }
            return findContainingItemView;
        }

        public View findViewByPosition(int i) {
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(childAt);
                if (childViewHolderInt != null && childViewHolderInt.getLayoutPosition() == i && !childViewHolderInt.shouldIgnore() && (this.mRecyclerView.mState.mInPreLayout || !childViewHolderInt.isRemoved())) {
                    return childAt;
                }
            }
            return null;
        }

        public abstract LayoutParams generateDefaultLayoutParams();

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
            if (recyclerView == null || recyclerView.mAdapter == null || !canScrollHorizontally()) {
                return 1;
            }
            return this.mRecyclerView.mAdapter.getItemCount();
        }

        public int getDecoratedBottom(View view) {
            return ((LayoutParams) view.getLayoutParams()).mDecorInsets.bottom + view.getBottom();
        }

        public void getDecoratedBoundsWithMargins(Rect rect, View view) {
            RecyclerView.getDecoratedBoundsWithMarginsInt(rect, view);
        }

        public int getDecoratedLeft(View view) {
            return view.getLeft() - ((LayoutParams) view.getLayoutParams()).mDecorInsets.left;
        }

        public int getDecoratedRight(View view) {
            return ((LayoutParams) view.getLayoutParams()).mDecorInsets.right + view.getRight();
        }

        public int getDecoratedTop(View view) {
            return view.getTop() - ((LayoutParams) view.getLayoutParams()).mDecorInsets.top;
        }

        public final int getItemCount() {
            RecyclerView recyclerView = this.mRecyclerView;
            Adapter adapter = recyclerView != null ? recyclerView.mAdapter : null;
            if (adapter != null) {
                return adapter.getItemCount();
            }
            return 0;
        }

        public final int getLayoutDirection() {
            RecyclerView recyclerView = this.mRecyclerView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            return ViewCompat.Api17Impl.getLayoutDirection(recyclerView);
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
            Adapter adapter;
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null || (adapter = recyclerView.mAdapter) == null) {
                return 1;
            }
            adapter.getClass();
            if (adapter instanceof PreferenceGroupAdapter) {
                if (canScrollVertically()) {
                    return this.mRecyclerView.mAdapter.seslGetAccessibilityItemCount();
                }
                return 1;
            }
            if (canScrollVertically()) {
                return this.mRecyclerView.mAdapter.getItemCount();
            }
            return 1;
        }

        public final void getTransformedBoundingBox(View view, Rect rect) {
            Matrix matrix;
            Rect rect2 = ((LayoutParams) view.getLayoutParams()).mDecorInsets;
            rect.set(-rect2.left, -rect2.top, view.getWidth() + rect2.right, view.getHeight() + rect2.bottom);
            if (this.mRecyclerView != null && (matrix = view.getMatrix()) != null && !matrix.isIdentity()) {
                RectF rectF = this.mRecyclerView.mTempRectF;
                rectF.set(rect);
                matrix.mapRect(rectF);
                rect.set((int) Math.floor(rectF.left), (int) Math.floor(rectF.top), (int) Math.ceil(rectF.right), (int) Math.ceil(rectF.bottom));
            }
            rect.offset(view.getLeft(), view.getTop());
        }

        public final boolean hasFocus() {
            RecyclerView recyclerView = this.mRecyclerView;
            return recyclerView != null && recyclerView.hasFocus();
        }

        public boolean isAutoMeasureEnabled() {
            return this.mAutoMeasure;
        }

        public void offsetChildrenHorizontal(int i) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                int childCount = recyclerView.mChildHelper.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    recyclerView.mChildHelper.getChildAt(i2).offsetLeftAndRight(i);
                }
            }
        }

        public void offsetChildrenVertical(int i) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                int childCount = recyclerView.mChildHelper.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    recyclerView.mChildHelper.getChildAt(i2).offsetTopAndBottom(i);
                }
            }
        }

        public boolean onAddFocusables(RecyclerView recyclerView, ArrayList arrayList, int i, int i2) {
            return false;
        }

        public View onFocusSearchFailed(View view, int i, Recycler recycler, State state) {
            return null;
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
            Adapter adapter = this.mRecyclerView.mAdapter;
            if (adapter != null) {
                if (adapter instanceof PreferenceGroupAdapter) {
                    accessibilityEvent.setItemCount(adapter.seslGetAccessibilityItemCount());
                } else {
                    accessibilityEvent.setItemCount(adapter.getItemCount());
                }
            }
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
            accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(getRowCountForAccessibility(recycler, state), getColumnCountForAccessibility(recycler, state), 0));
        }

        public final void onInitializeAccessibilityNodeInfoForItem(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt == null || childViewHolderInt.isRemoved() || this.mChildHelper.isHidden(childViewHolderInt.itemView)) {
                return;
            }
            RecyclerView recyclerView = this.mRecyclerView;
            onInitializeAccessibilityNodeInfoForItem(recyclerView.mRecycler, recyclerView.mState, view, accessibilityNodeInfoCompat);
        }

        public View onInterceptFocusSearch(View view, int i) {
            return null;
        }

        public void onItemsUpdated(int i, int i2) {
        }

        public void onLayoutChildren(Recycler recycler, State state) {
            Log.e("SeslRecyclerView", "You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public void onMeasure(Recycler recycler, State state, int i, int i2) {
            this.mRecyclerView.defaultOnMeasure(i, i2);
        }

        public boolean onRequestChildFocus(RecyclerView recyclerView, View view, View view2) {
            SmoothScroller smoothScroller = this.mSmoothScroller;
            return (smoothScroller != null && smoothScroller.mRunning) || recyclerView.isComputingLayout();
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        public boolean performAccessibilityAction(Recycler recycler, State state, int i, Bundle bundle) {
            int paddingTop;
            int paddingLeft;
            if (this.mRecyclerView == null) {
                return false;
            }
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
            this.mRecyclerView.smoothScrollBy(paddingLeft, paddingTop, true);
            return true;
        }

        public void removeAndRecycleAllViews(Recycler recycler) {
            int childCount = getChildCount();
            while (true) {
                childCount--;
                if (childCount < 0) {
                    return;
                }
                if (!RecyclerView.getChildViewHolderInt(getChildAt(childCount)).shouldIgnore()) {
                    View childAt = getChildAt(childCount);
                    removeViewAt(childCount);
                    recycler.recycleView(childAt);
                }
            }
        }

        public final void removeAndRecycleScrapInt(Recycler recycler) {
            ArrayList arrayList;
            int size = recycler.mAttachedScrap.size();
            int i = size - 1;
            while (true) {
                arrayList = recycler.mAttachedScrap;
                if (i < 0) {
                    break;
                }
                View view = ((ViewHolder) arrayList.get(i)).itemView;
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
                if (!childViewHolderInt.shouldIgnore()) {
                    childViewHolderInt.setIsRecyclable(false);
                    if (childViewHolderInt.isTmpDetached()) {
                        this.mRecyclerView.removeDetachedView(view, false);
                    }
                    ItemAnimator itemAnimator = this.mRecyclerView.mItemAnimator;
                    if (itemAnimator != null) {
                        itemAnimator.endAnimation(childViewHolderInt);
                    }
                    childViewHolderInt.setIsRecyclable(true);
                    ViewHolder childViewHolderInt2 = RecyclerView.getChildViewHolderInt(view);
                    childViewHolderInt2.mScrapContainer = null;
                    childViewHolderInt2.mInChangeScrap = false;
                    childViewHolderInt2.mFlags &= -33;
                    recycler.recycleViewHolderInternal(childViewHolderInt2);
                }
                i--;
            }
            arrayList.clear();
            ArrayList arrayList2 = recycler.mChangedScrap;
            if (arrayList2 != null) {
                arrayList2.clear();
            }
            if (size > 0) {
                this.mRecyclerView.invalidate();
            }
        }

        public final void removeAndRecycleView(View view, Recycler recycler) {
            ChildHelper childHelper = this.mChildHelper;
            C045510 c045510 = (C045510) childHelper.mCallback;
            int indexOfChild = RecyclerView.this.indexOfChild(view);
            if (indexOfChild >= 0) {
                if (childHelper.mBucket.remove(indexOfChild)) {
                    childHelper.unhideViewInternal(view);
                }
                c045510.removeViewAt(indexOfChild);
            }
            recycler.recycleView(view);
        }

        public final void removeViewAt(int i) {
            if (getChildAt(i) != null) {
                ChildHelper childHelper = this.mChildHelper;
                int offset = childHelper.getOffset(i);
                C045510 c045510 = (C045510) childHelper.mCallback;
                View childAt = RecyclerView.this.getChildAt(offset);
                if (childAt == null) {
                    return;
                }
                if (childHelper.mBucket.remove(offset)) {
                    childHelper.unhideViewInternal(childAt);
                }
                c045510.removeViewAt(offset);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x00a8, code lost:
        
            if (r8 == false) goto L32;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
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
            if (getLayoutDirection() != 1) {
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
                    recyclerView.smoothScrollBy(max, min2);
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

        public final void scrapOrRecycleView(Recycler recycler, int i, View view) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt.shouldIgnore()) {
                return;
            }
            if (childViewHolderInt.isInvalid() && !childViewHolderInt.isRemoved() && !this.mRecyclerView.mAdapter.mHasStableIds) {
                removeViewAt(i);
                recycler.recycleViewHolderInternal(childViewHolderInt);
            } else {
                getChildAt(i);
                this.mChildHelper.detachViewFromParent(i);
                recycler.scrapView(view);
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(childViewHolderInt);
            }
        }

        public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
            return 0;
        }

        public int scrollVerticallyBy(int i, Recycler recycler, State state) {
            return 0;
        }

        public final void setExactMeasureSpecsFrom(RecyclerView recyclerView) {
            setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
        }

        public final void setMeasureSpecs(int i, int i2) {
            this.mWidth = View.MeasureSpec.getSize(i);
            int mode = View.MeasureSpec.getMode(i);
            this.mWidthMode = mode;
            if (mode == 0 && !RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mWidth = 0;
            }
            this.mHeight = View.MeasureSpec.getSize(i2);
            int mode2 = View.MeasureSpec.getMode(i2);
            this.mHeightMode = mode2;
            if (mode2 != 0 || RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                return;
            }
            this.mHeight = 0;
        }

        public void setMeasuredDimension(int i, int i2, Rect rect) {
            int paddingRight = getPaddingRight() + getPaddingLeft() + rect.width();
            int paddingBottom = getPaddingBottom() + getPaddingTop() + rect.height();
            RecyclerView recyclerView = this.mRecyclerView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            this.mRecyclerView.setMeasuredDimension(chooseSize(i, paddingRight, ViewCompat.Api16Impl.getMinimumWidth(recyclerView)), chooseSize(i2, paddingBottom, ViewCompat.Api16Impl.getMinimumHeight(this.mRecyclerView)));
        }

        public final void setMeasuredDimensionFromChildren(int i, int i2) {
            int childCount = getChildCount();
            if (childCount == 0) {
                this.mRecyclerView.defaultOnMeasure(i, i2);
                return;
            }
            int i3 = VideoPlayer.MEDIA_ERROR_SYSTEM;
            int i4 = Integer.MAX_VALUE;
            int i5 = Integer.MIN_VALUE;
            int i6 = Integer.MAX_VALUE;
            for (int i7 = 0; i7 < childCount; i7++) {
                View childAt = getChildAt(i7);
                Rect rect = this.mRecyclerView.mTempRect;
                getDecoratedBoundsWithMargins(rect, childAt);
                int i8 = rect.left;
                if (i8 < i6) {
                    i6 = i8;
                }
                int i9 = rect.right;
                if (i9 > i3) {
                    i3 = i9;
                }
                int i10 = rect.top;
                if (i10 < i4) {
                    i4 = i10;
                }
                int i11 = rect.bottom;
                if (i11 > i5) {
                    i5 = i11;
                }
            }
            this.mRecyclerView.mTempRect.set(i6, i4, i3, i5);
            setMeasuredDimension(i, i2, this.mRecyclerView.mTempRect);
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
            this.mWidthMode = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
            this.mHeightMode = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
        }

        public final boolean shouldMeasureChild(View view, int i, int i2, LayoutParams layoutParams) {
            return (!view.isLayoutRequested() && this.mMeasurementCacheEnabled && isMeasurementUpToDate(view.getWidth(), i, ((ViewGroup.MarginLayoutParams) layoutParams).width) && isMeasurementUpToDate(view.getHeight(), i2, ((ViewGroup.MarginLayoutParams) layoutParams).height)) ? false : true;
        }

        public boolean shouldMeasureTwice() {
            return false;
        }

        public final boolean shouldReMeasureChild(View view, int i, int i2, LayoutParams layoutParams) {
            return (this.mMeasurementCacheEnabled && isMeasurementUpToDate(view.getMeasuredWidth(), i, ((ViewGroup.MarginLayoutParams) layoutParams).width) && isMeasurementUpToDate(view.getMeasuredHeight(), i2, ((ViewGroup.MarginLayoutParams) layoutParams).height)) ? false : true;
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, int i) {
            Log.e("SeslRecyclerView", "You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void startSmoothScroll(SmoothScroller smoothScroller) {
            SmoothScroller smoothScroller2 = this.mSmoothScroller;
            if (smoothScroller2 != null && smoothScroller != smoothScroller2 && smoothScroller2.mRunning) {
                smoothScroller2.stop();
            }
            this.mSmoothScroller = smoothScroller;
            RecyclerView recyclerView = this.mRecyclerView;
            ViewFlinger viewFlinger = recyclerView.mViewFlinger;
            RecyclerView.this.removeCallbacks(viewFlinger);
            viewFlinger.mOverScroller.abortAnimation();
            if (smoothScroller.mStarted) {
                Log.w("SeslRecyclerView", "An instance of " + smoothScroller.getClass().getSimpleName() + " was started more than once. Each instance of" + smoothScroller.getClass().getSimpleName() + " is intended to only be used once. You should create a new instance for each use.");
            }
            smoothScroller.mRecyclerView = recyclerView;
            smoothScroller.mLayoutManager = this;
            int i = smoothScroller.mTargetPosition;
            if (i == -1) {
                throw new IllegalArgumentException("Invalid target position");
            }
            recyclerView.mState.mTargetPosition = i;
            smoothScroller.mRunning = true;
            smoothScroller.mPendingInitialRun = true;
            smoothScroller.mTargetView = smoothScroller.findViewByPosition(i);
            smoothScroller.onStart();
            smoothScroller.mRecyclerView.mViewFlinger.postOnAnimation();
            smoothScroller.mStarted = true;
        }

        public boolean supportsPredictiveItemAnimations() {
            return this instanceof androidx.leanback.widget.GridLayoutManager;
        }

        public void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
            onItemsUpdated(i, i2);
        }

        public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int position = canScrollVertically() ? getPosition(view) : 0;
            int position2 = canScrollHorizontally() ? getPosition(view) : 0;
            Adapter adapter = this.mRecyclerView.mAdapter;
            adapter.getClass();
            if (adapter instanceof PreferenceGroupAdapter) {
                position = this.mRecyclerView.mAdapter.seslGetAccessibilityItemPosition(position);
                position2 = this.mRecyclerView.mAdapter.seslGetAccessibilityItemPosition(position2);
            }
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, position, 1, position2, 1, false));
        }

        public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
            return new LayoutParams(context, attributeSet);
        }

        public void onAdapterChanged(Adapter adapter) {
        }

        public void onDetachedFromWindow(RecyclerView recyclerView) {
        }

        public void onLayoutCompleted(State state) {
        }

        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        public void onScrollStateChanged(int i) {
        }

        public void scrollToPosition(int i) {
        }

        public void onItemsChanged() {
        }

        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z) {
            return requestChildRectangleOnScreen(recyclerView, view, rect, z, false);
        }

        public void collectInitialPrefetchPositions(int i, GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl) {
        }

        public void onItemsAdded(int i, int i2) {
        }

        public void onItemsMoved(int i, int i2) {
        }

        public void onItemsRemoved(int i, int i2) {
        }

        public void collectAdjacentPrefetchPositions(int i, int i2, State state, GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnChildAttachStateChangeListener {
        void onChildViewAttachedToWindow(View view);

        void onChildViewDetachedFromWindow(View view);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class OnFlingListener {
        public abstract boolean onFling(int i, int i2);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnItemTouchListener {
        boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent);

        void onRequestDisallowInterceptTouchEvent(boolean z);

        void onTouchEvent(MotionEvent motionEvent);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RecycledViewPool {
        public final SparseArray mScrap = new SparseArray();
        public int mAttachCountForClearing = 0;
        public final Set mAttachedAdaptersForPoolingContainer = Collections.newSetFromMap(new IdentityHashMap());

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ScrapData {
            public final ArrayList mScrapHeap = new ArrayList();
            public final int mMaxScrap = 5;
            public long mCreateRunningAverageNs = 0;
            public long mBindRunningAverageNs = 0;
        }

        public final ScrapData getScrapDataForType(int i) {
            SparseArray sparseArray = this.mScrap;
            ScrapData scrapData = (ScrapData) sparseArray.get(i);
            if (scrapData != null) {
                return scrapData;
            }
            ScrapData scrapData2 = new ScrapData();
            sparseArray.put(i, scrapData2);
            return scrapData2;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Recycler {
        public final ArrayList mAttachedScrap;
        public final ArrayList mCachedViews;
        public ArrayList mChangedScrap;
        public RecycledViewPool mRecyclerPool;
        public int mRequestedCacheMax;
        public final List mUnmodifiableAttachedScrap;
        public int mViewCacheMax;

        public Recycler() {
            ArrayList arrayList = new ArrayList();
            this.mAttachedScrap = arrayList;
            this.mChangedScrap = null;
            this.mCachedViews = new ArrayList();
            this.mUnmodifiableAttachedScrap = Collections.unmodifiableList(arrayList);
            this.mRequestedCacheMax = 2;
            this.mViewCacheMax = 2;
        }

        public final void addViewHolderToRecycledViewPool(ViewHolder viewHolder, boolean z) {
            RecyclerView.clearNestedRecyclerViewIfNotNested(viewHolder);
            RecyclerView recyclerView = RecyclerView.this;
            RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate = recyclerView.mAccessibilityDelegate;
            View view = viewHolder.itemView;
            if (recyclerViewAccessibilityDelegate != null) {
                AccessibilityDelegateCompat itemDelegate = recyclerViewAccessibilityDelegate.getItemDelegate();
                ViewCompat.setAccessibilityDelegate(view, itemDelegate instanceof RecyclerViewAccessibilityDelegate.ItemDelegate ? (AccessibilityDelegateCompat) ((WeakHashMap) ((RecyclerViewAccessibilityDelegate.ItemDelegate) itemDelegate).mOriginalItemDelegates).remove(view) : null);
            }
            if (z) {
                RecyclerListener recyclerListener = recyclerView.mRecyclerListener;
                if (recyclerListener != null) {
                    recyclerListener.onViewRecycled(viewHolder);
                }
                int size = recyclerView.mRecyclerListeners.size();
                for (int i = 0; i < size; i++) {
                    ((RecyclerListener) recyclerView.mRecyclerListeners.get(i)).onViewRecycled(viewHolder);
                }
                Adapter adapter = recyclerView.mAdapter;
                if (adapter != null) {
                    adapter.onViewRecycled(viewHolder);
                }
                if (recyclerView.mState != null) {
                    recyclerView.mViewInfoStore.removeViewHolder(viewHolder);
                }
            }
            viewHolder.mBindingAdapter = null;
            viewHolder.mOwnerRecyclerView = null;
            RecycledViewPool recycledViewPool = getRecycledViewPool();
            recycledViewPool.getClass();
            int i2 = viewHolder.mItemViewType;
            ArrayList arrayList = recycledViewPool.getScrapDataForType(i2).mScrapHeap;
            if (((RecycledViewPool.ScrapData) recycledViewPool.mScrap.get(i2)).mMaxScrap <= arrayList.size()) {
                PoolingContainer.callPoolingContainerOnRelease(view);
            } else {
                viewHolder.resetInternal();
                arrayList.add(viewHolder);
            }
        }

        public final int convertPreLayoutPositionToPostLayout(int i) {
            RecyclerView recyclerView = RecyclerView.this;
            if (i >= 0 && i < recyclerView.mState.getItemCount()) {
                return !recyclerView.mState.mInPreLayout ? i : recyclerView.mAdapterHelper.findPositionOffset(i, 0);
            }
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("invalid position ", i, ". State item count is ");
            m1m.append(recyclerView.mState.getItemCount());
            m1m.append(recyclerView.exceptionLabel());
            throw new IndexOutOfBoundsException(m1m.toString());
        }

        public final RecycledViewPool getRecycledViewPool() {
            if (this.mRecyclerPool == null) {
                this.mRecyclerPool = new RecycledViewPool();
                maybeSendPoolingContainerAttach();
            }
            return this.mRecyclerPool;
        }

        public final View getViewForPosition(int i) {
            return tryGetViewHolderForPositionByDeadline(i, Long.MAX_VALUE).itemView;
        }

        public final void maybeSendPoolingContainerAttach() {
            if (this.mRecyclerPool != null) {
                RecyclerView recyclerView = RecyclerView.this;
                if (recyclerView.mAdapter == null || !recyclerView.isAttachedToWindow()) {
                    return;
                }
                RecycledViewPool recycledViewPool = this.mRecyclerPool;
                recycledViewPool.mAttachedAdaptersForPoolingContainer.add(recyclerView.mAdapter);
            }
        }

        public final void poolingContainerDetach(Adapter adapter, boolean z) {
            RecycledViewPool recycledViewPool = this.mRecyclerPool;
            if (recycledViewPool == null) {
                return;
            }
            Set set = recycledViewPool.mAttachedAdaptersForPoolingContainer;
            set.remove(adapter);
            if (set.size() != 0 || z) {
                return;
            }
            int i = 0;
            while (true) {
                SparseArray sparseArray = recycledViewPool.mScrap;
                if (i >= sparseArray.size()) {
                    return;
                }
                ArrayList arrayList = ((RecycledViewPool.ScrapData) sparseArray.get(sparseArray.keyAt(i))).mScrapHeap;
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    PoolingContainer.callPoolingContainerOnRelease(((ViewHolder) arrayList.get(i2)).itemView);
                }
                i++;
            }
        }

        public final void recycleAndClearCachedViews() {
            ArrayList arrayList = this.mCachedViews;
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

        public final void recycleCachedViewAt(int i) {
            ArrayList arrayList = this.mCachedViews;
            addViewHolderToRecycledViewPool((ViewHolder) arrayList.get(i), true);
            arrayList.remove(i);
        }

        public final void recycleView(View view) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            boolean isTmpDetached = childViewHolderInt.isTmpDetached();
            RecyclerView recyclerView = RecyclerView.this;
            if (isTmpDetached) {
                recyclerView.removeDetachedView(view, false);
            }
            if (childViewHolderInt.isScrap()) {
                childViewHolderInt.mScrapContainer.unscrapView(childViewHolderInt);
            } else if (childViewHolderInt.wasReturnedFromScrap()) {
                childViewHolderInt.mFlags &= -33;
            }
            recycleViewHolderInternal(childViewHolderInt);
            if (recyclerView.mItemAnimator == null || childViewHolderInt.isRecyclable()) {
                return;
            }
            recyclerView.mItemAnimator.endAnimation(childViewHolderInt);
        }

        /* JADX WARN: Removed duplicated region for block: B:36:0x0050  */
        /* JADX WARN: Removed duplicated region for block: B:81:0x00bf  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void recycleViewHolderInternal(ViewHolder viewHolder) {
            boolean z;
            Adapter adapter;
            boolean z2;
            boolean z3;
            boolean z4;
            boolean z5;
            boolean isScrap = viewHolder.isScrap();
            RecyclerView recyclerView = RecyclerView.this;
            View view = viewHolder.itemView;
            if (isScrap || view.getParent() != null) {
                StringBuilder sb = new StringBuilder("Scrapped or attached views may not be recycled. isScrap:");
                sb.append(viewHolder.isScrap());
                sb.append(" isAttached:");
                sb.append(view.getParent() != null);
                sb.append(recyclerView.exceptionLabel());
                throw new IllegalArgumentException(sb.toString());
            }
            if (viewHolder.isTmpDetached()) {
                throw new IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + viewHolder + recyclerView.exceptionLabel());
            }
            if (viewHolder.shouldIgnore()) {
                throw new IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle." + recyclerView.exceptionLabel());
            }
            if ((viewHolder.mFlags & 16) == 0) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (ViewCompat.Api16Impl.hasTransientState(view)) {
                    z = true;
                    adapter = recyclerView.mAdapter;
                    if (!(adapter == null && z && adapter.onFailedToRecycleView(viewHolder)) || viewHolder.isRecyclable()) {
                        if (this.mViewCacheMax > 0) {
                            if (!((viewHolder.mFlags & 526) != 0)) {
                                ArrayList arrayList = this.mCachedViews;
                                int size = arrayList.size();
                                if (size >= this.mViewCacheMax && size > 0) {
                                    recycleCachedViewAt(0);
                                    size--;
                                }
                                if (RecyclerView.ALLOW_THREAD_GAP_WORK && size > 0) {
                                    GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView.mPrefetchRegistry;
                                    int i = viewHolder.mPosition;
                                    if (layoutPrefetchRegistryImpl.mPrefetchArray != null) {
                                        int i2 = layoutPrefetchRegistryImpl.mCount * 2;
                                        for (int i3 = 0; i3 < i2; i3 += 2) {
                                            if (layoutPrefetchRegistryImpl.mPrefetchArray[i3] == i) {
                                                z4 = true;
                                                break;
                                            }
                                        }
                                    }
                                    z4 = false;
                                    if (!z4) {
                                        do {
                                            size--;
                                            if (size < 0) {
                                                break;
                                            }
                                            int i4 = ((ViewHolder) arrayList.get(size)).mPosition;
                                            GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl2 = recyclerView.mPrefetchRegistry;
                                            if (layoutPrefetchRegistryImpl2.mPrefetchArray != null) {
                                                int i5 = layoutPrefetchRegistryImpl2.mCount * 2;
                                                for (int i6 = 0; i6 < i5; i6 += 2) {
                                                    if (layoutPrefetchRegistryImpl2.mPrefetchArray[i6] == i4) {
                                                        z5 = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            z5 = false;
                                        } while (z5);
                                        size++;
                                    }
                                }
                                arrayList.add(size, viewHolder);
                                z2 = true;
                                if (!z2) {
                                    addViewHolderToRecycledViewPool(viewHolder, true);
                                    r1 = true;
                                }
                                z3 = r1;
                                r1 = z2;
                            }
                        }
                        z2 = false;
                        if (!z2) {
                        }
                        z3 = r1;
                        r1 = z2;
                    } else {
                        z3 = false;
                    }
                    recyclerView.mViewInfoStore.removeViewHolder(viewHolder);
                    if (r1 && !z3 && z) {
                        PoolingContainer.callPoolingContainerOnRelease(view);
                        viewHolder.mBindingAdapter = null;
                        viewHolder.mOwnerRecyclerView = null;
                        return;
                    }
                    return;
                }
            }
            z = false;
            adapter = recyclerView.mAdapter;
            if (adapter == null && z && adapter.onFailedToRecycleView(viewHolder)) {
            }
            if (this.mViewCacheMax > 0) {
            }
            z2 = false;
            if (!z2) {
            }
            z3 = r1;
            r1 = z2;
            recyclerView.mViewInfoStore.removeViewHolder(viewHolder);
            if (r1) {
            }
        }

        public final void scrapView(View view) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            int i = childViewHolderInt.mFlags;
            boolean z = (i & 12) != 0;
            RecyclerView recyclerView = RecyclerView.this;
            if (!z) {
                if ((i & 2) != 0) {
                    ItemAnimator itemAnimator = recyclerView.mItemAnimator;
                    if (!(itemAnimator == null || itemAnimator.canReuseUpdatedViewHolder(childViewHolderInt, childViewHolderInt.getUnmodifiedPayloads()))) {
                        if (this.mChangedScrap == null) {
                            this.mChangedScrap = new ArrayList();
                        }
                        childViewHolderInt.mScrapContainer = this;
                        childViewHolderInt.mInChangeScrap = true;
                        this.mChangedScrap.add(childViewHolderInt);
                        return;
                    }
                }
            }
            if (childViewHolderInt.isInvalid() && !childViewHolderInt.isRemoved() && !recyclerView.mAdapter.mHasStableIds) {
                throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool." + recyclerView.exceptionLabel());
            }
            childViewHolderInt.mScrapContainer = this;
            childViewHolderInt.mInChangeScrap = false;
            this.mAttachedScrap.add(childViewHolderInt);
        }

        /* JADX WARN: Code restructure failed: missing block: B:271:0x0476, code lost:
        
            if (r7.isInvalid() == false) goto L248;
         */
        /* JADX WARN: Removed duplicated region for block: B:122:0x0225  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0080  */
        /* JADX WARN: Removed duplicated region for block: B:235:0x0411  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x008b  */
        /* JADX WARN: Removed duplicated region for block: B:253:0x05a4  */
        /* JADX WARN: Removed duplicated region for block: B:256:0x05c5 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:260:0x05ae  */
        /* JADX WARN: Removed duplicated region for block: B:266:0x0468  */
        /* JADX WARN: Removed duplicated region for block: B:275:0x0499  */
        /* JADX WARN: Removed duplicated region for block: B:285:0x04c0  */
        /* JADX WARN: Removed duplicated region for block: B:287:0x04c5  */
        /* JADX WARN: Removed duplicated region for block: B:293:0x04eb  */
        /* JADX WARN: Removed duplicated region for block: B:302:0x0520  */
        /* JADX WARN: Removed duplicated region for block: B:309:0x053a  */
        /* JADX WARN: Removed duplicated region for block: B:329:0x0596  */
        /* JADX WARN: Removed duplicated region for block: B:332:0x058f  */
        /* JADX WARN: Removed duplicated region for block: B:334:0x04c2  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final ViewHolder tryGetViewHolderForPositionByDeadline(int i, long j) {
            ViewHolder viewHolder;
            boolean z;
            boolean z2;
            long j2;
            AccessibilityManager accessibilityManager;
            boolean z3;
            boolean z4;
            boolean z5;
            ViewGroup.LayoutParams layoutParams;
            LayoutParams layoutParams2;
            RecyclerView findNestedRecyclerView;
            ViewHolder viewHolder2;
            View view;
            ViewHolder viewHolder3;
            boolean z6;
            int size;
            int findPositionOffset;
            RecyclerView recyclerView = RecyclerView.this;
            if (i < 0 || i >= recyclerView.mState.getItemCount()) {
                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("Invalid item position ", i, "(", i, "). Item count:");
                m45m.append(recyclerView.mState.getItemCount());
                m45m.append(recyclerView.exceptionLabel());
                throw new IndexOutOfBoundsException(m45m.toString());
            }
            if (recyclerView.mState.mInPreLayout) {
                ArrayList arrayList = this.mChangedScrap;
                if (arrayList != null && (size = arrayList.size()) != 0) {
                    int i2 = 0;
                    while (true) {
                        if (i2 < size) {
                            viewHolder = (ViewHolder) this.mChangedScrap.get(i2);
                            if (!viewHolder.wasReturnedFromScrap() && viewHolder.getLayoutPosition() == i) {
                                viewHolder.addFlags(32);
                                break;
                            }
                            i2++;
                        } else if (recyclerView.mAdapter.mHasStableIds && (findPositionOffset = recyclerView.mAdapterHelper.findPositionOffset(i, 0)) > 0 && findPositionOffset < recyclerView.mAdapter.getItemCount()) {
                            long itemId = recyclerView.mAdapter.getItemId(findPositionOffset);
                            for (int i3 = 0; i3 < size; i3++) {
                                ViewHolder viewHolder4 = (ViewHolder) this.mChangedScrap.get(i3);
                                if (!viewHolder4.wasReturnedFromScrap() && viewHolder4.mItemId == itemId) {
                                    viewHolder4.addFlags(32);
                                    viewHolder = viewHolder4;
                                    break;
                                }
                            }
                        }
                    }
                    if (viewHolder != null) {
                        z = true;
                        ArrayList arrayList2 = this.mCachedViews;
                        ArrayList arrayList3 = this.mAttachedScrap;
                        if (viewHolder == null) {
                            int size2 = arrayList3.size();
                            for (int i4 = 0; i4 < size2; i4++) {
                                viewHolder3 = (ViewHolder) arrayList3.get(i4);
                                if (!viewHolder3.wasReturnedFromScrap() && viewHolder3.getLayoutPosition() == i && !viewHolder3.isInvalid() && (recyclerView.mState.mInPreLayout || !viewHolder3.isRemoved())) {
                                    viewHolder3.addFlags(32);
                                    break;
                                }
                            }
                            ChildHelper childHelper = recyclerView.mChildHelper;
                            ArrayList arrayList4 = (ArrayList) childHelper.mHiddenViews;
                            int size3 = arrayList4.size();
                            int i5 = 0;
                            while (true) {
                                if (i5 >= size3) {
                                    view = null;
                                    break;
                                }
                                view = (View) arrayList4.get(i5);
                                ((C045510) childHelper.mCallback).getClass();
                                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
                                if (childViewHolderInt.getLayoutPosition() == i && !childViewHolderInt.isInvalid() && !childViewHolderInt.isRemoved()) {
                                    break;
                                }
                                i5++;
                            }
                            if (view != null) {
                                viewHolder = RecyclerView.getChildViewHolderInt(view);
                                ChildHelper childHelper2 = recyclerView.mChildHelper;
                                int indexOfChild = RecyclerView.this.indexOfChild(view);
                                if (indexOfChild < 0) {
                                    throw new IllegalArgumentException("view is not a child, cannot hide " + view);
                                }
                                ChildHelper.Bucket bucket = childHelper2.mBucket;
                                if (!bucket.get(indexOfChild)) {
                                    throw new RuntimeException("trying to unhide a view that was not hidden" + view);
                                }
                                bucket.clear(indexOfChild);
                                childHelper2.unhideViewInternal(view);
                                int indexOfChild2 = recyclerView.mChildHelper.indexOfChild(view);
                                if (indexOfChild2 == -1) {
                                    throw new IllegalStateException("layout index should not be -1 after unhiding a view:" + viewHolder + recyclerView.exceptionLabel());
                                }
                                recyclerView.mChildHelper.detachViewFromParent(indexOfChild2);
                                scrapView(view);
                                viewHolder.addFlags(8224);
                            } else {
                                int size4 = arrayList2.size();
                                for (int i6 = 0; i6 < size4; i6++) {
                                    viewHolder3 = (ViewHolder) arrayList2.get(i6);
                                    if (!viewHolder3.isInvalid() && viewHolder3.getLayoutPosition() == i && !viewHolder3.isAttachedToTransitionOverlay()) {
                                        arrayList2.remove(i6);
                                        viewHolder = viewHolder3;
                                    }
                                }
                                viewHolder = null;
                            }
                            if (viewHolder != null) {
                                if (viewHolder.isRemoved()) {
                                    z6 = recyclerView.mState.mInPreLayout;
                                } else {
                                    int i7 = viewHolder.mPosition;
                                    if (i7 < 0 || i7 >= recyclerView.mAdapter.getItemCount()) {
                                        throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + viewHolder + recyclerView.exceptionLabel());
                                    }
                                    if (recyclerView.mState.mInPreLayout || recyclerView.mAdapter.getItemViewType(viewHolder.mPosition) == viewHolder.mItemViewType) {
                                        Adapter adapter = recyclerView.mAdapter;
                                        if (!adapter.mHasStableIds || viewHolder.mItemId == adapter.getItemId(viewHolder.mPosition)) {
                                            z6 = true;
                                        }
                                    }
                                    z6 = false;
                                }
                                if (z6) {
                                    z = true;
                                } else {
                                    viewHolder.addFlags(4);
                                    if (viewHolder.isScrap()) {
                                        recyclerView.removeDetachedView(viewHolder.itemView, false);
                                        viewHolder.mScrapContainer.unscrapView(viewHolder);
                                    } else if (viewHolder.wasReturnedFromScrap()) {
                                        viewHolder.mFlags &= -33;
                                    }
                                    recycleViewHolderInternal(viewHolder);
                                    viewHolder = null;
                                }
                            }
                        }
                        if (viewHolder == null) {
                            int findPositionOffset2 = recyclerView.mAdapterHelper.findPositionOffset(i, 0);
                            if (findPositionOffset2 < 0 || findPositionOffset2 >= recyclerView.mAdapter.getItemCount()) {
                                StringBuilder m45m2 = GridLayoutManager$$ExternalSyntheticOutline0.m45m("Inconsistency detected. Invalid item position ", i, "(offset:", findPositionOffset2, ").state:");
                                m45m2.append(recyclerView.mState.getItemCount());
                                m45m2.append(recyclerView.exceptionLabel());
                                throw new IndexOutOfBoundsException(m45m2.toString());
                            }
                            int itemViewType = recyclerView.mAdapter.getItemViewType(findPositionOffset2);
                            Adapter adapter2 = recyclerView.mAdapter;
                            if (adapter2.mHasStableIds) {
                                long itemId2 = adapter2.getItemId(findPositionOffset2);
                                int size5 = arrayList3.size() - 1;
                                while (true) {
                                    if (size5 >= 0) {
                                        ViewHolder viewHolder5 = (ViewHolder) arrayList3.get(size5);
                                        ArrayList arrayList5 = arrayList2;
                                        if (viewHolder5.mItemId == itemId2 && !viewHolder5.wasReturnedFromScrap()) {
                                            if (itemViewType == viewHolder5.mItemViewType) {
                                                viewHolder5.addFlags(32);
                                                if (viewHolder5.isRemoved() && !recyclerView.mState.mInPreLayout) {
                                                    viewHolder5.mFlags = (viewHolder5.mFlags & (-15)) | 2;
                                                }
                                                viewHolder = viewHolder5;
                                            } else {
                                                arrayList3.remove(size5);
                                                View view2 = viewHolder5.itemView;
                                                recyclerView.removeDetachedView(view2, false);
                                                ViewHolder childViewHolderInt2 = RecyclerView.getChildViewHolderInt(view2);
                                                childViewHolderInt2.mScrapContainer = null;
                                                childViewHolderInt2.mInChangeScrap = false;
                                                childViewHolderInt2.mFlags &= -33;
                                                recycleViewHolderInternal(childViewHolderInt2);
                                            }
                                        }
                                        size5--;
                                        arrayList2 = arrayList5;
                                    } else {
                                        ArrayList arrayList6 = arrayList2;
                                        int size6 = arrayList6.size() - 1;
                                        while (true) {
                                            if (size6 < 0) {
                                                break;
                                            }
                                            ArrayList arrayList7 = arrayList6;
                                            viewHolder = (ViewHolder) arrayList7.get(size6);
                                            if (viewHolder.mItemId != itemId2 || viewHolder.isAttachedToTransitionOverlay()) {
                                                size6--;
                                                arrayList6 = arrayList7;
                                            } else if (itemViewType == viewHolder.mItemViewType) {
                                                arrayList7.remove(size6);
                                            } else {
                                                recycleCachedViewAt(size6);
                                            }
                                        }
                                        viewHolder = null;
                                    }
                                }
                                if (viewHolder != null) {
                                    viewHolder.mPosition = findPositionOffset2;
                                    z = true;
                                }
                            }
                            if (viewHolder == null) {
                                SparseArray sparseArray = getRecycledViewPool().mScrap;
                                RecycledViewPool.ScrapData scrapData = (RecycledViewPool.ScrapData) sparseArray.get(itemViewType);
                                if (scrapData != null) {
                                    ArrayList arrayList8 = scrapData.mScrapHeap;
                                    if (!arrayList8.isEmpty()) {
                                        int size7 = arrayList8.size();
                                        while (true) {
                                            size7--;
                                            if (size7 < 0) {
                                                break;
                                            }
                                            if (arrayList8.get(size7) != null) {
                                                if (!((ViewHolder) arrayList8.get(size7)).isAttachedToTransitionOverlay()) {
                                                    viewHolder2 = (ViewHolder) arrayList8.remove(size7);
                                                    break;
                                                }
                                            } else {
                                                StringBuilder m1m = AbstractC0000x2c234b15.m1m("ViewHolder object null when getRecycledView is in progress. pos= ", size7, " size=");
                                                m1m.append(arrayList8.size());
                                                m1m.append(" max= ");
                                                m1m.append(scrapData.mMaxScrap);
                                                m1m.append(" holder= ");
                                                int i8 = 0;
                                                for (int i9 = 0; i9 < sparseArray.size(); i9++) {
                                                    ArrayList arrayList9 = ((RecycledViewPool.ScrapData) sparseArray.valueAt(i9)).mScrapHeap;
                                                    if (arrayList9 != null) {
                                                        i8 = arrayList9.size() + i8;
                                                    }
                                                }
                                                m1m.append(i8);
                                                m1m.append(" scrapHeap= ");
                                                m1m.append(arrayList8);
                                                Log.e("SeslRecyclerView", m1m.toString());
                                            }
                                        }
                                    }
                                }
                                viewHolder2 = null;
                                if (viewHolder2 != null) {
                                    viewHolder2.resetInternal();
                                    int[] iArr = RecyclerView.NESTED_SCROLLING_ATTRS;
                                }
                                viewHolder = viewHolder2;
                            }
                            if (viewHolder == null) {
                                long nanoTime = recyclerView.getNanoTime();
                                if (j != Long.MAX_VALUE) {
                                    long j3 = this.mRecyclerPool.getScrapDataForType(itemViewType).mCreateRunningAverageNs;
                                    if (!(j3 == 0 || j3 + nanoTime < j)) {
                                        return null;
                                    }
                                }
                                Adapter adapter3 = recyclerView.mAdapter;
                                adapter3.getClass();
                                try {
                                    Trace.beginSection("RV CreateView");
                                    viewHolder = adapter3.onCreateViewHolder(recyclerView, itemViewType);
                                    if (viewHolder.itemView.getParent() != null) {
                                        throw new IllegalStateException("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
                                    }
                                    viewHolder.mItemViewType = itemViewType;
                                    Trace.endSection();
                                    if (RecyclerView.ALLOW_THREAD_GAP_WORK && (findNestedRecyclerView = RecyclerView.findNestedRecyclerView(viewHolder.itemView)) != null) {
                                        viewHolder.mNestedRecyclerView = new WeakReference(findNestedRecyclerView);
                                    }
                                    long nanoTime2 = recyclerView.getNanoTime() - nanoTime;
                                    RecycledViewPool.ScrapData scrapDataForType = this.mRecyclerPool.getScrapDataForType(itemViewType);
                                    long j4 = scrapDataForType.mCreateRunningAverageNs;
                                    if (j4 != 0) {
                                        nanoTime2 = (nanoTime2 / 4) + ((j4 / 4) * 3);
                                    }
                                    scrapDataForType.mCreateRunningAverageNs = nanoTime2;
                                } finally {
                                    Trace.endSection();
                                }
                            }
                        }
                        if (z) {
                            State state = recyclerView.mState;
                            if (!state.mInPreLayout) {
                                int i10 = viewHolder.mFlags;
                                if ((i10 & 8192) != 0) {
                                    viewHolder.mFlags = (i10 & (-8193)) | 0;
                                    if (state.mRunSimpleAnimations) {
                                        ItemAnimator.buildAdapterChangeFlagsForAnimations(viewHolder);
                                        ItemAnimator itemAnimator = recyclerView.mItemAnimator;
                                        viewHolder.getUnmodifiedPayloads();
                                        itemAnimator.getClass();
                                        ItemAnimator.ItemHolderInfo itemHolderInfo = new ItemAnimator.ItemHolderInfo();
                                        View view3 = viewHolder.itemView;
                                        itemHolderInfo.left = view3.getLeft();
                                        itemHolderInfo.top = view3.getTop();
                                        view3.getRight();
                                        view3.getBottom();
                                        recyclerView.recordAnimationInfoIfBouncedHiddenView(viewHolder, itemHolderInfo);
                                    }
                                }
                            }
                        }
                        if (recyclerView.mState.mInPreLayout || !viewHolder.isBound()) {
                            if (viewHolder.isBound()) {
                                if (!((viewHolder.mFlags & 2) != 0)) {
                                }
                            }
                            int findPositionOffset3 = recyclerView.mAdapterHelper.findPositionOffset(i, 0);
                            viewHolder.mBindingAdapter = null;
                            viewHolder.mOwnerRecyclerView = recyclerView;
                            int i11 = viewHolder.mItemViewType;
                            long nanoTime3 = recyclerView.getNanoTime();
                            if (j != Long.MAX_VALUE) {
                                long j5 = this.mRecyclerPool.getScrapDataForType(i11).mBindRunningAverageNs;
                                if (!(j5 == 0 || j5 + nanoTime3 < j)) {
                                    z3 = true;
                                    z5 = false;
                                    z4 = false;
                                    layoutParams = viewHolder.itemView.getLayoutParams();
                                    View view4 = viewHolder.itemView;
                                    if (layoutParams != null) {
                                        layoutParams2 = (LayoutParams) recyclerView.generateDefaultLayoutParams();
                                        view4.setLayoutParams(layoutParams2);
                                    } else if (recyclerView.checkLayoutParams(layoutParams)) {
                                        layoutParams2 = (LayoutParams) layoutParams;
                                    } else {
                                        layoutParams2 = (LayoutParams) recyclerView.generateLayoutParams(layoutParams);
                                        view4.setLayoutParams(layoutParams2);
                                    }
                                    layoutParams2.mViewHolder = viewHolder;
                                    if (z || !z4) {
                                        z3 = z5;
                                    }
                                    layoutParams2.mPendingInvalidate = z3;
                                    return viewHolder;
                                }
                            }
                            Adapter adapter4 = recyclerView.mAdapter;
                            adapter4.getClass();
                            z2 = viewHolder.mBindingAdapter != null;
                            if (z2) {
                                viewHolder.mPosition = findPositionOffset3;
                                if (adapter4.mHasStableIds) {
                                    viewHolder.mItemId = adapter4.getItemId(findPositionOffset3);
                                }
                                viewHolder.mFlags = (viewHolder.mFlags & (-520)) | 1;
                                Trace.beginSection("RV OnBindView");
                            }
                            viewHolder.mBindingAdapter = adapter4;
                            adapter4.onBindViewHolder(viewHolder, findPositionOffset3, viewHolder.getUnmodifiedPayloads());
                            View view5 = viewHolder.itemView;
                            if (z2) {
                                List list = viewHolder.mPayloads;
                                if (list != null) {
                                    ((ArrayList) list).clear();
                                }
                                viewHolder.mFlags &= KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN;
                                ViewGroup.LayoutParams layoutParams3 = view5.getLayoutParams();
                                if (layoutParams3 instanceof LayoutParams) {
                                    ((LayoutParams) layoutParams3).mInsetsDirty = true;
                                }
                            }
                            long nanoTime4 = recyclerView.getNanoTime() - nanoTime3;
                            RecycledViewPool.ScrapData scrapDataForType2 = this.mRecyclerPool.getScrapDataForType(viewHolder.mItemViewType);
                            j2 = scrapDataForType2.mBindRunningAverageNs;
                            if (j2 != 0) {
                                nanoTime4 = (nanoTime4 / 4) + ((j2 / 4) * 3);
                            }
                            scrapDataForType2.mBindRunningAverageNs = nanoTime4;
                            accessibilityManager = recyclerView.mAccessibilityManager;
                            if (accessibilityManager == null && accessibilityManager.isEnabled()) {
                                z3 = true;
                            } else {
                                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                if (ViewCompat.Api16Impl.getImportantForAccessibility(view5) == 0) {
                                    z3 = true;
                                    ViewCompat.Api16Impl.setImportantForAccessibility(view5, 1);
                                } else {
                                    z3 = true;
                                }
                                if (recyclerView.mAccessibilityDelegate == null) {
                                    RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate = new RecyclerViewAccessibilityDelegate(recyclerView);
                                    recyclerView.mAccessibilityDelegate = recyclerViewAccessibilityDelegate;
                                    ViewCompat.setAccessibilityDelegate(recyclerView, recyclerViewAccessibilityDelegate);
                                    Log.d("SeslRecyclerView", "attachAccessibilityDelegate: mAccessibilityDelegate is null, so re create");
                                }
                                AccessibilityDelegateCompat itemDelegate = recyclerView.mAccessibilityDelegate.getItemDelegate();
                                if (itemDelegate instanceof RecyclerViewAccessibilityDelegate.ItemDelegate) {
                                    RecyclerViewAccessibilityDelegate.ItemDelegate itemDelegate2 = (RecyclerViewAccessibilityDelegate.ItemDelegate) itemDelegate;
                                    itemDelegate2.getClass();
                                    View.AccessibilityDelegate accessibilityDelegate = ViewCompat.Api29Impl.getAccessibilityDelegate(view5);
                                    AccessibilityDelegateCompat accessibilityDelegateCompat = accessibilityDelegate != null ? accessibilityDelegate instanceof AccessibilityDelegateCompat.AccessibilityDelegateAdapter ? ((AccessibilityDelegateCompat.AccessibilityDelegateAdapter) accessibilityDelegate).mCompat : new AccessibilityDelegateCompat(accessibilityDelegate) : null;
                                    if (accessibilityDelegateCompat != null && accessibilityDelegateCompat != itemDelegate2) {
                                        ((WeakHashMap) itemDelegate2.mOriginalItemDelegates).put(view5, accessibilityDelegateCompat);
                                    }
                                }
                                ViewCompat.setAccessibilityDelegate(view5, itemDelegate);
                            }
                            if (recyclerView.mState.mInPreLayout) {
                                viewHolder.mPreLayoutPosition = i;
                            }
                            z4 = z3;
                            z5 = false;
                            layoutParams = viewHolder.itemView.getLayoutParams();
                            View view42 = viewHolder.itemView;
                            if (layoutParams != null) {
                            }
                            layoutParams2.mViewHolder = viewHolder;
                            if (z) {
                            }
                            z3 = z5;
                            layoutParams2.mPendingInvalidate = z3;
                            return viewHolder;
                        }
                        viewHolder.mPreLayoutPosition = i;
                        z3 = true;
                        z5 = false;
                        z4 = false;
                        layoutParams = viewHolder.itemView.getLayoutParams();
                        View view422 = viewHolder.itemView;
                        if (layoutParams != null) {
                        }
                        layoutParams2.mViewHolder = viewHolder;
                        if (z) {
                        }
                        z3 = z5;
                        layoutParams2.mPendingInvalidate = z3;
                        return viewHolder;
                    }
                }
                viewHolder = null;
                if (viewHolder != null) {
                }
            } else {
                viewHolder = null;
            }
            z = false;
            ArrayList arrayList22 = this.mCachedViews;
            ArrayList arrayList32 = this.mAttachedScrap;
            if (viewHolder == null) {
            }
            if (viewHolder == null) {
            }
            if (z) {
            }
            if (recyclerView.mState.mInPreLayout) {
            }
            if (viewHolder.isBound()) {
            }
            int findPositionOffset32 = recyclerView.mAdapterHelper.findPositionOffset(i, 0);
            viewHolder.mBindingAdapter = null;
            viewHolder.mOwnerRecyclerView = recyclerView;
            int i112 = viewHolder.mItemViewType;
            long nanoTime32 = recyclerView.getNanoTime();
            if (j != Long.MAX_VALUE) {
            }
            Adapter adapter42 = recyclerView.mAdapter;
            adapter42.getClass();
            if (viewHolder.mBindingAdapter != null) {
            }
            if (z2) {
            }
            viewHolder.mBindingAdapter = adapter42;
            adapter42.onBindViewHolder(viewHolder, findPositionOffset32, viewHolder.getUnmodifiedPayloads());
            View view52 = viewHolder.itemView;
            if (z2) {
            }
            long nanoTime42 = recyclerView.getNanoTime() - nanoTime32;
            RecycledViewPool.ScrapData scrapDataForType22 = this.mRecyclerPool.getScrapDataForType(viewHolder.mItemViewType);
            j2 = scrapDataForType22.mBindRunningAverageNs;
            if (j2 != 0) {
            }
            scrapDataForType22.mBindRunningAverageNs = nanoTime42;
            accessibilityManager = recyclerView.mAccessibilityManager;
            if (accessibilityManager == null && accessibilityManager.isEnabled()) {
            }
            if (recyclerView.mState.mInPreLayout) {
            }
            z4 = z3;
            z5 = false;
            layoutParams = viewHolder.itemView.getLayoutParams();
            View view4222 = viewHolder.itemView;
            if (layoutParams != null) {
            }
            layoutParams2.mViewHolder = viewHolder;
            if (z) {
            }
            z3 = z5;
            layoutParams2.mPendingInvalidate = z3;
            return viewHolder;
        }

        public final void unscrapView(ViewHolder viewHolder) {
            if (viewHolder.mInChangeScrap) {
                this.mChangedScrap.remove(viewHolder);
            } else {
                this.mAttachedScrap.remove(viewHolder);
            }
            viewHolder.mScrapContainer = null;
            viewHolder.mInChangeScrap = false;
            viewHolder.mFlags &= -33;
        }

        public final void updateViewCacheSize() {
            LayoutManager layoutManager = RecyclerView.this.mLayout;
            this.mViewCacheMax = this.mRequestedCacheMax + (layoutManager != null ? layoutManager.mPrefetchMaxCountObserved : 0);
            ArrayList arrayList = this.mCachedViews;
            for (int size = arrayList.size() - 1; size >= 0 && arrayList.size() > this.mViewCacheMax; size--) {
                recycleCachedViewAt(size);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface RecyclerListener {
        void onViewRecycled(ViewHolder viewHolder);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RecyclerViewDataObserver extends AdapterDataObserver {
        public RecyclerViewDataObserver() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onChanged() {
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.assertNotInLayoutOrScroll(null);
            recyclerView.mState.mStructureChanged = true;
            recyclerView.processDataSetCompletelyChanged(true);
            if (!recyclerView.mAdapterHelper.hasPendingUpdates()) {
                recyclerView.requestLayout();
            }
            SeslRecyclerViewFastScroller seslRecyclerViewFastScroller = recyclerView.mFastScroller;
            if (seslRecyclerViewFastScroller != null) {
                seslRecyclerViewFastScroller.mListAdapter = null;
            }
            recyclerView.getClass();
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0022, code lost:
        
            if (r2.size() == 1) goto L9;
         */
        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onItemRangeChanged(int i, int i2, Object obj) {
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.assertNotInLayoutOrScroll(null);
            AdapterHelper adapterHelper = recyclerView.mAdapterHelper;
            boolean z = true;
            if (i2 < 1) {
                adapterHelper.getClass();
            } else {
                ArrayList arrayList = adapterHelper.mPendingUpdates;
                arrayList.add(adapterHelper.obtainUpdateOp(4, i, i2, obj));
                adapterHelper.mExistingUpdateTypes |= 4;
            }
            z = false;
            if (z) {
                triggerUpdateProcessor();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0021, code lost:
        
            if (r3.size() == 1) goto L9;
         */
        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onItemRangeInserted(int i, int i2) {
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.assertNotInLayoutOrScroll(null);
            AdapterHelper adapterHelper = recyclerView.mAdapterHelper;
            boolean z = true;
            if (i2 < 1) {
                adapterHelper.getClass();
            } else {
                ArrayList arrayList = adapterHelper.mPendingUpdates;
                arrayList.add(adapterHelper.obtainUpdateOp(1, i, i2, null));
                adapterHelper.mExistingUpdateTypes |= 1;
            }
            z = false;
            if (z) {
                triggerUpdateProcessor();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:4:0x0023, code lost:
        
            if (r2.size() == 1) goto L9;
         */
        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onItemRangeMoved(int i, int i2) {
            boolean z;
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.assertNotInLayoutOrScroll(null);
            AdapterHelper adapterHelper = recyclerView.mAdapterHelper;
            adapterHelper.getClass();
            if (i != i2) {
                ArrayList arrayList = adapterHelper.mPendingUpdates;
                arrayList.add(adapterHelper.obtainUpdateOp(8, i, i2, null));
                adapterHelper.mExistingUpdateTypes |= 8;
                z = true;
            }
            z = false;
            if (z) {
                triggerUpdateProcessor();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0022, code lost:
        
            if (r3.size() == 1) goto L9;
         */
        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onItemRangeRemoved(int i, int i2) {
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.assertNotInLayoutOrScroll(null);
            AdapterHelper adapterHelper = recyclerView.mAdapterHelper;
            boolean z = true;
            if (i2 < 1) {
                adapterHelper.getClass();
            } else {
                ArrayList arrayList = adapterHelper.mPendingUpdates;
                arrayList.add(adapterHelper.obtainUpdateOp(2, i, i2, null));
                adapterHelper.mExistingUpdateTypes |= 2;
            }
            z = false;
            if (z) {
                triggerUpdateProcessor();
            }
        }

        public final void triggerUpdateProcessor() {
            boolean z = RecyclerView.POST_UPDATES_ON_ANIMATION;
            RecyclerView recyclerView = RecyclerView.this;
            if (!z || !recyclerView.mHasFixedSize || !recyclerView.mIsAttached) {
                recyclerView.mAdapterUpdateDuringMeasure = true;
                recyclerView.requestLayout();
            } else {
                RunnableC04541 runnableC04541 = recyclerView.mUpdateChildViewsRunnable;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postOnAnimation(recyclerView, runnableC04541);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum ScrollArrowDirection {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class SmoothScroller {
        public LayoutManager mLayoutManager;
        public boolean mPendingInitialRun;
        public RecyclerView mRecyclerView;
        public boolean mRunning;
        public boolean mStarted;
        public View mTargetView;
        public int mTargetPosition = -1;
        public final Action mRecyclingAction = new Action(0, 0);

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Action {
            public boolean mChanged;
            public int mConsecutiveUpdates;
            public int mDuration;
            public int mDx;
            public int mDy;
            public Interpolator mInterpolator;
            public int mJumpToPosition;

            public Action(int i, int i2) {
                this(i, i2, VideoPlayer.MEDIA_ERROR_SYSTEM, null);
            }

            public final void runIfNecessary(RecyclerView recyclerView) {
                int i = this.mJumpToPosition;
                if (i >= 0) {
                    this.mJumpToPosition = -1;
                    recyclerView.jumpToPositionForSmoothScroller(i);
                    this.mChanged = false;
                    return;
                }
                if (!this.mChanged) {
                    this.mConsecutiveUpdates = 0;
                    return;
                }
                Interpolator interpolator = this.mInterpolator;
                if (interpolator != null && this.mDuration < 1) {
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                }
                int i2 = this.mDuration;
                if (i2 < 1) {
                    throw new IllegalStateException("Scroll duration must be a positive number");
                }
                recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, i2, interpolator);
                int i3 = this.mConsecutiveUpdates + 1;
                this.mConsecutiveUpdates = i3;
                if (i3 > 10) {
                    Log.e("SeslRecyclerView", "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                }
                this.mChanged = false;
            }

            public final void update(int i, int i2, int i3, Interpolator interpolator) {
                this.mDx = i;
                this.mDy = i2;
                this.mDuration = i3;
                this.mInterpolator = interpolator;
                this.mChanged = true;
            }

            public Action(int i, int i2, int i3) {
                this(i, i2, i3, null);
            }

            public Action(int i, int i2, int i3, Interpolator interpolator) {
                this.mJumpToPosition = -1;
                this.mChanged = false;
                this.mConsecutiveUpdates = 0;
                this.mDx = i;
                this.mDy = i2;
                this.mDuration = i3;
                this.mInterpolator = interpolator;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public interface ScrollVectorProvider {
            PointF computeScrollVectorForPosition(int i);
        }

        public PointF computeScrollVectorForPosition(int i) {
            Object obj = this.mLayoutManager;
            if (obj instanceof ScrollVectorProvider) {
                return ((ScrollVectorProvider) obj).computeScrollVectorForPosition(i);
            }
            Log.w("SeslRecyclerView", "You should override computeScrollVectorForPosition when the LayoutManager does not implement " + ScrollVectorProvider.class.getCanonicalName());
            return null;
        }

        public final View findViewByPosition(int i) {
            return this.mRecyclerView.mLayout.findViewByPosition(i);
        }

        public final int getChildCount() {
            return this.mRecyclerView.mLayout.getChildCount();
        }

        public final void onAnimation(int i, int i2) {
            PointF computeScrollVectorForPosition;
            RecyclerView recyclerView = this.mRecyclerView;
            if (this.mTargetPosition == -1 || recyclerView == null) {
                stop();
            }
            if (this.mPendingInitialRun && this.mTargetView == null && this.mLayoutManager != null && (computeScrollVectorForPosition = computeScrollVectorForPosition(this.mTargetPosition)) != null) {
                float f = computeScrollVectorForPosition.x;
                if (f != 0.0f || computeScrollVectorForPosition.y != 0.0f) {
                    recyclerView.scrollStep((int) Math.signum(f), (int) Math.signum(computeScrollVectorForPosition.y), null);
                }
            }
            this.mPendingInitialRun = false;
            View view = this.mTargetView;
            Action action = this.mRecyclingAction;
            if (view != null) {
                this.mRecyclerView.getClass();
                if (RecyclerView.getChildLayoutPosition(view) == this.mTargetPosition) {
                    View view2 = this.mTargetView;
                    State state = recyclerView.mState;
                    onTargetFound(view2, action);
                    action.runIfNecessary(recyclerView);
                    stop();
                } else {
                    Log.e("SeslRecyclerView", "Passed over target position while smooth scrolling.");
                    this.mTargetView = null;
                }
            }
            if (this.mRunning) {
                State state2 = recyclerView.mState;
                onSeekTargetStep(i, i2, action);
                boolean z = action.mJumpToPosition >= 0;
                action.runIfNecessary(recyclerView);
                if (z && this.mRunning) {
                    this.mPendingInitialRun = true;
                    recyclerView.mViewFlinger.postOnAnimation();
                }
            }
        }

        public abstract void onSeekTargetStep(int i, int i2, Action action);

        public abstract void onStart();

        public abstract void onStop();

        public abstract void onTargetFound(View view, Action action);

        public final void stop() {
            if (this.mRunning) {
                this.mRunning = false;
                onStop();
                this.mRecyclerView.mState.mTargetPosition = -1;
                this.mTargetView = null;
                this.mTargetPosition = -1;
                this.mPendingInitialRun = false;
                LayoutManager layoutManager = this.mLayoutManager;
                if (layoutManager.mSmoothScroller == this) {
                    layoutManager.mSmoothScroller = null;
                }
                this.mLayoutManager = null;
                this.mRecyclerView = null;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class State {
        public long mFocusedItemId;
        public int mFocusedItemPosition;
        public int mFocusedSubChildId;
        public int mRemainingScrollHorizontal;
        public int mRemainingScrollVertical;
        public int mTargetPosition = -1;
        public int mPreviousLayoutItemCount = 0;
        public int mDeletedInvisibleItemCountSincePreviousLayout = 0;
        public int mLayoutStep = 1;
        public int mItemCount = 0;
        public boolean mStructureChanged = false;
        public boolean mInPreLayout = false;
        public boolean mTrackOldChangeHolders = false;
        public boolean mIsMeasuring = false;
        public boolean mRunSimpleAnimations = false;
        public boolean mRunPredictiveAnimations = false;

        public final void assertLayoutStep(int i) {
            if ((this.mLayoutStep & i) != 0) {
                return;
            }
            throw new IllegalStateException("Layout state should be one of " + Integer.toBinaryString(i) + " but it is " + Integer.toBinaryString(this.mLayoutStep));
        }

        public final int getItemCount() {
            return this.mInPreLayout ? this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout : this.mItemCount;
        }

        public final String toString() {
            return "State{mTargetPosition=" + this.mTargetPosition + ", mData=null, mItemCount=" + this.mItemCount + ", mIsMeasuring=" + this.mIsMeasuring + ", mPreviousLayoutItemCount=" + this.mPreviousLayoutItemCount + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.mDeletedInvisibleItemCountSincePreviousLayout + ", mStructureChanged=" + this.mStructureChanged + ", mInPreLayout=" + this.mInPreLayout + ", mRunSimpleAnimations=" + this.mRunSimpleAnimations + ", mRunPredictiveAnimations=" + this.mRunPredictiveAnimations + '}';
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StretchEdgeEffectFactory extends EdgeEffectFactory {
        public final EdgeEffect createEdgeEffect(RecyclerView recyclerView) {
            return new EdgeEffect(recyclerView.getContext());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ViewFlinger implements Runnable {
        public boolean mEatRunOnAnimationRequest;
        public Interpolator mInterpolator;
        public int mLastFlingX;
        public int mLastFlingY;
        public OverScroller mOverScroller;
        public boolean mReSchedulePostAnimationCallback;

        public ViewFlinger() {
            InterpolatorC04718 interpolatorC04718 = RecyclerView.sQuinticInterpolator;
            this.mInterpolator = interpolatorC04718;
            this.mEatRunOnAnimationRequest = false;
            this.mReSchedulePostAnimationCallback = false;
            this.mOverScroller = new OverScroller(RecyclerView.this.getContext(), interpolatorC04718);
        }

        public final void fling(int i, int i2) {
            RecyclerView.this.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            Interpolator interpolator = this.mInterpolator;
            InterpolatorC04718 interpolatorC04718 = RecyclerView.sQuinticInterpolator;
            if (interpolator != interpolatorC04718) {
                this.mInterpolator = interpolatorC04718;
                this.mOverScroller = new OverScroller(RecyclerView.this.getContext(), interpolatorC04718);
            }
            OverScroller overScroller = this.mOverScroller;
            RecyclerView recyclerView = RecyclerView.this;
            boolean z = recyclerView.mIsSkipMoveEvent;
            float f = recyclerView.mFrameLatency;
            Class cls = SeslOverScrollerReflector.mClass;
            Class cls2 = Integer.TYPE;
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(cls, "hidden_fling", cls2, cls2, Boolean.TYPE, Float.TYPE);
            if (declaredMethod != null) {
                SeslBaseReflector.invoke(overScroller, declaredMethod, Integer.valueOf(i), Integer.valueOf(i2), Boolean.valueOf(z), Float.valueOf(f));
            } else {
                overScroller.fling(0, 0, i, i2, VideoPlayer.MEDIA_ERROR_SYSTEM, Integer.MAX_VALUE, VideoPlayer.MEDIA_ERROR_SYSTEM, Integer.MAX_VALUE);
            }
            postOnAnimation();
        }

        public final void postOnAnimation() {
            if (this.mEatRunOnAnimationRequest) {
                this.mReSchedulePostAnimationCallback = true;
                return;
            }
            RecyclerView.this.removeCallbacks(this);
            RecyclerView recyclerView = RecyclerView.this;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postOnAnimation(recyclerView, this);
        }

        @Override // java.lang.Runnable
        public final void run() {
            int i;
            int i2;
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
                int i3 = currX - this.mLastFlingX;
                int i4 = currY - this.mLastFlingY;
                this.mLastFlingX = currX;
                this.mLastFlingY = currY;
                RecyclerView recyclerView2 = RecyclerView.this;
                int consumeFlingInStretch = RecyclerView.consumeFlingInStretch(i3, recyclerView2.mLeftGlow, recyclerView2.mRightGlow, recyclerView2.getWidth());
                RecyclerView recyclerView3 = RecyclerView.this;
                int consumeFlingInStretch2 = RecyclerView.consumeFlingInStretch(i4, recyclerView3.mTopGlow, recyclerView3.mBottomGlow, recyclerView3.getHeight());
                RecyclerView recyclerView4 = RecyclerView.this;
                int[] iArr = recyclerView4.mReusableIntPair;
                iArr[0] = 0;
                iArr[1] = 0;
                if (recyclerView4.dispatchNestedPreScroll(consumeFlingInStretch, consumeFlingInStretch2, 1, iArr, null)) {
                    RecyclerView recyclerView5 = RecyclerView.this;
                    int[] iArr2 = recyclerView5.mReusableIntPair;
                    consumeFlingInStretch -= iArr2[0];
                    int i5 = iArr2[1];
                    consumeFlingInStretch2 -= i5;
                    recyclerView5.adjustNestedScrollRangeBy(i5);
                } else {
                    RecyclerView.this.adjustNestedScrollRangeBy(consumeFlingInStretch2);
                }
                if (RecyclerView.this.getOverScrollMode() != 2) {
                    RecyclerView.this.considerReleasingGlowsOnScroll(consumeFlingInStretch, consumeFlingInStretch2);
                }
                RecyclerView recyclerView6 = RecyclerView.this;
                if (recyclerView6.mAdapter != null) {
                    int[] iArr3 = recyclerView6.mReusableIntPair;
                    iArr3[0] = 0;
                    iArr3[1] = 0;
                    recyclerView6.scrollStep(consumeFlingInStretch, consumeFlingInStretch2, iArr3);
                    RecyclerView recyclerView7 = RecyclerView.this;
                    int[] iArr4 = recyclerView7.mReusableIntPair;
                    i2 = iArr4[0];
                    i = iArr4[1];
                    consumeFlingInStretch -= i2;
                    consumeFlingInStretch2 -= i;
                    SmoothScroller smoothScroller = recyclerView7.mLayout.mSmoothScroller;
                    if (smoothScroller != null && !smoothScroller.mPendingInitialRun && smoothScroller.mRunning) {
                        int itemCount = recyclerView7.mState.getItemCount();
                        if (itemCount == 0) {
                            smoothScroller.stop();
                        } else if (smoothScroller.mTargetPosition >= itemCount) {
                            smoothScroller.mTargetPosition = itemCount - 1;
                            smoothScroller.onAnimation(i2, i);
                        } else {
                            smoothScroller.onAnimation(i2, i);
                        }
                    }
                } else {
                    i = 0;
                    i2 = 0;
                }
                if (!RecyclerView.this.mItemDecorations.isEmpty()) {
                    RecyclerView.this.invalidate();
                }
                RecyclerView recyclerView8 = RecyclerView.this;
                int[] iArr5 = recyclerView8.mReusableIntPair;
                iArr5[0] = 0;
                iArr5[1] = 0;
                if (RecyclerView.access$5400(recyclerView8, i2, i, consumeFlingInStretch, consumeFlingInStretch2, iArr5)) {
                    int[] iArr6 = RecyclerView.this.mScrollOffset;
                    iArr6[0] = 0;
                    iArr6[1] = 0;
                }
                RecyclerView recyclerView9 = RecyclerView.this;
                int[] iArr7 = recyclerView9.mScrollOffset;
                if (iArr7[0] < 0 || iArr7[1] < 0) {
                    iArr7[0] = 0;
                    iArr7[1] = 0;
                }
                int[] iArr8 = recyclerView9.mReusableIntPair;
                int i6 = consumeFlingInStretch - iArr8[0];
                int i7 = consumeFlingInStretch2 - iArr8[1];
                if (i2 != 0 || i != 0) {
                    recyclerView9.dispatchOnScrolled(i2, i);
                }
                if (!RecyclerView.this.awakenScrollBars()) {
                    RecyclerView.this.invalidate();
                }
                boolean z = overScroller.isFinished() || (((overScroller.getCurrX() == overScroller.getFinalX()) || i6 != 0) && ((overScroller.getCurrY() == overScroller.getFinalY()) || i7 != 0));
                RecyclerView recyclerView10 = RecyclerView.this;
                SmoothScroller smoothScroller2 = recyclerView10.mLayout.mSmoothScroller;
                if ((smoothScroller2 != null && smoothScroller2.mPendingInitialRun) || !z) {
                    postOnAnimation();
                    RecyclerView recyclerView11 = RecyclerView.this;
                    GapWorker gapWorker = recyclerView11.mGapWorker;
                    if (gapWorker != null) {
                        gapWorker.postFromTraversal(recyclerView11, i2, i);
                    }
                } else {
                    if (recyclerView10.getOverScrollMode() != 2 && !RecyclerView.this.mEdgeEffectByDragging) {
                        int currVelocity = (int) overScroller.getCurrVelocity();
                        int i8 = i6 < 0 ? -currVelocity : i6 > 0 ? currVelocity : 0;
                        if (i7 < 0) {
                            currVelocity = -currVelocity;
                        } else if (i7 <= 0) {
                            currVelocity = 0;
                        }
                        RecyclerView recyclerView12 = RecyclerView.this;
                        recyclerView12.getClass();
                        if (i8 < 0) {
                            recyclerView12.ensureLeftGlow();
                            if (recyclerView12.mLeftGlow.isFinished()) {
                                recyclerView12.mLeftGlow.onAbsorb(-i8);
                            }
                        } else if (i8 > 0) {
                            recyclerView12.ensureRightGlow();
                            if (recyclerView12.mRightGlow.isFinished()) {
                                recyclerView12.mRightGlow.onAbsorb(i8);
                            }
                        }
                        if (currVelocity < 0) {
                            recyclerView12.ensureTopGlow();
                            if (recyclerView12.mTopGlow.isFinished()) {
                                recyclerView12.mTopGlow.onAbsorb(-currVelocity);
                            }
                        } else if (currVelocity > 0) {
                            recyclerView12.ensureBottomGlow();
                            if (recyclerView12.mBottomGlow.isFinished()) {
                                recyclerView12.mBottomGlow.onAbsorb(currVelocity);
                            }
                        }
                        if (i8 != 0 || currVelocity != 0) {
                            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                            ViewCompat.Api16Impl.postInvalidateOnAnimation(recyclerView12);
                        }
                    }
                    if (RecyclerView.ALLOW_THREAD_GAP_WORK) {
                        GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = RecyclerView.this.mPrefetchRegistry;
                        int[] iArr9 = layoutPrefetchRegistryImpl.mPrefetchArray;
                        if (iArr9 != null) {
                            Arrays.fill(iArr9, -1);
                        }
                        layoutPrefetchRegistryImpl.mCount = 0;
                    }
                }
            }
            SmoothScroller smoothScroller3 = RecyclerView.this.mLayout.mSmoothScroller;
            if (smoothScroller3 != null && smoothScroller3.mPendingInitialRun) {
                smoothScroller3.onAnimation(0, 0);
            }
            this.mEatRunOnAnimationRequest = false;
            if (!this.mReSchedulePostAnimationCallback) {
                RecyclerView.this.setScrollState(0);
                RecyclerView.this.stopNestedScroll(1);
            } else {
                RecyclerView.this.removeCallbacks(this);
                RecyclerView recyclerView13 = RecyclerView.this;
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postOnAnimation(recyclerView13, this);
            }
        }

        public final void smoothScrollBy(int i, int i2, int i3, Interpolator interpolator) {
            int i4;
            int i5;
            if (i3 == Integer.MIN_VALUE) {
                int abs = Math.abs(i);
                int abs2 = Math.abs(i2);
                boolean z = abs > abs2;
                int sqrt = (int) Math.sqrt(0);
                int sqrt2 = (int) Math.sqrt((i2 * i2) + (i * i));
                RecyclerView recyclerView = RecyclerView.this;
                int width = z ? recyclerView.getWidth() : recyclerView.getHeight();
                int i6 = width / 2;
                float f = width;
                float f2 = i6;
                float sin = (((float) Math.sin((Math.min(1.0f, (sqrt2 * 1.0f) / f) - 0.5f) * 0.47123894f)) * f2) + f2;
                if (sqrt > 0) {
                    i5 = Math.round(Math.abs(sin / sqrt) * 1000.0f) * 4;
                } else {
                    if (!z) {
                        abs = abs2;
                    }
                    i5 = (int) (((abs / f) + 1.0f) * 300.0f);
                }
                i4 = Math.min(i5, 2000);
            } else {
                i4 = i3;
            }
            Interpolator interpolator2 = interpolator == null ? RecyclerView.sQuinticInterpolator : interpolator;
            RecyclerView.this.startNestedScroll(i != 0 ? 2 : 1, 1);
            if (!RecyclerView.this.dispatchNestedPreScroll(i, i2, 1, null, null)) {
                if (this.mInterpolator != interpolator2) {
                    this.mInterpolator = interpolator2;
                    this.mOverScroller = new OverScroller(RecyclerView.this.getContext(), interpolator2);
                }
                this.mLastFlingY = 0;
                this.mLastFlingX = 0;
                RecyclerView.this.setScrollState(2);
                this.mOverScroller.startScroll(0, 0, i, i2, i4);
                postOnAnimation();
            }
            RecyclerView.this.adjustNestedScrollRangeBy(i2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class ViewHolder {
        public static final List FULLUPDATE_PAYLOADS = Collections.emptyList();
        public final View itemView;
        public Adapter mBindingAdapter;
        public int mFlags;
        public WeakReference mNestedRecyclerView;
        public RecyclerView mOwnerRecyclerView;
        public int mPosition = -1;
        public int mOldPosition = -1;
        public long mItemId = -1;
        public int mItemViewType = -1;
        public int mPreLayoutPosition = -1;
        public ViewHolder mShadowedHolder = null;
        public ViewHolder mShadowingHolder = null;
        public List mPayloads = null;
        public List mUnmodifiedPayloads = null;
        public int mIsRecyclableCount = 0;
        public Recycler mScrapContainer = null;
        public boolean mInChangeScrap = false;
        public int mWasImportantForAccessibilityBeforeHidden = 0;
        int mPendingAccessibilityState = -1;

        public ViewHolder(View view) {
            if (view == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = view;
        }

        public final void addChangePayload(Object obj) {
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

        public final void addFlags(int i) {
            this.mFlags = i | this.mFlags;
        }

        public final int getAbsoluteAdapterPosition() {
            RecyclerView recyclerView = this.mOwnerRecyclerView;
            if (recyclerView == null) {
                return -1;
            }
            return recyclerView.getAdapterPositionInRecyclerView(this);
        }

        public final int getBindingAdapterPosition() {
            RecyclerView recyclerView;
            Adapter adapter;
            int adapterPositionInRecyclerView;
            if (this.mBindingAdapter == null || (recyclerView = this.mOwnerRecyclerView) == null || (adapter = recyclerView.mAdapter) == null || (adapterPositionInRecyclerView = recyclerView.getAdapterPositionInRecyclerView(this)) == -1 || this.mBindingAdapter != adapter) {
                return -1;
            }
            return adapterPositionInRecyclerView;
        }

        public final int getLayoutPosition() {
            int i = this.mPreLayoutPosition;
            return i == -1 ? this.mPosition : i;
        }

        public final List getUnmodifiedPayloads() {
            List list;
            return ((this.mFlags & 1024) != 0 || (list = this.mPayloads) == null || ((ArrayList) list).size() == 0) ? FULLUPDATE_PAYLOADS : this.mUnmodifiedPayloads;
        }

        public final boolean isAttachedToTransitionOverlay() {
            View view = this.itemView;
            return (view.getParent() == null || view.getParent() == this.mOwnerRecyclerView) ? false : true;
        }

        final boolean isBound() {
            return (this.mFlags & 1) != 0;
        }

        public final boolean isInvalid() {
            return (this.mFlags & 4) != 0;
        }

        public final boolean isRecyclable() {
            if ((this.mFlags & 16) == 0) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (!ViewCompat.Api16Impl.hasTransientState(this.itemView)) {
                    return true;
                }
            }
            return false;
        }

        final boolean isRemoved() {
            return (this.mFlags & 8) != 0;
        }

        public final boolean isScrap() {
            return this.mScrapContainer != null;
        }

        public final boolean isTmpDetached() {
            return (this.mFlags & 256) != 0;
        }

        public final void offsetPosition(int i, boolean z) {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if (z) {
                this.mPreLayoutPosition += i;
            }
            this.mPosition += i;
            View view = this.itemView;
            if (view.getLayoutParams() != null) {
                ((LayoutParams) view.getLayoutParams()).mInsetsDirty = true;
            }
        }

        public final void resetInternal() {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1L;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            List list = this.mPayloads;
            if (list != null) {
                ((ArrayList) list).clear();
            }
            this.mFlags &= KnoxContainerManager.ERROR_INVALID_PASSWORD_RESET_TOKEN;
            this.mWasImportantForAccessibilityBeforeHidden = 0;
            this.mPendingAccessibilityState = -1;
            RecyclerView.clearNestedRecyclerViewIfNotNested(this);
        }

        public final void setIsRecyclable(boolean z) {
            int i = this.mIsRecyclableCount;
            int i2 = z ? i - 1 : i + 1;
            this.mIsRecyclableCount = i2;
            if (i2 < 0) {
                this.mIsRecyclableCount = 0;
                Log.e("View", "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
                return;
            }
            if (!z && i2 == 1) {
                this.mFlags |= 16;
            } else if (z && i2 == 0) {
                this.mFlags &= -17;
            }
        }

        public final boolean shouldIgnore() {
            return (this.mFlags & 128) != 0;
        }

        public final String toString() {
            StringBuilder m2m = AbstractC0000x2c234b15.m2m(getClass().isAnonymousClass() ? "ViewHolder" : getClass().getSimpleName(), "{");
            m2m.append(Integer.toHexString(hashCode()));
            m2m.append(" position=");
            m2m.append(this.mPosition);
            m2m.append(" id=");
            m2m.append(this.mItemId);
            m2m.append(", oldPos=");
            m2m.append(this.mOldPosition);
            m2m.append(", pLpos:");
            m2m.append(this.mPreLayoutPosition);
            StringBuilder sb = new StringBuilder(m2m.toString());
            if (isScrap()) {
                sb.append(" scrap ");
                sb.append(this.mInChangeScrap ? "[changeScrap]" : "[attachedScrap]");
            }
            if (isInvalid()) {
                sb.append(" invalid");
            }
            if (!isBound()) {
                sb.append(" unbound");
            }
            boolean z = true;
            if ((this.mFlags & 2) != 0) {
                sb.append(" update");
            }
            if (isRemoved()) {
                sb.append(" removed");
            }
            if (shouldIgnore()) {
                sb.append(" ignored");
            }
            if (isTmpDetached()) {
                sb.append(" tmpDetached");
            }
            if (!isRecyclable()) {
                sb.append(" not recyclable(" + this.mIsRecyclableCount + ")");
            }
            if ((this.mFlags & 512) == 0 && !isInvalid()) {
                z = false;
            }
            if (z) {
                sb.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                sb.append(" no parent");
            }
            sb.append("}");
            return sb.toString();
        }

        public final boolean wasReturnedFromScrap() {
            return (this.mFlags & 32) != 0;
        }
    }

    /* JADX WARN: Type inference failed for: r0v11, types: [androidx.recyclerview.widget.RecyclerView$8] */
    static {
        Class cls = Integer.TYPE;
        LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class, cls, cls};
        LINEAR_INTERPOLATOR = new LinearInterpolator();
        sQuinticInterpolator = new Interpolator() { // from class: androidx.recyclerview.widget.RecyclerView.8
            @Override // android.animation.TimeInterpolator
            public final float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * f2 * f2 * f2 * f2) + 1.0f;
            }
        };
        sDefaultEdgeEffectFactory = new StretchEdgeEffectFactory();
    }

    public RecyclerView(Context context) {
        this(context, null);
    }

    public static boolean access$5400(RecyclerView recyclerView, int i, int i2, int i3, int i4, int[] iArr) {
        return recyclerView.getScrollingChildHelper().dispatchNestedScrollInternal(i, i2, i3, i4, null, 1, iArr);
    }

    private void adjustNestedScrollRange$1() {
        getLocationInWindow(this.mWindowOffsets);
        LayoutManager layoutManager = this.mLayout;
        int i = (layoutManager == null || !layoutManager.canScrollHorizontally()) ? this.mWindowOffsets[1] : this.mWindowOffsets[0];
        int i2 = this.mNestedScrollRange;
        int i3 = this.mInitialTopOffsetOfScreen;
        int i4 = i2 - (i3 - i);
        this.mRemainNestedScrollRange = i4;
        if (i3 - i < 0) {
            this.mNestedScrollRange = i4;
            this.mInitialTopOffsetOfScreen = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustNestedScrollRangeBy(int i) {
        if (this.mHasNestedScrollRange) {
            if (canScrollUp$1() && this.mRemainNestedScrollRange == 0) {
                return;
            }
            int i2 = this.mRemainNestedScrollRange - i;
            this.mRemainNestedScrollRange = i2;
            if (i2 < 0) {
                this.mRemainNestedScrollRange = 0;
                return;
            }
            int i3 = this.mNestedScrollRange;
            if (i2 > i3) {
                this.mRemainNestedScrollRange = i3;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canScrollUp$1() {
        boolean z;
        boolean z2;
        int childCount = getChildCount();
        LayoutManager layoutManager = this.mLayout;
        boolean z3 = true;
        if (layoutManager != null) {
            z = layoutManager.canScrollHorizontally();
            z2 = this.mLayout.getLayoutDirection() == 1;
        } else {
            z = false;
            z2 = false;
        }
        LayoutManager layoutManager2 = this.mLayout;
        boolean z4 = layoutManager2 instanceof LinearLayoutManager ? ((LinearLayoutManager) layoutManager2).mReverseLayout : false;
        boolean z5 = !z4 ? findFirstChildPosition() <= 0 : findFirstChildPosition() + childCount >= this.mAdapter.getItemCount();
        if (z5 || childCount <= 0) {
            return z5;
        }
        getDecoratedBoundsWithMarginsInt(this.mChildBound, getChildAt(z4 ? childCount - 1 : 0));
        if (!z ? this.mChildBound.top >= this.mListPadding.top : !z2 ? this.mChildBound.left >= this.mListPadding.left : this.mChildBound.right <= getRight() - this.mListPadding.right && this.mChildBound.right <= getWidth() - this.mListPadding.right) {
            z3 = false;
        }
        return z3;
    }

    public static void clearNestedRecyclerViewIfNotNested(ViewHolder viewHolder) {
        WeakReference weakReference = viewHolder.mNestedRecyclerView;
        if (weakReference != null) {
            View view = (View) weakReference.get();
            while (view != null) {
                if (view == viewHolder.itemView) {
                    return;
                }
                Object parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
            viewHolder.mNestedRecyclerView = null;
        }
    }

    public static int consumeFlingInStretch(int i, EdgeEffect edgeEffect, EdgeEffect edgeEffect2, int i2) {
        if (i > 0 && edgeEffect != null && EdgeEffectCompat.getDistance(edgeEffect) != 0.0f) {
            int round = Math.round(EdgeEffectCompat.onPullDistance(edgeEffect, ((-i) * 4.0f) / i2, 0.5f) * ((-i2) / 4.0f));
            if (round != i) {
                edgeEffect.finish();
            }
            return i - round;
        }
        if (i >= 0 || edgeEffect2 == null || EdgeEffectCompat.getDistance(edgeEffect2) == 0.0f) {
            return i;
        }
        float f = i2;
        int round2 = Math.round(EdgeEffectCompat.onPullDistance(edgeEffect2, (i * 4.0f) / f, 0.5f) * (f / 4.0f));
        if (round2 != i) {
            edgeEffect2.finish();
        }
        return i - round2;
    }

    public static RecyclerView findNestedRecyclerView(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        if (view instanceof RecyclerView) {
            return (RecyclerView) view;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RecyclerView findNestedRecyclerView = findNestedRecyclerView(viewGroup.getChildAt(i));
            if (findNestedRecyclerView != null) {
                return findNestedRecyclerView;
            }
        }
        return null;
    }

    public static int getChildAdapterPosition(View view) {
        ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            return childViewHolderInt.getAbsoluteAdapterPosition();
        }
        return -1;
    }

    public static int getChildLayoutPosition(View view) {
        ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            return childViewHolderInt.getLayoutPosition();
        }
        return -1;
    }

    public static ViewHolder getChildViewHolderInt(View view) {
        if (view == null) {
            return null;
        }
        return ((LayoutParams) view.getLayoutParams()).mViewHolder;
    }

    static void getDecoratedBoundsWithMarginsInt(Rect rect, View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect2 = layoutParams.mDecorInsets;
        rect.set((view.getLeft() - rect2.left) - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, (view.getTop() - rect2.top) - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, view.getRight() + rect2.right + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, view.getBottom() + rect2.bottom + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
    }

    private boolean isSupportGotoTop$1() {
        return isGoToTopAvailableEnvironment() && this.mEnableGoToTop;
    }

    private void pageScroll(int i) {
        int findFirstVisibleItemPosition;
        Adapter adapter = this.mAdapter;
        if (adapter == null) {
            Log.e("SeslRecyclerView", "No adapter attached; skipping pageScroll");
            return;
        }
        int itemCount = adapter.getItemCount();
        if (itemCount <= 0) {
            return;
        }
        int i2 = 0;
        if (i == 0) {
            findFirstVisibleItemPosition = findFirstVisibleItemPosition() - getChildCount();
        } else if (i == 1) {
            findFirstVisibleItemPosition = findLastVisibleItemPosition() + getChildCount();
        } else if (i == 2) {
            findFirstVisibleItemPosition = 0;
        } else if (i != 3) {
            return;
        } else {
            findFirstVisibleItemPosition = itemCount - 1;
        }
        int i3 = itemCount - 1;
        if (findFirstVisibleItemPosition > i3) {
            i2 = i3;
        } else if (findFirstVisibleItemPosition >= 0) {
            i2 = findFirstVisibleItemPosition;
        }
        this.mLayout.mRecyclerView.scrollToPosition(i2);
        this.mLayout.mRecyclerView.post(new Runnable() { // from class: androidx.recyclerview.widget.RecyclerView.19
            @Override // java.lang.Runnable
            public final void run() {
                View childAt = RecyclerView.this.getChildAt(0);
                if (childAt != null) {
                    childAt.requestFocus();
                }
            }
        });
    }

    private int releaseVerticalGlow(float f, int i) {
        float width = f / getWidth();
        float height = i / getHeight();
        EdgeEffect edgeEffect = this.mTopGlow;
        float f2 = 0.0f;
        if (edgeEffect == null || EdgeEffectCompat.getDistance(edgeEffect) == 0.0f) {
            EdgeEffect edgeEffect2 = this.mBottomGlow;
            if (edgeEffect2 != null && EdgeEffectCompat.getDistance(edgeEffect2) != 0.0f) {
                if (canScrollVertically(1)) {
                    this.mBottomGlow.onRelease();
                } else {
                    float onPullDistance = EdgeEffectCompat.onPullDistance(this.mBottomGlow, height, 1.0f - width);
                    if (EdgeEffectCompat.getDistance(this.mBottomGlow) == 0.0f) {
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
                if (EdgeEffectCompat.getDistance(this.mTopGlow) == 0.0f) {
                    this.mTopGlow.onRelease();
                }
                f2 = f3;
            }
            invalidate();
        }
        return Math.round(f2 * getHeight());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupGoToTop(int i) {
        if (isGoToTopAvailableEnvironment() && this.mEnableGoToTop) {
            removeCallbacks(this.mAutoHide);
            if (i == 1 && !canScrollUp$1()) {
                i = 0;
            }
            if (i == -1 && this.mSizeChnage) {
                i = (canScrollUp$1() || canScrollDown()) ? this.mGoToTopLastState : 0;
            } else if (i == -1 && (canScrollUp$1() || canScrollDown())) {
                i = 1;
            }
            if (i != 0) {
                removeCallbacks(this.mGoToToFadeOutRunnable);
            } else if (i != 1) {
                removeCallbacks(this.mGoToToFadeInRunnable);
            }
            if (this.mShowFadeOutGTP == 0 && i == 0 && this.mGoToTopLastState != 0) {
                post(this.mGoToToFadeOutRunnable);
            }
            if (i != 2) {
                this.mGoToTopView.setPressed(false);
            }
            this.mGoToTopState = i;
            int width = (((getWidth() - getPaddingLeft()) - getPaddingRight()) / 2) + getPaddingLeft();
            if (i != 0) {
                if (i == 1 || i == 2) {
                    removeCallbacks(this.mGoToToFadeOutRunnable);
                    int height = getHeight();
                    Rect rect = this.mGoToTopRect;
                    int i2 = this.mGoToTopSize;
                    int i3 = i2 / 2;
                    int i4 = this.mGoToTopBottomPadding;
                    int i5 = this.mGoToTopImmersiveBottomPadding;
                    rect.set(width - i3, ((height - i2) - i4) - i5, i3 + width, (height - i4) - i5);
                }
            } else if (this.mShowFadeOutGTP == 2) {
                this.mGoToTopRect.set(0, 0, 0, 0);
            }
            if (this.mShowFadeOutGTP == 2) {
                this.mShowFadeOutGTP = 0;
            }
            ImageView imageView = this.mGoToTopView;
            Rect rect2 = this.mGoToTopRect;
            imageView.layout(rect2.left, rect2.top, rect2.right, rect2.bottom);
            if (i == 1 && (this.mGoToTopLastState == 0 || this.mGoToTopView.getAlpha() == 0.0f || this.mSizeChnage)) {
                post(this.mGoToToFadeInRunnable);
            }
            this.mSizeChnage = false;
            this.mGoToTopLastState = this.mGoToTopState;
        }
    }

    public final void addAnimatingView(ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        boolean z = view.getParent() == this;
        this.mRecycler.unscrapView(getChildViewHolder(view));
        if (viewHolder.isTmpDetached()) {
            this.mChildHelper.attachViewToParent(view, -1, view.getLayoutParams(), true);
            return;
        }
        if (!z) {
            this.mChildHelper.addView(view, -1, true);
            return;
        }
        ChildHelper childHelper = this.mChildHelper;
        int indexOfChild = RecyclerView.this.indexOfChild(view);
        if (indexOfChild >= 0) {
            childHelper.mBucket.set(indexOfChild);
            childHelper.hideViewInternal(view);
        } else {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void addFocusables(ArrayList arrayList, int i, int i2) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null || !layoutManager.onAddFocusables(this, arrayList, i, i2)) {
            super.addFocusables(arrayList, i, i2);
        }
    }

    public final void addItemDecoration(ItemDecoration itemDecoration) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
        }
        if (this.mItemDecorations.isEmpty()) {
            setWillNotDraw(false);
        }
        this.mItemDecorations.add(itemDecoration);
        markItemDecorInsetsDirty();
        requestLayout();
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
            Log.w("SeslRecyclerView", "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new IllegalStateException("" + exceptionLabel()));
        }
    }

    public final void autoHide(int i) {
        if (this.mEnableGoToTop) {
            if (i != 0) {
                if (i == 1) {
                    removeCallbacks(this.mAutoHide);
                    postDelayed(this.mAutoHide, 1500L);
                    return;
                }
                return;
            }
            SeslRecyclerViewFastScroller seslRecyclerViewFastScroller = this.mFastScroller;
            if (seslRecyclerViewFastScroller != null && seslRecyclerViewFastScroller.isEnabled()) {
                return;
            }
            removeCallbacks(this.mAutoHide);
            postDelayed(this.mAutoHide, 1500L);
        }
    }

    public final boolean canScrollDown() {
        boolean z;
        boolean z2;
        int childCount = getChildCount();
        LayoutManager layoutManager = this.mLayout;
        boolean z3 = true;
        if (layoutManager != null) {
            z = layoutManager.canScrollHorizontally();
            z2 = this.mLayout.getLayoutDirection() == 1;
        } else {
            z = false;
            z2 = false;
        }
        LayoutManager layoutManager2 = this.mLayout;
        boolean z4 = layoutManager2 instanceof LinearLayoutManager ? ((LinearLayoutManager) layoutManager2).mReverseLayout : false;
        if (this.mAdapter == null) {
            Log.e("SeslRecyclerView", "No adapter attached; skipping canScrollDown");
            return false;
        }
        boolean z5 = !z4 ? findFirstChildPosition() + childCount >= this.mAdapter.getItemCount() : findFirstChildPosition() <= 0;
        if (z5 || childCount <= 0) {
            return z5;
        }
        getDecoratedBoundsWithMarginsInt(this.mChildBound, getChildAt(z4 ? 0 : childCount - 1));
        if (!z ? !(this.mChildBound.bottom > getBottom() - this.mListPadding.bottom || this.mChildBound.bottom > getHeight() - this.mListPadding.bottom) : !(!z2 ? this.mChildBound.right > getRight() - this.mListPadding.right || this.mChildBound.right > getWidth() - this.mListPadding.right : this.mChildBound.left < this.mListPadding.left)) {
            z3 = false;
        }
        return z3;
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && this.mLayout.checkLayoutParams((LayoutParams) layoutParams);
    }

    final void clearOldPositions() {
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < unfilteredChildCount; i++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (!childViewHolderInt.shouldIgnore()) {
                childViewHolderInt.mOldPosition = -1;
                childViewHolderInt.mPreLayoutPosition = -1;
            }
        }
        Recycler recycler = this.mRecycler;
        ArrayList arrayList = recycler.mCachedViews;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ViewHolder viewHolder = (ViewHolder) arrayList.get(i2);
            viewHolder.mOldPosition = -1;
            viewHolder.mPreLayoutPosition = -1;
        }
        ArrayList arrayList2 = recycler.mAttachedScrap;
        int size2 = arrayList2.size();
        for (int i3 = 0; i3 < size2; i3++) {
            ViewHolder viewHolder2 = (ViewHolder) arrayList2.get(i3);
            viewHolder2.mOldPosition = -1;
            viewHolder2.mPreLayoutPosition = -1;
        }
        ArrayList arrayList3 = recycler.mChangedScrap;
        if (arrayList3 != null) {
            int size3 = arrayList3.size();
            for (int i4 = 0; i4 < size3; i4++) {
                ViewHolder viewHolder3 = (ViewHolder) recycler.mChangedScrap.get(i4);
                viewHolder3.mOldPosition = -1;
                viewHolder3.mPreLayoutPosition = -1;
            }
        }
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
            return this.mLayout.computeHorizontalScrollOffset(this.mState);
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
            return this.mLayout.computeVerticalScrollOffset(this.mState);
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
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        }
    }

    final void consumePendingUpdateOperations() {
        if (!this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout) {
            Trace.beginSection("RV FullInvalidate");
            dispatchLayout();
            Trace.endSection();
            return;
        }
        if (this.mAdapterHelper.hasPendingUpdates()) {
            AdapterHelper adapterHelper = this.mAdapterHelper;
            int i = adapterHelper.mExistingUpdateTypes;
            boolean z = false;
            if ((i & 4) != 0) {
                if (!((i & 11) != 0)) {
                    Trace.beginSection("RV PartialInvalidate");
                    startInterceptRequestLayout();
                    onEnterLayoutOrScroll();
                    this.mAdapterHelper.preProcess();
                    if (!this.mLayoutWasDefered) {
                        int childCount = this.mChildHelper.getChildCount();
                        int i2 = 0;
                        while (true) {
                            if (i2 >= childCount) {
                                break;
                            }
                            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(i2));
                            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore()) {
                                if ((childViewHolderInt.mFlags & 2) != 0) {
                                    z = true;
                                    break;
                                }
                            }
                            i2++;
                        }
                        if (z) {
                            dispatchLayout();
                        } else {
                            this.mAdapterHelper.consumePostponedUpdates();
                        }
                    }
                    stopInterceptRequestLayout(true);
                    onExitLayoutOrScroll(true);
                    Trace.endSection();
                    return;
                }
            }
            if (adapterHelper.hasPendingUpdates()) {
                Trace.beginSection("RV FullInvalidate");
                dispatchLayout();
                Trace.endSection();
            }
        }
    }

    final void defaultOnMeasure(int i, int i2) {
        int paddingRight = getPaddingRight() + getPaddingLeft();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setMeasuredDimension(LayoutManager.chooseSize(i, paddingRight, ViewCompat.Api16Impl.getMinimumWidth(this)), LayoutManager.chooseSize(i2, getPaddingBottom() + getPaddingTop(), ViewCompat.Api16Impl.getMinimumHeight(this)));
    }

    final void dispatchChildDetached(View view) {
        ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        Adapter adapter = this.mAdapter;
        if (adapter != null && childViewHolderInt != null) {
            adapter.getClass();
        }
        List list = this.mOnChildAttachStateListeners;
        if (list != null) {
            for (int size = ((ArrayList) list).size() - 1; size >= 0; size--) {
                ((OnChildAttachStateChangeListener) ((ArrayList) this.mOnChildAttachStateListeners).get(size)).onChildViewDetachedFromWindow(view);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0042, code lost:
    
        if ((r3 != null && r3.isRunning()) != false) goto L22;
     */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dispatchDraw(Canvas canvas) {
        View childAt;
        super.dispatchDraw(canvas);
        int size = this.mItemDecorations.size();
        for (int i = 0; i < size; i++) {
            ((ItemDecoration) this.mItemDecorations.get(i)).seslOnDispatchDraw(canvas, this);
        }
        if (this.mDrawRect && ((this.mBlackTop != -1 || this.mLastBlackTop != -1) && !canScrollVertically(-1))) {
            if (canScrollVertically(1)) {
                ItemAnimator itemAnimator = this.mItemAnimator;
            }
            ValueAnimator valueAnimator = this.mLastItemAddRemoveAnim;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                this.mAnimatedBlackTop = this.mBlackTop;
            }
            ItemAnimator itemAnimator2 = this.mItemAnimator;
            if (itemAnimator2 != null && itemAnimator2.isRunning()) {
                ItemAnimator itemAnimator3 = this.mItemAnimator;
                int i2 = itemAnimator3 instanceof DefaultItemAnimator ? ((DefaultItemAnimator) itemAnimator3).mPendingAnimFlag : 0;
                if (i2 == 8) {
                    this.mIsSetOnlyAddAnim = true;
                } else if (i2 == 1) {
                    this.mIsSetOnlyRemoveAnim = true;
                }
                if (this.mDrawReverse) {
                    childAt = this.mBlackTop != -1 ? this.mChildHelper.getChildAt(0) : getChildAt(0);
                } else if (this.mBlackTop != -1) {
                    ChildHelper childHelper = this.mChildHelper;
                    childAt = childHelper.getChildAt(childHelper.getChildCount() - 1);
                } else {
                    childAt = getChildAt(getChildCount() - 1);
                }
                if (childAt != null) {
                    boolean z = this.mIsSetOnlyAddAnim;
                    if (!z && !this.mIsSetOnlyRemoveAnim) {
                        this.mAnimatedBlackTop = childAt.getHeight() + Math.round(childAt.getY());
                    } else if (this.mLastItemAddRemoveAnim == null) {
                        ItemAnimator itemAnimator4 = this.mItemAnimator;
                        if ((itemAnimator4 instanceof DefaultItemAnimator) && this.mLastItemAnimTop == -1) {
                            this.mLastItemAnimTop = ((DefaultItemAnimator) itemAnimator4).mLastItemBottom;
                        }
                        if (z) {
                            this.mLastItemAddRemoveAnim = ValueAnimator.ofInt(this.mLastItemAnimTop, childAt.getHeight() + ((int) childAt.getY()));
                        } else if (this.mIsSetOnlyRemoveAnim) {
                            this.mLastItemAddRemoveAnim = ValueAnimator.ofInt(this.mLastItemAnimTop, childAt.getBottom());
                        } else {
                            Log.d("SeslRecyclerView", "Not set only add/remove anim");
                        }
                        this.mLastItemAddRemoveAnim.setDuration(330L);
                        this.mLastItemAddRemoveAnim.addListener(this.mAnimListener);
                        this.mLastItemAddRemoveAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.recyclerview.widget.RecyclerView.13
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                RecyclerView.this.mAnimatedBlackTop = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                                RecyclerView.this.invalidate();
                            }
                        });
                        this.mLastItemAddRemoveAnim.start();
                    }
                }
                invalidate();
            }
            int i3 = this.mBlackTop;
            if (i3 != -1 || this.mAnimatedBlackTop != i3 || this.mIsSetOnlyAddAnim) {
                canvas.drawRect(0.0f, this.mAnimatedBlackTop, getWidth(), getBottom(), this.mRectPaint);
                if (this.mDrawLastRoundedCorner) {
                    this.mRoundedCorner.drawRoundedCorner(this.mAnimatedBlackTop, getWidth(), getBottom(), canvas);
                }
            }
        }
        this.mLastItemAnimTop = this.mBlackTop;
    }

    /* JADX WARN: Code restructure failed: missing block: B:81:0x03d8, code lost:
    
        if (r16.mHoverScrollStartTime != 0) goto L269;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x03ca, code lost:
    
        if (r5 > (r3 ? getBottom() : getRight())) goto L262;
     */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x02bd  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0155  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        int i;
        int i2;
        int i3;
        long j;
        int i4;
        if (this.mAdapter == null) {
            Log.d("SeslRecyclerView", "No adapter attached; skipping hover scroll");
            return super.dispatchHoverEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        int toolType = motionEvent.getToolType(0);
        if ((action == 7 || action == 9) && toolType == 2) {
            this.mIsPenHovered = true;
        } else if (action == 10) {
            this.mIsPenHovered = false;
        }
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(SeslTextViewReflector.mClass, "hidden_semIsTextViewHovered", new Class[0]);
        if (declaredMethod != null) {
            Object invoke = SeslBaseReflector.invoke(null, declaredMethod, new Object[0]);
            if (invoke instanceof Boolean) {
                z = ((Boolean) invoke).booleanValue();
                this.mNewTextViewHoverState = z;
                if (z && this.mOldTextViewHoverState && this.mIsPenDragBlockEnabled && (motionEvent.getButtonState() == 32 || motionEvent.getButtonState() == 2)) {
                    this.mIsNeedPenSelectIconSet = true;
                } else {
                    this.mIsNeedPenSelectIconSet = false;
                }
                this.mOldTextViewHoverState = this.mNewTextViewHoverState;
                if (action != 9) {
                    this.mNeedsHoverScroll = true;
                    if (this.mHasNestedScrollRange) {
                        adjustNestedScrollRange$1();
                    }
                    if (!this.mHoverScrollEnable) {
                        this.mNeedsHoverScroll = false;
                    }
                    if (this.mNeedsHoverScroll && toolType == 2) {
                        boolean z3 = Settings.System.getInt(this.mContext.getContentResolver(), SeslSettingsReflector$SeslSystemReflector.getField_SEM_PEN_HOVERING(), 0) == 1;
                        try {
                        } catch (Settings.SettingNotFoundException unused) {
                            Log.i("SeslRecyclerView", "dispatchHoverEvent car_mode_on SettingNotFoundException");
                        }
                        if (Settings.System.getInt(this.mContext.getContentResolver(), "car_mode_on") == 1) {
                            z2 = true;
                            if (z3 || z2) {
                                this.mNeedsHoverScroll = false;
                            }
                            if (z3 && this.mIsPenDragBlockEnabled && !this.mIsPenSelectPointerSetted && (motionEvent.getButtonState() == 32 || motionEvent.getButtonState() == 2)) {
                                SeslInputDeviceReflector.semSetPointerType(motionEvent.getDevice(), SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_PEN_SELECT());
                                this.mIsPenSelectPointerSetted = true;
                            }
                        }
                        z2 = false;
                        if (z3) {
                        }
                        this.mNeedsHoverScroll = false;
                        if (z3) {
                            SeslInputDeviceReflector.semSetPointerType(motionEvent.getDevice(), SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_PEN_SELECT());
                            this.mIsPenSelectPointerSetted = true;
                        }
                    }
                    if (this.mNeedsHoverScroll && toolType == 3) {
                        this.mNeedsHoverScroll = false;
                    }
                } else if (action == 7) {
                    if ((this.mIsPenDragBlockEnabled && !this.mIsPenSelectPointerSetted && motionEvent.getToolType(0) == 2 && (motionEvent.getButtonState() == 32 || motionEvent.getButtonState() == 2)) || this.mIsNeedPenSelectIconSet) {
                        SeslInputDeviceReflector.semSetPointerType(motionEvent.getDevice(), SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_PEN_SELECT());
                        this.mIsPenSelectPointerSetted = true;
                    } else if (this.mIsPenDragBlockEnabled && this.mIsPenSelectPointerSetted && motionEvent.getButtonState() != 32 && motionEvent.getButtonState() != 2) {
                        SeslInputDeviceReflector.semSetPointerType(motionEvent.getDevice(), SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_DEFAULT());
                        this.mIsPenSelectPointerSetted = false;
                    }
                } else if (action == 10 && this.mIsPenSelectPointerSetted) {
                    SeslInputDeviceReflector.semSetPointerType(motionEvent.getDevice(), SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_DEFAULT());
                    this.mIsPenSelectPointerSetted = false;
                }
                if (this.mNeedsHoverScroll) {
                    return super.dispatchHoverEvent(motionEvent);
                }
                boolean canScrollHorizontally = this.mLayout.canScrollHorizontally();
                int y = (int) (canScrollHorizontally ? motionEvent.getY() : motionEvent.getX());
                int x = (int) (canScrollHorizontally ? motionEvent.getX() : motionEvent.getY());
                int width = canScrollHorizontally ? getWidth() : getHeight();
                boolean canScrollDown = canScrollDown();
                boolean canScrollUp$1 = canScrollUp$1();
                boolean z4 = motionEvent.getToolType(0) == 2;
                if ((x <= this.mHoverTopAreaHeight + 0 || x >= (width - this.mHoverBottomAreaHeight) - this.mRemainNestedScrollRange) && y > 0) {
                    if (y <= (canScrollHorizontally ? getBottom() : getRight()) && ((canScrollUp$1 || canScrollDown) && (x < 0 || x > this.mHoverTopAreaHeight + 0 || canScrollUp$1 || !this.mIsHoverOverscrolled))) {
                        int i5 = width - this.mHoverBottomAreaHeight;
                        int i6 = this.mRemainNestedScrollRange;
                        if ((x < i5 - i6 || x > width - i6 || canScrollDown || !this.mIsHoverOverscrolled) && ((!z4 || (motionEvent.getButtonState() != 32 && motionEvent.getButtonState() != 2)) && z4 && !((KeyguardManager) this.mContext.getSystemService("keyguard")).inKeyguardRestrictedInputMode())) {
                            if (this.mHasNestedScrollRange && (i3 = this.mRemainNestedScrollRange) > 0 && i3 != this.mNestedScrollRange) {
                                adjustNestedScrollRange$1();
                            }
                            if (!this.mHoverAreaEnter) {
                                this.mHoverScrollStartTime = System.currentTimeMillis();
                            }
                            if (this.mRemainNestedScrollRange != 0) {
                                Rect rect = new Rect();
                                getLocalVisibleRect(rect);
                                if (width > rect.bottom) {
                                    i = this.mRemainNestedScrollRange;
                                    if (action == 7) {
                                        if (action != 9) {
                                            if (action != 10) {
                                                return true;
                                            }
                                            if (hasMessages(0)) {
                                                removeMessages(0);
                                            }
                                            if (this.mScrollState == 1) {
                                                setScrollState(0);
                                            }
                                            SeslInputDeviceReflector.semSetPointerType(motionEvent.getDevice(), SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_DEFAULT());
                                            this.mHoverRecognitionStartTime = 0L;
                                            this.mHoverScrollStartTime = 0L;
                                            this.mIsHoverOverscrolled = false;
                                            this.mHoverAreaEnter = false;
                                            this.mIsSendHoverScrollState = false;
                                            if (this.mHoverScrollStateForListener != 0) {
                                                this.mHoverScrollStateForListener = 0;
                                            }
                                            return super.dispatchHoverEvent(motionEvent);
                                        }
                                        this.mHoverAreaEnter = true;
                                        if (x >= 0 && x <= this.mHoverTopAreaHeight + 0) {
                                            if (hasMessages(0)) {
                                                return true;
                                            }
                                            this.mHoverRecognitionStartTime = System.currentTimeMillis();
                                            SeslInputDeviceReflector.semSetPointerType(motionEvent.getDevice(), getRotatedArrowPointerIcon(false, canScrollHorizontally));
                                            this.mHoverScrollDirection = 2;
                                            sendEmptyMessage(0);
                                            return true;
                                        }
                                        if (x < (width - this.mHoverBottomAreaHeight) - i || x > width - i || hasMessages(0)) {
                                            return true;
                                        }
                                        this.mHoverRecognitionStartTime = System.currentTimeMillis();
                                        SeslInputDeviceReflector.semSetPointerType(motionEvent.getDevice(), getRotatedArrowPointerIcon(true, canScrollHorizontally));
                                        this.mHoverScrollDirection = 1;
                                        sendEmptyMessage(0);
                                        return true;
                                    }
                                    if (!this.mHoverAreaEnter) {
                                        this.mHoverAreaEnter = true;
                                        motionEvent.setAction(10);
                                        return super.dispatchHoverEvent(motionEvent);
                                    }
                                    if (x >= 0 && x <= this.mHoverTopAreaHeight + 0) {
                                        if (hasMessages(0)) {
                                            return true;
                                        }
                                        this.mHoverRecognitionStartTime = System.currentTimeMillis();
                                        if (!this.mIsHoverOverscrolled || this.mHoverScrollDirection == 1) {
                                            SeslInputDeviceReflector.semSetPointerType(motionEvent.getDevice(), getRotatedArrowPointerIcon(false, canScrollHorizontally));
                                        }
                                        this.mHoverScrollDirection = 2;
                                        sendEmptyMessage(0);
                                        return true;
                                    }
                                    if (x < (width - this.mHoverBottomAreaHeight) - i || x > width - i) {
                                        if (hasMessages(0)) {
                                            removeMessages(0);
                                            if (this.mScrollState == 1) {
                                                setScrollState(0);
                                            }
                                        }
                                        SeslInputDeviceReflector.semSetPointerType(motionEvent.getDevice(), SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_DEFAULT());
                                        this.mHoverRecognitionStartTime = 0L;
                                        this.mHoverScrollStartTime = 0L;
                                        this.mIsHoverOverscrolled = false;
                                        this.mHoverAreaEnter = false;
                                        this.mIsSendHoverScrollState = false;
                                        return true;
                                    }
                                    if (hasMessages(0)) {
                                        return true;
                                    }
                                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                                    if (!this.mIsHoverOverscrolled || this.mHoverScrollDirection == 2) {
                                        i2 = 1;
                                        SeslInputDeviceReflector.semSetPointerType(motionEvent.getDevice(), getRotatedArrowPointerIcon(true, canScrollHorizontally));
                                    } else {
                                        i2 = 1;
                                    }
                                    this.mHoverScrollDirection = i2;
                                    sendEmptyMessage(0);
                                    if (!isSupportGotoTop$1() || this.mGoToTopState != 2 || this.mGoToTopRect.contains(y, x)) {
                                        return true;
                                    }
                                    this.mGoToTopState = i2;
                                    this.mGoToTopView.setPressed(false);
                                    autoHide(i2);
                                    return true;
                                }
                            }
                            i = 0;
                            if (action == 7) {
                            }
                        }
                    }
                }
                if (this.mHasNestedScrollRange && (i4 = this.mRemainNestedScrollRange) > 0 && i4 != this.mNestedScrollRange) {
                    adjustNestedScrollRange$1();
                }
                if (hasMessages(0)) {
                    removeMessages(0);
                    SeslInputDeviceReflector.semSetPointerType(motionEvent.getDevice(), SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_DEFAULT());
                    if (this.mScrollState == 1) {
                        setScrollState(0);
                    }
                }
                if ((x <= this.mHoverTopAreaHeight + 0 || x >= (width - this.mHoverBottomAreaHeight) - this.mRemainNestedScrollRange) && y > 0) {
                }
                this.mIsHoverOverscrolled = false;
                if (this.mHoverAreaEnter) {
                    j = 0;
                } else {
                    j = 0;
                }
                SeslInputDeviceReflector.semSetPointerType(motionEvent.getDevice(), SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_DEFAULT());
                this.mHoverRecognitionStartTime = j;
                this.mHoverScrollStartTime = j;
                this.mHoverAreaEnter = false;
                this.mIsSendHoverScrollState = false;
                if (action == 10) {
                    if (this.mHoverScrollStateForListener != 0) {
                        this.mHoverScrollStateForListener = 0;
                    } else {
                        this.mIsHoverOverscrolled = false;
                    }
                }
                return super.dispatchHoverEvent(motionEvent);
            }
        }
        z = false;
        this.mNewTextViewHoverState = z;
        if (z) {
        }
        this.mIsNeedPenSelectIconSet = false;
        this.mOldTextViewHoverState = this.mNewTextViewHoverState;
        if (action != 9) {
        }
        if (this.mNeedsHoverScroll) {
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if ((keyCode == 19 || keyCode == 20) && keyEvent.getAction() == 0) {
            this.mIsArrowKeyPressed = true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    /* JADX WARN: Code restructure failed: missing block: B:196:0x03d3, code lost:
    
        if (r15.mChildHelper.isHidden(getFocusedChild()) == false) goto L248;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x042c, code lost:
    
        if (r1.hasFocusable() != false) goto L215;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void dispatchLayout() {
        ViewHolder viewHolder;
        View view;
        View findViewById;
        boolean z;
        ViewHolder viewHolder2;
        View childAt;
        if (this.mAdapter == null) {
            Log.w("SeslRecyclerView", "No adapter attached; skipping layout");
            return;
        }
        if (this.mLayout == null) {
            Log.e("SeslRecyclerView", "No layout manager attached; skipping layout");
            return;
        }
        this.mState.mIsMeasuring = false;
        boolean z2 = true;
        boolean z3 = this.mLastAutoMeasureSkippedDueToExact && !(this.mLastAutoMeasureNonExactMeasuredWidth == getWidth() && this.mLastAutoMeasureNonExactMeasuredHeight == getHeight());
        this.mLastAutoMeasureNonExactMeasuredWidth = 0;
        this.mLastAutoMeasureNonExactMeasuredHeight = 0;
        this.mLastAutoMeasureSkippedDueToExact = false;
        if (this.mState.mLayoutStep == 1) {
            dispatchLayoutStep1();
            this.mLayout.setExactMeasureSpecsFrom(this);
            dispatchLayoutStep2();
        } else {
            AdapterHelper adapterHelper = this.mAdapterHelper;
            if (((adapterHelper.mPostponedList.isEmpty() || adapterHelper.mPendingUpdates.isEmpty()) ? false : true) || z3 || this.mLayout.mWidth != getWidth() || this.mLayout.mHeight != getHeight()) {
                this.mLayout.setExactMeasureSpecsFrom(this);
                dispatchLayoutStep2();
            } else {
                this.mLayout.setExactMeasureSpecsFrom(this);
            }
        }
        int i = 4;
        this.mState.assertLayoutStep(4);
        startInterceptRequestLayout();
        onEnterLayoutOrScroll();
        State state = this.mState;
        state.mLayoutStep = 1;
        View view2 = null;
        if (state.mRunSimpleAnimations) {
            int childCount = this.mChildHelper.getChildCount() - 1;
            while (childCount >= 0) {
                ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(childCount));
                if (!childViewHolderInt.shouldIgnore()) {
                    long changedHolderKey = getChangedHolderKey(childViewHolderInt);
                    this.mItemAnimator.getClass();
                    ItemAnimator.ItemHolderInfo itemHolderInfo = new ItemAnimator.ItemHolderInfo();
                    View view3 = childViewHolderInt.itemView;
                    itemHolderInfo.left = view3.getLeft();
                    itemHolderInfo.top = view3.getTop();
                    view3.getRight();
                    view3.getBottom();
                    ViewHolder viewHolder3 = (ViewHolder) this.mViewInfoStore.mOldChangedHolders.get(changedHolderKey);
                    if (viewHolder3 == null || viewHolder3.shouldIgnore()) {
                        this.mViewInfoStore.addToPostLayout(childViewHolderInt, itemHolderInfo);
                    } else {
                        ViewInfoStore.InfoRecord infoRecord = (ViewInfoStore.InfoRecord) this.mViewInfoStore.mLayoutHolderMap.get(viewHolder3);
                        boolean z4 = (infoRecord == null || (infoRecord.flags & 1) == 0) ? false : true;
                        ViewInfoStore.InfoRecord infoRecord2 = (ViewInfoStore.InfoRecord) this.mViewInfoStore.mLayoutHolderMap.get(childViewHolderInt);
                        boolean z5 = (infoRecord2 == null || (infoRecord2.flags & 1) == 0) ? false : true;
                        if (z4 && viewHolder3 == childViewHolderInt) {
                            this.mViewInfoStore.addToPostLayout(childViewHolderInt, itemHolderInfo);
                        } else {
                            ItemAnimator.ItemHolderInfo popFromLayoutStep = this.mViewInfoStore.popFromLayoutStep(viewHolder3, i);
                            this.mViewInfoStore.addToPostLayout(childViewHolderInt, itemHolderInfo);
                            ItemAnimator.ItemHolderInfo popFromLayoutStep2 = this.mViewInfoStore.popFromLayoutStep(childViewHolderInt, 8);
                            if (popFromLayoutStep == null) {
                                int childCount2 = this.mChildHelper.getChildCount();
                                for (int i2 = 0; i2 < childCount2; i2++) {
                                    ViewHolder childViewHolderInt2 = getChildViewHolderInt(this.mChildHelper.getChildAt(i2));
                                    if (childViewHolderInt2 != childViewHolderInt && getChangedHolderKey(childViewHolderInt2) == changedHolderKey) {
                                        Adapter adapter = this.mAdapter;
                                        if (adapter == null || !adapter.mHasStableIds) {
                                            throw new IllegalStateException("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:" + childViewHolderInt2 + " \n View Holder 2:" + childViewHolderInt + exceptionLabel());
                                        }
                                        throw new IllegalStateException("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:" + childViewHolderInt2 + " \n View Holder 2:" + childViewHolderInt + exceptionLabel());
                                    }
                                }
                                Log.e("SeslRecyclerView", "Problem while matching changed view holders with the newones. The pre-layout information for the change holder " + viewHolder3 + " cannot be found but it is necessary for " + childViewHolderInt + exceptionLabel());
                            } else {
                                viewHolder3.setIsRecyclable(false);
                                if (z4) {
                                    addAnimatingView(viewHolder3);
                                }
                                if (viewHolder3 != childViewHolderInt) {
                                    if (z5) {
                                        addAnimatingView(childViewHolderInt);
                                    }
                                    viewHolder3.mShadowedHolder = childViewHolderInt;
                                    addAnimatingView(viewHolder3);
                                    this.mRecycler.unscrapView(viewHolder3);
                                    childViewHolderInt.setIsRecyclable(false);
                                    childViewHolderInt.mShadowingHolder = viewHolder3;
                                }
                                if (this.mItemAnimator.animateChange(viewHolder3, childViewHolderInt, popFromLayoutStep, popFromLayoutStep2)) {
                                    postAnimationRunner();
                                }
                            }
                        }
                    }
                }
                childCount--;
                i = 4;
            }
            ViewInfoStore viewInfoStore = this.mViewInfoStore;
            C04729 c04729 = this.mViewInfoProcessCallback;
            SimpleArrayMap simpleArrayMap = viewInfoStore.mLayoutHolderMap;
            for (int i3 = simpleArrayMap.size - 1; i3 >= 0; i3--) {
                ViewHolder viewHolder4 = (ViewHolder) simpleArrayMap.keyAt(i3);
                ViewInfoStore.InfoRecord infoRecord3 = (ViewInfoStore.InfoRecord) simpleArrayMap.removeAt(i3);
                int i4 = infoRecord3.flags;
                if ((i4 & 3) == 3) {
                    RecyclerView recyclerView = RecyclerView.this;
                    recyclerView.mLayout.removeAndRecycleView(viewHolder4.itemView, recyclerView.mRecycler);
                } else if ((i4 & 1) != 0) {
                    ItemAnimator.ItemHolderInfo itemHolderInfo2 = infoRecord3.preInfo;
                    if (itemHolderInfo2 == null) {
                        RecyclerView recyclerView2 = RecyclerView.this;
                        recyclerView2.mLayout.removeAndRecycleView(viewHolder4.itemView, recyclerView2.mRecycler);
                    } else {
                        ItemAnimator.ItemHolderInfo itemHolderInfo3 = infoRecord3.postInfo;
                        RecyclerView recyclerView3 = RecyclerView.this;
                        recyclerView3.mRecycler.unscrapView(viewHolder4);
                        recyclerView3.addAnimatingView(viewHolder4);
                        viewHolder4.setIsRecyclable(false);
                        if (recyclerView3.mItemAnimator.animateDisappearance(viewHolder4, itemHolderInfo2, itemHolderInfo3)) {
                            recyclerView3.postAnimationRunner();
                        }
                    }
                } else if ((i4 & 14) == 14) {
                    ItemAnimator.ItemHolderInfo itemHolderInfo4 = infoRecord3.preInfo;
                    ItemAnimator.ItemHolderInfo itemHolderInfo5 = infoRecord3.postInfo;
                    RecyclerView recyclerView4 = RecyclerView.this;
                    recyclerView4.getClass();
                    viewHolder4.setIsRecyclable(false);
                    if (recyclerView4.mItemAnimator.animateAppearance(viewHolder4, itemHolderInfo4, itemHolderInfo5)) {
                        recyclerView4.postAnimationRunner();
                    }
                } else if ((i4 & 12) == 12) {
                    ItemAnimator.ItemHolderInfo itemHolderInfo6 = infoRecord3.preInfo;
                    ItemAnimator.ItemHolderInfo itemHolderInfo7 = infoRecord3.postInfo;
                    c04729.getClass();
                    viewHolder4.setIsRecyclable(false);
                    RecyclerView recyclerView5 = RecyclerView.this;
                    if (recyclerView5.mDataSetHasChangedAfterLayout) {
                        ItemAnimator itemAnimator = recyclerView5.mItemAnimator;
                        if (itemAnimator != null && itemAnimator.animateChange(viewHolder4, viewHolder4, itemHolderInfo6, itemHolderInfo7)) {
                            recyclerView5.postAnimationRunner();
                        }
                    } else {
                        ItemAnimator itemAnimator2 = recyclerView5.mItemAnimator;
                        if (itemAnimator2 != null && itemAnimator2.animatePersistence(viewHolder4, itemHolderInfo6, itemHolderInfo7)) {
                            recyclerView5.postAnimationRunner();
                        }
                    }
                } else if ((i4 & 4) != 0) {
                    ItemAnimator.ItemHolderInfo itemHolderInfo8 = infoRecord3.preInfo;
                    RecyclerView recyclerView6 = RecyclerView.this;
                    recyclerView6.mRecycler.unscrapView(viewHolder4);
                    recyclerView6.addAnimatingView(viewHolder4);
                    viewHolder4.setIsRecyclable(false);
                    if (recyclerView6.mItemAnimator.animateDisappearance(viewHolder4, itemHolderInfo8, null)) {
                        recyclerView6.postAnimationRunner();
                    }
                } else if ((i4 & 8) != 0) {
                    ItemAnimator.ItemHolderInfo itemHolderInfo9 = infoRecord3.preInfo;
                    ItemAnimator.ItemHolderInfo itemHolderInfo10 = infoRecord3.postInfo;
                    RecyclerView recyclerView7 = RecyclerView.this;
                    recyclerView7.getClass();
                    viewHolder4.setIsRecyclable(false);
                    if (recyclerView7.mItemAnimator.animateAppearance(viewHolder4, itemHolderInfo9, itemHolderInfo10)) {
                        recyclerView7.postAnimationRunner();
                    }
                }
                infoRecord3.flags = 0;
                infoRecord3.preInfo = null;
                infoRecord3.postInfo = null;
                ViewInfoStore.InfoRecord.sPool.release(infoRecord3);
            }
        }
        this.mLastBlackTop = this.mBlackTop;
        this.mBlackTop = -1;
        if (this.mDrawRect && !canScrollVertically(-1) && !canScrollVertically(1)) {
            int itemCount = this.mAdapter.getItemCount() - 1;
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.mLayout;
            boolean z6 = linearLayoutManager.mReverseLayout;
            if (z6 && linearLayoutManager.mStackFromEnd) {
                this.mDrawReverse = true;
                itemCount = 0;
            } else if (z6 || linearLayoutManager.mStackFromEnd) {
                this.mDrawRect = false;
                itemCount = -1;
            }
            if (itemCount >= 0 && itemCount <= findLastVisibleItemPosition() && (childAt = this.mChildHelper.getChildAt(itemCount)) != null) {
                this.mBlackTop = childAt.getBottom();
            }
        }
        this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        State state2 = this.mState;
        state2.mPreviousLayoutItemCount = state2.mItemCount;
        this.mDataSetHasChangedAfterLayout = false;
        this.mDispatchItemsChangedEvent = false;
        state2.mRunSimpleAnimations = false;
        state2.mRunPredictiveAnimations = false;
        this.mLayout.mRequestedSimpleAnimations = false;
        ArrayList arrayList = this.mRecycler.mChangedScrap;
        if (arrayList != null) {
            arrayList.clear();
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager.mPrefetchMaxObservedInInitialPrefetch) {
            layoutManager.mPrefetchMaxCountObserved = 0;
            layoutManager.mPrefetchMaxObservedInInitialPrefetch = false;
            this.mRecycler.updateViewCacheSize();
        }
        this.mLayout.onLayoutCompleted(this.mState);
        onExitLayoutOrScroll(true);
        stopInterceptRequestLayout(false);
        ViewInfoStore viewInfoStore2 = this.mViewInfoStore;
        viewInfoStore2.mLayoutHolderMap.clear();
        viewInfoStore2.mOldChangedHolders.clear();
        int[] iArr = this.mMinMaxLayoutPositions;
        int i5 = iArr[0];
        int i6 = iArr[1];
        findMinMaxChildLayoutPositions(iArr);
        int[] iArr2 = this.mMinMaxLayoutPositions;
        if (iArr2[0] == i5 && iArr2[1] == i6) {
            z2 = false;
        }
        if (z2) {
            dispatchOnScrolled(0, 0);
        }
        if (this.mPreserveFocusAfterLayout && this.mAdapter != null && hasFocus() && getDescendantFocusability() != 393216 && (getDescendantFocusability() != 131072 || !isFocused())) {
            if (!isFocused()) {
            }
            long j = this.mState.mFocusedItemId;
            if (j == -1 || !(z = this.mAdapter.mHasStableIds)) {
                viewHolder = null;
            } else {
                if (z) {
                    int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
                    viewHolder2 = null;
                    for (int i7 = 0; i7 < unfilteredChildCount; i7++) {
                        viewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i7));
                        if (viewHolder != null && !viewHolder.isRemoved() && viewHolder.mItemId == j) {
                            if (!this.mChildHelper.isHidden(viewHolder.itemView)) {
                                break;
                            } else {
                                viewHolder2 = viewHolder;
                            }
                        }
                    }
                } else {
                    viewHolder2 = null;
                }
                viewHolder = viewHolder2;
            }
            if (viewHolder != null) {
                ChildHelper childHelper = this.mChildHelper;
                view = viewHolder.itemView;
                if (!childHelper.isHidden(view)) {
                }
            }
            if (this.mChildHelper.getChildCount() > 0) {
                State state3 = this.mState;
                int i8 = state3.mFocusedItemPosition;
                int i9 = i8 != -1 ? i8 : 0;
                int itemCount2 = state3.getItemCount();
                for (int i10 = i9; i10 < itemCount2; i10++) {
                    ViewHolder findViewHolderForAdapterPosition = findViewHolderForAdapterPosition(i10);
                    if (findViewHolderForAdapterPosition == null) {
                        break;
                    }
                    View view4 = findViewHolderForAdapterPosition.itemView;
                    if (view4.hasFocusable()) {
                        view2 = view4;
                        break;
                    }
                }
                for (int min = Math.min(itemCount2, i9) - 1; min >= 0; min--) {
                    ViewHolder findViewHolderForAdapterPosition2 = findViewHolderForAdapterPosition(min);
                    if (findViewHolderForAdapterPosition2 == null) {
                        break;
                    }
                    view = findViewHolderForAdapterPosition2.itemView;
                    if (view.hasFocusable()) {
                        view2 = view;
                        break;
                    }
                }
            }
            if (view2 != null) {
                int i11 = this.mState.mFocusedSubChildId;
                if (i11 != -1 && (findViewById = view2.findViewById(i11)) != null && findViewById.isFocusable()) {
                    view2 = findViewById;
                }
                view2.requestFocus();
            }
        }
        State state4 = this.mState;
        state4.mFocusedItemId = -1L;
        state4.mFocusedItemPosition = -1;
        state4.mFocusedSubChildId = -1;
    }

    public final void dispatchLayoutStep1() {
        View findContainingItemView;
        this.mState.assertLayoutStep(1);
        fillRemainingScrollValues(this.mState);
        this.mState.mIsMeasuring = false;
        startInterceptRequestLayout();
        ViewInfoStore viewInfoStore = this.mViewInfoStore;
        viewInfoStore.mLayoutHolderMap.clear();
        viewInfoStore.mOldChangedHolders.clear();
        onEnterLayoutOrScroll();
        processAdapterUpdatesAndSetAnimationFlags();
        ViewHolder viewHolder = null;
        View focusedChild = (this.mPreserveFocusAfterLayout && hasFocus() && this.mAdapter != null) ? getFocusedChild() : null;
        if (focusedChild != null && (findContainingItemView = findContainingItemView(focusedChild)) != null) {
            viewHolder = getChildViewHolder(findContainingItemView);
        }
        if (viewHolder == null) {
            State state = this.mState;
            state.mFocusedItemId = -1L;
            state.mFocusedItemPosition = -1;
            state.mFocusedSubChildId = -1;
        } else {
            State state2 = this.mState;
            state2.mFocusedItemId = this.mAdapter.mHasStableIds ? viewHolder.mItemId : -1L;
            state2.mFocusedItemPosition = this.mDataSetHasChangedAfterLayout ? -1 : viewHolder.isRemoved() ? viewHolder.mOldPosition : viewHolder.getAbsoluteAdapterPosition();
            State state3 = this.mState;
            View view = viewHolder.itemView;
            int id = view.getId();
            while (!view.isFocused() && (view instanceof ViewGroup) && view.hasFocus()) {
                view = ((ViewGroup) view).getFocusedChild();
                if (view.getId() != -1) {
                    id = view.getId();
                }
            }
            state3.mFocusedSubChildId = id;
        }
        State state4 = this.mState;
        state4.mTrackOldChangeHolders = state4.mRunSimpleAnimations && this.mItemsChanged;
        this.mItemsChanged = false;
        this.mItemsAddedOrRemoved = false;
        state4.mInPreLayout = state4.mRunPredictiveAnimations;
        state4.mItemCount = this.mAdapter.getItemCount();
        findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (this.mState.mRunSimpleAnimations) {
            int childCount = this.mChildHelper.getChildCount();
            for (int i = 0; i < childCount; i++) {
                ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(i));
                if (!childViewHolderInt.shouldIgnore() && (!childViewHolderInt.isInvalid() || this.mAdapter.mHasStableIds)) {
                    ItemAnimator itemAnimator = this.mItemAnimator;
                    ItemAnimator.buildAdapterChangeFlagsForAnimations(childViewHolderInt);
                    childViewHolderInt.getUnmodifiedPayloads();
                    itemAnimator.getClass();
                    ItemAnimator.ItemHolderInfo itemHolderInfo = new ItemAnimator.ItemHolderInfo();
                    View view2 = childViewHolderInt.itemView;
                    itemHolderInfo.left = view2.getLeft();
                    itemHolderInfo.top = view2.getTop();
                    view2.getRight();
                    view2.getBottom();
                    SimpleArrayMap simpleArrayMap = this.mViewInfoStore.mLayoutHolderMap;
                    ViewInfoStore.InfoRecord infoRecord = (ViewInfoStore.InfoRecord) simpleArrayMap.get(childViewHolderInt);
                    if (infoRecord == null) {
                        infoRecord = ViewInfoStore.InfoRecord.obtain();
                        simpleArrayMap.put(childViewHolderInt, infoRecord);
                    }
                    infoRecord.preInfo = itemHolderInfo;
                    infoRecord.flags |= 4;
                    if (this.mState.mTrackOldChangeHolders) {
                        if (((childViewHolderInt.mFlags & 2) != 0) && !childViewHolderInt.isRemoved() && !childViewHolderInt.shouldIgnore() && !childViewHolderInt.isInvalid()) {
                            this.mViewInfoStore.mOldChangedHolders.put(getChangedHolderKey(childViewHolderInt), childViewHolderInt);
                        }
                    }
                }
            }
        }
        if (this.mState.mRunPredictiveAnimations) {
            int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
            for (int i2 = 0; i2 < unfilteredChildCount; i2++) {
                ViewHolder childViewHolderInt2 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i2));
                if (!childViewHolderInt2.shouldIgnore() && childViewHolderInt2.mOldPosition == -1) {
                    childViewHolderInt2.mOldPosition = childViewHolderInt2.mPosition;
                }
            }
            State state5 = this.mState;
            boolean z = state5.mStructureChanged;
            state5.mStructureChanged = false;
            this.mLayout.onLayoutChildren(this.mRecycler, state5);
            this.mState.mStructureChanged = z;
            for (int i3 = 0; i3 < this.mChildHelper.getChildCount(); i3++) {
                ViewHolder childViewHolderInt3 = getChildViewHolderInt(this.mChildHelper.getChildAt(i3));
                if (!childViewHolderInt3.shouldIgnore()) {
                    ViewInfoStore.InfoRecord infoRecord2 = (ViewInfoStore.InfoRecord) this.mViewInfoStore.mLayoutHolderMap.get(childViewHolderInt3);
                    if (!((infoRecord2 == null || (infoRecord2.flags & 4) == 0) ? false : true)) {
                        ItemAnimator.buildAdapterChangeFlagsForAnimations(childViewHolderInt3);
                        boolean z2 = (childViewHolderInt3.mFlags & 8192) != 0;
                        ItemAnimator itemAnimator2 = this.mItemAnimator;
                        childViewHolderInt3.getUnmodifiedPayloads();
                        itemAnimator2.getClass();
                        ItemAnimator.ItemHolderInfo itemHolderInfo2 = new ItemAnimator.ItemHolderInfo();
                        View view3 = childViewHolderInt3.itemView;
                        itemHolderInfo2.left = view3.getLeft();
                        itemHolderInfo2.top = view3.getTop();
                        view3.getRight();
                        view3.getBottom();
                        if (z2) {
                            recordAnimationInfoIfBouncedHiddenView(childViewHolderInt3, itemHolderInfo2);
                        } else {
                            SimpleArrayMap simpleArrayMap2 = this.mViewInfoStore.mLayoutHolderMap;
                            ViewInfoStore.InfoRecord infoRecord3 = (ViewInfoStore.InfoRecord) simpleArrayMap2.get(childViewHolderInt3);
                            if (infoRecord3 == null) {
                                infoRecord3 = ViewInfoStore.InfoRecord.obtain();
                                simpleArrayMap2.put(childViewHolderInt3, infoRecord3);
                            }
                            infoRecord3.flags |= 2;
                            infoRecord3.preInfo = itemHolderInfo2;
                        }
                    }
                }
            }
            clearOldPositions();
        } else {
            clearOldPositions();
        }
        onExitLayoutOrScroll(true);
        stopInterceptRequestLayout(false);
        this.mState.mLayoutStep = 2;
    }

    public final void dispatchLayoutStep2() {
        startInterceptRequestLayout();
        onEnterLayoutOrScroll();
        this.mState.assertLayoutStep(6);
        this.mAdapterHelper.consumeUpdatesInOnePass();
        this.mState.mItemCount = this.mAdapter.getItemCount();
        this.mState.mDeletedInvisibleItemCountSincePreviousLayout = 0;
        if (this.mPendingSavedState != null) {
            Adapter adapter = this.mAdapter;
            adapter.getClass();
            int i = AbstractC046520.f179x3d106619[adapter.mStateRestorationPolicy.ordinal()];
            if (i != 1 && (i != 2 || adapter.getItemCount() > 0)) {
                Parcelable parcelable = this.mPendingSavedState.mLayoutState;
                if (parcelable != null) {
                    this.mLayout.onRestoreInstanceState(parcelable);
                }
                this.mPendingSavedState = null;
            }
        }
        State state = this.mState;
        state.mInPreLayout = false;
        this.mLayout.onLayoutChildren(this.mRecycler, state);
        State state2 = this.mState;
        state2.mStructureChanged = false;
        state2.mRunSimpleAnimations = state2.mRunSimpleAnimations && this.mItemAnimator != null;
        state2.mLayoutStep = 4;
        onExitLayoutOrScroll(true);
        stopInterceptRequestLayout(false);
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
        return getScrollingChildHelper().dispatchNestedScrollInternal(i, i2, i3, i4, iArr, 0, null);
    }

    final void dispatchOnScrolled(int i, int i2) {
        this.mDispatchScrollCounter++;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        onScrollChanged(scrollX, scrollY, scrollX - i, scrollY - i2);
        SeslRecyclerViewFastScroller seslRecyclerViewFastScroller = this.mFastScroller;
        if (seslRecyclerViewFastScroller != null && this.mAdapter != null && (i != 0 || i2 != 0)) {
            seslRecyclerViewFastScroller.onScroll(findFirstVisibleItemPosition(), getChildCount(), this.mAdapter.getItemCount());
        }
        List list = this.mScrollListeners;
        if (list != null) {
            int size = ((ArrayList) list).size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                } else {
                    ((OnScrollListener) ((ArrayList) this.mScrollListeners).get(size)).onScrolled(this, i, i2);
                }
            }
        }
        this.mDispatchScrollCounter--;
    }

    @Override // android.view.View
    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchRestoreInstanceState(SparseArray sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchSaveInstanceState(SparseArray sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    /* JADX WARN: Removed duplicated region for block: B:132:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0172  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean z;
        int i;
        int i2;
        boolean z2;
        if (this.mLayout == null) {
            Log.d("SeslRecyclerView", "No layout manager attached; skipping gototop & multiselection");
            return super.dispatchTouchEvent(motionEvent);
        }
        int actionMasked = motionEvent.getActionMasked();
        int x = (int) (motionEvent.getX() + 0.5f);
        int y = (int) (motionEvent.getY() + 0.5f);
        final int i3 = 0;
        boolean z3 = motionEvent.getToolType(0) == 2;
        boolean z4 = (motionEvent.getButtonState() & 32) != 0;
        if (this.mPenDragSelectedItemArray == null) {
            this.mPenDragSelectedItemArray = new ArrayList();
        }
        int height = getHeight();
        if (this.mIsPenSelectionEnabled) {
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(SeslTextViewReflector.mClass, "hidden_semIsTextSelectionProgressing", new Class[0]);
            if (declaredMethod != null) {
                Object invoke = SeslBaseReflector.invoke(null, declaredMethod, new Object[0]);
                if (invoke instanceof Boolean) {
                    z2 = ((Boolean) invoke).booleanValue();
                    if (!z2) {
                        z = true;
                        this.mIsNeedPenSelection = z;
                        if (actionMasked != 0) {
                            if (actionMasked != 1) {
                                if (actionMasked != 2) {
                                    if (actionMasked != 3) {
                                        switch (actionMasked) {
                                            case IKnoxCustomManager.Stub.TRANSACTION_getWifiState /* 212 */:
                                                if (!isSupportGotoTop$1() && this.mGoToTopState == 2) {
                                                    if (canScrollUp$1()) {
                                                        Log.d("SeslRecyclerView", " can scroll top ");
                                                        int findFirstVisibleItemPosition = findFirstVisibleItemPosition();
                                                        boolean z5 = findFirstVisibleItemPosition > 0;
                                                        int findLastVisibleItemPosition = z5 ? findFirstVisibleItemPosition : findLastVisibleItemPosition();
                                                        int abs = Math.abs(((z5 ? 1 : -1) * 0) + (getChildCount() * 2));
                                                        if (computeVerticalScrollOffset() != 0) {
                                                            stopScroll();
                                                        }
                                                        if (Settings.System.getInt(getContext().getContentResolver(), "remove_animations", 0) == 1) {
                                                            scrollToPosition(0);
                                                        } else {
                                                            LayoutManager layoutManager = this.mLayout;
                                                            if (layoutManager instanceof StaggeredGridLayoutManager) {
                                                                ((StaggeredGridLayoutManager) layoutManager).scrollToPositionWithOffset(0, false);
                                                            } else {
                                                                if (findFirstVisibleItemPosition > 0 && ((z5 && abs > 0 && abs < findLastVisibleItemPosition) || (!z5 && abs > 0 && abs > findLastVisibleItemPosition))) {
                                                                    if (layoutManager instanceof LinearLayoutManager) {
                                                                        if ((layoutManager instanceof GridLayoutManager) && abs < (i = ((GridLayoutManager) layoutManager).mSpanCount)) {
                                                                            abs = i;
                                                                        }
                                                                        ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(abs, 0);
                                                                    } else {
                                                                        scrollToPosition(abs);
                                                                    }
                                                                }
                                                                post(new Runnable() { // from class: androidx.recyclerview.widget.RecyclerView.12
                                                                    @Override // java.lang.Runnable
                                                                    public final void run() {
                                                                        RecyclerView recyclerView = RecyclerView.this;
                                                                        if (recyclerView.mLayoutSuppressed) {
                                                                            return;
                                                                        }
                                                                        LayoutManager layoutManager2 = recyclerView.mLayout;
                                                                        if (layoutManager2 == null) {
                                                                            Log.e("SeslRecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
                                                                            return;
                                                                        }
                                                                        if (!(layoutManager2 instanceof LinearLayoutManager)) {
                                                                            layoutManager2.smoothScrollToPosition(recyclerView, i3);
                                                                            return;
                                                                        }
                                                                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager2;
                                                                        int i4 = i3;
                                                                        LinearLayoutManager.SmoothScrollerJumpIfNeeded smoothScrollerJumpIfNeeded = linearLayoutManager.new SmoothScrollerJumpIfNeeded(recyclerView.getContext());
                                                                        recyclerView.showGoToTop();
                                                                        smoothScrollerJumpIfNeeded.mTargetPosition = i4;
                                                                        linearLayoutManager.startSmoothScroll(smoothScrollerJumpIfNeeded);
                                                                        Log.d("SeslLinearLayoutManager", "smoothScroller2");
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }
                                                    autoHide(0);
                                                    playSoundEffect(0);
                                                    return true;
                                                }
                                                if (!this.mIsPenPressed) {
                                                    multiSelectionEnd();
                                                    return true;
                                                }
                                                multiSelectionEnd();
                                                break;
                                                break;
                                        }
                                        return super.dispatchTouchEvent(motionEvent);
                                    }
                                    if (isSupportGotoTop$1() && (i2 = this.mGoToTopState) != 0) {
                                        if (i2 == 2) {
                                            this.mGoToTopState = 1;
                                        }
                                        this.mGoToTopView.setPressed(false);
                                    }
                                }
                                if ((z3 && z4) || actionMasked == 213) {
                                    multiSelection(x, y, height);
                                } else {
                                    if (this.mIsCtrlMultiSelection) {
                                        multiSelection(x, y, height);
                                        return true;
                                    }
                                    if (isSupportGotoTop$1() && this.mGoToTopState == 2) {
                                        if (!this.mGoToTopRect.contains(x, y)) {
                                            this.mGoToTopState = 1;
                                            this.mGoToTopView.setPressed(false);
                                            autoHide(1);
                                        }
                                        return true;
                                    }
                                }
                                return super.dispatchTouchEvent(motionEvent);
                            }
                            if ((!z3 || !z4) && this.mIsCtrlMultiSelection) {
                                multiSelectionEnd();
                                this.mIsCtrlMultiSelection = false;
                                return true;
                            }
                            if (!isSupportGotoTop$1()) {
                            }
                            if (!this.mIsPenPressed) {
                            }
                        }
                        if ((z3 && actionMasked != 211) || !z4) {
                            isSupportGotoTop$1();
                            if (isSupportGotoTop$1() && this.mGoToTopState != 2 && this.mGoToTopRect.contains(x, y)) {
                                setupGoToTop(2);
                                this.mGoToTopView.setPressed(true);
                                return true;
                            }
                            if (this.mIsCtrlKeyPressed && motionEvent.getToolType(0) == 3) {
                                this.mIsCtrlMultiSelection = true;
                                this.mIsNeedPenSelection = true;
                                multiSelection(x, y, height);
                                return true;
                            }
                        } else if (this.mPenDragSelectedItemArray == null) {
                            this.mPenDragSelectedItemArray = new ArrayList();
                        }
                        return super.dispatchTouchEvent(motionEvent);
                    }
                }
            }
            z2 = false;
            if (!z2) {
            }
        }
        z = false;
        this.mIsNeedPenSelection = z;
        if (actionMasked != 0) {
        }
        if (z3) {
        }
        if (this.mPenDragSelectedItemArray == null) {
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        boolean z;
        ImageView imageView;
        super.draw(canvas);
        int size = this.mItemDecorations.size();
        for (int i = 0; i < size; i++) {
            ((ItemDecoration) this.mItemDecorations.get(i)).onDrawOver(canvas, this);
        }
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect == null || edgeEffect.isFinished()) {
            z = false;
        } else {
            int save = canvas.save();
            int paddingBottom = this.mClipToPadding ? getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((-getHeight()) + paddingBottom, 0.0f);
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
            z |= edgeEffect8 != null && edgeEffect8.draw(canvas);
            canvas.restoreToCount(save4);
        }
        if ((z || this.mItemAnimator == null || this.mItemDecorations.size() <= 0 || !this.mItemAnimator.isRunning()) ? z : true) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        }
        if (this.mEnableGoToTop) {
            this.mGoToTopView.setTranslationY(getScrollY());
            if (this.mGoToTopState != 0 && !canScrollUp$1()) {
                setupGoToTop(0);
            }
        }
        if (!isGoToTopAvailableEnvironment() && (imageView = this.mGoToTopView) != null && imageView.getAlpha() != 0.0f) {
            this.mGoToTopView.setAlpha(0.0f);
        }
        if (!this.mIsPenDragBlockEnabled || this.mLayout == null) {
            return;
        }
        if (this.mPenDragBlockLeft == 0 && this.mPenDragBlockTop == 0) {
            return;
        }
        int findFirstVisibleItemPosition = findFirstVisibleItemPosition();
        int findLastVisibleItemPosition = findLastVisibleItemPosition();
        int i2 = this.mPenTrackedChildPosition;
        if (i2 >= findFirstVisibleItemPosition && i2 <= findLastVisibleItemPosition) {
            View findViewByPosition = this.mLayout.findViewByPosition(i2);
            this.mPenTrackedChild = findViewByPosition;
            this.mPenDragStartY = (findViewByPosition != null ? findViewByPosition.getTop() : 0) + this.mPenDistanceFromTrackedChildTop;
        }
        int i3 = this.mPenDragStartY;
        int i4 = this.mPenDragEndY;
        int i5 = i3 < i4 ? i3 : i4;
        this.mPenDragBlockTop = i5;
        if (i4 > i3) {
            i3 = i4;
        }
        this.mPenDragBlockRect.set(this.mPenDragBlockLeft, i5, this.mPenDragBlockRight, i3);
        this.mPenDragBlockImage.setBounds(this.mPenDragBlockRect);
        this.mPenDragBlockImage.draw(canvas);
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        return super.drawChild(canvas, view, j);
    }

    final void ensureBottomGlow() {
        if (this.mBottomGlow != null) {
            return;
        }
        EdgeEffect createEdgeEffect = this.mEdgeEffectFactory.createEdgeEffect(this);
        this.mBottomGlow = createEdgeEffect;
        if (this.mClipToPadding) {
            createEdgeEffect.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
        } else {
            createEdgeEffect.setSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    final void ensureLeftGlow() {
        if (this.mLeftGlow != null) {
            return;
        }
        EdgeEffect createEdgeEffect = this.mEdgeEffectFactory.createEdgeEffect(this);
        this.mLeftGlow = createEdgeEffect;
        if (this.mClipToPadding) {
            createEdgeEffect.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
        } else {
            createEdgeEffect.setSize(getMeasuredHeight(), getMeasuredWidth());
        }
    }

    final void ensureRightGlow() {
        if (this.mRightGlow != null) {
            return;
        }
        EdgeEffect createEdgeEffect = this.mEdgeEffectFactory.createEdgeEffect(this);
        this.mRightGlow = createEdgeEffect;
        if (this.mClipToPadding) {
            createEdgeEffect.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
        } else {
            createEdgeEffect.setSize(getMeasuredHeight(), getMeasuredWidth());
        }
    }

    final void ensureTopGlow() {
        if (this.mTopGlow != null) {
            return;
        }
        EdgeEffect createEdgeEffect = this.mEdgeEffectFactory.createEdgeEffect(this);
        this.mTopGlow = createEdgeEffect;
        if (this.mClipToPadding) {
            createEdgeEffect.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
        } else {
            createEdgeEffect.setSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public final String exceptionLabel() {
        return " " + super.toString() + ", adapter:" + this.mAdapter + ", layout:" + this.mLayout + ", context:" + getContext();
    }

    public final void fillRemainingScrollValues(State state) {
        if (this.mScrollState != 2) {
            state.mRemainingScrollHorizontal = 0;
            state.mRemainingScrollVertical = 0;
        } else {
            OverScroller overScroller = this.mViewFlinger.mOverScroller;
            state.mRemainingScrollHorizontal = overScroller.getFinalX() - overScroller.getCurrX();
            state.mRemainingScrollVertical = overScroller.getFinalY() - overScroller.getCurrY();
        }
    }

    public final View findChildViewUnder(float f, float f2) {
        for (int childCount = this.mChildHelper.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = this.mChildHelper.getChildAt(childCount);
            float translationX = childAt.getTranslationX();
            float translationY = childAt.getTranslationY();
            if (f >= childAt.getLeft() + translationX && f <= childAt.getRight() + translationX && f2 >= childAt.getTop() + translationY && f2 <= childAt.getBottom() + translationY) {
                return childAt;
            }
        }
        return null;
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

    public final int findFirstChildPosition() {
        int i;
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager instanceof LinearLayoutManager) {
            i = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            i = ((StaggeredGridLayoutManager) this.mLayout).findFirstVisibleItemPositions()[layoutManager.getLayoutDirection() == 1 ? ((StaggeredGridLayoutManager) this.mLayout).mSpanCount - 1 : 0];
        } else {
            i = 0;
        }
        if (i == -1) {
            return 0;
        }
        return i;
    }

    public final int findFirstVisibleItemPosition() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions()[0];
        }
        return -1;
    }

    public final boolean findInterceptingOnItemTouchListener(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int size = this.mOnItemTouchListeners.size();
        for (int i = 0; i < size; i++) {
            OnItemTouchListener onItemTouchListener = (OnItemTouchListener) this.mOnItemTouchListeners.get(i);
            if (onItemTouchListener.onInterceptTouchEvent(this, motionEvent) && action != 3) {
                this.mInterceptingOnItemTouchListener = onItemTouchListener;
                return true;
            }
        }
        return false;
    }

    public final int findLastVisibleItemPosition() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
            return -1;
        }
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        int[] iArr = new int[staggeredGridLayoutManager.mSpanCount];
        for (int i = 0; i < staggeredGridLayoutManager.mSpanCount; i++) {
            StaggeredGridLayoutManager.Span span = staggeredGridLayoutManager.mSpans[i];
            iArr[i] = span.this$0.mReverseLayout ? span.findOnePartiallyOrCompletelyVisibleChild(0, span.mViews.size(), true, false) : span.findOnePartiallyOrCompletelyVisibleChild(r6.size() - 1, -1, true, false);
        }
        return iArr[0];
    }

    public final void findMinMaxChildLayoutPositions(int[] iArr) {
        int childCount = this.mChildHelper.getChildCount();
        if (childCount == 0) {
            iArr[0] = -1;
            iArr[1] = -1;
            return;
        }
        int i = Integer.MAX_VALUE;
        int i2 = VideoPlayer.MEDIA_ERROR_SYSTEM;
        for (int i3 = 0; i3 < childCount; i3++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(i3));
            if (!childViewHolderInt.shouldIgnore()) {
                int layoutPosition = childViewHolderInt.getLayoutPosition();
                if (layoutPosition < i) {
                    i = layoutPosition;
                }
                if (layoutPosition > i2) {
                    i2 = layoutPosition;
                }
            }
        }
        iArr[0] = i;
        iArr[1] = i2;
    }

    public final ViewHolder findViewHolderForAdapterPosition(int i) {
        ViewHolder viewHolder = null;
        if (this.mDataSetHasChangedAfterLayout) {
            return null;
        }
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i2 = 0; i2 < unfilteredChildCount; i2++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i2));
            if (childViewHolderInt != null && !childViewHolderInt.isRemoved() && getAdapterPositionInRecyclerView(childViewHolderInt) == i) {
                if (!this.mChildHelper.isHidden(childViewHolderInt.itemView)) {
                    return childViewHolderInt;
                }
                viewHolder = childViewHolderInt;
            }
        }
        return viewHolder;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0036 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ViewHolder findViewHolderForPosition(int i, boolean z) {
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        ViewHolder viewHolder = null;
        for (int i2 = 0; i2 < unfilteredChildCount; i2++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i2));
            if (childViewHolderInt != null && !childViewHolderInt.isRemoved()) {
                if (z) {
                    if (childViewHolderInt.mPosition != i) {
                        continue;
                    }
                    if (this.mChildHelper.isHidden(childViewHolderInt.itemView)) {
                        return childViewHolderInt;
                    }
                    viewHolder = childViewHolderInt;
                } else {
                    if (childViewHolderInt.getLayoutPosition() != i) {
                        continue;
                    }
                    if (this.mChildHelper.isHidden(childViewHolderInt.itemView)) {
                    }
                }
            }
        }
        return viewHolder;
    }

    /* JADX WARN: Code restructure failed: missing block: B:117:0x017f, code lost:
    
        if (r2 > 0) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x019d, code lost:
    
        if (r5 > 0) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x01a0, code lost:
    
        if (r2 < 0) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x01a3, code lost:
    
        if (r5 < 0) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x01ab, code lost:
    
        if ((r5 * r3) <= 0) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x01b3, code lost:
    
        if ((r5 * r3) >= 0) goto L147;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01da  */
    @Override // android.view.ViewGroup, android.view.ViewParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View focusSearch(View view, int i) {
        View view2;
        int i2;
        int top;
        int top2;
        int i3;
        char c;
        boolean z;
        View onInterceptFocusSearch = this.mLayout.onInterceptFocusSearch(view, i);
        if (onInterceptFocusSearch != null) {
            return onInterceptFocusSearch;
        }
        boolean z2 = true;
        boolean z3 = (this.mAdapter == null || this.mLayout == null || isComputingLayout() || this.mLayoutSuppressed) ? false : true;
        FocusFinder focusFinder = FocusFinder.getInstance();
        if (z3 && (i == 2 || i == 1)) {
            if (this.mLayout.canScrollVertically()) {
                if (focusFinder.findNextFocus(this, view, i == 2 ? 130 : 33) == null) {
                    z = true;
                    if (!z && this.mLayout.canScrollHorizontally()) {
                        z = focusFinder.findNextFocus(this, view, !((this.mLayout.getLayoutDirection() != 1) ^ (i != 2)) ? 66 : 17) != null;
                    }
                    if (z) {
                        consumePendingUpdateOperations();
                        if (findContainingItemView(view) == null) {
                            return null;
                        }
                        startInterceptRequestLayout();
                        this.mLayout.onFocusSearchFailed(view, i, this.mRecycler, this.mState);
                        stopInterceptRequestLayout(false);
                    }
                    view2 = focusFinder.findNextFocus(this, view, i);
                }
            }
            z = false;
            if (!z) {
                if (focusFinder.findNextFocus(this, view, !((this.mLayout.getLayoutDirection() != 1) ^ (i != 2)) ? 66 : 17) != null) {
                }
            }
            if (z) {
            }
            view2 = focusFinder.findNextFocus(this, view, i);
        } else {
            View findNextFocus = focusFinder.findNextFocus(this, view, i);
            if (findNextFocus == null && z3) {
                consumePendingUpdateOperations();
                if (findContainingItemView(view) == null) {
                    return null;
                }
                startInterceptRequestLayout();
                view2 = this.mLayout.onFocusSearchFailed(view, i, this.mRecycler, this.mState);
                stopInterceptRequestLayout(false);
            } else {
                view2 = findNextFocus;
            }
        }
        if (view2 != null && !view2.hasFocusable()) {
            if (getFocusedChild() == null || (i == 33 && view != null && view.getBottom() < view2.getBottom() && !canScrollVertically(-1))) {
                return super.focusSearch(view, i);
            }
            requestChildOnScreen(view2, null);
            return view;
        }
        if (view2 != null && view2 != this && view2 != view) {
            if (findContainingItemView(view2) == null) {
                z2 = false;
            } else if (view != null && findContainingItemView(view) != null) {
                this.mTempRect.set(0, 0, view.getWidth(), view.getHeight());
                this.mTempRect2.set(0, 0, view2.getWidth(), view2.getHeight());
                offsetDescendantRectToMyCoords(view, this.mTempRect);
                offsetDescendantRectToMyCoords(view2, this.mTempRect2);
                int i4 = this.mLayout.getLayoutDirection() != 1 ? 1 : -1;
                Rect rect = this.mTempRect;
                int i5 = rect.left;
                Rect rect2 = this.mTempRect2;
                int i6 = rect2.left;
                if ((i5 < i6 || rect.right <= i6) && rect.right < rect2.right) {
                    i3 = 1;
                } else {
                    int i7 = rect.right;
                    int i8 = rect2.right;
                    i3 = ((i7 > i8 || i5 >= i8) && i5 > i6) ? -1 : 0;
                }
                int i9 = rect.top;
                int i10 = rect2.top;
                if ((i9 < i10 || rect.bottom <= i10) && rect.bottom < rect2.bottom) {
                    c = 1;
                } else {
                    int i11 = rect.bottom;
                    int i12 = rect2.bottom;
                    c = ((i11 > i12 || i9 >= i12) && i9 > i10) ? (char) 65535 : (char) 0;
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
            if (!z2) {
                view2 = super.focusSearch(view, i);
            }
            if (this.mIsArrowKeyPressed && view2 == null && (this.mLayout instanceof StaggeredGridLayoutManager)) {
                if (i != 130) {
                    top = getFocusedChild().getBottom();
                    top2 = getBottom();
                } else if (i == 33) {
                    top = getFocusedChild().getTop();
                    top2 = getTop();
                } else {
                    i2 = 0;
                    ((StaggeredGridLayoutManager) this.mLayout).scrollBy(i2, this.mRecycler, this.mState);
                    this.mIsArrowKeyPressed = false;
                }
                i2 = top - top2;
                ((StaggeredGridLayoutManager) this.mLayout).scrollBy(i2, this.mRecycler, this.mState);
                this.mIsArrowKeyPressed = false;
            }
            return view2;
        }
        z2 = false;
        if (!z2) {
        }
        if (this.mIsArrowKeyPressed) {
            if (i != 130) {
            }
            i2 = top - top2;
            ((StaggeredGridLayoutManager) this.mLayout).scrollBy(i2, this.mRecycler, this.mState);
            this.mIsArrowKeyPressed = false;
        }
        return view2;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
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

    public final int getAdapterPositionInRecyclerView(ViewHolder viewHolder) {
        if (!((viewHolder.mFlags & 524) != 0) && viewHolder.isBound()) {
            AdapterHelper adapterHelper = this.mAdapterHelper;
            int i = viewHolder.mPosition;
            ArrayList arrayList = adapterHelper.mPendingUpdates;
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                AdapterHelper.UpdateOp updateOp = (AdapterHelper.UpdateOp) arrayList.get(i2);
                int i3 = updateOp.cmd;
                if (i3 != 1) {
                    if (i3 == 2) {
                        int i4 = updateOp.positionStart;
                        if (i4 <= i) {
                            int i5 = updateOp.itemCount;
                            if (i4 + i5 <= i) {
                                i -= i5;
                            }
                        } else {
                            continue;
                        }
                    } else if (i3 == 8) {
                        int i6 = updateOp.positionStart;
                        if (i6 == i) {
                            i = updateOp.itemCount;
                        } else {
                            if (i6 < i) {
                                i--;
                            }
                            if (updateOp.itemCount <= i) {
                                i++;
                            }
                        }
                    }
                } else if (updateOp.positionStart <= i) {
                    i += updateOp.itemCount;
                }
            }
            return i;
        }
        return -1;
    }

    @Override // android.view.View
    public final int getBaseline() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            return super.getBaseline();
        }
        layoutManager.getClass();
        return -1;
    }

    public final long getChangedHolderKey(ViewHolder viewHolder) {
        return this.mAdapter.mHasStableIds ? viewHolder.mItemId : viewHolder.mPosition;
    }

    @Override // android.view.ViewGroup
    public int getChildDrawingOrder(int i, int i2) {
        return super.getChildDrawingOrder(i, i2);
    }

    public final ViewHolder getChildViewHolder(View view) {
        ViewParent parent = view.getParent();
        if (parent == null || parent == this) {
            return getChildViewHolderInt(view);
        }
        throw new IllegalArgumentException("View " + view + " is not a direct child of " + this);
    }

    @Override // android.view.ViewGroup
    public final boolean getClipToPadding() {
        return this.mClipToPadding;
    }

    final Rect getItemDecorInsetsForChild(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (!layoutParams.mInsetsDirty) {
            return layoutParams.mDecorInsets;
        }
        if (this.mState.mInPreLayout && (layoutParams.isItemChanged() || layoutParams.mViewHolder.isInvalid())) {
            return layoutParams.mDecorInsets;
        }
        Rect rect = layoutParams.mDecorInsets;
        rect.set(0, 0, 0, 0);
        int size = this.mItemDecorations.size();
        for (int i = 0; i < size; i++) {
            this.mTempRect.set(0, 0, 0, 0);
            ((ItemDecoration) this.mItemDecorations.get(i)).getItemOffsets(this.mTempRect, view, this, this.mState);
            int i2 = rect.left;
            Rect rect2 = this.mTempRect;
            rect.left = i2 + rect2.left;
            rect.top += rect2.top;
            rect.right += rect2.right;
            rect.bottom += rect2.bottom;
        }
        layoutParams.mInsetsDirty = false;
        return rect;
    }

    public final LayoutManager getLayoutManager$1() {
        return this.mLayout;
    }

    final long getNanoTime() {
        if (ALLOW_THREAD_GAP_WORK) {
            return System.nanoTime();
        }
        return 0L;
    }

    public final int getRotatedArrowPointerIcon(boolean z, boolean z2) {
        return this.mHoverScrollArrows[(z ? z2 ? ScrollArrowDirection.RIGHT : ScrollArrowDirection.DOWN : z2 ? ScrollArrowDirection.LEFT : ScrollArrowDirection.UP).ordinal()];
    }

    public final NestedScrollingChildHelper getScrollingChildHelper() {
        if (this.mScrollingChildHelper == null) {
            this.mScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return this.mScrollingChildHelper;
    }

    @Override // android.view.View
    public final boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().getNestedScrollingParentForType(0) != null;
    }

    public void initFastScroller(StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2) {
        if (stateListDrawable == null || drawable == null || stateListDrawable2 == null || drawable2 == null) {
            throw new IllegalArgumentException("Trying to set fast scroller without both required drawables." + exceptionLabel());
        }
        Resources resources = getContext().getResources();
        new FastScroller(this, stateListDrawable, drawable, stateListDrawable2, drawable2, resources.getDimensionPixelSize(com.android.systemui.R.dimen.fastscroll_default_thickness), resources.getDimensionPixelSize(com.android.systemui.R.dimen.fastscroll_minimum_range), resources.getDimensionPixelOffset(com.android.systemui.R.dimen.fastscroll_margin));
    }

    public final void invalidateItemDecorations() {
        if (this.mItemDecorations.size() == 0) {
            return;
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout");
        }
        markItemDecorInsetsDirty();
        requestLayout();
    }

    @Override // android.view.View
    public final boolean isAttachedToWindow() {
        return this.mIsAttached;
    }

    public boolean isChildrenDrawingOrderEnabledInternal() {
        return isChildrenDrawingOrderEnabled();
    }

    public final boolean isComputingLayout() {
        return this.mLayoutOrScrollCounter > 0;
    }

    public final boolean isGoToTopAvailableEnvironment() {
        String string;
        AccessibilityManager accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        return (accessibilityManager == null || !accessibilityManager.isEnabled() || (string = Settings.Secure.getString(getContext().getContentResolver(), "enabled_accessibility_services")) == null || !(string.matches("(?i).*com.samsung.accessibility/com.samsung.android.app.talkback.TalkBackService.*") || string.matches("(?i).*com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService.*") || string.matches("(?i).*com.google.android.marvin.talkback.TalkBackService.*") || string.matches("(?i).*com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService.*"))) && getHeight() > this.mSeslOverlayFeatureHeight;
    }

    @Override // android.view.ViewGroup
    public final boolean isLayoutSuppressed() {
        return this.mLayoutSuppressed;
    }

    @Override // android.view.View
    public final boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().mIsNestedScrollingEnabled;
    }

    @Override // android.view.View
    public final boolean isVerticalScrollBarEnabled() {
        SeslRecyclerViewFastScroller seslRecyclerViewFastScroller = this.mFastScroller;
        return seslRecyclerViewFastScroller != null ? !seslRecyclerViewFastScroller.isEnabled() && super.isVerticalScrollBarEnabled() : super.isVerticalScrollBarEnabled();
    }

    final void jumpToPositionForSmoothScroller(int i) {
        if (this.mLayout == null) {
            return;
        }
        setScrollState(2);
        this.mLayout.scrollToPosition(i);
        awakenScrollBars();
    }

    final void markItemDecorInsetsDirty() {
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < unfilteredChildCount; i++) {
            ((LayoutParams) this.mChildHelper.getUnfilteredChildAt(i).getLayoutParams()).mInsetsDirty = true;
        }
        ArrayList arrayList = this.mRecycler.mCachedViews;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            LayoutParams layoutParams = (LayoutParams) ((ViewHolder) arrayList.get(i2)).itemView.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.mInsetsDirty = true;
            }
        }
    }

    public final void multiSelection(int i, int i2, int i3) {
        boolean z;
        if (this.mIsNeedPenSelection) {
            if (this.mIsFirstPenMoveEvent) {
                this.mPenDragStartX = i;
                this.mPenDragStartY = i2;
                this.mIsPenPressed = true;
                float f = i;
                float f2 = i2;
                View findChildViewUnder = findChildViewUnder(f, f2);
                this.mPenTrackedChild = findChildViewUnder;
                if (findChildViewUnder == null) {
                    View seslFindNearChildViewUnder = seslFindNearChildViewUnder(f, f2);
                    this.mPenTrackedChild = seslFindNearChildViewUnder;
                    if (seslFindNearChildViewUnder == null) {
                        Log.e("SeslRecyclerView", "multiSelection, mPenTrackedChild is NULL");
                        this.mIsPenPressed = false;
                        this.mIsFirstPenMoveEvent = false;
                        return;
                    }
                }
                this.mPenTrackedChildPosition = getChildLayoutPosition(this.mPenTrackedChild);
                this.mPenDistanceFromTrackedChildTop = this.mPenDragStartY - this.mPenTrackedChild.getTop();
                this.mIsFirstPenMoveEvent = false;
            }
            if (this.mPenDragStartX == 0 && this.mPenDragStartY == 0) {
                this.mPenDragStartX = i;
                this.mPenDragStartY = i2;
                this.mIsPenPressed = true;
            }
            this.mPenDragEndX = i;
            this.mPenDragEndY = i2;
            if (i2 < 0) {
                this.mPenDragEndY = 0;
            } else if (i2 > i3) {
                this.mPenDragEndY = i3;
            }
            int i4 = this.mPenDragStartX;
            this.mPenDragBlockLeft = i4 < i ? i4 : i;
            int i5 = this.mPenDragStartY;
            int i6 = this.mPenDragEndY;
            if (i5 >= i6) {
                i5 = i6;
            }
            this.mPenDragBlockTop = i5;
            if (i <= i4) {
                i = i4;
            }
            this.mPenDragBlockRight = i;
            z = true;
        } else {
            z = false;
        }
        if (z) {
            if (i2 <= this.mHoverTopAreaHeight + 0) {
                if (!this.mHoverAreaEnter) {
                    this.mHoverAreaEnter = true;
                    this.mHoverScrollStartTime = System.currentTimeMillis();
                }
                if (!hasMessages(0)) {
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    this.mHoverScrollDirection = 2;
                    sendEmptyMessage(0);
                }
            } else if (i2 >= (i3 - this.mHoverBottomAreaHeight) - this.mRemainNestedScrollRange) {
                if (!this.mHoverAreaEnter) {
                    this.mHoverAreaEnter = true;
                    this.mHoverScrollStartTime = System.currentTimeMillis();
                }
                if (!hasMessages(0)) {
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    this.mHoverScrollDirection = 1;
                    sendEmptyMessage(0);
                }
            } else {
                this.mHoverScrollStartTime = 0L;
                this.mHoverRecognitionStartTime = 0L;
                this.mHoverAreaEnter = false;
                if (hasMessages(0)) {
                    removeMessages(0);
                    if (this.mScrollState == 1) {
                        setScrollState(0);
                    }
                }
                this.mIsHoverOverscrolled = false;
            }
            if (this.mIsPenDragBlockEnabled) {
                invalidate();
            }
        }
    }

    public final void multiSelectionEnd() {
        this.mIsPenPressed = false;
        this.mIsFirstPenMoveEvent = true;
        this.mPenDragSelectedViewPosition = -1;
        this.mPenDragSelectedItemArray.clear();
        this.mPenDragStartX = 0;
        this.mPenDragStartY = 0;
        this.mPenDragEndX = 0;
        this.mPenDragEndY = 0;
        this.mPenDragBlockLeft = 0;
        this.mPenDragBlockTop = 0;
        this.mPenDragBlockRight = 0;
        this.mPenTrackedChild = null;
        this.mPenDistanceFromTrackedChildTop = 0;
        if (this.mIsPenDragBlockEnabled) {
            invalidate();
        }
        if (hasMessages(0)) {
            removeMessages(0);
        }
    }

    final void offsetPositionRecordsForRemove(int i, int i2, boolean z) {
        int i3 = i + i2;
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i4 = 0; i4 < unfilteredChildCount; i4++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i4));
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore()) {
                int i5 = childViewHolderInt.mPosition;
                if (i5 >= i3) {
                    childViewHolderInt.offsetPosition(-i2, z);
                    this.mState.mStructureChanged = true;
                } else if (i5 >= i) {
                    childViewHolderInt.addFlags(8);
                    childViewHolderInt.offsetPosition(-i2, z);
                    childViewHolderInt.mPosition = i - 1;
                    this.mState.mStructureChanged = true;
                }
            }
        }
        Recycler recycler = this.mRecycler;
        ArrayList arrayList = recycler.mCachedViews;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                requestLayout();
                return;
            }
            ViewHolder viewHolder = (ViewHolder) arrayList.get(size);
            if (viewHolder != null) {
                int i6 = viewHolder.mPosition;
                if (i6 >= i3) {
                    viewHolder.offsetPosition(-i2, z);
                } else if (i6 >= i) {
                    viewHolder.addFlags(8);
                    recycler.recycleCachedViewAt(size);
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        SeslRecyclerViewFastScroller seslRecyclerViewFastScroller;
        super.onAttachedToWindow();
        this.mLayoutOrScrollCounter = 0;
        this.mIsAttached = true;
        this.mFirstLayoutComplete = this.mFirstLayoutComplete && !isLayoutRequested();
        this.mRecycler.maybeSendPoolingContainerAttach();
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.mIsAttachedToWindow = true;
        }
        this.mPostedAnimatorRunner = false;
        if (ALLOW_THREAD_GAP_WORK) {
            ThreadLocal threadLocal = GapWorker.sGapWorker;
            GapWorker gapWorker = (GapWorker) threadLocal.get();
            this.mGapWorker = gapWorker;
            if (gapWorker == null) {
                this.mGapWorker = new GapWorker();
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                Display display = ViewCompat.Api17Impl.getDisplay(this);
                if (!isInEditMode() && display != null) {
                    float refreshRate = display.getRefreshRate();
                    r5 = refreshRate >= 30.0f ? refreshRate : 60.0f;
                    if (this.mIsNeedCheckLatency) {
                        this.mFrameLatency = 1000.0f / r5;
                        this.mIsNeedCheckLatency = false;
                    }
                }
                GapWorker gapWorker2 = this.mGapWorker;
                gapWorker2.mFrameIntervalNs = (long) (1.0E9f / r5);
                threadLocal.set(gapWorker2);
            }
            this.mGapWorker.mRecyclerViews.add(this);
            LayoutManager layoutManager2 = this.mLayout;
            if (layoutManager2 == null || layoutManager2.getLayoutDirection() != 1 || (seslRecyclerViewFastScroller = this.mFastScroller) == null) {
                return;
            }
            seslRecyclerViewFastScroller.setScrollbarPosition(getVerticalScrollbarPosition());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        GapWorker gapWorker;
        super.onDetachedFromWindow();
        ItemAnimator itemAnimator = this.mItemAnimator;
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        stopScroll();
        this.mIsAttached = false;
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.mIsAttachedToWindow = false;
            layoutManager.onDetachedFromWindow(this);
        }
        this.mPendingAccessibilityImportanceChange.clear();
        removeCallbacks(this.mItemAnimatorRunner);
        this.mViewInfoStore.getClass();
        while (ViewInfoStore.InfoRecord.sPool.acquire() != null) {
        }
        Recycler recycler = this.mRecycler;
        int i = 0;
        while (true) {
            ArrayList arrayList = recycler.mCachedViews;
            if (i >= arrayList.size()) {
                break;
            }
            PoolingContainer.callPoolingContainerOnRelease(((ViewHolder) arrayList.get(i)).itemView);
            i++;
        }
        recycler.poolingContainerDetach(RecyclerView.this.mAdapter, false);
        PoolingContainer.callPoolingContainerOnReleaseForChildren(this);
        if (ALLOW_THREAD_GAP_WORK && (gapWorker = this.mGapWorker) != null) {
            gapWorker.mRecyclerViews.remove(this);
            this.mGapWorker = null;
        }
        this.mIsNeedCheckLatency = true;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = this.mItemDecorations.size();
        for (int i = 0; i < size; i++) {
            ((ItemDecoration) this.mItemDecorations.get(i)).onDraw(canvas, this);
        }
        if (this.mIsNeedCheckLatency) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            Display display = ViewCompat.Api17Impl.getDisplay(this);
            if (display != null) {
                this.mFrameLatency = 1000.0f / display.getRefreshRate();
            } else {
                this.mFrameLatency = 16.66f;
            }
            this.mIsNeedCheckLatency = false;
        }
    }

    final void onEnterLayoutOrScroll() {
        this.mLayoutOrScrollCounter++;
    }

    public final void onExitLayoutOrScroll(boolean z) {
        int i;
        int i2 = this.mLayoutOrScrollCounter - 1;
        this.mLayoutOrScrollCounter = i2;
        if (i2 < 1) {
            this.mLayoutOrScrollCounter = 0;
            if (z) {
                int i3 = this.mEatenAccessibilityChangeFlags;
                this.mEatenAccessibilityChangeFlags = 0;
                if (i3 != 0) {
                    AccessibilityManager accessibilityManager = this.mAccessibilityManager;
                    if (accessibilityManager != null && accessibilityManager.isEnabled()) {
                        AccessibilityEvent obtain = AccessibilityEvent.obtain();
                        obtain.setEventType(2048);
                        obtain.setContentChangeTypes(i3);
                        sendAccessibilityEventUnchecked(obtain);
                    }
                }
                for (int size = this.mPendingAccessibilityImportanceChange.size() - 1; size >= 0; size--) {
                    ViewHolder viewHolder = this.mPendingAccessibilityImportanceChange.get(size);
                    if (viewHolder.itemView.getParent() == this && !viewHolder.shouldIgnore() && (i = viewHolder.mPendingAccessibilityState) != -1) {
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        ViewCompat.Api16Impl.setImportantForAccessibility(viewHolder.itemView, i);
                        viewHolder.mPendingAccessibilityState = -1;
                    }
                }
                this.mPendingAccessibilityImportanceChange.clear();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0086  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onGenericMotionEvent(MotionEvent motionEvent) {
        float f;
        float f2;
        if (this.mLayout != null && !this.mLayoutSuppressed && motionEvent.getAction() == 8) {
            if ((motionEvent.getSource() & 2) != 0) {
                f = this.mLayout.canScrollVertically() ? -motionEvent.getAxisValue(9) : 0.0f;
                if (this.mLayout.canScrollHorizontally()) {
                    f2 = motionEvent.getAxisValue(10);
                    if (f == 0.0f || f2 != 0.0f) {
                        startNestedScroll(f == 0.0f ? 1 : 2, 1);
                        if (!dispatchNestedPreScroll((int) (this.mScaledHorizontalScrollFactor * f2), (int) (this.mScaledVerticalScrollFactor * f), 1, null, null)) {
                            int i = (int) (f2 * this.mScaledHorizontalScrollFactor);
                            int i2 = (int) (f * this.mScaledVerticalScrollFactor);
                            LayoutManager layoutManager = this.mLayout;
                            if (layoutManager == null) {
                                Log.e("SeslRecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
                            } else if (!this.mLayoutSuppressed) {
                                int[] iArr = this.mReusableIntPair;
                                iArr[0] = 0;
                                iArr[1] = 0;
                                boolean canScrollHorizontally = layoutManager.canScrollHorizontally();
                                boolean canScrollVertically = this.mLayout.canScrollVertically();
                                int i3 = canScrollVertically ? (canScrollHorizontally ? 1 : 0) | 2 : canScrollHorizontally ? 1 : 0;
                                float y = motionEvent.getY();
                                float x = motionEvent.getX();
                                int releaseHorizontalGlow = i - releaseHorizontalGlow(y, i);
                                int releaseVerticalGlow = i2 - releaseVerticalGlow(x, i2);
                                startNestedScroll(i3, 1);
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
                }
                f2 = 0.0f;
                if (f == 0.0f) {
                }
                startNestedScroll(f == 0.0f ? 1 : 2, 1);
                if (!dispatchNestedPreScroll((int) (this.mScaledHorizontalScrollFactor * f2), (int) (this.mScaledVerticalScrollFactor * f), 1, null, null)) {
                }
            } else {
                if ((motionEvent.getSource() & QuickStepContract.SYSUI_STATE_BACK_DISABLED) != 0) {
                    float axisValue = motionEvent.getAxisValue(26);
                    if (this.mLayout.canScrollVertically()) {
                        f = -axisValue;
                        f2 = 0.0f;
                        if (f == 0.0f) {
                        }
                        startNestedScroll(f == 0.0f ? 1 : 2, 1);
                        if (!dispatchNestedPreScroll((int) (this.mScaledHorizontalScrollFactor * f2), (int) (this.mScaledVerticalScrollFactor * f), 1, null, null)) {
                        }
                    } else if (this.mLayout.canScrollHorizontally()) {
                        f2 = axisValue;
                        f = 0.0f;
                        if (f == 0.0f) {
                        }
                        startNestedScroll(f == 0.0f ? 1 : 2, 1);
                        if (!dispatchNestedPreScroll((int) (this.mScaledHorizontalScrollFactor * f2), (int) (this.mScaledVerticalScrollFactor * f), 1, null, null)) {
                        }
                    }
                }
                f = 0.0f;
                f2 = 0.0f;
                if (f == 0.0f) {
                }
                startNestedScroll(f == 0.0f ? 1 : 2, 1);
                if (!dispatchNestedPreScroll((int) (this.mScaledHorizontalScrollFactor * f2), (int) (this.mScaledVerticalScrollFactor * f), 1, null, null)) {
                }
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0036, code lost:
    
        if (r6 != 3) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00d6, code lost:
    
        if (r8 != 211) goto L164;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0088 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x02c0  */
    /* JADX WARN: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x017b  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        boolean z3;
        if (this.mLayoutSuppressed) {
            return false;
        }
        this.mInterceptingOnItemTouchListener = null;
        if (findInterceptingOnItemTouchListener(motionEvent)) {
            resetScroll();
            setScrollState(0);
            return true;
        }
        if (this.mLayout == null) {
            return false;
        }
        SeslRecyclerViewFastScroller seslRecyclerViewFastScroller = this.mFastScroller;
        if (seslRecyclerViewFastScroller != null) {
            if (seslRecyclerViewFastScroller.isEnabled()) {
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked != 0) {
                    if (actionMasked != 1) {
                        if (actionMasked == 2) {
                            if (seslRecyclerViewFastScroller.isPointInside(motionEvent.getX(), motionEvent.getY())) {
                                long j = seslRecyclerViewFastScroller.mPendingDrag;
                                if (j >= 0 && j <= SystemClock.uptimeMillis()) {
                                    seslRecyclerViewFastScroller.beginDrag();
                                    float posFromMotionEvent = seslRecyclerViewFastScroller.getPosFromMotionEvent(0.0f);
                                    seslRecyclerViewFastScroller.mOldThumbPosition = posFromMotionEvent;
                                    seslRecyclerViewFastScroller.scrollTo(posFromMotionEvent);
                                    z3 = seslRecyclerViewFastScroller.onTouchEvent(motionEvent);
                                    if (z3) {
                                        return true;
                                    }
                                }
                            } else {
                                seslRecyclerViewFastScroller.mPendingDrag = -1L;
                            }
                        }
                    }
                    seslRecyclerViewFastScroller.mPendingDrag = -1L;
                } else if (seslRecyclerViewFastScroller.isPointInside(motionEvent.getX(), motionEvent.getY())) {
                    seslRecyclerViewFastScroller.mRecyclerView.performHapticFeedback(seslRecyclerViewFastScroller.mVibrateIndex);
                    z3 = true;
                    if (z3) {
                    }
                }
            }
            z3 = false;
            if (z3) {
            }
        }
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        boolean canScrollHorizontally = this.mLayout.canScrollHorizontally();
        boolean canScrollVertically = this.mLayout.canScrollVertically();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int actionMasked2 = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        boolean z4 = motionEvent.getToolType(0) == 2;
        boolean z5 = (motionEvent.getButtonState() & 32) != 0;
        if (actionMasked2 != 0) {
            if (actionMasked2 == 1) {
                this.mVelocityTracker.clear();
                stopNestedScroll(0);
            } else if (actionMasked2 != 2) {
                if (actionMasked2 == 3) {
                    resetScroll();
                    setScrollState(0);
                } else if (actionMasked2 == 5) {
                    this.mScrollPointerId = motionEvent.getPointerId(actionIndex);
                    this.mLastTouchX = (int) (motionEvent.getX(actionIndex) + 0.5f);
                    this.mLastTouchY = (int) (motionEvent.getY(actionIndex) + 0.5f);
                } else if (actionMasked2 == 6) {
                    onPointerUp(motionEvent);
                }
            } else if (z4 && z5) {
                this.mLastTouchX = (int) (motionEvent.getX(actionIndex) + 0.5f);
                this.mLastTouchY = (int) (motionEvent.getY(actionIndex) + 0.5f);
            } else {
                int findPointerIndex = motionEvent.findPointerIndex(this.mScrollPointerId);
                if (findPointerIndex < 0) {
                    Log.e("SeslRecyclerView", "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                    obtain.recycle();
                    return false;
                }
                int x = (int) (motionEvent.getX(findPointerIndex) + 0.5f);
                int y = (int) (motionEvent.getY(findPointerIndex) + 0.5f);
                int i = this.mLastTouchX - x;
                int i2 = this.mLastTouchY - y;
                if (this.mScrollState != 1) {
                    if (canScrollHorizontally != 0) {
                        int abs = Math.abs(i);
                        int i3 = this.mTouchSlop;
                        if (abs > i3) {
                            i = i > 0 ? i - i3 : i + i3;
                            this.mLastTouchX = x;
                            z2 = true;
                            if (canScrollVertically) {
                                int abs2 = Math.abs(i2);
                                int i4 = this.mTouchSlop;
                                if (abs2 > i4) {
                                    i2 = i2 > 0 ? i2 - i4 : i2 + i4;
                                    this.mPreventFirstGlow = true;
                                    this.mLastTouchY = y;
                                    z2 = true;
                                }
                            }
                            if (z2) {
                                setScrollState(1);
                            }
                        }
                    }
                    z2 = false;
                    if (canScrollVertically) {
                    }
                    if (z2) {
                    }
                }
                if (this.mScrollState == 1) {
                    int[] iArr = this.mScrollOffset;
                    this.mLastTouchX = x - iArr[0];
                    this.mLastTouchY = y - iArr[1];
                    if (scrollByInternal(canScrollHorizontally != 0 ? i : 0, canScrollVertically ? i2 : 0, obtain, 0)) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    GapWorker gapWorker = this.mGapWorker;
                    if (gapWorker != null && (i != 0 || i2 != 0)) {
                        gapWorker.postFromTraversal(this, i, i2);
                    }
                }
                adjustNestedScrollRangeBy(i2);
            }
            obtain.recycle();
            return this.mScrollState != 1;
        }
        if ((!z4 || !z5) && actionMasked2 != 211) {
            if (this.mIgnoreMotionEventTillDown) {
                this.mIgnoreMotionEventTillDown = false;
            }
            this.mScrollPointerId = motionEvent.getPointerId(0);
            this.mLastTouchX = (int) (motionEvent.getX() + 0.5f);
            this.mLastTouchY = (int) (motionEvent.getY() + 0.5f);
            if (this.mUsePagingTouchSlopForStylus) {
                if (motionEvent.isFromSource(16386)) {
                    this.mTouchSlop = this.mPagingTouchSlop;
                } else {
                    this.mTouchSlop = this.mTouchSlop2;
                }
            }
            EdgeEffect edgeEffect = this.mLeftGlow;
            if (edgeEffect == null || EdgeEffectCompat.getDistance(edgeEffect) == 0.0f || canScrollHorizontally(-1)) {
                z = false;
            } else {
                EdgeEffectCompat.onPullDistance(this.mLeftGlow, 0.0f, 1.0f - (motionEvent.getY() / getHeight()));
                z = true;
            }
            EdgeEffect edgeEffect2 = this.mRightGlow;
            boolean z6 = z;
            if (edgeEffect2 != null) {
                z6 = z;
                if (EdgeEffectCompat.getDistance(edgeEffect2) != 0.0f) {
                    z6 = z;
                    if (!canScrollHorizontally(1)) {
                        EdgeEffectCompat.onPullDistance(this.mRightGlow, 0.0f, motionEvent.getY() / getHeight());
                        z6 = true;
                    }
                }
            }
            EdgeEffect edgeEffect3 = this.mTopGlow;
            boolean z7 = z6;
            if (edgeEffect3 != null) {
                z7 = z6;
                if (EdgeEffectCompat.getDistance(edgeEffect3) != 0.0f) {
                    z7 = z6;
                    if (!canScrollVertically(-1)) {
                        EdgeEffectCompat.onPullDistance(this.mTopGlow, 0.0f, motionEvent.getX() / getWidth());
                        z7 = true;
                    }
                }
            }
            EdgeEffect edgeEffect4 = this.mBottomGlow;
            boolean z8 = z7;
            if (edgeEffect4 != null) {
                z8 = z7;
                if (EdgeEffectCompat.getDistance(edgeEffect4) != 0.0f) {
                    z8 = z7;
                    if (!canScrollVertically(1)) {
                        EdgeEffectCompat.onPullDistance(this.mBottomGlow, 0.0f, 1.0f - (motionEvent.getX() / getWidth()));
                        z8 = true;
                    }
                }
            }
            if (z8 || this.mScrollState == 2) {
                getParent().requestDisallowInterceptTouchEvent(true);
                setScrollState(1);
                stopNestedScroll(1);
            }
            int[] iArr2 = this.mNestedOffsets;
            iArr2[1] = 0;
            iArr2[0] = 0;
            if (this.mHasNestedScrollRange) {
                adjustNestedScrollRange$1();
            }
            this.mPreventFirstGlow = false;
            int i5 = canScrollHorizontally;
            if (canScrollVertically) {
                i5 = (canScrollHorizontally ? 1 : 0) | 2;
            }
            startNestedScroll(i5, 0);
            this.mIsSkipMoveEvent = false;
        } else if (this.mIgnoreMotionEventTillDown) {
            this.mIgnoreMotionEventTillDown = false;
        }
        obtain.recycle();
        if (this.mScrollState != 1) {
        }
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 92) {
            if (i != 93) {
                if (i == 113 || i == 114) {
                    this.mIsCtrlKeyPressed = true;
                } else if (i != 122) {
                    if (i == 123 && keyEvent.hasNoModifiers()) {
                        pageScroll(3);
                    }
                } else if (keyEvent.hasNoModifiers()) {
                    pageScroll(2);
                }
            } else if (keyEvent.hasNoModifiers()) {
                pageScroll(1);
            }
        } else if (keyEvent.hasNoModifiers()) {
            pageScroll(0);
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 113 || i == 114) {
            this.mIsCtrlKeyPressed = false;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        LayoutManager layoutManager;
        LayoutManager layoutManager2;
        boolean z2;
        Trace.beginSection("RV OnLayout");
        dispatchLayout();
        Trace.endSection();
        this.mFirstLayoutComplete = true;
        SeslRecyclerViewFastScroller seslRecyclerViewFastScroller = this.mFastScroller;
        if (seslRecyclerViewFastScroller != null && this.mAdapter != null) {
            int childCount = getChildCount();
            int itemCount = this.mAdapter.getItemCount();
            int i5 = seslRecyclerViewFastScroller.mOldChildCount;
            RecyclerView recyclerView = seslRecyclerViewFastScroller.mRecyclerView;
            if (i5 == 0) {
                seslRecyclerViewFastScroller.mOldChildCount = recyclerView.getChildCount();
            }
            if (seslRecyclerViewFastScroller.mOldItemCount != itemCount || seslRecyclerViewFastScroller.mOldChildCount != childCount) {
                seslRecyclerViewFastScroller.mOldItemCount = itemCount;
                seslRecyclerViewFastScroller.mOldChildCount = childCount;
                if ((itemCount - childCount > 0) && seslRecyclerViewFastScroller.mState != 2) {
                    seslRecyclerViewFastScroller.setThumbPos(seslRecyclerViewFastScroller.getPosFromItemCount(recyclerView.findFirstVisibleItemPosition(), childCount, itemCount));
                }
                seslRecyclerViewFastScroller.updateLongList(childCount);
            }
        }
        if (z) {
            this.mSizeChnage = true;
            this.mSeslOverlayFeatureHeight = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_recyclerview_overlay_feature_hidden_height);
            if (this.mEnableGoToTop) {
                int height = ((getHeight() - this.mGoToTopSize) - this.mGoToTopBottomPadding) - 0;
                if (height < 0) {
                    this.mGoToTopImmersiveBottomPadding = 0;
                    Log.e("SeslRecyclerView", "The Immersive padding value (0) was too large to draw GoToTop.");
                    setupGoToTop(-1);
                    autoHide(1);
                    layoutManager = this.mLayout;
                    if (layoutManager != null || layoutManager.canScrollHorizontally()) {
                        layoutManager2 = this.mLayout;
                        if (layoutManager2 == null && layoutManager2.canScrollHorizontally()) {
                            getLocationInWindow(this.mWindowOffsets);
                            this.mRemainNestedScrollRange = 0;
                            this.mNestedScrollRange = 0;
                            this.mInitialTopOffsetOfScreen = this.mWindowOffsets[0];
                            return;
                        }
                    }
                    this.mHasNestedScrollRange = false;
                    ViewParent parent = getParent();
                    while (true) {
                        if (parent == null || !(parent instanceof ViewGroup)) {
                            break;
                        }
                        if (parent instanceof NestedScrollingParent2) {
                            Class<?> cls = parent.getClass();
                            while (true) {
                                if (cls == null) {
                                    z2 = false;
                                    break;
                                } else {
                                    if (cls.getSimpleName().equals("CoordinatorLayout")) {
                                        z2 = true;
                                        break;
                                    }
                                    cls = cls.getSuperclass();
                                }
                            }
                            if (z2) {
                                ViewGroup viewGroup = (ViewGroup) parent;
                                viewGroup.getLocationInWindow(this.mWindowOffsets);
                                int height2 = viewGroup.getHeight() + this.mWindowOffsets[1];
                                getLocationInWindow(this.mWindowOffsets);
                                this.mInitialTopOffsetOfScreen = this.mWindowOffsets[1];
                                int height3 = getHeight() - (height2 - this.mInitialTopOffsetOfScreen);
                                this.mRemainNestedScrollRange = height3;
                                if (height3 < 0) {
                                    this.mRemainNestedScrollRange = 0;
                                }
                                this.mNestedScrollRange = this.mRemainNestedScrollRange;
                                this.mHasNestedScrollRange = true;
                            }
                        }
                        parent = parent.getParent();
                    }
                    if (this.mHasNestedScrollRange) {
                        return;
                    }
                    this.mInitialTopOffsetOfScreen = 0;
                    this.mRemainNestedScrollRange = 0;
                    this.mNestedScrollRange = 0;
                    return;
                }
                this.mGoToTopImmersiveBottomPadding = 0;
                if (this.mGoToTopState != 0) {
                    int width = (((getWidth() - getPaddingLeft()) - getPaddingRight()) / 2) + getPaddingLeft();
                    Rect rect = this.mGoToTopRect;
                    int i6 = this.mGoToTopSize;
                    int i7 = i6 / 2;
                    rect.set(width - i7, height, i7 + width, i6 + height);
                    ImageView imageView = this.mGoToTopView;
                    Rect rect2 = this.mGoToTopRect;
                    imageView.layout(rect2.left, rect2.top, rect2.right, rect2.bottom);
                }
            }
            SeslRecyclerViewFastScroller seslRecyclerViewFastScroller2 = this.mFastScroller;
            if (seslRecyclerViewFastScroller2 != null && this.mAdapter != null) {
                seslRecyclerViewFastScroller2.mImmersiveBottomPadding = 0;
                seslRecyclerViewFastScroller2.updateOffsetAndRange();
            }
            setupGoToTop(-1);
            autoHide(1);
            layoutManager = this.mLayout;
            if (layoutManager != null) {
            }
            layoutManager2 = this.mLayout;
            if (layoutManager2 == null) {
            }
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        if (this.mLayout == null) {
            defaultOnMeasure(i, i2);
            return;
        }
        this.mListPadding.set(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        boolean z = false;
        if (this.mLayout.isAutoMeasureEnabled()) {
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            this.mLayout.onMeasure(this.mRecycler, this.mState, i, i2);
            if (mode == 1073741824 && mode2 == 1073741824) {
                z = true;
            }
            this.mLastAutoMeasureSkippedDueToExact = z;
            if (z || this.mAdapter == null) {
                return;
            }
            if (this.mState.mLayoutStep == 1) {
                dispatchLayoutStep1();
            }
            this.mLayout.setMeasureSpecs(i, i2);
            this.mState.mIsMeasuring = true;
            dispatchLayoutStep2();
            this.mLayout.setMeasuredDimensionFromChildren(i, i2);
            if (this.mLayout.shouldMeasureTwice()) {
                this.mLayout.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                this.mState.mIsMeasuring = true;
                dispatchLayoutStep2();
                this.mLayout.setMeasuredDimensionFromChildren(i, i2);
            }
            this.mLastAutoMeasureNonExactMeasuredWidth = getMeasuredWidth();
            this.mLastAutoMeasureNonExactMeasuredHeight = getMeasuredHeight();
            return;
        }
        if (this.mHasFixedSize) {
            this.mLayout.onMeasure(this.mRecycler, this.mState, i, i2);
            return;
        }
        if (this.mAdapterUpdateDuringMeasure) {
            startInterceptRequestLayout();
            onEnterLayoutOrScroll();
            processAdapterUpdatesAndSetAnimationFlags();
            onExitLayoutOrScroll(true);
            State state = this.mState;
            if (state.mRunPredictiveAnimations) {
                state.mInPreLayout = true;
            } else {
                this.mAdapterHelper.consumeUpdatesInOnePass();
                this.mState.mInPreLayout = false;
            }
            this.mAdapterUpdateDuringMeasure = false;
            stopInterceptRequestLayout(false);
        } else if (this.mState.mRunPredictiveAnimations) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
            return;
        }
        Adapter adapter = this.mAdapter;
        if (adapter != null) {
            this.mState.mItemCount = adapter.getItemCount();
        } else {
            this.mState.mItemCount = 0;
        }
        startInterceptRequestLayout();
        this.mLayout.onMeasure(this.mRecycler, this.mState, i, i2);
        stopInterceptRequestLayout(false);
        this.mState.mInPreLayout = false;
    }

    public final void onPointerUp(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mScrollPointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            this.mScrollPointerId = motionEvent.getPointerId(i);
            this.mLastTouchX = (int) (motionEvent.getX(i) + 0.5f);
            this.mLastTouchY = (int) (motionEvent.getY(i) + 0.5f);
        }
    }

    @Override // android.view.ViewGroup
    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        if (isComputingLayout()) {
            return false;
        }
        return super.onRequestFocusInDescendants(i, rect);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        this.mPendingSavedState = savedState;
        super.onRestoreInstanceState(savedState.mSuperState);
        requestLayout();
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        this.mIsNeedCheckLatency = true;
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
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i == i3 && i2 == i4) {
            return;
        }
        SeslRecyclerViewFastScroller seslRecyclerViewFastScroller = this.mFastScroller;
        if (seslRecyclerViewFastScroller != null) {
            boolean z = true;
            if (!seslRecyclerViewFastScroller.canScrollList(1) && !seslRecyclerViewFastScroller.canScrollList(-1)) {
                z = false;
            }
            seslRecyclerViewFastScroller.mLongList = z;
            seslRecyclerViewFastScroller.updateLayout();
        }
        this.mBottomGlow = null;
        this.mTopGlow = null;
        this.mRightGlow = null;
        this.mLeftGlow = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:120:0x033d, code lost:
    
        if (r0 != false) goto L205;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x02f5, code lost:
    
        if (r3 == 0) goto L202;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:143:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x02d2 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x02f1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0300  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x013e  */
    /* JADX WARN: Type inference failed for: r0v27 */
    /* JADX WARN: Type inference failed for: r0v36 */
    /* JADX WARN: Type inference failed for: r0v37 */
    /* JADX WARN: Type inference failed for: r10v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r10v1, types: [int] */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v21 */
    /* JADX WARN: Type inference failed for: r17v0, types: [android.view.ViewGroup, androidx.recyclerview.widget.RecyclerView] */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v28 */
    /* JADX WARN: Type inference failed for: r3v27 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r4v4, types: [boolean] */
    /* JADX WARN: Type inference failed for: r4v5, types: [int] */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v8 */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        int i;
        int i2;
        float f;
        float f2;
        ?? r0;
        ?? r2;
        boolean z2 = false;
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
            resetScroll();
            setScrollState(0);
            return true;
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            return false;
        }
        ?? canScrollHorizontally = layoutManager.canScrollHorizontally();
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
        ?? r3 = motionEvent.getToolType(0) == 2;
        ?? r5 = (motionEvent.getButtonState() & 32) != 0;
        SeslRecyclerViewFastScroller seslRecyclerViewFastScroller = this.mFastScroller;
        if (seslRecyclerViewFastScroller != null && seslRecyclerViewFastScroller.onTouchEvent(motionEvent)) {
            obtain.recycle();
            return true;
        }
        if (actionMasked == 0) {
            this.mScrollPointerId = motionEvent.getPointerId(0);
            this.mLastTouchX = (int) (motionEvent.getX() + 0.5f);
            this.mLastTouchY = (int) (motionEvent.getY() + 0.5f);
            if (this.mHasNestedScrollRange) {
                adjustNestedScrollRange$1();
            }
            if (canScrollVertically) {
                canScrollHorizontally = (canScrollHorizontally == true ? 1 : 0) | 2;
            }
            startNestedScroll(canScrollHorizontally, 0);
        } else if (actionMasked == 1) {
            this.mVelocityTracker.addMovement(obtain);
            this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxFlingVelocity);
            float f3 = canScrollHorizontally != 0 ? -this.mVelocityTracker.getXVelocity(this.mScrollPointerId) : 0.0f;
            float f4 = canScrollVertically ? -this.mVelocityTracker.getYVelocity(this.mScrollPointerId) : 0.0f;
            if (f3 != 0.0f || f4 != 0.0f) {
                int i3 = (int) f3;
                int i4 = (int) f4;
                LayoutManager layoutManager2 = this.mLayout;
                if (layoutManager2 == null) {
                    Log.e("SeslRecyclerView", "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
                } else if (!this.mLayoutSuppressed) {
                    ?? canScrollHorizontally2 = layoutManager2.canScrollHorizontally();
                    boolean canScrollVertically2 = this.mLayout.canScrollVertically();
                    if (canScrollHorizontally2 == 0 || Math.abs(i3) < this.mMinFlingVelocity) {
                        i3 = 0;
                    }
                    if (!canScrollVertically2 || Math.abs(i4) < this.mMinFlingVelocity) {
                        i4 = 0;
                    }
                    if (i3 != 0 || i4 != 0) {
                        if (i3 != 0) {
                            EdgeEffect edgeEffect = this.mLeftGlow;
                            if (edgeEffect == null || EdgeEffectCompat.getDistance(edgeEffect) == 0.0f) {
                                EdgeEffect edgeEffect2 = this.mRightGlow;
                                if (edgeEffect2 != null && EdgeEffectCompat.getDistance(edgeEffect2) != 0.0f) {
                                    if (shouldAbsorb(this.mRightGlow, i3, getWidth())) {
                                        this.mRightGlow.onAbsorb(i3);
                                        i3 = 0;
                                    }
                                    i = 0;
                                }
                            } else {
                                int i5 = -i3;
                                if (shouldAbsorb(this.mLeftGlow, i5, getWidth())) {
                                    this.mLeftGlow.onAbsorb(i5);
                                    i3 = 0;
                                }
                                i = 0;
                            }
                            if (i4 != 0) {
                                EdgeEffect edgeEffect3 = this.mTopGlow;
                                if (edgeEffect3 == null || EdgeEffectCompat.getDistance(edgeEffect3) == 0.0f) {
                                    EdgeEffect edgeEffect4 = this.mBottomGlow;
                                    if (edgeEffect4 != null && EdgeEffectCompat.getDistance(edgeEffect4) != 0.0f) {
                                        if (shouldAbsorb(this.mBottomGlow, i4, getHeight())) {
                                            this.mBottomGlow.onAbsorb(i4);
                                            i4 = 0;
                                        }
                                        i2 = 0;
                                    }
                                } else {
                                    int i6 = -i4;
                                    if (shouldAbsorb(this.mTopGlow, i6, getHeight())) {
                                        this.mTopGlow.onAbsorb(i6);
                                        i4 = 0;
                                    }
                                    i2 = 0;
                                }
                                if (i3 == 0 || i4 != 0) {
                                    int i7 = this.mMaxFlingVelocity;
                                    i3 = Math.max(-i7, Math.min(i3, i7));
                                    int i8 = this.mMaxFlingVelocity;
                                    i4 = Math.max(-i8, Math.min(i4, i8));
                                    this.mViewFlinger.fling(i3, i4);
                                }
                                if (i == 0 || i2 != 0) {
                                    f = i;
                                    f2 = i2;
                                    if (!dispatchNestedPreFling(f, f2)) {
                                        boolean z3 = canScrollHorizontally2 != 0 || canScrollVertically2;
                                        dispatchNestedFling(f, f2, z3);
                                        OnFlingListener onFlingListener = this.mOnFlingListener;
                                        if (onFlingListener == null || !onFlingListener.onFling(i, i2)) {
                                            if (z3) {
                                                if (canScrollVertically2) {
                                                    canScrollHorizontally2 = (canScrollHorizontally2 == true ? 1 : 0) | 2;
                                                }
                                                startNestedScroll(canScrollHorizontally2, 1);
                                                int i9 = this.mMaxFlingVelocity;
                                                int max = Math.max(-i9, Math.min(i, i9));
                                                int i10 = this.mMaxFlingVelocity;
                                                this.mViewFlinger.fling(max, Math.max(-i10, Math.min(i2, i10)));
                                            }
                                        }
                                        r0 = true;
                                    }
                                } else {
                                    if (i3 == 0) {
                                    }
                                    r0 = true;
                                }
                            }
                            i2 = i4;
                            i4 = 0;
                            if (i3 == 0) {
                            }
                            int i72 = this.mMaxFlingVelocity;
                            i3 = Math.max(-i72, Math.min(i3, i72));
                            int i82 = this.mMaxFlingVelocity;
                            i4 = Math.max(-i82, Math.min(i4, i82));
                            this.mViewFlinger.fling(i3, i4);
                            if (i == 0) {
                            }
                            f = i;
                            f2 = i2;
                            if (!dispatchNestedPreFling(f, f2)) {
                            }
                        }
                        i = i3;
                        i3 = 0;
                        if (i4 != 0) {
                        }
                        i2 = i4;
                        i4 = 0;
                        if (i3 == 0) {
                        }
                        int i722 = this.mMaxFlingVelocity;
                        i3 = Math.max(-i722, Math.min(i3, i722));
                        int i822 = this.mMaxFlingVelocity;
                        i4 = Math.max(-i822, Math.min(i4, i822));
                        this.mViewFlinger.fling(i3, i4);
                        if (i == 0) {
                        }
                        f = i;
                        f2 = i2;
                        if (!dispatchNestedPreFling(f, f2)) {
                        }
                    }
                }
                r0 = false;
            }
            setScrollState(0);
            Log.i("SeslRecyclerView", "onTouchUp() velocity : " + f4 + ", last move skip : " + this.mIsSkipMoveEvent + "(" + this.mFrameLatency + "), use scroller : " + this.mViewFlinger.mOverScroller.getClass().getName());
            resetScroll();
            z2 = true;
        } else if (actionMasked != 2) {
            if (actionMasked == 3) {
                resetScroll();
                setScrollState(0);
            } else if (actionMasked == 5) {
                this.mScrollPointerId = motionEvent.getPointerId(actionIndex);
                this.mLastTouchX = (int) (motionEvent.getX(actionIndex) + 0.5f);
                this.mLastTouchY = (int) (motionEvent.getY(actionIndex) + 0.5f);
            } else if (actionMasked == 6) {
                onPointerUp(motionEvent);
            }
        } else if (r3 == false || r5 == false) {
            int findPointerIndex = motionEvent.findPointerIndex(this.mScrollPointerId);
            if (findPointerIndex < 0) {
                Log.e("SeslRecyclerView", "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                obtain.recycle();
                return false;
            }
            int x = (int) (motionEvent.getX(findPointerIndex) + 0.5f);
            int y = (int) (motionEvent.getY(findPointerIndex) + 0.5f);
            int i11 = this.mLastTouchX - x;
            int i12 = this.mLastTouchY - y;
            if (this.mScrollState != 1) {
                if (canScrollHorizontally != 0) {
                    i11 = i11 > 0 ? Math.max(0, i11 - this.mTouchSlop) : Math.min(0, i11 + this.mTouchSlop);
                    if (i11 != 0) {
                        r2 = true;
                        if (canScrollVertically) {
                            i12 = i12 > 0 ? Math.max(0, i12 - this.mTouchSlop) : Math.min(0, i12 + this.mTouchSlop);
                            if (i12 != 0) {
                                r2 = true;
                            }
                        }
                        if (r2 != false) {
                            setScrollState(1);
                        }
                    }
                }
                r2 = false;
                if (canScrollVertically) {
                }
                if (r2 != false) {
                }
            }
            if (this.mScrollState == 1) {
                int[] iArr3 = this.mReusableIntPair;
                iArr3[0] = 0;
                iArr3[1] = 0;
                int releaseHorizontalGlow = i11 - releaseHorizontalGlow(motionEvent.getY(), i11);
                int releaseVerticalGlow = i12 - releaseVerticalGlow(motionEvent.getX(), i12);
                if (dispatchNestedPreScroll(canScrollHorizontally != 0 ? releaseHorizontalGlow : 0, canScrollVertically ? releaseVerticalGlow : 0, 0, this.mReusableIntPair, this.mScrollOffset)) {
                    int[] iArr4 = this.mReusableIntPair;
                    releaseHorizontalGlow -= iArr4[0];
                    releaseVerticalGlow -= iArr4[1];
                    int[] iArr5 = this.mNestedOffsets;
                    int i13 = iArr5[0];
                    int[] iArr6 = this.mScrollOffset;
                    iArr5[0] = i13 + iArr6[0];
                    iArr5[1] = iArr5[1] + iArr6[1];
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                int i14 = releaseVerticalGlow;
                int[] iArr7 = this.mScrollOffset;
                this.mLastTouchX = x - iArr7[0];
                this.mLastTouchY = y - iArr7[1];
                if ((motionEvent.getFlags() & QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING) != 0) {
                    this.mVelocityTracker.addMovement(obtain);
                    this.mIsSkipMoveEvent = true;
                    obtain.recycle();
                    return false;
                }
                if (scrollByInternal(canScrollHorizontally != 0 ? releaseHorizontalGlow : 0, canScrollVertically ? i14 : 0, motionEvent, 0)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                GapWorker gapWorker = this.mGapWorker;
                if (gapWorker != null && (releaseHorizontalGlow != 0 || i14 != 0)) {
                    gapWorker.postFromTraversal(this, releaseHorizontalGlow, i14);
                }
            }
        }
        if (!z2) {
            this.mVelocityTracker.addMovement(obtain);
        }
        obtain.recycle();
        return true;
    }

    final void postAnimationRunner() {
        if (this.mPostedAnimatorRunner || !this.mIsAttached) {
            return;
        }
        RunnableC04707 runnableC04707 = this.mItemAnimatorRunner;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.postOnAnimation(this, runnableC04707);
        this.mPostedAnimatorRunner = true;
    }

    public final void processAdapterUpdatesAndSetAnimationFlags() {
        boolean z;
        boolean z2 = false;
        if (this.mDataSetHasChangedAfterLayout) {
            AdapterHelper adapterHelper = this.mAdapterHelper;
            adapterHelper.recycleUpdateOpsAndClearList(adapterHelper.mPendingUpdates);
            adapterHelper.recycleUpdateOpsAndClearList(adapterHelper.mPostponedList);
            adapterHelper.mExistingUpdateTypes = 0;
            if (this.mDispatchItemsChangedEvent) {
                this.mLayout.onItemsChanged();
            }
        }
        if (this.mItemAnimator != null && this.mLayout.supportsPredictiveItemAnimations()) {
            this.mAdapterHelper.preProcess();
        } else {
            this.mAdapterHelper.consumeUpdatesInOnePass();
        }
        boolean z3 = this.mItemsAddedOrRemoved || this.mItemsChanged;
        State state = this.mState;
        boolean z4 = this.mFirstLayoutComplete && this.mItemAnimator != null && ((z = this.mDataSetHasChangedAfterLayout) || z3 || this.mLayout.mRequestedSimpleAnimations) && (!z || this.mAdapter.mHasStableIds);
        state.mRunSimpleAnimations = z4;
        if (z4 && z3 && !this.mDataSetHasChangedAfterLayout) {
            if (this.mItemAnimator != null && this.mLayout.supportsPredictiveItemAnimations()) {
                z2 = true;
            }
        }
        state.mRunPredictiveAnimations = z2;
    }

    public final void processDataSetCompletelyChanged(boolean z) {
        this.mDispatchItemsChangedEvent = z | this.mDispatchItemsChangedEvent;
        this.mDataSetHasChangedAfterLayout = true;
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < unfilteredChildCount; i++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore()) {
                childViewHolderInt.addFlags(6);
            }
        }
        markItemDecorInsetsDirty();
        Recycler recycler = this.mRecycler;
        ArrayList arrayList = recycler.mCachedViews;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ViewHolder viewHolder = (ViewHolder) arrayList.get(i2);
            if (viewHolder != null) {
                viewHolder.addFlags(6);
                viewHolder.addChangePayload(null);
            }
        }
        Adapter adapter = RecyclerView.this.mAdapter;
        if (adapter == null || !adapter.mHasStableIds) {
            recycler.recycleAndClearCachedViews();
        }
    }

    public final void recordAnimationInfoIfBouncedHiddenView(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo) {
        int i = (viewHolder.mFlags & (-8193)) | 0;
        viewHolder.mFlags = i;
        if (this.mState.mTrackOldChangeHolders) {
            if (((i & 2) != 0) && !viewHolder.isRemoved() && !viewHolder.shouldIgnore()) {
                this.mViewInfoStore.mOldChangedHolders.put(getChangedHolderKey(viewHolder), viewHolder);
            }
        }
        SimpleArrayMap simpleArrayMap = this.mViewInfoStore.mLayoutHolderMap;
        ViewInfoStore.InfoRecord infoRecord = (ViewInfoStore.InfoRecord) simpleArrayMap.get(viewHolder);
        if (infoRecord == null) {
            infoRecord = ViewInfoStore.InfoRecord.obtain();
            simpleArrayMap.put(viewHolder, infoRecord);
        }
        infoRecord.preInfo = itemHolderInfo;
        infoRecord.flags |= 4;
    }

    public final int releaseHorizontalGlow(float f, int i) {
        float height = f / getHeight();
        float width = i / getWidth();
        EdgeEffect edgeEffect = this.mLeftGlow;
        float f2 = 0.0f;
        if (edgeEffect == null || EdgeEffectCompat.getDistance(edgeEffect) == 0.0f) {
            EdgeEffect edgeEffect2 = this.mRightGlow;
            if (edgeEffect2 != null && EdgeEffectCompat.getDistance(edgeEffect2) != 0.0f) {
                if (canScrollHorizontally(1)) {
                    this.mRightGlow.onRelease();
                } else {
                    float onPullDistance = EdgeEffectCompat.onPullDistance(this.mRightGlow, width, height);
                    if (EdgeEffectCompat.getDistance(this.mRightGlow) == 0.0f) {
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
                if (EdgeEffectCompat.getDistance(this.mLeftGlow) == 0.0f) {
                    this.mLeftGlow.onRelease();
                }
                f2 = f3;
            }
            invalidate();
        }
        return Math.round(f2 * getWidth());
    }

    final void removeAndRecycleViews() {
        ItemAnimator itemAnimator = this.mItemAnimator;
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.removeAndRecycleAllViews(this.mRecycler);
        }
        LayoutManager layoutManager2 = this.mLayout;
        if (layoutManager2 != null) {
            layoutManager2.removeAndRecycleScrapInt(this.mRecycler);
        }
        Recycler recycler = this.mRecycler;
        recycler.mAttachedScrap.clear();
        recycler.recycleAndClearCachedViews();
    }

    @Override // android.view.ViewGroup
    public final void removeDetachedView(View view, boolean z) {
        ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            if (childViewHolderInt.isTmpDetached()) {
                childViewHolderInt.mFlags &= -257;
            } else if (!childViewHolderInt.shouldIgnore()) {
                throw new IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached." + childViewHolderInt + exceptionLabel());
            }
        }
        view.clearAnimation();
        dispatchChildDetached(view);
        super.removeDetachedView(view, z);
    }

    public final void removeItemDecoration(ItemDecoration itemDecoration) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
        }
        this.mItemDecorations.remove(itemDecoration);
        if (this.mItemDecorations.isEmpty()) {
            setWillNotDraw(getOverScrollMode() == 2);
        }
        markItemDecorInsetsDirty();
        requestLayout();
    }

    public final void removeOnScrollListener(OnScrollListener onScrollListener) {
        List list = this.mScrollListeners;
        if (list != null) {
            ((ArrayList) list).remove(onScrollListener);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestChildFocus(View view, View view2) {
        if (!this.mLayout.onRequestChildFocus(this, view, view2) && view2 != null) {
            requestChildOnScreen(view, view2);
        }
        super.requestChildFocus(view, view2);
    }

    public final void requestChildOnScreen(View view, View view2) {
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

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        return this.mLayout.requestChildRectangleOnScreen(this, view, rect, z);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z) {
        int size = this.mOnItemTouchListeners.size();
        for (int i = 0; i < size; i++) {
            ((OnItemTouchListener) this.mOnItemTouchListeners.get(i)).onRequestDisallowInterceptTouchEvent(z);
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

    public final void resetScroll() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.clear();
        }
        boolean z = false;
        stopNestedScroll(0);
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            z = this.mLeftGlow.isFinished();
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
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.View
    public final void scrollBy(int i, int i2) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e("SeslRecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
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

    /* JADX WARN: Removed duplicated region for block: B:27:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00f8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean scrollByInternal(int i, int i2, MotionEvent motionEvent, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        boolean z;
        boolean z2;
        consumePendingUpdateOperations();
        if (this.mAdapter != null) {
            int[] iArr = this.mReusableIntPair;
            iArr[0] = 0;
            iArr[1] = 0;
            scrollStep(i, i2, iArr);
            int[] iArr2 = this.mReusableIntPair;
            i5 = iArr2[0];
            i4 = iArr2[1];
            i6 = i - i5;
            i7 = i2 - i4;
        } else {
            i4 = 0;
            i5 = 0;
            i6 = 0;
            i7 = 0;
        }
        if (!this.mItemDecorations.isEmpty()) {
            invalidate();
        }
        int[] iArr3 = this.mReusableIntPair;
        iArr3[0] = 0;
        iArr3[1] = 0;
        if (!getScrollingChildHelper().dispatchNestedScrollInternal(i5, i4, i6, i7, this.mScrollOffset, i3, iArr3)) {
            int[] iArr4 = this.mScrollOffset;
            iArr4[0] = 0;
            iArr4[1] = 0;
        }
        int[] iArr5 = this.mReusableIntPair;
        int i8 = iArr5[0];
        int i9 = i6 - i8;
        int i10 = iArr5[1];
        int i11 = i7 - i10;
        boolean z3 = (i8 == 0 && i10 == 0) ? false : true;
        int i12 = this.mLastTouchX;
        int[] iArr6 = this.mScrollOffset;
        int i13 = iArr6[0];
        this.mLastTouchX = i12 - i13;
        int i14 = this.mLastTouchY;
        int i15 = iArr6[1];
        this.mLastTouchY = i14 - i15;
        int[] iArr7 = this.mNestedOffsets;
        iArr7[0] = iArr7[0] + i13;
        iArr7[1] = iArr7[1] + i15;
        if (!this.mPreventFirstGlow && getOverScrollMode() != 2) {
            if (motionEvent != null && !MotionEventCompat.isFromSource(motionEvent, 8194)) {
                float x = motionEvent.getX();
                float f = i9;
                float y = motionEvent.getY();
                float f2 = i11;
                if (f < 0.0f) {
                    ensureLeftGlow();
                    EdgeEffectCompat.onPullDistance(this.mLeftGlow, (-f) / getWidth(), 1.0f - (y / getHeight()));
                } else if (f > 0.0f) {
                    ensureRightGlow();
                    EdgeEffectCompat.onPullDistance(this.mRightGlow, f / getWidth(), y / getHeight());
                } else {
                    z2 = false;
                    if (f2 >= 0.0f) {
                        ensureTopGlow();
                        EdgeEffectCompat.onPullDistance(this.mTopGlow, (-f2) / getHeight(), x / getWidth());
                    } else {
                        if (f2 > 0.0f) {
                            ensureBottomGlow();
                            EdgeEffectCompat.onPullDistance(this.mBottomGlow, f2 / getHeight(), 1.0f - (x / getWidth()));
                        }
                        this.mEdgeEffectByDragging = z2;
                        if (!z2 || f != 0.0f || f2 != 0.0f) {
                            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
                        }
                    }
                    z2 = true;
                    this.mEdgeEffectByDragging = z2;
                    if (!z2) {
                    }
                    WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
                }
                z2 = true;
                if (f2 >= 0.0f) {
                }
                z2 = true;
                this.mEdgeEffectByDragging = z2;
                if (!z2) {
                }
                WeakHashMap weakHashMap22 = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
            }
            considerReleasingGlowsOnScroll(i, i2);
        }
        if (i5 != 0 || i4 != 0) {
            dispatchOnScrolled(i5, i4);
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        if (!(this.mLayout instanceof StaggeredGridLayoutManager) || (canScrollVertically(-1) && canScrollVertically(1))) {
            z = false;
        } else {
            z = false;
            this.mLayout.onScrollStateChanged(0);
        }
        this.mPreventFirstGlow = z;
        if (!z3 && i5 == 0 && i4 == 0) {
            return z;
        }
        return true;
    }

    public final void scrollStep(int i, int i2, int[] iArr) {
        int i3;
        ViewHolder viewHolder;
        startInterceptRequestLayout();
        onEnterLayoutOrScroll();
        Trace.beginSection("RV Scroll");
        fillRemainingScrollValues(this.mState);
        int scrollHorizontallyBy = i != 0 ? this.mLayout.scrollHorizontallyBy(i, this.mRecycler, this.mState) : 0;
        if (i2 != 0) {
            i3 = this.mLayout.scrollVerticallyBy(i2, this.mRecycler, this.mState);
            if (this.mGoToTopState == 0) {
                setupGoToTop(1);
                autoHide(1);
            }
        } else {
            i3 = 0;
        }
        Trace.endSection();
        int childCount = this.mChildHelper.getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = this.mChildHelper.getChildAt(i4);
            ViewHolder childViewHolder = getChildViewHolder(childAt);
            if (childViewHolder != null && (viewHolder = childViewHolder.mShadowingHolder) != null) {
                int left = childAt.getLeft();
                int top = childAt.getTop();
                View view = viewHolder.itemView;
                if (left != view.getLeft() || top != view.getTop()) {
                    view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
                }
            }
        }
        onExitLayoutOrScroll(true);
        stopInterceptRequestLayout(false);
        if (iArr != null) {
            iArr[0] = scrollHorizontallyBy;
            iArr[1] = i3;
        }
    }

    @Override // android.view.View
    public final void scrollTo(int i, int i2) {
        Log.w("SeslRecyclerView", "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void scrollToPosition(int i) {
        if (this.mLayoutSuppressed) {
            return;
        }
        stopScroll();
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e("SeslRecyclerView", "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        layoutManager.scrollToPosition(i);
        awakenScrollBars();
        SeslRecyclerViewFastScroller seslRecyclerViewFastScroller = this.mFastScroller;
        if (seslRecyclerViewFastScroller == null || this.mAdapter == null) {
            return;
        }
        seslRecyclerViewFastScroller.onScroll(findFirstVisibleItemPosition(), getChildCount(), this.mAdapter.getItemCount());
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

    public final View seslFindNearChildViewUnder(float f, float f2) {
        int i = (int) (f + 0.5f);
        int i2 = (int) (0.5f + f2);
        int childCount = this.mChildHelper.getChildCount() - 1;
        int i3 = 0;
        int i4 = i2;
        int i5 = Integer.MAX_VALUE;
        for (int i6 = childCount; i6 >= 0; i6--) {
            View childAt = getChildAt(i6);
            if (childAt != null) {
                int bottom = (childAt.getBottom() + childAt.getTop()) / 2;
                if (i3 != bottom) {
                    int abs = Math.abs(i2 - bottom);
                    if (abs < i5) {
                        i5 = abs;
                        i3 = bottom;
                        i4 = i3;
                    } else {
                        if (!(this.mLayout instanceof StaggeredGridLayoutManager)) {
                            break;
                        }
                        i3 = bottom;
                    }
                } else {
                    continue;
                }
            }
        }
        int i7 = -1;
        int i8 = Integer.MAX_VALUE;
        int i9 = Integer.MAX_VALUE;
        int i10 = -1;
        while (childCount >= 0) {
            View childAt2 = getChildAt(childCount);
            if (childAt2 != null) {
                int top = childAt2.getTop();
                int bottom2 = childAt2.getBottom();
                int left = childAt2.getLeft();
                int right = childAt2.getRight();
                if (i4 >= top && i4 <= bottom2) {
                    int abs2 = Math.abs(i - left);
                    int abs3 = Math.abs(i - right);
                    if (abs2 <= i8) {
                        i7 = childCount;
                        i8 = abs2;
                    }
                    if (abs3 <= i9) {
                        i10 = childCount;
                        i9 = abs3;
                    }
                }
                if (i4 > bottom2 || childCount == 0) {
                    return i8 < i9 ? this.mChildHelper.getChildAt(i7) : this.mChildHelper.getChildAt(i10);
                }
            }
            childCount--;
        }
        Log.e("SeslRecyclerView", "findNearChildViewUnder didn't find valid child view! " + f + ", " + f2);
        return null;
    }

    public final void seslSetFastScrollerEnabled(boolean z) {
        SeslRecyclerViewFastScroller seslRecyclerViewFastScroller = this.mFastScroller;
        if (seslRecyclerViewFastScroller != null) {
            r1 = z != seslRecyclerViewFastScroller.isEnabled();
            SeslRecyclerViewFastScroller seslRecyclerViewFastScroller2 = this.mFastScroller;
            if (seslRecyclerViewFastScroller2.mEnabled != z) {
                seslRecyclerViewFastScroller2.mEnabled = z;
                seslRecyclerViewFastScroller2.onStateDependencyChanged();
            }
        } else if (z) {
            SeslRecyclerViewFastScroller seslRecyclerViewFastScroller3 = new SeslRecyclerViewFastScroller(this);
            this.mFastScroller = seslRecyclerViewFastScroller3;
            if (!seslRecyclerViewFastScroller3.mEnabled) {
                seslRecyclerViewFastScroller3.mEnabled = true;
                seslRecyclerViewFastScroller3.onStateDependencyChanged();
            }
            this.mFastScroller.setScrollbarPosition(getVerticalScrollbarPosition());
        } else {
            r1 = false;
        }
        SeslRecyclerViewFastScroller seslRecyclerViewFastScroller4 = this.mFastScroller;
        if (seslRecyclerViewFastScroller4 != null && r1) {
            seslRecyclerViewFastScroller4.updateLayout();
        }
        if (this.mLayout instanceof StaggeredGridLayoutManager) {
            Log.w("SeslRecyclerView", "FastScroller cannot be used with StaggeredGridLayoutManager.");
        }
    }

    public final void seslSetFillBottomEnabled(boolean z) {
        if (this.mLayout instanceof LinearLayoutManager) {
            this.mDrawRect = z;
            requestLayout();
        }
    }

    public final void seslSetGoToTopEnabled(boolean z) {
        boolean isLightTheme = SeslMisc.isLightTheme(this.mContext);
        Drawable drawable = this.mContext.getResources().getDrawable(isLightTheme ? com.android.systemui.R.drawable.sesl_list_go_to_top_light : com.android.systemui.R.drawable.sesl_list_go_to_top_dark);
        this.mGoToTopImage = drawable;
        if (drawable != null) {
            if (this.mGoToTopView == null) {
                this.mGoToTopView = new ImageView(this.mContext);
            }
            this.mGoToTopView.setBackground(this.mContext.getResources().getDrawable(isLightTheme ? com.android.systemui.R.drawable.sesl_go_to_top_background_light : com.android.systemui.R.drawable.sesl_go_to_top_background_dark, null));
            this.mGoToTopView.setElevation(this.mGoToTopElevation);
            this.mGoToTopView.setImageDrawable(this.mGoToTopImage);
            if (z) {
                this.mGoToTopView.setAlpha(0.0f);
                if (!this.mEnableGoToTop) {
                    getOverlay().add(this.mGoToTopView);
                }
            } else if (this.mEnableGoToTop) {
                getOverlay().remove(this.mGoToTopView);
            }
            this.mEnableGoToTop = z;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mGoToTopFadeInAnimator = ofFloat;
            ofFloat.setDuration(333L);
            this.mGoToTopFadeInAnimator.setInterpolator(SeslAnimationUtils.SINE_IN_OUT_70);
            this.mGoToTopFadeInAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.recyclerview.widget.RecyclerView.14
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    try {
                        RecyclerView.this.mGoToTopView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    } catch (Exception unused) {
                    }
                }
            });
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
            this.mGoToTopFadeOutAnimator = ofFloat2;
            ofFloat2.setDuration(150L);
            this.mGoToTopFadeOutAnimator.setInterpolator(LINEAR_INTERPOLATOR);
            this.mGoToTopFadeOutAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.recyclerview.widget.RecyclerView.15
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    try {
                        RecyclerView.this.mGoToTopView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    } catch (Exception unused) {
                    }
                }
            });
            this.mGoToTopFadeOutAnimator.addListener(new Animator.AnimatorListener() { // from class: androidx.recyclerview.widget.RecyclerView.16
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    try {
                        RecyclerView recyclerView = RecyclerView.this;
                        recyclerView.mShowFadeOutGTP = 2;
                        recyclerView.setupGoToTop(0);
                    } catch (Exception unused) {
                    }
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    try {
                        RecyclerView.this.mShowFadeOutGTP = 1;
                    } catch (Exception unused) {
                    }
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator) {
                }
            });
        }
    }

    public void setAdapter(Adapter adapter) {
        suppressLayout(false);
        Adapter adapter2 = this.mAdapter;
        if (adapter2 != null) {
            adapter2.mObservable.unregisterObserver(this.mObserver);
            this.mAdapter.onDetachedFromRecyclerView(this);
        }
        removeAndRecycleViews();
        AdapterHelper adapterHelper = this.mAdapterHelper;
        adapterHelper.recycleUpdateOpsAndClearList(adapterHelper.mPendingUpdates);
        adapterHelper.recycleUpdateOpsAndClearList(adapterHelper.mPostponedList);
        adapterHelper.mExistingUpdateTypes = 0;
        Adapter adapter3 = this.mAdapter;
        this.mAdapter = adapter;
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.mObserver);
            adapter.onAttachedToRecyclerView(this);
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.onAdapterChanged(adapter3);
        }
        Recycler recycler = this.mRecycler;
        Adapter adapter4 = this.mAdapter;
        recycler.mAttachedScrap.clear();
        recycler.recycleAndClearCachedViews();
        recycler.poolingContainerDetach(adapter3, true);
        RecycledViewPool recycledViewPool = recycler.getRecycledViewPool();
        if (adapter3 != null) {
            recycledViewPool.mAttachCountForClearing--;
        }
        if (recycledViewPool.mAttachCountForClearing == 0) {
            int i = 0;
            while (true) {
                SparseArray sparseArray = recycledViewPool.mScrap;
                if (i >= sparseArray.size()) {
                    break;
                }
                RecycledViewPool.ScrapData scrapData = (RecycledViewPool.ScrapData) sparseArray.valueAt(i);
                if (scrapData != null) {
                    ArrayList arrayList = scrapData.mScrapHeap;
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        PoolingContainer.callPoolingContainerOnRelease(((ViewHolder) it.next()).itemView);
                    }
                    arrayList.clear();
                } else {
                    Log.e("SeslRecyclerView", "clear() wasn't executed because RecycledViewPool.mScrap was invalid");
                }
                i++;
            }
        }
        if (adapter4 != null) {
            recycledViewPool.mAttachCountForClearing++;
        }
        recycler.maybeSendPoolingContainerAttach();
        this.mState.mStructureChanged = true;
        processDataSetCompletelyChanged(false);
        requestLayout();
    }

    public boolean setChildImportantForAccessibilityInternal(ViewHolder viewHolder, int i) {
        if (isComputingLayout()) {
            viewHolder.mPendingAccessibilityState = i;
            this.mPendingAccessibilityImportanceChange.add(viewHolder);
            return false;
        }
        View view = viewHolder.itemView;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setImportantForAccessibility(view, i);
        return true;
    }

    @Override // android.view.ViewGroup
    public final void setClipToPadding(boolean z) {
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

    public final void setItemAnimator(ItemAnimator itemAnimator) {
        ItemAnimator itemAnimator2 = this.mItemAnimator;
        if (itemAnimator2 != null) {
            itemAnimator2.endAnimations();
            this.mItemAnimator.mListener = null;
        }
        this.mItemAnimator = itemAnimator;
        if (itemAnimator != null) {
            itemAnimator.mListener = this.mItemAnimatorListener;
            itemAnimator.mHostView = this;
        }
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        ChildHelper.Callback callback;
        RecyclerView recyclerView;
        if (layoutManager == this.mLayout) {
            return;
        }
        boolean z = layoutManager instanceof LinearLayoutManager;
        int i = 0;
        this.mDrawRect = this.mDrawRect && z;
        this.mDrawLastRoundedCorner = this.mDrawLastRoundedCorner && z;
        stopScroll();
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
            C045510 c045510 = (C045510) callback;
            c045510.getClass();
            ViewHolder childViewHolderInt = getChildViewHolderInt(view);
            if (childViewHolderInt != null) {
                RecyclerView.this.setChildImportantForAccessibilityInternal(childViewHolderInt, childViewHolderInt.mWasImportantForAccessibilityBeforeHidden);
                childViewHolderInt.mWasImportantForAccessibilityBeforeHidden = 0;
            }
            arrayList.remove(size);
        }
        C045510 c0455102 = (C045510) callback;
        int childCount = c0455102.getChildCount();
        while (true) {
            recyclerView = RecyclerView.this;
            if (i >= childCount) {
                break;
            }
            View childAt = recyclerView.getChildAt(i);
            recyclerView.dispatchChildDetached(childAt);
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
    public final void setLayoutTransition(LayoutTransition layoutTransition) {
        if (layoutTransition != null) {
            throw new IllegalArgumentException("Providing a LayoutTransition into RecyclerView is not supported. Please use setItemAnimator() instead for animating changes to the items in this RecyclerView");
        }
        super.setLayoutTransition(null);
    }

    @Override // android.view.View
    public final void setNestedScrollingEnabled(boolean z) {
        NestedScrollingChildHelper scrollingChildHelper = getScrollingChildHelper();
        if (scrollingChildHelper.mIsNestedScrollingEnabled) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.stopNestedScroll(scrollingChildHelper.mView);
        }
        scrollingChildHelper.mIsNestedScrollingEnabled = z;
    }

    final void setScrollState(int i) {
        SmoothScroller smoothScroller;
        if (i == this.mScrollState) {
            return;
        }
        RecyclerView$$ExternalSyntheticOutline0.m46m(AbstractC0000x2c234b15.m1m("setting scroll state to ", i, " from "), this.mScrollState, "SeslRecyclerView");
        this.mScrollState = i;
        if (i != 2) {
            ViewFlinger viewFlinger = this.mViewFlinger;
            RecyclerView.this.removeCallbacks(viewFlinger);
            viewFlinger.mOverScroller.abortAnimation();
            LayoutManager layoutManager = this.mLayout;
            if (layoutManager != null && (smoothScroller = layoutManager.mSmoothScroller) != null) {
                smoothScroller.stop();
            }
        }
        LayoutManager layoutManager2 = this.mLayout;
        if (layoutManager2 != null) {
            layoutManager2.onScrollStateChanged(i);
        }
        List list = this.mScrollListeners;
        if (list != null) {
            int size = ((ArrayList) list).size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                } else {
                    ((OnScrollListener) ((ArrayList) this.mScrollListeners).get(size)).onScrollStateChanged(this, i);
                }
            }
        }
        if (i == 1) {
            this.mEdgeEffectByDragging = false;
        }
    }

    public final boolean shouldAbsorb(EdgeEffect edgeEffect, int i, int i2) {
        if (i > 0) {
            return true;
        }
        float distance = EdgeEffectCompat.getDistance(edgeEffect) * i2;
        double log = Math.log((Math.abs(-i) * 0.35f) / (this.mPhysicalCoef * 0.015f));
        double d = DECELERATION_RATE;
        return ((float) (Math.exp((d / (d - 1.0d)) * log) * ((double) (this.mPhysicalCoef * 0.015f)))) < distance;
    }

    final void showGoToTop() {
        if (this.mEnableGoToTop && canScrollUp$1() && this.mGoToTopState != 2) {
            setupGoToTop(1);
            autoHide(1);
        }
    }

    public void smoothScrollBy(int i, int i2) {
        smoothScrollBy$2(i, i2);
    }

    public void smoothScrollBy$2(int i, int i2) {
        smoothScrollBy(i, i2, false);
    }

    public void smoothScrollToPosition(int i) {
        if (this.mLayoutSuppressed) {
            return;
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e("SeslRecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else {
            layoutManager.smoothScrollToPosition(this, i);
        }
    }

    public final void startInterceptRequestLayout() {
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

    public final void stopInterceptRequestLayout(boolean z) {
        if (this.mInterceptRequestLayoutDepth < 1) {
            this.mInterceptRequestLayoutDepth = 1;
        }
        if (!z && !this.mLayoutSuppressed) {
            this.mLayoutWasDefered = false;
        }
        if (this.mInterceptRequestLayoutDepth == 1) {
            if (z && this.mLayoutWasDefered && !this.mLayoutSuppressed && this.mLayout != null && this.mAdapter != null) {
                dispatchLayout();
            }
            if (!this.mLayoutSuppressed) {
                this.mLayoutWasDefered = false;
            }
        }
        this.mInterceptRequestLayoutDepth--;
    }

    @Override // android.view.View
    public final void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll(0);
    }

    public final void stopScroll() {
        SmoothScroller smoothScroller;
        setScrollState(0);
        ViewFlinger viewFlinger = this.mViewFlinger;
        RecyclerView.this.removeCallbacks(viewFlinger);
        viewFlinger.mOverScroller.abortAnimation();
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null || (smoothScroller = layoutManager.mSmoothScroller) == null) {
            return;
        }
        smoothScroller.stop();
    }

    @Override // android.view.ViewGroup
    public final void suppressLayout(boolean z) {
        if (z != this.mLayoutSuppressed) {
            assertNotInLayoutOrScroll("Do not suppressLayout in layout or scroll");
            if (z) {
                long uptimeMillis = SystemClock.uptimeMillis();
                onTouchEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0));
                this.mLayoutSuppressed = true;
                this.mIgnoreMotionEventTillDown = true;
                stopScroll();
                return;
            }
            this.mLayoutSuppressed = false;
            if (this.mLayoutWasDefered && this.mLayout != null && this.mAdapter != null) {
                requestLayout();
            }
            this.mLayoutWasDefered = false;
        }
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return this.mGoToTopImage == drawable || super.verifyDrawable(drawable);
    }

    public RecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.recyclerViewStyle);
    }

    public final void smoothScrollBy(int i, int i2, boolean z) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e("SeslRecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
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
            startNestedScroll(i3, 1);
        }
        this.mViewFlinger.smoothScrollBy(i, i2, VideoPlayer.MEDIA_ERROR_SYSTEM, null);
        showGoToTop();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: androidx.recyclerview.widget.RecyclerView.SavedState.1
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
        public Parcelable mLayoutState;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.mLayoutState = parcel.readParcelable(classLoader == null ? LayoutManager.class.getClassLoader() : classLoader);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mSuperState, i);
            parcel.writeParcelable(this.mLayoutState, 0);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.recyclerview.widget.RecyclerView$1] */
    /* JADX WARN: Type inference failed for: r2v18, types: [androidx.recyclerview.widget.RecyclerView$6] */
    /* JADX WARN: Type inference failed for: r2v20, types: [androidx.recyclerview.widget.RecyclerView$7] */
    /* JADX WARN: Type inference failed for: r2v5, types: [androidx.recyclerview.widget.RecyclerView$2] */
    /* JADX WARN: Type inference failed for: r2v7, types: [androidx.recyclerview.widget.RecyclerView$3] */
    /* JADX WARN: Type inference failed for: r2v8, types: [androidx.recyclerview.widget.RecyclerView$4] */
    /* JADX WARN: Type inference failed for: r2v9, types: [androidx.recyclerview.widget.RecyclerView$5] */
    public RecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int i2;
        ClassLoader classLoader;
        Constructor constructor;
        Object[] objArr;
        this.mObserver = new RecyclerViewDataObserver();
        this.mRecycler = new Recycler();
        this.mViewInfoStore = new ViewInfoStore();
        this.mUpdateChildViewsRunnable = new Runnable() { // from class: androidx.recyclerview.widget.RecyclerView.1
            @Override // java.lang.Runnable
            public final void run() {
                RecyclerView recyclerView = RecyclerView.this;
                if (!recyclerView.mFirstLayoutComplete || recyclerView.isLayoutRequested()) {
                    return;
                }
                RecyclerView recyclerView2 = RecyclerView.this;
                if (!recyclerView2.mIsAttached) {
                    recyclerView2.requestLayout();
                } else if (recyclerView2.mLayoutSuppressed) {
                    recyclerView2.mLayoutWasDefered = true;
                } else {
                    recyclerView2.consumePendingUpdateOperations();
                }
            }
        };
        this.mTempRect = new Rect();
        this.mTempRect2 = new Rect();
        this.mTempRectF = new RectF();
        this.mRecyclerListeners = new ArrayList();
        this.mItemDecorations = new ArrayList();
        this.mOnItemTouchListeners = new ArrayList();
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
        ItemAnimatorRestoreListener itemAnimatorRestoreListener = new ItemAnimatorRestoreListener();
        this.mItemAnimatorListener = itemAnimatorRestoreListener;
        this.mPostedAnimatorRunner = false;
        this.mMinMaxLayoutPositions = new int[2];
        this.mScrollOffset = new int[2];
        this.mNestedOffsets = new int[2];
        this.mWindowOffsets = new int[2];
        this.mEdgeEffectByDragging = false;
        this.mIsSkipMoveEvent = false;
        this.mFrameLatency = 16.66f;
        this.mIsNeedCheckLatency = true;
        this.mLastItemAddRemoveAnim = null;
        this.mIsSetOnlyAddAnim = false;
        this.mIsSetOnlyRemoveAnim = false;
        this.mLastItemAnimTop = -1;
        this.mPreventFirstGlow = false;
        this.mAnimListener = new Animator.AnimatorListener() { // from class: androidx.recyclerview.widget.RecyclerView.2
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                RecyclerView recyclerView = RecyclerView.this;
                recyclerView.mLastItemAddRemoveAnim = null;
                recyclerView.mIsSetOnlyAddAnim = false;
                recyclerView.mIsSetOnlyRemoveAnim = false;
                ItemAnimator itemAnimator = recyclerView.mItemAnimator;
                if (itemAnimator instanceof DefaultItemAnimator) {
                    ((DefaultItemAnimator) itemAnimator).mPendingAnimFlag = 0;
                }
                recyclerView.invalidate();
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        };
        this.mReusableIntPair = new int[2];
        this.mTouchSlop2 = 0;
        this.mPagingTouchSlop = 0;
        this.mUsePagingTouchSlopForStylus = false;
        this.mGoToToFadeOutRunnable = new Runnable() { // from class: androidx.recyclerview.widget.RecyclerView.3
            @Override // java.lang.Runnable
            public final void run() {
                RecyclerView recyclerView = RecyclerView.this;
                if (recyclerView.mGoToTopFadeOutAnimator.isRunning()) {
                    return;
                }
                if (recyclerView.mGoToTopFadeInAnimator.isRunning()) {
                    recyclerView.mGoToTopFadeOutAnimator.cancel();
                }
                recyclerView.mGoToTopFadeOutAnimator.setFloatValues(recyclerView.mGoToTopView.getAlpha(), 0.0f);
                recyclerView.mGoToTopFadeOutAnimator.start();
            }
        };
        this.mGoToToFadeInRunnable = new Runnable() { // from class: androidx.recyclerview.widget.RecyclerView.4
            @Override // java.lang.Runnable
            public final void run() {
                RecyclerView recyclerView = RecyclerView.this;
                if (recyclerView.mGoToTopFadeInAnimator.isRunning()) {
                    return;
                }
                if (recyclerView.mGoToTopFadeOutAnimator.isRunning()) {
                    recyclerView.mGoToTopFadeOutAnimator.cancel();
                }
                if (recyclerView.mGoToTopImage.getAlpha() < 255) {
                    recyclerView.mGoToTopImage.setAlpha(255);
                }
                recyclerView.mGoToTopFadeInAnimator.setFloatValues(recyclerView.mGoToTopView.getAlpha(), 1.0f);
                recyclerView.mGoToTopFadeInAnimator.start();
            }
        };
        this.mAutoHide = new Runnable() { // from class: androidx.recyclerview.widget.RecyclerView.5
            @Override // java.lang.Runnable
            public final void run() {
                RecyclerView.this.setupGoToTop(0);
            }
        };
        this.mEnableGoToTop = false;
        this.mSizeChnage = false;
        this.mSeslOverlayFeatureHeight = 0;
        this.mGoToTopRect = new Rect();
        this.mGoToTopState = 0;
        this.mGoToTopLastState = 0;
        this.mShowFadeOutGTP = 0;
        this.mIsPenSelectionEnabled = true;
        this.mIsPenPressed = false;
        this.mIsFirstPenMoveEvent = true;
        this.mIsNeedPenSelection = false;
        this.mPenDragSelectedViewPosition = -1;
        this.mIsPenDragBlockEnabled = true;
        this.mPenDragStartX = 0;
        this.mPenDragStartY = 0;
        this.mPenDragEndX = 0;
        this.mPenDragEndY = 0;
        this.mPenDragBlockLeft = 0;
        this.mPenDragBlockTop = 0;
        this.mPenDragBlockRight = 0;
        this.mPenTrackedChild = null;
        this.mPenTrackedChildPosition = -1;
        this.mPenDistanceFromTrackedChildTop = 0;
        this.mPenDragBlockRect = new Rect();
        this.mInitialTopOffsetOfScreen = 0;
        this.mRemainNestedScrollRange = 0;
        this.mNestedScrollRange = 0;
        this.mHasNestedScrollRange = false;
        this.mIsCtrlKeyPressed = false;
        this.mIsFirstMultiSelectionMove = true;
        this.mIsCtrlMultiSelection = false;
        this.mDrawRect = false;
        this.mDrawLastRoundedCorner = true;
        this.mDrawReverse = false;
        this.mBlackTop = -1;
        this.mLastBlackTop = -1;
        this.mAnimatedBlackTop = -1;
        this.mRectPaint = new Paint();
        this.mIsPenHovered = false;
        this.mIsPenSelectPointerSetted = false;
        this.mIsNeedPenSelectIconSet = false;
        this.mOldTextViewHoverState = false;
        this.mNewTextViewHoverState = false;
        this.mHoverScrollSpeed = 0;
        int[] iArr = new int[4];
        iArr[0] = SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_SCROLL_UP();
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod("android.view.PointerIcon", "hidden_SEM_TYPE_STYLUS_SCROLL_RIGHT", new Class[0]);
        Object invoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        iArr[1] = invoke instanceof Integer ? ((Integer) invoke).intValue() : 13;
        iArr[2] = SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_SCROLL_DOWN();
        Method declaredMethod2 = SeslBaseReflector.getDeclaredMethod("android.view.PointerIcon", "hidden_SEM_TYPE_STYLUS_SCROLL_LEFT", new Class[0]);
        Object invoke2 = declaredMethod2 != null ? SeslBaseReflector.invoke(null, declaredMethod2, new Object[0]) : null;
        iArr[3] = invoke2 instanceof Integer ? ((Integer) invoke2).intValue() : 17;
        this.mHoverScrollArrows = iArr;
        this.mHoverRecognitionDurationTime = 0L;
        this.mHoverRecognitionCurrentTime = 0L;
        this.mHoverRecognitionStartTime = 0L;
        this.mHoverScrollTimeInterval = 300L;
        this.mPenDragScrollTimeInterval = 500L;
        this.mHoverScrollStartTime = 0L;
        this.mHoverScrollDirection = -1;
        this.mIsHoverOverscrolled = false;
        this.mIsSendHoverScrollState = false;
        this.mHoverScrollStateForListener = 0;
        this.mHoverAreaEnter = false;
        new Rect();
        this.mHoverScrollEnable = true;
        this.mNeedsHoverScroll = false;
        this.mHoverTopAreaHeight = 0;
        this.mHoverBottomAreaHeight = 0;
        this.mListPadding = new Rect();
        this.mChildBound = new Rect();
        this.mIsCloseChildSetted = false;
        this.mOldHoverScrollDirection = -1;
        this.mCloseChildPositionByTop = -1;
        this.mCloseChildPositionByBottom = -1;
        this.mHoverHandler = new Handler(Looper.getMainLooper()) { // from class: androidx.recyclerview.widget.RecyclerView.6
            /* JADX WARN: Code restructure failed: missing block: B:85:0x01d9, code lost:
            
                if ((r4 == 0 || (r4 == r1.mAdapter.getItemCount() && r1.getChildAt(0).getTop() >= r1.mListPadding.top && r1.getChildAt(r4 - 1).getBottom() <= r1.getHeight() - r1.mListPadding.bottom)) == false) goto L112;
             */
            /* JADX WARN: Removed duplicated region for block: B:114:0x027c  */
            /* JADX WARN: Removed duplicated region for block: B:116:0x0281  */
            /* JADX WARN: Removed duplicated region for block: B:121:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:89:0x01e1  */
            @Override // android.os.Handler
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void handleMessage(Message message) {
                int i3;
                boolean z;
                RecyclerView recyclerView;
                if (message.what != 0) {
                    return;
                }
                RecyclerView recyclerView2 = RecyclerView.this;
                if (recyclerView2.mAdapter == null) {
                    Log.e("SeslRecyclerView", "No adapter attached; skipping MSG_HOVERSCROLL_MOVE");
                    return;
                }
                recyclerView2.mHoverRecognitionCurrentTime = System.currentTimeMillis();
                RecyclerView recyclerView3 = RecyclerView.this;
                long j = recyclerView3.mHoverRecognitionCurrentTime;
                recyclerView3.mHoverRecognitionDurationTime = (j - recyclerView3.mHoverRecognitionStartTime) / 1000;
                boolean z2 = recyclerView3.mIsPenHovered;
                if (z2 && j - recyclerView3.mHoverScrollStartTime < recyclerView3.mHoverScrollTimeInterval) {
                    return;
                }
                if (recyclerView3.mIsPenPressed && j - recyclerView3.mHoverScrollStartTime < recyclerView3.mPenDragScrollTimeInterval) {
                    return;
                }
                if (z2 && !recyclerView3.mIsSendHoverScrollState) {
                    recyclerView3.mIsSendHoverScrollState = true;
                }
                boolean canScrollVertically = recyclerView3.mLayout.canScrollVertically();
                boolean canScrollHorizontally = RecyclerView.this.mLayout.canScrollHorizontally();
                boolean z3 = RecyclerView.this.mLayout.getLayoutDirection() == 1;
                boolean canScrollDown = RecyclerView.this.canScrollDown();
                boolean canScrollUp$1 = RecyclerView.this.canScrollUp$1();
                RecyclerView recyclerView4 = RecyclerView.this;
                recyclerView4.mHoverScrollSpeed = (int) (TypedValue.applyDimension(1, RecyclerView.HOVERSCROLL_SPEED, recyclerView4.mContext.getResources().getDisplayMetrics()) + 0.5f);
                RecyclerView recyclerView5 = RecyclerView.this;
                long j2 = recyclerView5.mHoverRecognitionDurationTime;
                if (j2 > 2 && j2 < 4) {
                    int i4 = recyclerView5.mHoverScrollSpeed;
                    recyclerView5.mHoverScrollSpeed = i4 + ((int) (i4 * 0.1d));
                } else if (j2 >= 4 && j2 < 5) {
                    int i5 = recyclerView5.mHoverScrollSpeed;
                    recyclerView5.mHoverScrollSpeed = i5 + ((int) (i5 * 0.2d));
                } else if (j2 >= 5) {
                    int i6 = recyclerView5.mHoverScrollSpeed;
                    recyclerView5.mHoverScrollSpeed = i6 + ((int) (i6 * 0.3d));
                }
                int i7 = recyclerView5.mHoverScrollDirection;
                if (i7 == 2) {
                    i3 = (canScrollHorizontally && z3) ? recyclerView5.mHoverScrollSpeed * 1 : recyclerView5.mHoverScrollSpeed * (-1);
                    if (recyclerView5.mOldHoverScrollDirection != i7 && recyclerView5.mIsCloseChildSetted) {
                        recyclerView5.mPenTrackedChild = null;
                        recyclerView5.getClass();
                        recyclerView5.mPenDistanceFromTrackedChildTop = 0;
                        RecyclerView recyclerView6 = RecyclerView.this;
                        recyclerView6.mPenTrackedChildPosition = recyclerView6.mCloseChildPositionByBottom;
                        recyclerView6.mOldHoverScrollDirection = recyclerView6.mHoverScrollDirection;
                        recyclerView6.mIsCloseChildSetted = true;
                    }
                } else {
                    i3 = (canScrollHorizontally && z3) ? recyclerView5.mHoverScrollSpeed * (-1) : recyclerView5.mHoverScrollSpeed * 1;
                    if (recyclerView5.mOldHoverScrollDirection != i7 && recyclerView5.mIsCloseChildSetted) {
                        recyclerView5.mPenTrackedChild = null;
                        recyclerView5.getClass();
                        recyclerView5.mPenDistanceFromTrackedChildTop = 0;
                        RecyclerView recyclerView7 = RecyclerView.this;
                        recyclerView7.mPenTrackedChildPosition = recyclerView7.mCloseChildPositionByTop;
                        recyclerView7.mOldHoverScrollDirection = recyclerView7.mHoverScrollDirection;
                        recyclerView7.mIsCloseChildSetted = true;
                    }
                }
                RecyclerView recyclerView8 = RecyclerView.this;
                if (recyclerView8.getChildAt(recyclerView8.getChildCount() - 1) == null) {
                    return;
                }
                if ((i3 < 0 && canScrollUp$1) || (i3 > 0 && canScrollDown)) {
                    RecyclerView.this.startNestedScroll(canScrollHorizontally ? 1 : 2, 1);
                    if (RecyclerView.this.dispatchNestedPreScroll(canScrollHorizontally ? z3 ? -i3 : i3 : 0, canScrollVertically ? i3 : 0, 1, null, null)) {
                        RecyclerView.this.adjustNestedScrollRangeBy(i3);
                    } else {
                        RecyclerView recyclerView9 = RecyclerView.this;
                        int i8 = canScrollHorizontally ? z3 ? -i3 : i3 : 0;
                        if (!canScrollVertically) {
                            i3 = 0;
                        }
                        recyclerView9.scrollByInternal(i8, i3, null, 0);
                        RecyclerView.this.setScrollState(1);
                        RecyclerView.this.getClass();
                    }
                    sendEmptyMessageDelayed(0, 0L);
                    return;
                }
                int overScrollMode = RecyclerView.this.getOverScrollMode();
                if (overScrollMode != 0) {
                    if (overScrollMode == 1) {
                        RecyclerView recyclerView10 = RecyclerView.this;
                        int childCount = recyclerView10.getChildCount();
                    }
                    z = false;
                    if (z) {
                        RecyclerView recyclerView11 = RecyclerView.this;
                        if (!recyclerView11.mIsHoverOverscrolled) {
                            if (canScrollHorizontally) {
                                recyclerView11.ensureLeftGlow();
                                RecyclerView.this.ensureRightGlow();
                            } else {
                                recyclerView11.ensureTopGlow();
                                RecyclerView.this.ensureBottomGlow();
                            }
                            RecyclerView recyclerView12 = RecyclerView.this;
                            int i9 = recyclerView12.mHoverScrollDirection;
                            if (i9 == 2) {
                                if (canScrollHorizontally) {
                                    recyclerView12.mLeftGlow.onAbsorb(10000);
                                    if (!RecyclerView.this.mRightGlow.isFinished()) {
                                        RecyclerView.this.mRightGlow.onRelease();
                                    }
                                } else {
                                    recyclerView12.mTopGlow.onAbsorb(10000);
                                    if (!RecyclerView.this.mBottomGlow.isFinished()) {
                                        RecyclerView.this.mBottomGlow.onRelease();
                                    }
                                }
                            } else if (i9 == 1) {
                                if (canScrollHorizontally) {
                                    recyclerView12.mRightGlow.onAbsorb(10000);
                                    if (!RecyclerView.this.mLeftGlow.isFinished()) {
                                        RecyclerView.this.mLeftGlow.onRelease();
                                    }
                                } else {
                                    recyclerView12.mBottomGlow.onAbsorb(10000);
                                    RecyclerView.this.setupGoToTop(1);
                                    RecyclerView.this.autoHide(1);
                                    if (!RecyclerView.this.mTopGlow.isFinished()) {
                                        RecyclerView.this.mTopGlow.onRelease();
                                    }
                                }
                            }
                            RecyclerView.this.invalidate();
                            RecyclerView.this.mIsHoverOverscrolled = true;
                        }
                    }
                    recyclerView = RecyclerView.this;
                    if (recyclerView.mScrollState == 1) {
                        recyclerView.setScrollState(0);
                    }
                    if (z) {
                        RecyclerView recyclerView13 = RecyclerView.this;
                        if (recyclerView13.mIsHoverOverscrolled) {
                            return;
                        }
                        recyclerView13.mIsHoverOverscrolled = true;
                        return;
                    }
                    return;
                }
                z = true;
                if (z) {
                }
                recyclerView = RecyclerView.this;
                if (recyclerView.mScrollState == 1) {
                }
                if (z) {
                }
            }
        };
        this.mPendingAccessibilityImportanceChange = new ArrayList();
        this.mItemAnimatorRunner = new Runnable() { // from class: androidx.recyclerview.widget.RecyclerView.7
            @Override // java.lang.Runnable
            public final void run() {
                ItemAnimator itemAnimator = RecyclerView.this.mItemAnimator;
                if (itemAnimator != null) {
                    itemAnimator.runPendingAnimations();
                }
                RecyclerView.this.mPostedAnimatorRunner = false;
            }
        };
        this.mLastAutoMeasureNonExactMeasuredWidth = 0;
        this.mLastAutoMeasureNonExactMeasuredHeight = 0;
        this.mViewInfoProcessCallback = new C04729();
        new Runnable() { // from class: androidx.recyclerview.widget.RecyclerView.18
            @Override // java.lang.Runnable
            public final void run() {
                RecyclerView.this.ensureTopGlow();
                RecyclerView.this.mTopGlow.onAbsorb(10000);
                RecyclerView.this.invalidate();
            }
        };
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        this.mContext = context;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        Resources resources = context.getResources();
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mTouchSlop2 = viewConfiguration.getScaledTouchSlop();
        this.mPagingTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        this.mScaledHorizontalScrollFactor = viewConfiguration.getScaledHorizontalScrollFactor();
        this.mScaledVerticalScrollFactor = viewConfiguration.getScaledVerticalScrollFactor();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mHoverTopAreaHeight = (int) (TypedValue.applyDimension(1, 25.0f, resources.getDisplayMetrics()) + 0.5f);
        this.mHoverBottomAreaHeight = (int) (TypedValue.applyDimension(1, 25.0f, resources.getDisplayMetrics()) + 0.5f);
        this.mGoToTopBottomPadding = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_go_to_top_scrollable_view_gap);
        this.mGoToTopImmersiveBottomPadding = 0;
        this.mGoToTopSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_go_to_top_scrollable_view_size);
        this.mGoToTopElevation = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_go_to_top_elevation);
        this.mPhysicalCoef = context.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
        setWillNotDraw(getOverScrollMode() == 2);
        this.mItemAnimator.mListener = itemAnimatorRestoreListener;
        this.mAdapterHelper = new AdapterHelper(new C045611());
        this.mChildHelper = new ChildHelper(new C045510());
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api26Impl.getImportantForAutofill(this) == 0) {
            ViewCompat.Api26Impl.setImportantForAutofill(this, 8);
        }
        if (ViewCompat.Api16Impl.getImportantForAccessibility(this) == 0) {
            ViewCompat.Api16Impl.setImportantForAccessibility(this, 1);
        }
        this.mAccessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate = new RecyclerViewAccessibilityDelegate(this);
        this.mAccessibilityDelegate = recyclerViewAccessibilityDelegate;
        ViewCompat.setAccessibilityDelegate(this, recyclerViewAccessibilityDelegate);
        int[] iArr2 = R$styleable.RecyclerView;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr2, i, 0);
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr2, attributeSet, obtainStyledAttributes, i, 0);
        String string = obtainStyledAttributes.getString(8);
        if (obtainStyledAttributes.getInt(2, -1) == -1) {
            setDescendantFocusability(262144);
        }
        this.mClipToPadding = obtainStyledAttributes.getBoolean(1, true);
        if (obtainStyledAttributes.getBoolean(3, false)) {
            i2 = 4;
            initFastScroller((StateListDrawable) obtainStyledAttributes.getDrawable(6), obtainStyledAttributes.getDrawable(7), (StateListDrawable) obtainStyledAttributes.getDrawable(4), obtainStyledAttributes.getDrawable(5));
        } else {
            i2 = 4;
        }
        obtainStyledAttributes.recycle();
        if (string != null) {
            String trim = string.trim();
            if (!trim.isEmpty()) {
                if (trim.charAt(0) == '.') {
                    trim = context.getPackageName() + trim;
                } else if (!trim.contains(".")) {
                    trim = RecyclerView.class.getPackage().getName() + '.' + trim;
                }
                try {
                    if (isInEditMode()) {
                        classLoader = getClass().getClassLoader();
                    } else {
                        classLoader = context.getClassLoader();
                    }
                    Class<? extends U> asSubclass = Class.forName(trim, false, classLoader).asSubclass(LayoutManager.class);
                    try {
                        constructor = asSubclass.getConstructor(LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
                        Object[] objArr2 = new Object[i2];
                        objArr2[0] = context;
                        objArr2[1] = attributeSet;
                        objArr2[2] = Integer.valueOf(i);
                        objArr2[3] = 0;
                        objArr = objArr2;
                    } catch (NoSuchMethodException e) {
                        try {
                            constructor = asSubclass.getConstructor(new Class[0]);
                            objArr = null;
                        } catch (NoSuchMethodException e2) {
                            e2.initCause(e);
                            throw new IllegalStateException(attributeSet.getPositionDescription() + ": Error creating LayoutManager " + trim, e2);
                        }
                    }
                    constructor.setAccessible(true);
                    setLayoutManager((LayoutManager) constructor.newInstance(objArr));
                } catch (ClassCastException e3) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Class is not a LayoutManager " + trim, e3);
                } catch (ClassNotFoundException e4) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Unable to find LayoutManager " + trim, e4);
                } catch (IllegalAccessException e5) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Cannot access non-public constructor " + trim, e5);
                } catch (InstantiationException e6) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + trim, e6);
                } catch (InvocationTargetException e7) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + trim, e7);
                }
            }
        }
        int[] iArr3 = NESTED_SCROLLING_ATTRS;
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, iArr3, i, 0);
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr3, attributeSet, obtainStyledAttributes2, i, 0);
        boolean z = obtainStyledAttributes2.getBoolean(0, true);
        obtainStyledAttributes2.recycle();
        Resources resources2 = context.getResources();
        TypedValue typedValue = new TypedValue();
        this.mPenDragBlockImage = resources2.getDrawable(com.android.systemui.R.drawable.sesl_pen_block_selection);
        context.getTheme().resolveAttribute(com.android.systemui.R.attr.roundedCornerColor, typedValue, true);
        int i3 = typedValue.resourceId;
        if (i3 > 0) {
            this.mRectColor = resources2.getColor(i3);
        }
        this.mRectPaint.setColor(this.mRectColor);
        this.mRectPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mItemAnimator.mHostView = this;
        SeslSubheaderRoundedCorner seslSubheaderRoundedCorner = new SeslSubheaderRoundedCorner(getContext());
        this.mRoundedCorner = seslSubheaderRoundedCorner;
        seslSubheaderRoundedCorner.setRoundedCorners(12);
        setNestedScrollingEnabled(z);
        PoolingContainer.setPoolingContainer(this, true);
    }

    public final boolean dispatchNestedPreScroll(int i, int i2, int i3, int[] iArr, int[] iArr2) {
        return getScrollingChildHelper().dispatchNestedPreScroll(i, i2, i3, iArr, iArr2);
    }

    public final boolean startNestedScroll(int i, int i2) {
        return getScrollingChildHelper().startNestedScroll(i, i2);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public final void stopNestedScroll(int i) {
        getScrollingChildHelper().stopNestedScroll(i);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public final Rect mDecorInsets;
        public boolean mInsetsDirty;
        public boolean mPendingInvalidate;
        public ViewHolder mViewHolder;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mDecorInsets = new Rect();
            this.mInsetsDirty = true;
            this.mPendingInvalidate = false;
        }

        public final int getViewLayoutPosition() {
            return this.mViewHolder.getLayoutPosition();
        }

        public final boolean isItemChanged() {
            return (this.mViewHolder.mFlags & 2) != 0;
        }

        public final boolean isItemRemoved() {
            return this.mViewHolder.isRemoved();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.mDecorInsets = new Rect();
            this.mInsetsDirty = true;
            this.mPendingInvalidate = false;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.mDecorInsets = new Rect();
            this.mInsetsDirty = true;
            this.mPendingInvalidate = false;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mDecorInsets = new Rect();
            this.mInsetsDirty = true;
            this.mPendingInvalidate = false;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams) layoutParams);
            this.mDecorInsets = new Rect();
            this.mInsetsDirty = true;
            this.mPendingInvalidate = false;
        }
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.generateLayoutParams(layoutParams);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + exceptionLabel());
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class ItemDecoration {
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            ((LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            rect.set(0, 0, 0, 0);
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
        }

        public void seslOnDispatchDraw(Canvas canvas, RecyclerView recyclerView) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class OnScrollListener {
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        }
    }
}
