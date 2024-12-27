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

/* loaded from: classes5.dex */
public class SemDragAndDropHorizontalListAnimator extends SemAbsDragAndDropAnimator {
    private static final String TAG = "SemDragAndDropHListAnimator";
    private SemDragAndDropAnimationCore.ItemAnimationListener mItemAnimationListener;
    private SemHorizontalListView mListView;
    SparseIntArray mNonMovableItems;
    private AdapterView.OnItemLongClickListener mOnItemLongClickListener;
    private final int mScrollBarSize;

    public SemDragAndDropHorizontalListAnimator(Context context, SemHorizontalListView listview) {
        super(context, listview);
        this.mNonMovableItems = new SparseIntArray();
        this.mScrollBarSize = 10;
        this.mListView = listview;
        this.mListView.setDndListAnimator(this);
        initListeners();
        this.mDndAnimationCore.setAnimationListener(this.mItemAnimationListener);
        this.mListView.setOnItemLongClickListener(this.mOnItemLongClickListener);
    }

    private void initListeners() {
        this.mItemAnimationListener =
                new SemDragAndDropAnimationCore
                        .ItemAnimationListener() { // from class:
                                                   // com.samsung.android.animation.SemDragAndDropHorizontalListAnimator.1
                    @Override // com.samsung.android.animation.SemDragAndDropAnimationCore.ItemAnimationListener
                    public void onItemAnimatorEnd() {
                        if (SemDragAndDropHorizontalListAnimator.this.mListItemSelectionAnimating) {
                            SemDragAndDropHorizontalListAnimator.this.mListItemSelectionAnimating =
                                    false;
                            return;
                        }
                        if (SemDragAndDropHorizontalListAnimator.this.mDropDonePending) {
                            SemDragAndDropHorizontalListAnimator.this.mDropDonePending = false;
                            if (SemDragAndDropHorizontalListAnimator.this.mDndController != null) {
                                SemDragAndDropHorizontalListAnimator.this.mDndController.dropDone(
                                        SemDragAndDropHorizontalListAnimator.this.mFirstDragPos,
                                        SemDragAndDropHorizontalListAnimator.this.mDragPos);
                                SemDragAndDropHorizontalListAnimator.this
                                        .speakDragReleaseForAccessibility(
                                                SemDragAndDropHorizontalListAnimator.this.mDragPos);
                            }
                            SemDragAndDropHorizontalListAnimator.this.mItemAnimator.removeAll();
                            SemDragAndDropHorizontalListAnimator.this.resetDndPositionValues();
                            if (SemDragAndDropHorizontalListAnimator.this.mDndListener != null) {
                                Log.d(
                                        SemDragAndDropHorizontalListAnimator.TAG,
                                        "dndListener.onDragAndDropEnd() from onItemAnimatorEnd()");
                                SemDragAndDropHorizontalListAnimator.this.mDndListener
                                        .onDragAndDropEnd();
                            }
                            SemDragAndDropHorizontalListAnimator.this.mListView.setEnabled(true);
                        }
                    }
                };
        this.mOnItemLongClickListener =
                new AdapterView
                        .OnItemLongClickListener() { // from class:
                                                     // com.samsung.android.animation.SemDragAndDropHorizontalListAnimator.2
                    @Override // android.widget.AdapterView.OnItemLongClickListener
                    public boolean onItemLongClick(
                            AdapterView<?> parent, View view, int position, long id) {
                        return SemDragAndDropHorizontalListAnimator.this.initDragIfNecessary(
                                position);
                    }
                };
    }

    public void setDragAndDropController(
            SemAbsDragAndDropAnimator.DragAndDropController dndController) {
        if (dndController == null) {
            this.mDndController = null;
        } else if (this.mListView.getHeaderViewsCount() == 0
                && this.mListView.getFooterViewsCount() == 0) {
            this.mDndController = dndController;
        } else {
            this.mDndController = new HeaderFooterDndController(dndController);
        }
    }

    private boolean checkStartDnd(int x, int y, int itemPosition) {
        if (!checkDndGrabHandle(x, y, itemPosition)) {
            return false;
        }
        boolean canDrag = this.mDndController.canDrag(itemPosition);
        if (!canDrag) {
            speakNotDraggableForAccessibility(itemPosition);
        }
        return canDrag;
    }

