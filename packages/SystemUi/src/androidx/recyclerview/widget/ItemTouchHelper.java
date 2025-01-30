package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.reflect.view.SeslHapticFeedbackConstantsReflector;
import com.android.systemui.R;
import com.android.systemui.controls.management.FavoritesModel$itemTouchHelperCallback$1;
import com.android.systemui.controls.management.model.ReorderStructureModel$itemTouchHelper$1;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ItemTouchHelper extends RecyclerView.ItemDecoration implements RecyclerView.OnChildAttachStateChangeListener {
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
    public final RunnableC04431 mScrollRunnable = new RunnableC04431();
    public View mOverdrawChild = null;
    public int mOverdrawChildPosition = -1;
    public final C04442 mOnItemTouchListener = new RecyclerView.OnItemTouchListener() { // from class: androidx.recyclerview.widget.ItemTouchHelper.2
        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public final boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            int findPointerIndex;
            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
            itemTouchHelper.mGestureDetector.mImpl.mDetector.onTouchEvent(motionEvent);
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
                    ArrayList arrayList = (ArrayList) itemTouchHelper.mRecoverAnimations;
                    if (!arrayList.isEmpty()) {
                        View findChildView = itemTouchHelper.findChildView(motionEvent);
                        int size = arrayList.size() - 1;
                        while (true) {
                            if (size < 0) {
                                break;
                            }
                            RecoverAnimation recoverAnimation2 = (RecoverAnimation) arrayList.get(size);
                            if (recoverAnimation2.mViewHolder.itemView == findChildView) {
                                recoverAnimation = recoverAnimation2;
                                break;
                            }
                            size--;
                        }
                    }
                    if (recoverAnimation != null) {
                        Log.i("ItemTouchHelper", "onInterceptTouchEvent: #2 mInitialTouchX = " + itemTouchHelper.mInitialTouchX + " animation.mX = " + recoverAnimation.f177mX);
                        itemTouchHelper.mInitialTouchX = itemTouchHelper.mInitialTouchX - recoverAnimation.f177mX;
                        StringBuilder sb = new StringBuilder("onInterceptTouchEvent: #2 set mInitialTouchX = ");
                        sb.append(itemTouchHelper.mInitialTouchX);
                        Log.i("ItemTouchHelper", sb.toString());
                        itemTouchHelper.mInitialTouchY -= recoverAnimation.f178mY;
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
                if (i != -1 && (findPointerIndex = motionEvent.findPointerIndex(i)) >= 0) {
                    itemTouchHelper.checkSelectForSwipe(actionMasked, findPointerIndex, motionEvent);
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
            itemTouchHelper.mGestureDetector.mImpl.mDetector.onTouchEvent(motionEvent);
            VelocityTracker velocityTracker = itemTouchHelper.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.addMovement(motionEvent);
            }
            if (itemTouchHelper.mActivePointerId == -1) {
                return;
            }
            int actionMasked = motionEvent.getActionMasked();
            int findPointerIndex = motionEvent.findPointerIndex(itemTouchHelper.mActivePointerId);
            if (findPointerIndex >= 0) {
                itemTouchHelper.checkSelectForSwipe(actionMasked, findPointerIndex, motionEvent);
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
                        if (findPointerIndex >= 0) {
                            itemTouchHelper.updateDxDy(itemTouchHelper.mSelectedFlags, findPointerIndex, motionEvent);
                            itemTouchHelper.moveIfNecessary(viewHolder);
                            RecyclerView recyclerView = itemTouchHelper.mRecyclerView;
                            RunnableC04431 runnableC04431 = itemTouchHelper.mScrollRunnable;
                            recyclerView.removeCallbacks(runnableC04431);
                            runnableC04431.run();
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.recyclerview.widget.ItemTouchHelper$1 */
    public final class RunnableC04431 implements Runnable {
        public RunnableC04431() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0052, code lost:
        
            if (r11 < 0) goto L23;
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x0075, code lost:
        
            if (r11 > 0) goto L23;
         */
        /* JADX WARN: Removed duplicated region for block: B:21:0x00c2  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x00db  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x010f  */
        /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:38:0x0105  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x00f6  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void run() {
            int i;
            int i2;
            int i3;
            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
            if (itemTouchHelper.mSelected == null) {
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            long j = itemTouchHelper.mDragScrollStartTimeInMs;
            long j2 = j == Long.MIN_VALUE ? 0L : currentTimeMillis - j;
            RecyclerView.LayoutManager layoutManager = itemTouchHelper.mRecyclerView.mLayout;
            if (itemTouchHelper.mTmpRect == null) {
                itemTouchHelper.mTmpRect = new Rect();
            }
            layoutManager.calculateItemDecorationsForChild(itemTouchHelper.mTmpRect, itemTouchHelper.mSelected.itemView);
            boolean z = false;
            if (layoutManager.canScrollHorizontally()) {
                int i4 = (int) (itemTouchHelper.mSelectedStartX + itemTouchHelper.mDx);
                i = (i4 - itemTouchHelper.mTmpRect.left) - itemTouchHelper.mRecyclerView.getPaddingLeft();
                float f = itemTouchHelper.mDx;
                if (f < 0.0f) {
                }
                if (f > 0.0f) {
                    i = ((itemTouchHelper.mSelected.itemView.getWidth() + i4) + itemTouchHelper.mTmpRect.right) - (itemTouchHelper.mRecyclerView.getWidth() - itemTouchHelper.mRecyclerView.getPaddingRight());
                }
            }
            i = 0;
            if (layoutManager.canScrollVertically()) {
                int i5 = (int) (itemTouchHelper.mSelectedStartY + itemTouchHelper.mDy);
                int paddingTop = (i5 - itemTouchHelper.mTmpRect.top) - itemTouchHelper.mRecyclerView.getPaddingTop();
                float f2 = itemTouchHelper.mDy;
                if ((f2 < 0.0f && paddingTop < 0) || (f2 > 0.0f && (paddingTop = ((itemTouchHelper.mSelected.itemView.getHeight() + i5) + itemTouchHelper.mTmpRect.bottom) - (itemTouchHelper.mRecyclerView.getHeight() - itemTouchHelper.mRecyclerView.getPaddingBottom())) > 0)) {
                    i2 = paddingTop;
                    if (i != 0) {
                        Callback callback = itemTouchHelper.mCallback;
                        RecyclerView recyclerView = itemTouchHelper.mRecyclerView;
                        int width = itemTouchHelper.mSelected.itemView.getWidth();
                        itemTouchHelper.mRecyclerView.getWidth();
                        i = callback.interpolateOutOfBoundsScroll(recyclerView, width, i, j2);
                    }
                    int i6 = i;
                    if (i2 == 0) {
                        Callback callback2 = itemTouchHelper.mCallback;
                        RecyclerView recyclerView2 = itemTouchHelper.mRecyclerView;
                        int height = itemTouchHelper.mSelected.itemView.getHeight();
                        itemTouchHelper.mRecyclerView.getHeight();
                        i3 = i6;
                        i2 = callback2.interpolateOutOfBoundsScroll(recyclerView2, height, i2, j2);
                    } else {
                        i3 = i6;
                    }
                    if (i3 == 0 || i2 != 0) {
                        if (itemTouchHelper.mDragScrollStartTimeInMs == Long.MIN_VALUE) {
                            itemTouchHelper.mDragScrollStartTimeInMs = currentTimeMillis;
                        }
                        itemTouchHelper.mRecyclerView.scrollBy(i3, i2);
                        z = true;
                    } else {
                        itemTouchHelper.mDragScrollStartTimeInMs = Long.MIN_VALUE;
                    }
                    if (z) {
                        return;
                    }
                    ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                    RecyclerView.ViewHolder viewHolder = itemTouchHelper2.mSelected;
                    if (viewHolder != null) {
                        itemTouchHelper2.moveIfNecessary(viewHolder);
                    }
                    ItemTouchHelper itemTouchHelper3 = ItemTouchHelper.this;
                    itemTouchHelper3.mRecyclerView.removeCallbacks(itemTouchHelper3.mScrollRunnable);
                    RecyclerView recyclerView3 = ItemTouchHelper.this.mRecyclerView;
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.postOnAnimation(recyclerView3, this);
                    return;
                }
            }
            i2 = 0;
            if (i != 0) {
            }
            int i62 = i;
            if (i2 == 0) {
            }
            if (i3 == 0) {
            }
            if (itemTouchHelper.mDragScrollStartTimeInMs == Long.MIN_VALUE) {
            }
            itemTouchHelper.mRecyclerView.scrollBy(i3, i2);
            z = true;
            if (z) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean mShouldReactToLongPress = true;

        public ItemTouchHelperGestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final void onLongPress(MotionEvent motionEvent) {
            View findChildView;
            RecyclerView.ViewHolder childViewHolder;
            int i;
            if (!this.mShouldReactToLongPress || (findChildView = ItemTouchHelper.this.findChildView(motionEvent)) == null || (childViewHolder = ItemTouchHelper.this.mRecyclerView.getChildViewHolder(findChildView)) == null) {
                return;
            }
            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
            Callback callback = itemTouchHelper.mCallback;
            RecyclerView recyclerView = itemTouchHelper.mRecyclerView;
            int movementFlags = callback.getMovementFlags(childViewHolder);
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            int layoutDirection = ViewCompat.Api17Impl.getLayoutDirection(recyclerView);
            int i2 = movementFlags & 3158064;
            if (i2 != 0) {
                int i3 = movementFlags & (~i2);
                if (layoutDirection == 0) {
                    i = i2 >> 2;
                } else {
                    int i4 = i2 >> 1;
                    i3 |= (-3158065) & i4;
                    i = (i4 & 3158064) >> 2;
                }
                movementFlags = i3 | i;
            }
            if (!((16711680 & movementFlags) != 0)) {
                childViewHolder.itemView.announceForAccessibility(ItemTouchHelper.this.mRecyclerView.getContext().getString(R.string.dragndroplist_item_cannot_be_dragged, Integer.valueOf(childViewHolder.getLayoutPosition() + 1)));
                return;
            }
            int pointerId = motionEvent.getPointerId(0);
            int i5 = ItemTouchHelper.this.mActivePointerId;
            if (pointerId == i5) {
                int findPointerIndex = motionEvent.findPointerIndex(i5);
                float x = motionEvent.getX(findPointerIndex);
                float y = motionEvent.getY(findPointerIndex);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ViewDropHandler {
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
        C04442 c04442 = this.mOnItemTouchListener;
        if (recyclerView2 != null) {
            recyclerView2.removeItemDecoration(this);
            RecyclerView recyclerView3 = this.mRecyclerView;
            recyclerView3.mOnItemTouchListeners.remove(c04442);
            if (recyclerView3.mInterceptingOnItemTouchListener == c04442) {
                recyclerView3.mInterceptingOnItemTouchListener = null;
            }
            List list = this.mRecyclerView.mOnChildAttachStateListeners;
            if (list != null) {
                ((ArrayList) list).remove(this);
            }
            ArrayList arrayList = (ArrayList) this.mRecoverAnimations;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                RecoverAnimation recoverAnimation = (RecoverAnimation) arrayList.get(0);
                recoverAnimation.mValueAnimator.cancel();
                this.mCallback.clearView(this.mRecyclerView, recoverAnimation.mViewHolder);
            }
            arrayList.clear();
            this.mOverdrawChild = null;
            this.mOverdrawChildPosition = -1;
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
            this.mRecyclerView.mOnItemTouchListeners.add(c04442);
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
            int i3 = xVelocity <= 0.0f ? 4 : 8;
            float abs = Math.abs(xVelocity);
            if ((i3 & i) != 0 && i2 == i3 && abs >= callback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && abs > Math.abs(yVelocity)) {
                return i3;
            }
        }
        float swipeThreshold = callback.getSwipeThreshold() * this.mRecyclerView.getWidth();
        if ((i & i2) == 0 || Math.abs(this.mDx) <= swipeThreshold) {
            return 0;
        }
        return i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0071 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void checkSelectForSwipe(int i, int i2, MotionEvent motionEvent) {
        RecyclerView.ViewHolder viewHolder;
        int i3;
        View findChildView;
        if (this.mSelected != null || i != 2 || this.mActionState == 2) {
            return;
        }
        Callback callback = this.mCallback;
        if (!callback.isItemViewSwipeEnabled()) {
            return;
        }
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView.mScrollState == 1) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.mLayout;
        int i4 = this.mActivePointerId;
        if (i4 != -1) {
            int findPointerIndex = motionEvent.findPointerIndex(i4);
            float x = motionEvent.getX(findPointerIndex) - this.mInitialTouchX;
            float y = motionEvent.getY(findPointerIndex) - this.mInitialTouchY;
            float abs = Math.abs(x);
            float abs2 = Math.abs(y);
            float f = this.mSlop;
            if ((abs >= f || abs2 >= f) && ((abs <= abs2 || !layoutManager.canScrollHorizontally()) && ((abs2 <= abs || !layoutManager.canScrollVertically()) && (findChildView = findChildView(motionEvent)) != null))) {
                viewHolder = this.mRecyclerView.getChildViewHolder(findChildView);
                if (viewHolder != null) {
                    return;
                }
                RecyclerView recyclerView2 = this.mRecyclerView;
                int movementFlags = callback.getMovementFlags(viewHolder);
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                int layoutDirection = ViewCompat.Api17Impl.getLayoutDirection(recyclerView2);
                int i5 = movementFlags & 3158064;
                if (i5 != 0) {
                    int i6 = movementFlags & (~i5);
                    if (layoutDirection == 0) {
                        i3 = i5 >> 2;
                    } else {
                        int i7 = i5 >> 1;
                        i6 |= (-3158065) & i7;
                        i3 = (i7 & 3158064) >> 2;
                    }
                    movementFlags = i6 | i3;
                }
                int i8 = (movementFlags & 65280) >> 8;
                if (i8 == 0) {
                    return;
                }
                float x2 = motionEvent.getX(i2);
                float y2 = motionEvent.getY(i2);
                float f2 = x2 - this.mInitialTouchX;
                float f3 = y2 - this.mInitialTouchY;
                float abs3 = Math.abs(f2);
                float abs4 = Math.abs(f3);
                float f4 = this.mSlop;
                if (abs3 >= f4 || abs4 >= f4) {
                    if (abs3 > abs4) {
                        if (f2 < 0.0f && (i8 & 4) == 0) {
                            return;
                        }
                        if (f2 > 0.0f && (i8 & 8) == 0) {
                            return;
                        }
                    } else {
                        if (f3 < 0.0f && (i8 & 1) == 0) {
                            return;
                        }
                        if (f3 > 0.0f && (i8 & 2) == 0) {
                            return;
                        }
                    }
                    this.mDy = 0.0f;
                    this.mDx = 0.0f;
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    select(viewHolder, 1);
                    return;
                }
                return;
            }
        }
        viewHolder = null;
        if (viewHolder != null) {
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
            int i3 = yVelocity <= 0.0f ? 1 : 2;
            float abs = Math.abs(yVelocity);
            if ((i3 & i) != 0 && i3 == i2 && abs >= callback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && abs > Math.abs(xVelocity)) {
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
        RecoverAnimation recoverAnimation;
        ArrayList arrayList = (ArrayList) this.mRecoverAnimations;
        int size = arrayList.size();
        do {
            size--;
            if (size < 0) {
                return;
            } else {
                recoverAnimation = (RecoverAnimation) arrayList.get(size);
            }
        } while (recoverAnimation.mViewHolder != viewHolder);
        recoverAnimation.mOverridden |= z;
        if (!recoverAnimation.mEnded) {
            recoverAnimation.mValueAnimator.cancel();
        }
        arrayList.remove(size);
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
        List list = this.mRecoverAnimations;
        for (int size = ((ArrayList) list).size() - 1; size >= 0; size--) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) ((ArrayList) list).get(size);
            View view2 = recoverAnimation.mViewHolder.itemView;
            if (hitTest(view2, x, y, recoverAnimation.f177mX, recoverAnimation.f178mY)) {
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
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("getSelectedDxDy: #1 calledBy = ", i, " outPosition[0] = ");
            m1m.append(fArr[0]);
            m1m.append(", mSelectedStartX = ");
            m1m.append(this.mSelectedStartX);
            m1m.append(", mDx = ");
            m1m.append(this.mDx);
            m1m.append(", mSelected.itemView.getLeft() = ");
            m1m.append(this.mSelected.itemView.getLeft());
            Log.i("ItemTouchHelper", m1m.toString());
        } else {
            fArr[0] = this.mSelected.itemView.getTranslationX();
            StringBuilder m1m2 = AbstractC0000x2c234b15.m1m("getSelectedDxDy: #2 calledBy = ", i, " outPosition[0] = ");
            m1m2.append(this.mSelected.itemView.getTranslationX());
            Log.i("ItemTouchHelper", m1m2.toString());
        }
        if ((this.mSelectedFlags & 3) != 0) {
            fArr[1] = (this.mSelectedStartY + this.mDy) - this.mSelected.itemView.getTop();
        } else {
            fArr[1] = this.mSelected.itemView.getTranslationY();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void moveIfNecessary(RecyclerView.ViewHolder viewHolder) {
        ArrayList arrayList;
        int i;
        int i2;
        int bottom;
        int abs;
        int top;
        int abs2;
        int left;
        int abs3;
        int abs4;
        RecyclerView.LayoutManager layoutManager;
        int i3;
        int i4;
        int i5;
        if (!this.mRecyclerView.isLayoutRequested() && this.mActionState == 2) {
            Callback callback = this.mCallback;
            callback.getClass();
            int i6 = (int) (this.mSelectedStartX + this.mDx);
            int i7 = (int) (this.mSelectedStartY + this.mDy);
            float abs5 = Math.abs(i7 - viewHolder.itemView.getTop());
            View view = viewHolder.itemView;
            if (abs5 >= view.getHeight() * 0.5f || Math.abs(i6 - view.getLeft()) >= view.getWidth() * 0.5f) {
                List list = this.mSwapTargets;
                if (list == null) {
                    this.mSwapTargets = new ArrayList();
                    this.mDistances = new ArrayList();
                } else {
                    ((ArrayList) list).clear();
                    ((ArrayList) this.mDistances).clear();
                }
                int i8 = 0;
                int round = Math.round(this.mSelectedStartX + this.mDx) - 0;
                int round2 = Math.round(this.mSelectedStartY + this.mDy) - 0;
                int width = view.getWidth() + round + 0;
                int height = view.getHeight() + round2 + 0;
                int i9 = (round + width) / 2;
                int i10 = (round2 + height) / 2;
                RecyclerView.LayoutManager layoutManager2 = this.mRecyclerView.mLayout;
                int childCount = layoutManager2.getChildCount();
                while (i8 < childCount) {
                    View childAt = layoutManager2.getChildAt(i8);
                    if (childAt == view) {
                        i3 = round;
                        i4 = round2;
                        i5 = width;
                        layoutManager = layoutManager2;
                    } else {
                        layoutManager = layoutManager2;
                        if (childAt.getBottom() < round2 || childAt.getTop() > height || childAt.getRight() < round || childAt.getLeft() > width) {
                            i3 = round;
                        } else {
                            RecyclerView.ViewHolder childViewHolder = this.mRecyclerView.getChildViewHolder(childAt);
                            i3 = round;
                            if (callback.canDropOver(this.mSelected, childViewHolder)) {
                                int abs6 = Math.abs(i9 - ((childAt.getRight() + childAt.getLeft()) / 2));
                                int abs7 = Math.abs(i10 - ((childAt.getBottom() + childAt.getTop()) / 2));
                                int i11 = (abs7 * abs7) + (abs6 * abs6);
                                int size = ((ArrayList) this.mSwapTargets).size();
                                i4 = round2;
                                i5 = width;
                                int i12 = 0;
                                int i13 = 0;
                                while (i12 < size) {
                                    int i14 = size;
                                    if (i11 <= ((Integer) ((ArrayList) this.mDistances).get(i12)).intValue()) {
                                        break;
                                    }
                                    i13++;
                                    i12++;
                                    size = i14;
                                }
                                ((ArrayList) this.mSwapTargets).add(i13, childViewHolder);
                                ((ArrayList) this.mDistances).add(i13, Integer.valueOf(i11));
                            }
                        }
                        i4 = round2;
                        i5 = width;
                    }
                    i8++;
                    layoutManager2 = layoutManager;
                    round = i3;
                    round2 = i4;
                    width = i5;
                }
                ArrayList arrayList2 = (ArrayList) this.mSwapTargets;
                if (arrayList2.size() == 0) {
                    return;
                }
                int width2 = view.getWidth() + i6;
                int height2 = view.getHeight() + i7;
                int left2 = i6 - view.getLeft();
                int top2 = i7 - view.getTop();
                int size2 = arrayList2.size();
                RecyclerView.ViewHolder viewHolder2 = null;
                int i15 = 0;
                int i16 = -1;
                while (i15 < size2) {
                    RecyclerView.ViewHolder viewHolder3 = (RecyclerView.ViewHolder) arrayList2.get(i15);
                    if (left2 > 0) {
                        arrayList = arrayList2;
                        int right = viewHolder3.itemView.getRight() - width2;
                        i = width2;
                        if (right < 0) {
                            i2 = size2;
                            if (viewHolder3.itemView.getRight() > view.getRight() && (abs4 = Math.abs(right)) > i16) {
                                i16 = abs4;
                                viewHolder2 = viewHolder3;
                            }
                            if (left2 < 0 && (left = viewHolder3.itemView.getLeft() - i6) > 0 && viewHolder3.itemView.getLeft() < view.getLeft() && (abs3 = Math.abs(left)) > i16) {
                                i16 = abs3;
                                viewHolder2 = viewHolder3;
                            }
                            if (top2 < 0 && (top = viewHolder3.itemView.getTop() - i7) > 0 && viewHolder3.itemView.getTop() < view.getTop() && (abs2 = Math.abs(top)) > i16) {
                                i16 = abs2;
                                viewHolder2 = viewHolder3;
                            }
                            if (top2 > 0 && (bottom = viewHolder3.itemView.getBottom() - height2) < 0 && viewHolder3.itemView.getBottom() > view.getBottom() && (abs = Math.abs(bottom)) > i16) {
                                i16 = abs;
                                viewHolder2 = viewHolder3;
                            }
                            i15++;
                            arrayList2 = arrayList;
                            width2 = i;
                            size2 = i2;
                        }
                    } else {
                        arrayList = arrayList2;
                        i = width2;
                    }
                    i2 = size2;
                    if (left2 < 0) {
                        i16 = abs3;
                        viewHolder2 = viewHolder3;
                    }
                    if (top2 < 0) {
                        i16 = abs2;
                        viewHolder2 = viewHolder3;
                    }
                    if (top2 > 0) {
                        i16 = abs;
                        viewHolder2 = viewHolder3;
                    }
                    i15++;
                    arrayList2 = arrayList;
                    width2 = i;
                    size2 = i2;
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
                    RecyclerView.LayoutManager layoutManager3 = recyclerView.mLayout;
                    boolean z = layoutManager3 instanceof ViewDropHandler;
                    View view2 = viewHolder2.itemView;
                    if (z) {
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) ((ViewDropHandler) layoutManager3);
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
                            linearLayoutManager.scrollToPositionWithOffset(position2, linearLayoutManager.mOrientationHelper.getDecoratedStart(view2));
                        } else {
                            linearLayoutManager.scrollToPositionWithOffset(position2, linearLayoutManager.mOrientationHelper.getDecoratedEnd(view2) - linearLayoutManager.mOrientationHelper.getDecoratedMeasurement(view));
                        }
                    } else {
                        if (layoutManager3.canScrollHorizontally()) {
                            if (layoutManager3.getDecoratedLeft(view2) <= recyclerView.getPaddingLeft()) {
                                recyclerView.scrollToPosition(absoluteAdapterPosition);
                            }
                            if (layoutManager3.getDecoratedRight(view2) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                                recyclerView.scrollToPosition(absoluteAdapterPosition);
                            }
                        }
                        if (layoutManager3.canScrollVertically()) {
                            if (layoutManager3.getDecoratedTop(view2) <= recyclerView.getPaddingTop()) {
                                recyclerView.scrollToPosition(absoluteAdapterPosition);
                            }
                            if (layoutManager3.getDecoratedBottom(view2) >= recyclerView.getHeight() - recyclerView.getPaddingBottom()) {
                                recyclerView.scrollToPosition(absoluteAdapterPosition);
                            }
                        }
                    }
                    view.performHapticFeedback(SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(41));
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
    public final void onDraw(Canvas canvas, RecyclerView recyclerView) {
        float f;
        float f2;
        this.mOverdrawChildPosition = -1;
        if (this.mSelected != null) {
            float[] fArr = this.mTmpPosition;
            getSelectedDxDy(fArr, 2);
            float f3 = fArr[0];
            f2 = fArr[1];
            f = f3;
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
        int i2 = 0;
        while (i2 < size) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) arrayList.get(i2);
            float f4 = recoverAnimation.mStartDx;
            float f5 = recoverAnimation.mTargetX;
            if (f4 == f5) {
                recoverAnimation.f177mX = recoverAnimation.mViewHolder.itemView.getTranslationX();
            } else {
                recoverAnimation.f177mX = DependencyGraph$$ExternalSyntheticOutline0.m20m(f5, f4, recoverAnimation.mFraction, f4);
            }
            float f6 = recoverAnimation.mStartDy;
            float f7 = recoverAnimation.mTargetY;
            if (f6 == f7) {
                recoverAnimation.f178mY = recoverAnimation.mViewHolder.itemView.getTranslationY();
            } else {
                recoverAnimation.f178mY = DependencyGraph$$ExternalSyntheticOutline0.m20m(f7, f6, recoverAnimation.mFraction, f6);
            }
            int save = canvas.save();
            callback.onChildDraw(canvas, recyclerView, recoverAnimation.mViewHolder, recoverAnimation.f177mX, recoverAnimation.f178mY, recoverAnimation.mActionState, false);
            canvas.restoreToCount(save);
            i2++;
            arrayList = arrayList;
        }
        if (viewHolder != null) {
            int save2 = canvas.save();
            callback.onChildDraw(canvas, recyclerView, viewHolder, f, f2, i, true);
            canvas.restoreToCount(save2);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
        float f;
        float f2;
        if (this.mSelected != null) {
            float[] fArr = this.mTmpPosition;
            getSelectedDxDy(fArr, 1);
            float f3 = fArr[0];
            f2 = fArr[1];
            f = f3;
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
        int i2 = 0;
        while (i2 < size) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) arrayList.get(i2);
            int save = canvas.save();
            callback.onChildDrawOver(canvas, recyclerView, recoverAnimation.mViewHolder, recoverAnimation.f177mX, recoverAnimation.f178mY, recoverAnimation.mActionState, false);
            canvas.restoreToCount(save);
            i2++;
            arrayList = arrayList;
            size = size;
        }
        int i3 = size;
        ArrayList arrayList2 = arrayList;
        if (viewHolder != null) {
            int save2 = canvas.save();
            callback.onChildDrawOver(canvas, recyclerView, viewHolder, f, f2, i, true);
            canvas.restoreToCount(save2);
        }
        boolean z = false;
        for (int i4 = i3 - 1; i4 >= 0; i4--) {
            RecoverAnimation recoverAnimation2 = (RecoverAnimation) arrayList2.get(i4);
            boolean z2 = recoverAnimation2.mEnded;
            if (z2 && !recoverAnimation2.mIsPendingCleanup) {
                arrayList2.remove(i4);
            } else if (!z2) {
                z = true;
            }
        }
        if (z) {
            recyclerView.invalidate();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:108:0x00e2, code lost:
    
        if (r0 == 0) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x00af, code lost:
    
        if (r0 == 0) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x00b1, code lost:
    
        r0 = r1 << 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x00bb, code lost:
    
        r2 = r0 | r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x00b4, code lost:
    
        r0 = r1 << 1;
        r2 = r2 | (r0 & (-789517));
        r0 = (r0 & 789516) << 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x00c2, code lost:
    
        if (r2 > 0) goto L64;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void select(RecyclerView.ViewHolder viewHolder, int i) {
        Callback callback;
        RecyclerView.ViewHolder viewHolder2;
        int i2;
        boolean z;
        int i3;
        RecyclerView.ViewHolder viewHolder3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        float f;
        float signum;
        int i10;
        if (viewHolder == this.mSelected && i == this.mActionState) {
            return;
        }
        this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
        int i11 = this.mActionState;
        endRecoverAnimation(viewHolder, true);
        this.mActionState = i;
        if (i == 2) {
            if (viewHolder == null) {
                throw new IllegalArgumentException("Must pass a ViewHolder when dragging");
            }
            this.mOverdrawChild = viewHolder.itemView;
        }
        int i12 = (1 << ((i * 8) + 8)) - 1;
        final RecyclerView.ViewHolder viewHolder4 = this.mSelected;
        Callback callback2 = this.mCallback;
        if (viewHolder4 != null) {
            View view = viewHolder4.itemView;
            if (view.getParent() != null) {
                if (i11 == 2) {
                    i5 = 0;
                } else {
                    if (this.mActionState != 2) {
                        int movementFlags = callback2.getMovementFlags(viewHolder4);
                        RecyclerView recyclerView = this.mRecyclerView;
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        int layoutDirection = ViewCompat.Api17Impl.getLayoutDirection(recyclerView);
                        int i13 = movementFlags & 3158064;
                        if (i13 == 0) {
                            i7 = movementFlags;
                        } else {
                            int i14 = (~i13) & movementFlags;
                            if (layoutDirection == 0) {
                                i6 = i13 >> 2;
                            } else {
                                int i15 = i13 >> 1;
                                i14 |= i15 & (-3158065);
                                i6 = (i15 & 3158064) >> 2;
                            }
                            i7 = i6 | i14;
                        }
                        int i16 = (i7 & 65280) >> 8;
                        if (i16 != 0) {
                            int i17 = (movementFlags & 65280) >> 8;
                            if (Math.abs(this.mDx) > Math.abs(this.mDy)) {
                                i4 = checkHorizontalSwipe(viewHolder4, i16);
                                if (i4 <= 0) {
                                    i4 = checkVerticalSwipe(viewHolder4, i16);
                                } else if ((i17 & i4) == 0) {
                                    int layoutDirection2 = ViewCompat.Api17Impl.getLayoutDirection(this.mRecyclerView);
                                    i8 = i4 & 789516;
                                    if (i8 != 0) {
                                        i9 = i4 & (~i8);
                                    }
                                }
                                i5 = i4;
                            } else {
                                i4 = checkVerticalSwipe(viewHolder4, i16);
                                if (i4 <= 0) {
                                    i4 = checkHorizontalSwipe(viewHolder4, i16);
                                    if (i4 > 0) {
                                        if ((i17 & i4) == 0) {
                                            int layoutDirection3 = ViewCompat.Api17Impl.getLayoutDirection(this.mRecyclerView);
                                            i8 = i4 & 789516;
                                            if (i8 != 0) {
                                                i9 = i4 & (~i8);
                                            }
                                        }
                                    }
                                }
                                i5 = i4;
                            }
                        }
                    }
                    i4 = 0;
                    i5 = i4;
                }
                VelocityTracker velocityTracker = this.mVelocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    this.mVelocityTracker = null;
                }
                if (i5 == 1 || i5 == 2) {
                    f = 0.0f;
                    signum = Math.signum(this.mDy) * this.mRecyclerView.getHeight();
                } else if (i5 == 4 || i5 == 8 || i5 == 16 || i5 == 32) {
                    signum = 0.0f;
                    f = Math.signum(this.mDx) * this.mRecyclerView.getWidth();
                } else {
                    f = 0.0f;
                    signum = 0.0f;
                }
                if (i11 == 2) {
                    this.mSelected.itemView.announceForAccessibility(this.mRecyclerView.getContext().getString(R.string.dragndroplist_drag_release, Integer.valueOf(this.mSelected.getLayoutPosition() + 1)));
                    i10 = 8;
                } else {
                    i10 = i5 > 0 ? 2 : 4;
                }
                float[] fArr = this.mTmpPosition;
                getSelectedDxDy(fArr, 3);
                int i18 = i10;
                final int i19 = i5;
                i2 = 8;
                RecoverAnimation recoverAnimation = new RecoverAnimation(viewHolder4, i10, i11, fArr[0], fArr[1], f, signum) { // from class: androidx.recyclerview.widget.ItemTouchHelper.3
                    @Override // androidx.recyclerview.widget.ItemTouchHelper.RecoverAnimation, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        Log.i("ItemTouchHelper", "select: *** Start RecoverAnimation$onAnimationEnd ***");
                        if (this.mOverridden) {
                            Log.i("ItemTouchHelper", "select: *** End RecoverAnimation$onAnimationEnd *** return #1");
                            return;
                        }
                        TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("select$onAnimationEnd: swipeDir = "), i19, "ItemTouchHelper");
                        if (i19 <= 0) {
                            Log.i("ItemTouchHelper", "select$onAnimationEnd: #2 call mCallback.clearView(mRecyclerView = " + ItemTouchHelper.this.mRecyclerView + ", prevSelected = " + viewHolder4 + ")");
                            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                            itemTouchHelper.mCallback.clearView(itemTouchHelper.mRecyclerView, viewHolder4);
                        } else if (viewHolder4.itemView.isAttachedToWindow()) {
                            ((ArrayList) ItemTouchHelper.this.mPendingCleanup).add(viewHolder4.itemView);
                            this.mIsPendingCleanup = true;
                            if (i19 > 0) {
                                Log.i("ItemTouchHelper", "select$onAnimationEnd: postDispatchSwipe #4");
                                final ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                                final int i20 = i19;
                                itemTouchHelper2.mRecyclerView.post(new Runnable() { // from class: androidx.recyclerview.widget.ItemTouchHelper.4
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        RecyclerView recyclerView2 = ItemTouchHelper.this.mRecyclerView;
                                        if (recyclerView2 != null && recyclerView2.isAttachedToWindow()) {
                                            RecoverAnimation recoverAnimation2 = this;
                                            if (!recoverAnimation2.mOverridden && recoverAnimation2.mViewHolder.getAbsoluteAdapterPosition() != -1) {
                                                StringBuilder sb = new StringBuilder("postDispatchSwipe$run: mRecyclerView = ");
                                                sb.append(ItemTouchHelper.this.mRecyclerView);
                                                sb.append(", isAttachedToWindow = ");
                                                sb.append(ItemTouchHelper.this.mRecyclerView.isAttachedToWindow());
                                                sb.append(", !anim.mOverridden = ");
                                                boolean z2 = true;
                                                sb.append(!this.mOverridden);
                                                sb.append(", anim.mViewHolder.getAdapterPosition() = ");
                                                sb.append(this.mViewHolder.getBindingAdapterPosition());
                                                Log.i("ItemTouchHelper", sb.toString());
                                                RecyclerView.ItemAnimator itemAnimator = ItemTouchHelper.this.mRecyclerView.mItemAnimator;
                                                if (itemAnimator == null || !itemAnimator.isRunning()) {
                                                    ArrayList arrayList = (ArrayList) ItemTouchHelper.this.mRecoverAnimations;
                                                    int size = arrayList.size();
                                                    int i21 = 0;
                                                    while (true) {
                                                        if (i21 >= size) {
                                                            z2 = false;
                                                            break;
                                                        } else if (!((RecoverAnimation) arrayList.get(i21)).mEnded) {
                                                            break;
                                                        } else {
                                                            i21++;
                                                        }
                                                    }
                                                    if (!z2) {
                                                        StringBuilder sb2 = new StringBuilder("postDispatchSwipe$run: mCallback.onSwiped anim.mViewHolder = ");
                                                        sb2.append(this.mViewHolder);
                                                        sb2.append(", anim.mViewHolder.itemView = ");
                                                        sb2.append(this.mViewHolder.itemView);
                                                        sb2.append(" swipeDir=");
                                                        TooltipPopup$$ExternalSyntheticOutline0.m13m(sb2, i20, "ItemTouchHelper");
                                                        ItemTouchHelper.this.mCallback.onSwiped(this.mViewHolder);
                                                        ItemTouchHelper.this.endRecoverAnimation(this.mViewHolder, false);
                                                        return;
                                                    }
                                                }
                                                ItemTouchHelper.this.mRecyclerView.post(this);
                                                return;
                                            }
                                        }
                                        Log.i("ItemTouchHelper", "Failed to call mCallback.onSwiped()!, call seslOnSwipeFailed, flag = 0x" + Integer.toHexString(this.mViewHolder.mFlags));
                                        Callback callback3 = ItemTouchHelper.this.mCallback;
                                        RecyclerView.ViewHolder viewHolder5 = this.mViewHolder;
                                        callback3.getClass();
                                        ItemTouchHelper.this.endRecoverAnimation(this.mViewHolder, false);
                                    }
                                });
                            } else {
                                Log.i("ItemTouchHelper", "select$onAnimationEnd: swipeDir <= 0 #5 do nothing");
                            }
                        } else {
                            Log.i("ItemTouchHelper", "select$onAnimationEnd: #3 call mCallback.clearView(mRecyclerView = " + ItemTouchHelper.this.mRecyclerView + ", prevSelected = " + viewHolder4 + ")");
                            ItemTouchHelper itemTouchHelper3 = ItemTouchHelper.this;
                            itemTouchHelper3.mCallback.clearView(itemTouchHelper3.mRecyclerView, viewHolder4);
                        }
                        ItemTouchHelper itemTouchHelper4 = ItemTouchHelper.this;
                        View view2 = itemTouchHelper4.mOverdrawChild;
                        View view3 = viewHolder4.itemView;
                        if (view2 == view3 && view3 == view2) {
                            itemTouchHelper4.mOverdrawChild = null;
                        }
                        Log.i("ItemTouchHelper", "select: *** End RecoverAnimation$onAnimationEnd *** #6");
                    }
                };
                RecyclerView recyclerView2 = this.mRecyclerView;
                callback2.getClass();
                RecyclerView.ItemAnimator itemAnimator = recyclerView2.mItemAnimator;
                long moveDuration = itemAnimator == null ? i18 == 8 ? 200L : 250L : i18 == 8 ? itemAnimator.getMoveDuration() : itemAnimator.getRemoveDuration();
                Log.i("ItemTouchHelper", "select: setDuration = " + moveDuration);
                recoverAnimation.mValueAnimator.setDuration(moveDuration);
                ((ArrayList) this.mRecoverAnimations).add(recoverAnimation);
                recoverAnimation.mViewHolder.setIsRecyclable(false);
                recoverAnimation.mValueAnimator.start();
                viewHolder2 = viewHolder4;
                callback = callback2;
                viewHolder3 = null;
                z = true;
            } else {
                i2 = 8;
                if (view == this.mOverdrawChild) {
                    viewHolder3 = null;
                    this.mOverdrawChild = null;
                } else {
                    viewHolder3 = null;
                }
                viewHolder2 = viewHolder4;
                callback = callback2;
                callback.clearView(this.mRecyclerView, viewHolder2);
                z = false;
            }
            this.mSelected = viewHolder3;
        } else {
            callback = callback2;
            viewHolder2 = viewHolder4;
            i2 = 8;
            z = false;
        }
        if (viewHolder != null) {
            RecyclerView recyclerView3 = this.mRecyclerView;
            int movementFlags2 = callback.getMovementFlags(viewHolder);
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            int layoutDirection4 = ViewCompat.Api17Impl.getLayoutDirection(recyclerView3);
            int i20 = movementFlags2 & 3158064;
            if (i20 != 0) {
                int i21 = movementFlags2 & (~i20);
                if (layoutDirection4 == 0) {
                    i3 = 2;
                } else {
                    i3 = 2;
                    int i22 = i20 >> 1;
                    i21 |= i22 & (-3158065);
                    i20 = i22 & 3158064;
                }
                movementFlags2 = i21 | (i20 >> i3);
            }
            this.mSelectedFlags = (movementFlags2 & i12) >> (this.mActionState * i2);
            View view2 = viewHolder.itemView;
            this.mSelectedStartX = view2.getLeft();
            this.mSelectedStartY = view2.getTop();
            this.mSelected = viewHolder;
        }
        ViewParent parent = this.mRecyclerView.getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(this.mSelected != null);
        }
        if (!z) {
            this.mRecyclerView.mLayout.mRequestedSimpleAnimations = true;
        }
        int i23 = this.mActionState;
        if (i23 == 0) {
            callback.onSelectedChanged(viewHolder2, i23);
        } else {
            callback.onSelectedChanged(this.mSelected, i23);
        }
        if (i == 2) {
            this.mSelected.itemView.performHapticFeedback(0);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Callback {
        public static final InterpolatorC04471 sDragScrollInterpolator = new Interpolator() { // from class: androidx.recyclerview.widget.ItemTouchHelper.Callback.1
            @Override // android.animation.TimeInterpolator
            public final float getInterpolation(float f) {
                return f * f * f * f * f;
            }
        };
        public static final InterpolatorC04482 sDragViewScrollCapInterpolator = new InterpolatorC04482();
        public int mCachedMaxScrollSpeed = -1;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: androidx.recyclerview.widget.ItemTouchHelper$Callback$2 */
        public final class InterpolatorC04482 implements Interpolator {
            @Override // android.animation.TimeInterpolator
            public final float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * f2 * f2 * f2 * f2) + 1.0f;
            }
        }

        public static int makeMovementFlags(int i, int i2) {
            int i3 = (i2 | i) << 0;
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
                float floatValue = ((Float) tag).floatValue();
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api21Impl.setElevation(view, floatValue);
            }
            view.setTag(R.id.item_touch_helper_previous_elevation, null);
            view.setTranslationX(0.0f);
            view.setTranslationY(0.0f);
        }

        public abstract int getMovementFlags(RecyclerView.ViewHolder viewHolder);

        public float getSwipeThreshold() {
            return 0.5f;
        }

        public final int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int i, int i2, long j) {
            if (this.mCachedMaxScrollSpeed == -1) {
                this.mCachedMaxScrollSpeed = recyclerView.getResources().getDimensionPixelSize(R.dimen.item_touch_helper_max_drag_scroll_per_frame);
            }
            int interpolation = (int) (sDragViewScrollCapInterpolator.getInterpolation(Math.min(1.0f, (Math.abs(i2) * 1.0f) / i)) * ((int) Math.signum(i2)) * this.mCachedMaxScrollSpeed);
            float f = j <= 2000 ? j / 2000.0f : 1.0f;
            sDragScrollInterpolator.getClass();
            int i3 = (int) (f * f * f * f * f * interpolation);
            return i3 == 0 ? i2 > 0 ? 1 : -1 : i3;
        }

        public boolean isItemViewSwipeEnabled() {
            return !(this instanceof FavoritesModel$itemTouchHelperCallback$1);
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
                Float valueOf = Float.valueOf(ViewCompat.Api21Impl.getElevation(view));
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
                view.setTag(R.id.item_touch_helper_previous_elevation, valueOf);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

        /* renamed from: mX */
        public float f177mX;

        /* renamed from: mY */
        public float f178mY;
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
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mValueAnimator = ofFloat;
            ofFloat.setInterpolator(pathInterpolator);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.recyclerview.widget.ItemTouchHelper.RecoverAnimation.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    RecoverAnimation.this.mFraction = valueAnimator.getAnimatedFraction();
                }
            });
            ofFloat.setTarget(viewHolder.itemView);
            ofFloat.addListener(this);
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
