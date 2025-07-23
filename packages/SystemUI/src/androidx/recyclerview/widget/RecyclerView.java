package androidx.recyclerview.widget;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EdgeEffect;
import android.widget.ImageView;
import android.widget.OverScroller;
import androidx.appcompat.animation.SeslAnimationUtils;
import androidx.appcompat.animation.SeslRecoilAnimator;
import androidx.appcompat.graphics.drawable.SeslRecoilDrawable;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.util.SeslSubheaderRoundedCorner;
import androidx.collection.SimpleArrayMap;
import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.EdgeEffectCompat;
import androidx.customview.poolingcontainer.PoolingContainer;
import androidx.customview.view.AbsSavedState;
import androidx.leanback.widget.BaseGridView;
import androidx.preference.PreferenceGroupAdapter;
import androidx.recyclerview.R$styleable;
import androidx.recyclerview.widget.AdapterHelper;
import androidx.recyclerview.widget.ChildHelper;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.recyclerview.widget.ViewBoundsCheck;
import androidx.recyclerview.widget.ViewInfoStore;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.view.SeslPointerIconReflector;
import androidx.reflect.view.SeslViewReflector;
import androidx.reflect.widget.SeslOverScrollerReflector;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.container.KnoxContainerManager;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class RecyclerView extends ViewGroup implements NestedScrollingChild2 {
    public static final Class[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE;
    public static final Interpolator LINEAR_INTERPOLATOR;
    public static final StretchEdgeEffectFactory sDefaultEdgeEffectFactory;
    public static final AnonymousClass8 sQuinticInterpolator;
    public RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
    public final AccessibilityManager mAccessibilityManager;
    public Adapter mAdapter;
    public final AdapterHelper mAdapterHelper;
    public boolean mAdapterUpdateDuringMeasure;
    public final AnonymousClass2 mAnimListener;
    public int mAnimatedBlackTop;
    public final AnonymousClass5 mAutoHide;
    public int mBlackTop;
    public EdgeEffect mBottomGlow;
    public final Rect mChildBound;
    public final ChildHelper mChildHelper;
    public boolean mClipToPadding;
    public final int mCloseChildPositionByBottom;
    public final int mCloseChildPositionByTop;
    public final Context mContext;
    public boolean mDataSetHasChangedAfterLayout;
    public boolean mDispatchItemsChangedEvent;
    public int mDispatchScrollCounter;
    public boolean mDrawHorizontalPadding;
    public boolean mDrawLastRoundedCorner;
    public boolean mDrawRect;
    public boolean mDrawReverse;
    public int mEatenAccessibilityChangeFlags;
    public boolean mEdgeEffectByDragging;
    public final StretchEdgeEffectFactory mEdgeEffectFactory;
    public boolean mEnableGoToTop;
    boolean mFirstLayoutComplete;
    public float mFrameLatency;
    public GapWorker mGapWorker;
    public final AnonymousClass4 mGoToToFadeInRunnable;
    public final AnonymousClass3 mGoToToFadeOutRunnable;
    public final int mGoToTopBottomPadding;
    public final int mGoToTopElevation;
    public ValueAnimator mGoToTopFadeInAnimator;
    public ValueAnimator mGoToTopFadeOutAnimator;
    public Drawable mGoToTopImage;
    public int mGoToTopLastState;
    public final Rect mGoToTopRect;
    public final int mGoToTopSize;
    public int mGoToTopState;
    public ImageView mGoToTopView;
    public boolean mHasFixedSize;
    public boolean mHasNestedScrollRange;
    public boolean mHoverAreaEnter;
    public final int mHoverBottomAreaHeight;
    public final AnonymousClass6 mHoverHandler;
    public long mHoverRecognitionDurationTime;
    public long mHoverRecognitionStartTime;
    public final int[] mHoverScrollArrows;
    public int mHoverScrollDirection;
    public final boolean mHoverScrollEnable;
    public int mHoverScrollSpeed;
    public long mHoverScrollStartTime;
    public final long mHoverScrollTimeInterval;
    public final int mHoverTopAreaHeight;
    public boolean mIgnoreMotionEventTillDown;
    public int mInitialTopOffsetOfScreen;
    public int mInterceptRequestLayoutDepth;
    public OnItemTouchListener mInterceptingOnItemTouchListener;
    public boolean mIsArrowKeyPressed;
    public boolean mIsAttached;
    public boolean mIsCloseChildSetted;
    public boolean mIsCtrlKeyPressed;
    public boolean mIsCtrlMultiSelection;
    public boolean mIsEdgeEffectEnabled;
    public boolean mIsFirstPenMoveEvent;
    public boolean mIsHoverOverscrolled;
    public boolean mIsNeedCheckLatency;
    public boolean mIsNeedPenSelectIconSet;
    public boolean mIsNeedPenSelection;
    public final boolean mIsPenDragBlockEnabled;
    public boolean mIsPenHovered;
    public boolean mIsPenPressed;
    public boolean mIsPenSelectPointerSetted;
    public boolean mIsPenSelectionEnabled;
    public final boolean mIsRecoilEnabled;
    public final boolean mIsRecoilSupported;
    public boolean mIsSendHoverScrollState;
    public boolean mIsSetOnlyAddAnim;
    public boolean mIsSetOnlyRemoveAnim;
    public boolean mIsSkipMoveEvent;
    public DefaultItemAnimator mItemAnimator;
    public final SeslRecoilAnimator.Holder mItemAnimatorHolder;
    public final ItemAnimatorRestoreListener mItemAnimatorListener;
    public final AnonymousClass7 mItemAnimatorRunner;
    public final ItemBackgroundHolder mItemBackgroundHolder;
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
    public final int mMaxFlingVelocity;
    public final int mMinFlingVelocity;
    public final int[] mMinMaxLayoutPositions;
    public boolean mNeedsHoverScroll;
    public final int[] mNestedOffsets;
    public int mNestedScrollRange;
    public boolean mNewTextViewHoverState;
    public final RecyclerViewDataObserver mObserver;
    public int mOldHoverScrollDirection;
    public boolean mOldTextViewHoverState;
    public List mOnChildAttachStateListeners;
    public SnapHelper mOnFlingListener;
    public final ArrayList mOnItemTouchListeners;
    public int mPenDistanceFromTrackedChildTop;
    public final Drawable mPenDragBlockImage;
    public int mPenDragBlockLeft;
    public final Rect mPenDragBlockRect;
    public int mPenDragBlockRight;
    public int mPenDragBlockTop;
    public int mPenDragEndY;
    public final long mPenDragScrollTimeInterval;
    public ArrayList mPenDragSelectedItemArray;
    public int mPenDragStartX;
    public int mPenDragStartY;
    public View mPenTrackedChild;
    public int mPenTrackedChildPosition;
    final List<ViewHolder> mPendingAccessibilityImportanceChange;
    public SavedState mPendingSavedState;
    public final float mPhysicalCoef;
    public boolean mPostedAnimatorRunner;
    public GapWorker.LayoutPrefetchRegistryImpl mPrefetchRegistry;
    public boolean mPreserveFocusAfterLayout;
    public boolean mPreventFirstGlow;
    public final int mRectColor;
    public final Paint mRectPaint;
    public final Recycler mRecycler;
    public final List mRecyclerListeners;
    public int mRemainNestedScrollRange;
    public final int[] mReusableIntPair;
    public EdgeEffect mRightGlow;
    public final SeslSubheaderRoundedCorner mRoundedCorner;
    public final float mScaledHorizontalScrollFactor;
    public final float mScaledVerticalScrollFactor;
    public List mScrollListeners;
    public final int[] mScrollOffset;
    public int mScrollPointerId;
    public int mScrollState;
    public int mScrollbarBottomPadding;
    public NestedScrollingChildHelper mScrollingChildHelper;
    public int mSeslOverlayFeatureHeight;
    public int mShowFadeOutGTT;
    public boolean mSizeChange;
    public final State mState;
    public final Rect mTempRect;
    public final Rect mTempRect2;
    public final RectF mTempRectF;
    public EdgeEffect mTopGlow;
    public int mTouchSlop;
    public final AnonymousClass1 mUpdateChildViewsRunnable;
    public VelocityTracker mVelocityTracker;
    public final ViewFlinger mViewFlinger;
    public final AnonymousClass9 mViewInfoProcessCallback;
    public final ViewInfoStore mViewInfoStore;
    public final int[] mWindowOffsets;
    public static final int[] NESTED_SCROLLING_ATTRS = {R.attr.nestedScrollingEnabled};
    public static final float DECELERATION_RATE = (float) (Math.log(0.78d) / Math.log(0.9d));
    public static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC = true;
    public static final boolean POST_UPDATES_ON_ANIMATION = true;
    public static final boolean ALLOW_THREAD_GAP_WORK = true;
    public static final float HOVERSCROLL_SPEED = 10.0f;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.recyclerview.widget.RecyclerView$10, reason: invalid class name */
    public class AnonymousClass10 implements ChildHelper.Callback {
        public AnonymousClass10() {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.recyclerview.widget.RecyclerView$11, reason: invalid class name */
    public class AnonymousClass11 implements AdapterHelper.Callback {
        public AnonymousClass11() {
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
                    if (obj == null) {
                        childViewHolderInt.addFlags(1024);
                    } else if ((1024 & childViewHolderInt.mFlags) == 0) {
                        if (childViewHolderInt.mPayloads == null) {
                            ArrayList arrayList = new ArrayList();
                            childViewHolderInt.mPayloads = arrayList;
                            childViewHolderInt.mUnmodifiedPayloads = Collections.unmodifiableList(arrayList);
                        }
                        ((ArrayList) childViewHolderInt.mPayloads).add(obj);
                    }
                    ((LayoutParams) unfilteredChildAt.getLayoutParams()).mInsetsDirty = true;
                }
            }
            Recycler recycler = recyclerView.mRecycler;
            for (int size = recycler.mCachedViews.size() - 1; size >= 0; size--) {
                ViewHolder viewHolder = (ViewHolder) recycler.mCachedViews.get(size);
                if (viewHolder != null && (i3 = viewHolder.mPosition) >= i && i3 < i5) {
                    viewHolder.addFlags(2);
                    recycler.recycleCachedViewAt(size);
                }
            }
            recyclerView.mItemsChanged = true;
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
            Recycler recycler = recyclerView.mRecycler;
            int size = recycler.mCachedViews.size();
            for (int i4 = 0; i4 < size; i4++) {
                ViewHolder viewHolder = (ViewHolder) recycler.mCachedViews.get(i4);
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
            int size = recycler.mCachedViews.size();
            for (int i12 = 0; i12 < size; i12++) {
                ViewHolder viewHolder = (ViewHolder) recycler.mCachedViews.get(i12);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.recyclerview.widget.RecyclerView$9, reason: invalid class name */
    public class AnonymousClass9 {
        public AnonymousClass9() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Adapter {
        public final AdapterDataObservable mObservable = new AdapterDataObservable();
        public boolean mHasStableIds = false;
        public final StateRestorationPolicy mStateRestorationPolicy = StateRestorationPolicy.ALLOW;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public enum StateRestorationPolicy {
            ALLOW,
            /* JADX INFO: Fake field, exist only in values array */
            PREVENT_WHEN_EMPTY,
            /* JADX INFO: Fake field, exist only in values array */
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

        public final void notifyItemRemoved(int i) {
            this.mObservable.notifyItemRangeRemoved(i, 1);
        }

        public abstract void onBindViewHolder(ViewHolder viewHolder, int i);

        public void onBindViewHolder(ViewHolder viewHolder, int i, List list) {
            onBindViewHolder(viewHolder, i);
        }

        public abstract ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i);

        public boolean onFailedToRecycleView(ViewHolder viewHolder) {
            return false;
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

        public void onDetachedFromRecyclerView() {
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        }

        public void onViewAttachedToWindow(ViewHolder viewHolder) {
        }

        public void onViewRecycled(ViewHolder viewHolder) {
        }

        public int seslGetAccessibilityItemPosition(int i) {
            return i;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AdapterDataObservable extends Observable {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class EdgeEffectFactory {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class ItemAnimator {
        public ItemAnimatorRestoreListener mListener = null;
        public final ArrayList mFinishedListeners = new ArrayList();
        public RecyclerView mHostView = null;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class ItemHolderInfo {
            public int left;
            public int top;
        }

        public static int buildAdapterChangeFlagsForAnimations(ViewHolder viewHolder) {
            int i = viewHolder.mFlags;
            int i2 = i & 14;
            if (viewHolder.isInvalid()) {
                return 4;
            }
            if ((i & 4) == 0) {
                int i3 = viewHolder.mOldPosition;
                int absoluteAdapterPosition = viewHolder.getAbsoluteAdapterPosition();
                if (i3 != -1 && absoluteAdapterPosition != -1 && i3 != absoluteAdapterPosition) {
                    return i2 | 2048;
                }
            }
            return i2;
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x0088  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void dispatchAnimationFinished(androidx.recyclerview.widget.RecyclerView.ViewHolder r9) {
            /*
                r8 = this;
                androidx.recyclerview.widget.RecyclerView$ItemAnimatorRestoreListener r8 = r8.mListener
                if (r8 == 0) goto Lb4
                r0 = 1
                r9.setIsRecyclable(r0)
                androidx.recyclerview.widget.RecyclerView$ViewHolder r1 = r9.mShadowedHolder
                r2 = 0
                if (r1 == 0) goto L13
                androidx.recyclerview.widget.RecyclerView$ViewHolder r1 = r9.mShadowingHolder
                if (r1 != 0) goto L13
                r9.mShadowedHolder = r2
            L13:
                r9.mShadowingHolder = r2
                androidx.recyclerview.widget.RecyclerView r8 = androidx.recyclerview.widget.RecyclerView.this
                java.util.ArrayList r1 = r8.mItemDecorations
                int r2 = r1.size()
                r3 = 0
                r4 = r3
            L1f:
                if (r4 >= r2) goto L33
                java.lang.Object r5 = r1.get(r4)
                int r4 = r4 + 1
                androidx.recyclerview.widget.RecyclerView$ItemDecoration r5 = (androidx.recyclerview.widget.RecyclerView.ItemDecoration) r5
                boolean r6 = r5 instanceof androidx.recyclerview.widget.ItemTouchHelper
                if (r6 == 0) goto L1f
                androidx.recyclerview.widget.ItemTouchHelper r5 = (androidx.recyclerview.widget.ItemTouchHelper) r5
                r5.endRecoverAnimation(r9, r3)
                goto L1f
            L33:
                int r1 = r9.mFlags
                r1 = r1 & 16
                if (r1 == 0) goto L3b
                goto Lb4
            L3b:
                android.view.View r1 = r9.itemView
                r8.startInterceptRequestLayout()
                androidx.recyclerview.widget.ChildHelper r2 = r8.mChildHelper
                androidx.recyclerview.widget.ChildHelper$Bucket r4 = r2.mBucket
                androidx.recyclerview.widget.ChildHelper$Callback r5 = r2.mCallback
                int r6 = r2.mRemoveStatus
                if (r6 != r0) goto L58
                android.view.View r0 = r2.mViewInRemoveView
                if (r0 != r1) goto L50
            L4e:
                r0 = r3
                goto L86
            L50:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "Cannot call removeViewIfHidden within removeView(At) for a different view"
                r8.<init>(r9)
                throw r8
            L58:
                r7 = 2
                if (r6 == r7) goto Lac
                r2.mRemoveStatus = r7     // Catch: java.lang.Throwable -> L6f
                r6 = r5
                androidx.recyclerview.widget.RecyclerView$10 r6 = (androidx.recyclerview.widget.RecyclerView.AnonymousClass10) r6     // Catch: java.lang.Throwable -> L6f
                androidx.recyclerview.widget.RecyclerView r6 = androidx.recyclerview.widget.RecyclerView.this     // Catch: java.lang.Throwable -> L6f
                int r6 = r6.indexOfChild(r1)     // Catch: java.lang.Throwable -> L6f
                r7 = -1
                if (r6 != r7) goto L71
                r2.unhideViewInternal(r1)     // Catch: java.lang.Throwable -> L6f
            L6c:
                r2.mRemoveStatus = r3
                goto L86
            L6f:
                r8 = move-exception
                goto La9
            L71:
                boolean r7 = r4.get(r6)     // Catch: java.lang.Throwable -> L6f
                if (r7 == 0) goto L83
                r4.remove(r6)     // Catch: java.lang.Throwable -> L6f
                r2.unhideViewInternal(r1)     // Catch: java.lang.Throwable -> L6f
                androidx.recyclerview.widget.RecyclerView$10 r5 = (androidx.recyclerview.widget.RecyclerView.AnonymousClass10) r5     // Catch: java.lang.Throwable -> L6f
                r5.removeViewAt(r6)     // Catch: java.lang.Throwable -> L6f
                goto L6c
            L83:
                r2.mRemoveStatus = r3
                goto L4e
            L86:
                if (r0 == 0) goto L96
                androidx.recyclerview.widget.RecyclerView$ViewHolder r1 = androidx.recyclerview.widget.RecyclerView.getChildViewHolderInt(r1)
                androidx.recyclerview.widget.RecyclerView$Recycler r2 = r8.mRecycler
                r2.unscrapView(r1)
                androidx.recyclerview.widget.RecyclerView$Recycler r2 = r8.mRecycler
                r2.recycleViewHolderInternal(r1)
            L96:
                r1 = r0 ^ 1
                r8.stopInterceptRequestLayout(r1)
                if (r0 != 0) goto Lb4
                boolean r0 = r9.isTmpDetached()
                if (r0 == 0) goto Lb4
                android.view.View r9 = r9.itemView
                r8.removeDetachedView(r9, r3)
                return
            La9:
                r2.mRemoveStatus = r3
                throw r8
            Lac:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "Cannot call removeViewIfHidden within removeViewIfHidden"
                r8.<init>(r9)
                throw r8
            Lb4:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.ItemAnimator.dispatchAnimationFinished(androidx.recyclerview.widget.RecyclerView$ViewHolder):void");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ItemAnimatorRestoreListener {
        public ItemAnimatorRestoreListener() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ItemBackgroundHolder {
        public SeslRecoilDrawable mActiveBg = null;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: androidx.recyclerview.widget.RecyclerView$ItemBackgroundHolder$1, reason: invalid class name */
        public class AnonymousClass1 {
            public AnonymousClass1() {
            }
        }

        public ItemBackgroundHolder(RecyclerView recyclerView) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class ItemDecoration {
        @Deprecated
        public void getItemOffsets(Rect rect, int i, RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        @Deprecated
        public void onDraw(Canvas canvas, RecyclerView recyclerView) {
        }

        @Deprecated
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            getItemOffsets(rect, ((LayoutParams) view.getLayoutParams()).mViewHolder.getLayoutPosition(), recyclerView);
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
            onDraw(canvas, recyclerView);
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
            onDrawOver(canvas, recyclerView);
        }

        public void seslOnDispatchDraw(Canvas canvas, RecyclerView recyclerView, State state) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class LayoutManager {
        public boolean mAutoMeasure;
        public ChildHelper mChildHelper;
        public int mHeight;
        public int mHeightMode;
        public final ViewBoundsCheck mHorizontalBoundCheck;
        public final AnonymousClass1 mHorizontalBoundCheckCallback;
        public boolean mIsAttachedToWindow;
        public boolean mItemPrefetchEnabled;
        public final boolean mMeasurementCacheEnabled;
        public int mPrefetchMaxCountObserved;
        public boolean mPrefetchMaxObservedInInitialPrefetch;
        public RecyclerView mRecyclerView;
        public boolean mRequestedSimpleAnimations;
        public SmoothScroller mSmoothScroller;
        public final ViewBoundsCheck mVerticalBoundCheck;
        public final AnonymousClass2 mVerticalBoundCheckCallback;
        public int mWidth;
        public int mWidthMode;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Properties {
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

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0018, code lost:
        
            if (r6 == 1073741824) goto L14;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static int getChildMeasureSpec(boolean r4, int r5, int r6, int r7, int r8) {
            /*
                int r5 = r5 - r7
                r7 = 0
                int r5 = java.lang.Math.max(r7, r5)
                r0 = -2
                r1 = -1
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = 1073741824(0x40000000, float:2.0)
                if (r4 == 0) goto L1d
                if (r8 < 0) goto L12
            L10:
                r6 = r3
                goto L30
            L12:
                if (r8 != r1) goto L1a
                if (r6 == r2) goto L22
                if (r6 == 0) goto L1a
                if (r6 == r3) goto L22
            L1a:
                r6 = r7
                r8 = r6
                goto L30
            L1d:
                if (r8 < 0) goto L20
                goto L10
            L20:
                if (r8 != r1) goto L24
            L22:
                r8 = r5
                goto L30
            L24:
                if (r8 != r0) goto L1a
                if (r6 == r2) goto L2e
                if (r6 != r3) goto L2b
                goto L2e
            L2b:
                r8 = r5
                r6 = r7
                goto L30
            L2e:
                r8 = r5
                r6 = r2
            L30:
                int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r6)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.LayoutManager.getChildMeasureSpec(boolean, int, int, int, int):int");
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
            if (view != null) {
                return ((LayoutParams) view.getLayoutParams()).mViewHolder.getLayoutPosition();
            }
            Log.e("SeslRecyclerView", "View is null.");
            return -1;
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
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                scrapOrRecycleView(recycler, childCount, getChildAt(childCount));
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
            return view.getBottom() + ((LayoutParams) view.getLayoutParams()).mDecorInsets.bottom;
        }

        public void getDecoratedBoundsWithMargins(Rect rect, View view) {
            RecyclerView.getDecoratedBoundsWithMarginsInt(rect, view);
        }

        public int getDecoratedLeft(View view) {
            return view.getLeft() - ((LayoutParams) view.getLayoutParams()).mDecorInsets.left;
        }

        public int getDecoratedRight(View view) {
            return view.getRight() + ((LayoutParams) view.getLayoutParams()).mDecorInsets.right;
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
            if (recyclerView == null) {
                Log.e("SeslRecyclerView", "RecyclerView is null.");
                return 0;
            }
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            return recyclerView.getLayoutDirection();
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

        public final void getTransformedBoundingBox(Rect rect, View view) {
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
            if (accessibilityEvent == null) {
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

        /* JADX WARN: Removed duplicated region for block: B:14:0x008d A[ADDED_TO_REGION] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean performAccessibilityAction(androidx.recyclerview.widget.RecyclerView.Recycler r3, androidx.recyclerview.widget.RecyclerView.State r4, int r5, android.os.Bundle r6) {
            /*
                r2 = this;
                androidx.recyclerview.widget.RecyclerView r3 = r2.mRecyclerView
                r4 = 0
                if (r3 != 0) goto L7
                goto L8f
            L7:
                int r3 = r2.mHeight
                int r6 = r2.mWidth
                android.graphics.Rect r0 = new android.graphics.Rect
                r0.<init>()
                androidx.recyclerview.widget.RecyclerView r1 = r2.mRecyclerView
                android.graphics.Matrix r1 = r1.getMatrix()
                boolean r1 = r1.isIdentity()
                if (r1 == 0) goto L2c
                androidx.recyclerview.widget.RecyclerView r1 = r2.mRecyclerView
                boolean r1 = r1.getGlobalVisibleRect(r0)
                if (r1 == 0) goto L2c
                int r3 = r0.height()
                int r6 = r0.width()
            L2c:
                r0 = 4096(0x1000, float:5.74E-42)
                r1 = 1
                if (r5 == r0) goto L64
                r0 = 8192(0x2000, float:1.148E-41)
                if (r5 == r0) goto L38
                r3 = r4
                r5 = r3
                goto L8b
            L38:
                androidx.recyclerview.widget.RecyclerView r5 = r2.mRecyclerView
                r0 = -1
                boolean r5 = r5.canScrollVertically(r0)
                if (r5 == 0) goto L4d
                int r5 = r2.getPaddingTop()
                int r3 = r3 - r5
                int r5 = r2.getPaddingBottom()
                int r3 = r3 - r5
                int r3 = -r3
                goto L4e
            L4d:
                r3 = r4
            L4e:
                androidx.recyclerview.widget.RecyclerView r5 = r2.mRecyclerView
                boolean r5 = r5.canScrollHorizontally(r0)
                if (r5 == 0) goto L62
                int r5 = r2.getPaddingLeft()
                int r6 = r6 - r5
                int r5 = r2.getPaddingRight()
                int r6 = r6 - r5
                int r5 = -r6
                goto L8b
            L62:
                r5 = r4
                goto L8b
            L64:
                androidx.recyclerview.widget.RecyclerView r5 = r2.mRecyclerView
                boolean r5 = r5.canScrollVertically(r1)
                if (r5 == 0) goto L77
                int r5 = r2.getPaddingTop()
                int r3 = r3 - r5
                int r5 = r2.getPaddingBottom()
                int r3 = r3 - r5
                goto L78
            L77:
                r3 = r4
            L78:
                androidx.recyclerview.widget.RecyclerView r5 = r2.mRecyclerView
                boolean r5 = r5.canScrollHorizontally(r1)
                if (r5 == 0) goto L62
                int r5 = r2.getPaddingLeft()
                int r6 = r6 - r5
                int r5 = r2.getPaddingRight()
                int r5 = r6 - r5
            L8b:
                if (r3 != 0) goto L90
                if (r5 != 0) goto L90
            L8f:
                return r4
            L90:
                androidx.recyclerview.widget.RecyclerView r2 = r2.mRecyclerView
                r2.smoothScrollBy(r5, r3, r1)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.LayoutManager.performAccessibilityAction(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State, int, android.os.Bundle):boolean");
        }

        public void removeAndRecycleAllViews(Recycler recycler) {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                if (!RecyclerView.getChildViewHolderInt(getChildAt(childCount)).shouldIgnore()) {
                    View childAt = getChildAt(childCount);
                    removeViewAt(childCount);
                    recycler.recycleView(childAt);
                }
            }
        }

        public final void removeAndRecycleScrapInt(Recycler recycler) {
            int size = recycler.mAttachedScrap.size();
            for (int i = size - 1; i >= 0; i--) {
                View view = ((ViewHolder) recycler.mAttachedScrap.get(i)).itemView;
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
                if (!childViewHolderInt.shouldIgnore()) {
                    childViewHolderInt.setIsRecyclable(false);
                    if (childViewHolderInt.isTmpDetached()) {
                        this.mRecyclerView.removeDetachedView(view, false);
                    }
                    DefaultItemAnimator defaultItemAnimator = this.mRecyclerView.mItemAnimator;
                    if (defaultItemAnimator != null) {
                        defaultItemAnimator.endAnimation(childViewHolderInt);
                    }
                    childViewHolderInt.setIsRecyclable(true);
                    ViewHolder childViewHolderInt2 = RecyclerView.getChildViewHolderInt(view);
                    childViewHolderInt2.mScrapContainer = null;
                    childViewHolderInt2.mInChangeScrap = false;
                    childViewHolderInt2.mFlags &= -33;
                    recycler.recycleViewHolderInternal(childViewHolderInt2);
                }
            }
            recycler.mAttachedScrap.clear();
            ArrayList arrayList = recycler.mChangedScrap;
            if (arrayList != null) {
                arrayList.clear();
            }
            if (size > 0) {
                this.mRecyclerView.invalidate();
            }
        }

        public final void removeAndRecycleView(View view, Recycler recycler) {
            ChildHelper childHelper = this.mChildHelper;
            ChildHelper.Callback callback = childHelper.mCallback;
            int i = childHelper.mRemoveStatus;
            if (i == 1) {
                throw new IllegalStateException("Cannot call removeView(At) within removeView(At)");
            }
            if (i == 2) {
                throw new IllegalStateException("Cannot call removeView(At) within removeViewIfHidden");
            }
            try {
                childHelper.mRemoveStatus = 1;
                childHelper.mViewInRemoveView = view;
                int indexOfChild = RecyclerView.this.indexOfChild(view);
                if (indexOfChild >= 0) {
                    if (childHelper.mBucket.remove(indexOfChild)) {
                        childHelper.unhideViewInternal(view);
                    }
                    ((AnonymousClass10) callback).removeViewAt(indexOfChild);
                }
                childHelper.mRemoveStatus = 0;
                childHelper.mViewInRemoveView = null;
                recycler.recycleView(view);
            } catch (Throwable th) {
                childHelper.mRemoveStatus = 0;
                childHelper.mViewInRemoveView = null;
                throw th;
            }
        }

        public final void removeViewAt(int i) {
            if (getChildAt(i) != null) {
                ChildHelper childHelper = this.mChildHelper;
                ChildHelper.Callback callback = childHelper.mCallback;
                int i2 = childHelper.mRemoveStatus;
                if (i2 == 1) {
                    throw new IllegalStateException("Cannot call removeView(At) within removeView(At)");
                }
                if (i2 == 2) {
                    throw new IllegalStateException("Cannot call removeView(At) within removeViewIfHidden");
                }
                try {
                    int offset = childHelper.getOffset(i);
                    View childAt = RecyclerView.this.getChildAt(offset);
                    if (childAt != null) {
                        childHelper.mRemoveStatus = 1;
                        childHelper.mViewInRemoveView = childAt;
                        if (childHelper.mBucket.remove(offset)) {
                            childHelper.unhideViewInternal(childAt);
                        }
                        ((AnonymousClass10) callback).removeViewAt(offset);
                    }
                    childHelper.mRemoveStatus = 0;
                    childHelper.mViewInRemoveView = null;
                } catch (Throwable th) {
                    childHelper.mRemoveStatus = 0;
                    childHelper.mViewInRemoveView = null;
                    throw th;
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x00ab, code lost:
        
            if ((r5.bottom - r10) > r2) goto L28;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean requestChildRectangleOnScreen(androidx.recyclerview.widget.RecyclerView r9, android.view.View r10, android.graphics.Rect r11, boolean r12, boolean r13) {
            /*
                r8 = this;
                int r0 = r8.getPaddingLeft()
                int r1 = r8.getPaddingTop()
                int r2 = r8.mWidth
                int r3 = r8.getPaddingRight()
                int r2 = r2 - r3
                int r3 = r8.mHeight
                int r4 = r8.getPaddingBottom()
                int r3 = r3 - r4
                int r4 = r10.getLeft()
                int r5 = r11.left
                int r4 = r4 + r5
                int r5 = r10.getScrollX()
                int r4 = r4 - r5
                int r5 = r10.getTop()
                int r6 = r11.top
                int r5 = r5 + r6
                int r10 = r10.getScrollY()
                int r5 = r5 - r10
                int r10 = r11.width()
                int r10 = r10 + r4
                int r11 = r11.height()
                int r11 = r11 + r5
                int r4 = r4 - r0
                r0 = 0
                int r6 = java.lang.Math.min(r0, r4)
                int r5 = r5 - r1
                int r1 = java.lang.Math.min(r0, r5)
                int r10 = r10 - r2
                int r2 = java.lang.Math.max(r0, r10)
                int r11 = r11 - r3
                int r11 = java.lang.Math.max(r0, r11)
                int r3 = r8.getLayoutDirection()
                r7 = 1
                if (r3 != r7) goto L5c
                if (r2 == 0) goto L57
                goto L64
            L57:
                int r2 = java.lang.Math.max(r6, r10)
                goto L64
            L5c:
                if (r6 == 0) goto L5f
                goto L63
            L5f:
                int r6 = java.lang.Math.min(r4, r2)
            L63:
                r2 = r6
            L64:
                if (r1 == 0) goto L67
                goto L6b
            L67:
                int r1 = java.lang.Math.min(r5, r11)
            L6b:
                int[] r10 = new int[]{r2, r1}
                r11 = r10[r0]
                r10 = r10[r7]
                if (r13 == 0) goto Lae
                android.view.View r13 = r9.getFocusedChild()
                if (r13 != 0) goto L7c
                goto Lb3
            L7c:
                int r1 = r8.getPaddingLeft()
                int r2 = r8.getPaddingTop()
                int r3 = r8.mWidth
                int r4 = r8.getPaddingRight()
                int r3 = r3 - r4
                int r4 = r8.mHeight
                int r5 = r8.getPaddingBottom()
                int r4 = r4 - r5
                androidx.recyclerview.widget.RecyclerView r5 = r8.mRecyclerView
                android.graphics.Rect r5 = r5.mTempRect
                r8.getDecoratedBoundsWithMargins(r5, r13)
                int r8 = r5.left
                int r8 = r8 - r11
                if (r8 >= r3) goto Lb3
                int r8 = r5.right
                int r8 = r8 - r11
                if (r8 <= r1) goto Lb3
                int r8 = r5.top
                int r8 = r8 - r10
                if (r8 >= r4) goto Lb3
                int r8 = r5.bottom
                int r8 = r8 - r10
                if (r8 > r2) goto Lae
                goto Lb3
            Lae:
                if (r11 != 0) goto Lb4
                if (r10 == 0) goto Lb3
                goto Lb4
            Lb3:
                return r0
            Lb4:
                if (r12 == 0) goto Lba
                r9.scrollBy(r11, r10)
                return r7
            Lba:
                r9.smoothScrollBy(r11, r10)
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.LayoutManager.requestChildRectangleOnScreen(androidx.recyclerview.widget.RecyclerView, android.view.View, android.graphics.Rect, boolean, boolean):boolean");
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

        public void scrollToPosition(int i) {
            int[] iArr = RecyclerView.NESTED_SCROLLING_ATTRS;
        }

        public int scrollVerticallyBy(int i, Recycler recycler, State state) {
            return 0;
        }

        public final void setExactMeasureSpecsFrom(RecyclerView recyclerView) {
            setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 1073741824));
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
            this.mRecyclerView.setMeasuredDimension(chooseSize(i, paddingRight, recyclerView.getMinimumWidth()), chooseSize(i2, paddingBottom, this.mRecyclerView.getMinimumHeight()));
        }

        public final void setMeasuredDimensionFromChildren(int i, int i2) {
            int childCount = getChildCount();
            if (childCount == 0) {
                this.mRecyclerView.defaultOnMeasure(i, i2);
                return;
            }
            int i3 = Integer.MIN_VALUE;
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

        public final void setRecyclerView(RecyclerView recyclerView) {
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
            recyclerView.mViewFlinger.stop();
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
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, position, 1, position2, 1));
        }

        public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
            return new LayoutParams(context, attributeSet);
        }

        public void onItemsChanged() {
        }

        public void onAdapterChanged(Adapter adapter) {
        }

        public void onAttachedToWindow(RecyclerView recyclerView) {
        }

        public void onDetachedFromWindow(RecyclerView recyclerView) {
        }

        public void onLayoutCompleted(State state) {
        }

        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        public void onScrollStateChanged(int i) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnChildAttachStateChangeListener {
        void onChildViewAttachedToWindow(View view);

        void onChildViewDetachedFromWindow(View view);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class OnFlingListener {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnItemTouchListener {
        boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent);

        void onRequestDisallowInterceptTouchEvent(boolean z);

        void onTouchEvent(MotionEvent motionEvent);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RecycledViewPool {
        public final SparseArray mScrap = new SparseArray();
        public int mAttachCountForClearing = 0;
        public final Set mAttachedAdaptersForPoolingContainer = Collections.newSetFromMap(new IdentityHashMap());

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class ScrapData {
            public final ArrayList mScrapHeap = new ArrayList();
            public final int mMaxScrap = 5;
            public long mCreateRunningAverageNs = 0;
            public long mBindRunningAverageNs = 0;
        }

        public final ScrapData getScrapDataForType(int i) {
            ScrapData scrapData = (ScrapData) this.mScrap.get(i);
            if (scrapData != null) {
                return scrapData;
            }
            ScrapData scrapData2 = new ScrapData();
            this.mScrap.put(i, scrapData2);
            return scrapData2;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            View view = viewHolder.itemView;
            RecyclerView recyclerView = RecyclerView.this;
            RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate = recyclerView.mAccessibilityDelegate;
            if (recyclerViewAccessibilityDelegate != null) {
                AccessibilityDelegateCompat itemDelegate = recyclerViewAccessibilityDelegate.getItemDelegate();
                ViewCompat.setAccessibilityDelegate(view, itemDelegate instanceof RecyclerViewAccessibilityDelegate.ItemDelegate ? (AccessibilityDelegateCompat) ((WeakHashMap) ((RecyclerViewAccessibilityDelegate.ItemDelegate) itemDelegate).mOriginalItemDelegates).remove(view) : null);
            }
            if (z) {
                int size = ((ArrayList) recyclerView.mRecyclerListeners).size();
                for (int i = 0; i < size; i++) {
                    androidx.leanback.widget.GridLayoutManager gridLayoutManager = BaseGridView.this.mLayoutManager;
                    gridLayoutManager.getClass();
                    if (viewHolder.getAbsoluteAdapterPosition() != -1) {
                        gridLayoutManager.mChildrenStates.getClass();
                    }
                }
                Adapter adapter = recyclerView.mAdapter;
                if (adapter != null) {
                    adapter.onViewRecycled(viewHolder);
                }
                if (recyclerView.mState != null) {
                    recyclerView.mViewInfoStore.removeViewHolder(viewHolder);
                }
                int[] iArr = RecyclerView.NESTED_SCROLLING_ATTRS;
            }
            viewHolder.mBindingAdapter = null;
            viewHolder.mOwnerRecyclerView = null;
            RecycledViewPool recycledViewPool = getRecycledViewPool();
            recycledViewPool.getClass();
            int i2 = viewHolder.mItemViewType;
            ArrayList arrayList = recycledViewPool.getScrapDataForType(i2).mScrapHeap;
            if (((RecycledViewPool.ScrapData) recycledViewPool.mScrap.get(i2)).mMaxScrap <= arrayList.size()) {
                PoolingContainer.callPoolingContainerOnRelease(viewHolder.itemView);
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
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "invalid position ", ". State item count is ");
            m.append(recyclerView.mState.getItemCount());
            m.append(recyclerView.exceptionLabel());
            throw new IndexOutOfBoundsException(m.toString());
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
            RecyclerView recyclerView;
            Adapter adapter;
            RecycledViewPool recycledViewPool = this.mRecyclerPool;
            if (recycledViewPool == null || (adapter = (recyclerView = RecyclerView.this).mAdapter) == null || !recyclerView.mIsAttached) {
                return;
            }
            recycledViewPool.mAttachedAdaptersForPoolingContainer.add(adapter);
        }

        public final void poolingContainerDetach(Adapter adapter, boolean z) {
            RecycledViewPool recycledViewPool = this.mRecyclerPool;
            if (recycledViewPool != null) {
                recycledViewPool.mAttachedAdaptersForPoolingContainer.remove(adapter);
                if (recycledViewPool.mAttachedAdaptersForPoolingContainer.size() != 0 || z) {
                    return;
                }
                for (int i = 0; i < recycledViewPool.mScrap.size(); i++) {
                    SparseArray sparseArray = recycledViewPool.mScrap;
                    RecycledViewPool.ScrapData scrapData = (RecycledViewPool.ScrapData) sparseArray.get(sparseArray.keyAt(i));
                    if (scrapData != null) {
                        ArrayList arrayList = scrapData.mScrapHeap;
                        for (int i2 = 0; i2 < arrayList.size(); i2++) {
                            if (arrayList.get(i2) != null) {
                                View view = ((ViewHolder) arrayList.get(i2)).itemView;
                                PoolingContainer.callPoolingContainerOnRelease(((ViewHolder) arrayList.get(i2)).itemView);
                            }
                        }
                    }
                }
            }
        }

        public final void recycleAndClearCachedViews() {
            for (int size = this.mCachedViews.size() - 1; size >= 0; size--) {
                recycleCachedViewAt(size);
            }
            this.mCachedViews.clear();
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
            int[] iArr = RecyclerView.NESTED_SCROLLING_ATTRS;
            addViewHolderToRecycledViewPool((ViewHolder) this.mCachedViews.get(i), true);
            this.mCachedViews.remove(i);
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

        /* JADX WARN: Code restructure failed: missing block: B:65:0x00ab, code lost:
        
            r4 = r4 - 1;
         */
        /* JADX WARN: Removed duplicated region for block: B:72:0x00bc  */
        /* JADX WARN: Removed duplicated region for block: B:74:0x00c1  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void recycleViewHolderInternal(androidx.recyclerview.widget.RecyclerView.ViewHolder r11) {
            /*
                Method dump skipped, instructions count: 316
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.Recycler.recycleViewHolderInternal(androidx.recyclerview.widget.RecyclerView$ViewHolder):void");
        }

        public final void scrapView(View view) {
            DefaultItemAnimator defaultItemAnimator;
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            boolean z = (childViewHolderInt.mFlags & 12) != 0;
            RecyclerView recyclerView = RecyclerView.this;
            if (!z && childViewHolderInt.isUpdated() && (defaultItemAnimator = recyclerView.mItemAnimator) != null && childViewHolderInt.getUnmodifiedPayloads().isEmpty()) {
                if (!(!defaultItemAnimator.mSupportsChangeAnimations || childViewHolderInt.isInvalid())) {
                    if (this.mChangedScrap == null) {
                        this.mChangedScrap = new ArrayList();
                    }
                    childViewHolderInt.mScrapContainer = this;
                    childViewHolderInt.mInChangeScrap = true;
                    this.mChangedScrap.add(childViewHolderInt);
                    return;
                }
            }
            if (childViewHolderInt.isInvalid() && !childViewHolderInt.isRemoved() && !recyclerView.mAdapter.mHasStableIds) {
                throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool." + recyclerView.exceptionLabel());
            }
            childViewHolderInt.mScrapContainer = this;
            childViewHolderInt.mInChangeScrap = false;
            this.mAttachedScrap.add(childViewHolderInt);
        }

        /* JADX WARN: Code restructure failed: missing block: B:247:0x049c, code lost:
        
            if (r10.isInvalid() == false) goto L247;
         */
        /* JADX WARN: Code restructure failed: missing block: B:254:0x04c9, code lost:
        
            if ((r3 + r11) >= r27) goto L317;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0080  */
        /* JADX WARN: Removed duplicated region for block: B:228:0x05dd  */
        /* JADX WARN: Removed duplicated region for block: B:236:0x05e9  */
        /* JADX WARN: Removed duplicated region for block: B:341:0x0082  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final androidx.recyclerview.widget.RecyclerView.ViewHolder tryGetViewHolderForPositionByDeadline(int r26, long r27) {
            /*
                Method dump skipped, instructions count: 1582
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.Recycler.tryGetViewHolderForPositionByDeadline(int, long):androidx.recyclerview.widget.RecyclerView$ViewHolder");
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
            for (int size = this.mCachedViews.size() - 1; size >= 0 && this.mCachedViews.size() > this.mViewCacheMax; size--) {
                recycleCachedViewAt(size);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RecyclerViewDataObserver extends AdapterDataObserver {
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
            recyclerView.getClass();
            recyclerView.getClass();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeChanged(int i, int i2, Object obj) {
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.assertNotInLayoutOrScroll(null);
            AdapterHelper adapterHelper = recyclerView.mAdapterHelper;
            if (i2 < 1) {
                adapterHelper.getClass();
                return;
            }
            adapterHelper.mPendingUpdates.add(adapterHelper.obtainUpdateOp(4, i, i2, obj));
            adapterHelper.mExistingUpdateTypes |= 4;
            if (adapterHelper.mPendingUpdates.size() == 1) {
                triggerUpdateProcessor();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeInserted(int i, int i2) {
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.assertNotInLayoutOrScroll(null);
            AdapterHelper adapterHelper = recyclerView.mAdapterHelper;
            if (i2 < 1) {
                adapterHelper.getClass();
                return;
            }
            adapterHelper.mPendingUpdates.add(adapterHelper.obtainUpdateOp(1, i, i2, null));
            adapterHelper.mExistingUpdateTypes |= 1;
            if (adapterHelper.mPendingUpdates.size() == 1) {
                triggerUpdateProcessor();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeMoved(int i, int i2) {
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.assertNotInLayoutOrScroll(null);
            AdapterHelper adapterHelper = recyclerView.mAdapterHelper;
            adapterHelper.getClass();
            if (i == i2) {
                return;
            }
            adapterHelper.mPendingUpdates.add(adapterHelper.obtainUpdateOp(8, i, i2, null));
            adapterHelper.mExistingUpdateTypes |= 8;
            if (adapterHelper.mPendingUpdates.size() == 1) {
                triggerUpdateProcessor();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeRemoved(int i, int i2) {
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.assertNotInLayoutOrScroll(null);
            AdapterHelper adapterHelper = recyclerView.mAdapterHelper;
            if (i2 < 1) {
                adapterHelper.getClass();
                return;
            }
            adapterHelper.mPendingUpdates.add(adapterHelper.obtainUpdateOp(2, i, i2, null));
            adapterHelper.mExistingUpdateTypes |= 2;
            if (adapterHelper.mPendingUpdates.size() == 1) {
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
                AnonymousClass1 anonymousClass1 = recyclerView.mUpdateChildViewsRunnable;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                recyclerView.postOnAnimation(anonymousClass1);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum ScrollArrowDirection {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class SmoothScroller {
        public LayoutManager mLayoutManager;
        public boolean mPendingInitialRun;
        public RecyclerView mRecyclerView;
        public boolean mRunning;
        public boolean mStarted;
        public View mTargetView;
        public int mTargetPosition = -1;
        public final Action mRecyclingAction = new Action(0, 0);

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Action {
            public boolean mChanged;
            public int mConsecutiveUpdates;
            public int mDuration;
            public int mDx;
            public int mDy;
            public Interpolator mInterpolator;
            public int mJumpToPosition;

            public Action(int i, int i2) {
                this(i, i2, Integer.MIN_VALUE, null);
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class State {
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
            StringBuilder sb = new StringBuilder("State{mTargetPosition=");
            sb.append(this.mTargetPosition);
            sb.append(", mData=null, mItemCount=");
            sb.append(this.mItemCount);
            sb.append(", mIsMeasuring=");
            sb.append(this.mIsMeasuring);
            sb.append(", mPreviousLayoutItemCount=");
            sb.append(this.mPreviousLayoutItemCount);
            sb.append(", mDeletedInvisibleItemCountSincePreviousLayout=");
            sb.append(this.mDeletedInvisibleItemCountSincePreviousLayout);
            sb.append(", mStructureChanged=");
            sb.append(this.mStructureChanged);
            sb.append(", mInPreLayout=");
            sb.append(this.mInPreLayout);
            sb.append(", mRunSimpleAnimations=");
            sb.append(this.mRunSimpleAnimations);
            sb.append(", mRunPredictiveAnimations=");
            return BoundsAnimationElement$$ExternalSyntheticOutline0.m(sb, this.mRunPredictiveAnimations, '}');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class StretchEdgeEffectFactory extends EdgeEffectFactory {
        public final EdgeEffect createEdgeEffect(RecyclerView recyclerView) {
            return new EdgeEffect(recyclerView.getContext());
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ViewFlinger implements Runnable {
        public boolean mEatRunOnAnimationRequest;
        public Interpolator mInterpolator;
        public int mLastFlingX;
        public int mLastFlingY;
        public OverScroller mOverScroller;
        public boolean mReSchedulePostAnimationCallback;

        public ViewFlinger() {
            AnonymousClass8 anonymousClass8 = RecyclerView.sQuinticInterpolator;
            this.mInterpolator = anonymousClass8;
            this.mEatRunOnAnimationRequest = false;
            this.mReSchedulePostAnimationCallback = false;
            this.mOverScroller = new OverScroller(RecyclerView.this.getContext(), anonymousClass8);
        }

        public final void fling(int i, int i2) {
            RecyclerView.this.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            Interpolator interpolator = this.mInterpolator;
            AnonymousClass8 anonymousClass8 = RecyclerView.sQuinticInterpolator;
            if (interpolator != anonymousClass8) {
                this.mInterpolator = anonymousClass8;
                this.mOverScroller = new OverScroller(RecyclerView.this.getContext(), anonymousClass8);
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
                overScroller.fling(0, 0, i, i2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
            recyclerView.postOnAnimation(this);
        }

        @Override // java.lang.Runnable
        public final void run() {
            int i;
            int i2;
            RecyclerView recyclerView = RecyclerView.this;
            if (recyclerView.mLayout == null) {
                stop();
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
                    recyclerView5.adjustNestedScrollRangeBy$1(i5);
                } else {
                    RecyclerView.this.adjustNestedScrollRangeBy$1(consumeFlingInStretch2);
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
                    int i6 = iArr4[0];
                    int i7 = iArr4[1];
                    consumeFlingInStretch -= i6;
                    consumeFlingInStretch2 -= i7;
                    SmoothScroller smoothScroller = recyclerView7.mLayout.mSmoothScroller;
                    if (smoothScroller != null && !smoothScroller.mPendingInitialRun && smoothScroller.mRunning) {
                        int itemCount = recyclerView7.mState.getItemCount();
                        if (itemCount == 0) {
                            smoothScroller.stop();
                        } else if (smoothScroller.mTargetPosition >= itemCount) {
                            smoothScroller.mTargetPosition = itemCount - 1;
                            smoothScroller.onAnimation(i6, i7);
                        } else {
                            smoothScroller.onAnimation(i6, i7);
                        }
                    }
                    i2 = i7;
                    i = i6;
                } else {
                    i = 0;
                    i2 = 0;
                }
                int i8 = consumeFlingInStretch;
                int i9 = consumeFlingInStretch2;
                if (!RecyclerView.this.mItemDecorations.isEmpty()) {
                    RecyclerView.this.invalidate();
                }
                RecyclerView recyclerView8 = RecyclerView.this;
                int[] iArr5 = recyclerView8.mReusableIntPair;
                iArr5[0] = 0;
                iArr5[1] = 0;
                if (recyclerView8.getScrollingChildHelper().dispatchNestedScrollInternal(i, i2, i8, i9, null, 1, iArr5)) {
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
                int i10 = i8 - iArr8[0];
                int i11 = i9 - iArr8[1];
                if (i != 0 || i2 != 0) {
                    recyclerView9.dispatchOnScrolled(i, i2);
                }
                if (!RecyclerView.this.awakenScrollBars()) {
                    RecyclerView.this.invalidate();
                }
                boolean z = overScroller.isFinished() || (((overScroller.getCurrX() == overScroller.getFinalX()) || i10 != 0) && ((overScroller.getCurrY() == overScroller.getFinalY()) || i11 != 0));
                RecyclerView recyclerView10 = RecyclerView.this;
                SmoothScroller smoothScroller2 = recyclerView10.mLayout.mSmoothScroller;
                if ((smoothScroller2 == null || !smoothScroller2.mPendingInitialRun) && z) {
                    if (recyclerView10.getOverScrollMode() != 2 && !RecyclerView.this.mEdgeEffectByDragging) {
                        int currVelocity = (int) overScroller.getCurrVelocity();
                        int i12 = i10 < 0 ? -currVelocity : i10 > 0 ? currVelocity : 0;
                        if (i11 < 0) {
                            currVelocity = -currVelocity;
                        } else if (i11 <= 0) {
                            currVelocity = 0;
                        }
                        RecyclerView recyclerView11 = RecyclerView.this;
                        recyclerView11.getClass();
                        if (i12 < 0) {
                            recyclerView11.ensureLeftGlow();
                            if (recyclerView11.mLeftGlow.isFinished()) {
                                recyclerView11.mLeftGlow.onAbsorb(-i12);
                            }
                        } else if (i12 > 0) {
                            recyclerView11.ensureRightGlow();
                            if (recyclerView11.mRightGlow.isFinished()) {
                                recyclerView11.mRightGlow.onAbsorb(i12);
                            }
                        }
                        if (currVelocity < 0) {
                            recyclerView11.ensureTopGlow();
                            if (recyclerView11.mTopGlow.isFinished()) {
                                recyclerView11.mTopGlow.onAbsorb(-currVelocity);
                            }
                        } else if (currVelocity > 0) {
                            recyclerView11.ensureBottomGlow();
                            if (recyclerView11.mBottomGlow.isFinished()) {
                                recyclerView11.mBottomGlow.onAbsorb(currVelocity);
                            }
                        }
                        if (i12 != 0 || currVelocity != 0) {
                            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                            recyclerView11.postInvalidateOnAnimation();
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
                } else {
                    postOnAnimation();
                    RecyclerView recyclerView12 = RecyclerView.this;
                    GapWorker gapWorker = recyclerView12.mGapWorker;
                    if (gapWorker != null) {
                        gapWorker.postFromTraversal(recyclerView12, i, i2);
                    }
                }
                SeslViewReflector.setFrameContentVelocity(Math.abs(overScroller.getCurrVelocity()), RecyclerView.this);
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
                recyclerView13.postOnAnimation(this);
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
            RecyclerView.this.adjustNestedScrollRangeBy$1(i2);
        }

        public final void stop() {
            RecyclerView.this.removeCallbacks(this);
            this.mOverScroller.abortAnimation();
            SeslViewReflector.setFrameContentVelocity(0.0f, RecyclerView.this);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class ViewHolder {
        public static final List FULLUPDATE_PAYLOADS = Collections.EMPTY_LIST;
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
        public boolean mIsViewHolderRecoilEffectEnabled = true;

        public ViewHolder(View view) {
            if (view == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = view;
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
            if ((this.mFlags & 1024) != 0) {
                return FULLUPDATE_PAYLOADS;
            }
            List list = this.mPayloads;
            return (list == null || ((ArrayList) list).size() == 0) ? FULLUPDATE_PAYLOADS : this.mUnmodifiedPayloads;
        }

        public final boolean isAttachedToTransitionOverlay() {
            return (this.itemView.getParent() == null || this.itemView.getParent() == this.mOwnerRecyclerView) ? false : true;
        }

        public final boolean isBound() {
            return (this.mFlags & 1) != 0;
        }

        public final boolean isInvalid() {
            return (this.mFlags & 4) != 0;
        }

        public final boolean isRecyclable() {
            if ((this.mFlags & 16) != 0) {
                return false;
            }
            View view = this.itemView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            return !view.hasTransientState();
        }

        public final boolean isRemoved() {
            return (this.mFlags & 8) != 0;
        }

        public final boolean isScrap() {
            return this.mScrapContainer != null;
        }

        public final boolean isTmpDetached() {
            return (this.mFlags & 256) != 0;
        }

        public final boolean isUpdated() {
            return (this.mFlags & 2) != 0;
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
            if (this.itemView.getLayoutParams() != null) {
                ((LayoutParams) this.itemView.getLayoutParams()).mInsetsDirty = true;
            }
        }

        public final void resetInternal() {
            int[] iArr = RecyclerView.NESTED_SCROLLING_ATTRS;
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
                int[] iArr = RecyclerView.NESTED_SCROLLING_ATTRS;
                Log.e("View", "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
            } else if (!z && i2 == 1) {
                this.mFlags |= 16;
            } else if (z && i2 == 0) {
                this.mFlags &= -17;
            }
            int[] iArr2 = RecyclerView.NESTED_SCROLLING_ATTRS;
        }

        public final boolean shouldIgnore() {
            return (this.mFlags & 128) != 0;
        }

        public final String toString() {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(getClass().isAnonymousClass() ? "ViewHolder" : getClass().getSimpleName(), "{");
            m.append(Integer.toHexString(hashCode()));
            m.append(" position=");
            m.append(this.mPosition);
            m.append(" id=");
            m.append(this.mItemId);
            m.append(", oldPos=");
            m.append(this.mOldPosition);
            m.append(", pLpos:");
            m.append(this.mPreLayoutPosition);
            StringBuilder sb = new StringBuilder(m.toString());
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
            if ((this.mFlags & 512) != 0 || isInvalid()) {
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

    /* JADX WARN: Type inference failed for: r0v9, types: [androidx.recyclerview.widget.RecyclerView$8] */
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

    public static void getDecoratedBoundsWithMarginsInt(Rect rect, View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect2 = layoutParams.mDecorInsets;
        rect.set((view.getLeft() - rect2.left) - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, (view.getTop() - rect2.top) - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, view.getRight() + rect2.right + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, view.getBottom() + rect2.bottom + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
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

    public final void adjustNestedScrollRange$1() {
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

    public final void adjustNestedScrollRangeBy$1(int i) {
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

    public final void assertNotInLayoutOrScroll(String str) {
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
            if (i == 0) {
                removeCallbacks(this.mAutoHide);
                postDelayed(this.mAutoHide, 1500L);
            } else if (i == 1) {
                removeCallbacks(this.mAutoHide);
                postDelayed(this.mAutoHide, 1500L);
            }
        }
    }

    public final boolean canScrollDown$1() {
        boolean z;
        boolean z2;
        int childCount = getChildCount();
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            z = layoutManager.canScrollHorizontally();
            z2 = this.mLayout.getLayoutDirection() == 1;
        } else {
            z = false;
            z2 = false;
        }
        LayoutManager layoutManager2 = this.mLayout;
        boolean z3 = layoutManager2 instanceof LinearLayoutManager ? ((LinearLayoutManager) layoutManager2).mReverseLayout : false;
        if (this.mAdapter == null) {
            Log.e("SeslRecyclerView", "No adapter attached; skipping canScrollDown");
            return false;
        }
        boolean z4 = !z3 ? findFirstChildPosition() + childCount >= this.mAdapter.getItemCount() : findFirstChildPosition() <= 0;
        if (z4 || childCount <= 0) {
            return z4;
        }
        getDecoratedBoundsWithMarginsInt(this.mChildBound, getChildAt(z3 ? 0 : childCount - 1));
        return !z ? !(this.mChildBound.bottom > getBottom() - this.mListPadding.bottom || this.mChildBound.bottom > getHeight() - this.mListPadding.bottom) : !(!z2 ? !(this.mChildBound.right > getRight() - this.mListPadding.right || this.mChildBound.right > getWidth() - this.mListPadding.right) : this.mChildBound.left >= this.mListPadding.left);
    }

    public final boolean canScrollUp$1() {
        boolean z;
        boolean z2;
        int childCount = getChildCount();
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            z = layoutManager.canScrollHorizontally();
            z2 = this.mLayout.getLayoutDirection() == 1;
        } else {
            z = false;
            z2 = false;
        }
        LayoutManager layoutManager2 = this.mLayout;
        boolean z3 = layoutManager2 instanceof LinearLayoutManager ? ((LinearLayoutManager) layoutManager2).mReverseLayout : false;
        boolean z4 = !z3 ? findFirstChildPosition() <= 0 : findFirstChildPosition() + childCount >= this.mAdapter.getItemCount();
        if (z4 || childCount <= 0) {
            return z4;
        }
        getDecoratedBoundsWithMarginsInt(this.mChildBound, getChildAt(z3 ? childCount - 1 : 0));
        return !z ? this.mChildBound.top >= this.mListPadding.top : !z2 ? this.mChildBound.left < this.mListPadding.left : !(this.mChildBound.right <= getRight() - this.mListPadding.right && this.mChildBound.right <= getWidth() - this.mListPadding.right);
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && this.mLayout.checkLayoutParams((LayoutParams) layoutParams);
    }

    public final void clearOldPositions() {
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < unfilteredChildCount; i++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (!childViewHolderInt.shouldIgnore()) {
                childViewHolderInt.mOldPosition = -1;
                childViewHolderInt.mPreLayoutPosition = -1;
            }
        }
        Recycler recycler = this.mRecycler;
        int size = recycler.mCachedViews.size();
        for (int i2 = 0; i2 < size; i2++) {
            ViewHolder viewHolder = (ViewHolder) recycler.mCachedViews.get(i2);
            viewHolder.mOldPosition = -1;
            viewHolder.mPreLayoutPosition = -1;
        }
        int size2 = recycler.mAttachedScrap.size();
        for (int i3 = 0; i3 < size2; i3++) {
            ViewHolder viewHolder2 = (ViewHolder) recycler.mAttachedScrap.get(i3);
            viewHolder2.mOldPosition = -1;
            viewHolder2.mPreLayoutPosition = -1;
        }
        ArrayList arrayList = recycler.mChangedScrap;
        if (arrayList != null) {
            int size3 = arrayList.size();
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

    public final void considerReleasingGlowsOnScroll(int i, int i2) {
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
            postInvalidateOnAnimation();
        }
    }

    public final void consumePendingUpdateOperations() {
        if (!this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout) {
            Trace.beginSection("RV FullInvalidate");
            dispatchLayout();
            Trace.endSection();
            return;
        }
        if (this.mAdapterHelper.hasPendingUpdates()) {
            AdapterHelper adapterHelper = this.mAdapterHelper;
            int i = adapterHelper.mExistingUpdateTypes;
            if ((i & 4) == 0 || (i & 11) != 0) {
                if (adapterHelper.hasPendingUpdates()) {
                    Trace.beginSection("RV FullInvalidate");
                    dispatchLayout();
                    Trace.endSection();
                    return;
                }
                return;
            }
            Trace.beginSection("RV PartialInvalidate");
            startInterceptRequestLayout();
            onEnterLayoutOrScroll();
            this.mAdapterHelper.preProcess();
            if (!this.mLayoutWasDefered) {
                int childCount = this.mChildHelper.getChildCount();
                int i2 = 0;
                while (true) {
                    if (i2 < childCount) {
                        ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(i2));
                        if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore() && childViewHolderInt.isUpdated()) {
                            dispatchLayout();
                            break;
                        }
                        i2++;
                    } else {
                        this.mAdapterHelper.consumePostponedUpdates();
                        break;
                    }
                }
            }
            stopInterceptRequestLayout(true);
            onExitLayoutOrScroll(true);
            Trace.endSection();
        }
    }

    public final void defaultOnMeasure(int i, int i2) {
        int paddingRight = getPaddingRight() + getPaddingLeft();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setMeasuredDimension(LayoutManager.chooseSize(i, paddingRight, getMinimumWidth()), LayoutManager.chooseSize(i2, getPaddingBottom() + getPaddingTop(), getMinimumHeight()));
    }

    public final void dispatchChildDetached(View view) {
        getChildViewHolderInt(view);
        List list = this.mOnChildAttachStateListeners;
        if (list != null) {
            for (int size = ((ArrayList) list).size() - 1; size >= 0; size--) {
                ((OnChildAttachStateChangeListener) ((ArrayList) this.mOnChildAttachStateListeners).get(size)).onChildViewDetachedFromWindow(view);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        View childAt;
        DefaultItemAnimator defaultItemAnimator;
        super.dispatchDraw(canvas);
        int size = this.mItemDecorations.size();
        for (int i = 0; i < size; i++) {
            ((ItemDecoration) this.mItemDecorations.get(i)).seslOnDispatchDraw(canvas, this, this.mState);
        }
        int width = getWidth();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        if (this.mDrawRect && ((this.mBlackTop != -1 || this.mLastBlackTop != -1) && !canScrollVertically(-1) && (!canScrollVertically(1) || ((defaultItemAnimator = this.mItemAnimator) != null && defaultItemAnimator.isRunning())))) {
            ValueAnimator valueAnimator = this.mLastItemAddRemoveAnim;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                this.mAnimatedBlackTop = this.mBlackTop;
            }
            DefaultItemAnimator defaultItemAnimator2 = this.mItemAnimator;
            if (defaultItemAnimator2 != null && defaultItemAnimator2.isRunning()) {
                DefaultItemAnimator defaultItemAnimator3 = this.mItemAnimator;
                int i2 = defaultItemAnimator3 != null ? defaultItemAnimator3.mPendingAnimFlag : 0;
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
                        DefaultItemAnimator defaultItemAnimator4 = this.mItemAnimator;
                        if (defaultItemAnimator4 != null && this.mLastItemAnimTop == -1) {
                            this.mLastItemAnimTop = defaultItemAnimator4.mLastItemBottom;
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
                canvas.drawRect(0.0f, this.mAnimatedBlackTop, width, getBottom(), this.mRectPaint);
                if (this.mDrawLastRoundedCorner) {
                    SeslSubheaderRoundedCorner seslSubheaderRoundedCorner = this.mRoundedCorner;
                    seslSubheaderRoundedCorner.mRoundedCornerBounds.set(paddingLeft, this.mAnimatedBlackTop, width - paddingRight, getBottom());
                    seslSubheaderRoundedCorner.drawRoundedCornerInternal(canvas);
                }
            }
        }
        this.mLastItemAnimTop = this.mBlackTop;
        if (this.mDrawHorizontalPadding) {
            int height = getHeight();
            if (paddingLeft > 0) {
                canvas.drawRect(0.0f, 0.0f, paddingLeft, height, this.mRectPaint);
            }
            if (paddingRight > 0) {
                canvas.drawRect(width - paddingRight, 0.0f, width, height, this.mRectPaint);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:84:0x03b9, code lost:
    
        if (r17.mHoverScrollStartTime != 0) goto L272;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x03aa, code lost:
    
        if (r5 > (r3 ? getBottom() : getRight())) goto L258;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:141:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x02a7  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x014d  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean dispatchHoverEvent(android.view.MotionEvent r18) {
        /*
            Method dump skipped, instructions count: 983
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.dispatchHoverEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 19 || keyCode == 20) {
            if (keyEvent.getAction() == 0) {
                this.mIsArrowKeyPressed = true;
            }
        } else if (keyCode == 66 && this.mIsRecoilSupported && this.mIsRecoilEnabled) {
            if (keyEvent.getAction() == 0) {
                View focusedChild = getFocusedChild();
                if (focusedChild != null) {
                    this.mItemAnimatorHolder.setPress(focusedChild);
                }
            } else {
                this.mItemAnimatorHolder.setRelease();
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    /* JADX WARN: Code restructure failed: missing block: B:230:0x048f, code lost:
    
        if (r18.mChildHelper.isHidden(getFocusedChild()) == false) goto L283;
     */
    /* JADX WARN: Removed duplicated region for block: B:127:0x02c4  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0394 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0391  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0394 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dispatchLayout() {
        /*
            Method dump skipped, instructions count: 1375
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.dispatchLayout():void");
    }

    public final void dispatchLayoutStep1() {
        ViewInfoStore.InfoRecord infoRecord;
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
                    DefaultItemAnimator defaultItemAnimator = this.mItemAnimator;
                    ItemAnimator.buildAdapterChangeFlagsForAnimations(childViewHolderInt);
                    childViewHolderInt.getUnmodifiedPayloads();
                    defaultItemAnimator.getClass();
                    ItemAnimator.ItemHolderInfo itemHolderInfo = new ItemAnimator.ItemHolderInfo();
                    View view2 = childViewHolderInt.itemView;
                    itemHolderInfo.left = view2.getLeft();
                    itemHolderInfo.top = view2.getTop();
                    view2.getRight();
                    view2.getBottom();
                    SimpleArrayMap simpleArrayMap = this.mViewInfoStore.mLayoutHolderMap;
                    ViewInfoStore.InfoRecord infoRecord2 = (ViewInfoStore.InfoRecord) simpleArrayMap.get(childViewHolderInt);
                    if (infoRecord2 == null) {
                        infoRecord2 = ViewInfoStore.InfoRecord.obtain();
                        simpleArrayMap.put(childViewHolderInt, infoRecord2);
                    }
                    infoRecord2.preInfo = itemHolderInfo;
                    infoRecord2.flags |= 4;
                    if (this.mState.mTrackOldChangeHolders && childViewHolderInt.isUpdated() && !childViewHolderInt.isRemoved() && !childViewHolderInt.shouldIgnore() && !childViewHolderInt.isInvalid()) {
                        this.mViewInfoStore.mOldChangedHolders.put(getChangedHolderKey(childViewHolderInt), childViewHolderInt);
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
                if (!childViewHolderInt3.shouldIgnore() && ((infoRecord = (ViewInfoStore.InfoRecord) this.mViewInfoStore.mLayoutHolderMap.get(childViewHolderInt3)) == null || (infoRecord.flags & 4) == 0)) {
                    ItemAnimator.buildAdapterChangeFlagsForAnimations(childViewHolderInt3);
                    boolean z2 = (childViewHolderInt3.mFlags & 8192) != 0;
                    DefaultItemAnimator defaultItemAnimator2 = this.mItemAnimator;
                    childViewHolderInt3.getUnmodifiedPayloads();
                    defaultItemAnimator2.getClass();
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
            int ordinal = adapter.mStateRestorationPolicy.ordinal();
            if (ordinal == 1 ? adapter.getItemCount() > 0 : ordinal != 2) {
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

    public final void dispatchOnScrolled(int i, int i2) {
        this.mDispatchScrollCounter++;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        onScrollChanged(scrollX, scrollY, scrollX - i, scrollY - i2);
        List list = this.mScrollListeners;
        if (list != null) {
            for (int size = ((ArrayList) list).size() - 1; size >= 0; size--) {
                ((OnScrollListener) ((ArrayList) this.mScrollListeners).get(size)).onScrolled(this, i, i2);
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

    /* JADX WARN: Removed duplicated region for block: B:23:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0182  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean dispatchTouchEvent(android.view.MotionEvent r15) {
        /*
            Method dump skipped, instructions count: 498
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.dispatchTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        boolean z;
        ImageView imageView;
        super.draw(canvas);
        int size = this.mItemDecorations.size();
        for (int i = 0; i < size; i++) {
            ((ItemDecoration) this.mItemDecorations.get(i)).onDrawOver(canvas, this, this.mState);
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
            postInvalidateOnAnimation();
        }
        if (this.mEnableGoToTop) {
            this.mGoToTopView.setTranslationY(getScrollY());
            if (this.mGoToTopState != 0 && !canScrollUp$1()) {
                setupGoToTop$1(0);
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

    public final void ensureBottomGlow() {
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

    public final void ensureLeftGlow() {
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

    public final void ensureRightGlow() {
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

    public final void ensureTopGlow() {
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

    /* JADX WARN: Removed duplicated region for block: B:25:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View findClickableChildUnder(android.view.View r9, float r10, float r11) {
        /*
            r8 = this;
            boolean r0 = r9.isClickable()
            r1 = 0
            if (r0 == 0) goto L7d
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>()
            r9.getGlobalVisibleRect(r0)
            r8.getGlobalVisibleRect(r2)
            androidx.core.view.NestedScrollingChildHelper r3 = r8.mScrollingChildHelper
            if (r3 == 0) goto L6b
            android.view.ViewParent r3 = r3.getNestedScrollingParentForType(r1)
            boolean r3 = r3 instanceof androidx.core.widget.NestedScrollView
            if (r3 == 0) goto L6b
            androidx.core.view.NestedScrollingChildHelper r3 = r8.mScrollingChildHelper
            android.view.ViewParent r3 = r3.getNestedScrollingParentForType(r1)
            androidx.core.widget.NestedScrollView r3 = (androidx.core.widget.NestedScrollView) r3
            int r4 = r3.getScrollY()
            r5 = r8
            r6 = r1
        L31:
            boolean r7 = r5 instanceof androidx.core.widget.NestedScrollView
            if (r7 != 0) goto L49
            int r7 = r5.getTop()
            int r6 = r6 + r7
            android.view.ViewParent r7 = r5.getParent()
            boolean r7 = r7 instanceof android.view.ViewGroup
            if (r7 == 0) goto L49
            android.view.ViewParent r5 = r5.getParent()
            android.view.ViewGroup r5 = (android.view.ViewGroup) r5
            goto L31
        L49:
            if (r4 <= r6) goto L6b
            int r3 = r3.getScrollY()
            r4 = r8
            r5 = r1
        L51:
            boolean r6 = r4 instanceof androidx.core.widget.NestedScrollView
            if (r6 != 0) goto L69
            int r6 = r4.getTop()
            int r5 = r5 + r6
            android.view.ViewParent r6 = r4.getParent()
            boolean r6 = r6 instanceof android.view.ViewGroup
            if (r6 == 0) goto L69
            android.view.ViewParent r4 = r4.getParent()
            android.view.ViewGroup r4 = (android.view.ViewGroup) r4
            goto L51
        L69:
            int r3 = r3 - r5
            goto L6c
        L6b:
            r3 = r1
        L6c:
            int r4 = (int) r10
            int r5 = r2.left
            int r4 = r4 + r5
            int r5 = (int) r11
            int r2 = r2.top
            int r5 = r5 + r2
            int r5 = r5 - r3
            boolean r0 = r0.contains(r4, r5)
            if (r0 == 0) goto L7d
            r0 = r9
            goto L7e
        L7d:
            r0 = 0
        L7e:
            boolean r2 = r9 instanceof android.view.ViewGroup
            if (r2 == 0) goto L98
            android.view.ViewGroup r9 = (android.view.ViewGroup) r9
        L84:
            int r2 = r9.getChildCount()
            if (r1 >= r2) goto L98
            android.view.View r2 = r9.getChildAt(r1)
            android.view.View r2 = r8.findClickableChildUnder(r2, r10, r11)
            if (r2 == 0) goto L95
            return r2
        L95:
            int r1 = r1 + 1
            goto L84
        L98:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.findClickableChildUnder(android.view.View, float, float):android.view.View");
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0016, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View findContainingItemView(android.view.View r3) {
        /*
            r2 = this;
            android.view.ViewParent r0 = r3.getParent()
        L4:
            if (r0 == 0) goto L14
            if (r0 == r2) goto L14
            boolean r1 = r0 instanceof android.view.View
            if (r1 == 0) goto L14
            r3 = r0
            android.view.View r3 = (android.view.View) r3
            android.view.ViewParent r0 = r3.getParent()
            goto L4
        L14:
            if (r0 != r2) goto L17
            return r3
        L17:
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.findContainingItemView(android.view.View):android.view.View");
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
            iArr[i] = span.this$0.mReverseLayout ? span.findOnePartiallyOrCompletelyVisibleChild(0, span.mViews.size(), true, false) : span.findOnePartiallyOrCompletelyVisibleChild(span.mViews.size() - 1, -1, true, false);
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
        int i2 = Integer.MIN_VALUE;
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final androidx.recyclerview.widget.RecyclerView.ViewHolder findViewHolderForPosition(int r6, boolean r7) {
        /*
            r5 = this;
            androidx.recyclerview.widget.ChildHelper r0 = r5.mChildHelper
            int r0 = r0.getUnfilteredChildCount()
            r1 = 0
            r2 = 0
        L8:
            if (r2 >= r0) goto L3a
            androidx.recyclerview.widget.ChildHelper r3 = r5.mChildHelper
            android.view.View r3 = r3.getUnfilteredChildAt(r2)
            androidx.recyclerview.widget.RecyclerView$ViewHolder r3 = getChildViewHolderInt(r3)
            if (r3 == 0) goto L37
            boolean r4 = r3.isRemoved()
            if (r4 != 0) goto L37
            if (r7 == 0) goto L23
            int r4 = r3.mPosition
            if (r4 == r6) goto L2a
            goto L37
        L23:
            int r4 = r3.getLayoutPosition()
            if (r4 == r6) goto L2a
            goto L37
        L2a:
            androidx.recyclerview.widget.ChildHelper r1 = r5.mChildHelper
            android.view.View r4 = r3.itemView
            boolean r1 = r1.isHidden(r4)
            if (r1 == 0) goto L36
            r1 = r3
            goto L37
        L36:
            return r3
        L37:
            int r2 = r2 + 1
            goto L8
        L3a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.findViewHolderForPosition(int, boolean):androidx.recyclerview.widget.RecyclerView$ViewHolder");
    }

    /* JADX WARN: Code restructure failed: missing block: B:119:0x0186, code lost:
    
        if (r6 > 0) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x01a6, code lost:
    
        if (r5 > 0) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x01a9, code lost:
    
        if (r6 < 0) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x01ac, code lost:
    
        if (r5 < 0) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x01b4, code lost:
    
        if ((r5 * r9) <= 0) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x01bc, code lost:
    
        if ((r5 * r9) >= 0) goto L129;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00ea A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01e4  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x00f9  */
    @Override // android.view.ViewGroup, android.view.ViewParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View focusSearch(android.view.View r17, int r18) {
        /*
            Method dump skipped, instructions count: 511
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.focusSearch(android.view.View, int):android.view.View");
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
        if (((viewHolder.mFlags & 524) != 0) || !viewHolder.isBound()) {
            return -1;
        }
        AdapterHelper adapterHelper = this.mAdapterHelper;
        int i = viewHolder.mPosition;
        int size = adapterHelper.mPendingUpdates.size();
        for (int i2 = 0; i2 < size; i2++) {
            AdapterHelper.UpdateOp updateOp = (AdapterHelper.UpdateOp) adapterHelper.mPendingUpdates.get(i2);
            int i3 = updateOp.cmd;
            if (i3 != 1) {
                if (i3 == 2) {
                    int i4 = updateOp.positionStart;
                    if (i4 <= i) {
                        int i5 = updateOp.itemCount;
                        if (i4 + i5 > i) {
                            return -1;
                        }
                        i -= i5;
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

    public final Rect getItemDecorInsetsForChild(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (!layoutParams.mInsetsDirty) {
            return layoutParams.mDecorInsets;
        }
        if (this.mState.mInPreLayout && (layoutParams.mViewHolder.isUpdated() || layoutParams.mViewHolder.isInvalid())) {
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

    public final LayoutManager getLayoutManager() {
        return this.mLayout;
    }

    public final long getNanoTime() {
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
        return getScrollingChildHelper().hasNestedScrollingParent(0);
    }

    public final boolean hasPendingAdapterUpdates() {
        return !this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout || this.mAdapterHelper.hasPendingUpdates();
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
        return (accessibilityManager == null || !accessibilityManager.isEnabled() || (string = Settings.Secure.getString(getContext().getContentResolver(), SettingsHelper.INDEX_ENABLED_ACCESSIBILITY_SERVICES)) == null || !(string.matches("(?i).*com.samsung.accessibility/com.samsung.android.app.talkback.TalkBackService.*") || string.matches("(?i).*com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService.*") || string.matches("(?i).*com.google.android.marvin.talkback.TalkBackService.*") || string.matches("(?i).*com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService.*"))) && getHeight() > this.mSeslOverlayFeatureHeight;
    }

    @Override // android.view.ViewGroup
    public final boolean isLayoutSuppressed() {
        return this.mLayoutSuppressed;
    }

    @Override // android.view.View
    public final boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().mIsNestedScrollingEnabled;
    }

    public final boolean isSupportGotoTop$1() {
        return isGoToTopAvailableEnvironment() && this.mEnableGoToTop;
    }

    public final void jumpToPositionForSmoothScroller(int i) {
        if (this.mLayout == null) {
            return;
        }
        setScrollState(2);
        this.mLayout.scrollToPosition(i);
        awakenScrollBars();
    }

    public final void markItemDecorInsetsDirty() {
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < unfilteredChildCount; i++) {
            ((LayoutParams) this.mChildHelper.getUnfilteredChildAt(i).getLayoutParams()).mInsetsDirty = true;
        }
        Recycler recycler = this.mRecycler;
        int size = recycler.mCachedViews.size();
        for (int i2 = 0; i2 < size; i2++) {
            LayoutParams layoutParams = (LayoutParams) ((ViewHolder) recycler.mCachedViews.get(i2)).itemView.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.mInsetsDirty = true;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void multiSelection(int r20, int r21, int r22) {
        /*
            Method dump skipped, instructions count: 425
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.multiSelection(int, int, int):void");
    }

    public final void multiSelectionEnd() {
        this.mIsPenPressed = false;
        this.mIsFirstPenMoveEvent = true;
        this.mPenDragSelectedItemArray.clear();
        this.mPenDragStartX = 0;
        this.mPenDragStartY = 0;
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

    public final void offsetPositionRecordsForRemove(int i, int i2, boolean z) {
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
        for (int size = recycler.mCachedViews.size() - 1; size >= 0; size--) {
            ViewHolder viewHolder = (ViewHolder) recycler.mCachedViews.get(size);
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
        requestLayout();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mLayoutOrScrollCounter = 0;
        this.mIsAttached = true;
        this.mFirstLayoutComplete = this.mFirstLayoutComplete && !isLayoutRequested();
        this.mRecycler.maybeSendPoolingContainerAttach();
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.mIsAttachedToWindow = true;
            layoutManager.onAttachedToWindow(this);
        }
        this.mPostedAnimatorRunner = false;
        if (ALLOW_THREAD_GAP_WORK) {
            ThreadLocal threadLocal = GapWorker.sGapWorker;
            GapWorker gapWorker = (GapWorker) threadLocal.get();
            this.mGapWorker = gapWorker;
            if (gapWorker == null) {
                this.mGapWorker = new GapWorker();
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                Display display = getDisplay();
                if (!isInEditMode() && display != null) {
                    float refreshRate = display.getRefreshRate();
                    r4 = refreshRate >= 30.0f ? refreshRate : 60.0f;
                    if (this.mIsNeedCheckLatency) {
                        this.mFrameLatency = 1000.0f / r4;
                        this.mIsNeedCheckLatency = false;
                    }
                }
                GapWorker gapWorker2 = this.mGapWorker;
                gapWorker2.mFrameIntervalNs = (long) (1.0E9f / r4);
                threadLocal.set(gapWorker2);
            }
            GapWorker gapWorker3 = this.mGapWorker;
            gapWorker3.getClass();
            gapWorker3.mRecyclerViews.add(this);
            LayoutManager layoutManager2 = this.mLayout;
            if (layoutManager2 != null) {
                layoutManager2.getLayoutDirection();
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        GapWorker gapWorker;
        super.onDetachedFromWindow();
        DefaultItemAnimator defaultItemAnimator = this.mItemAnimator;
        if (defaultItemAnimator != null) {
            defaultItemAnimator.endAnimations();
        }
        stopScroll();
        int i = 0;
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
        for (int i2 = 0; i2 < recycler.mCachedViews.size(); i2++) {
            PoolingContainer.callPoolingContainerOnRelease(((ViewHolder) recycler.mCachedViews.get(i2)).itemView);
        }
        recycler.poolingContainerDetach(RecyclerView.this.mAdapter, false);
        PoolingContainer.callPoolingContainerOnReleaseForChildren(this);
        if (ALLOW_THREAD_GAP_WORK && (gapWorker = this.mGapWorker) != null) {
            gapWorker.mRecyclerViews.remove(this);
            this.mGapWorker = null;
        }
        this.mIsNeedCheckLatency = true;
        if (this.mIsRecoilSupported) {
            SeslRecoilAnimator.Holder holder = this.mItemAnimatorHolder;
            ArrayList arrayList = holder.mAnimators;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                SeslRecoilAnimator seslRecoilAnimator = (SeslRecoilAnimator) obj;
                if (seslRecoilAnimator.mIsPressed || seslRecoilAnimator.mAnimator.isRunning()) {
                    seslRecoilAnimator.mAnimator.end();
                }
                seslRecoilAnimator.mAnimator.removeAllUpdateListeners();
            }
            holder.mAnimators.clear();
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = this.mItemDecorations.size();
        for (int i = 0; i < size; i++) {
            ((ItemDecoration) this.mItemDecorations.get(i)).onDraw(canvas, this, this.mState);
        }
        if (this.mIsNeedCheckLatency) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            Display display = getDisplay();
            if (display != null) {
                this.mFrameLatency = 1000.0f / display.getRefreshRate();
            } else {
                this.mFrameLatency = 16.66f;
            }
            this.mIsNeedCheckLatency = false;
        }
    }

    public final void onEnterLayoutOrScroll() {
        this.mLayoutOrScrollCounter++;
    }

    public final void onExitLayoutOrScroll(boolean z) {
        int i;
        AccessibilityManager accessibilityManager;
        int i2 = this.mLayoutOrScrollCounter - 1;
        this.mLayoutOrScrollCounter = i2;
        if (i2 < 1) {
            this.mLayoutOrScrollCounter = 0;
            if (z) {
                int i3 = this.mEatenAccessibilityChangeFlags;
                this.mEatenAccessibilityChangeFlags = 0;
                if (i3 != 0 && (accessibilityManager = this.mAccessibilityManager) != null && accessibilityManager.isEnabled()) {
                    AccessibilityEvent obtain = AccessibilityEvent.obtain();
                    obtain.setEventType(2048);
                    obtain.setContentChangeTypes(i3);
                    sendAccessibilityEventUnchecked(obtain);
                }
                for (int size = this.mPendingAccessibilityImportanceChange.size() - 1; size >= 0; size--) {
                    ViewHolder viewHolder = this.mPendingAccessibilityImportanceChange.get(size);
                    if (viewHolder.itemView.getParent() == this && !viewHolder.shouldIgnore() && (i = viewHolder.mPendingAccessibilityState) != -1) {
                        View view = viewHolder.itemView;
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        view.setImportantForAccessibility(i);
                        viewHolder.mPendingAccessibilityState = -1;
                    }
                }
                this.mPendingAccessibilityImportanceChange.clear();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0088  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onGenericMotionEvent(android.view.MotionEvent r12) {
        /*
            Method dump skipped, instructions count: 262
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.onGenericMotionEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:119:0x02b7, code lost:
    
        if ((r1.getHeight() * r1.getWidth()) < ((r2.getHeight() * r2.getWidth()) * 0.5d)) goto L168;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0075, code lost:
    
        if (r9 != 211) goto L186;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:121:0x02bc  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x030c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x012c  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r17) {
        /*
            Method dump skipped, instructions count: 782
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 92) {
            if (i != 93) {
                if (i == 113 || i == 114) {
                    this.mIsCtrlKeyPressed = true;
                } else if (i != 122) {
                    if (i == 123 && keyEvent.hasNoModifiers()) {
                        pageScroll$1(3);
                    }
                } else if (keyEvent.hasNoModifiers()) {
                    pageScroll$1(2);
                }
            } else if (keyEvent.hasNoModifiers()) {
                pageScroll$1(1);
            }
        } else if (keyEvent.hasNoModifiers()) {
            pageScroll$1(0);
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
        Trace.beginSection("RV OnLayout");
        dispatchLayout();
        Trace.endSection();
        this.mFirstLayoutComplete = true;
        if (z) {
            this.mSizeChange = true;
            this.mSeslOverlayFeatureHeight = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_recyclerview_overlay_feature_hidden_height);
            if (this.mEnableGoToTop) {
                int height = (getHeight() - this.mGoToTopSize) - this.mGoToTopBottomPadding;
                if (height < 0) {
                    Log.e("SeslRecyclerView", "The Immersive padding value (0) was too large to draw GoToTop.");
                } else if (this.mGoToTopState != 0) {
                    int width = (((getWidth() - getPaddingLeft()) - getPaddingRight()) / 2) + getPaddingLeft();
                    Rect rect = this.mGoToTopRect;
                    int i5 = this.mGoToTopSize;
                    int i6 = i5 / 2;
                    rect.set(width - i6, height, i6 + width, i5 + height);
                    ImageView imageView = this.mGoToTopView;
                    Rect rect2 = this.mGoToTopRect;
                    imageView.layout(rect2.left, rect2.top, rect2.right, rect2.bottom);
                }
            }
            setupGoToTop$1(-1);
            autoHide(1);
            LayoutManager layoutManager = this.mLayout;
            if (layoutManager == null || layoutManager.canScrollHorizontally()) {
                LayoutManager layoutManager2 = this.mLayout;
                if (layoutManager2 == null || !layoutManager2.canScrollHorizontally()) {
                    return;
                }
                getLocationInWindow(this.mWindowOffsets);
                this.mRemainNestedScrollRange = 0;
                this.mNestedScrollRange = 0;
                this.mInitialTopOffsetOfScreen = this.mWindowOffsets[0];
                return;
            }
            this.mHasNestedScrollRange = false;
            ViewParent parent = getParent();
            while (true) {
                if (parent == null || !(parent instanceof ViewGroup)) {
                    break;
                }
                if (parent instanceof NestedScrollingParent2) {
                    for (Class<?> cls = parent.getClass(); cls != null; cls = cls.getSuperclass()) {
                        if (cls.getSimpleName().equals("CoordinatorLayout")) {
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
                }
                parent = parent.getParent();
            }
            if (this.mHasNestedScrollRange) {
                return;
            }
            this.mInitialTopOffsetOfScreen = 0;
            this.mRemainNestedScrollRange = 0;
            this.mNestedScrollRange = 0;
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
                this.mLayout.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
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
            return savedState;
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            savedState.mLayoutState = layoutManager.onSaveInstanceState();
            return savedState;
        }
        savedState.mLayoutState = null;
        return savedState;
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i == i3 && i2 == i4) {
            return;
        }
        this.mBottomGlow = null;
        this.mTopGlow = null;
        this.mRightGlow = null;
        this.mLeftGlow = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:147:0x02e2, code lost:
    
        if (r4 == 0) goto L214;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x0332, code lost:
    
        if (r4 != false) goto L215;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:133:0x027c  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x02bf A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x02de A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:150:0x02ee  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0131  */
    /* JADX WARN: Type inference failed for: r12v11 */
    /* JADX WARN: Type inference failed for: r12v2 */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* JADX WARN: Type inference failed for: r16v0, types: [android.view.ViewGroup, androidx.recyclerview.widget.RecyclerView] */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v23 */
    /* JADX WARN: Type inference failed for: r4v32 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v4, types: [boolean] */
    /* JADX WARN: Type inference failed for: r5v5, types: [int] */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r9v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r9v1, types: [int] */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v27 */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r17) {
        /*
            Method dump skipped, instructions count: 968
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public final void pageScroll$1(int i) {
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

    public final void postAnimationRunner() {
        if (this.mPostedAnimatorRunner || !this.mIsAttached) {
            return;
        }
        AnonymousClass7 anonymousClass7 = this.mItemAnimatorRunner;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        postOnAnimation(anonymousClass7);
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
        if (this.mItemAnimator == null || !this.mLayout.supportsPredictiveItemAnimations()) {
            this.mAdapterHelper.consumeUpdatesInOnePass();
        } else {
            this.mAdapterHelper.preProcess();
        }
        boolean z3 = this.mItemsAddedOrRemoved || this.mItemsChanged;
        State state = this.mState;
        boolean z4 = this.mFirstLayoutComplete && this.mItemAnimator != null && ((z = this.mDataSetHasChangedAfterLayout) || z3 || this.mLayout.mRequestedSimpleAnimations) && (!z || this.mAdapter.mHasStableIds);
        state.mRunSimpleAnimations = z4;
        if (z4 && z3 && !this.mDataSetHasChangedAfterLayout && this.mItemAnimator != null && this.mLayout.supportsPredictiveItemAnimations()) {
            z2 = true;
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
        int size = recycler.mCachedViews.size();
        for (int i2 = 0; i2 < size; i2++) {
            ViewHolder viewHolder = (ViewHolder) recycler.mCachedViews.get(i2);
            if (viewHolder != null) {
                viewHolder.addFlags(6);
                viewHolder.addFlags(1024);
            }
        }
        Adapter adapter = RecyclerView.this.mAdapter;
        if (adapter == null || !adapter.mHasStableIds) {
            recycler.recycleAndClearCachedViews();
        }
    }

    public final void recordAnimationInfoIfBouncedHiddenView(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo) {
        viewHolder.mFlags &= -8193;
        if (this.mState.mTrackOldChangeHolders && viewHolder.isUpdated() && !viewHolder.isRemoved() && !viewHolder.shouldIgnore()) {
            this.mViewInfoStore.mOldChangedHolders.put(getChangedHolderKey(viewHolder), viewHolder);
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

    public final int releaseVerticalGlow$1(float f, int i) {
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

    public final void removeAndRecycleViews() {
        DefaultItemAnimator defaultItemAnimator = this.mItemAnimator;
        if (defaultItemAnimator != null) {
            defaultItemAnimator.endAnimations();
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
            postInvalidateOnAnimation();
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

    /* JADX WARN: Removed duplicated region for block: B:29:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00fd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean scrollByInternal(int r19, int r20, android.view.MotionEvent r21, int r22) {
        /*
            Method dump skipped, instructions count: 351
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.scrollByInternal(int, int, android.view.MotionEvent, int):boolean");
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
                setupGoToTop$1(1);
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
                View view = viewHolder.itemView;
                int left = childAt.getLeft();
                int top = childAt.getTop();
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
        } else {
            layoutManager.scrollToPosition(i);
            awakenScrollBars();
        }
    }

    @Override // android.view.View, android.view.accessibility.AccessibilityEventSource
    public final void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (!isComputingLayout()) {
            super.sendAccessibilityEventUnchecked(accessibilityEvent);
        } else {
            int contentChangeTypes = accessibilityEvent != null ? accessibilityEvent.getContentChangeTypes() : 0;
            this.mEatenAccessibilityChangeFlags |= contentChangeTypes != 0 ? contentChangeTypes : 0;
        }
    }

    public final void seslSetFillBottomColor(int i) {
        this.mRectPaint.setColor(i);
        SeslSubheaderRoundedCorner seslSubheaderRoundedCorner = this.mRoundedCorner;
        seslSubheaderRoundedCorner.getClass();
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_IN);
        seslSubheaderRoundedCorner.mBottomLeftRoundColor = i;
        seslSubheaderRoundedCorner.mBottomLeftRound.mColorFilter = porterDuffColorFilter;
        seslSubheaderRoundedCorner.mBottomRightRoundColor = i;
        seslSubheaderRoundedCorner.mBottomRightRound.mColorFilter = porterDuffColorFilter;
    }

    public final void seslSetFillBottomEnabled() {
        if (this.mLayout instanceof LinearLayoutManager) {
            this.mDrawRect = true;
            requestLayout();
        }
    }

    public final void seslSetFillHorizontalPaddingEnabled(boolean z) {
        this.mDrawHorizontalPadding = z;
        int dimensionPixelOffset = z ? getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.sesl_system_scroller_vertical_padding) : 0;
        this.mScrollbarBottomPadding = dimensionPixelOffset;
        Class cls = SeslViewReflector.mClass;
        Class cls2 = Integer.TYPE;
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(cls, "semSetScrollBarTopPadding", cls2);
        if (declaredMethod != null) {
            SeslBaseReflector.invoke(this, declaredMethod, Integer.valueOf(dimensionPixelOffset));
        }
        int i = this.mScrollbarBottomPadding;
        Method declaredMethod2 = SeslBaseReflector.getDeclaredMethod(cls, "semSetScrollBarBottomPadding", cls2);
        if (declaredMethod2 != null) {
            SeslBaseReflector.invoke(this, declaredMethod2, Integer.valueOf(i));
        }
        requestLayout();
    }

    public final void seslSetGoToTopEnabled() {
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
            this.mGoToTopView.setAlpha(0.0f);
            if (!this.mEnableGoToTop) {
                getOverlay().add(this.mGoToTopView);
            }
            this.mEnableGoToTop = true;
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
                        recyclerView.mShowFadeOutGTT = 2;
                        recyclerView.setupGoToTop$1(0);
                    } catch (Exception unused) {
                    }
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    try {
                        RecyclerView.this.mShowFadeOutGTT = 1;
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
            this.mAdapter.onDetachedFromRecyclerView();
        }
        removeAndRecycleViews();
        AdapterHelper adapterHelper = this.mAdapterHelper;
        adapterHelper.recycleUpdateOpsAndClearList(adapterHelper.mPendingUpdates);
        adapterHelper.recycleUpdateOpsAndClearList(adapterHelper.mPostponedList);
        adapterHelper.mExistingUpdateTypes = 0;
        Adapter adapter3 = this.mAdapter;
        this.mAdapter = adapter;
        if (adapter != null) {
            adapter.mObservable.registerObserver(this.mObserver);
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
            for (int i = 0; i < recycledViewPool.mScrap.size(); i++) {
                RecycledViewPool.ScrapData scrapData = (RecycledViewPool.ScrapData) recycledViewPool.mScrap.valueAt(i);
                if (scrapData != null) {
                    ArrayList arrayList = scrapData.mScrapHeap;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        PoolingContainer.callPoolingContainerOnRelease(((ViewHolder) obj).itemView);
                    }
                    scrapData.mScrapHeap.clear();
                } else {
                    Log.e("SeslRecyclerView", "clear() wasn't executed because RecycledViewPool.mScrap was invalid");
                }
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
        view.setImportantForAccessibility(i);
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

    public final void setItemAnimator(DefaultItemAnimator defaultItemAnimator) {
        DefaultItemAnimator defaultItemAnimator2 = this.mItemAnimator;
        if (defaultItemAnimator2 != null) {
            defaultItemAnimator2.endAnimations();
            this.mItemAnimator.mListener = null;
        }
        this.mItemAnimator = defaultItemAnimator;
        if (defaultItemAnimator != null) {
            defaultItemAnimator.mListener = this.mItemAnimatorListener;
            defaultItemAnimator.mHostView = this;
        }
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        ChildHelper.Callback callback;
        if (layoutManager == this.mLayout) {
            return;
        }
        boolean z = layoutManager instanceof LinearLayoutManager;
        this.mDrawRect = this.mDrawRect && z;
        this.mDrawLastRoundedCorner = this.mDrawLastRoundedCorner && z;
        stopScroll();
        if (this.mLayout != null) {
            DefaultItemAnimator defaultItemAnimator = this.mItemAnimator;
            if (defaultItemAnimator != null) {
                defaultItemAnimator.endAnimations();
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
        int size = ((ArrayList) childHelper.mHiddenViews).size() - 1;
        while (true) {
            callback = childHelper.mCallback;
            if (size < 0) {
                break;
            }
            View view = (View) ((ArrayList) childHelper.mHiddenViews).get(size);
            AnonymousClass10 anonymousClass10 = (AnonymousClass10) callback;
            anonymousClass10.getClass();
            ViewHolder childViewHolderInt = getChildViewHolderInt(view);
            if (childViewHolderInt != null) {
                RecyclerView.this.setChildImportantForAccessibilityInternal(childViewHolderInt, childViewHolderInt.mWasImportantForAccessibilityBeforeHidden);
                childViewHolderInt.mWasImportantForAccessibilityBeforeHidden = 0;
            }
            ((ArrayList) childHelper.mHiddenViews).remove(size);
            size--;
        }
        RecyclerView recyclerView = RecyclerView.this;
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            recyclerView.dispatchChildDetached(childAt);
            childAt.clearAnimation();
        }
        recyclerView.removeAllViews();
        this.mLayout = layoutManager;
        if (layoutManager != null) {
            if (layoutManager.mRecyclerView != null) {
                throw new IllegalArgumentException("LayoutManager " + layoutManager + " is already attached to a RecyclerView:" + layoutManager.mRecyclerView.exceptionLabel());
            }
            layoutManager.setRecyclerView(this);
            if (this.mIsAttached) {
                LayoutManager layoutManager3 = this.mLayout;
                layoutManager3.mIsAttachedToWindow = true;
                layoutManager3.onAttachedToWindow(this);
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
            View view = scrollingChildHelper.mView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.stopNestedScroll(view);
        }
        scrollingChildHelper.mIsNestedScrollingEnabled = z;
    }

    public final void setScrollState(int i) {
        SmoothScroller smoothScroller;
        if (i == this.mScrollState) {
            return;
        }
        RecyclerView$$ExternalSyntheticOutline0.m(this.mScrollState, "SeslRecyclerView", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "setting scroll state to ", " from "));
        this.mScrollState = i;
        if (i != 2) {
            this.mViewFlinger.stop();
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
            for (int size = ((ArrayList) list).size() - 1; size >= 0; size--) {
                ((OnScrollListener) ((ArrayList) this.mScrollListeners).get(size)).onScrollStateChanged(this, i);
            }
        }
        if (i == 1) {
            this.mEdgeEffectByDragging = false;
        }
    }

    public final void setupGoToTop$1(int i) {
        if (this.mEnableGoToTop) {
            if (!isGoToTopAvailableEnvironment()) {
                this.mShowFadeOutGTT = 2;
                i = 0;
            }
            removeCallbacks(this.mAutoHide);
            if (i == 1 && !canScrollUp$1()) {
                i = 0;
            }
            if (i == -1 && this.mSizeChange) {
                i = (canScrollUp$1() || canScrollDown$1()) ? this.mGoToTopLastState : 0;
            } else if (i == -1 && (canScrollUp$1() || canScrollDown$1())) {
                i = 1;
            }
            if (i != 0) {
                removeCallbacks(this.mGoToToFadeOutRunnable);
            }
            if (i != 1) {
                removeCallbacks(this.mGoToToFadeInRunnable);
            }
            if (this.mShowFadeOutGTT == 0 && i == 0 && this.mGoToTopLastState != 0) {
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
                    rect.set(width - i3, (height - i2) - i4, i3 + width, height - i4);
                }
            } else if (this.mShowFadeOutGTT == 2) {
                this.mGoToTopRect.set(0, 0, 0, 0);
            }
            if (this.mShowFadeOutGTT == 2) {
                this.mShowFadeOutGTT = 0;
            }
            ImageView imageView = this.mGoToTopView;
            Rect rect2 = this.mGoToTopRect;
            imageView.layout(rect2.left, rect2.top, rect2.right, rect2.bottom);
            if (i == 1 && (this.mGoToTopLastState == 0 || this.mGoToTopView.getAlpha() == 0.0f || this.mSizeChange)) {
                post(this.mGoToToFadeInRunnable);
            }
            this.mSizeChange = false;
            this.mGoToTopLastState = this.mGoToTopState;
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

    public final void showGoToTop$1() {
        if (this.mEnableGoToTop && canScrollUp$1() && this.mGoToTopState != 2) {
            setupGoToTop$1(1);
            autoHide(1);
        }
    }

    public final void showPointerIcon(MotionEvent motionEvent, int i) {
        SeslViewReflector.semSetPointerIcon(this, motionEvent.getToolType(0), i == 20001 ? null : PointerIcon.getSystemIcon(this.mContext, i));
    }

    public void smoothScrollBy(int i, int i2) {
        smoothScrollBy$1(i, i2);
    }

    public void smoothScrollBy$1(int i, int i2) {
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
        this.mViewFlinger.stop();
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
        this.mViewFlinger.smoothScrollBy(i, i2, Integer.MIN_VALUE, null);
        showGoToTop$1();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedState extends AbsSavedState {
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
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.mLayoutState, 0);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.recyclerview.widget.RecyclerView$1] */
    /* JADX WARN: Type inference failed for: r5v19, types: [androidx.recyclerview.widget.RecyclerView$6] */
    /* JADX WARN: Type inference failed for: r5v21, types: [androidx.recyclerview.widget.RecyclerView$7] */
    /* JADX WARN: Type inference failed for: r5v5, types: [androidx.recyclerview.widget.RecyclerView$2] */
    /* JADX WARN: Type inference failed for: r5v7, types: [androidx.recyclerview.widget.RecyclerView$3] */
    /* JADX WARN: Type inference failed for: r5v8, types: [androidx.recyclerview.widget.RecyclerView$4] */
    /* JADX WARN: Type inference failed for: r5v9, types: [androidx.recyclerview.widget.RecyclerView$5] */
    public RecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ClassLoader classLoader;
        Constructor constructor;
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
        Object[] objArr = null;
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
        this.mIsEdgeEffectEnabled = true;
        this.mAnimListener = new Animator.AnimatorListener() { // from class: androidx.recyclerview.widget.RecyclerView.2
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                RecyclerView recyclerView = RecyclerView.this;
                recyclerView.mLastItemAddRemoveAnim = null;
                recyclerView.mIsSetOnlyAddAnim = false;
                recyclerView.mIsSetOnlyRemoveAnim = false;
                DefaultItemAnimator defaultItemAnimator = recyclerView.mItemAnimator;
                if (defaultItemAnimator != null) {
                    defaultItemAnimator.mPendingAnimFlag = 0;
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
                RecyclerView recyclerView = RecyclerView.this;
                int[] iArr = RecyclerView.NESTED_SCROLLING_ATTRS;
                recyclerView.setupGoToTop$1(0);
            }
        };
        this.mEnableGoToTop = false;
        this.mSizeChange = false;
        this.mSeslOverlayFeatureHeight = 0;
        this.mGoToTopRect = new Rect();
        this.mGoToTopState = 0;
        this.mGoToTopLastState = 0;
        this.mShowFadeOutGTT = 0;
        this.mIsPenSelectionEnabled = true;
        this.mIsPenPressed = false;
        this.mIsFirstPenMoveEvent = true;
        this.mIsNeedPenSelection = false;
        this.mIsPenDragBlockEnabled = true;
        this.mPenDragStartX = 0;
        this.mPenDragStartY = 0;
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
        this.mIsCtrlMultiSelection = false;
        this.mDrawHorizontalPadding = false;
        this.mDrawRect = false;
        this.mDrawLastRoundedCorner = true;
        this.mDrawReverse = false;
        this.mBlackTop = -1;
        this.mLastBlackTop = -1;
        this.mAnimatedBlackTop = -1;
        this.mRectPaint = new Paint();
        this.mScrollbarBottomPadding = 0;
        this.mIsPenHovered = false;
        this.mIsPenSelectPointerSetted = false;
        this.mIsNeedPenSelectIconSet = false;
        this.mOldTextViewHoverState = false;
        this.mNewTextViewHoverState = false;
        this.mHoverScrollSpeed = 0;
        int field_SEM_TYPE_STYLUS_SCROLL_UP = SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_SCROLL_UP();
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod("android.view.PointerIcon", "hidden_SEM_TYPE_STYLUS_SCROLL_RIGHT", new Class[0]);
        Object invoke = declaredMethod != null ? SeslBaseReflector.invoke(null, declaredMethod, new Object[0]) : null;
        int intValue = invoke instanceof Integer ? ((Integer) invoke).intValue() : 13;
        int field_SEM_TYPE_STYLUS_SCROLL_DOWN = SeslPointerIconReflector.getField_SEM_TYPE_STYLUS_SCROLL_DOWN();
        Method declaredMethod2 = SeslBaseReflector.getDeclaredMethod("android.view.PointerIcon", "hidden_SEM_TYPE_STYLUS_SCROLL_LEFT", new Class[0]);
        Object invoke2 = declaredMethod2 != null ? SeslBaseReflector.invoke(null, declaredMethod2, new Object[0]) : null;
        this.mHoverScrollArrows = new int[]{field_SEM_TYPE_STYLUS_SCROLL_UP, intValue, field_SEM_TYPE_STYLUS_SCROLL_DOWN, invoke2 instanceof Integer ? ((Integer) invoke2).intValue() : 17};
        this.mHoverRecognitionDurationTime = 0L;
        this.mHoverRecognitionStartTime = 0L;
        this.mHoverScrollTimeInterval = 300L;
        this.mPenDragScrollTimeInterval = 500L;
        this.mHoverScrollStartTime = 0L;
        this.mHoverScrollDirection = -1;
        this.mIsHoverOverscrolled = false;
        this.mIsSendHoverScrollState = false;
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
        this.mItemBackgroundHolder = new ItemBackgroundHolder(this);
        this.mHoverHandler = new Handler(Looper.getMainLooper()) { // from class: androidx.recyclerview.widget.RecyclerView.6
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i2;
                int childCount;
                if (message.what != 0) {
                    return;
                }
                RecyclerView recyclerView = RecyclerView.this;
                if (recyclerView.mAdapter == null) {
                    Log.e("SeslRecyclerView", "No adapter attached; skipping MSG_HOVERSCROLL_MOVE");
                    return;
                }
                long currentTimeMillis = System.currentTimeMillis();
                recyclerView.mHoverRecognitionDurationTime = (currentTimeMillis - recyclerView.mHoverRecognitionStartTime) / 1000;
                boolean z = recyclerView.mIsPenHovered;
                if (!z || currentTimeMillis - recyclerView.mHoverScrollStartTime >= recyclerView.mHoverScrollTimeInterval) {
                    if (!recyclerView.mIsPenPressed || currentTimeMillis - recyclerView.mHoverScrollStartTime >= recyclerView.mPenDragScrollTimeInterval) {
                        if (z && !recyclerView.mIsSendHoverScrollState) {
                            recyclerView.mIsSendHoverScrollState = true;
                        }
                        boolean canScrollVertically = recyclerView.mLayout.canScrollVertically();
                        boolean canScrollHorizontally = recyclerView.mLayout.canScrollHorizontally();
                        boolean z2 = recyclerView.mLayout.getLayoutDirection() == 1;
                        boolean canScrollDown$1 = recyclerView.canScrollDown$1();
                        boolean canScrollUp$1 = recyclerView.canScrollUp$1();
                        int applyDimension = (int) (TypedValue.applyDimension(1, RecyclerView.HOVERSCROLL_SPEED, recyclerView.mContext.getResources().getDisplayMetrics()) + 0.5f);
                        recyclerView.mHoverScrollSpeed = applyDimension;
                        long j = recyclerView.mHoverRecognitionDurationTime;
                        if (j > 2 && j < 4) {
                            recyclerView.mHoverScrollSpeed = applyDimension + ((int) (applyDimension * 0.1d));
                        } else if (j >= 4 && j < 5) {
                            recyclerView.mHoverScrollSpeed = applyDimension + ((int) (applyDimension * 0.2d));
                        } else if (j >= 5) {
                            recyclerView.mHoverScrollSpeed = applyDimension + ((int) (applyDimension * 0.3d));
                        }
                        int i3 = recyclerView.mHoverScrollDirection;
                        if (i3 == 2) {
                            i2 = (canScrollHorizontally && z2) ? recyclerView.mHoverScrollSpeed : recyclerView.mHoverScrollSpeed * (-1);
                            if (recyclerView.mOldHoverScrollDirection != i3 && recyclerView.mIsCloseChildSetted) {
                                recyclerView.mPenTrackedChild = null;
                                recyclerView.mPenDistanceFromTrackedChildTop = 0;
                                recyclerView.mPenTrackedChildPosition = recyclerView.mCloseChildPositionByBottom;
                                recyclerView.mOldHoverScrollDirection = i3;
                                recyclerView.mIsCloseChildSetted = true;
                            }
                        } else {
                            i2 = (canScrollHorizontally && z2) ? recyclerView.mHoverScrollSpeed * (-1) : recyclerView.mHoverScrollSpeed;
                            if (recyclerView.mOldHoverScrollDirection != i3 && recyclerView.mIsCloseChildSetted) {
                                recyclerView.mPenTrackedChild = null;
                                recyclerView.mPenDistanceFromTrackedChildTop = 0;
                                recyclerView.mPenTrackedChildPosition = recyclerView.mCloseChildPositionByTop;
                                recyclerView.mOldHoverScrollDirection = i3;
                                recyclerView.mIsCloseChildSetted = true;
                            }
                        }
                        if (recyclerView.getChildAt(recyclerView.getChildCount() - 1) == null) {
                            return;
                        }
                        if ((i2 < 0 && canScrollUp$1) || (i2 > 0 && canScrollDown$1)) {
                            recyclerView.startNestedScroll(canScrollHorizontally ? 1 : 2, 1);
                            if (RecyclerView.this.dispatchNestedPreScroll(canScrollHorizontally ? z2 ? -i2 : i2 : 0, canScrollVertically ? i2 : 0, 1, null, null)) {
                                recyclerView.adjustNestedScrollRangeBy$1(i2);
                            } else {
                                int i4 = canScrollHorizontally ? z2 ? -i2 : i2 : 0;
                                if (!canScrollVertically) {
                                    i2 = 0;
                                }
                                recyclerView.scrollByInternal(i4, i2, null, 0);
                                recyclerView.setScrollState(1);
                            }
                            recyclerView.mHoverHandler.sendEmptyMessageDelayed(0, 0L);
                            return;
                        }
                        int overScrollMode = recyclerView.getOverScrollMode();
                        boolean z3 = overScrollMode == 0 || (overScrollMode == 1 && (childCount = recyclerView.getChildCount()) != 0 && (childCount != recyclerView.mAdapter.getItemCount() || recyclerView.getChildAt(0).getTop() < recyclerView.mListPadding.top || recyclerView.getChildAt(childCount - 1).getBottom() > recyclerView.getHeight() - recyclerView.mListPadding.bottom));
                        if (z3 && !recyclerView.mIsHoverOverscrolled) {
                            if (canScrollHorizontally) {
                                recyclerView.ensureLeftGlow();
                                recyclerView.ensureRightGlow();
                            } else {
                                recyclerView.ensureTopGlow();
                                recyclerView.ensureBottomGlow();
                            }
                            int i5 = recyclerView.mHoverScrollDirection;
                            if (i5 == 2) {
                                if (canScrollHorizontally) {
                                    recyclerView.mLeftGlow.onAbsorb(10000);
                                    if (!recyclerView.mRightGlow.isFinished()) {
                                        recyclerView.mRightGlow.onRelease();
                                    }
                                } else {
                                    recyclerView.mTopGlow.onAbsorb(10000);
                                    if (!recyclerView.mBottomGlow.isFinished()) {
                                        recyclerView.mBottomGlow.onRelease();
                                    }
                                }
                            } else if (i5 == 1) {
                                if (canScrollHorizontally) {
                                    recyclerView.mRightGlow.onAbsorb(10000);
                                    if (!recyclerView.mLeftGlow.isFinished()) {
                                        recyclerView.mLeftGlow.onRelease();
                                    }
                                } else {
                                    recyclerView.mBottomGlow.onAbsorb(10000);
                                    recyclerView.setupGoToTop$1(1);
                                    recyclerView.autoHide(1);
                                    if (!recyclerView.mTopGlow.isFinished()) {
                                        recyclerView.mTopGlow.onRelease();
                                    }
                                }
                            }
                            recyclerView.invalidate();
                            recyclerView.mIsHoverOverscrolled = true;
                        }
                        if (recyclerView.mScrollState == 1) {
                            recyclerView.setScrollState(0);
                        }
                        if (z3 || recyclerView.mIsHoverOverscrolled) {
                            return;
                        }
                        recyclerView.mIsHoverOverscrolled = true;
                    }
                }
            }
        };
        this.mPendingAccessibilityImportanceChange = new ArrayList();
        this.mItemAnimatorRunner = new Runnable() { // from class: androidx.recyclerview.widget.RecyclerView.7
            @Override // java.lang.Runnable
            public final void run() {
                final DefaultItemAnimator defaultItemAnimator = RecyclerView.this.mItemAnimator;
                if (defaultItemAnimator != null) {
                    boolean isEmpty = defaultItemAnimator.mPendingRemovals.isEmpty();
                    boolean isEmpty2 = defaultItemAnimator.mPendingMoves.isEmpty();
                    boolean isEmpty3 = defaultItemAnimator.mPendingChanges.isEmpty();
                    boolean isEmpty4 = defaultItemAnimator.mPendingAdditions.isEmpty();
                    if (!isEmpty || !isEmpty2 || !isEmpty4 || !isEmpty3) {
                        ArrayList arrayList = defaultItemAnimator.mPendingRemovals;
                        int size = arrayList.size();
                        int i2 = 0;
                        while (i2 < size) {
                            Object obj = arrayList.get(i2);
                            i2++;
                            final ViewHolder viewHolder = (ViewHolder) obj;
                            final View view = viewHolder.itemView;
                            final ViewPropertyAnimator animate = view.animate();
                            long j = (view.getTag() == null || !view.getTag().equals("preferencecategory")) ? 100L : 0L;
                            defaultItemAnimator.mRemoveAnimations.add(viewHolder);
                            animate.setDuration(j).alpha(0.0f).setListener(new AnimatorListenerAdapter() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.4
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    animate.setListener(null);
                                    view.setAlpha(1.0f);
                                    DefaultItemAnimator.this.dispatchAnimationFinished(viewHolder);
                                    DefaultItemAnimator.this.mRemoveAnimations.remove(viewHolder);
                                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                                    DefaultItemAnimator defaultItemAnimator2 = DefaultItemAnimator.this;
                                    int i3 = defaultItemAnimator2.mPendingAnimFlag;
                                    if ((i3 & 1) != 0) {
                                        defaultItemAnimator2.mPendingAnimFlag = i3 & (-2);
                                    }
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationStart(Animator animator) {
                                    DefaultItemAnimator.this.getClass();
                                }
                            }).start();
                        }
                        defaultItemAnimator.mPendingRemovals.clear();
                        if (!isEmpty2) {
                            final ArrayList arrayList2 = new ArrayList();
                            arrayList2.addAll(defaultItemAnimator.mPendingMoves);
                            defaultItemAnimator.mMovesList.add(arrayList2);
                            defaultItemAnimator.mPendingMoves.clear();
                            new Runnable() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ArrayList arrayList3 = arrayList2;
                                    int size2 = arrayList3.size();
                                    int i3 = 0;
                                    while (i3 < size2) {
                                        Object obj2 = arrayList3.get(i3);
                                        i3++;
                                        MoveInfo moveInfo = (MoveInfo) obj2;
                                        final DefaultItemAnimator defaultItemAnimator2 = DefaultItemAnimator.this;
                                        final RecyclerView.ViewHolder viewHolder2 = moveInfo.holder;
                                        defaultItemAnimator2.getClass();
                                        final View view2 = viewHolder2.itemView;
                                        final int i4 = moveInfo.toX - moveInfo.fromX;
                                        final int i5 = moveInfo.toY - moveInfo.fromY;
                                        if (i4 != 0) {
                                            view2.animate().translationX(0.0f);
                                        }
                                        if (i5 != 0) {
                                            view2.animate().translationY(0.0f);
                                        }
                                        final ViewPropertyAnimator animate2 = view2.animate();
                                        animate2.setInterpolator(DefaultItemAnimator.ITEM_MOVE_INTERPOLATOR);
                                        defaultItemAnimator2.mMoveAnimations.add(viewHolder2);
                                        RecyclerView recyclerView = defaultItemAnimator2.mHostView;
                                        if (recyclerView != null && recyclerView.mBlackTop != -1 && viewHolder2.getLayoutPosition() == recyclerView.mChildHelper.getChildCount() - 1) {
                                            animate2.setUpdateListener(new ValueAnimator.AnimatorUpdateListener(defaultItemAnimator2, recyclerView) { // from class: androidx.recyclerview.widget.DefaultItemAnimator.6
                                                public final /* synthetic */ RecyclerView val$recyclerView;

                                                {
                                                    this.val$recyclerView = recyclerView;
                                                }

                                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                                    this.val$recyclerView.invalidate();
                                                }
                                            });
                                        }
                                        animate2.setDuration(400L).setListener(new AnimatorListenerAdapter() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.7
                                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                            public final void onAnimationCancel(Animator animator) {
                                                if (i4 != 0) {
                                                    view2.setTranslationX(0.0f);
                                                }
                                                if (i5 != 0) {
                                                    view2.setTranslationY(0.0f);
                                                }
                                            }

                                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                            public final void onAnimationEnd(Animator animator) {
                                                animate2.setListener(null);
                                                DefaultItemAnimator.this.dispatchAnimationFinished(viewHolder2);
                                                DefaultItemAnimator.this.mMoveAnimations.remove(viewHolder2);
                                                DefaultItemAnimator.this.dispatchFinishedWhenDone();
                                                DefaultItemAnimator defaultItemAnimator3 = DefaultItemAnimator.this;
                                                int i6 = defaultItemAnimator3.mPendingAnimFlag;
                                                if ((i6 & 2) != 0) {
                                                    defaultItemAnimator3.mPendingAnimFlag = i6 & (-3);
                                                }
                                                int i7 = defaultItemAnimator3.mPendingAnimFlag;
                                                if ((i7 & 8) != 0) {
                                                    defaultItemAnimator3.mPendingAnimFlag = i7 | 16;
                                                }
                                            }

                                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                            public final void onAnimationStart(Animator animator) {
                                                DefaultItemAnimator.this.getClass();
                                            }
                                        }).start();
                                    }
                                    arrayList2.clear();
                                    DefaultItemAnimator.this.mMovesList.remove(arrayList2);
                                }
                            }.run();
                        }
                        if (!isEmpty3) {
                            final ArrayList arrayList3 = new ArrayList();
                            arrayList3.addAll(defaultItemAnimator.mPendingChanges);
                            defaultItemAnimator.mChangesList.add(arrayList3);
                            defaultItemAnimator.mPendingChanges.clear();
                            new Runnable() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ArrayList arrayList4 = arrayList3;
                                    int size2 = arrayList4.size();
                                    int i3 = 0;
                                    while (i3 < size2) {
                                        Object obj2 = arrayList4.get(i3);
                                        i3++;
                                        final ChangeInfo changeInfo = (ChangeInfo) obj2;
                                        final DefaultItemAnimator defaultItemAnimator2 = DefaultItemAnimator.this;
                                        defaultItemAnimator2.getClass();
                                        RecyclerView.ViewHolder viewHolder2 = changeInfo.oldHolder;
                                        final View view2 = viewHolder2 == null ? null : viewHolder2.itemView;
                                        RecyclerView.ViewHolder viewHolder3 = changeInfo.newHolder;
                                        final View view3 = viewHolder3 != null ? viewHolder3.itemView : null;
                                        if (view2 != null) {
                                            final ViewPropertyAnimator duration = view2.animate().setDuration(400L);
                                            defaultItemAnimator2.mChangeAnimations.add(changeInfo.oldHolder);
                                            duration.translationX(changeInfo.toX - changeInfo.fromX);
                                            duration.translationY(changeInfo.toY - changeInfo.fromY);
                                            duration.alpha(0.0f).setDuration(400L).setInterpolator(DefaultItemAnimator.ITEM_MOVE_INTERPOLATOR).setListener(new AnimatorListenerAdapter() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.8
                                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                                public final void onAnimationEnd(Animator animator) {
                                                    duration.setListener(null);
                                                    view2.setAlpha(1.0f);
                                                    view2.setTranslationX(0.0f);
                                                    view2.setTranslationY(0.0f);
                                                    DefaultItemAnimator.this.dispatchAnimationFinished(changeInfo.oldHolder);
                                                    DefaultItemAnimator.this.mChangeAnimations.remove(changeInfo.oldHolder);
                                                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                                                    DefaultItemAnimator defaultItemAnimator3 = DefaultItemAnimator.this;
                                                    int i4 = defaultItemAnimator3.mPendingAnimFlag;
                                                    if ((i4 & 4) != 0) {
                                                        defaultItemAnimator3.mPendingAnimFlag = i4 & (-5);
                                                    }
                                                }

                                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                                public final void onAnimationStart(Animator animator) {
                                                    DefaultItemAnimator defaultItemAnimator3 = DefaultItemAnimator.this;
                                                    RecyclerView.ViewHolder viewHolder4 = changeInfo.oldHolder;
                                                    defaultItemAnimator3.getClass();
                                                }
                                            }).start();
                                        }
                                        if (view3 != null) {
                                            final ViewPropertyAnimator animate2 = view3.animate();
                                            defaultItemAnimator2.mChangeAnimations.add(changeInfo.newHolder);
                                            animate2.translationX(0.0f).translationY(0.0f).setDuration(400L).alpha(1.0f).setInterpolator(DefaultItemAnimator.ITEM_MOVE_INTERPOLATOR).setListener(new AnimatorListenerAdapter() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.9
                                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                                public final void onAnimationEnd(Animator animator) {
                                                    animate2.setListener(null);
                                                    view3.setAlpha(1.0f);
                                                    view3.setTranslationX(0.0f);
                                                    view3.setTranslationY(0.0f);
                                                    DefaultItemAnimator.this.dispatchAnimationFinished(changeInfo.newHolder);
                                                    DefaultItemAnimator.this.mChangeAnimations.remove(changeInfo.newHolder);
                                                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                                                }

                                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                                public final void onAnimationStart(Animator animator) {
                                                    DefaultItemAnimator defaultItemAnimator3 = DefaultItemAnimator.this;
                                                    RecyclerView.ViewHolder viewHolder4 = changeInfo.newHolder;
                                                    defaultItemAnimator3.getClass();
                                                }
                                            }).start();
                                        }
                                    }
                                    arrayList3.clear();
                                    DefaultItemAnimator.this.mChangesList.remove(arrayList3);
                                }
                            }.run();
                        }
                        if (!isEmpty4) {
                            final ArrayList arrayList4 = new ArrayList();
                            arrayList4.addAll(defaultItemAnimator.mPendingAdditions);
                            defaultItemAnimator.mAdditionsList.add(arrayList4);
                            defaultItemAnimator.mPendingAdditions.clear();
                            Runnable runnable = new Runnable() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ArrayList arrayList5 = arrayList4;
                                    int size2 = arrayList5.size();
                                    int i3 = 0;
                                    while (i3 < size2) {
                                        Object obj2 = arrayList5.get(i3);
                                        i3++;
                                        final RecyclerView.ViewHolder viewHolder2 = (RecyclerView.ViewHolder) obj2;
                                        final DefaultItemAnimator defaultItemAnimator2 = DefaultItemAnimator.this;
                                        defaultItemAnimator2.getClass();
                                        final View view2 = viewHolder2.itemView;
                                        final ViewPropertyAnimator animate2 = view2.animate();
                                        long j2 = (view2.getTag() == null || !view2.getTag().equals("preferencecategory")) ? 200L : 0L;
                                        defaultItemAnimator2.mAddAnimations.add(viewHolder2);
                                        animate2.alpha(1.0f).setDuration(j2).setListener(new AnimatorListenerAdapter() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.5
                                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                            public final void onAnimationCancel(Animator animator) {
                                                view2.setAlpha(1.0f);
                                            }

                                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                            public final void onAnimationEnd(Animator animator) {
                                                animate2.setListener(null);
                                                DefaultItemAnimator.this.dispatchAnimationFinished(viewHolder2);
                                                DefaultItemAnimator.this.mAddAnimations.remove(viewHolder2);
                                                DefaultItemAnimator.this.dispatchFinishedWhenDone();
                                                DefaultItemAnimator defaultItemAnimator3 = DefaultItemAnimator.this;
                                                int i4 = defaultItemAnimator3.mPendingAnimFlag;
                                                if ((i4 & 8) != 0) {
                                                    defaultItemAnimator3.mPendingAnimFlag = i4 & (-9);
                                                }
                                                int i5 = defaultItemAnimator3.mPendingAnimFlag;
                                                if ((i5 & 16) != 0) {
                                                    defaultItemAnimator3.mPendingAnimFlag = i5 & (-17);
                                                }
                                            }

                                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                            public final void onAnimationStart(Animator animator) {
                                                DefaultItemAnimator.this.getClass();
                                            }
                                        }).start();
                                    }
                                    arrayList4.clear();
                                    DefaultItemAnimator.this.mAdditionsList.remove(arrayList4);
                                }
                            };
                            if (isEmpty && isEmpty2 && isEmpty3) {
                                runnable.run();
                            } else {
                                View view2 = ((ViewHolder) arrayList4.get(0)).itemView;
                                if (view2.getTag() == null || !view2.getTag().equals("preferencecategory")) {
                                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                    view2.postOnAnimationDelayed(runnable, 100L);
                                } else {
                                    runnable.run();
                                }
                            }
                        }
                    }
                }
                RecyclerView.this.mPostedAnimatorRunner = false;
            }
        };
        this.mLastAutoMeasureNonExactMeasuredWidth = 0;
        this.mLastAutoMeasureNonExactMeasuredHeight = 0;
        this.mViewInfoProcessCallback = new AnonymousClass9();
        new Runnable() { // from class: androidx.recyclerview.widget.RecyclerView.18
            @Override // java.lang.Runnable
            public final void run() {
                RecyclerView.this.ensureTopGlow();
                RecyclerView.this.mTopGlow.onAbsorb(10000);
                RecyclerView.this.invalidate();
            }
        };
        this.mIsRecoilEnabled = true;
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        this.mContext = context;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        Resources resources = context.getResources();
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        viewConfiguration.getScaledTouchSlop();
        viewConfiguration.getScaledPagingTouchSlop();
        this.mScaledHorizontalScrollFactor = viewConfiguration.getScaledHorizontalScrollFactor();
        this.mScaledVerticalScrollFactor = viewConfiguration.getScaledVerticalScrollFactor();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mHoverTopAreaHeight = (int) (TypedValue.applyDimension(1, 25.0f, resources.getDisplayMetrics()) + 0.5f);
        this.mHoverBottomAreaHeight = (int) (TypedValue.applyDimension(1, 25.0f, resources.getDisplayMetrics()) + 0.5f);
        this.mGoToTopBottomPadding = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_go_to_top_scrollable_view_gap);
        this.mGoToTopSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_go_to_top_scrollable_view_size);
        this.mGoToTopElevation = resources.getDimensionPixelSize(com.android.systemui.R.dimen.sesl_go_to_top_elevation);
        this.mIsRecoilSupported = true;
        this.mItemAnimatorHolder = new SeslRecoilAnimator.Holder(context);
        this.mPhysicalCoef = context.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
        setWillNotDraw(getOverScrollMode() == 2);
        this.mItemAnimator.mListener = itemAnimatorRestoreListener;
        this.mAdapterHelper = new AdapterHelper(new AnonymousClass11());
        this.mChildHelper = new ChildHelper(new AnonymousClass10());
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api26Impl.getImportantForAutofill(this) == 0) {
            ViewCompat.Api26Impl.setImportantForAutofill(this, 8);
        }
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        this.mAccessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate = new RecyclerViewAccessibilityDelegate(this);
        this.mAccessibilityDelegate = recyclerViewAccessibilityDelegate;
        ViewCompat.setAccessibilityDelegate(this, recyclerViewAccessibilityDelegate);
        int[] iArr = R$styleable.RecyclerView;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, i, 0);
        String string = obtainStyledAttributes.getString(8);
        if (obtainStyledAttributes.getInt(2, -1) == -1) {
            setDescendantFocusability(262144);
        }
        this.mClipToPadding = obtainStyledAttributes.getBoolean(1, true);
        if (obtainStyledAttributes.getBoolean(3, false)) {
            initFastScroller((StateListDrawable) obtainStyledAttributes.getDrawable(6), obtainStyledAttributes.getDrawable(7), (StateListDrawable) obtainStyledAttributes.getDrawable(4), obtainStyledAttributes.getDrawable(5));
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
                        objArr = new Object[]{context, attributeSet, Integer.valueOf(i), 0};
                    } catch (NoSuchMethodException e) {
                        try {
                            Class[] clsArr = new Class[0];
                            constructor = asSubclass.getConstructor(null);
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
        int[] iArr2 = NESTED_SCROLLING_ATTRS;
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, iArr2, i, 0);
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr2, attributeSet, obtainStyledAttributes2, i, 0);
        boolean z = obtainStyledAttributes2.getBoolean(0, true);
        obtainStyledAttributes2.recycle();
        Resources resources2 = context.getResources();
        TypedValue typedValue = new TypedValue();
        this.mPenDragBlockImage = resources2.getDrawable(com.android.systemui.R.drawable.sesl_pen_block_selection);
        context.getTheme().resolveAttribute(com.android.systemui.R.attr.roundedCornerColor, typedValue, true);
        int i2 = typedValue.resourceId;
        if (i2 > 0) {
            this.mRectColor = resources2.getColor(i2);
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

    public final void startNestedScroll(int i, int i2) {
        getScrollingChildHelper().startNestedScroll(i, i2);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public final void stopNestedScroll(int i) {
        getScrollingChildHelper().stopNestedScroll(i);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LayoutParams extends ViewGroup.MarginLayoutParams {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class OnScrollListener {
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        }
    }
}
