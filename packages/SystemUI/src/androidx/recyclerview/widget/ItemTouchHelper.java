package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.reflect.view.SeslHapticFeedbackConstantsReflector;
import com.android.systemui.R;
import com.android.systemui.controls.management.adapter.StatefulControlAdapter$itemTouchHelperCallback$1;
import com.android.systemui.controls.management.model.ReorderStructureModel$itemTouchHelper$1;
import com.android.systemui.util.DelayableMarqueeTextView;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class ItemTouchHelper extends RecyclerView.ItemDecoration implements RecyclerView.OnChildAttachStateChangeListener {
    public final Callback mCallback;
    public List mDistances;
    public long mDragScrollStartTimeInMs;
    public float mDx;
    public float mDy;
    public GestureDetectorCompat mGestureDetector;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public ItemTouchHelperGestureListener mItemTouchHelperGestureListener;
    public float mMaxSwipeVelocity;
    public RecyclerView mRecyclerView;
    public int mSelectedFlags;
    public float mSelectedStartX;
    public float mSelectedStartY;
    public int mSlop;
    public List mSwapTargets;
    public float mSwipeEscapeVelocity;
    public Rect mTmpRect;
    public VelocityTracker mVelocityTracker;
    public final List mPendingCleanup = new ArrayList();
    public final float[] mTmpPosition = new float[2];
    public RecyclerView.ViewHolder mSelected = null;
    public int mActivePointerId = -1;
    public int mActionState = 0;
    public final List mRecoverAnimations = new ArrayList();
    public final AnonymousClass1 mScrollRunnable = new AnonymousClass1();
    public View mOverdrawChild = null;
    public final AnonymousClass2 mOnItemTouchListener = new RecyclerView.OnItemTouchListener() { // from class: androidx.recyclerview.widget.ItemTouchHelper.2
        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public final boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            int iFindPointerIndex;
            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
            itemTouchHelper.mGestureDetector.mDetector.onTouchEvent(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            RecoverAnimation recoverAnimation = null;
            if (actionMasked == 0) {
                itemTouchHelper.mActivePointerId = motionEvent.getPointerId(0);
                itemTouchHelper.mInitialTouchX = motionEvent.getX();
                Log.i("ItemTouchHelper", "onInterceptTouchEvent: #1 set mInitialTouchX = " + itemTouchHelper.mInitialTouchX);
                itemTouchHelper.mInitialTouchY = motionEvent.getY();
                VelocityTracker velocityTracker = itemTouchHelper.mVelocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                }
                itemTouchHelper.mVelocityTracker = VelocityTracker.obtain();
                if (itemTouchHelper.mSelected == null) {
                    if (!((ArrayList) itemTouchHelper.mRecoverAnimations).isEmpty()) {
                        View viewFindChildView = itemTouchHelper.findChildView(motionEvent);
                        int size = ((ArrayList) itemTouchHelper.mRecoverAnimations).size() - 1;
                        while (true) {
                            if (size < 0) {
                                break;
                            }
                            RecoverAnimation recoverAnimation2 = (RecoverAnimation) ((ArrayList) itemTouchHelper.mRecoverAnimations).get(size);
                            if (recoverAnimation2.mViewHolder.itemView == viewFindChildView) {
                                recoverAnimation = recoverAnimation2;
                                break;
                            }
                            size--;
                        }
                    }
                    if (recoverAnimation != null) {
                        Log.i("ItemTouchHelper", "onInterceptTouchEvent: #2 mInitialTouchX = " + itemTouchHelper.mInitialTouchX + " animation.mX = " + recoverAnimation.mX);
                        itemTouchHelper.mInitialTouchX = itemTouchHelper.mInitialTouchX - recoverAnimation.mX;
                        StringBuilder sb = new StringBuilder("onInterceptTouchEvent: #2 set mInitialTouchX = ");
                        sb.append(itemTouchHelper.mInitialTouchX);
                        Log.i("ItemTouchHelper", sb.toString());
                        itemTouchHelper.mInitialTouchY -= recoverAnimation.mY;
                        itemTouchHelper.endRecoverAnimation(recoverAnimation.mViewHolder, true);
                        if (((ArrayList) itemTouchHelper.mPendingCleanup).remove(recoverAnimation.mViewHolder.itemView)) {
                            itemTouchHelper.mCallback.clearView(itemTouchHelper.mRecyclerView, recoverAnimation.mViewHolder);
                        }
                        itemTouchHelper.select(recoverAnimation.mViewHolder, recoverAnimation.mActionState);
                        itemTouchHelper.updateDxDy(itemTouchHelper.mSelectedFlags, 0, motionEvent);
                    }
                }
            } else if (actionMasked == 3 || actionMasked == 1) {
                itemTouchHelper.mActivePointerId = -1;
                itemTouchHelper.select(null, 0);
            } else {
                int i = itemTouchHelper.mActivePointerId;
                if (i != -1 && (iFindPointerIndex = motionEvent.findPointerIndex(i)) >= 0) {
                    itemTouchHelper.checkSelectForSwipe(actionMasked, iFindPointerIndex, motionEvent);
                }
            }
            VelocityTracker velocityTracker2 = itemTouchHelper.mVelocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.addMovement(motionEvent);
            }
            return itemTouchHelper.mSelected != null;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public final void onRequestDisallowInterceptTouchEvent(boolean z) {
            if (z) {
                ItemTouchHelper.this.select(null, 0);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public final void onTouchEvent(MotionEvent motionEvent) {
            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
            itemTouchHelper.mGestureDetector.mDetector.onTouchEvent(motionEvent);
            VelocityTracker velocityTracker = itemTouchHelper.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.addMovement(motionEvent);
            }
            if (itemTouchHelper.mActivePointerId == -1) {
                return;
            }
            int actionMasked = motionEvent.getActionMasked();
            int iFindPointerIndex = motionEvent.findPointerIndex(itemTouchHelper.mActivePointerId);
            if (iFindPointerIndex >= 0) {
                itemTouchHelper.checkSelectForSwipe(actionMasked, iFindPointerIndex, motionEvent);
            }
            RecyclerView.ViewHolder viewHolder = itemTouchHelper.mSelected;
            if (viewHolder == null) {
                return;
            }
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (motionEvent.getButtonState() == 32) {
                        itemTouchHelper.select(null, 0);
                        itemTouchHelper.mActivePointerId = -1;
                        return;
                    } else {
                        if (iFindPointerIndex >= 0) {
                            itemTouchHelper.updateDxDy(itemTouchHelper.mSelectedFlags, iFindPointerIndex, motionEvent);
                            itemTouchHelper.moveIfNecessary(viewHolder);
                            RecyclerView recyclerView = itemTouchHelper.mRecyclerView;
                            AnonymousClass1 anonymousClass1 = itemTouchHelper.mScrollRunnable;
                            recyclerView.removeCallbacks(anonymousClass1);
                            anonymousClass1.run();
                            itemTouchHelper.mRecyclerView.invalidate();
                            return;
                        }
                        return;
                    }
                }
                if (actionMasked != 3) {
                    if (actionMasked != 6) {
                        return;
                    }
                    int actionIndex = motionEvent.getActionIndex();
                    if (motionEvent.getPointerId(actionIndex) == itemTouchHelper.mActivePointerId) {
                        itemTouchHelper.mActivePointerId = motionEvent.getPointerId(actionIndex != 0 ? 0 : 1);
                        itemTouchHelper.updateDxDy(itemTouchHelper.mSelectedFlags, actionIndex, motionEvent);
                        return;
                    }
                    return;
                }
                VelocityTracker velocityTracker2 = itemTouchHelper.mVelocityTracker;
                if (velocityTracker2 != null) {
                    velocityTracker2.clear();
                }
            }
            itemTouchHelper.select(null, 0);
            itemTouchHelper.mActivePointerId = -1;
        }
    };

    /* renamed from: androidx.recyclerview.widget.ItemTouchHelper$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x0079  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x00bf  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void run() {
            int iInterpolateOutOfBoundsScroll;
            int iInterpolateOutOfBoundsScroll2;
            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
            if (itemTouchHelper.mSelected != null) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                long j = itemTouchHelper.mDragScrollStartTimeInMs;
                long j2 = j == Long.MIN_VALUE ? 0L : jCurrentTimeMillis - j;
                RecyclerView.LayoutManager layoutManager = itemTouchHelper.mRecyclerView.mLayout;
                if (itemTouchHelper.mTmpRect == null) {
                    itemTouchHelper.mTmpRect = new Rect();
                }
                layoutManager.calculateItemDecorationsForChild(itemTouchHelper.mTmpRect, itemTouchHelper.mSelected.itemView);
                if (layoutManager.canScrollHorizontally()) {
                    int i = (int) (itemTouchHelper.mSelectedStartX + itemTouchHelper.mDx);
                    int paddingLeft = (i - itemTouchHelper.mTmpRect.left) - itemTouchHelper.mRecyclerView.getPaddingLeft();
                    float f = itemTouchHelper.mDx;
                    iInterpolateOutOfBoundsScroll = ((f >= 0.0f || paddingLeft >= 0) && (f <= 0.0f || (paddingLeft = ((itemTouchHelper.mSelected.itemView.getWidth() + i) + itemTouchHelper.mTmpRect.right) - (itemTouchHelper.mRecyclerView.getWidth() - itemTouchHelper.mRecyclerView.getPaddingRight())) <= 0)) ? 0 : paddingLeft;
                }
                if (layoutManager.canScrollVertically()) {
                    int i2 = (int) (itemTouchHelper.mSelectedStartY + itemTouchHelper.mDy);
                    iInterpolateOutOfBoundsScroll2 = (i2 - itemTouchHelper.mTmpRect.top) - itemTouchHelper.mRecyclerView.getPaddingTop();
                    float f2 = itemTouchHelper.mDy;
                    if ((f2 >= 0.0f || iInterpolateOutOfBoundsScroll2 >= 0) && (f2 <= 0.0f || (iInterpolateOutOfBoundsScroll2 = ((itemTouchHelper.mSelected.itemView.getHeight() + i2) + itemTouchHelper.mTmpRect.bottom) - (itemTouchHelper.mRecyclerView.getHeight() - itemTouchHelper.mRecyclerView.getPaddingBottom())) <= 0)) {
                        iInterpolateOutOfBoundsScroll2 = 0;
                    }
                }
                if (iInterpolateOutOfBoundsScroll != 0) {
                    RecyclerView recyclerView = itemTouchHelper.mRecyclerView;
                    int width = itemTouchHelper.mSelected.itemView.getWidth();
                    itemTouchHelper.mRecyclerView.getWidth();
                    iInterpolateOutOfBoundsScroll = itemTouchHelper.mCallback.interpolateOutOfBoundsScroll(recyclerView, width, iInterpolateOutOfBoundsScroll, j2);
                }
                int i3 = iInterpolateOutOfBoundsScroll;
                if (iInterpolateOutOfBoundsScroll2 != 0) {
                    RecyclerView recyclerView2 = itemTouchHelper.mRecyclerView;
                    int height = itemTouchHelper.mSelected.itemView.getHeight();
                    itemTouchHelper.mRecyclerView.getHeight();
                    iInterpolateOutOfBoundsScroll2 = itemTouchHelper.mCallback.interpolateOutOfBoundsScroll(recyclerView2, height, iInterpolateOutOfBoundsScroll2, j2);
                }
                if (i3 == 0 && iInterpolateOutOfBoundsScroll2 == 0) {
                    itemTouchHelper.mDragScrollStartTimeInMs = Long.MIN_VALUE;
                    return;
                }
                if (itemTouchHelper.mDragScrollStartTimeInMs == Long.MIN_VALUE) {
                    itemTouchHelper.mDragScrollStartTimeInMs = jCurrentTimeMillis;
                }
                itemTouchHelper.mRecyclerView.scrollBy(i3, iInterpolateOutOfBoundsScroll2);
                ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                RecyclerView.ViewHolder viewHolder = itemTouchHelper2.mSelected;
                if (viewHolder != null) {
                    itemTouchHelper2.moveIfNecessary(viewHolder);
                }
                ItemTouchHelper itemTouchHelper3 = ItemTouchHelper.this;
                itemTouchHelper3.mRecyclerView.removeCallbacks(itemTouchHelper3.mScrollRunnable);
                RecyclerView recyclerView3 = ItemTouchHelper.this.mRecyclerView;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                recyclerView3.postOnAnimation(this);
            }
        }
    }

    public class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean mShouldReactToLongPress = true;

        public ItemTouchHelperGestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final void onLongPress(MotionEvent motionEvent) {
            View viewFindChildView;
            RecyclerView.ViewHolder childViewHolder;
            if (!this.mShouldReactToLongPress || (viewFindChildView = ItemTouchHelper.this.findChildView(motionEvent)) == null || (childViewHolder = ItemTouchHelper.this.mRecyclerView.getChildViewHolder(viewFindChildView)) == null) {
                return;
            }
            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
            Callback callback = itemTouchHelper.mCallback;
            RecyclerView recyclerView = itemTouchHelper.mRecyclerView;
            int movementFlags = callback.getMovementFlags(childViewHolder);
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if ((Callback.convertToAbsoluteDirection(movementFlags, recyclerView.getLayoutDirection()) & 16711680) == 0) {
                childViewHolder.itemView.announceForAccessibility(ItemTouchHelper.this.mRecyclerView.getContext().getString(R.string.dragndroplist_item_cannot_be_dragged, Integer.valueOf(childViewHolder.getLayoutPosition() + 1)));
                return;
            }
            int pointerId = motionEvent.getPointerId(0);
            int i = ItemTouchHelper.this.mActivePointerId;
            if (pointerId == i) {
                int iFindPointerIndex = motionEvent.findPointerIndex(i);
                float x = motionEvent.getX(iFindPointerIndex);
                float y = motionEvent.getY(iFindPointerIndex);
                ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                itemTouchHelper2.mInitialTouchX = x;
                itemTouchHelper2.mInitialTouchY = y;
                itemTouchHelper2.mDy = 0.0f;
                itemTouchHelper2.mDx = 0.0f;
                if (itemTouchHelper2.mCallback.isLongPressDragEnabled()) {
                    ItemTouchHelper.this.select(childViewHolder, 2);
                }
            }
        }
    }

    public abstract class SimpleCallback extends Callback {
        public final int mDefaultDragDirs;
        public int mDefaultSwipeDirs;

        public SimpleCallback(int i, int i2) {
            this.mDefaultSwipeDirs = i2;
            this.mDefaultDragDirs = i;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public int getMovementFlags(RecyclerView.ViewHolder viewHolder) {
            return Callback.makeMovementFlags(this.mDefaultDragDirs, this.mDefaultSwipeDirs);
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.recyclerview.widget.ItemTouchHelper$2] */
    public ItemTouchHelper(Callback callback) {
        this.mCallback = callback;
    }

    public static boolean hitTest(View view, float f, float f2, float f3, float f4) {
        return f >= f3 && f <= f3 + ((float) view.getWidth()) && f2 >= f4 && f2 <= f4 + ((float) view.getHeight());
    }

    public final void attachToRecyclerView(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.mRecyclerView;
        if (recyclerView2 == recyclerView) {
            return;
        }
        AnonymousClass2 anonymousClass2 = this.mOnItemTouchListener;
        if (recyclerView2 != null) {
            recyclerView2.removeItemDecoration(this);
            RecyclerView recyclerView3 = this.mRecyclerView;
            recyclerView3.mOnItemTouchListeners.remove(anonymousClass2);
            if (recyclerView3.mInterceptingOnItemTouchListener == anonymousClass2) {
                recyclerView3.mInterceptingOnItemTouchListener = null;
            }
            List list = this.mRecyclerView.mOnChildAttachStateListeners;
            if (list != null) {
                ((ArrayList) list).remove(this);
            }
            int size = ((ArrayList) this.mRecoverAnimations).size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                }
                RecoverAnimation recoverAnimation = (RecoverAnimation) ((ArrayList) this.mRecoverAnimations).get(0);
                recoverAnimation.mValueAnimator.cancel();
                this.mCallback.clearView(this.mRecyclerView, recoverAnimation.mViewHolder);
            }
            ((ArrayList) this.mRecoverAnimations).clear();
            this.mOverdrawChild = null;
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            ItemTouchHelperGestureListener itemTouchHelperGestureListener = this.mItemTouchHelperGestureListener;
            if (itemTouchHelperGestureListener != null) {
                itemTouchHelperGestureListener.mShouldReactToLongPress = false;
                this.mItemTouchHelperGestureListener = null;
            }
            if (this.mGestureDetector != null) {
                this.mGestureDetector = null;
            }
        }
        this.mRecyclerView = recyclerView;
        if (recyclerView != null) {
            Resources resources = recyclerView.getResources();
            this.mSwipeEscapeVelocity = resources.getDimension(R.dimen.item_touch_helper_swipe_escape_velocity);
            this.mMaxSwipeVelocity = resources.getDimension(R.dimen.item_touch_helper_swipe_escape_max_velocity);
            ViewConfiguration viewConfiguration = ViewConfiguration.get(this.mRecyclerView.getContext());
            this.mSlop = viewConfiguration.getScaledTouchSlop();
            viewConfiguration.getScaledTouchSlop();
            viewConfiguration.getScaledPagingTouchSlop();
            this.mRecyclerView.addItemDecoration(this);
            this.mRecyclerView.mOnItemTouchListeners.add(anonymousClass2);
            RecyclerView recyclerView4 = this.mRecyclerView;
            if (recyclerView4.mOnChildAttachStateListeners == null) {
                recyclerView4.mOnChildAttachStateListeners = new ArrayList();
            }
            ((ArrayList) recyclerView4.mOnChildAttachStateListeners).add(this);
            this.mItemTouchHelperGestureListener = new ItemTouchHelperGestureListener();
            this.mGestureDetector = new GestureDetectorCompat(this.mRecyclerView.getContext(), this.mItemTouchHelperGestureListener);
        }
    }

    public final int checkHorizontalSwipe(RecyclerView.ViewHolder viewHolder, int i) {
        if ((i & 12) == 0) {
            return 0;
        }
        int i2 = this.mDx > 0.0f ? 8 : 4;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        Callback callback = this.mCallback;
        if (velocityTracker != null && this.mActivePointerId > -1) {
            float f = this.mMaxSwipeVelocity;
            callback.getClass();
            velocityTracker.computeCurrentVelocity(1000, f);
            float xVelocity = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
            float yVelocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
            int i3 = xVelocity > 0.0f ? 8 : 4;
            float fAbs = Math.abs(xVelocity);
            if ((i3 & i) != 0 && i2 == i3 && fAbs >= callback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && fAbs > Math.abs(yVelocity)) {
                return i3;
            }
        }
        float swipeThreshold = callback.getSwipeThreshold() * this.mRecyclerView.getWidth();
        if ((i & i2) == 0 || Math.abs(this.mDx) <= swipeThreshold) {
            return 0;
        }
        return i2;
    }

    public final void checkSelectForSwipe(int i, int i2, MotionEvent motionEvent) {
        View viewFindChildView;
        if (this.mSelected == null && i == 2 && this.mActionState != 2) {
            Callback callback = this.mCallback;
            if (callback.isItemViewSwipeEnabled()) {
                RecyclerView recyclerView = this.mRecyclerView;
                if (recyclerView.mScrollState == 1) {
                    return;
                }
                RecyclerView.LayoutManager layoutManager = recyclerView.mLayout;
                int i3 = this.mActivePointerId;
                RecyclerView.ViewHolder childViewHolder = null;
                if (i3 != -1) {
                    int iFindPointerIndex = motionEvent.findPointerIndex(i3);
                    float x = motionEvent.getX(iFindPointerIndex) - this.mInitialTouchX;
                    float y = motionEvent.getY(iFindPointerIndex) - this.mInitialTouchY;
                    float fAbs = Math.abs(x);
                    float fAbs2 = Math.abs(y);
                    float f = this.mSlop;
                    if ((fAbs >= f || fAbs2 >= f) && ((fAbs <= fAbs2 || !layoutManager.canScrollHorizontally()) && ((fAbs2 <= fAbs || !layoutManager.canScrollVertically()) && (viewFindChildView = findChildView(motionEvent)) != null))) {
                        childViewHolder = this.mRecyclerView.getChildViewHolder(viewFindChildView);
                    }
                }
                if (childViewHolder == null) {
                    return;
                }
                RecyclerView recyclerView2 = this.mRecyclerView;
                int movementFlags = callback.getMovementFlags(childViewHolder);
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                int iConvertToAbsoluteDirection = (Callback.convertToAbsoluteDirection(movementFlags, recyclerView2.getLayoutDirection()) & 65280) >> 8;
                if (iConvertToAbsoluteDirection == 0) {
                    return;
                }
                float x2 = motionEvent.getX(i2);
                float y2 = motionEvent.getY(i2);
                float f2 = x2 - this.mInitialTouchX;
                float f3 = y2 - this.mInitialTouchY;
                float fAbs3 = Math.abs(f2);
                float fAbs4 = Math.abs(f3);
                float f4 = this.mSlop;
                if (fAbs3 >= f4 || fAbs4 >= f4) {
                    if (fAbs3 > fAbs4) {
                        if (f2 < 0.0f && (iConvertToAbsoluteDirection & 4) == 0) {
                            return;
                        }
                        if (f2 > 0.0f && (iConvertToAbsoluteDirection & 8) == 0) {
                            return;
                        }
                    } else {
                        if (f3 < 0.0f && (iConvertToAbsoluteDirection & 1) == 0) {
                            return;
                        }
                        if (f3 > 0.0f && (iConvertToAbsoluteDirection & 2) == 0) {
                            return;
                        }
                    }
                    this.mDy = 0.0f;
                    this.mDx = 0.0f;
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    select(childViewHolder, 1);
                }
            }
        }
    }

    public final int checkVerticalSwipe(RecyclerView.ViewHolder viewHolder, int i) {
        if ((i & 3) == 0) {
            return 0;
        }
        int i2 = this.mDy > 0.0f ? 2 : 1;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        Callback callback = this.mCallback;
        if (velocityTracker != null && this.mActivePointerId > -1) {
            float f = this.mMaxSwipeVelocity;
            callback.getClass();
            velocityTracker.computeCurrentVelocity(1000, f);
            float xVelocity = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
            float yVelocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
            int i3 = yVelocity > 0.0f ? 2 : 1;
            float fAbs = Math.abs(yVelocity);
            if ((i3 & i) != 0 && i3 == i2 && fAbs >= callback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && fAbs > Math.abs(xVelocity)) {
                return i3;
            }
        }
        float swipeThreshold = callback.getSwipeThreshold() * this.mRecyclerView.getHeight();
        if ((i & i2) == 0 || Math.abs(this.mDy) <= swipeThreshold) {
            return 0;
        }
        return i2;
    }

    public final void endRecoverAnimation(RecyclerView.ViewHolder viewHolder, boolean z) {
        for (int size = ((ArrayList) this.mRecoverAnimations).size() - 1; size >= 0; size--) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) ((ArrayList) this.mRecoverAnimations).get(size);
            if (recoverAnimation.mViewHolder == viewHolder) {
                recoverAnimation.mOverridden |= z;
                if (!recoverAnimation.mEnded) {
                    recoverAnimation.mValueAnimator.cancel();
                }
                ((ArrayList) this.mRecoverAnimations).remove(size);
                return;
            }
        }
    }

    public final View findChildView(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        RecyclerView.ViewHolder viewHolder = this.mSelected;
        if (viewHolder != null) {
            View view = viewHolder.itemView;
            if (hitTest(view, x, y, this.mSelectedStartX + this.mDx, this.mSelectedStartY + this.mDy)) {
                return view;
            }
        }
        for (int size = ((ArrayList) this.mRecoverAnimations).size() - 1; size >= 0; size--) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) ((ArrayList) this.mRecoverAnimations).get(size);
            View view2 = recoverAnimation.mViewHolder.itemView;
            if (hitTest(view2, x, y, recoverAnimation.mX, recoverAnimation.mY)) {
                return view2;
            }
        }
        return this.mRecyclerView.findChildViewUnder(x, y);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        rect.setEmpty();
    }

    public final void getSelectedDxDy(float[] fArr, int i) {
        if ((this.mSelectedFlags & 12) != 0) {
            fArr[0] = (this.mSelectedStartX + this.mDx) - this.mSelected.itemView.getLeft();
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "getSelectedDxDy: #1 calledBy = ", " outPosition[0] = ");
            sbM.append(fArr[0]);
            sbM.append(", mSelectedStartX = ");
            sbM.append(this.mSelectedStartX);
            sbM.append(", mDx = ");
            sbM.append(this.mDx);
            sbM.append(", mSelected.itemView.getLeft() = ");
            sbM.append(this.mSelected.itemView.getLeft());
            Log.i("ItemTouchHelper", sbM.toString());
        } else {
            fArr[0] = this.mSelected.itemView.getTranslationX();
            StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "getSelectedDxDy: #2 calledBy = ", " outPosition[0] = ");
            sbM2.append(this.mSelected.itemView.getTranslationX());
            Log.i("ItemTouchHelper", sbM2.toString());
        }
        if ((this.mSelectedFlags & 3) != 0) {
            fArr[1] = (this.mSelectedStartY + this.mDy) - this.mSelected.itemView.getTop();
        } else {
            fArr[1] = this.mSelected.itemView.getTranslationY();
        }
    }

    public final void moveIfNecessary(RecyclerView.ViewHolder viewHolder) {
        int i;
        int i2;
        int iAbs;
        int iAbs2;
        int left;
        int iAbs3;
        int right;
        int iAbs4;
        int i3;
        int i4;
        if (!this.mRecyclerView.isLayoutRequested() && this.mActionState == 2) {
            Callback callback = this.mCallback;
            callback.getClass();
            int i5 = (int) (this.mSelectedStartX + this.mDx);
            int i6 = (int) (this.mSelectedStartY + this.mDy);
            if (Math.abs(i6 - viewHolder.itemView.getTop()) >= viewHolder.itemView.getHeight() * 0.5f || Math.abs(i5 - viewHolder.itemView.getLeft()) >= viewHolder.itemView.getWidth() * 0.5f) {
                List list = this.mSwapTargets;
                if (list == null) {
                    this.mSwapTargets = new ArrayList();
                    this.mDistances = new ArrayList();
                } else {
                    ((ArrayList) list).clear();
                    ((ArrayList) this.mDistances).clear();
                }
                int iRound = Math.round(this.mSelectedStartX + this.mDx);
                int iRound2 = Math.round(this.mSelectedStartY + this.mDy);
                int width = viewHolder.itemView.getWidth() + iRound;
                int height = viewHolder.itemView.getHeight() + iRound2;
                int i7 = (iRound + width) / 2;
                int i8 = (iRound2 + height) / 2;
                RecyclerView.LayoutManager layoutManager = this.mRecyclerView.mLayout;
                int childCount = layoutManager.getChildCount();
                Rect rect = new Rect(0, 0, this.mRecyclerView.getWidth(), this.mRecyclerView.getHeight());
                Rect rect2 = new Rect(iRound, iRound2, width, height);
                if ((layoutManager instanceof LinearLayoutManager) && layoutManager.canScrollVertically()) {
                    if (iRound < 0) {
                        rect2.right -= iRound;
                        rect2.left = 0;
                        i2 = 0;
                    } else {
                        i2 = 1;
                    }
                    if (width > this.mRecyclerView.getWidth()) {
                        rect2.left -= width - this.mRecyclerView.getWidth();
                        rect2.right = this.mRecyclerView.getWidth();
                        i2 = 0;
                    }
                    if (iRound2 < 0) {
                        rect2.bottom -= iRound2;
                        i = 0;
                        rect2.top = 0;
                        i2 = 0;
                    } else {
                        i = 0;
                    }
                    if (height > this.mRecyclerView.getHeight()) {
                        rect2.top -= height - this.mRecyclerView.getHeight();
                        rect2.bottom = this.mRecyclerView.getHeight();
                        i2 = i;
                    }
                } else {
                    i = 0;
                    i2 = 1;
                }
                int i9 = i;
                while (i9 < childCount) {
                    View childAt = layoutManager.getChildAt(i9);
                    if (childAt == null || childAt == viewHolder.itemView) {
                        i3 = i6;
                        i4 = i2;
                    } else {
                        i3 = i6;
                        i4 = i2;
                        Rect rect3 = new Rect(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom());
                        if (Rect.intersects(rect2, rect3) && (i4 != 0 || rect.contains(rect3))) {
                            RecyclerView.ViewHolder childViewHolder = this.mRecyclerView.getChildViewHolder(childAt);
                            if (callback.canDropOver(this.mSelected, childViewHolder)) {
                                int iAbs5 = Math.abs(i7 - ((childAt.getRight() + childAt.getLeft()) / 2));
                                int iAbs6 = Math.abs(i8 - ((childAt.getBottom() + childAt.getTop()) / 2));
                                int i10 = (iAbs6 * iAbs6) + (iAbs5 * iAbs5);
                                int size = ((ArrayList) this.mSwapTargets).size();
                                int i11 = 0;
                                for (int i12 = 0; i12 < size && i10 > ((Integer) ((ArrayList) this.mDistances).get(i12)).intValue(); i12++) {
                                    i11++;
                                }
                                ((ArrayList) this.mSwapTargets).add(i11, childViewHolder);
                                ((ArrayList) this.mDistances).add(i11, Integer.valueOf(i10));
                            }
                        }
                    }
                    i9++;
                    i6 = i3;
                    i2 = i4;
                }
                int i13 = i6;
                ArrayList arrayList = (ArrayList) this.mSwapTargets;
                if (arrayList.size() == 0) {
                    return;
                }
                int width2 = viewHolder.itemView.getWidth() + i5;
                int height2 = viewHolder.itemView.getHeight() + i13;
                int left2 = i5 - viewHolder.itemView.getLeft();
                int top = i13 - viewHolder.itemView.getTop();
                int size2 = arrayList.size();
                RecyclerView.ViewHolder viewHolder2 = null;
                int i14 = -1;
                for (int i15 = 0; i15 < size2; i15++) {
                    RecyclerView.ViewHolder viewHolder3 = (RecyclerView.ViewHolder) arrayList.get(i15);
                    if (left2 > 0 && (right = viewHolder3.itemView.getRight() - width2) < 0 && viewHolder3.itemView.getRight() > viewHolder.itemView.getRight() && (iAbs4 = Math.abs(right)) > i14) {
                        i14 = iAbs4;
                        viewHolder2 = viewHolder3;
                    }
                    if (left2 < 0 && (left = viewHolder3.itemView.getLeft() - i5) > 0 && viewHolder3.itemView.getLeft() < viewHolder.itemView.getLeft() && (iAbs3 = Math.abs(left)) > i14) {
                        i14 = iAbs3;
                        viewHolder2 = viewHolder3;
                    }
                    if (top < 0) {
                        int bottom = (viewHolder3.itemView.getBottom() + viewHolder3.itemView.getTop()) / 2;
                        int bottom2 = (viewHolder.itemView.getBottom() + viewHolder.itemView.getTop()) / 2;
                        int i16 = bottom - i13;
                        if (i16 > 0 && bottom < bottom2 && (iAbs2 = Math.abs(i16)) > i14) {
                            i14 = iAbs2;
                            viewHolder2 = viewHolder3;
                        }
                    }
                    if (top > 0) {
                        int bottom3 = (viewHolder3.itemView.getBottom() + viewHolder3.itemView.getTop()) / 2;
                        int bottom4 = (viewHolder.itemView.getBottom() + viewHolder.itemView.getTop()) / 2;
                        int i17 = bottom3 - height2;
                        if (i17 < 0 && bottom3 > bottom4 && (iAbs = Math.abs(i17)) > i14) {
                            i14 = iAbs;
                            viewHolder2 = viewHolder3;
                        }
                    }
                }
                if (viewHolder2 == null) {
                    ((ArrayList) this.mSwapTargets).clear();
                    ((ArrayList) this.mDistances).clear();
                    return;
                }
                int absoluteAdapterPosition = viewHolder2.getAbsoluteAdapterPosition();
                viewHolder.getAbsoluteAdapterPosition();
                if (callback.onMove(viewHolder, viewHolder2)) {
                    RecyclerView recyclerView = this.mRecyclerView;
                    RecyclerView.LayoutManager layoutManager2 = recyclerView.mLayout;
                    if (layoutManager2 instanceof LinearLayoutManager) {
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager2;
                        View view = viewHolder.itemView;
                        View view2 = viewHolder2.itemView;
                        linearLayoutManager.assertNotInLayoutOrScroll("Cannot drop a view during a scroll or layout calculation");
                        linearLayoutManager.ensureLayoutState();
                        linearLayoutManager.resolveShouldLayoutReverse();
                        int position = RecyclerView.LayoutManager.getPosition(view);
                        int position2 = RecyclerView.LayoutManager.getPosition(view2);
                        char c = position < position2 ? (char) 1 : (char) 65535;
                        if (linearLayoutManager.mShouldReverseLayout) {
                            if (c == 1) {
                                linearLayoutManager.scrollToPositionWithOffset(position2, linearLayoutManager.mOrientationHelper.getEndAfterPadding() - (linearLayoutManager.mOrientationHelper.getDecoratedMeasurement(view) + linearLayoutManager.mOrientationHelper.getDecoratedStart(view2)));
                            } else {
                                linearLayoutManager.scrollToPositionWithOffset(position2, linearLayoutManager.mOrientationHelper.getEndAfterPadding() - linearLayoutManager.mOrientationHelper.getDecoratedEnd(view2));
                            }
                        } else if (c == 65535) {
                            linearLayoutManager.scrollToPositionWithOffset(position2, linearLayoutManager.mOrientationHelper.getDecoratedStart(view2) - linearLayoutManager.mOrientationHelper.getStartAfterPadding());
                        } else {
                            linearLayoutManager.scrollToPositionWithOffset(position2, (linearLayoutManager.mOrientationHelper.getDecoratedEnd(view2) - linearLayoutManager.mOrientationHelper.getDecoratedMeasurement(view)) - linearLayoutManager.mOrientationHelper.getStartAfterPadding());
                        }
                    } else {
                        if (layoutManager2.canScrollHorizontally()) {
                            if (layoutManager2.getDecoratedLeft(viewHolder2.itemView) <= recyclerView.getPaddingLeft()) {
                                recyclerView.scrollToPosition(absoluteAdapterPosition);
                            }
                            if (layoutManager2.getDecoratedRight(viewHolder2.itemView) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                                recyclerView.scrollToPosition(absoluteAdapterPosition);
                            }
                        }
                        if (layoutManager2.canScrollVertically()) {
                            if (layoutManager2.getDecoratedTop(viewHolder2.itemView) <= recyclerView.getPaddingTop()) {
                                recyclerView.scrollToPosition(absoluteAdapterPosition);
                            }
                            if (layoutManager2.getDecoratedBottom(viewHolder2.itemView) >= recyclerView.getHeight() - recyclerView.getPaddingBottom()) {
                                recyclerView.scrollToPosition(absoluteAdapterPosition);
                            }
                        }
                    }
                    viewHolder.itemView.performHapticFeedback(SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(41));
                    this.mSelected.itemView.announceForAccessibility(this.mRecyclerView.getContext().getString(R.string.dragndroplist_drag_move, Integer.valueOf(absoluteAdapterPosition + 1)));
                }
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
    public final void onChildViewDetachedFromWindow(View view) {
        if (view == this.mOverdrawChild) {
            this.mOverdrawChild = null;
        }
        RecyclerView.ViewHolder childViewHolder = this.mRecyclerView.getChildViewHolder(view);
        if (childViewHolder == null) {
            return;
        }
        RecyclerView.ViewHolder viewHolder = this.mSelected;
        if (viewHolder != null && childViewHolder == viewHolder) {
            select(null, 0);
            return;
        }
        endRecoverAnimation(childViewHolder, false);
        if (((ArrayList) this.mPendingCleanup).remove(childViewHolder.itemView)) {
            this.mCallback.clearView(this.mRecyclerView, childViewHolder);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        float f;
        float f2;
        if (this.mSelected != null) {
            float[] fArr = this.mTmpPosition;
            getSelectedDxDy(fArr, 2);
            f = fArr[0];
            f2 = fArr[1];
        } else {
            f = 0.0f;
            f2 = 0.0f;
        }
        RecyclerView.ViewHolder viewHolder = this.mSelected;
        List list = this.mRecoverAnimations;
        int i = this.mActionState;
        Callback callback = this.mCallback;
        callback.getClass();
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) arrayList.get(i2);
            float f3 = recoverAnimation.mStartDx;
            float f4 = recoverAnimation.mTargetX;
            if (f3 == f4) {
                recoverAnimation.mX = recoverAnimation.mViewHolder.itemView.getTranslationX();
            } else {
                recoverAnimation.mX = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f4, f3, recoverAnimation.mFraction, f3);
            }
            float f5 = recoverAnimation.mStartDy;
            float f6 = recoverAnimation.mTargetY;
            if (f5 == f6) {
                recoverAnimation.mY = recoverAnimation.mViewHolder.itemView.getTranslationY();
            } else {
                recoverAnimation.mY = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f6, f5, recoverAnimation.mFraction, f5);
            }
            int iSave = canvas.save();
            callback.onChildDraw(canvas, recyclerView, recoverAnimation.mViewHolder, recoverAnimation.mX, recoverAnimation.mY, recoverAnimation.mActionState, false);
            canvas.restoreToCount(iSave);
        }
        if (viewHolder != null) {
            int iSave2 = canvas.save();
            callback.onChildDraw(canvas, recyclerView, viewHolder, f, f2, i, true);
            canvas.restoreToCount(iSave2);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        float f;
        float f2;
        if (this.mSelected != null) {
            float[] fArr = this.mTmpPosition;
            getSelectedDxDy(fArr, 1);
            float f3 = fArr[0];
            f = fArr[1];
            f2 = f3;
        } else {
            f = 0.0f;
            f2 = 0.0f;
        }
        RecyclerView.ViewHolder viewHolder = this.mSelected;
        List list = this.mRecoverAnimations;
        int i = this.mActionState;
        Callback callback = this.mCallback;
        callback.getClass();
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) arrayList.get(i2);
            int iSave = canvas.save();
            callback.onChildDrawOver(canvas, recyclerView, recoverAnimation.mViewHolder, recoverAnimation.mX, recoverAnimation.mY, recoverAnimation.mActionState, false);
            canvas.restoreToCount(iSave);
        }
        if (viewHolder != null) {
            int iSave2 = canvas.save();
            callback.onChildDrawOver(canvas, recyclerView, viewHolder, f2, f, i, true);
            canvas.restoreToCount(iSave2);
        }
        boolean z = false;
        for (int i3 = size - 1; i3 >= 0; i3--) {
            RecoverAnimation recoverAnimation2 = (RecoverAnimation) arrayList.get(i3);
            boolean z2 = recoverAnimation2.mEnded;
            if (z2 && !recoverAnimation2.mIsPendingCleanup) {
                arrayList.remove(i3);
            } else if (!z2) {
                z = true;
            }
        }
        if (z) {
            recyclerView.invalidate();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void select(RecyclerView.ViewHolder viewHolder, int i) {
        Callback callback;
        int i2;
        boolean z;
        RecyclerView.ViewHolder viewHolder2;
        int i3;
        int iCheckVerticalSwipe;
        final int i4;
        float fSignum;
        char c;
        int i5;
        if (viewHolder == this.mSelected && i == this.mActionState) {
            return;
        }
        this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
        int i6 = this.mActionState;
        endRecoverAnimation(viewHolder, true);
        this.mActionState = i;
        if (i == 2) {
            if (viewHolder == null) {
                throw new IllegalArgumentException("Must pass a ViewHolder when dragging");
            }
            this.mOverdrawChild = viewHolder.itemView;
        }
        int i7 = (1 << ((i * 8) + 8)) - 1;
        final RecyclerView.ViewHolder viewHolder3 = this.mSelected;
        Callback callback2 = this.mCallback;
        if (viewHolder3 != null) {
            if (viewHolder3.itemView.getParent() != null) {
                if (i6 == 2) {
                    i4 = 0;
                } else if (this.mActionState == 2) {
                    iCheckVerticalSwipe = 0;
                    i4 = iCheckVerticalSwipe;
                } else {
                    int movementFlags = callback2.getMovementFlags(viewHolder3);
                    RecyclerView recyclerView = this.mRecyclerView;
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    int iConvertToAbsoluteDirection = (Callback.convertToAbsoluteDirection(movementFlags, recyclerView.getLayoutDirection()) & 65280) >> 8;
                    if (iConvertToAbsoluteDirection != 0) {
                        int i8 = (movementFlags & 65280) >> 8;
                        if (Math.abs(this.mDx) > Math.abs(this.mDy)) {
                            iCheckVerticalSwipe = checkHorizontalSwipe(viewHolder3, iConvertToAbsoluteDirection);
                            if (iCheckVerticalSwipe <= 0) {
                                iCheckVerticalSwipe = checkVerticalSwipe(viewHolder3, iConvertToAbsoluteDirection);
                                if (iCheckVerticalSwipe <= 0) {
                                }
                            } else if ((i8 & iCheckVerticalSwipe) == 0) {
                                iCheckVerticalSwipe = Callback.convertToRelativeDirection(iCheckVerticalSwipe, this.mRecyclerView.getLayoutDirection());
                            }
                            i4 = iCheckVerticalSwipe;
                        } else {
                            iCheckVerticalSwipe = checkVerticalSwipe(viewHolder3, iConvertToAbsoluteDirection);
                            if (iCheckVerticalSwipe <= 0) {
                                iCheckVerticalSwipe = checkHorizontalSwipe(viewHolder3, iConvertToAbsoluteDirection);
                                if (iCheckVerticalSwipe > 0) {
                                    if ((i8 & iCheckVerticalSwipe) == 0) {
                                        iCheckVerticalSwipe = Callback.convertToRelativeDirection(iCheckVerticalSwipe, this.mRecyclerView.getLayoutDirection());
                                    }
                                }
                            }
                            i4 = iCheckVerticalSwipe;
                        }
                    }
                }
                VelocityTracker velocityTracker = this.mVelocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    this.mVelocityTracker = null;
                }
                float fSignum2 = 0.0f;
                if (i4 == 1 || i4 == 2) {
                    fSignum = Math.signum(this.mDy) * this.mRecyclerView.getHeight();
                } else if (i4 == 4 || i4 == 8 || i4 == 16 || i4 == 32) {
                    fSignum = 0.0f;
                    fSignum2 = Math.signum(this.mDx) * this.mRecyclerView.getWidth();
                } else {
                    fSignum = 0.0f;
                }
                if (i6 == 2) {
                    c = 0;
                    this.mSelected.itemView.announceForAccessibility(this.mRecyclerView.getContext().getString(R.string.dragndroplist_drag_release, Integer.valueOf(this.mSelected.getLayoutPosition() + 1)));
                    i5 = 8;
                } else {
                    c = 0;
                    i5 = i4 > 0 ? 2 : 4;
                }
                float[] fArr = this.mTmpPosition;
                getSelectedDxDy(fArr, 3);
                boolean z2 = c;
                viewHolder2 = null;
                RecoverAnimation recoverAnimation = new RecoverAnimation(viewHolder3, i5, i6, fArr[c], fArr[1], fSignum2, fSignum) { // from class: androidx.recyclerview.widget.ItemTouchHelper.3
                    @Override // androidx.recyclerview.widget.ItemTouchHelper.RecoverAnimation, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        Log.i("ItemTouchHelper", "select: *** Start RecoverAnimation$onAnimationEnd ***");
                        if (this.mOverridden) {
                            Log.i("ItemTouchHelper", "select: *** End RecoverAnimation$onAnimationEnd *** return #1");
                            return;
                        }
                        TooltipPopup$$ExternalSyntheticOutline0.m(i4, "ItemTouchHelper", new StringBuilder("select$onAnimationEnd: swipeDir = "));
                        if (i4 <= 0) {
                            Log.i("ItemTouchHelper", "select$onAnimationEnd: #2 call mCallback.clearView(mRecyclerView = " + ItemTouchHelper.this.mRecyclerView + ", prevSelected = " + viewHolder3 + ")");
                            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                            itemTouchHelper.mCallback.clearView(itemTouchHelper.mRecyclerView, viewHolder3);
                        } else if (viewHolder3.itemView.isAttachedToWindow()) {
                            ((ArrayList) ItemTouchHelper.this.mPendingCleanup).add(viewHolder3.itemView);
                            this.mIsPendingCleanup = true;
                            if (i4 > 0) {
                                Log.i("ItemTouchHelper", "select$onAnimationEnd: postDispatchSwipe #4");
                                final ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                                final int i9 = i4;
                                itemTouchHelper2.mRecyclerView.post(new Runnable() { // from class: androidx.recyclerview.widget.ItemTouchHelper.4
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        RecyclerView recyclerView2 = ItemTouchHelper.this.mRecyclerView;
                                        if (recyclerView2 != null && recyclerView2.mIsAttached) {
                                            RecoverAnimation recoverAnimation2 = this;
                                            if (!recoverAnimation2.mOverridden && recoverAnimation2.mViewHolder.getAbsoluteAdapterPosition() != -1) {
                                                StringBuilder sb = new StringBuilder("postDispatchSwipe$run: mRecyclerView = ");
                                                sb.append(ItemTouchHelper.this.mRecyclerView);
                                                sb.append(", isAttachedToWindow = ");
                                                sb.append(ItemTouchHelper.this.mRecyclerView.mIsAttached);
                                                sb.append(", !anim.mOverridden = ");
                                                sb.append(!this.mOverridden);
                                                sb.append(", anim.mViewHolder.getAdapterPosition() = ");
                                                sb.append(this.mViewHolder.getBindingAdapterPosition());
                                                Log.i("ItemTouchHelper", sb.toString());
                                                DefaultItemAnimator defaultItemAnimator = ItemTouchHelper.this.mRecyclerView.mItemAnimator;
                                                if (defaultItemAnimator == null || !defaultItemAnimator.isRunning()) {
                                                    ItemTouchHelper itemTouchHelper3 = ItemTouchHelper.this;
                                                    int size = ((ArrayList) itemTouchHelper3.mRecoverAnimations).size();
                                                    for (int i10 = 0; i10 < size; i10++) {
                                                        if (((RecoverAnimation) ((ArrayList) itemTouchHelper3.mRecoverAnimations).get(i10)).mEnded) {
                                                        }
                                                    }
                                                    StringBuilder sb2 = new StringBuilder("postDispatchSwipe$run: mCallback.onSwiped anim.mViewHolder = ");
                                                    sb2.append(this.mViewHolder);
                                                    sb2.append(", anim.mViewHolder.itemView = ");
                                                    sb2.append(this.mViewHolder.itemView);
                                                    sb2.append(" swipeDir=");
                                                    TooltipPopup$$ExternalSyntheticOutline0.m(i9, "ItemTouchHelper", sb2);
                                                    ItemTouchHelper.this.mCallback.onSwiped(this.mViewHolder);
                                                    ItemTouchHelper.this.endRecoverAnimation(this.mViewHolder, false);
                                                    return;
                                                }
                                                ItemTouchHelper.this.mRecyclerView.post(this);
                                                return;
                                            }
                                        }
                                        Log.i("ItemTouchHelper", "Failed to call mCallback.onSwiped()!, call seslOnSwipeFailed, flag = 0x" + Integer.toHexString(this.mViewHolder.mFlags));
                                        Callback callback3 = ItemTouchHelper.this.mCallback;
                                        RecyclerView.ViewHolder viewHolder4 = this.mViewHolder;
                                        callback3.getClass();
                                        ItemTouchHelper.this.endRecoverAnimation(this.mViewHolder, false);
                                    }
                                });
                            } else {
                                Log.i("ItemTouchHelper", "select$onAnimationEnd: swipeDir <= 0 #5 do nothing");
                            }
                        } else {
                            Log.i("ItemTouchHelper", "select$onAnimationEnd: #3 call mCallback.clearView(mRecyclerView = " + ItemTouchHelper.this.mRecyclerView + ", prevSelected = " + viewHolder3 + ")");
                            ItemTouchHelper itemTouchHelper3 = ItemTouchHelper.this;
                            itemTouchHelper3.mCallback.clearView(itemTouchHelper3.mRecyclerView, viewHolder3);
                        }
                        ItemTouchHelper itemTouchHelper4 = ItemTouchHelper.this;
                        View view = itemTouchHelper4.mOverdrawChild;
                        View view2 = viewHolder3.itemView;
                        if (view == view2 && view2 == view) {
                            itemTouchHelper4.mOverdrawChild = null;
                        }
                        Log.i("ItemTouchHelper", "select: *** End RecoverAnimation$onAnimationEnd *** #6");
                    }
                };
                RecyclerView recyclerView2 = this.mRecyclerView;
                callback2.getClass();
                long j = recyclerView2.mItemAnimator == null ? i5 == 8 ? 200L : 250L : i5 == 8 ? 400L : 100L;
                Log.i("ItemTouchHelper", "select: setDuration = " + j);
                recoverAnimation.mValueAnimator.setDuration(j);
                ((ArrayList) this.mRecoverAnimations).add(recoverAnimation);
                recoverAnimation.mViewHolder.setIsRecyclable(z2);
                recoverAnimation.mValueAnimator.start();
                callback = callback2;
                z = true;
                i3 = z2;
            } else {
                viewHolder2 = null;
                i3 = 0;
                if (viewHolder3.itemView == this.mOverdrawChild) {
                    this.mOverdrawChild = null;
                }
                callback = callback2;
                callback.clearView(this.mRecyclerView, viewHolder3);
                z = false;
            }
            this.mSelected = viewHolder2;
            i2 = i3;
        } else {
            callback = callback2;
            i2 = 0;
            z = false;
        }
        if (viewHolder != null) {
            RecyclerView recyclerView3 = this.mRecyclerView;
            int movementFlags2 = callback.getMovementFlags(viewHolder);
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            this.mSelectedFlags = (Callback.convertToAbsoluteDirection(movementFlags2, recyclerView3.getLayoutDirection()) & i7) >> (this.mActionState * 8);
            this.mSelectedStartX = viewHolder.itemView.getLeft();
            this.mSelectedStartY = viewHolder.itemView.getTop();
            this.mSelected = viewHolder;
        }
        ViewParent parent = this.mRecyclerView.getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(this.mSelected != null ? true : i2);
        }
        if (!z) {
            this.mRecyclerView.mLayout.mRequestedSimpleAnimations = true;
        }
        int i9 = this.mActionState;
        if (i9 == 0) {
            callback.onSelectedChanged(viewHolder3, i9);
        } else {
            callback.onSelectedChanged(this.mSelected, i9);
        }
        if (i == 2) {
            this.mSelected.itemView.performHapticFeedback(i2);
            this.mSelected.itemView.announceForAccessibility(this.mRecyclerView.getContext().getString(R.string.dragndroplist_drag_start, Integer.valueOf(this.mSelected.getLayoutPosition() + 1)));
        }
        this.mRecyclerView.invalidate();
    }

    public final void updateDxDy(int i, int i2, MotionEvent motionEvent) {
        float x = motionEvent.getX(i2);
        float y = motionEvent.getY(i2);
        this.mDx = x - this.mInitialTouchX;
        Log.i("ItemTouchHelper", "updateDxDy: mDx = " + this.mDx + " = (x = " + x + " - mInitialTouchX = " + this.mInitialTouchX + ")");
        this.mDy = y - this.mInitialTouchY;
        if ((i & 4) == 0) {
            this.mDx = Math.max(0.0f, this.mDx);
            Log.i("ItemTouchHelper", "updateDxDy: direction LEFT mDx = " + this.mDx);
        }
        if ((i & 8) == 0) {
            this.mDx = Math.min(0.0f, this.mDx);
            Log.i("ItemTouchHelper", "updateDxDy: direction RIGHT mDx = " + this.mDx);
        }
        if ((i & 1) == 0) {
            this.mDy = Math.max(0.0f, this.mDy);
        }
        if ((i & 2) == 0) {
            this.mDy = Math.min(0.0f, this.mDy);
        }
    }

    public abstract class Callback {
        public static final AnonymousClass1 sDragScrollInterpolator = new AnonymousClass1();
        public static final AnonymousClass2 sDragViewScrollCapInterpolator = new AnonymousClass2();
        public int mCachedMaxScrollSpeed = -1;

        /* renamed from: androidx.recyclerview.widget.ItemTouchHelper$Callback$1, reason: invalid class name */
        public class AnonymousClass1 implements Interpolator {
            @Override // android.animation.TimeInterpolator
            public final float getInterpolation(float f) {
                return f * f * f * f * f;
            }
        }

        /* renamed from: androidx.recyclerview.widget.ItemTouchHelper$Callback$2, reason: invalid class name */
        public class AnonymousClass2 implements Interpolator {
            @Override // android.animation.TimeInterpolator
            public final float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * f2 * f2 * f2 * f2) + 1.0f;
            }
        }

        public static int convertToAbsoluteDirection(int i, int i2) {
            int i3;
            int i4 = i & 3158064;
            if (i4 == 0) {
                return i;
            }
            int i5 = i & (~i4);
            if (i2 == 0) {
                i3 = i4 >> 2;
            } else {
                int i6 = i4 >> 1;
                i5 |= (-3158065) & i6;
                i3 = (i6 & 3158064) >> 2;
            }
            return i5 | i3;
        }

        public static int convertToRelativeDirection(int i, int i2) {
            int i3;
            int i4 = i & 789516;
            if (i4 == 0) {
                return i;
            }
            int i5 = i & (~i4);
            if (i2 == 0) {
                i3 = i4 << 2;
            } else {
                int i6 = i4 << 1;
                i5 |= (-789517) & i6;
                i3 = (i6 & 789516) << 2;
            }
            return i5 | i3;
        }

        public static int makeMovementFlags(int i, int i2) {
            int i3 = i2 | i;
            return (i << 16) | (i2 << 8) | i3;
        }

        public boolean canDropOver(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
            return true;
        }

        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            ItemTouchUIUtilImpl itemTouchUIUtilImpl = ItemTouchUIUtilImpl.INSTANCE;
            View view = viewHolder.itemView;
            itemTouchUIUtilImpl.getClass();
            Object tag = view.getTag(R.id.item_touch_helper_previous_elevation);
            if (tag instanceof Float) {
                float fFloatValue = ((Float) tag).floatValue();
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api21Impl.setElevation(view, fFloatValue);
            }
            view.setTag(R.id.item_touch_helper_previous_elevation, null);
            view.setTranslationX(0.0f);
            view.setTranslationY(0.0f);
        }

        public abstract int getMovementFlags(RecyclerView.ViewHolder viewHolder);

        public float getSwipeThreshold() {
            return 0.5f;
        }

        public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int i, int i2, long j) {
            if (this.mCachedMaxScrollSpeed == -1) {
                this.mCachedMaxScrollSpeed = recyclerView.getResources().getDimensionPixelSize(R.dimen.item_touch_helper_max_drag_scroll_per_frame);
            }
            int interpolation = (int) (sDragScrollInterpolator.getInterpolation(j <= DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY ? j / 2000.0f : 1.0f) * ((int) (sDragViewScrollCapInterpolator.getInterpolation(Math.min(1.0f, (Math.abs(i2) * 1.0f) / i)) * ((int) Math.signum(i2)) * this.mCachedMaxScrollSpeed)));
            return interpolation == 0 ? i2 > 0 ? 1 : -1 : interpolation;
        }

        public boolean isItemViewSwipeEnabled() {
            return !(this instanceof StatefulControlAdapter$itemTouchHelperCallback$1);
        }

        public boolean isLongPressDragEnabled() {
            return !(this instanceof ReorderStructureModel$itemTouchHelper$1);
        }

        public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            ItemTouchUIUtilImpl itemTouchUIUtilImpl = ItemTouchUIUtilImpl.INSTANCE;
            View view = viewHolder.itemView;
            itemTouchUIUtilImpl.getClass();
            if (z && view.getTag(R.id.item_touch_helper_previous_elevation) == null) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                Float fValueOf = Float.valueOf(ViewCompat.Api21Impl.getElevation(view));
                int childCount = recyclerView.getChildCount();
                float f3 = 0.0f;
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = recyclerView.getChildAt(i2);
                    if (childAt != view) {
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        float elevation = ViewCompat.Api21Impl.getElevation(childAt);
                        if (elevation > f3) {
                            f3 = elevation;
                        }
                    }
                }
                ViewCompat.Api21Impl.setElevation(view, f3 + 1.0f);
                view.setTag(R.id.item_touch_helper_previous_elevation, fValueOf);
            }
            view.setTranslationX(f);
            view.setTranslationY(f2);
        }

        public void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            ItemTouchUIUtilImpl itemTouchUIUtilImpl = ItemTouchUIUtilImpl.INSTANCE;
            View view = viewHolder.itemView;
            itemTouchUIUtilImpl.getClass();
        }

        public abstract boolean onMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2);

        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder != null) {
                ItemTouchUIUtilImpl.INSTANCE.getClass();
            }
        }

        public abstract void onSwiped(RecyclerView.ViewHolder viewHolder);

        public float getSwipeEscapeVelocity(float f) {
            return f;
        }
    }

    public class RecoverAnimation implements Animator.AnimatorListener {
        public final int mActionState;
        public float mFraction;
        public boolean mIsPendingCleanup;
        public final float mStartDx;
        public final float mStartDy;
        public final float mTargetX;
        public final float mTargetY;
        public final ValueAnimator mValueAnimator;
        public final RecyclerView.ViewHolder mViewHolder;
        public float mX;
        public float mY;
        public boolean mOverridden = false;
        public boolean mEnded = false;

        public RecoverAnimation(RecyclerView.ViewHolder viewHolder, int i, int i2, float f, float f2, float f3, float f4) {
            PathInterpolator pathInterpolator = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
            this.mActionState = i2;
            this.mViewHolder = viewHolder;
            this.mStartDx = f;
            this.mStartDy = f2;
            this.mTargetX = f3;
            this.mTargetY = f4;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mValueAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.setInterpolator(pathInterpolator);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.recyclerview.widget.ItemTouchHelper.RecoverAnimation.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    RecoverAnimation.this.mFraction = valueAnimator.getAnimatedFraction();
                }
            });
            valueAnimatorOfFloat.setTarget(viewHolder.itemView);
            valueAnimatorOfFloat.addListener(this);
            this.mFraction = 0.0f;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            this.mFraction = 1.0f;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (!this.mEnded) {
                this.mViewHolder.setIsRecyclable(true);
            }
            this.mEnded = true;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
    public final void onChildViewAttachedToWindow(View view) {
    }
}
