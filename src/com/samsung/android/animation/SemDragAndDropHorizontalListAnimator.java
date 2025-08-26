package com.samsung.android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SemHorizontalListView;
import com.android.internal.R;
import com.samsung.android.animation.SemAbsDragAndDropAnimator;
import com.samsung.android.animation.SemDragAndDropAnimationCore;

/* loaded from: classes6.dex */
public class SemDragAndDropHorizontalListAnimator extends SemAbsDragAndDropAnimator {
    private static final String TAG = "SemDragAndDropHListAnimator";
    private SemDragAndDropAnimationCore.ItemAnimationListener mItemAnimationListener;
    private SemHorizontalListView mListView;
    SparseIntArray mNonMovableItems;
    private AdapterView.OnItemLongClickListener mOnItemLongClickListener;
    private final int mScrollBarSize;

    public SemDragAndDropHorizontalListAnimator(Context context, SemHorizontalListView semHorizontalListView) {
        super(context, semHorizontalListView);
        this.mNonMovableItems = new SparseIntArray();
        this.mScrollBarSize = 10;
        this.mListView = semHorizontalListView;
        semHorizontalListView.setDndListAnimator(this);
        initListeners();
        this.mDndAnimationCore.setAnimationListener(this.mItemAnimationListener);
        this.mListView.setOnItemLongClickListener(this.mOnItemLongClickListener);
    }

