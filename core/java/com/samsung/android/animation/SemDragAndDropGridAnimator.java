package com.samsung.android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.HashSet;

@Deprecated
/* loaded from: classes5.dex */
public class SemDragAndDropGridAnimator extends SemAbsDragAndDropAnimator {
    private static final String TAG = "SemDragAndDropGridAnimator";
    private GridView mGridView;
    private SemDragAndDropAnimationCore.ItemAnimationListener mItemAnimationListener;
    private int mItemHeight;
    private int mItemWidth;
    HashSet<Integer> mNonMovableItems;
    private AdapterView.OnItemLongClickListener mOnItemLongClickListener;

    public SemDragAndDropGridAnimator(Context context, GridView gridview) {
        super(context, gridview);
        this.mNonMovableItems = new HashSet<>();
        this.mGridView = gridview;
        this.mGridView.setDndGridAnimator(this);
        this.mItemWidth = Integer.MIN_VALUE;
        this.mItemHeight = Integer.MIN_VALUE;
        initListeners();
        this.mDndAnimationCore.setAnimationListener(this.mItemAnimationListener);
        this.mGridView.setOnItemLongClickListener(this.mOnItemLongClickListener);
        this.mGridView.setSelector(17170445);
    }

    private void initListeners() {
        this.mItemAnimationListener =
                new SemDragAndDropAnimationCore.ItemAnimationListener() { // from class:
                    // com.samsung.android.animation.SemDragAndDropGridAnimator.1
                    @Override // com.samsung.android.animation.SemDragAndDropAnimationCore.ItemAnimationListener
                    public void onItemAnimatorEnd() {
                        if (SemDragAndDropGridAnimator.this.mListItemSelectionAnimating) {
                            SemDragAndDropGridAnimator.this.mListItemSelectionAnimating = false;
                            SemDragAndDropGridAnimator.this.updateDragViewBitmap();
                            return;
                        }
                        if (SemDragAndDropGridAnimator.this.mDropDonePending) {
                            SemDragAndDropGridAnimator.this.mDropDonePending = false;
                            if (SemDragAndDropGridAnimator.this.mDragPos
                                    != SemDragAndDropGridAnimator.this.mFirstDragPos) {
                                SemDragAndDropGridAnimator.this.mDndController.dropDone(
                                        SemDragAndDropGridAnimator.this.mFirstDragPos,
                                        SemDragAndDropGridAnimator.this.mDragPos);
                                SemDragAndDropGridAnimator.this.speakDragReleaseForAccessibility(
                                        SemDragAndDropGridAnimator.this.mDragPos);
                            }
                            SemDragAndDropGridAnimator.this.mItemAnimator.removeAll();
                            SemDragAndDropGridAnimator.this.resetDndPositionValues();
                            if (SemDragAndDropGridAnimator.this.mDndListener != null) {
                                Log.d(
                                        SemDragAndDropGridAnimator.TAG,
                                        "dndListener.onDragAndDropEnd() from"
                                            + " onAllAnimationsFinished()");
                                SemDragAndDropGridAnimator.this.mDndListener.onDragAndDropEnd();
                            }
                            SemDragAndDropGridAnimator.this.mGridView.setEnabled(true);
                        }
                    }
                };
        this.mOnItemLongClickListener =
                new AdapterView
                        .OnItemLongClickListener() { // from class:
                                                     // com.samsung.android.animation.SemDragAndDropGridAnimator.2
                    @Override // android.widget.AdapterView.OnItemLongClickListener
                    public boolean onItemLongClick(
                            AdapterView<?> parent, View view, int position, long id) {
                        return SemDragAndDropGridAnimator.this.initDragIfNecessary(position);
                    }
                };
    }