    private boolean checkDndGrabHandle(int x, int y, int itemPosition) {
        if (activatedByLongPress()) {
            return true;
        }
        Rect childRect = new Rect();
        View v = this.mListView.getChildAt(itemPosition - this.mListView.getFirstVisiblePosition());
        v.getHitRect(childRect);
        getDragGrabHandleHitRect(childRect, this.mTempRect);
        return this.mTempRect.contains(x, y);
    }

    public boolean startDrag() {
        if (this.mTempEvent == null) {
            return false;
        }
        int position =
                this.mListView.pointToPosition(
                        (int) this.mTempEvent.getX(), (int) this.mTempEvent.getY());
        return initDragIfNecessary(position);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b9 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r8) {
        /*
            r7 = this;
            int r0 = r8.getAction()
            r1 = 1
            r2 = 0
            switch(r0) {
                case 0: goto L33;
                case 1: goto L24;
                case 2: goto Lb;
                case 3: goto L24;
                default: goto L9;
            }
        L9:
            goto Lb9
        Lb:
            boolean r3 = r7.isDraggable()
            if (r3 == 0) goto Lb9
            int r3 = r7.mDndTouchMode
            if (r3 != r1) goto Lb9
            android.widget.SemHorizontalListView r3 = r7.mListView
            int r3 = r3.getCount()
            if (r3 <= r1) goto Lb9
            boolean r3 = r7.activatedByLongPress()
            if (r3 == 0) goto Lb9
            return r1
        L24:
            boolean r1 = r7.isDraggable()
            if (r1 == 0) goto Lb9
            int r1 = r7.mDndTouchMode
            if (r1 == 0) goto Lb9
            r7.onTouchUpCancel(r8)
            goto Lb9
        L33:
            android.widget.SemHorizontalListView r3 = r7.mListView
            boolean r3 = r3.isEnabled()
            if (r3 != 0) goto L3c
            return r2
        L3c:
            android.view.MotionEvent r3 = r7.mTempEvent
            if (r3 == 0) goto L45
            android.view.MotionEvent r3 = r7.mTempEvent
            r3.recycle()
        L45:
            android.view.MotionEvent r3 = android.view.MotionEvent.obtain(r8)
            r7.mTempEvent = r3
            int r3 = r8.getPointerId(r2)
            r7.mActivePointerId = r3
            float r3 = r8.getX()
            int r3 = (int) r3
            r7.mDndTouchX = r3
            float r3 = r8.getY()
            int r3 = (int) r3
            r7.mDndTouchY = r3
            int r3 = r7.mDndTouchX
            r7.mFirstTouchX = r3
            boolean r3 = r7.isDraggable()
            if (r3 == 0) goto Lb9
            android.widget.SemHorizontalListView r3 = r7.mListView
            int r3 = r3.getCount()
            if (r3 <= r1) goto Lb9
            android.widget.SemHorizontalListView r3 = r7.mListView
            int r4 = r7.mDndTouchX
            int r5 = r7.mDndTouchY
            int r3 = r3.pointToPosition(r4, r5)
            r4 = -1
            java.lang.String r5 = "SemDragAndDropHListAnimator"
            if (r3 != r4) goto L87
            java.lang.String r1 = "onInterceptTouchEvent : #1 return false, itemPosition invalid."
            android.util.Log.d(r5, r1)
            return r2
        L87:
            boolean r4 = r7.activatedByLongPress()
            if (r4 == 0) goto L94
            java.lang.String r1 = "onInterceptTouchEvent : #2 return false, activated By longPress."
            android.util.Log.d(r5, r1)
            return r2
        L94:
            if (r3 < 0) goto Laf
            android.widget.SemHorizontalListView r4 = r7.mListView
            int r4 = r4.getCount()
            if (r3 >= r4) goto Laf
            int r4 = r7.mDndTouchX
            int r6 = r7.mDndTouchY
            boolean r4 = r7.checkStartDnd(r4, r6, r3)
            if (r4 == 0) goto Laf
            boolean r4 = r7.initDrag(r3)
            if (r4 == 0) goto Lb8
            return r1
        Laf:
            java.lang.String r1 = "onInterceptTouchEvent : #3 resetDndState"
            android.util.Log.d(r5, r1)
            r7.resetDndState()
        Lb8:
        Lb9:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.animation.SemDragAndDropHorizontalListAnimator.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public AdapterView.OnItemLongClickListener getDragAndDropOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean initDragIfNecessary(int position) {
        if (isDraggable() && activatedByLongPress() && this.mListView.getCount() > 1) {
            if (position >= 0
                    && position < this.mListView.getCount()
                    && checkStartDnd(this.mDndTouchX, this.mDndTouchY, position)) {
                return initDrag(position);
            }
            resetDndState();
            return false;
        }
        return false;
    }

    private boolean initDrag(int itemPosition) {
        this.mDragView =
                this.mListView.getChildAt(itemPosition - this.mListView.getFirstVisiblePosition());
        if (this.mDragView == null) {
            Log.d(TAG, "initDrag : #4 return false, mDragView is null.");
            return false;
        }
        this.mListView.setEnableHoverDrawable(false);
        this.mDndTouchMode = 1;
        this.mFirstDragPos = itemPosition;
        this.mDragPos = this.mFirstDragPos;
        this.mDragView.getHitRect(this.mDragViewRect);
        speakDragStartForAccessibility(itemPosition);
        if (!this.mUserSetDragItemBitmap) {
            if (this.mDragViewBitmap != null) {
                this.mDragViewBitmap.recycle();
            }
            TypedValue typedValue = new TypedValue();
            int strokecolor = 29406;
            int strokeWidth =
                    Math.round(
                            this.mListView.getContext().getResources().getDisplayMetrics().density);
            boolean isValid =
                    this.mDragView
                            .getContext()
                            .getTheme()
                            .resolveAttribute(16843828, typedValue, true);
            if (!isValid) {
                this.mDragView
                        .getContext()
                        .getTheme()
                        .resolveAttribute(R.attr.parentIsDeviceDefaultDark, typedValue, true);
                boolean isDeviceDefaultLight = typedValue.data == 0;
                if (!isDeviceDefaultLight) {
                    strokecolor = 4100607;
                }
            } else {
                strokecolor = typedValue.data;
            }
            this.mDragViewBitmap =
                    SemAnimatorUtils.getBitmapDrawableFromView(this.mDragView).getBitmap();
            Canvas canvas = new Canvas(this.mDragViewBitmap);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokecolor);
            paint.setStrokeWidth(strokeWidth);
            Rect r =
                    new Rect(
                            0,
                            0,
                            this.mDragViewBitmap.getWidth() - 1,
                            this.mDragViewBitmap.getHeight() - 1);
            canvas.drawRect(r, paint);
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

    private void startSelectHighlightingAnimation(View child) {
        Rect hitRect = new Rect();
        child.getHitRect(hitRect);
        this.mListItemSelectionAnimating = true;
        this.mScaleUpAndDownAnimation =
                new SemDragAndDropAnimationCore.ItemSelectHighlightingAnimation(hitRect);
        this.mScaleUpAndDownAnimation.setStartAndDuration(0);
        this.mItemAnimator.putItemAnimation(this.mFirstDragPos, this.mScaleUpAndDownAnimation);
        this.mItemAnimator.start();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0038, code lost:

       return true;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            boolean r0 = r6.isDraggable()
            r1 = 0
            if (r0 == 0) goto L39
            int r0 = r6.mDndTouchMode
            if (r0 != 0) goto Lc
            goto L39
        Lc:
            int r0 = r7.getAction()
            r2 = r0 & 255(0xff, float:3.57E-43)
            r3 = 1
            switch(r2) {
                case 0: goto L37;
                case 1: goto L33;
                case 2: goto L2f;
                case 3: goto L33;
                case 4: goto L16;
                case 5: goto L16;
                case 6: goto L17;
                default: goto L16;
            }
        L16:
            goto L38
        L17:
            r2 = 65280(0xff00, float:9.1477E-41)
            r2 = r2 & r0
            int r2 = r2 >> 8
            int r4 = r7.getPointerId(r2)
            int r5 = r6.mActivePointerId
            if (r4 != r5) goto L38
            if (r2 != 0) goto L28
            r1 = r3
        L28:
            int r5 = r7.getPointerId(r1)
            r6.mActivePointerId = r5
            goto L38
        L2f:
            r6.onTouchMove(r7)
            goto L38
        L33:
            r6.onTouchUpCancel(r7)
            goto L38
        L37:
        L38:
            return r3
        L39:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.animation.SemDragAndDropHorizontalListAnimator.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void onTouchMove(MotionEvent event) {
        int pointerIndex = event.findPointerIndex(this.mActivePointerId);
        if (pointerIndex == -1) {
            pointerIndex = 0;
            this.mActivePointerId = event.getPointerId(0);
        }
        this.mDndTouchX = (int) event.getX(pointerIndex);
        this.mDndTouchY = (int) event.getY(pointerIndex);
        if (this.mDndTouchX
                > (this.mListView.getRight() - this.mListView.getPaddingRight())
                        - this.mListView.getLeft()) {
            this.mDndTouchX =
                    (this.mListView.getRight() - this.mListView.getPaddingRight())
                            - this.mListView.getLeft();
        } else if (this.mDndTouchX < this.mListView.getPaddingLeft()) {
            this.mDndTouchX = this.mListView.getPaddingLeft();
        }
        if (this.mScaleUpAndDownAnimation != null && !this.mScaleUpAndDownAnimation.isFinished()) {
            int distance = Math.abs(this.mDndTouchX - this.mFirstTouchX);
            if (distance > 15.0f) {
                this.mListItemSelectionAnimating = false;
            }
        }
        this.mDndTouchMode = 2;
        boolean needScroll = false;
        int left = this.mListView.getPaddingLeft();
        View temp = this.mListView.getChildAt(0);
        if (temp != null) {
            left += temp.getWidth() / 2;
        }
        int right =
                (this.mListView.getRight() - this.mListView.getPaddingRight())
                        - this.mListView.getLeft();
        View temp2 = this.mListView.getChildAt(this.mListView.getChildCount() - 1);
        if (temp2 != null) {
            right -= temp2.getWidth() / 2;
        }
        if (this.mDndTouchX > right || this.mDndTouchX < left) {
            needScroll = true;
            if (this.mDndAutoScrollMode == 0) {
                this.mListView.postOnAnimationDelayed(this.mAutoScrollRunnable, 150L);
            }
            if (this.mDndTouchX > right) {
                this.mDndAutoScrollMode = 2;
            }
            if (this.mDndTouchX < left) {
                this.mDndAutoScrollMode = 1;
            }
        }
        if (!needScroll) {
            this.mDndAutoScrollMode = 0;
        }
        if (this.mDndAutoScrollMode == 0) {
            this.mListView.removeCallbacks(this.mAutoScrollRunnable);
        }
        reorderIfNeeded();
    }

    @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator
    void reorderIfNeeded() {
        int prevDestPosition = this.mDragPos;
        int adjustedX =
                (this.mDndTouchX - this.mDndTouchOffsetX) + (this.mDragViewRect.width() / 2);
        int dragPos = findDragItemPosition(adjustedX);
        if (dragPos != -1 && this.mDndController.canDrop(this.mFirstDragPos, dragPos)) {
            this.mDragPos = dragPos;
        }
        if (prevDestPosition != this.mDragPos && !this.mListItemSelectionAnimating) {
            recalculateOffset(prevDestPosition, this.mDragPos);
            this.mItemAnimator.start();
        }
        if (prevDestPosition != this.mDragPos || this.mDragViewBitmap != null) {
            this.mListView.invalidate();
        }
    }

    private void onTouchUpCancel(MotionEvent event) {
        int distance;
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
        View dragView = this.mListView.getChildAt(this.mFirstDragPos - firstVisiblePosition);
        View dropView = this.mListView.getChildAt(this.mDragPos - firstVisiblePosition);
        if (dragView == null || dropView == null) {
            int offsetX = this.mDndTouchX;
            int draggedBitmapLeft = offsetX - this.mDndTouchOffsetX;
            boolean dropViewVisible = dropView != null;
            if (dropViewVisible) {
                distance = dropView.getLeft() - draggedBitmapLeft;
            } else if (this.mDragPos < firstVisiblePosition) {
                distance =
                        -((draggedBitmapLeft - this.mListView.getChildAt(0).getLeft())
                                + this.mDragViewRect.width());
            } else if (this.mListView.getChildCount() > 0) {
                distance =
                        this.mListView.getChildAt(this.mListView.getChildCount() - 1).getRight()
                                - draggedBitmapLeft;
            } else {
                Log.e(TAG, "mListView.getChildCount() = " + this.mListView.getChildCount());
                return;
            }
            Log.v(TAG, "dndListener.onTouchUp() dragView == null, distance = " + distance);
            ValueAnimator va = ValueAnimator.ofInt(0, distance);
            va.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // com.samsung.android.animation.SemDragAndDropHorizontalListAnimator.3
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animator) {
                            SemDragAndDropHorizontalListAnimator.this.mDragViewBitmapTranslateX =
                                    ((Integer) animator.getAnimatedValue()).intValue();
                            SemDragAndDropHorizontalListAnimator.this.mListView.invalidate();
                        }
                    });
            va.addListener(
                    new AnimatorListenerAdapter() { // from class:
                                                    // com.samsung.android.animation.SemDragAndDropHorizontalListAnimator.4
                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator anim) {
                            if (SemDragAndDropHorizontalListAnimator.this.mFirstDragPos
                                    != SemDragAndDropHorizontalListAnimator.this.mDragPos) {
                                SemDragAndDropHorizontalListAnimator.this.mDndController.dropDone(
                                        SemDragAndDropHorizontalListAnimator.this.mFirstDragPos,
                                        SemDragAndDropHorizontalListAnimator.this.mDragPos);
                                SemDragAndDropHorizontalListAnimator.this
                                        .speakDragReleaseForAccessibility(
                                                SemDragAndDropHorizontalListAnimator.this.mDragPos);
                            }
                            SemDragAndDropHorizontalListAnimator.this.mItemAnimator.removeAll();
                            SemDragAndDropHorizontalListAnimator.this.resetDndState();
                            if (SemDragAndDropHorizontalListAnimator.this.mDndListener != null) {
                                Log.d(
                                        SemDragAndDropHorizontalListAnimator.TAG,
                                        "dndListener.onDragAndDropEnd() from onAnimationEnd()");
                                SemDragAndDropHorizontalListAnimator.this.mDndListener
                                        .onDragAndDropEnd();
                            }
                        }
                    });
            va.setDuration(210L);
            va.setInterpolator(SINE_IN_OUT_70);
            va.start();
        } else if (this.mListItemSelectionAnimating) {
            resetDndState();
            if (this.mDndListener != null) {
                Log.d(TAG, "dndListener.onDragAndDropEnd() mListItemSelectionAnimating is true");
                this.mDndListener.onDragAndDropEnd();
            }
        } else {
            int offsetX2 = dropView.getLeft() - dragView.getLeft();
            int deltaX = dropView.getLeft() - (this.mDndTouchX - this.mDndTouchOffsetX);
            SemDragAndDropAnimationCore.TranslateItemAnimation t =
                    new SemDragAndDropAnimationCore.TranslateItemAnimation();
            t.translate(offsetX2, deltaX, 0, 0);
            t.setStartAndDuration(0.7f);
            this.mItemAnimator.putItemAnimation(this.mFirstDragPos, t);
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

    private int findDragItemPosition(int x) {
        int childCount = this.mListView.getChildCount();
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View v = this.mListView.getChildAt(i);
                v.getHitRect(this.mTempRect);
                if (this.mTempRect.contains(x, this.mTempRect.centerY())) {
                    return i + firstVisiblePosition;
                }
            }
            return -1;
        }
        return -1;
    }

    private int findMovedItemPosition(int x) {
        int childCount = this.mListView.getChildCount();
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                if (i != this.mFirstDragPos - firstVisiblePosition) {
                    View v = this.mListView.getChildAt(i);
                    v.getHitRect(this.mTempRect);
                    SemDragAndDropAnimationCore.ItemAnimation ia =
                            this.mItemAnimator.getItemAnimation(i + firstVisiblePosition);
                    if (ia instanceof SemDragAndDropAnimationCore.TranslateItemAnimation) {
                        int xPosAdjust =
                                ((SemDragAndDropAnimationCore.TranslateItemAnimation) ia)
                                        .getDestOffsetX();
                        if (this.mTempRect.contains(x - xPosAdjust, this.mTempRect.centerY())) {
                            return i + firstVisiblePosition;
                        }
                    } else {
                        continue;
                    }
                }
            }
            return -1;
        }
        return -1;
    }

    private void addReturningTranslation(int position) {
        SemDragAndDropAnimationCore.ItemAnimation ia =
                this.mItemAnimator.getItemAnimation(position);
        if (!(ia instanceof SemDragAndDropAnimationCore.TranslateItemAnimation)) {
            return;
        }
        SemDragAndDropAnimationCore.TranslateItemAnimation t =
                (SemDragAndDropAnimationCore.TranslateItemAnimation) ia;
        int xCurrentOffset = (int) t.getCurrentTranslateX();
        t.translate(0, -xCurrentOffset, 0, 0);
        t.setStartAndDuration(t.getProgress());
    }

    private void addNewTranslation(int position, int totalHeight) {
        SemDragAndDropAnimationCore.TranslateItemAnimation t;
        SemDragAndDropAnimationCore.ItemAnimation a = this.mItemAnimator.getItemAnimation(position);
        if (a instanceof SemDragAndDropAnimationCore.TranslateItemAnimation) {
            t = (SemDragAndDropAnimationCore.TranslateItemAnimation) a;
        } else {
            t = new SemDragAndDropAnimationCore.TranslateItemAnimation();
        }
        int xCurrentOffset = 0;
        if (!t.isFinished()) {
            xCurrentOffset = (int) t.getCurrentTranslateX();
        }
        t.translate(totalHeight, totalHeight - xCurrentOffset, 0, 0);
        if (!t.isFinished()) {
            t.setStartAndDuration(t.getProgress());
        } else {
            t.setStartAndDuration(0);
        }
        this.mItemAnimator.putItemAnimation(position, t);
    }

    private void recalculateOffset(int prevPos, int newPos) {
        View fixedView;
        int dividerWidth = this.mListView.getDividerHeight();
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        int childWidth = this.mDragViewRect.width() + dividerWidth;
        if (newPos > prevPos) {
            for (int i = prevPos + 1; i <= newPos; i++) {
                if (i > this.mFirstDragPos) {
                    if (this.mDndController.canDrop(this.mFirstDragPos, i)) {
                        int totalWidth = childWidth;
                        int currentIdx = i;
                        while (true) {
                            currentIdx--;
                            if (this.mNonMovableItems.indexOfKey(currentIdx) < 0) {
                                break;
                            } else {
                                totalWidth += this.mNonMovableItems.get(currentIdx);
                            }
                        }
                        if (this.mListView.isLayoutRtl()) {
                            addNewTranslation(i, totalWidth);
                        } else {
                            addNewTranslation(i, -totalWidth);
                        }
                    } else {
                        View fixedView2 = this.mListView.getChildAt(i - firstVisiblePosition);
                        if (fixedView2 != null) {
                            this.mNonMovableItems.put(i, fixedView2.getWidth() + dividerWidth);
                        }
                    }
                } else {
                    View child = this.mListView.getChildAt(i - firstVisiblePosition);
                    if (child == null) {
                        Log.e(TAG, "recalculateOffset('dragging down') no such item, i = " + i);
                    } else {
                        int centerX = SemAnimatorUtils.getViewCenterX(child);
                        int movedPos = findMovedItemPosition(centerX);
                        addReturningTranslation(movedPos);
                    }
                }
            }
            return;
        }
        for (int i2 = prevPos - 1; i2 >= newPos; i2--) {
            if (i2 < this.mFirstDragPos) {
                if (this.mDndController.canDrop(this.mFirstDragPos, i2)) {
                    int totalWidth2 = childWidth;
                    int currentIdx2 = i2;
                    while (true) {
                        currentIdx2++;
                        if (this.mNonMovableItems.indexOfKey(currentIdx2) < 0) {
                            break;
                        } else {
                            totalWidth2 += this.mNonMovableItems.get(currentIdx2);
                        }
                    }
                    if (this.mListView.isLayoutRtl()) {
                        addNewTranslation(i2, -totalWidth2);
                    } else {
                        addNewTranslation(i2, totalWidth2);
                    }
                } else if (this.mNonMovableItems.get(i2, -1) == -1
                        && (fixedView = this.mListView.getChildAt(i2 - firstVisiblePosition))
                                != null) {
                    this.mNonMovableItems.put(i2, fixedView.getWidth() + dividerWidth);
                }
            } else {
                View child2 = this.mListView.getChildAt(i2 - firstVisiblePosition);
                if (child2 == null) {
                    Log.e(TAG, "recalculateOffset('dragging up') no such item, i = " + i2);
                } else {
                    int centerX2 = SemAnimatorUtils.getViewCenterX(child2);
                    int movedPos2 = findMovedItemPosition(centerX2);
                    addReturningTranslation(movedPos2);
                }
            }
        }
    }

    private void getDragGrabHandleHitRect(Rect childRect, Rect outGrabHandleRect) {
        if (this.mDragGrabHandleDrawable != null) {
            int drawableWidth = this.mDragGrabHandleDrawable.getIntrinsicWidth();
            int drawableHeight = this.mDragGrabHandleDrawable.getIntrinsicHeight();
            childRect.left += this.mDragGrabHandlePadding.left;
            childRect.top += this.mDragGrabHandlePadding.top;
            childRect.right += this.mDragGrabHandlePadding.right;
            childRect.bottom += this.mDragGrabHandlePadding.bottom;
            childRect.top -= 10;
            childRect.bottom -= 10;
            Gravity.apply(
                    this.mDragGrabHandlePosGravity,
                    drawableWidth,
                    drawableHeight,
                    childRect,
                    outGrabHandleRect);
        }
    }

    private void drawDragHandle(
            Canvas canvas, Rect childRect, boolean isDraggedItem, boolean isAllowDragItem) {
        if (this.mDragGrabHandleDrawable != null && isAllowDragItem) {
            getDragGrabHandleHitRect(childRect, this.mTempRect);
            this.mDragGrabHandleDrawable.setBounds(this.mTempRect);
            this.mDragGrabHandleDrawable.setState(
                    isDraggedItem ? PRESSED_STATE_SET : EMPTY_STATE_SET);
            this.mDragGrabHandleDrawable.setAlpha(this.mDragHandleAlpha);
            this.mDragGrabHandleDrawable.draw(canvas);
        }
    }

    public boolean preDrawChild(Canvas canvas, View child, long drawingTime) {
        int index = this.mListView.indexOfChild(child);
        int pos = this.mListView.getFirstVisiblePosition() + index;
        if (isDraggable()
                && pos == this.mFirstDragPos
                && !this.mDropDonePending
                && !this.mListItemSelectionAnimating) {
            return false;
        }
        SemDragAndDropAnimationCore.ItemAnimation ia = this.mItemAnimator.getItemAnimation(pos);
        this.mCanvasSaveCount = 0;
        if (ia != null) {
            ia.getTransformation(this.mTempTrans);
            this.mCanvasSaveCount = canvas.save();
            canvas.concat(this.mTempTrans.getMatrix());
            return true;
        }
        return true;
    }

    public void postDrawChild(Canvas canvas, View child, long drawingTime) {
        drawDragHandlerIfNeeded(canvas, child, drawingTime);
        if (this.mCanvasSaveCount > 0) {
            canvas.restoreToCount(this.mCanvasSaveCount);
        }
    }

    private void drawDragHandlerIfNeeded(Canvas canvas, View child, long drawingTime) {
        if (isDraggable()) {
            int index = this.mListView.indexOfChild(child);
            int pos = this.mListView.getFirstVisiblePosition() + index;
            if (this.mListView.getAdapter().isEnabled(pos) && !isHeaderOrFooterViewPos(pos)) {
                child.getHitRect(this.mTempRect);
                drawDragHandle(canvas, this.mTempRect, false, this.mDndController.canDrag(pos));
            }
        }
    }

    private boolean isHeaderOrFooterViewPos(int pos) {
        if (pos < this.mListView.getHeaderViewsCount()
                || pos >= this.mListView.getCount() - this.mListView.getFooterViewsCount()) {
            return true;
        }
        return false;
    }

    public void dispatchDraw(Canvas canvas) {
        if (isDraggable() && this.mDragViewBitmap != null) {
            int draggedItemY = this.mListView.getPaddingTop();
            int draggedItemX = this.mDndTouchX - this.mDndTouchOffsetX;
            if (this.mListItemSelectionAnimating || this.mDragViewBitmap.isRecycled()) {
                return;
            }
            canvas.drawBitmap(
                    this.mDragViewBitmap,
                    this.mDragViewBitmapTranslateX + draggedItemX,
                    draggedItemY,
                    this.mDragViewBitmapPaint);
            this.mTempRect.top = -this.mListView.getPaddingTop();
            this.mTempRect.left = this.mDragViewBitmapTranslateX + draggedItemX;
            this.mTempRect.right = this.mTempRect.left + this.mDragViewRect.width();
            this.mTempRect.bottom = this.mTempRect.top + this.mListView.getHeight();
            drawDragHandle(canvas, this.mTempRect, true, true);
        }
    }

    private class HeaderFooterDndController
            implements SemAbsDragAndDropAnimator.DragAndDropController {
        private final SemAbsDragAndDropAnimator.DragAndDropController mWrappedController;

        HeaderFooterDndController(
                SemAbsDragAndDropAnimator.DragAndDropController wrappedController) {
            this.mWrappedController = wrappedController;
        }

        @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.DragAndDropController
        public boolean canDrag(int startPos) {
            if (this.mWrappedController == null
                    || startPos
                            < SemDragAndDropHorizontalListAnimator.this.mListView
                                    .getHeaderViewsCount()
                    || startPos
                            >= SemDragAndDropHorizontalListAnimator.this.mListView.getCount()
                                    - SemDragAndDropHorizontalListAnimator.this.mListView
                                            .getFooterViewsCount()) {
                return false;
            }
            return this.mWrappedController.canDrag(
                    startPos
                            - SemDragAndDropHorizontalListAnimator.this.mListView
                                    .getHeaderViewsCount());
        }

        @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.DragAndDropController
        public boolean canDrop(int startPos, int destPos) {
            if (this.mWrappedController == null
                    || destPos
                            < SemDragAndDropHorizontalListAnimator.this.mListView
                                    .getHeaderViewsCount()
                    || destPos
                            >= SemDragAndDropHorizontalListAnimator.this.mListView.getCount()
                                    - SemDragAndDropHorizontalListAnimator.this.mListView
                                            .getFooterViewsCount()) {
                return false;
            }
            return this.mWrappedController.canDrop(
                    startPos
                            - SemDragAndDropHorizontalListAnimator.this.mListView
                                    .getHeaderViewsCount(),
                    destPos
                            - SemDragAndDropHorizontalListAnimator.this.mListView
                                    .getHeaderViewsCount());
        }

        @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.DragAndDropController
        public void dropDone(int startPos, int destPos) {
            if (this.mWrappedController != null) {
                if (startPos
                        < SemDragAndDropHorizontalListAnimator.this.mListView
                                .getHeaderViewsCount()) {
                    startPos =
                            SemDragAndDropHorizontalListAnimator.this.mListView
                                    .getHeaderViewsCount();
                } else if (startPos
                        > SemDragAndDropHorizontalListAnimator.this.mListView.getCount()
                                - SemDragAndDropHorizontalListAnimator.this.mListView
                                        .getFooterViewsCount()) {
                    startPos =
                            (SemDragAndDropHorizontalListAnimator.this.mListView.getCount()
                                            - SemDragAndDropHorizontalListAnimator.this.mListView
                                                    .getFooterViewsCount())
                                    - 1;
                }
                if (destPos
                        < SemDragAndDropHorizontalListAnimator.this.mListView
                                .getHeaderViewsCount()) {
                    destPos =
                            SemDragAndDropHorizontalListAnimator.this.mListView
                                    .getHeaderViewsCount();
                } else if (destPos
                        >= SemDragAndDropHorizontalListAnimator.this.mListView.getCount()
                                - SemDragAndDropHorizontalListAnimator.this.mListView
                                        .getFooterViewsCount()) {
                    destPos =
                            (SemDragAndDropHorizontalListAnimator.this.mListView.getCount()
                                            - SemDragAndDropHorizontalListAnimator.this.mListView
                                                    .getFooterViewsCount())
                                    - 1;
                }
                this.mWrappedController.dropDone(
                        startPos
                                - SemDragAndDropHorizontalListAnimator.this.mListView
                                        .getHeaderViewsCount(),
                        destPos
                                - SemDragAndDropHorizontalListAnimator.this.mListView
                                        .getHeaderViewsCount());
            }
        }
    }
}