    private void initListeners() {
        this.mItemAnimationListener = new SemDragAndDropAnimationCore.ItemAnimationListener() { // from class: com.samsung.android.animation.SemDragAndDropHorizontalListAnimator.1
            @Override // com.samsung.android.animation.SemDragAndDropAnimationCore.ItemAnimationListener
            public void onItemAnimatorEnd() {
                if (SemDragAndDropHorizontalListAnimator.this.mListItemSelectionAnimating) {
                    SemDragAndDropHorizontalListAnimator.this.mListItemSelectionAnimating = false;
                    return;
                }
                if (SemDragAndDropHorizontalListAnimator.this.mDropDonePending) {
                    SemDragAndDropHorizontalListAnimator.this.mDropDonePending = false;
                    if (SemDragAndDropHorizontalListAnimator.this.mDndController != null) {
                        SemDragAndDropHorizontalListAnimator.this.mDndController.dropDone(SemDragAndDropHorizontalListAnimator.this.mFirstDragPos, SemDragAndDropHorizontalListAnimator.this.mDragPos);
                        SemDragAndDropHorizontalListAnimator semDragAndDropHorizontalListAnimator = SemDragAndDropHorizontalListAnimator.this;
                        semDragAndDropHorizontalListAnimator.speakDragReleaseForAccessibility(semDragAndDropHorizontalListAnimator.mDragPos);
                    }
                    SemDragAndDropHorizontalListAnimator.this.mItemAnimator.removeAll();
                    SemDragAndDropHorizontalListAnimator.this.resetDndPositionValues();
                    if (SemDragAndDropHorizontalListAnimator.this.mDndListener != null) {
                        Log.d(SemDragAndDropHorizontalListAnimator.TAG, "dndListener.onDragAndDropEnd() from onItemAnimatorEnd()");
                        SemDragAndDropHorizontalListAnimator.this.mDndListener.onDragAndDropEnd();
                    }
                    SemDragAndDropHorizontalListAnimator.this.mListView.setEnabled(true);
                }
            }
        };
        this.mOnItemLongClickListener = new AdapterView.OnItemLongClickListener() { // from class: com.samsung.android.animation.SemDragAndDropHorizontalListAnimator.2
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                return SemDragAndDropHorizontalListAnimator.this.initDragIfNecessary(i);
            }
        };
    }

    public void setDragAndDropController(SemAbsDragAndDropAnimator.DragAndDropController dragAndDropController) {
        if (dragAndDropController == null) {
            this.mDndController = null;
        } else if (this.mListView.getHeaderViewsCount() == 0 && this.mListView.getFooterViewsCount() == 0) {
            this.mDndController = dragAndDropController;
        } else {
            this.mDndController = new HeaderFooterDndController(dragAndDropController);
        }
    }

    private boolean checkStartDnd(int i, int i2, int i3) {
        if (!checkDndGrabHandle(i, i2, i3)) {
            return false;
        }
        boolean zCanDrag = this.mDndController.canDrag(i3);
        if (!zCanDrag) {
            speakNotDraggableForAccessibility(i3);
        }
        return zCanDrag;
    }

    private boolean checkDndGrabHandle(int i, int i2, int i3) {
        if (activatedByLongPress()) {
            return true;
        }
        Rect rect = new Rect();
        SemHorizontalListView semHorizontalListView = this.mListView;
        semHorizontalListView.getChildAt(i3 - semHorizontalListView.getFirstVisiblePosition()).getHitRect(rect);
        getDragGrabHandleHitRect(rect, this.mTempRect);
        return this.mTempRect.contains(i, i2);
    }

    public boolean startDrag() {
        if (this.mTempEvent == null) {
            return false;
        }
        return initDragIfNecessary(this.mListView.pointToPosition((int) this.mTempEvent.getX(), (int) this.mTempEvent.getY()));
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x002b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action == 1) {
                if (isDraggable() && this.mDndTouchMode != 0) {
                    onTouchUpCancel(motionEvent);
                }
            } else if (action != 2) {
                if (action == 3) {
                }
            } else if (isDraggable() && this.mDndTouchMode == 1 && this.mListView.getCount() > 1 && activatedByLongPress()) {
                return true;
            }
        } else {
            if (!this.mListView.isEnabled()) {
                return false;
            }
            if (this.mTempEvent != null) {
                this.mTempEvent.recycle();
            }
            this.mTempEvent = MotionEvent.obtain(motionEvent);
            this.mActivePointerId = motionEvent.getPointerId(0);
            this.mDndTouchX = (int) motionEvent.getX();
            this.mDndTouchY = (int) motionEvent.getY();
            this.mFirstTouchX = this.mDndTouchX;
            if (isDraggable() && this.mListView.getCount() > 1) {
                int iPointToPosition = this.mListView.pointToPosition(this.mDndTouchX, this.mDndTouchY);
                if (iPointToPosition == -1) {
                    Log.d(TAG, "onInterceptTouchEvent : #1 return false, itemPosition invalid.");
                    return false;
                }
                if (activatedByLongPress()) {
                    Log.d(TAG, "onInterceptTouchEvent : #2 return false, activated By longPress.");
                    return false;
                }
                if (iPointToPosition < 0 || iPointToPosition >= this.mListView.getCount() || !checkStartDnd(this.mDndTouchX, this.mDndTouchY, iPointToPosition)) {
                    Log.d(TAG, "onInterceptTouchEvent : #3 resetDndState");
                    resetDndState();
                } else if (initDrag(iPointToPosition)) {
                    return true;
                }
            }
        }
        return false;
    }

    public AdapterView.OnItemLongClickListener getDragAndDropOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean initDragIfNecessary(int i) {
        if (!isDraggable() || !activatedByLongPress() || this.mListView.getCount() <= 1) {
            return false;
        }
        if (i >= 0 && i < this.mListView.getCount() && checkStartDnd(this.mDndTouchX, this.mDndTouchY, i)) {
            return initDrag(i);
        }
        resetDndState();
        return false;
    }

    private boolean initDrag(int i) {
        int i2;
        SemHorizontalListView semHorizontalListView = this.mListView;
        this.mDragView = semHorizontalListView.getChildAt(i - semHorizontalListView.getFirstVisiblePosition());
        if (this.mDragView == null) {
            Log.d(TAG, "initDrag : #4 return false, mDragView is null.");
            return false;
        }
        this.mListView.setEnableHoverDrawable(false);
        this.mDndTouchMode = 1;
        this.mFirstDragPos = i;
        this.mDragPos = this.mFirstDragPos;
        this.mDragView.getHitRect(this.mDragViewRect);
        speakDragStartForAccessibility(i);
        if (!this.mUserSetDragItemBitmap) {
            if (this.mDragViewBitmap != null) {
                this.mDragViewBitmap.recycle();
            }
            TypedValue typedValue = new TypedValue();
            int iRound = Math.round(this.mListView.getContext().getResources().getDisplayMetrics().density);
            if (this.mDragView.getContext().getTheme().resolveAttribute(16843828, typedValue, true)) {
                i2 = typedValue.data;
            } else {
                this.mDragView.getContext().getTheme().resolveAttribute(R.attr.parentIsDeviceDefaultDark, typedValue, true);
                i2 = typedValue.data == 0 ? 29406 : 4100607;
            }
            this.mDragViewBitmap = SemAnimatorUtils.getBitmapDrawableFromView(this.mDragView).getBitmap();
            Canvas canvas = new Canvas(this.mDragViewBitmap);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(i2);
            paint.setStrokeWidth(iRound);
            canvas.drawRect(new Rect(0, 0, this.mDragViewBitmap.getWidth() - 1, this.mDragViewBitmap.getHeight() - 1), paint);
        }
        setDragViewAlpha(this.mDragViewBitmapAlpha);
        if (this.mDragViewBitmap != null) {
            this.mDndTouchOffsetX = this.mDndTouchX - this.mDragViewRect.left;
        }
        startSelectHighlightingAnimation(this.mDragView);
        if (this.mDndListener != null) {
            Log.d(TAG, "dndListener.OnDragAndDropStart()");
            this.mDndListener.onDragAndDropStart();
        } else {
            Log.d(TAG, "dndListener is null");
        }
        this.mListView.invalidate();
        return true;
    }

    private void startSelectHighlightingAnimation(View view) {
        Rect rect = new Rect();
        view.getHitRect(rect);
        this.mListItemSelectionAnimating = true;
        this.mScaleUpAndDownAnimation = new SemDragAndDropAnimationCore.ItemSelectHighlightingAnimation(rect);
        this.mScaleUpAndDownAnimation.setStartAndDuration(0);
        this.mItemAnimator.putItemAnimation(this.mFirstDragPos, this.mScaleUpAndDownAnimation);
        this.mItemAnimator.start();
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isDraggable() || this.mDndTouchMode == 0) {
            return false;
        }
        int action = motionEvent.getAction();
        int i = action & 255;
        if (i == 1) {
            onTouchUpCancel(motionEvent);
        } else if (i == 2) {
            onTouchMove(motionEvent);
        } else if (i != 3) {
            if (i == 6) {
                int i2 = (action & 65280) >> 8;
                if (motionEvent.getPointerId(i2) == this.mActivePointerId) {
                    this.mActivePointerId = motionEvent.getPointerId(i2 == 0 ? 1 : 0);
                }
            }
        }
        return true;
    }

    private void onTouchMove(MotionEvent motionEvent) {
        int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
        if (iFindPointerIndex == -1) {
            this.mActivePointerId = motionEvent.getPointerId(0);
            iFindPointerIndex = 0;
        }
        this.mDndTouchX = (int) motionEvent.getX(iFindPointerIndex);
        this.mDndTouchY = (int) motionEvent.getY(iFindPointerIndex);
        if (this.mDndTouchX > (this.mListView.getRight() - this.mListView.getPaddingRight()) - this.mListView.getLeft()) {
            this.mDndTouchX = (this.mListView.getRight() - this.mListView.getPaddingRight()) - this.mListView.getLeft();
        } else if (this.mDndTouchX < this.mListView.getPaddingLeft()) {
            this.mDndTouchX = this.mListView.getPaddingLeft();
        }
        if (this.mScaleUpAndDownAnimation != null && !this.mScaleUpAndDownAnimation.isFinished() && Math.abs(this.mDndTouchX - this.mFirstTouchX) > 15.0f) {
            this.mListItemSelectionAnimating = false;
        }
        this.mDndTouchMode = 2;
        int paddingLeft = this.mListView.getPaddingLeft();
        View childAt = this.mListView.getChildAt(0);
        if (childAt != null) {
            paddingLeft += childAt.getWidth() / 2;
        }
        int right = (this.mListView.getRight() - this.mListView.getPaddingRight()) - this.mListView.getLeft();
        SemHorizontalListView semHorizontalListView = this.mListView;
        View childAt2 = semHorizontalListView.getChildAt(semHorizontalListView.getChildCount() - 1);
        if (childAt2 != null) {
            right -= childAt2.getWidth() / 2;
        }
        if (this.mDndTouchX > right || this.mDndTouchX < paddingLeft) {
            if (this.mDndAutoScrollMode == 0) {
                this.mListView.postOnAnimationDelayed(this.mAutoScrollRunnable, 150L);
            }
            if (this.mDndTouchX > right) {
                this.mDndAutoScrollMode = 2;
            }
            if (this.mDndTouchX < paddingLeft) {
                this.mDndAutoScrollMode = 1;
            }
        } else {
            this.mDndAutoScrollMode = 0;
        }
        if (this.mDndAutoScrollMode == 0) {
            this.mListView.removeCallbacks(this.mAutoScrollRunnable);
        }
        reorderIfNeeded();
    }

    @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator
    void reorderIfNeeded() {
        int i = this.mDragPos;
        int iFindDragItemPosition = findDragItemPosition((this.mDndTouchX - this.mDndTouchOffsetX) + (this.mDragViewRect.width() / 2));
        if (iFindDragItemPosition != -1 && this.mDndController.canDrop(this.mFirstDragPos, iFindDragItemPosition)) {
            this.mDragPos = iFindDragItemPosition;
        }
        if (i != this.mDragPos && !this.mListItemSelectionAnimating) {
            recalculateOffset(i, this.mDragPos);
            this.mItemAnimator.start();
        }
        if (i == this.mDragPos && this.mDragViewBitmap == null) {
            return;
        }
        this.mListView.invalidate();
    }

    private void onTouchUpCancel(MotionEvent motionEvent) {
        int right;
        int i;
        this.mActivePointerId = -1;
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        if (this.mDndTouchMode == 1) {
            resetDndState();
            if (this.mDndListener != null) {
                Log.d(TAG, "dndListener.onDragAndDropEnd() DND_TOUCH_STATUS_START");
                this.mDndListener.onDragAndDropEnd();
            }
        }
        if (this.mDndTouchMode != 2) {
            return;
        }
        View childAt = this.mListView.getChildAt(this.mFirstDragPos - firstVisiblePosition);
        View childAt2 = this.mListView.getChildAt(this.mDragPos - firstVisiblePosition);
        if (childAt == null || childAt2 == null) {
            int i2 = this.mDndTouchX - this.mDndTouchOffsetX;
            if (childAt2 != null) {
                right = childAt2.getLeft();
            } else if (this.mDragPos < firstVisiblePosition) {
                i = -((i2 - this.mListView.getChildAt(0).getLeft()) + this.mDragViewRect.width());
                Log.v(TAG, "dndListener.onTouchUp() dragView == null, distance = " + i);
                ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, i);
                valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.animation.SemDragAndDropHorizontalListAnimator.3
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        SemDragAndDropHorizontalListAnimator.this.mDragViewBitmapTranslateX = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        SemDragAndDropHorizontalListAnimator.this.mListView.invalidate();
                    }
                });
                valueAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemDragAndDropHorizontalListAnimator.4
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (SemDragAndDropHorizontalListAnimator.this.mFirstDragPos != SemDragAndDropHorizontalListAnimator.this.mDragPos) {
                            SemDragAndDropHorizontalListAnimator.this.mDndController.dropDone(SemDragAndDropHorizontalListAnimator.this.mFirstDragPos, SemDragAndDropHorizontalListAnimator.this.mDragPos);
                            SemDragAndDropHorizontalListAnimator semDragAndDropHorizontalListAnimator = SemDragAndDropHorizontalListAnimator.this;
                            semDragAndDropHorizontalListAnimator.speakDragReleaseForAccessibility(semDragAndDropHorizontalListAnimator.mDragPos);
                        }
                        SemDragAndDropHorizontalListAnimator.this.mItemAnimator.removeAll();
                        SemDragAndDropHorizontalListAnimator.this.resetDndState();
                        if (SemDragAndDropHorizontalListAnimator.this.mDndListener != null) {
                            Log.d(SemDragAndDropHorizontalListAnimator.TAG, "dndListener.onDragAndDropEnd() from onAnimationEnd()");
                            SemDragAndDropHorizontalListAnimator.this.mDndListener.onDragAndDropEnd();
                        }
                    }
                });
                valueAnimatorOfInt.setDuration(210L);
                valueAnimatorOfInt.setInterpolator(SINE_IN_OUT_70);
                valueAnimatorOfInt.start();
            } else if (this.mListView.getChildCount() > 0) {
                SemHorizontalListView semHorizontalListView = this.mListView;
                right = semHorizontalListView.getChildAt(semHorizontalListView.getChildCount() - 1).getRight();
            } else {
                Log.e(TAG, "mListView.getChildCount() = " + this.mListView.getChildCount());
                return;
            }
            i = right - i2;
            Log.v(TAG, "dndListener.onTouchUp() dragView == null, distance = " + i);
            ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(0, i);
            valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.animation.SemDragAndDropHorizontalListAnimator.3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SemDragAndDropHorizontalListAnimator.this.mDragViewBitmapTranslateX = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    SemDragAndDropHorizontalListAnimator.this.mListView.invalidate();
                }
            });
            valueAnimatorOfInt2.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemDragAndDropHorizontalListAnimator.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (SemDragAndDropHorizontalListAnimator.this.mFirstDragPos != SemDragAndDropHorizontalListAnimator.this.mDragPos) {
                        SemDragAndDropHorizontalListAnimator.this.mDndController.dropDone(SemDragAndDropHorizontalListAnimator.this.mFirstDragPos, SemDragAndDropHorizontalListAnimator.this.mDragPos);
                        SemDragAndDropHorizontalListAnimator semDragAndDropHorizontalListAnimator = SemDragAndDropHorizontalListAnimator.this;
                        semDragAndDropHorizontalListAnimator.speakDragReleaseForAccessibility(semDragAndDropHorizontalListAnimator.mDragPos);
                    }
                    SemDragAndDropHorizontalListAnimator.this.mItemAnimator.removeAll();
                    SemDragAndDropHorizontalListAnimator.this.resetDndState();
                    if (SemDragAndDropHorizontalListAnimator.this.mDndListener != null) {
                        Log.d(SemDragAndDropHorizontalListAnimator.TAG, "dndListener.onDragAndDropEnd() from onAnimationEnd()");
                        SemDragAndDropHorizontalListAnimator.this.mDndListener.onDragAndDropEnd();
                    }
                }
            });
            valueAnimatorOfInt2.setDuration(210L);
            valueAnimatorOfInt2.setInterpolator(SINE_IN_OUT_70);
            valueAnimatorOfInt2.start();
        } else if (this.mListItemSelectionAnimating) {
            resetDndState();
            if (this.mDndListener != null) {
                Log.d(TAG, "dndListener.onDragAndDropEnd() mListItemSelectionAnimating is true");
                this.mDndListener.onDragAndDropEnd();
            }
        } else {
            int left = childAt2.getLeft() - childAt.getLeft();
            int left2 = childAt2.getLeft() - (this.mDndTouchX - this.mDndTouchOffsetX);
            SemDragAndDropAnimationCore.TranslateItemAnimation translateItemAnimation = new SemDragAndDropAnimationCore.TranslateItemAnimation();
            translateItemAnimation.translate(left, left2, 0, 0);
            translateItemAnimation.setStartAndDuration(0.7f);
            this.mItemAnimator.putItemAnimation(this.mFirstDragPos, translateItemAnimation);
            this.mItemAnimator.start();
            this.mRetainFirstDragViewPos = this.mFirstDragPos - firstVisiblePosition;
            this.mListView.setEnabled(false);
            this.mDropDonePending = true;
            resetDndTouchValuesAndBitmap();
            Log.d(TAG, "onTouchUp() start last animation");
        }
        this.mDndAutoScrollMode = 0;
        this.mListView.removeCallbacks(this.mAutoScrollRunnable);
        this.mListView.invalidate();
    }

    @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator
    void resetDndTouchValuesAndBitmap() {
        super.resetDndTouchValuesAndBitmap();
        this.mNonMovableItems.clear();
    }

    @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator
    void resetDndPositionValues() {
        super.resetDndPositionValues();
        this.mListView.setEnableHoverDrawable(true);
    }

    private int findDragItemPosition(int i) {
        int childCount = this.mListView.getChildCount();
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        if (childCount <= 0) {
            return -1;
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            this.mListView.getChildAt(i2).getHitRect(this.mTempRect);
            if (this.mTempRect.contains(i, this.mTempRect.centerY())) {
                return i2 + firstVisiblePosition;
            }
        }
        return -1;
    }

    private int findMovedItemPosition(int i) {
        int childCount = this.mListView.getChildCount();
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        if (childCount <= 0) {
            return -1;
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            if (i2 != this.mFirstDragPos - firstVisiblePosition) {
                this.mListView.getChildAt(i2).getHitRect(this.mTempRect);
                int i3 = i2 + firstVisiblePosition;
                SemDragAndDropAnimationCore.ItemAnimation itemAnimation = this.mItemAnimator.getItemAnimation(i3);
                if (itemAnimation instanceof SemDragAndDropAnimationCore.TranslateItemAnimation) {
                    if (this.mTempRect.contains(i - ((SemDragAndDropAnimationCore.TranslateItemAnimation) itemAnimation).getDestOffsetX(), this.mTempRect.centerY())) {
                        return i3;
                    }
                } else {
                    continue;
                }
            }
        }
        return -1;
    }

    private void addReturningTranslation(int i) {
        SemDragAndDropAnimationCore.ItemAnimation itemAnimation = this.mItemAnimator.getItemAnimation(i);
        if (itemAnimation instanceof SemDragAndDropAnimationCore.TranslateItemAnimation) {
            SemDragAndDropAnimationCore.TranslateItemAnimation translateItemAnimation = (SemDragAndDropAnimationCore.TranslateItemAnimation) itemAnimation;
            translateItemAnimation.translate(0, -((int) translateItemAnimation.getCurrentTranslateX()), 0, 0);
            translateItemAnimation.setStartAndDuration(translateItemAnimation.getProgress());
        }
    }

    private void addNewTranslation(int i, int i2) {
        SemDragAndDropAnimationCore.TranslateItemAnimation translateItemAnimation;
        SemDragAndDropAnimationCore.ItemAnimation itemAnimation = this.mItemAnimator.getItemAnimation(i);
        if (itemAnimation instanceof SemDragAndDropAnimationCore.TranslateItemAnimation) {
            translateItemAnimation = (SemDragAndDropAnimationCore.TranslateItemAnimation) itemAnimation;
        } else {
            translateItemAnimation = new SemDragAndDropAnimationCore.TranslateItemAnimation();
        }
        translateItemAnimation.translate(i2, i2 - (!translateItemAnimation.isFinished() ? (int) translateItemAnimation.getCurrentTranslateX() : 0), 0, 0);
        if (!translateItemAnimation.isFinished()) {
            translateItemAnimation.setStartAndDuration(translateItemAnimation.getProgress());
        } else {
            translateItemAnimation.setStartAndDuration(0);
        }
        this.mItemAnimator.putItemAnimation(i, translateItemAnimation);
    }

    private void recalculateOffset(int i, int i2) {
        View childAt;
        int dividerHeight = this.mListView.getDividerHeight();
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        int iWidth = this.mDragViewRect.width() + dividerHeight;
        if (i2 <= i) {
            for (int i3 = i - 1; i3 >= i2; i3--) {
                if (i3 < this.mFirstDragPos) {
                    if (this.mDndController.canDrop(this.mFirstDragPos, i3)) {
                        int i4 = i3;
                        int i5 = iWidth;
                        while (true) {
                            i4++;
                            if (this.mNonMovableItems.indexOfKey(i4) < 0) {
                                break;
                            } else {
                                i5 += this.mNonMovableItems.get(i4);
                            }
                        }
                        if (this.mListView.isLayoutRtl()) {
                            addNewTranslation(i3, -i5);
                        } else {
                            addNewTranslation(i3, i5);
                        }
                    } else if (this.mNonMovableItems.get(i3, -1) == -1 && (childAt = this.mListView.getChildAt(i3 - firstVisiblePosition)) != null) {
                        this.mNonMovableItems.put(i3, childAt.getWidth() + dividerHeight);
                    }
                } else {
                    View childAt2 = this.mListView.getChildAt(i3 - firstVisiblePosition);
                    if (childAt2 == null) {
                        Log.e(TAG, "recalculateOffset('dragging up') no such item, i = " + i3);
                    } else {
                        addReturningTranslation(findMovedItemPosition(SemAnimatorUtils.getViewCenterX(childAt2)));
                    }
                }
            }
            return;
        }
        while (true) {
            i++;
            if (i > i2) {
                return;
            }
            if (i > this.mFirstDragPos) {
                if (this.mDndController.canDrop(this.mFirstDragPos, i)) {
                    int i6 = i;
                    int i7 = iWidth;
                    while (true) {
                        i6--;
                        if (this.mNonMovableItems.indexOfKey(i6) < 0) {
                            break;
                        } else {
                            i7 += this.mNonMovableItems.get(i6);
                        }
                    }
                    if (this.mListView.isLayoutRtl()) {
                        addNewTranslation(i, i7);
                    } else {
                        addNewTranslation(i, -i7);
                    }
                } else {
                    View childAt3 = this.mListView.getChildAt(i - firstVisiblePosition);
                    if (childAt3 != null) {
                        this.mNonMovableItems.put(i, childAt3.getWidth() + dividerHeight);
                    }
                }
            } else {
                View childAt4 = this.mListView.getChildAt(i - firstVisiblePosition);
                if (childAt4 == null) {
                    Log.e(TAG, "recalculateOffset('dragging down') no such item, i = " + i);
                } else {
                    addReturningTranslation(findMovedItemPosition(SemAnimatorUtils.getViewCenterX(childAt4)));
                }
            }
        }
    }

    private void getDragGrabHandleHitRect(Rect rect, Rect rect2) {
        if (this.mDragGrabHandleDrawable != null) {
            int intrinsicWidth = this.mDragGrabHandleDrawable.getIntrinsicWidth();
            int intrinsicHeight = this.mDragGrabHandleDrawable.getIntrinsicHeight();
            rect.left += this.mDragGrabHandlePadding.left;
            rect.top += this.mDragGrabHandlePadding.top;
            rect.right += this.mDragGrabHandlePadding.right;
            rect.bottom += this.mDragGrabHandlePadding.bottom;
            rect.top -= 10;
            rect.bottom -= 10;
            Gravity.apply(this.mDragGrabHandlePosGravity, intrinsicWidth, intrinsicHeight, rect, rect2);
        }
    }

    private void drawDragHandle(Canvas canvas, Rect rect, boolean z, boolean z2) {
        if (this.mDragGrabHandleDrawable == null || !z2) {
            return;
        }
        getDragGrabHandleHitRect(rect, this.mTempRect);
        this.mDragGrabHandleDrawable.setBounds(this.mTempRect);
        this.mDragGrabHandleDrawable.setState(z ? PRESSED_STATE_SET : EMPTY_STATE_SET);
        this.mDragGrabHandleDrawable.setAlpha(this.mDragHandleAlpha);
        this.mDragGrabHandleDrawable.draw(canvas);
    }

    public boolean preDrawChild(Canvas canvas, View view, long j) {
        int iIndexOfChild = this.mListView.indexOfChild(view) + this.mListView.getFirstVisiblePosition();
        if (isDraggable() && iIndexOfChild == this.mFirstDragPos && !this.mDropDonePending && !this.mListItemSelectionAnimating) {
            return false;
        }
        SemDragAndDropAnimationCore.ItemAnimation itemAnimation = this.mItemAnimator.getItemAnimation(iIndexOfChild);
        this.mCanvasSaveCount = 0;
        if (itemAnimation == null) {
            return true;
        }
        itemAnimation.getTransformation(this.mTempTrans);
        this.mCanvasSaveCount = canvas.save();
        canvas.concat(this.mTempTrans.getMatrix());
        return true;
    }

    public void postDrawChild(Canvas canvas, View view, long j) {
        drawDragHandlerIfNeeded(canvas, view, j);
        if (this.mCanvasSaveCount > 0) {
            canvas.restoreToCount(this.mCanvasSaveCount);
        }
    }

    private void drawDragHandlerIfNeeded(Canvas canvas, View view, long j) {
        if (isDraggable()) {
            int iIndexOfChild = this.mListView.indexOfChild(view) + this.mListView.getFirstVisiblePosition();
            if (!this.mListView.getAdapter().isEnabled(iIndexOfChild) || isHeaderOrFooterViewPos(iIndexOfChild)) {
                return;
            }
            view.getHitRect(this.mTempRect);
            drawDragHandle(canvas, this.mTempRect, false, this.mDndController.canDrag(iIndexOfChild));
        }
    }

    private boolean isHeaderOrFooterViewPos(int i) {
        return i < this.mListView.getHeaderViewsCount() || i >= this.mListView.getCount() - this.mListView.getFooterViewsCount();
    }

    public void dispatchDraw(Canvas canvas) {
        if (!isDraggable() || this.mDragViewBitmap == null) {
            return;
        }
        int paddingTop = this.mListView.getPaddingTop();
        int i = this.mDndTouchX - this.mDndTouchOffsetX;
        if (this.mListItemSelectionAnimating || this.mDragViewBitmap.isRecycled()) {
            return;
        }
        canvas.drawBitmap(this.mDragViewBitmap, this.mDragViewBitmapTranslateX + i, paddingTop, this.mDragViewBitmapPaint);
        this.mTempRect.top = -this.mListView.getPaddingTop();
        this.mTempRect.left = i + this.mDragViewBitmapTranslateX;
        this.mTempRect.right = this.mTempRect.left + this.mDragViewRect.width();
        this.mTempRect.bottom = this.mTempRect.top + this.mListView.getHeight();
        drawDragHandle(canvas, this.mTempRect, true, true);
    }

    private class HeaderFooterDndController implements SemAbsDragAndDropAnimator.DragAndDropController {
        private final SemAbsDragAndDropAnimator.DragAndDropController mWrappedController;

        HeaderFooterDndController(SemAbsDragAndDropAnimator.DragAndDropController dragAndDropController) {
            this.mWrappedController = dragAndDropController;
        }

        @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.DragAndDropController
        public boolean canDrag(int i) {
            if (this.mWrappedController == null || i < SemDragAndDropHorizontalListAnimator.this.mListView.getHeaderViewsCount() || i >= SemDragAndDropHorizontalListAnimator.this.mListView.getCount() - SemDragAndDropHorizontalListAnimator.this.mListView.getFooterViewsCount()) {
                return false;
            }
            return this.mWrappedController.canDrag(i - SemDragAndDropHorizontalListAnimator.this.mListView.getHeaderViewsCount());
        }

        @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.DragAndDropController
        public boolean canDrop(int i, int i2) {
            if (this.mWrappedController == null || i2 < SemDragAndDropHorizontalListAnimator.this.mListView.getHeaderViewsCount() || i2 >= SemDragAndDropHorizontalListAnimator.this.mListView.getCount() - SemDragAndDropHorizontalListAnimator.this.mListView.getFooterViewsCount()) {
                return false;
            }
            return this.mWrappedController.canDrop(i - SemDragAndDropHorizontalListAnimator.this.mListView.getHeaderViewsCount(), i2 - SemDragAndDropHorizontalListAnimator.this.mListView.getHeaderViewsCount());
        }

        @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.DragAndDropController
        public void dropDone(int i, int i2) {
            if (this.mWrappedController != null) {
                if (i < SemDragAndDropHorizontalListAnimator.this.mListView.getHeaderViewsCount()) {
                    i = SemDragAndDropHorizontalListAnimator.this.mListView.getHeaderViewsCount();
                } else if (i > SemDragAndDropHorizontalListAnimator.this.mListView.getCount() - SemDragAndDropHorizontalListAnimator.this.mListView.getFooterViewsCount()) {
                    i = (SemDragAndDropHorizontalListAnimator.this.mListView.getCount() - SemDragAndDropHorizontalListAnimator.this.mListView.getFooterViewsCount()) - 1;
                }
                if (i2 < SemDragAndDropHorizontalListAnimator.this.mListView.getHeaderViewsCount()) {
                    i2 = SemDragAndDropHorizontalListAnimator.this.mListView.getHeaderViewsCount();
                } else if (i2 >= SemDragAndDropHorizontalListAnimator.this.mListView.getCount() - SemDragAndDropHorizontalListAnimator.this.mListView.getFooterViewsCount()) {
                    i2 = (SemDragAndDropHorizontalListAnimator.this.mListView.getCount() - SemDragAndDropHorizontalListAnimator.this.mListView.getFooterViewsCount()) - 1;
                }
                this.mWrappedController.dropDone(i - SemDragAndDropHorizontalListAnimator.this.mListView.getHeaderViewsCount(), i2 - SemDragAndDropHorizontalListAnimator.this.mListView.getHeaderViewsCount());
            }
        }
    }
}