    public void setDragAndDropController(
            SemAbsDragAndDropAnimator.DragAndDropController dndController) {
        this.mDndController = dndController;
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
        View v = this.mGridView.getChildAt(itemPosition - this.mGridView.getFirstVisiblePosition());
        v.getHitRect(childRect);
        getDragGrabHandleHitRect(childRect, this.mTempRect);
        return this.mTempRect.contains(x, y);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0093 A[ADDED_TO_REGION, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            int r0 = r7.getAction()
            r1 = 0
            r2 = 1
            switch(r0) {
                case 0: goto L32;
                case 1: goto L24;
                case 2: goto Lb;
                case 3: goto L24;
                default: goto L9;
            }
        L9:
            goto L93
        Lb:
            boolean r3 = r6.isDraggable()
            if (r3 == 0) goto L93
            int r3 = r6.mDndTouchMode
            if (r3 != r2) goto L93
            android.widget.GridView r3 = r6.mGridView
            int r3 = r3.getCount()
            if (r3 <= r2) goto L93
            boolean r3 = r6.activatedByLongPress()
            if (r3 == 0) goto L93
            return r2
        L24:
            boolean r2 = r6.isDraggable()
            if (r2 == 0) goto L93
            int r2 = r6.mDndTouchMode
            if (r2 == 0) goto L93
            r6.onTouchUpCancel(r7)
            goto L93
        L32:
            android.widget.GridView r3 = r6.mGridView
            boolean r3 = r3.isEnabled()
            if (r3 != 0) goto L3b
            return r1
        L3b:
            int r3 = r6.mDndTouchX
            r6.mFirstTouchX = r3
            int r3 = r6.mDndTouchY
            r6.mFirstTouchY = r3
            float r3 = r7.getX()
            int r3 = (int) r3
            r6.mDndTouchX = r3
            float r3 = r7.getY()
            int r3 = (int) r3
            r6.mDndTouchY = r3
            boolean r3 = r6.isDraggable()
            if (r3 == 0) goto L93
            android.widget.GridView r3 = r6.mGridView
            int r3 = r3.getCount()
            if (r3 <= r2) goto L93
            android.widget.GridView r3 = r6.mGridView
            int r4 = r6.mDndTouchX
            int r5 = r6.mDndTouchY
            int r3 = r3.pointToPosition(r4, r5)
            r4 = -1
            if (r3 != r4) goto L6d
            return r1
        L6d:
            boolean r4 = r6.activatedByLongPress()
            if (r4 == 0) goto L74
            return r1
        L74:
            if (r3 < 0) goto L8f
            android.widget.GridView r4 = r6.mGridView
            int r4 = r4.getCount()
            if (r3 >= r4) goto L8f
            int r4 = r6.mDndTouchX
            int r5 = r6.mDndTouchY
            boolean r4 = r6.checkStartDnd(r4, r5, r3)
            if (r4 == 0) goto L8f
            boolean r4 = r6.initDrag(r3)
            if (r4 == 0) goto L92
            return r2
        L8f:
            r6.resetDndState()
        L92:
        L93:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.animation.SemDragAndDropGridAnimator.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return this.mOnItemLongClickListener.onItemLongClick(parent, view, position, id);
    }

    public AdapterView.OnItemLongClickListener getDragAndDropOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean initDragIfNecessary(int position) {
        if (isDraggable() && activatedByLongPress() && this.mGridView.getCount() > 1) {
            if (position >= 0
                    && position < this.mGridView.getCount()
                    && checkStartDnd(this.mDndTouchX, this.mDndTouchY, position)) {
                return initDrag(position);
            }
            resetDndState();
            return false;
        }
        return false;
    }

    private boolean initDrag(int itemPosition) {
        findMovingArrage();
        this.mDragView =
                this.mGridView.getChildAt(itemPosition - this.mGridView.getFirstVisiblePosition());
        if (this.mDragView == null) {
            return false;
        }
        this.mGridView.setEnableHoverDrawable(false);
        this.mDndTouchMode = 2;
        this.mFirstDragPos = itemPosition;
        this.mDragPos = this.mFirstDragPos;
        this.mDragView.setPressed(false);
        this.mDragView.getHitRect(this.mDragViewRect);
        speakDragStartForAccessibility(itemPosition);
        if (this.mDragViewBitmap != null) {
            this.mDragViewBitmap.recycle();
        }
        updateDragViewBitmap();
        setDragViewAlpha(this.mDragViewBitmapAlpha);
        if (this.mDragViewBitmap != null) {
            this.mDndTouchOffsetX = this.mDndTouchX - this.mDragViewRect.left;
            this.mDndTouchOffsetY = this.mDndTouchY - this.mDragViewRect.top;
        }
        startSelectHighlightingAnimation(this.mDragView);
        if (this.mDndListener != null) {
            Log.d(TAG, "dndListener.OnDragAndDropStart()");
            this.mDndListener.onDragAndDropStart();
        }
        this.mGridView.invalidate();
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

    public boolean onTouchEvent(MotionEvent event) {
        if (!isDraggable() || this.mDndTouchMode == 0) {
            return false;
        }
        int action = event.getAction();
        switch (action) {
            case 1:
            case 3:
                onTouchUpCancel(event);
                return true;
            case 2:
                onTouchMove(event);
                return true;
            default:
                return true;
        }
    }

    private void onTouchUpCancel(MotionEvent event) {
        int numOfColumns;
        int index;
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
        int firstVisiblePosition = this.mGridView.getFirstVisiblePosition();
        int childCount = this.mGridView.getChildCount();
        View dragView = this.mGridView.getChildAt(this.mFirstDragPos - firstVisiblePosition);
        View dropView = this.mGridView.getChildAt(this.mDragPos - firstVisiblePosition);
        if (dragView == null || dropView == null) {
            int deltaX = this.mDndTouchX;
            int draggedBitmapLeft = deltaX - this.mDndTouchOffsetX;
            int draggedBitmapTop = this.mDndTouchY - this.mDndTouchOffsetY;
            boolean dropViewVisible = dropView != null;
            if (dropViewVisible) {
                numOfColumns = dropView.getLeft() - draggedBitmapLeft;
                index = dropView.getTop() - draggedBitmapTop;
            } else {
                int numOfColumns2 = this.mGridView.getNumColumns();
                if (childCount < numOfColumns2) {
                    Log.e(
                            TAG,
                            "Child cound ("
                                    + this.mGridView.getChildCount()
                                    + ") is smaller than column count ("
                                    + numOfColumns2
                                    + NavigationBarInflaterView.KEY_CODE_END);
                    resetDndState();
                    return;
                } else if (this.mDragPos >= firstVisiblePosition) {
                    int index2 =
                            (this.mGridView.getChildCount() + (this.mDragPos % numOfColumns2))
                                    - numOfColumns2;
                    int distanceX = this.mGridView.getChildAt(index2).getLeft() - draggedBitmapLeft;
                    numOfColumns = distanceX;
                    index = this.mGridView.getHeight() - draggedBitmapTop;
                } else {
                    int index3 = this.mDragPos % numOfColumns2;
                    int distanceX2 =
                            this.mGridView.getChildAt(index3).getLeft() - draggedBitmapLeft;
                    index =
                            ((-this.mGridView.getPaddingTop()) - draggedBitmapTop)
                                    - this.mDragViewRect.height();
                    numOfColumns = distanceX2;
                }
            }
            final int distanceX3 = index;
            final int distX = numOfColumns;
            Log.v(
                    TAG,
                    "dndListener.onTouchUp() dragView == null, distanceX="
                            + numOfColumns
                            + ", distanceY="
                            + index);
            ValueAnimator va = ValueAnimator.ofFloat(0.0f, 1.0f);
            va.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // com.samsung.android.animation.SemDragAndDropGridAnimator.3
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animator) {
                            SemDragAndDropGridAnimator.this.mDragViewBitmapTranslateX =
                                    (int) (distX * animator.getAnimatedFraction());
                            SemDragAndDropGridAnimator.this.mDragViewBitmapTranslateY =
                                    (int) (distanceX3 * animator.getAnimatedFraction());
                            SemDragAndDropGridAnimator.this.mGridView.invalidate();
                        }
                    });
            va.addListener(
                    new AnimatorListenerAdapter() { // from class:
                                                    // com.samsung.android.animation.SemDragAndDropGridAnimator.4
                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator anim) {
                            if (SemDragAndDropGridAnimator.this.mFirstDragPos
                                    != SemDragAndDropGridAnimator.this.mDragPos) {
                                SemDragAndDropGridAnimator.this.mDndController.dropDone(
                                        SemDragAndDropGridAnimator.this.mFirstDragPos,
                                        SemDragAndDropGridAnimator.this.mDragPos);
                                SemDragAndDropGridAnimator.this.speakDragReleaseForAccessibility(
                                        SemDragAndDropGridAnimator.this.mDragPos);
                            }
                            SemDragAndDropGridAnimator.this.mItemAnimator.removeAll();
                            SemDragAndDropGridAnimator.this.resetDndState();
                            if (SemDragAndDropGridAnimator.this.mDndListener != null) {
                                Log.d(
                                        SemDragAndDropGridAnimator.TAG,
                                        "dndListener.onDragAndDropEnd() from AnimationEnd");
                                SemDragAndDropGridAnimator.this.mDndListener.onDragAndDropEnd();
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
            int destOffsetX = dropView.getLeft() - dragView.getLeft();
            int destOffsetY = dropView.getTop() - dragView.getTop();
            int deltaX2 = dropView.getLeft() - (this.mDndTouchX - this.mDndTouchOffsetX);
            int deltaY = dropView.getTop() - (this.mDndTouchY - this.mDndTouchOffsetY);
            SemDragAndDropAnimationCore.TranslateItemAnimation t =
                    new SemDragAndDropAnimationCore.TranslateItemAnimation();
            t.translate(destOffsetX, deltaX2, destOffsetY, deltaY);
            t.setStartAndDuration(0.7f);
            this.mItemAnimator.putItemAnimation(this.mFirstDragPos, t);
            this.mItemAnimator.start();
            this.mRetainFirstDragViewPos = this.mFirstDragPos - firstVisiblePosition;
            this.mGridView.setEnabled(false);
            this.mDropDonePending = true;
            resetDndTouchValuesAndBitmap();
        }
        this.mGridView.removeCallbacks(this.mAutoScrollRunnable);
        this.mGridView.invalidate();
    }

    @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator
    void resetDndTouchValuesAndBitmap() {
        super.resetDndTouchValuesAndBitmap();
        this.mItemWidth = Integer.MIN_VALUE;
        this.mItemHeight = Integer.MIN_VALUE;
        this.mNonMovableItems.clear();
    }

    @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator
    void resetDndPositionValues() {
        super.resetDndPositionValues();
        this.mGridView.setEnableHoverDrawable(true);
    }

    private void onTouchMove(MotionEvent event) {
        this.mDndTouchX = (int) event.getX();
        this.mDndTouchY = (int) event.getY();
        if (this.mListItemSelectionAnimating
                && this.mScaleUpAndDownAnimation != null
                && !this.mScaleUpAndDownAnimation.isFinished()) {
            int distanceX = Math.abs(this.mDndTouchX - this.mFirstTouchX);
            int distanceY = Math.abs(this.mDndTouchY - this.mFirstTouchY);
            if (Math.max(distanceX, distanceY) > 15.0f) {
                this.mListItemSelectionAnimating = false;
                updateDragViewBitmap();
            }
        }
        int top = this.mGridView.getPaddingTop();
        View temp = this.mGridView.getChildAt(0);
        if (temp != null) {
            top += temp.getHeight() / 2;
        }
        int bottom =
                (this.mGridView.getBottom() - this.mGridView.getPaddingBottom())
                        - this.mGridView.getTop();
        View temp2 = this.mGridView.getChildAt(this.mGridView.getChildCount() - 1);
        if (temp2 != null) {
            bottom -= temp2.getHeight() / 2;
        }
        boolean needScroll = false;
        if (this.mDndTouchY > bottom || this.mDndTouchY < top) {
            needScroll = true;
            if (this.mDndAutoScrollMode == 0) {
                this.mGridView.postOnAnimationDelayed(this.mAutoScrollRunnable, 150L);
            }
            if (this.mDndTouchY > bottom) {
                this.mDndAutoScrollMode = 2;
            }
            if (this.mDndTouchY < top) {
                this.mDndAutoScrollMode = 1;
            }
        }
        if (!needScroll) {
            this.mDndAutoScrollMode = 0;
        }
        if (this.mDndAutoScrollMode == 0) {
            this.mGridView.removeCallbacks(this.mAutoScrollRunnable);
        }
        reorderIfNeeded();
    }

    @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator
    void reorderIfNeeded() {
        int prevDestPosition = this.mDragPos;
        int adjustedX =
                (this.mDndTouchX - this.mDndTouchOffsetX) + (this.mDragViewRect.width() / 2);
        int adjustedY =
                (this.mDndTouchY - this.mDndTouchOffsetY) + (this.mDragViewRect.height() / 2);
        int dragPos = this.mGridView.pointToPosition(adjustedX, adjustedY);
        if (dragPos != -1) {
            if (this.mDndController.canDrop(this.mFirstDragPos, dragPos)) {
                this.mDragPos = dragPos;
            } else if (this.mDragPos > dragPos) {
                int i = dragPos + 1;
                while (true) {
                    if (i >= this.mDragPos) {
                        break;
                    }
                    if (!this.mDndController.canDrop(this.mFirstDragPos, i)) {
                        i++;
                    } else {
                        this.mDragPos = i;
                        break;
                    }
                }
            } else {
                int i2 = dragPos - 1;
                while (true) {
                    if (i2 <= this.mDragPos) {
                        break;
                    }
                    if (!this.mDndController.canDrop(this.mFirstDragPos, i2)) {
                        i2--;
                    } else {
                        this.mDragPos = i2;
                        break;
                    }
                }
            }
        }
        int i3 = this.mDragPos;
        if (prevDestPosition != i3) {
            this.mListItemSelectionAnimating = false;
            recalculateOffset(prevDestPosition, this.mDragPos);
            this.mItemAnimator.start();
        }
        if (prevDestPosition != this.mDragPos || this.mDragViewBitmap != null) {
            this.mGridView.invalidate();
        }
    }

    private int findMovedItemIndex(View child) {
        int x = SemAnimatorUtils.getViewCenterX(child);
        int y = SemAnimatorUtils.getViewCenterY(child);
        int childCount = this.mGridView.getChildCount();
        int firstVisiblePosition = this.mGridView.getFirstVisiblePosition();
        if (childCount > 0) {
            for (int i = childCount - 1; i >= 0; i--) {
                View v = this.mGridView.getChildAt(i);
                v.getHitRect(this.mTempRect);
                int xPosAdjust = 0;
                int yPosAdjust = 0;
                SemDragAndDropAnimationCore.ItemAnimation ia =
                        this.mItemAnimator.getItemAnimation(i + firstVisiblePosition);
                if (ia instanceof SemDragAndDropAnimationCore.TranslateItemAnimation) {
                    xPosAdjust =
                            ((SemDragAndDropAnimationCore.TranslateItemAnimation) ia)
                                    .getDestOffsetX();
                    yPosAdjust =
                            ((SemDragAndDropAnimationCore.TranslateItemAnimation) ia)
                                    .getDestOffsetY();
                }
                if (i != this.mFirstDragPos - firstVisiblePosition
                        && this.mTempRect.contains(x - xPosAdjust, y - yPosAdjust)) {
                    return i + firstVisiblePosition;
                }
            }
            return -1;
        }
        return -1;
    }

    private void findMovingArrage() {
        if (this.mGridView.getCount() >= 2) {
            View v1 = this.mGridView.getChildAt(0);
            View v2 = this.mGridView.getChildAt(1);
            if (v1 == null || v2 == null) {
                return;
            }
            Rect r1 = new Rect();
            Rect r2 = new Rect();
            v1.getHitRect(r1);
            v2.getHitRect(r2);
            this.mItemWidth = Math.abs(r2.left - r1.left);
        } else {
            this.mItemWidth = 0;
        }
        if (this.mGridView.getCount() > this.mGridView.getNumColumns()) {
            View v12 = this.mGridView.getChildAt(0);
            View v22 = this.mGridView.getChildAt(this.mGridView.getNumColumns());
            if (v12 == null || v22 == null) {
                return;
            }
            Rect r12 = new Rect();
            Rect r22 = new Rect();
            v12.getHitRect(r12);
            v22.getHitRect(r22);
            this.mItemHeight = Math.abs(r22.top - r12.top);
            return;
        }
        this.mItemHeight = 0;
    }

    private void recalculateOffset(int prevDestPosition, int newDestPosition) {
        int deltaX;
        int deltaX2;
        int firstVisiblePosition = this.mGridView.getFirstVisiblePosition();
        int numColumnes = this.mGridView.getNumColumns();
        boolean isLayoutRtl = this.mGridView.isLayoutRtl();
        if (newDestPosition > prevDestPosition) {
            for (int i = prevDestPosition + 1; i <= newDestPosition; i++) {
                if (i > this.mFirstDragPos) {
                    if (this.mDndController.canDrop(this.mFirstDragPos, i)) {
                        int skipCount = 0;
                        for (int currentIdx = i - 1;
                                this.mNonMovableItems.contains(Integer.valueOf(currentIdx));
                                currentIdx--) {
                            skipCount++;
                        }
                        int startIndex = i;
                        int endIndex = (i - 1) - skipCount;
                        int rowDiff = (endIndex / numColumnes) - (startIndex / numColumnes);
                        int columnDiff = (endIndex % numColumnes) - (startIndex % numColumnes);
                        if (isLayoutRtl) {
                            deltaX2 = this.mItemWidth * columnDiff * (-1);
                        } else {
                            int deltaX3 = this.mItemWidth;
                            deltaX2 = deltaX3 * columnDiff;
                        }
                        int deltaX4 = this.mItemHeight;
                        int deltaY = deltaX4 * rowDiff;
                        addNewTranslation(i, deltaX2, deltaY);
                    } else {
                        this.mNonMovableItems.add(Integer.valueOf(i));
                    }
                } else {
                    View child = this.mGridView.getChildAt(i - firstVisiblePosition);
                    if (child == null) {
                        Log.e(TAG, "recalculateOffset('dragging down') no such item, i=" + i);
                    } else {
                        int movedPos = findMovedItemIndex(child);
                        addReturningTranslation(movedPos);
                    }
                }
            }
            return;
        }
        for (int i2 = prevDestPosition - 1; i2 >= newDestPosition; i2--) {
            if (i2 < this.mFirstDragPos) {
                if (this.mDndController.canDrop(this.mFirstDragPos, i2)) {
                    int skipCount2 = 0;
                    for (int currentIdx2 = i2 + 1;
                            this.mNonMovableItems.contains(Integer.valueOf(currentIdx2));
                            currentIdx2++) {
                        skipCount2++;
                    }
                    int startIndex2 = i2;
                    int endIndex2 = i2 + 1 + skipCount2;
                    int rowDiff2 = (endIndex2 / numColumnes) - (startIndex2 / numColumnes);
                    int columnDiff2 = (endIndex2 % numColumnes) - (startIndex2 % numColumnes);
                    if (isLayoutRtl) {
                        deltaX = this.mItemWidth * columnDiff2 * (-1);
                    } else {
                        int deltaX5 = this.mItemWidth;
                        deltaX = deltaX5 * columnDiff2;
                    }
                    int deltaX6 = this.mItemHeight;
                    int deltaY2 = deltaX6 * rowDiff2;
                    addNewTranslation(i2, deltaX, deltaY2);
                } else {
                    this.mNonMovableItems.add(Integer.valueOf(i2));
                }
            } else {
                View child2 = this.mGridView.getChildAt(i2 - firstVisiblePosition);
                if (child2 == null) {
                    Log.e(TAG, "recalculateOffset('dragging up') no such item, i=" + i2);
                } else {
                    int movedPos2 = findMovedItemIndex(child2);
                    addReturningTranslation(movedPos2);
                }
            }
        }
    }

    private void addNewTranslation(int position, int deltaX, int deltaY) {
        SemDragAndDropAnimationCore.TranslateItemAnimation t;
        SemDragAndDropAnimationCore.ItemAnimation a = this.mItemAnimator.getItemAnimation(position);
        if (a instanceof SemDragAndDropAnimationCore.TranslateItemAnimation) {
            t = (SemDragAndDropAnimationCore.TranslateItemAnimation) a;
        } else {
            t = new SemDragAndDropAnimationCore.TranslateItemAnimation();
        }
        int currentDestOffsetX = t.getDestOffsetX();
        int currentDestOffsetY = t.getDestOffsetY();
        int currentOffsetX = 0;
        int currentOffsetY = 0;
        if (!t.isFinished()) {
            currentOffsetX = (int) t.getCurrentTranslateX();
            currentOffsetY = (int) t.getCurrentTranslateY();
        }
        if (!t.isFinished()) {
            t.setStartAndDuration(t.getProgress());
        } else {
            t.setStartAndDuration(0);
        }
        int destOffsetX = deltaX + currentDestOffsetX;
        int destOffsetY = deltaY + currentDestOffsetY;
        t.translate(
                destOffsetX,
                destOffsetX - currentOffsetX,
                destOffsetY,
                destOffsetY - currentOffsetY);
        this.mItemAnimator.putItemAnimation(position, t);
    }

    private void addReturningTranslation(int position) {
        SemDragAndDropAnimationCore.TranslateItemAnimation t;
        SemDragAndDropAnimationCore.ItemAnimation a = this.mItemAnimator.getItemAnimation(position);
        int xPosAdjust = 0;
        int yPosAdjust = 0;
        if (a instanceof SemDragAndDropAnimationCore.TranslateItemAnimation) {
            t = (SemDragAndDropAnimationCore.TranslateItemAnimation) a;
            xPosAdjust =
                    (int)
                            ((SemDragAndDropAnimationCore.TranslateItemAnimation) a)
                                    .getCurrentTranslateX();
            yPosAdjust =
                    (int)
                            ((SemDragAndDropAnimationCore.TranslateItemAnimation) a)
                                    .getCurrentTranslateY();
        } else {
            t = new SemDragAndDropAnimationCore.TranslateItemAnimation();
        }
        t.translate(0, -xPosAdjust, 0, -yPosAdjust);
        t.setStartAndDuration(t.getProgress());
        this.mItemAnimator.putItemAnimation(position, t);
    }

    private void getDragGrabHandleHitRect(Rect childRect, Rect outGrabHandleRect) {
        if (this.mDragGrabHandleDrawable != null) {
            int drawableWidth = this.mDragGrabHandleDrawable.getIntrinsicWidth();
            int drawableHeight = this.mDragGrabHandleDrawable.getIntrinsicHeight();
            boolean isLayoutRtl = this.mGridView.isLayoutRtl();
            if (isLayoutRtl) {
                childRect.left += this.mDragGrabHandlePadding.right;
                childRect.top += this.mDragGrabHandlePadding.top;
                childRect.right -= this.mDragGrabHandlePadding.left;
                childRect.bottom += this.mDragGrabHandlePadding.bottom;
                Gravity.apply(
                        this.mDragGrabHandlePosGravity,
                        drawableWidth,
                        drawableHeight,
                        childRect,
                        outGrabHandleRect,
                        1);
                return;
            }
            childRect.left += this.mDragGrabHandlePadding.left;
            childRect.top += this.mDragGrabHandlePadding.top;
            childRect.right += this.mDragGrabHandlePadding.right;
            childRect.bottom += this.mDragGrabHandlePadding.bottom;
            Gravity.apply(
                    this.mDragGrabHandlePosGravity,
                    drawableWidth,
                    drawableHeight,
                    childRect,
                    outGrabHandleRect,
                    0);
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
        int index = this.mGridView.indexOfChild(child);
        int pos = this.mGridView.getFirstVisiblePosition() + index;
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
            int index = this.mGridView.indexOfChild(child);
            int pos = this.mGridView.getFirstVisiblePosition() + index;
            if (this.mGridView.getAdapter().isEnabled(pos)) {
                child.getHitRect(this.mTempRect);
                drawDragHandle(canvas, this.mTempRect, false, this.mDndController.canDrag(pos));
            }
        }
    }

    public void dispatchDraw(Canvas canvas) {
        if (isDraggable() && this.mDragViewBitmap != null && !this.mListItemSelectionAnimating) {
            int draggedItemX = this.mDndTouchX - this.mDndTouchOffsetX;
            int draggedItemY = this.mDndTouchY - this.mDndTouchOffsetY;
            canvas.drawBitmap(
                    this.mDragViewBitmap,
                    this.mDragViewBitmapTranslateX + draggedItemX,
                    this.mDragViewBitmapTranslateY + draggedItemY,
                    this.mDragViewBitmapPaint);
            this.mTempRect.left = this.mDragViewBitmapTranslateX + draggedItemX;
            this.mTempRect.top = this.mDragViewBitmapTranslateY + draggedItemY;
            this.mTempRect.bottom = this.mTempRect.top + this.mDragViewRect.height();
            this.mTempRect.right = this.mTempRect.left + this.mDragViewRect.width();
            drawDragHandle(canvas, this.mTempRect, true, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDragViewBitmap() {
        if (this.mDragView != null) {
            this.mDragViewBitmap =
                    SemAnimatorUtils.getBitmapDrawableFromView(this.mDragView).getBitmap();
        }
    }
}
