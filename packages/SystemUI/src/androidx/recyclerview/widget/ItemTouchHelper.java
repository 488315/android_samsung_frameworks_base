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
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int findPointerIndex;
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
                        View findChildView = itemTouchHelper.findChildView(motionEvent);
                        int size = ((ArrayList) itemTouchHelper.mRecoverAnimations).size() - 1;
                        while (true) {
                            if (size < 0) {
                                break;
                            }
                            RecoverAnimation recoverAnimation2 = (RecoverAnimation) ((ArrayList) itemTouchHelper.mRecoverAnimations).get(size);
                            if (recoverAnimation2.mViewHolder.itemView == findChildView) {
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
            itemTouchHelper.mGestureDetector.mDetector.onTouchEvent(motionEvent);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.recyclerview.widget.ItemTouchHelper$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x0099, code lost:
        
            if (r4 < 0) goto L36;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x00bc, code lost:
        
            if (r4 > 0) goto L36;
         */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0080  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x00c2  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x00da  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x0100  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x010d  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x00f1  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 291
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.ItemTouchHelper.AnonymousClass1.run():void");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            View findChildView;
            RecyclerView.ViewHolder childViewHolder;
            if (!this.mShouldReactToLongPress || (findChildView = ItemTouchHelper.this.findChildView(motionEvent)) == null || (childViewHolder = ItemTouchHelper.this.mRecyclerView.getChildViewHolder(findChildView)) == null) {
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
                int findPointerIndex = motionEvent.findPointerIndex(i);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public final void checkSelectForSwipe(int i, int i2, MotionEvent motionEvent) {
        View findChildView;
        if (this.mSelected == null && i == 2 && this.mActionState != 2) {
            Callback callback = this.mCallback;
            if (callback.isItemViewSwipeEnabled()) {
                RecyclerView recyclerView = this.mRecyclerView;
                if (recyclerView.mScrollState == 1) {
                    return;
                }
                RecyclerView.LayoutManager layoutManager = recyclerView.mLayout;
                int i3 = this.mActivePointerId;
                RecyclerView.ViewHolder viewHolder = null;
                if (i3 != -1) {
                    int findPointerIndex = motionEvent.findPointerIndex(i3);
                    float x = motionEvent.getX(findPointerIndex) - this.mInitialTouchX;
                    float y = motionEvent.getY(findPointerIndex) - this.mInitialTouchY;
                    float abs = Math.abs(x);
                    float abs2 = Math.abs(y);
                    float f = this.mSlop;
                    if ((abs >= f || abs2 >= f) && ((abs <= abs2 || !layoutManager.canScrollHorizontally()) && ((abs2 <= abs || !layoutManager.canScrollVertically()) && (findChildView = findChildView(motionEvent)) != null))) {
                        viewHolder = this.mRecyclerView.getChildViewHolder(findChildView);
                    }
                }
                if (viewHolder == null) {
                    return;
                }
                RecyclerView recyclerView2 = this.mRecyclerView;
                int movementFlags = callback.getMovementFlags(viewHolder);
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                int convertToAbsoluteDirection = (Callback.convertToAbsoluteDirection(movementFlags, recyclerView2.getLayoutDirection()) & 65280) >> 8;
                if (convertToAbsoluteDirection == 0) {
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
                        if (f2 < 0.0f && (convertToAbsoluteDirection & 4) == 0) {
                            return;
                        }
                        if (f2 > 0.0f && (convertToAbsoluteDirection & 8) == 0) {
                            return;
                        }
                    } else {
                        if (f3 < 0.0f && (convertToAbsoluteDirection & 1) == 0) {
                            return;
                        }
                        if (f3 > 0.0f && (convertToAbsoluteDirection & 2) == 0) {
                            return;
                        }
                    }
                    this.mDy = 0.0f;
                    this.mDx = 0.0f;
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    select(viewHolder, 1);
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
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "getSelectedDxDy: #1 calledBy = ", " outPosition[0] = ");
            m.append(fArr[0]);
            m.append(", mSelectedStartX = ");
            m.append(this.mSelectedStartX);
            m.append(", mDx = ");
            m.append(this.mDx);
            m.append(", mSelected.itemView.getLeft() = ");
            m.append(this.mSelected.itemView.getLeft());
            Log.i("ItemTouchHelper", m.toString());
        } else {
            fArr[0] = this.mSelected.itemView.getTranslationX();
            StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "getSelectedDxDy: #2 calledBy = ", " outPosition[0] = ");
            m2.append(this.mSelected.itemView.getTranslationX());
            Log.i("ItemTouchHelper", m2.toString());
        }
        if ((this.mSelectedFlags & 3) != 0) {
            fArr[1] = (this.mSelectedStartY + this.mDy) - this.mSelected.itemView.getTop();
        } else {
            fArr[1] = this.mSelected.itemView.getTranslationY();
        }
    }

    public final void moveIfNecessary(RecyclerView.ViewHolder viewHolder) {
        boolean z;
        int abs;
        int abs2;
        int left;
        int abs3;
        int right;
        int abs4;
        int i;
        boolean z2;
        if (!this.mRecyclerView.isLayoutRequested() && this.mActionState == 2) {
            Callback callback = this.mCallback;
            callback.getClass();
            int i2 = (int) (this.mSelectedStartX + this.mDx);
            int i3 = (int) (this.mSelectedStartY + this.mDy);
            if (Math.abs(i3 - viewHolder.itemView.getTop()) >= viewHolder.itemView.getHeight() * 0.5f || Math.abs(i2 - viewHolder.itemView.getLeft()) >= viewHolder.itemView.getWidth() * 0.5f) {
                List list = this.mSwapTargets;
                if (list == null) {
                    this.mSwapTargets = new ArrayList();
                    this.mDistances = new ArrayList();
                } else {
                    ((ArrayList) list).clear();
                    ((ArrayList) this.mDistances).clear();
                }
                int round = Math.round(this.mSelectedStartX + this.mDx);
                int round2 = Math.round(this.mSelectedStartY + this.mDy);
                int width = viewHolder.itemView.getWidth() + round;
                int height = viewHolder.itemView.getHeight() + round2;
                int i4 = (round + width) / 2;
                int i5 = (round2 + height) / 2;
                RecyclerView.LayoutManager layoutManager = this.mRecyclerView.mLayout;
                int childCount = layoutManager.getChildCount();
                Rect rect = new Rect(0, 0, this.mRecyclerView.getWidth(), this.mRecyclerView.getHeight());
                Rect rect2 = new Rect(round, round2, width, height);
                if (round < 0) {
                    rect2.right -= round;
                    rect2.left = 0;
                    z = false;
                } else {
                    z = true;
                }
                if (round2 < 0) {
                    rect2.bottom -= round2;
                    rect2.top = 0;
                    z = false;
                }
                if (width > this.mRecyclerView.getWidth()) {
                    rect2.left -= width - this.mRecyclerView.getWidth();
                    rect2.right = this.mRecyclerView.getWidth();
                    z = false;
                }
                if (height > this.mRecyclerView.getHeight()) {
                    rect2.top -= height - this.mRecyclerView.getHeight();
                    rect2.bottom = this.mRecyclerView.getHeight();
                    z = false;
                }
                int i6 = 0;
                while (i6 < childCount) {
                    View childAt = layoutManager.getChildAt(i6);
                    if (childAt == null || childAt == viewHolder.itemView) {
                        i = i3;
                        z2 = z;
                    } else {
                        i = i3;
                        z2 = z;
                        Rect rect3 = new Rect(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom());
                        if (Rect.intersects(rect2, rect3) && (z2 || rect.contains(rect3))) {
                            RecyclerView.ViewHolder childViewHolder = this.mRecyclerView.getChildViewHolder(childAt);
                            if (callback.canDropOver(this.mSelected, childViewHolder)) {
                                int abs5 = Math.abs(i4 - ((childAt.getRight() + childAt.getLeft()) / 2));
                                int abs6 = Math.abs(i5 - ((childAt.getBottom() + childAt.getTop()) / 2));
                                int i7 = (abs6 * abs6) + (abs5 * abs5);
                                int size = ((ArrayList) this.mSwapTargets).size();
                                int i8 = 0;
                                for (int i9 = 0; i9 < size && i7 > ((Integer) ((ArrayList) this.mDistances).get(i9)).intValue(); i9++) {
                                    i8++;
                                }
                                ((ArrayList) this.mSwapTargets).add(i8, childViewHolder);
                                ((ArrayList) this.mDistances).add(i8, Integer.valueOf(i7));
                            }
                        }
                    }
                    i6++;
                    i3 = i;
                    z = z2;
                }
                int i10 = i3;
                ArrayList arrayList = (ArrayList) this.mSwapTargets;
                if (arrayList.size() == 0) {
                    return;
                }
                int width2 = viewHolder.itemView.getWidth() + i2;
                int height2 = viewHolder.itemView.getHeight() + i10;
                int left2 = i2 - viewHolder.itemView.getLeft();
                int top = i10 - viewHolder.itemView.getTop();
                int size2 = arrayList.size();
                RecyclerView.ViewHolder viewHolder2 = null;
                int i11 = -1;
                for (int i12 = 0; i12 < size2; i12++) {
                    RecyclerView.ViewHolder viewHolder3 = (RecyclerView.ViewHolder) arrayList.get(i12);
                    if (left2 > 0 && (right = viewHolder3.itemView.getRight() - width2) < 0 && viewHolder3.itemView.getRight() > viewHolder.itemView.getRight() && (abs4 = Math.abs(right)) > i11) {
                        i11 = abs4;
                        viewHolder2 = viewHolder3;
                    }
                    if (left2 < 0 && (left = viewHolder3.itemView.getLeft() - i2) > 0 && viewHolder3.itemView.getLeft() < viewHolder.itemView.getLeft() && (abs3 = Math.abs(left)) > i11) {
                        i11 = abs3;
                        viewHolder2 = viewHolder3;
                    }
                    if (top < 0) {
                        int bottom = (viewHolder3.itemView.getBottom() + viewHolder3.itemView.getTop()) / 2;
                        int bottom2 = (viewHolder.itemView.getBottom() + viewHolder.itemView.getTop()) / 2;
                        int i13 = bottom - i10;
                        if (i13 > 0 && bottom < bottom2 && (abs2 = Math.abs(i13)) > i11) {
                            i11 = abs2;
                            viewHolder2 = viewHolder3;
                        }
                    }
                    if (top > 0) {
                        int bottom3 = (viewHolder3.itemView.getBottom() + viewHolder3.itemView.getTop()) / 2;
                        int bottom4 = (viewHolder.itemView.getBottom() + viewHolder.itemView.getTop()) / 2;
                        int i14 = bottom3 - height2;
                        if (i14 < 0 && bottom3 > bottom4 && (abs = Math.abs(i14)) > i11) {
                            i11 = abs;
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
            float f4 = recoverAnimation.mStartDx;
            float f5 = recoverAnimation.mTargetX;
            if (f4 == f5) {
                recoverAnimation.mX = recoverAnimation.mViewHolder.itemView.getTranslationX();
            } else {
                recoverAnimation.mX = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f5, f4, recoverAnimation.mFraction, f4);
            }
            float f6 = recoverAnimation.mStartDy;
            float f7 = recoverAnimation.mTargetY;
            if (f6 == f7) {
                recoverAnimation.mY = recoverAnimation.mViewHolder.itemView.getTranslationY();
            } else {
                recoverAnimation.mY = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f7, f6, recoverAnimation.mFraction, f6);
            }
            int save = canvas.save();
            callback.onChildDraw(canvas, recyclerView, recoverAnimation.mViewHolder, recoverAnimation.mX, recoverAnimation.mY, recoverAnimation.mActionState, false);
            canvas.restoreToCount(save);
        }
        if (viewHolder != null) {
            int save2 = canvas.save();
            callback.onChildDraw(canvas, recyclerView, viewHolder, f2, f, i, true);
            canvas.restoreToCount(save2);
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
            int save = canvas.save();
            callback.onChildDrawOver(canvas, recyclerView, recoverAnimation.mViewHolder, recoverAnimation.mX, recoverAnimation.mY, recoverAnimation.mActionState, false);
            canvas.restoreToCount(save);
        }
        if (viewHolder != null) {
            int save2 = canvas.save();
            callback.onChildDrawOver(canvas, recyclerView, viewHolder, f2, f, i, true);
            canvas.restoreToCount(save2);
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

    /* JADX WARN: Code restructure failed: missing block: B:84:0x0091, code lost:
    
        if (r8 > 0) goto L43;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void select(androidx.recyclerview.widget.RecyclerView.ViewHolder r21, int r22) {
        /*
            Method dump skipped, instructions count: 545
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.ItemTouchHelper.select(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Callback {
        public static final AnonymousClass1 sDragScrollInterpolator = new AnonymousClass1();
        public static final AnonymousClass2 sDragViewScrollCapInterpolator = new AnonymousClass2();
        public int mCachedMaxScrollSpeed = -1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: androidx.recyclerview.widget.ItemTouchHelper$Callback$1, reason: invalid class name */
        public class AnonymousClass1 implements Interpolator {
            @Override // android.animation.TimeInterpolator
            public final float getInterpolation(float f) {
                return f * f * f * f * f;
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
